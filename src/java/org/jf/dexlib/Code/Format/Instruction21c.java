/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Item;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;

public class Instruction21c
extends InstructionWithReference
implements SingleRegisterInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private byte regA;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction21c.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction21c(Opcode opcode, short s, Item item) {
        super(opcode, item);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        if (opcode == Opcode.NEW_INSTANCE) {
            if (!$assertionsDisabled && !(item instanceof TypeIdItem)) {
                throw new AssertionError();
            }
            if (((TypeIdItem)item).getTypeDescriptor().charAt(0) != 'L') {
                throw new RuntimeException("Only class references can be used with the new-instance opcode");
            }
        }
        this.regA = (byte)s;
    }

    private Instruction21c(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
        super(dexFile, opcode, arrby, n);
        if (opcode == Opcode.NEW_INSTANCE && ((TypeIdItem)this.getReferencedItem()).getTypeDescriptor().charAt(0) != 'L') {
            throw new RuntimeException("Only class references can be used with the new-instance opcode");
        }
        this.regA = arrby[n + 1];
    }

    @Override
    public Format getFormat() {
        return Format.Format21c;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        if (this.opcode == Opcode.CONST_STRING && this.getReferencedItem().getIndex() > 65535) {
            throw new RuntimeException("String offset is too large for const-string. Use string-const/jumbo instead.");
        }
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeShort(this.getReferencedItem().getIndex());
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction21c(dexFile, opcode, arrby, n);
        }
    }

}

