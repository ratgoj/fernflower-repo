package com.fernflower.orderbook.datebase;

/**
 * Created by SONY on 06.07.2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.abstracts.TableAbstract;
import com.fernflower.orderbook.enums.Priority;
import com.fernflower.orderbook.helpers.StringHelper;
import com.fernflower.orderbook.settings.AppSettings;

import java.sql.Date;
import java.util.ArrayList;

public class DbLayer extends SQLiteOpenHelper {

    public static final int dbVersion = 1;
    public static final String databaseName = "order_book.db";

    private ArrayList<TableAbstract> tableList;

    private static SQLiteDatabase db;

    //Классы таблиц
    private TableListsNames t_listsNames;
    private TableListItems t_listItems;
    private TableListsFulfilled t_listFulfilled;
    private TableCatalog t_catalog;
    private TableSettings t_settings;
    private TableTemplates t_templates;

    private String selection;
    private String orderBy;
    private String[] selectionArgs;

    public static DbLayer instance;

    private Context dbContext;


    private DbLayer(Context context) {
        //
        super(context, databaseName, null, dbVersion);
        initContext(context);
        tableList=new ArrayList<>();
        addTablesToList();
    }

    private void initContext(Context context) {
        this.dbContext = context;
    }


    public static DbLayer getInstance(Context context) {
        if (instance == null) {
            instance = new DbLayer(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DBLayer onCreate");
        for(TableAbstract table: tableList){
            db.execSQL(table.sqlCreateTable());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteAllTables(db);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    private void addTablesToList(){
        tableList.add(t_listsNames=new TableListsNames());
        tableList.add(t_listItems=new TableListItems());
        tableList.add(t_listFulfilled=new TableListsFulfilled());
        tableList.add(t_catalog=new TableCatalog());
        tableList.add(t_settings=new TableSettings());
        tableList.add(t_templates = new TableTemplates());
    }
     private void deleteAllTables(SQLiteDatabase db){
         if(!tableList.isEmpty()) {
             for (TableAbstract table : tableList) {
                 db.execSQL(table.sqlDeleteTable(table.getTableName()));
             }
         }
     }

    // Проверка пуста ли таблица
    public boolean isTableEmpty(String tableName) {
        boolean isEmpty = false;
        // проверка существования записей
        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        if (c.getCount() == 0) {
            isEmpty = true;
        }
        c.close();
        db.close();
        return isEmpty;
    }

    //Чтение настроек
    public void readSettings(){
       if(t_settings!=null){
           if(isTableEmpty(t_settings.getTableName())){
               setDefaultSettings();
               setAppSettings();
           }else{
              setAppSettings();
           }
       }else{
           StringHelper.getInstance().showError(dbContext, dbContext.getResources().getString(R.string.error_table_not_found)+" t_settings not exists");
       }
    }



    //Вставка настоек по умолумолчанию
    private void setDefaultSettings(){
        if(!tableList.isEmpty()) {
            db = DbLayer.getInstance(dbContext).getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put(t_settings.settingsName, AppSettings.showHint);
            cv.put(t_settings.settingsValue, "true");
            db.insert(t_settings.getTableName(), null, cv);
            cv.clear();

            cv.put(t_settings.settingsName, AppSettings.itemAutoinc);
            cv.put(t_settings.settingsValue, "true");
            db.insert(t_settings.getTableName(), null, cv);
            cv.clear();

            cv.put(t_settings.settingsName, AppSettings.offeredTempl);
            cv.put(t_settings.settingsValue, "true");
            db.insert(t_settings.getTableName(), null, cv);
            cv.clear();

            cv.put(t_settings.settingsName, AppSettings.daysBeforeHighPriority);
            cv.put(t_settings.settingsValue, String.valueOf(AppSettings.DAYS_H_PRIORITY));
            db.insert(t_settings.getTableName(), null, cv);
            cv.clear();

        }else{
            StringHelper.getInstance().showError(dbContext, dbContext.getResources().getString(R.string.error_no_tables));
        }
    }

    public String getSetting(String nameSetting){
        String resultValue=null;
        db = DbLayer.getInstance(dbContext).getReadableDatabase();
        String[] columns={t_settings.settingsValue};
        selection=t_settings.settingsName+"=?";
        selectionArgs=new String[]{nameSetting};
        Cursor cursor=db.query(t_settings.getTableName(), columns, selection, selectionArgs, null, null, null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                resultValue=cursor.getString(cursor.getColumnIndex(t_settings.settingsValue));
            }
        }
        return resultValue;
    }

    private void setAppSettings(){
        AppSettings.getInstance().setIsShowHint(new Boolean(getSetting(AppSettings.showHint)));
        AppSettings.getInstance().setIsOfferedTemplate(new Boolean(getSetting(AppSettings.offeredTempl)));
        AppSettings.getInstance().setIsItemAutocode(new Boolean(getSetting(AppSettings.itemAutoinc)));
        AppSettings.getInstance().setDaysHighPriority(Integer.valueOf(getSetting(AppSettings.daysBeforeHighPriority)));
    }

    //Вставки данных в таблицы
    public void insertInListsNames(Priority priority, String list_name, String client_name, Long create_date, Long end_date, Double total_price){
        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        ContentValues cv = new ContentValues();
        String prior=priority.toString();

        cv.put(t_listsNames.orderListsPriority, prior);
        cv.put(t_listsNames.orderListsName, list_name);
        cv.put(t_listsNames.orderListsClientName, client_name);
        cv.put(t_listsNames.orderListsCreateDate, create_date.toString());
        cv.put(t_listsNames.orderListsEndDate, end_date.toString());
        cv.put(t_listsNames.orderListsTotalPrice, total_price);

        db.insert(t_listsNames.getTableName(), null, cv);
        db.close();
    }

    public void insertInCatalog(int item_code, String item_name, float item_price){
        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(t_catalog.catalogItemCode, item_code);
        cv.put(t_catalog.catalogItemName, item_name);
        cv.put(t_catalog.catalogItemPrice, item_price);

        System.out.println("CV="+cv);

        db.insert(t_catalog.getTableName(), null, cv);
        db.close();
    }

    public void insertInListItems(int list_name_id, int item_code, int item_amount, float item_discount, float item_final_price){
        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(t_listItems.itemListNameId, list_name_id);
        cv.put(t_listItems.itemCode, item_code);
        cv.put(t_listItems.itemAmount, item_amount);
        cv.put(t_listItems.itemDiscount, item_discount);
        cv.put(t_listItems.itemFinalPrice, item_final_price);

        System.out.println("CV="+cv);

        db.insert(t_listItems.getTableName(), null, cv);
        db.close();
    }


    public void insertInTemplates(String templ_name, int item_codes, int item_discounts){
        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(t_templates.templateName,templ_name);
        cv.put(t_templates.templateItemCode,item_codes);
        cv.put(t_templates.templateItemDiscount, item_discounts);

        System.out.println("CV="+cv);

        db.insert(t_templates.getTableName(), null, cv);
        db.close();
    }

    public void insertInListsFulfilled(String list_name, String clien_name,  Long end_date, Long real_end_date, ArrayList<String> item_codes, float list_total_price, int reputation_point){

        String items_codes = null;
        items_codes=item_codes.toString().replace("[", "").replace("]", "").replace(", ", ",");

        db = DbLayer.getInstance(dbContext).getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(t_listFulfilled.fulfilledListName,list_name);
        cv.put(t_listFulfilled.fulfilledClienName, clien_name);
        cv.put(t_listFulfilled.fulfilledEndDate, end_date);
        cv.put(t_listFulfilled.fulfilledRealEndDate, real_end_date);
        cv.put(t_listFulfilled.fulfilledItemsCodes, items_codes);
        cv.put(t_listFulfilled.fulfilledTotalPrice, list_total_price);
        cv.put(t_listFulfilled.fulfilledReputationPoint, reputation_point);

        System.out.println("CV="+cv);

        db.insert(t_listFulfilled.getTableName(), null, cv);
        db.close();
    }
}
