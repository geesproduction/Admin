/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 */
package org.jf.dexlib.Util;

import java.util.HashMap;

public final class AccessFlags
extends Enum<AccessFlags> {
    private static final /* synthetic */ AccessFlags[] $VALUES;
    public static final /* enum */ AccessFlags ABSTRACT;
    public static final /* enum */ AccessFlags ANNOTATION;
    public static final /* enum */ AccessFlags BRIDGE;
    public static final /* enum */ AccessFlags CONSTRUCTOR;
    public static final /* enum */ AccessFlags DECLARED_SYNCHRONIZED;
    public static final /* enum */ AccessFlags ENUM;
    public static final /* enum */ AccessFlags FINAL;
    public static final /* enum */ AccessFlags INTERFACE;
    public static final /* enum */ AccessFlags NATIVE;
    public static final /* enum */ AccessFlags PRIVATE;
    public static final /* enum */ AccessFlags PROTECTED;
    public static final /* enum */ AccessFlags PUBLIC;
    public static final /* enum */ AccessFlags STATIC;
    public static final /* enum */ AccessFlags STRICTFP;
    public static final /* enum */ AccessFlags SYNCHRONIZED;
    public static final /* enum */ AccessFlags SYNTHETIC;
    public static final /* enum */ AccessFlags TRANSIENT;
    public static final /* enum */ AccessFlags VARARGS;
    public static final /* enum */ AccessFlags VOLATILE;
    private static HashMap<String, AccessFlags> accessFlagsByName;
    private static final AccessFlags[] allFlags;
    private String accessFlagName;
    private boolean validForClass;
    private boolean validForField;
    private boolean validForMethod;
    private int value;

    static {
        PUBLIC = new AccessFlags(1, "public", true, true, true);
        PRIVATE = new AccessFlags(2, "private", true, true, true);
        PROTECTED = new AccessFlags(4, "protected", true, true, true);
        STATIC = new AccessFlags(8, "static", true, true, true);
        FINAL = new AccessFlags(16, "final", true, true, true);
        SYNCHRONIZED = new AccessFlags(32, "synchronized", false, true, false);
        VOLATILE = new AccessFlags(64, "volatile", false, false, true);
        BRIDGE = new AccessFlags(64, "bridge", false, true, false);
        TRANSIENT = new AccessFlags(128, "transient", false, false, true);
        VARARGS = new AccessFlags(128, "varargs", false, true, false);
        NATIVE = new AccessFlags(256, "native", false, true, false);
        INTERFACE = new AccessFlags(512, "interface", true, false, false);
        ABSTRACT = new AccessFlags(1024, "abstract", true, true, false);
        STRICTFP = new AccessFlags(2048, "strictfp", false, true, false);
        SYNTHETIC = new AccessFlags(4096, "synthetic", true, true, true);
        ANNOTATION = new AccessFlags(8192, "annotation", true, false, false);
        ENUM = new AccessFlags(16384, "enum", true, false, true);
        CONSTRUCTOR = new AccessFlags(65536, "constructor", false, true, false);
        DECLARED_SYNCHRONIZED = new AccessFlags(131072, "declared-synchronized", false, true, false);
        AccessFlags[] arraccessFlags = new AccessFlags[]{PUBLIC, PRIVATE, PROTECTED, STATIC, FINAL, SYNCHRONIZED, VOLATILE, BRIDGE, TRANSIENT, VARARGS, NATIVE, INTERFACE, ABSTRACT, STRICTFP, SYNTHETIC, ANNOTATION, ENUM, CONSTRUCTOR, DECLARED_SYNCHRONIZED};
        $VALUES = arraccessFlags;
        allFlags = AccessFlags.values();
        accessFlagsByName = new HashMap();
        for (AccessFlags accessFlags : allFlags) {
            accessFlagsByName.put((Object)accessFlags.accessFlagName, (Object)accessFlags);
        }
    }

    private AccessFlags(int n2, String string3, boolean bl, boolean bl2, boolean bl3) {
        this.value = n2;
        this.accessFlagName = string3;
        this.validForClass = bl;
        this.validForMethod = bl2;
        this.validForField = bl3;
    }

    private static String formatAccessFlags(AccessFlags[] arraccessFlags) {
        int n = 0;
        int n2 = arraccessFlags.length;
        for (int i = 0; i < n2; ++i) {
            n += 1 + arraccessFlags[i].toString().length();
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        int n3 = arraccessFlags.length;
        for (int i = 0; i < n3; ++i) {
            stringBuilder.append(arraccessFlags[i].toString());
            stringBuilder.append(" ");
        }
        if (arraccessFlags.length > 0) {
            stringBuilder.delete(-1 + stringBuilder.length(), stringBuilder.length());
        }
        return stringBuilder.toString();
    }

    public static String formatAccessFlagsForClass(int n) {
        return AccessFlags.formatAccessFlags(AccessFlags.getAccessFlagsForClass(n));
    }

    public static String formatAccessFlagsForField(int n) {
        return AccessFlags.formatAccessFlags(AccessFlags.getAccessFlagsForField(n));
    }

    public static String formatAccessFlagsForMethod(int n) {
        return AccessFlags.formatAccessFlags(AccessFlags.getAccessFlagsForMethod(n));
    }

    public static AccessFlags getAccessFlag(String string2) {
        return (AccessFlags)((Object)accessFlagsByName.get((Object)string2));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static AccessFlags[] getAccessFlagsForClass(int n) {
        int n2 = 0;
        for (AccessFlags accessFlags : allFlags) {
            if (!accessFlags.validForClass || (n & accessFlags.value) == 0) continue;
            ++n2;
        }
        AccessFlags[] arraccessFlags = new AccessFlags[n2];
        AccessFlags[] arraccessFlags2 = allFlags;
        int n3 = arraccessFlags2.length;
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3) {
            int n6;
            AccessFlags accessFlags = arraccessFlags2[n4];
            if (accessFlags.validForClass && (n & accessFlags.value) != 0) {
                n6 = n5 + 1;
                arraccessFlags[n5] = accessFlags;
            } else {
                n6 = n5;
            }
            ++n4;
            n5 = n6;
        }
        return arraccessFlags;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static AccessFlags[] getAccessFlagsForField(int n) {
        int n2 = 0;
        for (AccessFlags accessFlags : allFlags) {
            if (!accessFlags.validForField || (n & accessFlags.value) == 0) continue;
            ++n2;
        }
        AccessFlags[] arraccessFlags = new AccessFlags[n2];
        AccessFlags[] arraccessFlags2 = allFlags;
        int n3 = arraccessFlags2.length;
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3) {
            int n6;
            AccessFlags accessFlags = arraccessFlags2[n4];
            if (accessFlags.validForField && (n & accessFlags.value) != 0) {
                n6 = n5 + 1;
                arraccessFlags[n5] = accessFlags;
            } else {
                n6 = n5;
            }
            ++n4;
            n5 = n6;
        }
        return arraccessFlags;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static AccessFlags[] getAccessFlagsForMethod(int n) {
        int n2 = 0;
        for (AccessFlags accessFlags : allFlags) {
            if (!accessFlags.validForMethod || (n & accessFlags.value) == 0) continue;
            ++n2;
        }
        AccessFlags[] arraccessFlags = new AccessFlags[n2];
        AccessFlags[] arraccessFlags2 = allFlags;
        int n3 = arraccessFlags2.length;
        int n4 = 0;
        int n5 = 0;
        while (n4 < n3) {
            int n6;
            AccessFlags accessFlags = arraccessFlags2[n4];
            if (accessFlags.validForMethod && (n & accessFlags.value) != 0) {
                n6 = n5 + 1;
                arraccessFlags[n5] = accessFlags;
            } else {
                n6 = n5;
            }
            ++n4;
            n5 = n6;
        }
        return arraccessFlags;
    }

    public static AccessFlags valueOf(String string2) {
        return (AccessFlags)Enum.valueOf(AccessFlags.class, (String)string2);
    }

    public static AccessFlags[] values() {
        return (AccessFlags[])$VALUES.clone();
    }

    public int getValue() {
        return this.value;
    }

    public String toString() {
        return this.accessFlagName;
    }
}

