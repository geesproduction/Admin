/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Point
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  java.lang.Math
 *  java.lang.Object
 */
package lecho.lib.hellocharts.computator;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import lecho.lib.hellocharts.listener.DummyVieportChangeListener;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Viewport;

public class ChartComputator {
    protected static final float DEFAULT_MAXIMUM_ZOOM = 20.0f;
    protected int chartHeight;
    protected int chartWidth;
    protected Rect contentRectMinusAllMargins = new Rect();
    protected Rect contentRectMinusAxesMargins = new Rect();
    protected Viewport currentViewport = new Viewport();
    protected Rect maxContentRect = new Rect();
    protected Viewport maxViewport = new Viewport();
    protected float maxZoom = 20.0f;
    protected float minViewportHeight;
    protected float minViewportWidth;
    protected ViewportChangeListener viewportChangeListener = new DummyVieportChangeListener();

    private void computeMinimumWidthAndHeight() {
        this.minViewportWidth = this.maxViewport.width() / this.maxZoom;
        this.minViewportHeight = this.maxViewport.height() / this.maxZoom;
    }

    public float computeRawDistanceX(float f) {
        return f * ((float)this.contentRectMinusAllMargins.width() / this.currentViewport.width());
    }

    public float computeRawDistanceY(float f) {
        return f * ((float)this.contentRectMinusAllMargins.height() / this.currentViewport.height());
    }

    public float computeRawX(float f) {
        return (f - this.currentViewport.left) * ((float)this.contentRectMinusAllMargins.width() / this.currentViewport.width()) + (float)this.contentRectMinusAllMargins.left;
    }

    public float computeRawY(float f) {
        float f2 = (f - this.currentViewport.bottom) * ((float)this.contentRectMinusAllMargins.height() / this.currentViewport.height());
        return (float)this.contentRectMinusAllMargins.bottom - f2;
    }

    public void computeScrollSurfaceSize(Point point) {
        point.set((int)(this.maxViewport.width() * (float)this.contentRectMinusAllMargins.width() / this.currentViewport.width()), (int)(this.maxViewport.height() * (float)this.contentRectMinusAllMargins.height() / this.currentViewport.height()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void constrainViewport(float f, float f2, float f3, float f4) {
        if (f3 - f < this.minViewportWidth) {
            f3 = f + this.minViewportWidth;
            if (f < this.maxViewport.left) {
                f = this.maxViewport.left;
                f3 = f + this.minViewportWidth;
            } else if (f3 > this.maxViewport.right) {
                f3 = this.maxViewport.right;
                f = f3 - this.minViewportWidth;
            }
        }
        if (f2 - f4 < this.minViewportHeight) {
            f4 = f2 - this.minViewportHeight;
            if (f2 > this.maxViewport.top) {
                f2 = this.maxViewport.top;
                f4 = f2 - this.minViewportHeight;
            } else if (f4 < this.maxViewport.bottom) {
                f4 = this.maxViewport.bottom;
                f2 = f4 + this.minViewportHeight;
            }
        }
        this.currentViewport.left = Math.max((float)this.maxViewport.left, (float)f);
        this.currentViewport.top = Math.min((float)this.maxViewport.top, (float)f2);
        this.currentViewport.right = Math.min((float)this.maxViewport.right, (float)f3);
        this.currentViewport.bottom = Math.max((float)this.maxViewport.bottom, (float)f4);
        this.viewportChangeListener.onViewportChanged(this.currentViewport);
    }

    public int getChartHeight() {
        return this.chartHeight;
    }

    public int getChartWidth() {
        return this.chartWidth;
    }

    public Rect getContentRectMinusAllMargins() {
        return this.contentRectMinusAllMargins;
    }

    public Rect getContentRectMinusAxesMargins() {
        return this.contentRectMinusAxesMargins;
    }

    public Viewport getCurrentViewport() {
        return this.currentViewport;
    }

    public float getMaxZoom() {
        return this.maxZoom;
    }

    public Viewport getMaximumViewport() {
        return this.maxViewport;
    }

    public float getMinimumViewportHeight() {
        return this.minViewportHeight;
    }

    public float getMinimumViewportWidth() {
        return this.minViewportWidth;
    }

    public Viewport getVisibleViewport() {
        return this.currentViewport;
    }

    public void insetContentRect(int n, int n2, int n3, int n4) {
        this.contentRectMinusAxesMargins.left = n + this.contentRectMinusAxesMargins.left;
        this.contentRectMinusAxesMargins.top = n2 + this.contentRectMinusAxesMargins.top;
        this.contentRectMinusAxesMargins.right -= n3;
        this.contentRectMinusAxesMargins.bottom -= n4;
        this.insetContentRectByInternalMargins(n, n2, n3, n4);
    }

    public void insetContentRectByInternalMargins(int n, int n2, int n3, int n4) {
        this.contentRectMinusAllMargins.left = n + this.contentRectMinusAllMargins.left;
        this.contentRectMinusAllMargins.top = n2 + this.contentRectMinusAllMargins.top;
        this.contentRectMinusAllMargins.right -= n3;
        this.contentRectMinusAllMargins.bottom -= n4;
    }

    public boolean isWithinContentRect(float f, float f2, float f3) {
        return f >= (float)this.contentRectMinusAllMargins.left - f3 && f <= f3 + (float)this.contentRectMinusAllMargins.right && f2 <= f3 + (float)this.contentRectMinusAllMargins.bottom && f2 >= (float)this.contentRectMinusAllMargins.top - f3;
    }

    public boolean rawPixelsToDataPoint(float f, float f2, PointF pointF) {
        if (!this.contentRectMinusAllMargins.contains((int)f, (int)f2)) {
            return false;
        }
        pointF.set(this.currentViewport.left + (f - (float)this.contentRectMinusAllMargins.left) * this.currentViewport.width() / (float)this.contentRectMinusAllMargins.width(), this.currentViewport.bottom + (f2 - (float)this.contentRectMinusAllMargins.bottom) * this.currentViewport.height() / (float)(-this.contentRectMinusAllMargins.height()));
        return true;
    }

    public void resetContentRect() {
        this.contentRectMinusAxesMargins.set(this.maxContentRect);
        this.contentRectMinusAllMargins.set(this.maxContentRect);
    }

    public void setContentRect(int n, int n2, int n3, int n4, int n5, int n6) {
        this.chartWidth = n;
        this.chartHeight = n2;
        this.maxContentRect.set(n3, n4, n - n5, n2 - n6);
        this.contentRectMinusAxesMargins.set(this.maxContentRect);
        this.contentRectMinusAllMargins.set(this.maxContentRect);
    }

    public void setCurrentViewport(float f, float f2, float f3, float f4) {
        this.constrainViewport(f, f2, f3, f4);
    }

    public void setCurrentViewport(Viewport viewport) {
        this.constrainViewport(viewport.left, viewport.top, viewport.right, viewport.bottom);
    }

    public void setMaxViewport(float f, float f2, float f3, float f4) {
        this.maxViewport.set(f, f2, f3, f4);
        ChartComputator.super.computeMinimumWidthAndHeight();
    }

    public void setMaxViewport(Viewport viewport) {
        this.setMaxViewport(viewport.left, viewport.top, viewport.right, viewport.bottom);
    }

    public void setMaxZoom(float f) {
        if (f < 1.0f) {
            f = 1.0f;
        }
        this.maxZoom = f;
        ChartComputator.super.computeMinimumWidthAndHeight();
        this.setCurrentViewport(this.currentViewport);
    }

    public void setViewportChangeListener(ViewportChangeListener viewportChangeListener) {
        if (viewportChangeListener == null) {
            this.viewportChangeListener = new DummyVieportChangeListener();
            return;
        }
        this.viewportChangeListener = viewportChangeListener;
    }

    public void setViewportTopLeft(float f, float f2) {
        float f3 = this.currentViewport.width();
        float f4 = this.currentViewport.height();
        float f5 = Math.max((float)this.maxViewport.left, (float)Math.min((float)f, (float)(this.maxViewport.right - f3)));
        float f6 = Math.max((float)(f4 + this.maxViewport.bottom), (float)Math.min((float)f2, (float)this.maxViewport.top));
        this.constrainViewport(f5, f6, f5 + f3, f6 - f4);
    }

    public void setVisibleViewport(Viewport viewport) {
        this.setCurrentViewport(viewport);
    }
}

