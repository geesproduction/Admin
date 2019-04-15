/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Format.Instruction20t;
import org.jf.dexlib.Code.Format.Instruction21c;
import org.jf.dexlib.Code.Format.Instruction30t;
import org.jf.dexlib.Code.Format.Instruction31c;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionIterator;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.MultiOffsetInstruction;
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.ReferenceType;
import org.jf.dexlib.Debug.DebugInstructionIterator;
import org.jf.dexlib.Debug.DebugOpcode;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ByteArrayInput;
import org.jf.dexlib.Util.DebugInfoBuilder;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;
import org.jf.dexlib.Util.SparseArray;
import org.jf.dexlib.Util.SparseIntArray;

public class CodeItem
extends Item<CodeItem> {
    static final /* synthetic */ boolean $assertionsDisabled;
    public DebugInfoItem debugInfo;
    public EncodedCatchHandler[] encodedCatchHandlers;
    public int inWords;
    public Instruction[] instructions;
    public int outWords;
    private ClassDataItem.EncodedMethod parent;
    public int registerCount;
    public TryItem[] tries;
    private boolean update;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !CodeItem.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public CodeItem(DexFile dexFile) {
        super(dexFile);
    }

    private CodeItem(DexFile dexFile, int n, int n2, int n3, DebugInfoItem debugInfoItem, Instruction[] arrinstruction, TryItem[] arrtryItem, EncodedCatchHandler[] arrencodedCatchHandler) {
        super(dexFile);
        this.registerCount = n;
        this.inWords = n2;
        this.outWords = n3;
        this.debugInfo = debugInfoItem;
        if (debugInfoItem != null) {
            debugInfoItem.setParent((CodeItem)this);
        }
        this.instructions = arrinstruction;
        this.tries = arrtryItem;
        this.encodedCatchHandlers = arrencodedCatchHandler;
    }

    public static EncodedCatchHandler copyEncodedCatchHandler(DexFile dexFile, EncodedCatchHandler encodedCatchHandler) {
        if (encodedCatchHandler.handlers == null) {
            return null;
        }
        EncodedTypeAddrPair[] arrencodedTypeAddrPair = new EncodedTypeAddrPair[encodedCatchHandler.handlers.length];
        for (int i = 0; i < encodedCatchHandler.handlers.length; ++i) {
            arrencodedTypeAddrPair[i] = CodeItem.copyEncodedTypeAddrPair(dexFile, encodedCatchHandler.handlers[i]);
        }
        return new EncodedCatchHandler(arrencodedTypeAddrPair, encodedCatchHandler.getCatchAllHandlerAddress());
    }

    public static EncodedTypeAddrPair copyEncodedTypeAddrPair(DexFile dexFile, EncodedTypeAddrPair encodedTypeAddrPair) {
        return new EncodedTypeAddrPair(TypeIdItem.internTypeIdItem(dexFile, encodedTypeAddrPair.exceptionType.getTypeDescriptor()), encodedTypeAddrPair.getHandlerAddress());
    }

    public static TryItem copyTryItem(DexFile dexFile, TryItem tryItem, List<EncodedCatchHandler> list) {
        EncodedCatchHandler encodedCatchHandler = CodeItem.copyEncodedCatchHandler(dexFile, tryItem.encodedCatchHandler);
        list.add((Object)encodedCatchHandler);
        return new TryItem(tryItem.getStartCodeAddress(), tryItem.getTryLength(), encodedCatchHandler);
    }

    private int getInstructionsLength() {
        Instruction[] arrinstruction = this.instructions;
        int n = 0;
        int n2 = arrinstruction.length;
        for (int i = 0; i < n2; ++i) {
            n += arrinstruction[i].getSize(n);
        }
        return n;
    }

    public static CodeItem internCodeItem(DexFile dexFile, int n, int n2, int n3, DebugInfoItem debugInfoItem, List<Instruction> list, List<TryItem> list2, List<EncodedCatchHandler> list3) {
        Object[] arrobject = null;
        if (list2 != null) {
            int n4 = list2.size();
            arrobject = null;
            if (n4 > 0) {
                arrobject = new TryItem[list2.size()];
                list2.toArray(arrobject);
            }
        }
        Object[] arrobject2 = null;
        if (list3 != null) {
            int n5 = list3.size();
            arrobject2 = null;
            if (n5 > 0) {
                arrobject2 = new EncodedCatchHandler[list3.size()];
                list3.toArray(arrobject2);
            }
        }
        Object[] arrobject3 = null;
        if (list != null) {
            int n6 = list.size();
            arrobject3 = null;
            if (n6 > 0) {
                arrobject3 = new Instruction[list.size()];
                list.toArray(arrobject3);
            }
        }
        CodeItem codeItem = new CodeItem(dexFile, n, n2, n3, debugInfoItem, (Instruction[])arrobject3, (TryItem[])arrobject, (EncodedCatchHandler[])arrobject2);
        return dexFile.CodeItemsSection.intern(codeItem);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void replaceInstructionAtAddress(int var1, Instruction var2_2) {
        block32 : {
            block33 : {
                var3_3 = null;
                var4_4 = new int[1 + this.instructions.length];
                var5_5 = new SparseIntArray();
                var6_6 = 0;
                var7_7 = 0;
                for (var8_8 = 0; var8_8 < this.instructions.length; var6_6 += var50_10.getSize((int)var6_6), ++var8_8) {
                    var50_10 = this.instructions[var8_8];
                    if (var6_6 == var1) {
                        var3_3 = var50_10;
                        var7_7 = var8_8;
                    }
                    if ((var50_10.opcode == Opcode.PACKED_SWITCH || var50_10.opcode == Opcode.SPARSE_SWITCH) && var5_5.indexOfKey(var51_9 = var6_6 + ((OffsetInstruction)var50_10).getTargetAddressOffset()) < 0) {
                        var5_5.put(var51_9, var6_6);
                    }
                    var4_4[var8_8] = var6_6;
                }
                var4_4[var8_8] = var6_6;
                if (var3_3 == null) {
                    throw new RuntimeException("There is no instruction at address " + var1);
                }
                this.instructions[var7_7] = var2_2;
                if (var3_3.getSize(var1) == var2_2.getSize(var1)) {
                    return;
                }
                var9_11 = new SparseIntArray();
                var10_12 = new SparseIntArray();
                var11_13 = 0;
                for (var12_14 = 0; var12_14 < this.instructions.length; var11_13 += var48_16.getSize((int)var11_13), ++var12_14) {
                    var48_16 = this.instructions[var12_14];
                    var49_15 = var4_4[var12_14];
                    var9_11.append(var11_13, var49_15);
                    var10_12.append(var49_15, var11_13);
                }
                var9_11.append(var11_13, var4_4[var12_14]);
                var10_12.append(var4_4[var12_14], var11_13);
                var13_17 = 0;
                var14_18 = 0;
                block2 : do {
                    if (var14_18 < this.instructions.length) {
                        var36_21 = this.instructions[var14_18];
                        if (var36_21 instanceof OffsetInstruction) {
                            var45_19 = (OffsetInstruction)var36_21;
                            if (!CodeItem.$assertionsDisabled && var9_11.indexOfKey(var13_17) < 0) {
                                throw new AssertionError();
                            }
                            var46_24 = var9_11.get(var13_17) + var45_19.getTargetAddressOffset();
                            if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(var46_24) < 0) {
                                throw new AssertionError();
                            }
                            var47_27 = var10_12.get(var46_24) - var13_17;
                            if (var47_27 != var45_19.getTargetAddressOffset()) {
                                var45_19.updateTargetAddressOffset(var47_27);
                            }
                        } else if (var36_21 instanceof MultiOffsetInstruction) {
                            var37_20 = (MultiOffsetInstruction)var36_21;
                            if (!CodeItem.$assertionsDisabled && var9_11.indexOfKey(var13_17) < 0) {
                                throw new AssertionError();
                            }
                            var38_29 = var5_5.get(var9_11.get(var13_17), -1);
                            if (var38_29 == -1) {
                                throw new RuntimeException("This method contains an unreferenced switch data block at address " + var13_17 + " and can't be automatically fixed.");
                            }
                            if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(var38_29) < 0) {
                                throw new AssertionError();
                            }
                            var39_28 = var10_12.get(var38_29);
                            var40_30 = var37_20.getTargets();
                            var41_22 = 0;
                            break;
                        }
                    } else {
                        if (this.debugInfo != null) {
                            var33_31 = this.debugInfo.getEncodedDebugInfo();
                            var34_32 = new ByteArrayInput(var33_31);
                            var35_33 = (CodeItem)this.new DebugInstructionFixer(var33_31, var10_12);
                            DebugInstructionIterator.IterateInstructions(var34_32, var35_33);
                            if (var35_33.result != null) {
                                this.debugInfo.setEncodedDebugInfo(var35_33.result);
                            }
                        }
                        if (this.encodedCatchHandlers == null) break block32;
                        var23_34 = this.encodedCatchHandlers;
                        var24_35 = var23_34.length;
                        break block33;
                    }
                    do {
                        var13_17 += var36_21.getSize(var13_17);
                        ++var14_18;
                        continue block2;
                        break;
                    } while (true);
                    break;
                } while (true);
                do {
                    if (var41_22 >= (var42_23 = var40_30.length)) ** continue;
                    var43_26 = var38_29 + var40_30[var41_22];
                    if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(var43_26) < 0) {
                        throw new AssertionError();
                    }
                    var44_25 = var10_12.get(var43_26) - var39_28;
                    if (var44_25 != var40_30[var41_22]) {
                        var37_20.updateTarget(var41_22, var44_25);
                    }
                    ++var41_22;
                } while (true);
            }
            for (var25_36 = 0; var25_36 < var24_35; ++var25_36) {
                var26_38 = var23_34[var25_36];
                if (EncodedCatchHandler.access$500(var26_38) != -1) {
                    if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(EncodedCatchHandler.access$500(var26_38)) < 0) {
                        throw new AssertionError();
                    }
                    EncodedCatchHandler.access$502(var26_38, var10_12.get(EncodedCatchHandler.access$500(var26_38)));
                }
                for (EncodedTypeAddrPair var30_39 : var26_38.handlers) {
                    if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(EncodedTypeAddrPair.access$600(var30_39)) < 0) {
                        throw new AssertionError();
                    }
                    EncodedTypeAddrPair.access$602(var30_39, var10_12.get(EncodedTypeAddrPair.access$600(var30_39)));
                }
            }
        }
        if (this.tries == null) return;
        var15_42 = this.tries;
        var16_43 = var15_42.length;
        var17_44 = 0;
        while (var17_44 < var16_43) {
            var18_47 = var15_42[var17_44];
            var19_46 = TryItem.access$700(var18_47);
            var20_45 = TryItem.access$700(var18_47) + TryItem.access$800(var18_47);
            if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(var19_46) < 0) {
                throw new AssertionError();
            }
            TryItem.access$702(var18_47, var10_12.get(var19_46));
            if (!CodeItem.$assertionsDisabled && var10_12.indexOfKey(var20_45) < 0) {
                throw new AssertionError();
            }
            TryItem.access$802(var18_47, var10_12.get(var20_45) - TryItem.access$700(var18_47));
            ++var17_44;
        }
    }

    public int compareTo(CodeItem codeItem) {
        if (this.parent == null) {
            if (codeItem.parent == null) {
                return 0;
            }
            return -1;
        }
        if (codeItem.parent == null) {
            return 1;
        }
        return this.parent.method.compareTo(codeItem.parent.method);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Instruction[] copyInstruction(DexFile dexFile) {
        Instruction[] arrinstruction = this.instructions;
        int n = 0;
        int n2 = arrinstruction.length;
        while (n < n2) {
            Instruction instruction = arrinstruction[n];
            block0 : switch (instruction.getFormat()) {
                default: {
                    break;
                }
                case Format21c: 
                case Format31c: 
                case Format22c: 
                case Format35c: 
                case Format35s: 
                case Format3rc: {
                    InstructionWithReference instructionWithReference = (InstructionWithReference)instruction;
                    switch (2.$SwitchMap$org$jf$dexlib$Code$ReferenceType[instruction.opcode.referenceType.ordinal()]) {
                        default: {
                            break block0;
                        }
                        case 1: {
                            instructionWithReference.setReferencedItem(((FieldIdItem)instructionWithReference.getReferencedItem()).internFieldIdItem(dexFile));
                            break block0;
                        }
                        case 2: {
                            instructionWithReference.setReferencedItem(((MethodIdItem)instructionWithReference.getReferencedItem()).internMethodIdItem(dexFile));
                            break block0;
                        }
                        case 3: {
                            instructionWithReference.setReferencedItem(TypeIdItem.internTypeIdItem(dexFile, ((TypeIdItem)instructionWithReference.getReferencedItem()).getTypeDescriptor()));
                            break block0;
                        }
                        case 4: 
                    }
                    instructionWithReference.setReferencedItem(StringIdItem.internStringIdItem(dexFile, ((StringIdItem)instructionWithReference.getReferencedItem()).getStringValue()));
                }
            }
            ++n;
        }
        return arrinstruction;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public TryItem[] copyTryItems(DexFile dexFile, List<EncodedCatchHandler> list) {
        if (this.tries == null) {
            return null;
        }
        TryItem[] arrtryItem = new TryItem[this.tries.length];
        int n = 0;
        while (n < this.tries.length) {
            arrtryItem[n] = CodeItem.copyTryItem(dexFile, this.tries[n], list);
            ++n;
        }
        return arrtryItem;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void fixInstructions(boolean var1, boolean var2_2) {
        block7 : do {
            var3_7 = 0;
            var4_8 = 0;
            do {
                block14 : {
                    block13 : {
                        block12 : {
                            var6_11 = this.instructions.length;
                            var7_10 = false;
                            if (var4_8 >= var6_11) continue block7;
                            var8_3 = this.instructions[var4_8];
                            if (!var2_2) break block12;
                            try {
                                if (var8_3.opcode != Opcode.GOTO) break block12;
                            }
                            catch (Exception var10_12) {
                                try {
                                    throw ExceptionWithContext.withContext(var10_12, "Error while attempting to fix " + var8_3.opcode.name + " instruction at address " + var3_7);
                                }
                                catch (Exception var5_13) {
                                    throw this.addExceptionContext(var5_13);
                                }
                            }
                            var13_5 = ((OffsetInstruction)var8_3).getTargetAddressOffset();
                            if ((byte)var13_5 == var13_5) break block14;
                            if ((short)var13_5 != var13_5) ** GOTO lbl24
                            CodeItem.super.replaceInstructionAtAddress(var3_7, new Instruction20t(Opcode.GOTO_16, var13_5));
                            break;
lbl24: // 1 sources:
                            CodeItem.super.replaceInstructionAtAddress(var3_7, new Instruction30t(Opcode.GOTO_32, var13_5));
                            break;
                        }
                        if (var2_2) {
                            if (var8_3.opcode != Opcode.GOTO_16) break block13;
                            var12_9 = ((OffsetInstruction)var8_3).getTargetAddressOffset();
                            if ((short)var12_9 != var12_9) {
                                CodeItem.super.replaceInstructionAtAddress(var3_7, new Instruction30t(Opcode.GOTO_32, var12_9));
                                var7_10 = true;
                                continue block7;
                            }
                            break block14;
                        }
                    }
                    if (var1 && var8_3.opcode == Opcode.CONST_STRING && (var11_4 = (Instruction21c)var8_3).getReferencedItem().getIndex() > 65535) {
                        CodeItem.super.replaceInstructionAtAddress(var3_7, new Instruction31c(Opcode.CONST_STRING_JUMBO, (short)var11_4.getRegisterA(), var11_4.getReferencedItem()));
                        var7_10 = true;
                        continue block7;
                    }
                }
                var9_6 = var8_3.getSize(var3_7);
                var3_7 += var9_6;
                ++var4_8;
            } while (true);
            var7_10 = true;
        } while (var7_10);
    }

    @Override
    public String getConciseIdentity() {
        if (this.parent == null) {
            return "code_item @0x" + Integer.toHexString((int)this.getOffset());
        }
        return "code_item @0x" + Integer.toHexString((int)this.getOffset()) + " (" + this.parent.method.getMethodString() + ")";
    }

    public DebugInfoItem getDebugInfo() {
        return this.debugInfo;
    }

    public EncodedCatchHandler[] getHandlers() {
        return this.encodedCatchHandlers;
    }

    public Instruction[] getInstructions() {
        return this.instructions;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_CODE_ITEM;
    }

    public ClassDataItem.EncodedMethod getParent() {
        return this.parent;
    }

    public int getRegisterCount() {
        return this.registerCount;
    }

    public TryItem[] getTries() {
        return this.tries;
    }

    public CodeItem internCodeItem(DexFile dexFile) {
        int n = this.parent.method.getPrototype().getParameterRegisterCount();
        if ((this.parent.accessFlags & AccessFlags.STATIC.getValue()) == 0) {
            ++n;
        }
        this.inWords = n;
        ArrayList arrayList = new ArrayList(3);
        TryItem[] arrtryItem = this.copyTryItems(dexFile, (List<EncodedCatchHandler>)arrayList);
        int n2 = arrayList.size();
        Object[] arrobject = null;
        if (n2 > 0) {
            arrobject = new EncodedCatchHandler[arrayList.size()];
            arrayList.toArray(arrobject);
        }
        CodeItem codeItem = new CodeItem(dexFile, this.registerCount, this.inWords, this.outWords, null, this.copyInstruction(dexFile), arrtryItem, (EncodedCatchHandler[])arrobject);
        return dexFile.CodeItemsSection.intern(codeItem);
    }

    @Override
    protected int placeItem(int n) {
        int n2 = n + (16 + 2 * CodeItem.super.getInstructionsLength());
        if (this.tries != null && this.tries.length > 0) {
            int n3 = AlignmentUtils.alignOffset(n2, 4) + 8 * this.tries.length;
            n2 = n3 + Leb128Utils.unsignedLeb128Size(this.encodedCatchHandlers.length);
            EncodedCatchHandler[] arrencodedCatchHandler = this.encodedCatchHandlers;
            int n4 = arrencodedCatchHandler.length;
            for (int i = 0; i < n4; ++i) {
                n2 = arrencodedCatchHandler[i].place(n2, n3);
            }
        }
        return n2;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.registerCount = input.readShort();
        this.inWords = input.readShort();
        this.outWords = input.readShort();
        int n = input.readShort();
        this.debugInfo = (DebugInfoItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_DEBUG_INFO_ITEM, input.readInt());
        if (this.debugInfo != null) {
            this.debugInfo.setParent((CodeItem)this);
        }
        int n2 = input.readInt();
        final ArrayList arrayList = new ArrayList();
        byte[] arrby = input.readBytes(n2 * 2);
        DexFile dexFile = this.dexFile;
        InstructionIterator.ProcessInstructionDelegate processInstructionDelegate = new InstructionIterator.ProcessInstructionDelegate(){

            @Override
            public void ProcessInstruction(int n, Instruction instruction) {
                arrayList.add((Object)instruction);
            }
        };
        InstructionIterator.IterateInstructions(dexFile, arrby, processInstructionDelegate);
        this.instructions = new Instruction[arrayList.size()];
        arrayList.toArray((Object[])this.instructions);
        if (n > 0) {
            input.alignTo(4);
            int n3 = input.getCursor();
            input.setCursor(n3 + n * 8);
            int n4 = input.getCursor();
            int n5 = input.readUnsignedLeb128();
            SparseArray<EncodedCatchHandler> sparseArray = new SparseArray<EncodedCatchHandler>(n5);
            this.encodedCatchHandlers = new EncodedCatchHandler[n5];
            for (int i = 0; i < n5; ++i) {
                try {
                    EncodedCatchHandler encodedCatchHandler;
                    int n6 = input.getCursor() - n4;
                    EncodedCatchHandler[] arrencodedCatchHandler = this.encodedCatchHandlers;
                    arrencodedCatchHandler[i] = encodedCatchHandler = new EncodedCatchHandler(this.dexFile, input, null);
                    sparseArray.append(n6, this.encodedCatchHandlers[i]);
                }
                catch (Exception exception) {
                    throw ExceptionWithContext.withContext(exception, "Error while reading EncodedCatchHandler at index " + i);
                }
            }
            int n7 = input.getCursor();
            input.setCursor(n3);
            this.tries = new TryItem[n];
            for (int i = 0; i < n; ++i) {
                try {
                    TryItem tryItem;
                    TryItem[] arrtryItem = this.tries;
                    arrtryItem[i] = tryItem = new TryItem(input, sparseArray, null);
                }
                catch (Exception exception) {
                    throw ExceptionWithContext.withContext(exception, "Error while reading TryItem at index " + i);
                }
            }
            input.setCursor(n7);
        }
    }

    protected void setParent(ClassDataItem.EncodedMethod encodedMethod) {
        this.parent = encodedMethod;
    }

    public void updateCode(Instruction[] arrinstruction) {
        this.update = true;
        this.instructions = arrinstruction;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        int n = CodeItem.super.getInstructionsLength();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, this.parent.method.getMethodString());
            annotatedOutput.annotate(2, "registers_size: 0x" + Integer.toHexString((int)this.registerCount) + " (" + this.registerCount + ")");
            annotatedOutput.annotate(2, "ins_size: 0x" + Integer.toHexString((int)this.inWords) + " (" + this.inWords + ")");
            annotatedOutput.annotate(2, "outs_size: 0x" + Integer.toHexString((int)this.outWords) + " (" + this.outWords + ")");
            int n2 = this.tries == null ? 0 : this.tries.length;
            annotatedOutput.annotate(2, "tries_size: 0x" + Integer.toHexString((int)n2) + " (" + n2 + ")");
            if (this.debugInfo == null) {
                annotatedOutput.annotate(4, "debug_info_off:");
            } else {
                annotatedOutput.annotate(4, "debug_info_off: 0x" + this.debugInfo.getOffset());
            }
            annotatedOutput.annotate(4, "insns_size: 0x" + Integer.toHexString((int)n) + " (" + n + ")");
        }
        annotatedOutput.writeShort(this.registerCount);
        annotatedOutput.writeShort(this.inWords);
        annotatedOutput.writeShort(this.outWords);
        if (this.tries == null) {
            annotatedOutput.writeShort(0);
        } else {
            annotatedOutput.writeShort(this.tries.length);
        }
        if (this.debugInfo == null) {
            annotatedOutput.writeInt(0);
        } else {
            annotatedOutput.writeInt(this.debugInfo.getOffset());
        }
        annotatedOutput.writeInt(n);
        Instruction[] arrinstruction = this.instructions;
        int n3 = 0;
        int n4 = arrinstruction.length;
        for (int i = 0; i < n4; ++i) {
            n3 = arrinstruction[i].write(annotatedOutput, n3);
        }
        if (this.tries == null || this.tries.length <= 0) return;
        if (annotatedOutput.annotates()) {
            if (n3 % 2 != 0) {
                annotatedOutput.annotate("padding");
                annotatedOutput.writeShort(0);
            }
            TryItem[] arrtryItem = this.tries;
            int n5 = arrtryItem.length;
            int n6 = 0;
            for (int i = 0; i < n5; ++i) {
                TryItem tryItem = arrtryItem[i];
                StringBuilder stringBuilder = new StringBuilder().append("[0x");
                int n7 = n6 + 1;
                annotatedOutput.annotate(0, stringBuilder.append(Integer.toHexString((int)n6)).append("] try_item").toString());
                annotatedOutput.indent();
                tryItem.writeTo(annotatedOutput);
                annotatedOutput.deindent();
                n6 = n7;
            }
            annotatedOutput.annotate("handler_count: 0x" + Integer.toHexString((int)this.encodedCatchHandlers.length) + "(" + this.encodedCatchHandlers.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.encodedCatchHandlers.length);
            EncodedCatchHandler[] arrencodedCatchHandler = this.encodedCatchHandlers;
            int n8 = arrencodedCatchHandler.length;
            int n9 = 0;
            for (int i = 0; i < n8; ++i) {
                EncodedCatchHandler encodedCatchHandler = arrencodedCatchHandler[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n10 = n9 + 1;
                annotatedOutput.annotate(0, stringBuilder.append(Integer.toHexString((int)n9)).append("] encoded_catch_handler").toString());
                annotatedOutput.indent();
                encodedCatchHandler.writeTo(annotatedOutput);
                annotatedOutput.deindent();
                n9 = n10;
            }
            return;
        } else {
            if (n3 % 2 != 0) {
                annotatedOutput.writeShort(0);
            }
            TryItem[] arrtryItem = this.tries;
            int n11 = arrtryItem.length;
            for (int i = 0; i < n11; ++i) {
                arrtryItem[i].writeTo(annotatedOutput);
            }
            annotatedOutput.writeUnsignedLeb128(this.encodedCatchHandlers.length);
            EncodedCatchHandler[] arrencodedCatchHandler = this.encodedCatchHandlers;
            int n12 = arrencodedCatchHandler.length;
            for (int i = 0; i < n12; ++i) {
                arrencodedCatchHandler[i].writeTo(annotatedOutput);
            }
        }
    }

    private class DebugInstructionFixer
    extends DebugInstructionIterator.ProcessRawDebugInstructionDelegate {
        static final /* synthetic */ boolean $assertionsDisabled;
        private int currentCodeAddress = 0;
        private SparseIntArray newAddressByOriginalAddress;
        private final byte[] originalEncodedDebugInfo;
        public byte[] result = null;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !CodeItem.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        public DebugInstructionFixer(byte[] arrby, SparseIntArray sparseIntArray) {
            this.newAddressByOriginalAddress = sparseIntArray;
            this.originalEncodedDebugInfo = arrby;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void ProcessAdvancePC(int n, int n2, int n3) {
            int n4;
            this.currentCodeAddress = n3 + this.currentCodeAddress;
            if (this.result != null || (n4 = this.newAddressByOriginalAddress.get(this.currentCodeAddress, -1)) == -1 || n4 == this.currentCodeAddress) {
                return;
            }
            int n5 = n4 - (this.currentCodeAddress - n3);
            if (!$assertionsDisabled && n5 <= 0) {
                throw new AssertionError();
            }
            int n6 = Leb128Utils.unsignedLeb128Size(n5);
            if (n6 + 1 == n2) {
                this.result = this.originalEncodedDebugInfo;
                Leb128Utils.writeUnsignedLeb128(n5, this.result, n + 1);
                return;
            }
            this.result = new byte[n6 + this.originalEncodedDebugInfo.length - (n2 - 1)];
            System.arraycopy((Object)this.originalEncodedDebugInfo, (int)0, (Object)this.result, (int)0, (int)n);
            this.result[n] = DebugOpcode.DBG_ADVANCE_PC.value;
            Leb128Utils.writeUnsignedLeb128(n5, this.result, n + 1);
            System.arraycopy((Object)this.originalEncodedDebugInfo, (int)(n + n2), (Object)this.result, (int)(1 + (n + n6)), (int)(this.originalEncodedDebugInfo.length - (1 + (n + n6))));
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void ProcessSpecialOpcode(int n, int n2, int n3, int n4) {
            int n5;
            block7 : {
                block6 : {
                    this.currentCodeAddress = n4 + this.currentCodeAddress;
                    if (this.result != null) break block6;
                    n5 = this.newAddressByOriginalAddress.get(this.currentCodeAddress, -1);
                    if (!$assertionsDisabled && n5 == -1) {
                        throw new AssertionError();
                    }
                    if (n5 != this.currentCodeAddress) break block7;
                }
                return;
            }
            int n6 = n5 - (this.currentCodeAddress - n4);
            if (!$assertionsDisabled && n6 <= 0) {
                throw new AssertionError();
            }
            if (n3 < 2 && n6 > 16 || n3 > 1 && n6 > 15) {
                int n7 = n5 - this.currentCodeAddress;
                int n8 = Leb128Utils.signedLeb128Size(n7);
                this.result = new byte[1 + (n8 + this.originalEncodedDebugInfo.length)];
                System.arraycopy((Object)this.originalEncodedDebugInfo, (int)0, (Object)this.result, (int)0, (int)n);
                this.result[n] = 1;
                Leb128Utils.writeUnsignedLeb128(n7, this.result, n + 1);
                System.arraycopy((Object)this.originalEncodedDebugInfo, (int)n, (Object)this.result, (int)(1 + (n + n8)), (int)(this.result.length - (1 + (n + n8))));
                return;
            }
            this.result = this.originalEncodedDebugInfo;
            this.result[n] = DebugInfoBuilder.calculateSpecialOpcode(n3, n6);
        }
    }

    public static class EncodedCatchHandler {
        private int baseOffset;
        private int catchAllHandlerAddress;
        public final EncodedTypeAddrPair[] handlers;
        private int offset;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private EncodedCatchHandler(DexFile dexFile, Input input) {
            int n = input.readSignedLeb128();
            this.handlers = n < 0 ? new EncodedTypeAddrPair[n * -1] : new EncodedTypeAddrPair[n];
            for (int i = 0; i < this.handlers.length; ++i) {
                try {
                    this.handlers[i] = new EncodedTypeAddrPair(dexFile, input, null);
                    continue;
                }
                catch (Exception exception) {
                    throw ExceptionWithContext.withContext(exception, "Error while reading EncodedTypeAddrPair at index " + i);
                }
            }
            if (n <= 0) {
                this.catchAllHandlerAddress = input.readUnsignedLeb128();
                return;
            }
            this.catchAllHandlerAddress = -1;
        }

        /* synthetic */ EncodedCatchHandler(DexFile dexFile, Input input, 1 var3_2) {
            super(dexFile, input);
        }

        public EncodedCatchHandler(EncodedTypeAddrPair[] arrencodedTypeAddrPair, int n) {
            this.handlers = arrencodedTypeAddrPair;
            this.catchAllHandlerAddress = n;
        }

        static /* synthetic */ int access$500(EncodedCatchHandler encodedCatchHandler) {
            return encodedCatchHandler.catchAllHandlerAddress;
        }

        static /* synthetic */ int access$502(EncodedCatchHandler encodedCatchHandler, int n) {
            encodedCatchHandler.catchAllHandlerAddress = n;
            return n;
        }

        private int getOffsetInList() {
            return this.offset - this.baseOffset;
        }

        private int place(int n, int n2) {
            this.offset = n;
            this.baseOffset = n2;
            int n3 = this.handlers.length;
            if (this.catchAllHandlerAddress > -1) {
                n3 *= -1;
                n += Leb128Utils.unsignedLeb128Size(this.catchAllHandlerAddress);
            }
            int n4 = n + Leb128Utils.signedLeb128Size(n3);
            EncodedTypeAddrPair[] arrencodedTypeAddrPair = this.handlers;
            int n5 = arrencodedTypeAddrPair.length;
            for (int i = 0; i < n5; ++i) {
                n4 += arrencodedTypeAddrPair[i].getSize();
            }
            return n4;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void writeTo(AnnotatedOutput annotatedOutput) {
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate("size: 0x" + Integer.toHexString((int)this.handlers.length) + " (" + this.handlers.length + ")");
                int n = this.handlers.length;
                if (this.catchAllHandlerAddress > -1) {
                    n *= -1;
                }
                annotatedOutput.writeSignedLeb128(n);
                EncodedTypeAddrPair[] arrencodedTypeAddrPair = this.handlers;
                int n2 = arrencodedTypeAddrPair.length;
                int n3 = 0;
                for (int i = 0; i < n2; ++i) {
                    EncodedTypeAddrPair encodedTypeAddrPair = arrencodedTypeAddrPair[i];
                    StringBuilder stringBuilder = new StringBuilder().append("[");
                    int n4 = n3 + 1;
                    annotatedOutput.annotate(0, stringBuilder.append(n3).append("] encoded_type_addr_pair").toString());
                    annotatedOutput.indent();
                    encodedTypeAddrPair.writeTo(annotatedOutput);
                    annotatedOutput.deindent();
                    n3 = n4;
                }
                if (this.catchAllHandlerAddress <= -1) return;
                {
                    annotatedOutput.annotate("catch_all_addr: 0x" + Integer.toHexString((int)this.catchAllHandlerAddress));
                    annotatedOutput.writeUnsignedLeb128(this.catchAllHandlerAddress);
                    return;
                }
            } else {
                int n = this.handlers.length;
                if (this.catchAllHandlerAddress > -1) {
                    n *= -1;
                }
                annotatedOutput.writeSignedLeb128(n);
                EncodedTypeAddrPair[] arrencodedTypeAddrPair = this.handlers;
                int n5 = arrencodedTypeAddrPair.length;
                for (int i = 0; i < n5; ++i) {
                    arrencodedTypeAddrPair[i].writeTo(annotatedOutput);
                }
                if (this.catchAllHandlerAddress <= -1) return;
                {
                    annotatedOutput.writeUnsignedLeb128(this.catchAllHandlerAddress);
                    return;
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            if (this != object) {
                if (object == null || !this.getClass().equals((Object)object.getClass())) {
                    return false;
                }
                EncodedCatchHandler encodedCatchHandler = (EncodedCatchHandler)object;
                if (this.handlers.length != encodedCatchHandler.handlers.length || this.catchAllHandlerAddress != encodedCatchHandler.catchAllHandlerAddress) {
                    return false;
                }
                for (int i = 0; i < this.handlers.length; ++i) {
                    if (this.handlers[i].equals(encodedCatchHandler.handlers[i])) continue;
                    return false;
                }
            }
            return true;
        }

        public int getCatchAllHandlerAddress() {
            return this.catchAllHandlerAddress;
        }

        public int hashCode() {
            int n = 0;
            for (EncodedTypeAddrPair encodedTypeAddrPair : this.handlers) {
                n = n * 31 + encodedTypeAddrPair.hashCode();
            }
            return n * 31 + this.catchAllHandlerAddress;
        }
    }

    public static class EncodedTypeAddrPair {
        public final TypeIdItem exceptionType;
        private int handlerAddress;

        private EncodedTypeAddrPair(DexFile dexFile, Input input) {
            this.exceptionType = dexFile.TypeIdsSection.getItemByIndex(input.readUnsignedLeb128());
            this.handlerAddress = input.readUnsignedLeb128();
        }

        /* synthetic */ EncodedTypeAddrPair(DexFile dexFile, Input input, 1 var3_2) {
            super(dexFile, input);
        }

        public EncodedTypeAddrPair(TypeIdItem typeIdItem, int n) {
            this.exceptionType = typeIdItem;
            this.handlerAddress = n;
        }

        static /* synthetic */ int access$600(EncodedTypeAddrPair encodedTypeAddrPair) {
            return encodedTypeAddrPair.handlerAddress;
        }

        static /* synthetic */ int access$602(EncodedTypeAddrPair encodedTypeAddrPair, int n) {
            encodedTypeAddrPair.handlerAddress = n;
            return n;
        }

        private int getSize() {
            return Leb128Utils.unsignedLeb128Size(this.exceptionType.getIndex()) + Leb128Utils.unsignedLeb128Size(this.handlerAddress);
        }

        private void writeTo(AnnotatedOutput annotatedOutput) {
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate("exception_type: " + this.exceptionType.getTypeDescriptor());
                annotatedOutput.writeUnsignedLeb128(this.exceptionType.getIndex());
                annotatedOutput.annotate("handler_addr: 0x" + Integer.toHexString((int)this.handlerAddress));
                annotatedOutput.writeUnsignedLeb128(this.handlerAddress);
                return;
            }
            annotatedOutput.writeUnsignedLeb128(this.exceptionType.getIndex());
            annotatedOutput.writeUnsignedLeb128(this.handlerAddress);
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
                    EncodedTypeAddrPair encodedTypeAddrPair = (EncodedTypeAddrPair)object;
                    if (this.exceptionType != encodedTypeAddrPair.exceptionType || this.handlerAddress != encodedTypeAddrPair.handlerAddress) break block5;
                }
                return true;
            }
            return false;
        }

        public int getHandlerAddress() {
            return this.handlerAddress;
        }

        public int hashCode() {
            return 31 * this.exceptionType.hashCode() + this.handlerAddress;
        }
    }

    public static class TryItem {
        public final EncodedCatchHandler encodedCatchHandler;
        private int startCodeAddress;
        private int tryLength;

        public TryItem(int n, int n2, EncodedCatchHandler encodedCatchHandler) {
            this.startCodeAddress = n;
            this.tryLength = n2;
            this.encodedCatchHandler = encodedCatchHandler;
        }

        private TryItem(Input input, SparseArray<EncodedCatchHandler> sparseArray) {
            this.startCodeAddress = input.readInt();
            this.tryLength = input.readShort();
            this.encodedCatchHandler = sparseArray.get(input.readShort());
            if (this.encodedCatchHandler == null) {
                throw new RuntimeException("Could not find the EncodedCatchHandler referenced by this TryItem");
            }
        }

        /* synthetic */ TryItem(Input input, SparseArray sparseArray, 1 var3_2) {
            super(input, sparseArray);
        }

        static /* synthetic */ int access$700(TryItem tryItem) {
            return tryItem.startCodeAddress;
        }

        static /* synthetic */ int access$702(TryItem tryItem, int n) {
            tryItem.startCodeAddress = n;
            return n;
        }

        static /* synthetic */ int access$800(TryItem tryItem) {
            return tryItem.tryLength;
        }

        static /* synthetic */ int access$802(TryItem tryItem, int n) {
            tryItem.tryLength = n;
            return n;
        }

        private void writeTo(AnnotatedOutput annotatedOutput) {
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate(4, "start_addr: 0x" + Integer.toHexString((int)this.startCodeAddress));
                annotatedOutput.annotate(2, "try_length: 0x" + Integer.toHexString((int)this.tryLength) + " (" + this.tryLength + ")");
                annotatedOutput.annotate(2, "handler_off: 0x" + Integer.toHexString((int)this.encodedCatchHandler.getOffsetInList()));
            }
            annotatedOutput.writeInt(this.startCodeAddress);
            annotatedOutput.writeShort(this.tryLength);
            annotatedOutput.writeShort(this.encodedCatchHandler.getOffsetInList());
        }

        public int getStartCodeAddress() {
            return this.startCodeAddress;
        }

        public int getTryLength() {
            return this.tryLength;
        }
    }

}

