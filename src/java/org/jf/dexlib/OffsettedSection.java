/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
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
import org.jf.dexlib.Util.Input;

public class OffsettedSection<T extends Item>
extends Section<T> {
    static final /* synthetic */ boolean $assertionsDisabled;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !OffsettedSection.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public OffsettedSection(DexFile dexFile, ItemType itemType) {
        super(dexFile, itemType);
    }

    @Override
    public void readItems(Input input, ReadContext readContext) {
        ArrayList arrayList = this.items;
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            if (!$assertionsDisabled && arrayList.get(i) != null) {
                throw new AssertionError();
            }
            input.alignTo(this.ItemType.ItemAlignment);
            Item item = ItemFactory.makeItem(this.ItemType, this.DexFile);
            arrayList.set(i, (Object)item);
            item.readFrom(input, i, readContext);
        }
        readContext.setItemsForSection(this.ItemType, (ArrayList<? extends Item>)arrayList);
    }
}

