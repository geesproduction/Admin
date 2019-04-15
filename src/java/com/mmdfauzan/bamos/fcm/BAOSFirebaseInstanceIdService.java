/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  com.google.firebase.iid.FirebaseInstanceId
 *  com.google.firebase.iid.FirebaseInstanceIdService
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.fcm;

import android.content.Context;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mmdfauzan.bamos.app.DataPref;

public class BAOSFirebaseInstanceIdService
extends FirebaseInstanceIdService {
    private static final String TAG = "BAOSFCMIIDService";
    DataPref dataPref;
    String refreshedToken;

    private void sendRegistrationToServer(String string2) {
    }

    public void onTokenRefresh() {
        this.refreshedToken = FirebaseInstanceId.getInstance().getToken();
        this.dataPref = new DataPref((Context)this);
        this.dataPref.setFcmToken(this.refreshedToken);
        if (this.dataPref.isLoggedIn() && !this.dataPref.getEmail().equals((Object)"") && !this.dataPref.getToken().equals((Object)"")) {
            this.sendRegistrationToServer(this.refreshedToken);
        }
    }
}

