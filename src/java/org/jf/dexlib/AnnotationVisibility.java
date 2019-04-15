/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib;

public final class AnnotationVisibility
extends Enum<AnnotationVisibility> {
    private static final /* synthetic */ AnnotationVisibility[] $VALUES;
    public static final /* enum */ AnnotationVisibility BUILD = new AnnotationVisibility(0, "build");
    public static final /* enum */ AnnotationVisibility RUNTIME = new AnnotationVisibility(1, "runtime");
    public static final /* enum */ AnnotationVisibility SYSTEM = new AnnotationVisibility(2, "system");
    public final byte value;
    public final String visibility;

    static {
        AnnotationVisibility[] arrannotationVisibility = new AnnotationVisibility[]{BUILD, RUNTIME, SYSTEM};
        $VALUES = arrannotationVisibility;
    }

    private AnnotationVisibility(byte by, String string3) {
        this.value = by;
        this.visibility = string3;
    }

    public static AnnotationVisibility fromByte(byte by) {
        switch (by) {
            default: {
                throw new RuntimeException("Invalid annotation visibility value: " + by);
            }
            case 0: {
                return BUILD;
            }
            case 1: {
                return RUNTIME;
            }
            case 2: 
        }
        return SYSTEM;
    }

    public static AnnotationVisibility valueOf(String string2) {
        return (AnnotationVisibility)Enum.valueOf(AnnotationVisibility.class, (String)string2);
    }

    public static AnnotationVisibility[] values() {
        return (AnnotationVisibility[])$VALUES.clone();
    }
}

