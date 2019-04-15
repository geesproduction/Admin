/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.ProgressDialog
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Toast
 *  android.widget.ToggleButton
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mmdfauzan.bamos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.mmdfauzan.bamos.MemberActivity;
import com.mmdfauzan.bamos.adapter.MemberAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.MemberItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MemberActivity
extends AppCompatActivity {
    Button buttonExport;
    DataPref dataPref;
    String export = "http://os.bikinaplikasi.com/404";
    boolean firstChange = true;
    String force_login;
    ImageButton imageButtonCari;
    LinearLayout layoutForceLogin;
    LinearLayout layoutLoading;
    ListView listViewMember;
    boolean loadingMore = false;
    MemberAdapter memberAdapter;
    ArrayList<MemberItem> memberItems = new ArrayList();
    int page = 1;
    int pages;
    ToggleButton toggleButtonForceLogin;

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.page = 1;
            this.memberItems = new ArrayList();
            this.memberAdapter = null;
            this.listViewMember.setAdapter(null);
            AsyncTask<String, String, JSONObject> asyncTask = new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)MemberActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)MemberActivity.this.dataPref.getToken());
                        hashMap.put((Object)"p", (Object)arrstring[0]);
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_members", "POST", (HashMap<String, String>)hashMap);
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
                    block14 : {
                        block13 : {
                            MemberActivity.this.loadingMore = false;
                            MemberActivity.this.layoutLoading.setVisibility(8);
                            if (jSONObject == null) {
                                AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                                Object[] arrobject = new String[]{String.valueOf((int)MemberActivity.this.page)};
                                asyncTask.execute(arrobject);
                                return;
                            }
                            try {
                                JSONArray jSONArray;
                                int n = jSONObject.getInt("success");
                                String string = jSONObject.getString("message");
                                if (n == 1) {
                                    String string2 = jSONObject.getString("force_login");
                                    if (string2.equals((Object)"0")) {
                                        MemberActivity.this.toggleButtonForceLogin.setChecked(false);
                                    } else if (string2.equals((Object)"1")) {
                                        MemberActivity.this.toggleButtonForceLogin.setChecked(true);
                                    }
                                    MemberActivity.this.firstChange = false;
                                    MemberActivity.this.pages = jSONObject.getInt("pages");
                                    jSONArray = jSONObject.getJSONArray("member");
                                } else {
                                    Toast.makeText((Context)MemberActivity.this, (CharSequence)string, (int)1).show();
                                    return;
                                }
                                for (int i = 0; i < jSONArray.length(); ++i) {
                                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                    MemberItem memberItem = new MemberItem();
                                    memberItem.setNamaMember(jSONObject2.getString("nama"));
                                    memberItem.setEmailMember(jSONObject2.getString("email"));
                                    memberItem.setTeleponMember(jSONObject2.getString("telepon"));
                                    memberItem.setAlamatMember(jSONObject2.getString("alamat"));
                                    memberItem.setStatusMember(jSONObject2.getString("status"));
                                    memberItem.setIdMember(jSONObject2.getString("idmember"));
                                    memberItem.setLastLoginMember(jSONObject2.getString("lastactivity"));
                                    memberItem.setProfilePictureMember(jSONObject2.getString("profile_picture"));
                                    memberItem.setSaldoMember(jSONObject2.getString("saldo"));
                                    memberItem.setRegisterTimeMember(jSONObject2.getString("register_time"));
                                    MemberActivity.this.memberItems.add((Object)memberItem);
                                }
                                if (jSONArray.length() <= 0) break block13;
                                MemberActivity.this.buttonExport.setVisibility(0);
                                break block14;
                            }
                            catch (JSONException jSONException) {
                                jSONException.printStackTrace();
                                return;
                            }
                        }
                        MemberActivity.this.buttonExport.setVisibility(4);
                    }
                    MemberActivity.this.layoutForceLogin.setVisibility(0);
                    MemberActivity.this.export = jSONObject.getString("export");
                    if (MemberActivity.this.memberAdapter == null) {
                        MemberActivity.this.memberAdapter = new MemberAdapter((Activity)MemberActivity.this, MemberActivity.this.memberItems);
                        MemberActivity.this.listViewMember.setAdapter((ListAdapter)MemberActivity.this.memberAdapter);
                    } else {
                        MemberActivity.this.memberAdapter.setMember(MemberActivity.this.memberItems);
                        MemberActivity.this.memberAdapter.notifyDataSetChanged();
                    }
                    MemberActivity.this.page = 1 + MemberActivity.this.page;
                }

                protected void onPreExecute() {
                    MemberActivity.this.loadingMore = true;
                    MemberActivity.this.layoutLoading.setVisibility(0);
                }
            };
            Object[] arrobject = new String[]{String.valueOf((int)this.page)};
            asyncTask.execute(arrobject);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427383);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Member");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewMember = (ListView)this.findViewById(2131296711);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.layoutForceLogin = (LinearLayout)this.findViewById(2131296648);
        this.layoutForceLogin.setVisibility(8);
        this.toggleButtonForceLogin = (ToggleButton)this.findViewById(2131297145);
        this.buttonExport = (Button)this.findViewById(2131296348);
        this.imageButtonCari = (ImageButton)this.findViewById(2131296586);
        this.imageButtonCari.setOnClickListener(new View.OnClickListener((MemberActivity)this){
            final /* synthetic */ MemberActivity this$0;
            {
                this.this$0 = memberActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.FindMemberActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.toggleButtonForceLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener((MemberActivity)this){
            final /* synthetic */ MemberActivity this$0;
            {
                this.this$0 = memberActivity;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (!this.this$0.firstChange) {
                    this.this$0.force_login = bl ? "1" : "0";
                    new AsyncTask<String, String, JSONObject>(){
                        JSONParser jsonParser = new JSONParser();
                        ProgressDialog progressDialog;

                        protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                            try {
                                HashMap hashMap = new HashMap();
                                hashMap.put((Object)"email", (Object)MemberActivity.this.dataPref.getEmail());
                                hashMap.put((Object)"token", (Object)MemberActivity.this.dataPref.getToken());
                                hashMap.put((Object)"force_login", (Object)MemberActivity.this.force_login);
                                JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/update_force_login", "POST", (HashMap<String, String>)hashMap);
                                return jSONObject;
                            }
                            catch (Exception exception) {
                                exception.printStackTrace();
                                return null;
                            }
                        }

                        /*
                         * Enabled force condition propagation
                         * Lifted jumps to return sites
                         */
                        protected void onPostExecute(JSONObject jSONObject) {
                            if (this.progressDialog.isShowing()) {
                                this.progressDialog.dismiss();
                            }
                            if (jSONObject != null) {
                                String string;
                                try {
                                    int n = jSONObject.getInt("success");
                                    string = jSONObject.getString("message");
                                    if (n == 1) return;
                                }
                                catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                    return;
                                }
                                Toast.makeText((Context)MemberActivity.this, (CharSequence)string, (int)1).show();
                                return;
                            }
                            new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        }

                        protected void onPreExecute() {
                            this.progressDialog = new ProgressDialog((Context)MemberActivity.this);
                            this.progressDialog.setMessage((CharSequence)"Mohon tunggu.");
                            this.progressDialog.setIndeterminate(false);
                            this.progressDialog.setCancelable(false);
                            this.progressDialog.show();
                        }
                    }.execute((Object[])new String[0]);
                }
            }
        });
        this.buttonExport.setOnClickListener(new View.OnClickListener((MemberActivity)this){
            final /* synthetic */ MemberActivity this$0;
            {
                this.this$0 = memberActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(android.net.Uri.parse((String)this.this$0.export));
                this.this$0.startActivity(intent);
            }
        });
        this.listViewMember.setOnScrollListener(new AbsListView.OnScrollListener((MemberActivity)this){
            final /* synthetic */ MemberActivity this$0;
            {
                this.this$0 = memberActivity;
            }

            public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                if (n + n2 == n3 && !this.this$0.loadingMore && this.this$0.page <= this.this$0.pages) {
                    AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
                    Object[] arrobject = new String[]{String.valueOf((int)this.this$0.page)};
                    asyncTask.execute(arrobject);
                }
            }

            public void onScrollStateChanged(AbsListView absListView, int n) {
            }
        });
        this.listViewMember.setOnItemClickListener(new AdapterView.OnItemClickListener((MemberActivity)this){
            final /* synthetic */ MemberActivity this$0;
            {
                this.this$0 = memberActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.EditMemberActivity.class);
                intent.putExtra("idmember", ((MemberItem)this.this$0.memberItems.get(n)).getIdMember());
                intent.putExtra("nama", ((MemberItem)this.this$0.memberItems.get(n)).getNamaMember());
                intent.putExtra("email", ((MemberItem)this.this$0.memberItems.get(n)).getEmailMember());
                intent.putExtra("telepon", ((MemberItem)this.this$0.memberItems.get(n)).getTeleponMember());
                intent.putExtra("alamat", ((MemberItem)this.this$0.memberItems.get(n)).getAlamatMember());
                intent.putExtra("lastactivity", ((MemberItem)this.this$0.memberItems.get(n)).getLastLoginMember());
                intent.putExtra("status", ((MemberItem)this.this$0.memberItems.get(n)).getStatusMember());
                intent.putExtra("profile_picture", ((MemberItem)this.this$0.memberItems.get(n)).getProfilePictureMember());
                intent.putExtra("saldo", ((MemberItem)this.this$0.memberItems.get(n)).getSaldoMember());
                intent.putExtra("register_time", ((MemberItem)this.this$0.memberItems.get(n)).getRegisterTimeMember());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        AsyncTask<String, String, JSONObject> asyncTask = new /* invalid duplicate definition of identical inner class */;
        Object[] arrobject = new String[]{String.valueOf((int)this.page)};
        asyncTask.execute(arrobject);
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
        this.finish();
        return true;
    }

}

