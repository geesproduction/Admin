/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Color
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public abstract class ChartUtils {
    public static final int[] COLORS;
    public static final int COLOR_BLUE = 0;
    public static final int COLOR_GREEN = 0;
    private static int COLOR_INDEX = 0;
    public static final int COLOR_ORANGE = 0;
    public static final int COLOR_RED = 0;
    public static final int COLOR_VIOLET = 0;
    private static final float DARKEN_INTENSITY = 0.9f;
    private static final float DARKEN_SATURATION = 1.1f;
    public static final int DEFAULT_COLOR;
    public static final int DEFAULT_DARKEN_COLOR;

    static {
        DEFAULT_COLOR = Color.parseColor((String)"#DFDFDF");
        DEFAULT_DARKEN_COLOR = Color.parseColor((String)"#DDDDDD");
        COLOR_BLUE = Color.parseColor((String)"#33B5E5");
        COLOR_VIOLET = Color.parseColor((String)"#AA66CC");
        COLOR_GREEN = Color.parseColor((String)"#99CC00");
        COLOR_ORANGE = Color.parseColor((String)"#FFBB33");
        COLOR_RED = Color.parseColor((String)"#FF4444");
        int[] arrn = new int[]{COLOR_BLUE, COLOR_VIOLET, COLOR_GREEN, COLOR_ORANGE, COLOR_RED};
        COLORS = arrn;
        COLOR_INDEX = 0;
    }

    public static int darkenColor(int n) {
        float[] arrf = new float[3];
        int n2 = Color.alpha((int)n);
        Color.colorToHSV((int)n, (float[])arrf);
        arrf[1] = Math.min((float)(1.1f * arrf[1]), (float)1.0f);
        arrf[2] = 0.9f * arrf[2];
        int n3 = Color.HSVToColor((float[])arrf);
        return Color.argb((int)n2, (int)Color.red((int)n3), (int)Color.green((int)n3), (int)Color.blue((int)n3));
    }

    public static int dp2px(float f, int n) {
        if (n == 0) {
            return 0;
        }
        return (int)(0.5f + f * (float)n);
    }

    public static int mm2px(Context context, int n) {
        return (int)(0.5f + TypedValue.applyDimension((int)5, (float)n, (DisplayMetrics)context.getResources().getDisplayMetrics()));
    }

    public static final int nextColor() {
        if (COLOR_INDEX >= COLORS.length) {
            COLOR_INDEX = 0;
        }
        int[] arrn = COLORS;
        int n = COLOR_INDEX;
        COLOR_INDEX = n + 1;
        return arrn[n];
    }

    public static final int pickColor() {
        return COLORS[(int)Math.round((double)(Math.random() * (double)(-1 + COLORS.length)))];
    }

    public static int px2dp(float f, int n) {
        return (int)Math.ceil((double)((float)n / f));
    }

    public static int px2sp(float f, int n) {
        return (int)Math.ceil((double)((float)n / f));
    }

    public static int sp2px(float f, int n) {
        if (n == 0) {
            return 0;
        }
        return (int)(0.5f + f * (float)n);
    }
}

