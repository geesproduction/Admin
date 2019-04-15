/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Point
 *  android.graphics.Rect
 *  android.support.v4.widget.ScrollerCompat
 *  java.lang.Object
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.widget.ScrollerCompat;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.model.Viewport;

public class ChartScroller {
    private ScrollerCompat scroller;
    private Viewport scrollerStartViewport = new Viewport();
    private Point surfaceSizeBuffer = new Point();

    public ChartScroller(Context context) {
        this.scroller = ScrollerCompat.create((Context)context);
    }

    public boolean computeScrollOffset(ChartComputator chartComputator) {
        if (this.scroller.computeScrollOffset()) {
            Viewport viewport = chartComputator.getMaximumViewport();
            chartComputator.computeScrollSurfaceSize(this.surfaceSizeBuffer);
            chartComputator.setViewportTopLeft(viewport.left + viewport.width() * (float)this.scroller.getCurrX() / (float)this.surfaceSizeBuffer.x, viewport.top - viewport.height() * (float)this.scroller.getCurrY() / (float)this.surfaceSizeBuffer.y);
            return true;
        }
        return false;
    }

    public boolean fling(int n, int n2, ChartComputator chartComputator) {
        chartComputator.computeScrollSurfaceSize(this.surfaceSizeBuffer);
        this.scrollerStartViewport.set(chartComputator.getCurrentViewport());
        int n3 = (int)((float)this.surfaceSizeBuffer.x * (this.scrollerStartViewport.left - chartComputator.getMaximumViewport().left) / chartComputator.getMaximumViewport().width());
        int n4 = (int)((float)this.surfaceSizeBuffer.y * (chartComputator.getMaximumViewport().top - this.scrollerStartViewport.top) / chartComputator.getMaximumViewport().height());
        this.scroller.abortAnimation();
        int n5 = chartComputator.getContentRectMinusAllMargins().width();
        int n6 = chartComputator.getContentRectMinusAllMargins().height();
        this.scroller.fling(n3, n4, n, n2, 0, 1 + (this.surfaceSizeBuffer.x - n5), 0, 1 + (this.surfaceSizeBuffer.y - n6));
        return true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean scroll(ChartComputator chartComputator, float f, float f2, ScrollResult scrollResult) {
        boolean bl;
        boolean bl2;
        Viewport viewport = chartComputator.getMaximumViewport();
        Viewport viewport2 = chartComputator.getVisibleViewport();
        Viewport viewport3 = chartComputator.getCurrentViewport();
        Rect rect = chartComputator.getContentRectMinusAllMargins();
        boolean bl3 = viewport3.left > viewport.left;
        boolean bl4 = viewport3.right < viewport.right;
        boolean bl5 = viewport3.top < viewport.top;
        boolean bl6 = viewport3.bottom > viewport.bottom;
        if (bl3 && f <= 0.0f) {
            bl2 = true;
        } else {
            bl2 = false;
            if (bl4) {
                float f3 = f FCMPL 0.0f;
                bl2 = false;
                if (f3 >= 0) {
                    bl2 = true;
                }
            }
        }
        if (bl5 && f2 <= 0.0f) {
            bl = true;
        } else {
            bl = false;
            if (bl6) {
                float f4 = f2 FCMPL 0.0f;
                bl = false;
                if (f4 >= 0) {
                    bl = true;
                }
            }
        }
        if (bl2 || bl) {
            chartComputator.computeScrollSurfaceSize(this.surfaceSizeBuffer);
            float f5 = f * viewport2.width() / (float)rect.width();
            float f6 = -f2 * viewport2.height() / (float)rect.height();
            chartComputator.setViewportTopLeft(f5 + viewport3.left, f6 + viewport3.top);
        }
        scrollResult.canScrollX = bl2;
        scrollResult.canScrollY = bl;
        return bl2 || bl;
    }

    public boolean startScroll(ChartComputator chartComputator) {
        this.scroller.abortAnimation();
        this.scrollerStartViewport.set(chartComputator.getCurrentViewport());
        return true;
    }

    public static class ScrollResult {
        public boolean canScrollX;
        public boolean canScrollY;
    }

}

