/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.View
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.ProgressBar
 *  com.google.android.gms.ads.AdListener
 *  com.google.android.gms.ads.AdRequest
 *  com.google.android.gms.ads.AdRequest$Builder
 *  com.google.android.gms.ads.AdView
 *  com.google.android.gms.ads.InterstitialAd
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.mmdfauzan.bamos.DaftarActivity;

public class DaftarActivity
extends AppCompatActivity {
    AdView adView;
    InterstitialAd interstitial;
    AlertDialog myAlertDialog;
    ProgressBar progressBar;
    WebView webView;

    public void displayInterstitial() {
        if (this.interstitial.isLoaded()) {
            this.interstitial.show();
        }
    }

    public void onBackPressed() {
        this.myAlertDialog = new AlertDialog.Builder((Context)this).create();
        this.myAlertDialog.setTitle((CharSequence)"Batalkan Pendaftaran");
        this.myAlertDialog.setMessage((CharSequence)"Anda yakin ingin membatalkan pendaftaran?");
        this.myAlertDialog.setButton(-2, (CharSequence)"Ya", new DialogInterface.OnClickListener(this){
            final /* synthetic */ DaftarActivity this$0;
            {
                this.this$0 = daftarActivity;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                this.this$0.finish();
            }
        });
        this.myAlertDialog.setButton(-1, (CharSequence)"Tidak", new DialogInterface.OnClickListener(this){
            final /* synthetic */ DaftarActivity this$0;
            {
                this.this$0 = daftarActivity;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                this.this$0.myAlertDialog.dismiss();
            }
        });
        this.myAlertDialog.show();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427365);
        this.getSupportActionBar().setTitle((CharSequence)"Pendaftaran Toko");
        this.progressBar = (ProgressBar)this.findViewById(2131296746);
        this.interstitial = new InterstitialAd((Context)this);
        this.interstitial.setAdUnitId("ca-app-pub-6383827200192790/8433611462");
        AdRequest adRequest = new AdRequest.Builder().build();
        this.interstitial.loadAd(adRequest);
        this.adView = (AdView)this.findViewById(2131296299);
        this.adView.loadAd(new AdRequest.Builder().build());
        this.adView.setAdListener(new AdListener((DaftarActivity)this){
            final /* synthetic */ DaftarActivity this$0;
            {
                this.this$0 = daftarActivity;
            }

            public void onAdFailedToLoad(int n) {
                this.this$0.adView.setVisibility(8);
            }

            public void onAdLoaded() {
                this.this$0.adView.setVisibility(0);
            }
        });
        this.webView = (WebView)this.findViewById(2131297169);
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl("http://os.bikinaplikasi.com/daftar/mulai");
        this.webView.setWebViewClient(new WebViewClient((DaftarActivity)this){
            final /* synthetic */ DaftarActivity this$0;
            {
                this.this$0 = daftarActivity;
            }

            public void onPageFinished(WebView webView, String string) {
                try {
                    this.this$0.progressBar.setVisibility(8);
                    return;
                }
                catch (java.lang.Exception exception) {
                    exception.printStackTrace();
                    return;
                }
            }

            public void onPageStarted(WebView webView, String string, android.graphics.Bitmap bitmap) {
                this.this$0.displayInterstitial();
                this.this$0.progressBar.setVisibility(0);
            }

            public void onReceivedError(WebView webView, int n, String string, String string2) {
                this.this$0.progressBar.setVisibility(8);
                this.this$0.webView.setVisibility(4);
                this.this$0.myAlertDialog = new AlertDialog.Builder((Context)this.this$0).create();
                this.this$0.myAlertDialog.setTitle((CharSequence)"Error");
                this.this$0.myAlertDialog.setMessage((CharSequence)"Tidak dapat terhubung dengan jaringan. Coba lagi beberapa saat.");
                this.this$0.myAlertDialog.setButton((CharSequence)"OK", new DialogInterface.OnClickListener(this){
                    final /* synthetic */ 2 this$1;
                    {
                        this.this$1 = var1;
                    }

                    public void onClick(DialogInterface dialogInterface, int n) {
                        this.this$1.this$0.myAlertDialog.dismiss();
                    }
                });
                this.this$0.myAlertDialog.show();
            }

            public boolean shouldOverrideUrlLoading(WebView webView, String string) {
                if (string.startsWith("http://os.bikinaplikasi.com/daftar/bikin?u=")) {
                    this.this$0.progressBar.setVisibility(8);
                    String string2 = string.replace((CharSequence)"http://os.bikinaplikasi.com/daftar/bikin?u=", (CharSequence)"");
                    android.content.Intent intent = new android.content.Intent((Context)this.this$0, com.mmdfauzan.bamos.BikinFileActivity.class);
                    intent.putExtra("un", string2);
                    this.this$0.startActivity(intent);
                    this.this$0.finish();
                }
                return false;
            }
        });
    }
}

