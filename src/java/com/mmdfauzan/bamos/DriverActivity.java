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
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.DriverActivity;
import com.mmdfauzan.bamos.adapter.DriverAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.DriverItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DriverActivity
extends AppCompatActivity {
    ImageButton buttonAdd;
    DataPref dataPref;
    DriverAdapter driverAdapter;
    ArrayList<DriverItem> driverItems = new ArrayList();
    LinearLayout layoutLoading;
    ListView listViewDriver;
    boolean loadingMore = false;
    int page = 1;
    int pages;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.driverItems = new ArrayList();
            this.driverAdapter = null;
            this.listViewDriver.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)DriverActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)DriverActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_all_driver", "POST", (HashMap<String, String>)hashMap);
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
                    DriverActivity.this.loadingMore = false;
                    DriverActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)DriverActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                            DriverActivity.this.pages = jSONObject2.getInt("pages");
                            jSONArray = jSONObject.getJSONArray("driver");
                        } else {
                            Toast.makeText((Context)DriverActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                            DriverItem driverItem = new DriverItem();
                            driverItem.setId_driver(jSONObject3.getString("iddriver"));
                            driverItem.setNama(jSONObject3.getString("nama"));
                            driverItem.setTelepon(jSONObject3.getString("telepon"));
                            driverItem.setKode(jSONObject3.getString("kodedriver"));
                            driverItem.setOnline(jSONObject3.getString("online"));
                            driverItem.setStatus(jSONObject3.getString("status"));
                            driverItem.setTerakhir_login(jSONObject3.getString("terakhir_login"));
                            DriverActivity.this.driverItems.add((Object)driverItem);
                        }
                        if (DriverActivity.this.driverAdapter == null) {
                            DriverActivity.this.driverAdapter = new DriverAdapter((Activity)DriverActivity.this, DriverActivity.this.driverItems);
                            DriverActivity.this.listViewDriver.setAdapter((ListAdapter)DriverActivity.this.driverAdapter);
                        } else {
                            DriverActivity.this.driverAdapter.setDriver(DriverActivity.this.driverItems);
                            DriverActivity.this.driverAdapter.notifyDataSetChanged();
                        }
                        DriverActivity.this.page = 1 + DriverActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    DriverActivity.this.loadingMore = true;
                    DriverActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427367);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Driver");
        this.dataPref = new DataPref((Context)this);
        this.listViewDriver = (ListView)this.findViewById(2131296708);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.buttonAdd = (ImageButton)this.findViewById(2131296584);
        this.buttonAdd.setOnClickListener(new View.OnClickListener((DriverActivity)this){
            final /* synthetic */ DriverActivity this$0;
            {
                this.this$0 = driverActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.EditDriverActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewDriver.setOnItemClickListener(new AdapterView.OnItemClickListener((DriverActivity)this){
            final /* synthetic */ DriverActivity this$0;
            {
                this.this$0 = driverActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.EditDriverActivity.class);
                intent.putExtra("edit", true);
                intent.putExtra("iddriver", ((DriverItem)this.this$0.driverItems.get(n)).getId_driver());
                intent.putExtra("nama", ((DriverItem)this.this$0.driverItems.get(n)).getNama());
                intent.putExtra("kodedriver", ((DriverItem)this.this$0.driverItems.get(n)).getKode());
                intent.putExtra("telepon", ((DriverItem)this.this$0.driverItems.get(n)).getTelepon());
                intent.putExtra("online", ((DriverItem)this.this$0.driverItems.get(n)).getOnline());
                intent.putExtra("status", ((DriverItem)this.this$0.driverItems.get(n)).getStatus());
                intent.putExtra("terakhir_login", ((DriverItem)this.this$0.driverItems.get(n)).getTerakhir_login());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewDriver.setOnScrollListener(new AbsListView.OnScrollListener((DriverActivity)this){
            final /* synthetic */ DriverActivity this$0;
            {
                this.this$0 = driverActivity;
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

}

