/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.ViewParent
 *  java.lang.Float
 *  java.lang.Object
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ViewParent;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.gesture.ChartScroller;
import lecho.lib.hellocharts.gesture.ChartZoomer;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.view.Chart;

public class ChartTouchHandler {
    protected Chart chart;
    protected ChartScroller chartScroller;
    protected ChartZoomer chartZoomer;
    protected ChartComputator computator;
    protected ContainerScrollType containerScrollType;
    protected GestureDetector gestureDetector;
    protected boolean isScrollEnabled = true;
    protected boolean isValueSelectionEnabled = false;
    protected boolean isValueTouchEnabled = true;
    protected boolean isZoomEnabled = true;
    protected SelectedValue oldSelectedValue = new SelectedValue();
    protected ChartRenderer renderer;
    protected ScaleGestureDetector scaleGestureDetector;
    protected SelectedValue selectedValue = new SelectedValue();
    protected SelectedValue selectionModeOldValue = new SelectedValue();
    protected ViewParent viewParent;

    public ChartTouchHandler(Context context, Chart chart) {
        this.chart = chart;
        this.computator = chart.getChartComputator();
        this.renderer = chart.getChartRenderer();
        this.gestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)(ChartTouchHandler)this.new ChartGestureListener());
        this.scaleGestureDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)(ChartTouchHandler)this.new ChartScaleGestureListener());
        this.chartScroller = new ChartScroller(context);
        this.chartZoomer = new ChartZoomer(context, ZoomType.HORIZONTAL_AND_VERTICAL);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void allowParentInterceptTouchEvent(ChartScroller.ScrollResult scrollResult) {
        if (this.viewParent == null) return;
        {
            if (ContainerScrollType.HORIZONTAL == this.containerScrollType && !scrollResult.canScrollX && !this.scaleGestureDetector.isInProgress()) {
                this.viewParent.requestDisallowInterceptTouchEvent(false);
                return;
            } else {
                if (ContainerScrollType.VERTICAL != this.containerScrollType || scrollResult.canScrollY || this.scaleGestureDetector.isInProgress()) return;
                {
                    this.viewParent.requestDisallowInterceptTouchEvent(false);
                    return;
                }
            }
        }
    }

    private boolean checkTouch(float f, float f2) {
        this.oldSelectedValue.set(this.selectedValue);
        this.selectedValue.clear();
        if (this.renderer.checkTouch(f, f2)) {
            this.selectedValue.set(this.renderer.getSelectedValue());
        }
        if (this.oldSelectedValue.isSet() && this.selectedValue.isSet() && !this.oldSelectedValue.equals(this.selectedValue)) {
            return false;
        }
        return this.renderer.isTouched();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean computeTouch(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        boolean bl = false;
        switch (n) {
            default: {
                return bl;
            }
            case 0: {
                boolean bl2 = this.renderer.isTouched();
                boolean bl3 = ChartTouchHandler.super.checkTouch(motionEvent.getX(), motionEvent.getY());
                bl = false;
                if (bl2 == bl3) return bl;
                bl = true;
                if (!this.isValueSelectionEnabled) return bl;
                this.selectionModeOldValue.clear();
                if (!bl2) return bl;
                if (this.renderer.isTouched()) return bl;
                this.chart.callTouchListener();
                return bl;
            }
            case 1: {
                boolean bl4 = this.renderer.isTouched();
                bl = false;
                if (!bl4) return bl;
                if (!ChartTouchHandler.super.checkTouch(motionEvent.getX(), motionEvent.getY())) {
                    this.renderer.clearTouch();
                    return true;
                }
                if (this.isValueSelectionEnabled) {
                    if (this.selectionModeOldValue.equals(this.selectedValue)) return true;
                    this.selectionModeOldValue.set(this.selectedValue);
                    this.chart.callTouchListener();
                    return true;
                }
                this.chart.callTouchListener();
                this.renderer.clearTouch();
                return true;
            }
            case 2: {
                boolean bl5 = this.renderer.isTouched();
                bl = false;
                if (!bl5) return bl;
                boolean bl6 = ChartTouchHandler.super.checkTouch(motionEvent.getX(), motionEvent.getY());
                bl = false;
                if (bl6) return bl;
                this.renderer.clearTouch();
                return true;
            }
            case 3: 
        }
        boolean bl7 = this.renderer.isTouched();
        bl = false;
        if (!bl7) return bl;
        this.renderer.clearTouch();
        return true;
    }

    private void disallowParentInterceptTouchEvent() {
        if (this.viewParent != null) {
            this.viewParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public boolean computeScroll() {
        boolean bl = this.isScrollEnabled;
        boolean bl2 = false;
        if (bl) {
            boolean bl3 = this.chartScroller.computeScrollOffset(this.computator);
            bl2 = false;
            if (bl3) {
                bl2 = true;
            }
        }
        if (this.isZoomEnabled && this.chartZoomer.computeZoom(this.computator)) {
            bl2 = true;
        }
        return bl2;
    }

    public ZoomType getZoomType() {
        return this.chartZoomer.getZoomType();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean handleTouchEvent(MotionEvent motionEvent) {
        boolean bl = this.gestureDetector.onTouchEvent(motionEvent);
        boolean bl2 = this.scaleGestureDetector.onTouchEvent(motionEvent) || bl;
        if (this.isZoomEnabled && this.scaleGestureDetector.isInProgress()) {
            ChartTouchHandler.super.disallowParentInterceptTouchEvent();
        }
        if (!this.isValueTouchEnabled) return bl2;
        if (ChartTouchHandler.super.computeTouch(motionEvent)) return true;
        if (bl2) return true;
        return false;
    }

    public boolean handleTouchEvent(MotionEvent motionEvent, ViewParent viewParent, ContainerScrollType containerScrollType) {
        this.viewParent = viewParent;
        this.containerScrollType = containerScrollType;
        return this.handleTouchEvent(motionEvent);
    }

    public boolean isScrollEnabled() {
        return this.isScrollEnabled;
    }

    public boolean isValueSelectionEnabled() {
        return this.isValueSelectionEnabled;
    }

    public boolean isValueTouchEnabled() {
        return this.isValueTouchEnabled;
    }

    public boolean isZoomEnabled() {
        return this.isZoomEnabled;
    }

    public void resetTouchHandler() {
        this.computator = this.chart.getChartComputator();
        this.renderer = this.chart.getChartRenderer();
    }

    public void setScrollEnabled(boolean bl) {
        this.isScrollEnabled = bl;
    }

    public void setValueSelectionEnabled(boolean bl) {
        this.isValueSelectionEnabled = bl;
    }

    public void setValueTouchEnabled(boolean bl) {
        this.isValueTouchEnabled = bl;
    }

    public void setZoomEnabled(boolean bl) {
        this.isZoomEnabled = bl;
    }

    public void setZoomType(ZoomType zoomType) {
        this.chartZoomer.setZoomType(zoomType);
    }

    protected class ChartGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        protected ChartScroller.ScrollResult scrollResult = new ChartScroller.ScrollResult();

        protected ChartGestureListener() {
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (ChartTouchHandler.this.isZoomEnabled) {
                return ChartTouchHandler.this.chartZoomer.startZoom(motionEvent, ChartTouchHandler.this.computator);
            }
            return false;
        }

        public boolean onDown(MotionEvent motionEvent) {
            if (ChartTouchHandler.this.isScrollEnabled) {
                ChartTouchHandler.this.disallowParentInterceptTouchEvent();
                return ChartTouchHandler.this.chartScroller.startScroll(ChartTouchHandler.this.computator);
            }
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (ChartTouchHandler.this.isScrollEnabled) {
                return ChartTouchHandler.this.chartScroller.fling((int)(-f), (int)(-f2), ChartTouchHandler.this.computator);
            }
            return false;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (ChartTouchHandler.this.isScrollEnabled) {
                boolean bl = ChartTouchHandler.this.chartScroller.scroll(ChartTouchHandler.this.computator, f, f2, this.scrollResult);
                ChartTouchHandler.this.allowParentInterceptTouchEvent(this.scrollResult);
                return bl;
            }
            return false;
        }
    }

    protected class ChartScaleGestureListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        protected ChartScaleGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (ChartTouchHandler.this.isZoomEnabled) {
                float f = 2.0f - scaleGestureDetector.getScaleFactor();
                if (Float.isInfinite((float)f)) {
                    f = 1.0f;
                }
                return ChartTouchHandler.this.chartZoomer.scale(ChartTouchHandler.this.computator, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), f);
            }
            return false;
        }
    }

}

