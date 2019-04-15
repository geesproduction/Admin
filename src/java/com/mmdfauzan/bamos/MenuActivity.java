/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncTask
 *  android.os.Bundle
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ImageButton
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.Toast
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.mmdfauzan.bamos.MenuActivity;
import com.mmdfauzan.bamos.adapter.MainMenuAdapter;
import com.mmdfauzan.bamos.app.DataPref;
import com.mmdfauzan.bamos.helper.JSONParser;
import com.mmdfauzan.bamos.model.MainMenuItem;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity
extends AppCompatActivity {
    ImageButton buttonAdd;
    DataPref dataPref;
    LinearLayout layoutLoading;
    ListView listViewMenu;
    boolean loadingMore = false;
    MainMenuAdapter mainMenuAdapter;
    ArrayList<MainMenuItem> mainMenuItems = new ArrayList();

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 2 && n2 == -1) {
            this.mainMenuItems = new ArrayList();
            this.mainMenuAdapter = null;
            this.listViewMenu.setAdapter(null);
            new AsyncTask<String, String, JSONObject>(){
                JSONParser jsonParser = new JSONParser();

                protected /* varargs */ JSONObject doInBackground(String ... arrstring) {
                    try {
                        HashMap hashMap = new HashMap();
                        hashMap.put((Object)"email", (Object)MenuActivity.this.dataPref.getEmail());
                        hashMap.put((Object)"token", (Object)MenuActivity.this.dataPref.getToken());
                        JSONObject jSONObject = this.jsonParser.makeHttpRequest("http://os.bikinaplikasi.com/api/admin_api_v2/get_menu", "POST", (HashMap<String, String>)hashMap);
                        return jSONObject;
                    }
                    catch (Exception exception) {
                        exception.printStackTrace();
                        return null;
                    }
                }

                /*
                 * Unable to fully structure code
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 * Lifted jumps to return sites
                 */
                protected void onPostExecute(JSONObject var1) {
                    MenuActivity.this.loadingMore = false;
                    MenuActivity.this.layoutLoading.setVisibility(8);
                    if (var1 == null) {
                        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
                        return;
                    }
                    try {
                        var4_2 = var1.getInt("success");
                        var5_3 = var1.getString("message");
                        if (var4_2 == 1) {
                            var6_4 = var1.getJSONArray("menu");
                        } else {
                            Toast.makeText((Context)MenuActivity.this, (CharSequence)var5_3, (int)1).show();
                            return;
                        }
                        for (var7_5 = 0; var7_5 < var6_4.length(); ++var7_5) {
                            var8_6 = var6_4.getJSONObject(var7_5);
                            var9_7 = new MainMenuItem();
                            var9_7.setIdmenu(var8_6.getString("idmenu"));
                            var9_7.setNama_menu(var8_6.getString("nama"));
                            var9_7.setIcon_menu(var8_6.getString("icon"));
                            var9_7.setLink_menu(var8_6.getString("link"));
                            MenuActivity.this.mainMenuItems.add((Object)var9_7);
                        }
                        if (var6_4.length() >= 8) {
                            MenuActivity.this.buttonAdd.setVisibility(8);
                        } else {
                            MenuActivity.this.buttonAdd.setVisibility(0);
                        }
                        if (MenuActivity.this.mainMenuAdapter != null) ** break block10
                        MenuActivity.this.mainMenuAdapter = new MainMenuAdapter((Activity)MenuActivity.this, MenuActivity.this.mainMenuItems);
                        MenuActivity.this.listViewMenu.setAdapter((ListAdapter)MenuActivity.this.mainMenuAdapter);
                        return;
                    }
                    catch (JSONException var3_8) {
                        var3_8.printStackTrace();
                        return;
                    }
                    {
                        
                        MenuActivity.this.mainMenuAdapter.setMainMenu(MenuActivity.this.mainMenuItems);
                        MenuActivity.this.mainMenuAdapter.notifyDataSetChanged();
                        return;
                    }
                }

                protected void onPreExecute() {
                    MenuActivity.this.loadingMore = true;
                    MenuActivity.this.layoutLoading.setVisibility(0);
                }
            }.execute((Object[])new String[0]);
            this.setResult(-1);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427384);
        this.getSupportActionBar().setElevation(0.0f);
        this.getSupportActionBar().setTitle((CharSequence)"Menu");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.dataPref = new DataPref((Context)this);
        this.listViewMenu = (ListView)this.findViewById(2131296712);
        this.layoutLoading = (LinearLayout)this.findViewById(2131296672);
        this.buttonAdd = (ImageButton)this.findViewById(2131296584);
        this.buttonAdd.setOnClickListener(new View.OnClickListener((MenuActivity)this){
            final /* synthetic */ MenuActivity this$0;
            {
                this.this$0 = menuActivity;
            }

            public void onClick(View view) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.AddMenuActivity.class);
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        this.listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener((MenuActivity)this){
            final /* synthetic */ MenuActivity this$0;
            {
                this.this$0 = menuActivity;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                Intent intent = new Intent((Context)this.this$0, com.mmdfauzan.bamos.AddMenuActivity.class);
                intent.putExtra("edit", true);
                intent.putExtra("nama", ((MainMenuItem)this.this$0.mainMenuItems.get(n)).getNama_menu());
                intent.putExtra("idmenu", ((MainMenuItem)this.this$0.mainMenuItems.get(n)).getIdmenu());
                intent.putExtra("link", ((MainMenuItem)this.this$0.mainMenuItems.get(n)).getLink_menu());
                intent.putExtra("icon", ((MainMenuItem)this.this$0.mainMenuItems.get(n)).getIcon_menu());
                this.this$0.startActivityForResult(intent, 2);
            }
        });
        new /* invalid duplicate definition of identical inner class */.execute((Object[])new String[0]);
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

