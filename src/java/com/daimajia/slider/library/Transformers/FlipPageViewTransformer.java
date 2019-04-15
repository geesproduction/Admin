/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.View
 *  android.view.ViewParent
 *  com.nineoldandroids.view.ViewHelper
 *  java.lang.Math
 */
package com.daimajia.slider.library.Transformers;

import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.nineoldandroids.view.ViewHelper;

public class FlipPageViewTransformer
extends BaseTransformer {
    private void setRotation(View view, float f, float f2) {
        if (f > 0.0f) {
            ViewHelper.setRotationY((View)view, (float)(-180.0f * (1.0f + f2)));
            return;
        }
        ViewHelper.setRotationY((View)view, (float)(180.0f * (1.0f + f2)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSize(View view, float f, float f2) {
        float f3 = f != 0.0f && f != 1.0f ? f2 : 1.0f;
        ViewHelper.setScaleX((View)view, (float)f3);
        if (f == 0.0f || f == 1.0f) {
            f2 = 1.0f;
        }
        ViewHelper.setScaleY((View)view, (float)f2);
    }

    private void setTranslation(View view) {
        ViewHelper.setTranslationX((View)view, (float)(((ViewPagerEx)view.getParent()).getScrollX() - view.getLeft()));
    }

    private void setVisibility(View view, float f) {
        if ((double)f < 0.5 && (double)f > -0.5) {
            view.setVisibility(0);
            return;
        }
        view.setVisibility(4);
    }

    @Override
    protected void onTransform(View view, float f) {
        float f2 = 1.0f - Math.abs((float)f);
        if (Build.VERSION.SDK_INT >= 13) {
            view.setCameraDistance(12000.0f);
        }
        FlipPageViewTransformer.super.setVisibility(view, f);
        FlipPageViewTransformer.super.setTranslation(view);
        FlipPageViewTransformer.super.setSize(view, f, f2);
        FlipPageViewTransformer.super.setRotation(view, f, f2);
    }
}

