/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.mmdfauzan.bamos.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.model.DriverItem;
import java.util.ArrayList;

public class DriverAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<DriverItem> data_driver = new ArrayList();
    ArrayList<DriverItem> driverItems;

    public DriverAdapter(Activity activity, ArrayList<DriverItem> arrayList) {
        this.activity = activity;
        this.data_driver = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_driver.size();
    }

    public Object getItem(int n) {
        return this.data_driver.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427437, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297098);
        TextView textView2 = (TextView)view2.findViewById(2131297074);
        (LinearLayout)view2.findViewById(2131296683);
        DriverItem driverItem = (DriverItem)this.data_driver.get(n);
        textView.setText((CharSequence)driverItem.getNama());
        textView2.setText((CharSequence)(driverItem.getKode() + driverItem.getId_driver()));
        return view2;
    }

    public void setDriver(ArrayList<DriverItem> arrayList) {
        this.driverItems = arrayList;
    }
}

