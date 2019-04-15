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
import org.jf.dexlib.Code.TwoRegisterInstruction;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.NumberUtils;

public class Instruction22t
extends Instruction
implements OffsetInstruction,
TwoRegisterInstruction {
    static final /* synthetic */ boolean $assertionsDisabled;
    public static final Instruction.InstructionFactory Factory;
    private byte regA;
    private byte regB;
    private short targetAddressOffset;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Instruction22t.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        Factory = new Factory(null);
    }

    public Instruction22t(Opcode opcode, byte by, byte by2, short s) {
        super(opcode);
        if (by >= 16 || by2 >= 16) {
            throw new RuntimeException("The register number must be less than v16");
        }
        if (s == 0) {
            throw new RuntimeException("The address offset cannot be 0.");
        }
        this.regA = by;
        this.regB = by2;
        this.targetAddressOffset = s;
    }

    private Instruction22t(Opcode opcode, byte[] arrby, int n) {
        super(opcode);
        if (!$assertionsDisabled && arrby[n] != opcode.value) {
            throw new AssertionError();
        }
        this.regA = NumberUtils.decodeLowUnsignedNibble(arrby[n + 1]);
        this.regB = NumberUtils.decodeHighUnsignedNibble(arrby[n + 1]);
        this.targetAddressOffset = NumberUtils.decodeShort(arrby, n + 2);
        if (!$assertionsDisabled && this.targetAddressOffset == 0) {
            throw new AssertionError();
        }
    }

    /* synthetic */ Instruction22t(Opcode opcode, byte[] arrby, int n, 1 var4) {
        super(opcode, arrby, n);
    }

    @Override
    public Format getFormat() {
        return Format.Format22t;
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
        annotatedOutput.writeByte(this.regB << 4 | this.regA);
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
            return new Instruction22t(opcode, arrby, n, null);
        }
    }

}

