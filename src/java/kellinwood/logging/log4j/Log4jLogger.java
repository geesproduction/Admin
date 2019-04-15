/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  org.apache.log4j.Logger
 */
package kellinwood.logging.log4j;

import kellinwood.logging.LoggerInterface;
import org.apache.log4j.Logger;

public class Log4jLogger
implements LoggerInterface {
    Logger log;

    public Log4jLogger(String string2) {
        this.log = Logger.getLogger((String)string2);
    }

    @Override
    public void debug(String string2) {
        this.log.debug((Object)string2);
    }

    @Override
    public void debug(String string2, Throwable throwable) {
        this.log.debug((Object)string2, throwable);
    }

    @Override
    public void error(String string2) {
        this.log.error((Object)string2);
    }

    @Override
    public void error(String string2, Throwable throwable) {
        this.log.error((Object)string2, throwable);
    }

    @Override
    public void info(String string2) {
        this.log.info((Object)string2);
    }

    @Override
    public void info(String string2, Throwable throwable) {
        this.log.info((Object)string2, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled();
    }

    @Override
    public boolean isWarningEnabled() {
        return true;
    }

    @Override
    public void warning(String string2) {
        this.log.warn((Object)string2);
    }

    @Override
    public void warning(String string2, Throwable throwable) {
        this.log.warn((Object)string2, throwable);
    }
}

