package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 22.07.2015.
 */
public class TableListsFulfilled extends TableAbstract {
    public static final String tableName="order_lists_fulfilled";

    public final String fulfilledListName = "list_name";
    public final String fulfilledClienName = "client_name";
    public final String fulfilledEndDate = "end_date";
    public final String fulfilledRealEndDate = "real_end_date";
    public final String fulfilledItemsCodes = "items_codes";
    public final String fulfilledTotalPrice = "total_price";
    public final String fulfilledReputationPoint = "reputation_point";

    public TableListsFulfilled(){
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
        sb.append(fulfilledListName).append(type_text).append(comma);
        sb.append(fulfilledClienName).append(type_text).append(comma);
        sb.append(fulfilledEndDate).append(type_date).append(comma);
        sb.append(fulfilledRealEndDate).append(type_date).append(comma);
        sb.append(fulfilledItemsCodes).append(type_text).append(comma);
        sb.append(fulfilledTotalPrice).append(type_real).append(comma);
        sb.append(fulfilledReputationPoint).append(type_int);
        sb.append(")");
        return sb.toString();
    }
}
