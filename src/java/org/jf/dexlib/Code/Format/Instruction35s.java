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
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction35s
extends InstructionWithReference
implements FiveRegisterInstruction {
    public static final Instruction.InstructionFactory Factory = new Factory(null);
    private byte regA;
    private byte regCount;
    private byte regD;
    private byte regE;
    private byte regF;
    private byte regG;

    public Instruction35s(Opcode opcode, int n, byte by, byte by2, byte by3, byte by4, byte by5, Item item) {
        super(opcode, item);
        if (n > 5) {
            throw new RuntimeException("regCount cannot be greater than 5");
        }
        if (by >= 16 || by2 >= 16 || by3 >= 16 || by4 >= 16 || by5 >= 16) {
            throw new RuntimeException("All register args must fit in 4 bits");
        }
        Instruction35s.checkItem(opcode, item, n);
        this.regCount = (byte)n;
        this.regA = by5;
        this.regD = by;
        this.regE = by2;
        this.regF = by3;
        this.regG = by4;
    }

    protected Instruction35s(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
        super(dexFile, opcode, arrby, n);
        if (this.getRegCount() > 5) {
            throw new RuntimeException("regCount cannot be greater than 5");
        }
        Instruction35s.checkItem(opcode, this.getReferencedItem(), this.getRegCount());
        this.regCount = NumberUtils.decodeHighUnsignedNibble(arrby[n + 1]);
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.regD = NumberUtils.decodeLowUnsignedNibble(arrby[n + 4]);
        this.regE = NumberUtils.decodeHighUnsignedNibble(arrby[n + 4]);
        this.regF = NumberUtils.decodeLowUnsignedNibble(arrby[n + 5]);
        this.regG = NumberUtils.decodeHighUnsignedNibble(arrby[n + 5]);
    }

    private static void checkItem(Opcode opcode, Item item, int n) {
        if (opcode == Opcode.FILLED_NEW_ARRAY) {
            String string2 = ((TypeIdItem)item).getTypeDescriptor();
            if (string2.charAt(0) != '[') {
                throw new RuntimeException("The type must be an array type");
            }
            if (string2.charAt(1) == 'J' || string2.charAt(1) == 'D') {
                throw new RuntimeException("The type cannot be an array of longs or doubles");
            }
        } else if (opcode.value >= Opcode.INVOKE_VIRTUAL.value && opcode.value <= Opcode.INVOKE_INTERFACE.value) {
            int n2 = ((MethodIdItem)item).getPrototype().getParameterRegisterCount();
            if (opcode != Opcode.INVOKE_STATIC) {
                ++n2;
            }
            if (n2 != n) {
                throw new RuntimeException("regCount does not match the number of arguments of the method");
            }
        }
    }

    @Override
    public Format getFormat() {
        return Format.Format35s;
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
        annotatedOutput.writeShort(this.getReferencedItem().getIndex());
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
            return new Instruction35s(dexFile, opcode, arrby, n);
        }
    }

}

