/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
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
 *  android.widget.ProgressBar
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.app.AlertDialog;
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
import android.widget.ProgressBar;
import com.mmdfauzan.bamos.PersyaratanActivity;

public class PersyaratanActivity
extends AppCompatActivity {
    AlertDialog myAlertDialog;
    ProgressBar progressBarLoading;
    String url = "http://os.bikinaplikasi.com/persyaratan";
    WebView webView;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427391);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Aturan Penggunaan dan Kebijakan Privasi");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.webView = (WebView)this.findViewById(2131297168);
        this.progressBarLoading = (ProgressBar)this.findViewById(2131296759);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webView.setWebChromeClient(new WebChromeClient((PersyaratanActivity)this){
            final /* synthetic */ PersyaratanActivity this$0;
            {
                this.this$0 = persyaratanActivity;
            }

            public boolean onJsConfirm(WebView webView, String string, String string2, android.webkit.JsResult jsResult) {
                new android.app.AlertDialog$Builder(webView.getContext()).setMessage((CharSequence)string2).setCancelable(false).setPositiveButton(17039370, new android.content.DialogInterface$OnClickListener(this, jsResult){
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
        this.webView.loadUrl(this.url);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setDownloadListener(new DownloadListener((PersyaratanActivity)this){
            final /* synthetic */ PersyaratanActivity this$0;
            {
                this.this$0 = persyaratanActivity;
            }

            public void onDownloadStart(String string, String string2, String string3, String string4, long l) {
                android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                intent.setData(android.net.Uri.parse((String)string));
                this.this$0.startActivity(intent);
            }
        });
        this.webView.setWebViewClient(new WebViewClient((PersyaratanActivity)this){
            final /* synthetic */ PersyaratanActivity this$0;
            {
                this.this$0 = persyaratanActivity;
            }

            public void onPageFinished(WebView webView, String string) {
                this.this$0.progressBarLoading.setVisibility(8);
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
                this.this$0.progressBarLoading.setVisibility(0);
            }

            public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
                super.onReceivedError(webView, webResourceRequest, webResourceError);
                this.this$0.webView.setVisibility(8);
                this.this$0.myAlertDialog = new android.app.AlertDialog$Builder((android.content.Context)this.this$0).create();
                this.this$0.myAlertDialog.setTitle((CharSequence)"Terjadi Kesalahan");
                this.this$0.myAlertDialog.setMessage((CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.");
                this.this$0.myAlertDialog.setButton(-1, (CharSequence)"OK", new android.content.DialogInterface$OnClickListener(this){
                    final /* synthetic */ 3 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onClick(android.content.DialogInterface dialogInterface, int n) {
                        this.this$1.this$0.myAlertDialog.dismiss();
                    }
                });
                this.this$0.myAlertDialog.show();
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                boolean bl = string.startsWith("http");
                boolean bl2 = false;
                if (!bl) {
                    bl2 = true;
                    android.content.Intent intent = new android.content.Intent("android.intent.action.VIEW");
                    intent.setData(android.net.Uri.parse((String)string));
                    this.this$0.startActivity(intent);
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

