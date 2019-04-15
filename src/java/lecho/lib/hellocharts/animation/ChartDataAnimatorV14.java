/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.annotation.SuppressLint
 *  java.lang.Object
 */
package lecho.lib.hellocharts.animation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.animation.ChartDataAnimator;
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.view.Chart;

@SuppressLint(value={"NewApi"})
public class ChartDataAnimatorV14
implements ChartDataAnimator,
Animator.AnimatorListener,
ValueAnimator.AnimatorUpdateListener {
    private ChartAnimationListener animationListener = new DummyChartAnimationListener();
    private ValueAnimator animator;
    private final Chart chart;

    public ChartDataAnimatorV14(Chart chart) {
        this.chart = chart;
        this.animator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.animator.addListener((Animator.AnimatorListener)this);
        this.animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)this);
    }

    @Override
    public void cancelAnimation() {
        this.animator.cancel();
    }

    @Override
    public boolean isAnimationStarted() {
        return this.animator.isStarted();
    }

    public void onAnimationCancel(Animator animator) {
    }

    public void onAnimationEnd(Animator animator) {
        this.chart.animationDataFinished();
        this.animationListener.onAnimationFinished();
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
        this.animationListener.onAnimationStarted();
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.chart.animationDataUpdate(valueAnimator.getAnimatedFraction());
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
        if (l >= 0L) {
            this.animator.setDuration(l);
        } else {
            this.animator.setDuration(500L);
        }
        this.animator.start();
    }
}

