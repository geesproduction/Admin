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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.GambarKategoriActivity;
import com.mmdfauzan.bamos.adapter.GambarKategoriAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.KategoriItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GambarKategoriActivity
extends AppCompatActivity {
    DataPref dataPref;
    String gambar;
    GambarKategoriAdapter gambarKategoriAdapter;
    String idkategori;
    ArrayList<KategoriItem> kategoriItems = new ArrayList();
    ListView listViewGambar;
    boolean loadingMore = false;
    int page = 1;
    int pages;
    String selected_gambar;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427376);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Pilih Gambar Kategori");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewGambar = (ListView)this.findViewById(2131296709);
        Intent intent = this.getIntent();
        this.idkategori = intent.getStringExtra("idkategori");
        this.gambar = intent.getStringExtra("gambar");
        this.listViewGambar.setOnScrollListener(new AbsListView.OnScrollListener((GambarKategoriActivity)this){
            final /* synthetic */ GambarKategoriActivity this$0;
            {
                this.this$0 = gambarKategoriActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)GambarKategoriActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)GambarKategoriActivity.this.dataPref.getToken());
                                hashMap.put((Object)"idkategori", (Object)GambarKategoriActivity.this.idkategori);
                                hashMap.put((Object)"p", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_images", "POST", (HashMap<String, String>)hashMap);
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
                            GambarKategoriActivity.this.loadingMore = false;
                            if (jSONObject == null) {
                                AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                                Object[] arrobject = new String[]{String.valueOf((int)GambarKategoriActivity.this.page)};
                                asyncTask.execute(arrobject);
                                return;
                            }
                            try {
                                JSONArray jSONArray;
                                int n = jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                if (n == 1) {
                                    GambarKategoriActivity.this.pages = jSONObject.getInt("pages");
                                    new KategoriItem();
                                    jSONArray = jSONObject.getJSONArray("barang");
                                } else {
                                    Toast.makeText((Context)GambarKategoriActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                for (int i = 0; i < jSONArray.length(); ++i) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    KategoriItem kategoriItem = new KategoriItem();
                                    kategoriItem.setGambar(jSONObject2.getString("gambar"));
                                    GambarKategoriActivity.this.kategoriItems.add((Object)kategoriItem);
                                }
                                if (GambarKategoriActivity.this.gambarKategoriAdapter == null) {
                                    GambarKategoriActivity.this.gambarKategoriAdapter = new GambarKategoriAdapter((Activity)GambarKategoriActivity.this, GambarKategoriActivity.this.kategoriItems);
                                    GambarKategoriActivity.this.listViewGambar.setAdapter((ListAdapter)GambarKategoriActivity.this.gambarKategoriAdapter);
                                } else {
                                    GambarKategoriActivity.this.gambarKategoriAdapter.setKategori(GambarKategoriActivity.this.kategoriItems);
                                    GambarKategoriActivity.this.gambarKategoriAdapter.notifyDataSetChanged();
                                }
                                GambarKategoriActivity.this.page = 1 + GambarKategoriActivity.this.page;
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }

                        protected void onPreExecute() {
                            GambarKategoriActivity.this.loadingMore = true;
                        }
                    };
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.listViewGambar.setOnItemClickListener(new AdapterView.OnItemClickListener((GambarKategoriActivity)this){
            final /* synthetic */ GambarKategoriActivity this$0;
            {
                this.this$0 = gambarKategoriActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.selected_gambar = ((KategoriItem)this.this$0.kategoriItems.get(n)).getGambar();
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)GambarKategoriActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)GambarKategoriActivity.this.dataPref.getToken());
                            hashMap.put((Object)"idkategori", (Object)GambarKategoriActivity.this.idkategori);
                            hashMap.put((Object)"gambar", (Object)GambarKategoriActivity.this.selected_gambar);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/select_category_image", "POST", (HashMap<String, String>)hashMap);
                            return jSONObject;
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            return null;
                        }
                    }

                    protected void onPostExecute(JSONObject jSONObject) {
                        GambarKategoriActivity.this.loadingMore = false;
                        if (jSONObject != null) {
                            try {
                                jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                Toast.makeText((Context)GambarKategoriActivity.this, (CharSequence)string, (int)1).show();
                                Intent intent = new Intent();
                                intent.putExtra("gambar", GambarKategoriActivity.this.selected_gambar);
                                GambarKategoriActivity.this.setResult(-1, intent);
                                GambarKategoriActivity.this.finish();
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        Toast.makeText((Context)GambarKategoriActivity.this, (CharSequence)"Terjadi kesalahan", (int)1).show();
                    }

                    protected void onPreExecute() {
                        GambarKategoriActivity.this.loadingMore = true;
                    }
                }.execute((Object[])new String[0]);
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

