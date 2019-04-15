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
import lecho.lib.hellocharts.formatter.BubbleChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleBubbleChartValueFormatter;
import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.BubbleValue;

public class BubbleChartData
extends AbstractChartData {
    public static final float DEFAULT_BUBBLE_SCALE = 1.0f;
    public static final int DEFAULT_MIN_BUBBLE_RADIUS_DP = 6;
    private float bubbleScale;
    private BubbleChartValueFormatter formatter;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private int minBubbleRadius;
    private List<BubbleValue> values;

    public BubbleChartData() {
        this.formatter = new SimpleBubbleChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.minBubbleRadius = 6;
        this.bubbleScale = 1.0f;
        this.values = new ArrayList();
    }

    public BubbleChartData(List<BubbleValue> list) {
        this.formatter = new SimpleBubbleChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.minBubbleRadius = 6;
        this.bubbleScale = 1.0f;
        this.values = new ArrayList();
        this.setValues(list);
    }

    public BubbleChartData(BubbleChartData bubbleChartData) {
        super(bubbleChartData);
        this.formatter = new SimpleBubbleChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.minBubbleRadius = 6;
        this.bubbleScale = 1.0f;
        this.values = new ArrayList();
        this.formatter = bubbleChartData.formatter;
        this.hasLabels = bubbleChartData.hasLabels;
        this.hasLabelsOnlyForSelected = bubbleChartData.hasLabelsOnlyForSelected;
        this.minBubbleRadius = bubbleChartData.minBubbleRadius;
        this.bubbleScale = bubbleChartData.bubbleScale;
        for (BubbleValue bubbleValue : bubbleChartData.getValues()) {
            this.values.add((Object)new BubbleValue(bubbleValue));
        }
    }

    public static BubbleChartData generateDummyData() {
        BubbleChartData bubbleChartData = new BubbleChartData();
        ArrayList arrayList = new ArrayList(4);
        arrayList.add((Object)new BubbleValue(0.0f, 20.0f, 15000.0f));
        arrayList.add((Object)new BubbleValue(3.0f, 22.0f, 20000.0f));
        arrayList.add((Object)new BubbleValue(5.0f, 25.0f, 5000.0f));
        arrayList.add((Object)new BubbleValue(7.0f, 30.0f, 30000.0f));
        arrayList.add((Object)new BubbleValue(11.0f, 22.0f, 10.0f));
        bubbleChartData.setValues((List<BubbleValue>)arrayList);
        return bubbleChartData;
    }

    @Override
    public void finish() {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((BubbleValue)iterator.next()).finish();
        }
    }

    public float getBubbleScale() {
        return this.bubbleScale;
    }

    public BubbleChartValueFormatter getFormatter() {
        return this.formatter;
    }

    public int getMinBubbleRadius() {
        return this.minBubbleRadius;
    }

    public List<BubbleValue> getValues() {
        return this.values;
    }

    public boolean hasLabels() {
        return this.hasLabels;
    }

    public boolean hasLabelsOnlyForSelected() {
        return this.hasLabelsOnlyForSelected;
    }

    public void setBubbleScale(float f) {
        this.bubbleScale = f;
    }

    public BubbleChartData setFormatter(BubbleChartValueFormatter bubbleChartValueFormatter) {
        if (bubbleChartValueFormatter != null) {
            this.formatter = bubbleChartValueFormatter;
        }
        return this;
    }

    public BubbleChartData setHasLabels(boolean bl) {
        this.hasLabels = bl;
        if (bl) {
            this.hasLabelsOnlyForSelected = false;
        }
        return this;
    }

    public BubbleChartData setHasLabelsOnlyForSelected(boolean bl) {
        this.hasLabelsOnlyForSelected = bl;
        if (bl) {
            this.hasLabels = false;
        }
        return this;
    }

    public void setMinBubbleRadius(int n) {
        this.minBubbleRadius = n;
    }

    public BubbleChartData setValues(List<BubbleValue> list) {
        if (list == null) {
            this.values = new ArrayList();
            return this;
        }
        this.values = list;
        return this;
    }

    @Override
    public void update(float f) {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((BubbleValue)iterator.next()).update(f);
        }
    }
}

