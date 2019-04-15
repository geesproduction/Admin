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
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class TypeEncodedValue
extends EncodedValue {
    public final TypeIdItem value;

    protected TypeEncodedValue(DexFile dexFile, Input input, byte by) {
        int n = (int)EncodedValueUtils.decodeUnsignedIntegralValue(input.readBytes(by + 1));
        this.value = dexFile.TypeIdsSection.getItemByIndex(n);
    }

    public TypeEncodedValue(TypeIdItem typeIdItem) {
        this.value = typeIdItem;
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        TypeEncodedValue typeEncodedValue = (TypeEncodedValue)encodedValue;
        return this.value.getIndex() - typeEncodedValue.value.getIndex();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_TYPE;
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
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_TYPE.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: " + this.value.getTypeDescriptor());
        }
        annotatedOutput.writeByte(ValueType.VALUE_TYPE.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

