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
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class TypeIdItem
extends Item<TypeIdItem> {
    private StringIdItem typeDescriptor;

    protected TypeIdItem(DexFile dexFile) {
        super(dexFile);
    }

    private TypeIdItem(DexFile dexFile, StringIdItem stringIdItem) {
        super(dexFile);
        this.typeDescriptor = stringIdItem;
    }

    public static TypeIdItem internTypeIdItem(DexFile dexFile, String string2) {
        StringIdItem stringIdItem = StringIdItem.internStringIdItem(dexFile, string2);
        if (stringIdItem == null) {
            return null;
        }
        TypeIdItem typeIdItem = new TypeIdItem(dexFile, stringIdItem);
        return dexFile.TypeIdsSection.intern(typeIdItem);
    }

    public static TypeIdItem internTypeIdItem(DexFile dexFile, StringIdItem stringIdItem) {
        TypeIdItem typeIdItem = new TypeIdItem(dexFile, stringIdItem);
        return dexFile.TypeIdsSection.intern(typeIdItem);
    }

    public static TypeIdItem lookupTypeIdItem(DexFile dexFile, String string2) {
        StringIdItem stringIdItem = StringIdItem.lookupStringIdItem(dexFile, string2);
        if (stringIdItem == null) {
            return null;
        }
        TypeIdItem typeIdItem = new TypeIdItem(dexFile, stringIdItem);
        return dexFile.TypeIdsSection.getInternedItem(typeIdItem);
    }

    public int compareTo(TypeIdItem typeIdItem) {
        return this.typeDescriptor.compareTo(typeIdItem.typeDescriptor);
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
                TypeIdItem typeIdItem = (TypeIdItem)object;
                if (this.typeDescriptor != typeIdItem.typeDescriptor) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "type_id_item: " + this.getTypeDescriptor();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_TYPE_ID_ITEM;
    }

    public int getRegisterCount() {
        String string2 = this.getTypeDescriptor();
        if (string2.charAt(0) == 'J' || string2.charAt(0) == 'D') {
            return 2;
        }
        return 1;
    }

    public String getTypeDescriptor() {
        return this.typeDescriptor.getStringValue();
    }

    public int hashCode() {
        return this.typeDescriptor.hashCode();
    }

    @Override
    protected int placeItem(int n) {
        return n + 4;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        int n = input.readInt();
        this.typeDescriptor = this.dexFile.StringIdsSection.getItemByIndex(n);
    }

    public void setTypeDescriptor(String string2) {
        this.typeDescriptor.setStringValue(string2);
    }

    public String toShorty() {
        String string2 = this.getTypeDescriptor();
        if (string2.length() > 1) {
            string2 = "L";
        }
        return string2;
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, this.typeDescriptor.getConciseIdentity());
        }
        annotatedOutput.writeInt(this.typeDescriptor.getIndex());
    }
}

