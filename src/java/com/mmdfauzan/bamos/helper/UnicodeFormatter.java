/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

public class UnicodeFormatter {
    public static String byteToHex(byte by) {
        char[] arrc = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] arrc2 = new char[]{arrc[15 & by >> 4], arrc[by & 15]};
        return new String(arrc2);
    }

    public static String charToHex(char c) {
        byte by = (byte)(c >>> 8);
        byte by2 = (byte)(c & 255);
        return UnicodeFormatter.byteToHex(by) + UnicodeFormatter.byteToHex(by2);
    }
}

