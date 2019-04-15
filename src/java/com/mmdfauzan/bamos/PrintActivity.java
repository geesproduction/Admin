/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothSocket
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.os.Handler
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.nio.Buffer
 *  java.nio.ByteBuffer
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.Set
 *  java.util.UUID
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.DeviceListActivity;
import com.mmdfauzan.bamos.PrintActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.BluetoothPrinter;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.UnicodeFormatter;
import com.mmdfauzan.bamos.model.ProdukItem;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PrintActivity
extends AppCompatActivity
implements Runnable {
    public static final int ALIGN_CENTER = 100;
    public static final int ALIGN_LEFT = 102;
    public static final int ALIGN_RIGHT = 101;
    private static final byte[] ESC_ALIGN_CENTER;
    private static final byte[] ESC_ALIGN_LEFT;
    private static final byte[] ESC_ALIGN_RIGHT;
    private static final byte[] NEW_LINE;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    protected static final String TAG = "TAG";
    String PRINT_CONNECTED = "Printer terhubung. Print untuk mencetak struk.";
    String PRINT_DISCONNECTED = "Tidak terhubung ke printer. Pilih printer untuk memulai.";
    String alamat;
    private UUID applicationUUID = UUID.fromString((String)"00001101-0000-1000-8000-00805F9B34FB");
    String berat;
    Button buttonPrint;
    Button buttonScanDevice;
    CheckBox checkBoxAlamat;
    DataPref dataPref;
    String diskon;
    String harga;
    String hubungi;
    String idpesanan;
    String jumlah;
    String kecamatan;
    String keterangan;
    String kodepembayaran;
    String kodepesanan;
    String kodepos;
    String kota;
    String kurir;
    LinearLayout layoutButtonPilihPrinter;
    LinearLayout layoutButtonPrint;
    BluetoothAdapter mBluetoothAdapter;
    private ProgressDialog mBluetoothConnectProgressDialog;
    BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    Button mDisc;
    private Handler mHandler = new Handler(this){
        final /* synthetic */ PrintActivity this$0;
        {
            this.this$0 = printActivity;
        }

        public void handleMessage(android.os.Message message) {
            PrintActivity.access$200(this.this$0).dismiss();
            this.this$0.buttonScanDevice.setText((CharSequence)this.this$0.mBluetoothDevice.getName());
            Toast.makeText((Context)this.this$0, (CharSequence)"Printer Terhubung", (int)0).show();
            this.this$0.setVisibility();
        }
    };
    String nama;
    String namatoko;
    String ongkir;
    OutputStream os;
    ArrayList<ProdukItem> produkItems = new ArrayList();
    String provinsi;
    String telepon;
    TextView textViewJudul;
    String total;
    String waktu;

    static {
        NEW_LINE = new byte[]{10};
        ESC_ALIGN_CENTER = new byte[]{27, 97, 1};
        ESC_ALIGN_RIGHT = new byte[]{27, 97, 2};
        ESC_ALIGN_LEFT = new byte[]{27, 97, 0};
    }

    private void ListPairedDevices() {
        Set set = this.mBluetoothAdapter.getBondedDevices();
        if (set.size() > 0) {
            for (BluetoothDevice bluetoothDevice : set) {
                Log.e((String)TAG, (String)("PairedDevices: " + bluetoothDevice.getName() + "  " + bluetoothDevice.getAddress()));
            }
        }
    }

    static /* synthetic */ void access$000(PrintActivity printActivity) {
        printActivity.ListPairedDevices();
    }

    static /* synthetic */ BluetoothSocket access$100(PrintActivity printActivity) {
        return printActivity.mBluetoothSocket;
    }

    static /* synthetic */ ProgressDialog access$200(PrintActivity printActivity) {
        return printActivity.mBluetoothConnectProgressDialog;
    }

    private void closeSocket(BluetoothSocket bluetoothSocket) {
        try {
            bluetoothSocket.close();
            Log.e((String)TAG, (String)"SocketClosed");
            return;
        }
        catch (IOException iOException) {
            Log.e((String)TAG, (String)"CouldNotCloseSocket");
            return;
        }
    }

    private static String encodeNonAscii(String string) {
        return string.replace('\u00e1', 'a').replace('\u010d', 'c').replace('\u010f', 'd').replace('\u00e9', 'e').replace('\u011b', 'e').replace('\u00ed', 'i').replace('\u0148', 'n').replace('\u00f3', 'o').replace('\u0159', 'r').replace('\u0161', 's').replace('\u0165', 't').replace('\u00fa', 'u').replace('\u016f', 'u').replace('\u00fd', 'y').replace('\u017e', 'z').replace('\u00c1', 'A').replace('\u010c', 'C').replace('\u010e', 'D').replace('\u00c9', 'E').replace('\u011a', 'E').replace('\u00cd', 'I').replace('\u0147', 'N').replace('\u00d3', 'O').replace('\u0158', 'R').replace('\u0160', 'S').replace('\u0164', 'T').replace('\u00da', 'U').replace('\u016e', 'U').replace('\u00dd', 'Y').replace('\u017d', 'Z');
    }

    public static byte intToByteArray(int n) {
        byte[] arrby = ByteBuffer.allocate((int)4).putInt(n).array();
        for (int i = 0; i < arrby.length; ++i) {
            System.out.println("Selva  [" + i + "] = 0x" + UnicodeFormatter.byteToHex(arrby[i]));
        }
        return arrby[3];
    }

    public boolean addNewLine() {
        return this.printUnicode(NEW_LINE);
    }

    public int addNewLines(int n) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            if (!this.addNewLine()) continue;
            ++n2;
        }
        return n2;
    }

    public void feedPaper() {
        this.addNewLine();
        this.addNewLine();
        this.addNewLine();
        this.addNewLine();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        switch (n) {
            case 1: {
                if (n2 == -1) {
                    String string = intent.getExtras().getString("DeviceAddress");
                    Log.e((String)TAG, (String)("Coming incoming address " + string));
                    this.mBluetoothDevice = this.mBluetoothAdapter.getRemoteDevice(string);
                    this.mBluetoothConnectProgressDialog = ProgressDialog.show((Context)this, (CharSequence)"Menghubungkan...", (CharSequence)this.mBluetoothDevice.getName(), (boolean)true, (boolean)false);
                    new Thread((Runnable)this).start();
                }
            }
            default: {
                break;
            }
            case 2: {
                if (n2 != -1) break;
                PrintActivity.super.ListPairedDevices();
                this.startActivityForResult(new Intent((Context)this, DeviceListActivity.class), 1);
            }
        }
        this.setVisibility();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onBackPressed() {
        try {
            if (this.mBluetoothSocket != null) {
                this.mBluetoothSocket.close();
            }
        }
        catch (Exception exception) {
            Log.e((String)"Tag", (String)"Exe ", (Throwable)exception);
        }
        this.setResult(0);
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427395);
        this.dataPref = new DataPref((Context)this);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Cetak Struk");
        this.idpesanan = this.getIntent().getStringExtra("idpesanan");
        this.textViewJudul = (TextView)this.findViewById(2131297067);
        this.checkBoxAlamat = (CheckBox)this.findViewById(2131296415);
        this.checkBoxAlamat.setChecked(false);
        this.checkBoxAlamat.setVisibility(8);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)PrintActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)PrintActivity.this.dataPref.getToken());
                    hashMap.put((Object)"idpesanan", (Object)PrintActivity.this.idpesanan);
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_order_receipt", "POST", (HashMap<String, String>)hashMap);
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
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            protected void onPostExecute(JSONObject jSONObject) {
                if (this.progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                }
                if (jSONObject != null) {
                    String string;
                    block8 : {
                        int n = jSONObject.getInt("success");
                        string = jSONObject.getString("message");
                        if (n != 1) break block8;
                        JSONObject jSONObject2 = jSONObject.getJSONObject("pesanan");
                        PrintActivity.this.namatoko = jSONObject2.getString("namatoko");
                        PrintActivity.this.hubungi = jSONObject2.getString("hubungi");
                        PrintActivity.this.kodepesanan = jSONObject2.getString("kodepesanan");
                        PrintActivity.this.waktu = jSONObject2.getString("waktu");
                        PrintActivity.this.kurir = jSONObject2.getString("kurir");
                        PrintActivity.this.ongkir = jSONObject2.getString("ongkir");
                        PrintActivity.this.diskon = jSONObject2.getString("diskon");
                        PrintActivity.this.kodepembayaran = jSONObject2.getString("kodepembayaran");
                        PrintActivity.this.total = jSONObject2.getString("total");
                        PrintActivity.this.jumlah = jSONObject2.getString("jumlah");
                        PrintActivity.this.berat = jSONObject2.getString("berat");
                        PrintActivity.this.alamat = jSONObject2.getString("alamat");
                        PrintActivity.this.provinsi = jSONObject2.getString("provinsi");
                        PrintActivity.this.kota = jSONObject2.getString("kota");
                        PrintActivity.this.kecamatan = jSONObject2.getString("kecamatan");
                        PrintActivity.this.kodepos = jSONObject2.getString("kodepos");
                        PrintActivity.this.nama = jSONObject2.getString("nama");
                        PrintActivity.this.telepon = jSONObject2.getString("telepon");
                        PrintActivity.this.keterangan = jSONObject2.getString("keterangan");
                        JSONArray jSONArray = jSONObject2.getJSONArray("barang");
                        int n2 = 0;
                        do {
                            if (n2 >= jSONArray.length()) return;
                            JSONObject jSONObject3 = jSONArray.getJSONObject(n2);
                            ProdukItem produkItem = new ProdukItem();
                            produkItem.setNamaProduk(jSONObject3.getString("nama"));
                            produkItem.setHarga(jSONObject3.getString("harga"));
                            produkItem.setStok(jSONObject3.getString("jumlah"));
                            PrintActivity.this.produkItems.add((Object)produkItem);
                            ++n2;
                        } while (true);
                    }
                    try {
                        Toast.makeText((Context)PrintActivity.this, (CharSequence)string, (int)1).show();
                        PrintActivity.this.finish();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                Toast.makeText((Context)PrintActivity.this, (CharSequence)"Gagal terhubung ke server. Coba lagi nanti.", (int)1).show();
                PrintActivity.this.finish();
            }

            protected void onPreExecute() {
                super.onPreExecute();
                this.progressDialog = new ProgressDialog((Context)PrintActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.textViewJudul.setText((CharSequence)this.PRINT_DISCONNECTED);
        this.buttonScanDevice = (Button)this.findViewById(2131296390);
        this.buttonScanDevice.setOnClickListener(new View.OnClickListener((PrintActivity)this){
            final /* synthetic */ PrintActivity this$0;
            {
                this.this$0 = printActivity;
            }

            public void onClick(View view) {
                this.this$0.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (this.this$0.mBluetoothAdapter == null) {
                    return;
                }
                if (!this.this$0.mBluetoothAdapter.isEnabled()) {
                    Intent intent = new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE");
                    this.this$0.startActivityForResult(intent, 2);
                    return;
                }
                PrintActivity.access$000(this.this$0);
                Intent intent = new Intent((Context)this.this$0, DeviceListActivity.class);
                this.this$0.startActivityForResult(intent, 1);
            }
        });
        this.buttonPrint = (Button)this.findViewById(2131296384);
        this.buttonPrint.setVisibility(8);
        this.buttonPrint.setOnClickListener(new View.OnClickListener((PrintActivity)this){
            final /* synthetic */ PrintActivity this$0;
            {
                this.this$0 = printActivity;
            }

            public void onClick(View view) {
                if (PrintActivity.access$100(this.this$0) != null) {
                    new Thread(this){
                        final /* synthetic */ 2 this$1;
                        {
                            this.this$1 = var1;
                        }

                        public void run() {
                            this.this$1.this$0.os = PrintActivity.access$100(this.this$1.this$0).getOutputStream();
                            this.this$1.this$0.feedPaper();
                            this.this$1.this$0.setAlign(100);
                            this.this$1.this$0.setBold(true);
                            this.this$1.this$0.printText(this.this$1.this$0.namatoko);
                            this.this$1.this$0.addNewLine();
                            this.this$1.this$0.printText(this.this$1.this$0.hubungi);
                            this.this$1.this$0.setBold(false);
                            this.this$1.this$0.addNewLine();
                            this.this$1.this$0.setAlign(102);
                            this.this$1.this$0.printDoubleLines();
                            this.this$1.this$0.addNewLine();
                            this.this$1.this$0.printText(this.this$1.this$0.waktu);
                            this.this$1.this$0.addNewLine();
                            this.this$1.this$0.printText("#" + this.this$1.this$0.kodepesanan);
                            this.this$1.this$0.addNewLine();
                            this.this$1.this$0.printDoubleLines();
                            this.this$1.this$0.addNewLines(2);
                            int n = 0;
                            do {
                                if (n >= this.this$1.this$0.produkItems.size()) break;
                                this.this$1.this$0.setAlign(102);
                                this.this$1.this$0.printText(((ProdukItem)this.this$1.this$0.produkItems.get(n)).getNamaProduk());
                                this.this$1.this$0.addNewLine();
                                this.this$1.this$0.printText(((ProdukItem)this.this$1.this$0.produkItems.get(n)).getStok());
                                this.this$1.this$0.addNewLine();
                                this.this$1.this$0.setAlign(101);
                                this.this$1.this$0.printText(((ProdukItem)this.this$1.this$0.produkItems.get(n)).getHarga());
                                this.this$1.this$0.addNewLine();
                                ++n;
                            } while (true);
                            try {
                                if (!this.this$1.this$0.ongkir.equals((Object)"0")) {
                                    this.this$1.this$0.setAlign(102);
                                    this.this$1.this$0.printText("Ongkir " + this.this$1.this$0.kurir);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.setAlign(101);
                                    this.this$1.this$0.printText(this.this$1.this$0.ongkir);
                                    this.this$1.this$0.addNewLine();
                                }
                                if (!this.this$1.this$0.kodepembayaran.equals((Object)"0")) {
                                    this.this$1.this$0.setAlign(102);
                                    this.this$1.this$0.printText("Kode pembayaran");
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.setAlign(101);
                                    this.this$1.this$0.printText(this.this$1.this$0.kodepembayaran);
                                    this.this$1.this$0.addNewLine();
                                }
                                if (!this.this$1.this$0.diskon.equals((Object)"0")) {
                                    this.this$1.this$0.setAlign(102);
                                    this.this$1.this$0.printText("Diskon");
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.setAlign(101);
                                    this.this$1.this$0.printText("-" + this.this$1.this$0.diskon);
                                    this.this$1.this$0.addNewLine();
                                }
                                this.this$1.this$0.setAlign(102);
                                this.this$1.this$0.printLine();
                                this.this$1.this$0.addNewLine();
                                this.this$1.this$0.setBold(true);
                                this.this$1.this$0.printText("Total");
                                this.this$1.this$0.addNewLine();
                                this.this$1.this$0.setAlign(101);
                                this.this$1.this$0.printText(this.this$1.this$0.total);
                                this.this$1.this$0.setBold(false);
                                this.this$1.this$0.addNewLines(2);
                                this.this$1.this$0.setAlign(102);
                                this.this$1.this$0.printText("Item    : " + this.this$1.this$0.jumlah);
                                this.this$1.this$0.addNewLine();
                                if (!this.this$1.this$0.berat.equals((Object)"")) {
                                    this.this$1.this$0.setAlign(102);
                                    this.this$1.this$0.printText("Berat   : " + this.this$1.this$0.berat);
                                    this.this$1.this$0.addNewLine();
                                }
                                if (!this.this$1.this$0.keterangan.equals((Object)"")) {
                                    this.this$1.this$0.printText("Catatan : " + this.this$1.this$0.keterangan);
                                    this.this$1.this$0.addNewLine();
                                }
                                if (this.this$1.this$0.checkBoxAlamat.isChecked()) {
                                    this.this$1.this$0.printDoubleLines();
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.setBold(true);
                                    this.this$1.this$0.printText("Kepada:");
                                    this.this$1.this$0.setBold(false);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.printText(this.this$1.this$0.nama);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.printText(this.this$1.this$0.telepon);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.printText(this.this$1.this$0.alamat);
                                    this.this$1.this$0.addNewLine();
                                    if (!this.this$1.this$0.kecamatan.equals((Object)"")) {
                                        this.this$1.this$0.printText(this.this$1.this$0.kecamatan);
                                        this.this$1.this$0.addNewLine();
                                        this.this$1.this$0.printText(this.this$1.this$0.kota);
                                        this.this$1.this$0.addNewLine();
                                        this.this$1.this$0.printText(this.this$1.this$0.provinsi);
                                        this.this$1.this$0.addNewLine();
                                        this.this$1.this$0.printText(this.this$1.this$0.kodepos);
                                        this.this$1.this$0.addNewLine();
                                    }
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.setBold(true);
                                    this.this$1.this$0.printText("Pengirim:");
                                    this.this$1.this$0.setBold(false);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.printText(this.this$1.this$0.namatoko);
                                    this.this$1.this$0.addNewLine();
                                    this.this$1.this$0.printText(this.this$1.this$0.hubungi);
                                    this.this$1.this$0.addNewLine();
                                }
                                this.this$1.this$0.printDoubleLines();
                                this.this$1.this$0.feedPaper();
                                return;
                            }
                            catch (Exception exception) {
                                Log.e((String)"MainActivity", (String)"Exe ", (Throwable)exception);
                                return;
                            }
                        }
                    }.start();
                    return;
                }
                Toast.makeText((Context)this.this$0, (CharSequence)"Pilih printer terlebih dahulu", (int)1).show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (this.mBluetoothSocket != null) {
                this.mBluetoothSocket.close();
            }
            return;
        }
        catch (Exception exception) {
            Log.e((String)"Tag", (String)"Exe ", (Throwable)exception);
            return;
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

    public boolean printDoubleLines() {
        return this.printText("================================");
    }

    public boolean printImage(Bitmap bitmap) {
        return this.printUnicode(BluetoothPrinter.decodeBitmap(bitmap));
    }

    public boolean printLine() {
        return this.printText("________________________________");
    }

    public boolean printText(String string) {
        try {
            this.os.write(PrintActivity.encodeNonAscii(string).getBytes());
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public boolean printUnicode(byte[] arrby) {
        try {
            this.os.write(arrby);
            return true;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return false;
        }
    }

    public void run() {
        try {
            this.mBluetoothSocket = this.mBluetoothDevice.createRfcommSocketToServiceRecord(this.applicationUUID);
            this.mBluetoothAdapter.cancelDiscovery();
            this.mBluetoothSocket.connect();
            this.mHandler.sendEmptyMessage(0);
            return;
        }
        catch (IOException iOException) {
            Log.e((String)TAG, (String)"CouldNotConnectToSocket", (Throwable)iOException);
            this.closeSocket(this.mBluetoothSocket);
            return;
        }
    }

    public byte[] sel(int n) {
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)2);
        byteBuffer.putInt(n);
        byteBuffer.flip();
        return byteBuffer.array();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setAlign(int n) {
        byte[] arrby;
        switch (n) {
            default: {
                arrby = ESC_ALIGN_LEFT;
                break;
            }
            case 100: {
                arrby = ESC_ALIGN_CENTER;
                break;
            }
            case 102: {
                arrby = ESC_ALIGN_LEFT;
                break;
            }
            case 101: {
                arrby = ESC_ALIGN_RIGHT;
            }
        }
        try {
            this.os.write(arrby);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setBold(boolean bl) {
        int n = 1;
        byte[] arrby = new byte[3];
        arrby[0] = 27;
        arrby[n] = 69;
        if (!bl) {
            n = 0;
        }
        arrby[2] = n;
        this.printUnicode(arrby);
    }

    public void setLarge() {
        this.printUnicode(new byte[]{27, 33, 32});
    }

    public void setLineSpacing(int n) {
        byte[] arrby = new byte[]{27, 51, (byte)n};
        this.printUnicode(arrby);
    }

    public void setNormal() {
        this.printUnicode(new byte[]{27, 33, 0});
    }

    public void setVisibility() {
        if (this.mBluetoothSocket == null) {
            this.buttonPrint.setVisibility(8);
            this.textViewJudul.setText((CharSequence)this.PRINT_DISCONNECTED);
            this.checkBoxAlamat.setVisibility(8);
            return;
        }
        this.buttonPrint.setVisibility(0);
        this.textViewJudul.setText((CharSequence)this.PRINT_CONNECTED);
        this.checkBoxAlamat.setVisibility(0);
    }

}

