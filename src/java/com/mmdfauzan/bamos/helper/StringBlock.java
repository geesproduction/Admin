/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.nio.ByteBuffer
 *  java.nio.CharBuffer
 *  java.nio.charset.CharacterCodingException
 *  java.nio.charset.Charset
 *  java.nio.charset.CharsetDecoder
 *  java.nio.charset.CharsetEncoder
 *  java.util.ArrayList
 *  java.util.List
 *  org.jf.dexlib.Util.Utf8Utils
 */
package com.mmdfauzan.bamos.helper;

import com.mmdfauzan.bamos.helper.LEDataInputStream;
import com.mmdfauzan.bamos.helper.LEDataOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.List;
import org.jf.dexlib.Util.Utf8Utils;

public class StringBlock {
    public static final int CHUNK_STRINGBLOCK = 1835009;
    public static final int IS_UTF8 = 256;
    private static final CharsetDecoder UTF16LE_DECODER = Charset.forName((String)"UTF-16LE").newDecoder();
    private static final CharsetEncoder UTF16LE_ENCODER = Charset.forName((String)"UTF-16LE").newEncoder();
    private static final CharsetDecoder UTF8_DECODER = Charset.forName((String)"UTF-8").newDecoder();
    private static final CharsetEncoder UTF8_ENCODER = Charset.forName((String)"UTF-8").newEncoder();
    private int chunkSize;
    private int flags;
    private boolean m_isUTF8;
    private int[] m_stringOffsets;
    byte[] m_strings;
    private int[] m_styleOffsets;
    private int[] m_styles;
    private int stringsOffset;
    private int styleOffsetCount;
    private int stylesOffset;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String decodeString(int n, int n2) {
        try {
            CharsetDecoder charsetDecoder;
            if (this.m_isUTF8) {
                charsetDecoder = UTF8_DECODER;
                do {
                    return charsetDecoder.decode(ByteBuffer.wrap((byte[])this.m_strings, (int)n, (int)n2)).toString();
                    break;
                } while (true);
            }
            charsetDecoder = UTF16LE_DECODER;
            return charsetDecoder.decode(ByteBuffer.wrap((byte[])this.m_strings, (int)n, (int)n2)).toString();
        }
        catch (CharacterCodingException characterCodingException) {
            return null;
        }
    }

    private static final int getShort(byte[] arrby, int n) {
        return (255 & arrby[n + 1]) << 8 | 255 & arrby[n];
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] getStyle(int n) {
        int[] arrn = this.m_styleOffsets;
        int[] arrn2 = null;
        if (arrn != null) {
            int[] arrn3 = this.m_styles;
            arrn2 = null;
            if (arrn3 != null) {
                int n2 = this.m_styleOffsets.length;
                arrn2 = null;
                if (n < n2) {
                    int n3 = this.m_styleOffsets[n] / 4;
                    int n4 = 0;
                    for (int i = n3; i < this.m_styles.length && this.m_styles[i] != -1; ++n4, ++i) {
                    }
                    arrn2 = null;
                    if (n4 != 0) {
                        int n5 = n4 % 3;
                        arrn2 = null;
                        if (n5 == 0) {
                            arrn2 = new int[n4];
                            int n6 = n3;
                            int n7 = 0;
                            while (n6 < this.m_styles.length && this.m_styles[n6] != -1) {
                                int n8 = n7 + 1;
                                int[] arrn4 = this.m_styles;
                                int n9 = n6 + 1;
                                arrn2[n7] = arrn4[n6];
                                n7 = n8;
                                n6 = n9;
                            }
                        }
                    }
                }
            }
        }
        return arrn2;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static final int[] getVarint(byte[] arrby, int n) {
        byte by = arrby[n];
        boolean bl = (by & 128) != 0;
        int n2 = by & 127;
        if (!bl) {
            return new int[]{n2, 1};
        }
        int[] arrn = new int[]{n2 << 8 | 255 & arrby[n + 1], 2};
        return arrn;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void outputStyleTag(String var1, StringBuilder var2_3, boolean var3_2) {
        block7 : {
            var2_3.append('<');
            if (var3_2) {
                var2_3.append('/');
            }
            if ((var5_4 = var1.indexOf(59)) != -1) break block7;
            var2_3.append(var1);
            ** GOTO lbl13
        }
        var2_3.append(var1.substring(0, var5_4));
        if (var3_2) ** GOTO lbl13
        var7_5 = true;
        do {
            block8 : {
                if (var7_5) break block8;
lbl13: // 3 sources:
                var2_3.append('>');
                return;
            }
            var8_6 = var1.indexOf(61, var5_4 + 1);
            var2_3.append(' ').append(var1.substring(var5_4 + 1, var8_6)).append("=\"");
            var5_4 = var1.indexOf(59, var8_6 + 1);
            if (var5_4 != -1) {
                var10_7 = var1.substring(var8_6 + 1, var5_4);
            } else {
                var10_7 = var1.substring(var8_6 + 1);
                var7_5 = false;
            }
            var2_3.append(var10_7).append('\"');
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static StringBlock read(LEDataInputStream lEDataInputStream) throws IOException {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        lEDataInputStream.skipCheckInt(1835009);
        StringBlock stringBlock = new StringBlock();
        stringBlock.chunkSize = n5 = lEDataInputStream.readInt();
        System.out.println("chunkSize " + n5);
        int n8 = lEDataInputStream.readInt();
        System.out.println("stringCount " + n8);
        stringBlock.styleOffsetCount = n3 = lEDataInputStream.readInt();
        System.out.println("styleOffsetCount " + n3);
        stringBlock.flags = n6 = lEDataInputStream.readInt();
        stringBlock.stringsOffset = n4 = lEDataInputStream.readInt();
        System.out.println("stringsOffset " + n4);
        stringBlock.stylesOffset = n = lEDataInputStream.readInt();
        System.out.println("stylesOffset " + n);
        boolean bl = (n6 & 256) != 0;
        stringBlock.m_isUTF8 = bl;
        stringBlock.m_stringOffsets = lEDataInputStream.readIntArray(n8);
        if (n3 != 0) {
            stringBlock.m_styleOffsets = lEDataInputStream.readIntArray(n3);
        }
        if ((n7 = (n2 = n == 0 ? n5 : n) - n4) % 4 != 0) {
            throw new IOException("String data size is not multiple of 4 (" + n7 + ").");
        }
        stringBlock.m_strings = new byte[n7];
        lEDataInputStream.readFully(stringBlock.m_strings);
        if (n != 0) {
            int n9 = n5 - n;
            if (n9 % 4 != 0) {
                throw new IOException("Style data size is not multiple of 4 (" + n9 + ").");
            }
            stringBlock.m_styles = lEDataInputStream.readIntArray(n9 / 4);
            System.out.println("m_styles_size " + n9);
        }
        System.out.println();
        return stringBlock;
    }

    public int getChunkSize() {
        return this.chunkSize;
    }

    /*
     * Enabled aggressive block sorting
     */
    public String getHTML(int n) {
        int[] arrn;
        String string2 = this.getString(n);
        if (string2 == null || (arrn = StringBlock.super.getStyle(n)) == null) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder(32 + string2.length());
        int[] arrn2 = new int[arrn.length / 3];
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            int n5 = -1;
            for (int i = 0; i != arrn.length; i += 3) {
                if (arrn[i + 1] == -1 || n5 != -1 && arrn[n5 + 1] <= arrn[i + 1]) continue;
                n5 = i;
            }
            int n6 = n5 != -1 ? arrn[n5 + 1] : string2.length();
            int n7 = n3 - 1;
            do {
                int n8;
                int n9;
                if (n7 < 0 || (n8 = arrn[(n9 = arrn2[n7]) + 2]) >= n6) {
                    n4 = n7 + 1;
                    if (n2 < n6) {
                        stringBuilder.append(string2.substring(n2, n6));
                        n2 = n6;
                    }
                    if (n5 != -1) break;
                    return stringBuilder.toString();
                }
                if (n2 <= n8) {
                    stringBuilder.append(string2.substring(n2, n8 + 1));
                    n2 = n8 + 1;
                }
                StringBlock.super.outputStyleTag(this.getString(arrn[n9]), stringBuilder, true);
                --n7;
            } while (true);
            StringBlock.super.outputStyleTag(this.getString(arrn[n5]), stringBuilder, false);
            arrn[n5 + 1] = -1;
            int n10 = n4 + 1;
            arrn2[n4] = n5;
            n3 = n10;
        } while (true);
    }

    public int getSize() {
        if (this.m_stringOffsets != null) {
            return this.m_stringOffsets.length;
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String getString(int n) {
        int n2;
        int n3;
        if (n < 0 || this.m_stringOffsets == null || n >= this.m_stringOffsets.length) {
            return null;
        }
        int n4 = this.m_stringOffsets[n];
        if (!this.m_isUTF8) {
            n2 = 2 * StringBlock.getShort(this.m_strings, n4);
            n3 = n4 + 2;
            do {
                return StringBlock.super.decodeString(n3, n2);
                break;
            } while (true);
        }
        int n5 = n4 + StringBlock.getVarint(this.m_strings, n4)[1];
        int[] arrn = StringBlock.getVarint(this.m_strings, n5);
        n3 = n5 + arrn[1];
        n2 = arrn[0];
        return StringBlock.super.decodeString(n3, n2);
    }

    public void getStrings(List<String> list) {
        int n = this.getSize();
        for (int i = 0; i < n; ++i) {
            list.add((Object)Utf8Utils.escapeString((String)this.getString(i)));
        }
    }

    public void write(LEDataOutputStream lEDataOutputStream) throws IOException {
        ArrayList arrayList = new ArrayList(this.getSize());
        this.getStrings((List<String>)arrayList);
        this.write((List<String>)arrayList, lEDataOutputStream);
    }

    public void write(List<String> list, LEDataOutputStream lEDataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        LEDataOutputStream lEDataOutputStream2 = new LEDataOutputStream((OutputStream)byteArrayOutputStream);
        int n = list.size();
        int[] arrn = new int[n];
        int n2 = 0;
        ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
        LEDataOutputStream lEDataOutputStream3 = new LEDataOutputStream((OutputStream)byteArrayOutputStream2);
        for (int i = 0; i < n; ++i) {
            arrn[i] = n2;
            char[] arrc = Utf8Utils.escapeSequence((String)((String)list.get(i))).toCharArray();
            lEDataOutputStream3.writeShort((short)arrc.length);
            lEDataOutputStream3.writeCharArray(arrc);
            lEDataOutputStream3.writeShort((short)0);
            n2 += 4 + 2 * arrc.length;
        }
        int n3 = byteArrayOutputStream2.size();
        int n4 = n3 % 4;
        if (n4 != 0) {
            for (int i = 0; i < 4 - n4; ++i) {
                byteArrayOutputStream2.write(0);
            }
            n3 + (4 - n4);
        }
        byte[] arrby = byteArrayOutputStream2.toByteArray();
        System.out.println("string chunk size: " + this.chunkSize);
        lEDataOutputStream2.writeInt(n);
        lEDataOutputStream2.writeInt(this.styleOffsetCount);
        lEDataOutputStream2.writeInt(this.flags);
        lEDataOutputStream2.writeInt(this.stringsOffset);
        lEDataOutputStream2.writeInt(this.stylesOffset);
        lEDataOutputStream2.writeIntArray(arrn);
        if (this.styleOffsetCount != 0) {
            System.out.println("write stylesOffset");
            lEDataOutputStream2.writeIntArray(this.m_styleOffsets);
        }
        lEDataOutputStream2.writeFully(arrby);
        if (this.m_styles != null) {
            System.out.println("write m_styles");
            lEDataOutputStream2.writeIntArray(this.m_styles);
        }
        lEDataOutputStream.writeInt(1835009);
        byte[] arrby2 = byteArrayOutputStream.toByteArray();
        lEDataOutputStream.writeInt(8 + arrby2.length);
        lEDataOutputStream.writeFully(arrby2);
    }
}

