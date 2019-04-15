/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.Arrays
 */
package org.jf.dexlib;

import java.util.Arrays;
import org.jf.dexlib.Util.Input;

public class OdexHeader {
    public static final byte[] MAGIC_35 = new byte[]{100, 101, 121, 10, 48, 51, 53, 0};
    public static final byte[] MAGIC_36 = new byte[]{100, 101, 121, 10, 48, 51, 54, 0};
    public final int auxLength;
    public final int auxOffset;
    public final int depsLength;
    public final int depsOffset;
    public final int dexLength;
    public final int dexOffset;
    public final int flags;
    public final byte[] magic;
    public final int version;

    /*
     * Enabled aggressive block sorting
     */
    public OdexHeader(Input input) {
        this.magic = input.readBytes(8);
        if (Arrays.equals((byte[])MAGIC_35, (byte[])this.magic)) {
            this.version = 35;
        } else {
            if (!Arrays.equals((byte[])MAGIC_36, (byte[])this.magic)) {
                throw new RuntimeException("The magic value is not one of the expected values");
            }
            this.version = 36;
        }
        this.dexOffset = input.readInt();
        this.dexLength = input.readInt();
        this.depsOffset = input.readInt();
        this.depsLength = input.readInt();
        this.auxOffset = input.readInt();
        this.auxLength = input.readInt();
        this.flags = input.readInt();
        input.readInt();
    }
}

