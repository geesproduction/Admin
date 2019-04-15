/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.jf.dexlib.Code;

public interface MultiOffsetInstruction {
    public int[] getTargets();

    public void updateTarget(int var1, int var2);
}

