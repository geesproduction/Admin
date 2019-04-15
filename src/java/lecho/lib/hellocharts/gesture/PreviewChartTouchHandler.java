/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  java.lang.Float
 */
package lecho.lib.hellocharts.gesture;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.gesture.ChartZoomer;
import lecho.lib.hellocharts.view.Chart;

public class PreviewChartTouchHandler
extends ChartTouchHandler {
    public PreviewChartTouchHandler(Context context, Chart chart) {
        super(context, chart);
        this.gestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)(PreviewChartTouchHandler)this.new PreviewChartGestureListener());
        this.scaleGestureDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)(PreviewChartTouchHandler)this.new ChartScaleGestureListener());
        this.isValueTouchEnabled = false;
        this.isValueSelectionEnabled = false;
    }

    protected class ChartScaleGestureListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        protected ChartScaleGestureListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            if (PreviewChartTouchHandler.this.isZoomEnabled) {
                float f = scaleGestureDetector.getCurrentSpan() / scaleGestureDetector.getPreviousSpan();
                if (Float.isInfinite((float)f)) {
                    f = 1.0f;
                }
                return PreviewChartTouchHandler.this.chartZoomer.scale(PreviewChartTouchHandler.this.computator, scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), f);
            }
            return false;
        }
    }

    protected class PreviewChartGestureListener
    extends ChartTouchHandler.ChartGestureListener {
        protected PreviewChartGestureListener() {
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onFling(motionEvent, motionEvent2, -f, -f2);
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return super.onScroll(motionEvent, motionEvent2, -f, -f2);
        }
    }

}

