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
import org.jf.dexlib.Code.OdexedFieldAccess;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.TwoRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction22cs
extends Instruction
implements TwoRegisterInstruction,
OdexedFieldAccess {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private short fieldOffset;
    private byte regA;
    private byte regB;

    public Instruction22cs(Opcode opcode, byte by, byte by2, int n) {
        super(opcode);
        if (by >= 16 || by2 >= 16) {
            throw new RuntimeException("The register number must be less than v16");
        }
        if (n >= 65536) {
            throw new RuntimeException("The field offset must be less than 65536");
        }
        this.regA = by;
        this.regB = by2;
        this.fieldOffset = (short)n;
    }

    private Instruction22cs(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.regB = NumberUtils.decodeHighUnsignedNibble(arrby[n + 1]);
        this.fieldOffset = (short)NumberUtils.decodeUnsignedShort(arrby, n + 2);
    }

    /* synthetic */ Instruction22cs(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public int getFieldOffset() {
        return 65535 & this.fieldOffset;
    }

    @Override
    public Format getFormat() {
        return Format.Format22cs;
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
        annotatedOutput.writeShort((int)this.fieldOffset);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction22cs(opcode, arrby, n, null);
        }
    }

}

