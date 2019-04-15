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

public class ByteEncodedValue
extends EncodedValue {
    public final byte value;

    public ByteEncodedValue(byte by) {
        this.value = by;
    }

    protected ByteEncodedValue(Input input) {
        this.value = (byte)EncodedValueUtils.decodeSignedIntegralValue(input.readBytes(1));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        ByteEncodedValue byteEncodedValue = (ByteEncodedValue)encodedValue;
        if (this.value < byteEncodedValue.value) {
            return -1;
        }
        return this.value > byteEncodedValue.value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_BYTE;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int placeValue(int n) {
        return n + 2;
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_BYTE.name() + ",value_arg=0");
            annotatedOutput.annotate(1, "value: 0x" + Integer.toHexString((int)this.value) + " (" + this.value + ")");
        }
        annotatedOutput.writeByte((int)ValueType.VALUE_BYTE.value);
        annotatedOutput.writeByte((int)this.value);
    }
}

