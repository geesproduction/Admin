/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 */
package org.jf.dexlib;

import java.util.ArrayList;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemFactory;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Section;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;

public class IndexedSection<T extends Item>
extends Section<T> {
    public IndexedSection(DexFile dexFile, ItemType itemType) {
        super(dexFile, itemType);
    }

    public T getItemByIndex(int n) {
        Item item;
        try {
            item = (Item)this.items.get(n);
        }
        catch (Exception exception) {
            throw ExceptionWithContext.withContext(exception, "Error occured while retrieving the " + this.ItemType.TypeName + " item at index " + n);
        }
        return (T)item;
    }

    public T getOptionalItemByIndex(int n) {
        if (n == -1) {
            return null;
        }
        return this.getItemByIndex(n);
    }

    @Override
    protected void readItems(Input input, ReadContext readContext) {
        ArrayList arrayList = this.items;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Item item = ItemFactory.makeItem(this.ItemType, this.DexFile);
            arrayList.set(i, (Object)item);
            item.readFrom(input, i, readContext);
        }
    }
}

