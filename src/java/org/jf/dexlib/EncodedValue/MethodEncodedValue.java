/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class MethodEncodedValue
extends EncodedValue {
    public final MethodIdItem value;

    protected MethodEncodedValue(DexFile dexFile, Input input, byte by) {
        int n = (int)EncodedValueUtils.decodeUnsignedIntegralValue(input.readBytes(by + 1));
        this.value = dexFile.MethodIdsSection.getItemByIndex(n);
    }

    public MethodEncodedValue(MethodIdItem methodIdItem) {
        this.value = methodIdItem;
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        MethodEncodedValue methodEncodedValue = (MethodEncodedValue)encodedValue;
        return this.value.getIndex() - methodEncodedValue.value.getIndex();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_METHOD;
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public int placeValue(int n) {
        return 1 + (n + EncodedValueUtils.getRequiredBytesForUnsignedIntegralValue(this.value.getIndex()));
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeUnsignedIntegralValue(this.value.getIndex());
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_METHOD.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: " + this.value.getMethodString());
        }
        annotatedOutput.writeByte(ValueType.VALUE_METHOD.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

