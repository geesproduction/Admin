/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.FilterWriter
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.IllegalArgumentException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public final class IndentingWriter
extends FilterWriter {
    private boolean collectingIndent;
    private int column;
    private int indent;
    private final int maxIndent;
    private final String prefix;
    private final int width;

    public IndentingWriter(Writer writer, int n) {
        super(writer, n, "");
    }

    /*
     * Enabled aggressive block sorting
     */
    public IndentingWriter(Writer writer, int n, String string2) {
        super(writer);
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        if (n < 0) {
            throw new IllegalArgumentException("width < 0");
        }
        if (string2 == null) {
            throw new NullPointerException("prefix == null");
        }
        int n2 = n != 0 ? n : Integer.MAX_VALUE;
        this.width = n2;
        this.maxIndent = n >> 1;
        if (string2.length() == 0) {
            string2 = null;
        }
        this.prefix = string2;
        IndentingWriter.super.bol();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void bol() {
        this.column = 0;
        boolean bl = this.maxIndent != 0;
        this.collectingIndent = bl;
        this.indent = 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(int n) throws IOException {
        Object object;
        Object object2 = object = this.lock;
        synchronized (object2) {
            if (this.collectingIndent) {
                if (n == 32) {
                    this.indent = 1 + this.indent;
                    if (this.indent >= this.maxIndent) {
                        this.indent = this.maxIndent;
                        this.collectingIndent = false;
                    }
                } else {
                    this.collectingIndent = false;
                }
            }
            if (this.column == this.width && n != 10) {
                this.out.write(10);
                this.column = 0;
            }
            if (this.column == 0) {
                if (this.prefix != null) {
                    this.out.write(this.prefix);
                }
                if (!this.collectingIndent) {
                    for (int i = 0; i < this.indent; ++i) {
                        this.out.write(32);
                    }
                    this.column = this.indent;
                }
            }
            this.out.write(n);
            if (n == 10) {
                IndentingWriter.super.bol();
            } else {
                this.column = 1 + this.column;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(String string2, int n, int n2) throws IOException {
        Object object;
        Object object2 = object = this.lock;
        synchronized (object2) {
            while (n2 > 0) {
                this.write(string2.charAt(n));
                ++n;
                --n2;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void write(char[] arrc, int n, int n2) throws IOException {
        Object object;
        Object object2 = object = this.lock;
        synchronized (object2) {
            while (n2 > 0) {
                this.write(arrc[n]);
                ++n;
                --n2;
            }
            return;
        }
    }
}

