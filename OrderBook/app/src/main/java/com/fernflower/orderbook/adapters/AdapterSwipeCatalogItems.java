package com.fernflower.orderbook.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.entities.CatalogItem;
import com.fernflower.orderbook.fragments.CatalogFragment;
import com.fernflower.orderbook.helpers.DialogHelper;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;
import com.fernflower.orderbook.settings.AppSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SONY on 12.09.2015.
 */
public class AdapterSwipeCatalogItems extends ArrayAdapter {

    protected static final int RESULT_SPEACH = 1;

    Context context;
    List<CatalogItem> originalItemsData = new ArrayList<>();
    List<CatalogItem> filterItemsData = new ArrayList<>();
    CatalogItem currentItem=null;
    int layoutResId;

    private ItemFilter myItemFilter;
    private CatalogFragment screen;
    private Dialog editItem;
    private EditText voiceResult;

    public AdapterSwipeCatalogItems(Context context, int layoutResourceId, List data, CatalogFragment screen) {
        super(context, layoutResourceId, data);
        this.context=context;
        this.layoutResId=layoutResourceId;
        this.originalItemsData.addAll(data);
        this.filterItemsData.addAll(data);
        this.screen=screen;
    }


    public void changeArrayData(List data){
        this.originalItemsData.clear();
        this.filterItemsData.clear();
        this.originalItemsData.addAll(data);
        this.filterItemsData.addAll(data);
    }


    private void editItemDialog(final int position){
        CatalogItem item = originalItemsData.get(position);
        editItem = new Dialog(context);
        editItem.requestWindowFeature(Window.FEATURE_NO_TITLE);
        editItem.setContentView(R.layout.dialog_add_edit_catalog_item);
        //Инициализируем все поля диалового окна
        final EditText itemCode = (EditText) editItem.findViewById(R.id.ci_dialog_item_code);
        final EditText itemName = (EditText) editItem.findViewById(R.id.ci_dialog_item_name);
        final EditText itemPrice = (EditText) editItem.findViewById(R.id.ci_dialog_item_price);
        final EditText itemDescribe = (EditText) editItem.findViewById(R.id.ci_dialog_item_describe);

        ImageButton microphone = (ImageButton) editItem.findViewById(R.id.ci_dialog_microphone_button);
        ImageButton cancel = (ImageButton) editItem.findViewById(R.id.ci_dialog_cancel_button);
        ImageButton ok = (ImageButton) editItem.findViewById(R.id.ci_dialog_ok_button);


        itemCode.setText(String.valueOf(item.getItemCode()));
        itemName.setText(item.getItemName());
        itemPrice.setText(String.valueOf(item.getItemPrice()));
        itemDescribe.setText(item.getItemDescribe());

        if(AppSettings.getInstance().isItemAutocode()){
            itemName.requestFocus();
        }else{
            itemCode.setEnabled(true);
            itemCode.requestFocus();
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItem.cancel();
            }
        });

        microphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                if(itemCode.isFocused() || itemName.isFocused() || itemDescribe.isFocused() || itemPrice.isFocused() ){
                    try{
                        screen.setCurrentEdits(itemCode, itemName, itemPrice, itemDescribe);
                        screen.startActivityForResult(intent, RESULT_SPEACH);

                    }catch (ActivityNotFoundException e){
                        StringHelper.getInstance().showError(context,context.getResources().getString(R.string.error_voice_not_supported));
                    }
                }else{
                    StringHelper.getInstance().showError(context,context.getResources().getString(R.string.voice_no_choose_input));
                }

            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DialogHelper.getInstance().checkEditOnText(itemCode, itemName, itemPrice, itemDescribe )) {
                    int code = Integer.parseInt(itemCode.getText().toString());
                    String name = itemName.getText().toString();
                    float price = Float.parseFloat(itemPrice.getText().toString());
                    String describe = itemDescribe.getText().toString();
                    CatalogItem itemEdit = new CatalogItem(code, name, price, describe);
                    screen.editItemdata(position, itemEdit);
                    editItem.dismiss();

                }else{
                    StringHelper.getInstance().showHint(context, context.getString(R.string.no_data_from_inputs));
                }
            }
        });

        editItem.show();
    }


    @Override
    public Filter getFilter() {
        if(myItemFilter==null){
            myItemFilter = new ItemFilter();
        }
        return myItemFilter;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemsHolder holder=null;
        View row=convertView;


        if(row==null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResId, parent, false);

            holder = new ItemsHolder();

            holder.itemCode=(TextView)row.findViewById(R.id.ci_code);
            holder.itemName=(TextView)row.findViewById(R.id.ci_name);
            holder.itemPrice=(TextView)row.findViewById(R.id.ci_price);

            holder.edit=(ImageButton)row.findViewById(R.id.ci_swipe_edit);
            holder.delete=(ImageButton)row.findViewById(R.id.ci_swipe_delete);
            holder.addToOrder=(ImageButton)row.findViewById(R.id.ci_swipe_add_item);

            row.setTag(holder);
        }else{
            holder=(ItemsHolder)row.getTag();
        }

        currentItem = filterItemsData.get(position);

        holder.itemCode.setText(String.valueOf(currentItem.getItemCode()));
        holder.itemName.setText(currentItem.getItemName());
        holder.itemPrice.setText(String.valueOf(currentItem.getItemPrice()));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editItemDialog(position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(position);
            }
        });

        holder.addToOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringHelper.getInstance().showHint(context, "Add item to order stub: "+position);
            }
        });
        return row;

    }

    private void deleteItem(int position){
        originalItemsData.remove(position);
        filterItemsData.remove(position);
        ListsLayerHelper.getInstance().getCatalogItems().remove(position);
        notifyDataSetChanged();
    }
    //Вспомогательный класс для адаптера, все поля это элементы строки
    static class ItemsHolder{
        TextView itemCode;
        TextView itemName;
        TextView itemPrice;

        ImageButton edit;
        ImageButton delete;
        ImageButton addToOrder;
    }

    private class ItemFilter extends Filter{
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            constraint = constraint.toString().toLowerCase();
            FilterResults results =new FilterResults();
            if(constraint!=null && constraint.length()>0){
                ArrayList<CatalogItem> filtredItems = new ArrayList<>();
                for(int i = 0; i < originalItemsData.size(); i++){
                    CatalogItem item = originalItemsData.get(i);
                    if(item.getItemName().toLowerCase().contains(constraint)){
                        filtredItems.add(item);
                    }
                    results.count=filtredItems.size();
                    results.values=filtredItems;
                }
            }else{
                synchronized (this){
                    results.values=originalItemsData;
                    results.count=originalItemsData.size();
                }
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterItemsData=(ArrayList<CatalogItem>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i=0; i < filterItemsData.size(); i++){
                add(filterItemsData.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}
