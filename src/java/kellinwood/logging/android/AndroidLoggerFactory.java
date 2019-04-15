/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package kellinwood.logging.android;

import kellinwood.logging.LoggerFactory;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.android.AndroidLogger;

public class AndroidLoggerFactory
implements LoggerFactory {
    @Override
    public LoggerInterface getLogger(String string2) {
        return new AndroidLogger(string2);
    }
}

