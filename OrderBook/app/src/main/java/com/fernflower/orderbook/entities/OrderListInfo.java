package com.fernflower.orderbook.entities;

import android.graphics.drawable.Drawable;

import com.fernflower.orderbook.abstracts.OrderList;
import com.fernflower.orderbook.enums.Priority;
import com.fernflower.orderbook.helpers.DateHelper;
import com.fernflower.orderbook.helpers.StringHelper;
import com.fernflower.orderbook.settings.AppSettings;

/**
 * Created by SONY on 27.08.2015.
 */
public class OrderListInfo extends OrderList{

    private Priority priority;
    private long createDate;
    private Drawable icon;

    public OrderListInfo(){

    }


    public OrderListInfo(Priority priority, String name, String clientName, long createDate, long endDate, double listTotalPrice){
        this.priority=priority;
        this.setListName(name);
        this.setClientName(clientName);
        this.createDate=createDate;
        this.setEndDate(endDate);
        this.setListTotalPrice(listTotalPrice);
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }



    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }




    public String creatingDate() {
        if(AppSettings.getInstance().isAmerican()){
            return DateHelper.getInstance().dateToAmerFormatString(createDate);
        }else {
            return DateHelper.getInstance().dateToFormatString(createDate);
        }
    }


    public String dateOfEnd() {
        if(AppSettings.getInstance().isAmerican()){
            return DateHelper.getInstance().dateToAmerFormatString(this.getEndDate());
        }else {
            return DateHelper.getInstance().dateToFormatString(this.getEndDate());
        }
    }


    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
