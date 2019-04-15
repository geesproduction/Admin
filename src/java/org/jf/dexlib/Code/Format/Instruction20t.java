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
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction20t
extends Instruction
implements OffsetInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private int targetAddressOffset;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction20t.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction20t(Opcode opcode, int n) {
        super(opcode);
        this.targetAddressOffset = n;
        if (this.targetAddressOffset == 0) {
            throw new RuntimeException("The address offset cannot be 0. Use goto/32 instead.");
        }
    }

    private Instruction20t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        if (!$assertionsDisabled && arrby[n] != opcode.value) {
            throw new AssertionError();
        }
        this.targetAddressOffset = NumberUtils.decodeShort(arrby, n + 2);
        if (!$assertionsDisabled && this.targetAddressOffset == 0) {
            throw new AssertionError();
        }
    }

    /* synthetic */ Instruction20t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format20t;
    }

    @Override
    public int getTargetAddressOffset() {
        return this.targetAddressOffset;
    }

    @Override
    public void updateTargetAddressOffset(int n) {
        this.targetAddressOffset = n;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        if (this.targetAddressOffset == 0) {
            throw new RuntimeException("The address offset cannot be 0. Use goto/32 instead");
        }
        if (this.targetAddressOffset < -32768 || this.targetAddressOffset > 32767) {
            throw new RuntimeException("The address offset is out of range. It must be in [-32768,-1] or [1, 32768]");
        }
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(0);
        annotatedOutput.writeShort(this.targetAddressOffset);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction20t(opcode, arrby, n, null);
        }
    }

}

