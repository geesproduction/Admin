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

public class CubeInTransformer
extends BaseTransformer {
    @Override
    public boolean isPagingEnabled() {
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = f > 0.0f ? 0.0f : (float)view.getWidth();
        ViewHelper.setPivotX((View)view, (float)f2);
        ViewHelper.setPivotY((View)view, (float)0.0f);
        ViewHelper.setRotation((View)view, (float)(-90.0f * f));
    }
}

