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
import lecho.lib.hellocharts.animation.ChartDataAnimator;
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.view.Chart;

public class ChartDataAnimatorV8
implements ChartDataAnimator {
    private ChartAnimationListener animationListener;
    final Chart chart;
    long duration;
    final Handler handler;
    final Interpolator interpolator = new AccelerateDecelerateInterpolator();
    boolean isAnimationStarted = false;
    private final Runnable runnable;
    long start;

    public ChartDataAnimatorV8(Chart chart) {
        this.runnable = new Runnable(){

            public void run() {
                long l = SystemClock.uptimeMillis() - ChartDataAnimatorV8.this.start;
                if (l > ChartDataAnimatorV8.this.duration) {
                    ChartDataAnimatorV8.this.isAnimationStarted = false;
                    ChartDataAnimatorV8.this.handler.removeCallbacks(ChartDataAnimatorV8.this.runnable);
                    ChartDataAnimatorV8.this.chart.animationDataFinished();
                    return;
                }
                float f = Math.min((float)ChartDataAnimatorV8.this.interpolator.getInterpolation((float)l / (float)ChartDataAnimatorV8.this.duration), (float)1.0f);
                ChartDataAnimatorV8.this.chart.animationDataUpdate(f);
                ChartDataAnimatorV8.this.handler.postDelayed((Runnable)this, 16L);
            }
        };
        this.animationListener = new DummyChartAnimationListener();
        this.chart = chart;
        this.handler = new Handler();
    }

    @Override
    public void cancelAnimation() {
        this.isAnimationStarted = false;
        this.handler.removeCallbacks(this.runnable);
        this.chart.animationDataFinished();
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

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void startAnimation(long l) {
        this.duration = l >= 0L ? l : 500L;
        this.isAnimationStarted = true;
        this.animationListener.onAnimationStarted();
        this.start = SystemClock.uptimeMillis();
        this.handler.post(this.runnable);
    }

}

