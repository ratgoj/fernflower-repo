package com.fernflower.orderbook.helpers;

import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.fernflower.orderbook.entities.OrderListInfo;
import com.fernflower.orderbook.settings.AppSettings;

import java.util.ArrayList;

/**
 * Created by SONY on 15.07.2015.
 */
public class StringHelper {
    Context context;

    public static final String ORDER_NAME_TAG = "oder_name_tag";
    public static final String VIEW_ORDER_NAME_TAG = "view_order_name_tag";

    public static StringHelper instance;
    private String tempOrderName;
    private int lniter;

    private StringHelper(){

    }

    public static StringHelper getInstance() {
        if(instance==null){
            instance=new StringHelper();
        }
        return instance;
    }

    public void showHint(Context context, String message){
        if(AppSettings.getInstance().isShowHint()){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }


    public void showError(Context context, String message){
        if(AppSettings.getInstance().isShowError()){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    public void showDescription(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //Метод для проверки совпадения имени списка и замены его на отличающийся
    public String varificationOfOrderName(String orderName, ArrayList<OrderListInfo> orders){
        String res;
        boolean isMached=false;
        if(orders.isEmpty()){
            res = orderName;
        }else {
            for (OrderListInfo order : orders) {
                if (orderName.equals(order.getListName())) {
                    lniter++;
                    tempOrderName = " " + String.valueOf(lniter);
                    if (lniter > 1) {
                        orderName = stringParse(orderName);
                    }
                    isMached = true;
                    break;
                } else {
                    tempOrderName = "";
                }
            }
            if (isMached) {
                res = varificationOfOrderName(orderName + tempOrderName, orders);
            } else {
                if (tempOrderName != null) {
                    res = orderName + tempOrderName;
                } else {
                    res = orderName;
                }
            }
            lniter = 0;
        }
        return res;
    }

    //Работа с именем списка откусываем после последнего пробела
    public String stringParse(String enteredListName){
        enteredListName=enteredListName.substring(0, enteredListName.lastIndexOf(" "));
        return enteredListName;
    }

    //Получить или текст или подсказку из EditText
    public String getEditTextContent(EditText editText){
        String result;
        if(editText.getText()!=null && editText.getText().length()!=0){
            result=editText.getText().toString();
        }else{
            result=editText.getHint().toString();
        }
        return result;
    }
}
