package com.fernflower.orderbook.datebase;

import com.fernflower.orderbook.abstracts.TableAbstract;

/**
 * Created by SaprykinAA on 22.07.2015.
 */
public class TableSettings extends TableAbstract {
    public static final String tableName="settings";

    public final String settingsName = "settings_name";
    public final String settingsValue = "settings_value";

    public TableSettings(){
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
        sb.append(settingsName).append(type_text).append(comma);
        sb.append(settingsValue).append(type_text);
        sb.append(")");
        return sb.toString();
    }
}
