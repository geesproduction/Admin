/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.String
 */
package org.jf.dexlib.Code.Analysis;

import org.jf.dexlib.Util.ExceptionWithContext;

public class ValidationException
extends ExceptionWithContext {
    private int codeAddress;

    public ValidationException(int n, String string2) {
        super(string2);
        this.codeAddress = n;
    }

    public ValidationException(String string2) {
        super(string2);
    }

    public int getCodeAddress() {
        return this.codeAddress;
    }

    public void setCodeAddress(int n) {
        this.codeAddress = n;
    }
}

