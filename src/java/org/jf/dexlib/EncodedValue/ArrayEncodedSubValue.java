/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;

public class ArrayEncodedSubValue
extends EncodedValue {
    private int hashCode = 0;
    public final EncodedValue[] values;

    public ArrayEncodedSubValue(DexFile dexFile, Input input) {
        this.values = new EncodedValue[input.readUnsignedLeb128()];
        for (int i = 0; i < this.values.length; ++i) {
            this.values[i] = EncodedValue.readEncodedValue(dexFile, input);
        }
    }

    public ArrayEncodedSubValue(EncodedValue[] arrencodedValue) {
        this.values = arrencodedValue;
    }

    private void calcHashCode() {
        this.hashCode = 0;
        for (EncodedValue encodedValue : this.values) {
            this.hashCode = 31 * this.hashCode + encodedValue.hashCode();
        }
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        ArrayEncodedSubValue arrayEncodedSubValue = (ArrayEncodedSubValue)encodedValue;
        int n = this.values.length - arrayEncodedSubValue.values.length;
        if (n != 0) {
            return n;
        }
        for (int i = 0; i < this.values.length; ++i) {
            n = this.values[i].compareTo(arrayEncodedSubValue.values[i]);
            if (n == 0) continue;
            return n;
        }
        return n;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_ARRAY;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    public int placeValue(int n) {
        int n2 = n + Leb128Utils.unsignedLeb128Size(this.values.length);
        EncodedValue[] arrencodedValue = this.values;
        int n3 = arrencodedValue.length;
        for (int i = 0; i < n3; ++i) {
            n2 = arrencodedValue[i].placeValue(n2);
        }
        return n2;
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("array_size: 0x" + Integer.toHexString((int)this.values.length) + " (" + this.values.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.values.length);
            EncodedValue[] arrencodedValue = this.values;
            int n = arrencodedValue.length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                EncodedValue encodedValue = arrencodedValue[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n3 = n2 + 1;
                annotatedOutput.annotate(0, stringBuilder.append(n2).append("] array_element").toString());
                annotatedOutput.indent();
                encodedValue.writeValue(annotatedOutput);
                annotatedOutput.deindent();
                n2 = n3;
            }
        } else {
            annotatedOutput.writeUnsignedLeb128(this.values.length);
            EncodedValue[] arrencodedValue = this.values;
            int n = arrencodedValue.length;
            for (int i = 0; i < n; ++i) {
                arrencodedValue[i].writeValue(annotatedOutput);
            }
        }
    }
}

