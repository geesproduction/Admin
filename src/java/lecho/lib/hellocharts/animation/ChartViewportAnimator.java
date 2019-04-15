/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.animation;

import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.model.Viewport;

public interface ChartViewportAnimator {
    public static final int FAST_ANIMATION_DURATION = 300;

    public void cancelAnimation();

    public boolean isAnimationStarted();

    public void setChartAnimationListener(ChartAnimationListener var1);

    public void startAnimation(Viewport var1, Viewport var2);

    public void startAnimation(Viewport var1, Viewport var2, long var3);
}

