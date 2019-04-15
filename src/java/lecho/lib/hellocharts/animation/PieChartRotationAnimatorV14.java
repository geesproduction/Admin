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
import lecho.lib.hellocharts.animation.DummyChartAnimationListener;
import lecho.lib.hellocharts.animation.PieChartRotationAnimator;
import lecho.lib.hellocharts.view.PieChartView;

@SuppressLint(value={"NewApi"})
public class PieChartRotationAnimatorV14
implements PieChartRotationAnimator,
Animator.AnimatorListener,
ValueAnimator.AnimatorUpdateListener {
    private ChartAnimationListener animationListener;
    private ValueAnimator animator;
    private final PieChartView chart;
    private float startRotation;
    private float targetRotation;

    public PieChartRotationAnimatorV14(PieChartView pieChartView) {
        super(pieChartView, 200L);
    }

    public PieChartRotationAnimatorV14(PieChartView pieChartView, long l) {
        this.startRotation = 0.0f;
        this.targetRotation = 0.0f;
        this.animationListener = new DummyChartAnimationListener();
        this.chart = pieChartView;
        this.animator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
        this.animator.setDuration(l);
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
        this.chart.setChartRotation((int)this.targetRotation, false);
        this.animationListener.onAnimationFinished();
    }

    public void onAnimationRepeat(Animator animator) {
    }

    public void onAnimationStart(Animator animator) {
        this.animationListener.onAnimationStarted();
    }

    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = valueAnimator.getAnimatedFraction();
        float f2 = (360.0f + (this.startRotation + f * (this.targetRotation - this.startRotation)) % 360.0f) % 360.0f;
        this.chart.setChartRotation((int)f2, false);
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
        this.animator.start();
    }
}

