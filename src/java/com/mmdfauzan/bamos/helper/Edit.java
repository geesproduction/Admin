/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package com.mmdfauzan.bamos.helper;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface Edit {
    public void read(List<String> var1, byte[] var2) throws IOException;

    public void write(String var1, OutputStream var2) throws IOException;
}

