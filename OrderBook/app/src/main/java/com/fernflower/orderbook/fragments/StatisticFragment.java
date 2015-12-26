package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fernflower.orderbook.R;

/**
 * Created by SONY on 15.07.2015.
 */
public class StatisticFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistic_fragment, container, false);
        return rootView;
    }
}
