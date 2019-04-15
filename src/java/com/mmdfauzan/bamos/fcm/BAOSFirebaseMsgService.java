/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.NotificationChannel
 *  android.app.NotificationManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.support.v4.app.NotificationCompat
 *  android.support.v4.app.NotificationCompat$Builder
 *  android.support.v4.content.LocalBroadcastManager
 *  com.google.firebase.messaging.FirebaseMessagingService
 *  com.google.firebase.messaging.RemoteMessage
 *  com.mmdfauzan.bamos.ChatDetailActivity
 *  com.mmdfauzan.bamos.MainActivity
 *  com.mmdfauzan.bamos.MemberActivity
 *  com.mmdfauzan.bamos.PemberitahuanActivity
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 */
package com.mmdfauzan.bamos.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mmdfauzan.bamos.ChatDetailActivity;
import com.mmdfauzan.bamos.MainActivity;
import com.mmdfauzan.bamos.MemberActivity;
import com.mmdfauzan.bamos.PemberitahuanActivity;
import com.mmdfauzan.bamos.app.DataPref;
import java.util.Map;

public class BAOSFirebaseMsgService
extends FirebaseMessagingService {
    private static final String TAG = "BAOSFCMService";
    String chatId;
    String chatIdBarang;
    String chatMessage;
    String chatNama;
    String chatNamaBarang;
    String chatSender;
    String chatTimeSent;
    DataPref dataPref;
    int idNotification;

    /*
     * Enabled aggressive block sorting
     */
    private void createNotification(String string2, String string3, String string4, String string5) {
        Intent intent;
        if (string2.equals((Object)"1")) {
            intent = new Intent((Context)this, MainActivity.class);
            intent.putExtra("idpesanan", string3);
        } else if (string2.equals((Object)"2")) {
            intent = new Intent((Context)this, MemberActivity.class);
        } else if (string2.equals((Object)"3")) {
            intent = new Intent((Context)this, PemberitahuanActivity.class);
        } else if (string2.equals((Object)"4")) {
            intent = new Intent((Context)this, ChatDetailActivity.class);
            intent.putExtra("idchat", this.chatId);
            intent.putExtra("nama", this.chatNama);
        } else {
            intent = new Intent((Context)this, MainActivity.class);
        }
        intent.addFlags(67108864);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this, (int)0, (Intent)intent, (int)1073741824);
        NotificationManager notificationManager = (NotificationManager)this.getSystemService("notification");
        Uri uri = RingtoneManager.getDefaultUri((int)2);
        if (Build.VERSION.SDK_INT >= 26 && notificationManager.getNotificationChannel("Bikin Aplikasi Online Shop") == null) {
            NotificationChannel notificationChannel = new NotificationChannel("Bikin Aplikasi Online Shop", (CharSequence)"Online Shop Notification", 4);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(-65281);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder((Context)this, "Bikin Aplikasi Online Shop").setSmallIcon(2131230890).setContentTitle((CharSequence)string4).setContentText((CharSequence)string5).setPriority(1).setAutoCancel(true).setSound(uri).setVibrate(new long[]{500L, 500L}).setDefaults(-1).setLights(-65281, 3000, 3000).setContentIntent(pendingIntent);
        notificationManager.notify(this.idNotification, builder.build());
        this.dataPref.setIdNotification(1 + this.idNotification);
    }

    public void onMessageReceived(RemoteMessage remoteMessage) {
        String string2;
        String string3;
        String string4;
        String string5;
        block3 : {
            block2 : {
                string3 = (String)remoteMessage.getData().get((Object)"type");
                string5 = (String)remoteMessage.getData().get((Object)"title");
                string2 = (String)remoteMessage.getData().get((Object)"body");
                string4 = (String)remoteMessage.getData().get((Object)"id");
                this.dataPref = new DataPref(this.getApplicationContext());
                this.idNotification = this.dataPref.getIdNotification();
                if (!this.dataPref.isLoggedIn()) break block2;
                if (!string3.equals((Object)"4")) break block3;
                this.chatId = (String)remoteMessage.getData().get((Object)"idchat");
                this.chatMessage = (String)remoteMessage.getData().get((Object)"message");
                this.chatTimeSent = (String)remoteMessage.getData().get((Object)"time_sent");
                this.chatSender = (String)remoteMessage.getData().get((Object)"sender");
                this.chatNama = (String)remoteMessage.getData().get((Object)"nama");
                this.chatIdBarang = (String)remoteMessage.getData().get((Object)"idbarang");
                this.chatNamaBarang = (String)remoteMessage.getData().get((Object)"namabarang");
                BAOSFirebaseMsgService.super.createNotification(string3, string4, string5, string2);
                Intent intent = new Intent("chat");
                intent.putExtra("idnotification", this.idNotification);
                intent.putExtra("idchat", this.chatId);
                intent.putExtra("message", this.chatMessage);
                intent.putExtra("sender", this.chatSender);
                intent.putExtra("time_sent", this.chatTimeSent);
                intent.putExtra("nama", this.chatNama);
                intent.putExtra("idbarang", this.chatIdBarang);
                intent.putExtra("namabarang", this.chatNamaBarang);
                LocalBroadcastManager.getInstance((Context)this).sendBroadcast(intent);
            }
            return;
        }
        BAOSFirebaseMsgService.super.createNotification(string3, string4, string5, string2);
    }
}

