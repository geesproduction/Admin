/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  java.lang.Object
 */
package lecho.lib.hellocharts.renderer;

import android.graphics.Canvas;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.Viewport;

public interface ChartRenderer {
    public boolean checkTouch(float var1, float var2);

    public void clearTouch();

    public void draw(Canvas var1);

    public void drawUnclipped(Canvas var1);

    public Viewport getCurrentViewport();

    public Viewport getMaximumViewport();

    public SelectedValue getSelectedValue();

    public boolean isTouched();

    public boolean isViewportCalculationEnabled();

    public void onChartDataChanged();

    public void onChartSizeChanged();

    public void onChartViewportChanged();

    public void resetRenderer();

    public void selectValue(SelectedValue var1);

    public void setCurrentViewport(Viewport var1);

    public void setMaximumViewport(Viewport var1);

    public void setViewportCalculationEnabled(boolean var1);
}

