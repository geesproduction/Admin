/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 */
package lecho.lib.hellocharts.computator;

import android.graphics.Rect;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.listener.ViewportChangeListener;
import lecho.lib.hellocharts.model.Viewport;

public class PreviewChartComputator
extends ChartComputator {
    @Override
    public float computeRawX(float f) {
        return (f - this.maxViewport.left) * ((float)this.contentRectMinusAllMargins.width() / this.maxViewport.width()) + (float)this.contentRectMinusAllMargins.left;
    }

    @Override
    public float computeRawY(float f) {
        float f2 = (f - this.maxViewport.bottom) * ((float)this.contentRectMinusAllMargins.height() / this.maxViewport.height());
        return (float)this.contentRectMinusAllMargins.bottom - f2;
    }

    @Override
    public void constrainViewport(float f, float f2, float f3, float f4) {
        super.constrainViewport(f, f2, f3, f4);
        this.viewportChangeListener.onViewportChanged(this.currentViewport);
    }

    @Override
    public Viewport getVisibleViewport() {
        return this.maxViewport;
    }

    @Override
    public void setVisibleViewport(Viewport viewport) {
        this.setMaxViewport(viewport);
    }
}

