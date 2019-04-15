/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.text.SimpleDateFormat
 *  java.util.Date
 */
package kellinwood.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import kellinwood.logging.LoggerInterface;

public abstract class AbstractLogger
implements LoggerInterface {
    protected String category;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public AbstractLogger(String string2) {
        this.category = string2;
    }

    @Override
    public void debug(String string2) {
        this.writeFixNullMessage("DEBUG", string2, null);
    }

    @Override
    public void debug(String string2, Throwable throwable) {
        this.writeFixNullMessage("DEBUG", string2, throwable);
    }

    @Override
    public void error(String string2) {
        this.writeFixNullMessage("ERROR", string2, null);
    }

    @Override
    public void error(String string2, Throwable throwable) {
        this.writeFixNullMessage("ERROR", string2, throwable);
    }

    protected String format(String string2, String string3) {
        Object[] arrobject = new Object[]{this.dateFormat.format(new Date()), string2, this.category, string3};
        return String.format((String)"%s %s %s: %s\n", (Object[])arrobject);
    }

    @Override
    public void info(String string2) {
        this.writeFixNullMessage("INFO", string2, null);
    }

    @Override
    public void info(String string2, Throwable throwable) {
        this.writeFixNullMessage("INFO", string2, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isWarningEnabled() {
        return true;
    }

    @Override
    public void warning(String string2) {
        this.writeFixNullMessage("WARNING", string2, null);
    }

    @Override
    public void warning(String string2, Throwable throwable) {
        this.writeFixNullMessage("WARNING", string2, throwable);
    }

    protected abstract void write(String var1, String var2, Throwable var3);

    /*
     * Enabled aggressive block sorting
     */
    protected void writeFixNullMessage(String string2, String string3, Throwable throwable) {
        if (string3 == null) {
            string3 = throwable != null ? throwable.getClass().getName() : "null";
        }
        this.write(string2, string3, throwable);
    }
}

