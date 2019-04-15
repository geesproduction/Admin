/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.PrintStream
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 */
package kellinwood.logging;

import java.io.PrintStream;
import kellinwood.logging.LoggerFactory;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.StreamLogger;

public class ConsoleLoggerFactory
implements LoggerFactory {
    @Override
    public LoggerInterface getLogger(String string2) {
        return new StreamLogger(string2, System.out);
    }
}

