/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.io.BufferedInputStream
 *  java.io.BufferedReader
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.Reader
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.net.HttpURLConnection
 *  java.net.URL
 *  java.net.URLConnection
 *  java.net.URLEncoder
 *  java.util.HashMap
 *  java.util.Set
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos.helper;

import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
    String charset = "UTF-8";
    HttpURLConnection conn;
    JSONObject jObj = null;
    String paramsString;
    StringBuilder result;
    StringBuilder sbParams;
    URL urlObj;
    DataOutputStream wr;

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public JSONObject makeHttpRequest(String var1, String var2_3, HashMap<String, String> var3_2) {
        this.sbParams = new StringBuilder();
        var4_4 = 0;
        for (String var14_6 : var3_2.keySet()) {
            if (var4_4 == 0) ** GOTO lbl7
            try {
                this.sbParams.append("&");
lbl7: // 2 sources:
                this.sbParams.append(var14_6).append("=").append(URLEncoder.encode((String)((String)var3_2.get((Object)var14_6)), (String)this.charset));
            }
            catch (UnsupportedEncodingException var15_7) {
                var15_7.printStackTrace();
            }
            ++var4_4;
        }
        if (var2_3.equals((Object)"POST")) {
            try {
                this.urlObj = new URL(var1);
                this.conn = (HttpURLConnection)this.urlObj.openConnection();
                this.conn.setDoOutput(true);
                this.conn.setRequestMethod("POST");
                this.conn.setRequestProperty("Accept-Charset", this.charset);
                this.conn.setReadTimeout(100000);
                this.conn.setConnectTimeout(120000);
                this.conn.connect();
                this.paramsString = this.sbParams.toString();
                this.wr = new DataOutputStream(this.conn.getOutputStream());
                this.wr.writeBytes(this.paramsString);
                this.wr.flush();
                this.wr.close();
            }
            catch (IOException var13_8) {
                var13_8.printStackTrace();
            }
        } else if (var2_3.equals((Object)"GET")) {
            if (this.sbParams.length() != 0) {
                var1 = var1 + "?" + this.sbParams.toString();
            }
            try {
                this.urlObj = new URL(var1);
                this.conn = (HttpURLConnection)this.urlObj.openConnection();
                this.conn.setDoOutput(false);
                this.conn.setRequestMethod("GET");
                this.conn.setRequestProperty("Accept-Charset", this.charset);
                this.conn.setConnectTimeout(120000);
                this.conn.connect();
            }
            catch (IOException var6_9) {
                var6_9.printStackTrace();
            }
        }
        try {
            var7_10 = new BufferedReader((Reader)new InputStreamReader((InputStream)new BufferedInputStream(this.conn.getInputStream())));
            this.result = new StringBuilder();
            while ((var11_11 = var7_10.readLine()) != null) {
                this.result.append(var11_11);
            }
        }
        catch (IOException var8_12) {
            var8_12.printStackTrace();
        }
        this.conn.disconnect();
        if (this.result == null) {
            this.jObj = null;
            return this.jObj;
        }
        try {
            this.jObj = new JSONObject(this.result.toString());
            return this.jObj;
        }
        catch (JSONException var9_13) {
            Log.e((String)"JSON Parser", (String)("Error parsing data " + var9_13.toString()));
            return this.jObj;
        }
    }
}

