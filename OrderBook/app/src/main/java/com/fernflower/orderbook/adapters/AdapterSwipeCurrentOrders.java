package com.fernflower.orderbook.adapters;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.entities.OrderListInfo;
import com.fernflower.orderbook.enums.Priority;
import com.fernflower.orderbook.fragments.EditOrderFragment;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;

import java.util.List;

/**
 * Created by SONY on 03.09.2015.
 */
public class AdapterSwipeCurrentOrders extends ArrayAdapter {
    List<OrderListInfo> ordersData;
    OrderListInfo order = null;
    Context context;
    int layoutResID;

    Fragment fragmentScreen;
    FragmentManager fragmentManager;

    public AdapterSwipeCurrentOrders(Context context, int layoutResourceId, List<OrderListInfo> data){
        super(context, layoutResourceId, data);

        this.context=context;
        this.layoutResID = layoutResourceId;
        this.ordersData = data;
        this.fragmentManager=((Activity)context).getFragmentManager();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        OrdersHolder holder=null;
        View row=convertView;

        if(row==null){
            LayoutInflater inflater=((Activity)context).getLayoutInflater();
            row=inflater.inflate(layoutResID,parent,false);

            holder=new OrdersHolder();

            holder.priority_icon=(ImageView)row.findViewById(R.id.row_priority_img);
            holder.order_name=(TextView)row.findViewById(R.id.row_order_name);
            holder.customer_name=(TextView)row.findViewById(R.id.row_customer_name);
            holder.date_of_creation=(TextView)row.findViewById(R.id.row_creation_date);
            holder.date_of_end=(TextView)row.findViewById(R.id.row_end_date);
            holder.total_price=(TextView)row.findViewById(R.id.row_number_total_price);

            holder.edit=(ImageButton)row.findViewById(R.id.swipe_edit);
            holder.delete=(ImageButton)row.findViewById(R.id.swipe_delete);

            row.setTag(holder);
        }else{
            holder=(OrdersHolder)row.getTag();
        }

        order = ordersData.get(position);
        //устанавливаем картинку приоритета
        setPriorityImg(order.getPriority());

        holder.priority_icon.setImageDrawable(order.getIcon());
        holder.order_name.setText(order.getListName());
        holder.customer_name.setText(order.getClientName());
        holder.date_of_creation.setText(order.creatingDate());
        holder.date_of_end.setText(order.dateOfEnd());
        holder.total_price.setText(String.valueOf(order.getListTotalPrice()));

        holder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //StringHelper.getInstance().showHint(context, "Edit click on position" + position);
                fragmentScreen=new EditOrderFragment();
                Bundle args=new Bundle();
                args.putString(StringHelper.ORDER_NAME_TAG, ordersData.get(position).getListName());
                fragmentScreen.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentScreen).commit();
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                deleteOrder(position);
            }
        });

        return row;
    }

    private void deleteOrder(int position){
        //ordersData.remove(position);
        ListsLayerHelper.getInstance().getCurrentOrders().remove(position);
        notifyDataSetChanged();
    }

    //устанавливаем картинку приоритета
    private void setPriorityImg(Priority priority){
        if(order!=null){
            switch (priority){
                case Low:
                    order.setIcon(getContext().getResources().getDrawable(R.mipmap.priority_low));
                    break;
                case  Medium:
                    order.setIcon(getContext().getResources().getDrawable(R.mipmap.priority_medium));
                    break;
                case  High:
                    order.setIcon(getContext().getResources().getDrawable(R.mipmap.priority_high));
                    break;
            }
        }
    }

    //Вспомогательный класс для адаптера, все поля это элементы строки заказа
    static class OrdersHolder{
        ImageView priority_icon;
        TextView order_name;
        TextView customer_name;
        TextView date_of_creation;
        TextView date_of_end;
        TextView total_price;

        ImageButton edit;
        ImageButton delete;
        }
}
