/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.Iterator
 */
package org.jf.dexlib.Code.Format;

import java.util.Iterator;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.MultiOffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class PackedSwitchDataPseudoInstruction
extends Instruction
implements MultiOffsetInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private int firstKey;
    private int[] targets;

    public PackedSwitchDataPseudoInstruction(int n, int[] arrn) {
        super(Opcode.NOP);
        if (arrn.length > 65535) {
            throw new RuntimeException("The packed-switch data contains too many elements. The maximum number of switch elements is 65535");
        }
        this.firstKey = n;
        this.targets = arrn;
    }

    public PackedSwitchDataPseudoInstruction(byte[] arrby, int n) {
        super(Opcode.NOP);
        if (arrby[n] != 0) {
            throw new RuntimeException("Invalid opcode byte for a PackedSwitchData pseudo-instruction");
        }
        if (arrby[n + 1] != 1) {
            throw new RuntimeException("Invalid sub-opcode byte for a PackedSwitchData pseudo-instruction");
        }
        int n2 = NumberUtils.decodeUnsignedShort(arrby, n + 2);
        this.firstKey = NumberUtils.decodeInt(arrby, n + 4);
        this.targets = new int[n2];
        for (int i = 0; i < n2; ++i) {
            this.targets[i] = NumberUtils.decodeInt(arrby, n + 8 + i * 4);
        }
    }

    @Override
    protected void annotateInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.annotate(2 * this.getSize(n), "[0x" + Integer.toHexString((int)n) + "] " + "packed-switch-data instruction");
    }

    public int getFirstKey() {
        return this.firstKey;
    }

    @Override
    public Format getFormat() {
        return Format.PackedSwitchData;
    }

    @Override
    public int getSize(int n) {
        return 4 + 2 * this.getTargetCount() + n % 2;
    }

    public int getTargetCount() {
        return this.targets.length;
    }

    @Override
    public int[] getTargets() {
        return this.targets;
    }

    public Iterator<PackedSwitchTarget> iterateKeysAndTargets() {
        return new Iterator<PackedSwitchTarget>(){
            int i;
            PackedSwitchTarget packedSwitchTarget;
            final int targetCount;
            int value;
            {
                this.targetCount = PackedSwitchDataPseudoInstruction.this.getTargetCount();
                this.i = 0;
                this.value = PackedSwitchDataPseudoInstruction.this.getFirstKey();
                this.packedSwitchTarget = new PackedSwitchTarget();
            }

            public boolean hasNext() {
                return this.i < this.targetCount;
            }

            public PackedSwitchTarget next() {
                PackedSwitchTarget packedSwitchTarget = this.packedSwitchTarget;
                int n = this.value;
                this.value = n + 1;
                packedSwitchTarget.value = n;
                this.packedSwitchTarget.targetAddressOffset = PackedSwitchDataPseudoInstruction.this.targets[this.i];
                this.i = 1 + this.i;
                return this.packedSwitchTarget;
            }

            public void remove() {
            }
        };
    }

    @Override
    public void updateTarget(int n, int n2) {
        this.targets[n] = n2;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.alignTo(4);
        annotatedOutput.writeByte(0);
        annotatedOutput.writeByte(1);
        annotatedOutput.writeShort(this.targets.length);
        annotatedOutput.writeInt(this.firstKey);
        int[] arrn = this.targets;
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            annotatedOutput.writeInt(arrn[i]);
        }
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            if (opcode != Opcode.NOP) {
                throw new RuntimeException("The opcode for a PackedSwitchDataPseudoInstruction must be NOP");
            }
            return new PackedSwitchDataPseudoInstruction(arrby, n);
        }
    }

    public static class PackedSwitchTarget {
        public int targetAddressOffset;
        public int value;
    }

}

