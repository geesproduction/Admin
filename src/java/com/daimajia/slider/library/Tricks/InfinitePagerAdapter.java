/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Parcelable
 *  android.support.v4.view.PagerAdapter
 *  android.view.View
 *  android.view.ViewGroup
 *  java.lang.ClassLoader
 *  java.lang.Object
 *  java.lang.String
 */
package com.daimajia.slider.library.Tricks;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.slider.library.SliderAdapter;

public class InfinitePagerAdapter
extends PagerAdapter {
    private static final boolean DEBUG = false;
    private static final String TAG = "InfinitePagerAdapter";
    private SliderAdapter adapter;

    public InfinitePagerAdapter(SliderAdapter sliderAdapter) {
        this.adapter = sliderAdapter;
    }

    private void debug(String string2) {
    }

    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        if (this.getRealCount() == 0) {
            return;
        }
        int n2 = n % this.getRealCount();
        InfinitePagerAdapter.super.debug("destroyItem: real position: " + n);
        InfinitePagerAdapter.super.debug("destroyItem: virtual position: " + n2);
        this.adapter.destroyItem(viewGroup, n2, object);
    }

    public void finishUpdate(ViewGroup viewGroup) {
        this.adapter.finishUpdate(viewGroup);
    }

    public int getCount() {
        return Integer.MAX_VALUE;
    }

    public SliderAdapter getRealAdapter() {
        return this.adapter;
    }

    public int getRealCount() {
        return this.adapter.getCount();
    }

    public Object instantiateItem(ViewGroup viewGroup, int n) {
        if (this.getRealCount() == 0) {
            return null;
        }
        int n2 = n % this.getRealCount();
        InfinitePagerAdapter.super.debug("instantiateItem: real position: " + n);
        InfinitePagerAdapter.super.debug("instantiateItem: virtual position: " + n2);
        return this.adapter.instantiateItem(viewGroup, n2);
    }

    public boolean isViewFromObject(View view, Object object) {
        return this.adapter.isViewFromObject(view, object);
    }

    public void restoreState(Parcelable parcelable, ClassLoader classLoader) {
        this.adapter.restoreState(parcelable, classLoader);
    }

    public Parcelable saveState() {
        return this.adapter.saveState();
    }

    public void startUpdate(ViewGroup viewGroup) {
        this.adapter.startUpdate(viewGroup);
    }
}

