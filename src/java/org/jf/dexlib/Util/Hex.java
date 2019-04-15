/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.IllegalArgumentException
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 */
package org.jf.dexlib.Util;

public final class Hex {
    private Hex() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String dump(byte[] arrby, int n, int n2, int n3, int n4, int n5) {
        int n6 = n + n2;
        if ((n6 | (n | n2)) < 0 || n6 > arrby.length) {
            throw new IndexOutOfBoundsException("arr.length " + arrby.length + "; " + n + "..!" + n6);
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("outOffset < 0");
        }
        if (n2 == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(6 + n2 * 4);
        int n7 = 0;
        while (n2 > 0) {
            if (n7 == 0) {
                String string2;
                switch (n5) {
                    default: {
                        string2 = Hex.u4(n3);
                        break;
                    }
                    case 2: {
                        string2 = Hex.u1(n3);
                        break;
                    }
                    case 4: {
                        string2 = Hex.u2(n3);
                        break;
                    }
                    case 6: {
                        string2 = Hex.u3(n3);
                    }
                }
                stringBuffer.append(string2);
                stringBuffer.append(": ");
            } else if (!(n7 & true)) {
                stringBuffer.append(' ');
            }
            stringBuffer.append(Hex.u1(arrby[n]));
            ++n3;
            ++n;
            if (++n7 == n4) {
                stringBuffer.append('\n');
                n7 = 0;
            }
            --n2;
        }
        if (n7 != 0) {
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String s1(int n) {
        char[] arrc = new char[3];
        if (n < 0) {
            arrc[0] = 45;
            n = -n;
        } else {
            arrc[0] = 43;
        }
        int n2 = 0;
        while (n2 < 2) {
            arrc[2 - n2] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
            ++n2;
        }
        return new String(arrc);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String s2(int n) {
        char[] arrc = new char[5];
        if (n < 0) {
            arrc[0] = 45;
            n = -n;
        } else {
            arrc[0] = 43;
        }
        int n2 = 0;
        while (n2 < 4) {
            arrc[4 - n2] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
            ++n2;
        }
        return new String(arrc);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String s4(int n) {
        char[] arrc = new char[9];
        if (n < 0) {
            arrc[0] = 45;
            n = -n;
        } else {
            arrc[0] = 43;
        }
        int n2 = 0;
        while (n2 < 8) {
            arrc[8 - n2] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
            ++n2;
        }
        return new String(arrc);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static String s8(long l) {
        char[] arrc = new char[17];
        if (l < 0L) {
            arrc[0] = 45;
            l = -l;
        } else {
            arrc[0] = 43;
        }
        int n = 0;
        while (n < 16) {
            arrc[16 - n] = Character.forDigit((int)(15 & (int)l), (int)16);
            l >>= 4;
            ++n;
        }
        return new String(arrc);
    }

    public static String u1(int n) {
        char[] arrc = new char[2];
        for (int i = 0; i < 2; ++i) {
            arrc[1 - i] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
        }
        return new String(arrc);
    }

    public static String u2(int n) {
        char[] arrc = new char[4];
        for (int i = 0; i < 4; ++i) {
            arrc[3 - i] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
        }
        return new String(arrc);
    }

    public static String u2or4(int n) {
        if (n == (char)n) {
            return Hex.u2(n);
        }
        return Hex.u4(n);
    }

    public static String u3(int n) {
        char[] arrc = new char[6];
        for (int i = 0; i < 6; ++i) {
            arrc[5 - i] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
        }
        return new String(arrc);
    }

    public static String u4(int n) {
        char[] arrc = new char[8];
        for (int i = 0; i < 8; ++i) {
            arrc[7 - i] = Character.forDigit((int)(n & 15), (int)16);
            n >>= 4;
        }
        return new String(arrc);
    }

    public static String u8(long l) {
        char[] arrc = new char[16];
        for (int i = 0; i < 16; ++i) {
            arrc[15 - i] = Character.forDigit((int)(15 & (int)l), (int)16);
            l >>= 4;
        }
        return new String(arrc);
    }

    public static String uNibble(int n) {
        char[] arrc = new char[]{Character.forDigit((int)(n & 15), (int)16)};
        return new String(arrc);
    }
}

