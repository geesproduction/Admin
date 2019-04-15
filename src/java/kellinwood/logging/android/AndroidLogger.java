/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.Log
 *  android.widget.Toast
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 */
package kellinwood.logging.android;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import kellinwood.logging.AbstractLogger;

public class AndroidLogger
extends AbstractLogger {
    boolean isDebugToastEnabled = false;
    boolean isErrorToastEnabled = true;
    boolean isInfoToastEnabled = false;
    boolean isWarningToastEnabled = true;
    Context toastContext;

    public AndroidLogger(String string2) {
        super(string2);
        int n = this.category.lastIndexOf(46);
        if (n > 0) {
            this.category = this.category.substring(n + 1);
        }
    }

    public void debugLO(String string2, Throwable throwable) {
        boolean bl = this.isDebugToastEnabled;
        this.isDebugToastEnabled = false;
        this.writeFixNullMessage("DEBUG", string2, throwable);
        this.isDebugToastEnabled = bl;
    }

    public void errorLO(String string2, Throwable throwable) {
        boolean bl = this.isErrorToastEnabled;
        this.isErrorToastEnabled = false;
        this.writeFixNullMessage("ERROR", string2, throwable);
        this.isErrorToastEnabled = bl;
    }

    public Context getToastContext() {
        return this.toastContext;
    }

    public void infoLO(String string2, Throwable throwable) {
        boolean bl = this.isInfoToastEnabled;
        this.isInfoToastEnabled = false;
        this.writeFixNullMessage("INFO", string2, throwable);
        this.isInfoToastEnabled = bl;
    }

    @Override
    public boolean isDebugEnabled() {
        return Log.isLoggable((String)this.category, (int)3);
    }

    public boolean isDebugToastEnabled() {
        return this.isDebugToastEnabled;
    }

    @Override
    public boolean isErrorEnabled() {
        return Log.isLoggable((String)this.category, (int)6);
    }

    public boolean isErrorToastEnabled() {
        return this.isErrorToastEnabled;
    }

    @Override
    public boolean isInfoEnabled() {
        return Log.isLoggable((String)this.category, (int)4);
    }

    public boolean isInfoToastEnabled() {
        return this.isInfoToastEnabled;
    }

    @Override
    public boolean isWarningEnabled() {
        return Log.isLoggable((String)this.category, (int)5);
    }

    public boolean isWarningToastEnabled() {
        return this.isWarningToastEnabled;
    }

    public void setDebugToastEnabled(boolean bl) {
        this.isDebugToastEnabled = bl;
    }

    public void setErrorToastEnabled(boolean bl) {
        this.isErrorToastEnabled = bl;
    }

    public void setInfoToastEnabled(boolean bl) {
        this.isInfoToastEnabled = bl;
    }

    public void setToastContext(Context context) {
        this.toastContext = context;
    }

    public void setWarningToastEnabled(boolean bl) {
        this.isWarningToastEnabled = bl;
    }

    protected void toast(String string2) {
        try {
            if (this.toastContext != null) {
                Toast.makeText((Context)this.toastContext, (CharSequence)string2, (int)1).show();
            }
            return;
        }
        catch (Throwable throwable) {
            Log.e((String)this.category, (String)string2, (Throwable)throwable);
            return;
        }
    }

    public void warningLO(String string2, Throwable throwable) {
        boolean bl = this.isWarningToastEnabled;
        this.isWarningToastEnabled = false;
        this.writeFixNullMessage("WARNING", string2, throwable);
        this.isWarningToastEnabled = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(String string2, String string3, Throwable throwable) {
        if ("ERROR".equals((Object)string2)) {
            if (throwable != null) {
                Log.e((String)this.category, (String)string3, (Throwable)throwable);
            } else {
                Log.e((String)this.category, (String)string3);
            }
            if (!this.isErrorToastEnabled) return;
            {
                this.toast(string3);
                return;
            }
        } else if ("DEBUG".equals((Object)string2)) {
            if (throwable != null) {
                Log.d((String)this.category, (String)string3, (Throwable)throwable);
            } else {
                Log.d((String)this.category, (String)string3);
            }
            if (!this.isDebugToastEnabled) return;
            {
                this.toast(string3);
                return;
            }
        } else if ("WARNING".equals((Object)string2)) {
            if (throwable != null) {
                Log.w((String)this.category, (String)string3, (Throwable)throwable);
            } else {
                Log.w((String)this.category, (String)string3);
            }
            if (!this.isWarningToastEnabled) return;
            {
                this.toast(string3);
                return;
            }
        } else {
            if (!"INFO".equals((Object)string2)) return;
            {
                if (throwable != null) {
                    Log.i((String)this.category, (String)string3, (Throwable)throwable);
                } else {
                    Log.i((String)this.category, (String)string3);
                }
                if (!this.isInfoToastEnabled) return;
                {
                    this.toast(string3);
                    return;
                }
            }
        }
    }
}

