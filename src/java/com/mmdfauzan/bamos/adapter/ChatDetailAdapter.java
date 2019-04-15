/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.support.v4.content.ContextCompat
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  com.mmdfauzan.bamos.ViewProdukActivity
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package com.mmdfauzan.bamos.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.ViewProdukActivity;
import com.mmdfauzan.bamos.model.ChatItem;
import java.util.ArrayList;

public class ChatDetailAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<ChatItem> data_chat = new ArrayList();
    private View vi;

    public ChatDetailAdapter(Activity activity, ArrayList<ChatItem> arrayList) {
        this.activity = activity;
        this.data_chat = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
    }

    public static final int getColor(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return ContextCompat.getColor((Context)context, (int)n);
        }
        return context.getResources().getColor(n);
    }

    public int getCount() {
        return this.data_chat.size();
    }

    public Object getItem(int n) {
        return this.data_chat.get(n);
    }

    public long getItemId(int n) {
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup viewGroup) {
        this.vi = view;
        if (view == null) {
            this.vi = inflater.inflate(2131427428, null);
        }
        TextView textView = (TextView)this.vi.findViewById(2131297096);
        TextView textView2 = (TextView)this.vi.findViewById(2131297132);
        TextView textView3 = (TextView)this.vi.findViewById(2131297099);
        LinearLayout linearLayout = (LinearLayout)this.vi.findViewById(2131296642);
        LinearLayout linearLayout2 = (LinearLayout)this.vi.findViewById(2131296643);
        LinearLayout linearLayout3 = (LinearLayout)this.vi.findViewById(2131296670);
        ChatItem chatItem = (ChatItem)this.data_chat.get(n);
        final String string2 = chatItem.getIdBarang();
        String string3 = chatItem.getNamaBarang();
        if (!(string2.equals((Object)"") || string2.equals((Object)"null") || string2.equals((Object)"0"))) {
            linearLayout3.setVisibility(0);
            textView3.setText((CharSequence)string3);
            textView3.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    Intent intent = new Intent(ChatDetailAdapter.this.vi.getContext(), ViewProdukActivity.class);
                    intent.putExtra("idbarang", string2);
                    ChatDetailAdapter.this.vi.getContext().startActivity(intent);
                }
            });
        } else {
            linearLayout3.setVisibility(8);
        }
        textView.setText((CharSequence)chatItem.getMessage());
        textView2.setText((CharSequence)chatItem.getTimeSent());
        if (chatItem.getSender().equals((Object)"0")) {
            linearLayout.setBackgroundResource(2131230820);
            linearLayout2.setGravity(5);
            textView.setTextColor(ChatDetailAdapter.getColor(this.vi.getContext(), 2131099708));
            textView2.setTextColor(ChatDetailAdapter.getColor(this.vi.getContext(), 2131099713));
            return this.vi;
        }
        if (!chatItem.getSender().equals((Object)"1")) return this.vi;
        linearLayout.setBackgroundResource(2131230821);
        linearLayout2.setGravity(3);
        textView.setTextColor(ChatDetailAdapter.getColor(this.vi.getContext(), 2131099709));
        textView2.setTextColor(ChatDetailAdapter.getColor(this.vi.getContext(), 2131099714));
        return this.vi;
    }

    public void setChatDetil(ArrayList<ChatItem> arrayList) {
        this.data_chat = arrayList;
    }

}

