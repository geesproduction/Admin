/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.AssetManager
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.provider.DocumentsContract
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Audio
 *  android.provider.MediaStore$Audio$Media
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.provider.MediaStore$Video
 *  android.provider.MediaStore$Video$Media
 *  android.support.v4.app.ActivityCompat
 *  android.support.v4.content.ContextCompat
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.Toast
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
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.zip.ZipEntry
 *  java.util.zip.ZipInputStream
 *  java.util.zip.ZipOutputStream
 */
package com.mmdfauzan.bamos;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.mmdfauzan.bamos.LoginActivity;
import com.mmdfauzan.bamos.SplashActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.AXmlEditor;
import com.mmdfauzan.bamos.helper.FileUtil;
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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class SplashActivity
extends AppCompatActivity {
    private static final int REQUEST_WRITE_STORAGE = 112;
    Button buttonStart;
    DataPref dataPref;
    String gambaricon = null;
    boolean lanjut = true;
    LinearLayout layoutFailed;
    LinearLayout layoutTest;
    LinearLayout layoutWelcome;
    String namaaplikasi = "untungshop";
    int num = 0;
    OutputStream out;
    String pathfile;
    String status = "sukses";
    File tempFile = new File((Object)Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/tmp/tmpzip.tmp");
    String username = "untungshop";
    String versi = "52";
    int warna = -30584;

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
            if (SplashActivity.isExternalStorageDocument(uri)) {
                String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
                boolean bl2 = "primary".equalsIgnoreCase(arrstring[0]);
                string = null;
                if (!bl2) return string;
                return (Object)Environment.getExternalStorageDirectory() + "/" + arrstring[1];
            }
            if (SplashActivity.isDownloadsDocument(uri)) {
                String string2 = DocumentsContract.getDocumentId((Uri)uri);
                return SplashActivity.getDataColumn(context, ContentUris.withAppendedId((Uri)Uri.parse((String)"content://downloads/public_downloads"), (long)Long.valueOf((String)string2)), null, null);
            }
            boolean bl3 = SplashActivity.isMediaDocument(uri);
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
            return SplashActivity.getDataColumn(context, uri2, "_id=?", arrstring2);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (!SplashActivity.isGooglePhotosUri(uri)) return SplashActivity.getDataColumn(context, uri, null, null);
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

    public void bikinCO(int n) {
        String string = "#" + Integer.toHexString((int)n);
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
        String string = StringUtils.join((Collection<String>)arrayList, "\n").replaceFirst("namatoko", this.username).replace((CharSequence)"namatoko.situsbelanja.com", (CharSequence)(this.username + ".situsbelanja.com")).replace((CharSequence)"namatoko.olshp.com", (CharSequence)(this.username + ".olshp.com")).replaceAll("domainbikinaplikasionlineshop.com", this.dataPref.getWebsite()).replace((CharSequence)"Nama Toko", (CharSequence)this.namaaplikasi).replaceFirst("com.bikinaplikasi.onlineshop", "com.bikinaplikasi." + this.username).replaceFirst("com.bikinaplikasi.onlineshop.permission.C2D_MESSAGE", "com.bikinaplikasi." + this.username + ".permission.C2D_MESSAGE").replaceFirst("com.bikinaplikasi.onlineshop.google_measurement_service", "com.bikinaplikasi." + this.username + ".google_measurement_service").replace((CharSequence)"android:debuggable=\"true\"", (CharSequence)"android:debuggable=\"false\"").replace((CharSequence)"4.47", (CharSequence)(this.versi + ".0"));
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
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427399);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Bikin Aplikasi  Online Shop");
        this.buttonStart = (Button)this.findViewById(2131296398);
        this.layoutWelcome = (LinearLayout)this.findViewById(2131296696);
        this.layoutTest = (LinearLayout)this.findViewById(2131296684);
        this.layoutFailed = (LinearLayout)this.findViewById(2131296647);
        this.layoutTest.setVisibility(8);
        this.layoutWelcome.setVisibility(8);
        this.layoutFailed.setVisibility(8);
        this.dataPref = new DataPref((Context)this);
        if (this.dataPref.isStarted()) {
            this.startActivity(new Intent((Context)this, LoginActivity.class));
            this.finish();
            return;
        }
        this.layoutWelcome.setVisibility(0);
        boolean bl = ContextCompat.checkSelfPermission((Context)this, (String)"android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        if (!bl) {
            ActivityCompat.requestPermissions((Activity)this, (String[])new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, (int)112);
        }
        this.buttonStart.setOnClickListener(new View.OnClickListener((SplashActivity)this){
            final /* synthetic */ SplashActivity this$0;
            {
                this.this$0 = splashActivity;
            }

            public void onClick(View view) {
                this.this$0.layoutWelcome.setVisibility(8);
                this.this$0.layoutTest.setVisibility(0);
                new AsyncTask<String, Void, String>(this.this$0){

                    protected /* varargs */ String doInBackground(String ... arrstring) {
                        SplashActivity.this.bikin();
                        return null;
                    }

                    protected void onPostExecute(String string) {
                        SplashActivity.this.layoutTest.setVisibility(8);
                        if (SplashActivity.this.status.equals((Object)"gagal")) {
                            SplashActivity.this.layoutFailed.setVisibility(0);
                            return;
                        }
                        SplashActivity.this.dataPref.setStarted(true);
                        SplashActivity.this.startActivity(new Intent((Context)SplashActivity.this, LoginActivity.class));
                        SplashActivity.this.finish();
                    }
                }.execute((Object[])new String[0]);
            }
        });
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        super.onRequestPermissionsResult(n, arrstring, arrn);
        switch (n) {
            default: {
                return;
            }
            case 112: {
                if (arrn.length > 0 && arrn[0] == 0) return;
                Toast.makeText((Context)this, (CharSequence)"Mohon izinkan aplikasi untuk mengakses penyimpanan data.", (int)1).show();
                return;
            }
        }
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

