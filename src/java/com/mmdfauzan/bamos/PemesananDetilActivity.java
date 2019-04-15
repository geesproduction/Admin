/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.ClipData
 *  android.content.ClipboardManager
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentManager
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AlertDialog
 *  android.support.v7.app.AlertDialog$Builder
 *  android.support.v7.app.AppCompatActivity
 *  android.text.ClipboardManager
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 *  android.widget.Toast
 *  com.google.android.gms.maps.OnMapReadyCallback
 *  com.google.android.gms.maps.SupportMapFragment
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mmdfauzan.bamos.PemesananDetilActivity;
import com.mmdfauzan.bamos.adapter.PemesananDetilAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.ShowToast;
import com.mmdfauzan.bamos.model.PemesananItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PemesananDetilActivity
extends AppCompatActivity {
    Button buttonCancel;
    Button buttonEditPengiriman;
    Button buttonFullscreen;
    Button buttonHapusPemesanan;
    Button buttonKirim;
    Button buttonLink;
    Button buttonProcess;
    Button buttonSave;
    DataPref dataPref;
    String dropshipData = "";
    EditText editTextResi;
    String export_address;
    String export_order;
    String idMember;
    ImageButton imageButtonCall;
    ImageButton imageButtonCopyData;
    ImageButton imageButtonExportAddress;
    ImageButton imageButtonExportOrder;
    ImageButton imageButtonMemberChat;
    ImageButton imageButtonMemberSms;
    ImageButton imageButtonMemberTelepon;
    ImageButton imageButtonMemberWa;
    ImageButton imageButtonPrintOrder;
    ImageButton imageButtonShareData;
    ImageButton imageButtonSms;
    ImageButton imageButtonWa;
    String jarak;
    String keterangan;
    String kodepesanan;
    String kurir;
    String latitude;
    LinearLayout layoutAlamatDetail;
    LinearLayout layoutKirim;
    LinearLayout layoutMap;
    LinearLayout layoutProses;
    LinearLayout layoutResi;
    String link;
    ListView listViewPemesanan;
    String lokasi;
    String longitude;
    String namaMember;
    String ongkir;
    String pemesananData = "";
    String pemesananId;
    String penerimaData = "";
    String pesananData = "\n\nDETAIL PESANAN\n";
    boolean process = true;
    String resi;
    ShowToast showToast;
    SupportMapFragment supportMapFragment;
    String telepon;
    String teleponMember;
    TextView textViewLokasi;
    String totalData = "";
    View viewBukti;
    View viewDropship;
    View viewJudul;
    View viewMember;
    View viewPenerima;
    View viewTotal;

    private void setMap(String string, String string2) {
        this.supportMapFragment.getMapAsync(new OnMapReadyCallback((PemesananDetilActivity)this, string, string2){
            final /* synthetic */ PemesananDetilActivity this$0;
            final /* synthetic */ String val$lat;
            final /* synthetic */ String val$lon;
            {
                this.this$0 = pemesananDetilActivity;
                this.val$lat = string;
                this.val$lon = string2;
            }

            public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                double d = java.lang.Double.valueOf((String)this.val$lat);
                double d2 = java.lang.Double.valueOf((String)this.val$lon);
                if (android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_FINE_LOCATION") != 0 && android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    this.this$0.showToast.Toast("Aplikasi tidak diberikan izin mengakses lokasi. Lakukan pengaturan untuk memberikan izin akses lokasi.");
                    return;
                }
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(d, d2);
                com.google.android.gms.maps.model.BitmapDescriptor bitmapDescriptor = com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource((int)2131230900);
                googleMap.addMarker(new com.google.android.gms.maps.model.MarkerOptions().position(latLng).title("Lokasi Penerima").icon(bitmapDescriptor));
                googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition((com.google.android.gms.maps.model.CameraPosition)new com.google.android.gms.maps.model.CameraPosition$Builder().target(new com.google.android.gms.maps.model.LatLng(d, d2)).zoom(15.0f).build()));
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void copyData(String string) {
        if (Build.VERSION.SDK_INT >= 11) {
            ((ClipboardManager)this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence)"Data Pesanan", (CharSequence)string));
        } else {
            ((android.text.ClipboardManager)this.getSystemService("clipboard")).setText((CharSequence)string);
        }
        Toast.makeText((Context)this.getApplicationContext(), (CharSequence)"Data telah disalin.", (int)0).show();
    }

    public void editPengiriman() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        View view = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427432, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        EditText editText = (EditText)view.findViewById(2131296505);
        EditText editText2 = (EditText)view.findViewById(2131296530);
        this.ongkir = this.ongkir.replaceAll("[^\\d]", "");
        editText.setText((CharSequence)this.kurir);
        editText2.setText((CharSequence)this.ongkir);
        Button button = (Button)view.findViewById(2131296389);
        Button button2 = (Button)view.findViewById(2131296337);
        button.setOnClickListener(new View.OnClickListener(this, editText, editText2, alertDialog){
            final /* synthetic */ PemesananDetilActivity this$0;
            final /* synthetic */ AlertDialog val$b;
            final /* synthetic */ EditText val$editTextKurir;
            final /* synthetic */ EditText val$editTextOngkir;
            {
                this.this$0 = pemesananDetilActivity;
                this.val$editTextKurir = editText;
                this.val$editTextOngkir = editText2;
                this.val$b = alertDialog;
            }

            public void onClick(View view) {
                this.this$0.kurir = this.val$editTextKurir.getText().toString();
                this.this$0.ongkir = this.val$editTextOngkir.getText().toString();
                if (this.this$0.kurir.length() > 1) {
                    ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                                hashMap.put((Object)"idpesanan", (Object)PemesananDetilActivity.this.pemesananId);
                                hashMap.put((Object)"kurir", (Object)PemesananDetilActivity.this.kurir);
                                hashMap.put((Object)"ongkir", (Object)PemesananDetilActivity.this.ongkir);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_order_shipment", "POST", (HashMap<String, String>)hashMap);
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
                                if (n == 1) {
                                    Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                    PemesananDetilActivity.this.setResult(-1);
                                    Intent intent = new Intent((Context)PemesananDetilActivity.this, PemesananDetilActivity.class);
                                    intent.putExtra("idpesanan", PemesananDetilActivity.this.pemesananId);
                                    PemesananDetilActivity.this.startActivity(intent);
                                    PemesananDetilActivity.this.finish();
                                    return;
                                }
                                Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Mengubah pengiriman.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                    this.val$b.dismiss();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener(this, alertDialog){
            final /* synthetic */ PemesananDetilActivity this$0;
            final /* synthetic */ AlertDialog val$b;
            {
                this.this$0 = pemesananDetilActivity;
                this.val$b = alertDialog;
            }

            public void onClick(View view) {
                this.val$b.dismiss();
            }
        });
        alertDialog.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427388);
        this.getSupportActionBar().setTitle((CharSequence)"Detail Pesanan");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.pemesananId = this.getIntent().getStringExtra("idpesanan");
        this.viewPenerima = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427446, null, false);
        this.viewDropship = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427430, null, false);
        this.viewJudul = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427435, null, false);
        this.viewTotal = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427449, null, false);
        this.viewMember = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427442, null, false);
        this.viewBukti = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427427, null, false);
        this.layoutProses = (LinearLayout)this.findViewById(2131296678);
        this.layoutResi = (LinearLayout)this.findViewById(2131296679);
        this.layoutAlamatDetail = (LinearLayout)this.viewPenerima.findViewById(2131296639);
        this.layoutMap = (LinearLayout)this.viewPenerima.findViewById(2131296674);
        this.layoutMap.setVisibility(8);
        this.layoutKirim = (LinearLayout)this.findViewById(2131296666);
        this.listViewPemesanan = (ListView)this.findViewById(2131296714);
        this.editTextResi = (EditText)this.findViewById(2131296541);
        this.buttonCancel = (Button)this.findViewById(2131296337);
        this.buttonProcess = (Button)this.findViewById(2131296385);
        this.buttonKirim = (Button)this.findViewById(2131296363);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonHapusPemesanan = (Button)this.viewMember.findViewById(2131296357);
        this.imageButtonMemberTelepon = (ImageButton)this.viewMember.findViewById(2131296603);
        this.imageButtonMemberSms = (ImageButton)this.viewMember.findViewById(2131296602);
        this.imageButtonMemberWa = (ImageButton)this.viewMember.findViewById(2131296604);
        this.imageButtonMemberChat = (ImageButton)this.viewMember.findViewById(2131296601);
        this.buttonEditPengiriman = (Button)this.viewTotal.findViewById(2131296347);
        this.buttonFullscreen = (Button)this.viewPenerima.findViewById(2131296351);
        this.buttonLink = (Button)this.viewPenerima.findViewById(2131296364);
        this.imageButtonCall = (ImageButton)this.viewPenerima.findViewById(2131296585);
        this.imageButtonSms = (ImageButton)this.viewPenerima.findViewById(2131296618);
        this.imageButtonWa = (ImageButton)this.viewPenerima.findViewById(2131296619);
        this.imageButtonExportAddress = (ImageButton)this.viewPenerima.findViewById(2131296589);
        this.textViewLokasi = (TextView)this.viewPenerima.findViewById(2131297087);
        this.supportMapFragment = (SupportMapFragment)this.getSupportFragmentManager().findFragmentById(2131296720);
        this.imageButtonCopyData = (ImageButton)this.viewPenerima.findViewById(2131296588);
        this.imageButtonShareData = (ImageButton)this.viewPenerima.findViewById(2131296617);
        this.imageButtonExportOrder = (ImageButton)this.viewPenerima.findViewById(2131296590);
        this.imageButtonPrintOrder = (ImageButton)this.viewPenerima.findViewById(2131296609);
        this.buttonCancel.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.process = false;
                this.this$0.prosesPesanan();
            }
        });
        this.buttonProcess.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.process = true;
                this.this$0.prosesPesanan();
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.resi = this.this$0.editTextResi.getText().toString();
                if (this.this$0.resi.length() > 4) {
                    ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                    this.this$0.editTextResi.setError(null);
                    AlertDialog alertDialog = new AlertDialog.Builder((Context)this.this$0).create();
                    alertDialog.setTitle((CharSequence)"Proses Pesanan");
                    alertDialog.setMessage((CharSequence)"Status pesanan akan menjadi dikirim, lanjutkan proses ini?");
                    alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
                        final /* synthetic */ 3 this$1;
                        {
                            this.this$1 = var1;
                        }

                        public void onClick(DialogInterface dialogInterface, int n) {
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    try {
                                        HashMap hashMap = new HashMap();
                                        hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                                        hashMap.put((Object)"idpesanan", (Object)PemesananDetilActivity.this.pemesananId);
                                        hashMap.put((Object)"resi", (Object)PemesananDetilActivity.this.resi);
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_awb", "POST", (HashMap<String, String>)hashMap);
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
                                        if (n == 1) {
                                            Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                            PemesananDetilActivity.this.setResult(-1);
                                            PemesananDetilActivity.this.finish();
                                            return;
                                        }
                                        Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    catch (JSONException jSONException) {
                                        jSONException.printStackTrace();
                                        return;
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Mengubah status pesanan.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                        }
                    });
                    alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this, alertDialog){
                        final /* synthetic */ 3 this$1;
                        final /* synthetic */ AlertDialog val$alertDialog;
                        {
                            this.this$1 = var1;
                            this.val$alertDialog = alertDialog;
                        }

                        public void onClick(DialogInterface dialogInterface, int n) {
                            this.val$alertDialog.dismiss();
                        }
                    });
                    alertDialog.show();
                    return;
                }
                this.this$0.editTextResi.setError((CharSequence)"Masukkan resi yang valid.");
            }
        });
        this.buttonKirim.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder((Context)this.this$0).create();
                alertDialog.setTitle((CharSequence)"Proses Pesanan");
                alertDialog.setMessage((CharSequence)"Status pesanan akan menjadi dikirim, lanjutkan proses ini?");
                alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
                    final /* synthetic */ 4 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onClick(DialogInterface dialogInterface, int n) {
                        this.this$1.this$0.resi = "";
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                    }
                });
                alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this, alertDialog){
                    final /* synthetic */ 4 this$1;
                    final /* synthetic */ AlertDialog val$alertDialog;
                    {
                        this.this$1 = var1;
                        this.val$alertDialog = alertDialog;
                    }

                    public void onClick(DialogInterface dialogInterface, int n) {
                        this.val$alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                    hashMap.put((Object)"idpesanan", (Object)PemesananDetilActivity.this.pemesananId);
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_order_detail", "POST", (HashMap<String, String>)hashMap);
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
                int n2;
                block63 : {
                    String string;
                    block62 : {
                        TextView textView;
                        LinearLayout linearLayout;
                        TextView textView2;
                        TextView textView3;
                        LinearLayout linearLayout2;
                        if (this.progressDialog.isShowing()) {
                            this.progressDialog.dismiss();
                        }
                        if (jSONObject == null) {
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        try {
                            int n3 = jSONObject.getInt("success");
                            string = jSONObject.getString("message");
                            if (n3 == 1) {
                                n = jSONObject.getInt("tipe");
                                n2 = jSONObject.getInt("bukti_pembayaran");
                                JSONObject jSONObject2 = jSONObject.getJSONArray("penerima").getJSONObject(0);
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297098)).setText((CharSequence)jSONObject2.getString("nama"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297130)).setText((CharSequence)jSONObject2.getString("telepon"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297051)).setText((CharSequence)jSONObject2.getString("alamat"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297071)).setText((CharSequence)jSONObject2.getString("kecamatan"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297081)).setText((CharSequence)jSONObject2.getString("kota"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297124)).setText((CharSequence)jSONObject2.getString("provinsi"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297079)).setText((CharSequence)jSONObject2.getString("kodepos"));
                                ((TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297072)).setText((CharSequence)jSONObject2.getString("keterangan"));
                                textView3 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297127);
                                textView = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297073);
                                textView2 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297135);
                                linearLayout = (LinearLayout)PemesananDetilActivity.this.viewPenerima.findViewById(2131296644);
                                linearLayout2 = (LinearLayout)PemesananDetilActivity.this.viewPenerima.findViewById(2131296645);
                                if (n == 0) {
                                    linearLayout.setVisibility(0);
                                    linearLayout2.setVisibility(8);
                                } else {
                                    linearLayout.setVisibility(8);
                                    linearLayout2.setVisibility(0);
                                    PemesananDetilActivity.this.imageButtonShareData.setVisibility(8);
                                    PemesananDetilActivity.this.imageButtonCopyData.setVisibility(8);
                                    PemesananDetilActivity.this.imageButtonExportOrder.setVisibility(8);
                                    PemesananDetilActivity.this.imageButtonPrintOrder.setVisibility(8);
                                }
                                PemesananDetilActivity.this.latitude = jSONObject2.getString("latitude");
                                PemesananDetilActivity.this.longitude = jSONObject2.getString("longitude");
                                PemesananDetilActivity.this.jarak = jSONObject2.getString("jarak");
                                PemesananDetilActivity.this.lokasi = jSONObject2.getString("lokasi");
                                PemesananDetilActivity.this.link = jSONObject2.getString("link");
                                PemesananDetilActivity.this.telepon = jSONObject2.getString("telepon");
                                PemesananDetilActivity.this.telepon = PemesananDetilActivity.this.telepon.replaceAll("[\\D]", "");
                                if (!(PemesananDetilActivity.this.latitude.equals((Object)"") || PemesananDetilActivity.this.latitude.equals((Object)"null") || PemesananDetilActivity.this.latitude.equals((Object)"0") || PemesananDetilActivity.this.longitude.equals((Object)"") || PemesananDetilActivity.this.longitude.equals((Object)"null") || PemesananDetilActivity.this.longitude.equals((Object)"0"))) {
                                    PemesananDetilActivity.this.setMap(PemesananDetilActivity.this.latitude, PemesananDetilActivity.this.longitude);
                                    PemesananDetilActivity.this.textViewLokasi.setText((CharSequence)PemesananDetilActivity.this.lokasi);
                                    PemesananDetilActivity.this.layoutMap.setVisibility(0);
                                    PemesananDetilActivity.this.layoutAlamatDetail.setVisibility(8);
                                } else {
                                    PemesananDetilActivity.this.layoutMap.setVisibility(8);
                                    PemesananDetilActivity.this.layoutAlamatDetail.setVisibility(0);
                                }
                                PemesananDetilActivity.this.penerimaData = !PemesananDetilActivity.this.latitude.equals((Object)"") && !PemesananDetilActivity.this.latitude.equals((Object)"null") && !PemesananDetilActivity.this.latitude.equals((Object)"0") && !PemesananDetilActivity.this.longitude.equals((Object)"") && !PemesananDetilActivity.this.longitude.equals((Object)"null") && !PemesananDetilActivity.this.longitude.equals((Object)"0") ? "DATA PENERIMA\n\nNama :\n" + jSONObject2.getString("nama") + "\nTelepon :\n" + jSONObject2.getString("telepon") + "\nLokasi :\n" + PemesananDetilActivity.this.lokasi + "\nKeterangan :\n" + jSONObject2.getString("keterangan") + "\nPeta :\n" + PemesananDetilActivity.this.link : "DATA PENERIMA\n\nNama :\n" + jSONObject2.getString("nama") + "\nTelepon :\n" + jSONObject2.getString("telepon") + "\nAlamat :\n" + jSONObject2.getString("alamat") + "\nKecamatan :\n" + jSONObject2.getString("kecamatan") + "\nKota/Kabupaten :\n" + jSONObject2.getString("kota") + "\nProvinsi :\n" + jSONObject2.getString("provinsi") + "\nKode Pos :\n" + jSONObject2.getString("kodepos") + "\nKeterangan :\n" + jSONObject2.getString("keterangan");
                            }
                            break block62;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                        ((TextView)PemesananDetilActivity.this.viewJudul.findViewById(2131297067)).setText((CharSequence)"DATA PESANAN");
                        JSONObject jSONObject3 = jSONObject.getJSONArray("pesanan").getJSONObject(0);
                        String string2 = jSONObject3.getString("kodepembayaran");
                        TextView textView4 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297082);
                        TextView textView5 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297052);
                        TextView textView6 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297108);
                        TextView textView7 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297075);
                        TextView textView8 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297100);
                        TextView textView9 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297069);
                        TextView textView10 = (TextView)PemesananDetilActivity.this.viewTotal.findViewById(2131297061);
                        LinearLayout linearLayout3 = (LinearLayout)PemesananDetilActivity.this.viewTotal.findViewById(2131296694);
                        LinearLayout linearLayout4 = (LinearLayout)PemesananDetilActivity.this.viewTotal.findViewById(2131296668);
                        LinearLayout linearLayout5 = (LinearLayout)PemesananDetilActivity.this.viewTotal.findViewById(2131296677);
                        if (n != 0) {
                            linearLayout5.setVisibility(8);
                        }
                        PemesananDetilActivity.this.kurir = jSONObject3.getString("kurir");
                        PemesananDetilActivity.this.ongkir = jSONObject3.getString("ongkir");
                        if (!(PemesananDetilActivity.this.latitude.equals((Object)"") || PemesananDetilActivity.this.latitude.equals((Object)"null") || PemesananDetilActivity.this.latitude.equals((Object)"0") || PemesananDetilActivity.this.longitude.equals((Object)"") || PemesananDetilActivity.this.longitude.equals((Object)"null") || PemesananDetilActivity.this.longitude.equals((Object)"0") || PemesananDetilActivity.this.jarak.equals((Object)"0"))) {
                            PemesananDetilActivity.this.kurir = PemesananDetilActivity.this.kurir + " (" + PemesananDetilActivity.this.jarak + " km)";
                        }
                        textView4.setText((CharSequence)PemesananDetilActivity.this.kurir);
                        textView5.setText((CharSequence)jSONObject3.getString("berat"));
                        textView6.setText((CharSequence)("Rp " + PemesananDetilActivity.this.ongkir));
                        textView8.setText((CharSequence)jSONObject3.getString("voucher"));
                        textView9.setText((CharSequence)("Rp -" + jSONObject3.getString("diskon")));
                        textView10.setText((CharSequence)("Rp " + jSONObject3.getString("total")));
                        textView7.setText((CharSequence)("Rp " + string2));
                        if (string2.equals((Object)"0")) {
                            linearLayout4.setVisibility(8);
                        } else {
                            linearLayout4.setVisibility(0);
                        }
                        textView2.setText((CharSequence)jSONObject3.getString("waktu"));
                        if (jSONObject3.getInt("saldo") == 1) {
                            textView3.setVisibility(0);
                        } else {
                            textView3.setVisibility(8);
                        }
                        String string3 = jSONObject3.getString("keteranganseller");
                        textView.setText((CharSequence)string3);
                        if (string3.length() > 1) {
                            textView.setVisibility(0);
                        } else {
                            textView.setVisibility(8);
                        }
                        PemesananDetilActivity.this.export_address = jSONObject3.getString("export_address");
                        PemesananDetilActivity.this.export_order = jSONObject3.getString("export_order");
                        if (!(PemesananDetilActivity.this.export_order.equals((Object)"") || PemesananDetilActivity.this.export_order.equals((Object)"null") || PemesananDetilActivity.this.export_order.equals((Object)"0") || n != 0)) {
                            PemesananDetilActivity.this.imageButtonExportOrder.setVisibility(0);
                            PemesananDetilActivity.this.imageButtonPrintOrder.setVisibility(0);
                        } else {
                            PemesananDetilActivity.this.imageButtonExportOrder.setVisibility(8);
                            PemesananDetilActivity.this.imageButtonPrintOrder.setVisibility(8);
                        }
                        if (n != 0) {
                            linearLayout.setVisibility(8);
                            linearLayout2.setVisibility(0);
                            TextView textView11 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297068);
                            TextView textView12 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297133);
                            textView11.setText((CharSequence)jSONObject3.getString("judul"));
                            textView12.setText((CharSequence)("Rp " + jSONObject3.getString("total")));
                        }
                        PemesananDetilActivity.this.totalData = "\n\n# " + PemesananDetilActivity.this.kurir + " (" + jSONObject3.getString("berat") + ") / " + jSONObject3.getString("ongkir");
                        if (!string2.equals((Object)"0")) {
                            PemesananDetilActivity.this.totalData = PemesananDetilActivity.this.totalData + "\n\n# Kode Unik Pembayaran / " + string2;
                        }
                        if (!jSONObject3.getString("voucher").equals((Object)"")) {
                            PemesananDetilActivity.this.totalData = PemesananDetilActivity.this.totalData + "\n\n# " + jSONObject3.getString("voucher") + " / -" + jSONObject3.getString("diskon") + " (DISKON)";
                        }
                        PemesananDetilActivity.this.totalData = PemesananDetilActivity.this.totalData + "\n\nTOTAL ----- " + jSONObject3.getString("total");
                        TextView textView13 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297077);
                        PemesananDetilActivity.this.kodepesanan = jSONObject3.getString("kode");
                        textView13.setText((CharSequence)PemesananDetilActivity.this.kodepesanan);
                        TextView textView14 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297128);
                        PemesananDetilActivity.this.editTextResi.setText((CharSequence)jSONObject3.getString("resi"));
                        if (jSONObject3.getString("status").equals((Object)"0")) {
                            PemesananDetilActivity.this.buttonEditPengiriman.setVisibility(0);
                        } else {
                            PemesananDetilActivity.this.buttonEditPengiriman.setVisibility(8);
                        }
                        if (jSONObject3.getString("status").equals((Object)"0")) {
                            textView14.setText((CharSequence)"BELUM DIPROSES");
                        } else if (jSONObject3.getString("status").equals((Object)"1")) {
                            textView14.setText((CharSequence)"TELAH DIPROSES");
                        } else if (jSONObject3.getString("status").equals((Object)"2")) {
                            textView14.setText((CharSequence)"TELAH DIKIRIM");
                        } else if (jSONObject3.getString("status").equals((Object)"3")) {
                            textView14.setText((CharSequence)"DIBATALKAN");
                        } else if (jSONObject3.getString("status").equals((Object)"4")) {
                            textView14.setText((CharSequence)"TELAH DITERIMA PEMBELI");
                        } else {
                            textView14.setText((CharSequence)"");
                        }
                        if (jSONObject3.getString("status").equals((Object)"0")) {
                            PemesananDetilActivity.this.layoutProses.setVisibility(0);
                            PemesananDetilActivity.this.layoutResi.setVisibility(8);
                        } else if (jSONObject3.getString("status").equals((Object)"1")) {
                            if (!(PemesananDetilActivity.this.latitude.equals((Object)"") || PemesananDetilActivity.this.latitude.equals((Object)"null") || PemesananDetilActivity.this.latitude.equals((Object)"0") || PemesananDetilActivity.this.longitude.equals((Object)"") || PemesananDetilActivity.this.longitude.equals((Object)"null") || PemesananDetilActivity.this.longitude.equals((Object)"0"))) {
                                PemesananDetilActivity.this.layoutKirim.setVisibility(0);
                                PemesananDetilActivity.this.layoutResi.setVisibility(8);
                            } else {
                                PemesananDetilActivity.this.layoutKirim.setVisibility(8);
                                PemesananDetilActivity.this.layoutResi.setVisibility(0);
                            }
                            if (n != 0) {
                                PemesananDetilActivity.this.layoutKirim.setVisibility(8);
                                PemesananDetilActivity.this.layoutResi.setVisibility(8);
                            }
                            PemesananDetilActivity.this.layoutProses.setVisibility(8);
                        } else if (jSONObject3.getString("status").equals((Object)"2")) {
                            if (!(PemesananDetilActivity.this.latitude.equals((Object)"") || PemesananDetilActivity.this.latitude.equals((Object)"null") || PemesananDetilActivity.this.latitude.equals((Object)"0") || PemesananDetilActivity.this.longitude.equals((Object)"") || PemesananDetilActivity.this.longitude.equals((Object)"null") || PemesananDetilActivity.this.longitude.equals((Object)"0"))) {
                                PemesananDetilActivity.this.layoutKirim.setVisibility(8);
                                PemesananDetilActivity.this.layoutResi.setVisibility(8);
                            } else {
                                PemesananDetilActivity.this.layoutKirim.setVisibility(8);
                                PemesananDetilActivity.this.layoutResi.setVisibility(0);
                            }
                            PemesananDetilActivity.this.layoutProses.setVisibility(8);
                        } else {
                            PemesananDetilActivity.this.layoutKirim.setVisibility(8);
                            PemesananDetilActivity.this.layoutResi.setVisibility(8);
                            PemesananDetilActivity.this.layoutProses.setVisibility(8);
                        }
                        String string4 = jSONObject3.getString("rating");
                        String string5 = jSONObject3.getString("review");
                        LinearLayout linearLayout6 = (LinearLayout)PemesananDetilActivity.this.viewPenerima.findViewById(2131296680);
                        ImageView imageView = (ImageView)PemesananDetilActivity.this.viewPenerima.findViewById(2131296630);
                        TextView textView15 = (TextView)PemesananDetilActivity.this.viewPenerima.findViewById(2131297056);
                        if (string4.equals((Object)"1") || string4.equals((Object)"2")) {
                            if (string4.equals((Object)"1")) {
                                imageView.setImageResource(2131230935);
                            } else if (string4.equals((Object)"2")) {
                                imageView.setImageResource(2131230851);
                            }
                            textView15.setText((CharSequence)string5);
                            linearLayout6.setVisibility(0);
                        } else {
                            linearLayout6.setVisibility(8);
                        }
                        JSONObject jSONObject4 = jSONObject.getJSONArray("member").getJSONObject(0);
                        TextView textView16 = (TextView)PemesananDetilActivity.this.viewMember.findViewById(2131297102);
                        TextView textView17 = (TextView)PemesananDetilActivity.this.viewMember.findViewById(2131297057);
                        TextView textView18 = (TextView)PemesananDetilActivity.this.viewMember.findViewById(2131297131);
                        LinearLayout linearLayout7 = (LinearLayout)PemesananDetilActivity.this.viewMember.findViewById(2131296675);
                        LinearLayout linearLayout8 = (LinearLayout)PemesananDetilActivity.this.viewMember.findViewById(2131296676);
                        textView16.setText((CharSequence)jSONObject4.getString("nama"));
                        textView17.setText((CharSequence)jSONObject4.getString("email"));
                        textView18.setText((CharSequence)jSONObject4.getString("telepon"));
                        PemesananDetilActivity.this.idMember = jSONObject4.getString("idmember");
                        PemesananDetilActivity.this.namaMember = jSONObject4.getString("nama");
                        PemesananDetilActivity.this.teleponMember = jSONObject4.getString("telepon");
                        if (jSONObject4.getBoolean("ismember")) {
                            linearLayout7.setVisibility(0);
                            linearLayout8.setVisibility(8);
                        } else {
                            linearLayout7.setVisibility(8);
                            linearLayout8.setVisibility(0);
                        }
                        if (jSONObject3.getString("diskon").equals((Object)"0")) {
                            linearLayout3.setVisibility(8);
                        } else {
                            linearLayout3.setVisibility(0);
                        }
                        TextView textView19 = (TextView)PemesananDetilActivity.this.viewDropship.findViewById(2131297055);
                        TextView textView20 = (TextView)PemesananDetilActivity.this.viewDropship.findViewById(2131297054);
                        JSONObject jSONObject5 = jSONObject.getJSONObject("dropship");
                        String string6 = jSONObject5.getString("dropship");
                        String string7 = jSONObject5.getString("dropship_nama");
                        String string8 = jSONObject5.getString("dropship_keterangan");
                        PemesananDetilActivity.this.listViewPemesanan.addHeaderView(PemesananDetilActivity.this.viewPenerima, null, false);
                        if (string6.equals((Object)"1")) {
                            textView19.setText((CharSequence)string7);
                            textView20.setText((CharSequence)string8);
                            if (n == 0) {
                                PemesananDetilActivity.this.listViewPemesanan.addHeaderView(PemesananDetilActivity.this.viewDropship, null, false);
                            }
                            PemesananDetilActivity.this.dropshipData = "\n\nDATA PENGIRIM (DROPSHIP)\n\nNama :\n" + string7 + "\nKeterangan:\n" + string8 + "\n";
                        }
                        if (n == 0) {
                            PemesananDetilActivity.this.listViewPemesanan.addHeaderView(PemesananDetilActivity.this.viewJudul, null, false);
                        }
                        break block63;
                    }
                    Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                    PemesananDetilActivity.this.finish();
                    return;
                }
                if (n == 0 || n == 1) {
                    PemesananDetilActivity.this.listViewPemesanan.addFooterView(PemesananDetilActivity.this.viewTotal, null, false);
                }
                if (n2 == 1) {
                    ImageView imageView = (ImageView)PemesananDetilActivity.this.viewBukti.findViewById(2131296626);
                    Picasso.with((Context)PemesananDetilActivity.this).load("http://os.bikinaplikasi.com/gambar/konfirmasi/" + PemesananDetilActivity.this.kodepesanan + ".jpg").placeholder(2131230939).into(imageView);
                    Button button = (Button)PemesananDetilActivity.this.viewBukti.findViewById(2131296392);
                    Button button2 = (Button)PemesananDetilActivity.this.viewBukti.findViewById(2131296340);
                    View.OnClickListener onClickListener = new View.OnClickListener(){

                        public void onClick(View view) {
                            PemesananDetilActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)("http://os.bikinaplikasi.com/gambar/konfirmasi/" + PemesananDetilActivity.this.kodepesanan + ".jpg"))));
                        }
                    };
                    button.setOnClickListener(onClickListener);
                    View.OnClickListener onClickListener2 = new View.OnClickListener(){

                        public void onClick(View view) {
                            final AlertDialog alertDialog = new AlertDialog.Builder((Context)PemesananDetilActivity.this).create();
                            alertDialog.setTitle((CharSequence)"Hapus Bukti Pembayaran");
                            alertDialog.setMessage((CharSequence)"Bukti pembayaran yang dihapus tidak dapat dikembalikan, pastikan telah menyimpan bukti pembayaran. Hapus bukti pembayaran ini?");
                            alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(){

                                public void onClick(DialogInterface dialogInterface, int n) {
                                    new AsyncTask<String, String, JSONObject>(){
                                        JSONParser jsonParser = new JSONParser();
                                        ProgressDialog progressDialog;

                                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                            try {
                                                HashMap hashMap = new HashMap();
                                                hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                                                hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                                                hashMap.put((Object)"kodepesanan", (Object)PemesananDetilActivity.this.kodepesanan);
                                                if (PemesananDetilActivity.this.process) {
                                                    return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_receipt", "POST", (HashMap<String, String>)hashMap);
                                                }
                                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/cancel_order", "POST", (HashMap<String, String>)hashMap);
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
                                                if (n == 1) {
                                                    Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                                    PemesananDetilActivity.this.finish();
                                                    return;
                                                }
                                                Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                                return;
                                            }
                                            catch (JSONException jSONException) {
                                                jSONException.printStackTrace();
                                                return;
                                            }
                                        }

                                        protected void onPreExecute() {
                                            this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                                            this.progressDialog.setMessage((CharSequence)"Menghapus bukti pembayaran.");
                                            this.progressDialog.setIndeterminate(false);
                                            this.progressDialog.setCancelable(false);
                                            this.progressDialog.show();
                                        }
                                    }.execute((Object[])new String[0]);
                                }
                            });
                            alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(){

                                public void onClick(DialogInterface dialogInterface, int n) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog.show();
                        }

                    };
                    button2.setOnClickListener(onClickListener2);
                    PemesananDetilActivity.this.listViewPemesanan.addFooterView(PemesananDetilActivity.this.viewBukti, null, false);
                }
                PemesananDetilActivity.this.listViewPemesanan.addFooterView(PemesananDetilActivity.this.viewMember, null, false);
                ArrayList arrayList = new ArrayList();
                new PemesananItem();
                JSONArray jSONArray = jSONObject.getJSONArray("pesanan_item");
                for (int i = 0; i < jSONArray.length(); ++i) {
                    JSONObject jSONObject6 = jSONArray.getJSONObject(i);
                    PemesananItem pemesananItem = new PemesananItem();
                    pemesananItem.setNamaProduk(jSONObject6.getString("nama"));
                    pemesananItem.setJumlahProduk(jSONObject6.getString("jumlah"));
                    pemesananItem.setHargaProduk(jSONObject6.getString("harga"));
                    pemesananItem.setGambarProduk(jSONObject6.getString("gambar"));
                    pemesananItem.setGrosir(jSONObject6.getString("grosir"));
                    PemesananDetilActivity.this.pesananData = PemesananDetilActivity.this.pesananData + "\n\n* " + jSONObject6.getString("nama") + " / " + jSONObject6.getString("jumlah") + " / " + jSONObject6.getString("harga");
                    arrayList.add((Object)pemesananItem);
                }
                PemesananDetilAdapter pemesananDetilAdapter = new PemesananDetilAdapter((Activity)PemesananDetilActivity.this, (ArrayList<PemesananItem>)arrayList);
                PemesananDetilActivity.this.listViewPemesanan.setAdapter((ListAdapter)pemesananDetilAdapter);
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }

        }.execute((Object[])new String[0]);
        this.buttonLink.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.copyData(this.this$0.link);
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)this.this$0.link)));
            }
        });
        this.buttonFullscreen.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.LokasiActivity.class);
                intent.putExtra("latitude", this.this$0.latitude);
                intent.putExtra("longitude", this.this$0.longitude);
                intent.putExtra("link", this.this$0.link);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonCopyData.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.pemesananData = this.this$0.penerimaData + this.this$0.dropshipData + this.this$0.pesananData + this.this$0.totalData;
                this.this$0.copyData(this.this$0.pemesananData);
            }
        });
        this.imageButtonShareData.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.pemesananData = this.this$0.penerimaData + this.this$0.dropshipData + this.this$0.pesananData + this.this$0.totalData;
                this.this$0.shareData(this.this$0.pemesananData);
            }
        });
        this.buttonEditPengiriman.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.editPengiriman();
            }
        });
        this.buttonHapusPemesanan.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder((Context)this.this$0).create();
                alertDialog.setTitle((CharSequence)"Hapus Pesanan");
                alertDialog.setMessage((CharSequence)"Pesanan yang dihapus tidak dapat dilihat dan dikembalikan lagi. Kamu yakin ingin menghapus pesanan ini?");
                alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
                    final /* synthetic */ 10 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onClick(DialogInterface dialogInterface, int n) {
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();
                            ProgressDialog progressDialog;

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"idpesanan", (Object)PemesananDetilActivity.this.pemesananId);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_order", "POST", (HashMap<String, String>)hashMap);
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
                                    if (n == 1) {
                                        Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                        PemesananDetilActivity.this.setResult(-1);
                                        PemesananDetilActivity.this.finish();
                                        return;
                                    }
                                    Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Menghapus pesanan");
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                    }
                });
                alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this, alertDialog){
                    final /* synthetic */ 10 this$1;
                    final /* synthetic */ AlertDialog val$alertDialog;
                    {
                        this.this$1 = var1;
                        this.val$alertDialog = alertDialog;
                    }

                    public void onClick(DialogInterface dialogInterface, int n) {
                        this.val$alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
        this.imageButtonMemberChat.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatDetailActivity.class);
                intent.putExtra("startChat", 1);
                intent.putExtra("idmember", this.this$0.idMember);
                intent.putExtra("nama", this.this$0.namaMember);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberTelepon.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts((String)"tel", (String)this.this$0.teleponMember, null));
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberSms.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", this.this$0.teleponMember);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberWa.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.openWA(this.this$0.teleponMember);
            }
        });
        this.imageButtonCall.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts((String)"tel", (String)this.this$0.telepon, null));
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonSms.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", this.this$0.telepon);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonWa.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                this.this$0.openWA(this.this$0.telepon);
            }
        });
        this.imageButtonExportAddress.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)this.this$0.export_address));
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonExportOrder.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)this.this$0.export_order));
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonPrintOrder.setOnClickListener(new View.OnClickListener((PemesananDetilActivity)this){
            final /* synthetic */ PemesananDetilActivity this$0;
            {
                this.this$0 = pemesananDetilActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PrintActivity.class);
                intent.putExtra("idpesanan", this.this$0.pemesananId);
                this.this$0.startActivity(intent);
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

    public void openWA(String string) {
        if (string.startsWith("0")) {
            string = string.replaceFirst("0", "62");
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse((String)("https://api.whatsapp.com/send?phone=" + string)));
        this.startActivity(intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void prosesPesanan() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        View view = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427448, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        TextView textView = (TextView)view.findViewById(2131297067);
        TextView textView2 = (TextView)view.findViewById(2131297065);
        String string = "";
        String string2 = "";
        if (this.process) {
            string = "Proses Pesanan";
            string2 = "Kamu yakin akan memproses pesanan ini?";
        } else if (!this.process) {
            string = "Batalkan Pesanan";
            string2 = "Kamu yakin akan membatalkan pesanan ini?";
        }
        textView.setText((CharSequence)string);
        textView2.setText((CharSequence)string2);
        Button button = (Button)view.findViewById(2131296409);
        Button button2 = (Button)view.findViewById(2131296402);
        button.setOnClickListener(new View.OnClickListener(this, (EditText)view.findViewById(2131296502), alertDialog){
            final /* synthetic */ PemesananDetilActivity this$0;
            final /* synthetic */ AlertDialog val$b;
            final /* synthetic */ EditText val$editTextKeterangan;
            {
                this.this$0 = pemesananDetilActivity;
                this.val$editTextKeterangan = editText;
                this.val$b = alertDialog;
            }

            public void onClick(View view) {
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.this$0.keterangan = this.val$editTextKeterangan.getText().toString();
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)PemesananDetilActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)PemesananDetilActivity.this.dataPref.getToken());
                            hashMap.put((Object)"idpesanan", (Object)PemesananDetilActivity.this.pemesananId);
                            hashMap.put((Object)"keteranganseller", (Object)PemesananDetilActivity.this.keterangan);
                            if (PemesananDetilActivity.this.process) {
                                return this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/process_order", "POST", (HashMap<String, String>)hashMap);
                            }
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/cancel_order", "POST", (HashMap<String, String>)hashMap);
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
                            if (n == 1) {
                                Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                                PemesananDetilActivity.this.setResult(-1);
                                PemesananDetilActivity.this.finish();
                                return;
                            }
                            Toast.makeText((Context)PemesananDetilActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)PemesananDetilActivity.this);
                        if (PemesananDetilActivity.this.process) {
                            this.progressDialog.setMessage((CharSequence)"Memproses pesanan.");
                        } else {
                            this.progressDialog.setMessage((CharSequence)"Membatalkan pesanan.");
                        }
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
                this.val$b.dismiss();
            }
        });
        button2.setOnClickListener(new View.OnClickListener(this, alertDialog){
            final /* synthetic */ PemesananDetilActivity this$0;
            final /* synthetic */ AlertDialog val$b;
            {
                this.this$0 = pemesananDetilActivity;
                this.val$b = alertDialog;
            }

            public void onClick(View view) {
                this.val$b.dismiss();
            }
        });
        alertDialog.show();
    }

    public void shareData(String string) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", this.kodepesanan);
        intent.putExtra("android.intent.extra.TEXT", string);
        this.startActivity(Intent.createChooser((Intent)intent, (CharSequence)"Bagikan melalui"));
    }

}

