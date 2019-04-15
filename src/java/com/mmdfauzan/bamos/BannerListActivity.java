/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.mmdfauzan.bamos.BannerListActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerListActivity
extends AppCompatActivity {
    Button buttonBanner1;
    Button buttonBanner2;
    Button buttonBanner3;
    Button buttonBanner4;
    Button buttonBanner5;
    DataPref dataPref;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427359);
        this.getSupportActionBar().setTitle((CharSequence)"Atur Banner");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.buttonBanner1 = (Button)this.findViewById(2131296331);
        this.buttonBanner2 = (Button)this.findViewById(2131296332);
        this.buttonBanner3 = (Button)this.findViewById(2131296333);
        this.buttonBanner4 = (Button)this.findViewById(2131296334);
        this.buttonBanner5 = (Button)this.findViewById(2131296335);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)BannerListActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)BannerListActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/check_premium", "POST", (HashMap<String, String>)hashMap);
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
                        if (jSONObject.getInt("premium") == 1) {
                            BannerListActivity.this.buttonBanner2.setVisibility(0);
                            BannerListActivity.this.buttonBanner3.setVisibility(0);
                            BannerListActivity.this.buttonBanner4.setVisibility(0);
                            BannerListActivity.this.buttonBanner5.setVisibility(0);
                            return;
                        }
                        BannerListActivity.this.buttonBanner2.setVisibility(8);
                        BannerListActivity.this.buttonBanner3.setVisibility(8);
                        BannerListActivity.this.buttonBanner4.setVisibility(8);
                        BannerListActivity.this.buttonBanner5.setVisibility(8);
                        return;
                    }
                    Toast.makeText((Context)BannerListActivity.this, (CharSequence)string, (int)1).show();
                    return;
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                    return;
                }
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)BannerListActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.buttonBanner1.setOnClickListener(new View.OnClickListener((BannerListActivity)this){
            final /* synthetic */ BannerListActivity this$0;
            {
                this.this$0 = bannerListActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerActivity.class);
                intent.putExtra("banner_id", 1);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonBanner2.setOnClickListener(new View.OnClickListener((BannerListActivity)this){
            final /* synthetic */ BannerListActivity this$0;
            {
                this.this$0 = bannerListActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerActivity.class);
                intent.putExtra("banner_id", 2);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonBanner3.setOnClickListener(new View.OnClickListener((BannerListActivity)this){
            final /* synthetic */ BannerListActivity this$0;
            {
                this.this$0 = bannerListActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerActivity.class);
                intent.putExtra("banner_id", 3);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonBanner4.setOnClickListener(new View.OnClickListener((BannerListActivity)this){
            final /* synthetic */ BannerListActivity this$0;
            {
                this.this$0 = bannerListActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerActivity.class);
                intent.putExtra("banner_id", 4);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonBanner5.setOnClickListener(new View.OnClickListener((BannerListActivity)this){
            final /* synthetic */ BannerListActivity this$0;
            {
                this.this$0 = bannerListActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerActivity.class);
                intent.putExtra("banner_id", 5);
                this.this$0.startActivity(intent);
            }
        });
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

