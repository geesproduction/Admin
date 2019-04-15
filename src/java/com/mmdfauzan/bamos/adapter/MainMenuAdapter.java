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
import com.mmdfauzan.bamos.model.MainMenuItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;

public class MainMenuAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<MainMenuItem> data_menu = new ArrayList();

    public MainMenuAdapter(Activity activity, ArrayList<MainMenuItem> arrayList) {
        this.activity = activity;
        this.data_menu = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_menu.size();
    }

    public Object getItem(int n) {
        return this.data_menu.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427440, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297103);
        TextView textView2 = (TextView)view2.findViewById(2131297086);
        ImageView imageView = (ImageView)view2.findViewById(2131296628);
        MainMenuItem mainMenuItem = (MainMenuItem)this.data_menu.get(n);
        textView.setText((CharSequence)mainMenuItem.getNama_menu());
        textView2.setText((CharSequence)mainMenuItem.getLink_menu());
        Picasso.with((Context)this.activity).load(mainMenuItem.getIcon_menu()).placeholder(2131230939).resize(80, 80).centerInside().into(imageView);
        return view2;
    }

    public void setMainMenu(ArrayList<MainMenuItem> arrayList) {
        this.data_menu = arrayList;
    }
}

