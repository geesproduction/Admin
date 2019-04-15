/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Util;

import org.jf.dexlib.Util.Output;

public interface AnnotatedOutput
extends Output {
    public void annotate(int var1, String var2);

    public void annotate(String var1);

    public boolean annotates();

    public void deindent();

    public void endAnnotation();

    public int getAnnotationWidth();

    public void indent();

    public boolean isVerbose();

    public void setIndentAmount(int var1);
}

