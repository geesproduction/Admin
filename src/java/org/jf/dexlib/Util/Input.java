/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Util;

public interface Input {
    public void alignTo(int var1);

    public void assertCursor(int var1);

    public int getCursor();

    public void read(byte[] var1);

    public void read(byte[] var1, int var2, int var3);

    public byte readByte();

    public byte[] readBytes(int var1);

    public int readInt();

    public long readLong();

    public int readShort();

    public int readSignedLeb128();

    public int readUnsignedLeb128();

    public int readUnsignedOrSignedLeb128();

    public String realNullTerminatedUtf8String();

    public void setCursor(int var1);

    public void skipBytes(int var1);
}

