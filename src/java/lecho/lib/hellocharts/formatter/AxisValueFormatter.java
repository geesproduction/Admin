/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package lecho.lib.hellocharts.formatter;

import lecho.lib.hellocharts.model.AxisValue;

public interface AxisValueFormatter {
    public int formatValueForAutoGeneratedAxis(char[] var1, float var2, int var3);

    public int formatValueForManualAxis(char[] var1, AxisValue var2);
}

