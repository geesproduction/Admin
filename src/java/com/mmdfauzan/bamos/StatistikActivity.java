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
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.TextView
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.Rupiah;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class StatistikActivity
extends AppCompatActivity {
    DataPref dataPref;
    TextView textViewKategori;
    TextView textViewMember;
    TextView textViewMemberBulanIni;
    TextView textViewMemberBulanLalu;
    TextView textViewMemberHariIni;
    TextView textViewMemberHariLalu;
    TextView textViewMemberMingguIni;
    TextView textViewMemberMingguLalu;
    TextView textViewPemesanan;
    TextView textViewPemesananBulanIni;
    TextView textViewPemesananBulanLalu;
    TextView textViewPemesananHariIni;
    TextView textViewPemesananHariLalu;
    TextView textViewPemesananMingguIni;
    TextView textViewPemesananMingguLalu;
    TextView textViewPendapatanBulanIni;
    TextView textViewPendapatanBulanLalu;
    TextView textViewPendapatanHariIni;
    TextView textViewPendapatanHariLalu;
    TextView textViewPendapatanMingguIni;
    TextView textViewPendapatanMingguLalu;
    TextView textViewProduk;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427400);
        this.dataPref = new DataPref((Context)this);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Statistik");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.textViewKategori = (TextView)this.findViewById(2131297070);
        this.textViewProduk = (TextView)this.findViewById(2131297123);
        this.textViewMember = (TextView)this.findViewById(2131297089);
        this.textViewPemesanan = (TextView)this.findViewById(2131297110);
        this.textViewPendapatanHariIni = (TextView)this.findViewById(2131297119);
        this.textViewMemberHariIni = (TextView)this.findViewById(2131297092);
        this.textViewPemesananHariIni = (TextView)this.findViewById(2131297113);
        this.textViewPendapatanMingguIni = (TextView)this.findViewById(2131297121);
        this.textViewMemberMingguIni = (TextView)this.findViewById(2131297094);
        this.textViewPemesananMingguIni = (TextView)this.findViewById(2131297115);
        this.textViewPendapatanBulanIni = (TextView)this.findViewById(2131297117);
        this.textViewMemberBulanIni = (TextView)this.findViewById(2131297090);
        this.textViewPemesananBulanIni = (TextView)this.findViewById(2131297111);
        this.textViewPendapatanHariLalu = (TextView)this.findViewById(2131297120);
        this.textViewMemberHariLalu = (TextView)this.findViewById(2131297093);
        this.textViewPemesananHariLalu = (TextView)this.findViewById(2131297114);
        this.textViewPendapatanMingguLalu = (TextView)this.findViewById(2131297122);
        this.textViewMemberMingguLalu = (TextView)this.findViewById(2131297095);
        this.textViewPemesananMingguLalu = (TextView)this.findViewById(2131297116);
        this.textViewPendapatanBulanLalu = (TextView)this.findViewById(2131297118);
        this.textViewMemberBulanLalu = (TextView)this.findViewById(2131297091);
        this.textViewPemesananBulanLalu = (TextView)this.findViewById(2131297112);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)StatistikActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)StatistikActivity.this.dataPref.getToken());
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_statistics", "POST", (HashMap<String, String>)hashMap);
                    Log.e((String)"WEEEEEEEEEW", (String)jSONObject.toString());
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
                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                            Rupiah rupiah = new Rupiah();
                            StatistikActivity.this.textViewKategori.setText((CharSequence)jSONObject2.getString("kategori"));
                            StatistikActivity.this.textViewProduk.setText((CharSequence)jSONObject2.getString("produk"));
                            StatistikActivity.this.textViewMember.setText((CharSequence)jSONObject2.getString("member"));
                            StatistikActivity.this.textViewPemesanan.setText((CharSequence)jSONObject2.getString("pemesanan"));
                            StatistikActivity.this.textViewPendapatanHariIni.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_hari")) + ",-"));
                            StatistikActivity.this.textViewMemberHariIni.setText((CharSequence)jSONObject2.getString("member_hari_jumlah"));
                            StatistikActivity.this.textViewPemesananHariIni.setText((CharSequence)jSONObject2.getString("pemesanan_hari_jumlah"));
                            StatistikActivity.this.textViewPendapatanHariLalu.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_hari_lalu")) + ",-"));
                            StatistikActivity.this.textViewMemberHariLalu.setText((CharSequence)jSONObject2.getString("member_hari_lalu_jumlah"));
                            StatistikActivity.this.textViewPemesananHariLalu.setText((CharSequence)jSONObject2.getString("pemesanan_hari_lalu_jumlah"));
                            StatistikActivity.this.textViewPendapatanMingguIni.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_minggu")) + ",-"));
                            StatistikActivity.this.textViewMemberMingguIni.setText((CharSequence)jSONObject2.getString("member_minggu_jumlah"));
                            StatistikActivity.this.textViewPemesananMingguIni.setText((CharSequence)jSONObject2.getString("pemesanan_minggu_jumlah"));
                            StatistikActivity.this.textViewPendapatanMingguLalu.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_minggu_lalu")) + ",-"));
                            StatistikActivity.this.textViewMemberMingguLalu.setText((CharSequence)jSONObject2.getString("member_minggu_lalu_jumlah"));
                            StatistikActivity.this.textViewPemesananMingguLalu.setText((CharSequence)jSONObject2.getString("pemesanan_minggu_lalu_jumlah"));
                            StatistikActivity.this.textViewPendapatanBulanIni.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_bulan")) + ",-"));
                            StatistikActivity.this.textViewMemberBulanIni.setText((CharSequence)jSONObject2.getString("member_bulan_jumlah"));
                            StatistikActivity.this.textViewPemesananBulanIni.setText((CharSequence)jSONObject2.getString("pemesanan_bulan_jumlah"));
                            StatistikActivity.this.textViewPendapatanBulanLalu.setText((CharSequence)(rupiah.toRupiah(jSONObject2.getString("pendapatan_bulan_lalu")) + ",-"));
                            StatistikActivity.this.textViewMemberBulanLalu.setText((CharSequence)jSONObject2.getString("member_bulan_lalu_jumlah"));
                            StatistikActivity.this.textViewPemesananBulanLalu.setText((CharSequence)jSONObject2.getString("pemesanan_bulan_lalu_jumlah"));
                            return;
                        }
                        if (n == 0) {
                            Toast.makeText((Context)StatistikActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        break block8;
                    }
                    new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                }
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)StatistikActivity.this);
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

