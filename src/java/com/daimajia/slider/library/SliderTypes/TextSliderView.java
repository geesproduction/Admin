/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 *  java.lang.CharSequence
 *  java.lang.String
 */
package com.daimajia.slider.library.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.slider.library.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

public class TextSliderView
extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View view = LayoutInflater.from((Context)this.getContext()).inflate(R.layout.render_type_text, null);
        ImageView imageView = (ImageView)view.findViewById(R.id.daimajia_slider_image);
        ((TextView)view.findViewById(R.id.description)).setText((CharSequence)this.getDescription());
        this.bindEventAndShow(view, imageView);
        return view;
    }
}

