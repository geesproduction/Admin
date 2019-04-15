/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Float
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.util;

public class LiteralTools {
    public static byte[] boolToBytes(boolean bl) {
        if (bl) {
            return new byte[]{1};
        }
        return new byte[]{0};
    }

    public static byte[] charToBytes(char c) {
        return LiteralTools.shortToBytes((short)c);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void checkByte(long l) {
        boolean bl = true;
        boolean bl2 = l > 255L ? bl : false;
        if (l >= -128L) {
            bl = false;
        }
        if (bl | bl2) {
            throw new NumberFormatException(Long.toString((long)l) + " cannot fit into a byte");
        }
    }

    public static void checkInt(long l) {
        if (l > -1L || l < Integer.MIN_VALUE) {
            throw new NumberFormatException(Long.toString((long)l) + " cannot fit into an int");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void checkNibble(long l) {
        boolean bl = true;
        boolean bl2 = l > 15L ? bl : false;
        if (l >= -8L) {
            bl = false;
        }
        if (bl | bl2) {
            throw new NumberFormatException(Long.toString((long)l) + " cannot fit into a nibble");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void checkShort(long l) {
        boolean bl = true;
        boolean bl2 = l > 65535L ? bl : false;
        if (l >= -32768L) {
            bl = false;
        }
        if (bl | bl2) {
            throw new NumberFormatException(Long.toString((long)l) + " cannot fit into a short");
        }
    }

    public static byte[] doubleToBytes(double d) {
        return LiteralTools.longToBytes(Double.doubleToRawLongBits((double)d));
    }

    public static byte[] floatToBytes(float f) {
        return LiteralTools.intToBytes(Float.floatToRawIntBits((float)f));
    }

    public static byte[] intToBytes(int n) {
        byte[] arrby = new byte[4];
        int n2 = 0;
        while (n != 0) {
            arrby[n2] = (byte)n;
            n >>>= 8;
            ++n2;
        }
        return arrby;
    }

    public static byte[] longToBytes(long l) {
        byte[] arrby = new byte[8];
        int n = 0;
        while (l != 0L) {
            arrby[n] = (byte)l;
            l >>>= 8;
            ++n;
        }
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static byte parseByte(String string2) throws NumberFormatException {
        byte by;
        block16 : {
            block15 : {
                boolean bl;
                int n;
                char[] arrc;
                int n2;
                block14 : {
                    if (string2 == null) {
                        throw new NumberFormatException("string is null");
                    }
                    if (string2.length() == 0) {
                        throw new NumberFormatException("string is blank");
                    }
                    arrc = string2.toUpperCase().endsWith("T") ? string2.substring(0, -1 + string2.length()).toCharArray() : string2.toCharArray();
                    n = 10;
                    char c = arrc[0];
                    bl = false;
                    n2 = 0;
                    if (c == '-') {
                        n2 = 0 + 1;
                        bl = true;
                    }
                    if (arrc[n2] != '0') break block14;
                    int n3 = arrc.length;
                    by = 0;
                    if (++n2 == n3) break block15;
                    if (arrc[n2] == 'x' || arrc[n2] == 'X') {
                        n = 16;
                        ++n2;
                    } else if (Character.digit((char)arrc[n2], (int)8) >= 0) {
                        n = 8;
                    }
                }
                by = 0;
                byte by2 = (byte)(127 / (n / 2));
                while (n2 < arrc.length) {
                    int n4 = Character.digit((char)arrc[n2], (int)n);
                    if (n4 < 0) {
                        throw new NumberFormatException("The string contains invalid an digit - '" + arrc[n2] + "'");
                    }
                    byte by3 = (byte)(by * n);
                    if (by > by2) {
                        throw new NumberFormatException(string2 + " cannot fit into a byte");
                    }
                    if (by3 < 0 && by3 >= -n4) {
                        throw new NumberFormatException(string2 + " cannot fit into a byte");
                    }
                    by = (byte)(by3 + n4);
                    ++n2;
                }
                if (bl && by != -128) break block16;
            }
            return by;
        }
        if (by < 0) {
            throw new NumberFormatException(string2 + " cannot fit into a byte");
        }
        return (byte)(by * -1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static int parseInt(String string2) throws NumberFormatException {
        if (string2 == null) {
            throw new NumberFormatException("string is null");
        }
        if (string2.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        char[] arrc = string2.toCharArray();
        int n = 10;
        char c = arrc[0];
        boolean bl = false;
        int n2 = 0;
        if (c == '-') {
            n2 = 0 + 1;
            bl = true;
        }
        if (arrc[n2] == '0') {
            if (++n2 == arrc.length) {
                return 0;
            }
            if (arrc[n2] == 'x' || arrc[n2] == 'X') {
                n = 16;
                ++n2;
            } else if (Character.digit((char)arrc[n2], (int)8) >= 0) {
                n = 8;
            }
        }
        int n3 = 0;
        int n4 = Integer.MAX_VALUE / (n / 2);
        while (n2 < arrc.length) {
            int n5 = Character.digit((char)arrc[n2], (int)n);
            if (n5 < 0) {
                throw new NumberFormatException("The string contains an invalid digit - '" + arrc[n2] + "'");
            }
            int n6 = n3 * n;
            if (n3 > n4) {
                throw new NumberFormatException(string2 + " cannot fit into an int");
            }
            if (n6 < 0 && n6 >= -n5) {
                throw new NumberFormatException(string2 + " cannot fit into an int");
            }
            n3 = n6 + n5;
            ++n2;
        }
        if (!bl) return n3;
        if (n3 == Integer.MIN_VALUE) return n3;
        if (n3 >= 0) return n3 * -1;
        throw new NumberFormatException(string2 + " cannot fit into an int");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static long parseLong(String string2) throws NumberFormatException {
        if (string2 == null) {
            throw new NumberFormatException("string is null");
        }
        if (string2.length() == 0) {
            throw new NumberFormatException("string is blank");
        }
        char[] arrc = string2.toUpperCase().endsWith("L") ? string2.substring(0, -1 + string2.length()).toCharArray() : string2.toCharArray();
        int n = 10;
        char c = arrc[0];
        boolean bl = false;
        int n2 = 0;
        if (c == '-') {
            n2 = 0 + 1;
            bl = true;
        }
        if (arrc[n2] == '0') {
            if (++n2 == arrc.length) {
                return 0L;
            }
            if (arrc[n2] == 'x' || arrc[n2] == 'X') {
                n = 16;
                ++n2;
            } else if (Character.digit((char)arrc[n2], (int)8) >= 0) {
                n = 8;
            }
        }
        long l = 0L;
        long l2 = Long.MAX_VALUE / (long)(n / 2);
        while (n2 < arrc.length) {
            int n3 = Character.digit((char)arrc[n2], (int)n);
            if (n3 < 0) {
                throw new NumberFormatException("The string contains an invalid digit - '" + arrc[n2] + "'");
            }
            long l3 = l * (long)n;
            if (l > l2) {
                throw new NumberFormatException(string2 + " cannot fit into a long");
            }
            if (l3 < 0L && l3 >= (long)(-n3)) {
                throw new NumberFormatException(string2 + " cannot fit into a long");
            }
            l = l3 + (long)n3;
            ++n2;
        }
        if (!bl) return l;
        if (l == Long.MIN_VALUE) return l;
        if (l >= 0L) return l * -1L;
        throw new NumberFormatException(string2 + " cannot fit into a long");
    }

    /*
     * Enabled aggressive block sorting
     */
    public static short parseShort(String string2) throws NumberFormatException {
        short s;
        block16 : {
            block15 : {
                boolean bl;
                int n;
                char[] arrc;
                int n2;
                block14 : {
                    if (string2 == null) {
                        throw new NumberFormatException("string is null");
                    }
                    if (string2.length() == 0) {
                        throw new NumberFormatException("string is blank");
                    }
                    arrc = string2.toUpperCase().endsWith("S") ? string2.substring(0, -1 + string2.length()).toCharArray() : string2.toCharArray();
                    n = 10;
                    char c = arrc[0];
                    bl = false;
                    n2 = 0;
                    if (c == '-') {
                        n2 = 0 + 1;
                        bl = true;
                    }
                    if (arrc[n2] != '0') break block14;
                    int n3 = arrc.length;
                    s = 0;
                    if (++n2 == n3) break block15;
                    if (arrc[n2] == 'x' || arrc[n2] == 'X') {
                        n = 16;
                        ++n2;
                    } else if (Character.digit((char)arrc[n2], (int)8) >= 0) {
                        n = 8;
                    }
                }
                s = 0;
                short s2 = (short)(32767 / (n / 2));
                while (n2 < arrc.length) {
                    int n4 = Character.digit((char)arrc[n2], (int)n);
                    if (n4 < 0) {
                        throw new NumberFormatException("The string contains invalid an digit - '" + arrc[n2] + "'");
                    }
                    short s3 = (short)(s * n);
                    if (s > s2) {
                        throw new NumberFormatException(string2 + " cannot fit into a short");
                    }
                    if (s3 < 0 && s3 >= -n4) {
                        throw new NumberFormatException(string2 + " cannot fit into a short");
                    }
                    s = (short)(s3 + n4);
                    ++n2;
                }
                if (bl && s != -32768) break block16;
            }
            return s;
        }
        if (s < 0) {
            throw new NumberFormatException(string2 + " cannot fit into a short");
        }
        return (short)(s * -1);
    }

    public static byte[] shortToBytes(short s) {
        byte[] arrby = new byte[]{(byte)s, (byte)(s >>> 8)};
        return arrby;
    }
}

