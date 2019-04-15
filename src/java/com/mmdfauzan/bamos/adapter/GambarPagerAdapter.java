/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.view.PagerAdapter
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  com.squareup.picasso.Picasso
 *  com.squareup.picasso.RequestCreator
 *  java.lang.Object
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Date
 */
package com.mmdfauzan.bamos.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.mmdfauzan.bamos.helper.PinchTouchImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GambarPagerAdapter
extends PagerAdapter {
    Context context;
    String[] images;
    LayoutInflater layoutInflater;

    public GambarPagerAdapter(Context context, String[] arrstring) {
        this.context = context;
        this.images = arrstring;
        this.layoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup.removeView((View)((LinearLayout)object));
    }

    public int getCount() {
        return this.images.length;
    }

    public Object instantiateItem(ViewGroup viewGroup, int n) {
        View view = this.layoutInflater.inflate(2131427433, viewGroup, false);
        String string2 = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        PinchTouchImageView pinchTouchImageView = (PinchTouchImageView)view.findViewById(2131296621);
        Picasso.with((Context)this.context).load("http://os.bikinaplikasi.com/gambar/" + this.images[n] + "?" + string2).placeholder(2131230939).into((ImageView)pinchTouchImageView);
        viewGroup.addView(view);
        return view;
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == (LinearLayout)object;
    }
}

