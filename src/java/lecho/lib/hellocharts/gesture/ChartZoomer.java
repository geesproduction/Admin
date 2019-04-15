/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.graphics.Rect
 *  android.view.MotionEvent
 *  java.lang.Object
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.gesture.ZoomerCompat;
import lecho.lib.hellocharts.model.Viewport;

public class ChartZoomer {
    public static final float ZOOM_AMOUNT = 0.25f;
    private Viewport scrollerStartViewport = new Viewport();
    private PointF viewportFocus = new PointF();
    private PointF zoomFocalPoint = new PointF();
    private ZoomType zoomType;
    private ZoomerCompat zoomer;

    public ChartZoomer(Context context, ZoomType zoomType) {
        this.zoomer = new ZoomerCompat(context);
        this.zoomType = zoomType;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setCurrentViewport(ChartComputator chartComputator, float f, float f2, float f3, float f4) {
        Viewport viewport = chartComputator.getCurrentViewport();
        if (ZoomType.HORIZONTAL_AND_VERTICAL == this.zoomType) {
            chartComputator.setCurrentViewport(f, f2, f3, f4);
            return;
        } else {
            if (ZoomType.HORIZONTAL == this.zoomType) {
                chartComputator.setCurrentViewport(f, viewport.top, f3, viewport.bottom);
                return;
            }
            if (ZoomType.VERTICAL != this.zoomType) return;
            {
                chartComputator.setCurrentViewport(viewport.left, f2, viewport.right, f4);
                return;
            }
        }
    }

    public boolean computeZoom(ChartComputator chartComputator) {
        if (this.zoomer.computeZoom()) {
            float f = (1.0f - this.zoomer.getCurrZoom()) * this.scrollerStartViewport.width();
            float f2 = (1.0f - this.zoomer.getCurrZoom()) * this.scrollerStartViewport.height();
            float f3 = (this.zoomFocalPoint.x - this.scrollerStartViewport.left) / this.scrollerStartViewport.width();
            float f4 = (this.zoomFocalPoint.y - this.scrollerStartViewport.bottom) / this.scrollerStartViewport.height();
            ChartZoomer.super.setCurrentViewport(chartComputator, this.zoomFocalPoint.x - f * f3, this.zoomFocalPoint.y + f2 * (1.0f - f4), this.zoomFocalPoint.x + f * (1.0f - f3), this.zoomFocalPoint.y - f2 * f4);
            return true;
        }
        return false;
    }

    public ZoomType getZoomType() {
        return this.zoomType;
    }

    public boolean scale(ChartComputator chartComputator, float f, float f2, float f3) {
        float f4 = f3 * chartComputator.getCurrentViewport().width();
        float f5 = f3 * chartComputator.getCurrentViewport().height();
        if (!chartComputator.rawPixelsToDataPoint(f, f2, this.viewportFocus)) {
            return false;
        }
        float f6 = this.viewportFocus.x - (f - (float)chartComputator.getContentRectMinusAllMargins().left) * (f4 / (float)chartComputator.getContentRectMinusAllMargins().width());
        float f7 = this.viewportFocus.y + (f2 - (float)chartComputator.getContentRectMinusAllMargins().top) * (f5 / (float)chartComputator.getContentRectMinusAllMargins().height());
        ChartZoomer.super.setCurrentViewport(chartComputator, f6, f7, f6 + f4, f7 - f5);
        return true;
    }

    public void setZoomType(ZoomType zoomType) {
        this.zoomType = zoomType;
    }

    public boolean startZoom(MotionEvent motionEvent, ChartComputator chartComputator) {
        this.zoomer.forceFinished(true);
        this.scrollerStartViewport.set(chartComputator.getCurrentViewport());
        if (!chartComputator.rawPixelsToDataPoint(motionEvent.getX(), motionEvent.getY(), this.zoomFocalPoint)) {
            return false;
        }
        this.zoomer.startZoom(0.25f);
        return true;
    }
}

