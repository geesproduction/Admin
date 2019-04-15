/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.IllegalArgumentException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 */
package org.jf.dexlib.Util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.jf.dexlib.Util.IndentingWriter;

public final class TwoColumnOutput {
    private final StringBuffer leftBuf;
    private final IndentingWriter leftColumn;
    private final int leftWidth;
    private final Writer out;
    private final StringBuffer rightBuf;
    private final IndentingWriter rightColumn;

    public TwoColumnOutput(OutputStream outputStream, int n, int n2, String string2) {
        super((Writer)new OutputStreamWriter(outputStream), n, n2, string2);
    }

    public TwoColumnOutput(Writer writer, int n, int n2, String string2) {
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        if (n < 1) {
            throw new IllegalArgumentException("leftWidth < 1");
        }
        if (n2 < 1) {
            throw new IllegalArgumentException("rightWidth < 1");
        }
        if (string2 == null) {
            throw new NullPointerException("spacer == null");
        }
        StringWriter stringWriter = new StringWriter(1000);
        StringWriter stringWriter2 = new StringWriter(1000);
        this.out = writer;
        this.leftWidth = n;
        this.leftBuf = stringWriter.getBuffer();
        this.rightBuf = stringWriter2.getBuffer();
        this.leftColumn = new IndentingWriter((Writer)stringWriter, n);
        this.rightColumn = new IndentingWriter((Writer)stringWriter2, n2, string2);
    }

    private static void appendNewlineIfNecessary(StringBuffer stringBuffer, Writer writer) throws IOException {
        int n = stringBuffer.length();
        if (n != 0 && stringBuffer.charAt(n - 1) != '\n') {
            writer.write(10);
        }
    }

    private void flushLeft() throws IOException {
        TwoColumnOutput.appendNewlineIfNecessary(this.leftBuf, (Writer)this.leftColumn);
        while (this.leftBuf.length() != 0) {
            this.rightColumn.write(10);
            this.outputFullLines();
        }
    }

    private void flushRight() throws IOException {
        TwoColumnOutput.appendNewlineIfNecessary(this.rightBuf, (Writer)this.rightColumn);
        while (this.rightBuf.length() != 0) {
            this.leftColumn.write(10);
            this.outputFullLines();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void outputFullLines() throws IOException {
        int n;
        int n2;
        while ((n = this.leftBuf.indexOf("\n")) >= 0 && (n2 = this.rightBuf.indexOf("\n")) >= 0) {
            if (n != 0) {
                this.out.write(this.leftBuf.substring(0, n));
            }
            if (n2 != 0) {
                TwoColumnOutput.writeSpaces(this.out, this.leftWidth - n);
                this.out.write(this.rightBuf.substring(0, n2));
            }
            this.out.write(10);
            this.leftBuf.delete(0, n + 1);
            this.rightBuf.delete(0, n2 + 1);
        }
        return;
    }

    public static String toString(String string2, int n, String string3, String string4, int n2) {
        StringWriter stringWriter = new StringWriter(3 * (string2.length() + string4.length()));
        TwoColumnOutput twoColumnOutput = new TwoColumnOutput((Writer)stringWriter, n, n2, string3);
        try {
            twoColumnOutput.getLeft().write(string2);
            twoColumnOutput.getRight().write(string4);
        }
        catch (IOException iOException) {
            throw new RuntimeException("shouldn't happen", (Throwable)iOException);
        }
        twoColumnOutput.flush();
        return stringWriter.toString();
    }

    private static void writeSpaces(Writer writer, int n) throws IOException {
        while (n > 0) {
            writer.write(32);
            --n;
        }
    }

    public void flush() {
        try {
            TwoColumnOutput.appendNewlineIfNecessary(this.leftBuf, (Writer)this.leftColumn);
            TwoColumnOutput.appendNewlineIfNecessary(this.rightBuf, (Writer)this.rightColumn);
            this.outputFullLines();
            this.flushLeft();
            this.flushRight();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException((Throwable)iOException);
        }
    }

    public Writer getLeft() {
        return this.leftColumn;
    }

    public Writer getRight() {
        return this.rightColumn;
    }
}

