/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.animation;

import lecho.lib.hellocharts.animation.ChartAnimationListener;

public interface ChartDataAnimator {
    public static final long DEFAULT_ANIMATION_DURATION = 500L;

    public void cancelAnimation();

    public boolean isAnimationStarted();

    public void setChartAnimationListener(ChartAnimationListener var1);

    public void startAnimation(long var1);
}

