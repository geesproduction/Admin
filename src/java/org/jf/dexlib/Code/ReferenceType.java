/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Code;

import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;

public final class ReferenceType
extends Enum<ReferenceType> {
    private static final /* synthetic */ ReferenceType[] $VALUES;
    public static final /* enum */ ReferenceType field;
    public static final /* enum */ ReferenceType method;
    public static final /* enum */ ReferenceType none;
    public static final /* enum */ ReferenceType string;
    public static final /* enum */ ReferenceType type;

    static {
        string = new ReferenceType();
        type = new ReferenceType();
        field = new ReferenceType();
        method = new ReferenceType();
        none = new ReferenceType();
        ReferenceType[] arrreferenceType = new ReferenceType[]{string, type, field, method, none};
        $VALUES = arrreferenceType;
    }

    public static ReferenceType valueOf(String string2) {
        return (ReferenceType)Enum.valueOf(ReferenceType.class, (String)string2);
    }

    public static ReferenceType[] values() {
        return (ReferenceType[])$VALUES.clone();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean checkItem(Item item) {
        switch (1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[this.ordinal()]) {
            default: {
                return false;
            }
            case 1: {
                return item instanceof StringIdItem;
            }
            case 2: {
                return item instanceof TypeIdItem;
            }
            case 3: {
                return item instanceof FieldIdItem;
            }
            case 4: {
                return item instanceof MethodIdItem;
            }
            case 5: {
                if (item != null) return false;
                return true;
            }
        }
    }

}

