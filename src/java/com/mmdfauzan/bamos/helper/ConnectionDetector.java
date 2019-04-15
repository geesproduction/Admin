/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.NetworkInfo
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
    private Context _context;

    public ConnectionDetector(Context context) {
        this._context = context;
    }

    public boolean isConnectingToInternet() {
        boolean bl;
        block2 : {
            block3 : {
                NetworkInfo networkInfo = ((ConnectivityManager)this._context.getSystemService("connectivity")).getActiveNetworkInfo();
                bl = false;
                if (networkInfo == null) break block2;
                if (networkInfo.getType() == 1) break block3;
                int n = networkInfo.getType();
                bl = false;
                if (n != 0) break block2;
            }
            bl = true;
        }
        return bl;
    }
}

