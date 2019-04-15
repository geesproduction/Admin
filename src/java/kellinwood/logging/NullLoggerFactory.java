/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package kellinwood.logging;

import kellinwood.logging.LoggerFactory;
import kellinwood.logging.LoggerInterface;

public class NullLoggerFactory
implements LoggerFactory {
    static LoggerInterface logger = new LoggerInterface(){

        @Override
        public void debug(String string2) {
        }

        @Override
        public void debug(String string2, Throwable throwable) {
        }

        @Override
        public void error(String string2) {
        }

        @Override
        public void error(String string2, Throwable throwable) {
        }

        @Override
        public void info(String string2) {
        }

        @Override
        public void info(String string2, Throwable throwable) {
        }

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public boolean isErrorEnabled() {
            return false;
        }

        @Override
        public boolean isInfoEnabled() {
            return false;
        }

        @Override
        public boolean isWarningEnabled() {
            return false;
        }

        @Override
        public void warning(String string2) {
        }

        @Override
        public void warning(String string2, Throwable throwable) {
        }
    };

    @Override
    public LoggerInterface getLogger(String string2) {
        return logger;
    }

}

