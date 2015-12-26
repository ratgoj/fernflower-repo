package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 22.07.2015.
 */
public class TableListItems extends TableAbstract {

    public final String tableName="order_list_items";

    public final String itemListNameId = "list_name_id";
    public final String itemCode = "item_code";
    public final String itemAmount = "item_amount";
    public final String itemDiscount = "item_discount";
    public final String itemFinalPrice = "item_final_price";

    public TableListItems(){
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
        sb.append(_id).append(comma);
        sb.append(itemListNameId).append(type_int).append(comma);
        sb.append(itemCode).append(type_int).append(comma);
        sb.append(itemAmount).append(type_int).append(comma);
        sb.append(itemDiscount).append(type_real).append(comma);
        sb.append(itemFinalPrice).append(type_real).append(comma);
        sb.append("FOREIGN KEY(").append(itemListNameId).append(") REFERENCES ").append(TableListsNames.tableName).append("(id)").append(comma);
        sb.append("FOREIGN KEY(").append(itemCode).append(") REFERENCES ").append(TableCatalog.tableName).append("(item_code)");
        sb.append(")");
        return sb.toString();
    }

}
