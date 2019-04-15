/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction30t
extends Instruction
implements OffsetInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private int targetAddressOffset;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction30t.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction30t(Opcode opcode, int n) {
        super(opcode);
        this.targetAddressOffset = n;
    }

    private Instruction30t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        if (!$assertionsDisabled && arrby[n] != opcode.value) {
            throw new AssertionError();
        }
        this.targetAddressOffset = NumberUtils.decodeInt(arrby, n + 2);
    }

    /* synthetic */ Instruction30t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format30t;
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
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte(0);
        annotatedOutput.writeInt(this.targetAddressOffset);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction30t(opcode, arrby, n, null);
        }
    }

}

