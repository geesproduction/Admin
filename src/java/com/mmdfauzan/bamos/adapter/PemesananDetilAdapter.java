/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.mmdfauzan.bamos.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mmdfauzan.bamos.model.PemesananItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class PemesananDetilAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<PemesananItem> data_pemesanan = new ArrayList();

    public PemesananDetilAdapter(Activity activity, ArrayList<PemesananItem> arrayList) {
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

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427445, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297105);
        TextView textView2 = (TextView)view2.findViewById(2131297059);
        TextView textView3 = (TextView)view2.findViewById(2131297061);
        TextView textView4 = (TextView)view2.findViewById(2131297058);
        ImageView imageView = (ImageView)view2.findViewById(2131296628);
        PemesananItem pemesananItem = (PemesananItem)this.data_pemesanan.get(n);
        if (pemesananItem.getGrosir().equals((Object)"1")) {
            textView4.setVisibility(0);
        } else {
            textView4.setVisibility(8);
        }
        textView.setText((CharSequence)pemesananItem.getNamaProduk());
        textView2.setText((CharSequence)pemesananItem.getJumlahProduk());
        textView3.setText((CharSequence)("Rp " + pemesananItem.getHargaProduk()));
        Picasso.with((Context)this.activity).load("http://os.bikinaplikasi.com/gambar/" + pemesananItem.getGambarProduk()).placeholder(2131230939).resize(200, 200).centerCrop().into(imageView);
        return view2;
    }

    public void setPemesanan(ArrayList<PemesananItem> arrayList) {
        this.data_pemesanan = arrayList;
    }
}

