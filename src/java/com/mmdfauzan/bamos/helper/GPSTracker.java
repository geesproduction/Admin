/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.app.Service
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.location.Location
 *  android.location.LocationListener
 *  android.location.LocationManager
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.support.v4.app.ActivityCompat
 *  android.util.Log
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

public class GPSTracker
extends Service
implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10L;
    private static final long MIN_TIME_BW_UPDATES = 60000L;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    private final Context mContext;

    public GPSTracker(Context context) {
        this.mContext = context;
        this.getLocation();
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public double getLatitude() {
        if (this.location != null) {
            this.latitude = this.location.getLatitude();
        }
        return this.latitude;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Location getLocation() {
        try {
            boolean bl;
            this.locationManager = (LocationManager)this.mContext.getSystemService("location");
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (!this.isGPSEnabled && !(bl = this.isNetworkEnabled)) {
                return this.location;
            }
            this.canGetLocation = true;
            if (this.isNetworkEnabled) {
                this.locationManager.requestLocationUpdates("network", 60000L, 10.0f, (LocationListener)this);
                if (this.locationManager != null) {
                    this.location = this.locationManager.getLastKnownLocation("network");
                    if (this.location != null) {
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
                if (ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.ACCESS_COARSE_LOCATION") != 0) {
                    return this.location;
                }
            }
            if (!this.isGPSEnabled) return this.location;
            if (this.location != null) return this.location;
            this.locationManager.requestLocationUpdates("gps", 60000L, 10.0f, (LocationListener)this);
            Log.d((String)"GPS Enabled", (String)"GPS Enabled");
            if (this.locationManager == null) return this.location;
            this.location = this.locationManager.getLastKnownLocation("gps");
            if (this.location == null) return this.location;
            this.latitude = this.location.getLatitude();
            this.longitude = this.location.getLongitude();
            return this.location;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return this.location;
        }
    }

    public double getLongitude() {
        if (this.location != null) {
            this.longitude = this.location.getLongitude();
        }
        return this.longitude;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location) {
    }

    public void onProviderDisabled(String string2) {
    }

    public void onProviderEnabled(String string2) {
    }

    public void onStatusChanged(String string2, int n, Bundle bundle) {
    }

    public void showSettingsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle((CharSequence)"GPS is settings");
        builder.setMessage((CharSequence)"GPS is not enabled. Do you want to go to settings menu?");
        builder.setPositiveButton((CharSequence)"Settings", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                GPSTracker.this.mContext.startActivity(intent);
            }
        });
        builder.setNegativeButton((CharSequence)"Cancel", new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    public void stopUsingGPS() {
        if (this.locationManager != null && ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.ACCESS_FINE_LOCATION") != 0 && ActivityCompat.checkSelfPermission((Context)this, (String)"android.permission.ACCESS_COARSE_LOCATION") != 0) {
            this.locationManager.removeUpdates((LocationListener)this);
        }
    }

}

