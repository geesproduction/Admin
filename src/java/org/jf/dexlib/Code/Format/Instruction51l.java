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
import org.jf.dexlib.Code.LiteralInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction51l
extends Instruction
implements SingleRegisterInstruction,
LiteralInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private long litB;
    private byte regA;

    public Instruction51l(Opcode opcode, short s, long l) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        this.regA = (byte)s;
        this.litB = l;
    }

    private Instruction51l(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = (byte)NumberUtils.decodeUnsignedByte(arrby[n + 1]);
        this.litB = NumberUtils.decodeLong(arrby, n + 2);
    }

    /* synthetic */ Instruction51l(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format51l;
    }

    @Override
    public long getLiteral() {
        return this.litB;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeLong(this.litB);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction51l(opcode, arrby, n, null);
        }
    }

}

