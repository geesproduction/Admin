/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code;

import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.ReferenceType;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.NumberUtils;

public abstract class InstructionWithReference
extends Instruction {
    private Item referencedItem;

    protected InstructionWithReference(Opcode opcode, Item item) {
        super(opcode);
        this.referencedItem = item;
        InstructionWithReference.super.checkReferenceType();
    }

    protected InstructionWithReference(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        InstructionWithReference.super.lookupReferencedItem(dexFile, opcode, this.getReferencedItemIndex(arrby, n));
    }

    private void checkReferenceType() {
        switch (1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[this.opcode.referenceType.ordinal()]) {
            default: {
                if (this.referencedItem == null) break;
                throw new RuntimeException(this.referencedItem.getClass().getSimpleName() + " is invalid for opcode " + this.opcode.name + ". This opcode does not reference an item");
            }
            case 1: {
                if (this.referencedItem instanceof FieldIdItem) break;
                throw new RuntimeException(this.referencedItem.getClass().getSimpleName() + " is the wrong item type for opcode " + this.opcode.name + ". Expecting FieldIdItem.");
            }
            case 2: {
                if (this.referencedItem instanceof MethodIdItem) break;
                throw new RuntimeException(this.referencedItem.getClass().getSimpleName() + " is the wrong item type for opcode " + this.opcode.name + ". Expecting MethodIdItem.");
            }
            case 3: {
                if (this.referencedItem instanceof TypeIdItem) break;
                throw new RuntimeException(this.referencedItem.getClass().getSimpleName() + " is the wrong item type for opcode " + this.opcode.name + ". Expecting TypeIdItem.");
            }
            case 4: {
                if (this.referencedItem instanceof StringIdItem) break;
                throw new RuntimeException(this.referencedItem.getClass().getSimpleName() + " is the wrong item type for opcode " + this.opcode.name + ". Expecting StringIdItem.");
            }
        }
    }

    private void lookupReferencedItem(DexFile dexFile, Opcode opcode, int n) {
        switch (1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[opcode.referenceType.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                this.referencedItem = dexFile.FieldIdsSection.getItemByIndex(n);
                return;
            }
            case 2: {
                this.referencedItem = dexFile.MethodIdsSection.getItemByIndex(n);
                return;
            }
            case 3: {
                this.referencedItem = dexFile.TypeIdsSection.getItemByIndex(n);
                return;
            }
            case 4: 
        }
        this.referencedItem = dexFile.StringIdsSection.getItemByIndex(n);
    }

    public Item getReferencedItem() {
        return this.referencedItem;
    }

    protected int getReferencedItemIndex(byte[] arrby, int n) {
        return NumberUtils.decodeUnsignedShort(arrby, n + 2);
    }

    public void setReferencedItem(Item item) {
        this.referencedItem = item;
    }

}

