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
 *  java.lang.Integer
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
import com.mmdfauzan.bamos.model.ProdukItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class ProdukAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<ProdukItem> data_produk = new ArrayList();

    public ProdukAdapter(Activity activity, ArrayList<ProdukItem> arrayList) {
        this.activity = activity;
        this.data_produk = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_produk.size();
    }

    public Object getItem(int n) {
        return this.data_produk.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427447, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297105);
        TextView textView2 = (TextView)view2.findViewById(2131297129);
        ImageView imageView = (ImageView)view2.findViewById(2131296628);
        ProdukItem produkItem = (ProdukItem)this.data_produk.get(n);
        textView.setText((CharSequence)produkItem.getNamaProduk());
        int n2 = Integer.parseInt((String)produkItem.getStokVarian1()) + Integer.parseInt((String)produkItem.getStokVarian2()) + Integer.parseInt((String)produkItem.getStokVarian3()) + Integer.parseInt((String)produkItem.getStokVarian4()) + Integer.parseInt((String)produkItem.getStokVarian5()) + Integer.parseInt((String)produkItem.getStokVarian6()) + Integer.parseInt((String)produkItem.getStokVarian7()) + Integer.parseInt((String)produkItem.getStokVarian8()) + Integer.parseInt((String)produkItem.getStokVarian9()) + Integer.parseInt((String)produkItem.getStokVarian10());
        textView2.setText((CharSequence)("Stok tersedia : " + String.valueOf((int)n2) + " item"));
        Picasso.with((Context)this.activity).load("http://os.bikinaplikasi.com/gambar/" + produkItem.getGambar()).placeholder(2131230939).resize(200, 200).centerCrop().into(imageView);
        return view2;
    }

    public void setProduk(ArrayList<ProdukItem> arrayList) {
        this.data_produk = arrayList;
    }
}

