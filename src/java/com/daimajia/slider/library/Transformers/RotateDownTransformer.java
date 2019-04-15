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

public class RotateDownTransformer
extends BaseTransformer {
    private static final float ROT_MOD = -15.0f;

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

    @Override
    protected void onTransform(View view, float f) {
        float f2 = view.getWidth();
        float f3 = view.getHeight();
        float f4 = -1.25f * (-15.0f * f);
        ViewHelper.setPivotX((View)view, (float)(0.5f * f2));
        ViewHelper.setPivotY((View)view, (float)f3);
        ViewHelper.setRotation((View)view, (float)f4);
    }
}

