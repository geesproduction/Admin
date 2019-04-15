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
import org.jf.dexlib.Code.OdexedInvokeVirtual;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.RegisterRangeInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction3rms
extends Instruction
implements RegisterRangeInstruction,
OdexedInvokeVirtual {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private short methodIndex;
    private byte regCount;
    private short startReg;

    public Instruction3rms(Opcode opcode, short s, int n, int n2) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("regCount must be less than 256");
        }
        if (s < 0) {
            throw new RuntimeException("regCount cannot be negative");
        }
        if (n >= 65536) {
            throw new RuntimeException("The beginning register of the range must be less than 65536");
        }
        if (n < 0) {
            throw new RuntimeException("The beginning register of the range cannot be negative");
        }
        if (n2 >= 65536) {
            throw new RuntimeException("The method index must be less than 65536");
        }
        this.regCount = (byte)s;
        this.startReg = (short)n;
        this.methodIndex = (short)n2;
    }

    private Instruction3rms(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        this.regCount = (byte)NumberUtils.decodeUnsignedByte(arrby[n + 1]);
        this.methodIndex = (short)NumberUtils.decodeUnsignedShort(arrby, n + 2);
        this.startReg = (short)NumberUtils.decodeUnsignedShort(arrby, n + 4);
    }

    /* synthetic */ Instruction3rms(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format3rms;
    }

    @Override
    public int getMethodIndex() {
        return 65535 & this.methodIndex;
    }

    @Override
    public short getRegCount() {
        return (short)(255 & this.regCount);
    }

    @Override
    public int getStartRegister() {
        return 65535 & this.startReg;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regCount);
        annotatedOutput.writeShort((int)this.methodIndex);
        annotatedOutput.writeShort((int)this.startReg);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction3rms(opcode, arrby, n, null);
        }
    }

}

