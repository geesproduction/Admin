/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.List;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Section;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public class MapItem
extends Item<MapItem> {
    static final /* synthetic */ boolean $assertionsDisabled;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !MapItem.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected MapItem(DexFile dexFile) {
        super(dexFile);
    }

    private void writeSectionInfo(AnnotatedOutput annotatedOutput, ItemType itemType, int n, int n2) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(2, "item_type: " + (Object)((Object)itemType));
            annotatedOutput.annotate(2, "unused");
            annotatedOutput.annotate(4, "section_size: 0x" + Integer.toHexString((int)n) + " (" + n + ")");
            annotatedOutput.annotate(4, "section_off: 0x" + Integer.toHexString((int)n2));
        }
        annotatedOutput.writeShort(itemType.MapValue);
        annotatedOutput.writeShort(0);
        annotatedOutput.writeInt(n);
        annotatedOutput.writeInt(n2);
    }

    public int compareTo(MapItem mapItem) {
        return 0;
    }

    @Override
    public String getConciseIdentity() {
        return "map_item";
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_MAP_LIST;
    }

    @Override
    protected int placeItem(int n) {
        Section[] arrsection = this.dexFile.getOrderedSections();
        return n + 4 + 12 * (2 + arrsection.length);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        int n = input.readInt();
        for (int i = 0; i < n; ++i) {
            ItemType itemType = ItemType.fromInt(input.readShort());
            input.readShort();
            readContext.addSection(itemType, input.readInt(), input.readInt());
        }
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (!$assertionsDisabled && this.getOffset() <= 0) {
            throw new AssertionError();
        }
        Section[] arrsection = this.dexFile.getOrderedSections();
        annotatedOutput.annotate("map_size: 0x" + Integer.toHexString((int)(2 + arrsection.length)) + " (" + Integer.toString((int)(2 + arrsection.length)) + ")");
        annotatedOutput.writeInt(2 + arrsection.length);
        StringBuilder stringBuilder = new StringBuilder().append("[");
        int n = 0 + 1;
        annotatedOutput.annotate(0, stringBuilder.append(0).append("]").toString());
        annotatedOutput.indent();
        MapItem.super.writeSectionInfo(annotatedOutput, ItemType.TYPE_HEADER_ITEM, 1, 0);
        annotatedOutput.deindent();
        for (Section section : this.dexFile.getOrderedSections()) {
            StringBuilder stringBuilder2 = new StringBuilder().append("[");
            int n2 = n + 1;
            annotatedOutput.annotate(0, stringBuilder2.append(n).append("]").toString());
            annotatedOutput.indent();
            MapItem.super.writeSectionInfo(annotatedOutput, section.ItemType, section.getItems().size(), section.getOffset());
            annotatedOutput.deindent();
            n = n2;
        }
        StringBuilder stringBuilder3 = new StringBuilder().append("[");
        n + 1;
        annotatedOutput.annotate(0, stringBuilder3.append(n).append("]").toString());
        annotatedOutput.indent();
        MapItem.super.writeSectionInfo(annotatedOutput, ItemType.TYPE_MAP_LIST, 1, this.dexFile.MapItem.getOffset());
        annotatedOutput.deindent();
    }
}

