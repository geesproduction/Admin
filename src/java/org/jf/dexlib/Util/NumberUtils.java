/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package org.jf.dexlib.Util;

public class NumberUtils {
    public static byte decodeHighSignedNibble(byte by) {
        return (byte)(by >> 4);
    }

    public static byte decodeHighUnsignedNibble(byte by) {
        return (byte)((by & 255) >>> 4);
    }

    public static int decodeInt(byte by, byte by2, byte by3, byte by4) {
        return by & 255 | (by2 & 255) << 8 | (by3 & 255) << 16 | by4 << 24;
    }

    public static int decodeInt(byte[] arrby, int n) {
        int n2 = n + 1;
        int n3 = 255 & arrby[n];
        int n4 = n2 + 1;
        int n5 = n3 | (255 & arrby[n2]) << 8;
        int n6 = n4 + 1;
        return n5 | (255 & arrby[n4]) << 16 | arrby[n6] << 24;
    }

    public static long decodeLong(byte by, byte by2, byte by3, byte by4, byte by5, byte by6, byte by7, byte by8) {
        return 255L & (long)by | (255L & (long)by2) << 8 | (255L & (long)by3) << 16 | (255L & (long)by4) << 24 | (255L & (long)by5) << 32 | (255L & (long)by6) << 40 | (255L & (long)by7) << 48 | (long)by8 << 56;
    }

    public static long decodeLong(byte[] arrby, int n) {
        int n2 = n + 1;
        long l = 255L & (long)arrby[n];
        int n3 = n2 + 1;
        long l2 = l | (255L & (long)arrby[n2]) << 8;
        int n4 = n3 + 1;
        long l3 = l2 | (255L & (long)arrby[n3]) << 16;
        int n5 = n4 + 1;
        long l4 = l3 | (255L & (long)arrby[n4]) << 24;
        int n6 = n5 + 1;
        long l5 = l4 | (255L & (long)arrby[n5]) << 32;
        int n7 = n6 + 1;
        long l6 = l5 | (255L & (long)arrby[n6]) << 40;
        int n8 = n7 + 1;
        return l6 | (255L & (long)arrby[n7]) << 48 | (long)arrby[n8] << 56;
    }

    public static byte decodeLowSignedNibble(byte by) {
        return (byte)((byte)(by << 4) >> 4);
    }

    public static byte decodeLowUnsignedNibble(byte by) {
        return (byte)(by & 15);
    }

    public static short decodeShort(byte by, byte by2) {
        return (short)(by & 255 | by2 << 8);
    }

    public static short decodeShort(byte[] arrby, int n) {
        int n2 = n + 1;
        return (short)(255 & arrby[n] | arrby[n2] << 8);
    }

    public static short decodeUnsignedByte(byte by) {
        return (short)(by & 255);
    }

    public static int decodeUnsignedShort(byte by, byte by2) {
        return by & 255 | (by2 & 255) << 8;
    }

    public static int decodeUnsignedShort(byte[] arrby, int n) {
        int n2 = n + 1;
        return 255 & arrby[n] | (255 & arrby[n2]) << 8;
    }
}

