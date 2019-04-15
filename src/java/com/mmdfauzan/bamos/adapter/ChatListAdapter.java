/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.graphics.Typeface
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
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.model.ChatItem;
import java.util.ArrayList;

public class ChatListAdapter
extends BaseAdapter {
    private static LayoutInflater inflater = null;
    private Activity activity;
    private ArrayList<ChatItem> data_chat = new ArrayList();

    public ChatListAdapter(Activity activity, ArrayList<ChatItem> arrayList) {
        this.activity = activity;
        this.data_chat = arrayList;
        inflater = (LayoutInflater)this.activity.getSystemService("layout_inflater");
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
        View view2 = view;
        if (view == null) {
            view2 = inflater.inflate(2131427436, null);
        }
        TextView textView = (TextView)view2.findViewById(2131297098);
        TextView textView2 = (TextView)view2.findViewById(2131297083);
        LinearLayout linearLayout = (LinearLayout)view2.findViewById(2131296683);
        ChatItem chatItem = (ChatItem)this.data_chat.get(n);
        textView.setText((CharSequence)chatItem.getNama());
        textView2.setText((CharSequence)chatItem.getMessage());
        if (chatItem.getMessage().equals((Object)"null")) {
            textView2.setText((CharSequence)"");
        }
        if (chatItem.getSender().equals((Object)"1")) {
            if (chatItem.getTimeRead().equals(null) || chatItem.getTimeRead().equals((Object)"null")) {
                linearLayout.setVisibility(0);
                textView.setTypeface(Typeface.DEFAULT_BOLD);
                textView2.setTypeface(Typeface.DEFAULT_BOLD);
                return view2;
            } else {
                if (chatItem.getTimeRead().equals(null) || chatItem.getTimeRead().equals((Object)"null")) return view2;
                {
                    linearLayout.setVisibility(4);
                    textView.setTypeface(Typeface.DEFAULT);
                    textView2.setTypeface(Typeface.DEFAULT);
                    return view2;
                }
            }
        } else {
            if (!chatItem.getSender().equals((Object)"0")) return view2;
            {
                linearLayout.setVisibility(4);
                textView.setTypeface(Typeface.DEFAULT);
                textView2.setTypeface(Typeface.DEFAULT);
                return view2;
            }
        }
    }

    public void setChatList(ArrayList<ChatItem> arrayList) {
        this.data_chat = arrayList;
    }
}

