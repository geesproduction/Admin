/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction31t
extends Instruction
implements OffsetInstruction,
SingleRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte regA;
    private int targetAddressOffset;

    public Instruction31t(Opcode opcode, short s, int n) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        this.regA = (byte)s;
        this.targetAddressOffset = n;
    }

    private Instruction31t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = arrby[n + 1];
        this.targetAddressOffset = NumberUtils.decodeInt(arrby, n + 2);
    }

    /* synthetic */ Instruction31t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format31t;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    public int getTargetAddressOffset() {
        return this.targetAddressOffset;
    }

    @Override
    public void updateTargetAddressOffset(int n) {
        this.targetAddressOffset = n;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeInt(this.targetAddressOffset + (n + this.targetAddressOffset) % 2);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction31t(opcode, arrby, n, null);
        }
    }

}

