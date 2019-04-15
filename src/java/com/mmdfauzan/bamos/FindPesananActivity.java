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
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.mmdfauzan.bamos.FindPesananActivity;
import com.mmdfauzan.bamos.adapter.PemesananAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.PemesananItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindPesananActivity
extends AppCompatActivity {
    DataPref dataPref;
    EditText editTextCari;
    LinearLayout layoutLoading;
    ListView listViewPemesanan;
    boolean loadingMore = false;
    int page = 1;
    int pages;
    PemesananAdapter pemesananAdapter;
    ArrayList<PemesananItem> pemesananItems = new ArrayList();
    String q;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.pemesananItems = new ArrayList();
            this.pemesananAdapter = null;
            this.listViewPemesanan.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)FindPesananActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)FindPesananActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        hashMap.put((Object)"q", (Object)FindPesananActivity.this.q);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/find_order", "POST", (HashMap<String, String>)hashMap);
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
                    FindPesananActivity.this.loadingMore = false;
                    FindPesananActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)FindPesananActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        int n = jSONObject.getInt("success");
                        jSONObject.getString("message");
                        if (n != 1) return;
                        FindPesananActivity.this.pages = jSONObject.getInt("pages");
                        new PemesananItem();
                        JSONArray jSONArray = jSONObject.getJSONArray("pemesanan");
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            PemesananItem pemesananItem = new PemesananItem();
                            pemesananItem.setIdPesanan(jSONObject2.getString("idpesanan"));
                            pemesananItem.setNamaPesanan(jSONObject2.getString("namapesanan"));
                            pemesananItem.setKodePesanan(jSONObject2.getString("kodepesanan"));
                            pemesananItem.setWaktuPesanan(jSONObject2.getString("waktupesanan"));
                            pemesananItem.setStatusPesanan(jSONObject2.getString("statuspesanan"));
                            pemesananItem.setBuktiPembayaran(jSONObject2.getString("bukti_pembayaran"));
                            pemesananItem.setSaldo(jSONObject2.getString("saldo"));
                            pemesananItem.setAlamatPesanan(jSONObject2.getString("alamat"));
                            FindPesananActivity.this.pemesananItems.add((Object)pemesananItem);
                        }
                        if (FindPesananActivity.this.pemesananAdapter == null) {
                            FindPesananActivity.this.pemesananAdapter = new PemesananAdapter((Activity)FindPesananActivity.this, FindPesananActivity.this.pemesananItems);
                            FindPesananActivity.this.listViewPemesanan.setAdapter((ListAdapter)FindPesananActivity.this.pemesananAdapter);
                        } else {
                            FindPesananActivity.this.pemesananAdapter.setPemesanan(FindPesananActivity.this.pemesananItems);
                            FindPesananActivity.this.pemesananAdapter.notifyDataSetChanged();
                        }
                        FindPesananActivity.this.page = 1 + FindPesananActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    FindPesananActivity.this.loadingMore = true;
                    FindPesananActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    public void onBackPressed() {
        this.setResult(-1);
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427373);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Cari Pesanan");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.editTextCari = (EditText)this.findViewById(2131296481);
        this.listViewPemesanan = (ListView)this.findViewById(2131296714);
        this.editTextCari.setOnEditorActionListener(new TextView.OnEditorActionListener((FindPesananActivity)this){
            final /* synthetic */ FindPesananActivity this$0;
            {
                this.this$0 = findPesananActivity;
            }

            public boolean onEditorAction(TextView textView, int n, android.view.KeyEvent keyEvent) {
                if (n == 3) {
                    ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    this.this$0.q = this.this$0.editTextCari.getText().toString();
                    if (this.this$0.q.length() > 0) {
                        this.this$0.page = 1;
                        this.this$0.pemesananItems = new ArrayList();
                        this.this$0.pemesananAdapter = null;
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                        asyncTask.execute(arrobject);
                    }
                    return true;
                }
                return false;
            }
        });
        this.listViewPemesanan.setOnItemClickListener(new AdapterView.OnItemClickListener((FindPesananActivity)this){
            final /* synthetic */ FindPesananActivity this$0;
            {
                this.this$0 = findPesananActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PemesananDetilActivity.class);
                intent.putExtra("idpesanan", ((PemesananItem)this.this$0.pemesananItems.get(n)).getIdPesanan());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewPemesanan.setOnScrollListener(new AbsListView.OnScrollListener((FindPesananActivity)this){
            final /* synthetic */ FindPesananActivity this$0;
            {
                this.this$0 = findPesananActivity;
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
        this.setResult(-1);
        this.finish();
        return true;
    }

}

