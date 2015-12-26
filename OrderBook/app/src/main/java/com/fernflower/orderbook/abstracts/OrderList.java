package com.fernflower.orderbook.abstracts;

/**
 * Created by SONY on 28.08.2015.
 */
public abstract class OrderList {
    private String listName;
    private String clientName;
    private long endDate;
    private double listTotalPrice;

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public double getListTotalPrice() {
        return listTotalPrice;
    }

    public void setListTotalPrice(double listTotalPrice) {
        this.listTotalPrice = listTotalPrice;
    }

    public abstract String dateOfEnd();
}
