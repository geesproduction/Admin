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
import org.jf.dexlib.Util.NumberUtils;

public class Instruction22s
extends Instruction
implements TwoRegisterInstruction,
LiteralInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private short litC;
    private byte regA;
    private byte regB;

    public Instruction22s(Opcode opcode, byte by, byte by2, short s) {
        super(opcode);
        if (by >= 16 || by2 >= 16) {
            throw new RuntimeException("The register number must be less than v16");
        }
        this.regA = by;
        this.regB = by2;
        this.litC = s;
    }

    private Instruction22s(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.regB = NumberUtils.decodeHighUnsignedNibble(arrby[n + 1]);
        this.litC = NumberUtils.decodeShort(arrby, n + 2);
    }

    /* synthetic */ Instruction22s(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format22s;
    }

    @Override
    public long getLiteral() {
        return this.litC;
    }

    @Override
    public int getRegisterA() {
        return this.regA;
    }

    @Override
    public int getRegisterB() {
        return this.regB;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(this.regB << 4 | this.regA);
        annotatedOutput.writeShort((int)this.litC);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction22s(opcode, arrby, n, null);
        }
    }

}

