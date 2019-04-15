/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 */
package lecho.lib.hellocharts.model;

public class SelectedValue {
    private int firstIndex;
    private int secondIndex;
    private SelectedValueType type;

    public SelectedValue() {
        this.type = SelectedValueType.NONE;
        this.clear();
    }

    public SelectedValue(int n, int n2, SelectedValueType selectedValueType) {
        this.type = SelectedValueType.NONE;
        this.set(n, n2, selectedValueType);
    }

    public void clear() {
        this.set(Integer.MIN_VALUE, Integer.MIN_VALUE, SelectedValueType.NONE);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block8 : {
            block7 : {
                if (this == object) break block7;
                if (object == null) {
                    return false;
                }
                if (this.getClass() != object.getClass()) {
                    return false;
                }
                SelectedValue selectedValue = (SelectedValue)object;
                if (this.firstIndex != selectedValue.firstIndex) {
                    return false;
                }
                if (this.secondIndex != selectedValue.secondIndex) {
                    return false;
                }
                if (this.type != selectedValue.type) break block8;
            }
            return true;
        }
        return false;
    }

    public int getFirstIndex() {
        return this.firstIndex;
    }

    public int getSecondIndex() {
        return this.secondIndex;
    }

    public SelectedValueType getType() {
        return this.type;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        int n2 = 31 * (31 * (31 + this.firstIndex) + this.secondIndex);
        if (this.type == null) {
            n = 0;
            do {
                return n2 + n;
                break;
            } while (true);
        }
        n = this.type.hashCode();
        return n2 + n;
    }

    public boolean isSet() {
        return this.firstIndex >= 0 && this.secondIndex >= 0;
    }

    public void set(int n, int n2, SelectedValueType selectedValueType) {
        this.firstIndex = n;
        this.secondIndex = n2;
        if (selectedValueType != null) {
            this.type = selectedValueType;
            return;
        }
        this.type = SelectedValueType.NONE;
    }

    public void set(SelectedValue selectedValue) {
        this.firstIndex = selectedValue.firstIndex;
        this.secondIndex = selectedValue.secondIndex;
        this.type = selectedValue.type;
    }

    public void setFirstIndex(int n) {
        this.firstIndex = n;
    }

    public void setSecondIndex(int n) {
        this.secondIndex = n;
    }

    public void setType(SelectedValueType selectedValueType) {
        this.type = selectedValueType;
    }

    public String toString() {
        return "SelectedValue [firstIndex=" + this.firstIndex + ", secondIndex=" + this.secondIndex + ", type=" + (Object)((Object)this.type) + "]";
    }

    public static final class SelectedValueType
    extends Enum<SelectedValueType> {
        private static final /* synthetic */ SelectedValueType[] $VALUES;
        public static final /* enum */ SelectedValueType COLUMN;
        public static final /* enum */ SelectedValueType LINE;
        public static final /* enum */ SelectedValueType NONE;

        static {
            NONE = new SelectedValueType();
            LINE = new SelectedValueType();
            COLUMN = new SelectedValueType();
            SelectedValueType[] arrselectedValueType = new SelectedValueType[]{NONE, LINE, COLUMN};
            $VALUES = arrselectedValueType;
        }

        public static SelectedValueType valueOf(String string2) {
            return (SelectedValueType)Enum.valueOf(SelectedValueType.class, (String)string2);
        }

        public static SelectedValueType[] values() {
            return (SelectedValueType[])$VALUES.clone();
        }
    }

}

