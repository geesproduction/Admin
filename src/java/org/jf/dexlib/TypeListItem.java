/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.List;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.ReadOnlyArrayList;

public class TypeListItem
extends Item<TypeListItem> {
    private int hashCode = 0;
    public TypeIdItem[] typeList;

    protected TypeListItem(DexFile dexFile) {
        super(dexFile);
    }

    private TypeListItem(DexFile dexFile, TypeIdItem[] arrtypeIdItem) {
        super(dexFile);
        this.typeList = arrtypeIdItem;
    }

    private void calcHashCode() {
        int n = 1;
        for (TypeIdItem typeIdItem : this.typeList) {
            n = n * 31 + typeIdItem.hashCode();
        }
        this.hashCode = n;
    }

    public static List<TypeIdItem> getTypes(TypeListItem typeListItem) {
        if (typeListItem == null) {
            return null;
        }
        return typeListItem.getTypes();
    }

    public static TypeListItem internTypeListItem(DexFile dexFile, List<TypeIdItem> list) {
        Object[] arrobject = new TypeIdItem[list.size()];
        list.toArray(arrobject);
        TypeListItem typeListItem = new TypeListItem(dexFile, (TypeIdItem[])arrobject);
        return dexFile.TypeListsSection.intern(typeListItem);
    }

    public static TypeListItem lookupTypeListItem(DexFile dexFile, List<TypeIdItem> list) {
        Object[] arrobject = new TypeIdItem[list.size()];
        list.toArray(arrobject);
        TypeListItem typeListItem = new TypeListItem(dexFile, (TypeIdItem[])arrobject);
        return dexFile.TypeListsSection.getInternedItem(typeListItem);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int compareTo(TypeListItem typeListItem) {
        if (typeListItem == null) {
            return 1;
        }
        int n = this.typeList.length;
        int n2 = typeListItem.typeList.length;
        int n3 = Math.min((int)n, (int)n2);
        for (int i = 0; i < n3; ++i) {
            int n4 = this.typeList[i].compareTo(typeListItem.typeList[i]);
            if (n4 != 0) return n4;
        }
        if (n < n2) {
            return -1;
        }
        if (n <= n2) return 0;
        return 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this != object) {
            if (object == null || !this.getClass().equals((Object)object.getClass())) {
                return false;
            }
            TypeListItem typeListItem = (TypeListItem)object;
            if (this.typeList.length != typeListItem.typeList.length) {
                return false;
            }
            for (int i = 0; i < this.typeList.length; ++i) {
                if (this.typeList[i] == typeListItem.typeList[i]) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public String getConciseIdentity() {
        return "type_list: " + this.getTypeListString("");
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_TYPE_LIST;
    }

    public int getRegisterCount() {
        int n = 0;
        TypeIdItem[] arrtypeIdItem = this.typeList;
        int n2 = arrtypeIdItem.length;
        for (int i = 0; i < n2; ++i) {
            n += arrtypeIdItem[i].getRegisterCount();
        }
        return n;
    }

    public String getShortyString() {
        StringBuilder stringBuilder = new StringBuilder();
        TypeIdItem[] arrtypeIdItem = this.typeList;
        int n = arrtypeIdItem.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(arrtypeIdItem[i].toShorty());
        }
        return stringBuilder.toString();
    }

    public int getTypeCount() {
        return this.typeList.length;
    }

    public TypeIdItem getTypeIdItem(int n) {
        return this.typeList[n];
    }

    public TypeIdItem[] getTypeList() {
        return this.typeList;
    }

    public String getTypeListString(String string2) {
        int n = 0;
        TypeIdItem[] arrtypeIdItem = this.typeList;
        int n2 = arrtypeIdItem.length;
        for (int i = 0; i < n2; ++i) {
            n = n + arrtypeIdItem[i].getTypeDescriptor().length() + string2.length();
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        TypeIdItem[] arrtypeIdItem2 = this.typeList;
        int n3 = arrtypeIdItem2.length;
        for (int i = 0; i < n3; ++i) {
            stringBuilder.append(arrtypeIdItem2[i].getTypeDescriptor());
            stringBuilder.append(string2);
        }
        if (this.typeList.length > 0) {
            stringBuilder.delete(stringBuilder.length() - string2.length(), stringBuilder.length());
        }
        return stringBuilder.toString();
    }

    public List<TypeIdItem> getTypes() {
        return new ReadOnlyArrayList<TypeIdItem>(this.typeList);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.calcHashCode();
        }
        return this.hashCode;
    }

    public TypeListItem internTypeListItem(DexFile dexFile) {
        TypeIdItem[] arrtypeIdItem = this.typeList;
        TypeIdItem[] arrtypeIdItem2 = null;
        if (arrtypeIdItem != null) {
            arrtypeIdItem2 = new TypeIdItem[this.typeList.length];
            for (int i = 0; i < this.typeList.length; ++i) {
                arrtypeIdItem2[i] = TypeIdItem.internTypeIdItem(dexFile, this.typeList[i].getTypeDescriptor());
            }
        }
        TypeListItem typeListItem = new TypeListItem(dexFile, arrtypeIdItem2);
        return dexFile.TypeListsSection.intern(typeListItem);
    }

    @Override
    protected int placeItem(int n) {
        return n + 4 + 2 * this.typeList.length;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        int n = input.readInt();
        this.typeList = new TypeIdItem[n];
        for (int i = 0; i < n; ++i) {
            int n2 = input.readShort();
            this.typeList[i] = this.dexFile.TypeIdsSection.getItemByIndex(n2);
        }
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "size: 0x" + Integer.toHexString((int)this.typeList.length) + " (" + this.typeList.length + ")");
            for (TypeIdItem typeIdItem : this.typeList) {
                annotatedOutput.annotate(2, "type_id_item: " + typeIdItem.getTypeDescriptor());
            }
        }
        annotatedOutput.writeInt(this.typeList.length);
        TypeIdItem[] arrtypeIdItem = this.typeList;
        int n = arrtypeIdItem.length;
        for (int i = 0; i < n; ++i) {
            annotatedOutput.writeShort(arrtypeIdItem[i].getIndex());
        }
    }
}

