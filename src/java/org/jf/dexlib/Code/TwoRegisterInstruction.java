/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.jf.dexlib.Code;

import org.jf.dexlib.Code.SingleRegisterInstruction;

public interface TwoRegisterInstruction
extends SingleRegisterInstruction {
    @Override
    public int getRegisterA();

    public int getRegisterB();
}

