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
import com.mmdfauzan.bamos.FindProductActivity;
import com.mmdfauzan.bamos.adapter.ProdukAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.ProdukItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindProductActivity
extends AppCompatActivity {
    DataPref dataPref;
    LinearLayout layoutLoading;
    ListView listViewProduk;
    boolean loadingMore = false;
    int page = 1;
    int pages;
    ProdukAdapter produkAdapter;
    ArrayList<ProdukItem> produkItems = new ArrayList();
    String query;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.produkItems = new ArrayList();
            this.produkAdapter = null;
            this.listViewProduk.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)FindProductActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)FindProductActivity.this.dataPref.getToken());
                        hashMap.put((Object)"q", (Object)FindProductActivity.this.query);
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        Log.d((String)"request", (String)"starting");
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/find_product", "POST", (HashMap<String, String>)hashMap);
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
                    FindProductActivity.this.loadingMore = false;
                    FindProductActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)FindProductActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            FindProductActivity.this.pages = jSONObject.getInt("pages");
                            new ProdukItem();
                            jSONArray = jSONObject.getJSONArray("barang");
                        } else {
                            Toast.makeText((Context)FindProductActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            ProdukItem produkItem = new ProdukItem();
                            produkItem.setNamaProduk(jSONObject2.getString("nama"));
                            produkItem.setGambar(jSONObject2.getString("gambar"));
                            produkItem.setIdProduk(jSONObject2.getString("idbarang"));
                            produkItem.setKategoriProduk(jSONObject2.getString("idkategori"));
                            produkItem.setBeratProduk(jSONObject2.getString("berat"));
                            produkItem.setHarga(jSONObject2.getString("harga"));
                            produkItem.setHargaLama(jSONObject2.getString("harga_lama"));
                            produkItem.setHargaGrosir1(jSONObject2.getString("harga_grosir1"));
                            produkItem.setHargaGrosir1Minimal(jSONObject2.getString("harga_grosir1_minimal"));
                            produkItem.setHargaGrosir2(jSONObject2.getString("harga_grosir2"));
                            produkItem.setHargaGrosir2Minimal(jSONObject2.getString("harga_grosir2_minimal"));
                            produkItem.setHargaGrosir3(jSONObject2.getString("harga_grosir3"));
                            produkItem.setHargaGrosir3Minimal(jSONObject2.getString("harga_grosir3_minimal"));
                            produkItem.setHargaGrosir4(jSONObject2.getString("harga_grosir4"));
                            produkItem.setHargaGrosir4Minimal(jSONObject2.getString("harga_grosir4_minimal"));
                            produkItem.setHargaGrosir5(jSONObject2.getString("harga_grosir5"));
                            produkItem.setHargaGrosir5Minimal(jSONObject2.getString("harga_grosir5_minimal"));
                            produkItem.setDeskripsiProduk(jSONObject2.getString("deskripsi"));
                            produkItem.setLinkBukalapak(jSONObject2.getString("link_bukalapak"));
                            produkItem.setLinkTokopedia(jSONObject2.getString("link_tokopedia"));
                            produkItem.setLinkShopee(jSONObject2.getString("link_shopee"));
                            produkItem.setNamaVarian1(jSONObject2.getString("variasi1_nama"));
                            produkItem.setNamaVarian2(jSONObject2.getString("variasi2_nama"));
                            produkItem.setNamaVarian3(jSONObject2.getString("variasi3_nama"));
                            produkItem.setNamaVarian4(jSONObject2.getString("variasi4_nama"));
                            produkItem.setNamaVarian5(jSONObject2.getString("variasi5_nama"));
                            produkItem.setNamaVarian6(jSONObject2.getString("variasi6_nama"));
                            produkItem.setNamaVarian7(jSONObject2.getString("variasi7_nama"));
                            produkItem.setNamaVarian8(jSONObject2.getString("variasi8_nama"));
                            produkItem.setNamaVarian9(jSONObject2.getString("variasi9_nama"));
                            produkItem.setNamaVarian10(jSONObject2.getString("variasi10_nama"));
                            produkItem.setStokVarian1(jSONObject2.getString("variasi1_jumlah"));
                            produkItem.setStokVarian2(jSONObject2.getString("variasi2_jumlah"));
                            produkItem.setStokVarian3(jSONObject2.getString("variasi3_jumlah"));
                            produkItem.setStokVarian4(jSONObject2.getString("variasi4_jumlah"));
                            produkItem.setStokVarian5(jSONObject2.getString("variasi5_jumlah"));
                            produkItem.setStokVarian6(jSONObject2.getString("variasi6_jumlah"));
                            produkItem.setStokVarian7(jSONObject2.getString("variasi7_jumlah"));
                            produkItem.setStokVarian8(jSONObject2.getString("variasi8_jumlah"));
                            produkItem.setStokVarian9(jSONObject2.getString("variasi9_jumlah"));
                            produkItem.setStokVarian10(jSONObject2.getString("variasi10_jumlah"));
                            FindProductActivity.this.produkItems.add((Object)produkItem);
                        }
                        if (FindProductActivity.this.produkAdapter == null) {
                            FindProductActivity.this.produkAdapter = new ProdukAdapter((Activity)FindProductActivity.this, FindProductActivity.this.produkItems);
                            FindProductActivity.this.listViewProduk.setAdapter((ListAdapter)FindProductActivity.this.produkAdapter);
                        } else {
                            FindProductActivity.this.produkAdapter.setProduk(FindProductActivity.this.produkItems);
                            FindProductActivity.this.produkAdapter.notifyDataSetChanged();
                        }
                        FindProductActivity.this.page = 1 + FindProductActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    FindProductActivity.this.loadingMore = true;
                    FindProductActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427374);
        this.getSupportActionBar().setElevation(0.0f);
        this.query = this.getIntent().getStringExtra("q");
        this.getSupportActionBar().setTitle((CharSequence)("Pencarian: " + this.query));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewProduk = (ListView)this.findViewById(2131296715);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.listViewProduk.setOnScrollListener(new AbsListView.OnScrollListener((FindProductActivity)this){
            final /* synthetic */ FindProductActivity this$0;
            {
                this.this$0 = findProductActivity;
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
        this.listViewProduk.setOnItemClickListener(new AdapterView.OnItemClickListener((FindProductActivity)this){
            final /* synthetic */ FindProductActivity this$0;
            {
                this.this$0 = findProductActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.AddProductActivity.class);
                intent.putExtra("produk_edit", true);
                intent.putExtra("nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaProduk());
                intent.putExtra("idbarang", ((ProdukItem)this.this$0.produkItems.get(n)).getIdProduk());
                intent.putExtra("idkategori", ((ProdukItem)this.this$0.produkItems.get(n)).getKategoriProduk());
                intent.putExtra("deskripsi", ((ProdukItem)this.this$0.produkItems.get(n)).getDeskripsiProduk());
                intent.putExtra("harga", ((ProdukItem)this.this$0.produkItems.get(n)).getHarga());
                intent.putExtra("harga_lama", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaLama());
                intent.putExtra("harga_grosir1", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir1());
                intent.putExtra("harga_grosir1_minimal", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir1Minimal());
                intent.putExtra("harga_grosir2", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir2());
                intent.putExtra("harga_grosir2_minimal", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir2Minimal());
                intent.putExtra("harga_grosir3", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir3());
                intent.putExtra("harga_grosir3_minimal", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir3Minimal());
                intent.putExtra("harga_grosir4", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir4());
                intent.putExtra("harga_grosir4_minimal", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir4Minimal());
                intent.putExtra("harga_grosir5", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir5());
                intent.putExtra("harga_grosir5_minimal", ((ProdukItem)this.this$0.produkItems.get(n)).getHargaGrosir5Minimal());
                intent.putExtra("berat", ((ProdukItem)this.this$0.produkItems.get(n)).getBeratProduk());
                intent.putExtra("gambar", ((ProdukItem)this.this$0.produkItems.get(n)).getGambar());
                intent.putExtra("link_bukalapak", ((ProdukItem)this.this$0.produkItems.get(n)).getLinkBukalapak());
                intent.putExtra("link_tokopedia", ((ProdukItem)this.this$0.produkItems.get(n)).getLinkTokopedia());
                intent.putExtra("link_shopee", ((ProdukItem)this.this$0.produkItems.get(n)).getLinkShopee());
                intent.putExtra("variasi1_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian1());
                intent.putExtra("variasi1_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian1());
                intent.putExtra("variasi2_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian2());
                intent.putExtra("variasi2_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian2());
                intent.putExtra("variasi3_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian3());
                intent.putExtra("variasi3_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian3());
                intent.putExtra("variasi4_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian4());
                intent.putExtra("variasi4_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian4());
                intent.putExtra("variasi5_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian5());
                intent.putExtra("variasi5_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian5());
                intent.putExtra("variasi6_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian6());
                intent.putExtra("variasi6_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian6());
                intent.putExtra("variasi7_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian7());
                intent.putExtra("variasi7_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian7());
                intent.putExtra("variasi8_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian8());
                intent.putExtra("variasi8_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian8());
                intent.putExtra("variasi9_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian9());
                intent.putExtra("variasi9_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian9());
                intent.putExtra("variasi10_nama", ((ProdukItem)this.this$0.produkItems.get(n)).getNamaVarian10());
                intent.putExtra("variasi10_jumlah", ((ProdukItem)this.this$0.produkItems.get(n)).getStokVarian10());
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

