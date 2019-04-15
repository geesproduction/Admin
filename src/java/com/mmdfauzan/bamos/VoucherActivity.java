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
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.Spinner
 *  android.widget.TextView
 *  android.widget.Toast
 *  android.widget.ToggleButton
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mmdfauzan.bamos.VoucherActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.VoucherItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class VoucherActivity
extends AppCompatActivity {
    String aktifVoucher;
    String besarVoucher;
    Button buttonDelete;
    Button buttonSave;
    DataPref dataPref;
    EditText editTextBesarVoucher;
    EditText editTextKodeVoucher;
    EditText editTextMaksimal;
    EditText editTextMinimalPembelian;
    boolean firstChange = true;
    String idVoucher;
    String jenisVoucher;
    String kodeVoucher;
    LinearLayout layoutAddVoucher;
    LinearLayout layoutBesarVoucher;
    LinearLayout layoutFormBesarVoucher;
    LinearLayout layoutFormMaksimal;
    LinearLayout layoutMaksimal;
    LinearLayout layoutVoucher;
    String maksimal;
    String minimal;
    int newVoucher = 0;
    Spinner spinnerJenisVoucher;
    TextView textViewBesarVoucher;
    TextView textViewJenisVoucher;
    TextView textViewKodeVoucher;
    TextView textViewMaksimal;
    TextView textViewMinimalPembelian;
    ToggleButton toggleButtonStatus;
    ArrayList<VoucherItem> voucherItems = new ArrayList();

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427407);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Atur Voucher");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.layoutVoucher = (LinearLayout)this.findViewById(2131296694);
        this.layoutAddVoucher = (LinearLayout)this.findViewById(2131296638);
        this.layoutBesarVoucher = (LinearLayout)this.findViewById(2131296641);
        this.layoutFormBesarVoucher = (LinearLayout)this.findViewById(2131296650);
        this.layoutFormMaksimal = (LinearLayout)this.findViewById(2131296651);
        this.layoutMaksimal = (LinearLayout)this.findViewById(2131296673);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonDelete = (Button)this.findViewById(2131296340);
        this.editTextKodeVoucher = (EditText)this.findViewById(2131296504);
        this.editTextBesarVoucher = (EditText)this.findViewById(2131296478);
        this.editTextMinimalPembelian = (EditText)this.findViewById(2131296513);
        this.editTextMaksimal = (EditText)this.findViewById(2131296511);
        this.toggleButtonStatus = (ToggleButton)this.findViewById(2131297152);
        this.spinnerJenisVoucher = (Spinner)this.findViewById(2131296823);
        this.textViewKodeVoucher = (TextView)this.findViewById(2131297078);
        this.textViewJenisVoucher = (TextView)this.findViewById(2131297066);
        this.textViewBesarVoucher = (TextView)this.findViewById(2131297053);
        this.textViewMinimalPembelian = (TextView)this.findViewById(2131297097);
        this.textViewMaksimal = (TextView)this.findViewById(2131297088);
        Intent intent = this.getIntent();
        this.newVoucher = intent.getIntExtra("newVoucher", 0);
        if (this.newVoucher == 0) {
            this.idVoucher = intent.getStringExtra("idvoucher");
        }
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)VoucherActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)VoucherActivity.this.dataPref.getToken());
                    hashMap.put((Object)"newVoucher", (Object)String.valueOf((int)VoucherActivity.this.newVoucher));
                    if (VoucherActivity.this.newVoucher == 0) {
                        hashMap.put((Object)"idvoucher", (Object)VoucherActivity.this.idVoucher);
                    }
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_voucher", "POST", (HashMap<String, String>)hashMap);
                    Log.d((String)"JSON result", (String)jSONObject.toString());
                    return jSONObject;
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                    return null;
                }
            }

            /*
             * Exception decompiling
             */
            protected void onPostExecute(JSONObject var1) {
                // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[TRYBLOCK]
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
                this.progressDialog = new ProgressDialog((Context)VoucherActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.toggleButtonStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((VoucherActivity)this){
            final /* synthetic */ VoucherActivity this$0;
            {
                this.this$0 = voucherActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (!this.this$0.firstChange) {
                    this.this$0.aktifVoucher = bl ? "1" : "0";
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)VoucherActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)VoucherActivity.this.dataPref.getToken());
                                hashMap.put((Object)"idvoucher", (Object)VoucherActivity.this.idVoucher);
                                hashMap.put((Object)"aktif", (Object)VoucherActivity.this.aktifVoucher);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_voucher", "POST", (HashMap<String, String>)hashMap);
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
                            if (this.progressDialog.isShowing()) {
                                this.progressDialog.dismiss();
                            }
                            if (jSONObject == null) {
                                new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                return;
                            }
                            try {
                                int n = jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                if (n != 1 && n == 0) {
                                    Toast.makeText((Context)VoucherActivity.this, (CharSequence)string, (int)1).show();
                                }
                                if (jSONObject.getString("aktif").equals((Object)"1")) {
                                    VoucherActivity.this.toggleButtonStatus.setChecked(true);
                                    return;
                                }
                                VoucherActivity.this.toggleButtonStatus.setChecked(false);
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)VoucherActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                }
            }
        });
        this.buttonDelete.setOnClickListener(new View.OnClickListener((VoucherActivity)this){
            final /* synthetic */ VoucherActivity this$0;
            {
                this.this$0 = voucherActivity;
            }

            public void onClick(View view) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)VoucherActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)VoucherActivity.this.dataPref.getToken());
                            hashMap.put((Object)"idvoucher", (Object)VoucherActivity.this.idVoucher);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_voucher", "POST", (HashMap<String, String>)hashMap);
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
                                    VoucherActivity.this.layoutAddVoucher.setVisibility(8);
                                    VoucherActivity.this.layoutVoucher.setVisibility(8);
                                    VoucherActivity.this.setResult(-1);
                                    VoucherActivity.this.finish();
                                    return;
                                }
                                if (n == 0) {
                                    Toast.makeText((Context)VoucherActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                break block8;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)VoucherActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Menghapus voucher.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        this.spinnerJenisVoucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((VoucherActivity)this){
            final /* synthetic */ VoucherActivity this$0;
            {
                this.this$0 = voucherActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                if (((VoucherItem)this.this$0.voucherItems.get(n)).getId().equals((Object)"3")) {
                    this.this$0.editTextBesarVoucher.setText((CharSequence)"0");
                    this.this$0.layoutFormBesarVoucher.setVisibility(8);
                    this.this$0.layoutFormMaksimal.setVisibility(0);
                } else if (((VoucherItem)this.this$0.voucherItems.get(n)).getId().equals((Object)"1")) {
                    this.this$0.layoutFormBesarVoucher.setVisibility(0);
                    this.this$0.layoutFormMaksimal.setVisibility(0);
                } else if (((VoucherItem)this.this$0.voucherItems.get(n)).getId().equals((Object)"2")) {
                    this.this$0.layoutFormBesarVoucher.setVisibility(0);
                    this.this$0.layoutFormMaksimal.setVisibility(8);
                }
                this.this$0.jenisVoucher = ((VoucherItem)this.this$0.voucherItems.get(n)).getId();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((VoucherActivity)this){
            final /* synthetic */ VoucherActivity this$0;
            {
                this.this$0 = voucherActivity;
            }

            public void onClick(View view) {
                this.this$0.kodeVoucher = this.this$0.editTextKodeVoucher.getText().toString();
                this.this$0.besarVoucher = this.this$0.editTextBesarVoucher.getText().toString();
                this.this$0.minimal = this.this$0.editTextMinimalPembelian.getText().toString();
                this.this$0.maksimal = this.this$0.editTextMaksimal.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.kodeVoucher.length() > 2) {
                    this.this$0.editTextKodeVoucher.setError(null);
                    if (this.this$0.minimal.length() > 0) {
                        this.this$0.editTextMinimalPembelian.setError(null);
                        if (this.this$0.jenisVoucher.equals((Object)"1") && !this.this$0.maksimal.equals((Object)"0") && !this.this$0.maksimal.equals((Object)"")) {
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    try {
                                        HashMap hashMap = new HashMap();
                                        hashMap.put((Object)"email", (Object)VoucherActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)VoucherActivity.this.dataPref.getToken());
                                        hashMap.put((Object)"kodevoucher", (Object)VoucherActivity.this.kodeVoucher);
                                        hashMap.put((Object)"jenisvoucher", (Object)VoucherActivity.this.jenisVoucher);
                                        hashMap.put((Object)"besarvoucher", (Object)VoucherActivity.this.besarVoucher);
                                        hashMap.put((Object)"minimal", (Object)VoucherActivity.this.minimal);
                                        hashMap.put((Object)"maksimal", (Object)VoucherActivity.this.maksimal);
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_voucher", "POST", (HashMap<String, String>)hashMap);
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
                                                Toast.makeText((Context)VoucherActivity.this, (CharSequence)string, (int)1).show();
                                                VoucherActivity.this.voucherItems = new ArrayList();
                                                VoucherActivity.this.setResult(-1);
                                                VoucherActivity.this.finish();
                                                return;
                                            }
                                            if (n == 0) {
                                                Toast.makeText((Context)VoucherActivity.this, (CharSequence)string, (int)1).show();
                                                return;
                                            }
                                            break block8;
                                        }
                                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)VoucherActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Menambahkan voucher.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                            return;
                        }
                        if (this.this$0.jenisVoucher.equals((Object)"3") && !this.this$0.maksimal.equals((Object)"0") && !this.this$0.maksimal.equals((Object)"")) {
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        if (this.this$0.jenisVoucher.equals((Object)"2")) {
                            this.this$0.maksimal = "0";
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        this.this$0.editTextMaksimal.setError((CharSequence)"Mohon isikan maksimal potongan yang valid");
                        return;
                    }
                    this.this$0.editTextMinimalPembelian.setError((CharSequence)"Mohon isikan minimal pembelian yang valid");
                    return;
                }
                this.this$0.editTextKodeVoucher.setError((CharSequence)"Mohon isikan kode voucher yang valid");
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

