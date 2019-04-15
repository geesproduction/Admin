/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 *  android.widget.Toast
 *  android.widget.ToggleButton
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mmdfauzan.bamos.EditMemberActivity;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

public class EditMemberActivity
extends AppCompatActivity {
    Button buttonSave;
    Button buttonUbahSaldo;
    boolean changed = false;
    CircleImageView circleImageViewProfile;
    DataPref dataPref;
    EditText editTextPassword1;
    EditText editTextPassword2;
    EditText editTextPasswordAdmin;
    EditText editTextSaldo;
    EditText editTextSaldoPasswordAdmin;
    String idMember;
    ImageButton imageButtonMemberChat;
    ImageButton imageButtonMemberSms;
    ImageButton imageButtonMemberTelepon;
    ImageButton imageButtonMemberWa;
    LinearLayout layoutSaldo;
    String namaMember;
    String password1;
    String password2;
    String passwordAdmin;
    String passwordMember;
    String saldo;
    String selectedStatus;
    String status;
    String teleponMember;
    TextView textViewAlamat;
    TextView textViewEmail;
    TextView textViewLastLogin;
    TextView textViewNama;
    TextView textViewRegister;
    TextView textViewTelepon;
    ToggleButton toggleButtonStatus;

    public void onBackPressed() {
        if (this.changed) {
            this.setResult(-1);
        }
        this.finish();
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427370);
        this.getSupportActionBar().setTitle((CharSequence)"Kelola Member");
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.toggleButtonStatus = (ToggleButton)this.findViewById(2131297152);
        this.textViewNama = (TextView)this.findViewById(2131297098);
        this.textViewEmail = (TextView)this.findViewById(2131297056);
        this.textViewTelepon = (TextView)this.findViewById(2131297130);
        this.textViewAlamat = (TextView)this.findViewById(2131297051);
        this.textViewLastLogin = (TextView)this.findViewById(2131297084);
        this.textViewRegister = (TextView)this.findViewById(2131297125);
        this.editTextPassword1 = (EditText)this.findViewById(2131296534);
        this.editTextPassword2 = (EditText)this.findViewById(2131296535);
        this.editTextPasswordAdmin = (EditText)this.findViewById(2131296537);
        this.editTextSaldoPasswordAdmin = (EditText)this.findViewById(2131296543);
        this.editTextSaldo = (EditText)this.findViewById(2131296542);
        this.buttonSave = (Button)this.findViewById(2131296389);
        this.buttonUbahSaldo = (Button)this.findViewById(2131296404);
        this.circleImageViewProfile = (CircleImageView)this.findViewById(2131296629);
        this.layoutSaldo = (LinearLayout)this.findViewById(2131296681);
        this.imageButtonMemberTelepon = (ImageButton)this.findViewById(2131296603);
        this.imageButtonMemberSms = (ImageButton)this.findViewById(2131296602);
        this.imageButtonMemberWa = (ImageButton)this.findViewById(2131296604);
        this.imageButtonMemberChat = (ImageButton)this.findViewById(2131296601);
        if (this.dataPref.getSaldo().equals((Object)"1")) {
            this.layoutSaldo.setVisibility(0);
        } else {
            this.layoutSaldo.setVisibility(8);
        }
        Intent intent = this.getIntent();
        this.idMember = intent.getStringExtra("idmember");
        this.namaMember = intent.getStringExtra("nama");
        this.teleponMember = intent.getStringExtra("telepon");
        this.textViewNama.setText((CharSequence)this.namaMember);
        this.textViewEmail.setText((CharSequence)intent.getStringExtra("email"));
        this.textViewTelepon.setText((CharSequence)this.teleponMember);
        this.textViewAlamat.setText((CharSequence)intent.getStringExtra("alamat"));
        this.textViewLastLogin.setText((CharSequence)intent.getStringExtra("lastactivity"));
        this.textViewRegister.setText((CharSequence)intent.getStringExtra("register_time"));
        this.editTextSaldo.setText((CharSequence)intent.getStringExtra("saldo"));
        Picasso.with((Context)this).load(intent.getStringExtra("profile_picture")).placeholder(2131230844).resize(100, 100).centerInside().noFade().into((ImageView)this.circleImageViewProfile);
        this.status = intent.getStringExtra("status");
        if (this.status.equals((Object)"0")) {
            this.toggleButtonStatus.setChecked(false);
        } else if (this.status.equals((Object)"1")) {
            this.toggleButtonStatus.setChecked(true);
        }
        this.toggleButtonStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                this.this$0.selectedStatus = bl ? "1" : "0";
                new AsyncTask<String, String, JSONObject>(){
                    JSONParser jsonParser = new JSONParser();
                    ProgressDialog progressDialog;

                    protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                        try {
                            HashMap hashMap = new HashMap();
                            hashMap.put((Object)"email", (Object)EditMemberActivity.this.dataPref.getEmail());
                            hashMap.put((Object)"token", (Object)EditMemberActivity.this.dataPref.getToken());
                            hashMap.put((Object)"idmember", (Object)EditMemberActivity.this.idMember);
                            hashMap.put((Object)"status", (Object)EditMemberActivity.this.selectedStatus);
                            JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_member_status", "POST", (HashMap<String, String>)hashMap);
                            return jSONObject;
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                            return null;
                        }
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
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                            return;
                        }
                        try {
                            int n = jSONObject.getInt("success");
                            String string = jSONObject.getString("message");
                            if (n == 1) {
                                EditMemberActivity.this.changed = true;
                                EditMemberActivity.this.status = jSONObject.getString("status");
                            } else {
                                Toast.makeText((Context)EditMemberActivity.this, (CharSequence)string, (int)1).show();
                            }
                            if (EditMemberActivity.this.status.equals((Object)"1")) {
                                EditMemberActivity.this.toggleButtonStatus.setChecked(true);
                                return;
                            }
                            EditMemberActivity.this.toggleButtonStatus.setChecked(false);
                            return;
                        }
                        catch (JSONException jSONException) {
                            jSONException.printStackTrace();
                            return;
                        }
                    }

                    protected void onPreExecute() {
                        this.progressDialog = new ProgressDialog((Context)EditMemberActivity.this);
                        this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                        this.progressDialog.setIndeterminate(false);
                        this.progressDialog.setCancelable(false);
                        this.progressDialog.show();
                    }
                }.execute((Object[])new String[0]);
            }
        });
        this.buttonUbahSaldo.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.this$0.passwordAdmin = this.this$0.editTextSaldoPasswordAdmin.getText().toString();
                this.this$0.saldo = this.this$0.editTextSaldo.getText().toString();
                if (this.this$0.passwordAdmin.length() > 4) {
                    this.this$0.editTextSaldoPasswordAdmin.setError(null);
                    if (this.this$0.saldo.length() > 2) {
                        this.this$0.editTextSaldo.setError(null);
                        new AsyncTask<String, String, JSONObject>(){
                            JSONParser jsonParser = new JSONParser();
                            ProgressDialog progressDialog;

                            protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                try {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put((Object)"email", (Object)EditMemberActivity.this.dataPref.getEmail());
                                    hashMap.put((Object)"token", (Object)EditMemberActivity.this.dataPref.getToken());
                                    hashMap.put((Object)"idmember", (Object)EditMemberActivity.this.idMember);
                                    hashMap.put((Object)"saldo", (Object)EditMemberActivity.this.saldo);
                                    hashMap.put((Object)"passwordadmin", (Object)EditMemberActivity.this.passwordAdmin);
                                    JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_balance", "POST", (HashMap<String, String>)hashMap);
                                    return jSONObject;
                                }
                                catch (Exception exception) {
                                    exception.printStackTrace();
                                    return null;
                                }
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
                                    new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                    return;
                                }
                                try {
                                    int n = jSONObject.getInt("success");
                                    String string = jSONObject.getString("message");
                                    if (n == 1) {
                                        EditMemberActivity.this.setResult(-1);
                                        Toast.makeText((Context)EditMemberActivity.this, (CharSequence)string, (int)1).show();
                                        EditMemberActivity.this.finish();
                                        return;
                                    }
                                    Toast.makeText((Context)EditMemberActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                            }

                            protected void onPreExecute() {
                                this.progressDialog = new ProgressDialog((Context)EditMemberActivity.this);
                                this.progressDialog.setMessage((CharSequence)"Mengubah Saldo.");
                                this.progressDialog.setIndeterminate(false);
                                this.progressDialog.setCancelable(false);
                                this.progressDialog.show();
                            }
                        }.execute((Object[])new String[0]);
                        return;
                    }
                    this.this$0.editTextSaldo.setError((CharSequence)"Masukkan saldo dengan benar");
                    return;
                }
                this.this$0.editTextSaldoPasswordAdmin.setError((CharSequence)"Masukkan password dengan benar");
            }
        });
        this.buttonSave.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                ((android.view.inputmethod.InputMethodManager)this.this$0.getSystemService("input_method")).hideSoftInputFromWindow(view.getWindowToken(), 0);
                this.this$0.password1 = this.this$0.editTextPassword1.getText().toString();
                this.this$0.password2 = this.this$0.editTextPassword2.getText().toString();
                this.this$0.passwordAdmin = this.this$0.editTextPasswordAdmin.getText().toString();
                if (this.this$0.password1.length() > 5) {
                    this.this$0.editTextPassword1.setError(null);
                    if (this.this$0.password1.equals((Object)this.this$0.password2)) {
                        this.this$0.editTextPassword2.setError(null);
                        if (this.this$0.passwordAdmin.length() > 0) {
                            this.this$0.editTextPasswordAdmin.setError(null);
                            this.this$0.passwordMember = this.this$0.password1;
                            new AsyncTask<String, String, JSONObject>(){
                                JSONParser jsonParser = new JSONParser();
                                ProgressDialog progressDialog;

                                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                                    try {
                                        HashMap hashMap = new HashMap();
                                        hashMap.put((Object)"email", (Object)EditMemberActivity.this.dataPref.getEmail());
                                        hashMap.put((Object)"token", (Object)EditMemberActivity.this.dataPref.getToken());
                                        hashMap.put((Object)"idmember", (Object)EditMemberActivity.this.idMember);
                                        hashMap.put((Object)"passwordmember", (Object)EditMemberActivity.this.passwordMember);
                                        hashMap.put((Object)"passwordadmin", (Object)EditMemberActivity.this.passwordAdmin);
                                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_member_password", "POST", (HashMap<String, String>)hashMap);
                                        return jSONObject;
                                    }
                                    catch (Exception exception) {
                                        exception.printStackTrace();
                                        return null;
                                    }
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
                                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                                        return;
                                    }
                                    try {
                                        int n = jSONObject.getInt("success");
                                        String string = jSONObject.getString("message");
                                        if (n == 1) {
                                            Toast.makeText((Context)EditMemberActivity.this, (CharSequence)string, (int)1).show();
                                            EditMemberActivity.this.finish();
                                            return;
                                        }
                                        Toast.makeText((Context)EditMemberActivity.this, (CharSequence)string, (int)1).show();
                                        return;
                                    }
                                    catch (JSONException jSONException) {
                                        jSONException.printStackTrace();
                                        return;
                                    }
                                }

                                protected void onPreExecute() {
                                    this.progressDialog = new ProgressDialog((Context)EditMemberActivity.this);
                                    this.progressDialog.setMessage((CharSequence)"Mengubah Password.");
                                    this.progressDialog.setIndeterminate(false);
                                    this.progressDialog.setCancelable(false);
                                    this.progressDialog.show();
                                }
                            }.execute((Object[])new String[0]);
                            return;
                        }
                        this.this$0.editTextPasswordAdmin.setError((CharSequence)"Isikan password admin");
                        return;
                    }
                    this.this$0.editTextPassword2.setError((CharSequence)"Password tidak sama");
                    return;
                }
                this.this$0.editTextPassword1.setError((CharSequence)"Password minimal 6 karakter");
            }
        });
        this.imageButtonMemberChat.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.ChatDetailActivity.class);
                intent.putExtra("startChat", 1);
                intent.putExtra("idmember", this.this$0.idMember);
                intent.putExtra("nama", this.this$0.namaMember);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberTelepon.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts((String)"tel", (String)this.this$0.teleponMember, null));
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberSms.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", this.this$0.teleponMember);
                this.this$0.startActivity(intent);
            }
        });
        this.imageButtonMemberWa.setOnClickListener(new View.OnClickListener((EditMemberActivity)this){
            final /* synthetic */ EditMemberActivity this$0;
            {
                this.this$0 = editMemberActivity;
            }

            public void onClick(View view) {
                this.this$0.openWA(this.this$0.teleponMember);
            }
        });
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
        if (this.changed) {
            this.setResult(-1);
        }
        this.finish();
        return true;
    }

    public void openWA(String string) {
        if (string.startsWith("0")) {
            string = string.replaceFirst("0", "62");
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse((String)("https://api.whatsapp.com/send?phone=" + string)));
        this.startActivity(intent);
    }

}

