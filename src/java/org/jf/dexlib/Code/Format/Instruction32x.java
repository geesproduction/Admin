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
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.TwoRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction32x
extends Instruction
implements TwoRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private short regA;
    private short regB;

    public Instruction32x(Opcode opcode, int n, int n2) {
        super(opcode);
        if (n >= 65536 || n2 >= 65536) {
            throw new RuntimeException("The register number must be less than v65536");
        }
        this.regA = (short)n;
        this.regB = (short)n2;
    }

    private Instruction32x(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = (short)NumberUtils.decodeUnsignedShort(arrby, n + 2);
        this.regB = (short)NumberUtils.decodeUnsignedShort(arrby, n + 4);
    }

    /* synthetic */ Instruction32x(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format32x;
    }

    @Override
    public int getRegisterA() {
        return 65535 & this.regA;
    }

    @Override
    public int getRegisterB() {
        return 65535 & this.regB;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(0);
        annotatedOutput.writeShort((int)this.regA);
        annotatedOutput.writeShort((int)this.regB);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction32x(opcode, arrby, n, null);
        }
    }

}

