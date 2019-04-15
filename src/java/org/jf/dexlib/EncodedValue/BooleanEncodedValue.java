/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;

public class BooleanEncodedValue
extends EncodedValue {
    public static final BooleanEncodedValue FalseValue;
    public static final BooleanEncodedValue TrueValue;
    public final boolean value;

    static {
        TrueValue = new BooleanEncodedValue(true);
        FalseValue = new BooleanEncodedValue(false);
    }

    private BooleanEncodedValue(boolean bl) {
        this.value = bl;
    }

    protected static BooleanEncodedValue getBooleanEncodedValue(byte by) {
        if (by == 0) {
            return FalseValue;
        }
        if (by == 1) {
            return TrueValue;
        }
        throw new RuntimeException("valueArg must be either 0 or 1");
    }

    public static BooleanEncodedValue getBooleanEncodedValue(boolean bl) {
        if (bl) {
            return TrueValue;
        }
        return FalseValue;
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        BooleanEncodedValue booleanEncodedValue = (BooleanEncodedValue)encodedValue;
        if (this.value == booleanEncodedValue.value) {
            return 0;
        }
        if (this.value) {
            return 1;
        }
        return -1;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_BOOLEAN;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int placeValue(int n) {
        return n + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("value_type=" + ValueType.VALUE_BOOLEAN.name() + ",value=" + Boolean.toString((boolean)this.value));
        }
        byte by = ValueType.VALUE_BOOLEAN.value;
        int n = this.value ? 1 : 0;
        annotatedOutput.writeByte(by | n << 5);
    }
}

