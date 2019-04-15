/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class DoubleEncodedValue
extends EncodedValue {
    public final double value;

    public DoubleEncodedValue(double d) {
        this.value = d;
    }

    protected DoubleEncodedValue(Input input, byte by) {
        this.value = Double.longBitsToDouble((long)EncodedValueUtils.decodeRightZeroExtendedValue(input.readBytes(by + 1)));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        DoubleEncodedValue doubleEncodedValue = (DoubleEncodedValue)encodedValue;
        return Double.compare((double)this.value, (double)doubleEncodedValue.value);
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_DOUBLE;
    }

    public int hashCode() {
        return (int)Double.doubleToRawLongBits((double)this.value);
    }

    @Override
    public int placeValue(int n) {
        return n + 1 + EncodedValueUtils.getRequiredBytesForRightZeroExtendedValue(Double.doubleToRawLongBits((double)this.value));
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeRightZeroExtendedValue(Double.doubleToRawLongBits((double)this.value));
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_DOUBLE.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: " + this.value);
        }
        annotatedOutput.writeByte(ValueType.VALUE_DOUBLE.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

