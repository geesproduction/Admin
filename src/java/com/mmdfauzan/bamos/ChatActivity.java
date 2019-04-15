/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.ChatActivity;
import com.mmdfauzan.bamos.adapter.ChatListAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.ChatItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity
extends AppCompatActivity {
    ArrayList<ChatItem> chatItems = new ArrayList();
    ChatListAdapter chatListAdapter;
    DataPref dataPref;
    ImageButton imageButtonAdd;
    LinearLayout layoutLoading;
    ListView listViewChat;
    boolean loadingMore = false;
    int page = 1;
    int pages;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.chatItems = new ArrayList();
            this.chatListAdapter = null;
            this.listViewChat.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)ChatActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)ChatActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_chat_list", "POST", (HashMap<String, String>)hashMap);
                        return jSONObject;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        return null;
                    }
                }

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                protected void onPostExecute(JSONObject jSONObject) {
                    ChatActivity.this.loadingMore = false;
                    ChatActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)ChatActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            ChatActivity.this.pages = jSONObject.getInt("pages");
                            new ChatItem();
                            jSONArray = jSONObject.getJSONArray("chat");
                        } else {
                            Toast.makeText((Context)ChatActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            ChatItem chatItem = new ChatItem();
                            chatItem.setIdChat(jSONObject2.getString("idchat"));
                            chatItem.setNama(jSONObject2.getString("nama"));
                            chatItem.setMessage(jSONObject2.getString("message"));
                            chatItem.setTimeRead(jSONObject2.getString("time_read"));
                            chatItem.setSender(jSONObject2.getString("sender"));
                            chatItem.setNamaBarang(jSONObject2.getString("namabarang"));
                            chatItem.setIdBarang(jSONObject2.getString("idbarang"));
                            ChatActivity.this.chatItems.add((Object)chatItem);
                        }
                        if (ChatActivity.this.chatListAdapter == null) {
                            ChatActivity.this.chatListAdapter = new ChatListAdapter((Activity)ChatActivity.this, ChatActivity.this.chatItems);
                            ChatActivity.this.listViewChat.setAdapter((ListAdapter)ChatActivity.this.chatListAdapter);
                        } else {
                            ChatActivity.this.chatListAdapter.setChatList(ChatActivity.this.chatItems);
                            ChatActivity.this.chatListAdapter.notifyDataSetChanged();
                        }
                        ChatActivity.this.page = 1 + ChatActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    ChatActivity.this.loadingMore = true;
                    ChatActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427362);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Pesan Masuk");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewChat = (ListView)this.findViewById(2131296706);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.imageButtonAdd = (ImageButton)this.findViewById(2131296584);
        this.imageButtonAdd.setOnClickListener(new View.OnClickListener((ChatActivity)this){
            final /* synthetic */ ChatActivity this$0;
            {
                this.this$0 = chatActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatMemberListActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener((ChatActivity)this){
            final /* synthetic */ ChatActivity this$0;
            {
                this.this$0 = chatActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatDetailActivity.class);
                intent.putExtra("idchat", ((ChatItem)this.this$0.chatItems.get(n)).getIdChat());
                intent.putExtra("nama", ((ChatItem)this.this$0.chatItems.get(n)).getNama());
                this.this$0.startActivityForResult(intent, 2);
                ((ChatItem)this.this$0.chatItems.get(n)).setTimeRead("v");
                this.this$0.chatListAdapter.notifyDataSetChanged();
            }
        });
        this.listViewChat.setOnScrollListener(new AbsListView.OnScrollListener((ChatActivity)this){
            final /* synthetic */ ChatActivity this$0;
            {
                this.this$0 = chatActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
        Object[] arrobject = new String[]{String.valueOf((int)this.page)};
        asyncTask.execute(arrobject);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return true;
                    break;
                } while (true);
            }
            case 16908332: 
        }
        this.finish();
        return true;
    }

}

