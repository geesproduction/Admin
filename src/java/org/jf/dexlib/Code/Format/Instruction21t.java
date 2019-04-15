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
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction21t
extends Instruction
implements OffsetInstruction,
SingleRegisterInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private byte regA;
    private short targetAddressOffset;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction21t.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction21t(Opcode opcode, short s, short s2) {
        super(opcode);
        if (s >= 256) {
            throw new RuntimeException("The register number must be less than v256");
        }
        if (s2 == 0) {
            throw new RuntimeException("The address offset cannot be 0.");
        }
        this.regA = (byte)s;
        this.targetAddressOffset = s2;
    }

    private Instruction21t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        if (!$assertionsDisabled && arrby[n] != opcode.value) {
            throw new AssertionError();
        }
        this.regA = arrby[n + 1];
        this.targetAddressOffset = NumberUtils.decodeShort(arrby, n + 2);
        if (!$assertionsDisabled && this.targetAddressOffset == 0) {
            throw new AssertionError();
        }
    }

    /* synthetic */ Instruction21t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format21t;
    }

    @Override
    public int getRegisterA() {
        return 255 & this.regA;
    }

    @Override
    public int getTargetAddressOffset() {
        return this.targetAddressOffset;
    }

    @Override
    public void updateTargetAddressOffset(int n) {
        if (n < -32768 || n > 32767) {
            throw new RuntimeException("The address offset " + n + " is out of range. It must be in [-32768, 32767]");
        }
        if (n == 0) {
            throw new RuntimeException("The address offset cannot be 0");
        }
        this.targetAddressOffset = (short)n;
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.writeByte((int)this.opcode.value);
        annotatedOutput.writeByte((int)this.regA);
        annotatedOutput.writeShort((int)this.targetAddressOffset);
    }

    private static class Factory
    implements Instruction.InstructionFactory {
        private Factory() {
        }

        /* synthetic */ Factory(1 var1) {
        }

        @Override
        public Instruction makeInstruction(DexFile dexFile, Opcode opcode, byte[] arrby, int n) {
            return new Instruction21t(opcode, arrby, n, null);
        }
    }

}

