/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.io.DataOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 *  java.net.HttpURLConnection
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.net.URLConnection
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos.helper;

import android.util.Log;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class Uploader {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public JSONObject uploadFile(String var1, String var2_3, String var3_2) {
        var4_4 = null;
        var5_5 = new File(var1);
        if (!var5_5.isFile()) {
            return null;
        }
        var6_6 = new FileInputStream(var5_5);
        var7_7 = new URL(var2_3);
        var15_8 = (HttpURLConnection)var7_7.openConnection();
        var15_8.setDoInput(true);
        var15_8.setDoOutput(true);
        var15_8.setUseCaches(false);
        var15_8.setRequestMethod("POST");
        var15_8.setRequestProperty("Connection", "Keep-Alive");
        var15_8.setRequestProperty("ENCTYPE", "multipart/form-data");
        var15_8.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");
        var15_8.setRequestProperty("uploaded_file", var3_2);
        var16_9 = new DataOutputStream(var15_8.getOutputStream());
        try {
            var16_9.writeBytes("--" + "*****" + "\r\n");
            var16_9.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + var3_2 + "\"" + "\r\n");
            var16_9.writeBytes("\r\n");
            var17_10 = Math.min((int)var6_6.available(), (int)10485760);
            var18_11 = new byte[var17_10];
            var19_12 = var6_6.read(var18_11, 0, var17_10);
            while (var19_12 > 0) {
                var16_9.write(var18_11, 0, var17_10);
                var17_10 = Math.min((int)var6_6.available(), (int)10485760);
                var19_12 = var6_6.read(var18_11, 0, var17_10);
            }
            var16_9.writeBytes("\r\n");
            var16_9.writeBytes("--" + "*****" + "--" + "\r\n");
            var20_13 = var15_8.getResponseCode();
            var21_14 = var15_8.getResponseMessage();
            Log.i((String)"uploadFile", (String)("HTTP Response is : " + var21_14 + ": " + var20_13));
            var6_6.close();
            var16_9.flush();
            var23_15 = var15_8.getInputStream();
            var24_16 = new StringBuffer();
            while ((var25_17 = var23_15.read()) != -1) {
                var24_16.append((char)var25_17);
            }
            var4_4 = var24_16.toString();
            Log.i((String)"Response", (String)var4_4);
            var16_9.close();
            ** GOTO lbl57
        }
        catch (MalformedURLException var8_18) {}
        ** GOTO lbl-1000
        catch (Exception var13_23) {}
        ** GOTO lbl-1000
        catch (Exception var13_25) {}
lbl-1000: // 2 sources:
        {
            Log.e((String)"Upload file", (String)("Exception : " + var13_24.getMessage()), (Throwable)var13_24);
            ** GOTO lbl57
        }
        catch (MalformedURLException var8_20) {
            var4_4 = null;
        }
lbl-1000: // 2 sources:
        {
            var8_19.printStackTrace();
            Log.e((String)"Upload file to server", (String)("error: " + var8_19.getMessage()), (Throwable)var8_19);
lbl57: // 3 sources:
            try {
                var10_21 = new JSONObject(var4_4);
                return var10_21;
            }
            catch (Exception var12_26) {
                var12_26.printStackTrace();
                return null;
            }
        }
    }
}

