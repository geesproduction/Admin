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

public interface ChartData {
    public void finish();

    public Axis getAxisXBottom();

    public Axis getAxisXTop();

    public Axis getAxisYLeft();

    public Axis getAxisYRight();

    public int getValueLabelBackgroundColor();

    public int getValueLabelTextColor();

    public int getValueLabelTextSize();

    public Typeface getValueLabelTypeface();

    public boolean isValueLabelBackgroundAuto();

    public boolean isValueLabelBackgroundEnabled();

    public void setAxisXBottom(Axis var1);

    public void setAxisXTop(Axis var1);

    public void setAxisYLeft(Axis var1);

    public void setAxisYRight(Axis var1);

    public void setValueLabelBackgroundAuto(boolean var1);

    public void setValueLabelBackgroundColor(int var1);

    public void setValueLabelBackgroundEnabled(boolean var1);

    public void setValueLabelTextSize(int var1);

    public void setValueLabelTypeface(Typeface var1);

    public void setValueLabelsTextColor(int var1);

    public void update(float var1);
}

