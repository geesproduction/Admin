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

public class Instruction11n
extends Instruction
implements SingleRegisterInstruction,
LiteralInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte litB;
    private byte regA;

    public Instruction11n(Opcode opcode, byte by, byte by2) {
        super(opcode);
        if (by >= 16) {
            throw new RuntimeException("The register number must be less than v16");
        }
        if (by2 < -8 || by2 >= 8) {
            throw new RuntimeException("The literal value must be between -8 and 7 inclusive");
        }
        this.regA = by;
        this.litB = by2;
    }

    private Instruction11n(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.litB = NumberUtils.decodeHighSignedNibble(arrby[n + 1]);
    }

    /* synthetic */ Instruction11n(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format11n;
    }

    @Override
    public long getLiteral() {
        return this.litB;
    }

    @Override
    public int getRegisterA() {
        return this.regA;
    }

    @Override
    public void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(this.litB << 4 | this.regA);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction11n(opcode, arrby, n, null);
        }
    }

}

