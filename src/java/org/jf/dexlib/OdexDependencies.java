/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.UnsupportedEncodingException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 */
package org.jf.dexlib;

import java.io.UnsupportedEncodingException;
import org.jf.dexlib.Util.Input;

public class OdexDependencies {
    public final int crc;
    public final int dalvikBuild;
    private final String[] dependencies;
    private final byte[][] dependencyChecksums;
    public final int modificationTime;

    public OdexDependencies(Input input) {
        this.modificationTime = input.readInt();
        this.crc = input.readInt();
        this.dalvikBuild = input.readInt();
        int n = input.readInt();
        this.dependencies = new String[n];
        this.dependencyChecksums = new byte[n][];
        for (int i = 0; i < n; ++i) {
            int n2 = input.readInt();
            try {
                this.dependencies[i] = new String(input.readBytes(n2), 0, n2 - 1, "US-ASCII");
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException((Throwable)unsupportedEncodingException);
            }
            this.dependencyChecksums[i] = input.readBytes(20);
        }
    }

    public String getDependency(int n) {
        return this.dependencies[n];
    }

    public byte[] getDependencyChecksum(int n) {
        return (byte[])this.dependencyChecksums[n].clone();
    }

    public int getDependencyCount() {
        return this.dependencies.length;
    }
}

