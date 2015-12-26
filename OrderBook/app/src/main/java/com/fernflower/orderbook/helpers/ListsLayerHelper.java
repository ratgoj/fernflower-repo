package com.fernflower.orderbook.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

import com.fernflower.orderbook.entities.CatalogItem;
import com.fernflower.orderbook.entities.OrderItem;
import com.fernflower.orderbook.entities.OrderListInfo;
import com.fernflower.orderbook.enums.Priority;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by SONY on 07.09.2015.
 */
public class ListsLayerHelper {

    public static ListsLayerHelper instance;

    private OrderListInfo order;
    private ArrayList<OrderListInfo> currentOrders = new ArrayList<OrderListInfo>();

    private CatalogItem item;
    private ArrayList<CatalogItem> catalogItems = new ArrayList<CatalogItem>();

    private String currentOrderName;
    private OrderItem orderItem;
    private ArrayList<OrderItem> orderListItems = new ArrayList<>();
    private HashMap<String, ArrayList<OrderItem>> allOrders = new HashMap<>();

    private ListsLayerHelper(){
        tempAddOrders();
        tempAddCatalogItems();
    }

    public static ListsLayerHelper getInstance() {
        if(instance==null){
            instance = new ListsLayerHelper();
        }
        return instance;
    }



    public ArrayList<OrderListInfo> getCurrentOrders() {
        return currentOrders;
    }

    public void setCurrentOrders(ArrayList<OrderListInfo> currentOrders) {
        this.currentOrders = currentOrders;
        System.out.println("Layer Array: "+this.currentOrders);
    }

    public ArrayList<CatalogItem> getCatalogItems() {
        return catalogItems;
    }

    public void setCatalogItems(ArrayList<CatalogItem> catalogItems) {
        this.catalogItems = catalogItems;
    }


    //Перевод Dp в пиксели, для SwipeView
    public int convertDpToPixel(Context context,float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public String getCurrentOrderName() {
        return currentOrderName;
    }

    public void setCurrentOrderName(String currentOrderName) {
        this.currentOrderName = currentOrderName;
    }

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }

    public ArrayList<OrderItem> getOrderListItems() {
        return orderListItems;
    }

    public void setOrderListItems(ArrayList<OrderItem> orderListItems) {
        this.orderListItems = orderListItems;
    }

    public void setItemsToOrder(String orderName, ArrayList<OrderItem> itemsData){
        allOrders.put(orderName, itemsData);
    }

    public void setCurrentDataToOrder(){
        setItemsToOrder(currentOrderName, orderListItems);
    }

    public ArrayList<OrderItem> getOrderItems(String orderName){
        return allOrders.get(orderName);
    }

    private void tempAddOrders(){
        long createTime=DateHelper.getInstance().getCurrentDateInMillis();
        //order = new OrderListInfo(Priority.Low, "First Order", "Paul", createTime, createTime+1000000, 0);
        this.currentOrders.add(new OrderListInfo(Priority.Low, "First Order", "Paul", createTime, createTime+1000000, 0));
        //order = new OrderListInfo(Priority.Medium, "Second Order", "Jack", createTime, createTime+2000000, 0);
        this.currentOrders.add(new OrderListInfo(Priority.Medium, "Second Order", "Jack", createTime, createTime+2000000, 0));
        //order = new OrderListInfo(Priority.High, "Third Order", "Mike", createTime, createTime, 0);
        this.currentOrders.add(new OrderListInfo(Priority.High, "Third Order", "Mike", createTime, createTime, 0));

        order = new OrderListInfo(Priority.Low, "4 Order", "Klara", createTime, createTime-10000000, 0);
        this.currentOrders.add(order);
        order = new OrderListInfo(Priority.Medium, "5 Order", "Andy", createTime, createTime-20000000, 0);
        this.currentOrders.add(order);
        order = new OrderListInfo(Priority.Low, "6 Order", "Mishel", createTime, createTime-20000000, 0);
        this.currentOrders.add(order);
        order = new OrderListInfo(Priority.Low, "7 Order", "April", createTime, createTime-20000000, 0);
        this.currentOrders.add(order);
    }

    private void tempAddCatalogItems(){
        for(int icode=1; icode<=10; icode++){
            String str= icode%2==0? "a":"r";
            item = new CatalogItem(icode,str+"Item Name:"+icode,icode+100.2f, "Describe Item - "+icode);
            catalogItems.add(item);
        }
    }

}
