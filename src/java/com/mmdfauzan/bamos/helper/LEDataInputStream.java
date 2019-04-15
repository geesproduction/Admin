/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataInput
 *  java.io.DataInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package com.mmdfauzan.bamos.helper;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class LEDataInputStream
implements DataInput {
    protected final DataInputStream dis;
    int end;
    protected final InputStream is;
    int s;
    protected final byte[] work;

    public LEDataInputStream(InputStream inputStream) {
        this.is = inputStream;
        this.dis = new DataInputStream(inputStream);
        this.work = new byte[8];
    }

    public int read(byte[] arrby, int n, int n2) throws IOException {
        return this.dis.read(arrby, n, n2);
    }

    public final boolean readBoolean() throws IOException {
        return this.dis.readBoolean();
    }

    public final byte readByte() throws IOException {
        return this.dis.readByte();
    }

    public final char readChar() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return (char)((255 & this.work[1]) << 8 | 255 & this.work[0]);
    }

    public final double readDouble() throws IOException {
        return Double.longBitsToDouble((long)this.readLong());
    }

    public final float readFloat() throws IOException {
        return Float.intBitsToFloat((int)this.readInt());
    }

    public final void readFully(byte[] arrby) throws IOException {
        this.dis.readFully(arrby, 0, arrby.length);
    }

    public final void readFully(byte[] arrby, int n, int n2) throws IOException {
        this.dis.readFully(arrby, n, n2);
    }

    public final int readInt() throws IOException {
        this.dis.readFully(this.work, 0, 4);
        return this.work[3] << 24 | (255 & this.work[2]) << 16 | (255 & this.work[1]) << 8 | 255 & this.work[0];
    }

    public int[] readIntArray(int n) throws IOException {
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = this.readInt();
        }
        return arrn;
    }

    public final String readLine() throws IOException {
        return this.dis.readLine();
    }

    public final long readLong() throws IOException {
        this.dis.readFully(this.work, 0, 8);
        return this.work[7] << 56 | (255 & this.work[6]) << 48 | (255 & this.work[5]) << 40 | (255 & this.work[4]) << 32 | (255 & this.work[3]) << 24 | (255 & this.work[2]) << 16 | (255 & this.work[1]) << 8 | 255 & this.work[0];
    }

    public String readNulEndedString(int n, boolean bl) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(16);
        int n2 = n;
        do {
            int n3;
            short s;
            block6 : {
                block5 : {
                    n3 = n2 - 1;
                    if (n2 == 0) break block5;
                    s = this.readShort();
                    this.end = 2 + this.end;
                    if (s != 0) break block6;
                }
                if (bl) {
                    this.skipBytes(n3 * 2);
                    this.end += n3 * 2;
                }
                return stringBuilder.toString();
            }
            stringBuilder.append((char)s);
            n2 = n3;
        } while (true);
    }

    public final short readShort() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return (short)((255 & this.work[1]) << 8 | 255 & this.work[0]);
    }

    public final String readUTF() throws IOException {
        return this.dis.readUTF();
    }

    public final int readUnsignedByte() throws IOException {
        return this.dis.readUnsignedByte();
    }

    public final int readUnsignedShort() throws IOException {
        this.dis.readFully(this.work, 0, 2);
        return (255 & this.work[1]) << 8 | 255 & this.work[0];
    }

    public int size() {
        return this.end;
    }

    public final int skipBytes(int n) throws IOException {
        return this.dis.skipBytes(n);
    }

    public void skipCheckByte(byte by) throws IOException {
        byte by2 = this.readByte();
        if (by2 != by) {
            Object[] arrobject = new Object[]{by, by2};
            throw new IOException(String.format((String)"Expected: 0x%08x, got: 0x%08x", (Object[])arrobject));
        }
    }

    public void skipCheckInt(int n) throws IOException {
        int n2 = this.readInt();
        if (n2 != n) {
            Object[] arrobject = new Object[]{n, n2};
            throw new IOException(String.format((String)"Expected: 0x%08x, got: 0x%08x", (Object[])arrobject));
        }
    }

    public void skipCheckShort(short s) throws IOException {
        short s2 = this.readShort();
        if (s2 != s) {
            Object[] arrobject = new Object[]{s, s2};
            throw new IOException(String.format((String)"Expected: 0x%08x, got: 0x%08x", (Object[])arrobject));
        }
    }

    public void skipInt() throws IOException {
        this.skipBytes(4);
    }
}

