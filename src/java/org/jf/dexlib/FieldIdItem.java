/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 */
package org.jf.dexlib;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class FieldIdItem
extends Item<FieldIdItem> {
    static final /* synthetic */ boolean $assertionsDisabled;
    String cachedFieldString;
    public TypeIdItem classType;
    public StringIdItem fieldName;
    public TypeIdItem fieldType;
    private int hashCode;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !FieldIdItem.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected FieldIdItem(DexFile dexFile) {
        super(dexFile);
        this.hashCode = 0;
        this.cachedFieldString = null;
    }

    private FieldIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeIdItem typeIdItem2, StringIdItem stringIdItem) {
        super(dexFile);
        if (!$assertionsDisabled && typeIdItem.dexFile != dexFile) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && typeIdItem2.dexFile != dexFile) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && stringIdItem.dexFile != dexFile) {
            throw new AssertionError();
        }
        this.classType = typeIdItem;
        this.fieldType = typeIdItem2;
        this.fieldName = stringIdItem;
    }

    private void calcHashCode() {
        this.hashCode = this.classType.hashCode();
        this.hashCode = 31 * this.hashCode + this.fieldType.hashCode();
        this.hashCode = 31 * this.hashCode + this.fieldName.hashCode();
    }

    public static FieldIdItem internFieldIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeIdItem typeIdItem2, StringIdItem stringIdItem) {
        FieldIdItem fieldIdItem = new FieldIdItem(dexFile, typeIdItem, typeIdItem2, stringIdItem);
        return dexFile.FieldIdsSection.intern(fieldIdItem);
    }

    public static FieldIdItem lookupFieldIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeIdItem typeIdItem2, StringIdItem stringIdItem) {
        FieldIdItem fieldIdItem = new FieldIdItem(dexFile, typeIdItem, typeIdItem2, stringIdItem);
        return dexFile.FieldIdsSection.getInternedItem(fieldIdItem);
    }

    public int compareTo(FieldIdItem fieldIdItem) {
        int n = this.classType.compareTo(fieldIdItem.classType);
        if (n != 0) {
            return n;
        }
        int n2 = this.fieldName.compareTo(fieldIdItem.fieldName);
        if (n2 != 0) {
            return n2;
        }
        return this.fieldType.compareTo(fieldIdItem.fieldType);
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
                FieldIdItem fieldIdItem = (FieldIdItem)object;
                if (this.classType != fieldIdItem.classType || this.fieldType != fieldIdItem.fieldType || this.fieldName != fieldIdItem.fieldName) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return this.getFieldString();
    }

    public TypeIdItem getContainingClass() {
        return this.classType;
    }

    public StringIdItem getFieldName() {
        return this.fieldName;
    }

    public String getFieldString() {
        if (this.cachedFieldString == null) {
            String string2 = this.classType.getTypeDescriptor();
            String string3 = this.fieldName.getStringValue();
            String string4 = this.fieldType.getTypeDescriptor();
            StringBuffer stringBuffer = new StringBuffer(3 + (string2.length() + string3.length() + string4.length()));
            stringBuffer.append(string2);
            stringBuffer.append("->");
            stringBuffer.append(string3);
            stringBuffer.append(":");
            stringBuffer.append(string4);
            this.cachedFieldString = stringBuffer.toString();
        }
        return this.cachedFieldString;
    }

    public TypeIdItem getFieldType() {
        return this.fieldType;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_FIELD_ID_ITEM;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    public FieldIdItem internFieldIdItem(DexFile dexFile) {
        FieldIdItem fieldIdItem = new FieldIdItem(dexFile, TypeIdItem.internTypeIdItem(dexFile, this.classType.getTypeDescriptor()), TypeIdItem.internTypeIdItem(dexFile, this.fieldType.getTypeDescriptor()), StringIdItem.internStringIdItem(dexFile, this.fieldName.getStringValue()));
        return dexFile.FieldIdsSection.intern(fieldIdItem);
    }

    @Override
    protected int placeItem(int n) {
        return n + 8;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.classType = this.dexFile.TypeIdsSection.getItemByIndex(input.readShort());
        this.fieldType = this.dexFile.TypeIdsSection.getItemByIndex(input.readShort());
        this.fieldName = this.dexFile.StringIdsSection.getItemByIndex(input.readInt());
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(2, "class_type: " + this.classType.getTypeDescriptor());
            annotatedOutput.annotate(2, "field_type: " + this.fieldType.getTypeDescriptor());
            annotatedOutput.annotate(4, "field_name: " + this.fieldName.getStringValue());
        }
        annotatedOutput.writeShort(this.classType.getIndex());
        annotatedOutput.writeShort(this.fieldType.getIndex());
        annotatedOutput.writeInt(this.fieldName.getIndex());
    }
}

