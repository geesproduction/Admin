/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v4.app.Fragment
 *  android.support.v4.app.FragmentManager
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageButton
 *  com.google.android.gms.maps.OnMapReadyCallback
 *  com.google.android.gms.maps.SupportMapFragment
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.mmdfauzan.bamos.LokasiActivity;
import com.mmdfauzan.bamos.helper.ShowToast;

public class LokasiActivity
extends AppCompatActivity {
    ShowToast showToast;
    SupportMapFragment supportMapFragment;

    private void setMap(String string, String string2) {
        this.supportMapFragment.getMapAsync(new OnMapReadyCallback((LokasiActivity)this, string, string2){
            final /* synthetic */ LokasiActivity this$0;
            final /* synthetic */ String val$lat;
            final /* synthetic */ String val$lon;
            {
                this.this$0 = lokasiActivity;
                this.val$lat = string;
                this.val$lon = string2;
            }

            public void onMapReady(com.google.android.gms.maps.GoogleMap googleMap) {
                double d = java.lang.Double.valueOf((String)this.val$lat);
                double d2 = java.lang.Double.valueOf((String)this.val$lon);
                if (android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_FINE_LOCATION") != 0 && android.support.v4.app.ActivityCompat.checkSelfPermission((Context)this.this$0, (String)"android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    this.this$0.showToast.Toast("Aplikasi tidak diberikan izin mengakses lokasi. Lakukan pengaturan untuk memberikan izin akses lokasi.");
                    return;
                }
                com.google.android.gms.maps.model.LatLng latLng = new com.google.android.gms.maps.model.LatLng(d, d2);
                com.google.android.gms.maps.model.BitmapDescriptor bitmapDescriptor = com.google.android.gms.maps.model.BitmapDescriptorFactory.fromResource((int)2131230900);
                com.google.android.gms.maps.model.MarkerOptions markerOptions = new com.google.android.gms.maps.model.MarkerOptions().position(latLng).title("Lokasi Penerima").icon(bitmapDescriptor);
                googleMap.clear();
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newCameraPosition((com.google.android.gms.maps.model.CameraPosition)new com.google.android.gms.maps.model.CameraPosition$Builder().target(latLng).zoom(12.0f).build()));
                googleMap.addMarker(markerOptions);
                googleMap.setMyLocationEnabled(true);
            }
        });
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427381);
        this.showToast = new ShowToast((Context)this);
        this.supportMapFragment = (SupportMapFragment)this.getSupportFragmentManager().findFragmentById(2131296720);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setTitle((CharSequence)"Lokasi");
        Intent intent = this.getIntent();
        String string = intent.getStringExtra("latitude");
        String string2 = intent.getStringExtra("longitude");
        String string3 = intent.getStringExtra("link");
        ((ImageButton)this.findViewById(2131296610)).setOnClickListener(new View.OnClickListener((LokasiActivity)this, string3){
            final /* synthetic */ LokasiActivity this$0;
            final /* synthetic */ String val$link;
            {
                this.this$0 = lokasiActivity;
                this.val$link = string;
            }

            public void onClick(View view) {
                this.this$0.startActivity(new Intent("android.intent.action.VIEW", android.net.Uri.parse((String)this.val$link)));
            }
        });
        LokasiActivity.super.setMap(string, string2);
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

