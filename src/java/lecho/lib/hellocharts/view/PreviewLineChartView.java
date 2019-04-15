/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.support.v4.view.ViewCompat
 *  android.util.AttributeSet
 *  android.view.View
 *  java.lang.String
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import lecho.lib.hellocharts.computator.ChartComputator;
import lecho.lib.hellocharts.computator.PreviewChartComputator;
import lecho.lib.hellocharts.gesture.ChartTouchHandler;
import lecho.lib.hellocharts.gesture.PreviewChartTouchHandler;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.renderer.PreviewLineChartRenderer;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class PreviewLineChartView
extends LineChartView {
    private static final String TAG = "PreviewLineChartView";
    protected PreviewLineChartRenderer previewChartRenderer;

    public PreviewLineChartView(Context context) {
        super(context, null, 0);
    }

    public PreviewLineChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public PreviewLineChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.chartComputator = new PreviewChartComputator();
        this.previewChartRenderer = new PreviewLineChartRenderer(context, (Chart)this, (LineChartDataProvider)this);
        this.touchHandler = new PreviewChartTouchHandler(context, (Chart)this);
        this.setChartRenderer(this.previewChartRenderer);
        this.setLineChartData(LineChartData.generateDummyData());
    }

    public int getPreviewColor() {
        return this.previewChartRenderer.getPreviewColor();
    }

    public void setPreviewColor(int n) {
        this.previewChartRenderer.setPreviewColor(n);
        ViewCompat.postInvalidateOnAnimation((View)this);
    }
}

