/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;

public class AnnotationEncodedSubValue
extends EncodedValue {
    public final TypeIdItem annotationType;
    private int hashCode = 0;
    public final StringIdItem[] names;
    public final EncodedValue[] values;

    public AnnotationEncodedSubValue(DexFile dexFile, Input input) {
        this.annotationType = dexFile.TypeIdsSection.getItemByIndex(input.readUnsignedLeb128());
        this.names = new StringIdItem[input.readUnsignedLeb128()];
        this.values = new EncodedValue[this.names.length];
        for (int i = 0; i < this.names.length; ++i) {
            this.names[i] = dexFile.StringIdsSection.getItemByIndex(input.readUnsignedLeb128());
            this.values[i] = EncodedValue.readEncodedValue(dexFile, input);
        }
    }

    public AnnotationEncodedSubValue(TypeIdItem typeIdItem, StringIdItem[] arrstringIdItem, EncodedValue[] arrencodedValue) {
        this.annotationType = typeIdItem;
        if (arrstringIdItem.length != arrencodedValue.length) {
            throw new RuntimeException("The names and values parameters must be the same length");
        }
        this.names = arrstringIdItem;
        this.values = arrencodedValue;
    }

    private void calcHashCode() {
        this.hashCode = this.annotationType.hashCode();
        for (int i = 0; i < this.names.length; ++i) {
            this.hashCode = 31 * this.hashCode + this.names[i].hashCode();
            this.hashCode = 31 * this.hashCode + this.values[i].hashCode();
        }
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        AnnotationEncodedSubValue annotationEncodedSubValue = (AnnotationEncodedSubValue)encodedValue;
        int n = this.annotationType.compareTo(annotationEncodedSubValue.annotationType);
        if (n != 0) {
            return n;
        }
        int n2 = this.names.length - annotationEncodedSubValue.names.length;
        if (n2 != 0) {
            return n2;
        }
        for (int i = 0; i < this.names.length; ++i) {
            int n3 = this.names[i].compareTo(annotationEncodedSubValue.names[i]);
            if (n3 != 0) {
                return n3;
            }
            n2 = this.values[i].compareTo(annotationEncodedSubValue.values[i]);
            if (n2 == 0) continue;
            return n2;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public AnnotationEncodedSubValue copyAnnotationEncodedSubValue(DexFile dexFile) {
        TypeIdItem typeIdItem;
        if (this.annotationType == null) {
            typeIdItem = null;
            do {
                return new AnnotationEncodedSubValue(typeIdItem, this.copyNames(dexFile), this.copyValues(dexFile));
                break;
            } while (true);
        }
        typeIdItem = TypeIdItem.internTypeIdItem(dexFile, this.annotationType.getTypeDescriptor());
        return new AnnotationEncodedSubValue(typeIdItem, this.copyNames(dexFile), this.copyValues(dexFile));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public StringIdItem[] copyNames(DexFile dexFile) {
        if (this.names == null) {
            return null;
        }
        StringIdItem[] arrstringIdItem = new StringIdItem[this.names.length];
        int n = 0;
        while (n < arrstringIdItem.length) {
            arrstringIdItem[n] = StringIdItem.internStringIdItem(dexFile, this.names[n].getStringValue());
            ++n;
        }
        return arrstringIdItem;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public EncodedValue[] copyValues(DexFile dexFile) {
        if (this.values == null) {
            return null;
        }
        EncodedValue[] arrencodedValue = new EncodedValue[this.values.length];
        int n = 0;
        while (n < arrencodedValue.length) {
            arrencodedValue[n] = EncodedValue.copyEncodedValue(dexFile, this.values[n]);
            ++n;
        }
        return arrencodedValue;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_ANNOTATION;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    public int placeValue(int n) {
        int n2 = n + Leb128Utils.unsignedLeb128Size(this.annotationType.getIndex()) + Leb128Utils.unsignedLeb128Size(this.names.length);
        for (int i = 0; i < this.names.length; ++i) {
            int n3 = n2 + Leb128Utils.unsignedLeb128Size(this.names[i].getIndex());
            n2 = this.values[i].placeValue(n3);
        }
        return n2;
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        annotatedOutput.annotate("annotation_type: " + this.annotationType.getTypeDescriptor());
        annotatedOutput.writeUnsignedLeb128(this.annotationType.getIndex());
        annotatedOutput.annotate("element_count: 0x" + Integer.toHexString((int)this.names.length) + " (" + this.names.length + ")");
        annotatedOutput.writeUnsignedLeb128(this.names.length);
        for (int i = 0; i < this.names.length; ++i) {
            annotatedOutput.annotate(0, "[" + i + "] annotation_element");
            annotatedOutput.indent();
            annotatedOutput.annotate("element_name: " + this.names[i].getStringValue());
            annotatedOutput.writeUnsignedLeb128(this.names[i].getIndex());
            annotatedOutput.annotate(0, "element_value:");
            annotatedOutput.indent();
            this.values[i].writeValue(annotatedOutput);
            annotatedOutput.deindent();
            annotatedOutput.deindent();
        }
    }
}

