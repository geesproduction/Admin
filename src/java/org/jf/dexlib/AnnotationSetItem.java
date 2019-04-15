/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Comparator
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.jf.dexlib.AnnotationItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.AnnotationEncodedSubValue;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class AnnotationSetItem
extends Item<AnnotationSetItem> {
    private AnnotationItem[] annotations;
    private int hashCode = 0;

    protected AnnotationSetItem(DexFile dexFile) {
        super(dexFile);
    }

    private AnnotationSetItem(DexFile dexFile, AnnotationItem[] arrannotationItem) {
        super(dexFile);
        this.annotations = arrannotationItem;
    }

    private void calcHashCode() {
        this.hashCode = 0;
        for (AnnotationItem annotationItem : this.annotations) {
            this.hashCode = 31 * this.hashCode + annotationItem.hashCode();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static AnnotationSetItem internAnnotationSetItem(DexFile dexFile, List<AnnotationItem> list) {
        AnnotationSetItem annotationSetItem;
        if (list == null) {
            annotationSetItem = new AnnotationSetItem(dexFile, new AnnotationItem[0]);
            do {
                return dexFile.AnnotationSetsSection.intern(annotationSetItem);
                break;
            } while (true);
        }
        Object[] arrobject = new AnnotationItem[list.size()];
        list.toArray(arrobject);
        annotationSetItem = new AnnotationSetItem(dexFile, (AnnotationItem[])arrobject);
        return dexFile.AnnotationSetsSection.intern(annotationSetItem);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int compareTo(AnnotationSetItem annotationSetItem) {
        if (annotationSetItem == null) {
            return 1;
        }
        int n = this.annotations.length - annotationSetItem.annotations.length;
        if (n != 0) return n;
        int n2 = 0;
        while (n2 < this.annotations.length) {
            n = this.annotations[n2].compareTo(annotationSetItem.annotations[n2]);
            if (n != 0) return n;
            ++n2;
        }
        return n;
    }

    public AnnotationSetItem copyAnnotationSetItem(DexFile dexFile) {
        ArrayList arrayList = new ArrayList(this.annotations.length);
        AnnotationItem[] arrannotationItem = this.annotations;
        int n = arrannotationItem.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add((Object)arrannotationItem[i].copyAnnotationItem(dexFile));
        }
        return AnnotationSetItem.internAnnotationSetItem(dexFile, (List<AnnotationItem>)arrayList);
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
                if (this.compareTo((AnnotationSetItem)object) != 0) break block5;
            }
            return true;
        }
        return false;
    }

    public AnnotationItem[] getAnnotations() {
        return this.annotations;
    }

    @Override
    public String getConciseIdentity() {
        return "annotation_set_item @0x" + Integer.toHexString((int)this.getOffset());
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_ANNOTATION_SET_ITEM;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected int placeItem(int n) {
        return n + 4 + 4 * this.annotations.length;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.annotations = new AnnotationItem[input.readInt()];
        for (int i = 0; i < this.annotations.length; ++i) {
            this.annotations[i] = (AnnotationItem)readContext.getOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_ITEM, input.readInt());
        }
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        Arrays.sort((Object[])this.annotations, (Comparator)new Comparator<AnnotationItem>(){

            public int compare(AnnotationItem annotationItem, AnnotationItem annotationItem2) {
                int n;
                int n2 = annotationItem.getEncodedAnnotation().annotationType.getIndex();
                if (n2 < (n = annotationItem2.getEncodedAnnotation().annotationType.getIndex())) {
                    return -1;
                }
                return n2 != n;
            }
        });
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "size: 0x" + Integer.toHexString((int)this.annotations.length) + " (" + this.annotations.length + ")");
            for (AnnotationItem annotationItem : this.annotations) {
                annotatedOutput.annotate(4, "annotation_off: 0x" + Integer.toHexString((int)annotationItem.getOffset()) + " - " + annotationItem.getEncodedAnnotation().annotationType.getTypeDescriptor());
            }
        }
        annotatedOutput.writeInt(this.annotations.length);
        AnnotationItem[] arrannotationItem = this.annotations;
        int n = arrannotationItem.length;
        for (int i = 0; i < n; ++i) {
            annotatedOutput.writeInt(arrannotationItem[i].getOffset());
        }
    }

}

