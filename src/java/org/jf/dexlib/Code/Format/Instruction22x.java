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

public class Instruction22x
extends Instruction
implements TwoRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte regA;
    private short regB;

    public Instruction22x(Opcode opcode, short s, int n) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        if (n >= 65536) {
            throw new RuntimeException("The register number must be less than v65536");
        }
        this.regA = (byte)s;
        this.regB = (short)n;
    }

    private Instruction22x(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = arrby[n + 1];
        this.regB = (short)NumberUtils.decodeUnsignedShort(arrby, n + 2);
    }

    /* synthetic */ Instruction22x(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format22x;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    public int getRegisterB() {
        return 65535 & this.regB;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
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
            return new Instruction22x(opcode, arrby, n, null);
        }
    }

}

