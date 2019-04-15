/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class LEDataOutputStream {
    protected DataOutputStream dos;

    public LEDataOutputStream(OutputStream outputStream) {
        this.dos = new DataOutputStream(outputStream);
    }

    public int size() {
        return this.dos.size();
    }

    public final void writeByte(byte by) throws IOException {
        this.dos.writeByte((int)by);
    }

    public final void writeChar(char c) throws IOException {
        this.dos.writeByte(c & 255);
        this.dos.writeByte(255 & c >>> 8);
    }

    public final void writeCharArray(char[] arrc) throws IOException {
        for (int i = 0; i < arrc.length; ++i) {
            this.writeChar(arrc[i]);
        }
    }

    public final void writeFully(byte[] arrby) throws IOException {
        this.dos.write(arrby, 0, arrby.length);
    }

    public final void writeFully(byte[] arrby, int n, int n2) throws IOException {
        this.dos.write(arrby, n, n2);
    }

    public final void writeInt(int n) throws IOException {
        this.dos.writeByte(n & 255);
        this.dos.writeByte(255 & n >>> 8);
        this.dos.writeByte(255 & n >>> 16);
        this.dos.writeByte(255 & n >>> 24);
    }

    public final void writeIntArray(int[] arrn) throws IOException {
        this.writeIntArray(arrn, 0, arrn.length);
    }

    public final void writeIntArray(int[] arrn, int n, int n2) throws IOException {
        while (n < n2) {
            this.writeInt(arrn[n]);
            ++n;
        }
    }

    public final void writeNulEndedString(String string2, int n, boolean bl) throws IOException {
        char[] arrc = string2.toCharArray();
        int n2 = 0;
        while (n2 < arrc.length && n != 0) {
            int n3 = n2 + 1;
            this.writeChar(arrc[n2]);
            --n;
            n2 = n3;
        }
        if (bl) {
            for (int i = 0; i < n * 2; ++i) {
                this.dos.writeByte(0);
            }
        }
    }

    public final void writeShort(short s) throws IOException {
        this.dos.writeByte(s & 255);
        this.dos.writeByte(255 & s >>> 8);
    }
}

