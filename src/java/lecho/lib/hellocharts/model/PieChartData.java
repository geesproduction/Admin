/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.graphics.Typeface
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 */
package lecho.lib.hellocharts.model;

import android.graphics.Typeface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lecho.lib.hellocharts.formatter.PieChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimplePieChartValueFormatter;
import lecho.lib.hellocharts.model.AbstractChartData;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.SliceValue;

public class PieChartData
extends AbstractChartData {
    public static final float DEFAULT_CENTER_CIRCLE_SCALE = 0.6f;
    public static final int DEFAULT_CENTER_TEXT1_SIZE_SP = 42;
    public static final int DEFAULT_CENTER_TEXT2_SIZE_SP = 16;
    private static final int DEFAULT_SLICE_SPACING_DP = 2;
    private int centerCircleColor;
    private float centerCircleScale;
    private String centerText1;
    private int centerText1Color;
    private int centerText1FontSize;
    private Typeface centerText1Typeface;
    private String centerText2;
    private int centerText2Color;
    private int centerText2FontSize;
    private Typeface centerText2Typeface;
    private PieChartValueFormatter formatter;
    private boolean hasCenterCircle;
    private boolean hasLabels;
    private boolean hasLabelsOnlyForSelected;
    private boolean hasLabelsOutside;
    private int slicesSpacing;
    private List<SliceValue> values;

    public PieChartData() {
        this.centerText1FontSize = 42;
        this.centerText2FontSize = 16;
        this.centerCircleScale = 0.6f;
        this.slicesSpacing = 2;
        this.formatter = new SimplePieChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.hasLabelsOutside = false;
        this.hasCenterCircle = false;
        this.centerCircleColor = 0;
        this.centerText1Color = -16777216;
        this.centerText2Color = -16777216;
        this.values = new ArrayList();
        this.setAxisXBottom(null);
        this.setAxisYLeft(null);
    }

    public PieChartData(List<SliceValue> list) {
        this.centerText1FontSize = 42;
        this.centerText2FontSize = 16;
        this.centerCircleScale = 0.6f;
        this.slicesSpacing = 2;
        this.formatter = new SimplePieChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.hasLabelsOutside = false;
        this.hasCenterCircle = false;
        this.centerCircleColor = 0;
        this.centerText1Color = -16777216;
        this.centerText2Color = -16777216;
        this.values = new ArrayList();
        this.setValues(list);
        this.setAxisXBottom(null);
        this.setAxisYLeft(null);
    }

    public PieChartData(PieChartData pieChartData) {
        super(pieChartData);
        this.centerText1FontSize = 42;
        this.centerText2FontSize = 16;
        this.centerCircleScale = 0.6f;
        this.slicesSpacing = 2;
        this.formatter = new SimplePieChartValueFormatter();
        this.hasLabels = false;
        this.hasLabelsOnlyForSelected = false;
        this.hasLabelsOutside = false;
        this.hasCenterCircle = false;
        this.centerCircleColor = 0;
        this.centerText1Color = -16777216;
        this.centerText2Color = -16777216;
        this.values = new ArrayList();
        this.formatter = pieChartData.formatter;
        this.hasLabels = pieChartData.hasLabels;
        this.hasLabelsOnlyForSelected = pieChartData.hasLabelsOnlyForSelected;
        this.hasLabelsOutside = pieChartData.hasLabelsOutside;
        this.hasCenterCircle = pieChartData.hasCenterCircle;
        this.centerCircleColor = pieChartData.centerCircleColor;
        this.centerCircleScale = pieChartData.centerCircleScale;
        this.centerText1Color = pieChartData.centerText1Color;
        this.centerText1FontSize = pieChartData.centerText1FontSize;
        this.centerText1Typeface = pieChartData.centerText1Typeface;
        this.centerText1 = pieChartData.centerText1;
        this.centerText2Color = pieChartData.centerText2Color;
        this.centerText2FontSize = pieChartData.centerText2FontSize;
        this.centerText2Typeface = pieChartData.centerText2Typeface;
        this.centerText2 = pieChartData.centerText2;
        for (SliceValue sliceValue : pieChartData.values) {
            this.values.add((Object)new SliceValue(sliceValue));
        }
    }

    public static PieChartData generateDummyData() {
        PieChartData pieChartData = new PieChartData();
        ArrayList arrayList = new ArrayList(4);
        arrayList.add((Object)new SliceValue(40.0f));
        arrayList.add((Object)new SliceValue(20.0f));
        arrayList.add((Object)new SliceValue(30.0f));
        arrayList.add((Object)new SliceValue(50.0f));
        pieChartData.setValues((List<SliceValue>)arrayList);
        return pieChartData;
    }

    @Override
    public void finish() {
        Iterator iterator = this.values.iterator();
        while (iterator.hasNext()) {
            ((SliceValue)iterator.next()).finish();
        }
    }

    public int getCenterCircleColor() {
        return this.centerCircleColor;
    }

    public float getCenterCircleScale() {
        return this.centerCircleScale;
    }

    public String getCenterText1() {
        return this.centerText1;
    }

    public int getCenterText1Color() {
        return this.centerText1Color;
    }

    public int getCenterText1FontSize() {
        return this.centerText1FontSize;
    }

    public Typeface getCenterText1Typeface() {
        return this.centerText1Typeface;
    }

    public String getCenterText2() {
        return this.centerText2;
    }

    public int getCenterText2Color() {
        return this.centerText2Color;
    }

    public int getCenterText2FontSize() {
        return this.centerText2FontSize;
    }

    public Typeface getCenterText2Typeface() {
        return this.centerText2Typeface;
    }

    public PieChartValueFormatter getFormatter() {
        return this.formatter;
    }

    public int getSlicesSpacing() {
        return this.slicesSpacing;
    }

    public List<SliceValue> getValues() {
        return this.values;
    }

    public boolean hasCenterCircle() {
        return this.hasCenterCircle;
    }

    public boolean hasLabels() {
        return this.hasLabels;
    }

    public boolean hasLabelsOnlyForSelected() {
        return this.hasLabelsOnlyForSelected;
    }

    public boolean hasLabelsOutside() {
        return this.hasLabelsOutside;
    }

    @Override
    public void setAxisXBottom(Axis axis) {
        super.setAxisXBottom(null);
    }

    @Override
    public void setAxisYLeft(Axis axis) {
        super.setAxisYLeft(null);
    }

    public PieChartData setCenterCircleColor(int n) {
        this.centerCircleColor = n;
        return this;
    }

    public PieChartData setCenterCircleScale(float f) {
        this.centerCircleScale = f;
        return this;
    }

    public PieChartData setCenterText1(String string2) {
        this.centerText1 = string2;
        return this;
    }

    public PieChartData setCenterText1Color(int n) {
        this.centerText1Color = n;
        return this;
    }

    public PieChartData setCenterText1FontSize(int n) {
        this.centerText1FontSize = n;
        return this;
    }

    public PieChartData setCenterText1Typeface(Typeface typeface) {
        this.centerText1Typeface = typeface;
        return this;
    }

    public PieChartData setCenterText2(String string2) {
        this.centerText2 = string2;
        return this;
    }

    public PieChartData setCenterText2Color(int n) {
        this.centerText2Color = n;
        return this;
    }

    public PieChartData setCenterText2FontSize(int n) {
        this.centerText2FontSize = n;
        return this;
    }

    public PieChartData setCenterText2Typeface(Typeface typeface) {
        this.centerText2Typeface = typeface;
        return this;
    }

    public PieChartData setFormatter(PieChartValueFormatter pieChartValueFormatter) {
        if (pieChartValueFormatter != null) {
            this.formatter = pieChartValueFormatter;
        }
        return this;
    }

    public PieChartData setHasCenterCircle(boolean bl) {
        this.hasCenterCircle = bl;
        return this;
    }

    public PieChartData setHasLabels(boolean bl) {
        this.hasLabels = bl;
        if (bl) {
            this.hasLabelsOnlyForSelected = false;
        }
        return this;
    }

    public PieChartData setHasLabelsOnlyForSelected(boolean bl) {
        this.hasLabelsOnlyForSelected = bl;
        if (bl) {
            this.hasLabels = false;
        }
        return this;
    }

    public PieChartData setHasLabelsOutside(boolean bl) {
        this.hasLabelsOutside = bl;
        return this;
    }

    public PieChartData setSlicesSpacing(int n) {
        this.slicesSpacing = n;
        return this;
    }

    public PieChartData setValues(List<SliceValue> list) {
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
            ((SliceValue)iterator.next()).update(f);
        }
    }
}

