/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentManager
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.ProgressBar
 *  android.widget.TextView
 *  android.widget.Toast
 *  com.google.android.gms.maps.OnMapReadyCallback
 *  com.google.android.gms.maps.SupportMapFragment
 *  java.lang.CharSequence
 *  java.lang.Double
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mmdfauzan.bamos.PengirimanLokasiActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.GPSTracker;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class PengirimanLokasiActivity
extends AppCompatActivity {
    String alamat;
    Button buttonPilih;
    DataPref dataPref;
    GPSTracker gpsTracker;
    String latitude;
    String longitude;
    ProgressBar progressBarLoading;
    Double selected_latitude;
    Double selected_longitude;
    ShowToast showToast;
    SupportMapFragment supportMapFragment;
    TextView textViewAlamat;

    private void setMap(String string, String string2) {
        this.supportMapFragment.getMapAsync(new OnMapReadyCallback((PengirimanLokasiActivity)this, string, string2){
            final /* synthetic */ PengirimanLokasiActivity this$0;
            final /* synthetic */ String val$lat;
            final /* synthetic */ String val$lon;
            {
                this.this$0 = pengirimanLokasiActivity;
                this.val$lat = string;
                this.val$lon = string2;
            }

            public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                double d = Double.valueOf((String)this.val$lat);
                double d2 = Double.valueOf((String)this.val$lon);
                if (android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_FINE_LOCATION") != 0 && android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    this.this$0.showToast.Toast("Aplikasi tidak diberikan izin mengakses lokasi. Lakukan pengaturan untuk memberikan izin akses lokasi.");
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition((com.google.android.gms.maps.model.CameraPosition)new com.google.android.gms.maps.model.CameraPosition$Builder().target(new com.google.android.gms.maps.model.LatLng(d, d2)).zoom(15.0f).build()));
                googleMap.setOnCameraMoveListener(new com.google.android.gms.maps.GoogleMap$OnCameraMoveListener(this){
                    final /* synthetic */ 2 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onCameraMove() {
                    }
                });
                googleMap.setOnCameraIdleListener(new com.google.android.gms.maps.GoogleMap$OnCameraIdleListener(this, googleMap){
                    final /* synthetic */ 2 this$1;
                    final /* synthetic */ com.google.android.gms.maps.GoogleMap val$googleMap;
                    {
                        this.this$1 = var1;
                        this.val$googleMap = googleMap;
                    }

                    public void onCameraIdle() {
                        com.google.android.gms.maps.model.LatLng latLng = this.val$googleMap.getCameraPosition().target;
                        double d = latLng.latitude;
                        double d2 = latLng.longitude;
                        this.this$1.this$0.selected_latitude = d;
                        this.this$1.this$0.selected_longitude = d2;
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"latitude", (Object)String.valueOf((Object)PengirimanLokasiActivity.this.selected_latitude));
                                    hashMap.put((Object)"longitude", (Object)String.valueOf((Object)PengirimanLokasiActivity.this.selected_longitude));
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_location", "POST", (HashMap<String, String>)hashMap);
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
                                PengirimanLokasiActivity.this.progressBarLoading.setVisibility(8);
                                if (jSONObject == null) {
                                    PengirimanLokasiActivity.this.showToast.ToastError();
                                    return;
                                }
                                try {
                                    int n = jSONObject.getInt("success");
                                    String string = jSONObject.getString("message");
                                    if (n == 1) {
                                        PengirimanLokasiActivity.this.alamat = jSONObject.getString("alamat");
                                        PengirimanLokasiActivity.this.textViewAlamat.setText((CharSequence)PengirimanLokasiActivity.this.alamat);
                                        PengirimanLokasiActivity.this.textViewAlamat.setVisibility(0);
                                        return;
                                    }
                                    PengirimanLokasiActivity.this.showToast.Toast(string);
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }

                            protected void onPreExecute() {
                                PengirimanLokasiActivity.this.textViewAlamat.setVisibility(8);
                                PengirimanLokasiActivity.this.progressBarLoading.setVisibility(0);
                            }
                        }.execute((Object[])new String[0]);
                    }
                });
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427390);
        this.getSupportActionBar().setTitle((CharSequence)"Lokasi Pengiriman");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.showToast = new ShowToast((Context)this);
        this.dataPref = new DataPref((Context)this);
        this.supportMapFragment = (SupportMapFragment)this.getSupportFragmentManager().findFragmentById(2131296720);
        this.progressBarLoading = (ProgressBar)this.findViewById(2131296759);
        this.textViewAlamat = (TextView)this.findViewById(2131297051);
        this.buttonPilih = (Button)this.findViewById(2131296380);
        Intent intent = this.getIntent();
        this.latitude = intent.getStringExtra("latitude");
        this.longitude = intent.getStringExtra("longitude");
        this.gpsTracker = new GPSTracker((Context)this);
        if (!this.latitude.equals((Object)"0") && !this.longitude.equals((Object)"0")) {
            this.selected_latitude = Double.valueOf((String)this.latitude);
            this.selected_longitude = Double.valueOf((String)this.longitude);
        } else if (this.gpsTracker.canGetLocation()) {
            this.selected_latitude = this.gpsTracker.getLatitude();
            this.selected_longitude = this.gpsTracker.getLongitude();
        } else {
            this.selected_latitude = -6.1751133;
            this.selected_longitude = 106.8249236;
        }
        PengirimanLokasiActivity.super.setMap(String.valueOf((Object)this.selected_latitude), String.valueOf((Object)this.selected_longitude));
        this.buttonPilih.setOnClickListener(new View.OnClickListener((PengirimanLokasiActivity)this){
            final /* synthetic */ PengirimanLokasiActivity this$0;
            {
                this.this$0 = pengirimanLokasiActivity;
            }

            public void onClick(View view) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)PengirimanLokasiActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)PengirimanLokasiActivity.this.dataPref.getToken());
                            hashMap.put((Object)"lokasi_lat", (Object)String.valueOf((Object)PengirimanLokasiActivity.this.selected_latitude));
                            hashMap.put((Object)"lokasi_lon", (Object)String.valueOf((Object)PengirimanLokasiActivity.this.selected_longitude));
                            hashMap.put((Object)"lokasi", (Object)PengirimanLokasiActivity.this.alamat);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_location", "POST", (HashMap<String, String>)hashMap);
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
                                Toast.makeText((Context)PengirimanLokasiActivity.this, (CharSequence)string, (int)1).show();
                                if (n != 1) return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                            Intent intent = new Intent();
                            intent.putExtra("latitude", String.valueOf((Object)PengirimanLokasiActivity.this.selected_latitude));
                            intent.putExtra("longitude", String.valueOf((Object)PengirimanLokasiActivity.this.selected_longitude));
                            intent.putExtra("lokasi", PengirimanLokasiActivity.this.alamat);
                            PengirimanLokasiActivity.this.setResult(-1, intent);
                            PengirimanLokasiActivity.this.finish();
                            return;
                        }
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)PengirimanLokasiActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Menyimpan lokasi.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
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

