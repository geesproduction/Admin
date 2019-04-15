/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class IntEncodedValue
extends EncodedValue {
    public final int value;

    public IntEncodedValue(int n) {
        this.value = n;
    }

    protected IntEncodedValue(Input input, byte by) {
        this.value = (int)EncodedValueUtils.decodeSignedIntegralValue(input.readBytes(by + 1));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        IntEncodedValue intEncodedValue = (IntEncodedValue)encodedValue;
        if (this.value < intEncodedValue.value) {
            return -1;
        }
        return this.value > intEncodedValue.value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_INT;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int placeValue(int n) {
        return 1 + (n + EncodedValueUtils.getRequiredBytesForSignedIntegralValue(this.value));
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeSignedIntegralValue(this.value);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_INT.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: 0x" + Integer.toHexString((int)this.value) + " (" + this.value + ")");
        }
        annotatedOutput.writeByte(ValueType.VALUE_INT.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

