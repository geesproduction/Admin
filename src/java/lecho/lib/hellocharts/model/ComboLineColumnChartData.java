/*
 * Decompiled with CFR 0.0.
 */
package lecho.lib.hellocharts.model;

import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.LineChartData;

public class ComboLineColumnChartData
extends AbstractChartData {
    private ColumnChartData columnChartData;
    private LineChartData lineChartData;

    public ComboLineColumnChartData() {
        this.columnChartData = new ColumnChartData();
        this.lineChartData = new LineChartData();
    }

    public ComboLineColumnChartData(ColumnChartData columnChartData, LineChartData lineChartData) {
        this.setColumnChartData(columnChartData);
        this.setLineChartData(lineChartData);
    }

    public ComboLineColumnChartData(ComboLineColumnChartData comboLineColumnChartData) {
        super(comboLineColumnChartData);
        this.setColumnChartData(new ColumnChartData(comboLineColumnChartData.getColumnChartData()));
        this.setLineChartData(new LineChartData(comboLineColumnChartData.getLineChartData()));
    }

    public static ComboLineColumnChartData generateDummyData() {
        ComboLineColumnChartData comboLineColumnChartData = new ComboLineColumnChartData();
        comboLineColumnChartData.setColumnChartData(ColumnChartData.generateDummyData());
        comboLineColumnChartData.setLineChartData(LineChartData.generateDummyData());
        return comboLineColumnChartData;
    }

    @Override
    public void finish() {
        this.columnChartData.finish();
        this.lineChartData.finish();
    }

    public ColumnChartData getColumnChartData() {
        return this.columnChartData;
    }

    public LineChartData getLineChartData() {
        return this.lineChartData;
    }

    public void setColumnChartData(ColumnChartData columnChartData) {
        if (columnChartData == null) {
            this.columnChartData = new ColumnChartData();
            return;
        }
        this.columnChartData = columnChartData;
    }

    public void setLineChartData(LineChartData lineChartData) {
        if (lineChartData == null) {
            this.lineChartData = new LineChartData();
            return;
        }
        this.lineChartData = lineChartData;
    }

    @Override
    public void update(float f) {
        this.columnChartData.update(f);
        this.lineChartData.update(f);
    }
}

