/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.animation;

import lecho.lib.hellocharts.animation.ChartAnimationListener;

public interface PieChartRotationAnimator {
    public static final int FAST_ANIMATION_DURATION = 200;

    public void cancelAnimation();

    public boolean isAnimationStarted();

    public void setChartAnimationListener(ChartAnimationListener var1);

    public void startAnimation(float var1, float var2);
}

