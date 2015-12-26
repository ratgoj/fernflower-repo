package com.fernflower.orderbook.entities;

import com.fernflower.orderbook.abstracts.OrderList;
import com.fernflower.orderbook.helpers.DateHelper;
import com.fernflower.orderbook.settings.AppSettings;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by SONY on 05.09.2015.
 */
public class OrderListInfoComparator  {

    private static DateComparator dateComparator;
    private static PriorityComparator priorityComparator;
    private static NameComparator nameComparator;

    public static Comparator<OrderListInfo> getPriorityComparator(){
        if (priorityComparator == null){
            priorityComparator = new PriorityComparator();
        }

        return priorityComparator;
    }

    public static Comparator<OrderListInfo> getDateComparator(){
        if(dateComparator == null){
            dateComparator = new DateComparator();
        }
        return dateComparator;
    }

    public static Comparator<OrderListInfo> getNameComparator(){
        if (nameComparator == null){
            nameComparator = new NameComparator();
        }
        return nameComparator;
    }

    private static class PriorityComparator implements Comparator<OrderListInfo>{
        @Override
        public int compare(OrderListInfo order1, OrderListInfo order2) {
            int result = order1.getPriority().compareTo(order2.getPriority());
            if(result == 0){
                Date date1 = DateHelper.getInstance().getDateFromMillis(order1.getEndDate());
                Date date2 = DateHelper.getInstance().getDateFromMillis(order2.getEndDate());
                result=date1.compareTo(date2);
            }
            return result;
        }
    }

    private static class DateComparator implements Comparator<OrderListInfo>{

        @Override
        public int compare(OrderListInfo order1, OrderListInfo order2) {
            Date date1 = DateHelper.getInstance().getDateFromMillis(order1.getEndDate());
            Date date2 = DateHelper.getInstance().getDateFromMillis(order2.getEndDate());
            return date1.compareTo(date2);
        }
    }

    private static  class NameComparator implements Comparator<OrderListInfo>{

        @Override
        public int compare(OrderListInfo order1, OrderListInfo order2) {
            int result = order1.getListName().compareTo(order2.getListName());
            if(result == 0){
                Date date1 = DateHelper.getInstance().getDateFromMillis(order1.getEndDate());
                Date date2 = DateHelper.getInstance().getDateFromMillis(order2.getEndDate());
                result=date1.compareTo(date2);
            }
            return result;
        }
    }
}
