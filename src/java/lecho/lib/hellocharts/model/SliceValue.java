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

public class SliceValue {
    private static final int DEFAULT_SLICE_SPACING_DP = 2;
    private int color;
    private int darkenColor;
    private float diff;
    private char[] label;
    private float originValue;
    @Deprecated
    private int sliceSpacing;
    private float value;

    public SliceValue() {
        this.sliceSpacing = 2;
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(0.0f);
    }

    public SliceValue(float f) {
        this.sliceSpacing = 2;
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(f);
    }

    public SliceValue(float f, int n) {
        this.sliceSpacing = 2;
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(f);
        this.setColor(n);
    }

    public SliceValue(float f, int n, int n2) {
        this.sliceSpacing = 2;
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(f);
        this.setColor(n);
        this.sliceSpacing = n2;
    }

    public SliceValue(SliceValue sliceValue) {
        this.sliceSpacing = 2;
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.setValue(sliceValue.value);
        this.setColor(sliceValue.color);
        this.sliceSpacing = sliceValue.sliceSpacing;
        this.label = sliceValue.label;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block11 : {
            block10 : {
                if (this == object) break block10;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                SliceValue sliceValue = (SliceValue)object;
                if (this.color != sliceValue.color) {
                    return false;
                }
                if (this.darkenColor != sliceValue.darkenColor) {
                    return false;
                }
                if (Float.compare((float)sliceValue.diff, (float)this.diff) != 0) {
                    return false;
                }
                if (Float.compare((float)sliceValue.originValue, (float)this.originValue) != 0) {
                    return false;
                }
                if (this.sliceSpacing != sliceValue.sliceSpacing) {
                    return false;
                }
                if (Float.compare((float)sliceValue.value, (float)this.value) != 0) {
                    return false;
                }
                if (!Arrays.equals((char[])this.label, (char[])sliceValue.label)) break block11;
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

    @Deprecated
    public int getSliceSpacing() {
        return this.sliceSpacing;
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
        int n6 = 31 * (31 * (31 * (31 * (n4 + n5) + this.color) + this.darkenColor) + this.sliceSpacing);
        char[] arrc = this.label;
        int n7 = 0;
        if (arrc != null) {
            n7 = Arrays.hashCode((char[])this.label);
        }
        return n6 + n7;
    }

    public SliceValue setColor(int n) {
        this.color = n;
        this.darkenColor = ChartUtils.darkenColor(n);
        return this;
    }

    public SliceValue setLabel(String string2) {
        this.label = string2.toCharArray();
        return this;
    }

    @Deprecated
    public SliceValue setLabel(char[] arrc) {
        this.label = arrc;
        return this;
    }

    @Deprecated
    public SliceValue setSliceSpacing(int n) {
        this.sliceSpacing = n;
        return this;
    }

    public SliceValue setTarget(float f) {
        this.setValue(this.value);
        this.diff = f - this.originValue;
        return this;
    }

    public SliceValue setValue(float f) {
        this.value = f;
        this.originValue = f;
        this.diff = 0.0f;
        return this;
    }

    public String toString() {
        return "SliceValue [value=" + this.value + "]";
    }

    public void update(float f) {
        this.value = this.originValue + f * this.diff;
    }
}

