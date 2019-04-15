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

public class AxisValue {
    private char[] label;
    private float value;

    public AxisValue(float f) {
        this.setValue(f);
    }

    @Deprecated
    public AxisValue(float f, char[] arrc) {
        this.value = f;
        this.label = arrc;
    }

    public AxisValue(AxisValue axisValue) {
        this.value = axisValue.value;
        this.label = axisValue.label;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block6 : {
            block5 : {
                if (this == object) break block5;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                AxisValue axisValue = (AxisValue)object;
                if (Float.compare((float)axisValue.value, (float)this.value) != 0) {
                    return false;
                }
                if (!Arrays.equals((char[])this.label, (char[])axisValue.label)) break block6;
            }
            return true;
        }
        return false;
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
        char[] arrc = this.label;
        int n3 = 0;
        if (arrc != null) {
            n3 = Arrays.hashCode((char[])this.label);
        }
        return n2 + n3;
    }

    public AxisValue setLabel(String string2) {
        this.label = string2.toCharArray();
        return this;
    }

    @Deprecated
    public AxisValue setLabel(char[] arrc) {
        this.label = arrc;
        return this;
    }

    public AxisValue setValue(float f) {
        this.value = f;
        return this;
    }
}

