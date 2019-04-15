/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib;

import org.jf.dexlib.AnnotationVisibility;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.AnnotationEncodedSubValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class AnnotationItem
extends Item<AnnotationItem> {
    private AnnotationEncodedSubValue annotationValue;
    private int hashCode = 0;
    private AnnotationVisibility visibility;

    protected AnnotationItem(DexFile dexFile) {
        super(dexFile);
    }

    private AnnotationItem(DexFile dexFile, AnnotationVisibility annotationVisibility, AnnotationEncodedSubValue annotationEncodedSubValue) {
        super(dexFile);
        this.visibility = annotationVisibility;
        this.annotationValue = annotationEncodedSubValue;
    }

    private void calcHashCode() {
        this.hashCode = this.visibility.value;
        this.hashCode = 31 * this.hashCode + this.annotationValue.hashCode();
    }

    public static AnnotationItem internAnnotationItem(DexFile dexFile, AnnotationVisibility annotationVisibility, AnnotationEncodedSubValue annotationEncodedSubValue) {
        AnnotationItem annotationItem = new AnnotationItem(dexFile, annotationVisibility, annotationEncodedSubValue);
        return dexFile.AnnotationsSection.intern(annotationItem);
    }

    public int compareTo(AnnotationItem annotationItem) {
        int n = this.visibility.value - annotationItem.visibility.value;
        if (n == 0) {
            n = this.annotationValue.compareTo(annotationItem.annotationValue);
        }
        return n;
    }

    public AnnotationItem copyAnnotationItem(DexFile dexFile) {
        return AnnotationItem.internAnnotationItem(dexFile, this.visibility, this.annotationValue.copyAnnotationEncodedSubValue(dexFile));
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5 : {
            block4 : {
                if (this == object) break block4;
                if (object == null || !this.getClass().equals((Object)object.getClass())) {
                    return false;
                }
                AnnotationItem annotationItem = (AnnotationItem)object;
                if (this.visibility != annotationItem.visibility || !this.annotationValue.equals(annotationItem.annotationValue)) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "annotation_item @0x" + Integer.toHexString((int)this.getOffset());
    }

    public AnnotationEncodedSubValue getEncodedAnnotation() {
        return this.annotationValue;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_ANNOTATION_ITEM;
    }

    public AnnotationVisibility getVisibility() {
        return this.visibility;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected int placeItem(int n) {
        return this.annotationValue.placeValue(n + 1);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.visibility = AnnotationVisibility.fromByte(input.readByte());
        this.annotationValue = new AnnotationEncodedSubValue(this.dexFile, input);
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("visibility: " + this.visibility.name());
            annotatedOutput.writeByte((int)this.visibility.value);
            this.annotationValue.writeValue(annotatedOutput);
            return;
        }
        annotatedOutput.writeByte((int)this.visibility.value);
        this.annotationValue.writeValue(annotatedOutput);
    }
}

