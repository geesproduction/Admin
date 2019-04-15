/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package org.jf.dexlib.Util;

public final class ByteArray {
    private final byte[] bytes;
    private final int size;
    private final int start;

    public ByteArray(byte[] arrby) {
        super(arrby, 0, arrby.length);
    }

    public ByteArray(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            throw new NullPointerException("bytes == null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("start < 0");
        }
        if (n2 < n) {
            throw new IllegalArgumentException("end < start");
        }
        if (n2 > arrby.length) {
            throw new IllegalArgumentException("end > bytes.length");
        }
        this.bytes = arrby;
        this.start = n;
        this.size = n2 - n;
    }

    private void checkOffsets(int n, int n2) {
        if (n < 0 || n2 < n || n2 > this.size) {
            throw new IllegalArgumentException("bad range: " + n + ".." + n2 + "; actual size " + this.size);
        }
    }

    private int getByte0(int n) {
        return this.bytes[n + this.start];
    }

    private int getUnsignedByte0(int n) {
        return 255 & this.bytes[n + this.start];
    }

    public int getByte(int n) {
        ByteArray.super.checkOffsets(n, n + 1);
        return ByteArray.super.getByte0(n);
    }

    public void getBytes(byte[] arrby, int n) {
        if (arrby.length - n < this.size) {
            throw new IndexOutOfBoundsException("(out.length - offset) < size()");
        }
        System.arraycopy((Object)this.bytes, (int)this.start, (Object)arrby, (int)n, (int)this.size);
    }

    public int getInt(int n) {
        ByteArray.super.checkOffsets(n, n + 4);
        return ByteArray.super.getByte0(n) << 24 | ByteArray.super.getUnsignedByte0(n + 1) << 16 | ByteArray.super.getUnsignedByte0(n + 2) << 8 | ByteArray.super.getUnsignedByte0(n + 3);
    }

    public long getLong(int n) {
        ByteArray.super.checkOffsets(n, n + 8);
        int n2 = ByteArray.super.getByte0(n) << 24 | ByteArray.super.getUnsignedByte0(n + 1) << 16 | ByteArray.super.getUnsignedByte0(n + 2) << 8 | ByteArray.super.getUnsignedByte0(n + 3);
        return 0xFFFFFFFFL & (long)(ByteArray.super.getByte0(n + 4) << 24 | ByteArray.super.getUnsignedByte0(n + 5) << 16 | ByteArray.super.getUnsignedByte0(n + 6) << 8 | ByteArray.super.getUnsignedByte0(n + 7)) | (long)n2 << 32;
    }

    public int getShort(int n) {
        ByteArray.super.checkOffsets(n, n + 2);
        return ByteArray.super.getByte0(n) << 8 | ByteArray.super.getUnsignedByte0(n + 1);
    }

    public int getUnsignedByte(int n) {
        ByteArray.super.checkOffsets(n, n + 1);
        return ByteArray.super.getUnsignedByte0(n);
    }

    public int getUnsignedShort(int n) {
        ByteArray.super.checkOffsets(n, n + 2);
        return ByteArray.super.getUnsignedByte0(n) << 8 | ByteArray.super.getUnsignedByte0(n + 1);
    }

    public int size() {
        return this.size;
    }

    public ByteArray slice(int n, int n2) {
        ByteArray.super.checkOffsets(n, n2);
        return new ByteArray(this.bytes, n + this.start, n2 + this.start);
    }

    public int underlyingOffset(int n, byte[] arrby) {
        if (arrby != this.bytes) {
            throw new IllegalArgumentException("wrong bytes");
        }
        return n + this.start;
    }
}

