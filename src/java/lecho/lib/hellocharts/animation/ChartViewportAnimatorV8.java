/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.SystemClock
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.Interpolator
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 */
package lecho.lib.hellocharts.animation;

import android.os.Handler;
import android.os.SystemClock;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.animation.ChartViewportAnimator;
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.Chart;

public class ChartViewportAnimatorV8
implements ChartViewportAnimator {
    private ChartAnimationListener animationListener = new DummyChartAnimationListener();
    final Chart chart;
    private long duration;
    final Handler handler;
    final Interpolator interpolator = new AccelerateDecelerateInterpolator();
    boolean isAnimationStarted = false;
    private Viewport newViewport = new Viewport();
    private final Runnable runnable;
    long start;
    private Viewport startViewport = new Viewport();
    private Viewport targetViewport = new Viewport();

    public ChartViewportAnimatorV8(Chart chart) {
        this.runnable = new Runnable(){

            public void run() {
                long l = SystemClock.uptimeMillis() - ChartViewportAnimatorV8.this.start;
                if (l > ChartViewportAnimatorV8.this.duration) {
                    ChartViewportAnimatorV8.this.isAnimationStarted = false;
                    ChartViewportAnimatorV8.this.handler.removeCallbacks(ChartViewportAnimatorV8.this.runnable);
                    ChartViewportAnimatorV8.this.chart.setCurrentViewport(ChartViewportAnimatorV8.this.targetViewport);
                    ChartViewportAnimatorV8.this.animationListener.onAnimationFinished();
                    return;
                }
                float f = Math.min((float)ChartViewportAnimatorV8.this.interpolator.getInterpolation((float)l / (float)ChartViewportAnimatorV8.this.duration), (float)1.0f);
                float f2 = f * (ChartViewportAnimatorV8.access$200((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).left - ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).left);
                float f3 = f * (ChartViewportAnimatorV8.access$200((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).top - ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).top);
                float f4 = f * (ChartViewportAnimatorV8.access$200((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).right - ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).right);
                float f5 = f * (ChartViewportAnimatorV8.access$200((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).bottom - ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).bottom);
                ChartViewportAnimatorV8.this.newViewport.set(f2 + ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).left, f3 + ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).top, f4 + ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).right, f5 + ChartViewportAnimatorV8.access$400((ChartViewportAnimatorV8)ChartViewportAnimatorV8.this).bottom);
                ChartViewportAnimatorV8.this.chart.setCurrentViewport(ChartViewportAnimatorV8.this.newViewport);
                ChartViewportAnimatorV8.this.handler.postDelayed((Runnable)this, 16L);
            }
        };
        this.chart = chart;
        this.duration = 300L;
        this.handler = new Handler();
    }

    static /* synthetic */ Viewport access$400(ChartViewportAnimatorV8 chartViewportAnimatorV8) {
        return chartViewportAnimatorV8.startViewport;
    }

    @Override
    public void cancelAnimation() {
        this.isAnimationStarted = false;
        this.handler.removeCallbacks(this.runnable);
        this.chart.setCurrentViewport(this.targetViewport);
        this.animationListener.onAnimationFinished();
    }

    @Override
    public boolean isAnimationStarted() {
        return this.isAnimationStarted;
    }

    @Override
    public void setChartAnimationListener(ChartAnimationListener chartAnimationListener) {
        if (chartAnimationListener == null) {
            this.animationListener = new DummyChartAnimationListener();
            return;
        }
        this.animationListener = chartAnimationListener;
    }

    @Override
    public void startAnimation(Viewport viewport, Viewport viewport2) {
        this.startViewport.set(viewport);
        this.targetViewport.set(viewport2);
        this.duration = 300L;
        this.isAnimationStarted = true;
        this.animationListener.onAnimationStarted();
        this.start = SystemClock.uptimeMillis();
        this.handler.post(this.runnable);
    }

    @Override
    public void startAnimation(Viewport viewport, Viewport viewport2, long l) {
        this.startViewport.set(viewport);
        this.targetViewport.set(viewport2);
        this.duration = l;
        this.isAnimationStarted = true;
        this.animationListener.onAnimationStarted();
        this.start = SystemClock.uptimeMillis();
        this.handler.post(this.runnable);
    }

}

