/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.app;

import android.content.Context;
import android.content.SharedPreferences;

public class DataPref {
    private static final String PREF_NAME = "bmsdmnprf";
    int PRIVATE_MODE = 0;
    Context _context;
    String color = "color";
    SharedPreferences.Editor editor;
    String email = "email";
    String fcmToken = "fcmtoken";
    String idNotification = "idNotification";
    String loggedIn = "loggedIn";
    String namatoko = "namatoko";
    SharedPreferences pref;
    String saldo = "saldo";
    String started = "started";
    String token = "token";
    String username = "username";
    String website = "website";

    public DataPref(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, this.PRIVATE_MODE);
        this.editor = this.pref.edit();
    }

    public String getColor() {
        return this.pref.getString(this.color, "ff8888");
    }

    public String getEmail() {
        return this.pref.getString(this.email, null);
    }

    public String getFcmToken() {
        return this.pref.getString(this.fcmToken, null);
    }

    public int getIdNotification() {
        return this.pref.getInt(this.idNotification, 0);
    }

    public String getNamatoko() {
        return this.pref.getString(this.namatoko, null);
    }

    public String getSaldo() {
        return this.pref.getString(this.saldo, "0");
    }

    public String getToken() {
        return this.pref.getString(this.token, null);
    }

    public String getUsername() {
        return this.pref.getString(this.username, null);
    }

    public String getWebsite() {
        String string2 = this.pref.getString(this.website, "domainbikinaplikasionlineshop.com");
        if (string2.equals((Object)"") || string2.toLowerCase().equals((Object)"null") || string2.equals(null)) {
            string2 = "domainbikinaplikasionlineshop.com";
        }
        return string2;
    }

    public boolean isLoggedIn() {
        return this.pref.getBoolean(this.loggedIn, false);
    }

    public boolean isStarted() {
        return this.pref.getBoolean(this.started, false);
    }

    public void setColor(String string2) {
        this.editor.putString(this.color, string2);
        this.editor.commit();
    }

    public void setEmail(String string2) {
        this.editor.putString(this.email, string2);
        this.editor.commit();
    }

    public void setFcmToken(String string2) {
        this.editor.putString(this.fcmToken, string2);
        this.editor.commit();
    }

    public void setIdNotification(int n) {
        this.editor.putInt(this.idNotification, n);
        this.editor.commit();
    }

    public void setLoggedIn(boolean bl) {
        this.editor.putBoolean(this.loggedIn, bl);
        this.editor.commit();
    }

    public void setLogin(String string2, String string3, String string4, boolean bl) {
        this.editor.putString(this.email, string2);
        this.editor.putString(this.username, string3);
        this.editor.putString(this.token, string4);
        this.editor.putBoolean(this.loggedIn, bl);
        this.editor.commit();
    }

    public void setNamatoko(String string2) {
        this.editor.putString(this.namatoko, string2);
        this.editor.commit();
    }

    public void setSaldo(String string2) {
        this.editor.putString(this.saldo, string2);
        this.editor.commit();
    }

    public void setStarted(boolean bl) {
        this.editor.putBoolean(this.started, bl);
        this.editor.commit();
    }

    public void setToken(String string2) {
        this.editor.putString(this.token, string2);
        this.editor.commit();
    }

    public void setUsername(String string2) {
        this.editor.putString(this.username, string2);
        this.editor.commit();
    }

    public void setWebsite(String string2) {
        this.editor.putString(this.website, string2);
        this.editor.commit();
    }
}

