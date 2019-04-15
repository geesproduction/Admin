/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.animation.Interpolator
 *  android.widget.Scroller
 */
package com.daimajia.slider.library.Tricks;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class FixedSpeedScroller
extends Scroller {
    private int mDuration;

    public FixedSpeedScroller(Context context) {
        super(context);
        this.mDuration = 1000;
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
        this.mDuration = 1000;
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator, int n) {
        super(context, interpolator);
        this.mDuration = n;
    }

    public void startScroll(int n, int n2, int n3, int n4) {
        super.startScroll(n, n2, n3, n4, this.mDuration);
    }

    public void startScroll(int n, int n2, int n3, int n4, int n5) {
        super.startScroll(n, n2, n3, n4, this.mDuration);
    }
}

