/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  com.nineoldandroids.view.ViewHelper
 */
package com.daimajia.slider.library.Transformers;

import android.view.View;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.nineoldandroids.view.ViewHelper;

public class FlipHorizontalTransformer
extends BaseTransformer {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = 180.0f * f;
        float f3 = f2 > 90.0f || f2 < -90.0f ? 0.0f : 1.0f;
        ViewHelper.setAlpha((View)view, (float)f3);
        ViewHelper.setPivotY((View)view, (float)(0.5f * (float)view.getHeight()));
        ViewHelper.setPivotX((View)view, (float)(0.5f * (float)view.getWidth()));
        ViewHelper.setRotationY((View)view, (float)f2);
    }
}

