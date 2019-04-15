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

public class PointValue {
    private float diffX;
    private float diffY;
    private char[] label;
    private float originX;
    private float originY;
    private float x;
    private float y;

    public PointValue() {
        this.set(0.0f, 0.0f);
    }

    public PointValue(float f, float f2) {
        this.set(f, f2);
    }

    public PointValue(PointValue pointValue) {
        this.set(pointValue.x, pointValue.y);
        this.label = pointValue.label;
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
                PointValue pointValue = (PointValue)object;
                if (Float.compare((float)pointValue.diffX, (float)this.diffX) != 0) {
                    return false;
                }
                if (Float.compare((float)pointValue.diffY, (float)this.diffY) != 0) {
                    return false;
                }
                if (Float.compare((float)pointValue.originX, (float)this.originX) != 0) {
                    return false;
                }
                if (Float.compare((float)pointValue.originY, (float)this.originY) != 0) {
                    return false;
                }
                if (Float.compare((float)pointValue.x, (float)this.x) != 0) {
                    return false;
                }
                if (Float.compare((float)pointValue.y, (float)this.y) != 0) {
                    return false;
                }
                if (!Arrays.equals((char[])this.label, (char[])pointValue.label)) break block11;
            }
            return true;
        }
        return false;
    }

    public void finish() {
        this.set(this.originX + this.diffX, this.originY + this.diffY);
    }

    @Deprecated
    public char[] getLabel() {
        return this.label;
    }

    public char[] getLabelAsChars() {
        return this.label;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = this.x != 0.0f ? Float.floatToIntBits((float)this.x) : 0;
        int n2 = n * 31;
        int n3 = this.y != 0.0f ? Float.floatToIntBits((float)this.y) : 0;
        int n4 = 31 * (n2 + n3);
        int n5 = this.originX != 0.0f ? Float.floatToIntBits((float)this.originX) : 0;
        int n6 = 31 * (n4 + n5);
        int n7 = this.originY != 0.0f ? Float.floatToIntBits((float)this.originY) : 0;
        int n8 = 31 * (n6 + n7);
        int n9 = this.diffX != 0.0f ? Float.floatToIntBits((float)this.diffX) : 0;
        int n10 = 31 * (n8 + n9);
        int n11 = this.diffY != 0.0f ? Float.floatToIntBits((float)this.diffY) : 0;
        int n12 = 31 * (n10 + n11);
        char[] arrc = this.label;
        int n13 = 0;
        if (arrc != null) {
            n13 = Arrays.hashCode((char[])this.label);
        }
        return n12 + n13;
    }

    public PointValue set(float f, float f2) {
        this.x = f;
        this.y = f2;
        this.originX = f;
        this.originY = f2;
        this.diffX = 0.0f;
        this.diffY = 0.0f;
        return this;
    }

    public PointValue setLabel(String string2) {
        this.label = string2.toCharArray();
        return this;
    }

    @Deprecated
    public PointValue setLabel(char[] arrc) {
        this.label = arrc;
        return this;
    }

    public PointValue setTarget(float f, float f2) {
        this.set(this.x, this.y);
        this.diffX = f - this.originX;
        this.diffY = f2 - this.originY;
        return this;
    }

    public String toString() {
        return "PointValue [x=" + this.x + ", y=" + this.y + "]";
    }

    public void update(float f) {
        this.x = this.originX + f * this.diffX;
        this.y = this.originY + f * this.diffY;
    }
}

