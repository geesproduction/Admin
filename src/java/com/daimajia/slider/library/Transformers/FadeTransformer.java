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

public class FadeTransformer
extends BaseTransformer {
    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        if (f < -1.0f || f > 1.0f) {
            ViewHelper.setAlpha((View)view, (float)0.6f);
            return;
        } else {
            if (f <= 0.0f || f <= 1.0f) {
                float f2 = f <= 0.0f ? f + 1.0f : 1.0f - f;
                ViewHelper.setAlpha((View)view, (float)f2);
                return;
            }
            if (f != 0.0f) return;
            {
                ViewHelper.setAlpha((View)view, (float)1.0f);
                return;
            }
        }
    }
}

