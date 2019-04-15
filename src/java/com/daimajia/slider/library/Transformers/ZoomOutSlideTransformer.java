/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  com.nineoldandroids.view.ViewHelper
 *  java.lang.Math
 */
package com.daimajia.slider.library.Transformers;

import android.view.View;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.nineoldandroids.view.ViewHelper;

public class ZoomOutSlideTransformer
extends BaseTransformer {
    private static final float MIN_ALPHA = 0.5f;
    private static final float MIN_SCALE = 0.85f;

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        if (f >= -1.0f || f <= 1.0f) {
            float f2 = view.getHeight();
            float f3 = Math.max((float)0.85f, (float)(1.0f - Math.abs((float)f)));
            float f4 = f2 * (1.0f - f3) / 2.0f;
            float f5 = (float)view.getWidth() * (1.0f - f3) / 2.0f;
            ViewHelper.setPivotY((View)view, (float)(0.5f * f2));
            if (f < 0.0f) {
                ViewHelper.setTranslationX((View)view, (float)(f5 - f4 / 2.0f));
            } else {
                ViewHelper.setTranslationX((View)view, (float)(-f5 + f4 / 2.0f));
            }
            ViewHelper.setScaleX((View)view, (float)f3);
            ViewHelper.setScaleY((View)view, (float)f3);
            ViewHelper.setAlpha((View)view, (float)(0.5f + 0.5f * ((f3 - 0.85f) / 0.14999998f)));
        }
    }
}

