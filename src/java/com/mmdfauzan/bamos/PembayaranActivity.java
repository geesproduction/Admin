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
 *  android.text.Html
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.CheckBox
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
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.mmdfauzan.bamos.PembayaranActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class PembayaranActivity
extends AppCompatActivity {
    Button buttonSave;
    CheckBox checkBoxKodePembayaran;
    CheckBox checkBoxMenuKonfirmasi;
    CheckBox checkBoxMenuPembayaran;
    CheckBox checkBoxPembayaranDelivery;
    CheckBox checkBoxSaldo;
    DataPref dataPref;
    EditText editTextPembayaran;
    String editedPembayaran;
    String kodePembayaran;
    String menuKonfirmasi;
    String menuPembayaran;
    String pembayaranDelivery;
    String saldo;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427385);
        this.getSupportActionBar().setTitle((CharSequence)"Atur Pembayaran");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.editTextPembayaran = (EditText)this.findViewById(2131296538);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.checkBoxKodePembayaran = (CheckBox)this.findViewById(2131296426);
        this.checkBoxMenuPembayaran = (CheckBox)this.findViewById(2131296428);
        this.checkBoxMenuKonfirmasi = (CheckBox)this.findViewById(2131296427);
        this.checkBoxPembayaranDelivery = (CheckBox)this.findViewById(2131296432);
        this.checkBoxSaldo = (CheckBox)this.findViewById(2131296435);
        this.buttonSave.setOnClickListener(new View.OnClickListener((PembayaranActivity)this){
            final /* synthetic */ PembayaranActivity this$0;
            {
                this.this$0 = pembayaranActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                this.this$0.editedPembayaran = this.this$0.editTextPembayaran.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.editedPembayaran.length() <= 10) {
                    this.this$0.editTextPembayaran.setError((CharSequence)"Masukkan informasi pembayaran dengan valid");
                    return;
                }
                this.this$0.editTextPembayaran.setError(null);
                this.this$0.kodePembayaran = this.this$0.checkBoxKodePembayaran.isChecked() ? "1" : "0";
                this.this$0.pembayaranDelivery = this.this$0.checkBoxPembayaranDelivery.isChecked() ? "1" : "0";
                this.this$0.menuPembayaran = this.this$0.checkBoxMenuPembayaran.isChecked() ? "1" : "0";
                this.this$0.menuKonfirmasi = this.this$0.checkBoxMenuKonfirmasi.isChecked() ? "1" : "0";
                this.this$0.saldo = this.this$0.checkBoxSaldo.isChecked() ? "1" : "0";
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)PembayaranActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)PembayaranActivity.this.dataPref.getToken());
                            hashMap.put((Object)"pembayaran", (Object)PembayaranActivity.this.editedPembayaran);
                            hashMap.put((Object)"pembayaran_kode", (Object)PembayaranActivity.this.kodePembayaran);
                            hashMap.put((Object)"pembayaran_delivery", (Object)PembayaranActivity.this.pembayaranDelivery);
                            hashMap.put((Object)"menu_pembayaran", (Object)PembayaranActivity.this.menuPembayaran);
                            hashMap.put((Object)"menu_konfirmasi", (Object)PembayaranActivity.this.menuKonfirmasi);
                            hashMap.put((Object)"saldo", (Object)PembayaranActivity.this.saldo);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_payment", "POST", (HashMap<String, String>)hashMap);
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
                                    Toast.makeText((Context)PembayaranActivity.this, (CharSequence)string, (int)1).show();
                                    PembayaranActivity.this.dataPref.setSaldo(PembayaranActivity.this.saldo);
                                    PembayaranActivity.this.finish();
                                    return;
                                }
                                if (n == 0) {
                                    Toast.makeText((Context)PembayaranActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                break block8;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)PembayaranActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Menyimpan pembayaran.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)PembayaranActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)PembayaranActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_payment", "POST", (HashMap<String, String>)hashMap);
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
                block13 : {
                    String string2;
                    String string3;
                    String string4;
                    block15 : {
                        block14 : {
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
                                if (n != 1) break block13;
                                string4 = jSONObject.getString("pembayaran");
                                String string5 = jSONObject.getString("pembayaran_kode");
                                String string6 = jSONObject.getString("pembayaran_delivery");
                                String string7 = jSONObject.getString("menu_pembayaran");
                                string2 = jSONObject.getString("menu_konfirmasi");
                                string3 = jSONObject.getString("saldo");
                                if (string5.equals((Object)"1")) {
                                    PembayaranActivity.this.checkBoxKodePembayaran.setChecked(true);
                                } else {
                                    PembayaranActivity.this.checkBoxKodePembayaran.setChecked(false);
                                }
                                if (string6.equals((Object)"1")) {
                                    PembayaranActivity.this.checkBoxPembayaranDelivery.setChecked(true);
                                } else {
                                    PembayaranActivity.this.checkBoxPembayaranDelivery.setChecked(false);
                                }
                                if (!string7.equals((Object)"1")) break block14;
                                PembayaranActivity.this.checkBoxMenuPembayaran.setChecked(true);
                                break block15;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        PembayaranActivity.this.checkBoxMenuPembayaran.setChecked(false);
                    }
                    if (string2.equals((Object)"1")) {
                        PembayaranActivity.this.checkBoxMenuKonfirmasi.setChecked(true);
                    } else {
                        PembayaranActivity.this.checkBoxMenuKonfirmasi.setChecked(false);
                    }
                    if (string3.equals((Object)"1")) {
                        PembayaranActivity.this.checkBoxSaldo.setChecked(true);
                    } else {
                        PembayaranActivity.this.checkBoxSaldo.setChecked(false);
                    }
                    String string8 = Html.fromHtml((String)string4.replace((CharSequence)"\n", (CharSequence)"&lt;br/&gt;")).toString().replace((CharSequence)"<br/>", (CharSequence)"\n");
                    PembayaranActivity.this.editTextPembayaran.setText((CharSequence)string8);
                    return;
                }
                if (n != 0) return;
                Toast.makeText((Context)PembayaranActivity.this, (CharSequence)string, (int)1).show();
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)PembayaranActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
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

