/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class LongEncodedValue
extends EncodedValue {
    public final long value;

    public LongEncodedValue(long l) {
        this.value = l;
    }

    protected LongEncodedValue(Input input, byte by) {
        this.value = EncodedValueUtils.decodeSignedIntegralValue(input.readBytes(by + 1));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        LongEncodedValue longEncodedValue = (LongEncodedValue)encodedValue;
        if (this.value < longEncodedValue.value) {
            return -1;
        }
        return this.value > longEncodedValue.value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_LONG;
    }

    public int hashCode() {
        return (int)this.value;
    }

    @Override
    public int placeValue(int n) {
        return 1 + (n + EncodedValueUtils.getRequiredBytesForSignedIntegralValue(this.value));
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeSignedIntegralValue(this.value);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_LONG.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: 0x" + Long.toHexString((long)this.value) + " (" + this.value + ")");
        }
        annotatedOutput.writeByte(ValueType.VALUE_LONG.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

