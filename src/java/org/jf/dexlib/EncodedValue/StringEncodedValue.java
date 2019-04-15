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
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Utf8Utils;

public class StringEncodedValue
extends EncodedValue {
    public final StringIdItem value;

    protected StringEncodedValue(DexFile dexFile, Input input, byte by) {
        int n = (int)EncodedValueUtils.decodeUnsignedIntegralValue(input.readBytes(by + 1));
        this.value = dexFile.StringIdsSection.getItemByIndex(n);
    }

    public StringEncodedValue(StringIdItem stringIdItem) {
        this.value = stringIdItem;
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        StringEncodedValue stringEncodedValue = (StringEncodedValue)encodedValue;
        return this.value.getIndex() - stringEncodedValue.value.getIndex();
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_STRING;
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
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_STRING.name() + ",value_arg=" + (-1 + arrby.length));
            annotatedOutput.annotate(arrby.length, "value: \"" + Utf8Utils.escapeString(this.value.getStringValue()) + "\"");
        }
        annotatedOutput.writeByte(ValueType.VALUE_STRING.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

