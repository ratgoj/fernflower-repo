package com.fernflower.orderbook.entities;

import com.fernflower.orderbook.abstracts.Items;

/**
 * Created by SONY on 12.09.2015.
 */
public class CatalogItem extends Items {
    private String itemName;
    private float itemPrice;
    private String itemDescribe;

    public CatalogItem() {
    }

    public CatalogItem(int code, String itemName, float price, String describe) {
        this.setItemCode(code);
        this.itemName = itemName;
        this.itemPrice = price;
        this.itemDescribe = describe;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String toString() {
        return this.itemName;
    }

    public String getItemDescribe() { return itemDescribe; }

    public void setItemDescribe(String itemDescribe) { this.itemDescribe = itemDescribe; }
}
