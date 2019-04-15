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
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction11x
extends Instruction
implements SingleRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte regA;

    public Instruction11x(Opcode opcode, short s) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        this.regA = (byte)s;
    }

    private Instruction11x(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = (byte)NumberUtils.decodeUnsignedByte(arrby[n + 1]);
    }

    /* synthetic */ Instruction11x(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format11x;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    public void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction11x(opcode, arrby, n, null);
        }
    }

}

