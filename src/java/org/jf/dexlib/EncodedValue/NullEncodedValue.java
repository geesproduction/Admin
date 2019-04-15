/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;

public class NullEncodedValue
extends EncodedValue {
    public static final NullEncodedValue NullValue = new NullEncodedValue();

    private NullEncodedValue() {
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        return 0;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_NULL;
    }

    public int hashCode() {
        return 1;
    }

    @Override
    public int placeValue(int n) {
        return n + 1;
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("value_type=" + ValueType.VALUE_NULL.name() + ",value_arg=0");
        }
        annotatedOutput.writeByte((int)ValueType.VALUE_NULL.value);
    }
}

