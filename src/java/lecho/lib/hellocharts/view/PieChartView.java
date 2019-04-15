/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.RectF
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.support.v4.view.ViewCompat
 *  android.util.AttributeSet
 *  android.view.View
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import java.util.List;
import lecho.lib.hellocharts.animation.PieChartRotationAnimator;
import lecho.lib.hellocharts.animation.PieChartRotationAnimatorV14;
import lecho.lib.hellocharts.animation.PieChartRotationAnimatorV8;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.gesture.PieChartTouchHandler;
import lecho.lib.hellocharts.listener.DummyPieChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.provider.PieChartDataProvider;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.renderer.PieChartRenderer;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.Chart;

public class PieChartView
extends AbstractChartView
implements PieChartDataProvider {
    private static final String TAG = "PieChartView";
    protected PieChartData data;
    protected PieChartOnValueSelectListener onValueTouchListener;
    protected PieChartRenderer pieChartRenderer;
    protected PieChartRotationAnimator rotationAnimator;

    public PieChartView(Context context) {
        super(context, null, 0);
    }

    public PieChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    /*
     * Enabled aggressive block sorting
     */
    public PieChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.onValueTouchListener = new DummyPieChartOnValueSelectListener();
        this.pieChartRenderer = new PieChartRenderer(context, (Chart)this, (PieChartDataProvider)this);
        this.touchHandler = new PieChartTouchHandler(context, (PieChartView)this);
        this.setChartRenderer(this.pieChartRenderer);
        this.rotationAnimator = Build.VERSION.SDK_INT < 14 ? new PieChartRotationAnimatorV8((PieChartView)this) : new PieChartRotationAnimatorV14((PieChartView)this);
        this.setPieChartData(PieChartData.generateDummyData());
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = this.chartRenderer.getSelectedValue();
        if (selectedValue.isSet()) {
            SliceValue sliceValue = (SliceValue)this.data.getValues().get(selectedValue.getFirstIndex());
            this.onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), sliceValue);
            return;
        }
        this.onValueTouchListener.onValueDeselected();
    }

    @Override
    public ChartData getChartData() {
        return this.data;
    }

    public int getChartRotation() {
        return this.pieChartRenderer.getChartRotation();
    }

    public float getCircleFillRatio() {
        return this.pieChartRenderer.getCircleFillRatio();
    }

    public RectF getCircleOval() {
        return this.pieChartRenderer.getCircleOval();
    }

    public PieChartOnValueSelectListener getOnValueTouchListener() {
        return this.onValueTouchListener;
    }

    @Override
    public PieChartData getPieChartData() {
        return this.data;
    }

    public SliceValue getValueForAngle(int n, SelectedValue selectedValue) {
        return this.pieChartRenderer.getValueForAngle(n, selectedValue);
    }

    public boolean isChartRotationEnabled() {
        if (this.touchHandler instanceof PieChartTouchHandler) {
            return ((PieChartTouchHandler)this.touchHandler).isRotationEnabled();
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setChartRotation(int n, boolean bl) {
        if (bl) {
            this.rotationAnimator.cancelAnimation();
            this.rotationAnimator.startAnimation(this.pieChartRenderer.getChartRotation(), n);
        } else {
            this.pieChartRenderer.setChartRotation(n);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setChartRotationEnabled(boolean bl) {
        if (this.touchHandler instanceof PieChartTouchHandler) {
            ((PieChartTouchHandler)this.touchHandler).setRotationEnabled(bl);
        }
    }

    public void setCircleFillRatio(float f) {
        this.pieChartRenderer.setCircleFillRatio(f);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setCircleOval(RectF rectF) {
        this.pieChartRenderer.setCircleOval(rectF);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    public void setOnValueTouchListener(PieChartOnValueSelectListener pieChartOnValueSelectListener) {
        if (pieChartOnValueSelectListener != null) {
            this.onValueTouchListener = pieChartOnValueSelectListener;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setPieChartData(PieChartData pieChartData) {
        this.data = pieChartData == null ? PieChartData.generateDummyData() : pieChartData;
        super.onChartDataChange();
    }
}

