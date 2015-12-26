package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 22.07.2015.
 */
public class TableTemplates extends TableAbstract {
    public static final String tableName="templates";

    public final String templateName = "template_name";
    public final String templateItemCode = "item_code";
    public final String templateItemDiscount = "item_discount";

    public TableTemplates(){
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
        sb.append(templateName).append(type_text).append(comma);
        sb.append(templateItemCode).append(type_int).append(comma);
        sb.append(templateItemDiscount).append(type_real);
        sb.append(")");
        return sb.toString();
    }
}
