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
import org.jf.dexlib.Code.ThreeRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;

public class Instruction23x
extends Instruction
implements ThreeRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Instruction.InstructionFactory(null){

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction23x(opcode, arrby, n, null);
        }
    };
    private byte regA;
    private byte regB;
    private byte regC;

    public Instruction23x(Opcode opcode, short s, short s2, short s3) {
        super(opcode);
        if (s >= 256 || s2 >= 256 || s3 >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        this.regA = (byte)s;
        this.regB = (byte)s2;
        this.regC = (byte)s3;
    }

    private Instruction23x(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = arrby[n + 1];
        this.regB = arrby[n + 2];
        this.regC = arrby[n + 3];
    }

    /* synthetic */ Instruction23x(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format23x;
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
    public int getRegisterC() {
        return 255 & this.regC;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeByte((int)this.regB);
        annotatedOutput.writeByte((int)this.regC);
    }

}

