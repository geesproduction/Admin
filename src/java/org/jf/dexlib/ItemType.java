/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.TreeMap
 */
package org.jf.dexlib;

import java.util.TreeMap;

public final class ItemType
extends Enum<ItemType> {
    private static final /* synthetic */ ItemType[] $VALUES;
    public static final /* enum */ ItemType TYPE_ANNOTATIONS_DIRECTORY_ITEM;
    public static final /* enum */ ItemType TYPE_ANNOTATION_ITEM;
    public static final /* enum */ ItemType TYPE_ANNOTATION_SET_ITEM;
    public static final /* enum */ ItemType TYPE_ANNOTATION_SET_REF_LIST;
    public static final /* enum */ ItemType TYPE_CLASS_DATA_ITEM;
    public static final /* enum */ ItemType TYPE_CLASS_DEF_ITEM;
    public static final /* enum */ ItemType TYPE_CODE_ITEM;
    public static final /* enum */ ItemType TYPE_DEBUG_INFO_ITEM;
    public static final /* enum */ ItemType TYPE_ENCODED_ARRAY_ITEM;
    public static final /* enum */ ItemType TYPE_FIELD_ID_ITEM;
    public static final /* enum */ ItemType TYPE_HEADER_ITEM;
    public static final /* enum */ ItemType TYPE_MAP_LIST;
    public static final /* enum */ ItemType TYPE_METHOD_ID_ITEM;
    public static final /* enum */ ItemType TYPE_PROTO_ID_ITEM;
    public static final /* enum */ ItemType TYPE_STRING_DATA_ITEM;
    public static final /* enum */ ItemType TYPE_STRING_ID_ITEM;
    public static final /* enum */ ItemType TYPE_TYPE_ID_ITEM;
    public static final /* enum */ ItemType TYPE_TYPE_LIST;
    private static final TreeMap<Integer, ItemType> itemTypeIntegerMap;
    public final int ItemAlignment;
    public final int MapValue;
    public final int SectionIndex;
    public final String TypeName;

    static {
        TYPE_HEADER_ITEM = new ItemType(0, 17, 4, "header_item");
        TYPE_STRING_ID_ITEM = new ItemType(1, 0, 4, "string_id_item");
        TYPE_TYPE_ID_ITEM = new ItemType(2, 1, 4, "type_id_item");
        TYPE_PROTO_ID_ITEM = new ItemType(3, 2, 4, "proto_id_item");
        TYPE_FIELD_ID_ITEM = new ItemType(4, 3, 4, "field_id_item");
        TYPE_METHOD_ID_ITEM = new ItemType(5, 4, 4, "method_id_item");
        TYPE_CLASS_DEF_ITEM = new ItemType(6, 5, 4, "class_def_item");
        TYPE_MAP_LIST = new ItemType(4096, 16, 4, "map_list");
        TYPE_TYPE_LIST = new ItemType(4097, 6, 4, "type_list");
        TYPE_ANNOTATION_SET_REF_LIST = new ItemType(4098, 7, 4, "annotation_set_ref_list");
        TYPE_ANNOTATION_SET_ITEM = new ItemType(4099, 8, 4, "annotation_set_item");
        TYPE_CLASS_DATA_ITEM = new ItemType(8192, 9, 1, "class_data_item");
        TYPE_CODE_ITEM = new ItemType(8193, 10, 4, "code_item");
        TYPE_STRING_DATA_ITEM = new ItemType(8194, 11, 1, "string_data_item");
        TYPE_DEBUG_INFO_ITEM = new ItemType(8195, 12, 1, "debug_info_item");
        TYPE_ANNOTATION_ITEM = new ItemType(8196, 13, 1, "annotation_item");
        TYPE_ENCODED_ARRAY_ITEM = new ItemType(8197, 14, 1, "encoded_array_item");
        TYPE_ANNOTATIONS_DIRECTORY_ITEM = new ItemType(8198, 15, 4, "annotations_directory_item");
        ItemType[] arritemType = new ItemType[]{TYPE_HEADER_ITEM, TYPE_STRING_ID_ITEM, TYPE_TYPE_ID_ITEM, TYPE_PROTO_ID_ITEM, TYPE_FIELD_ID_ITEM, TYPE_METHOD_ID_ITEM, TYPE_CLASS_DEF_ITEM, TYPE_MAP_LIST, TYPE_TYPE_LIST, TYPE_ANNOTATION_SET_REF_LIST, TYPE_ANNOTATION_SET_ITEM, TYPE_CLASS_DATA_ITEM, TYPE_CODE_ITEM, TYPE_STRING_DATA_ITEM, TYPE_DEBUG_INFO_ITEM, TYPE_ANNOTATION_ITEM, TYPE_ENCODED_ARRAY_ITEM, TYPE_ANNOTATIONS_DIRECTORY_ITEM};
        $VALUES = arritemType;
        itemTypeIntegerMap = new TreeMap();
        for (ItemType itemType : ItemType.values()) {
            itemTypeIntegerMap.put((Object)itemType.MapValue, (Object)itemType);
        }
    }

    private ItemType(int n2, int n3, int n4, String string3) {
        this.MapValue = n2;
        this.SectionIndex = n3;
        this.ItemAlignment = n4;
        this.TypeName = string3;
    }

    public static ItemType fromInt(int n) {
        return (ItemType)((Object)itemTypeIntegerMap.get((Object)n));
    }

    public static ItemType valueOf(String string2) {
        return (ItemType)Enum.valueOf(ItemType.class, (String)string2);
    }

    public static ItemType[] values() {
        return (ItemType[])$VALUES.clone();
    }

    public boolean isIndexedItem() {
        return this.MapValue <= 4096;
    }
}

