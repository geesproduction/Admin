/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.io.PrintWriter
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.Throwable
 */
package org.jf.dexlib.Util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class ExceptionWithContext
extends RuntimeException {
    private StringBuffer context;

    public ExceptionWithContext(String string2) {
        super(string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public ExceptionWithContext(String string2, Throwable throwable) {
        if (string2 == null) {
            string2 = throwable != null ? throwable.getMessage() : null;
        }
        super(string2, throwable);
        if (throwable instanceof ExceptionWithContext) {
            String string3 = ((ExceptionWithContext)throwable).context.toString();
            this.context = new StringBuffer(200 + string3.length());
            this.context.append(string3);
            return;
        }
        this.context = new StringBuffer(200);
    }

    public ExceptionWithContext(Throwable throwable) {
        super(null, throwable);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static ExceptionWithContext withContext(Throwable throwable, String string2) {
        ExceptionWithContext exceptionWithContext = throwable instanceof ExceptionWithContext ? (ExceptionWithContext)((Object)throwable) : new ExceptionWithContext(throwable);
        exceptionWithContext.addContext(string2);
        return exceptionWithContext;
    }

    public void addContext(String string2) {
        if (string2 == null) {
            throw new NullPointerException("str == null");
        }
        this.context.append(string2);
        if (!string2.endsWith("\n")) {
            this.context.append('\n');
        }
    }

    public String getContext() {
        return this.context.toString();
    }

    public void printContext(PrintStream printStream) {
        printStream.println(this.getMessage());
        printStream.print((Object)this.context);
    }

    public void printContext(PrintWriter printWriter) {
        printWriter.println(this.getMessage());
        printWriter.print((Object)this.context);
    }

    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        printStream.println((Object)this.context);
    }

    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        printWriter.println((Object)this.context);
    }
}

