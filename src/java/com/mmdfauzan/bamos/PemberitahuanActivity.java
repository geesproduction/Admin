/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.ProgressDialog
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.graphics.Paint
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Environment
 *  android.os.Parcelable
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.util.Log
 *  android.view.MenuItem
 *  android.view.View
 *  android.webkit.DownloadListener
 *  android.webkit.GeolocationPermissions
 *  android.webkit.GeolocationPermissions$Callback
 *  android.webkit.JsResult
 *  android.webkit.ValueCallback
 *  android.webkit.WebChromeClient
 *  android.webkit.WebChromeClient$FileChooserParams
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.TextView
 *  android.widget.Toast
 *  java.io.File
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.HashMap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import com.mmdfauzan.bamos.PemberitahuanActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class PemberitahuanActivity
extends AppCompatActivity {
    private static final int FILECHOOSER_RESULTCODE = 1;
    private static final int INPUT_FILE_REQUEST_CODE = 1;
    private static final String TAG = PemberitahuanActivity.class.getSimpleName();
    DataPref dataPref;
    private String mCameraPhotoPath;
    private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMessage;
    TextView textViewPemberitahuan;
    private WebSettings webSettings;
    WebView webViewPemberitahuan;

    private File createImageFile() throws IOException {
        String string = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return File.createTempFile((String)("JPEG_" + string + "_"), (String)".jpg", (File)Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_PICTURES));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void onActivityResult(int var1, int var2_3, Intent var3_2) {
        block10 : {
            block11 : {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (var1 != 1 || this.mFilePathCallback == null) {
                        super.onActivityResult(var1, var2_3, var3_2);
                        return;
                    }
                    var7_4 = null;
                    if (var2_3 == -1) {
                        if (var3_2 == null) {
                            var9_5 = this.mCameraPhotoPath;
                            var7_4 = null;
                            if (var9_5 != null) {
                                var7_4 = new Uri[]{Uri.parse((String)this.mCameraPhotoPath)};
                            }
                        } else {
                            var8_6 = var3_2.getDataString();
                            var7_4 = null;
                            if (var8_6 != null) {
                                var7_4 = new Uri[]{Uri.parse((String)var8_6)};
                            }
                        }
                    }
                    this.mFilePathCallback.onReceiveValue(var7_4);
                    this.mFilePathCallback = null;
                    return;
                }
                if (Build.VERSION.SDK_INT > 19) return;
                if (var1 != 1 || this.mUploadMessage == null) {
                    super.onActivityResult(var1, var2_3, var3_2);
                    return;
                }
                if (var1 != 1) return;
                if (this.mUploadMessage == null) return;
                if (var2_3 == -1) break block11;
                var5_7 = null;
                break block10;
            }
            if (var3_2 != null) ** GOTO lbl34
            try {
                var5_7 = this.mCapturedImageURI;
                break block10;
lbl34: // 1 sources:
                var5_7 = var6_8 = var3_2.getData();
            }
            catch (Exception var4_9) {
                Toast.makeText((Context)this.getApplicationContext(), (CharSequence)("activity :" + (Object)var4_9), (int)1).show();
                var5_7 = null;
            }
        }
        this.mUploadMessage.onReceiveValue(var5_7);
        this.mUploadMessage = null;
    }

    public void onBackPressed() {
        if (this.webViewPemberitahuan.canGoBack()) {
            this.webViewPemberitahuan.goBack();
            return;
        }
        super.onBackPressed();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427386);
        this.getSupportActionBar().setTitle((CharSequence)"Informasi");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.textViewPemberitahuan = (TextView)this.findViewById(2131297109);
        this.webViewPemberitahuan = (WebView)this.findViewById(2131297172);
        this.webViewPemberitahuan.clearCache(true);
        this.webViewPemberitahuan.loadUrl("http://os.bikinaplikasi.com/api/admin_api_v2/notifications?email=" + this.dataPref.getEmail() + "&token=" + this.dataPref.getToken());
        this.webViewPemberitahuan.getSettings().setJavaScriptEnabled(true);
        this.webViewPemberitahuan.getSettings().setGeolocationEnabled(true);
        this.webViewPemberitahuan.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webViewPemberitahuan.setHorizontalScrollBarEnabled(false);
        this.webViewPemberitahuan.setVerticalScrollBarEnabled(false);
        this.webViewPemberitahuan.setDownloadListener(new DownloadListener((PemberitahuanActivity)this){
            final /* synthetic */ PemberitahuanActivity this$0;
            {
                this.this$0 = pemberitahuanActivity;
            }

            public void onDownloadStart(String string, String string2, String string3, String string4, long l) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse((String)string));
                this.this$0.startActivity(intent);
            }
        });
        this.webViewPemberitahuan.getSettings().setBuiltInZoomControls(true);
        this.webViewPemberitahuan.getSettings().setDisplayZoomControls(false);
        this.webSettings = this.webViewPemberitahuan.getSettings();
        this.webSettings.setAppCacheEnabled(false);
        WebSettings webSettings = this.webSettings;
        webSettings.setCacheMode(1);
        this.webSettings.setJavaScriptEnabled(true);
        this.webSettings.setLoadWithOverviewMode(true);
        this.webSettings.setAllowFileAccess(true);
        this.webViewPemberitahuan.setWebChromeClient(new WebChromeClient(){

            public void onGeolocationPermissionsShowPrompt(String string, GeolocationPermissions.Callback callback) {
                callback.invoke(string, true, false);
            }

            public boolean onJsConfirm(WebView webView, String string, String string2, final JsResult jsResult) {
                new AlertDialog.Builder(webView.getContext()).setTitle((CharSequence)webView.getTitle()).setMessage((CharSequence)string2).setCancelable(false).setPositiveButton(17039370, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        jsResult.confirm();
                    }
                }).setNegativeButton(17039360, new DialogInterface.OnClickListener(){

                    public void onClick(DialogInterface dialogInterface, int n) {
                        jsResult.cancel();
                    }
                }).show();
                return true;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                if (PemberitahuanActivity.this.mFilePathCallback != null) {
                    PemberitahuanActivity.this.mFilePathCallback.onReceiveValue(null);
                }
                PemberitahuanActivity.this.mFilePathCallback = valueCallback;
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                if (intent.resolveActivity(PemberitahuanActivity.this.getPackageManager()) != null) {
                    File file = null;
                    try {
                        file = PemberitahuanActivity.this.createImageFile();
                        intent.putExtra("PhotoPath", PemberitahuanActivity.this.mCameraPhotoPath);
                    }
                    catch (IOException iOException) {
                        Log.e((String)TAG, (String)"Unable to create Image File", (Throwable)iOException);
                    }
                    if (file != null) {
                        PemberitahuanActivity.this.mCameraPhotoPath = "file:" + file.getAbsolutePath();
                        intent.putExtra("output", (Parcelable)Uri.fromFile((File)file));
                    } else {
                        intent = null;
                    }
                }
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.addCategory("android.intent.category.OPENABLE");
                intent2.setType("*/*");
                Intent[] arrintent = intent != null ? new Intent[]{intent} : new Intent[]{};
                Intent intent3 = new Intent("android.intent.action.CHOOSER");
                intent3.putExtra("android.intent.extra.INTENT", (Parcelable)intent2);
                intent3.putExtra("android.intent.extra.TITLE", "Image Chooser");
                intent3.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])arrintent);
                PemberitahuanActivity.this.startActivityForResult(intent3, 1);
                return true;
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                this.openFileChooser(valueCallback, "");
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String string) {
                PemberitahuanActivity.this.mUploadMessage = valueCallback;
                File file = new File(Environment.getExternalStoragePublicDirectory((String)Environment.DIRECTORY_PICTURES), "AndroidExampleFolder");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File((Object)file + File.separator + "IMG_" + String.valueOf((long)System.currentTimeMillis()) + ".jpg");
                Log.d((String)"File", (String)("File: " + (Object)file2));
                PemberitahuanActivity.this.mCapturedImageURI = Uri.fromFile((File)file2);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", (Parcelable)PemberitahuanActivity.this.mCapturedImageURI);
                Intent intent2 = new Intent("android.intent.action.GET_CONTENT");
                intent2.addCategory("android.intent.category.OPENABLE");
                intent2.setType("image/*");
                Intent intent3 = Intent.createChooser((Intent)intent2, (CharSequence)"Image Chooser");
                intent3.putExtra("android.intent.extra.INITIAL_INTENTS", new Parcelable[]{intent});
                PemberitahuanActivity.this.startActivityForResult(intent3, 1);
            }

            public void openFileChooser(ValueCallback<Uri> valueCallback, String string, String string2) {
                this.openFileChooser(valueCallback, string);
            }

        });
        if (Build.VERSION.SDK_INT >= 19) {
            this.webViewPemberitahuan.setLayerType(2, null);
        } else if (Build.VERSION.SDK_INT >= 11 && Build.VERSION.SDK_INT < 19) {
            this.webViewPemberitahuan.setLayerType(1, null);
        }
        this.webViewPemberitahuan.setWebViewClient(new WebViewClient((PemberitahuanActivity)this){
            final /* synthetic */ PemberitahuanActivity this$0;
            {
                this.this$0 = pemberitahuanActivity;
            }

            public void onPageFinished(WebView webView, String string) {
                this.this$0.getSupportActionBar().setTitle((CharSequence)webView.getTitle());
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
                this.this$0.getSupportActionBar().setTitle((CharSequence)"Loading...");
            }

            public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
                this.this$0.webViewPemberitahuan.setVisibility(8);
                Toast.makeText((Context)this.this$0, (CharSequence)"Tidak dapat terhubung ke server.", (int)1).show();
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean setAction(Uri uri) {
                boolean bl;
                String string = uri.toString();
                this.this$0.dataPref.getWebsite();
                this.this$0.dataPref.getUsername();
                if (!string.startsWith("http://") && !string.startsWith("https://")) {
                    bl = true;
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse((String)string));
                    this.this$0.startActivity(intent);
                    return bl;
                }
                if (!(string.startsWith("http://appandro.id/go") || string.startsWith("https://appandro.id/go") || string.startsWith("http://www.appandro.id/go"))) {
                    boolean bl2 = string.startsWith("https://www.appandro.id/go");
                    bl = false;
                    if (!bl2) return bl;
                }
                String string2 = string.replace((CharSequence)"http://www.appandro.id/go?to=", (CharSequence)"").replace((CharSequence)"https://www.appandro.id/go?to=", (CharSequence)"").replace((CharSequence)"http://appandro.id/go?to=", (CharSequence)"").replace((CharSequence)"https://appandro.id/go?to=", (CharSequence)"");
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse((String)string2));
                this.this$0.startActivity(intent);
                return true;
            }

            @android.annotation.TargetApi(value=24)
            public boolean shouldOverrideUrlLoading(WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
                return this.setAction(webResourceRequest.getUrl());
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                return this.setAction(Uri.parse((String)string));
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

