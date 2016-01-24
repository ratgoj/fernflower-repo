package com.fernflower.orderbook.entities;

import com.fernflower.orderbook.abstracts.Items;

/**
 * Created by SONY on 11.10.2015.
 */
public class OrderItem  extends Items{
    private String itemName;
    private int itemAmount;
    private float itemPrice;
    private float itemDiscount;
    private float itemFinalPrice;
    
    public OrderItem(int code, String name, float itemPrice){
        super.setItemCode(code);
        this.itemName = name;
        this.itemPrice = itemPrice;
        this.itemAmount = 1;
        this.itemDiscount = 0;
        this.itemFinalPrice = calculateThisFinalPrise();
    }

    public OrderItem(int code, String name, float itemPrice, float discount){
        super.setItemCode(code);
        this.itemName = name;
        this.itemPrice = itemPrice;
        this.itemAmount = 1;
        this.itemDiscount = discount;
        this.itemFinalPrice = calculateThisFinalPrise();
    }

    private float calculateFinalPrise(float price, float discount, int amount){
        return (price-(price*(discount/100)))*amount;
    }

    public float calculateThisFinalPrise(){
        return calculateFinalPrise(this.itemPrice, this.itemDiscount, this.itemAmount);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemAmount() {
       /* if(this.itemAmount==0){
            this.itemAmount=1;
        }*/
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public float getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(float itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public float getItemFinalPrice() {
        this.itemFinalPrice = calculateThisFinalPrise();
        return this.itemFinalPrice;
    }

}
