package com.fernflower.orderbook.helpers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by SONY on 27.08.2015.
 */
public class DateHelper {

    public static DateHelper instance;
    private Calendar calendar= Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat americanSdf = new SimpleDateFormat("MM.dd.yyyy");

    private DateHelper(){}

    public static DateHelper getInstance(){
        if(instance==null){
            instance=new DateHelper();
        }
        return instance;
    }

    public int daysBetweenDates(long endDate, long currentDate){
        SimpleDateFormat mySdf = new SimpleDateFormat("ddMMyyyy");
        return (Integer.parseInt(mySdf.format(endDate))-Integer.parseInt(mySdf.format(currentDate)))/1000000;
    }

    public int daysBetweenCurrent(long endDate){
        return daysBetweenDates(endDate, getCurrentDateInMillis());
    }

    public String dateToFormatString(long date){
        return sdf.format(date);
    }

    public String dateToAmerFormatString(long date){
        return americanSdf.format(date);
    }

    public Date getCurrentDate(){
        return calendar.getTime();
    }

    public long getCurrentDateInMillis(){
        return calendar.getTimeInMillis();
    }

    public Date getDateFromMillis(long millis){
        return new Date(millis);
    }

}
