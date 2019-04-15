/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.TreeMap
 *  kellinwood.logging.NullLoggerFactory
 */
package kellinwood.logging;

import java.util.Map;
import java.util.TreeMap;
import kellinwood.logging.LoggerFactory;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.NullLoggerFactory;

public class LoggerManager {
    static LoggerFactory factory = new NullLoggerFactory();
    static Map<String, LoggerInterface> loggers = new TreeMap();

    public static LoggerInterface getLogger(String string2) {
        LoggerInterface loggerInterface = (LoggerInterface)loggers.get((Object)string2);
        if (loggerInterface == null) {
            loggerInterface = factory.getLogger(string2);
            loggers.put((Object)string2, (Object)loggerInterface);
        }
        return loggerInterface;
    }

    public static void setLoggerFactory(LoggerFactory loggerFactory) {
        factory = loggerFactory;
    }
}

