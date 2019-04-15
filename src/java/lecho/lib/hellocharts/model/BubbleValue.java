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
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;

public class BubbleValue {
    private int color;
    private int darkenColor;
    private float diffX;
    private float diffY;
    private float diffZ;
    private char[] label;
    private float originX;
    private float originY;
    private float originZ;
    private ValueShape shape;
    private float x;
    private float y;
    private float z;

    public BubbleValue() {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.shape = ValueShape.CIRCLE;
        this.set(0.0f, 0.0f, 0.0f);
    }

    public BubbleValue(float f, float f2, float f3) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.shape = ValueShape.CIRCLE;
        this.set(f, f2, f3);
    }

    public BubbleValue(float f, float f2, float f3, int n) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.shape = ValueShape.CIRCLE;
        this.set(f, f2, f3);
        this.setColor(n);
    }

    public BubbleValue(BubbleValue bubbleValue) {
        this.color = ChartUtils.DEFAULT_COLOR;
        this.darkenColor = ChartUtils.DEFAULT_DARKEN_COLOR;
        this.shape = ValueShape.CIRCLE;
        this.set(bubbleValue.x, bubbleValue.y, bubbleValue.z);
        this.setColor(bubbleValue.color);
        this.label = bubbleValue.label;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block17 : {
            block16 : {
                if (this == object) break block16;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                BubbleValue bubbleValue = (BubbleValue)object;
                if (this.color != bubbleValue.color) {
                    return false;
                }
                if (this.darkenColor != bubbleValue.darkenColor) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.diffX, (float)this.diffX) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.diffY, (float)this.diffY) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.diffZ, (float)this.diffZ) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.originX, (float)this.originX) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.originY, (float)this.originY) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.originZ, (float)this.originZ) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.x, (float)this.x) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.y, (float)this.y) != 0) {
                    return false;
                }
                if (Float.compare((float)bubbleValue.z, (float)this.z) != 0) {
                    return false;
                }
                if (!Arrays.equals((char[])this.label, (char[])bubbleValue.label)) {
                    return false;
                }
                if (this.shape != bubbleValue.shape) break block17;
            }
            return true;
        }
        return false;
    }

    public void finish() {
        this.set(this.originX + this.diffX, this.originY + this.diffY, this.originZ + this.diffZ);
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

    public ValueShape getShape() {
        return this.shape;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = this.x != 0.0f ? Float.floatToIntBits((float)this.x) : 0;
        int n2 = n * 31;
        int n3 = this.y != 0.0f ? Float.floatToIntBits((float)this.y) : 0;
        int n4 = 31 * (n2 + n3);
        int n5 = this.z != 0.0f ? Float.floatToIntBits((float)this.z) : 0;
        int n6 = 31 * (n4 + n5);
        int n7 = this.originX != 0.0f ? Float.floatToIntBits((float)this.originX) : 0;
        int n8 = 31 * (n6 + n7);
        int n9 = this.originY != 0.0f ? Float.floatToIntBits((float)this.originY) : 0;
        int n10 = 31 * (n8 + n9);
        int n11 = this.originZ != 0.0f ? Float.floatToIntBits((float)this.originZ) : 0;
        int n12 = 31 * (n10 + n11);
        int n13 = this.diffX != 0.0f ? Float.floatToIntBits((float)this.diffX) : 0;
        int n14 = 31 * (n12 + n13);
        int n15 = this.diffY != 0.0f ? Float.floatToIntBits((float)this.diffY) : 0;
        int n16 = 31 * (n14 + n15);
        int n17 = this.diffZ != 0.0f ? Float.floatToIntBits((float)this.diffZ) : 0;
        int n18 = 31 * (31 * (31 * (n16 + n17) + this.color) + this.darkenColor);
        int n19 = this.shape != null ? this.shape.hashCode() : 0;
        int n20 = 31 * (n18 + n19);
        char[] arrc = this.label;
        int n21 = 0;
        if (arrc != null) {
            n21 = Arrays.hashCode((char[])this.label);
        }
        return n20 + n21;
    }

    public BubbleValue set(float f, float f2, float f3) {
        this.x = f;
        this.y = f2;
        this.z = f3;
        this.originX = f;
        this.originY = f2;
        this.originZ = f3;
        this.diffX = 0.0f;
        this.diffY = 0.0f;
        this.diffZ = 0.0f;
        return this;
    }

    public BubbleValue setColor(int n) {
        this.color = n;
        this.darkenColor = ChartUtils.darkenColor(n);
        return this;
    }

    public BubbleValue setLabel(String string2) {
        this.label = string2.toCharArray();
        return this;
    }

    @Deprecated
    public BubbleValue setLabel(char[] arrc) {
        this.label = arrc;
        return this;
    }

    public BubbleValue setShape(ValueShape valueShape) {
        this.shape = valueShape;
        return this;
    }

    public BubbleValue setTarget(float f, float f2, float f3) {
        this.set(this.x, this.y, this.z);
        this.diffX = f - this.originX;
        this.diffY = f2 - this.originY;
        this.diffZ = f3 - this.originZ;
        return this;
    }

    public String toString() {
        return "BubbleValue [x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
    }

    public void update(float f) {
        this.x = this.originX + f * this.diffX;
        this.y = this.originY + f * this.diffY;
        this.z = this.originZ + f * this.diffZ;
    }
}

