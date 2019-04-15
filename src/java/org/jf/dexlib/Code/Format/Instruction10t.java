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

public class Instruction10t
extends Instruction
implements OffsetInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private int targetAddressOffset;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction10t.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction10t(Opcode opcode, int n) {
        super(opcode);
        this.targetAddressOffset = n;
        if (this.targetAddressOffset == 0) {
            throw new RuntimeException("The address offset cannot be 0. Use goto/32 instead.");
        }
    }

    private Instruction10t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        if (!$assertionsDisabled && arrby[n] != opcode.value) {
            throw new AssertionError();
        }
        this.targetAddressOffset = arrby[n + 1];
        if (!$assertionsDisabled && this.targetAddressOffset == 0) {
            throw new AssertionError();
        }
    }

    /* synthetic */ Instruction10t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format10t;
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
        if (this.targetAddressOffset < -128 || this.targetAddressOffset > 127) {
            throw new RuntimeException("The address offset is out of range. It must be in [-128,-1] or [1, 127]");
        }
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(this.targetAddressOffset);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction10t(opcode, arrby, n, null);
        }
    }

}

