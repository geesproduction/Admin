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
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.RegisterRangeInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction3rc
extends InstructionWithReference
implements RegisterRangeInstruction {
    public static final Instruction.InstructionFactory Factory = new Instruction.InstructionFactory(null){

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction3rc(dexFile, opcode, arrby, n);
        }
    };
    private byte regCount;
    private short startReg;

    public Instruction3rc(Opcode opcode, short s, int n, Item item) {
        super(opcode, item);
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
        this.regCount = (byte)s;
        this.startReg = (short)n;
        Instruction3rc.checkItem(opcode, item, s);
    }

    private Instruction3rc(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
        super(dexFile, opcode, arrby, n);
        this.regCount = (byte)NumberUtils.decodeUnsignedByte(arrby[n + 1]);
        this.startReg = (short)NumberUtils.decodeUnsignedShort(arrby, n + 4);
        Instruction3rc.checkItem(opcode, this.getReferencedItem(), this.getRegCount());
    }

    private static void checkItem(Opcode opcode, Item item, int n) {
        if (opcode == Opcode.FILLED_NEW_ARRAY_RANGE) {
            String string = ((TypeIdItem)item).getTypeDescriptor();
            if (string.charAt(0) != '[') {
                throw new RuntimeException("The type must be an array type");
            }
            if (string.charAt(1) == 'J' || string.charAt(1) == 'D') {
                throw new RuntimeException("The type cannot be an array of longs or doubles");
            }
        } else if (opcode.value >= Opcode.INVOKE_VIRTUAL_RANGE.value && opcode.value <= Opcode.INVOKE_INTERFACE_RANGE.value) {
            int n2 = ((MethodIdItem)item).getPrototype().getParameterRegisterCount();
            if (opcode != Opcode.INVOKE_STATIC_RANGE) {
                ++n2;
            }
            if (n2 != n) {
                throw new RuntimeException("regCount does not match the number of arguments of the method");
            }
        }
    }

    @Override
    public Format getFormat() {
        return Format.Format3rc;
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
        annotatedOutput.writeShort(this.getReferencedItem().getIndex());
        annotatedOutput.writeShort((int)this.startReg);
    }

}

