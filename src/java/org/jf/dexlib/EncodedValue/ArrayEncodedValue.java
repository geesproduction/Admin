/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.ArrayEncodedSubValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class ArrayEncodedValue
extends ArrayEncodedSubValue {
    protected ArrayEncodedValue(DexFile dexFile, Input input) {
        super(dexFile, input);
    }

    public ArrayEncodedValue(EncodedValue[] arrencodedValue) {
        super(arrencodedValue);
    }

    @Override
    public int placeValue(int n) {
        return super.placeValue(n + 1);
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("value_type=" + ValueType.VALUE_ARRAY.name() + ",value_arg=0");
        }
        annotatedOutput.writeByte((int)ValueType.VALUE_ARRAY.value);
        super.writeValue(annotatedOutput);
    }
}

