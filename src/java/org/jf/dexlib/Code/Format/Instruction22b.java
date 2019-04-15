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
import org.jf.dexlib.Code.TwoRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;

public class Instruction22b
extends Instruction
implements TwoRegisterInstruction,
LiteralInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte litC;
    private byte regA;
    private byte regB;

    public Instruction22b(Opcode opcode, short s, short s2, byte by) {
        super(opcode);
        if (s >= 256 || s2 >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        this.regA = (byte)s;
        this.regB = (byte)s2;
        this.litC = by;
    }

    private Instruction22b(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = arrby[n + 1];
        this.regB = arrby[n + 2];
        this.litC = arrby[n + 3];
    }

    /* synthetic */ Instruction22b(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format22b;
    }

    @Override
    public long getLiteral() {
        return this.litC;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    public int getRegisterB() {
        return 255 & this.regB;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeByte((int)this.regB);
        annotatedOutput.writeByte((int)this.litC);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction22b(opcode, arrby, n, null);
        }
    }

}

