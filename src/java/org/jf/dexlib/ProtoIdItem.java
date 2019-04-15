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
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class ProtoIdItem
extends Item<ProtoIdItem> {
    private String cachedPrototypeString;
    private int hashCode;
    public TypeListItem parameters;
    public TypeIdItem returnType;
    public StringIdItem shortyDescriptor;

    protected ProtoIdItem(DexFile dexFile) {
        super(dexFile);
        this.hashCode = 0;
        this.cachedPrototypeString = null;
    }

    private ProtoIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeListItem typeListItem) {
        super(dexFile);
        String string2 = typeIdItem.toShorty();
        if (typeListItem != null) {
            string2 = string2 + typeListItem.getShortyString();
        }
        this.shortyDescriptor = StringIdItem.internStringIdItem(dexFile, string2);
        this.returnType = typeIdItem;
        this.parameters = typeListItem;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void calcHashCode() {
        this.hashCode = this.returnType.hashCode();
        int n = 31 * this.hashCode;
        int n2 = this.parameters == null ? 0 : this.parameters.hashCode();
        this.hashCode = n2 + n;
    }

    public static ProtoIdItem internProtoIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeListItem typeListItem) {
        ProtoIdItem protoIdItem = new ProtoIdItem(dexFile, typeIdItem, typeListItem);
        return dexFile.ProtoIdsSection.intern(protoIdItem);
    }

    public static ProtoIdItem lookupProtoIdItem(DexFile dexFile, TypeIdItem typeIdItem, TypeListItem typeListItem) {
        ProtoIdItem protoIdItem = new ProtoIdItem(dexFile, typeIdItem, typeListItem);
        return dexFile.ProtoIdsSection.getInternedItem(protoIdItem);
    }

    public int compareTo(ProtoIdItem protoIdItem) {
        int n = this.returnType.compareTo(protoIdItem.returnType);
        if (n != 0) {
            return n;
        }
        if (this.parameters == null) {
            if (protoIdItem.parameters == null) {
                return 0;
            }
            return -1;
        }
        if (protoIdItem.parameters == null) {
            return 1;
        }
        return this.parameters.compareTo(protoIdItem.parameters);
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
                ProtoIdItem protoIdItem = (ProtoIdItem)object;
                if (this.returnType != protoIdItem.returnType || this.parameters != protoIdItem.parameters) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public String getConciseIdentity() {
        return "proto_id_item: " + this.getPrototypeString();
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_PROTO_ID_ITEM;
    }

    public int getParameterRegisterCount() {
        if (this.parameters == null) {
            return 0;
        }
        return this.parameters.getRegisterCount();
    }

    public TypeListItem getParameters() {
        return this.parameters;
    }

    public String getPrototypeString() {
        if (this.cachedPrototypeString == null) {
            StringBuilder stringBuilder = new StringBuilder("(");
            if (this.parameters != null) {
                stringBuilder.append(this.parameters.getTypeListString(""));
            }
            stringBuilder.append(")");
            stringBuilder.append(this.returnType.getTypeDescriptor());
            this.cachedPrototypeString = stringBuilder.toString();
        }
        return this.cachedPrototypeString;
    }

    public TypeIdItem getReturnType() {
        return this.returnType;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ProtoIdItem internProtoIdItem(DexFile dexFile) {
        TypeIdItem typeIdItem = TypeIdItem.internTypeIdItem(dexFile, this.returnType.getTypeDescriptor());
        TypeListItem typeListItem = this.parameters != null ? this.parameters.internTypeListItem(dexFile) : null;
        ProtoIdItem protoIdItem = new ProtoIdItem(dexFile, typeIdItem, typeListItem);
        return dexFile.ProtoIdsSection.intern(protoIdItem);
    }

    @Override
    protected int placeItem(int n) {
        return n + 12;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.shortyDescriptor = this.dexFile.StringIdsSection.getItemByIndex(input.readInt());
        this.returnType = this.dexFile.TypeIdsSection.getItemByIndex(input.readInt());
        this.parameters = (TypeListItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_TYPE_LIST, input.readInt());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "shorty_descriptor: " + this.shortyDescriptor.getStringValue());
            annotatedOutput.annotate(4, "return_type: " + this.returnType.getTypeDescriptor());
            if (this.parameters == null) {
                annotatedOutput.annotate(4, "parameters:");
            } else {
                annotatedOutput.annotate(4, "parameters: " + this.parameters.getTypeListString(""));
            }
        }
        annotatedOutput.writeInt(this.shortyDescriptor.getIndex());
        annotatedOutput.writeInt(this.returnType.getIndex());
        int n = this.parameters == null ? 0 : this.parameters.getOffset();
        annotatedOutput.writeInt(n);
    }
}

