/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.gesture;

public final class ZoomType
extends Enum<ZoomType> {
    private static final /* synthetic */ ZoomType[] $VALUES;
    public static final /* enum */ ZoomType HORIZONTAL = new ZoomType();
    public static final /* enum */ ZoomType HORIZONTAL_AND_VERTICAL;
    public static final /* enum */ ZoomType VERTICAL;

    static {
        VERTICAL = new ZoomType();
        HORIZONTAL_AND_VERTICAL = new ZoomType();
        ZoomType[] arrzoomType = new ZoomType[]{HORIZONTAL, VERTICAL, HORIZONTAL_AND_VERTICAL};
        $VALUES = arrzoomType;
    }

    public static ZoomType valueOf(String string2) {
        return (ZoomType)Enum.valueOf(ZoomType.class, (String)string2);
    }

    public static ZoomType[] values() {
        return (ZoomType[])$VALUES.clone();
    }
}

