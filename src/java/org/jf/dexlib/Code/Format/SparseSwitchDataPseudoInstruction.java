/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
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

public class SparseSwitchDataPseudoInstruction
extends Instruction
implements MultiOffsetInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private int[] keys;
    private int[] targets;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !SparseSwitchDataPseudoInstruction.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public SparseSwitchDataPseudoInstruction(byte[] arrby, int n) {
        super(Opcode.NOP);
        if (arrby[n] != 0) {
            throw new RuntimeException("Invalid opcode byte for a SparseSwitchData pseudo-instruction");
        }
        if (arrby[n + 1] != 2) {
            throw new RuntimeException("Invalid sub-opcode byte for a SparseSwitchData pseudo-instruction");
        }
        int n2 = NumberUtils.decodeUnsignedShort(arrby, n + 2);
        this.keys = new int[n2];
        this.targets = new int[n2];
        for (int i = 0; i < n2; ++i) {
            this.keys[i] = NumberUtils.decodeInt(arrby, n + 4 + i * 4);
            this.targets[i] = NumberUtils.decodeInt(arrby, n + 4 + n2 * 4 + i * 4);
        }
    }

    public SparseSwitchDataPseudoInstruction(int[] arrn, int[] arrn2) {
        super(Opcode.NOP);
        if (arrn.length != arrn2.length) {
            throw new RuntimeException("The number of keys and targets don't match");
        }
        if (arrn2.length == 0) {
            throw new RuntimeException("The sparse-switch data must contain at least 1 key/target");
        }
        if (arrn2.length > 65535) {
            throw new RuntimeException("The sparse-switch data contains too many elements. The maximum number of switch elements is 65535");
        }
        this.keys = arrn;
        this.targets = arrn2;
    }

    @Override
    protected void annotateInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.annotate(2 * this.getSize(n), "[0x" + Integer.toHexString((int)n) + "] " + "sparse-switch-data instruction");
    }

    @Override
    public Format getFormat() {
        return Format.SparseSwitchData;
    }

    public int[] getKeys() {
        return this.keys;
    }

    @Override
    public int getSize(int n) {
        return 2 + 4 * this.getTargetCount() + n % 2;
    }

    public int getTargetCount() {
        return this.targets.length;
    }

    @Override
    public int[] getTargets() {
        return this.targets;
    }

    public Iterator<SparseSwitchTarget> iterateKeysAndTargets() {
        return new Iterator<SparseSwitchTarget>(){
            int i;
            SparseSwitchTarget sparseSwitchTarget;
            final int targetCount;
            {
                this.targetCount = SparseSwitchDataPseudoInstruction.this.getTargetCount();
                this.i = 0;
                this.sparseSwitchTarget = new SparseSwitchTarget();
            }

            public boolean hasNext() {
                return this.i < this.targetCount;
            }

            public SparseSwitchTarget next() {
                this.sparseSwitchTarget.key = SparseSwitchDataPseudoInstruction.this.keys[this.i];
                this.sparseSwitchTarget.targetAddressOffset = SparseSwitchDataPseudoInstruction.this.targets[this.i];
                this.i = 1 + this.i;
                return this.sparseSwitchTarget;
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
        annotatedOutput.writeByte(2);
        annotatedOutput.writeShort(this.targets.length);
        if (this.targets.length > 0) {
            annotatedOutput.writeInt(this.keys[0]);
            for (int i = 1; i < this.keys.length; ++i) {
                int n2 = this.keys[i];
                if (!$assertionsDisabled && n2 < this.keys[i - 1]) {
                    throw new AssertionError();
                }
                annotatedOutput.writeInt(n2);
            }
            int[] arrn = this.targets;
            int n3 = arrn.length;
            for (int i = 0; i < n3; ++i) {
                annotatedOutput.writeInt(arrn[i]);
            }
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
                throw new RuntimeException("The opcode for a SparseSwitchDataPseudoInstruction must be NOP");
            }
            return new SparseSwitchDataPseudoInstruction(arrby, n);
        }
    }

    public static class SparseSwitchTarget {
        public int key;
        public int targetAddressOffset;
    }

}

