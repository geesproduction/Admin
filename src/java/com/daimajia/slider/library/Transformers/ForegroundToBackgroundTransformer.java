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

public class ForegroundToBackgroundTransformer
extends BaseTransformer {
    private static final float min(float f, float f2) {
        if (f < f2) {
            return f2;
        }
        return f;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = 1.0f;
        float f3 = view.getHeight();
        float f4 = view.getWidth();
        if (!(f > 0.0f)) {
            f2 = Math.abs((float)(f2 + f));
        }
        float f5 = ForegroundToBackgroundTransformer.min(f2, 0.5f);
        ViewHelper.setScaleX((View)view, (float)f5);
        ViewHelper.setScaleY((View)view, (float)f5);
        ViewHelper.setPivotX((View)view, (float)(f4 * 0.5f));
        ViewHelper.setPivotY((View)view, (float)(f3 * 0.5f));
        float f6 = f > 0.0f ? f4 * f : 0.25f * (f * -f4);
        ViewHelper.setTranslationX((View)view, (float)f6);
    }
}

