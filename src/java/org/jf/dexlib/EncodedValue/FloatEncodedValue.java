/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Float
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class FloatEncodedValue
extends EncodedValue {
    public final float value;

    public FloatEncodedValue(float f) {
        this.value = f;
    }

    protected FloatEncodedValue(Input input, byte by) {
        this.value = Float.intBitsToFloat((int)((int)(0xFFFFFFFFL & EncodedValueUtils.decodeRightZeroExtendedValue(input.readBytes(by + 1)) >> 32)));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        FloatEncodedValue floatEncodedValue = (FloatEncodedValue)encodedValue;
        return Float.compare((float)this.value, (float)floatEncodedValue.value);
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_FLOAT;
    }

    public int hashCode() {
        return Float.floatToRawIntBits((float)this.value);
    }

    @Override
    public int placeValue(int n) {
        return n + 1 + EncodedValueUtils.getRequiredBytesForRightZeroExtendedValue((long)Float.floatToRawIntBits((float)this.value) << 32);
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeRightZeroExtendedValue((long)Float.floatToRawIntBits((float)this.value) << 32);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_FLOAT.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: " + this.value);
        }
        annotatedOutput.writeByte(ValueType.VALUE_FLOAT.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

