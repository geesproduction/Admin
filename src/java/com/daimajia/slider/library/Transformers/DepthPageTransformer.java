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

public class DepthPageTransformer
extends BaseTransformer {
    private static final float MIN_SCALE = 0.75f;

    @Override
    protected boolean isPagingEnabled() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        if (f <= 0.0f) {
            ViewHelper.setTranslationX((View)view, (float)0.0f);
            ViewHelper.setScaleX((View)view, (float)1.0f);
            ViewHelper.setScaleY((View)view, (float)1.0f);
            return;
        } else {
            if (!(f <= 1.0f)) return;
            {
                float f2 = 0.75f + 0.25f * (1.0f - Math.abs((float)f));
                ViewHelper.setAlpha((View)view, (float)(1.0f - f));
                ViewHelper.setPivotY((View)view, (float)(0.5f * (float)view.getHeight()));
                ViewHelper.setTranslationX((View)view, (float)((float)view.getWidth() * -f));
                ViewHelper.setScaleX((View)view, (float)f2);
                ViewHelper.setScaleY((View)view, (float)f2);
                return;
            }
        }
    }
}

