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
import org.jf.dexlib.EncodedValue.ArrayEncodedSubValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class EncodedArrayItem
extends Item<EncodedArrayItem> {
    private ArrayEncodedSubValue encodedArray;
    private int hashCode = 0;

    protected EncodedArrayItem(DexFile dexFile) {
        super(dexFile);
    }

    private EncodedArrayItem(DexFile dexFile, ArrayEncodedSubValue arrayEncodedSubValue) {
        super(dexFile);
        this.encodedArray = arrayEncodedSubValue;
    }

    private void calcHashCode() {
        this.hashCode = this.encodedArray.hashCode();
    }

    public static EncodedArrayItem internEncodedArrayItem(DexFile dexFile, ArrayEncodedSubValue arrayEncodedSubValue) {
        EncodedArrayItem encodedArrayItem = new EncodedArrayItem(dexFile, arrayEncodedSubValue);
        return dexFile.EncodedArraysSection.intern(encodedArrayItem);
    }

    public int compareTo(EncodedArrayItem encodedArrayItem) {
        return this.encodedArray.compareTo(encodedArrayItem.encodedArray);
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
                EncodedArrayItem encodedArrayItem = (EncodedArrayItem)object;
                if (this.encodedArray.compareTo(encodedArrayItem.encodedArray) != 0) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "encoded_array @0x" + Integer.toHexString((int)this.getOffset());
    }

    public ArrayEncodedSubValue getEncodedArray() {
        return this.encodedArray;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_ENCODED_ARRAY_ITEM;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    @Override
    protected int placeItem(int n) {
        return this.encodedArray.placeValue(n);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.encodedArray = new ArrayEncodedSubValue(this.dexFile, input);
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        this.encodedArray.writeValue(annotatedOutput);
    }
}

