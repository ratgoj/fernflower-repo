package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 21.07.2015.
 */
public class TableListsNames extends TableAbstract {
    public static final String tableName="order_lists_names";

    public final String orderListsName = "order_list_name";
    public final String orderListsPriority = "order_priority";
    public final String orderListsClientName = "order_client_name";
    public final String orderListsCreateDate = "order_create_date";
    public final String orderListsEndDate = "order_end_date";
    public final String orderListsTotalPrice = "order_total_price";

    public TableListsNames(){
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
        sb.append(orderListsPriority).append(type_text).append(comma);
        sb.append(orderListsName).append(type_text).append(comma);
        sb.append(orderListsClientName).append(type_text).append(comma);
        sb.append(orderListsCreateDate).append(type_date).append(comma);
        sb.append(orderListsEndDate).append(type_date).append(comma);
        sb.append(orderListsTotalPrice).append(type_real);
        sb.append(")");
        return sb.toString();
    }

}
