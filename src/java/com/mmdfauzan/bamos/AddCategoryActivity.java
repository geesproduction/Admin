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
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.Toast
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.mmdfauzan.bamos.AddCategoryActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class AddCategoryActivity
extends AppCompatActivity {
    Button buttonDelete;
    Button buttonSave;
    DataPref dataPref;
    EditText editTextNamaKategori;
    String gambar;
    String idKategori;
    ImageView imageViewGambar;
    boolean kategoriEdit = false;
    LinearLayout layoutGambar;
    String namaKategori;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 3 && n2 == -1) {
            this.gambar = intent.getStringExtra("gambar");
            this.setGambar(this.gambar);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427355);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Kategori Baru");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.editTextNamaKategori = (EditText)this.findViewById(2131296516);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonDelete = (Button)this.findViewById(2131296340);
        this.buttonDelete.setVisibility(8);
        this.layoutGambar = (LinearLayout)this.findViewById(2131296652);
        this.imageViewGambar = (ImageView)this.findViewById(2131296628);
        this.layoutGambar.setVisibility(8);
        Intent intent = this.getIntent();
        this.kategoriEdit = intent.getBooleanExtra("kategori_edit", false);
        if (this.kategoriEdit) {
            this.namaKategori = intent.getStringExtra("nama");
            this.getSupportActionBar().setTitle((CharSequence)"Edit Kategori");
            this.editTextNamaKategori.setText((CharSequence)intent.getStringExtra("nama"));
            this.idKategori = intent.getStringExtra("idkategori");
            this.gambar = intent.getStringExtra("gambar");
            this.setGambar(this.gambar);
            this.buttonDelete.setVisibility(0);
            this.buttonDelete.setOnClickListener(new View.OnClickListener((AddCategoryActivity)this){
                final /* synthetic */ AddCategoryActivity this$0;
                {
                    this.this$0 = addCategoryActivity;
                }

                public void onClick(View view) {
                    android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog$Builder((Context)this.this$0).create();
                    alertDialog.setTitle((CharSequence)"Hapus Kategori");
                    alertDialog.setMessage((CharSequence)("Menghapus kategori juga akan menghapus semua produk dalam kategori tersebut. Kamu yakin ingin menghapus kategori " + this.this$0.namaKategori + "?"));
                    alertDialog.setButton(-1, (CharSequence)"Ya", new android.content.DialogInterface$OnClickListener(this){
                        final /* synthetic */ 1 this$1;
                        {
                            this.this$1 = var1;
                        }

                        public void onClick(android.content.DialogInterface dialogInterface, int n) {
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    try {
                                        HashMap hashMap = new HashMap();
                                        hashMap.put((Object)"email", (Object)AddCategoryActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)AddCategoryActivity.this.dataPref.getToken());
                                        hashMap.put((Object)"idkategori", (Object)AddCategoryActivity.this.idKategori);
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_category", "POST", (HashMap<String, String>)hashMap);
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
                                                Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)string, (int)1).show();
                                                AddCategoryActivity.this.setResult(-1);
                                                AddCategoryActivity.this.finish();
                                                return;
                                            }
                                            if (n == 0) {
                                                Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)string, (int)1).show();
                                                return;
                                            }
                                            break block8;
                                        }
                                        Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)AddCategoryActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Menghapus kategori.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                        }
                    });
                    alertDialog.setButton(-2, (CharSequence)"Tidak", new android.content.DialogInterface$OnClickListener(this, alertDialog){
                        final /* synthetic */ 1 this$1;
                        final /* synthetic */ android.support.v7.app.AlertDialog val$alertDialog;
                        {
                            this.this$1 = var1;
                            this.val$alertDialog = alertDialog;
                        }

                        public void onClick(android.content.DialogInterface dialogInterface, int n) {
                            this.val$alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });
            this.layoutGambar.setVisibility(0);
            this.imageViewGambar.setOnClickListener(new View.OnClickListener((AddCategoryActivity)this){
                final /* synthetic */ AddCategoryActivity this$0;
                {
                    this.this$0 = addCategoryActivity;
                }

                public void onClick(View view) {
                    Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.GambarKategoriActivity.class);
                    intent.putExtra("idkategori", this.this$0.idKategori);
                    intent.putExtra("gambar", this.this$0.gambar);
                    this.this$0.startActivityForResult(intent, 3);
                    this.this$0.setResult(-1);
                }
            });
        }
        this.buttonSave.setOnClickListener(new View.OnClickListener((AddCategoryActivity)this){
            final /* synthetic */ AddCategoryActivity this$0;
            {
                this.this$0 = addCategoryActivity;
            }

            public void onClick(View view) {
                this.this$0.namaKategori = this.this$0.editTextNamaKategori.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.namaKategori.length() > 3) {
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)AddCategoryActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)AddCategoryActivity.this.dataPref.getToken());
                                if (AddCategoryActivity.this.kategoriEdit) {
                                    hashMap.put((Object)"idkategori", (Object)AddCategoryActivity.this.idKategori);
                                }
                                hashMap.put((Object)"nama", (Object)AddCategoryActivity.this.namaKategori);
                                if (AddCategoryActivity.this.kategoriEdit) {
                                    return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/edit_category", "POST", (HashMap<String, String>)hashMap);
                                }
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_category", "POST", (HashMap<String, String>)hashMap);
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
                                        Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)string, (int)1).show();
                                        AddCategoryActivity.this.setResult(-1);
                                        AddCategoryActivity.this.finish();
                                        return;
                                    }
                                    if (n == 0) {
                                        Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    break block8;
                                }
                                Toast.makeText((Context)AddCategoryActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                            }
                        }

                        /*
                         * Enabled aggressive block sorting
                         */
                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)AddCategoryActivity.this);
                            if (AddCategoryActivity.this.kategoriEdit) {
                                this.progressDialog.setMessage((CharSequence)"Mengubah kategori.");
                            } else {
                                this.progressDialog.setMessage((CharSequence)"Menambahkan kategori.");
                            }
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                    return;
                }
                Toast.makeText((Context)this.this$0, (CharSequence)"Mohon isikan nama kategori dengan valid", (int)1).show();
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

    public void setGambar(String string) {
        Picasso.with((Context)this).load("http://os.bikinaplikasi.com/gambar/" + string).placeholder(2131230939).resize(300, 300).centerInside().into(this.imageViewGambar);
    }

}

