/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package kellinwood.logging.log4j;

import kellinwood.logging.LoggerFactory;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.log4j.Log4jLogger;

public class Log4jLoggerFactory
implements LoggerFactory {
    @Override
    public LoggerInterface getLogger(String string2) {
        return new Log4jLogger(string2);
    }
}

