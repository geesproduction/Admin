/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collections
 *  java.util.HashMap
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public abstract class Section<T extends Item> {
    static final /* synthetic */ boolean $assertionsDisabled;
    public final DexFile DexFile;
    public final ItemType ItemType;
    protected final ArrayList<T> items;
    protected int offset = 0;
    protected HashMap<T, T> uniqueItems = null;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Section.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected Section(DexFile dexFile, ItemType itemType) {
        this.DexFile = dexFile;
        this.items = new ArrayList();
        this.ItemType = itemType;
    }

    private void buildInternedItemMap() {
        this.uniqueItems = new HashMap();
        for (Item item : this.items) {
            if (!$assertionsDisabled && item == null) {
                throw new AssertionError();
            }
            this.uniqueItems.put((Object)item, (Object)item);
        }
    }

    protected T getInternedItem(T t) {
        if (this.uniqueItems == null) {
            Section.super.buildInternedItemMap();
        }
        return (T)((Item)this.uniqueItems.get(t));
    }

    public List<T> getItems() {
        return this.items;
    }

    public int getOffset() {
        return this.offset;
    }

    protected T intern(T t) {
        if (t == null) {
            return null;
        }
        T t2 = this.getInternedItem(t);
        if (t2 == null) {
            this.uniqueItems.put(t, t);
            this.items.add(t);
            return t;
        }
        return t2;
    }

    protected int placeAt(int n) {
        ArrayList<T> arrayList = this.items;
        if (arrayList.size() > 0) {
            n = AlignmentUtils.alignOffset(n, this.ItemType.ItemAlignment);
            if (!$assertionsDisabled && this.DexFile.getInplace() && n != this.offset) {
                throw new AssertionError();
            }
            this.offset = n;
            int n2 = arrayList.size();
            for (int i = 0; i < n2; ++i) {
                Item item = (Item)arrayList.get(i);
                if (!$assertionsDisabled && item == null) {
                    throw new AssertionError();
                }
                n = item.placeAt(AlignmentUtils.alignOffset(n, this.ItemType.ItemAlignment), i);
            }
        } else {
            this.offset = 0;
        }
        return n;
    }

    protected void readFrom(int n, Input input, ReadContext readContext) {
        ArrayList<T> arrayList = this.items;
        arrayList.ensureCapacity(n);
        for (int i = arrayList.size(); i < n; ++i) {
            arrayList.add(null);
        }
        input.alignTo(this.ItemType.ItemAlignment);
        this.offset = input.getCursor();
        this.readItems(input, readContext);
    }

    protected abstract void readItems(Input var1, ReadContext var2);

    protected void sortSection() {
        Collections.sort(this.items);
    }

    protected void writeTo(AnnotatedOutput annotatedOutput) {
        annotatedOutput.annotate(0, " ");
        annotatedOutput.annotate(0, "-----------------------------");
        annotatedOutput.annotate(0, this.ItemType.TypeName + " section");
        annotatedOutput.annotate(0, "-----------------------------");
        annotatedOutput.annotate(0, " ");
        for (Item item : this.items) {
            if (!$assertionsDisabled && item == null) {
                throw new AssertionError();
            }
            annotatedOutput.alignTo(this.ItemType.ItemAlignment);
            item.writeTo(annotatedOutput);
        }
    }
}

