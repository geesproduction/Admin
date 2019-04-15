/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.String
 *  java.lang.Throwable
 */
package kellinwood.logging;

import java.io.PrintStream;
import kellinwood.logging.AbstractLogger;

public class StreamLogger
extends AbstractLogger {
    PrintStream out;

    public StreamLogger(String string2, PrintStream printStream) {
        super(string2);
        this.out = printStream;
    }

    @Override
    protected void write(String string2, String string3, Throwable throwable) {
        this.out.print(this.format(string2, string3));
        if (throwable != null) {
            throwable.printStackTrace(this.out);
        }
    }
}

