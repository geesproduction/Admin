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

public class AccordionTransformer
extends BaseTransformer {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = f < 0.0f ? 0.0f : (float)view.getWidth();
        ViewHelper.setPivotX((View)view, (float)f2);
        float f3 = f < 0.0f ? 1.0f + f : 1.0f - f;
        ViewHelper.setScaleX((View)view, (float)f3);
    }
}

