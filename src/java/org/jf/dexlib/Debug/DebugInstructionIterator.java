/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 */
package org.jf.dexlib.Debug;

import org.jf.dexlib.Debug.DebugOpcode;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.ByteArrayInput;
import org.jf.dexlib.Util.Input;

public class DebugInstructionIterator {
    public static void DecodeInstructions(DebugInfoItem debugInfoItem, int n, ProcessDecodedDebugInstructionDelegate processDecodedDebugInstructionDelegate) {
        int n2 = 0;
        int n3 = debugInfoItem.getLineStart();
        ByteArrayInput byteArrayInput = new ByteArrayInput(debugInfoItem.getEncodedDebugInfo());
        DexFile dexFile = debugInfoItem.getDexFile();
        Local[] arrlocal = new Local[n];
        block13 : do {
            int n4 = byteArrayInput.getCursor();
            byte by = byteArrayInput.readByte();
            switch (1.$SwitchMap$org$jf$dexlib$Debug$DebugOpcode[DebugOpcode.getDebugOpcodeByValue(by).ordinal()]) {
                default: {
                    continue block13;
                }
                case 1: {
                    return;
                }
                case 2: {
                    n2 += byteArrayInput.readUnsignedLeb128();
                    continue block13;
                }
                case 3: {
                    n3 += byteArrayInput.readSignedLeb128();
                    continue block13;
                }
                case 4: {
                    int n5 = byteArrayInput.readUnsignedLeb128();
                    StringIdItem stringIdItem = dexFile.StringIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    TypeIdItem typeIdItem = dexFile.TypeIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    arrlocal[n5] = new Local(n5, stringIdItem, typeIdItem, null);
                    processDecodedDebugInstructionDelegate.ProcessStartLocal(n2, byteArrayInput.getCursor() - n4, n5, stringIdItem, typeIdItem);
                    continue block13;
                }
                case 5: {
                    int n6 = byteArrayInput.readUnsignedLeb128();
                    StringIdItem stringIdItem = dexFile.StringIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    TypeIdItem typeIdItem = dexFile.TypeIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    StringIdItem stringIdItem2 = dexFile.StringIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    arrlocal[n6] = new Local(n6, stringIdItem, typeIdItem, stringIdItem2);
                    processDecodedDebugInstructionDelegate.ProcessStartLocalExtended(n2, byteArrayInput.getCursor() - n4, n6, stringIdItem, typeIdItem, stringIdItem2);
                    continue block13;
                }
                case 6: {
                    int n7 = byteArrayInput.readUnsignedLeb128();
                    Local local = arrlocal[n7];
                    if (local == null) {
                        int n8 = byteArrayInput.getCursor() - n4;
                        processDecodedDebugInstructionDelegate.ProcessEndLocal(n2, n8, n7, null, null, null);
                        continue block13;
                    }
                    int n9 = byteArrayInput.getCursor() - n4;
                    StringIdItem stringIdItem = local.name;
                    TypeIdItem typeIdItem = local.type;
                    StringIdItem stringIdItem3 = local.signature;
                    processDecodedDebugInstructionDelegate.ProcessEndLocal(n2, n9, n7, stringIdItem, typeIdItem, stringIdItem3);
                    continue block13;
                }
                case 7: {
                    int n10 = byteArrayInput.readUnsignedLeb128();
                    Local local = arrlocal[n10];
                    if (local == null) {
                        int n11 = byteArrayInput.getCursor() - n4;
                        processDecodedDebugInstructionDelegate.ProcessRestartLocal(n2, n11, n10, null, null, null);
                        continue block13;
                    }
                    int n12 = byteArrayInput.getCursor() - n4;
                    StringIdItem stringIdItem = local.name;
                    TypeIdItem typeIdItem = local.type;
                    StringIdItem stringIdItem4 = local.signature;
                    processDecodedDebugInstructionDelegate.ProcessRestartLocal(n2, n12, n10, stringIdItem, typeIdItem, stringIdItem4);
                    continue block13;
                }
                case 8: {
                    processDecodedDebugInstructionDelegate.ProcessSetPrologueEnd(n2);
                    continue block13;
                }
                case 9: {
                    processDecodedDebugInstructionDelegate.ProcessSetEpilogueBegin(n2);
                    continue block13;
                }
                case 10: {
                    StringIdItem stringIdItem = dexFile.StringIdsSection.getOptionalItemByIndex(-1 + byteArrayInput.readUnsignedLeb128());
                    processDecodedDebugInstructionDelegate.ProcessSetFile(n2, byteArrayInput.getCursor() - n4, stringIdItem);
                    continue block13;
                }
                case 11: 
            }
            int n13 = -10 + (by & 255);
            processDecodedDebugInstructionDelegate.ProcessLineEmit(n2 += n13 / 15, n3 += -4 + n13 % 15);
        } while (true);
    }

    public static void IterateInstructions(Input input, ProcessRawDebugInstructionDelegate processRawDebugInstructionDelegate) {
        block12 : do {
            int n = input.getCursor();
            byte by = input.readByte();
            switch (by) {
                default: {
                    int n2 = -10 + (by & 255);
                    processRawDebugInstructionDelegate.ProcessSpecialOpcode(n, by, -4 + n2 % 15, n2 / 15);
                    continue block12;
                }
                case 0: {
                    processRawDebugInstructionDelegate.ProcessEndSequence(n);
                    return;
                }
                case 1: {
                    int n3 = input.readUnsignedLeb128();
                    processRawDebugInstructionDelegate.ProcessAdvancePC(n, input.getCursor() - n, n3);
                    continue block12;
                }
                case 2: {
                    int n4 = input.readSignedLeb128();
                    processRawDebugInstructionDelegate.ProcessAdvanceLine(n, input.getCursor() - n, n4);
                    continue block12;
                }
                case 3: {
                    int n5 = input.readUnsignedOrSignedLeb128();
                    boolean bl = false;
                    if (n5 < 0) {
                        bl = true;
                        n5 ^= -1;
                    }
                    int n6 = -1 + input.readUnsignedLeb128();
                    int n7 = -1 + input.readUnsignedLeb128();
                    processRawDebugInstructionDelegate.ProcessStartLocal(n, input.getCursor() - n, n5, n6, n7, bl);
                    continue block12;
                }
                case 4: {
                    int n8 = input.readUnsignedOrSignedLeb128();
                    boolean bl = false;
                    if (n8 < 0) {
                        bl = true;
                        n8 ^= -1;
                    }
                    int n9 = -1 + input.readUnsignedLeb128();
                    int n10 = -1 + input.readUnsignedLeb128();
                    int n11 = -1 + input.readUnsignedLeb128();
                    processRawDebugInstructionDelegate.ProcessStartLocalExtended(n, input.getCursor() - n, n8, n9, n10, n11, bl);
                    continue block12;
                }
                case 5: {
                    int n12 = input.readUnsignedOrSignedLeb128();
                    boolean bl = false;
                    if (n12 < 0) {
                        bl = true;
                        n12 ^= -1;
                    }
                    processRawDebugInstructionDelegate.ProcessEndLocal(n, input.getCursor() - n, n12, bl);
                    continue block12;
                }
                case 6: {
                    int n13 = input.readUnsignedOrSignedLeb128();
                    boolean bl = false;
                    if (n13 < 0) {
                        bl = true;
                        n13 ^= -1;
                    }
                    processRawDebugInstructionDelegate.ProcessRestartLocal(n, input.getCursor() - n, n13, bl);
                    continue block12;
                }
                case 7: {
                    processRawDebugInstructionDelegate.ProcessSetPrologueEnd(n);
                    continue block12;
                }
                case 8: {
                    processRawDebugInstructionDelegate.ProcessSetEpilogueBegin(n);
                    continue block12;
                }
                case 9: 
            }
            int n14 = input.readUnsignedLeb128();
            processRawDebugInstructionDelegate.ProcessSetFile(n, input.getCursor() - n, n14);
        } while (true);
    }

    private static class Local {
        public final StringIdItem name;
        public final int register;
        public final StringIdItem signature;
        public final TypeIdItem type;

        public Local(int n, StringIdItem stringIdItem, TypeIdItem typeIdItem, StringIdItem stringIdItem2) {
            this.register = n;
            this.name = stringIdItem;
            this.type = typeIdItem;
            this.signature = stringIdItem2;
        }
    }

    public static class ProcessDecodedDebugInstructionDelegate {
        public void ProcessEndLocal(int n, int n2, int n3, StringIdItem stringIdItem, TypeIdItem typeIdItem, StringIdItem stringIdItem2) {
        }

        public void ProcessLineEmit(int n, int n2) {
        }

        public void ProcessRestartLocal(int n, int n2, int n3, StringIdItem stringIdItem, TypeIdItem typeIdItem, StringIdItem stringIdItem2) {
        }

        public void ProcessSetEpilogueBegin(int n) {
        }

        public void ProcessSetFile(int n, int n2, StringIdItem stringIdItem) {
        }

        public void ProcessSetPrologueEnd(int n) {
        }

        public void ProcessStartLocal(int n, int n2, int n3, StringIdItem stringIdItem, TypeIdItem typeIdItem) {
        }

        public void ProcessStartLocalExtended(int n, int n2, int n3, StringIdItem stringIdItem, TypeIdItem typeIdItem, StringIdItem stringIdItem2) {
        }
    }

    public static class ProcessRawDebugInstructionDelegate {
        public void ProcessAdvanceLine(int n, int n2, int n3) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_ADVANCE_LINE, n, n2);
        }

        public void ProcessAdvancePC(int n, int n2, int n3) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_ADVANCE_PC, n, n2);
        }

        public void ProcessEndLocal(int n, int n2, int n3, boolean bl) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_END_LOCAL, n, n2);
        }

        public void ProcessEndSequence(int n) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_END_SEQUENCE, n, 1);
        }

        public void ProcessRestartLocal(int n, int n2, int n3, boolean bl) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_RESTART_LOCAL, n, n2);
        }

        public void ProcessSetEpilogueBegin(int n) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_SET_EPILOGUE_BEGIN, n, 1);
        }

        public void ProcessSetFile(int n, int n2, int n3) {
        }

        public void ProcessSetPrologueEnd(int n) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_SET_PROLOGUE_END, n, 1);
        }

        public void ProcessSpecialOpcode(int n, int n2, int n3, int n4) {
            this.ProcessStaticOpcode(DebugOpcode.DBG_SPECIAL_OPCODE, n, 1);
        }

        public void ProcessStartLocal(int n, int n2, int n3, int n4, int n5, boolean bl) {
        }

        public void ProcessStartLocalExtended(int n, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        }

        public void ProcessStaticOpcode(DebugOpcode debugOpcode, int n, int n2) {
        }
    }

}

