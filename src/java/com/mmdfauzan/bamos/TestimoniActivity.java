/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.mmdfauzan.bamos.TestimoniActivity;
import com.mmdfauzan.bamos.adapter.TestimoniAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.PemesananItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestimoniActivity
extends AppCompatActivity {
    DataPref dataPref;
    LinearLayout layoutLoading;
    ListView listViewTestimoni;
    boolean loadingMore = false;
    int page = 1;
    int pages = 0;
    ArrayList<PemesananItem> pemesananItems = new ArrayList();
    TestimoniAdapter testimoniAdapter;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427403);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Testimoni");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewTestimoni = (ListView)this.findViewById(2131296716);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.listViewTestimoni.setOnScrollListener(new AbsListView.OnScrollListener((TestimoniActivity)this){
            final /* synthetic */ TestimoniActivity this$0;
            {
                this.this$0 = testimoniActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)TestimoniActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)TestimoniActivity.this.dataPref.getToken());
                                hashMap.put((Object)"p", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_testimoni", "POST", (HashMap<String, String>)hashMap);
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
                         * Lifted jumps to return sites
                         */
                        protected void onPostExecute(JSONObject jSONObject) {
                            TestimoniActivity.this.loadingMore = false;
                            TestimoniActivity.this.layoutLoading.setVisibility(8);
                            if (jSONObject == null) {
                                AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                                Object[] arrobject = new String[]{String.valueOf((int)TestimoniActivity.this.page)};
                                asyncTask.execute(arrobject);
                                return;
                            }
                            try {
                                int n = jSONObject.getInt("success");
                                jSONObject.getString("message");
                                if (n != 1) return;
                                TestimoniActivity.this.pages = jSONObject.getInt("pages");
                                new PemesananItem();
                                JSONArray jSONArray = jSONObject.getJSONArray("pesanan");
                                for (int i = 0; i < jSONArray.length(); ++i) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    PemesananItem pemesananItem = new PemesananItem();
                                    pemesananItem.setIdPesanan(jSONObject2.getString("idpesanan"));
                                    pemesananItem.setNamaPesanan(jSONObject2.getString("nama"));
                                    pemesananItem.setRating(jSONObject2.getString("rating"));
                                    pemesananItem.setReview(jSONObject2.getString("review"));
                                    pemesananItem.setReviewWaktu(jSONObject2.getString("review_waktu"));
                                    TestimoniActivity.this.pemesananItems.add((Object)pemesananItem);
                                }
                                if (TestimoniActivity.this.testimoniAdapter == null) {
                                    TestimoniActivity.this.testimoniAdapter = new TestimoniAdapter((Activity)TestimoniActivity.this, TestimoniActivity.this.pemesananItems);
                                    TestimoniActivity.this.listViewTestimoni.setAdapter((ListAdapter)TestimoniActivity.this.testimoniAdapter);
                                } else {
                                    TestimoniActivity.this.testimoniAdapter.setPemesanan(TestimoniActivity.this.pemesananItems);
                                    TestimoniActivity.this.testimoniAdapter.notifyDataSetChanged();
                                }
                                TestimoniActivity.this.page = 1 + TestimoniActivity.this.page;
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }

                        protected void onPreExecute() {
                            TestimoniActivity.this.loadingMore = true;
                            TestimoniActivity.this.layoutLoading.setVisibility(0);
                        }
                    };
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.listViewTestimoni.setOnItemClickListener(new AdapterView.OnItemClickListener((TestimoniActivity)this){
            final /* synthetic */ TestimoniActivity this$0;
            {
                this.this$0 = testimoniActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.PemesananDetilActivity.class);
                intent.putExtra("idpesanan", ((PemesananItem)this.this$0.pemesananItems.get(n)).getIdPesanan());
                this.this$0.startActivity(intent);
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

