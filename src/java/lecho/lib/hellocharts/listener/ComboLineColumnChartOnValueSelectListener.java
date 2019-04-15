/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.listener;

import lecho.lib.hellocharts.listener.OnValueDeselectListener;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;

public interface ComboLineColumnChartOnValueSelectListener
extends OnValueDeselectListener {
    public void onColumnValueSelected(int var1, int var2, SubcolumnValue var3);

    public void onPointValueSelected(int var1, int var2, PointValue var3);
}

