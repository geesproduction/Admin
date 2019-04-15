/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Util.AnnotatedOutput;

public class UnresolvedOdexInstruction
extends Instruction {
    public final int ObjectRegisterNum;
    public final Instruction OriginalInstruction;

    public UnresolvedOdexInstruction(Instruction instruction, int n) {
        super(instruction.opcode);
        this.OriginalInstruction = instruction;
        this.ObjectRegisterNum = n;
    }

    @Override
    public Format getFormat() {
        return Format.UnresolvedOdexInstruction;
    }

    @Override
    public int getSize(int n) {
        return this.OriginalInstruction.getSize(n);
    }

    @Override
    protected void writeInstruction(AnnotatedOutput annotatedOutput, int n) {
        throw new RuntimeException("Cannot rewrite an instruction that couldn't be deodexed");
    }
}

