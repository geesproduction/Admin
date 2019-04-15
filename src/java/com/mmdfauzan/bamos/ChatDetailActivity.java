/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.NotificationManager
 *  android.app.ProgressDialog
 *  android.content.BroadcastReceiver
 *  android.content.ClipData
 *  android.content.ClipboardManager
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.support.v4.content.LocalBroadcastManager
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.text.ClipboardManager
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemLongClickListener
 *  android.widget.EditText
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
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.ChatDetailActivity;
import com.mmdfauzan.bamos.adapter.ChatDetailAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.ChatItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatDetailActivity
extends AppCompatActivity {
    int chatCount = 0;
    ChatDetailAdapter chatDetailAdapter;
    String chatId;
    ArrayList<ChatItem> chatItems = new ArrayList();
    int currentFirstVisibleItem;
    int currentScrollState;
    int currentTotalItemCount;
    int currentVisibleItemCount;
    DataPref dataPref;
    EditText editTextMessage;
    int firstVisibleItem;
    ImageButton imageButtonSend;
    LinearLayout layoutLoading;
    ListView listViewChat;
    boolean loadingMore = false;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    String memberId;
    String message = null;
    String nama;
    int newchat = 0;
    int page = 1;
    int pages;
    int startChat = 0;

    /*
     * Enabled aggressive block sorting
     */
    public void copyChat(String string) {
        if (Build.VERSION.SDK_INT >= 11) {
            ((ClipboardManager)this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence)"Pesan", (CharSequence)string));
        } else {
            ((android.text.ClipboardManager)this.getSystemService("clipboard")).setText((CharSequence)string);
        }
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Pesan disalin.", (int)0).show();
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.chatItems = new ArrayList();
            this.chatDetailAdapter = null;
            this.listViewChat.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)ChatDetailActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)ChatDetailActivity.this.dataPref.getToken());
                        hashMap.put((Object)"idchat", (Object)ChatDetailActivity.this.chatId);
                        hashMap.put((Object)"newchat", (Object)String.valueOf((int)ChatDetailActivity.this.newchat));
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_chat_detail", "POST", (HashMap<String, String>)hashMap);
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
                    ArrayList arrayList;
                    JSONArray jSONArray;
                    ChatDetailActivity.this.loadingMore = false;
                    ChatDetailActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)ChatDetailActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    int n = jSONObject.getInt("success");
                    String string = jSONObject.getString("message");
                    if (n == 1) {
                        ChatDetailActivity.this.pages = jSONObject.getInt("pages");
                        arrayList = new ArrayList();
                        jSONArray = jSONObject.getJSONArray("chat");
                        ChatDetailActivity.this.chatCount = jSONArray.length();
                    } else {
                        Toast.makeText((Context)ChatDetailActivity.this, (CharSequence)string, (int)1).show();
                        return;
                    }
                    for (int i = -1 + ChatDetailActivity.this.chatCount; i >= 0; --i) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        ChatItem chatItem = new ChatItem();
                        chatItem.setMessage(jSONObject2.getString("message"));
                        chatItem.setTimeSent(jSONObject2.getString("time_sent"));
                        chatItem.setSender(jSONObject2.getString("sender"));
                        chatItem.setNamaBarang(jSONObject2.getString("namabarang"));
                        chatItem.setIdBarang(jSONObject2.getString("idbarang"));
                        arrayList.add((Object)chatItem);
                    }
                    try {
                        for (int i = 0; i < ChatDetailActivity.this.chatItems.size(); ++i) {
                            ChatItem chatItem = new ChatItem();
                            chatItem.setMessage(((ChatItem)ChatDetailActivity.this.chatItems.get(i)).getMessage());
                            chatItem.setTimeSent(((ChatItem)ChatDetailActivity.this.chatItems.get(i)).getTimeSent());
                            chatItem.setSender(((ChatItem)ChatDetailActivity.this.chatItems.get(i)).getSender());
                            chatItem.setNamaBarang(((ChatItem)ChatDetailActivity.this.chatItems.get(i)).getNamaBarang());
                            chatItem.setIdBarang(((ChatItem)ChatDetailActivity.this.chatItems.get(i)).getIdBarang());
                            arrayList.add((Object)chatItem);
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                    ChatDetailActivity.this.chatItems = arrayList;
                    if (ChatDetailActivity.this.chatDetailAdapter == null) {
                        ChatDetailActivity.this.chatDetailAdapter = new ChatDetailAdapter((Activity)ChatDetailActivity.this, (ArrayList<ChatItem>)arrayList);
                        ChatDetailActivity.this.listViewChat.setAdapter((ListAdapter)ChatDetailActivity.this.chatDetailAdapter);
                        ChatDetailActivity.this.listViewChat.setSelection(-1 + ChatDetailActivity.this.chatDetailAdapter.getCount());
                    } else {
                        ChatDetailActivity.this.chatDetailAdapter.setChatDetil((ArrayList<ChatItem>)arrayList);
                        ChatDetailActivity.this.chatDetailAdapter.notifyDataSetChanged();
                        ChatDetailActivity.this.listViewChat.setSelection(ChatDetailActivity.this.chatCount);
                    }
                    ChatDetailActivity.this.page = 1 + ChatDetailActivity.this.page;
                }

                protected void onPreExecute() {
                    ChatDetailActivity.this.loadingMore = true;
                    ChatDetailActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427363);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewChat = (ListView)this.findViewById(2131296707);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.imageButtonSend = (ImageButton)this.findViewById(2131296611);
        this.editTextMessage = (EditText)this.findViewById(2131296512);
        Intent intent = this.getIntent();
        this.startChat = intent.getIntExtra("startChat", 0);
        this.memberId = intent.getStringExtra("idmember");
        this.chatId = intent.getStringExtra("idchat");
        this.nama = intent.getStringExtra("nama");
        this.getSupportActionBar().setTitle((CharSequence)this.nama);
        if (this.startChat == 0) {
            this.mRegistrationBroadcastReceiver = new BroadcastReceiver((ChatDetailActivity)this){
                final /* synthetic */ ChatDetailActivity this$0;
                {
                    this.this$0 = chatDetailActivity;
                }

                /*
                 * Enabled aggressive block sorting
                 */
                public void onReceive(Context context, Intent intent) {
                    if (intent.getStringExtra("idchat").equals((Object)this.this$0.chatId)) {
                        int n = intent.getIntExtra("idnotification", 0);
                        ((NotificationManager)this.this$0.getApplicationContext().getSystemService("notification")).cancel(n);
                        ChatItem chatItem = new ChatItem();
                        chatItem.setMessage(intent.getStringExtra("message"));
                        chatItem.setTimeSent(intent.getStringExtra("time_sent"));
                        chatItem.setSender(intent.getStringExtra("sender"));
                        chatItem.setNamaBarang(intent.getStringExtra("namabarang"));
                        chatItem.setIdBarang(intent.getStringExtra("idbarang"));
                        int n2 = this.this$0.chatItems.size();
                        this.this$0.chatItems.add(n2, (Object)chatItem);
                        this.this$0.newchat = 1 + this.this$0.newchat;
                        if (this.this$0.chatDetailAdapter == null) {
                            this.this$0.chatDetailAdapter = new ChatDetailAdapter((Activity)this.this$0, this.this$0.chatItems);
                            this.this$0.listViewChat.setAdapter((ListAdapter)this.this$0.chatDetailAdapter);
                        } else {
                            this.this$0.chatDetailAdapter.setChatDetil(this.this$0.chatItems);
                            this.this$0.chatDetailAdapter.notifyDataSetChanged();
                        }
                        this.this$0.listViewChat.setSelection(-1 + this.this$0.chatDetailAdapter.getCount());
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)ChatDetailActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)ChatDetailActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"idchat", (Object)ChatDetailActivity.this.chatId);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/set_read_chat", "POST", (HashMap<String, String>)hashMap);
                                    return jSONObject;
                                }
                                catch (Exception exception) {
                                    exception.printStackTrace();
                                    return null;
                                }
                            }

                            /*
                             * Enabled force condition propagation
                             * Lifted jumps to return sites
                             */
                            protected void onPostExecute(JSONObject jSONObject) {
                                if (jSONObject == null) return;
                                try {
                                    jSONObject.getInt("success");
                                    jSONObject.getString("message");
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }

                            protected void onPreExecute() {
                            }
                        }.execute((Object[])new String[0]);
                        this.this$0.setResult(-1);
                    }
                }
            };
            AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        } else {
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();
                ProgressDialog progressDialog = new ProgressDialog((Context)ChatDetailActivity.this);

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)ChatDetailActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)ChatDetailActivity.this.dataPref.getToken());
                        hashMap.put((Object)"idmember", (Object)ChatDetailActivity.this.memberId);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_chat_id", "POST", (HashMap<String, String>)hashMap);
                        Log.d((String)"JSON result", (String)jSONObject.toString());
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
                    if (this.progressDialog.isShowing()) {
                        this.progressDialog.dismiss();
                    }
                    if (jSONObject == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            ChatDetailActivity.this.chatId = jSONObject.getString("idchat");
                            ChatDetailActivity.this.mRegistrationBroadcastReceiver = new BroadcastReceiver(){

                                /*
                                 * Enabled aggressive block sorting
                                 */
                                public void onReceive(Context context, Intent intent) {
                                    if (intent.getStringExtra("idchat").equals((Object)ChatDetailActivity.this.chatId)) {
                                        int n = intent.getIntExtra("idnotification", 0);
                                        ((NotificationManager)ChatDetailActivity.this.getApplicationContext().getSystemService("notification")).cancel(n);
                                        ChatItem chatItem = new ChatItem();
                                        chatItem.setMessage(intent.getStringExtra("message"));
                                        chatItem.setTimeSent(intent.getStringExtra("time_sent"));
                                        chatItem.setSender(intent.getStringExtra("sender"));
                                        chatItem.setNamaBarang(intent.getStringExtra("namabarang"));
                                        chatItem.setIdBarang(intent.getStringExtra("idbarang"));
                                        int n2 = ChatDetailActivity.this.chatItems.size();
                                        ChatDetailActivity.this.chatItems.add(n2, (Object)chatItem);
                                        ChatDetailActivity.this.newchat = 1 + ChatDetailActivity.this.newchat;
                                        if (ChatDetailActivity.this.chatDetailAdapter == null) {
                                            ChatDetailActivity.this.chatDetailAdapter = new ChatDetailAdapter((Activity)ChatDetailActivity.this, ChatDetailActivity.this.chatItems);
                                            ChatDetailActivity.this.listViewChat.setAdapter((ListAdapter)ChatDetailActivity.this.chatDetailAdapter);
                                        } else {
                                            ChatDetailActivity.this.chatDetailAdapter.setChatDetil(ChatDetailActivity.this.chatItems);
                                            ChatDetailActivity.this.chatDetailAdapter.notifyDataSetChanged();
                                        }
                                        ChatDetailActivity.this.listViewChat.setSelection(-1 + ChatDetailActivity.this.chatDetailAdapter.getCount());
                                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                        ChatDetailActivity.this.setResult(-1);
                                    }
                                }
                            };
                            LocalBroadcastManager.getInstance((Context)ChatDetailActivity.this).registerReceiver(ChatDetailActivity.this.mRegistrationBroadcastReceiver, new IntentFilter("chat"));
                            AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                            Object[] arrobject = new String[]{String.valueOf((int)ChatDetailActivity.this.page)};
                            asyncTask.execute(arrobject);
                            return;
                        }
                        Toast.makeText((Context)ChatDetailActivity.this, (CharSequence)string, (int)1).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                    this.progressDialog.setCancelable(false);
                    this.progressDialog.show();
                }

            }.execute((Object[])new String[0]);
        }
        this.listViewChat.setOnScrollListener(new AbsListView.OnScrollListener((ChatDetailActivity)this){
            final /* synthetic */ ChatDetailActivity this$0;
            {
                this.this$0 = chatDetailActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                this.this$0.currentFirstVisibleItem = n;
                this.this$0.currentVisibleItemCount = n2;
                this.this$0.currentTotalItemCount = n3;
                if (n + n2 != n3 || this.this$0.loadingMore || this.this$0.page <= this.this$0.pages) {
                    // empty if block
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
                this.this$0.currentScrollState = n;
                if (this.this$0.currentVisibleItemCount > 0 && this.this$0.currentScrollState == 0 && this.this$0.currentFirstVisibleItem == 0 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }
        });
        this.listViewChat.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener((ChatDetailActivity)this){
            final /* synthetic */ ChatDetailActivity this$0;
            {
                this.this$0 = chatDetailActivity;
            }

            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.copyChat(((ChatItem)this.this$0.chatItems.get(n)).getMessage());
                return true;
            }
        });
        this.imageButtonSend.setOnClickListener(new View.OnClickListener((ChatDetailActivity)this){
            final /* synthetic */ ChatDetailActivity this$0;
            {
                this.this$0 = chatDetailActivity;
            }

            public void onClick(View view) {
                this.this$0.message = this.this$0.editTextMessage.getText().toString();
                if (!(this.this$0.message.equals(null) || this.this$0.message.equals((Object)"") || this.this$0.message.equals((Object)" "))) {
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)ChatDetailActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)ChatDetailActivity.this.dataPref.getToken());
                                hashMap.put((Object)"idchat", (Object)ChatDetailActivity.this.chatId);
                                hashMap.put((Object)"message", (Object)ChatDetailActivity.this.message);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/send_chat", "POST", (HashMap<String, String>)hashMap);
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
                            ChatDetailActivity.this.editTextMessage.setEnabled(true);
                            ChatDetailActivity.this.imageButtonSend.setEnabled(true);
                            if (jSONObject == null) return;
                            {
                                try {
                                    int n = jSONObject.getInt("success");
                                    String string = jSONObject.getString("message");
                                    if (n == 1) {
                                        ChatDetailActivity.this.setResult(-1);
                                        ChatDetailActivity.this.editTextMessage.setText((CharSequence)"");
                                        JSONObject jSONObject2 = jSONObject.getJSONObject("chat");
                                        ChatItem chatItem = new ChatItem();
                                        chatItem.setMessage(jSONObject2.getString("message"));
                                        chatItem.setTimeSent(jSONObject2.getString("time_sent"));
                                        chatItem.setSender(jSONObject2.getString("sender"));
                                        chatItem.setNamaBarang(jSONObject2.getString("namabarang"));
                                        chatItem.setIdBarang(jSONObject2.getString("idbarang"));
                                        int n2 = ChatDetailActivity.this.chatItems.size();
                                        ChatDetailActivity.this.chatItems.add(n2, (Object)chatItem);
                                        ChatDetailActivity.this.newchat = 1 + ChatDetailActivity.this.newchat;
                                        if (ChatDetailActivity.this.chatDetailAdapter == null) {
                                            ChatDetailActivity.this.chatDetailAdapter = new ChatDetailAdapter((Activity)ChatDetailActivity.this, ChatDetailActivity.this.chatItems);
                                            ChatDetailActivity.this.listViewChat.setAdapter((ListAdapter)ChatDetailActivity.this.chatDetailAdapter);
                                        } else {
                                            ChatDetailActivity.this.chatDetailAdapter.setChatDetil(ChatDetailActivity.this.chatItems);
                                            ChatDetailActivity.this.chatDetailAdapter.notifyDataSetChanged();
                                        }
                                        ChatDetailActivity.this.listViewChat.setSelection(-1 + ChatDetailActivity.this.chatDetailAdapter.getCount());
                                        return;
                                    }
                                    Toast.makeText((Context)ChatDetailActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }
                        }

                        protected void onPreExecute() {
                            ChatDetailActivity.this.editTextMessage.setEnabled(false);
                            ChatDetailActivity.this.imageButtonSend.setEnabled(false);
                        }
                    }.execute((Object[])new String[0]);
                }
            }
        });
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance((Context)this).unregisterReceiver(this.mRegistrationBroadcastReceiver);
        super.onDestroy();
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

    protected void onResume() {
        if (this.startChat == 0) {
            LocalBroadcastManager.getInstance((Context)this).registerReceiver(this.mRegistrationBroadcastReceiver, new IntentFilter("chat"));
        }
        super.onResume();
    }

}

