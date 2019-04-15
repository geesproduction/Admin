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
import lecho.lib.hellocharts.animation.ChartViewportAnimator;
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.Chart;

@SuppressLint(value={"NewApi"})
public class ChartViewportAnimatorV14
implements ChartViewportAnimator,
Animator.AnimatorListener,
ValueAnimator.AnimatorUpdateListener {
    private ChartAnimationListener animationListener = new DummyChartAnimationListener();
    private ValueAnimator animator;
    private final Chart chart;
    private Viewport newViewport = new Viewport();
    private Viewport startViewport = new Viewport();
    private Viewport targetViewport = new Viewport();

    public ChartViewportAnimatorV14(Chart chart) {
        this.chart = chart;
        this.animator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.animator.addListener((Animator.AnimatorListener)this);
        this.animator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)this);
        this.animator.setDuration(300L);
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
        this.chart.setCurrentViewport(this.targetViewport);
        this.animationListener.onAnimationFinished();
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
        this.animationListener.onAnimationStarted();
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = valueAnimator.getAnimatedFraction();
        float f2 = f * (this.targetViewport.left - this.startViewport.left);
        float f3 = f * (this.targetViewport.top - this.startViewport.top);
        float f4 = f * (this.targetViewport.right - this.startViewport.right);
        float f5 = f * (this.targetViewport.bottom - this.startViewport.bottom);
        this.newViewport.set(f2 + this.startViewport.left, f3 + this.startViewport.top, f4 + this.startViewport.right, f5 + this.startViewport.bottom);
        this.chart.setCurrentViewport(this.newViewport);
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
        this.animator.setDuration(300L);
        this.animator.start();
    }

    @Override
    public void startAnimation(Viewport viewport, Viewport viewport2, long l) {
        this.startViewport.set(viewport);
        this.targetViewport.set(viewport2);
        this.animator.setDuration(l);
        this.animator.start();
    }
}

