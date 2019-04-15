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

public class StackTransformer
extends BaseTransformer {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = f FCMPG 0.0f;
        float f3 = 0.0f;
        if (f2 >= 0) {
            f3 = f * (float)(-view.getWidth());
        }
        ViewHelper.setTranslationX((View)view, (float)f3);
    }
}

