/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.util.AttributeSet;
import java.util.List;
import lecho.lib.hellocharts.listener.DummyLineChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.renderer.LineChartRenderer;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.Chart;

public class LineChartView
extends AbstractChartView
implements LineChartDataProvider {
    private static final String TAG = "LineChartView";
    protected LineChartData data;
    protected LineChartOnValueSelectListener onValueTouchListener;

    public LineChartView(Context context) {
        super(context, null, 0);
    }

    public LineChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public LineChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.onValueTouchListener = new DummyLineChartOnValueSelectListener();
        this.setChartRenderer(new LineChartRenderer(context, (Chart)this, (LineChartDataProvider)this));
        this.setLineChartData(LineChartData.generateDummyData());
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = this.chartRenderer.getSelectedValue();
        if (selectedValue.isSet()) {
            PointValue pointValue = (PointValue)((Line)this.data.getLines().get(selectedValue.getFirstIndex())).getValues().get(selectedValue.getSecondIndex());
            this.onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), selectedValue.getSecondIndex(), pointValue);
            return;
        }
        this.onValueTouchListener.onValueDeselected();
    }

    @Override
    public ChartData getChartData() {
        return this.data;
    }

    @Override
    public LineChartData getLineChartData() {
        return this.data;
    }

    public LineChartOnValueSelectListener getOnValueTouchListener() {
        return this.onValueTouchListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setLineChartData(LineChartData lineChartData) {
        this.data = lineChartData == null ? LineChartData.generateDummyData() : lineChartData;
        super.onChartDataChange();
    }

    public void setOnValueTouchListener(LineChartOnValueSelectListener lineChartOnValueSelectListener) {
        if (lineChartOnValueSelectListener != null) {
            this.onValueTouchListener = lineChartOnValueSelectListener;
        }
    }
}

