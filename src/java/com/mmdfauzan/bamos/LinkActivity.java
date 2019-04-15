/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.ClipData
 *  android.content.ClipboardManager
 *  android.content.Context
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.text.ClipboardManager
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.File
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.ClipboardManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.LinkActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.Uploader;
import java.io.File;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LinkActivity
extends AppCompatActivity {
    Button buttonBikin;
    Button buttonCopy;
    Button buttonUpdate;
    Button buttonUpload;
    DataPref dataPref;
    File fileApk;
    LinearLayout layoutGetLink;
    LinearLayout layoutLink;
    TextView textViewInfo;
    TextView textViewInfoUpdate;
    TextView textViewLink;
    TextView textViewMessage;
    String uriApk;

    /*
     * Enabled aggressive block sorting
     */
    public void copyData(String string) {
        if (Build.VERSION.SDK_INT >= 11) {
            ((android.content.ClipboardManager)this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence)"Link Download", (CharSequence)string));
        } else {
            ((ClipboardManager)this.getSystemService("clipboard")).setText((CharSequence)string);
        }
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Link telah disalin.", (int)0).show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427379);
        this.dataPref = new DataPref((Context)this);
        this.getSupportActionBar().setTitle((CharSequence)"Link Download");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.layoutLink = (LinearLayout)this.findViewById(2131296671);
        this.layoutGetLink = (LinearLayout)this.findViewById(2131296658);
        this.buttonUpdate = (Button)this.findViewById(2131296405);
        this.buttonUpload = (Button)this.findViewById(2131296406);
        this.buttonBikin = (Button)this.findViewById(2131296336);
        this.buttonCopy = (Button)this.findViewById(2131296339);
        this.textViewInfo = (TextView)this.findViewById(2131297063);
        this.textViewInfoUpdate = (TextView)this.findViewById(2131297064);
        this.textViewMessage = (TextView)this.findViewById(2131297096);
        this.textViewLink = (TextView)this.findViewById(2131297085);
        this.fileApk = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/" + this.dataPref.getUsername() + ".apk");
        this.uriApk = (Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/" + this.dataPref.getUsername() + ".apk";
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)LinkActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)LinkActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_link", "POST", (HashMap<String, String>)hashMap);
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
                int n;
                String string;
                block8 : {
                    if (this.progressDialog.isShowing()) {
                        this.progressDialog.dismiss();
                    }
                    if (jSONObject == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        n = jSONObject.getInt("success");
                        string = jSONObject.getString("message");
                        if (n == 1) {
                            LinkActivity.this.layoutLink.setVisibility(0);
                            LinkActivity.this.layoutGetLink.setVisibility(8);
                            JSONObject jSONObject2 = jSONObject.getJSONArray("link").getJSONObject(0);
                            LinkActivity.this.textViewMessage.setText((CharSequence)jSONObject2.getString("message"));
                            LinkActivity.this.textViewLink.setText((CharSequence)jSONObject2.getString("url"));
                            final String string2 = jSONObject2.getString("url");
                            LinkActivity.this.buttonCopy.setOnClickListener(new View.OnClickListener(){

                                public void onClick(View view) {
                                    LinkActivity.this.copyData(string2);
                                }
                            });
                            if (LinkActivity.this.fileApk.exists()) {
                                LinkActivity.this.buttonUpdate.setVisibility(0);
                                LinkActivity.this.textViewInfoUpdate.setText((CharSequence)"Pilih Perbarui APK jika telah melakukan Edit Aplikasi");
                                LinkActivity.this.textViewInfoUpdate.setVisibility(0);
                                return;
                            }
                            LinkActivity.this.buttonUpdate.setVisibility(8);
                            LinkActivity.this.textViewInfoUpdate.setText((CharSequence)"Masuk ke Edit Aplikasi untuk memperbarui aplikasi. Jika tetap tidak bisa silakan lewati LANGKAH 1 dan langsung ikuti LANGKAH 2 setelah proses Edit Aplikasi.");
                            LinkActivity.this.textViewInfoUpdate.setVisibility(0);
                            return;
                        }
                        if (n != 2) break block8;
                        LinkActivity.this.layoutLink.setVisibility(8);
                        LinkActivity.this.layoutGetLink.setVisibility(0);
                        if (LinkActivity.this.fileApk.exists()) {
                            LinkActivity.this.textViewInfo.setText((CharSequence)"Sentuh tombol di bawah ini untuk mulai mendapatkan link.");
                            LinkActivity.this.buttonUpload.setVisibility(0);
                            LinkActivity.this.buttonBikin.setVisibility(8);
                            return;
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                    LinkActivity.this.textViewInfo.setText((CharSequence)"Silakan masuk ke menu Edit Aplikasi. Lewati LANGKAH 1 dan langsung ikuti LANGKAH 2.");
                    LinkActivity.this.buttonUpload.setVisibility(8);
                    LinkActivity.this.buttonBikin.setVisibility(0);
                    return;
                }
                if (n != 0) return;
                Toast.makeText((Context)LinkActivity.this, (CharSequence)string, (int)1).show();
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)LinkActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }.execute((Object[])new String[0]);
        this.buttonUpload.setOnClickListener(new View.OnClickListener((LinkActivity)this){
            final /* synthetic */ LinkActivity this$0;
            {
                this.this$0 = linkActivity;
            }

            public void onClick(View view) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            JSONObject jSONObject = new Uploader().uploadFile(LinkActivity.this.uriApk, "http://os.bikinaplikasi.com/api/admin_api_v2/upload_apk?e=" + LinkActivity.this.dataPref.getEmail() + "&t=" + LinkActivity.this.dataPref.getToken(), LinkActivity.this.dataPref.getUsername() + ".apk");
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
                                    new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                    Toast.makeText((Context)LinkActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                if (n == 0) {
                                    Toast.makeText((Context)LinkActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                break block8;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)LinkActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mendapatkan link aplikasi. Ini mungkin memerlukan waktu cukup lama. Mohon jangan tutup aplikasi atau berpindah aplikasi.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        this.buttonUpdate.setOnClickListener(new View.OnClickListener((LinkActivity)this){
            final /* synthetic */ LinkActivity this$0;
            {
                this.this$0 = linkActivity;
            }

            public void onClick(View view) {
                new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
            }
        });
        this.buttonBikin.setOnClickListener(new View.OnClickListener((LinkActivity)this){
            final /* synthetic */ LinkActivity this$0;
            {
                this.this$0 = linkActivity;
            }

            public void onClick(View view) {
                android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BikinFileActivity.class);
                intent.putExtra("edit_aplikasi", true);
                this.this$0.startActivity(intent);
                this.this$0.finish();
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

