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
import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.model.SubcolumnValue;

public class Column {
    private ColumnChartValueFormatter formatter;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private List<SubcolumnValue> values;

    public Column() {
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.formatter = new SimpleColumnChartValueFormatter();
        this.values = new ArrayList();
    }

    public Column(List<SubcolumnValue> list) {
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.formatter = new SimpleColumnChartValueFormatter();
        this.values = new ArrayList();
        this.setValues(list);
    }

    public Column(Column column) {
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.formatter = new SimpleColumnChartValueFormatter();
        this.values = new ArrayList();
        this.hasLabels = column.hasLabels;
        this.hasLabelsOnlyForSelected = column.hasLabelsOnlyForSelected;
        this.formatter = column.formatter;
        for (SubcolumnValue subcolumnValue : column.values) {
            this.values.add((Object)new SubcolumnValue(subcolumnValue));
        }
    }

    public void finish() {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((SubcolumnValue)iterator.next()).finish();
        }
    }

    public ColumnChartValueFormatter getFormatter() {
        return this.formatter;
    }

    public List<SubcolumnValue> getValues() {
        return this.values;
    }

    public boolean hasLabels() {
        return this.hasLabels;
    }

    public boolean hasLabelsOnlyForSelected() {
        return this.hasLabelsOnlyForSelected;
    }

    public Column setFormatter(ColumnChartValueFormatter columnChartValueFormatter) {
        if (columnChartValueFormatter != null) {
            this.formatter = columnChartValueFormatter;
        }
        return this;
    }

    public Column setHasLabels(boolean bl) {
        this.hasLabels = bl;
        if (bl) {
            this.hasLabelsOnlyForSelected = false;
        }
        return this;
    }

    public Column setHasLabelsOnlyForSelected(boolean bl) {
        this.hasLabelsOnlyForSelected = bl;
        if (bl) {
            this.hasLabels = false;
        }
        return this;
    }

    public Column setValues(List<SubcolumnValue> list) {
        if (list == null) {
            this.values = new ArrayList();
            return this;
        }
        this.values = list;
        return this;
    }

    public void update(float f) {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((SubcolumnValue)iterator.next()).update(f);
        }
    }
}

