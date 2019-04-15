/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  java.io.DataOutputStream
 *  java.io.FileInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 *  java.net.HttpURLConnection
 *  java.net.MalformedURLException
 *  java.net.URL
 *  java.net.URLConnection
 */
package com.mmdfauzan.bamos.helper;

import android.util.Log;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpFileUpload
implements Runnable {
    URL connectURL;
    byte[] dataToServer;
    FileInputStream fileInputStream = null;
    String fileName;
    String responseString;
    String status = "0";

    public HttpFileUpload(String string2, String string3, String string4) {
        try {
            this.fileName = string3;
            this.fileInputStream = new FileInputStream(string4);
            this.connectURL = new URL(string2);
            return;
        }
        catch (Exception exception) {
            Log.i((String)"HttpFileUpload", (String)"URL Malformatted");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String Send() {
        try {
            int n;
            Log.e((String)"fSnd", (String)"Starting Http File Sending to URL");
            HttpURLConnection httpURLConnection = (HttpURLConnection)this.connectURL.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("ENCTYPE", "multipart/form-data");
            httpURLConnection.setRequestProperty("uploaded_file", this.fileName);
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + "*****");
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + this.fileName + "\"" + "\r\n");
            dataOutputStream.writeBytes("\r\n");
            Log.e((String)"fSnd", (String)"Headers are written");
            int n2 = Math.min((int)this.fileInputStream.available(), (int)15360);
            byte[] arrby = new byte[n2];
            int n3 = this.fileInputStream.read(arrby, 0, n2);
            while (n3 > 0) {
                dataOutputStream.write(arrby, 0, n2);
                n2 = Math.min((int)this.fileInputStream.available(), (int)15360);
                n3 = this.fileInputStream.read(arrby, 0, n2);
            }
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.writeBytes("--" + "*****" + "--" + "\r\n");
            this.fileInputStream.close();
            dataOutputStream.flush();
            Log.e((String)"fSnd", (String)("File Sent, Response: " + String.valueOf((int)httpURLConnection.getResponseCode())));
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            while ((n = inputStream.read()) != -1) {
                stringBuffer.append((char)n);
            }
            this.status = stringBuffer.toString();
            Log.i((String)"Response", (String)this.status);
            dataOutputStream.close();
            return this.status;
        }
        catch (MalformedURLException malformedURLException) {
            Log.e((String)"fSnd", (String)("URL error: " + malformedURLException.getMessage()), (Throwable)malformedURLException);
            return this.status;
        }
        catch (IOException iOException) {
            Log.e((String)"fSnd", (String)("IO error: " + iOException.getMessage()), (Throwable)iOException);
            return this.status;
        }
    }

    public void run() {
    }
}

