/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.jf.dexlib.Util;

public final class Leb128Utils {
    private Leb128Utils() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int signedLeb128Size(int n) {
        int n2;
        int n3 = n >> 7;
        int n4 = 0;
        boolean bl = true;
        if ((Integer.MIN_VALUE & n) == 0) {
            n2 = 0;
        } else {
            n2 = -1;
            n4 = 0;
        }
        while (bl) {
            bl = n3 != n2 || (n3 & 1) != (1 & n >> 6);
            n = n3;
            n3 >>= 7;
            ++n4;
        }
        return n4;
    }

    public static int unsignedLeb128Size(int n) {
        int n2 = n >>> 7;
        int n3 = 0;
        while (n2 != 0) {
            n2 >>>= 7;
            ++n3;
        }
        return n3 + 1;
    }

    public static void writeUnsignedLeb128(int n, byte[] arrby, int n2) {
        int n3 = n >>> 7;
        int n4 = 0;
        while (n3 != 0) {
            arrby[n2] = (byte)(128 | n & 127);
            ++n2;
            n = n3;
            n3 >>>= 7;
            ++n4;
        }
        arrby[n2] = (byte)(n & 127);
    }
}

