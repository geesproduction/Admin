/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.os.StrictMode$ThreadPolicy$Builder
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.LinearLayout
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
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
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import com.mmdfauzan.bamos.AddCategoryActivity;
import com.mmdfauzan.bamos.AddProductActivity;
import com.mmdfauzan.bamos.EditGambarActivity;
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

public class AddProductActivity
extends AppCompatActivity {
    String beratProduk;
    Button buttonAddHargaGrosir;
    Button buttonAddVarian;
    Button buttonDelete;
    Button buttonEditGambar;
    Button buttonSave;
    DataPref dataPref;
    String deskripsiProduk;
    EditText editTextBeratProduk;
    EditText editTextDeskripsiProduk;
    EditText editTextHarga;
    EditText editTextHargaGrosir1;
    EditText editTextHargaGrosir1Minimal;
    EditText editTextHargaGrosir2;
    EditText editTextHargaGrosir2Minimal;
    EditText editTextHargaGrosir3;
    EditText editTextHargaGrosir3Minimal;
    EditText editTextHargaGrosir4;
    EditText editTextHargaGrosir4Minimal;
    EditText editTextHargaGrosir5;
    EditText editTextHargaGrosir5Minimal;
    EditText editTextHargaLama;
    EditText editTextLinkBukalapak;
    EditText editTextLinkShopee;
    EditText editTextLinkTokopedia;
    EditText editTextNamaProduk;
    EditText editTextNamaVarian1;
    EditText editTextNamaVarian10;
    EditText editTextNamaVarian2;
    EditText editTextNamaVarian3;
    EditText editTextNamaVarian4;
    EditText editTextNamaVarian5;
    EditText editTextNamaVarian6;
    EditText editTextNamaVarian7;
    EditText editTextNamaVarian8;
    EditText editTextNamaVarian9;
    EditText editTextStokVarian1;
    EditText editTextStokVarian10;
    EditText editTextStokVarian2;
    EditText editTextStokVarian3;
    EditText editTextStokVarian4;
    EditText editTextStokVarian5;
    EditText editTextStokVarian6;
    EditText editTextStokVarian7;
    EditText editTextStokVarian8;
    EditText editTextStokVarian9;
    String errorMessage = "Mohon lengkapi isian.";
    String harga;
    String hargaGrosir1;
    String hargaGrosir1Minimal;
    String hargaGrosir2;
    String hargaGrosir2Minimal;
    String hargaGrosir3;
    String hargaGrosir3Minimal;
    String hargaGrosir4;
    String hargaGrosir4Minimal;
    String hargaGrosir5;
    String hargaGrosir5Minimal;
    String hargaLama;
    ArrayList<KategoriItem> kategoriItems = new ArrayList();
    String kategoriProduk;
    LinearLayout layoutHargaGrosir1;
    LinearLayout layoutHargaGrosir2;
    LinearLayout layoutHargaGrosir3;
    LinearLayout layoutHargaGrosir4;
    LinearLayout layoutHargaGrosir5;
    LinearLayout layoutVarian10;
    LinearLayout layoutVarian2;
    LinearLayout layoutVarian3;
    LinearLayout layoutVarian4;
    LinearLayout layoutVarian5;
    LinearLayout layoutVarian6;
    LinearLayout layoutVarian7;
    LinearLayout layoutVarian8;
    LinearLayout layoutVarian9;
    String linkBukalapak;
    String linkShopee;
    String linkTokopedia;
    String namaProduk;
    String namaVarian1;
    String namaVarian10;
    String namaVarian2;
    String namaVarian3;
    String namaVarian4;
    String namaVarian5;
    String namaVarian6;
    String namaVarian7;
    String namaVarian8;
    String namaVarian9;
    boolean productEdit = false;
    String selectedIdKategori;
    String selectedIdProduct;
    int selectedKategori = 0;
    ShowToast showToast;
    Spinner spinnerKategori;
    String stokVarian1;
    String stokVarian10;
    String stokVarian2;
    String stokVarian3;
    String stokVarian4;
    String stokVarian5;
    String stokVarian6;
    String stokVarian7;
    String stokVarian8;
    String stokVarian9;
    int visibleGrosir = 1;
    int visibleVarian = 1;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427357);
        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }
        this.dataPref = new DataPref((Context)this);
        Intent intent = this.getIntent();
        this.productEdit = intent.getBooleanExtra("produk_edit", false);
        this.getSupportActionBar().setElevation(0.0f);
        this.showToast = new ShowToast((Context)this);
        if (this.productEdit) {
            this.getSupportActionBar().setTitle((CharSequence)"Edit Produk");
        } else {
            this.getSupportActionBar().setTitle((CharSequence)"Produk Baru");
        }
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.spinnerKategori = (Spinner)this.findViewById(2131296824);
        this.layoutVarian2 = (LinearLayout)this.findViewById(2131296686);
        this.layoutVarian3 = (LinearLayout)this.findViewById(2131296687);
        this.layoutVarian4 = (LinearLayout)this.findViewById(2131296688);
        this.layoutVarian5 = (LinearLayout)this.findViewById(2131296689);
        this.layoutVarian6 = (LinearLayout)this.findViewById(2131296690);
        this.layoutVarian7 = (LinearLayout)this.findViewById(2131296691);
        this.layoutVarian8 = (LinearLayout)this.findViewById(2131296692);
        this.layoutVarian9 = (LinearLayout)this.findViewById(2131296693);
        this.layoutVarian10 = (LinearLayout)this.findViewById(2131296685);
        this.layoutVarian2.setVisibility(8);
        this.layoutVarian3.setVisibility(8);
        this.layoutVarian4.setVisibility(8);
        this.layoutVarian5.setVisibility(8);
        this.layoutVarian6.setVisibility(8);
        this.layoutVarian7.setVisibility(8);
        this.layoutVarian8.setVisibility(8);
        this.layoutVarian9.setVisibility(8);
        this.layoutVarian10.setVisibility(8);
        this.layoutHargaGrosir1 = (LinearLayout)this.findViewById(2131296659);
        this.layoutHargaGrosir2 = (LinearLayout)this.findViewById(2131296660);
        this.layoutHargaGrosir3 = (LinearLayout)this.findViewById(2131296661);
        this.layoutHargaGrosir4 = (LinearLayout)this.findViewById(2131296662);
        this.layoutHargaGrosir5 = (LinearLayout)this.findViewById(2131296663);
        this.editTextHargaGrosir1 = (EditText)this.findViewById(2131296486);
        this.editTextHargaGrosir2 = (EditText)this.findViewById(2131296488);
        this.editTextHargaGrosir3 = (EditText)this.findViewById(2131296490);
        this.editTextHargaGrosir4 = (EditText)this.findViewById(2131296492);
        this.editTextHargaGrosir5 = (EditText)this.findViewById(2131296494);
        this.editTextHargaGrosir1Minimal = (EditText)this.findViewById(2131296487);
        this.editTextHargaGrosir2Minimal = (EditText)this.findViewById(2131296489);
        this.editTextHargaGrosir3Minimal = (EditText)this.findViewById(2131296491);
        this.editTextHargaGrosir4Minimal = (EditText)this.findViewById(2131296493);
        this.editTextHargaGrosir5Minimal = (EditText)this.findViewById(2131296495);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonAddVarian = (Button)this.findViewById(2131296328);
        this.buttonAddHargaGrosir = (Button)this.findViewById(2131296327);
        this.buttonEditGambar = (Button)this.findViewById(2131296346);
        this.buttonEditGambar.setVisibility(8);
        this.buttonDelete = (Button)this.findViewById(2131296340);
        this.buttonDelete.setVisibility(8);
        this.editTextNamaProduk = (EditText)this.findViewById(2131296518);
        this.editTextDeskripsiProduk = (EditText)this.findViewById(2131296482);
        this.editTextHarga = (EditText)this.findViewById(2131296485);
        this.editTextHargaLama = (EditText)this.findViewById(2131296496);
        this.editTextBeratProduk = (EditText)this.findViewById(2131296477);
        this.editTextNamaVarian1 = (EditText)this.findViewById(2131296520);
        this.editTextNamaVarian2 = (EditText)this.findViewById(2131296522);
        this.editTextNamaVarian3 = (EditText)this.findViewById(2131296523);
        this.editTextNamaVarian4 = (EditText)this.findViewById(2131296524);
        this.editTextNamaVarian5 = (EditText)this.findViewById(2131296525);
        this.editTextNamaVarian6 = (EditText)this.findViewById(2131296526);
        this.editTextNamaVarian7 = (EditText)this.findViewById(2131296527);
        this.editTextNamaVarian8 = (EditText)this.findViewById(2131296528);
        this.editTextNamaVarian9 = (EditText)this.findViewById(2131296529);
        this.editTextNamaVarian10 = (EditText)this.findViewById(2131296521);
        this.editTextStokVarian1 = (EditText)this.findViewById(2131296545);
        this.editTextStokVarian2 = (EditText)this.findViewById(2131296547);
        this.editTextStokVarian3 = (EditText)this.findViewById(2131296548);
        this.editTextStokVarian4 = (EditText)this.findViewById(2131296549);
        this.editTextStokVarian5 = (EditText)this.findViewById(2131296550);
        this.editTextStokVarian6 = (EditText)this.findViewById(2131296551);
        this.editTextStokVarian7 = (EditText)this.findViewById(2131296552);
        this.editTextStokVarian8 = (EditText)this.findViewById(2131296553);
        this.editTextStokVarian9 = (EditText)this.findViewById(2131296554);
        this.editTextStokVarian10 = (EditText)this.findViewById(2131296546);
        this.editTextLinkBukalapak = (EditText)this.findViewById(2131296507);
        this.editTextLinkTokopedia = (EditText)this.findViewById(2131296510);
        this.editTextLinkShopee = (EditText)this.findViewById(2131296509);
        this.spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener((AddProductActivity)this){
            final /* synthetic */ AddProductActivity this$0;
            {
                this.this$0 = addProductActivity;
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                this.this$0.selectedKategori = n;
                this.this$0.kategoriProduk = ((KategoriItem)this.this$0.kategoriItems.get(n)).getIdKategori();
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.buttonAddVarian.setOnClickListener(new View.OnClickListener((AddProductActivity)this){
            final /* synthetic */ AddProductActivity this$0;
            {
                this.this$0 = addProductActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                if (this.this$0.visibleVarian == 1) {
                    this.this$0.layoutVarian2.setVisibility(0);
                    this.this$0.visibleVarian = 2;
                    return;
                } else {
                    if (this.this$0.visibleVarian == 2) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.visibleVarian = 3;
                        return;
                    }
                    if (this.this$0.visibleVarian == 3) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.visibleVarian = 4;
                        return;
                    }
                    if (this.this$0.visibleVarian == 4) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.visibleVarian = 5;
                        return;
                    }
                    if (this.this$0.visibleVarian == 5) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.layoutVarian6.setVisibility(0);
                        this.this$0.visibleVarian = 6;
                        return;
                    }
                    if (this.this$0.visibleVarian == 6) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.layoutVarian6.setVisibility(0);
                        this.this$0.layoutVarian7.setVisibility(0);
                        this.this$0.visibleVarian = 7;
                        return;
                    }
                    if (this.this$0.visibleVarian == 7) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.layoutVarian6.setVisibility(0);
                        this.this$0.layoutVarian7.setVisibility(0);
                        this.this$0.layoutVarian8.setVisibility(0);
                        this.this$0.visibleVarian = 8;
                        return;
                    }
                    if (this.this$0.visibleVarian == 8) {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.layoutVarian6.setVisibility(0);
                        this.this$0.layoutVarian7.setVisibility(0);
                        this.this$0.layoutVarian8.setVisibility(0);
                        this.this$0.layoutVarian9.setVisibility(0);
                        this.this$0.visibleVarian = 9;
                        return;
                    }
                    if (this.this$0.visibleVarian != 9) return;
                    {
                        this.this$0.layoutVarian2.setVisibility(0);
                        this.this$0.layoutVarian3.setVisibility(0);
                        this.this$0.layoutVarian4.setVisibility(0);
                        this.this$0.layoutVarian5.setVisibility(0);
                        this.this$0.layoutVarian6.setVisibility(0);
                        this.this$0.layoutVarian7.setVisibility(0);
                        this.this$0.layoutVarian8.setVisibility(0);
                        this.this$0.layoutVarian9.setVisibility(0);
                        this.this$0.layoutVarian10.setVisibility(0);
                        this.this$0.visibleVarian = 10;
                        this.this$0.buttonAddVarian.setVisibility(8);
                        return;
                    }
                }
            }
        });
        this.buttonAddHargaGrosir.setOnClickListener(new View.OnClickListener((AddProductActivity)this){
            final /* synthetic */ AddProductActivity this$0;
            {
                this.this$0 = addProductActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                String string = this.this$0.editTextHargaGrosir1.getText().toString();
                String string2 = this.this$0.editTextHargaGrosir2.getText().toString();
                String string3 = this.this$0.editTextHargaGrosir3.getText().toString();
                String string4 = this.this$0.editTextHargaGrosir4.getText().toString();
                this.this$0.editTextHargaGrosir5.getText().toString();
                String string5 = this.this$0.editTextHargaGrosir1Minimal.getText().toString();
                String string6 = this.this$0.editTextHargaGrosir2Minimal.getText().toString();
                String string7 = this.this$0.editTextHargaGrosir3Minimal.getText().toString();
                String string8 = this.this$0.editTextHargaGrosir4Minimal.getText().toString();
                this.this$0.editTextHargaGrosir5Minimal.getText().toString();
                if (this.this$0.visibleGrosir == 1) {
                    if (string5.equals((Object)"") || string.equals((Object)"")) return;
                    {
                        this.this$0.layoutHargaGrosir2.setVisibility(0);
                        this.this$0.visibleGrosir = 2;
                    }
                    return;
                }
                if (this.this$0.visibleGrosir == 2) {
                    if (string6.equals((Object)"") || string2.equals((Object)"")) return;
                    {
                        if (java.lang.Integer.parseInt((String)string6) > java.lang.Integer.parseInt((String)string5)) {
                            this.this$0.layoutHargaGrosir3.setVisibility(0);
                            this.this$0.visibleGrosir = 3;
                            return;
                        }
                        this.this$0.showToast.Toast("Minimal barang harus lebih besar dari sebelumnya.");
                        return;
                    }
                }
                if (this.this$0.visibleGrosir == 3) {
                    if (string7.equals((Object)"") || string3.equals((Object)"")) return;
                    {
                        if (java.lang.Integer.parseInt((String)string7) > java.lang.Integer.parseInt((String)string6)) {
                            this.this$0.layoutHargaGrosir4.setVisibility(0);
                            this.this$0.visibleGrosir = 4;
                            return;
                        }
                        this.this$0.showToast.Toast("Minimal barang harus lebih besar dari sebelumnya.");
                        return;
                    }
                }
                if (this.this$0.visibleGrosir != 4 || string8.equals((Object)"") || string4.equals((Object)"")) return;
                {
                    if (java.lang.Integer.parseInt((String)string8) > java.lang.Integer.parseInt((String)string7)) {
                        this.this$0.layoutHargaGrosir5.setVisibility(0);
                        this.this$0.visibleGrosir = 5;
                        this.this$0.buttonAddHargaGrosir.setVisibility(8);
                        return;
                    }
                }
                this.this$0.showToast.Toast("Minimal barang harus lebih besar dari sebelumnya.");
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((AddProductActivity)this){
            final /* synthetic */ AddProductActivity this$0;
            {
                this.this$0 = addProductActivity;
            }

            public void onClick(View view) {
                this.this$0.namaProduk = this.this$0.editTextNamaProduk.getText().toString();
                this.this$0.deskripsiProduk = this.this$0.editTextDeskripsiProduk.getText().toString();
                this.this$0.harga = this.this$0.editTextHarga.getText().toString();
                this.this$0.hargaLama = this.this$0.editTextHargaLama.getText().toString();
                this.this$0.beratProduk = this.this$0.editTextBeratProduk.getText().toString();
                this.this$0.namaVarian1 = this.this$0.editTextNamaVarian1.getText().toString();
                this.this$0.stokVarian1 = this.this$0.editTextStokVarian1.getText().toString();
                this.this$0.namaVarian2 = this.this$0.editTextNamaVarian2.getText().toString();
                this.this$0.stokVarian2 = this.this$0.editTextStokVarian2.getText().toString();
                this.this$0.namaVarian3 = this.this$0.editTextNamaVarian3.getText().toString();
                this.this$0.stokVarian3 = this.this$0.editTextStokVarian3.getText().toString();
                this.this$0.namaVarian4 = this.this$0.editTextNamaVarian4.getText().toString();
                this.this$0.stokVarian4 = this.this$0.editTextStokVarian4.getText().toString();
                this.this$0.namaVarian5 = this.this$0.editTextNamaVarian5.getText().toString();
                this.this$0.stokVarian5 = this.this$0.editTextStokVarian5.getText().toString();
                this.this$0.namaVarian6 = this.this$0.editTextNamaVarian6.getText().toString();
                this.this$0.stokVarian6 = this.this$0.editTextStokVarian6.getText().toString();
                this.this$0.namaVarian7 = this.this$0.editTextNamaVarian7.getText().toString();
                this.this$0.stokVarian7 = this.this$0.editTextStokVarian7.getText().toString();
                this.this$0.namaVarian8 = this.this$0.editTextNamaVarian8.getText().toString();
                this.this$0.stokVarian8 = this.this$0.editTextStokVarian8.getText().toString();
                this.this$0.namaVarian9 = this.this$0.editTextNamaVarian9.getText().toString();
                this.this$0.stokVarian9 = this.this$0.editTextStokVarian9.getText().toString();
                this.this$0.namaVarian10 = this.this$0.editTextNamaVarian10.getText().toString();
                this.this$0.stokVarian10 = this.this$0.editTextStokVarian10.getText().toString();
                this.this$0.linkBukalapak = this.this$0.editTextLinkBukalapak.getText().toString();
                this.this$0.linkTokopedia = this.this$0.editTextLinkTokopedia.getText().toString();
                this.this$0.linkShopee = this.this$0.editTextLinkShopee.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.namaProduk.length() > 3) {
                    this.this$0.editTextNamaProduk.setError(null);
                    if (this.this$0.deskripsiProduk.length() > 5) {
                        this.this$0.editTextDeskripsiProduk.setError(null);
                        if (this.this$0.harga.length() > 0) {
                            this.this$0.editTextHarga.setError(null);
                            this.this$0.hargaGrosir1 = this.this$0.editTextHargaGrosir1.getText().toString();
                            this.this$0.hargaGrosir2 = this.this$0.editTextHargaGrosir2.getText().toString();
                            this.this$0.hargaGrosir3 = this.this$0.editTextHargaGrosir3.getText().toString();
                            this.this$0.hargaGrosir4 = this.this$0.editTextHargaGrosir4.getText().toString();
                            this.this$0.hargaGrosir5 = this.this$0.editTextHargaGrosir5.getText().toString();
                            this.this$0.hargaGrosir1Minimal = this.this$0.editTextHargaGrosir1Minimal.getText().toString();
                            this.this$0.hargaGrosir2Minimal = this.this$0.editTextHargaGrosir2Minimal.getText().toString();
                            this.this$0.hargaGrosir3Minimal = this.this$0.editTextHargaGrosir3Minimal.getText().toString();
                            this.this$0.hargaGrosir4Minimal = this.this$0.editTextHargaGrosir4Minimal.getText().toString();
                            this.this$0.hargaGrosir5Minimal = this.this$0.editTextHargaGrosir5Minimal.getText().toString();
                            if (this.this$0.hargaGrosir1Minimal.equals((Object)"")) {
                                this.this$0.hargaGrosir1 = "0";
                                this.this$0.hargaGrosir1Minimal = "0";
                            }
                            if (this.this$0.hargaGrosir2Minimal.equals((Object)"")) {
                                this.this$0.hargaGrosir2 = "0";
                                this.this$0.hargaGrosir2Minimal = "0";
                            }
                            if (this.this$0.hargaGrosir3Minimal.equals((Object)"")) {
                                this.this$0.hargaGrosir3 = "0";
                                this.this$0.hargaGrosir3Minimal = "0";
                            }
                            if (this.this$0.hargaGrosir4Minimal.equals((Object)"")) {
                                this.this$0.hargaGrosir4 = "0";
                                this.this$0.hargaGrosir4Minimal = "0";
                            }
                            if (this.this$0.hargaGrosir5Minimal.equals((Object)"")) {
                                this.this$0.hargaGrosir5 = "0";
                                this.this$0.hargaGrosir5Minimal = "0";
                            }
                            if (this.this$0.beratProduk.length() > 0) {
                                this.this$0.editTextBeratProduk.setError(null);
                                if (this.this$0.namaVarian1.length() > 0) {
                                    this.this$0.editTextNamaVarian1.setError(null);
                                    if (this.this$0.stokVarian1.length() > 0) {
                                        this.this$0.editTextStokVarian1.setError(null);
                                        if (!this.this$0.kategoriProduk.equals(null)) {
                                            if (this.this$0.productEdit) {
                                                new AsyncTask<String, String, JSONObject>(){
                                                    JSONParser jsonParser = new JSONParser();
                                                    ProgressDialog progressDialog;

                                                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                                        try {
                                                            HashMap hashMap = new HashMap();
                                                            hashMap.put((Object)"email", (Object)AddProductActivity.this.dataPref.getEmail());
                                                            hashMap.put((Object)"token", (Object)AddProductActivity.this.dataPref.getToken());
                                                            hashMap.put((Object)"idbarang", (Object)AddProductActivity.this.selectedIdProduct);
                                                            hashMap.put((Object)"nama", (Object)AddProductActivity.this.namaProduk);
                                                            hashMap.put((Object)"idkategori", (Object)AddProductActivity.this.kategoriProduk);
                                                            hashMap.put((Object)"deskripsi", (Object)AddProductActivity.this.deskripsiProduk);
                                                            hashMap.put((Object)"berat", (Object)AddProductActivity.this.beratProduk);
                                                            hashMap.put((Object)"harga", (Object)AddProductActivity.this.harga);
                                                            hashMap.put((Object)"harga_lama", (Object)AddProductActivity.this.hargaLama);
                                                            hashMap.put((Object)"harga_grosir1", (Object)AddProductActivity.this.hargaGrosir1);
                                                            hashMap.put((Object)"harga_grosir1_minimal", (Object)AddProductActivity.this.hargaGrosir1Minimal);
                                                            hashMap.put((Object)"harga_grosir2", (Object)AddProductActivity.this.hargaGrosir2);
                                                            hashMap.put((Object)"harga_grosir2_minimal", (Object)AddProductActivity.this.hargaGrosir2Minimal);
                                                            hashMap.put((Object)"harga_grosir3", (Object)AddProductActivity.this.hargaGrosir3);
                                                            hashMap.put((Object)"harga_grosir3_minimal", (Object)AddProductActivity.this.hargaGrosir3Minimal);
                                                            hashMap.put((Object)"harga_grosir4", (Object)AddProductActivity.this.hargaGrosir4);
                                                            hashMap.put((Object)"harga_grosir4_minimal", (Object)AddProductActivity.this.hargaGrosir4Minimal);
                                                            hashMap.put((Object)"harga_grosir5", (Object)AddProductActivity.this.hargaGrosir5);
                                                            hashMap.put((Object)"harga_grosir5_minimal", (Object)AddProductActivity.this.hargaGrosir5Minimal);
                                                            hashMap.put((Object)"variasi1_nama", (Object)AddProductActivity.this.namaVarian1);
                                                            hashMap.put((Object)"variasi1_jumlah", (Object)AddProductActivity.this.stokVarian1);
                                                            hashMap.put((Object)"variasi2_nama", (Object)AddProductActivity.this.namaVarian2);
                                                            hashMap.put((Object)"variasi2_jumlah", (Object)AddProductActivity.this.stokVarian2);
                                                            hashMap.put((Object)"variasi3_nama", (Object)AddProductActivity.this.namaVarian3);
                                                            hashMap.put((Object)"variasi3_jumlah", (Object)AddProductActivity.this.stokVarian3);
                                                            hashMap.put((Object)"variasi4_nama", (Object)AddProductActivity.this.namaVarian4);
                                                            hashMap.put((Object)"variasi4_jumlah", (Object)AddProductActivity.this.stokVarian4);
                                                            hashMap.put((Object)"variasi5_nama", (Object)AddProductActivity.this.namaVarian5);
                                                            hashMap.put((Object)"variasi5_jumlah", (Object)AddProductActivity.this.stokVarian5);
                                                            hashMap.put((Object)"variasi6_nama", (Object)AddProductActivity.this.namaVarian6);
                                                            hashMap.put((Object)"variasi6_jumlah", (Object)AddProductActivity.this.stokVarian6);
                                                            hashMap.put((Object)"variasi7_nama", (Object)AddProductActivity.this.namaVarian7);
                                                            hashMap.put((Object)"variasi7_jumlah", (Object)AddProductActivity.this.stokVarian7);
                                                            hashMap.put((Object)"variasi8_nama", (Object)AddProductActivity.this.namaVarian8);
                                                            hashMap.put((Object)"variasi8_jumlah", (Object)AddProductActivity.this.stokVarian8);
                                                            hashMap.put((Object)"variasi9_nama", (Object)AddProductActivity.this.namaVarian9);
                                                            hashMap.put((Object)"variasi9_jumlah", (Object)AddProductActivity.this.stokVarian9);
                                                            hashMap.put((Object)"variasi10_nama", (Object)AddProductActivity.this.namaVarian10);
                                                            hashMap.put((Object)"variasi10_jumlah", (Object)AddProductActivity.this.stokVarian10);
                                                            hashMap.put((Object)"link_bukalapak", (Object)AddProductActivity.this.linkBukalapak);
                                                            hashMap.put((Object)"link_tokopedia", (Object)AddProductActivity.this.linkTokopedia);
                                                            hashMap.put((Object)"link_shopee", (Object)AddProductActivity.this.linkShopee);
                                                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/edit_product", "POST", (HashMap<String, String>)hashMap);
                                                            Log.d((String)"JSON result", (String)jSONObject.toString());
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
                                                                    Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                                    AddProductActivity.this.setResult(-1);
                                                                    AddProductActivity.this.finish();
                                                                    return;
                                                                }
                                                                if (n == 0) {
                                                                    Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                                    return;
                                                                }
                                                                break block8;
                                                            }
                                                            Toast.makeText((Context)AddProductActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                                                        }
                                                    }

                                                    protected void onPreExecute() {
                                                        this.progressDialog = new ProgressDialog((Context)AddProductActivity.this);
                                                        this.progressDialog.setMessage((CharSequence)"Mengubah produk.");
                                                        this.progressDialog.setIndeterminate(false);
                                                        this.progressDialog.setCancelable(false);
                                                        this.progressDialog.show();
                                                    }
                                                }.execute((Object[])new String[0]);
                                                return;
                                            }
                                            new AsyncTask<String, String, JSONObject>(){
                                                JSONParser jsonParser = new JSONParser();
                                                ProgressDialog progressDialog;

                                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                                    try {
                                                        HashMap hashMap = new HashMap();
                                                        hashMap.put((Object)"email", (Object)AddProductActivity.this.dataPref.getEmail());
                                                        hashMap.put((Object)"token", (Object)AddProductActivity.this.dataPref.getToken());
                                                        hashMap.put((Object)"nama", (Object)AddProductActivity.this.namaProduk);
                                                        hashMap.put((Object)"idkategori", (Object)AddProductActivity.this.kategoriProduk);
                                                        hashMap.put((Object)"deskripsi", (Object)AddProductActivity.this.deskripsiProduk);
                                                        hashMap.put((Object)"berat", (Object)AddProductActivity.this.beratProduk);
                                                        hashMap.put((Object)"harga", (Object)AddProductActivity.this.harga);
                                                        hashMap.put((Object)"harga_lama", (Object)AddProductActivity.this.hargaLama);
                                                        hashMap.put((Object)"harga_grosir1", (Object)AddProductActivity.this.hargaGrosir1);
                                                        hashMap.put((Object)"harga_grosir1_minimal", (Object)AddProductActivity.this.hargaGrosir1Minimal);
                                                        hashMap.put((Object)"harga_grosir2", (Object)AddProductActivity.this.hargaGrosir2);
                                                        hashMap.put((Object)"harga_grosir2_minimal", (Object)AddProductActivity.this.hargaGrosir2Minimal);
                                                        hashMap.put((Object)"harga_grosir3", (Object)AddProductActivity.this.hargaGrosir3);
                                                        hashMap.put((Object)"harga_grosir3_minimal", (Object)AddProductActivity.this.hargaGrosir3Minimal);
                                                        hashMap.put((Object)"harga_grosir4", (Object)AddProductActivity.this.hargaGrosir4);
                                                        hashMap.put((Object)"harga_grosir4_minimal", (Object)AddProductActivity.this.hargaGrosir4Minimal);
                                                        hashMap.put((Object)"harga_grosir5", (Object)AddProductActivity.this.hargaGrosir5);
                                                        hashMap.put((Object)"harga_grosir5_minimal", (Object)AddProductActivity.this.hargaGrosir5Minimal);
                                                        hashMap.put((Object)"variasi1_nama", (Object)AddProductActivity.this.namaVarian1);
                                                        hashMap.put((Object)"variasi1_jumlah", (Object)AddProductActivity.this.stokVarian1);
                                                        hashMap.put((Object)"variasi2_nama", (Object)AddProductActivity.this.namaVarian2);
                                                        hashMap.put((Object)"variasi2_jumlah", (Object)AddProductActivity.this.stokVarian2);
                                                        hashMap.put((Object)"variasi3_nama", (Object)AddProductActivity.this.namaVarian3);
                                                        hashMap.put((Object)"variasi3_jumlah", (Object)AddProductActivity.this.stokVarian3);
                                                        hashMap.put((Object)"variasi4_nama", (Object)AddProductActivity.this.namaVarian4);
                                                        hashMap.put((Object)"variasi4_jumlah", (Object)AddProductActivity.this.stokVarian4);
                                                        hashMap.put((Object)"variasi5_nama", (Object)AddProductActivity.this.namaVarian5);
                                                        hashMap.put((Object)"variasi5_jumlah", (Object)AddProductActivity.this.stokVarian5);
                                                        hashMap.put((Object)"variasi6_nama", (Object)AddProductActivity.this.namaVarian6);
                                                        hashMap.put((Object)"variasi6_jumlah", (Object)AddProductActivity.this.stokVarian6);
                                                        hashMap.put((Object)"variasi7_nama", (Object)AddProductActivity.this.namaVarian7);
                                                        hashMap.put((Object)"variasi7_jumlah", (Object)AddProductActivity.this.stokVarian7);
                                                        hashMap.put((Object)"variasi8_nama", (Object)AddProductActivity.this.namaVarian8);
                                                        hashMap.put((Object)"variasi8_jumlah", (Object)AddProductActivity.this.stokVarian8);
                                                        hashMap.put((Object)"variasi9_nama", (Object)AddProductActivity.this.namaVarian9);
                                                        hashMap.put((Object)"variasi9_jumlah", (Object)AddProductActivity.this.stokVarian9);
                                                        hashMap.put((Object)"variasi10_nama", (Object)AddProductActivity.this.namaVarian10);
                                                        hashMap.put((Object)"variasi10_jumlah", (Object)AddProductActivity.this.stokVarian10);
                                                        hashMap.put((Object)"link_bukalapak", (Object)AddProductActivity.this.linkBukalapak);
                                                        hashMap.put((Object)"link_tokopedia", (Object)AddProductActivity.this.linkTokopedia);
                                                        hashMap.put((Object)"link_shopee", (Object)AddProductActivity.this.linkShopee);
                                                        Log.e((String)"GROSS", (String)(AddProductActivity.this.hargaGrosir1 + " - " + AddProductActivity.this.hargaGrosir1Minimal + " | " + AddProductActivity.this.hargaGrosir2 + " - " + AddProductActivity.this.hargaGrosir2Minimal + " | " + AddProductActivity.this.hargaGrosir3 + " - " + AddProductActivity.this.hargaGrosir3Minimal + " | " + AddProductActivity.this.hargaGrosir4 + " - " + AddProductActivity.this.hargaGrosir4Minimal + " | " + AddProductActivity.this.hargaGrosir5 + " - " + AddProductActivity.this.hargaGrosir5Minimal));
                                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/add_product", "POST", (HashMap<String, String>)hashMap);
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
                                                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                                AddProductActivity.this.setResult(-1);
                                                                String string2 = jSONObject.getJSONObject("data").getString("idbarang");
                                                                Intent intent = new Intent((Context)AddProductActivity.this, EditGambarActivity.class);
                                                                intent.putExtra("idbarang", string2);
                                                                AddProductActivity.this.startActivity(intent);
                                                                AddProductActivity.this.finish();
                                                                return;
                                                            }
                                                            if (n == 0) {
                                                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                                return;
                                                            }
                                                            break block8;
                                                        }
                                                        Toast.makeText((Context)AddProductActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                                                    }
                                                }

                                                protected void onPreExecute() {
                                                    this.progressDialog = new ProgressDialog((Context)AddProductActivity.this);
                                                    this.progressDialog.setMessage((CharSequence)"Menambahkan produk.");
                                                    this.progressDialog.setIndeterminate(false);
                                                    this.progressDialog.setCancelable(false);
                                                    this.progressDialog.show();
                                                }
                                            }.execute((Object[])new String[0]);
                                            return;
                                        }
                                        Toast.makeText((Context)this.this$0, (CharSequence)"Mohon pilih kategori produk terlebih dahulu", (int)1).show();
                                        return;
                                    }
                                    this.this$0.editTextStokVarian1.setError((CharSequence)this.this$0.errorMessage);
                                    return;
                                }
                                this.this$0.editTextNamaVarian1.setError((CharSequence)this.this$0.errorMessage);
                                return;
                            }
                            this.this$0.editTextBeratProduk.setError((CharSequence)this.this$0.errorMessage);
                            return;
                        }
                        this.this$0.editTextHarga.setError((CharSequence)this.this$0.errorMessage);
                        return;
                    }
                    this.this$0.editTextDeskripsiProduk.setError((CharSequence)this.this$0.errorMessage);
                    return;
                }
                this.this$0.editTextNamaProduk.setError((CharSequence)this.this$0.errorMessage);
            }
        });
        if (this.productEdit) {
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();
                ProgressDialog progressDialog;

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)AddProductActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)AddProductActivity.this.dataPref.getToken());
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_all_categories", "POST", (HashMap<String, String>)hashMap);
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
                    ArrayList arrayList;
                    int n;
                    if (this.progressDialog.isShowing()) {
                        this.progressDialog.dismiss();
                    }
                    if (jSONObject == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n2 = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n2 == 1) {
                            new KategoriItem();
                            jSONArray = jSONObject.getJSONArray("kategori");
                        } else {
                            if (n2 == 0) {
                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                AddProductActivity.this.finish();
                                return;
                            }
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            KategoriItem kategoriItem = new KategoriItem();
                            kategoriItem.setIdKategori(jSONObject2.getString("idkategori"));
                            kategoriItem.setNamaKategori(jSONObject2.getString("nama"));
                            AddProductActivity.this.kategoriItems.add((Object)kategoriItem);
                        }
                        arrayList = new ArrayList();
                        n = 0;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                    do {
                        if (n >= AddProductActivity.this.kategoriItems.size()) {
                            ArrayAdapter arrayAdapter = new ArrayAdapter((Context)AddProductActivity.this, 17367048, (List)arrayList);
                            arrayAdapter.setDropDownViewResource(17367049);
                            AddProductActivity.this.spinnerKategori.setAdapter((SpinnerAdapter)arrayAdapter);
                            AddProductActivity.this.spinnerKategori.setSelection(AddProductActivity.this.selectedKategori);
                            return;
                        }
                        arrayList.add((Object)((KategoriItem)AddProductActivity.this.kategoriItems.get(n)).getNamaKategori());
                        if (((KategoriItem)AddProductActivity.this.kategoriItems.get(n)).getIdKategori().equals((Object)AddProductActivity.this.selectedIdKategori)) {
                            AddProductActivity.this.selectedKategori = n;
                        }
                        ++n;
                    } while (true);
                }

                protected void onPreExecute() {
                    this.progressDialog = new ProgressDialog((Context)AddProductActivity.this);
                    this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                    this.progressDialog.setIndeterminate(false);
                    this.progressDialog.setCancelable(false);
                    this.progressDialog.show();
                }
            }.execute((Object[])new String[0]);
        } else {
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();
                ProgressDialog progressDialog;

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)AddProductActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)AddProductActivity.this.dataPref.getToken());
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/check_product", "POST", (HashMap<String, String>)hashMap);
                        Log.d((String)"JSON result", (String)jSONObject.toString());
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
                    ArrayList arrayList;
                    if (this.progressDialog.isShowing()) {
                        this.progressDialog.dismiss();
                    }
                    if (jSONObject == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        JSONArray jSONArray;
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            new KategoriItem();
                            jSONArray = jSONObject.getJSONArray("kategori");
                        } else {
                            if (n == 2) {
                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                Intent intent = new Intent((Context)AddProductActivity.this, AddCategoryActivity.class);
                                AddProductActivity.this.startActivity(intent);
                                AddProductActivity.this.finish();
                                return;
                            }
                            Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                            AddProductActivity.this.finish();
                            return;
                        }
                        for (int i = 0; i < jSONArray.length(); ++i) {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            KategoriItem kategoriItem = new KategoriItem();
                            kategoriItem.setIdKategori(jSONObject2.getString("idkategori"));
                            kategoriItem.setNamaKategori(jSONObject2.getString("nama"));
                            AddProductActivity.this.kategoriItems.add((Object)kategoriItem);
                        }
                        arrayList = new ArrayList();
                        for (int i = 0; i < AddProductActivity.this.kategoriItems.size(); ++i) {
                            arrayList.add((Object)((KategoriItem)AddProductActivity.this.kategoriItems.get(i)).getNamaKategori());
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                    {
                        ArrayAdapter arrayAdapter = new ArrayAdapter((Context)AddProductActivity.this, 17367048, (List)arrayList);
                        arrayAdapter.setDropDownViewResource(17367049);
                        AddProductActivity.this.spinnerKategori.setAdapter((SpinnerAdapter)arrayAdapter);
                        return;
                    }
                }

                protected void onPreExecute() {
                    this.progressDialog = new ProgressDialog((Context)AddProductActivity.this);
                    this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                    this.progressDialog.setIndeterminate(false);
                    this.progressDialog.setCancelable(false);
                    this.progressDialog.show();
                }
            }.execute((Object[])new String[0]);
        }
        if (this.productEdit) {
            this.editTextNamaProduk.setText((CharSequence)intent.getStringExtra("nama"));
            this.editTextDeskripsiProduk.setText((CharSequence)intent.getStringExtra("deskripsi"));
            this.editTextHarga.setText((CharSequence)intent.getStringExtra("harga"));
            this.editTextHargaLama.setText((CharSequence)intent.getStringExtra("harga_lama"));
            this.editTextBeratProduk.setText((CharSequence)intent.getStringExtra("berat"));
            this.editTextLinkBukalapak.setText((CharSequence)intent.getStringExtra("link_bukalapak"));
            this.editTextLinkTokopedia.setText((CharSequence)intent.getStringExtra("link_tokopedia"));
            this.editTextLinkShopee.setText((CharSequence)intent.getStringExtra("link_shopee"));
            this.editTextNamaVarian1.setText((CharSequence)intent.getStringExtra("variasi1_nama"));
            this.editTextStokVarian1.setText((CharSequence)intent.getStringExtra("variasi1_jumlah"));
            this.editTextNamaVarian2.setText((CharSequence)intent.getStringExtra("variasi2_nama"));
            this.editTextStokVarian2.setText((CharSequence)intent.getStringExtra("variasi2_jumlah"));
            this.editTextNamaVarian3.setText((CharSequence)intent.getStringExtra("variasi3_nama"));
            this.editTextStokVarian3.setText((CharSequence)intent.getStringExtra("variasi3_jumlah"));
            this.editTextNamaVarian4.setText((CharSequence)intent.getStringExtra("variasi4_nama"));
            this.editTextStokVarian4.setText((CharSequence)intent.getStringExtra("variasi4_jumlah"));
            this.editTextNamaVarian5.setText((CharSequence)intent.getStringExtra("variasi5_nama"));
            this.editTextStokVarian5.setText((CharSequence)intent.getStringExtra("variasi5_jumlah"));
            this.editTextNamaVarian6.setText((CharSequence)intent.getStringExtra("variasi6_nama"));
            this.editTextStokVarian6.setText((CharSequence)intent.getStringExtra("variasi6_jumlah"));
            this.editTextNamaVarian7.setText((CharSequence)intent.getStringExtra("variasi7_nama"));
            this.editTextStokVarian7.setText((CharSequence)intent.getStringExtra("variasi7_jumlah"));
            this.editTextNamaVarian8.setText((CharSequence)intent.getStringExtra("variasi8_nama"));
            this.editTextStokVarian8.setText((CharSequence)intent.getStringExtra("variasi8_jumlah"));
            this.editTextNamaVarian9.setText((CharSequence)intent.getStringExtra("variasi9_nama"));
            this.editTextStokVarian9.setText((CharSequence)intent.getStringExtra("variasi9_jumlah"));
            this.editTextNamaVarian10.setText((CharSequence)intent.getStringExtra("variasi10_nama"));
            this.editTextStokVarian10.setText((CharSequence)intent.getStringExtra("variasi10_jumlah"));
            String string = intent.getStringExtra("harga_grosir1_minimal");
            String string2 = intent.getStringExtra("harga_grosir1");
            String string3 = intent.getStringExtra("harga_grosir2_minimal");
            String string4 = intent.getStringExtra("harga_grosir2");
            String string5 = intent.getStringExtra("harga_grosir3_minimal");
            String string6 = intent.getStringExtra("harga_grosir3");
            String string7 = intent.getStringExtra("harga_grosir4_minimal");
            String string8 = intent.getStringExtra("harga_grosir4");
            String string9 = intent.getStringExtra("harga_grosir5_minimal");
            String string10 = intent.getStringExtra("harga_grosir5");
            this.layoutHargaGrosir1.setVisibility(0);
            this.layoutHargaGrosir2.setVisibility(0);
            this.layoutHargaGrosir3.setVisibility(0);
            this.layoutHargaGrosir4.setVisibility(0);
            this.layoutHargaGrosir5.setVisibility(0);
            this.buttonAddHargaGrosir.setVisibility(8);
            this.visibleGrosir = 5;
            if (string.equals((Object)"0")) {
                this.editTextHargaGrosir1Minimal.setText((CharSequence)"");
                this.editTextHargaGrosir1.setText((CharSequence)"");
            } else {
                this.editTextHargaGrosir1Minimal.setText((CharSequence)string);
                this.editTextHargaGrosir1.setText((CharSequence)string2);
            }
            if (string3.equals((Object)"0")) {
                this.editTextHargaGrosir2Minimal.setText((CharSequence)"");
                this.editTextHargaGrosir2.setText((CharSequence)"");
            } else {
                this.editTextHargaGrosir2Minimal.setText((CharSequence)string3);
                this.editTextHargaGrosir2.setText((CharSequence)string4);
            }
            if (string5.equals((Object)"0")) {
                this.editTextHargaGrosir3Minimal.setText((CharSequence)"");
                this.editTextHargaGrosir3.setText((CharSequence)"");
            } else {
                this.editTextHargaGrosir3Minimal.setText((CharSequence)string5);
                this.editTextHargaGrosir3.setText((CharSequence)string6);
            }
            if (string7.equals((Object)"0")) {
                this.editTextHargaGrosir4Minimal.setText((CharSequence)"");
                this.editTextHargaGrosir4.setText((CharSequence)"");
            } else {
                this.editTextHargaGrosir4Minimal.setText((CharSequence)string7);
                this.editTextHargaGrosir4.setText((CharSequence)string8);
            }
            if (string9.equals((Object)"0")) {
                this.editTextHargaGrosir5Minimal.setText((CharSequence)"");
                this.editTextHargaGrosir5.setText((CharSequence)"");
            } else {
                this.editTextHargaGrosir5Minimal.setText((CharSequence)string9);
                this.editTextHargaGrosir5.setText((CharSequence)string10);
            }
            this.namaProduk = intent.getStringExtra("nama");
            this.selectedIdKategori = intent.getStringExtra("idkategori");
            this.selectedIdProduct = intent.getStringExtra("idbarang");
            this.layoutVarian2.setVisibility(0);
            this.layoutVarian3.setVisibility(0);
            this.layoutVarian4.setVisibility(0);
            this.layoutVarian5.setVisibility(0);
            this.layoutVarian6.setVisibility(0);
            this.layoutVarian7.setVisibility(0);
            this.layoutVarian8.setVisibility(0);
            this.layoutVarian9.setVisibility(0);
            this.layoutVarian10.setVisibility(0);
            this.buttonAddVarian.setVisibility(8);
            this.buttonDelete.setVisibility(0);
            this.buttonDelete.setOnClickListener(new View.OnClickListener((AddProductActivity)this){
                final /* synthetic */ AddProductActivity this$0;
                {
                    this.this$0 = addProductActivity;
                }

                public void onClick(View view) {
                    android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog$Builder((Context)this.this$0).create();
                    alertDialog.setTitle((CharSequence)"Hapus Produk");
                    alertDialog.setMessage((CharSequence)("Kamu yakin ingin menghapus produk " + this.this$0.namaProduk + "?"));
                    alertDialog.setButton(-1, (CharSequence)"Ya", new android.content.DialogInterface$OnClickListener(this){
                        final /* synthetic */ 5 this$1;
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
                                        hashMap.put((Object)"email", (Object)AddProductActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)AddProductActivity.this.dataPref.getToken());
                                        hashMap.put((Object)"idbarang", (Object)AddProductActivity.this.selectedIdProduct);
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_product", "POST", (HashMap<String, String>)hashMap);
                                        Log.d((String)"JSON result", (String)jSONObject.toString());
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
                                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                AddProductActivity.this.setResult(-1);
                                                AddProductActivity.this.finish();
                                                return;
                                            }
                                            if (n == 0) {
                                                Toast.makeText((Context)AddProductActivity.this, (CharSequence)string, (int)1).show();
                                                return;
                                            }
                                            break block8;
                                        }
                                        Toast.makeText((Context)AddProductActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)AddProductActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Menghapus produk.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                        }
                    });
                    alertDialog.setButton(-2, (CharSequence)"Tidak", new android.content.DialogInterface$OnClickListener(this, alertDialog){
                        final /* synthetic */ 5 this$1;
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
            this.buttonEditGambar.setVisibility(0);
            this.buttonEditGambar.setOnClickListener(new View.OnClickListener((AddProductActivity)this){
                final /* synthetic */ AddProductActivity this$0;
                {
                    this.this$0 = addProductActivity;
                }

                public void onClick(View view) {
                    Intent intent = new Intent((Context)this.this$0, EditGambarActivity.class);
                    intent.putExtra("idbarang", this.this$0.selectedIdProduct);
                    this.this$0.startActivity(intent);
                }
            });
        }
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

