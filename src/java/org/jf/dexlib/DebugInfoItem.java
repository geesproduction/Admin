/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.List
 *  org.jf.dexlib.DebugInfoItem$2
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.Debug.DebugInstructionIterator;
import org.jf.dexlib.Debug.DebugOpcode;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ByteArrayInput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;

public class DebugInfoItem
extends Item<DebugInfoItem> {
    private byte[] encodedDebugInfo;
    private int lineStart;
    private StringIdItem[] parameterNames;
    private CodeItem parent;
    private Item[] referencedItems;

    public DebugInfoItem(DexFile dexFile) {
        super(dexFile);
        this.parent = null;
    }

    private DebugInfoItem(DexFile dexFile, int n, StringIdItem[] arrstringIdItem, byte[] arrby, Item[] arritem) {
        super(dexFile);
        this.parent = null;
        this.lineStart = n;
        this.parameterNames = arrstringIdItem;
        this.encodedDebugInfo = arrby;
        this.referencedItems = arritem;
    }

    public static DebugInfoItem internDebugInfoItem(DexFile dexFile, int n, StringIdItem[] arrstringIdItem, byte[] arrby, Item[] arritem) {
        DebugInfoItem debugInfoItem = new DebugInfoItem(dexFile, n, arrstringIdItem, arrby, arritem);
        return dexFile.DebugInfoItemsSection.intern(debugInfoItem);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeItemWithAnnotations(final AnnotatedOutput annotatedOutput) {
        annotatedOutput.annotate(0, this.parent.getParent().method.getMethodString());
        annotatedOutput.annotate("line_start: 0x" + Integer.toHexString((int)this.lineStart) + " (" + this.lineStart + ")");
        annotatedOutput.writeUnsignedLeb128(this.lineStart);
        annotatedOutput.annotate("parameters_size: 0x" + Integer.toHexString((int)this.parameterNames.length) + " (" + this.parameterNames.length + ")");
        annotatedOutput.writeUnsignedLeb128(this.parameterNames.length);
        StringIdItem[] arrstringIdItem = this.parameterNames;
        int n = arrstringIdItem.length;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            int n5;
            if (n2 >= n) {
                DebugInstructionIterator.IterateInstructions(new ByteArrayInput(this.encodedDebugInfo), new DebugInstructionIterator.ProcessRawDebugInstructionDelegate(){
                    static final /* synthetic */ boolean $assertionsDisabled;
                    private int referencedItemsPosition = 0;

                    /*
                     * Enabled aggressive block sorting
                     */
                    static {
                        boolean bl = !DebugInfoItem.class.desiredAssertionStatus();
                        $assertionsDisabled = bl;
                    }

                    @Override
                    public void ProcessAdvanceLine(int n, int n2, int n3) {
                        annotatedOutput.annotate("DBG_ADVANCE_LINE");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_ADVANCE_LINE.value);
                        annotatedOutput.indent();
                        annotatedOutput.annotate("line_diff: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        annotatedOutput.writeSignedLeb128(n3);
                        annotatedOutput.deindent();
                    }

                    @Override
                    public void ProcessAdvancePC(int n, int n2, int n3) {
                        annotatedOutput.annotate("DBG_ADVANCE_PC");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_ADVANCE_PC.value);
                        annotatedOutput.indent();
                        annotatedOutput.annotate("addr_diff: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        annotatedOutput.writeUnsignedLeb128(n3);
                        annotatedOutput.deindent();
                    }

                    @Override
                    public void ProcessEndLocal(int n, int n2, int n3, boolean bl) {
                        annotatedOutput.annotate("DBG_END_LOCAL");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_END_LOCAL.value);
                        annotatedOutput.annotate("register_num: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        if (bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                            return;
                        }
                        annotatedOutput.writeUnsignedLeb128(n3);
                    }

                    @Override
                    public void ProcessEndSequence(int n) {
                        annotatedOutput.annotate("DBG_END_SEQUENCE");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_END_SEQUENCE.value);
                    }

                    @Override
                    public void ProcessRestartLocal(int n, int n2, int n3, boolean bl) {
                        annotatedOutput.annotate("DBG_RESTART_LOCAL");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_RESTART_LOCAL.value);
                        annotatedOutput.annotate("register_num: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        if (bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                            return;
                        }
                        annotatedOutput.writeUnsignedLeb128(n3);
                    }

                    @Override
                    public void ProcessSetEpilogueBegin(int n) {
                        annotatedOutput.annotate("DBG_SET_EPILOGUE_BEGIN");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_SET_EPILOGUE_BEGIN.value);
                    }

                    @Override
                    public void ProcessSetFile(int n, int n2, int n3) {
                        annotatedOutput.annotate("DBG_SET_FILE");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_SET_FILE.value);
                        if (n3 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n4 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n4 + 1;
                            Item item = arritem[n4];
                            if (!$assertionsDisabled && !(item instanceof StringIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("source_file: \"" + ((StringIdItem)item).getStringValue() + "\"");
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                            return;
                        }
                        annotatedOutput.annotate("source_file: ");
                        annotatedOutput.writeByte(0);
                    }

                    @Override
                    public void ProcessSetPrologueEnd(int n) {
                        annotatedOutput.annotate("DBG_SET_PROLOGUE_END");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_SET_PROLOGUE_END.value);
                    }

                    @Override
                    public void ProcessSpecialOpcode(int n, int n2, int n3, int n4) {
                        annotatedOutput.annotate("DBG_SPECIAL_OPCODE: line_diff=0x" + Integer.toHexString((int)n3) + "(" + n3 + "),addressDiff=0x" + Integer.toHexString((int)n4) + "(" + n4 + ")");
                        annotatedOutput.writeByte(n2);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void ProcessStartLocal(int n, int n2, int n3, int n4, int n5, boolean bl) {
                        annotatedOutput.annotate("DBG_START_LOCAL");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_START_LOCAL.value);
                        annotatedOutput.indent();
                        annotatedOutput.annotate("register_num: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        if (DebugInfoItem.this.dexFile.getPreserveSignedRegisters() && bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                        } else {
                            annotatedOutput.writeUnsignedLeb128(n3);
                        }
                        if (n4 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n6 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n6 + 1;
                            Item item = arritem[n6];
                            if (!$assertionsDisabled && !(item instanceof StringIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("name: " + ((StringIdItem)item).getStringValue());
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                        } else {
                            annotatedOutput.annotate("name: ");
                            annotatedOutput.writeByte(0);
                        }
                        if (n5 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n7 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n7 + 1;
                            Item item = arritem[n7];
                            if (!$assertionsDisabled && !(item instanceof TypeIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("type: " + ((TypeIdItem)item).getTypeDescriptor());
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                        } else {
                            annotatedOutput.annotate("type: ");
                            annotatedOutput.writeByte(0);
                        }
                        annotatedOutput.deindent();
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void ProcessStartLocalExtended(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
                        annotatedOutput.annotate("DBG_START_LOCAL_EXTENDED");
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_START_LOCAL_EXTENDED.value);
                        annotatedOutput.indent();
                        annotatedOutput.annotate("register_num: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
                        if (DebugInfoItem.this.dexFile.getPreserveSignedRegisters() && bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                        } else {
                            annotatedOutput.writeUnsignedLeb128(n3);
                        }
                        if (n4 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n7 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n7 + 1;
                            Item item = arritem[n7];
                            if (!$assertionsDisabled && !(item instanceof StringIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("name: " + ((StringIdItem)item).getStringValue());
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                        } else {
                            annotatedOutput.annotate("name: ");
                            annotatedOutput.writeByte(0);
                        }
                        if (n5 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n8 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n8 + 1;
                            Item item = arritem[n8];
                            if (!$assertionsDisabled && !(item instanceof TypeIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("type: " + ((TypeIdItem)item).getTypeDescriptor());
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                        } else {
                            annotatedOutput.annotate("type: ");
                            annotatedOutput.writeByte(0);
                        }
                        if (n6 != -1) {
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n9 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n9 + 1;
                            Item item = arritem[n9];
                            if (!$assertionsDisabled && !(item instanceof StringIdItem)) {
                                throw new AssertionError();
                            }
                            annotatedOutput.annotate("signature: " + ((StringIdItem)item).getStringValue());
                            annotatedOutput.writeUnsignedLeb128(1 + item.getIndex());
                        } else {
                            annotatedOutput.annotate("signature: ");
                            annotatedOutput.writeByte(0);
                        }
                        annotatedOutput.deindent();
                    }
                });
                return;
            }
            StringIdItem stringIdItem = arrstringIdItem[n2];
            if (stringIdItem == null) {
                StringBuilder stringBuilder = new StringBuilder().append("[");
                n5 = n3 + 1;
                annotatedOutput.annotate(stringBuilder.append(n3).append("] parameterName: ").toString());
                n4 = 0;
            } else {
                StringBuilder stringBuilder = new StringBuilder().append("[");
                n5 = n3 + 1;
                annotatedOutput.annotate(stringBuilder.append(n3).append("] parameterName: ").append(stringIdItem.getStringValue()).toString());
                n4 = 1 + stringIdItem.getIndex();
            }
            annotatedOutput.writeUnsignedLeb128(n4);
            ++n2;
            n3 = n5;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeItemWithNoAnnotations(final AnnotatedOutput annotatedOutput) {
        annotatedOutput.writeUnsignedLeb128(this.lineStart);
        annotatedOutput.writeUnsignedLeb128(this.parameterNames.length);
        StringIdItem[] arrstringIdItem = this.parameterNames;
        int n = arrstringIdItem.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                DebugInstructionIterator.IterateInstructions(new ByteArrayInput(this.encodedDebugInfo), new DebugInstructionIterator.ProcessRawDebugInstructionDelegate(){
                    private int referencedItemsPosition = 0;

                    @Override
                    public void ProcessSetFile(int n, int n2, int n3) {
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_SET_FILE.value);
                        if (n3 != -1) {
                            AnnotatedOutput annotatedOutput2 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n4 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n4 + 1;
                            annotatedOutput2.writeUnsignedLeb128(1 + arritem[n4].getIndex());
                            return;
                        }
                        annotatedOutput.writeByte(0);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void ProcessStartLocal(int n, int n2, int n3, int n4, int n5, boolean bl) {
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_START_LOCAL.value);
                        if (DebugInfoItem.this.dexFile.getPreserveSignedRegisters() && bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                        } else {
                            annotatedOutput.writeUnsignedLeb128(n3);
                        }
                        if (n4 != -1) {
                            AnnotatedOutput annotatedOutput2 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n6 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n6 + 1;
                            annotatedOutput2.writeUnsignedLeb128(1 + arritem[n6].getIndex());
                        } else {
                            annotatedOutput.writeByte(0);
                        }
                        if (n5 != -1) {
                            AnnotatedOutput annotatedOutput3 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n7 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n7 + 1;
                            annotatedOutput3.writeUnsignedLeb128(1 + arritem[n7].getIndex());
                            return;
                        }
                        annotatedOutput.writeByte(0);
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public void ProcessStartLocalExtended(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
                        annotatedOutput.writeByte((int)DebugOpcode.DBG_START_LOCAL_EXTENDED.value);
                        if (DebugInfoItem.this.dexFile.getPreserveSignedRegisters() && bl) {
                            annotatedOutput.writeSignedLeb128(n3);
                        } else {
                            annotatedOutput.writeUnsignedLeb128(n3);
                        }
                        if (n4 != -1) {
                            AnnotatedOutput annotatedOutput2 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n7 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n7 + 1;
                            annotatedOutput2.writeUnsignedLeb128(1 + arritem[n7].getIndex());
                        } else {
                            annotatedOutput.writeByte(0);
                        }
                        if (n5 != -1) {
                            AnnotatedOutput annotatedOutput3 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n8 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n8 + 1;
                            annotatedOutput3.writeUnsignedLeb128(1 + arritem[n8].getIndex());
                        } else {
                            annotatedOutput.writeByte(0);
                        }
                        if (n6 != -1) {
                            AnnotatedOutput annotatedOutput4 = annotatedOutput;
                            Item[] arritem = DebugInfoItem.this.referencedItems;
                            int n9 = this.referencedItemsPosition;
                            this.referencedItemsPosition = n9 + 1;
                            annotatedOutput4.writeUnsignedLeb128(1 + arritem[n9].getIndex());
                            return;
                        }
                        annotatedOutput.writeByte(0);
                    }

                    @Override
                    public void ProcessStaticOpcode(DebugOpcode debugOpcode, int n, int n2) {
                        annotatedOutput.write(DebugInfoItem.this.encodedDebugInfo, n, n2);
                    }
                });
                return;
            }
            StringIdItem stringIdItem = arrstringIdItem[n2];
            int n3 = stringIdItem == null ? 0 : 1 + stringIdItem.getIndex();
            annotatedOutput.writeUnsignedLeb128(n3);
            ++n2;
        } while (true);
    }

    public int compareTo(DebugInfoItem debugInfoItem) {
        if (this.parent == null) {
            if (debugInfoItem.parent == null) {
                return 0;
            }
            return -1;
        }
        if (debugInfoItem.parent == null) {
            return 1;
        }
        return this.parent.compareTo(debugInfoItem.parent);
    }

    @Override
    public String getConciseIdentity() {
        return "debug_info_item @0x" + Integer.toHexString((int)this.getOffset());
    }

    public byte[] getEncodedDebugInfo() {
        return this.encodedDebugInfo;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_DEBUG_INFO_ITEM;
    }

    public int getLineStart() {
        return this.lineStart;
    }

    public StringIdItem[] getParameterNames() {
        return this.parameterNames;
    }

    public Item[] getReferencedItems() {
        return this.referencedItems;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int placeItem(int n) {
        int n2 = n + Leb128Utils.unsignedLeb128Size(this.lineStart) + Leb128Utils.unsignedLeb128Size(this.parameterNames.length);
        StringIdItem[] arrstringIdItem = this.parameterNames;
        int n3 = arrstringIdItem.length;
        int n4 = 0;
        do {
            if (n4 >= n3) {
                ByteArrayInput byteArrayInput = new ByteArrayInput(this.encodedDebugInfo);
                2 var7_9 = new 2((DebugInfoItem)this);
                DebugInstructionIterator.IterateInstructions(byteArrayInput, (DebugInstructionIterator.ProcessRawDebugInstructionDelegate)var7_9);
                return n2 + var7_9.length;
            }
            StringIdItem stringIdItem = arrstringIdItem[n4];
            int n5 = stringIdItem == null ? 0 : 1 + stringIdItem.getIndex();
            n2 += Leb128Utils.unsignedLeb128Size(n5);
            ++n4;
        } while (true);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.lineStart = input.readUnsignedLeb128();
        this.parameterNames = new StringIdItem[input.readUnsignedLeb128()];
        IndexedSection<StringIdItem> indexedSection = this.dexFile.StringIdsSection;
        for (int i = 0; i < this.parameterNames.length; ++i) {
            this.parameterNames[i] = indexedSection.getOptionalItemByIndex(-1 + input.readUnsignedLeb128());
        }
        int n = input.getCursor();
        ArrayList arrayList = new ArrayList(50);
        DebugInstructionIterator.IterateInstructions(input, new DebugInstructionIterator.ProcessRawDebugInstructionDelegate((List)arrayList){
            final /* synthetic */ List val$referencedItemsList;
            {
                this.val$referencedItemsList = list;
            }

            @Override
            public void ProcessSetFile(int n, int n2, int n3) {
                if (n3 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.StringIdsSection.getItemByIndex(n3));
                }
            }

            @Override
            public void ProcessStartLocal(int n, int n2, int n3, int n4, int n5, boolean bl) {
                if (n4 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.StringIdsSection.getItemByIndex(n4));
                }
                if (n5 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.TypeIdsSection.getItemByIndex(n5));
                }
            }

            @Override
            public void ProcessStartLocalExtended(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
                if (n4 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.StringIdsSection.getItemByIndex(n4));
                }
                if (n5 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.TypeIdsSection.getItemByIndex(n5));
                }
                if (n6 != -1) {
                    this.val$referencedItemsList.add((Object)DebugInfoItem.this.dexFile.StringIdsSection.getItemByIndex(n6));
                }
            }
        });
        this.referencedItems = new Item[arrayList.size()];
        arrayList.toArray((Object[])this.referencedItems);
        int n2 = input.getCursor() - n;
        input.setCursor(n);
        this.encodedDebugInfo = input.readBytes(n2);
    }

    protected void setEncodedDebugInfo(byte[] arrby) {
        this.encodedDebugInfo = arrby;
    }

    protected void setParent(CodeItem codeItem) {
        this.parent = codeItem;
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            DebugInfoItem.super.writeItemWithAnnotations(annotatedOutput);
            return;
        }
        DebugInfoItem.super.writeItemWithNoAnnotations(annotatedOutput);
    }

    class 1ProcessDebugInstructionDelegateWithLength
    extends DebugInstructionIterator.ProcessRawDebugInstructionDelegate {
        public int length = 0;

        1ProcessDebugInstructionDelegateWithLength() {
        }
    }

}

