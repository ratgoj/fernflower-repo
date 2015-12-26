package com.fernflower.orderbook.entities;

import com.fernflower.orderbook.abstracts.OrderList;

/**
 * Created by SONY on 27.08.2015.
 */
public class OrderListFulfilled extends OrderList {
    private long realEndDate;
    private int reputationPoint;
    private int[] itemsCodes;

    public OrderListFulfilled(){

    }

    public OrderListFulfilled(String name, String clientName, long realEndDate, long endDate, int[] itemsCodes, double listTotalPrice, int reputationPoint){
        this.setListName(name);
        this.setClientName(clientName);
        this.realEndDate=realEndDate;
        this.setEndDate(endDate);
        this.itemsCodes=itemsCodes;
        this.setListTotalPrice(listTotalPrice);
        this.reputationPoint=reputationPoint;

    }

    public long getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(long realEndDate) {
        this.realEndDate = realEndDate;
    }

    public int getReputationPoint() {
        return reputationPoint;
    }

    public void setReputationPoint(int reputationPoint) {
        this.reputationPoint = reputationPoint;
    }

    public int[] getItemsCodes() {
        return itemsCodes;
    }

    public void setItemsCodes(int[] itemsCodes) {
        this.itemsCodes = itemsCodes;
    }

    @Override
    public String dateOfEnd() {
        return null;
    }
}
