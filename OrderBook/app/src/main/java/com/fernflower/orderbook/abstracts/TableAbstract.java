package com.fernflower.orderbook.abstracts;

import com.fernflower.orderbook.interfaces.TableInterface;

/**
 * Created by SaprykinAA on 21.07.2015.
 */
public abstract class TableAbstract implements TableInterface{
    public StringBuffer sb;


    public  TableAbstract(){
        sb=new StringBuffer();
    }

    public abstract String getTableName();
    public abstract String sqlCreateTable();


    public String sqlDeleteTable(String tableName){
        return "drop table if exists "+tableName;
    }

}
