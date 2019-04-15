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
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
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
import com.mmdfauzan.bamos.model.KategoriItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class GambarKategoriAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<KategoriItem> data_kategori = new ArrayList();

    public GambarKategoriAdapter(Activity activity, ArrayList<KategoriItem> arrayList) {
        this.activity = activity;
        this.data_kategori = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_kategori.size();
    }

    public Object getItem(int n) {
        return this.data_kategori.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427438, null);
        }
        ImageView imageView = (ImageView)view2.findViewById(2131296628);
        KategoriItem kategoriItem = (KategoriItem)this.data_kategori.get(n);
        Picasso.with((Context)this.activity).load("http://os.bikinaplikasi.com/gambar/" + kategoriItem.getGambar()).placeholder(2131230939).resize(500, 500).centerInside().into(imageView);
        return view2;
    }

    public void setKategori(ArrayList<KategoriItem> arrayList) {
        this.data_kategori = arrayList;
    }
}

