/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 */
package com.mmdfauzan.bamos.helper;

import com.mmdfauzan.bamos.helper.LEDataInputStream;
import com.mmdfauzan.bamos.helper.LEDataOutputStream;
import com.mmdfauzan.bamos.helper.StringBlock;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class AXmlDecoder {
    private static final int AXML_CHUNK_TYPE = 524291;
    ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
    private final LEDataInputStream mIn;
    public StringBlock mTableStrings;

    private AXmlDecoder(LEDataInputStream lEDataInputStream) {
        this.mIn = lEDataInputStream;
    }

    private void checkChunk(int n, int n2) throws IOException {
        if (n != n2) {
            Object[] arrobject = new Object[]{n2, (short)n};
            throw new IOException(String.format((String)"Invalid chunk type: expected=0x%08x, got=0x%08x", (Object[])arrobject));
        }
    }

    public static void main(String[] arrstring) throws IOException {
        AXmlDecoder aXmlDecoder = AXmlDecoder.read((InputStream)new FileInputStream("term.xml"));
        ArrayList arrayList = new ArrayList();
        aXmlDecoder.mTableStrings.getStrings((List<String>)arrayList);
        for (int i = 0; i < arrayList.size(); ++i) {
            System.out.println(i + " " + (String)arrayList.get(i));
        }
        aXmlDecoder.write((List<String>)arrayList, (OutputStream)new FileOutputStream("test.xml"));
    }

    public static AXmlDecoder read(InputStream inputStream) throws IOException {
        AXmlDecoder aXmlDecoder = new AXmlDecoder(new LEDataInputStream(inputStream));
        aXmlDecoder.readStrings();
        return aXmlDecoder;
    }

    private void readStrings() throws IOException {
        int n;
        this.checkChunk(this.mIn.readInt(), 524291);
        this.mIn.readInt();
        this.mTableStrings = StringBlock.read(this.mIn);
        byte[] arrby = new byte[2048];
        while ((n = this.mIn.read(arrby, 0, 2048)) != -1) {
            this.byteOut.write(arrby, 0, n);
        }
    }

    public void write(List<String> list, LEDataOutputStream lEDataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        LEDataOutputStream lEDataOutputStream2 = new LEDataOutputStream((OutputStream)byteArrayOutputStream);
        this.mTableStrings.write(list, lEDataOutputStream2);
        lEDataOutputStream2.writeFully(this.byteOut.toByteArray());
        lEDataOutputStream.writeInt(524291);
        lEDataOutputStream.writeInt(8 + byteArrayOutputStream.size());
        lEDataOutputStream.writeFully(byteArrayOutputStream.toByteArray());
    }

    public void write(List<String> list, OutputStream outputStream) throws IOException {
        this.write(list, new LEDataOutputStream(outputStream));
    }
}

