/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.model;

public final class ValueShape
extends Enum<ValueShape> {
    private static final /* synthetic */ ValueShape[] $VALUES;
    public static final /* enum */ ValueShape CIRCLE = new ValueShape();
    public static final /* enum */ ValueShape DIAMOND;
    public static final /* enum */ ValueShape SQUARE;

    static {
        SQUARE = new ValueShape();
        DIAMOND = new ValueShape();
        ValueShape[] arrvalueShape = new ValueShape[]{CIRCLE, SQUARE, DIAMOND};
        $VALUES = arrvalueShape;
    }

    public static ValueShape valueOf(String string2) {
        return (ValueShape)Enum.valueOf(ValueShape.class, (String)string2);
    }

    public static ValueShape[] values() {
        return (ValueShape[])$VALUES.clone();
    }
}

