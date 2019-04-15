/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.ContentResolver
 *  android.content.ContentUris
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
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
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AlertDialog
 *  android.support.v7.app.AlertDialog$Builder
 *  android.support.v7.app.AppCompatActivity
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.EditGambarActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.helper.RealPathUtil;
import com.mmdfauzan.bamos.helper.ShowToast;
import com.mmdfauzan.bamos.helper.Uploader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class EditGambarActivity
extends AppCompatActivity {
    Button buttonSelesai;
    DataPref dataPref;
    String file_gambar;
    String file_gambar1;
    String file_gambar2;
    String file_gambar3;
    String file_gambar4;
    String file_gambar5;
    String id_produk;
    ImageButton imageButtonGambar1;
    ImageButton imageButtonGambar2;
    ImageButton imageButtonGambar3;
    ImageButton imageButtonGambar4;
    ImageButton imageButtonGambar5;
    ImageButton imageButtonHapusGambar1;
    ImageButton imageButtonHapusGambar2;
    ImageButton imageButtonHapusGambar3;
    ImageButton imageButtonHapusGambar4;
    ImageButton imageButtonHapusGambar5;
    ImageButton imageButtonSetCover1;
    ImageButton imageButtonSetCover2;
    ImageButton imageButtonSetCover3;
    ImageButton imageButtonSetCover4;
    ImageButton imageButtonSetCover5;
    int imageNumber = 1;
    LinearLayout layoutGambar1;
    LinearLayout layoutGambar2;
    LinearLayout layoutGambar3;
    LinearLayout layoutGambar4;
    LinearLayout layoutGambar5;
    String selectedImage;
    ShowToast showToast;
    TextView textViewNonPremium;

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
            if (EditGambarActivity.isExternalStorageDocument(uri)) {
                String[] arrstring = DocumentsContract.getDocumentId((Uri)uri).split(":");
                boolean bl2 = "primary".equalsIgnoreCase(arrstring[0]);
                string = null;
                if (!bl2) return string;
                return (Object)Environment.getExternalStorageDirectory() + "/" + arrstring[1];
            }
            if (EditGambarActivity.isDownloadsDocument(uri)) {
                String string2 = DocumentsContract.getDocumentId((Uri)uri);
                return EditGambarActivity.getDataColumn(context, ContentUris.withAppendedId((Uri)Uri.parse((String)"content://downloads/public_downloads"), (long)Long.valueOf((String)string2)), null, null);
            }
            boolean bl3 = EditGambarActivity.isMediaDocument(uri);
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
            return EditGambarActivity.getDataColumn(context, uri2, "_id=?", arrstring2);
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (!EditGambarActivity.isGooglePhotosUri(uri)) return EditGambarActivity.getDataColumn(context, uri, null, null);
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
    public void deleteImage() {
        if (this.imageNumber == 1) {
            this.file_gambar = this.file_gambar1;
        } else if (this.imageNumber == 2) {
            this.file_gambar = this.file_gambar2;
        } else if (this.imageNumber == 3) {
            this.file_gambar = this.file_gambar3;
        } else if (this.imageNumber == 4) {
            this.file_gambar = this.file_gambar4;
        } else if (this.imageNumber == 5) {
            this.file_gambar = this.file_gambar5;
        }
        AlertDialog alertDialog = new AlertDialog.Builder((Context)this).create();
        alertDialog.setTitle((CharSequence)"Hapus Foto");
        alertDialog.setMessage((CharSequence)"Kamu yakin ingin menghapus foto ini?");
        alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)EditGambarActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)EditGambarActivity.this.dataPref.getToken());
                            hashMap.put((Object)"file_gambar", (Object)EditGambarActivity.this.file_gambar);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/delete_image", "POST", (HashMap<String, String>)hashMap);
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
                        if (this.progressDialog.isShowing()) {
                            this.progressDialog.dismiss();
                        }
                        if (jSONObject == null) {
                            EditGambarActivity.this.showToast.ToastError();
                            return;
                        }
                        try {
                            int n = jSONObject.getInt("success");
                            String string = jSONObject.getString("message");
                            if (n == 1) {
                                new AsyncTask<String, String, JSONObject>(){
                                    JSONParser jsonParser = new JSONParser();
                                    ProgressDialog progressDialog;

                                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                        try {
                                            HashMap hashMap = new HashMap();
                                            hashMap.put((Object)"email", (Object)EditGambarActivity.this.dataPref.getEmail());
                                            hashMap.put((Object)"token", (Object)EditGambarActivity.this.dataPref.getToken());
                                            hashMap.put((Object)"idbarang", (Object)EditGambarActivity.this.id_produk);
                                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_image", "POST", (HashMap<String, String>)hashMap);
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
                                        String string;
                                        block11 : {
                                            block12 : {
                                                String string2;
                                                block14 : {
                                                    block13 : {
                                                        if (this.progressDialog.isShowing()) {
                                                            this.progressDialog.dismiss();
                                                        }
                                                        if (jSONObject == null) {
                                                            EditGambarActivity.this.showToast.ToastError();
                                                            EditGambarActivity.this.finish();
                                                            return;
                                                        }
                                                        try {
                                                            int n = jSONObject.getInt("success");
                                                            string = jSONObject.getString("message");
                                                            if (n != 1) break block11;
                                                            JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                                            String string3 = jSONObject2.getString("gambar");
                                                            String string4 = jSONObject2.getString("gambar2");
                                                            String string5 = jSONObject2.getString("gambar3");
                                                            String string6 = jSONObject2.getString("gambar4");
                                                            string2 = jSONObject2.getString("gambar5");
                                                            EditGambarActivity.this.file_gambar1 = jSONObject2.getString("file_gambar1");
                                                            EditGambarActivity.this.file_gambar2 = jSONObject2.getString("file_gambar2");
                                                            EditGambarActivity.this.file_gambar3 = jSONObject2.getString("file_gambar3");
                                                            EditGambarActivity.this.file_gambar4 = jSONObject2.getString("file_gambar4");
                                                            EditGambarActivity.this.file_gambar5 = jSONObject2.getString("file_gambar5");
                                                            int n2 = jSONObject2.getInt("premium");
                                                            if (!string3.equals((Object)"") && !string3.equals((Object)"null")) {
                                                                Picasso.with((Context)EditGambarActivity.this).load("http://os.bikinaplikasi.com/gambar/" + string3).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar1);
                                                            }
                                                            if (n2 != 1) break block12;
                                                            EditGambarActivity.this.layoutGambar2.setVisibility(0);
                                                            EditGambarActivity.this.layoutGambar3.setVisibility(0);
                                                            EditGambarActivity.this.layoutGambar4.setVisibility(0);
                                                            EditGambarActivity.this.layoutGambar5.setVisibility(0);
                                                            EditGambarActivity.this.textViewNonPremium.setVisibility(8);
                                                            if (!string4.equals((Object)"") && !string4.equals((Object)"null")) {
                                                                Picasso.with((Context)EditGambarActivity.this).load("http://os.bikinaplikasi.com/gambar/" + string4).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar2);
                                                            } else {
                                                                Picasso.with((Context)EditGambarActivity.this).load(2131230939).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar2);
                                                            }
                                                            if (!string5.equals((Object)"") && !string5.equals((Object)"null")) {
                                                                Picasso.with((Context)EditGambarActivity.this).load("http://os.bikinaplikasi.com/gambar/" + string5).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar3);
                                                            } else {
                                                                Picasso.with((Context)EditGambarActivity.this).load(2131230939).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar3);
                                                            }
                                                            if (string6.equals((Object)"") || string6.equals((Object)"null")) break block13;
                                                            Picasso.with((Context)EditGambarActivity.this).load("http://os.bikinaplikasi.com/gambar/" + string6).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar4);
                                                            break block14;
                                                        }
                                                        catch (JSONException jSONException) {
                                                            jSONException.printStackTrace();
                                                            return;
                                                        }
                                                    }
                                                    Picasso.with((Context)EditGambarActivity.this).load(2131230939).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar4);
                                                }
                                                if (!string2.equals((Object)"") && !string2.equals((Object)"null")) {
                                                    Picasso.with((Context)EditGambarActivity.this).load("http://os.bikinaplikasi.com/gambar/" + string2).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar5);
                                                    return;
                                                }
                                                Picasso.with((Context)EditGambarActivity.this).load(2131230939).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)EditGambarActivity.this.imageButtonGambar5);
                                                return;
                                            }
                                            EditGambarActivity.this.textViewNonPremium.setVisibility(0);
                                            EditGambarActivity.this.layoutGambar2.setVisibility(8);
                                            EditGambarActivity.this.layoutGambar3.setVisibility(8);
                                            EditGambarActivity.this.layoutGambar4.setVisibility(8);
                                            EditGambarActivity.this.layoutGambar5.setVisibility(8);
                                            return;
                                        }
                                        Toast.makeText((Context)EditGambarActivity.this, (CharSequence)string, (int)1).show();
                                        EditGambarActivity.this.finish();
                                    }

                                    protected void onPreExecute() {
                                        this.progressDialog = new ProgressDialog((Context)EditGambarActivity.this);
                                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                                        this.progressDialog.setIndeterminate(false);
                                        this.progressDialog.setCancelable(false);
                                        this.progressDialog.show();
                                    }
                                }.execute((Object[])new String[0]);
                                return;
                            }
                            Toast.makeText((Context)EditGambarActivity.this, (CharSequence)string, (int)1).show();
                            return;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)EditGambarActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this, alertDialog){
            final /* synthetic */ EditGambarActivity this$0;
            final /* synthetic */ AlertDialog val$alertDialog;
            {
                this.this$0 = editGambarActivity;
                this.val$alertDialog = alertDialog;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                this.val$alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent intent) {
        if (n == 0 && n2 == -1 && intent != null) {
            String string = Build.VERSION.SDK_INT < 11 ? RealPathUtil.getRealPathFromURI_BelowAPI11((Context)this, intent.getData()) : (Build.VERSION.SDK_INT < 19 ? RealPathUtil.getRealPathFromURI_API11to18((Context)this, intent.getData()) : EditGambarActivity.getPath((Context)this, intent.getData()));
            if (this.imageNumber == 1) {
                Picasso.with((Context)this).load(intent.getData()).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)this.imageButtonGambar1);
                this.file_gambar = this.file_gambar1;
            } else if (this.imageNumber == 2) {
                Picasso.with((Context)this).load(intent.getData()).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)this.imageButtonGambar2);
                this.file_gambar = this.file_gambar2;
            } else if (this.imageNumber == 3) {
                Picasso.with((Context)this).load(intent.getData()).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)this.imageButtonGambar3);
                this.file_gambar = this.file_gambar3;
            } else if (this.imageNumber == 4) {
                Picasso.with((Context)this).load(intent.getData()).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)this.imageButtonGambar4);
                this.file_gambar = this.file_gambar4;
            } else if (this.imageNumber == 5) {
                Picasso.with((Context)this).load(intent.getData()).placeholder(2131230939).resize(500, 500).centerInside().into((ImageView)this.imageButtonGambar5);
                this.file_gambar = this.file_gambar5;
            }
            this.selectedImage = string;
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();
                ProgressDialog progressDialog;

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        JSONObject jSONObject = new Uploader().uploadFile(EditGambarActivity.this.selectedImage, "http://os.bikinaplikasi.com/api/admin_api_v2/upload_image?e=" + EditGambarActivity.this.dataPref.getEmail() + "&t=" + EditGambarActivity.this.dataPref.getToken(), EditGambarActivity.this.file_gambar);
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
                        EditGambarActivity.this.showToast.ToastError();
                        return;
                    }
                    try {
                        int n = jSONObject.getInt("success");
                        String string = jSONObject.getString("message");
                        if (n == 1) {
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        Toast.makeText((Context)EditGambarActivity.this, (CharSequence)string, (int)1).show();
                        return;
                    }
                    catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                        return;
                    }
                }

                protected void onPreExecute() {
                    this.progressDialog = new ProgressDialog((Context)EditGambarActivity.this);
                    this.progressDialog.setMessage((CharSequence)"Mengunggah gambar.");
                    this.progressDialog.setIndeterminate(false);
                    this.progressDialog.setCancelable(false);
                    this.progressDialog.show();
                }
            }.execute((Object[])new String[0]);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427369);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Foto Produk");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.showToast = new ShowToast((Context)this);
        this.id_produk = this.getIntent().getStringExtra("idbarang");
        this.layoutGambar1 = (LinearLayout)this.findViewById(2131296653);
        this.layoutGambar2 = (LinearLayout)this.findViewById(2131296654);
        this.layoutGambar3 = (LinearLayout)this.findViewById(2131296655);
        this.layoutGambar4 = (LinearLayout)this.findViewById(2131296656);
        this.layoutGambar5 = (LinearLayout)this.findViewById(2131296657);
        this.layoutGambar2.setVisibility(8);
        this.layoutGambar3.setVisibility(8);
        this.layoutGambar4.setVisibility(8);
        this.layoutGambar5.setVisibility(8);
        this.imageButtonGambar1 = (ImageButton)this.findViewById(2131296591);
        this.imageButtonGambar2 = (ImageButton)this.findViewById(2131296592);
        this.imageButtonGambar3 = (ImageButton)this.findViewById(2131296593);
        this.imageButtonGambar4 = (ImageButton)this.findViewById(2131296594);
        this.imageButtonGambar5 = (ImageButton)this.findViewById(2131296595);
        this.imageButtonHapusGambar1 = (ImageButton)this.findViewById(2131296596);
        this.imageButtonHapusGambar2 = (ImageButton)this.findViewById(2131296597);
        this.imageButtonHapusGambar3 = (ImageButton)this.findViewById(2131296598);
        this.imageButtonHapusGambar4 = (ImageButton)this.findViewById(2131296599);
        this.imageButtonHapusGambar5 = (ImageButton)this.findViewById(2131296600);
        this.imageButtonSetCover1 = (ImageButton)this.findViewById(2131296612);
        this.imageButtonSetCover2 = (ImageButton)this.findViewById(2131296613);
        this.imageButtonSetCover3 = (ImageButton)this.findViewById(2131296614);
        this.imageButtonSetCover4 = (ImageButton)this.findViewById(2131296615);
        this.imageButtonSetCover5 = (ImageButton)this.findViewById(2131296616);
        this.textViewNonPremium = (TextView)this.findViewById(2131297107);
        this.textViewNonPremium.setVisibility(8);
        this.buttonSelesai = (Button)this.findViewById(2131296391);
        this.buttonSelesai.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.finish();
            }
        });
        this.imageButtonGambar1.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 1;
                this.this$0.selectImage();
            }
        });
        this.imageButtonGambar2.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 2;
                this.this$0.selectImage();
            }
        });
        this.imageButtonGambar3.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 3;
                this.this$0.selectImage();
            }
        });
        this.imageButtonGambar4.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 4;
                this.this$0.selectImage();
            }
        });
        this.imageButtonGambar5.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 5;
                this.this$0.selectImage();
            }
        });
        this.imageButtonHapusGambar1.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 1;
                this.this$0.deleteImage();
            }
        });
        this.imageButtonHapusGambar2.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 2;
                this.this$0.deleteImage();
            }
        });
        this.imageButtonHapusGambar3.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 3;
                this.this$0.deleteImage();
            }
        });
        this.imageButtonHapusGambar4.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 4;
                this.this$0.deleteImage();
            }
        });
        this.imageButtonHapusGambar5.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 5;
                this.this$0.deleteImage();
            }
        });
        this.imageButtonSetCover1.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 1;
                this.this$0.setCategoryImage();
            }
        });
        this.imageButtonSetCover2.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 2;
                this.this$0.setCategoryImage();
            }
        });
        this.imageButtonSetCover3.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 3;
                this.this$0.setCategoryImage();
            }
        });
        this.imageButtonSetCover4.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 4;
                this.this$0.setCategoryImage();
            }
        });
        this.imageButtonSetCover5.setOnClickListener(new View.OnClickListener((EditGambarActivity)this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(View view) {
                this.this$0.imageNumber = 5;
                this.this$0.setCategoryImage();
            }
        });
        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
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

    public void selectImage() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(Intent.createChooser((Intent)intent, (CharSequence)"Pilih foto produk"), 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setCategoryImage() {
        if (this.imageNumber == 1) {
            this.file_gambar = this.file_gambar1;
        } else if (this.imageNumber == 2) {
            this.file_gambar = this.file_gambar2;
        } else if (this.imageNumber == 3) {
            this.file_gambar = this.file_gambar3;
        } else if (this.imageNumber == 4) {
            this.file_gambar = this.file_gambar4;
        } else if (this.imageNumber == 5) {
            this.file_gambar = this.file_gambar5;
        }
        AlertDialog alertDialog = new AlertDialog.Builder((Context)this).create();
        alertDialog.setTitle((CharSequence)"Jadikan Foto Kategori");
        alertDialog.setMessage((CharSequence)"Foto ini akan dijadikan foto kategori di tampilan awal aplikasi. Lanjutkan?");
        alertDialog.setButton(-1, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
            final /* synthetic */ EditGambarActivity this$0;
            {
                this.this$0 = editGambarActivity;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)EditGambarActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)EditGambarActivity.this.dataPref.getToken());
                            hashMap.put((Object)"idbarang", (Object)EditGambarActivity.this.id_produk);
                            hashMap.put((Object)"file_gambar", (Object)EditGambarActivity.this.file_gambar);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/set_category_image", "POST", (HashMap<String, String>)hashMap);
                            return jSONObject;
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            return null;
                        }
                    }

                    protected void onPostExecute(JSONObject jSONObject) {
                        if (this.progressDialog.isShowing()) {
                            this.progressDialog.dismiss();
                        }
                        if (jSONObject != null) {
                            try {
                                jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                Toast.makeText((Context)EditGambarActivity.this, (CharSequence)string, (int)1).show();
                                return;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        EditGambarActivity.this.showToast.ToastError();
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)EditGambarActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        alertDialog.setButton(-2, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this, alertDialog){
            final /* synthetic */ EditGambarActivity this$0;
            final /* synthetic */ AlertDialog val$alertDialog;
            {
                this.this$0 = editGambarActivity;
                this.val$alertDialog = alertDialog;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                this.val$alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

}

