package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernflower.orderbook.R;

/**
 * Created by SONY on 06.07.2015.
 */
public class ComplitedOrdersFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complited_orders_fragment, container, false);
        return rootView;
    }
}
