/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.daimajia.slider.library.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class DefaultSliderView
extends BaseSliderView {
    public DefaultSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from((Context)this.getContext()).inflate(R.layout.render_type_default, null);
        this.bindEventAndShow(view, (ImageView)view.findViewById(R.id.daimajia_slider_image));
        return view;
    }
}

