/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.Appendable
 *  java.lang.CharSequence
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 */
package org.jf.util;

import java.io.IOException;
import java.io.Writer;

public class IndentingWriter
extends Writer {
    private static final String newLine = System.getProperty((String)"line.separator");
    private boolean beginningOfLine;
    private final char[] buffer;
    private int indentLevel;
    private final StringBuilder writer;

    public IndentingWriter() {
        this.buffer = new char[16];
        this.indentLevel = 0;
        this.writer = new StringBuilder(32);
    }

    public IndentingWriter(StringBuilder stringBuilder) {
        this.buffer = new char[16];
        this.indentLevel = 0;
        this.writer = stringBuilder;
    }

    public Writer append(char c) throws IOException {
        this.write(c);
        return this;
    }

    public Writer append(CharSequence charSequence) throws IOException {
        this.write(charSequence.toString());
        return this;
    }

    public Writer append(CharSequence charSequence, int n, int n2) throws IOException {
        this.write(charSequence.subSequence(n, n2).toString());
        return this;
    }

    public void close() throws IOException {
    }

    public void deindent(int n) {
        this.indentLevel -= n;
        if (this.indentLevel < 0) {
            this.indentLevel = 0;
        }
    }

    public void flush() throws IOException {
    }

    public String getString() {
        return this.writer.toString();
    }

    public StringBuilder getStringBuilder() {
        return this.writer;
    }

    public void indent(int n) {
        this.indentLevel = n + this.indentLevel;
        if (this.indentLevel < 0) {
            this.indentLevel = 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printIntAsDec(int n) throws IOException {
        boolean bl;
        int n2 = 0;
        if (n < 0) {
            bl = true;
        } else {
            n2 = 0;
            bl = false;
        }
        do {
            int n3 = n % 10;
            char[] arrc = this.buffer;
            int n4 = n2 + 1;
            arrc[n2] = (char)(n3 + 48);
            if ((n /= 10) == 0) {
                if (bl) {
                    this.write(45);
                }
                int n5 = n4;
                do {
                    if (n5 <= 0) {
                        return;
                    }
                    char[] arrc2 = this.buffer;
                    this.write(arrc2[--n5]);
                } while (true);
            }
            n2 = n4;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void printLongAsHex(long l) throws IOException {
        int n = 0;
        do {
            int n2;
            int n3;
            if ((n2 = (int)(15L & l)) < 10) {
                char[] arrc = this.buffer;
                n3 = n + 1;
                arrc[n] = (char)(n2 + 48);
            } else {
                char[] arrc = this.buffer;
                n3 = n + 1;
                arrc[n] = (char)(97 + (n2 - 10));
            }
            if ((l >>>= 4) == 0L) {
                do {
                    if (n3 <= 0) {
                        return;
                    }
                    char[] arrc = this.buffer;
                    this.write(arrc[--n3]);
                } while (true);
            }
            n = n3;
        } while (true);
    }

    public void write(int n) throws IOException {
        if (n == 10) {
            this.writer.append(newLine);
            this.beginningOfLine = true;
            return;
        }
        if (this.beginningOfLine) {
            for (int i = 0; i < this.indentLevel; ++i) {
                this.writer.append(' ');
            }
        }
        this.beginningOfLine = false;
        this.writer.append((char)n);
    }

    public void write(String string2) throws IOException {
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            this.write(string2.charAt(i));
        }
    }

    public void write(String string2, int n, int n2) throws IOException {
        int n3 = n2 + n;
        int n4 = n;
        while (n4 < n3) {
            int n5 = n4 + 1;
            this.write(string2.charAt(n4));
            n4 = n5;
        }
    }

    public void write(char[] arrc) throws IOException {
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            this.write(arrc[i]);
        }
    }

    public void write(char[] arrc, int n, int n2) throws IOException {
        int n3 = n2 + n;
        int n4 = n;
        while (n4 < n3) {
            int n5 = n4 + 1;
            this.write(arrc[n4]);
            n4 = n5;
        }
    }
}

