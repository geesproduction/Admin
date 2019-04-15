/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  lecho.lib.hellocharts.renderer.ComboLineColumnChartRenderer
 */
package lecho.lib.hellocharts.view;

import android.content.Context;
import android.util.AttributeSet;
import java.util.List;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.DummyCompoLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.provider.ColumnChartDataProvider;
import lecho.lib.hellocharts.provider.ComboLineColumnChartDataProvider;
import lecho.lib.hellocharts.provider.LineChartDataProvider;
import lecho.lib.hellocharts.renderer.ChartRenderer;
import lecho.lib.hellocharts.renderer.ColumnChartRenderer;
import lecho.lib.hellocharts.renderer.ComboLineColumnChartRenderer;
import lecho.lib.hellocharts.renderer.LineChartRenderer;
import lecho.lib.hellocharts.view.AbstractChartView;
import lecho.lib.hellocharts.view.Chart;

public class ComboLineColumnChartView
extends AbstractChartView
implements ComboLineColumnChartDataProvider {
    private static final String TAG = "ComboLineColumnChartView";
    protected ColumnChartDataProvider columnChartDataProvider;
    protected ComboLineColumnChartData data;
    protected LineChartDataProvider lineChartDataProvider;
    protected ComboLineColumnChartOnValueSelectListener onValueTouchListener;

    public ComboLineColumnChartView(Context context) {
        super(context, null, 0);
    }

    public ComboLineColumnChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public ComboLineColumnChartView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.columnChartDataProvider = new ComboColumnChartDataProvider((ComboLineColumnChartView)this, null);
        this.lineChartDataProvider = new ComboLineChartDataProvider((ComboLineColumnChartView)this, null);
        this.onValueTouchListener = new DummyCompoLineColumnChartOnValueSelectListener();
        this.setChartRenderer((ChartRenderer)new ComboLineColumnChartRenderer(context, (Chart)this, this.columnChartDataProvider, this.lineChartDataProvider));
        this.setComboLineColumnChartData(ComboLineColumnChartData.generateDummyData());
    }

    @Override
    public void callTouchListener() {
        SelectedValue selectedValue = this.chartRenderer.getSelectedValue();
        if (selectedValue.isSet()) {
            if (SelectedValue.SelectedValueType.COLUMN.equals((Object)selectedValue.getType())) {
                SubcolumnValue subcolumnValue = (SubcolumnValue)((Column)this.data.getColumnChartData().getColumns().get(selectedValue.getFirstIndex())).getValues().get(selectedValue.getSecondIndex());
                this.onValueTouchListener.onColumnValueSelected(selectedValue.getFirstIndex(), selectedValue.getSecondIndex(), subcolumnValue);
                return;
            }
            if (SelectedValue.SelectedValueType.LINE.equals((Object)selectedValue.getType())) {
                PointValue pointValue = (PointValue)((Line)this.data.getLineChartData().getLines().get(selectedValue.getFirstIndex())).getValues().get(selectedValue.getSecondIndex());
                this.onValueTouchListener.onPointValueSelected(selectedValue.getFirstIndex(), selectedValue.getSecondIndex(), pointValue);
                return;
            }
            throw new IllegalArgumentException("Invalid selected value type " + selectedValue.getType().name());
        }
        this.onValueTouchListener.onValueDeselected();
    }

    @Override
    public ChartData getChartData() {
        return this.data;
    }

    @Override
    public ComboLineColumnChartData getComboLineColumnChartData() {
        return this.data;
    }

    public ComboLineColumnChartOnValueSelectListener getOnValueTouchListener() {
        return this.onValueTouchListener;
    }

    public void setColumnChartRenderer(Context context, ColumnChartRenderer columnChartRenderer) {
        this.setChartRenderer((ChartRenderer)new ComboLineColumnChartRenderer(context, (Chart)this, columnChartRenderer, this.lineChartDataProvider));
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setComboLineColumnChartData(ComboLineColumnChartData comboLineColumnChartData) {
        this.data = comboLineColumnChartData == null ? null : comboLineColumnChartData;
        super.onChartDataChange();
    }

    public void setLineChartRenderer(Context context, LineChartRenderer lineChartRenderer) {
        this.setChartRenderer((ChartRenderer)new ComboLineColumnChartRenderer(context, (Chart)this, this.columnChartDataProvider, lineChartRenderer));
    }

    public void setOnValueTouchListener(ComboLineColumnChartOnValueSelectListener comboLineColumnChartOnValueSelectListener) {
        if (comboLineColumnChartOnValueSelectListener != null) {
            this.onValueTouchListener = comboLineColumnChartOnValueSelectListener;
        }
    }

    private class ComboColumnChartDataProvider
    implements ColumnChartDataProvider {
        final /* synthetic */ ComboLineColumnChartView this$0;

        private ComboColumnChartDataProvider(ComboLineColumnChartView comboLineColumnChartView) {
            this.this$0 = comboLineColumnChartView;
        }

        /* synthetic */ ComboColumnChartDataProvider(ComboLineColumnChartView comboLineColumnChartView, 1 var2_2) {
            super(comboLineColumnChartView);
        }

        @Override
        public ColumnChartData getColumnChartData() {
            return this.this$0.data.getColumnChartData();
        }

        @Override
        public void setColumnChartData(ColumnChartData columnChartData) {
            this.this$0.data.setColumnChartData(columnChartData);
        }
    }

    private class ComboLineChartDataProvider
    implements LineChartDataProvider {
        final /* synthetic */ ComboLineColumnChartView this$0;

        private ComboLineChartDataProvider(ComboLineColumnChartView comboLineColumnChartView) {
            this.this$0 = comboLineColumnChartView;
        }

        /* synthetic */ ComboLineChartDataProvider(ComboLineColumnChartView comboLineColumnChartView, 1 var2_2) {
            super(comboLineColumnChartView);
        }

        @Override
        public LineChartData getLineChartData() {
            return this.this$0.data.getLineChartData();
        }

        @Override
        public void setLineChartData(LineChartData lineChartData) {
            this.this$0.data.setLineChartData(lineChartData);
        }
    }

}

