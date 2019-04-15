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
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.ImageButton
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.Toast
 *  android.widget.ToggleButton
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  org.json.JSONArray
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mmdfauzan.bamos.TokoActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import com.mmdfauzan.bamos.model.KategoriItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TokoActivity
extends AppCompatActivity {
    String bbm;
    Button buttonSave;
    DataPref dataPref;
    EditText editTextBbm;
    EditText editTextEmail;
    EditText editTextHubungi;
    EditText editTextIdToko;
    EditText editTextLine;
    EditText editTextNama;
    EditText editTextNamaToko;
    EditText editTextSms;
    EditText editTextTelepon;
    EditText editTextTentangToko;
    EditText editTextWa;
    EditText editTextWelcome;
    String email;
    String hubungi;
    String idToko;
    ImageButton imageButtonMoreHubungi;
    ImageButton imageButtonMoreTentangToko;
    ImageButton imageButtonMoreWelcome;
    String kategori;
    ArrayList<KategoriItem> kategoriItems = new ArrayList();
    String kategoriList;
    String line;
    String metode;
    String nama;
    String namaToko;
    String produkPilihan;
    String produkPilihanId;
    String produkTerbaru;
    String produkTerlaris;
    String produkTerlarisId;
    String selectedJenis;
    int selectedProdukPilihan;
    int selectedProdukTerlaris;
    ShowToast showToast;
    String sms;
    Spinner spinnerJenis;
    Spinner spinnerProdukPilihan;
    Spinner spinnerProdukTerlaris;
    String telepon;
    String tentangToko;
    String textTentang;
    ToggleButton toggleButtonKategori;
    ToggleButton toggleButtonKategoriList;
    ToggleButton toggleButtonMetode;
    ToggleButton toggleButtonProdukPilihan;
    ToggleButton toggleButtonProdukTerbaru;
    ToggleButton toggleButtonProdukTerlaris;
    String wa;
    String welcome;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427404);
        this.getSupportActionBar().setTitle((CharSequence)"Atur Toko");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.editTextNamaToko = (EditText)this.findViewById(2131296519);
        this.editTextNama = (EditText)this.findViewById(2131296514);
        this.editTextEmail = (EditText)this.findViewById(2131296483);
        this.editTextIdToko = (EditText)this.findViewById(2131296499);
        this.editTextTentangToko = (EditText)this.findViewById(2131296557);
        this.editTextSms = (EditText)this.findViewById(2131296544);
        this.editTextBbm = (EditText)this.findViewById(2131296476);
        this.editTextWa = (EditText)this.findViewById(2131296558);
        this.editTextLine = (EditText)this.findViewById(2131296506);
        this.editTextHubungi = (EditText)this.findViewById(2131296497);
        this.editTextWelcome = (EditText)this.findViewById(2131296559);
        this.editTextTelepon = (EditText)this.findViewById(2131296555);
        this.toggleButtonProdukPilihan = (ToggleButton)this.findViewById(2131297149);
        this.toggleButtonProdukTerlaris = (ToggleButton)this.findViewById(2131297151);
        this.toggleButtonProdukTerbaru = (ToggleButton)this.findViewById(2131297150);
        this.toggleButtonKategori = (ToggleButton)this.findViewById(2131297146);
        this.toggleButtonKategoriList = (ToggleButton)this.findViewById(2131297147);
        this.toggleButtonMetode = (ToggleButton)this.findViewById(2131297148);
        this.spinnerProdukPilihan = (Spinner)this.findViewById(2131296827);
        this.spinnerProdukTerlaris = (Spinner)this.findViewById(2131296828);
        this.spinnerJenis = (Spinner)this.findViewById(2131296822);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.imageButtonMoreHubungi = (ImageButton)this.findViewById(2131296605);
        this.imageButtonMoreTentangToko = (ImageButton)this.findViewById(2131296606);
        this.imageButtonMoreWelcome = (ImageButton)this.findViewById(2131296607);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)TokoActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)TokoActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_store", "POST", (HashMap<String, String>)hashMap);
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
             * Lifted jumps to return sites
             */
            protected void onPostExecute(JSONObject jSONObject) {
                JSONObject jSONObject2;
                JSONArray jSONArray;
                block24 : {
                    int n;
                    String string;
                    block21 : {
                        block23 : {
                            block22 : {
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
                                    if (n != 1) break block21;
                                    jSONObject2 = jSONObject.getJSONArray("toko").getJSONObject(0);
                                    TokoActivity.this.editTextNamaToko.setText((CharSequence)jSONObject2.getString("namatoko"));
                                    TokoActivity.this.editTextIdToko.setText((CharSequence)jSONObject2.getString("username"));
                                    TokoActivity.this.editTextTentangToko.setText((CharSequence)jSONObject2.getString("tentang"));
                                    String string2 = Html.fromHtml((String)jSONObject2.getString("tentang").replace((CharSequence)"\n", (CharSequence)"&lt;br/&gt;")).toString().replace((CharSequence)"<br/>", (CharSequence)"\n");
                                    TokoActivity.this.editTextTentangToko.setText((CharSequence)string2);
                                    TokoActivity.this.editTextNama.setText((CharSequence)jSONObject2.getString("nama"));
                                    TokoActivity.this.editTextEmail.setText((CharSequence)jSONObject2.getString("email"));
                                    TokoActivity.this.editTextTelepon.setText((CharSequence)jSONObject2.getString("telepon"));
                                    TokoActivity.this.editTextSms.setText((CharSequence)jSONObject2.getString("sms"));
                                    TokoActivity.this.editTextBbm.setText((CharSequence)jSONObject2.getString("bbm"));
                                    TokoActivity.this.editTextWa.setText((CharSequence)jSONObject2.getString("wa"));
                                    TokoActivity.this.editTextLine.setText((CharSequence)jSONObject2.getString("line"));
                                    String string3 = Html.fromHtml((String)jSONObject2.getString("hubungi").replace((CharSequence)"\n", (CharSequence)"&lt;br/&gt;")).toString().replace((CharSequence)"<br/>", (CharSequence)"\n");
                                    TokoActivity.this.editTextHubungi.setText((CharSequence)string3);
                                    String string4 = Html.fromHtml((String)jSONObject2.getString("welcome_message").replace((CharSequence)"\n", (CharSequence)"&lt;br/&gt;")).toString().replace((CharSequence)"<br/>", (CharSequence)"\n");
                                    TokoActivity.this.editTextWelcome.setText((CharSequence)string4);
                                    int n2 = jSONObject2.getInt("jenis");
                                    TokoActivity.this.spinnerJenis.setSelection(n2);
                                    if (jSONObject2.getString("kategori_pilihan").equals((Object)"1")) {
                                        TokoActivity.this.toggleButtonProdukPilihan.setChecked(true);
                                    } else {
                                        TokoActivity.this.toggleButtonProdukPilihan.setChecked(false);
                                    }
                                    if (jSONObject2.getString("kategori_terlaris").equals((Object)"1")) {
                                        TokoActivity.this.toggleButtonProdukTerlaris.setChecked(true);
                                    } else {
                                        TokoActivity.this.toggleButtonProdukTerlaris.setChecked(false);
                                    }
                                    if (!jSONObject2.getString("kategori_terbaru").equals((Object)"1")) break block22;
                                    TokoActivity.this.toggleButtonProdukTerbaru.setChecked(true);
                                    break block23;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }
                            TokoActivity.this.toggleButtonProdukTerbaru.setChecked(false);
                        }
                        if (jSONObject2.getString("menu_kategori").equals((Object)"1")) {
                            TokoActivity.this.toggleButtonKategori.setChecked(true);
                        } else {
                            TokoActivity.this.toggleButtonKategori.setChecked(false);
                        }
                        if (jSONObject2.getString("menu_kategori_list").equals((Object)"1")) {
                            TokoActivity.this.toggleButtonKategoriList.setChecked(true);
                        } else {
                            TokoActivity.this.toggleButtonKategoriList.setChecked(false);
                        }
                        if (jSONObject2.getString("metode").equals((Object)"2")) {
                            TokoActivity.this.toggleButtonMetode.setChecked(true);
                        } else {
                            TokoActivity.this.toggleButtonMetode.setChecked(false);
                        }
                        jSONArray = jSONObject.getJSONArray("kategori");
                        break block24;
                    }
                    if (n != 0) return;
                    Toast.makeText((Context)TokoActivity.this, (CharSequence)string, (int)1).show();
                    return;
                }
                for (int i = 0; i < jSONArray.length(); ++i) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    KategoriItem kategoriItem = new KategoriItem();
                    kategoriItem.setIdKategori(jSONObject3.getString("idkategori"));
                    kategoriItem.setNamaKategori(jSONObject3.getString("nama"));
                    TokoActivity.this.kategoriItems.add((Object)kategoriItem);
                }
                ArrayList arrayList = new ArrayList();
                int n = 0;
                do {
                    if (n >= TokoActivity.this.kategoriItems.size()) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter((Context)TokoActivity.this, 17367048, (List)arrayList);
                        arrayAdapter.setDropDownViewResource(17367049);
                        TokoActivity.this.spinnerProdukPilihan.setAdapter((SpinnerAdapter)arrayAdapter);
                        TokoActivity.this.spinnerProdukPilihan.setSelection(TokoActivity.this.selectedProdukPilihan);
                        TokoActivity.this.spinnerProdukTerlaris.setAdapter((SpinnerAdapter)arrayAdapter);
                        TokoActivity.this.spinnerProdukTerlaris.setSelection(TokoActivity.this.selectedProdukTerlaris);
                        if (jSONArray.length() >= 1) return;
                        TokoActivity.this.produkPilihanId = "0";
                        TokoActivity.this.produkTerlarisId = "0";
                        return;
                    }
                    arrayList.add((Object)((KategoriItem)TokoActivity.this.kategoriItems.get(n)).getNamaKategori());
                    if (((KategoriItem)TokoActivity.this.kategoriItems.get(n)).getIdKategori().equals((Object)jSONObject2.getString("kategori_pilihan_idkategori"))) {
                        TokoActivity.this.selectedProdukPilihan = n;
                    }
                    if (((KategoriItem)TokoActivity.this.kategoriItems.get(n)).getIdKategori().equals((Object)jSONObject2.getString("kategori_terlaris_idkategori"))) {
                        TokoActivity.this.selectedProdukTerlaris = n;
                    }
                    ++n;
                } while (true);
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)TokoActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.spinnerProdukPilihan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.produkPilihanId = ((KategoriItem)this.this$0.kategoriItems.get(n)).getIdKategori();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerProdukTerlaris.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.produkTerlarisId = ((KategoriItem)this.this$0.kategoriItems.get(n)).getIdKategori();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerJenis.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                if (n == 1) {
                    this.this$0.selectedJenis = "1";
                    return;
                }
                this.this$0.selectedJenis = "0";
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.toggleButtonProdukPilihan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    this.this$0.produkPilihan = "1";
                    return;
                }
                this.this$0.produkPilihan = "0";
            }
        });
        this.toggleButtonProdukTerlaris.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    this.this$0.produkTerlaris = "1";
                    return;
                }
                this.this$0.produkTerlaris = "0";
            }
        });
        this.toggleButtonProdukTerbaru.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    this.this$0.produkTerbaru = "1";
                    return;
                }
                this.this$0.produkTerbaru = "0";
            }
        });
        this.toggleButtonMetode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    this.this$0.metode = "2";
                    return;
                }
                this.this$0.metode = "1";
            }
        });
        this.imageButtonMoreTentangToko.setOnClickListener(new View.OnClickListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onClick(View view) {
                if (this.this$0.editTextTentangToko.getVisibility() == 0) {
                    this.this$0.imageButtonMoreTentangToko.setImageResource(2131230872);
                    this.this$0.editTextTentangToko.setVisibility(8);
                    return;
                }
                this.this$0.imageButtonMoreTentangToko.setImageResource(2131230871);
                this.this$0.editTextTentangToko.setVisibility(0);
            }
        });
        this.imageButtonMoreWelcome.setOnClickListener(new View.OnClickListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onClick(View view) {
                if (this.this$0.editTextWelcome.getVisibility() == 0) {
                    this.this$0.imageButtonMoreWelcome.setImageResource(2131230872);
                    this.this$0.editTextWelcome.setVisibility(8);
                    return;
                }
                this.this$0.imageButtonMoreWelcome.setImageResource(2131230871);
                this.this$0.editTextWelcome.setVisibility(0);
            }
        });
        this.imageButtonMoreHubungi.setOnClickListener(new View.OnClickListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            public void onClick(View view) {
                if (this.this$0.editTextHubungi.getVisibility() == 0) {
                    this.this$0.imageButtonMoreHubungi.setImageResource(2131230872);
                    this.this$0.editTextHubungi.setVisibility(8);
                    return;
                }
                this.this$0.imageButtonMoreHubungi.setImageResource(2131230871);
                this.this$0.editTextHubungi.setVisibility(0);
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((TokoActivity)this){
            final /* synthetic */ TokoActivity this$0;
            {
                this.this$0 = tokoActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                this.this$0.namaToko = this.this$0.editTextNamaToko.getText().toString();
                this.this$0.tentangToko = this.this$0.editTextTentangToko.getText().toString();
                this.this$0.sms = this.this$0.editTextSms.getText().toString();
                this.this$0.wa = this.this$0.editTextWa.getText().toString();
                this.this$0.line = this.this$0.editTextLine.getText().toString();
                this.this$0.bbm = this.this$0.editTextBbm.getText().toString();
                this.this$0.nama = this.this$0.editTextNama.getText().toString();
                this.this$0.hubungi = this.this$0.editTextHubungi.getText().toString();
                this.this$0.welcome = this.this$0.editTextWelcome.getText().toString();
                this.this$0.telepon = this.this$0.editTextTelepon.getText().toString();
                this.this$0.produkPilihan = this.this$0.toggleButtonProdukPilihan.isChecked() ? "1" : "0";
                this.this$0.produkTerlaris = this.this$0.toggleButtonProdukTerlaris.isChecked() ? "1" : "0";
                this.this$0.produkTerbaru = this.this$0.toggleButtonProdukTerbaru.isChecked() ? "1" : "0";
                this.this$0.kategori = this.this$0.toggleButtonKategori.isChecked() ? "1" : "0";
                this.this$0.kategoriList = this.this$0.toggleButtonKategoriList.isChecked() ? "1" : "0";
                this.this$0.metode = this.this$0.toggleButtonMetode.isChecked() ? "2" : "1";
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.nama.length() <= 2) {
                    this.this$0.showToast.Toast("Mohon isikan nama yang valid");
                    return;
                }
                if (this.this$0.telepon.length() > 8 && this.this$0.telepon.startsWith("62")) {
                    if (this.this$0.namaToko.length() <= 2) {
                        this.this$0.showToast.Toast("Mohon isikan nama toko yang valid");
                        return;
                    }
                    if (this.this$0.tentangToko.length() <= 10) {
                        this.this$0.showToast.Toast("Mohon isikan tentang toko yang valid");
                        return;
                    }
                    if (this.this$0.sms.length() <= 0 && this.this$0.wa.length() <= 0 && this.this$0.line.length() <= 0 && this.this$0.bbm.length() <= 0) {
                        this.this$0.showToast.Toast("Mohon isikan minimal salah satu kontak");
                        return;
                    }
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)TokoActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)TokoActivity.this.dataPref.getToken());
                                hashMap.put((Object)"nama", (Object)TokoActivity.this.nama);
                                hashMap.put((Object)"telepon", (Object)TokoActivity.this.telepon);
                                hashMap.put((Object)"namatoko", (Object)TokoActivity.this.namaToko);
                                hashMap.put((Object)"tentang", (Object)TokoActivity.this.tentangToko);
                                hashMap.put((Object)"welcome_message", (Object)TokoActivity.this.welcome);
                                hashMap.put((Object)"hubungi", (Object)TokoActivity.this.hubungi);
                                hashMap.put((Object)"sms", (Object)TokoActivity.this.sms);
                                hashMap.put((Object)"wa", (Object)TokoActivity.this.wa);
                                hashMap.put((Object)"bbm", (Object)TokoActivity.this.bbm);
                                hashMap.put((Object)"line", (Object)TokoActivity.this.line);
                                hashMap.put((Object)"kategori_pilihan", (Object)TokoActivity.this.produkPilihan);
                                hashMap.put((Object)"kategori_pilihan_idkategori", (Object)TokoActivity.this.produkPilihanId);
                                hashMap.put((Object)"kategori_terlaris", (Object)TokoActivity.this.produkTerlaris);
                                hashMap.put((Object)"kategori_terlaris_idkategori", (Object)TokoActivity.this.produkTerlarisId);
                                hashMap.put((Object)"kategori_terbaru", (Object)TokoActivity.this.produkTerbaru);
                                hashMap.put((Object)"menu_kategori", (Object)TokoActivity.this.kategori);
                                hashMap.put((Object)"menu_kategori_list", (Object)TokoActivity.this.kategoriList);
                                hashMap.put((Object)"metode", (Object)TokoActivity.this.metode);
                                hashMap.put((Object)"jenis", (Object)TokoActivity.this.selectedJenis);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_store", "POST", (HashMap<String, String>)hashMap);
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
                                        Toast.makeText((Context)TokoActivity.this, (CharSequence)string, (int)1).show();
                                        TokoActivity.this.finish();
                                        return;
                                    }
                                    if (n == 0) {
                                        Toast.makeText((Context)TokoActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    break block8;
                                }
                                new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            }
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)TokoActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Menyimpan pengaturan toko.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                    return;
                }
                this.this$0.showToast.Toast("Pastikan mengisikan nomor telepon yang valid dan merupakan nomor Indonesia");
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

