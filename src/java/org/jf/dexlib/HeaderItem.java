/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.List
 */
package org.jf.dexlib;

import java.io.PrintStream;
import java.util.List;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MapItem;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Utf8Utils;

public class HeaderItem
extends Item<HeaderItem> {
    private static final int BIG_ENDIAN = 2018910994;
    private static final int HEADER_SIZE = 112;
    private static final int LITTLE_ENDIAN = 305419896;
    public static final byte[] MAGIC = new byte[]{100, 101, 120, 10, 48, 51, 53, 0};

    protected HeaderItem(DexFile dexFile) {
        super(dexFile);
    }

    public int compareTo(HeaderItem headerItem) {
        return 0;
    }

    @Override
    public String getConciseIdentity() {
        return "header_item";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_HEADER_ITEM;
    }

    @Override
    protected int placeItem(int n) {
        return 112;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        byte[] arrby = input.readBytes(8);
        for (int i = 0; i < 8; ++i) {
            if (MAGIC[i] == arrby[i]) continue;
            throw new RuntimeException("The magic value is not the expected value");
        }
        input.readBytes(20);
        input.readInt();
        input.readInt();
        if (input.readInt() != 112) {
            throw new RuntimeException("The header size is not the expected value (0x70)");
        }
        int n = input.readInt();
        if (n == 2018910994) {
            throw new RuntimeException("This dex file is big endian. Only little endian is currently supported.");
        }
        if (n != 305419896) {
            throw new RuntimeException("The endian tag is not 0x12345678 or 0x78563412");
        }
        if ((input.readInt() | input.readInt()) != 0) {
            System.err.println("This dex file has a link section, which is not supported. Ignoring.");
        }
        int n2 = input.readInt();
        readContext.addSection(ItemType.TYPE_MAP_LIST, 1, n2);
        int n3 = input.readInt();
        int n4 = input.readInt();
        readContext.addSection(ItemType.TYPE_STRING_ID_ITEM, n3, n4);
        int n5 = input.readInt();
        int n6 = input.readInt();
        readContext.addSection(ItemType.TYPE_TYPE_ID_ITEM, n5, n6);
        int n7 = input.readInt();
        int n8 = input.readInt();
        readContext.addSection(ItemType.TYPE_PROTO_ID_ITEM, n7, n8);
        int n9 = input.readInt();
        int n10 = input.readInt();
        readContext.addSection(ItemType.TYPE_FIELD_ID_ITEM, n9, n10);
        int n11 = input.readInt();
        int n12 = input.readInt();
        readContext.addSection(ItemType.TYPE_METHOD_ID_ITEM, n11, n12);
        int n13 = input.readInt();
        int n14 = input.readInt();
        readContext.addSection(ItemType.TYPE_CLASS_DEF_ITEM, n13, n14);
        input.readInt();
        input.readInt();
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 8; ++i) {
            stringBuilder.append((char)MAGIC[i]);
        }
        annotatedOutput.annotate("magic: " + Utf8Utils.escapeString(stringBuilder.toString()));
        annotatedOutput.write(MAGIC);
        annotatedOutput.annotate("checksum");
        annotatedOutput.writeInt(0);
        annotatedOutput.annotate("signature");
        annotatedOutput.write(new byte[20]);
        annotatedOutput.annotate("file_size: 0x" + Integer.toHexString((int)this.dexFile.getFileSize()) + " (" + this.dexFile.getFileSize() + " bytes)");
        annotatedOutput.writeInt(this.dexFile.getFileSize());
        annotatedOutput.annotate("header_size: 0x" + Integer.toHexString((int)112));
        annotatedOutput.writeInt(112);
        annotatedOutput.annotate("endian_tag: 0x" + Integer.toHexString((int)305419896));
        annotatedOutput.writeInt(305419896);
        annotatedOutput.annotate("link_size: 0");
        annotatedOutput.writeInt(0);
        annotatedOutput.annotate("link_off: 0");
        annotatedOutput.writeInt(0);
        annotatedOutput.annotate("map_off: 0x" + Integer.toHexString((int)this.dexFile.MapItem.getOffset()));
        annotatedOutput.writeInt(this.dexFile.MapItem.getOffset());
        annotatedOutput.annotate("string_ids_size: " + this.dexFile.StringIdsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.StringIdsSection.getItems().size());
        annotatedOutput.annotate("string_ids_off: 0x" + Integer.toHexString((int)this.dexFile.StringIdsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.StringIdsSection.getOffset());
        annotatedOutput.annotate("type_ids_size: " + this.dexFile.TypeIdsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.TypeIdsSection.getItems().size());
        annotatedOutput.annotate("type_ids_off: 0x" + Integer.toHexString((int)this.dexFile.TypeIdsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.TypeIdsSection.getOffset());
        annotatedOutput.annotate("proto_ids_size: " + this.dexFile.ProtoIdsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.ProtoIdsSection.getItems().size());
        annotatedOutput.annotate("proto_ids_off: 0x" + Integer.toHexString((int)this.dexFile.ProtoIdsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.ProtoIdsSection.getOffset());
        annotatedOutput.annotate("field_ids_size: " + this.dexFile.FieldIdsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.FieldIdsSection.getItems().size());
        annotatedOutput.annotate("field_ids_off: 0x" + Integer.toHexString((int)this.dexFile.FieldIdsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.FieldIdsSection.getOffset());
        annotatedOutput.annotate("method_ids_size: " + this.dexFile.MethodIdsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.MethodIdsSection.getItems().size());
        annotatedOutput.annotate("method_ids_off: 0x" + Integer.toHexString((int)this.dexFile.MethodIdsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.MethodIdsSection.getOffset());
        annotatedOutput.annotate("class_defs_size: " + this.dexFile.ClassDefsSection.getItems().size());
        annotatedOutput.writeInt(this.dexFile.ClassDefsSection.getItems().size());
        annotatedOutput.annotate("class_defs_off: 0x" + Integer.toHexString((int)this.dexFile.ClassDefsSection.getOffset()));
        annotatedOutput.writeInt(this.dexFile.ClassDefsSection.getOffset());
        annotatedOutput.annotate("data_size: 0x" + Integer.toHexString((int)this.dexFile.getDataSize()) + " (" + this.dexFile.getDataSize() + " bytes)");
        annotatedOutput.writeInt(this.dexFile.getDataSize());
        annotatedOutput.annotate("data_off: 0x" + Integer.toHexString((int)this.dexFile.getDataOffset()));
        annotatedOutput.writeInt(this.dexFile.getDataOffset());
    }
}

