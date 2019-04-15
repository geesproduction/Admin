/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Color
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.annotation.NonNull
 *  android.support.design.widget.NavigationView
 *  android.support.design.widget.NavigationView$OnNavigationItemSelectedListener
 *  android.support.v4.app.ActivityCompat
 *  android.support.v4.content.ContextCompat
 *  android.support.v4.widget.DrawerLayout
 *  android.support.v4.widget.DrawerLayout$DrawerListener
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.ActionBarDrawerToggle
 *  android.support.v7.app.AlertDialog
 *  android.support.v7.app.AlertDialog$Builder
 *  android.support.v7.app.AppCompatActivity
 *  android.support.v7.widget.Toolbar
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.TextView
 *  android.widget.Toast
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
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.LoginActivity;
import com.mmdfauzan.bamos.MainActivity;
import com.mmdfauzan.bamos.adapter.PemesananAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.PemesananItem;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity
extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_FINE_LOCATION = 111;
    private static final int REQUEST_WRITE_STORAGE = 112;
    Button buttonBanner;
    Button buttonChat;
    Button buttonDriver;
    Button buttonEVoucher;
    Button buttonEditAplikasi;
    Button buttonFiturPremium;
    Button buttonFiturPremiumBig;
    Button buttonHapusAkun;
    Button buttonInformasiToko;
    Button buttonInformation;
    Button buttonKategori;
    Button buttonKelola;
    Button buttonLinkDownload;
    Button buttonLogout;
    Button buttonMember;
    Button buttonMenu;
    Button buttonPembayaran;
    Button buttonPemberitahuan;
    Button buttonPemesanan;
    Button buttonPengiriman;
    Button buttonPersyaratan;
    Button buttonPetunjuk;
    Button buttonProduk;
    Button buttonReset;
    Button buttonStatistik;
    Button buttonTentang;
    Button buttonTestimoni;
    Button buttonTutorial;
    Button buttonVoucher;
    Button buttonWebsite;
    View contentView;
    DataPref dataPref;
    boolean firstOpen = true;
    ImageButton imageButtonCari;
    ImageView imageViewProfile;
    ImageView imageViewVerified;
    LinearLayout layoutHeaderMenu;
    LinearLayout layoutLoading;
    ListView listViewPemesanan;
    boolean loadingMore = false;
    NavigationView navigationView;
    int page = 1;
    int pages;
    PemesananAdapter pemesananAdapter;
    ArrayList<PemesananItem> pemesananItems = new ArrayList();
    View primaryMenu;
    Spinner spinnerFilter;
    TextView textViewIdToko;
    TextView textViewKategori;
    TextView textViewMember;
    TextView textViewNamaToko;
    TextView textViewPemesanan;
    TextView textViewProduk;
    String url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order";
    View viewHeader;

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.pemesananAdapter = null;
            this.pemesananItems = new ArrayList();
            this.listViewPemesanan.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)MainActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)MainActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest(MainActivity.this.url_pesanan, "POST", (HashMap<String, String>)hashMap);
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
                    MainActivity.this.loadingMore = false;
                    MainActivity.this.layoutLoading.setVisibility(8);
                    if (jSONObject == null) {
                        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                        Object[] arrobject = new String[]{String.valueOf((int)MainActivity.this.page)};
                        asyncTask.execute(arrobject);
                        return;
                    }
                    try {
                        int n = jSONObject.getInt("success");
                        jSONObject.getString("message");
                        if (n != 1) return;
                        MainActivity.this.pages = jSONObject.getInt("pages");
                        new PemesananItem();
                        JSONArray jSONArray = jSONObject.getJSONArray("pemesanan");
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            PemesananItem pemesananItem = new PemesananItem();
                            pemesananItem.setIdPesanan(jSONObject2.getString("idpesanan"));
                            pemesananItem.setNamaPesanan(jSONObject2.getString("namapesanan"));
                            pemesananItem.setKodePesanan(jSONObject2.getString("kodepesanan"));
                            pemesananItem.setWaktuPesanan(jSONObject2.getString("waktupesanan"));
                            pemesananItem.setStatusPesanan(jSONObject2.getString("statuspesanan"));
                            pemesananItem.setBuktiPembayaran(jSONObject2.getString("bukti_pembayaran"));
                            pemesananItem.setSaldo(jSONObject2.getString("saldo"));
                            pemesananItem.setAlamatPesanan(jSONObject2.getString("alamat"));
                            MainActivity.this.pemesananItems.add((Object)pemesananItem);
                        }
                        if (MainActivity.this.pemesananAdapter == null) {
                            MainActivity.this.pemesananAdapter = new PemesananAdapter((Activity)MainActivity.this, MainActivity.this.pemesananItems);
                            MainActivity.this.listViewPemesanan.setAdapter((ListAdapter)MainActivity.this.pemesananAdapter);
                        } else {
                            MainActivity.this.pemesananAdapter.setPemesanan(MainActivity.this.pemesananItems);
                            MainActivity.this.pemesananAdapter.notifyDataSetChanged();
                        }
                        MainActivity.this.page = 1 + MainActivity.this.page;
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    MainActivity.this.loadingMore = true;
                    MainActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
            return;
        } else {
            if (n != 3 || n2 != -1) return;
            return;
        }
    }

    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)this.findViewById(2131296471);
        if (drawerLayout.isDrawerOpen(8388611)) {
            drawerLayout.closeDrawer(8388611);
            return;
        }
        super.onBackPressed();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427382);
        this.dataPref = new DataPref((Context)this);
        Toolbar toolbar = (Toolbar)this.findViewById(2131297153);
        this.setSupportActionBar(toolbar);
        this.navigationView = (NavigationView)this.findViewById(2131296728);
        this.navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener)this);
        this.viewHeader = this.navigationView.getHeaderView(0);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Bikin Aplikasi Online Shop");
        DrawerLayout drawerLayout = (DrawerLayout)this.findViewById(2131296471);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle((Activity)this, drawerLayout, toolbar, 2131624004, 2131624003);
        drawerLayout.addDrawerListener((DrawerLayout.DrawerListener)actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.textViewIdToko = (TextView)this.viewHeader.findViewById(2131297062);
        this.textViewIdToko.setText((CharSequence)("ID: " + this.dataPref.getUsername()));
        this.imageButtonCari = (ImageButton)this.findViewById(2131296586);
        this.imageButtonCari.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.FindPesananActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        ((ImageButton)this.findViewById(2131296608)).setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent((Context)this.this$0, com.mmdfauzan.bamos.PemberitahuanActivity.class));
            }
        });
        this.buttonPemesanan = (Button)this.viewHeader.findViewById(2131296375);
        this.buttonStatistik = (Button)this.viewHeader.findViewById(2131296399);
        this.buttonKategori = (Button)this.viewHeader.findViewById(2131296361);
        this.buttonProduk = (Button)this.viewHeader.findViewById(2131296386);
        this.buttonMember = (Button)this.viewHeader.findViewById(2131296369);
        this.buttonDriver = (Button)this.viewHeader.findViewById(2131296342);
        this.buttonPembayaran = (Button)this.viewHeader.findViewById(2131296373);
        this.buttonPengiriman = (Button)this.viewHeader.findViewById(2131296376);
        this.buttonBanner = (Button)this.viewHeader.findViewById(2131296330);
        this.buttonVoucher = (Button)this.viewHeader.findViewById(2131296407);
        this.buttonInformasiToko = (Button)this.viewHeader.findViewById(2131296358);
        this.buttonKelola = (Button)this.viewHeader.findViewById(2131296362);
        this.buttonTutorial = (Button)this.viewHeader.findViewById(2131296403);
        this.buttonPemberitahuan = (Button)this.viewHeader.findViewById(2131296374);
        this.buttonChat = (Button)this.viewHeader.findViewById(2131296338);
        this.buttonWebsite = (Button)this.viewHeader.findViewById(2131296408);
        this.buttonLinkDownload = (Button)this.viewHeader.findViewById(2131296365);
        this.buttonEditAplikasi = (Button)this.viewHeader.findViewById(2131296345);
        this.buttonFiturPremium = (Button)this.viewHeader.findViewById(2131296383);
        this.buttonPetunjuk = (Button)this.viewHeader.findViewById(2131296379);
        this.buttonReset = (Button)this.viewHeader.findViewById(2131296388);
        this.buttonPersyaratan = (Button)this.viewHeader.findViewById(2131296378);
        this.buttonTentang = (Button)this.viewHeader.findViewById(2131296400);
        this.buttonLogout = (Button)this.viewHeader.findViewById(2131296367);
        this.buttonEVoucher = (Button)this.viewHeader.findViewById(2131296343);
        this.buttonTestimoni = (Button)this.viewHeader.findViewById(2131296401);
        this.buttonHapusAkun = (Button)this.viewHeader.findViewById(2131296355);
        this.buttonFiturPremiumBig = (Button)this.viewHeader.findViewById(2131296349);
        this.buttonInformation = (Button)this.viewHeader.findViewById(2131296359);
        this.buttonMenu = (Button)this.viewHeader.findViewById(2131296370);
        this.buttonInformation.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent((Context)this.this$0, com.mmdfauzan.bamos.PemberitahuanActivity.class));
            }
        });
        this.textViewNamaToko = (TextView)this.viewHeader.findViewById(2131297106);
        this.textViewKategori = (TextView)this.viewHeader.findViewById(2131297070);
        this.textViewProduk = (TextView)this.viewHeader.findViewById(2131297123);
        this.textViewMember = (TextView)this.viewHeader.findViewById(2131297089);
        this.textViewPemesanan = (TextView)this.viewHeader.findViewById(2131297110);
        this.imageViewVerified = (ImageView)this.viewHeader.findViewById(2131296631);
        this.imageViewProfile = (ImageView)this.viewHeader.findViewById(2131296629);
        this.layoutHeaderMenu = (LinearLayout)this.viewHeader.findViewById(2131296664);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.listViewPemesanan = (ListView)this.findViewById(2131296714);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)MainActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)MainActivity.this.dataPref.getToken());
                    hashMap.put((Object)"fcmtoken", (Object)MainActivity.this.dataPref.getFcmToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/check_login", "POST", (HashMap<String, String>)hashMap);
                    Log.e((String)"WEWWW", (String)jSONObject.toString());
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
                if (jSONObject != null) {
                    try {
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            JSONObject jSONObject2 = jSONObject.getJSONArray("data").getJSONObject(0);
                            MainActivity.this.dataPref.setColor(jSONObject2.getString("color"));
                            MainActivity.this.dataPref.setSaldo(jSONObject2.getString("saldo"));
                            MainActivity.this.dataPref.setWebsite(jSONObject2.getString("website"));
                            MainActivity.this.textViewNamaToko.setText((CharSequence)jSONObject2.getString("namatoko"));
                            MainActivity.this.textViewKategori.setText((CharSequence)jSONObject2.getString("kategori"));
                            MainActivity.this.textViewProduk.setText((CharSequence)jSONObject2.getString("produk"));
                            MainActivity.this.textViewMember.setText((CharSequence)jSONObject2.getString("member"));
                            MainActivity.this.textViewPemesanan.setText((CharSequence)jSONObject2.getString("pemesanan"));
                            if (jSONObject2.getString("premium").equals((Object)"1")) {
                                MainActivity.this.imageViewVerified.setVisibility(0);
                            } else {
                                MainActivity.this.imageViewVerified.setVisibility(8);
                            }
                            MainActivity.this.layoutHeaderMenu.setBackgroundColor(Color.parseColor((String)jSONObject2.getString("warna")));
                            return;
                        }
                        if (n != 0) return;
                        {
                            Toast.makeText((Context)MainActivity.this, (CharSequence)string, (int)1).show();
                            MainActivity.this.dataPref.setLogin(null, null, null, false);
                            MainActivity.this.startActivity(new Intent((Context)MainActivity.this, LoginActivity.class));
                            MainActivity.this.finish();
                            return;
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Toast.makeText((Context)MainActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Cek koneksi kamu dan coba lagi nanti.", (int)1).show();
                MainActivity.this.finish();
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)MainActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                if (MainActivity.this.firstOpen) {
                    this.progressDialog.show();
                }
            }
        }.execute((Object[])new String[0]);
        Picasso.with((Context)this).load("http://os.bikinaplikasi.com/api/data/get_image.php?u=" + this.dataPref.getUsername()).placeholder(2131230933).into(this.imageViewProfile);
        this.spinnerFilter = (Spinner)this.findViewById(2131296820);
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource((Context)this.getApplicationContext(), (int)2130903040, (int)2131427451);
        this.spinnerFilter.setAdapter((SpinnerAdapter)arrayAdapter);
        this.spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                if (n == 0) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order";
                } else if (n == 1) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order?f=1";
                } else if (n == 2) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order?f=2";
                } else if (n == 3) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order?f=4";
                } else if (n == 4) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order?f=5";
                } else if (n == 5) {
                    this.this$0.url_pesanan = "http://os.bikinaplikasi.com/api/admin_api_v2/get_order?f=3";
                }
                this.this$0.pemesananAdapter = null;
                this.this$0.pemesananItems = new ArrayList();
                this.this$0.listViewPemesanan.setAdapter(null);
                this.this$0.page = 1;
                this.this$0.pages = 1;
                AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                asyncTask.execute(arrobject);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.listViewPemesanan.setOnItemClickListener(new AdapterView.OnItemClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PemesananDetilActivity.class);
                intent.putExtra("idpesanan", ((PemesananItem)this.this$0.pemesananItems.get(n)).getIdPesanan());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewPemesanan.setOnScrollListener(new AbsListView.OnScrollListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.buttonPetunjuk.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PetunjukActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonStatistik.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.StatistikActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonPemesanan.setOnClickListener(new View.OnClickListener((MainActivity)this, drawerLayout){
            final /* synthetic */ MainActivity this$0;
            final /* synthetic */ DrawerLayout val$drawer;
            {
                this.this$0 = mainActivity;
                this.val$drawer = drawerLayout;
            }

            public void onClick(View view) {
                if (this.val$drawer.isDrawerOpen(8388611)) {
                    this.val$drawer.closeDrawer(8388611);
                }
            }
        });
        this.buttonProduk.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ProdukActivity.class);
                this.this$0.startActivityForResult(intent, 3);
            }
        });
        this.buttonEVoucher.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.EVoucherActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonKategori.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.KategoriActivity.class);
                this.this$0.startActivityForResult(intent, 3);
            }
        });
        this.buttonMember.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.MemberActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonTestimoni.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.TestimoniActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonDriver.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.DriverActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonPembayaran.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PembayaranActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonPersyaratan.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PersyaratanActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonTentang.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.TentangActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonPengiriman.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.PengirimanActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonFiturPremium.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.FiturPremiumActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonFiturPremiumBig.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.FiturPremiumActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonBanner.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.BannerListActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonVoucher.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.VoucherListActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonPemberitahuan.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.BuatPemberitahuanActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonChat.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonInformasiToko.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.TokoActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonKelola.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", android.net.Uri.parse((String)"http://os.bikinaplikasi.com/admin")));
            }
        });
        this.buttonTutorial.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", android.net.Uri.parse((String)"http://tutorial.bikinaplikasi.com")));
            }
        });
        this.buttonWebsite.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.WebsiteActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonLinkDownload.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.LinkActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonMenu.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.MenuActivity.class);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonReset.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", android.net.Uri.parse((String)"http://os.bikinaplikasi.com/lupapassword")));
            }
        });
        this.buttonEditAplikasi.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.BikinFileActivity.class);
                intent.putExtra("un", "");
                intent.putExtra("edit_aplikasi", true);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonHapusAkun.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                this.this$0.promptDeleteAccount();
            }
        });
        this.buttonLogout.setOnClickListener(new View.OnClickListener((MainActivity)this){
            final /* synthetic */ MainActivity this$0;
            {
                this.this$0 = mainActivity;
            }

            public void onClick(View view) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)MainActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)MainActivity.this.dataPref.getToken());
                            hashMap.put((Object)"fcmtoken", (Object)MainActivity.this.dataPref.getFcmToken());
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/logout", "POST", (HashMap<String, String>)hashMap);
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
                            String string;
                            try {
                                int n = jSONObject.getInt("success");
                                string = jSONObject.getString("message");
                                if (n != 1) return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                            Toast.makeText((Context)MainActivity.this, (CharSequence)string, (int)1).show();
                            MainActivity.this.dataPref.setLogin(null, null, null, false);
                            MainActivity.this.startActivity(new Intent((Context)MainActivity.this, LoginActivity.class));
                            MainActivity.this.finish();
                            return;
                        }
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)MainActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        boolean bl = ContextCompat.checkSelfPermission((Context)this, (String)"android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        boolean bl2 = ContextCompat.checkSelfPermission((Context)this, (String)"android.permission.ACCESS_FINE_LOCATION") == 0;
        String[] arrstring = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION"};
        if (!bl || !bl2) {
            ActivityCompat.requestPermissions((Activity)this, (String[])arrstring, (int)1);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        block5 : {
            super.onRequestPermissionsResult(n, arrstring, arrn);
            switch (n) {
                case 112: {
                    if (arrn.length <= 0 || arrn[0] != 0) break;
                }
                case 111: {
                    break block5;
                }
            }
            Toast.makeText((Context)this, (CharSequence)"Mohon izinkan aplikasi untuk mengakses penyimpanan data.", (int)1).show();
        }
        if (arrn.length <= 0 || arrn[0] != 0) {
            Toast.makeText((Context)this, (CharSequence)"Mohon izinkan aplikasi untuk mengakses lokasi.", (int)1).show();
            return;
        }
    }

    public void promptDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        View view = ((LayoutInflater)this.getSystemService("layout_inflater")).inflate(2131427429, null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        EditText editText = (EditText)view.findViewById(2131296533);
        ((Button)view.findViewById(2131296355)).setOnClickListener(new View.OnClickListener(this, editText, alertDialog){
            final /* synthetic */ MainActivity this$0;
            final /* synthetic */ AlertDialog val$b;
            final /* synthetic */ EditText val$editTextPassword;
            {
                this.this$0 = mainActivity;
                this.val$editTextPassword = editText;
                this.val$b = alertDialog;
            }

            public void onClick(View view) {
                String string = this.val$editTextPassword.getText().toString();
                if (string.length() > 3) {
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)MainActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)MainActivity.this.dataPref.getToken());
                                hashMap.put((Object)"fcmtoken", (Object)MainActivity.this.dataPref.getFcmToken());
                                hashMap.put((Object)"password", (Object)arrstring[0]);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_account", "POST", (HashMap<String, String>)hashMap);
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
                            if (jSONObject != null) {
                                try {
                                    int n = jSONObject.getInt("success");
                                    String string = jSONObject.getString("message");
                                    if (n == 1) {
                                        Toast.makeText((Context)MainActivity.this, (CharSequence)string, (int)1).show();
                                        MainActivity.this.dataPref.setLogin(null, null, null, false);
                                        MainActivity.this.startActivity(new Intent((Context)MainActivity.this, LoginActivity.class));
                                        MainActivity.this.finish();
                                        return;
                                    }
                                    Toast.makeText((Context)MainActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                }
                            }
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)MainActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Menghapus akun.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[]{string});
                    this.val$b.dismiss();
                }
            }
        });
        alertDialog.show();
    }

}

