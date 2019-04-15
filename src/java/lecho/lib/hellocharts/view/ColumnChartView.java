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
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.DummyColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.provider.ColumnChartDataProvider;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.renderer.ColumnChartRenderer;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.Chart;

public class ColumnChartView
extends AbstractChartView
implements ColumnChartDataProvider {
    private static final String TAG = "ColumnChartView";
    private ColumnChartData data;
    private ColumnChartOnValueSelectListener onValueTouchListener;

    public ColumnChartView(Context context) {
        super(context, null, 0);
    }

    public ColumnChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public ColumnChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.onValueTouchListener = new DummyColumnChartOnValueSelectListener();
        this.setChartRenderer(new ColumnChartRenderer(context, (Chart)this, (ColumnChartDataProvider)this));
        this.setColumnChartData(ColumnChartData.generateDummyData());
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = this.chartRenderer.getSelectedValue();
        if (selectedValue.isSet()) {
            SubcolumnValue subcolumnValue = (SubcolumnValue)((Column)this.data.getColumns().get(selectedValue.getFirstIndex())).getValues().get(selectedValue.getSecondIndex());
            this.onValueTouchListener.onValueSelected(selectedValue.getFirstIndex(), selectedValue.getSecondIndex(), subcolumnValue);
            return;
        }
        this.onValueTouchListener.onValueDeselected();
    }

    @Override
    public ColumnChartData getChartData() {
        return this.data;
    }

    @Override
    public ColumnChartData getColumnChartData() {
        return this.data;
    }

    public ColumnChartOnValueSelectListener getOnValueTouchListener() {
        return this.onValueTouchListener;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setColumnChartData(ColumnChartData columnChartData) {
        this.data = columnChartData == null ? ColumnChartData.generateDummyData() : columnChartData;
        super.onChartDataChange();
    }

    public void setOnValueTouchListener(ColumnChartOnValueSelectListener columnChartOnValueSelectListener) {
        if (columnChartOnValueSelectListener != null) {
            this.onValueTouchListener = columnChartOnValueSelectListener;
        }
    }
}

