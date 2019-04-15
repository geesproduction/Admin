/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.support.v4.view.ViewCompat
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewParent
 *  java.lang.Math
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import lecho.lib.hellocharts.animation.ChartAnimationListener;
import lecho.lib.hellocharts.animation.ChartDataAnimator;
import lecho.lib.hellocharts.animation.ChartDataAnimatorV14;
import lecho.lib.hellocharts.animation.ChartDataAnimatorV8;
import lecho.lib.hellocharts.animation.ChartViewportAnimator;
import lecho.lib.hellocharts.animation.ChartViewportAnimatorV14;
import lecho.lib.hellocharts.animation.ChartViewportAnimatorV8;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.AxesRenderer;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;

public abstract class AbstractChartView
extends View
implements Chart {
    protected AxesRenderer axesRenderer;
    protected ChartComputator chartComputator;
    protected ChartRenderer chartRenderer;
    protected ContainerScrollType containerScrollType;
    protected ChartDataAnimator dataAnimator;
    protected boolean isContainerScrollEnabled;
    protected boolean isInteractive;
    protected ChartTouchHandler touchHandler;
    protected ChartViewportAnimator viewportAnimator;

    public AbstractChartView(Context context) {
        super(context, null, 0);
    }

    public AbstractChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public AbstractChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.isInteractive = true;
        this.isContainerScrollEnabled = false;
        this.chartComputator = new ChartComputator();
        this.touchHandler = new ChartTouchHandler(context, (Chart)this);
        this.axesRenderer = new AxesRenderer(context, (Chart)this);
        if (Build.VERSION.SDK_INT < 14) {
            this.dataAnimator = new ChartDataAnimatorV8((Chart)this);
            this.viewportAnimator = new ChartViewportAnimatorV8((Chart)this);
            return;
        }
        this.viewportAnimator = new ChartViewportAnimatorV14((Chart)this);
        this.dataAnimator = new ChartDataAnimatorV14((Chart)this);
    }

    private Viewport computeScrollViewport(float f, float f2) {
        Viewport viewport = this.getMaximumViewport();
        Viewport viewport2 = this.getCurrentViewport();
        Viewport viewport3 = new Viewport(viewport2);
        if (viewport.contains(f, f2)) {
            float f3 = viewport2.width();
            float f4 = viewport2.height();
            float f5 = f3 / 2.0f;
            float f6 = f4 / 2.0f;
            float f7 = f - f5;
            float f8 = f2 + f6;
            float f9 = Math.max((float)viewport.left, (float)Math.min((float)f7, (float)(viewport.right - f3)));
            float f10 = Math.max((float)(f4 + viewport.bottom), (float)Math.min((float)f8, (float)viewport.top));
            viewport3.set(f9, f10, f9 + f3, f10 - f4);
        }
        return viewport3;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Viewport computeZoomViewport(float f, float f2, float f3) {
        Viewport viewport = this.getMaximumViewport();
        Viewport viewport2 = new Viewport(this.getMaximumViewport());
        if (!viewport.contains(f, f2)) return viewport2;
        {
            ZoomType zoomType;
            if (f3 < 1.0f) {
                f3 = 1.0f;
            } else if (f3 > this.getMaxZoom()) {
                f3 = this.getMaxZoom();
            }
            float f4 = viewport2.width() / f3;
            float f5 = viewport2.height() / f3;
            float f6 = f4 / 2.0f;
            float f7 = f5 / 2.0f;
            float f8 = f - f6;
            float f9 = f + f6;
            float f10 = f2 + f7;
            float f11 = f2 - f7;
            if (f8 < viewport.left) {
                f8 = viewport.left;
                f9 = f8 + f4;
            } else if (f9 > viewport.right) {
                f9 = viewport.right;
                f8 = f9 - f4;
            }
            if (f10 > viewport.top) {
                f10 = viewport.top;
                f11 = f10 - f5;
            } else if (f11 < viewport.bottom) {
                f11 = viewport.bottom;
                f10 = f11 + f5;
            }
            if (ZoomType.HORIZONTAL_AND_VERTICAL == (zoomType = this.getZoomType())) {
                viewport2.set(f8, f10, f9, f11);
                return viewport2;
            } else {
                if (ZoomType.HORIZONTAL == zoomType) {
                    viewport2.left = f8;
                    viewport2.right = f9;
                    return viewport2;
                }
                if (ZoomType.VERTICAL != zoomType) return viewport2;
                {
                    viewport2.top = f10;
                    viewport2.bottom = f11;
                    return viewport2;
                }
            }
        }
    }

    @Override
    public void animationDataFinished() {
        this.getChartData().finish();
        this.chartRenderer.onChartViewportChanged();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void animationDataUpdate(float f) {
        this.getChartData().update(f);
        this.chartRenderer.onChartViewportChanged();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void cancelDataAnimation() {
        this.dataAnimator.cancelAnimation();
    }

    public void computeScroll() {
        super.computeScroll();
        if (this.isInteractive && this.touchHandler.computeScroll()) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
    }

    @Override
    public AxesRenderer getAxesRenderer() {
        return this.axesRenderer;
    }

    @Override
    public ChartComputator getChartComputator() {
        return this.chartComputator;
    }

    @Override
    public ChartRenderer getChartRenderer() {
        return this.chartRenderer;
    }

    @Override
    public Viewport getCurrentViewport() {
        return this.getChartRenderer().getCurrentViewport();
    }

    @Override
    public float getMaxZoom() {
        return this.chartComputator.getMaxZoom();
    }

    @Override
    public Viewport getMaximumViewport() {
        return this.chartRenderer.getMaximumViewport();
    }

    @Override
    public SelectedValue getSelectedValue() {
        return this.chartRenderer.getSelectedValue();
    }

    @Override
    public ChartTouchHandler getTouchHandler() {
        return this.touchHandler;
    }

    @Override
    public float getZoomLevel() {
        Viewport viewport = this.getMaximumViewport();
        Viewport viewport2 = this.getCurrentViewport();
        return Math.max((float)(viewport.width() / viewport2.width()), (float)(viewport.height() / viewport2.height()));
    }

    @Override
    public ZoomType getZoomType() {
        return this.touchHandler.getZoomType();
    }

    @Override
    public boolean isContainerScrollEnabled() {
        return this.isContainerScrollEnabled;
    }

    @Override
    public boolean isInteractive() {
        return this.isInteractive;
    }

    @Override
    public boolean isScrollEnabled() {
        return this.touchHandler.isScrollEnabled();
    }

    @Override
    public boolean isValueSelectionEnabled() {
        return this.touchHandler.isValueSelectionEnabled();
    }

    @Override
    public boolean isValueTouchEnabled() {
        return this.touchHandler.isValueTouchEnabled();
    }

    @Override
    public boolean isViewportCalculationEnabled() {
        return this.chartRenderer.isViewportCalculationEnabled();
    }

    @Override
    public boolean isZoomEnabled() {
        return this.touchHandler.isZoomEnabled();
    }

    @Override
    public void moveTo(float f, float f2) {
        this.setCurrentViewport(AbstractChartView.super.computeScrollViewport(f, f2));
    }

    @Override
    public void moveToWithAnimation(float f, float f2) {
        this.setCurrentViewportWithAnimation(AbstractChartView.super.computeScrollViewport(f, f2));
    }

    protected void onChartDataChange() {
        this.chartComputator.resetContentRect();
        this.chartRenderer.onChartDataChanged();
        this.axesRenderer.onChartDataChanged();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isEnabled()) {
            this.axesRenderer.drawInBackground(canvas);
            int n = canvas.save();
            canvas.clipRect(this.chartComputator.getContentRectMinusAllMargins());
            this.chartRenderer.draw(canvas);
            canvas.restoreToCount(n);
            this.chartRenderer.drawUnclipped(canvas);
            this.axesRenderer.drawInForeground(canvas);
            return;
        }
        canvas.drawColor(ChartUtils.DEFAULT_COLOR);
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.chartComputator.setContentRect(this.getWidth(), this.getHeight(), this.getPaddingLeft(), this.getPaddingTop(), this.getPaddingRight(), this.getPaddingBottom());
        this.chartRenderer.onChartSizeChanged();
        this.axesRenderer.onChartSizeChanged();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        super.onTouchEvent(motionEvent);
        if (!this.isInteractive) {
            return false;
        }
        boolean bl = this.isContainerScrollEnabled ? this.touchHandler.handleTouchEvent(motionEvent, this.getParent(), this.containerScrollType) : this.touchHandler.handleTouchEvent(motionEvent);
        if (bl) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
        return true;
    }

    protected void resetRendererAndTouchHandler() {
        this.chartRenderer.resetRenderer();
        this.axesRenderer.resetRenderer();
        this.touchHandler.resetTouchHandler();
    }

    @Override
    public void resetViewports() {
        this.chartRenderer.setMaximumViewport(null);
        this.chartRenderer.setCurrentViewport(null);
    }

    @Override
    public void selectValue(SelectedValue selectedValue) {
        this.chartRenderer.selectValue(selectedValue);
        this.callTouchListener();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setChartRenderer(ChartRenderer chartRenderer) {
        this.chartRenderer = chartRenderer;
        this.resetRendererAndTouchHandler();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setContainerScrollEnabled(boolean bl, ContainerScrollType containerScrollType) {
        this.isContainerScrollEnabled = bl;
        this.containerScrollType = containerScrollType;
    }

    @Override
    public void setCurrentViewport(Viewport viewport) {
        if (viewport != null) {
            this.chartRenderer.setCurrentViewport(viewport);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setCurrentViewportWithAnimation(Viewport viewport) {
        if (viewport != null) {
            this.viewportAnimator.cancelAnimation();
            this.viewportAnimator.startAnimation(this.getCurrentViewport(), viewport);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setCurrentViewportWithAnimation(Viewport viewport, long l) {
        if (viewport != null) {
            this.viewportAnimator.cancelAnimation();
            this.viewportAnimator.startAnimation(this.getCurrentViewport(), viewport, l);
        }
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setDataAnimationListener(ChartAnimationListener chartAnimationListener) {
        this.dataAnimator.setChartAnimationListener(chartAnimationListener);
    }

    @Override
    public void setInteractive(boolean bl) {
        this.isInteractive = bl;
    }

    @Override
    public void setMaxZoom(float f) {
        this.chartComputator.setMaxZoom(f);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setMaximumViewport(Viewport viewport) {
        this.chartRenderer.setMaximumViewport(viewport);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    @Override
    public void setScrollEnabled(boolean bl) {
        this.touchHandler.setScrollEnabled(bl);
    }

    @Override
    public void setValueSelectionEnabled(boolean bl) {
        this.touchHandler.setValueSelectionEnabled(bl);
    }

    @Override
    public void setValueTouchEnabled(boolean bl) {
        this.touchHandler.setValueTouchEnabled(bl);
    }

    @Override
    public void setViewportAnimationListener(ChartAnimationListener chartAnimationListener) {
        this.viewportAnimator.setChartAnimationListener(chartAnimationListener);
    }

    @Override
    public void setViewportCalculationEnabled(boolean bl) {
        this.chartRenderer.setViewportCalculationEnabled(bl);
    }

    @Override
    public void setViewportChangeListener(ViewportChangeListener viewportChangeListener) {
        this.chartComputator.setViewportChangeListener(viewportChangeListener);
    }

    @Override
    public void setZoomEnabled(boolean bl) {
        this.touchHandler.setZoomEnabled(bl);
    }

    @Override
    public void setZoomLevel(float f, float f2, float f3) {
        this.setCurrentViewport(AbstractChartView.super.computeZoomViewport(f, f2, f3));
    }

    @Override
    public void setZoomLevelWithAnimation(float f, float f2, float f3) {
        this.setCurrentViewportWithAnimation(AbstractChartView.super.computeZoomViewport(f, f2, f3));
    }

    @Override
    public void setZoomType(ZoomType zoomType) {
        this.touchHandler.setZoomType(zoomType);
    }

    @Override
    public void startDataAnimation() {
        this.dataAnimator.startAnimation(Long.MIN_VALUE);
    }

    @Override
    public void startDataAnimation(long l) {
        this.dataAnimator.startAnimation(l);
    }
}

