/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringDataItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Utf8Utils;

public class StringIdItem
extends Item<StringIdItem> {
    private StringDataItem stringDataItem;

    protected StringIdItem(DexFile dexFile) {
        super(dexFile);
    }

    protected StringIdItem(DexFile dexFile, StringDataItem stringDataItem) {
        super(dexFile);
        this.stringDataItem = stringDataItem;
    }

    public static StringIdItem internStringIdItem(DexFile dexFile, String string2) {
        StringDataItem stringDataItem = StringDataItem.internStringDataItem(dexFile, string2);
        if (stringDataItem == null) {
            return null;
        }
        StringIdItem stringIdItem = new StringIdItem(dexFile, stringDataItem);
        return dexFile.StringIdsSection.intern(stringIdItem);
    }

    public static StringIdItem lookupStringIdItem(DexFile dexFile, String string2) {
        StringDataItem stringDataItem = StringDataItem.lookupStringDataItem(dexFile, string2);
        if (stringDataItem == null) {
            return null;
        }
        StringIdItem stringIdItem = new StringIdItem(dexFile, stringDataItem);
        return dexFile.StringIdsSection.getInternedItem(stringIdItem);
    }

    public int compareTo(StringIdItem stringIdItem) {
        return this.getStringValue().compareTo(stringIdItem.getStringValue());
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
                StringIdItem stringIdItem = (StringIdItem)object;
                if (this.stringDataItem != stringIdItem.stringDataItem) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "string_id_item: " + Utf8Utils.escapeString(this.getStringValue());
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_STRING_ID_ITEM;
    }

    public StringDataItem getStringDataItem() {
        return this.stringDataItem;
    }

    public String getStringValue() {
        return this.stringDataItem.getStringValue();
    }

    public int hashCode() {
        return this.stringDataItem.hashCode();
    }

    @Override
    protected int placeItem(int n) {
        return n + 4;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        int n = input.readInt();
        this.stringDataItem = (StringDataItem)readContext.getOffsettedItemByOffset(ItemType.TYPE_STRING_DATA_ITEM, n);
    }

    public void setStringValue(String string2) {
        this.stringDataItem.setStringValue(string2);
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, this.stringDataItem.getConciseIdentity());
        }
        annotatedOutput.writeInt(this.stringDataItem.getOffset());
    }
}

