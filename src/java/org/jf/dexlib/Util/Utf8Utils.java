/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Character
 *  java.lang.IllegalArgumentException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 */
package org.jf.dexlib.Util;

import java.io.IOException;
import java.io.Writer;
import org.jf.dexlib.Util.Hex;

public final class Utf8Utils {
    private static char[] tempBuffer = null;

    private static int digit(char c) {
        int n = Character.digit((char)c, (int)16);
        if (n < 0 || n > 15) {
            throw new IllegalArgumentException("illegal not hex character: " + c);
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String escapeSequence(String string2) {
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c == '\\') {
                if (n2 >= n - 1) {
                    throw new IllegalArgumentException("escape Sequence error: " + c);
                }
                switch (string2.charAt(++n2)) {
                    default: {
                        throw new IllegalArgumentException("escape Sequence error: " + string2.substring(n2 - 1));
                    }
                    case 'b': {
                        stringBuilder.append('\b');
                        break;
                    }
                    case 't': {
                        stringBuilder.append('\t');
                        break;
                    }
                    case 'n': {
                        stringBuilder.append('\n');
                        break;
                    }
                    case 'r': {
                        stringBuilder.append('\r');
                        break;
                    }
                    case '\'': {
                        stringBuilder.append('\'');
                        break;
                    }
                    case '\"': {
                        stringBuilder.append('\"');
                        break;
                    }
                    case '\\': {
                        stringBuilder.append('\\');
                        break;
                    }
                    case 'u': {
                        int n3 = n2 + 1;
                        if (n3 > n - 4) {
                            throw new IllegalArgumentException("unicode error: " + string2.substring(n3 - 2));
                        }
                        int n4 = n3 + 1;
                        int n5 = Utf8Utils.digit(string2.charAt(n3)) << 12;
                        int n6 = n4 + 1;
                        int n7 = n5 | Utf8Utils.digit(string2.charAt(n4)) << 8;
                        int n8 = n6 + 1;
                        stringBuilder.append((char)(n7 | Utf8Utils.digit(string2.charAt(n6)) << 4 | Utf8Utils.digit(string2.charAt(n8))));
                        n2 = n8;
                        break;
                    }
                }
            } else {
                stringBuilder.append(c);
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static String escapeString(String var0) {
        var1_1 = var0.length();
        var2_2 = new StringBuilder(var1_1 * 3 / 2);
        var3_3 = 0;
        while (var3_3 < var1_1) {
            block8 : {
                block7 : {
                    var4_4 = var0.charAt(var3_3);
                    if (var4_4 < ' ' || var4_4 >= '') break block7;
                    if (var4_4 == '\'' || var4_4 == '\"' || var4_4 == '\\') {
                        var2_2.append('\\');
                    }
                    var2_2.append(var4_4);
                    break block8;
                }
                if (var4_4 > '') ** GOTO lbl-1000
                switch (var4_4) {
                    default: lbl-1000: // 2 sources:
                    {
                        var2_2.append(var4_4);
                        break;
                    }
                    case '\n': {
                        var2_2.append("\\n");
                        break;
                    }
                    case '\r': {
                        var2_2.append("\\r");
                        break;
                    }
                    case '\t': {
                        var2_2.append("\\t");
                    }
                }
            }
            ++var3_3;
        }
        return var2_2.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte[] stringToUtf8Bytes(String string2) {
        int n = string2.length();
        byte[] arrby = new byte[n * 3];
        int n2 = 0;
        int n3 = 0;
        do {
            if (n3 >= n) {
                byte[] arrby2 = new byte[n2];
                System.arraycopy((Object)arrby, (int)0, (Object)arrby2, (int)0, (int)n2);
                return arrby2;
            }
            char c = string2.charAt(n3);
            if (c != '\u0000' && c < '') {
                arrby[n2] = (byte)c;
                ++n2;
            } else if (c < '\u0800') {
                arrby[n2] = (byte)(192 | 31 & c >> 6);
                arrby[n2 + 1] = (byte)(128 | c & 63);
                n2 += 2;
            } else {
                arrby[n2] = (byte)(224 | 15 & c >> 12);
                arrby[n2 + 1] = (byte)(128 | 63 & c >> 6);
                arrby[n2 + 2] = (byte)(128 | c & 63);
                n2 += 3;
            }
            ++n3;
        } while (true);
    }

    private static String throwBadUtf8(int n, int n2) {
        throw new IllegalArgumentException("bad utf-8 byte " + Hex.u1(n) + " at offset " + Hex.u4(n2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String utf8BytesToString(byte[] arrby, int n, int n2) {
        if (tempBuffer == null || tempBuffer.length < n2) {
            tempBuffer = new char[n2];
        }
        char[] arrc = tempBuffer;
        int n3 = 0;
        int n4 = n;
        while (n2 > 0) {
            char c;
            int n5 = 255 & arrby[n4];
            switch (n5 >> 4) {
                default: {
                    return Utf8Utils.throwBadUtf8(n5, n4);
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: {
                    --n2;
                    if (n5 == 0) {
                        return Utf8Utils.throwBadUtf8(n5, n4);
                    }
                    c = (char)n5;
                    ++n4;
                    break;
                }
                case 12: 
                case 13: {
                    if ((n2 -= 2) < 0) {
                        return Utf8Utils.throwBadUtf8(n5, n4);
                    }
                    int n6 = 255 & arrby[n4 + 1];
                    if ((n6 & 192) != 128) {
                        return Utf8Utils.throwBadUtf8(n6, n4 + 1);
                    }
                    int n7 = (n5 & 31) << 6 | n6 & 63;
                    if (n7 != 0 && n7 < 128) {
                        return Utf8Utils.throwBadUtf8(n6, n4 + 1);
                    }
                    c = (char)n7;
                    n4 += 2;
                    break;
                }
                case 14: {
                    if ((n2 -= 3) < 0) {
                        return Utf8Utils.throwBadUtf8(n5, n4);
                    }
                    int n8 = 255 & arrby[n4 + 1];
                    if ((n8 & 192) != 128) {
                        return Utf8Utils.throwBadUtf8(n8, n4 + 1);
                    }
                    int n9 = 255 & arrby[n4 + 2];
                    if ((n8 & 192) != 128) {
                        return Utf8Utils.throwBadUtf8(n9, n4 + 2);
                    }
                    int n10 = (n5 & 15) << 12 | (n8 & 63) << 6 | n9 & 63;
                    if (n10 < 2048) {
                        return Utf8Utils.throwBadUtf8(n9, n4 + 2);
                    }
                    c = (char)n10;
                    n4 += 3;
                }
            }
            arrc[n3] = c;
            ++n3;
        }
        return new String(arrc, 0, n3);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void writeEscapedChar(Writer var0_1, char var1) throws IOException {
        if (var1 >= ' ' && var1 < '') {
            if (var1 == '\'' || var1 == '\"' || var1 == '\\') {
                var0_1.write(92);
            }
            var0_1.write((int)var1);
            return;
        }
        if (var1 > '') ** GOTO lbl-1000
        switch (var1) {
            default: lbl-1000: // 2 sources:
            {
                var0_1.write("\\u");
                var0_1.write((int)Character.forDigit((int)(var1 >> 12), (int)16));
                var0_1.write((int)Character.forDigit((int)(15 & var1 >> 8), (int)16));
                var0_1.write((int)Character.forDigit((int)(15 & var1 >> 4), (int)16));
                var0_1.write((int)Character.forDigit((int)(var1 & 15), (int)16));
                return;
            }
            case '\n': {
                var0_1.write("\\n");
                return;
            }
            case '\r': {
                var0_1.write("\\r");
                return;
            }
            case '\t': 
        }
        var0_1.write("\\t");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static void writeEscapedString(Writer var0_1, String var1) throws IOException {
        var2_2 = 0;
        while (var2_2 < var1.length()) {
            block8 : {
                block7 : {
                    var3_3 = var1.charAt(var2_2);
                    if (var3_3 < ' ' || var3_3 >= '') break block7;
                    if (var3_3 == '\'' || var3_3 == '\"' || var3_3 == '\\') {
                        var0_1.write(92);
                    }
                    var0_1.write((int)var3_3);
                    break block8;
                }
                if (var3_3 > '') ** GOTO lbl-1000
                switch (var3_3) {
                    default: lbl-1000: // 2 sources:
                    {
                        var0_1.write((int)var3_3);
                        break;
                    }
                    case '\n': {
                        var0_1.write("\\n");
                        break;
                    }
                    case '\r': {
                        var0_1.write("\\r");
                        break;
                    }
                    case '\t': {
                        var0_1.write("\\t");
                    }
                }
            }
            ++var2_2;
        }
    }
}

