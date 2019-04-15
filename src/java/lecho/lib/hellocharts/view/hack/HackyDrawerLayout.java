/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.widget.DrawerLayout
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  java.lang.Exception
 */
package lecho.lib.hellocharts.view.hack;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HackyDrawerLayout
extends DrawerLayout {
    public HackyDrawerLayout(Context context) {
        super(context);
    }

    public HackyDrawerLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HackyDrawerLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
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

