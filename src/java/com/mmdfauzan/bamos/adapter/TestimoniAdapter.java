/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
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
import android.widget.ImageView;
import android.widget.TextView;
import com.mmdfauzan.bamos.model.PemesananItem;
import java.util.ArrayList;

public class TestimoniAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<PemesananItem> data_pemesanan = new ArrayList();

    public TestimoniAdapter(Activity activity, ArrayList<PemesananItem> arrayList) {
        this.activity = activity;
        this.data_pemesanan = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_pemesanan.size();
    }

    public Object getItem(int n) {
        return this.data_pemesanan.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427441, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297098);
        TextView textView2 = (TextView)view2.findViewById(2131297135);
        TextView textView3 = (TextView)view2.findViewById(2131297126);
        ImageView imageView = (ImageView)view2.findViewById(2131296630);
        PemesananItem pemesananItem = (PemesananItem)this.data_pemesanan.get(n);
        textView.setText((CharSequence)pemesananItem.getNamaPesanan());
        textView2.setText((CharSequence)pemesananItem.getReviewWaktu());
        textView3.setText((CharSequence)pemesananItem.getReview());
        String string2 = pemesananItem.getRating();
        if (string2.equals((Object)"1")) {
            imageView.setImageResource(2131230935);
            return view2;
        }
        if (string2.equals((Object)"2")) {
            imageView.setImageResource(2131230851);
            return view2;
        }
        imageView.setImageResource(2131230851);
        return view2;
    }

    public void setPemesanan(ArrayList<PemesananItem> arrayList) {
        this.data_pemesanan = arrayList;
    }
}

