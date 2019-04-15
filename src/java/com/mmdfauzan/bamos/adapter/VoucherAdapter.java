/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
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
import android.widget.TextView;
import com.mmdfauzan.bamos.model.VoucherItem;
import java.util.ArrayList;

public class VoucherAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<VoucherItem> data_voucher = new ArrayList();

    public VoucherAdapter(Activity activity, ArrayList<VoucherItem> arrayList) {
        this.activity = activity;
        this.data_voucher = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_voucher.size();
    }

    public Object getItem(int n) {
        return this.data_voucher.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427431, null);
        }
        TextView textView = (TextView)view2.findViewById(2131296849);
        TextView textView2 = (TextView)view2.findViewById(2131296953);
        textView.setText((CharSequence)((VoucherItem)this.data_voucher.get(n)).getKodeVoucher());
        textView2.setVisibility(8);
        return view2;
    }

    public void setVoucher(ArrayList<VoucherItem> arrayList) {
        this.data_voucher = arrayList;
    }
}

