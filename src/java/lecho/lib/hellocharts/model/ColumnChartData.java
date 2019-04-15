/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.SubcolumnValue;

public class ColumnChartData
extends AbstractChartData {
    public static final float DEFAULT_BASE_VALUE = 0.0f;
    public static final float DEFAULT_FILL_RATIO = 0.75f;
    private float baseValue;
    private List<Column> columns;
    private float fillRatio;
    private boolean isStacked;

    public ColumnChartData() {
        this.fillRatio = 0.75f;
        this.baseValue = 0.0f;
        this.columns = new ArrayList();
        this.isStacked = false;
    }

    public ColumnChartData(List<Column> list) {
        this.fillRatio = 0.75f;
        this.baseValue = 0.0f;
        this.columns = new ArrayList();
        this.isStacked = false;
        this.setColumns(list);
    }

    public ColumnChartData(ColumnChartData columnChartData) {
        super(columnChartData);
        this.fillRatio = 0.75f;
        this.baseValue = 0.0f;
        this.columns = new ArrayList();
        this.isStacked = false;
        this.isStacked = columnChartData.isStacked;
        this.fillRatio = columnChartData.fillRatio;
        for (Column column : columnChartData.columns) {
            this.columns.add((Object)new Column(column));
        }
    }

    public static ColumnChartData generateDummyData() {
        ColumnChartData columnChartData = new ColumnChartData();
        ArrayList arrayList = new ArrayList(4);
        for (int i = 1; i <= 4; ++i) {
            ArrayList arrayList2 = new ArrayList(4);
            arrayList2.add((Object)new SubcolumnValue(i));
            arrayList.add((Object)new Column((List<SubcolumnValue>)arrayList2));
        }
        columnChartData.setColumns((List<Column>)arrayList);
        return columnChartData;
    }

    @Override
    public void finish() {
        Iterator iterator = this.columns.iterator();
        while (iterator.hasNext()) {
            ((Column)iterator.next()).finish();
        }
    }

    public float getBaseValue() {
        return this.baseValue;
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public float getFillRatio() {
        return this.fillRatio;
    }

    public boolean isStacked() {
        return this.isStacked;
    }

    public ColumnChartData setBaseValue(float f) {
        this.baseValue = f;
        return this;
    }

    public ColumnChartData setColumns(List<Column> list) {
        if (list == null) {
            this.columns = new ArrayList();
            return this;
        }
        this.columns = list;
        return this;
    }

    public ColumnChartData setFillRatio(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.fillRatio = f;
        return this;
    }

    public ColumnChartData setStacked(boolean bl) {
        this.isStacked = bl;
        return this;
    }

    @Override
    public void update(float f) {
        Iterator iterator = this.columns.iterator();
        while (iterator.hasNext()) {
            ((Column)iterator.next()).update(f);
        }
    }
}

