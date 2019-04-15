/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.AnnotationEncodedSubValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class AnnotationEncodedValue
extends AnnotationEncodedSubValue {
    protected AnnotationEncodedValue(DexFile dexFile, Input input) {
        super(dexFile, input);
    }

    public AnnotationEncodedValue(TypeIdItem typeIdItem, StringIdItem[] arrstringIdItem, EncodedValue[] arrencodedValue) {
        super(typeIdItem, arrstringIdItem, arrencodedValue);
    }

    public AnnotationEncodedValue copyAnnotationEncodedValue(DexFile dexFile) {
        return new AnnotationEncodedValue(TypeIdItem.internTypeIdItem(dexFile, this.annotationType.getTypeDescriptor()), this.copyNames(dexFile), this.copyValues(dexFile));
    }

    @Override
    public int placeValue(int n) {
        return super.placeValue(n + 1);
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("value_type=" + ValueType.VALUE_ANNOTATION.name() + ",value_arg=0");
        }
        annotatedOutput.writeByte((int)ValueType.VALUE_ANNOTATION.value);
        super.writeValue(annotatedOutput);
    }
}

