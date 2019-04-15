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
 *  android.view.View$OnClickListener
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.VoucherListActivity;
import com.mmdfauzan.bamos.adapter.VoucherAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.VoucherItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoucherListActivity
extends AppCompatActivity {
    ImageButton buttonAdd;
    DataPref dataPref;
    LinearLayout layoutLoading;
    ListView listViewVoucher;
    boolean loadingMore = false;
    VoucherAdapter voucherAdapter;
    ArrayList<VoucherItem> voucherItems = new ArrayList();

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.voucherItems = new ArrayList();
            this.voucherAdapter = null;
            this.listViewVoucher.setAdapter(null);
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)VoucherListActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)VoucherListActivity.this.dataPref.getToken());
                        Log.d((String)"request", (String)"starting");
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_voucher_list", "POST", (HashMap<String, String>)hashMap);
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
                    VoucherListActivity.this.loadingMore = false;
                    VoucherListActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            new VoucherItem();
                            jSONArray = jSONObject.getJSONArray("voucher");
                        } else {
                            Toast.makeText((Context)VoucherListActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            VoucherItem voucherItem = new VoucherItem();
                            voucherItem.setIdVouvher(jSONObject2.getString("idvoucher"));
                            voucherItem.setKodeVoucher(jSONObject2.getString("kodevoucher"));
                            VoucherListActivity.this.voucherItems.add((Object)voucherItem);
                        }
                        if (VoucherListActivity.this.voucherAdapter == null) {
                            VoucherListActivity.this.voucherAdapter = new VoucherAdapter((Activity)VoucherListActivity.this, VoucherListActivity.this.voucherItems);
                            VoucherListActivity.this.listViewVoucher.setAdapter((ListAdapter)VoucherListActivity.this.voucherAdapter);
                            return;
                        }
                        VoucherListActivity.this.voucherAdapter.setVoucher(VoucherListActivity.this.voucherItems);
                        VoucherListActivity.this.voucherAdapter.notifyDataSetChanged();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    VoucherListActivity.this.loadingMore = true;
                    VoucherListActivity.this.layoutLoading.setVisibility(0);
                }
            }.execute((Object[])new String[0]);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427408);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Voucher");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewVoucher = (ListView)this.findViewById(2131296717);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.buttonAdd = (ImageButton)this.findViewById(2131296584);
        this.buttonAdd.setOnClickListener(new View.OnClickListener((VoucherListActivity)this){
            final /* synthetic */ VoucherListActivity this$0;
            {
                this.this$0 = voucherListActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.VoucherActivity.class);
                intent.putExtra("newVoucher", 1);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewVoucher.setOnItemClickListener(new AdapterView.OnItemClickListener((VoucherListActivity)this){
            final /* synthetic */ VoucherListActivity this$0;
            {
                this.this$0 = voucherListActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.VoucherActivity.class);
                intent.putExtra("newVoucher", 0);
                intent.putExtra("kodevoucher", ((VoucherItem)this.this$0.voucherItems.get(n)).getKodeVoucher());
                intent.putExtra("idvoucher", ((VoucherItem)this.this$0.voucherItems.get(n)).getIdVouvher());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
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

