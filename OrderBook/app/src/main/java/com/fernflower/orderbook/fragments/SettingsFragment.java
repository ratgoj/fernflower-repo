package com.fernflower.orderbook.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fernflower.orderbook.R;

/**
 * Created by SONY on 15.07.2015.
 */
public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        TextView myLabel=(TextView)rootView.findViewById(R.id.txtSettings);
        myLabel.setText("Settings is cool");
        return rootView;
    }
}
