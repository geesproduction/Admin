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
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.LinearLayout
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.mmdfauzan.bamos.EditDriverActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.FormValidation;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class EditDriverActivity
extends AppCompatActivity {
    Button buttonHapus;
    Button buttonSimpan;
    Button buttonSimpanAdd;
    Button buttonSimpanPassword;
    DataPref dataPref;
    boolean driverEdit = false;
    EditText editTextKode;
    EditText editTextNama;
    EditText editTextNamaAdd;
    EditText editTextPassword;
    EditText editTextPasswordAdd;
    EditText editTextPasswordAdmin;
    EditText editTextTelepon;
    EditText editTextTeleponAdd;
    String error = "Masukkan data yang valid";
    FormValidation formValidation;
    String idDriver;
    String kodeDriver;
    LinearLayout layoutAdd;
    LinearLayout layoutEdit;
    String namaDriver;
    String passwordAdmin;
    String passwordBaru;
    String passwordDriver;
    ShowToast showToast;
    String statusDriver;
    String teleponDriver;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427368);
        this.getSupportActionBar().setElevation(0.0f);
        this.layoutAdd = (LinearLayout)this.findViewById(2131296637);
        this.layoutEdit = (LinearLayout)this.findViewById(2131296646);
        this.editTextKode = (EditText)this.findViewById(2131296503);
        this.editTextNama = (EditText)this.findViewById(2131296514);
        this.editTextNamaAdd = (EditText)this.findViewById(2131296515);
        this.editTextTelepon = (EditText)this.findViewById(2131296555);
        this.editTextTeleponAdd = (EditText)this.findViewById(2131296556);
        this.editTextPassword = (EditText)this.findViewById(2131296533);
        this.editTextPasswordAdmin = (EditText)this.findViewById(2131296537);
        this.editTextPasswordAdd = (EditText)this.findViewById(2131296536);
        this.buttonSimpan = (Button)this.findViewById(2131296393);
        this.buttonSimpanPassword = (Button)this.findViewById(2131296396);
        this.buttonSimpanAdd = (Button)this.findViewById(2131296394);
        this.buttonHapus = (Button)this.findViewById(2131296354);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.formValidation = new FormValidation((Context)this);
        Intent intent = this.getIntent();
        this.driverEdit = intent.getBooleanExtra("edit", false);
        if (this.driverEdit) {
            this.getSupportActionBar().setTitle((CharSequence)"Edit Driver");
            this.idDriver = intent.getStringExtra("iddriver");
            this.kodeDriver = intent.getStringExtra("kodedriver");
            this.namaDriver = intent.getStringExtra("nama");
            this.teleponDriver = intent.getStringExtra("telepon");
            this.editTextKode.setText((CharSequence)(this.kodeDriver + this.idDriver));
            this.editTextNama.setText((CharSequence)this.namaDriver);
            this.editTextTelepon.setText((CharSequence)this.teleponDriver);
            this.layoutAdd.setVisibility(8);
            this.layoutEdit.setVisibility(0);
        } else {
            this.getSupportActionBar().setTitle((CharSequence)"Driver Baru");
            this.layoutAdd.setVisibility(0);
            this.layoutEdit.setVisibility(8);
        }
        this.buttonSimpan.setOnClickListener(new View.OnClickListener((EditDriverActivity)this){
            final /* synthetic */ EditDriverActivity this$0;
            {
                this.this$0 = editDriverActivity;
            }

            public void onClick(View view) {
                this.this$0.formValidation.hideKeyboard(view);
                this.this$0.namaDriver = this.this$0.editTextNama.getText().toString();
                this.this$0.teleponDriver = this.this$0.editTextTelepon.getText().toString();
                if (FormValidation.isValidName(this.this$0.namaDriver)) {
                    this.this$0.editTextNama.setError(null);
                    if (FormValidation.isValidPhoneNumber(this.this$0.teleponDriver)) {
                        this.this$0.editTextTelepon.setError(null);
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();
                            ProgressDialog progressDialog;

                            /*
                             * Enabled aggressive block sorting
                             * Enabled unnecessary exception pruning
                             * Enabled aggressive exception aggregation
                             */
                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)EditDriverActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)EditDriverActivity.this.dataPref.getToken());
                                    if (EditDriverActivity.this.driverEdit) {
                                        hashMap.put((Object)"iddriver", (Object)EditDriverActivity.this.idDriver);
                                    } else {
                                        hashMap.put((Object)"password", (Object)EditDriverActivity.this.passwordDriver);
                                    }
                                    hashMap.put((Object)"nama", (Object)EditDriverActivity.this.namaDriver);
                                    hashMap.put((Object)"telepon", (Object)EditDriverActivity.this.teleponDriver);
                                    if (!EditDriverActivity.this.driverEdit) return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_driver", "POST", (HashMap<String, String>)hashMap);
                                    return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/edit_driver", "POST", (HashMap<String, String>)hashMap);
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
                                            EditDriverActivity.this.showToast.Toast(string);
                                            EditDriverActivity.this.setResult(-1);
                                            EditDriverActivity.this.finish();
                                            return;
                                        }
                                        if (n == 0) {
                                            EditDriverActivity.this.showToast.Toast(string);
                                            return;
                                        }
                                        break block8;
                                    }
                                    EditDriverActivity.this.showToast.ToastError();
                                }
                            }

                            /*
                             * Enabled aggressive block sorting
                             */
                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)EditDriverActivity.this);
                                if (EditDriverActivity.this.driverEdit) {
                                    this.progressDialog.setMessage((CharSequence)"Mengyimpan data.");
                                } else {
                                    this.progressDialog.setMessage((CharSequence)"Menambahkan driver.");
                                }
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                        return;
                    }
                    this.this$0.editTextTelepon.setError((CharSequence)this.this$0.error);
                    return;
                }
                this.this$0.editTextNama.setError((CharSequence)this.this$0.error);
            }
        });
        this.buttonSimpanAdd.setOnClickListener(new View.OnClickListener((EditDriverActivity)this){
            final /* synthetic */ EditDriverActivity this$0;
            {
                this.this$0 = editDriverActivity;
            }

            public void onClick(View view) {
                this.this$0.formValidation.hideKeyboard(view);
                this.this$0.namaDriver = this.this$0.editTextNamaAdd.getText().toString();
                this.this$0.teleponDriver = this.this$0.editTextTeleponAdd.getText().toString();
                this.this$0.passwordDriver = this.this$0.editTextPasswordAdd.getText().toString();
                if (FormValidation.isValidName(this.this$0.namaDriver)) {
                    this.this$0.editTextNamaAdd.setError(null);
                    if (FormValidation.isValidPhoneNumber(this.this$0.teleponDriver)) {
                        this.this$0.editTextTeleponAdd.setError(null);
                        if (FormValidation.isValidPassword(this.this$0.passwordDriver)) {
                            this.this$0.editTextPasswordAdd.setError(null);
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        this.this$0.editTextPasswordAdd.setError((CharSequence)this.this$0.error);
                        return;
                    }
                    this.this$0.editTextTeleponAdd.setError((CharSequence)this.this$0.error);
                    return;
                }
                this.this$0.editTextNamaAdd.setError((CharSequence)this.this$0.error);
            }
        });
        this.buttonSimpanPassword.setOnClickListener(new View.OnClickListener((EditDriverActivity)this){
            final /* synthetic */ EditDriverActivity this$0;
            {
                this.this$0 = editDriverActivity;
            }

            public void onClick(View view) {
                this.this$0.formValidation.hideKeyboard(view);
                this.this$0.passwordBaru = this.this$0.editTextPassword.getText().toString();
                this.this$0.passwordAdmin = this.this$0.editTextPasswordAdmin.getText().toString();
                if (FormValidation.isValidPassword(this.this$0.passwordBaru)) {
                    this.this$0.editTextPassword.setError(null);
                    if (FormValidation.isValidPassword(this.this$0.passwordAdmin)) {
                        this.this$0.editTextPasswordAdmin.setError(null);
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();
                            ProgressDialog progressDialog;

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)EditDriverActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)EditDriverActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"iddriver", (Object)EditDriverActivity.this.idDriver);
                                    hashMap.put((Object)"password", (Object)EditDriverActivity.this.passwordBaru);
                                    hashMap.put((Object)"password_admin", (Object)EditDriverActivity.this.passwordAdmin);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_driver_password", "POST", (HashMap<String, String>)hashMap);
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
                                            EditDriverActivity.this.showToast.Toast(string);
                                            EditDriverActivity.this.setResult(-1);
                                            EditDriverActivity.this.finish();
                                            return;
                                        }
                                        if (n == 0) {
                                            EditDriverActivity.this.showToast.Toast(string);
                                            return;
                                        }
                                        break block8;
                                    }
                                    EditDriverActivity.this.showToast.ToastError();
                                }
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)EditDriverActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Menyimpan data.");
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                        return;
                    }
                    this.this$0.editTextPasswordAdmin.setError((CharSequence)this.this$0.error);
                    return;
                }
                this.this$0.editTextPassword.setError((CharSequence)this.this$0.error);
            }
        });
        this.buttonHapus.setOnClickListener(new View.OnClickListener((EditDriverActivity)this){
            final /* synthetic */ EditDriverActivity this$0;
            {
                this.this$0 = editDriverActivity;
            }

            public void onClick(View view) {
                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog$Builder((Context)this.this$0).create();
                alertDialog.setTitle((CharSequence)"Hapus Driver");
                alertDialog.setMessage((CharSequence)("Kamu yakin ingin menghapus driver " + this.this$0.namaDriver + "?"));
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
                                    hashMap.put((Object)"email", (Object)EditDriverActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)EditDriverActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"iddriver", (Object)EditDriverActivity.this.idDriver);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_driver", "POST", (HashMap<String, String>)hashMap);
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
                                            EditDriverActivity.this.showToast.Toast(string);
                                            EditDriverActivity.this.setResult(-1);
                                            EditDriverActivity.this.finish();
                                            return;
                                        }
                                        if (n == 0) {
                                            EditDriverActivity.this.showToast.Toast(string);
                                            return;
                                        }
                                        break block8;
                                    }
                                    EditDriverActivity.this.showToast.ToastError();
                                }
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)EditDriverActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Menghapus kategori.");
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

}

