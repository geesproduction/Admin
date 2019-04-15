/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.ArrayList
 */
package org.jf.dexlib;

import java.util.ArrayList;
import org.jf.dexlib.AnnotationDirectoryItem;
import org.jf.dexlib.AnnotationItem;
import org.jf.dexlib.AnnotationSetItem;
import org.jf.dexlib.AnnotationSetRefList;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.EncodedArrayItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.StringDataItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.SparseArray;

public class ReadContext {
    static final /* synthetic */ boolean $assertionsDisabled;
    private SparseArray<AnnotationDirectoryItem> annotationDirectoryItems = new SparseArray(0);
    private SparseArray<AnnotationItem> annotationItems = new SparseArray(0);
    private SparseArray<AnnotationSetItem> annotationSetItems = new SparseArray(0);
    private SparseArray<AnnotationSetRefList> annotationSetRefLists = new SparseArray(0);
    private SparseArray<ClassDataItem> classDataItems = new SparseArray(0);
    private SparseArray<CodeItem> codeItems = new SparseArray(0);
    private SparseArray<DebugInfoItem> debugInfoItems = new SparseArray(0);
    private SparseArray<EncodedArrayItem> encodedArrayItems = new SparseArray(0);
    private SparseArray[] itemsByType;
    private int[] sectionOffsets;
    private int[] sectionSizes;
    private SparseArray<StringDataItem> stringDataItems = new SparseArray(0);
    private SparseArray<TypeListItem> typeListItems = new SparseArray(0);

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ReadContext.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public ReadContext() {
        SparseArray[] arrsparseArray = new SparseArray[]{null, null, null, null, null, null, this.typeListItems, this.annotationSetRefLists, this.annotationSetItems, this.classDataItems, this.codeItems, this.stringDataItems, this.debugInfoItems, this.annotationItems, this.encodedArrayItems, this.annotationDirectoryItems, null, null};
        this.itemsByType = arrsparseArray;
        this.sectionSizes = new int[18];
        this.sectionOffsets = new int[18];
        for (int i = 0; i < 18; ++i) {
            this.sectionSizes[i] = -1;
            this.sectionOffsets[i] = -1;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void addSection(ItemType itemType, int n, int n2) {
        int n3;
        int n4 = this.sectionSizes[itemType.SectionIndex];
        if (n4 == -1) {
            this.sectionSizes[itemType.SectionIndex] = n;
        } else if (n4 != n) {
            throw new RuntimeException("The section size in the header and map for item type " + (Object)((Object)itemType) + " do not match");
        }
        if ((n3 = this.sectionOffsets[itemType.SectionIndex]) == -1) {
            this.sectionOffsets[itemType.SectionIndex] = n2;
            return;
        } else {
            if (n3 == n2) return;
            {
                throw new RuntimeException("The section offset in the header and map for item type " + (Object)((Object)itemType) + " do not match");
            }
        }
    }

    public Item getOffsettedItemByOffset(ItemType itemType, int n) {
        if (!$assertionsDisabled && itemType.isIndexedItem()) {
            throw new AssertionError();
        }
        Item item = (Item)this.itemsByType[itemType.SectionIndex].get(n);
        if (item == null) {
            Object[] arrobject = new Object[]{itemType.TypeName, n};
            throw new ExceptionWithContext(String.format((String)"Could not find the %s item at offset %#x", (Object[])arrobject));
        }
        return item;
    }

    public Item getOptionalOffsettedItemByOffset(ItemType itemType, int n) {
        if (!$assertionsDisabled && itemType.isIndexedItem()) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && itemType.isIndexedItem()) {
            throw new AssertionError();
        }
        Item item = (Item)this.itemsByType[itemType.SectionIndex].get(n);
        if (item == null && n != 0) {
            Object[] arrobject = new Object[]{itemType.TypeName, n};
            throw new ExceptionWithContext(String.format((String)"Could not find the %s item at offset %#x", (Object[])arrobject));
        }
        return item;
    }

    public int getSectionOffset(ItemType itemType) {
        return this.sectionOffsets[itemType.SectionIndex];
    }

    public int getSectionSize(ItemType itemType) {
        return this.sectionSizes[itemType.SectionIndex];
    }

    public void setItemsForSection(ItemType itemType, ArrayList<? extends Item> arrayList) {
        if (!$assertionsDisabled && itemType.isIndexedItem()) {
            throw new AssertionError();
        }
        SparseArray sparseArray = this.itemsByType[itemType.SectionIndex];
        sparseArray.ensureCapacity(arrayList.size());
        for (Item item : arrayList) {
            sparseArray.append(item.getOffset(), item);
        }
    }
}

