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
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.PointValue;

public class LineChartData
extends AbstractChartData {
    public static final float DEFAULT_BASE_VALUE;
    private float baseValue;
    private List<Line> lines;

    public LineChartData() {
        this.lines = new ArrayList();
        this.baseValue = 0.0f;
    }

    public LineChartData(List<Line> list) {
        this.lines = new ArrayList();
        this.baseValue = 0.0f;
        this.setLines(list);
    }

    public LineChartData(LineChartData lineChartData) {
        super(lineChartData);
        this.lines = new ArrayList();
        this.baseValue = 0.0f;
        this.baseValue = lineChartData.baseValue;
        for (Line line : lineChartData.lines) {
            this.lines.add((Object)new Line(line));
        }
    }

    public static LineChartData generateDummyData() {
        LineChartData lineChartData = new LineChartData();
        ArrayList arrayList = new ArrayList(4);
        arrayList.add((Object)new PointValue(0.0f, 2.0f));
        arrayList.add((Object)new PointValue(1.0f, 4.0f));
        arrayList.add((Object)new PointValue(2.0f, 3.0f));
        arrayList.add((Object)new PointValue(3.0f, 4.0f));
        Line line = new Line((List<PointValue>)arrayList);
        ArrayList arrayList2 = new ArrayList(1);
        arrayList2.add((Object)line);
        lineChartData.setLines((List<Line>)arrayList2);
        return lineChartData;
    }

    @Override
    public void finish() {
        Iterator iterator = this.lines.iterator();
        while (iterator.hasNext()) {
            ((Line)iterator.next()).finish();
        }
    }

    public float getBaseValue() {
        return this.baseValue;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public LineChartData setBaseValue(float f) {
        this.baseValue = f;
        return this;
    }

    public LineChartData setLines(List<Line> list) {
        if (list == null) {
            this.lines = new ArrayList();
            return this;
        }
        this.lines = list;
        return this;
    }

    @Override
    public void update(float f) {
        Iterator iterator = this.lines.iterator();
        while (iterator.hasNext()) {
            ((Line)iterator.next()).update(f);
        }
    }
}

