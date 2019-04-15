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
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.Button
 *  android.widget.EditText
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mmdfauzan.bamos.BuatPemberitahuanActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class BuatPemberitahuanActivity
extends AppCompatActivity {
    Button buttonKirim;
    DataPref dataPref;
    EditText editTextPemberitahuanIsi;
    EditText editTextPemberitahuanJudul;
    String pemberitahuanIsi;
    String pemberitahuanJudul;
    WebView webViewPemberitahuan;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427361);
        this.getSupportActionBar().setTitle((CharSequence)"Kirim Pemberitahuan");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.editTextPemberitahuanJudul = (EditText)this.findViewById(2131296540);
        this.editTextPemberitahuanIsi = (EditText)this.findViewById(2131296539);
        this.buttonKirim = (Button)this.findViewById(2131296363);
        this.webViewPemberitahuan = (WebView)this.findViewById(2131297172);
        this.buttonKirim.setOnClickListener(new View.OnClickListener((BuatPemberitahuanActivity)this){
            final /* synthetic */ BuatPemberitahuanActivity this$0;
            {
                this.this$0 = buatPemberitahuanActivity;
            }

            public void onClick(View view) {
                this.this$0.pemberitahuanJudul = this.this$0.editTextPemberitahuanJudul.getText().toString();
                this.this$0.pemberitahuanIsi = this.this$0.editTextPemberitahuanIsi.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.pemberitahuanJudul.length() > 3 && this.this$0.pemberitahuanIsi.length() > 10) {
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)BuatPemberitahuanActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)BuatPemberitahuanActivity.this.dataPref.getToken());
                                hashMap.put((Object)"title", (Object)BuatPemberitahuanActivity.this.pemberitahuanJudul);
                                hashMap.put((Object)"body", (Object)BuatPemberitahuanActivity.this.pemberitahuanIsi);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_notification", "POST", (HashMap<String, String>)hashMap);
                                return jSONObject;
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                                return null;
                            }
                        }

                        protected void onPostExecute(JSONObject jSONObject) {
                            block8 : {
                                block7 : {
                                    int n;
                                    String string;
                                    block6 : {
                                        if (this.progressDialog.isShowing()) {
                                            this.progressDialog.dismiss();
                                        }
                                        if (jSONObject == null) break block7;
                                        try {
                                            n = jSONObject.getInt("success");
                                            string = jSONObject.getString("message");
                                            if (n != 1) break block6;
                                        }
                                        catch (JSONException jSONException) {
                                            jSONException.printStackTrace();
                                            return;
                                        }
                                        Toast.makeText((Context)BuatPemberitahuanActivity.this, (CharSequence)string, (int)1).show();
                                        BuatPemberitahuanActivity.this.finish();
                                        return;
                                    }
                                    if (n == 0) {
                                        Toast.makeText((Context)BuatPemberitahuanActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    break block8;
                                }
                                Toast.makeText((Context)BuatPemberitahuanActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                            }
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)BuatPemberitahuanActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Mengirim pemberitahuan.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                    return;
                }
                Toast.makeText((Context)this.this$0, (CharSequence)"Masukkan informasi yang lebih panjang", (int)1).show();
            }
        });
        this.webViewPemberitahuan.clearCache(true);
        this.webViewPemberitahuan.getSettings().setJavaScriptEnabled(true);
        this.webViewPemberitahuan.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        this.webViewPemberitahuan.setWebViewClient(new WebViewClient());
        this.webViewPemberitahuan.loadUrl("http://os.bikinaplikasi.com/data/pemberitahuan?idtoko=" + this.dataPref.getUsername());
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

