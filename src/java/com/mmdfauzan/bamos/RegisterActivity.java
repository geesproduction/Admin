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
 *  android.text.TextUtils
 *  android.util.Patterns
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ProgressBar
 *  android.widget.Spinner
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
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
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.mmdfauzan.bamos.BikinFileActivity;
import com.mmdfauzan.bamos.RegisterActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import com.mmdfauzan.bamos.model.CityItem;
import com.mmdfauzan.bamos.model.ProvinceItem;
import com.mmdfauzan.bamos.model.SubdistrictItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity
extends AppCompatActivity {
    String DEFAULT_ID = "0";
    Button buttonRegister;
    ArrayList<CityItem> cityItems;
    DataPref dataPref;
    EditText editTextEmail;
    EditText editTextIdToko;
    EditText editTextKategoriToko;
    EditText editTextNama;
    EditText editTextNamaToko;
    EditText editTextPassword;
    EditText editTextTelepon;
    String email;
    boolean firstSelectKecamatan = true;
    boolean firstSelectKota = true;
    boolean firstSelectProvinsi = true;
    String idtoko;
    String kategori;
    String kecamatan;
    String kota;
    String nama;
    String namatoko;
    String password;
    ProgressBar progressBarKecamatan;
    ProgressBar progressBarKota;
    ProgressBar progressBarProvinsi;
    ArrayList<ProvinceItem> provinceItems;
    String provinsi;
    ShowToast showToast;
    Spinner spinnerKecamatan;
    Spinner spinnerKota;
    Spinner spinnerProvinsi;
    ArrayList<SubdistrictItem> subdistrictItems;
    String telepon;

    public static final boolean isValidEmail(CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427397);
        this.getSupportActionBar().setTitle((CharSequence)"Pendaftaran");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.editTextNama = (EditText)this.findViewById(2131296514);
        this.editTextEmail = (EditText)this.findViewById(2131296483);
        this.editTextPassword = (EditText)this.findViewById(2131296533);
        this.editTextIdToko = (EditText)this.findViewById(2131296499);
        this.editTextNamaToko = (EditText)this.findViewById(2131296519);
        this.editTextKategoriToko = (EditText)this.findViewById(2131296501);
        this.editTextTelepon = (EditText)this.findViewById(2131296555);
        this.spinnerProvinsi = (Spinner)this.findViewById(2131296829);
        this.spinnerKota = (Spinner)this.findViewById(2131296826);
        this.spinnerKecamatan = (Spinner)this.findViewById(2131296825);
        this.buttonRegister = (Button)this.findViewById(2131296387);
        this.progressBarProvinsi = (ProgressBar)this.findViewById(2131296760);
        this.progressBarKota = (ProgressBar)this.findViewById(2131296758);
        this.progressBarKecamatan = (ProgressBar)this.findViewById(2131296757);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_province", "POST", (HashMap<String, String>)hashMap);
                    if (jSONObject != null) {
                        return jSONObject;
                    }
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }

            /*
             * Exception decompiling
             */
            protected void onPostExecute(JSONObject var1) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[UNCONDITIONALDOLOOP]
                // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
                // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
                // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
                // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
                // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
                // org.benf.cfr.reader.entities.g.p(Method.java:396)
                // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
                // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
                // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
                // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
                // org.benf.cfr.reader.b.a(Driver.java:128)
                // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
                // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
                // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
                // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
                // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
                // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
                // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
                // java.lang.Thread.run(Thread.java:818)
                throw new IllegalStateException("Decompilation failed");
            }

            protected void onPreExecute() {
                RegisterActivity.this.progressBarProvinsi.setVisibility(0);
                RegisterActivity.this.buttonRegister.setVisibility(4);
            }
        }.execute((Object[])new String[0]);
        this.buttonRegister.setOnClickListener(new View.OnClickListener((RegisterActivity)this){
            final /* synthetic */ RegisterActivity this$0;
            {
                this.this$0 = registerActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                this.this$0.nama = this.this$0.editTextNama.getText().toString();
                this.this$0.email = this.this$0.editTextEmail.getText().toString();
                this.this$0.password = this.this$0.editTextPassword.getText().toString();
                this.this$0.idtoko = this.this$0.editTextIdToko.getText().toString();
                this.this$0.namatoko = this.this$0.editTextNamaToko.getText().toString();
                this.this$0.kategori = this.this$0.editTextKategoriToko.getText().toString();
                this.this$0.telepon = this.this$0.editTextTelepon.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.nama.length() <= 3) {
                    this.this$0.showToast.Toast("Mohon isikan data yang valid");
                    return;
                }
                if (!RegisterActivity.isValidEmail(this.this$0.email)) {
                    this.this$0.showToast.Toast("Mohon isikan data yang valid");
                    return;
                }
                if (this.this$0.telepon.length() > 8 && this.this$0.telepon.startsWith("62")) {
                    if (this.this$0.password.length() <= 5) {
                        this.this$0.showToast.Toast("Mohon isikan data yang valid");
                        return;
                    }
                    if (this.this$0.idtoko.length() <= 3) {
                        this.this$0.showToast.Toast("Mohon isikan data yang valid");
                        return;
                    }
                    char c = this.this$0.idtoko.charAt(0);
                    boolean bl = c >= '0' && c <= '9';
                    if (bl) {
                        this.this$0.showToast.Toast("Mohon isikan data yang valid");
                        return;
                    }
                    if (this.this$0.namatoko.length() <= 2) {
                        this.this$0.showToast.Toast("Mohon isikan data yang valid");
                        return;
                    }
                    if (!this.this$0.kecamatan.equals((Object)this.this$0.DEFAULT_ID)) {
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();
                            ProgressDialog progressDialog;

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)RegisterActivity.this.email);
                                    hashMap.put((Object)"password", (Object)RegisterActivity.this.password);
                                    hashMap.put((Object)"username", (Object)RegisterActivity.this.idtoko);
                                    hashMap.put((Object)"nama", (Object)RegisterActivity.this.nama);
                                    hashMap.put((Object)"namatoko", (Object)RegisterActivity.this.namatoko);
                                    hashMap.put((Object)"kategori", (Object)RegisterActivity.this.kategori);
                                    hashMap.put((Object)"subdistrict_id", (Object)RegisterActivity.this.kecamatan);
                                    hashMap.put((Object)"telepon", (Object)RegisterActivity.this.telepon);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/registration", "POST", (HashMap<String, String>)hashMap);
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
                                            Toast.makeText((Context)RegisterActivity.this, (CharSequence)string, (int)0).show();
                                            String string2 = jSONObject.getString("email");
                                            String string3 = jSONObject.getString("token");
                                            String string4 = jSONObject.getString("username");
                                            RegisterActivity.this.dataPref.setLogin(string2, string4, string3, true);
                                            Intent intent = new Intent((Context)RegisterActivity.this, BikinFileActivity.class);
                                            intent.setFlags(268435456);
                                            intent.addFlags(32768);
                                            intent.addFlags(32768);
                                            intent.addFlags(67108864);
                                            RegisterActivity.this.startActivity(intent);
                                            return;
                                        }
                                        if (n == 0) {
                                            Toast.makeText((Context)RegisterActivity.this, (CharSequence)string, (int)1).show();
                                            return;
                                        }
                                        break block8;
                                    }
                                    new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                }
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)RegisterActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Proses pendaftaran, mohon tunggu.");
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                    }
                    return;
                }
                this.this$0.showToast.Toast("Masukkan nomor telepon yang valid dan merupakan nomor Indonesia");
            }
        });
        this.spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((RegisterActivity)this){
            final /* synthetic */ RegisterActivity this$0;
            {
                this.this$0 = registerActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.provinsi = ((ProvinceItem)this.this$0.provinceItems.get(n)).getProvince_id();
                this.this$0.kecamatan = "0";
                this.this$0.spinnerKecamatan.setAdapter(null);
                if (!this.this$0.provinsi.equals((Object)this.this$0.DEFAULT_ID)) {
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"province", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_city", "GET", (HashMap<String, String>)hashMap);
                                if (jSONObject != null) {
                                    return jSONObject;
                                }
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            return null;
                        }

                        /*
                         * Exception decompiling
                         */
                        protected void onPostExecute(JSONObject var1) {
                            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[UNCONDITIONALDOLOOP]
                            // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
                            // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
                            // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
                            // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
                            // org.benf.cfr.reader.entities.g.p(Method.java:396)
                            // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
                            // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
                            // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
                            // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
                            // org.benf.cfr.reader.b.a(Driver.java:128)
                            // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
                            // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
                            // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
                            // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
                            // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
                            // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
                            // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
                            // java.lang.Thread.run(Thread.java:818)
                            throw new IllegalStateException("Decompilation failed");
                        }

                        protected void onPreExecute() {
                            RegisterActivity.this.progressBarKota.setVisibility(0);
                            RegisterActivity.this.buttonRegister.setVisibility(4);
                        }
                    };
                    Object[] arrobject = new String[]{this.this$0.provinsi};
                    asyncTask.execute(arrobject);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((RegisterActivity)this){
            final /* synthetic */ RegisterActivity this$0;
            {
                this.this$0 = registerActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.kota = ((CityItem)this.this$0.cityItems.get(n)).getCity_id();
                if (!this.this$0.kota.equals((Object)this.this$0.DEFAULT_ID)) {
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"city", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_subdistrict", "GET", (HashMap<String, String>)hashMap);
                                if (jSONObject != null) {
                                    return jSONObject;
                                }
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            return null;
                        }

                        /*
                         * Exception decompiling
                         */
                        protected void onPostExecute(JSONObject var1) {
                            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 5[UNCONDITIONALDOLOOP]
                            // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
                            // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
                            // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
                            // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
                            // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
                            // org.benf.cfr.reader.entities.g.p(Method.java:396)
                            // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
                            // org.benf.cfr.reader.entities.d.c(ClassFile.java:773)
                            // org.benf.cfr.reader.entities.d.e(ClassFile.java:870)
                            // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
                            // org.benf.cfr.reader.b.a(Driver.java:128)
                            // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
                            // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
                            // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
                            // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
                            // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
                            // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
                            // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
                            // java.lang.Thread.run(Thread.java:818)
                            throw new IllegalStateException("Decompilation failed");
                        }

                        protected void onPreExecute() {
                            RegisterActivity.this.progressBarKecamatan.setVisibility(0);
                            RegisterActivity.this.buttonRegister.setVisibility(4);
                        }
                    };
                    Object[] arrobject = new String[]{this.this$0.kota};
                    asyncTask.execute(arrobject);
                }
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((RegisterActivity)this){
            final /* synthetic */ RegisterActivity this$0;
            {
                this.this$0 = registerActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.kecamatan = ((SubdistrictItem)this.this$0.subdistrictItems.get(n)).getSubdistrict_id();
                if (this.this$0.kecamatan.equals((Object)this.this$0.DEFAULT_ID)) {
                    this.this$0.buttonRegister.setVisibility(4);
                    return;
                }
                this.this$0.buttonRegister.setVisibility(0);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
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

