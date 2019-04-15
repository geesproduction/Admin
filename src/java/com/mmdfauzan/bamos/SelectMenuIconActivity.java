/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.webkit.DownloadListener
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.mmdfauzan.bamos.SelectMenuIconActivity;

public class SelectMenuIconActivity
extends AppCompatActivity {
    WebView webViewWebsite;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427398);
        this.getSupportActionBar().setTitle((CharSequence)"Menu");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.webViewWebsite = (WebView)this.findViewById(2131297174);
        this.webViewWebsite.clearCache(true);
        if (this.getIntent().getIntExtra("edit", 1) == 0) {
            this.webViewWebsite.loadUrl("http://os.bikinaplikasi.com/menu#cara");
        } else {
            this.webViewWebsite.loadUrl("http://os.bikinaplikasi.com/menu");
        }
        this.webViewWebsite.getSettings().setJavaScriptEnabled(true);
        this.webViewWebsite.getSettings().setGeolocationEnabled(true);
        this.webViewWebsite.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.webViewWebsite.setHorizontalScrollBarEnabled(false);
        this.webViewWebsite.setVerticalScrollBarEnabled(false);
        this.webViewWebsite.setDownloadListener(new DownloadListener((SelectMenuIconActivity)this){
            final /* synthetic */ SelectMenuIconActivity this$0;
            {
                this.this$0 = selectMenuIconActivity;
            }

            public void onDownloadStart(String string, String string2, String string3, String string4, long l) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(android.net.Uri.parse((String)string));
                this.this$0.startActivity(intent);
            }
        });
        this.webViewWebsite.getSettings().setBuiltInZoomControls(true);
        this.webViewWebsite.getSettings().setDisplayZoomControls(false);
        this.webViewWebsite.setWebViewClient(new WebViewClient((SelectMenuIconActivity)this){
            final /* synthetic */ SelectMenuIconActivity this$0;
            {
                this.this$0 = selectMenuIconActivity;
            }

            public void onPageFinished(WebView webView, String string) {
                this.this$0.getSupportActionBar().setTitle((CharSequence)webView.getTitle());
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
                this.this$0.getSupportActionBar().setTitle((CharSequence)"Loading...");
            }

            public void onReceivedError(WebView webView, android.webkit.WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
                this.this$0.webViewWebsite.setVisibility(8);
                android.widget.Toast.makeText((android.content.Context)this.this$0, (CharSequence)"Tidak dapat terhubung ke server.", (int)1).show();
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean setAction(android.net.Uri uri) {
                boolean bl;
                String string = uri.toString();
                if (!string.startsWith("http://") && !string.startsWith("https://")) {
                    bl = true;
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(android.net.Uri.parse((String)string));
                    this.this$0.startActivity(intent);
                    return bl;
                }
                if (!(string.startsWith("http://os.bikinaplikasi.com/menu/icon") || string.startsWith("https://os.bikinaplikasi.com/menu/icon") || string.startsWith("http://osfile.uzanmedia.id/menu/icon"))) {
                    boolean bl2 = string.startsWith("https://osfile.uzanmedia.id/menu/icon");
                    bl = false;
                    if (!bl2) return bl;
                }
                Intent intent = new Intent();
                intent.putExtra("icon", string);
                this.this$0.setResult(-1, intent);
                this.this$0.finish();
                return true;
            }

            @android.annotation.TargetApi(value=24)
            public boolean shouldOverrideUrlLoading(WebView webView, android.webkit.WebResourceRequest webResourceRequest) {
                return this.setAction(webResourceRequest.getUrl());
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                return this.setAction(android.net.Uri.parse((String)string));
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

