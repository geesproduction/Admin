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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.KategoriActivity;
import com.mmdfauzan.bamos.adapter.KategoriAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.KategoriItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KategoriActivity
extends AppCompatActivity {
    ImageButton buttonAdd;
    DataPref dataPref;
    KategoriAdapter kategoriAdapter;
    ArrayList<KategoriItem> kategoriItems = new ArrayList();
    LinearLayout layoutLoading;
    ListView listViewKategori;
    boolean loadingMore = false;
    int page = 1;
    int pages;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.kategoriItems = new ArrayList();
            this.kategoriAdapter = null;
            this.listViewKategori.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)KategoriActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)KategoriActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_categories", "POST", (HashMap<String, String>)hashMap);
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
                    KategoriActivity.this.loadingMore = false;
                    KategoriActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)KategoriActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            KategoriActivity.this.pages = jSONObject.getInt("pages");
                            new KategoriItem();
                            jSONArray = jSONObject.getJSONArray("kategori");
                        } else {
                            Toast.makeText((Context)KategoriActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            KategoriItem kategoriItem = new KategoriItem();
                            kategoriItem.setIdKategori(jSONObject2.getString("idkategori"));
                            kategoriItem.setNamaKategori(jSONObject2.getString("nama"));
                            kategoriItem.setGambar(jSONObject2.getString("gambar"));
                            KategoriActivity.this.kategoriItems.add((Object)kategoriItem);
                        }
                        if (KategoriActivity.this.kategoriAdapter == null) {
                            KategoriActivity.this.kategoriAdapter = new KategoriAdapter((Activity)KategoriActivity.this, KategoriActivity.this.kategoriItems);
                            KategoriActivity.this.listViewKategori.setAdapter((ListAdapter)KategoriActivity.this.kategoriAdapter);
                        } else {
                            KategoriActivity.this.kategoriAdapter.setKategori(KategoriActivity.this.kategoriItems);
                            KategoriActivity.this.kategoriAdapter.notifyDataSetChanged();
                        }
                        KategoriActivity.this.page = 1 + KategoriActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    KategoriActivity.this.loadingMore = true;
                    KategoriActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
            this.setResult(-1);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427377);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Kategori");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewKategori = (ListView)this.findViewById(2131296710);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.buttonAdd = (ImageButton)this.findViewById(2131296584);
        this.buttonAdd.setOnClickListener(new View.OnClickListener((KategoriActivity)this){
            final /* synthetic */ KategoriActivity this$0;
            {
                this.this$0 = kategoriActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.AddCategoryActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewKategori.setOnItemClickListener(new AdapterView.OnItemClickListener((KategoriActivity)this){
            final /* synthetic */ KategoriActivity this$0;
            {
                this.this$0 = kategoriActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.AddCategoryActivity.class);
                intent.putExtra("kategori_edit", true);
                intent.putExtra("nama", ((KategoriItem)this.this$0.kategoriItems.get(n)).getNamaKategori());
                intent.putExtra("idkategori", ((KategoriItem)this.this$0.kategoriItems.get(n)).getIdKategori());
                intent.putExtra("gambar", ((KategoriItem)this.this$0.kategoriItems.get(n)).getGambar());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewKategori.setOnScrollListener(new AbsListView.OnScrollListener((KategoriActivity)this){
            final /* synthetic */ KategoriActivity this$0;
            {
                this.this$0 = kategoriActivity;
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

