/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.Util.SparseArray;

public final class ValueType
extends Enum<ValueType> {
    private static final /* synthetic */ ValueType[] $VALUES;
    public static final /* enum */ ValueType VALUE_ANNOTATION;
    public static final /* enum */ ValueType VALUE_ARRAY;
    public static final /* enum */ ValueType VALUE_BOOLEAN;
    public static final /* enum */ ValueType VALUE_BYTE;
    public static final /* enum */ ValueType VALUE_CHAR;
    public static final /* enum */ ValueType VALUE_DOUBLE;
    public static final /* enum */ ValueType VALUE_ENUM;
    public static final /* enum */ ValueType VALUE_FIELD;
    public static final /* enum */ ValueType VALUE_FLOAT;
    public static final /* enum */ ValueType VALUE_INT;
    public static final /* enum */ ValueType VALUE_LONG;
    public static final /* enum */ ValueType VALUE_METHOD;
    public static final /* enum */ ValueType VALUE_NULL;
    public static final /* enum */ ValueType VALUE_SHORT;
    public static final /* enum */ ValueType VALUE_STRING;
    public static final /* enum */ ValueType VALUE_TYPE;
    private static final SparseArray<ValueType> valueTypeIntegerMap;
    public final byte value;

    static {
        VALUE_BYTE = new ValueType(0);
        VALUE_SHORT = new ValueType(2);
        VALUE_CHAR = new ValueType(3);
        VALUE_INT = new ValueType(4);
        VALUE_LONG = new ValueType(6);
        VALUE_FLOAT = new ValueType(16);
        VALUE_DOUBLE = new ValueType(17);
        VALUE_STRING = new ValueType(23);
        VALUE_TYPE = new ValueType(24);
        VALUE_FIELD = new ValueType(25);
        VALUE_METHOD = new ValueType(26);
        VALUE_ENUM = new ValueType(27);
        VALUE_ARRAY = new ValueType(28);
        VALUE_ANNOTATION = new ValueType(29);
        VALUE_NULL = new ValueType(30);
        VALUE_BOOLEAN = new ValueType(31);
        ValueType[] arrvalueType = new ValueType[]{VALUE_BYTE, VALUE_SHORT, VALUE_CHAR, VALUE_INT, VALUE_LONG, VALUE_FLOAT, VALUE_DOUBLE, VALUE_STRING, VALUE_TYPE, VALUE_FIELD, VALUE_METHOD, VALUE_ENUM, VALUE_ARRAY, VALUE_ANNOTATION, VALUE_NULL, VALUE_BOOLEAN};
        $VALUES = arrvalueType;
        valueTypeIntegerMap = new SparseArray(16);
        for (ValueType valueType : ValueType.values()) {
            valueTypeIntegerMap.put(valueType.value, valueType);
        }
    }

    private ValueType(byte by) {
        this.value = by;
    }

    public static ValueType fromByte(byte by) {
        return valueTypeIntegerMap.get(by);
    }

    public static ValueType valueOf(String string2) {
        return (ValueType)Enum.valueOf(ValueType.class, (String)string2);
    }

    public static ValueType[] values() {
        return (ValueType[])$VALUES.clone();
    }
}

