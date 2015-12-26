package com.fernflower.orderbook.interfaces;

/**
 * Created by SaprykinAA on 21.07.2015.
 */
public interface TableInterface {

    //типы полей таблиц
    public final String type_text = " text";
    public final String type_real = " real";
    public final String type_boolean = " boolean";
    public final String type_int = " integer";
    public final String type_date = " long";

    public final String _id = "id integer primary key autoincrement";
    public final String id =  "id integer";
    public final String primery_key = " primary key";
    public final String comma = ", ";
}
