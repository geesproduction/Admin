/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.gesture;

public final class ContainerScrollType
extends Enum<ContainerScrollType> {
    private static final /* synthetic */ ContainerScrollType[] $VALUES;
    public static final /* enum */ ContainerScrollType HORIZONTAL = new ContainerScrollType();
    public static final /* enum */ ContainerScrollType VERTICAL = new ContainerScrollType();

    static {
        ContainerScrollType[] arrcontainerScrollType = new ContainerScrollType[]{HORIZONTAL, VERTICAL};
        $VALUES = arrcontainerScrollType;
    }

    public static ContainerScrollType valueOf(String string2) {
        return (ContainerScrollType)Enum.valueOf(ContainerScrollType.class, (String)string2);
    }

    public static ContainerScrollType[] values() {
        return (ContainerScrollType[])$VALUES.clone();
    }
}

