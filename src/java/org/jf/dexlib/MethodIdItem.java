/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package org.jf.dexlib;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class MethodIdItem
extends Item<MethodIdItem> {
    private String cachedMethodString;
    private String cachedVirtualMethodString;
    public TypeIdItem classType;
    private int hashCode;
    public StringIdItem methodName;
    public ProtoIdItem methodPrototype;

    protected MethodIdItem(DexFile dexFile) {
        super(dexFile);
        this.hashCode = 0;
        this.cachedMethodString = null;
        this.cachedVirtualMethodString = null;
    }

    private MethodIdItem(DexFile dexFile, TypeIdItem typeIdItem, ProtoIdItem protoIdItem, StringIdItem stringIdItem) {
        super(dexFile);
        this.classType = typeIdItem;
        this.methodPrototype = protoIdItem;
        this.methodName = stringIdItem;
    }

    private void calcHashCode() {
        this.hashCode = this.classType.hashCode();
        this.hashCode = 31 * this.hashCode + this.methodPrototype.hashCode();
        this.hashCode = 31 * this.hashCode + this.methodName.hashCode();
    }

    public static MethodIdItem internMethodIdItem(DexFile dexFile, TypeIdItem typeIdItem, ProtoIdItem protoIdItem, StringIdItem stringIdItem) {
        MethodIdItem methodIdItem = new MethodIdItem(dexFile, typeIdItem, protoIdItem, stringIdItem);
        return dexFile.MethodIdsSection.intern(methodIdItem);
    }

    public static MethodIdItem lookupMethodIdItem(DexFile dexFile, TypeIdItem typeIdItem, ProtoIdItem protoIdItem, StringIdItem stringIdItem) {
        MethodIdItem methodIdItem = new MethodIdItem(dexFile, typeIdItem, protoIdItem, stringIdItem);
        return dexFile.MethodIdsSection.getInternedItem(methodIdItem);
    }

    public int compareTo(MethodIdItem methodIdItem) {
        int n = this.classType.compareTo(methodIdItem.classType);
        if (n != 0) {
            return n;
        }
        int n2 = this.methodName.compareTo(methodIdItem.methodName);
        if (n2 != 0) {
            return n2;
        }
        return this.methodPrototype.compareTo(methodIdItem.methodPrototype);
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
                MethodIdItem methodIdItem = (MethodIdItem)object;
                if (this.classType != methodIdItem.classType || this.methodPrototype != methodIdItem.methodPrototype || this.methodName != methodIdItem.methodName) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "method_id_item: " + this.getMethodString();
    }

    public TypeIdItem getContainingClass() {
        return this.classType;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_METHOD_ID_ITEM;
    }

    public StringIdItem getMethodName() {
        return this.methodName;
    }

    public String getMethodString() {
        if (this.cachedMethodString == null) {
            String string2 = this.classType.getTypeDescriptor();
            String string3 = this.methodName.getStringValue();
            String string4 = this.methodPrototype.getPrototypeString();
            StringBuilder stringBuilder = new StringBuilder(2 + (string2.length() + string3.length() + string4.length()));
            stringBuilder.append(string2);
            stringBuilder.append("->");
            stringBuilder.append(string3);
            stringBuilder.append(string4);
            this.cachedMethodString = stringBuilder.toString();
        }
        return this.cachedMethodString;
    }

    public ProtoIdItem getPrototype() {
        return this.methodPrototype;
    }

    public String getVirtualMethodString() {
        if (this.cachedVirtualMethodString == null) {
            String string2 = this.methodName.getStringValue();
            String string3 = this.methodPrototype.getPrototypeString();
            StringBuilder stringBuilder = new StringBuilder(string2.length() + string3.length());
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            this.cachedVirtualMethodString = stringBuilder.toString();
        }
        return this.cachedVirtualMethodString;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    public MethodIdItem internMethodIdItem(DexFile dexFile) {
        MethodIdItem methodIdItem = new MethodIdItem(dexFile, TypeIdItem.internTypeIdItem(dexFile, this.classType.getTypeDescriptor()), this.methodPrototype.internProtoIdItem(dexFile), StringIdItem.internStringIdItem(dexFile, this.methodName.getStringValue()));
        return dexFile.MethodIdsSection.intern(methodIdItem);
    }

    @Override
    protected int placeItem(int n) {
        return n + 8;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.classType = this.dexFile.TypeIdsSection.getItemByIndex(input.readShort());
        this.methodPrototype = this.dexFile.ProtoIdsSection.getItemByIndex(input.readShort());
        this.methodName = this.dexFile.StringIdsSection.getItemByIndex(input.readInt());
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(2, "class_type: " + this.classType.getTypeDescriptor());
            annotatedOutput.annotate(2, "method_prototype: " + this.methodPrototype.getPrototypeString());
            annotatedOutput.annotate(4, "method_name: " + this.methodName.getStringValue());
        }
        annotatedOutput.writeShort(this.classType.getIndex());
        annotatedOutput.writeShort(this.methodPrototype.getIndex());
        annotatedOutput.writeInt(this.methodName.getIndex());
    }
}

