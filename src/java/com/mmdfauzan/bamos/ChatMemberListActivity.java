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
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.ChatMemberListActivity;
import com.mmdfauzan.bamos.adapter.MemberAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.MemberItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatMemberListActivity
extends AppCompatActivity {
    DataPref dataPref;
    LinearLayout layoutLoading;
    ListView listViewMember;
    boolean loadingMore = false;
    MemberAdapter memberAdapter;
    ArrayList<MemberItem> memberItems = new ArrayList();
    int page = 1;
    int pages;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.setResult(-1);
            this.finish();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427364);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Chat ke Member");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewMember = (ListView)this.findViewById(2131296711);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.listViewMember.setOnScrollListener(new AbsListView.OnScrollListener((ChatMemberListActivity)this){
            final /* synthetic */ ChatMemberListActivity this$0;
            {
                this.this$0 = chatMemberListActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)ChatMemberListActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)ChatMemberListActivity.this.dataPref.getToken());
                                hashMap.put((Object)"p", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_members_active", "POST", (HashMap<String, String>)hashMap);
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
                            ChatMemberListActivity.this.loadingMore = false;
                            ChatMemberListActivity.this.layoutLoading.setVisibility(8);
                            if (jSONObject == null) {
                                AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                                Object[] arrobject = new String[]{String.valueOf((int)ChatMemberListActivity.this.page)};
                                asyncTask.execute(arrobject);
                                return;
                            }
                            try {
                                JSONArray jSONArray;
                                int n = jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                if (n == 1) {
                                    ChatMemberListActivity.this.pages = jSONObject.getInt("pages");
                                    jSONArray = jSONObject.getJSONArray("member");
                                } else {
                                    Toast.makeText((Context)ChatMemberListActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                for (int i = 0; i < jSONArray.length(); ++i) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    MemberItem memberItem = new MemberItem();
                                    memberItem.setNamaMember(jSONObject2.getString("nama"));
                                    memberItem.setEmailMember(jSONObject2.getString("email"));
                                    memberItem.setTeleponMember(jSONObject2.getString("telepon"));
                                    memberItem.setAlamatMember(jSONObject2.getString("alamat"));
                                    memberItem.setStatusMember(jSONObject2.getString("status"));
                                    memberItem.setIdMember(jSONObject2.getString("idmember"));
                                    memberItem.setLastLoginMember(jSONObject2.getString("lastactivity"));
                                    memberItem.setProfilePictureMember(jSONObject2.getString("profile_picture"));
                                    memberItem.setSaldoMember(jSONObject2.getString("saldo"));
                                    memberItem.setRegisterTimeMember(jSONObject2.getString("register_time"));
                                    ChatMemberListActivity.this.memberItems.add((Object)memberItem);
                                }
                                if (ChatMemberListActivity.this.memberAdapter == null) {
                                    ChatMemberListActivity.this.memberAdapter = new MemberAdapter((Activity)ChatMemberListActivity.this, ChatMemberListActivity.this.memberItems);
                                    ChatMemberListActivity.this.listViewMember.setAdapter((ListAdapter)ChatMemberListActivity.this.memberAdapter);
                                } else {
                                    ChatMemberListActivity.this.memberAdapter.setMember(ChatMemberListActivity.this.memberItems);
                                    ChatMemberListActivity.this.memberAdapter.notifyDataSetChanged();
                                }
                                ChatMemberListActivity.this.page = 1 + ChatMemberListActivity.this.page;
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }

                        protected void onPreExecute() {
                            ChatMemberListActivity.this.loadingMore = true;
                            ChatMemberListActivity.this.layoutLoading.setVisibility(0);
                        }
                    };
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.listViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener((ChatMemberListActivity)this){
            final /* synthetic */ ChatMemberListActivity this$0;
            {
                this.this$0 = chatMemberListActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatDetailActivity.class);
                intent.putExtra("startChat", 1);
                intent.putExtra("idmember", ((MemberItem)this.this$0.memberItems.get(n)).getIdMember());
                intent.putExtra("nama", ((MemberItem)this.this$0.memberItems.get(n)).getNamaMember());
                this.this$0.startActivityForResult(intent, 2);
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

