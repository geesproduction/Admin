/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Color
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
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.model.PemesananItem;
import java.util.ArrayList;

public class PemesananAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<PemesananItem> data_pemesanan = new ArrayList();

    public PemesananAdapter(Activity activity, ArrayList<PemesananItem> arrayList) {
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
            view2 = inflater.inflate(2131427434, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297076);
        TextView textView2 = (TextView)view2.findViewById(2131297104);
        TextView textView3 = (TextView)view2.findViewById(2131297136);
        TextView textView4 = (TextView)view2.findViewById(2131297051);
        TextView textView5 = (TextView)view2.findViewById(2131297080);
        LinearLayout linearLayout = (LinearLayout)view2.findViewById(2131296683);
        LinearLayout linearLayout2 = (LinearLayout)view2.findViewById(2131296669);
        PemesananItem pemesananItem = (PemesananItem)this.data_pemesanan.get(n);
        textView.setText((CharSequence)pemesananItem.getKodePesanan());
        textView2.setText((CharSequence)pemesananItem.getNamaPesanan());
        textView3.setText((CharSequence)pemesananItem.getWaktuPesanan());
        textView4.setText((CharSequence)pemesananItem.getAlamatPesanan());
        if (pemesananItem.getStatusPesanan().equals((Object)"0")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#f44b42"));
        } else if (pemesananItem.getStatusPesanan().equals((Object)"1")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#5942f4"));
        } else if (pemesananItem.getStatusPesanan().equals((Object)"2")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#42f474"));
        } else if (pemesananItem.getStatusPesanan().equals((Object)"3")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#d142f4"));
        } else if (pemesananItem.getStatusPesanan().equals((Object)"4")) {
            linearLayout.setBackgroundColor(Color.parseColor((String)"#f7c14c"));
        }
        if (!pemesananItem.getBuktiPembayaran().equals((Object)"1")) {
            linearLayout2.setVisibility(8);
            return view2;
        }
        linearLayout2.setVisibility(0);
        if (pemesananItem.getSaldo().equals((Object)"1")) {
            textView5.setText((CharSequence)"Telah dibayar dengan saldo");
            linearLayout2.setBackgroundColor(Color.parseColor((String)"#1d6ff4"));
            return view2;
        } else {
            if (!pemesananItem.getSaldo().equals((Object)"0")) return view2;
            {
                textView5.setText((CharSequence)"Konfirmasi pembayaran");
                linearLayout2.setBackgroundColor(Color.parseColor((String)"#0dc435"));
                return view2;
            }
        }
    }

    public void setPemesanan(ArrayList<PemesananItem> arrayList) {
        this.data_pemesanan = arrayList;
    }
}

