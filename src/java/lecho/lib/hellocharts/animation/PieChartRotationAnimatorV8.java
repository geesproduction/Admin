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
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.animation.PieChartRotationAnimator;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChartRotationAnimatorV8
implements PieChartRotationAnimator {
    private ChartAnimationListener animationListener;
    final PieChartView chart;
    final long duration;
    final Handler handler;
    final Interpolator interpolator;
    boolean isAnimationStarted;
    private final Runnable runnable;
    long start;
    private float startRotation;
    private float targetRotation;

    public PieChartRotationAnimatorV8(PieChartView pieChartView) {
        super(pieChartView, 200L);
    }

    public PieChartRotationAnimatorV8(PieChartView pieChartView, long l) {
        this.interpolator = new AccelerateDecelerateInterpolator();
        this.isAnimationStarted = false;
        this.startRotation = 0.0f;
        this.targetRotation = 0.0f;
        this.animationListener = new DummyChartAnimationListener();
        this.runnable = new Runnable(){

            public void run() {
                long l = SystemClock.uptimeMillis() - PieChartRotationAnimatorV8.this.start;
                if (l > PieChartRotationAnimatorV8.this.duration) {
                    PieChartRotationAnimatorV8.this.isAnimationStarted = false;
                    PieChartRotationAnimatorV8.this.handler.removeCallbacks(PieChartRotationAnimatorV8.this.runnable);
                    PieChartRotationAnimatorV8.this.chart.setChartRotation((int)PieChartRotationAnimatorV8.this.targetRotation, false);
                    PieChartRotationAnimatorV8.this.animationListener.onAnimationFinished();
                    return;
                }
                float f = Math.min((float)PieChartRotationAnimatorV8.this.interpolator.getInterpolation((float)l / (float)PieChartRotationAnimatorV8.this.duration), (float)1.0f);
                float f2 = (360.0f + (PieChartRotationAnimatorV8.this.startRotation + f * (PieChartRotationAnimatorV8.this.targetRotation - PieChartRotationAnimatorV8.this.startRotation)) % 360.0f) % 360.0f;
                PieChartRotationAnimatorV8.this.chart.setChartRotation((int)f2, false);
                PieChartRotationAnimatorV8.this.handler.postDelayed((Runnable)this, 16L);
            }
        };
        this.chart = pieChartView;
        this.duration = l;
        this.handler = new Handler();
    }

    @Override
    public void cancelAnimation() {
        this.isAnimationStarted = false;
        this.handler.removeCallbacks(this.runnable);
        this.chart.setChartRotation((int)this.targetRotation, false);
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
    public void startAnimation(float f, float f2) {
        this.startRotation = (360.0f + f % 360.0f) % 360.0f;
        this.targetRotation = (360.0f + f2 % 360.0f) % 360.0f;
        this.isAnimationStarted = true;
        this.animationListener.onAnimationStarted();
        this.start = SystemClock.uptimeMillis();
        this.handler.post(this.runnable);
    }

}

