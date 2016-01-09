package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.entities.OrderItem;
import com.fernflower.orderbook.helpers.ListsLayerHelper;
import com.fernflower.orderbook.helpers.StringHelper;

import java.util.ArrayList;

/**
 * Created by SONY on 08.09.2015.
 */
public class EditOrderFragment extends Fragment {
    /*Сделать сохранение в базу Имени списка и элементов (отдельно)*/
    private Bundle args;
    private ArrayList<OrderItem> editOrderItems;
    private OrderItem currentOrderItem;
    ListView itemsListView;
    EditText findByCode;
    Button searchButton;
    Button addFindItem;
    TextView itemName;
    EditText itemAmount;
    EditText itemDiscount;

    /*Подумать над тем что бы все в listView заменить на TextView, а по клику на элементе списка он
    * заполнял верхние EditText-ы*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args=getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.edit_order_fragment, container, false);
        init(rootView);
        return rootView;
    }

    private void init(View view){
        TextView orderTitle=(TextView)view.findViewById(R.id.txtEditOrderName);
        String orderName=args.getString(StringHelper.ORDER_NAME_TAG);
        orderTitle.setText(orderTitle.getText() + " " + orderName);


        tempGetItems();

    }

    private void tempGetItems(){
        editOrderItems= ListsLayerHelper.getInstance().getOrderListItems();
    }

}
