/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib.AnnotationSetItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class AnnotationSetRefList
extends Item<AnnotationSetRefList> {
    private AnnotationSetItem[] annotationSets;
    private int hashCode = 0;

    protected AnnotationSetRefList(DexFile dexFile) {
        super(dexFile);
    }

    private AnnotationSetRefList(DexFile dexFile, AnnotationSetItem[] arrannotationSetItem) {
        super(dexFile);
        this.annotationSets = arrannotationSetItem;
    }

    private void calcHashCode() {
        this.hashCode = 0;
        for (AnnotationSetItem annotationSetItem : this.annotationSets) {
            this.hashCode = 31 * this.hashCode + annotationSetItem.hashCode();
        }
    }

    public static AnnotationSetRefList internAnnotationSetRefList(DexFile dexFile, List<AnnotationSetItem> list) {
        Object[] arrobject = new AnnotationSetItem[list.size()];
        list.toArray(arrobject);
        AnnotationSetRefList annotationSetRefList = new AnnotationSetRefList(dexFile, (AnnotationSetItem[])arrobject);
        return dexFile.AnnotationSetRefListsSection.intern(annotationSetRefList);
    }

    public int compareTo(AnnotationSetRefList annotationSetRefList) {
        int n = this.annotationSets.length - annotationSetRefList.annotationSets.length;
        if (n != 0) {
            return n;
        }
        for (int i = 0; i < this.annotationSets.length; ++i) {
            n = this.annotationSets[i].compareTo(annotationSetRefList.annotationSets[i]);
            if (n == 0) continue;
            return n;
        }
        return n;
    }

    public AnnotationSetRefList copyAnnotationSetRefList(DexFile dexFile) {
        ArrayList arrayList = new ArrayList(this.annotationSets.length);
        AnnotationSetItem[] arrannotationSetItem = this.annotationSets;
        int n = arrannotationSetItem.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add((Object)arrannotationSetItem[i].copyAnnotationSetItem(dexFile));
        }
        return AnnotationSetRefList.internAnnotationSetRefList(dexFile, (List<AnnotationSetItem>)arrayList);
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
                if (this.compareTo((AnnotationSetRefList)object) != 0) break block5;
            }
            return true;
        }
        return false;
    }

    public AnnotationSetItem[] getAnnotationSets() {
        return this.annotationSets;
    }

    @Override
    public String getConciseIdentity() {
        return "annotation_set_item @0x" + Integer.toHexString((int)this.getOffset());
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_ANNOTATION_SET_REF_LIST;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected int placeItem(int n) {
        return n + 4 + 4 * this.annotationSets.length;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.annotationSets = new AnnotationSetItem[input.readInt()];
        for (int i = 0; i < this.annotationSets.length; ++i) {
            this.annotationSets[i] = (AnnotationSetItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_SET_ITEM, input.readInt());
        }
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "size: 0x" + Integer.toHexString((int)this.annotationSets.length) + " (" + this.annotationSets.length + ")");
            for (AnnotationSetItem annotationSetItem : this.annotationSets) {
                annotatedOutput.annotate(4, "annotation_set_off: 0x" + Integer.toHexString((int)annotationSetItem.getOffset()));
            }
        }
        annotatedOutput.writeInt(this.annotationSets.length);
        AnnotationSetItem[] arrannotationSetItem = this.annotationSets;
        int n = arrannotationSetItem.length;
        for (int i = 0; i < n; ++i) {
            annotatedOutput.writeInt(arrannotationSetItem[i].getOffset());
        }
    }
}

