/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 */
package org.jf.dexlib.Util;

import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Utf8Utils;

public class ByteArrayInput
implements Input {
    private int cursor;
    private byte[] data;

    public ByteArrayInput(byte[] arrby) {
        if (arrby == null) {
            throw new NullPointerException("data == null");
        }
        this.data = arrby;
        this.cursor = 0;
    }

    private static void throwBounds() {
        throw new IndexOutOfBoundsException("attempt to read past the end");
    }

    private static void throwInvalidLeb() {
        throw new RuntimeException("invalid LEB128 integer encountered");
    }

    @Override
    public void alignTo(int n) {
        this.cursor = AlignmentUtils.alignOffset(this.cursor, n);
    }

    @Override
    public void assertCursor(int n) {
        if (this.cursor != n) {
            throw new ExceptionWithContext("expected cursor " + n + "; actual value: " + this.cursor);
        }
    }

    public byte[] getArray() {
        return this.data;
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }

    @Override
    public void read(byte[] arrby) {
        int n = arrby.length;
        int n2 = n + this.cursor;
        if (n2 > this.data.length) {
            ByteArrayInput.throwBounds();
        }
        System.arraycopy((Object)this.data, (int)this.cursor, (Object)arrby, (int)0, (int)n);
        this.cursor = n2;
    }

    @Override
    public void read(byte[] arrby, int n, int n2) {
        int n3 = n2 + this.cursor;
        if (n3 > this.data.length) {
            ByteArrayInput.throwBounds();
        }
        System.arraycopy((Object)this.data, (int)this.cursor, (Object)arrby, (int)n, (int)n2);
        this.cursor = n3;
    }

    @Override
    public byte readByte() {
        byte[] arrby = this.data;
        int n = this.cursor;
        this.cursor = n + 1;
        return arrby[n];
    }

    @Override
    public byte[] readBytes(int n) {
        int n2 = n + this.cursor;
        if (n2 > this.data.length) {
            ByteArrayInput.throwBounds();
        }
        byte[] arrby = new byte[n];
        System.arraycopy((Object)this.data, (int)this.cursor, (Object)arrby, (int)0, (int)n);
        this.cursor = n2;
        return arrby;
    }

    @Override
    public int readInt() {
        int n = this.cursor;
        byte[] arrby = this.data;
        int n2 = n + 1;
        int n3 = 255 & arrby[n];
        byte[] arrby2 = this.data;
        int n4 = n2 + 1;
        int n5 = n3 + ((255 & arrby2[n2]) << 8);
        byte[] arrby3 = this.data;
        int n6 = n4 + 1;
        int n7 = n5 + ((255 & arrby3[n4]) << 16);
        byte[] arrby4 = this.data;
        int n8 = n6 + 1;
        int n9 = n7 + ((255 & arrby4[n6]) << 24);
        this.cursor = n8;
        return n9;
    }

    @Override
    public long readLong() {
        int n = this.cursor;
        byte[] arrby = this.data;
        int n2 = n + 1;
        long l = 255L & (long)arrby[n];
        byte[] arrby2 = this.data;
        int n3 = n2 + 1;
        long l2 = l | (255L & (long)arrby2[n2]) << 8;
        byte[] arrby3 = this.data;
        int n4 = n3 + 1;
        long l3 = l2 | (255L & (long)arrby3[n3]) << 16;
        byte[] arrby4 = this.data;
        int n5 = n4 + 1;
        long l4 = l3 | (255L & (long)arrby4[n4]) << 24;
        byte[] arrby5 = this.data;
        int n6 = n5 + 1;
        long l5 = l4 | (255L & (long)arrby5[n5]) << 32;
        byte[] arrby6 = this.data;
        int n7 = n6 + 1;
        long l6 = l5 | (255L & (long)arrby6[n6]) << 40;
        byte[] arrby7 = this.data;
        int n8 = n7 + 1;
        long l7 = l6 | (255L & (long)arrby7[n7]) << 48;
        byte[] arrby8 = this.data;
        int n9 = n8 + 1;
        long l8 = l7 | (255L & (long)arrby8[n8]) << 56;
        this.cursor = n9;
        return l8;
    }

    @Override
    public int readShort() {
        int n = this.cursor;
        byte[] arrby = this.data;
        int n2 = n + 1;
        int n3 = 255 & arrby[n];
        byte[] arrby2 = this.data;
        int n4 = n2 + 1;
        int n5 = n3 + ((255 & arrby2[n2]) << 8);
        this.cursor = n4;
        return n5;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int readSignedLeb128() {
        int n;
        int n2;
        int n3 = this.cursor;
        byte[] arrby = this.data;
        int n4 = n3 + 1;
        int n5 = 255 & arrby[n3];
        if (n5 <= 127) {
            n2 = n5 << 25 >> 25;
            n = n4;
        } else {
            byte[] arrby2 = this.data;
            n = n4 + 1;
            int n6 = 255 & arrby2[n4];
            int n7 = n5 & 127 | (n6 & 127) << 7;
            if (n6 <= 127) {
                n2 = n7 << 18 >> 18;
            } else {
                byte[] arrby3 = this.data;
                int n8 = n + 1;
                int n9 = 255 & arrby3[n];
                int n10 = n7 | (n9 & 127) << 14;
                if (n9 <= 127) {
                    n2 = n10 << 11 >> 11;
                    n = n8;
                } else {
                    byte[] arrby4 = this.data;
                    n = n8 + 1;
                    int n11 = 255 & arrby4[n8];
                    int n12 = n10 | (n11 & 127) << 21;
                    if (n11 <= 127) {
                        n2 = n12 << 4 >> 4;
                    } else {
                        byte[] arrby5 = this.data;
                        int n13 = n + 1;
                        int n14 = 255 & arrby5[n];
                        if (n14 > 15) {
                            ByteArrayInput.throwInvalidLeb();
                        }
                        n2 = n12 | n14 << 28;
                        n = n13;
                    }
                }
            }
        }
        this.cursor = n;
        return n2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public int readUnsignedLeb128() {
        block4 : {
            block3 : {
                var1_1 = this.cursor;
                var2_2 = this.data;
                var3_3 = var1_1 + 1;
                var4_4 = 255 & var2_2[var1_1];
                if (var4_4 <= 127) break block3;
                var6_5 = this.data;
                var5_6 = var3_3 + 1;
                var7_7 = 255 & var6_5[var3_3];
                var4_4 = var4_4 & 127 | (var7_7 & 127) << 7;
                if (var7_7 <= 127) break block4;
                var8_8 = this.data;
                var3_3 = var5_6 + 1;
                var9_9 = 255 & var8_8[var5_6];
                var4_4 |= (var9_9 & 127) << 14;
                if (var9_9 > 127) {
                    var10_10 = this.data;
                    var5_6 = var3_3 + 1;
                    var11_11 = 255 & var10_10[var3_3];
                    var4_4 |= (var11_11 & 127) << 21;
                    if (var11_11 > 127) {
                        var12_12 = this.data;
                        var13_13 = var5_6 + 1;
                        var14_14 = 255 & var12_12[var5_6];
                        if (var14_14 > 15) {
                            ByteArrayInput.throwInvalidLeb();
                        }
                        var4_4 |= var14_14 << 28;
                        var5_6 = var13_13;
                        ** GOTO lbl33
                    } else {
                        ** GOTO lbl30
                    }
                }
                break block3;
lbl30: // 2 sources:
                break block4;
            }
            var5_6 = var3_3;
        }
        this.cursor = var5_6;
        return var4_4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int readUnsignedOrSignedLeb128() {
        int n = this.cursor;
        byte[] arrby = this.data;
        int n2 = n + 1;
        int n3 = 255 & arrby[n];
        if (n3 <= 127) {
            this.cursor = n2;
            return n3;
        }
        byte[] arrby2 = this.data;
        int n4 = n2 + 1;
        int n5 = 255 & arrby2[n2];
        int n6 = n3 & 127 | (n5 & 127) << 7;
        if (n5 > 127) {
            byte[] arrby3 = this.data;
            int n7 = n4 + 1;
            int n8 = 255 & arrby3[n4];
            n6 |= (n8 & 127) << 14;
            if (n8 > 127) {
                byte[] arrby4 = this.data;
                n4 = n7 + 1;
                int n9 = 255 & arrby4[n7];
                n6 |= (n9 & 127) << 21;
                if (n9 > 127) {
                    byte[] arrby5 = this.data;
                    int n10 = n4 + 1;
                    int n11 = 255 & arrby5[n4];
                    if (n11 > 15) {
                        ByteArrayInput.throwInvalidLeb();
                    }
                    n6 |= n11 << 28;
                    n4 = n10;
                }
            } else {
                n4 = n7;
            }
        }
        this.cursor = n4;
        if (this.data[n4 - 1] == 0) {
            return ~n6;
        }
        return n6;
    }

    @Override
    public String realNullTerminatedUtf8String() {
        int n = this.cursor;
        while (this.data[this.cursor] != 0) {
            this.cursor = 1 + this.cursor;
        }
        int n2 = this.cursor - n;
        this.cursor = 1 + this.cursor;
        return Utf8Utils.utf8BytesToString(this.data, n, n2);
    }

    @Override
    public void setCursor(int n) {
        if (n < 0 || n >= this.data.length) {
            throw new IndexOutOfBoundsException("The provided cursor value is not within the bounds of this instance's data array");
        }
        this.cursor = n;
    }

    @Override
    public void skipBytes(int n) {
        this.cursor = n + this.cursor;
    }
}

