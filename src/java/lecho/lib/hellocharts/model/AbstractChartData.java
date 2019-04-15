/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  java.lang.Object
 */
package lecho.lib.hellocharts.model;

import android.graphics.Typeface;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.util.ChartUtils;

public abstract class AbstractChartData
implements ChartData {
    public static final int DEFAULT_TEXT_SIZE_SP = 12;
    protected Axis axisXBottom;
    protected Axis axisXTop;
    protected Axis axisYLeft;
    protected Axis axisYRight;
    protected boolean isValueLabelBackgroundEnabled;
    protected boolean isValueLabelBackgrountAuto;
    protected int valueLabelBackgroundColor;
    protected int valueLabelTextColor;
    protected int valueLabelTextSize;
    protected Typeface valueLabelTypeface;

    public AbstractChartData() {
        this.valueLabelTextColor = -1;
        this.valueLabelTextSize = 12;
        this.isValueLabelBackgroundEnabled = true;
        this.isValueLabelBackgrountAuto = true;
        this.valueLabelBackgroundColor = ChartUtils.darkenColor(ChartUtils.DEFAULT_DARKEN_COLOR);
    }

    public AbstractChartData(AbstractChartData abstractChartData) {
        this.valueLabelTextColor = -1;
        this.valueLabelTextSize = 12;
        this.isValueLabelBackgroundEnabled = true;
        this.isValueLabelBackgrountAuto = true;
        this.valueLabelBackgroundColor = ChartUtils.darkenColor(ChartUtils.DEFAULT_DARKEN_COLOR);
        if (abstractChartData.axisXBottom != null) {
            this.axisXBottom = new Axis(abstractChartData.axisXBottom);
        }
        if (abstractChartData.axisXTop != null) {
            this.axisXTop = new Axis(abstractChartData.axisXTop);
        }
        if (abstractChartData.axisYLeft != null) {
            this.axisYLeft = new Axis(abstractChartData.axisYLeft);
        }
        if (abstractChartData.axisYRight != null) {
            this.axisYRight = new Axis(abstractChartData.axisYRight);
        }
        this.valueLabelTextColor = abstractChartData.valueLabelTextColor;
        this.valueLabelTextSize = abstractChartData.valueLabelTextSize;
        this.valueLabelTypeface = abstractChartData.valueLabelTypeface;
    }

    @Override
    public Axis getAxisXBottom() {
        return this.axisXBottom;
    }

    @Override
    public Axis getAxisXTop() {
        return this.axisXTop;
    }

    @Override
    public Axis getAxisYLeft() {
        return this.axisYLeft;
    }

    @Override
    public Axis getAxisYRight() {
        return this.axisYRight;
    }

    @Override
    public int getValueLabelBackgroundColor() {
        return this.valueLabelBackgroundColor;
    }

    @Override
    public int getValueLabelTextColor() {
        return this.valueLabelTextColor;
    }

    @Override
    public int getValueLabelTextSize() {
        return this.valueLabelTextSize;
    }

    @Override
    public Typeface getValueLabelTypeface() {
        return this.valueLabelTypeface;
    }

    @Override
    public boolean isValueLabelBackgroundAuto() {
        return this.isValueLabelBackgrountAuto;
    }

    @Override
    public boolean isValueLabelBackgroundEnabled() {
        return this.isValueLabelBackgroundEnabled;
    }

    @Override
    public void setAxisXBottom(Axis axis) {
        this.axisXBottom = axis;
    }

    @Override
    public void setAxisXTop(Axis axis) {
        this.axisXTop = axis;
    }

    @Override
    public void setAxisYLeft(Axis axis) {
        this.axisYLeft = axis;
    }

    @Override
    public void setAxisYRight(Axis axis) {
        this.axisYRight = axis;
    }

    @Override
    public void setValueLabelBackgroundAuto(boolean bl) {
        this.isValueLabelBackgrountAuto = bl;
    }

    @Override
    public void setValueLabelBackgroundColor(int n) {
        this.valueLabelBackgroundColor = n;
    }

    @Override
    public void setValueLabelBackgroundEnabled(boolean bl) {
        this.isValueLabelBackgroundEnabled = bl;
    }

    @Override
    public void setValueLabelTextSize(int n) {
        this.valueLabelTextSize = n;
    }

    @Override
    public void setValueLabelTypeface(Typeface typeface) {
        this.valueLabelTypeface = typeface;
    }

    @Override
    public void setValueLabelsTextColor(int n) {
        this.valueLabelTextColor = n;
    }
}

