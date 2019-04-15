/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.StrictMode
 *  android.os.StrictMode$ThreadPolicy
 *  android.os.StrictMode$ThreadPolicy$Builder
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
 *  android.text.Html
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.Toast
 *  android.widget.ToggleButton
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.HashMap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mmdfauzan.bamos.BannerActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.RealPathUtil;
import com.mmdfauzan.bamos.helper.Uploader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class BannerActivity
extends AppCompatActivity {
    String banner;
    String bannerDescription;
    int bannerId;
    String bannerImage = "";
    Button buttonGambar;
    Button buttonSave;
    DataPref dataPref;
    EditText editTextBannerDescription;
    String editedBanner;
    String editedBannerDescription;
    Boolean imageChanged = false;
    ImageView imageViewBanner;
    LinearLayout layoutBannerOn;
    String selectedImage;
    ToggleButton toggleButtonAktif;
    String username;

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
            if (BannerActivity.isExternalStorageDocument(uri)) {
                String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
                boolean bl2 = "primary".equalsIgnoreCase(arrstring[0]);
                string = null;
                if (!bl2) return string;
                return (Object)Environment.getExternalStorageDirectory() + "/" + arrstring[1];
            }
            if (BannerActivity.isDownloadsDocument(uri)) {
                String string2 = DocumentsContract.getDocumentId((Uri)uri);
                return BannerActivity.getDataColumn(context, ContentUris.withAppendedId((Uri)Uri.parse((String)"content://downloads/public_downloads"), (long)Long.valueOf((String)string2)), null, null);
            }
            boolean bl3 = BannerActivity.isMediaDocument(uri);
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
            return BannerActivity.getDataColumn(context, uri2, "_id=?", arrstring2);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (!BannerActivity.isGooglePhotosUri(uri)) return BannerActivity.getDataColumn(context, uri, null, null);
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

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 0 && n2 == -1 && intent != null) {
            String string = Build.VERSION.SDK_INT < 11 ? RealPathUtil.getRealPathFromURI_BelowAPI11((Context)this, intent.getData()) : (Build.VERSION.SDK_INT < 19 ? RealPathUtil.getRealPathFromURI_API11to18((Context)this, intent.getData()) : BannerActivity.getPath((Context)this, intent.getData()));
            Picasso.with((Context)this).load(intent.getData()).placeholder(2131230938).into(this.imageViewBanner);
            this.selectedImage = string;
            this.imageChanged = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427358);
        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.setThreadPolicy((StrictMode.ThreadPolicy)new StrictMode.ThreadPolicy.Builder().permitAll().build());
        }
        this.getSupportActionBar().setTitle((CharSequence)"Atur Banner");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.bannerId = this.getIntent().getIntExtra("banner_id", 1);
        this.bannerImage = this.bannerId == 5 ? "_5" : (this.bannerId == 4 ? "_4" : (this.bannerId == 3 ? "_3" : (this.bannerId == 2 ? "_2" : "")));
        this.layoutBannerOn = (LinearLayout)this.findViewById(2131296640);
        this.buttonGambar = (Button)this.findViewById(2131296352);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.toggleButtonAktif = (ToggleButton)this.findViewById(2131297144);
        this.imageViewBanner = (ImageView)this.findViewById(2131296625);
        this.editTextBannerDescription = (EditText)this.findViewById(2131296475);
        this.toggleButtonAktif.setChecked(false);
        this.layoutBannerOn.setVisibility(8);
        new AsyncTask<String, String, JSONObject>(){
            JSONParser jsonParser = new JSONParser();
            ProgressDialog progressDialog;

            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                try {
                    HashMap hashMap = new HashMap();
                    hashMap.put((Object)"email", (Object)BannerActivity.this.dataPref.getEmail());
                    hashMap.put((Object)"token", (Object)BannerActivity.this.dataPref.getToken());
                    hashMap.put((Object)"banner_id", (Object)String.valueOf((int)BannerActivity.this.bannerId));
                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_banner", "POST", (HashMap<String, String>)hashMap);
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
                            BannerActivity.this.banner = jSONObject.getString("banner");
                            BannerActivity.this.username = jSONObject.getString("username");
                            BannerActivity.this.bannerDescription = jSONObject.getString("banner_description");
                            if (BannerActivity.this.banner.equals((Object)"1")) {
                                BannerActivity.this.toggleButtonAktif.setChecked(true);
                                BannerActivity.this.layoutBannerOn.setVisibility(0);
                            } else {
                                BannerActivity.this.toggleButtonAktif.setChecked(false);
                                BannerActivity.this.layoutBannerOn.setVisibility(8);
                            }
                            String string2 = Html.fromHtml((String)BannerActivity.this.bannerDescription.replace((CharSequence)"\n", (CharSequence)"&lt;br/&gt;")).toString().replace((CharSequence)"<br/>", (CharSequence)"\n");
                            BannerActivity.this.editTextBannerDescription.setText((CharSequence)string2);
                            String string3 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                            Picasso.with((Context)BannerActivity.this).load("http://os.bikinaplikasi.com/gambar/banner/" + BannerActivity.this.username + BannerActivity.this.bannerImage + ".jpg?" + string3).placeholder(2131230938).into(BannerActivity.this.imageViewBanner);
                            return;
                        }
                        if (n != 0) return;
                        {
                            Toast.makeText((Context)BannerActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }
                new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
            }

            protected void onPreExecute() {
                this.progressDialog = new ProgressDialog((Context)BannerActivity.this);
                this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                this.progressDialog.setIndeterminate(false);
                this.progressDialog.setCancelable(false);
                this.progressDialog.show();
            }
        }.execute((Object[])new String[0]);
        this.toggleButtonAktif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((BannerActivity)this){
            final /* synthetic */ BannerActivity this$0;
            {
                this.this$0 = bannerActivity;
            }

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    this.this$0.layoutBannerOn.setVisibility(0);
                    return;
                }
                this.this$0.layoutBannerOn.setVisibility(8);
            }
        });
        this.buttonGambar.setOnClickListener(new View.OnClickListener((BannerActivity)this){
            final /* synthetic */ BannerActivity this$0;
            {
                this.this$0 = bannerActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                this.this$0.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Pilih gambar banner"), 0);
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((BannerActivity)this){
            final /* synthetic */ BannerActivity this$0;
            {
                this.this$0 = bannerActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                this.this$0.editedBannerDescription = this.this$0.editTextBannerDescription.getText().toString();
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (this.this$0.editedBannerDescription.length() <= 0) {
                    this.this$0.editTextBannerDescription.setError((CharSequence)"Mohon isi deskripsi banner");
                    return;
                }
                this.this$0.editTextBannerDescription.setError(null);
                this.this$0.editedBanner = this.this$0.toggleButtonAktif.isChecked() ? "1" : "0";
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        JSONObject jSONObject;
                        block3 : {
                            JSONObject jSONObject2;
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)BannerActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)BannerActivity.this.dataPref.getToken());
                                hashMap.put((Object)"banner_id", (Object)String.valueOf((int)BannerActivity.this.bannerId));
                                hashMap.put((Object)"banner", (Object)BannerActivity.this.editedBanner);
                                hashMap.put((Object)"banner_description", (Object)BannerActivity.this.editedBannerDescription);
                                jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_banner", "POST", (HashMap<String, String>)hashMap);
                                if (jSONObject == null) break block3;
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                                return null;
                            }
                            if (jSONObject.getInt("success") != 1 || !BannerActivity.this.imageChanged.booleanValue()) break block3;
                            jSONObject = jSONObject2 = new Uploader().uploadFile(BannerActivity.this.selectedImage, "http://os.bikinaplikasi.com/api/admin_api_v2/upload_banner?e=" + BannerActivity.this.dataPref.getEmail() + "&t=" + BannerActivity.this.dataPref.getToken(), BannerActivity.this.username + BannerActivity.this.bannerImage + ".jpg");
                        }
                        return jSONObject;
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
                                    Toast.makeText((Context)BannerActivity.this, (CharSequence)string, (int)1).show();
                                    BannerActivity.this.finish();
                                    return;
                                }
                                if (n == 0) {
                                    Toast.makeText((Context)BannerActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                break block8;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)BannerActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Menyimpan banner.");
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

