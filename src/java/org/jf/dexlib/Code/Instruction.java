/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Code;

import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.Util.AnnotatedOutput;

public abstract class Instruction {
    public final Opcode opcode;

    protected Instruction(Opcode opcode) {
        this.opcode = opcode;
    }

    protected void annotateInstruction(AnnotatedOutput annotatedOutput, int n) {
        annotatedOutput.annotate(2 * this.getSize(n), "[0x" + Integer.toHexString((int)n) + "] " + this.opcode.name + " instruction");
    }

    public abstract Format getFormat();

    public int getSize(int n) {
        return this.opcode.format.size / 2;
    }

    public int write(AnnotatedOutput annotatedOutput, int n) {
        if (annotatedOutput.annotates()) {
            this.annotateInstruction(annotatedOutput, n);
        }
        this.writeInstruction(annotatedOutput, n);
        return n + this.getSize(n);
    }

    protected abstract void writeInstruction(AnnotatedOutput var1, int var2);

    public static interface InstructionFactory {
        public Instruction makeInstruction(DexFile var1, Opcode var2, byte[] var3, int var4);
    }

}

