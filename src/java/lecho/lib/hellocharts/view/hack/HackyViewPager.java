/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.view.ViewPager
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  java.lang.Exception
 */
package lecho.lib.hellocharts.view.hack;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyViewPager
extends ViewPager {
    public HackyViewPager(Context context) {
        super(context);
    }

    public HackyViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            boolean bl = super.onInterceptTouchEvent(motionEvent);
            return bl;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}

