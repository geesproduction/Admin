/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.res.AssetManager
 *  android.database.Cursor
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Color
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Handler
 *  android.os.Parcelable
 *  android.provider.DocumentsContract
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Audio
 *  android.provider.MediaStore$Audio$Media
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Video
 *  android.provider.MediaStore$Video$Media
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.Toast
 *  com.google.android.gms.ads.AdListener
 *  com.google.android.gms.ads.AdRequest
 *  com.google.android.gms.ads.AdRequest$Builder
 *  com.google.android.gms.ads.AdView
 *  com.google.android.gms.ads.InterstitialAd
 *  java.io.ByteArrayOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileNotFoundException
 *  java.io.FileOutputStream
 *  java.io.FileWriter
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Void
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.List
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipInputStream
 *  java.util.zip.ZipOutputStream
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mmdfauzan.bamos.BikinFileActivity;
import com.mmdfauzan.bamos.PetunjukEditActivity;
import com.mmdfauzan.bamos.SuccessActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.AXmlEditor;
import com.mmdfauzan.bamos.helper.FileUtil;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.RealPathUtil;
import com.mmdfauzan.bamos.helper.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONException;
import org.json.JSONObject;

public class BikinFileActivity
extends AppCompatActivity {
    int PICK_IMAGE = 1;
    AdView adView;
    Button buttonDownloadIcon;
    Button buttonGetPreviousColor;
    DataPref dataPref;
    ProgressDialog dialog;
    boolean editAplikasi = false;
    EditText ednamaaplikasi;
    String gambaricon = null;
    Handler handler;
    ImageView icon;
    InterstitialAd interstitial;
    boolean lanjut = true;
    LinearLayout layoutForm;
    LinearLayout layoutLoading;
    String namaaplikasi;
    int num = 0;
    OutputStream out;
    String pathfile;
    Button pilihwarna;
    String selectedColor = "#FF8888";
    String status = "sukses";
    Button submit;
    String tempColor;
    File tempFile;
    Thread thread;
    String un = "";
    String username;
    String versi;
    int warna = -30584;

    private void checkImageSize(String string) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)string, (BitmapFactory.Options)options);
        int n = options.outHeight;
        int n2 = options.outWidth;
        if (n > 200 || n2 > 200) {
            AlertDialog alertDialog = new AlertDialog.Builder((Context)this).create();
            alertDialog.setTitle((CharSequence)"Gambar Berukuran Besar");
            alertDialog.setMessage((CharSequence)"Gambar yang dipilih memiliki ukuran lebih besar dari 200x200 piksel, aplikasi mungkin tidak dapat diinstal jika menggunakan gambar yang terlalu besar. Disarankan untuk menggunakan gambar lain atau resize terlebih dahulu.");
            alertDialog.setButton(-2, (CharSequence)"Mengerti", new DialogInterface.OnClickListener((BikinFileActivity)this, alertDialog){
                final /* synthetic */ BikinFileActivity this$0;
                final /* synthetic */ AlertDialog val$adialog;
                {
                    this.this$0 = bikinFileActivity;
                    this.val$adialog = alertDialog;
                }

                public void onClick(DialogInterface dialogInterface, int n) {
                    this.val$adialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    public static String getDataColumn(Context context, Uri uri, String string, String[] arrstring) {
        Cursor cursor;
        block5 : {
            cursor = null;
            String[] arrstring2 = new String[]{"_data"};
            cursor = context.getContentResolver().query(uri, arrstring2, string, arrstring, null);
            if (cursor == null) break block5;
            if (!cursor.moveToFirst()) break block5;
            String string2 = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string2;
        }
        return null;
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String getPath(Context context, Uri uri) {
        String string;
        boolean bl = Build.VERSION.SDK_INT >= 19;
        if (bl && DocumentsContract.isDocumentUri((Context)context, (Uri)uri)) {
            Uri uri2;
            if (BikinFileActivity.isExternalStorageDocument(uri)) {
                String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
                boolean bl2 = "primary".equalsIgnoreCase(arrstring[0]);
                string = null;
                if (!bl2) return string;
                return (Object)Environment.getExternalStorageDirectory() + "/" + arrstring[1];
            }
            if (BikinFileActivity.isDownloadsDocument(uri)) {
                String string2 = DocumentsContract.getDocumentId((Uri)uri);
                return BikinFileActivity.getDataColumn(context, ContentUris.withAppendedId((Uri)Uri.parse((String)"content://downloads/public_downloads"), (long)Long.valueOf((String)string2)), null, null);
            }
            boolean bl3 = BikinFileActivity.isMediaDocument(uri);
            string = null;
            if (!bl3) return string;
            String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
            String string3 = arrstring[0];
            if ("image".equals((Object)string3)) {
                uri2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals((Object)string3)) {
                uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else {
                boolean bl4 = "audio".equals((Object)string3);
                uri2 = null;
                if (bl4) {
                    uri2 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
            }
            String[] arrstring2 = new String[]{arrstring[1]};
            return BikinFileActivity.getDataColumn(context, uri2, "_id=?", arrstring2);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (!BikinFileActivity.isGooglePhotosUri(uri)) return BikinFileActivity.getDataColumn(context, uri, null, null);
            return uri.getLastPathSegment();
        }
        boolean bl5 = "file".equalsIgnoreCase(uri.getScheme());
        string = null;
        if (!bl5) return string;
        return uri.getPath();
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals((Object)uri.getAuthority());
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals((Object)uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals((Object)uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals((Object)uri.getAuthority());
    }

    public void addFilesToExistingZip(File file, File[] arrfile) throws IOException {
        this.tempFile(file);
        byte[] arrby = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream((InputStream)new FileInputStream(this.tempFile));
        ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)new FileOutputStream(file));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            String string = zipEntry.getName();
            boolean bl = true;
            int n = arrfile.length;
            int n2 = 0;
            do {
                block7 : {
                    int n3;
                    block6 : {
                        if (n2 >= n) break block6;
                        if (!arrfile[n2].getName().equals((Object)string)) break block7;
                        bl = false;
                    }
                    if (!bl) break;
                    zipOutputStream.putNextEntry(new ZipEntry(string));
                    while ((n3 = zipInputStream.read(arrby)) > 0) {
                        zipOutputStream.write(arrby, 0, n3);
                    }
                    break;
                }
                ++n2;
            } while (true);
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        for (int i = 0; i < arrfile.length; ++i) {
            int n;
            FileInputStream fileInputStream = new FileInputStream(arrfile[i]);
            zipOutputStream.putNextEntry(new ZipEntry("assets/" + arrfile[i].getName()));
            while ((n = fileInputStream.read(arrby)) > 0) {
                zipOutputStream.write(arrby, 0, n);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();
        this.tempFile.delete();
    }

    public void addIcon(File file, File[] arrfile) throws IOException {
        this.tempFile(file);
        byte[] arrby = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream((InputStream)new FileInputStream(this.tempFile));
        ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)new FileOutputStream(file));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            String string = zipEntry.getName();
            boolean bl = true;
            int n = arrfile.length;
            int n2 = 0;
            do {
                block7 : {
                    int n3;
                    block6 : {
                        if (n2 >= n) break block6;
                        if (!arrfile[n2].getName().equals((Object)string)) break block7;
                        bl = false;
                    }
                    if (!bl) break;
                    zipOutputStream.putNextEntry(new ZipEntry(string));
                    while ((n3 = zipInputStream.read(arrby)) > 0) {
                        zipOutputStream.write(arrby, 0, n3);
                    }
                    break;
                }
                ++n2;
            } while (true);
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        for (int i = 0; i < arrfile.length; ++i) {
            int n;
            FileInputStream fileInputStream = new FileInputStream(arrfile[i]);
            zipOutputStream.putNextEntry(new ZipEntry("res/drawable/" + arrfile[i].getName()));
            while ((n = fileInputStream.read(arrby)) > 0) {
                zipOutputStream.write(arrby, 0, n);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();
        this.tempFile.delete();
    }

    public void addManifest(File file, File[] arrfile) throws IOException {
        this.tempFile(file);
        byte[] arrby = new byte[1024];
        ZipInputStream zipInputStream = new ZipInputStream((InputStream)new FileInputStream(this.tempFile));
        ZipOutputStream zipOutputStream = new ZipOutputStream((OutputStream)new FileOutputStream(file));
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            String string = zipEntry.getName();
            boolean bl = true;
            int n = arrfile.length;
            int n2 = 0;
            do {
                block7 : {
                    int n3;
                    block6 : {
                        if (n2 >= n) break block6;
                        if (!arrfile[n2].getName().equals((Object)string)) break block7;
                        bl = false;
                    }
                    if (!bl) break;
                    zipOutputStream.putNextEntry(new ZipEntry(string));
                    while ((n3 = zipInputStream.read(arrby)) > 0) {
                        zipOutputStream.write(arrby, 0, n3);
                    }
                    break;
                }
                ++n2;
            } while (true);
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
        for (int i = 0; i < arrfile.length; ++i) {
            int n;
            FileInputStream fileInputStream = new FileInputStream(arrfile[i]);
            zipOutputStream.putNextEntry(new ZipEntry(arrfile[i].getName()));
            while ((n = fileInputStream.read(arrby)) > 0) {
                zipOutputStream.write(arrby, 0, n);
            }
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
        zipOutputStream.close();
        this.tempFile.delete();
    }

    /*
     * Exception decompiling
     */
    public void bikin() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 10[CATCHBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
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

    public void bikinCO(String string) {
        try {
            FileWriter fileWriter = new FileWriter(new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp", "co"));
            fileWriter.append((CharSequence)string);
            fileWriter.flush();
            fileWriter.close();
            this.lanjut = true;
            return;
        }
        catch (IOException iOException) {
            this.lanjut = false;
            iOException.printStackTrace();
            return;
        }
    }

    public void bikinFolder() {
        new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI");
        File file = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp");
        boolean bl = true;
        if (!file.exists()) {
            bl = file.mkdirs();
        }
        if (bl) {
            this.lanjut = true;
            return;
        }
        this.lanjut = false;
    }

    public void bikinUN(String string) {
        try {
            FileWriter fileWriter = new FileWriter(new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp", "un"));
            fileWriter.append((CharSequence)string);
            fileWriter.flush();
            fileWriter.close();
            this.lanjut = true;
            return;
        }
        catch (IOException iOException) {
            this.lanjut = false;
            iOException.printStackTrace();
            return;
        }
    }

    public void copy(File file) throws IOException {
        int n;
        InputStream inputStream = this.getAssets().open("NamaToko.zip");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void copyIcon(File file, File file2) throws IOException {
        Object object = this.gambaricon == null ? this.getAssets().open("ico.png") : new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] arrby = new byte[1024];
        do {
            int n;
            if ((n = object.read(arrby)) <= 0) {
                object.close();
                fileOutputStream.close();
                return;
            }
            fileOutputStream.write(arrby, 0, n);
        } while (true);
    }

    public void copyKey(File file) throws IOException {
        int n;
        InputStream inputStream = this.getAssets().open("mmdfauzan.key");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    public void copyManifest(File file) throws IOException {
        int n;
        InputStream inputStream = this.getAssets().open("AndroidManifest.xml");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n);
        }
        inputStream.close();
        fileOutputStream.close();
    }

    public void displayInterstitial() {
        if (this.interstitial.isLoaded()) {
            this.interstitial.show();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void editManifest() {
        byte[] arrby;
        AXmlEditor aXmlEditor = new AXmlEditor();
        File file = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp/AndroidManifest.xml");
        try {
            this.copyManifest(file);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        ArrayList arrayList = new ArrayList();
        File file2 = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp", "AndroidManifest.xml");
        try {
            byte[] arrby2;
            arrby = arrby2 = FileUtil.readFile(file2);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            arrby = null;
        }
        try {
            aXmlEditor.read((List<String>)arrayList, arrby);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        String string = StringUtils.join((Collection<String>)arrayList, "\n").replaceFirst("namatoko", this.username).replace((CharSequence)"namatoko.situsbelanja.com", (CharSequence)(this.username + ".situsbelanja.com")).replace((CharSequence)"namatoko.olshp.com", (CharSequence)(this.username + ".olshp.com")).replace((CharSequence)"domainbikinaplikasionlineshop.com", (CharSequence)this.dataPref.getWebsite()).replace((CharSequence)"Nama Toko", (CharSequence)this.namaaplikasi).replaceFirst("com.bikinaplikasi.onlineshop", "com.bikinaplikasi." + this.username).replaceFirst("com.bikinaplikasi.onlineshop.permission.C2D_MESSAGE", "com.bikinaplikasi." + this.username + ".permission.C2D_MESSAGE").replaceFirst("com.bikinaplikasi.onlineshop.google_measurement_service", "com.bikinaplikasi." + this.username + ".google_measurement_service").replace((CharSequence)"android:debuggable=\"true\"", (CharSequence)"android:debuggable=\"false\"").replace((CharSequence)"4.47", (CharSequence)(this.versi + ".0"));
        Integer.parseInt((String)this.versi);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        File file3 = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp", "AndroidManifest.xml");
        try {
            this.out = new FileOutputStream(file3);
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        try {
            aXmlEditor.write(string.toString(), (OutputStream)byteArrayOutputStream);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        try {
            this.out = new FileOutputStream(file3);
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        try {
            this.out.write(byteArrayOutputStream.toByteArray());
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
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 0 && n2 == -1 && intent != null) {
            String string = Build.VERSION.SDK_INT < 11 ? RealPathUtil.getRealPathFromURI_BelowAPI11((Context)this, intent.getData()) : (Build.VERSION.SDK_INT < 19 ? RealPathUtil.getRealPathFromURI_API11to18((Context)this, intent.getData()) : BikinFileActivity.getPath((Context)this, intent.getData()));
            this.icon.setImageURI(intent.getData());
            BikinFileActivity.super.checkImageSize(string);
            this.gambaricon = string;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427360);
        this.dataPref = new DataPref((Context)this);
        this.username = this.dataPref.getUsername();
        this.versi = "52";
        this.getSupportActionBar().setTitle((CharSequence)"Bikin Aplikasi");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.interstitial = new InterstitialAd((Context)this);
        this.interstitial.setAdUnitId("ca-app-pub-6383827200192790/8433611462");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.interstitial.loadAd(adRequest);
        this.adView = (AdView)this.findViewById(2131296299);
        this.adView.loadAd(new AdRequest.Builder().build());
        this.adView.setAdListener(new AdListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onAdFailedToLoad(int n) {
                this.this$0.adView.setVisibility(8);
            }

            public void onAdLoaded() {
                this.this$0.adView.setVisibility(0);
            }
        });
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.layoutForm = (LinearLayout)this.findViewById(2131296649);
        this.pilihwarna = (Button)this.findViewById(2131296743);
        this.buttonDownloadIcon = (Button)this.findViewById(2131296341);
        this.buttonGetPreviousColor = (Button)this.findViewById(2131296353);
        this.tempFile = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp/tmpzip.tmp");
        this.ednamaaplikasi = (EditText)this.findViewById(2131296727);
        this.submit = (Button)this.findViewById(2131296840);
        this.icon = (ImageView)this.findViewById(2131296579);
        this.buttonDownloadIcon.setOnClickListener(new View.OnClickListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)("http://os.bikinaplikasi.com/app/icon?id=" + this.this$0.dataPref.getUsername()))));
            }
        });
        this.buttonGetPreviousColor.setOnClickListener(new View.OnClickListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onClick(View view) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"username", (Object)BikinFileActivity.this.dataPref.getUsername());
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_color", "POST", (HashMap<String, String>)hashMap);
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
                                jSONObject.getString("message");
                                if (n != 1) return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                            BikinFileActivity.this.tempColor = jSONObject.getString("color");
                            BikinFileActivity.this.tempColor = BikinFileActivity.this.tempColor.replace((CharSequence)"#", (CharSequence)"");
                            BikinFileActivity.this.tempColor = BikinFileActivity.this.tempColor.toUpperCase();
                            BikinFileActivity.this.warna = Integer.parseInt((String)BikinFileActivity.this.tempColor, (int)16);
                            BikinFileActivity.this.selectedColor = "#" + Integer.toHexString((int)BikinFileActivity.this.warna);
                            BikinFileActivity.this.pilihwarna.setBackgroundColor(Color.parseColor((String)BikinFileActivity.this.selectedColor));
                            return;
                        }
                        Toast.makeText((Context)BikinFileActivity.this, (CharSequence)"Gagal mendapatkan warna aplikasi", (int)1).show();
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)BikinFileActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        this.pilihwarna.setOnClickListener(new View.OnClickListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onClick(View view) {
                new yuku.ambilwarna.AmbilWarnaDialog((Context)this.this$0, this.this$0.warna, false, new yuku.ambilwarna.AmbilWarnaDialog$OnAmbilWarnaListener(this){
                    final /* synthetic */ 4 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onCancel(yuku.ambilwarna.AmbilWarnaDialog ambilWarnaDialog) {
                    }

                    public void onOk(yuku.ambilwarna.AmbilWarnaDialog ambilWarnaDialog, int n) {
                        this.this$1.this$0.warna = n;
                        this.this$1.this$0.selectedColor = "#" + Integer.toHexString((int)n);
                        this.this$1.this$0.pilihwarna.setBackgroundColor(Color.parseColor((String)this.this$1.this$0.selectedColor));
                    }
                }).show();
            }
        });
        this.submit.setOnClickListener(new View.OnClickListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onClick(View view) {
                this.this$0.displayInterstitial();
                this.this$0.username = this.this$0.username.replaceAll("\\W", "");
                this.this$0.namaaplikasi = this.this$0.ednamaaplikasi.getText().toString();
                if (this.this$0.username.equals((Object)"") || this.this$0.namaaplikasi.equals((Object)"")) {
                    Toast.makeText((Context)this.this$0, (CharSequence)"Mohon lengkapi semua isian!", (int)1).show();
                    return;
                }
                this.this$0.layoutLoading.setVisibility(0);
                this.this$0.layoutForm.setVisibility(8);
                new AsyncTask<String, Void, String>(this.this$0){

                    protected /* varargs */ String doInBackground(String ... arrstring) {
                        BikinFileActivity.this.bikin();
                        return null;
                    }

                    protected void onPostExecute(String string) {
                        BikinFileActivity.this.layoutLoading.setVisibility(8);
                        BikinFileActivity.this.layoutForm.setVisibility(0);
                        if (BikinFileActivity.this.status.equals((Object)"gagal")) {
                            final AlertDialog alertDialog = new AlertDialog.Builder((Context)BikinFileActivity.this).create();
                            alertDialog.setTitle((CharSequence)"Gagal!");
                            alertDialog.setMessage((CharSequence)"Tidak dapat membuat aplikasi. Perangkat kamu tidak didukung.");
                            alertDialog.setButton(-2, (CharSequence)"OK", new DialogInterface.OnClickListener(){

                                public void onClick(DialogInterface dialogInterface, int n) {
                                    alertDialog.dismiss();
                                    BikinFileActivity.this.finish();
                                }
                            });
                            alertDialog.show();
                            return;
                        }
                        Intent intent = new Intent((Context)BikinFileActivity.this, SuccessActivity.class);
                        intent.putExtra("edit_aplikasi", BikinFileActivity.this.editAplikasi);
                        BikinFileActivity.this.startActivity(intent);
                        BikinFileActivity.this.finish();
                    }

                }.execute((Object[])new String[0]);
            }
        });
        ((Button)this.findViewById(2131296742)).setOnClickListener(new View.OnClickListener((BikinFileActivity)this){
            final /* synthetic */ BikinFileActivity this$0;
            {
                this.this$0 = bikinFileActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                this.this$0.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Pilih gambar"), 0);
            }
        });
        this.editAplikasi = this.getIntent().getBooleanExtra("edit_aplikasi", false);
        if (this.editAplikasi) {
            this.startActivity(new Intent((Context)this, PetunjukEditActivity.class));
            this.buttonDownloadIcon.setVisibility(0);
            this.buttonGetPreviousColor.setVisibility(0);
            this.getSupportActionBar().setTitle((CharSequence)"Edit Aplikasi");
            this.submit.setText((CharSequence)"EDIT APLIKASI");
            return;
        }
        this.buttonDownloadIcon.setVisibility(8);
        this.buttonGetPreviousColor.setVisibility(8);
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

    public void share() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.STREAM", (Parcelable)Uri.fromFile((File)new File(this.pathfile)));
        intent.setType("text/plain");
        this.startActivity(intent);
    }

    public void tempFile(File file) throws IOException {
        int n;
        File file2 = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp/tmpzip.tmp");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        byte[] arrby = new byte[1024];
        while ((n = fileInputStream.read(arrby)) > 0) {
            fileOutputStream.write(arrby, 0, n);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

}

