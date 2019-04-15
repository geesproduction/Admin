/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Patterns
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mmdfauzan.bamos.LoginActivity;
import com.mmdfauzan.bamos.MainActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity
extends AppCompatActivity {
    Button buttonForgotPassword;
    Button buttonLogin;
    Button buttonRegister;
    DataPref dataPref;
    EditText editTextEmail;
    EditText editTextPassword;
    String textEmail;
    String textPassword;

    public static final boolean isValidEmail(CharSequence charSequence) {
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427380);
        this.getSupportActionBar().setTitle((CharSequence)"Masuk");
        this.getSupportActionBar().setElevation(0.0f);
        this.editTextEmail = (EditText)this.findViewById(2131296483);
        this.editTextPassword = (EditText)this.findViewById(2131296533);
        this.buttonLogin = (Button)this.findViewById(2131296366);
        this.buttonForgotPassword = (Button)this.findViewById(2131296350);
        this.buttonRegister = (Button)this.findViewById(2131296387);
        this.dataPref = new DataPref((Context)this);
        if (!this.dataPref.isLoggedIn()) {
            this.buttonLogin.setOnClickListener(new View.OnClickListener((LoginActivity)this){
                final /* synthetic */ LoginActivity this$0;
                {
                    this.this$0 = loginActivity;
                }

                public void onClick(View view) {
                    this.this$0.textEmail = this.this$0.editTextEmail.getText().toString();
                    this.this$0.textPassword = this.this$0.editTextPassword.getText().toString();
                    android.view.inputmethod.InputMethodManager inputMethodManager = (android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method");
                    if (this.this$0.textEmail.length() > 5 && LoginActivity.isValidEmail(this.this$0.textEmail)) {
                        this.this$0.editTextEmail.setError(null);
                        if (this.this$0.textPassword.length() > 0) {
                            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            this.this$0.editTextPassword.setError(null);
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                /*
                                 * Enabled force condition propagation
                                 * Lifted jumps to return sites
                                 */
                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)LoginActivity.this.textEmail);
                                    hashMap.put((Object)"password", (Object)LoginActivity.this.textPassword);
                                    hashMap.put((Object)"fcmtoken", (Object)LoginActivity.this.dataPref.getFcmToken());
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/login", "POST", (HashMap<String, String>)hashMap);
                                    if (jSONObject == null) return null;
                                    try {
                                        Log.d((String)"JSON result", (String)jSONObject.toString());
                                        return jSONObject;
                                    }
                                    catch (Exception exception) {
                                        exception.printStackTrace();
                                    }
                                    return null;
                                }

                                /*
                                 * Enabled aggressive block sorting
                                 * Enabled unnecessary exception pruning
                                 * Enabled aggressive exception aggregation
                                 */
                                protected void onPostExecute(JSONObject jSONObject) {
                                    if (this.progressDialog.isShowing()) {
                                        this.progressDialog.dismiss();
                                    }
                                    if (jSONObject == null) {
                                        Toast.makeText((Context)LoginActivity.this, (CharSequence)"Tidak dapat terhubung ke server. Coba lagi nanti.", (int)1).show();
                                        return;
                                    }
                                    try {
                                        int n = jSONObject.getInt("success");
                                        String string = jSONObject.getString("message");
                                        if (n == 1) {
                                            Toast.makeText((Context)LoginActivity.this, (CharSequence)string, (int)0).show();
                                            String string2 = jSONObject.getString("email");
                                            String string3 = jSONObject.getString("token");
                                            String string4 = jSONObject.getString("username");
                                            LoginActivity.this.dataPref.setLogin(string2, string4, string3, true);
                                            Intent intent = new Intent((Context)LoginActivity.this, MainActivity.class);
                                            intent.setFlags(268435456);
                                            intent.addFlags(32768);
                                            intent.addFlags(32768);
                                            intent.addFlags(67108864);
                                            LoginActivity.this.startActivity(intent);
                                            return;
                                        }
                                        Toast.makeText((Context)LoginActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    catch (JSONException jSONException) {
                                        jSONException.printStackTrace();
                                        return;
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)LoginActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Masuk ke Akun Bikin Aplikasi : Online Shop");
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                            return;
                        }
                        this.this$0.editTextPassword.setError((CharSequence)"Mohon isikan password");
                        return;
                    }
                    this.this$0.editTextEmail.setError((CharSequence)"Mohon isikan email yang valid");
                }
            });
            this.buttonRegister.setOnClickListener(new View.OnClickListener((LoginActivity)this){
                final /* synthetic */ LoginActivity this$0;
                {
                    this.this$0 = loginActivity;
                }

                public void onClick(View view) {
                    this.this$0.startActivity(new Intent((Context)this.this$0, com.mmdfauzan.bamos.RegisterActivity.class));
                }
            });
            this.buttonForgotPassword.setOnClickListener(new View.OnClickListener((LoginActivity)this){
                final /* synthetic */ LoginActivity this$0;
                {
                    this.this$0 = loginActivity;
                }

                public void onClick(View view) {
                    this.this$0.startActivity(new Intent("android.intent.action.VIEW", android.net.Uri.parse((String)"http://os.bikinaplikasi.com/lupapassword")));
                }
            });
            return;
        }
        this.startActivity(new Intent((Context)this, MainActivity.class));
        this.finish();
    }

}

