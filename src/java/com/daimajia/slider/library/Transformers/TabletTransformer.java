/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Camera
 *  android.graphics.Matrix
 *  android.view.View
 *  com.nineoldandroids.view.ViewHelper
 *  java.lang.Math
 */
package com.daimajia.slider.library.Transformers;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import com.daimajia.slider.library.Transformers.BaseTransformer;
import com.nineoldandroids.view.ViewHelper;

public class TabletTransformer
extends BaseTransformer {
    private static final Camera OFFSET_CAMERA;
    private static final Matrix OFFSET_MATRIX;
    private static final float[] OFFSET_TEMP_FLOAT;

    static {
        OFFSET_MATRIX = new Matrix();
        OFFSET_CAMERA = new Camera();
        OFFSET_TEMP_FLOAT = new float[2];
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected static final float getOffsetXForRotation(float f, int n, int n2) {
        float f2;
        OFFSET_MATRIX.reset();
        OFFSET_CAMERA.save();
        OFFSET_CAMERA.rotateY(Math.abs((float)f));
        OFFSET_CAMERA.getMatrix(OFFSET_MATRIX);
        OFFSET_CAMERA.restore();
        OFFSET_MATRIX.preTranslate(0.5f * (float)(-n), 0.5f * (float)(-n2));
        OFFSET_MATRIX.postTranslate(0.5f * (float)n, 0.5f * (float)n2);
        TabletTransformer.OFFSET_TEMP_FLOAT[0] = n;
        TabletTransformer.OFFSET_TEMP_FLOAT[1] = n2;
        OFFSET_MATRIX.mapPoints(OFFSET_TEMP_FLOAT);
        float f3 = (float)n - OFFSET_TEMP_FLOAT[0];
        if (f > 0.0f) {
            f2 = 1.0f;
            do {
                return f2 * f3;
                break;
            } while (true);
        }
        f2 = -1.0f;
        return f2 * f3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onTransform(View view, float f) {
        float f2 = f < 0.0f ? 30.0f : -30.0f;
        float f3 = f2 * Math.abs((float)f);
        ViewHelper.setTranslationX((View)view, (float)TabletTransformer.getOffsetXForRotation(f3, view.getWidth(), view.getHeight()));
        ViewHelper.setPivotX((View)view, (float)(0.5f * (float)view.getWidth()));
        ViewHelper.setPivotY((View)view, (float)0.0f);
        ViewHelper.setRotationY((View)view, (float)f3);
    }
}

