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
import com.mmdfauzan.bamos.AddMenuActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class AddMenuActivity
extends AppCompatActivity {
    Button buttonHapusMenu;
    Button buttonPengisianMenu;
    Button buttonPilihIcon;
    Button buttonSimpanMenu;
    DataPref dataPref;
    EditText editTextIconMenu;
    EditText editTextLinkMenu;
    EditText editTextNamaMenu;
    boolean menuEdit = false;
    String menu_icon;
    String menu_id;
    String menu_link;
    String menu_nama;
    ShowToast showToast;

    protected void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 5 && n2 == -1) {
            this.editTextIconMenu.setText((CharSequence)intent.getStringExtra("icon"));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427356);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Menu");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.editTextNamaMenu = (EditText)this.findViewById(2131296517);
        this.editTextIconMenu = (EditText)this.findViewById(2131296498);
        this.editTextLinkMenu = (EditText)this.findViewById(2131296508);
        this.buttonPilihIcon = (Button)this.findViewById(2131296382);
        this.buttonPengisianMenu = (Button)this.findViewById(2131296377);
        this.buttonSimpanMenu = (Button)this.findViewById(2131296395);
        this.buttonHapusMenu = (Button)this.findViewById(2131296356);
        Intent intent = this.getIntent();
        if (intent.getBooleanExtra("edit", false)) {
            this.buttonHapusMenu.setVisibility(0);
            this.menu_id = intent.getStringExtra("idmenu");
            this.menuEdit = true;
            this.editTextNamaMenu.setText((CharSequence)intent.getStringExtra("nama"));
            this.editTextIconMenu.setText((CharSequence)intent.getStringExtra("icon"));
            this.editTextLinkMenu.setText((CharSequence)intent.getStringExtra("link"));
        } else {
            this.buttonHapusMenu.setVisibility(8);
            this.menuEdit = false;
        }
        this.buttonPilihIcon.setOnClickListener(new View.OnClickListener((AddMenuActivity)this){
            final /* synthetic */ AddMenuActivity this$0;
            {
                this.this$0 = addMenuActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.SelectMenuIconActivity.class);
                intent.putExtra("edit", 1);
                this.this$0.startActivityForResult(intent, 5);
            }
        });
        this.buttonPengisianMenu.setOnClickListener(new View.OnClickListener((AddMenuActivity)this){
            final /* synthetic */ AddMenuActivity this$0;
            {
                this.this$0 = addMenuActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.SelectMenuIconActivity.class);
                intent.putExtra("edit", 0);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonSimpanMenu.setOnClickListener(new View.OnClickListener((AddMenuActivity)this){
            final /* synthetic */ AddMenuActivity this$0;
            {
                this.this$0 = addMenuActivity;
            }

            public void onClick(View view) {
                if (this.this$0.editTextNamaMenu.getText().toString().length() > 0) {
                    if (this.this$0.editTextIconMenu.getText().toString().length() > 12) {
                        if (this.this$0.editTextLinkMenu.getText().toString().length() > 3) {
                            this.this$0.menu_icon = this.this$0.editTextIconMenu.getText().toString();
                            this.this$0.menu_link = this.this$0.editTextLinkMenu.getText().toString();
                            this.this$0.menu_nama = this.this$0.editTextNamaMenu.getText().toString();
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    try {
                                        HashMap hashMap = new HashMap();
                                        hashMap.put((Object)"email", (Object)AddMenuActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)AddMenuActivity.this.dataPref.getToken());
                                        if (AddMenuActivity.this.menuEdit) {
                                            hashMap.put((Object)"idmenu", (Object)AddMenuActivity.this.menu_id);
                                        }
                                        hashMap.put((Object)"icon", (Object)AddMenuActivity.this.menu_icon);
                                        hashMap.put((Object)"link", (Object)AddMenuActivity.this.menu_link);
                                        hashMap.put((Object)"nama", (Object)AddMenuActivity.this.menu_nama);
                                        if (AddMenuActivity.this.menuEdit) {
                                            return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/edit_menu", "POST", (HashMap<String, String>)hashMap);
                                        }
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_menu", "POST", (HashMap<String, String>)hashMap);
                                        return jSONObject;
                                    }
                                    catch (Exception exception) {
                                        exception.printStackTrace();
                                        return null;
                                    }
                                }

                                /*
                                 * Enabled force condition propagation
                                 * Lifted jumps to return sites
                                 */
                                protected void onPostExecute(JSONObject jSONObject) {
                                    if (this.progressDialog.isShowing()) {
                                        this.progressDialog.dismiss();
                                    }
                                    if (jSONObject != null) {
                                        try {
                                            int n = jSONObject.getInt("success");
                                            String string = jSONObject.getString("message");
                                            AddMenuActivity.this.showToast.Toast(string);
                                            if (n != 1) return;
                                        }
                                        catch (JSONException jSONException) {
                                            jSONException.printStackTrace();
                                            return;
                                        }
                                        AddMenuActivity.this.setResult(-1);
                                        AddMenuActivity.this.finish();
                                        return;
                                    }
                                    AddMenuActivity.this.showToast.ToastError();
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)AddMenuActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Menyimpan menu.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                            return;
                        }
                        this.this$0.showToast.Toast("Masukkan link menu yang valid");
                        return;
                    }
                    this.this$0.showToast.Toast("Masukkan link icon yang valid");
                    return;
                }
                this.this$0.showToast.Toast("Nama menu harus setidaknya 1 karakter.");
            }
        });
        this.buttonHapusMenu.setOnClickListener(new View.OnClickListener((AddMenuActivity)this){
            final /* synthetic */ AddMenuActivity this$0;
            {
                this.this$0 = addMenuActivity;
            }

            public void onClick(View view) {
                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog$Builder((Context)this.this$0).create();
                alertDialog.setTitle((CharSequence)"Hapus Menu");
                alertDialog.setMessage((CharSequence)"Menghapus menu mungkin akan menggeser urutan menu. Tetap hapus menu ini?");
                alertDialog.setButton(-1, (CharSequence)"Ya", new android.content.DialogInterface$OnClickListener(this){
                    final /* synthetic */ 4 this$1;
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
                                    hashMap.put((Object)"email", (Object)AddMenuActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)AddMenuActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"idmenu", (Object)AddMenuActivity.this.menu_id);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_menu", "POST", (HashMap<String, String>)hashMap);
                                    return jSONObject;
                                }
                                catch (Exception exception) {
                                    exception.printStackTrace();
                                    return null;
                                }
                            }

                            /*
                             * Enabled force condition propagation
                             * Lifted jumps to return sites
                             */
                            protected void onPostExecute(JSONObject jSONObject) {
                                if (this.progressDialog.isShowing()) {
                                    this.progressDialog.dismiss();
                                }
                                if (jSONObject != null) {
                                    try {
                                        int n = jSONObject.getInt("success");
                                        String string = jSONObject.getString("message");
                                        AddMenuActivity.this.showToast.Toast(string);
                                        if (n != 1) return;
                                    }
                                    catch (JSONException jSONException) {
                                        jSONException.printStackTrace();
                                        return;
                                    }
                                    AddMenuActivity.this.setResult(-1);
                                    AddMenuActivity.this.finish();
                                    return;
                                }
                                AddMenuActivity.this.showToast.ToastError();
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)AddMenuActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Menghapus menu.");
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                    }
                });
                alertDialog.setButton(-2, (CharSequence)"Tidak", new android.content.DialogInterface$OnClickListener(this, alertDialog){
                    final /* synthetic */ 4 this$1;
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

