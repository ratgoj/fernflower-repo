package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernflower.orderbook.R;
import com.fernflower.orderbook.helpers.StringHelper;

/**
 * Created by SONY on 08.09.2015.
 */
public class EditOrderFragment extends Fragment {

    private Bundle args;

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
        orderTitle.setText(orderTitle.getText()+" "+orderName);
    }

}
