package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 22.07.2015.
 */
public class TableCatalog extends TableAbstract {
    public static final String tableName="catalog";

    public final String catalogItemCode = "item_code";
    public final String catalogItemName = "item_name";
    public final String catalogItemPrice = "item_price";
    public final String catalogItemDescribe = "item_describe";

    public TableCatalog(){
        sb.delete(0, sb.length());
    }

    @Override
    public  String getTableName() {
        return tableName;
    }

    @Override
    public String sqlCreateTable() {
        sb.append("create table if not exists ");
        sb.append(tableName);
        sb.append(" (");
        sb.append(id).append(comma);
        sb.append(catalogItemCode).append(type_int).append(comma);
        sb.append(catalogItemName).append(type_text).append(comma);
        sb.append(catalogItemPrice).append(type_real).append(comma);
        sb.append(catalogItemDescribe).append(type_text).append(comma);
        sb.append("primary key(id ").append(comma).append(catalogItemCode).append(")");
        sb.append(")");
        return sb.toString();
    }
}
