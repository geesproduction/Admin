/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.LinearLayout
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.mmdfauzan.bamos.SuccessActivity;
import com.mmdfauzan.bamos.app.DataPref;

public class SuccessActivity
extends AppCompatActivity {
    Button buttonEdit;
    Button buttonInstall;
    Button buttonKelola;
    Button buttonLink;
    DataPref dataPref;
    boolean editAplikasi = false;
    LinearLayout layoutKelola;

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427401);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Berhasil");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.buttonInstall = (Button)this.findViewById(2131296360);
        this.buttonEdit = (Button)this.findViewById(2131296344);
        this.buttonLink = (Button)this.findViewById(2131296364);
        this.buttonKelola = (Button)this.findViewById(2131296362);
        this.layoutKelola = (LinearLayout)this.findViewById(2131296665);
        this.editAplikasi = this.getIntent().getBooleanExtra("edit_aplikasi", false);
        if (this.editAplikasi) {
            this.layoutKelola.setVisibility(8);
        } else {
            this.layoutKelola.setVisibility(0);
        }
        this.buttonInstall.setOnClickListener(new View.OnClickListener((SuccessActivity)this){
            final /* synthetic */ SuccessActivity this$0;
            {
                this.this$0 = successActivity;
            }

            public void onClick(View view) {
                android.widget.Toast.makeText((Context)this.this$0, (CharSequence)"Setelah selesai instalasi tekan KEMBALI untuk melengkapi langkah selanjutnya.", (int)1).show();
                java.io.File file = new java.io.File((java.lang.Object)android.os.Environment.getExternalStorageDirectory() + "/BIKINAPLIKASI/" + this.this$0.dataPref.getUsername() + ".apk");
                android.net.Uri uri = android.support.v4.content.FileProvider.getUriForFile((Context)this.this$0, (String)(this.this$0.getApplicationContext().getPackageName() + ".provider"), (java.io.File)file);
                if (android.os.Build$VERSION.SDK_INT >= 24) {
                    Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
                    intent.setData(uri);
                    intent.setFlags(1);
                    this.this$0.startActivity(intent);
                    return;
                }
                android.net.Uri uri2 = android.net.Uri.fromFile((java.io.File)file);
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setDataAndType(uri2, "application/vnd.android.package-archive");
                intent.setFlags(268435456);
                this.this$0.startActivity(intent);
            }
        });
        this.buttonEdit.setOnClickListener(new View.OnClickListener((SuccessActivity)this){
            final /* synthetic */ SuccessActivity this$0;
            {
                this.this$0 = successActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.BikinFileActivity.class);
                if (this.this$0.editAplikasi) {
                    intent.putExtra("edit_aplikasi", true);
                }
                this.this$0.startActivity(intent);
                this.this$0.finish();
            }
        });
        this.buttonKelola.setOnClickListener(new View.OnClickListener((SuccessActivity)this){
            final /* synthetic */ SuccessActivity this$0;
            {
                this.this$0 = successActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.MainActivity.class);
                this.this$0.startActivity(intent);
                this.this$0.finish();
            }
        });
        this.buttonLink.setOnClickListener(new View.OnClickListener((SuccessActivity)this){
            final /* synthetic */ SuccessActivity this$0;
            {
                this.this$0 = successActivity;
            }

            public void onClick(View view) {
                if (this.this$0.editAplikasi) {
                    android.widget.Toast.makeText((Context)this.this$0, (CharSequence)"Pilih tombol PERBARUI APK", (int)1).show();
                }
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.LinkActivity.class);
                this.this$0.startActivity(intent);
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

