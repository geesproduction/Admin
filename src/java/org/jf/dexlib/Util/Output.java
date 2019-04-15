/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.jf.dexlib.Util;

import org.jf.dexlib.Util.ByteArray;

public interface Output {
    public void alignTo(int var1);

    public void assertCursor(int var1);

    public int getCursor();

    public void write(ByteArray var1);

    public void write(byte[] var1);

    public void write(byte[] var1, int var2, int var3);

    public void writeByte(int var1);

    public void writeInt(int var1);

    public void writeLong(long var1);

    public void writeShort(int var1);

    public int writeSignedLeb128(int var1);

    public int writeUnsignedLeb128(int var1);

    public void writeZeroes(int var1);
}

