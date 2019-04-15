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

public class ZoomInTransformer
extends BaseTransformer {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = f < 0.0f ? f + 1.0f : Math.abs((float)(1.0f - f));
        ViewHelper.setScaleX((View)view, (float)f2);
        ViewHelper.setScaleY((View)view, (float)f2);
        ViewHelper.setPivotX((View)view, (float)(0.5f * (float)view.getWidth()));
        ViewHelper.setPivotY((View)view, (float)(0.5f * (float)view.getHeight()));
        float f3 = f FCMPG -1.0f;
        float f4 = 0.0f;
        if (f3 >= 0) {
            float f5 = f FCMPL 1.0f;
            f4 = 0.0f;
            if (f5 <= 0) {
                f4 = 1.0f - (f2 - 1.0f);
            }
        }
        ViewHelper.setAlpha((View)view, (float)f4);
    }
}

