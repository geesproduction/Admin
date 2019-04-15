/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Long
 *  java.lang.Object
 */
package org.jf.dexlib.Util;

public class EncodedValueUtils {
    public static long decodeRightZeroExtendedValue(byte[] arrby) {
        long l = 0L;
        for (int i = 0; i < arrby.length; ++i) {
            l |= (long)(255 & arrby[i]) << i * 8;
        }
        return l << 8 * (8 - arrby.length);
    }

    public static long decodeSignedIntegralValue(byte[] arrby) {
        long l = 0L;
        for (int i = 0; i < arrby.length; ++i) {
            l |= (long)(255 & arrby[i]) << i * 8;
        }
        int n = 8 * (8 - arrby.length);
        return l << n >> n;
    }

    public static long decodeUnsignedIntegralValue(byte[] arrby) {
        long l = 0L;
        for (int i = 0; i < arrby.length; ++i) {
            l |= (long)(255 & arrby[i]) << i * 8;
        }
        return l;
    }

    public static byte[] encodeRightZeroExtendedValue(long l) {
        int n = EncodedValueUtils.getRequiredBytesForRightZeroExtendedValue(l);
        long l2 = l >> 64 - n * 8;
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)l2;
            l2 >>= 8;
        }
        return arrby;
    }

    public static byte[] encodeSignedIntegralValue(long l) {
        int n = EncodedValueUtils.getRequiredBytesForSignedIntegralValue(l);
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)l;
            l >>= 8;
        }
        return arrby;
    }

    public static byte[] encodeUnsignedIntegralValue(long l) {
        int n = EncodedValueUtils.getRequiredBytesForUnsignedIntegralValue(l);
        byte[] arrby = new byte[n];
        for (int i = 0; i < n; ++i) {
            arrby[i] = (byte)l;
            l >>= 8;
        }
        return arrby;
    }

    public static int getRequiredBytesForRightZeroExtendedValue(long l) {
        int n = 64 - Long.numberOfTrailingZeros((long)l);
        if (n == 0) {
            n = 1;
        }
        return n + 7 >> 3;
    }

    public static byte getRequiredBytesForSignedIntegralValue(long l) {
        return (byte)(7 + (65 - Long.numberOfLeadingZeros((long)(l ^ l >> 63))) >> 3);
    }

    public static byte getRequiredBytesForUnsignedIntegralValue(long l) {
        int n = 64 - Long.numberOfLeadingZeros((long)l);
        if (n == 0) {
            n = 1;
        }
        return (byte)(n + 7 >> 3);
    }
}

