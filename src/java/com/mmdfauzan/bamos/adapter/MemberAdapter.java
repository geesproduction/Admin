/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.graphics.Color
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.ImageView
 *  android.widget.LinearLayout
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
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.Rupiah;
import com.mmdfauzan.bamos.model.MemberItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;

public class MemberAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<MemberItem> data_member = new ArrayList();

    public MemberAdapter(Activity activity, ArrayList<MemberItem> arrayList) {
        this.activity = activity;
        this.data_member = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.data_member.size();
    }

    public Object getItem(int n) {
        return this.data_member.get(n);
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
            view2 = inflater.inflate(2131427439, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297098);
        TextView textView2 = (TextView)view2.findViewById(2131297056);
        TextView textView3 = (TextView)view2.findViewById(2131297127);
        LinearLayout linearLayout = (LinearLayout)view2.findViewById(2131296683);
        CircleImageView circleImageView = (CircleImageView)view2.findViewById(2131296630);
        MemberItem memberItem = (MemberItem)this.data_member.get(n);
        textView.setText((CharSequence)memberItem.getNamaMember());
        textView2.setText((CharSequence)memberItem.getEmailMember());
        textView3.setText((CharSequence)new Rupiah().toRupiah(memberItem.getSaldoMember()));
        DataPref dataPref = new DataPref((Context)this.activity.getApplication());
        if (dataPref.getSaldo().equals((Object)"1")) {
            textView3.setVisibility(0);
        } else if (dataPref.getSaldo().equals((Object)"0")) {
            textView3.setVisibility(8);
        }
        Picasso.with((Context)this.activity.getApplicationContext()).load(memberItem.getProfilePictureMember()).placeholder(2131230844).resize(100, 100).centerInside().noFade().into((ImageView)circleImageView);
        if (memberItem.getStatusMember().equals((Object)"0")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#f44b42"));
            return view2;
        } else {
            if (!memberItem.getStatusMember().equals((Object)"1")) return view2;
            {
                linearLayout.setBackgroundColor(Color.parseColor((String)"#42f474"));
                return view2;
            }
        }
    }

    public void setMember(ArrayList<MemberItem> arrayList) {
        this.data_member = arrayList;
    }
}

