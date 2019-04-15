/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Object
 */
package org.jf.dexlib.Util;

public abstract class AlignmentUtils {
    static final /* synthetic */ boolean $assertionsDisabled;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !AlignmentUtils.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public static int alignOffset(int n, int n2) {
        int n3 = n2 - 1;
        if (!($assertionsDisabled || n2 >= 0 && (n3 & n2) == 0)) {
            throw new AssertionError();
        }
        return n + n3 & ~n3;
    }

    public static boolean isAligned(int n, int n2) {
        return n % n2 == 0;
    }
}

