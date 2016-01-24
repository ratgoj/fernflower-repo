package com.fernflower.orderbook.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.entities.OrderItem;

import java.util.List;

/**
 * Created by SONY on 17.12.2015.
 */
public class AdapterLvEditOrder extends ArrayAdapter<OrderItem> {

    int resource;

    public AdapterLvEditOrder(Context context, int resource, List<OrderItem> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.itemNumber = (TextView) convertView.findViewById(R.id.eoir_number);
            holder.itemName = (TextView) convertView.findViewById(R.id.eoir_item_name);
            holder.itemAmount = (TextView) convertView.findViewById(R.id.eoir_item_amount);
            holder.itemDiscount = (TextView) convertView.findViewById(R.id.eoir_item_discount);
            holder.itemPrice = (TextView) convertView.findViewById(R.id.eoir_item_price);

            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        OrderItem orderItem = getItem(position);
        holder.itemNumber.setText(String.valueOf(position + 1));
        holder.itemName.setText(orderItem.getItemName());
        String amount = (orderItem.getItemAmount() == 0) ? "" : String.valueOf(orderItem.getItemAmount());
        holder.itemAmount.setText(amount);
        String discount = (orderItem.getItemDiscount() == 0) ? "" : String.valueOf(orderItem.getItemDiscount());
        holder.itemDiscount.setText(discount);
        holder.itemPrice.setText(String.format("%.1f", orderItem.getItemFinalPrice()));

        return convertView;
    }

    static class ViewHolder {
        TextView itemNumber;
        TextView itemName;
        TextView itemAmount;
        TextView itemDiscount;
        TextView itemPrice;
    }
}
