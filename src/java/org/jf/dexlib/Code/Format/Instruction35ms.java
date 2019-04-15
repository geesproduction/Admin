/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.FiveRegisterInstruction;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.OdexedInvokeVirtual;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction35ms
extends Instruction
implements FiveRegisterInstruction,
OdexedInvokeVirtual {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private short methodIndex;
    private byte regA;
    private byte regCount;
    private byte regD;
    private byte regE;
    private byte regF;
    private byte regG;

    public Instruction35ms(Opcode opcode, int n, byte by, byte by2, byte by3, byte by4, byte by5, int n2) {
        super(opcode);
        if (n > 5) {
            throw new RuntimeException("regCount cannot be greater than 5");
        }
        if (by >= 16 || by2 >= 16 || by3 >= 16 || by4 >= 16 || by5 >= 16) {
            throw new RuntimeException("All register args must fit in 4 bits");
        }
        if (n2 >= 65536) {
            throw new RuntimeException("The method index must be less than 65536");
        }
        this.regCount = (byte)n;
        this.regA = by5;
        this.regD = by;
        this.regE = by2;
        this.regF = by3;
        this.regG = by4;
        this.methodIndex = (short)n2;
    }

    private Instruction35ms(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regCount = NumberUtils.decodeHighUnsignedNibble(arrby[n + 1]);
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.regD = NumberUtils.decodeLowUnsignedNibble(arrby[n + 4]);
        this.regE = NumberUtils.decodeHighUnsignedNibble(arrby[n + 4]);
        this.regF = NumberUtils.decodeLowUnsignedNibble(arrby[n + 5]);
        this.regG = NumberUtils.decodeHighUnsignedNibble(arrby[n + 5]);
        this.methodIndex = (short)NumberUtils.decodeUnsignedShort(arrby, n + 2);
    }

    /* synthetic */ Instruction35ms(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format35ms;
    }

    @Override
    public int getMethodIndex() {
        return 65535 & this.methodIndex;
    }

    @Override
    public byte getRegCount() {
        return this.regCount;
    }

    @Override
    public byte getRegisterA() {
        return this.regA;
    }

    @Override
    public byte getRegisterD() {
        return this.regD;
    }

    @Override
    public byte getRegisterE() {
        return this.regE;
    }

    @Override
    public byte getRegisterF() {
        return this.regF;
    }

    @Override
    public byte getRegisterG() {
        return this.regG;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(this.regCount << 4 | this.regA);
        annotatedOutput.writeShort((int)this.methodIndex);
        annotatedOutput.writeByte(this.regE << 4 | this.regD);
        annotatedOutput.writeByte(this.regG << 4 | this.regF);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction35ms(opcode, arrby, n, null);
        }
    }

}

