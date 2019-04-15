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
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.EditText
 *  android.widget.ProgressBar
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.Toast
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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.mmdfauzan.bamos.PengirimanActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import com.mmdfauzan.bamos.model.CityItem;
import com.mmdfauzan.bamos.model.ProvinceItem;
import com.mmdfauzan.bamos.model.SubdistrictItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PengirimanActivity
extends AppCompatActivity {
    String biayaDelivery;
    String biayaDeliveryMinimal;
    Button buttonLokasi;
    Button buttonSave;
    CheckBox checkBoxCahaya;
    CheckBox checkBoxCod;
    CheckBox checkBoxDelivery;
    CheckBox checkBoxDropship;
    CheckBox checkBoxEkonomis;
    CheckBox checkBoxEkspres;
    CheckBox checkBoxEsl;
    CheckBox checkBoxIndah;
    CheckBox checkBoxJne;
    CheckBox checkBoxJnt;
    CheckBox checkBoxMenuPengiriman;
    CheckBox checkBoxPahala;
    CheckBox checkBoxPandu;
    CheckBox checkBoxPos;
    CheckBox checkBoxRpx;
    CheckBox checkBoxSicepat;
    CheckBox checkBoxTiki;
    CheckBox checkBoxWahana;
    ArrayList<CityItem> cityItems;
    DataPref dataPref;
    EditText editTextBiayaDelivery;
    EditText editTextBiayaDeliveryMinimal;
    EditText editTextJarak;
    EditText editTextOngkirAdmin;
    EditText editTextOngkirCod;
    boolean firstSelectKecamatan = true;
    boolean firstSelectKota = true;
    boolean firstSelectProvinsi = true;
    String jarak;
    String kecamatan;
    String kota;
    String latitude;
    String lokasi;
    String longitude;
    String ongkirAdmin;
    String ongkirCod;
    ProgressBar progressBarKecamatan;
    ProgressBar progressBarKota;
    ProgressBar progressBarProvinsi;
    ArrayList<ProvinceItem> provinceItems;
    String provinsi;
    String selectedCahaya;
    String selectedCod;
    String selectedDelivery;
    String selectedDropship;
    String selectedEkonomis;
    String selectedEkspres;
    String selectedEsl;
    String selectedIndah;
    String selectedJne;
    String selectedJnt;
    String selectedKecamatan;
    String selectedKota;
    String selectedMenuPengiriman;
    String selectedNamaKecamatan;
    String selectedNamaKota;
    String selectedNamaProvinsi;
    String selectedPahala;
    String selectedPandu;
    String selectedPos;
    String selectedProvinsi;
    String selectedRpx;
    String selectedSicepat;
    String selectedTiki;
    String selectedWahana;
    int selectionKecamatan;
    int selectionKota;
    int selectionProvinsi;
    ShowToast showToast;
    Spinner spinnerKecamatan;
    Spinner spinnerKota;
    Spinner spinnerProvinsi;
    ArrayList<SubdistrictItem> subdistrictItems;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 4 && n2 == -1) {
            this.latitude = intent.getStringExtra("latitude");
            this.longitude = intent.getStringExtra("longitude");
            this.lokasi = intent.getStringExtra("lokasi");
            this.buttonLokasi.setText((CharSequence)this.lokasi);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427389);
        this.getSupportActionBar().setTitle((CharSequence)"Atur Pengiriman");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.spinnerProvinsi = (Spinner)this.findViewById(2131296829);
        this.spinnerKota = (Spinner)this.findViewById(2131296826);
        this.spinnerKecamatan = (Spinner)this.findViewById(2131296825);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonLokasi = (Button)this.findViewById(2131296368);
        this.buttonSave.setVisibility(4);
        this.checkBoxJne = (CheckBox)this.findViewById(2131296424);
        this.checkBoxTiki = (CheckBox)this.findViewById(2131296437);
        this.checkBoxPos = (CheckBox)this.findViewById(2131296433);
        this.checkBoxWahana = (CheckBox)this.findViewById(2131296438);
        this.checkBoxJnt = (CheckBox)this.findViewById(2131296425);
        this.checkBoxSicepat = (CheckBox)this.findViewById(2131296436);
        this.checkBoxEsl = (CheckBox)this.findViewById(2131296422);
        this.checkBoxRpx = (CheckBox)this.findViewById(2131296434);
        this.checkBoxPandu = (CheckBox)this.findViewById(2131296431);
        this.checkBoxPahala = (CheckBox)this.findViewById(2131296430);
        this.checkBoxCahaya = (CheckBox)this.findViewById(2131296416);
        this.checkBoxIndah = (CheckBox)this.findViewById(2131296423);
        this.checkBoxEkspres = (CheckBox)this.findViewById(2131296421);
        this.checkBoxEkonomis = (CheckBox)this.findViewById(2131296420);
        this.checkBoxCod = (CheckBox)this.findViewById(2131296417);
        this.checkBoxDropship = (CheckBox)this.findViewById(2131296419);
        this.checkBoxDelivery = (CheckBox)this.findViewById(2131296418);
        this.checkBoxMenuPengiriman = (CheckBox)this.findViewById(2131296429);
        this.progressBarProvinsi = (ProgressBar)this.findViewById(2131296760);
        this.progressBarKota = (ProgressBar)this.findViewById(2131296758);
        this.progressBarKecamatan = (ProgressBar)this.findViewById(2131296757);
        this.editTextOngkirAdmin = (EditText)this.findViewById(2131296531);
        this.editTextOngkirCod = (EditText)this.findViewById(2131296532);
        this.editTextJarak = (EditText)this.findViewById(2131296500);
        this.editTextBiayaDelivery = (EditText)this.findViewById(2131296479);
        this.editTextBiayaDeliveryMinimal = (EditText)this.findViewById(2131296480);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)PengirimanActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)PengirimanActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_shipment", "POST", (HashMap<String, String>)hashMap);
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
                block41 : {
                    JSONObject jSONObject2;
                    block43 : {
                        block42 : {
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
                                if (n != 1) break block41;
                                jSONObject2 = jSONObject.getJSONArray("pengiriman").getJSONObject(0);
                                if (jSONObject2.getString("jne").equals((Object)"1")) {
                                    PengirimanActivity.this.checkBoxJne.setChecked(true);
                                } else {
                                    PengirimanActivity.this.checkBoxJne.setChecked(false);
                                }
                                if (jSONObject2.getString("tiki").equals((Object)"1")) {
                                    PengirimanActivity.this.checkBoxTiki.setChecked(true);
                                } else {
                                    PengirimanActivity.this.checkBoxTiki.setChecked(false);
                                }
                                if (!jSONObject2.getString("pos").equals((Object)"1")) break block42;
                                PengirimanActivity.this.checkBoxPos.setChecked(true);
                                break block43;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        PengirimanActivity.this.checkBoxPos.setChecked(false);
                    }
                    if (jSONObject2.getString("jnt").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxJnt.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxJnt.setChecked(false);
                    }
                    if (jSONObject2.getString("wahana").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxWahana.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxWahana.setChecked(false);
                    }
                    if (jSONObject2.getString("sicepat").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxSicepat.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxSicepat.setChecked(false);
                    }
                    if (jSONObject2.getString("esl").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxEsl.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxEsl.setChecked(false);
                    }
                    if (jSONObject2.getString("rpx").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxRpx.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxRpx.setChecked(false);
                    }
                    if (jSONObject2.getString("pandu").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxPandu.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxPandu.setChecked(false);
                    }
                    if (jSONObject2.getString("pahala").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxPahala.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxPahala.setChecked(false);
                    }
                    if (jSONObject2.getString("cahaya").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxCahaya.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxCahaya.setChecked(false);
                    }
                    if (jSONObject2.getString("indah").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxIndah.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxIndah.setChecked(false);
                    }
                    if (jSONObject2.getString("ekspres").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxEkspres.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxEkspres.setChecked(false);
                    }
                    if (jSONObject2.getString("ekonomis").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxEkonomis.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxEkonomis.setChecked(false);
                    }
                    if (jSONObject2.getString("cod").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxCod.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxCod.setChecked(false);
                    }
                    if (jSONObject2.getString("dropship").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxDropship.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxDropship.setChecked(false);
                    }
                    if (jSONObject2.getString("delivery").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxDelivery.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxDelivery.setChecked(false);
                    }
                    if (jSONObject2.getString("menu_pengiriman").equals((Object)"1")) {
                        PengirimanActivity.this.checkBoxMenuPengiriman.setChecked(true);
                    } else {
                        PengirimanActivity.this.checkBoxMenuPengiriman.setChecked(false);
                    }
                    PengirimanActivity.this.editTextOngkirAdmin.setText((CharSequence)jSONObject2.getString("ongkir_admin"));
                    PengirimanActivity.this.editTextOngkirCod.setText((CharSequence)jSONObject2.getString("ongkir_cod"));
                    PengirimanActivity.this.editTextJarak.setText((CharSequence)jSONObject2.getString("radius"));
                    PengirimanActivity.this.editTextBiayaDelivery.setText((CharSequence)jSONObject2.getString("biaya_delivery"));
                    PengirimanActivity.this.editTextBiayaDeliveryMinimal.setText((CharSequence)jSONObject2.getString("biaya_delivery_minimal"));
                    PengirimanActivity.this.latitude = jSONObject2.getString("lokasi_lat");
                    PengirimanActivity.this.longitude = jSONObject2.getString("lokasi_lon");
                    PengirimanActivity.this.lokasi = jSONObject2.getString("lokasi");
                    if (!(PengirimanActivity.this.lokasi.equals((Object)"") || PengirimanActivity.this.lokasi.equals((Object)" ") || PengirimanActivity.this.lokasi.equals((Object)"null"))) {
                        PengirimanActivity.this.buttonLokasi.setText((CharSequence)PengirimanActivity.this.lokasi);
                    } else {
                        PengirimanActivity.this.buttonLokasi.setText((CharSequence)"Lokasi belum ditentukan");
                    }
                    JSONObject jSONObject3 = jSONObject.getJSONArray("origin").getJSONObject(0);
                    PengirimanActivity.this.provinsi = jSONObject3.getString("province_id");
                    PengirimanActivity.this.kota = jSONObject3.getString("city_id");
                    PengirimanActivity.this.kecamatan = jSONObject3.getString("subdistrict_id");
                    AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
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
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        protected void onPostExecute(JSONObject jSONObject) {
                            int n;
                            ArrayList arrayList;
                            PengirimanActivity.this.progressBarProvinsi.setVisibility(4);
                            if (jSONObject == null) {
                                PengirimanActivity.this.showToast.ToastError();
                                return;
                            }
                            try {
                                PengirimanActivity.this.provinceItems = new ArrayList();
                                JSONArray jSONArray = jSONObject.getJSONObject("rajaongkir").getJSONArray("results");
                                for (int i = 0; i < jSONArray.length(); ++i) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    ProvinceItem provinceItem = new ProvinceItem();
                                    provinceItem.setProvince_id(jSONObject2.getString("province_id"));
                                    provinceItem.setProvince(jSONObject2.getString("province"));
                                    PengirimanActivity.this.provinceItems.add((Object)provinceItem);
                                }
                                arrayList = new ArrayList();
                                n = 0;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                            do {
                                if (n < PengirimanActivity.this.provinceItems.size()) {
                                    arrayList.add((Object)((ProvinceItem)PengirimanActivity.this.provinceItems.get(n)).getProvince());
                                    if (((ProvinceItem)PengirimanActivity.this.provinceItems.get(n)).getProvince_id().equals((Object)PengirimanActivity.this.provinsi)) {
                                        PengirimanActivity.this.selectionProvinsi = n;
                                    }
                                } else {
                                    ArrayAdapter arrayAdapter = new ArrayAdapter((Context)PengirimanActivity.this, 17367048, (List)arrayList);
                                    arrayAdapter.setDropDownViewResource(17367049);
                                    PengirimanActivity.this.spinnerProvinsi.setAdapter((SpinnerAdapter)arrayAdapter);
                                    if (PengirimanActivity.this.firstSelectProvinsi) {
                                        PengirimanActivity.this.spinnerProvinsi.setSelection(PengirimanActivity.this.selectionProvinsi);
                                    }
                                    PengirimanActivity.this.firstSelectProvinsi = false;
                                    return;
                                }
                                ++n;
                            } while (true);
                        }

                        protected void onPreExecute() {
                            PengirimanActivity.this.progressBarProvinsi.setVisibility(0);
                            PengirimanActivity.this.buttonSave.setVisibility(4);
                        }
                    };
                    Object[] arrobject = new String[]{PengirimanActivity.this.provinsi};
                    asyncTask.execute(arrobject);
                    return;
                }
                if (n != 0) return;
                Toast.makeText((Context)PengirimanActivity.this, (CharSequence)string, (int)1).show();
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)PengirimanActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.spinnerProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((PengirimanActivity)this){
            final /* synthetic */ PengirimanActivity this$0;
            {
                this.this$0 = pengirimanActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
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
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    protected void onPostExecute(JSONObject jSONObject) {
                        int n;
                        ArrayList arrayList;
                        PengirimanActivity.this.progressBarKota.setVisibility(4);
                        if (jSONObject == null) {
                            PengirimanActivity.this.showToast.ToastError();
                            return;
                        }
                        try {
                            PengirimanActivity.this.cityItems = new ArrayList();
                            JSONArray jSONArray = jSONObject.getJSONObject("rajaongkir").getJSONArray("results");
                            for (int i = 0; i < jSONArray.length(); ++i) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                CityItem cityItem = new CityItem();
                                cityItem.setCity_id(jSONObject2.getString("city_id"));
                                cityItem.setCity_name(jSONObject2.getString("city_name"));
                                cityItem.setType(jSONObject2.getString("type"));
                                PengirimanActivity.this.cityItems.add((Object)cityItem);
                            }
                            arrayList = new ArrayList();
                            n = 0;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                        do {
                            if (n < PengirimanActivity.this.cityItems.size()) {
                                arrayList.add((Object)(((CityItem)PengirimanActivity.this.cityItems.get(n)).getCity_name() + " (" + ((CityItem)PengirimanActivity.this.cityItems.get(n)).getType() + ")"));
                                if (((CityItem)PengirimanActivity.this.cityItems.get(n)).getCity_id().equals((Object)PengirimanActivity.this.kota)) {
                                    PengirimanActivity.this.selectionKota = n;
                                }
                            } else {
                                ArrayAdapter arrayAdapter = new ArrayAdapter((Context)PengirimanActivity.this, 17367048, (List)arrayList);
                                arrayAdapter.setDropDownViewResource(17367049);
                                PengirimanActivity.this.spinnerKota.setAdapter((SpinnerAdapter)arrayAdapter);
                                if (PengirimanActivity.this.firstSelectKota) {
                                    PengirimanActivity.this.spinnerKota.setSelection(PengirimanActivity.this.selectionKota);
                                }
                                PengirimanActivity.this.firstSelectKota = false;
                                return;
                            }
                            ++n;
                        } while (true);
                    }

                    protected void onPreExecute() {
                        PengirimanActivity.this.progressBarKota.setVisibility(0);
                        PengirimanActivity.this.buttonSave.setVisibility(4);
                    }
                };
                Object[] arrobject = new String[]{((ProvinceItem)this.this$0.provinceItems.get(n)).getProvince_id()};
                asyncTask.execute(arrobject);
                this.this$0.selectedProvinsi = ((ProvinceItem)this.this$0.provinceItems.get(n)).getProvince_id();
                this.this$0.selectedNamaProvinsi = ((ProvinceItem)this.this$0.provinceItems.get(n)).getProvince();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerKota.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((PengirimanActivity)this){
            final /* synthetic */ PengirimanActivity this$0;
            {
                this.this$0 = pengirimanActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
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
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     */
                    protected void onPostExecute(JSONObject jSONObject) {
                        int n;
                        ArrayList arrayList;
                        PengirimanActivity.this.progressBarKecamatan.setVisibility(4);
                        if (jSONObject == null) {
                            PengirimanActivity.this.showToast.ToastError();
                            return;
                        }
                        try {
                            PengirimanActivity.this.subdistrictItems = new ArrayList();
                            JSONArray jSONArray = jSONObject.getJSONObject("rajaongkir").getJSONArray("results");
                            for (int i = 0; i < jSONArray.length(); ++i) {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                SubdistrictItem subdistrictItem = new SubdistrictItem();
                                subdistrictItem.setSubdistrict_id(jSONObject2.getString("subdistrict_id"));
                                subdistrictItem.setSubdistrict_name(jSONObject2.getString("subdistrict_name"));
                                subdistrictItem.setType(jSONObject2.getString("type"));
                                PengirimanActivity.this.subdistrictItems.add((Object)subdistrictItem);
                            }
                            arrayList = new ArrayList();
                            n = 0;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                        do {
                            if (n < PengirimanActivity.this.subdistrictItems.size()) {
                                arrayList.add((Object)((SubdistrictItem)PengirimanActivity.this.subdistrictItems.get(n)).getSubdistrict_name());
                                if (((SubdistrictItem)PengirimanActivity.this.subdistrictItems.get(n)).getSubdistrict_id().equals((Object)PengirimanActivity.this.kecamatan)) {
                                    PengirimanActivity.this.selectionKecamatan = n;
                                }
                            } else {
                                ArrayAdapter arrayAdapter = new ArrayAdapter((Context)PengirimanActivity.this, 17367048, (List)arrayList);
                                arrayAdapter.setDropDownViewResource(17367049);
                                PengirimanActivity.this.spinnerKecamatan.setAdapter((SpinnerAdapter)arrayAdapter);
                                if (PengirimanActivity.this.firstSelectKecamatan) {
                                    PengirimanActivity.this.spinnerKecamatan.setSelection(PengirimanActivity.this.selectionKecamatan);
                                }
                                PengirimanActivity.this.firstSelectKecamatan = false;
                                PengirimanActivity.this.buttonSave.setVisibility(0);
                                return;
                            }
                            ++n;
                        } while (true);
                    }

                    protected void onPreExecute() {
                        PengirimanActivity.this.progressBarKecamatan.setVisibility(0);
                        PengirimanActivity.this.buttonSave.setVisibility(4);
                    }
                };
                Object[] arrobject = new String[]{((CityItem)this.this$0.cityItems.get(n)).getCity_id()};
                asyncTask.execute(arrobject);
                this.this$0.selectedKota = ((CityItem)this.this$0.cityItems.get(n)).getCity_id();
                this.this$0.selectedNamaKota = ((CityItem)this.this$0.cityItems.get(n)).getCity_name();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.spinnerKecamatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((PengirimanActivity)this){
            final /* synthetic */ PengirimanActivity this$0;
            {
                this.this$0 = pengirimanActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.selectedKecamatan = ((SubdistrictItem)this.this$0.subdistrictItems.get(n)).getSubdistrict_id();
                this.this$0.selectedNamaKecamatan = ((SubdistrictItem)this.this$0.subdistrictItems.get(n)).getSubdistrict_name();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((PengirimanActivity)this){
            final /* synthetic */ PengirimanActivity this$0;
            {
                this.this$0 = pengirimanActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                this.this$0.selectedJne = this.this$0.checkBoxJne.isChecked() ? "1" : "0";
                this.this$0.selectedTiki = this.this$0.checkBoxTiki.isChecked() ? "1" : "0";
                this.this$0.selectedPos = this.this$0.checkBoxPos.isChecked() ? "1" : "0";
                this.this$0.selectedJnt = this.this$0.checkBoxJnt.isChecked() ? "1" : "0";
                this.this$0.selectedWahana = this.this$0.checkBoxWahana.isChecked() ? "1" : "0";
                this.this$0.selectedSicepat = this.this$0.checkBoxSicepat.isChecked() ? "1" : "0";
                this.this$0.selectedEsl = this.this$0.checkBoxEsl.isChecked() ? "1" : "0";
                this.this$0.selectedRpx = this.this$0.checkBoxRpx.isChecked() ? "1" : "0";
                this.this$0.selectedPandu = this.this$0.checkBoxPandu.isChecked() ? "1" : "0";
                this.this$0.selectedCahaya = this.this$0.checkBoxCahaya.isChecked() ? "1" : "0";
                this.this$0.selectedPahala = this.this$0.checkBoxPahala.isChecked() ? "1" : "0";
                this.this$0.selectedIndah = this.this$0.checkBoxIndah.isChecked() ? "1" : "0";
                this.this$0.selectedEkspres = this.this$0.checkBoxEkspres.isChecked() ? "1" : "0";
                this.this$0.selectedEkonomis = this.this$0.checkBoxEkonomis.isChecked() ? "1" : "0";
                this.this$0.selectedCod = this.this$0.checkBoxCod.isChecked() ? "1" : "0";
                this.this$0.selectedDropship = this.this$0.checkBoxDropship.isChecked() ? "1" : "0";
                this.this$0.selectedDelivery = this.this$0.checkBoxDelivery.isChecked() ? "1" : "0";
                this.this$0.selectedMenuPengiriman = this.this$0.checkBoxMenuPengiriman.isChecked() ? "1" : "0";
                this.this$0.ongkirAdmin = this.this$0.editTextOngkirAdmin.getText().toString();
                this.this$0.ongkirCod = this.this$0.editTextOngkirCod.getText().toString();
                this.this$0.jarak = this.this$0.editTextJarak.getText().toString();
                this.this$0.biayaDelivery = this.this$0.editTextBiayaDelivery.getText().toString();
                this.this$0.biayaDeliveryMinimal = this.this$0.editTextBiayaDeliveryMinimal.getText().toString();
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)PengirimanActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)PengirimanActivity.this.dataPref.getToken());
                            hashMap.put((Object)"province", (Object)PengirimanActivity.this.selectedNamaProvinsi);
                            hashMap.put((Object)"city_name", (Object)PengirimanActivity.this.selectedNamaKota);
                            hashMap.put((Object)"subdistrict_name", (Object)PengirimanActivity.this.selectedNamaKecamatan);
                            hashMap.put((Object)"city_id", (Object)PengirimanActivity.this.selectedKota);
                            hashMap.put((Object)"subdistrict_id", (Object)PengirimanActivity.this.selectedKecamatan);
                            hashMap.put((Object)"jne", (Object)PengirimanActivity.this.selectedJne);
                            hashMap.put((Object)"tiki", (Object)PengirimanActivity.this.selectedTiki);
                            hashMap.put((Object)"pos", (Object)PengirimanActivity.this.selectedPos);
                            hashMap.put((Object)"jnt", (Object)PengirimanActivity.this.selectedJnt);
                            hashMap.put((Object)"wahana", (Object)PengirimanActivity.this.selectedWahana);
                            hashMap.put((Object)"sicepat", (Object)PengirimanActivity.this.selectedSicepat);
                            hashMap.put((Object)"esl", (Object)PengirimanActivity.this.selectedEsl);
                            hashMap.put((Object)"rpx", (Object)PengirimanActivity.this.selectedRpx);
                            hashMap.put((Object)"pandu", (Object)PengirimanActivity.this.selectedPandu);
                            hashMap.put((Object)"pahala", (Object)PengirimanActivity.this.selectedPahala);
                            hashMap.put((Object)"cahaya", (Object)PengirimanActivity.this.selectedCahaya);
                            hashMap.put((Object)"indah", (Object)PengirimanActivity.this.selectedIndah);
                            hashMap.put((Object)"ekonomis", (Object)PengirimanActivity.this.selectedEkonomis);
                            hashMap.put((Object)"ekspres", (Object)PengirimanActivity.this.selectedEkspres);
                            hashMap.put((Object)"cod", (Object)PengirimanActivity.this.selectedCod);
                            hashMap.put((Object)"ongkir_admin", (Object)PengirimanActivity.this.ongkirAdmin);
                            hashMap.put((Object)"ongkir_cod", (Object)PengirimanActivity.this.ongkirCod);
                            hashMap.put((Object)"dropship", (Object)PengirimanActivity.this.selectedDropship);
                            hashMap.put((Object)"delivery", (Object)PengirimanActivity.this.selectedDelivery);
                            hashMap.put((Object)"menu_pengiriman", (Object)PengirimanActivity.this.selectedMenuPengiriman);
                            hashMap.put((Object)"radius", (Object)PengirimanActivity.this.jarak);
                            hashMap.put((Object)"biaya_delivery", (Object)PengirimanActivity.this.biayaDelivery);
                            hashMap.put((Object)"biaya_delivery_minimal", (Object)PengirimanActivity.this.biayaDeliveryMinimal);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_shipment", "POST", (HashMap<String, String>)hashMap);
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
                                    Toast.makeText((Context)PengirimanActivity.this, (CharSequence)string, (int)1).show();
                                    PengirimanActivity.this.finish();
                                    return;
                                }
                                if (n == 0) {
                                    Toast.makeText((Context)PengirimanActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                break block8;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)PengirimanActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        this.buttonLokasi.setOnClickListener(new View.OnClickListener((PengirimanActivity)this){
            final /* synthetic */ PengirimanActivity this$0;
            {
                this.this$0 = pengirimanActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PengirimanLokasiActivity.class);
                if (this.this$0.latitude.equals((Object)"") || this.this$0.longitude.equals((Object)"") || this.this$0.latitude.equals((Object)"null") || this.this$0.longitude.equals((Object)"null")) {
                    intent.putExtra("latitude", "0");
                    intent.putExtra("longitude", "0");
                } else {
                    intent.putExtra("latitude", this.this$0.latitude);
                    intent.putExtra("longitude", this.this$0.longitude);
                }
                this.this$0.startActivityForResult(intent, 4);
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

