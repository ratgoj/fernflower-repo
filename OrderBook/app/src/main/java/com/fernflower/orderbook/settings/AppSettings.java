package com.fernflower.orderbook.settings;

import com.fernflower.orderbook.datebase.DbLayer;

/**
 * Created by SONY on 06.07.2015.
 */
public class AppSettings {

    public  static AppSettings instance;

    public static final String showHint="show_hint";
    public static final String itemAutoinc="item_autoincrement";
    public static final String offeredTempl="offered_template";
    public static final String daysBeforeHighPriority="days_before_high_priority";
    public static int DAYS_H_PRIORITY=3;

    private boolean isShowHint=false;
    private boolean isItemAutocode=false;
    private boolean isOfferedTemplate=false;
    private int daysHighPriority;

    private boolean american=false;


    //Temp vars
    private boolean isShowError=true;

    private int reputation;

    private AppSettings(){

    }

    public static AppSettings getInstance() {
        if (instance==null){
            instance=new AppSettings();
        }
        return instance;
    }

    public boolean isShowHint() {
        return isShowHint;
    }

    public void setIsShowHint(boolean isShowHint) {
        this.isShowHint = isShowHint;
    }

    public boolean isItemAutocode() {
        return isItemAutocode;
    }

    public void setIsItemAutocode(boolean isItemAutocode) {
        this.isItemAutocode = isItemAutocode;
    }

    public boolean isOfferedTemplate() {
        return isOfferedTemplate;
    }

    public void setIsOfferedTemplate(boolean isOfferedTemplate) {
        this.isOfferedTemplate = isOfferedTemplate;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public boolean isShowError() {
        return isShowError;
    }

    public void setShowError(boolean isShowError) {
        this.isShowError = isShowError;
    }

    public int getDaysHighPriority() {
        return daysHighPriority;
    }

    public void setDaysHighPriority(int daysHighPriority) {
        this.daysHighPriority = daysHighPriority;
    }

    public boolean isAmerican() {
        return american;
    }

    public void setAmerican(boolean american) {
        this.american = american;
    }
}
