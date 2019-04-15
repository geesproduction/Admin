/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.RectF
 *  android.support.v4.widget.ScrollerCompat
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  java.lang.Math
 *  java.lang.Object
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.graphics.RectF;
import android.support.v4.widget.ScrollerCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.PieChartView;

public class PieChartTouchHandler
extends ChartTouchHandler {
    public static final int FLING_VELOCITY_DOWNSCALE = 4;
    private boolean isRotationEnabled = true;
    protected PieChartView pieChart;
    protected ScrollerCompat scroller;

    public PieChartTouchHandler(Context context, PieChartView pieChartView) {
        super(context, pieChartView);
        this.pieChart = pieChartView;
        this.scroller = ScrollerCompat.create((Context)context);
        this.gestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new ChartGestureListener((PieChartTouchHandler)this, null));
        this.scaleGestureDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ChartScaleGestureListener((PieChartTouchHandler)this, null));
        this.isZoomEnabled = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean computeScroll() {
        if (!this.isRotationEnabled || !this.scroller.computeScrollOffset()) {
            return false;
        }
        this.pieChart.setChartRotation(this.scroller.getCurrY(), false);
        return false;
    }

    @Override
    public boolean handleTouchEvent(MotionEvent motionEvent) {
        block3 : {
            boolean bl;
            block2 : {
                bl = super.handleTouchEvent(motionEvent);
                if (!this.isRotationEnabled) break block2;
                if (!this.gestureDetector.onTouchEvent(motionEvent) && !bl) break block3;
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean isRotationEnabled() {
        return this.isRotationEnabled;
    }

    public void setRotationEnabled(boolean bl) {
        this.isRotationEnabled = bl;
    }

    private class ChartGestureListener
    extends GestureDetector.SimpleOnGestureListener {
        final /* synthetic */ PieChartTouchHandler this$0;

        private ChartGestureListener(PieChartTouchHandler pieChartTouchHandler) {
            this.this$0 = pieChartTouchHandler;
        }

        /* synthetic */ ChartGestureListener(PieChartTouchHandler pieChartTouchHandler, 1 var2_2) {
            super(pieChartTouchHandler);
        }

        private float vectorToScalarScroll(float f, float f2, float f3, float f4) {
            return (float)Math.sqrt((double)(f * f + f2 * f2)) * Math.signum((float)(f * -f4 + f3 * f2));
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            return false;
        }

        public boolean onDown(MotionEvent motionEvent) {
            if (this.this$0.isRotationEnabled) {
                this.this$0.scroller.abortAnimation();
                return true;
            }
            return false;
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (this.this$0.isRotationEnabled) {
                RectF rectF = this.this$0.pieChart.getCircleOval();
                float f3 = rectF.centerX();
                float f4 = rectF.centerY();
                float f5 = ChartGestureListener.super.vectorToScalarScroll(f, f2, motionEvent2.getX() - f3, motionEvent2.getY() - f4);
                this.this$0.scroller.abortAnimation();
                this.this$0.scroller.fling(0, this.this$0.pieChart.getChartRotation(), 0, (int)f5 / 4, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                return true;
            }
            return false;
        }

        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            boolean bl = this.this$0.isRotationEnabled;
            boolean bl2 = false;
            if (bl) {
                RectF rectF = this.this$0.pieChart.getCircleOval();
                float f3 = rectF.centerX();
                float f4 = rectF.centerY();
                float f5 = ChartGestureListener.super.vectorToScalarScroll(f, f2, motionEvent2.getX() - f3, motionEvent2.getY() - f4);
                this.this$0.pieChart.setChartRotation(this.this$0.pieChart.getChartRotation() - (int)f5 / 4, false);
                bl2 = true;
            }
            return bl2;
        }
    }

    private class ChartScaleGestureListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        final /* synthetic */ PieChartTouchHandler this$0;

        private ChartScaleGestureListener(PieChartTouchHandler pieChartTouchHandler) {
            this.this$0 = pieChartTouchHandler;
        }

        /* synthetic */ ChartScaleGestureListener(PieChartTouchHandler pieChartTouchHandler, 1 var2_2) {
            super(pieChartTouchHandler);
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }
    }

}

