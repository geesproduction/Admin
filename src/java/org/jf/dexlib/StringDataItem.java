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

import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;
import org.jf.dexlib.Util.Utf8Utils;

public class StringDataItem
extends Item<StringDataItem> {
    private int hashCode = 0;
    private String stringValue;

    protected StringDataItem(DexFile dexFile) {
        super(dexFile);
    }

    private StringDataItem(DexFile dexFile, String string2) {
        super(dexFile);
        this.stringValue = string2;
    }

    private void calcHashCode() {
        this.hashCode = this.getStringValue().hashCode();
    }

    public static StringDataItem internStringDataItem(DexFile dexFile, String string2) {
        StringDataItem stringDataItem = new StringDataItem(dexFile, string2);
        return dexFile.StringDataSection.intern(stringDataItem);
    }

    public static StringDataItem lookupStringDataItem(DexFile dexFile, String string2) {
        StringDataItem stringDataItem = new StringDataItem(dexFile, string2);
        return dexFile.StringDataSection.getInternedItem(stringDataItem);
    }

    public int compareTo(StringDataItem stringDataItem) {
        return this.getStringValue().compareTo(stringDataItem.getStringValue());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || !this.getClass().equals((Object)object.getClass())) {
            return false;
        }
        StringDataItem stringDataItem = (StringDataItem)object;
        return this.getStringValue().equals((Object)stringDataItem.getStringValue());
    }

    @Override
    public String getConciseIdentity() {
        return "string_data_item: \"" + Utf8Utils.escapeString(this.getStringValue()) + "\"";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_STRING_DATA_ITEM;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected int placeItem(int n) {
        return 1 + (n + Leb128Utils.unsignedLeb128Size(this.stringValue.length()) + Utf8Utils.stringToUtf8Bytes(this.stringValue).length);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        input.readUnsignedLeb128();
        this.stringValue = input.realNullTerminatedUtf8String();
    }

    public void setStringValue(String string2) {
        this.stringValue = string2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        byte[] arrby = Utf8Utils.stringToUtf8Bytes(this.stringValue);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("string_size: 0x" + Integer.toHexString((int)this.stringValue.length()) + " (" + this.stringValue.length() + ")");
            annotatedOutput.writeUnsignedLeb128(this.stringValue.length());
            annotatedOutput.annotate(1 + arrby.length, "string_data: \"" + Utf8Utils.escapeString(this.stringValue) + "\"");
        } else {
            annotatedOutput.writeUnsignedLeb128(this.stringValue.length());
        }
        annotatedOutput.write(arrby);
        annotatedOutput.writeByte(0);
    }
}

