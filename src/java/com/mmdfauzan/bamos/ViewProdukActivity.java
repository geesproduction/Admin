/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.HashMap
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.mmdfauzan.bamos.ViewProdukActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.Rupiah;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewProdukActivity
extends AppCompatActivity {
    DataPref dataPref;
    String idbarang;
    int position;
    SliderLayout sliderLayout;
    TextView textViewBerat;
    TextView textViewHarga;
    TextView textViewHargaLama;
    TextView textViewNama;
    TextView textViewStok;
    WebView webViewDeskripsi;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427406);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Produk");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.sliderLayout = (SliderLayout)this.findViewById(2131296814);
        this.sliderLayout.stopAutoCycle();
        this.textViewNama = (TextView)this.findViewById(2131297098);
        this.textViewHargaLama = (TextView)this.findViewById(2131297060);
        this.textViewHargaLama.setPaintFlags(16 | this.textViewHargaLama.getPaintFlags());
        this.textViewHarga = (TextView)this.findViewById(2131297059);
        this.textViewBerat = (TextView)this.findViewById(2131297052);
        this.textViewStok = (TextView)this.findViewById(2131297129);
        this.webViewDeskripsi = (WebView)this.findViewById(2131297170);
        this.idbarang = this.getIntent().getStringExtra("idbarang");
        this.webViewDeskripsi.getSettings().setJavaScriptEnabled(true);
        this.webViewDeskripsi.getSettings().setGeolocationEnabled(true);
        this.webViewDeskripsi.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webViewDeskripsi.setHorizontalScrollBarEnabled(false);
        this.webViewDeskripsi.setVerticalScrollBarEnabled(false);
        this.webViewDeskripsi.setWebViewClient(new WebViewClient((ViewProdukActivity)this){
            final /* synthetic */ ViewProdukActivity this$0;
            {
                this.this$0 = viewProdukActivity;
            }

            public void onPageFinished(WebView webView, String string) {
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
            }

            public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
                Toast.makeText((Context)this.this$0.getApplicationContext(), (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                this.this$0.webViewDeskripsi.setVisibility(8);
            }
        });
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                HashMap hashMap = new HashMap();
                hashMap.put((Object)"email", (Object)ViewProdukActivity.this.dataPref.getEmail());
                hashMap.put((Object)"token", (Object)ViewProdukActivity.this.dataPref.getToken());
                hashMap.put((Object)"idbarang", (Object)ViewProdukActivity.this.idbarang);
                Log.d((String)"request", (String)"starting");
                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_product", "POST", (HashMap<String, String>)hashMap);
                if (jSONObject == null) return null;
                try {
                    Log.d((String)"JSON result", (String)jSONObject.toString());
                    return jSONObject;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
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
                if (jSONObject != null) {
                    try {
                        int n = jSONObject.getInt("success");
                        jSONObject.getString("message");
                        if (n != 1) return;
                        JSONObject jSONObject2 = jSONObject.getJSONArray("barang").getJSONObject(0);
                        String string = jSONObject2.getString("idbarang");
                        String string2 = jSONObject2.getString("nama");
                        String string3 = jSONObject2.getString("harga");
                        String string4 = jSONObject2.getString("berat");
                        jSONObject2.getString("deskripsi");
                        String string5 = jSONObject2.getString("gambar");
                        String string6 = jSONObject2.getString("gambar2");
                        String string7 = jSONObject2.getString("gambar3");
                        String string8 = jSONObject2.getString("gambar4");
                        String string9 = jSONObject2.getString("gambar5");
                        String string10 = jSONObject2.getString("harga_lama");
                        String string11 = jSONObject2.getString("variasi1_jumlah");
                        String string12 = jSONObject2.getString("variasi1_nama");
                        String string13 = jSONObject2.getString("variasi2_jumlah");
                        String string14 = jSONObject2.getString("variasi2_nama");
                        String string15 = jSONObject2.getString("variasi3_jumlah");
                        String string16 = jSONObject2.getString("variasi3_nama");
                        String string17 = jSONObject2.getString("variasi4_jumlah");
                        String string18 = jSONObject2.getString("variasi4_nama");
                        String string19 = jSONObject2.getString("variasi5_jumlah");
                        String string20 = jSONObject2.getString("variasi5_nama");
                        String string21 = jSONObject2.getString("variasi6_jumlah");
                        String string22 = jSONObject2.getString("variasi6_nama");
                        String string23 = jSONObject2.getString("variasi7_jumlah");
                        String string24 = jSONObject2.getString("variasi7_nama");
                        String string25 = jSONObject2.getString("variasi8_jumlah");
                        String string26 = jSONObject2.getString("variasi8_nama");
                        String string27 = jSONObject2.getString("variasi9_jumlah");
                        String string28 = jSONObject2.getString("variasi9_nama");
                        String string29 = jSONObject2.getString("variasi10_jumlah");
                        String string30 = jSONObject2.getString("variasi10_nama");
                        String string31 = jSONObject2.getString("harga_grosir1");
                        String string32 = jSONObject2.getString("harga_grosir1_minimal");
                        String string33 = jSONObject2.getString("harga_grosir2");
                        String string34 = jSONObject2.getString("harga_grosir2_minimal");
                        String string35 = jSONObject2.getString("harga_grosir3");
                        String string36 = jSONObject2.getString("harga_grosir3_minimal");
                        String string37 = jSONObject2.getString("harga_grosir4");
                        String string38 = jSONObject2.getString("harga_grosir4_minimal");
                        String string39 = jSONObject2.getString("harga_grosir5");
                        String string40 = jSONObject2.getString("harga_grosir5_minimal");
                        Rupiah rupiah = new Rupiah();
                        String string41 = "0";
                        String string42 = "";
                        String string43 = "";
                        String string44 = "";
                        String string45 = "";
                        String string46 = "";
                        if (!string40.equals((Object)"0")) {
                            string46 = "\n\u2265 " + string40 + " barang : " + rupiah.toRupiah(string39);
                            string41 = string40;
                        }
                        if (!string38.equals((Object)"0")) {
                            String string47;
                            if (string41.equals((Object)"0")) {
                                string47 = "\u2265 " + string38 + " barang";
                            } else {
                                String string48 = String.valueOf((int)(-1 + Integer.parseInt((String)string41)));
                                string47 = string38 + " - " + string48 + " barang";
                            }
                            string45 = "\n" + string47 + " : " + rupiah.toRupiah(string37);
                            string41 = string38;
                        }
                        if (!string36.equals((Object)"0")) {
                            String string49;
                            if (string41.equals((Object)"0")) {
                                string49 = "\u2265 " + string36 + " barang";
                            } else {
                                String string50 = String.valueOf((int)(-1 + Integer.parseInt((String)string41)));
                                string49 = string36 + " - " + string50 + " barang";
                            }
                            string44 = "\n" + string49 + " : " + rupiah.toRupiah(string35);
                            string41 = string36;
                        }
                        if (!string34.equals((Object)"0")) {
                            String string51;
                            if (string41.equals((Object)"0")) {
                                string51 = "\u2265 " + string34 + " barang";
                            } else {
                                String string52 = String.valueOf((int)(-1 + Integer.parseInt((String)string41)));
                                string51 = string34 + " - " + string52 + " barang";
                            }
                            string43 = "\n" + string51 + " : " + rupiah.toRupiah(string33);
                            string41 = string34;
                        }
                        if (!string32.equals((Object)"0")) {
                            String string53;
                            if (string41.equals((Object)"0")) {
                                string53 = "\u2265 " + string32 + " barang";
                            } else {
                                String string54;
                                String string55 = String.valueOf((int)(-1 + Integer.parseInt((String)string41)));
                                string53 = string54 = string32 + " - " + string55 + " barang";
                            }
                            string42 = "\n" + string53 + " : " + rupiah.toRupiah(string31);
                        }
                        String string56 = "Stok :\n";
                        if (Integer.parseInt((String)string11) > 0) {
                            string56 = string56 + "- " + string12 + " (" + string11 + ")\n";
                        }
                        if (Integer.parseInt((String)string13) > 0) {
                            string56 = string56 + "- " + string14 + " (" + string13 + ")\n";
                        }
                        if (Integer.parseInt((String)string15) > 0) {
                            string56 = string56 + "- " + string16 + " (" + string15 + ")\n";
                        }
                        if (Integer.parseInt((String)string17) > 0) {
                            string56 = string56 + "- " + string18 + " (" + string17 + ")\n";
                        }
                        if (Integer.parseInt((String)string19) > 0) {
                            string56 = string56 + "- " + string20 + " (" + string19 + ")\n";
                        }
                        if (Integer.parseInt((String)string21) > 0) {
                            string56 = string56 + "- " + string22 + " (" + string21 + ")\n";
                        }
                        if (Integer.parseInt((String)string23) > 0) {
                            string56 = string56 + "- " + string24 + " (" + string23 + ")\n";
                        }
                        if (Integer.parseInt((String)string25) > 0) {
                            string56 = string56 + "- " + string26 + " (" + string25 + ")\n";
                        }
                        if (Integer.parseInt((String)string27) > 0) {
                            string56 = string56 + "- " + string28 + " (" + string27 + ")\n";
                        }
                        if (Integer.parseInt((String)string29) > 0) {
                            string56 = string56 + "- " + string30 + " (" + string29 + ")\n";
                        }
                        String string57 = rupiah.toRupiah(string3);
                        String string58 = string57 + string42 + string43 + string44 + string45 + string46;
                        ViewProdukActivity.this.textViewNama.setText((CharSequence)string2);
                        ViewProdukActivity.this.textViewHarga.setText((CharSequence)string58);
                        if (!string10.equals((Object)"0")) {
                            ViewProdukActivity.this.textViewHargaLama.setText((CharSequence)rupiah.toRupiah(string10));
                        }
                        ViewProdukActivity.this.textViewStok.setText((CharSequence)string56);
                        ViewProdukActivity.this.textViewBerat.setText((CharSequence)("Berat : " + string4 + " gram"));
                        String string59 = "http://os.bikinaplikasi.com/data/produk/?idtoko=" + ViewProdukActivity.this.dataPref.getUsername() + "&produk=" + string;
                        ViewProdukActivity.this.webViewDeskripsi.loadUrl(string59);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                        String string60 = simpleDateFormat.format(new Date());
                        ViewProdukActivity.this.position = 0;
                        if (!string5.equals((Object)"") && !string5.equals((Object)"null")) {
                            int n2 = ViewProdukActivity.this.position;
                            DefaultSliderView defaultSliderView = new DefaultSliderView((Context)ViewProdukActivity.this);
                            defaultSliderView.image("http://os.bikinaplikasi.com/gambar/" + string5 + "?" + string60).setScaleType(BaseSliderView.ScaleType.CenterInside);
                            ViewProdukActivity.this.sliderLayout.addSlider(defaultSliderView);
                            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(this, n2, string5, string6, string7, string8, string9){
                                final /* synthetic */ GetBarang this$1;
                                final /* synthetic */ String val$gambar;
                                final /* synthetic */ String val$gambar2;
                                final /* synthetic */ String val$gambar3;
                                final /* synthetic */ String val$gambar4;
                                final /* synthetic */ String val$gambar5;
                                final /* synthetic */ int val$gambar_position;
                                {
                                    this.this$1 = getBarang;
                                    this.val$gambar_position = n;
                                    this.val$gambar = string;
                                    this.val$gambar2 = string2;
                                    this.val$gambar3 = string3;
                                    this.val$gambar4 = string4;
                                    this.val$gambar5 = string5;
                                }

                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    Intent intent = new Intent((Context)this.this$1.ViewProdukActivity.this, com.mmdfauzan.bamos.ViewGambarActivity.class);
                                    intent.putExtra("idgambar", this.val$gambar_position);
                                    intent.putExtra("gambar", this.val$gambar);
                                    intent.putExtra("gambar2", this.val$gambar2);
                                    intent.putExtra("gambar3", this.val$gambar3);
                                    intent.putExtra("gambar4", this.val$gambar4);
                                    intent.putExtra("gambar5", this.val$gambar5);
                                    this.this$1.ViewProdukActivity.this.startActivity(intent);
                                }
                            });
                            ViewProdukActivity viewProdukActivity = ViewProdukActivity.this;
                            viewProdukActivity.position = 1 + viewProdukActivity.position;
                        }
                        if (!string6.equals((Object)"") && !string6.equals((Object)"null")) {
                            int n3 = ViewProdukActivity.this.position;
                            DefaultSliderView defaultSliderView = new DefaultSliderView((Context)ViewProdukActivity.this);
                            defaultSliderView.image("http://os.bikinaplikasi.com/gambar/" + string6 + "?" + string60).setScaleType(BaseSliderView.ScaleType.CenterInside);
                            ViewProdukActivity.this.sliderLayout.addSlider(defaultSliderView);
                            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(this, n3, string5, string6, string7, string8, string9){
                                final /* synthetic */ GetBarang this$1;
                                final /* synthetic */ String val$gambar;
                                final /* synthetic */ String val$gambar2;
                                final /* synthetic */ String val$gambar3;
                                final /* synthetic */ String val$gambar4;
                                final /* synthetic */ String val$gambar5;
                                final /* synthetic */ int val$gambar_position;
                                {
                                    this.this$1 = getBarang;
                                    this.val$gambar_position = n;
                                    this.val$gambar = string;
                                    this.val$gambar2 = string2;
                                    this.val$gambar3 = string3;
                                    this.val$gambar4 = string4;
                                    this.val$gambar5 = string5;
                                }

                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    Intent intent = new Intent((Context)this.this$1.ViewProdukActivity.this, com.mmdfauzan.bamos.ViewGambarActivity.class);
                                    intent.putExtra("idgambar", this.val$gambar_position);
                                    intent.putExtra("gambar", this.val$gambar);
                                    intent.putExtra("gambar2", this.val$gambar2);
                                    intent.putExtra("gambar3", this.val$gambar3);
                                    intent.putExtra("gambar4", this.val$gambar4);
                                    intent.putExtra("gambar5", this.val$gambar5);
                                    this.this$1.ViewProdukActivity.this.startActivity(intent);
                                }
                            });
                            ViewProdukActivity viewProdukActivity = ViewProdukActivity.this;
                            viewProdukActivity.position = 1 + viewProdukActivity.position;
                        }
                        if (!string7.equals((Object)"") && !string7.equals((Object)"null")) {
                            int n4 = ViewProdukActivity.this.position;
                            DefaultSliderView defaultSliderView = new DefaultSliderView((Context)ViewProdukActivity.this);
                            defaultSliderView.image("http://os.bikinaplikasi.com/gambar/" + string7 + "?" + string60).setScaleType(BaseSliderView.ScaleType.CenterInside);
                            ViewProdukActivity.this.sliderLayout.addSlider(defaultSliderView);
                            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(this, n4, string5, string6, string7, string8, string9){
                                final /* synthetic */ GetBarang this$1;
                                final /* synthetic */ String val$gambar;
                                final /* synthetic */ String val$gambar2;
                                final /* synthetic */ String val$gambar3;
                                final /* synthetic */ String val$gambar4;
                                final /* synthetic */ String val$gambar5;
                                final /* synthetic */ int val$gambar_position;
                                {
                                    this.this$1 = getBarang;
                                    this.val$gambar_position = n;
                                    this.val$gambar = string;
                                    this.val$gambar2 = string2;
                                    this.val$gambar3 = string3;
                                    this.val$gambar4 = string4;
                                    this.val$gambar5 = string5;
                                }

                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    Intent intent = new Intent((Context)this.this$1.ViewProdukActivity.this, com.mmdfauzan.bamos.ViewGambarActivity.class);
                                    intent.putExtra("idgambar", this.val$gambar_position);
                                    intent.putExtra("gambar", this.val$gambar);
                                    intent.putExtra("gambar2", this.val$gambar2);
                                    intent.putExtra("gambar3", this.val$gambar3);
                                    intent.putExtra("gambar4", this.val$gambar4);
                                    intent.putExtra("gambar5", this.val$gambar5);
                                    this.this$1.ViewProdukActivity.this.startActivity(intent);
                                }
                            });
                            ViewProdukActivity viewProdukActivity = ViewProdukActivity.this;
                            viewProdukActivity.position = 1 + viewProdukActivity.position;
                        }
                        if (!string8.equals((Object)"") && !string8.equals((Object)"null")) {
                            int n5 = ViewProdukActivity.this.position;
                            DefaultSliderView defaultSliderView = new DefaultSliderView((Context)ViewProdukActivity.this);
                            defaultSliderView.image("http://os.bikinaplikasi.com/gambar/" + string8 + "?" + string60).setScaleType(BaseSliderView.ScaleType.CenterInside);
                            ViewProdukActivity.this.sliderLayout.addSlider(defaultSliderView);
                            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(this, n5, string5, string6, string7, string8, string9){
                                final /* synthetic */ GetBarang this$1;
                                final /* synthetic */ String val$gambar;
                                final /* synthetic */ String val$gambar2;
                                final /* synthetic */ String val$gambar3;
                                final /* synthetic */ String val$gambar4;
                                final /* synthetic */ String val$gambar5;
                                final /* synthetic */ int val$gambar_position;
                                {
                                    this.this$1 = getBarang;
                                    this.val$gambar_position = n;
                                    this.val$gambar = string;
                                    this.val$gambar2 = string2;
                                    this.val$gambar3 = string3;
                                    this.val$gambar4 = string4;
                                    this.val$gambar5 = string5;
                                }

                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    Intent intent = new Intent((Context)this.this$1.ViewProdukActivity.this, com.mmdfauzan.bamos.ViewGambarActivity.class);
                                    intent.putExtra("idgambar", this.val$gambar_position);
                                    intent.putExtra("gambar", this.val$gambar);
                                    intent.putExtra("gambar2", this.val$gambar2);
                                    intent.putExtra("gambar3", this.val$gambar3);
                                    intent.putExtra("gambar4", this.val$gambar4);
                                    intent.putExtra("gambar5", this.val$gambar5);
                                    this.this$1.ViewProdukActivity.this.startActivity(intent);
                                }
                            });
                            ViewProdukActivity viewProdukActivity = ViewProdukActivity.this;
                            viewProdukActivity.position = 1 + viewProdukActivity.position;
                        }
                        if (!string9.equals((Object)"") && !string9.equals((Object)"null")) {
                            int n6 = ViewProdukActivity.this.position;
                            DefaultSliderView defaultSliderView = new DefaultSliderView((Context)ViewProdukActivity.this);
                            defaultSliderView.image("http://os.bikinaplikasi.com/gambar/" + string9 + "?" + string60).setScaleType(BaseSliderView.ScaleType.CenterInside);
                            ViewProdukActivity.this.sliderLayout.addSlider(defaultSliderView);
                            defaultSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener(this, n6, string5, string6, string7, string8, string9){
                                final /* synthetic */ GetBarang this$1;
                                final /* synthetic */ String val$gambar;
                                final /* synthetic */ String val$gambar2;
                                final /* synthetic */ String val$gambar3;
                                final /* synthetic */ String val$gambar4;
                                final /* synthetic */ String val$gambar5;
                                final /* synthetic */ int val$gambar_position;
                                {
                                    this.this$1 = getBarang;
                                    this.val$gambar_position = n;
                                    this.val$gambar = string;
                                    this.val$gambar2 = string2;
                                    this.val$gambar3 = string3;
                                    this.val$gambar4 = string4;
                                    this.val$gambar5 = string5;
                                }

                                public void onSliderClick(BaseSliderView baseSliderView) {
                                    Intent intent = new Intent((Context)this.this$1.ViewProdukActivity.this, com.mmdfauzan.bamos.ViewGambarActivity.class);
                                    intent.putExtra("idgambar", this.val$gambar_position);
                                    intent.putExtra("gambar", this.val$gambar);
                                    intent.putExtra("gambar2", this.val$gambar2);
                                    intent.putExtra("gambar3", this.val$gambar3);
                                    intent.putExtra("gambar4", this.val$gambar4);
                                    intent.putExtra("gambar5", this.val$gambar5);
                                    this.this$1.ViewProdukActivity.this.startActivity(intent);
                                }
                            });
                        }
                        if (ViewProdukActivity.this.position < 1) return;
                        ViewProdukActivity.this.sliderLayout.setCurrentPosition(0);
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                } else {
                    Toast.makeText((Context)ViewProdukActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi.", (int)1).show();
                }
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)ViewProdukActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
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

