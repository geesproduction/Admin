/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.support.v4.view.PagerAdapter
 *  android.support.v4.view.ViewPager
 *  android.support.v7.app.ActionBar
 *  android.support.v7.app.AppCompatActivity
 *  android.view.MenuItem
 *  android.view.View
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import com.mmdfauzan.bamos.adapter.GambarPagerAdapter;

public class ViewGambarActivity
extends AppCompatActivity {
    GambarPagerAdapter gambarPagerAdapter;
    String[] images;
    ViewPager viewPager;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2131427405);
        this.getSupportActionBar().setTitle((CharSequence)"Foto Produk");
        this.getSupportActionBar().hide();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.viewPager = (ViewPager)this.findViewById(2131297165);
        Intent intent = this.getIntent();
        int n = intent.getIntExtra("idgambar", 0);
        String string = intent.getStringExtra("gambar");
        String string2 = intent.getStringExtra("gambar2");
        String string3 = intent.getStringExtra("gambar3");
        String string4 = intent.getStringExtra("gambar4");
        String string5 = intent.getStringExtra("gambar5");
        boolean bl = string.equals((Object)"");
        int n2 = 0;
        if (!bl) {
            boolean bl2 = string.equals((Object)"null");
            n2 = 0;
            if (!bl2) {
                n2 = 0 + 1;
            }
        }
        if (!string2.equals((Object)"") && !string2.equals((Object)"null")) {
            ++n2;
        }
        if (!string3.equals((Object)"") && !string3.equals((Object)"null")) {
            ++n2;
        }
        if (!string4.equals((Object)"") && !string4.equals((Object)"null")) {
            ++n2;
        }
        if (!string5.equals((Object)"") && !string5.equals((Object)"null")) {
            ++n2;
        }
        this.images = new String[n2];
        boolean bl3 = string.equals((Object)"");
        int n3 = 0;
        if (!bl3) {
            boolean bl4 = string.equals((Object)"null");
            n3 = 0;
            if (!bl4) {
                this.images[0] = string;
                n3 = 0 + 1;
            }
        }
        if (!string2.equals((Object)"") && !string2.equals((Object)"null")) {
            this.images[n3] = string2;
            ++n3;
        }
        if (!string3.equals((Object)"") && !string3.equals((Object)"null")) {
            this.images[n3] = string3;
            ++n3;
        }
        if (!string4.equals((Object)"") && !string4.equals((Object)"null")) {
            this.images[n3] = string4;
            ++n3;
        }
        if (!string5.equals((Object)"") && !string5.equals((Object)"null")) {
            this.images[n3] = string5;
            n3 + 1;
        }
        this.gambarPagerAdapter = new GambarPagerAdapter((Context)this, this.images);
        this.viewPager.setAdapter((PagerAdapter)this.gambarPagerAdapter);
        this.viewPager.setCurrentItem(n);
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

