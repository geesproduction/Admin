/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Deprecated
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Arrays
 */
package lecho.lib.hellocharts.model;

import java.util.Arrays;
import lecho.lib.hellocharts.util.ChartUtils;

public class SubcolumnValue {
    private int color;
    private int darkenColor;
    private float diff;
    private char[] label;
    private float originValue;
    private float value;

    public SubcolumnValue() {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(0.0f);
    }

    public SubcolumnValue(float f) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(f);
    }

    public SubcolumnValue(float f, int n) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(f);
        this.setColor(n);
    }

    public SubcolumnValue(SubcolumnValue subcolumnValue) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(subcolumnValue.value);
        this.setColor(subcolumnValue.color);
        this.label = subcolumnValue.label;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block10 : {
            block9 : {
                if (this == object) break block9;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                SubcolumnValue subcolumnValue = (SubcolumnValue)object;
                if (this.color != subcolumnValue.color) {
                    return false;
                }
                if (this.darkenColor != subcolumnValue.darkenColor) {
                    return false;
                }
                if (Float.compare((float)subcolumnValue.diff, (float)this.diff) != 0) {
                    return false;
                }
                if (Float.compare((float)subcolumnValue.originValue, (float)this.originValue) != 0) {
                    return false;
                }
                if (Float.compare((float)subcolumnValue.value, (float)this.value) != 0) {
                    return false;
                }
                if (!Arrays.equals((char[])this.label, (char[])subcolumnValue.label)) break block10;
            }
            return true;
        }
        return false;
    }

    public void finish() {
        this.setValue(this.originValue + this.diff);
    }

    public int getColor() {
        return this.color;
    }

    public int getDarkenColor() {
        return this.darkenColor;
    }

    @Deprecated
    public char[] getLabel() {
        return this.label;
    }

    public char[] getLabelAsChars() {
        return this.label;
    }

    public float getValue() {
        return this.value;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = this.value != 0.0f ? Float.floatToIntBits((float)this.value) : 0;
        int n2 = n * 31;
        int n3 = this.originValue != 0.0f ? Float.floatToIntBits((float)this.originValue) : 0;
        int n4 = 31 * (n2 + n3);
        int n5 = this.diff != 0.0f ? Float.floatToIntBits((float)this.diff) : 0;
        int n6 = 31 * (31 * (31 * (n4 + n5) + this.color) + this.darkenColor);
        char[] arrc = this.label;
        int n7 = 0;
        if (arrc != null) {
            n7 = Arrays.hashCode((char[])this.label);
        }
        return n6 + n7;
    }

    public SubcolumnValue setColor(int n) {
        this.color = n;
        this.darkenColor = ChartUtils.darkenColor(n);
        return this;
    }

    public SubcolumnValue setLabel(String string2) {
        this.label = string2.toCharArray();
        return this;
    }

    @Deprecated
    public SubcolumnValue setLabel(char[] arrc) {
        this.label = arrc;
        return this;
    }

    public SubcolumnValue setTarget(float f) {
        this.setValue(this.value);
        this.diff = f - this.originValue;
        return this;
    }

    public SubcolumnValue setValue(float f) {
        this.value = f;
        this.originValue = f;
        this.diff = 0.0f;
        return this;
    }

    public String toString() {
        return "ColumnValue [value=" + this.value + "]";
    }

    public void update(float f) {
        this.value = this.originValue + f * this.diff;
    }
}

