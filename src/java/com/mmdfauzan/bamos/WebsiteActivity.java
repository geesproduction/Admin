/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.webkit.DownloadListener
 *  android.webkit.WebChromeClient
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.mmdfauzan.bamos.WebsiteActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.ShowToast;

public class WebsiteActivity
extends AppCompatActivity {
    Button buttonAktif;
    DataPref dataPref;
    LinearLayout layoutLoading;
    LinearLayout layoutWebsite;
    ShowToast showToast;
    TextView textViewInfo;
    TextView textViewUrl;
    WebView webViewWebsite;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427409);
        this.getSupportActionBar().setTitle((CharSequence)"Website");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.webViewWebsite = (WebView)this.findViewById(2131297174);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.showToast = new ShowToast((Context)this);
        this.webViewWebsite.getSettings().setJavaScriptEnabled(true);
        this.webViewWebsite.getSettings().setGeolocationEnabled(true);
        this.webViewWebsite.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webViewWebsite.setWebChromeClient(new WebChromeClient((WebsiteActivity)this){
            final /* synthetic */ WebsiteActivity this$0;
            {
                this.this$0 = websiteActivity;
            }

            public boolean onJsConfirm(WebView webView, String string, String string2, android.webkit.JsResult jsResult) {
                new android.support.v7.app.AlertDialog$Builder(webView.getContext()).setTitle((CharSequence)webView.getTitle()).setMessage((CharSequence)string2).setCancelable(false).setPositiveButton(17039370, new android.content.DialogInterface$OnClickListener(this, jsResult){
                    final /* synthetic */ 1 this$1;
                    final /* synthetic */ android.webkit.JsResult val$result;
                    {
                        this.this$1 = var1;
                        this.val$result = jsResult;
                    }

                    public void onClick(android.content.DialogInterface dialogInterface, int n) {
                        this.val$result.confirm();
                    }
                }).setNegativeButton(17039360, new android.content.DialogInterface$OnClickListener(this, jsResult){
                    final /* synthetic */ 1 this$1;
                    final /* synthetic */ android.webkit.JsResult val$result;
                    {
                        this.this$1 = var1;
                        this.val$result = jsResult;
                    }

                    public void onClick(android.content.DialogInterface dialogInterface, int n) {
                        this.val$result.cancel();
                    }
                }).show();
                return true;
            }
        });
        this.webViewWebsite.clearCache(true);
        this.webViewWebsite.loadUrl("http://os.bikinaplikasi.com/website?i=" + this.dataPref.getUsername() + "&e=" + this.dataPref.getEmail() + "&t=" + this.dataPref.getToken());
        this.webViewWebsite.setHorizontalScrollBarEnabled(false);
        this.webViewWebsite.setVerticalScrollBarEnabled(false);
        this.webViewWebsite.setDownloadListener(new DownloadListener((WebsiteActivity)this){
            final /* synthetic */ WebsiteActivity this$0;
            {
                this.this$0 = websiteActivity;
            }

            public void onDownloadStart(String string, String string2, String string3, String string4, long l) {
                android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                intent.setData(android.net.Uri.parse((String)string));
                this.this$0.startActivity(intent);
            }
        });
        this.webViewWebsite.setWebViewClient(new WebViewClient((WebsiteActivity)this){
            final /* synthetic */ WebsiteActivity this$0;
            {
                this.this$0 = websiteActivity;
            }

            public void onPageFinished(WebView webView, String string) {
                this.this$0.layoutLoading.setVisibility(8);
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
                this.this$0.layoutLoading.setVisibility(0);
            }

            public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
                this.this$0.showToast.ToastError();
                this.this$0.webViewWebsite.setVisibility(8);
                this.this$0.layoutLoading.setVisibility(8);
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                boolean bl = string.startsWith("http");
                boolean bl2 = false;
                if (!bl) {
                    boolean bl3 = string.startsWith("https");
                    bl2 = false;
                    if (!bl3) {
                        bl2 = true;
                        android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                        intent.setData(android.net.Uri.parse((String)string));
                        this.this$0.startActivity(intent);
                    }
                }
                return bl2;
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

