/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Byte
 *  java.lang.Comparable
 *  java.lang.Enum
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  org.jf.dexlib.EncodedValue.AnnotationEncodedValue
 *  org.jf.dexlib.EncodedValue.ArrayEncodedValue
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.AnnotationEncodedValue;
import org.jf.dexlib.EncodedValue.ArrayEncodedValue;
import org.jf.dexlib.EncodedValue.BooleanEncodedValue;
import org.jf.dexlib.EncodedValue.ByteEncodedValue;
import org.jf.dexlib.EncodedValue.CharEncodedValue;
import org.jf.dexlib.EncodedValue.DoubleEncodedValue;
import org.jf.dexlib.EncodedValue.EnumEncodedValue;
import org.jf.dexlib.EncodedValue.FieldEncodedValue;
import org.jf.dexlib.EncodedValue.FloatEncodedValue;
import org.jf.dexlib.EncodedValue.IntEncodedValue;
import org.jf.dexlib.EncodedValue.LongEncodedValue;
import org.jf.dexlib.EncodedValue.MethodEncodedValue;
import org.jf.dexlib.EncodedValue.NullEncodedValue;
import org.jf.dexlib.EncodedValue.ShortEncodedValue;
import org.jf.dexlib.EncodedValue.StringEncodedValue;
import org.jf.dexlib.EncodedValue.TypeEncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;

public abstract class EncodedValue
implements Comparable<EncodedValue> {
    public static EncodedValue copyEncodedValue(DexFile dexFile, EncodedValue encodedValue) {
        switch (1.$SwitchMap$org$jf$dexlib$EncodedValue$ValueType[encodedValue.getValueType().ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return new ByteEncodedValue(((ByteEncodedValue)encodedValue).value);
            }
            case 2: {
                return new ShortEncodedValue(((ShortEncodedValue)encodedValue).value);
            }
            case 3: {
                return new CharEncodedValue(((CharEncodedValue)encodedValue).value);
            }
            case 4: {
                return new IntEncodedValue(((IntEncodedValue)encodedValue).value);
            }
            case 5: {
                return new LongEncodedValue(((LongEncodedValue)encodedValue).value);
            }
            case 6: {
                return new FloatEncodedValue(((FloatEncodedValue)encodedValue).value);
            }
            case 7: {
                return new DoubleEncodedValue(((DoubleEncodedValue)encodedValue).value);
            }
            case 8: {
                return new StringEncodedValue(StringIdItem.internStringIdItem(dexFile, ((StringEncodedValue)encodedValue).value.getStringValue()));
            }
            case 9: {
                return new TypeEncodedValue(TypeIdItem.internTypeIdItem(dexFile, ((TypeEncodedValue)encodedValue).value.getTypeDescriptor()));
            }
            case 10: {
                return new FieldEncodedValue(((FieldEncodedValue)encodedValue).value.internFieldIdItem(dexFile));
            }
            case 11: {
                return new MethodEncodedValue(((MethodEncodedValue)encodedValue).value.internMethodIdItem(dexFile));
            }
            case 12: {
                return new EnumEncodedValue(((EnumEncodedValue)encodedValue).value.internFieldIdItem(dexFile));
            }
            case 13: {
                EncodedValue[] arrencodedValue = ((ArrayEncodedValue)encodedValue).values;
                EncodedValue[] arrencodedValue2 = new EncodedValue[arrencodedValue.length];
                int n = arrencodedValue2.length;
                for (int i = 0; i < n; ++i) {
                    arrencodedValue2[i] = EncodedValue.copyEncodedValue(dexFile, arrencodedValue[i]);
                }
                return new ArrayEncodedValue(arrencodedValue2);
            }
            case 14: {
                return ((AnnotationEncodedValue)encodedValue).copyAnnotationEncodedValue(dexFile);
            }
            case 15: {
                return NullEncodedValue.NullValue;
            }
            case 16: 
        }
        return BooleanEncodedValue.getBooleanEncodedValue(((BooleanEncodedValue)encodedValue).value);
    }

    public static EncodedValue readEncodedValue(DexFile dexFile, Input input) {
        Byte by = input.readByte();
        ValueType valueType = ValueType.fromByte((byte)(31 & by));
        byte by2 = (byte)((255 & by) >> 5);
        switch (1.$SwitchMap$org$jf$dexlib$EncodedValue$ValueType[valueType.ordinal()]) {
            default: {
                return null;
            }
            case 1: {
                return new ByteEncodedValue(input);
            }
            case 2: {
                return new ShortEncodedValue(input, by2);
            }
            case 3: {
                return new CharEncodedValue(input, by2);
            }
            case 4: {
                return new IntEncodedValue(input, by2);
            }
            case 5: {
                return new LongEncodedValue(input, by2);
            }
            case 6: {
                return new FloatEncodedValue(input, by2);
            }
            case 7: {
                return new DoubleEncodedValue(input, by2);
            }
            case 8: {
                return new StringEncodedValue(dexFile, input, by2);
            }
            case 9: {
                return new TypeEncodedValue(dexFile, input, by2);
            }
            case 10: {
                return new FieldEncodedValue(dexFile, input, by2);
            }
            case 11: {
                return new MethodEncodedValue(dexFile, input, by2);
            }
            case 12: {
                return new EnumEncodedValue(dexFile, input, by2);
            }
            case 13: {
                return new ArrayEncodedValue(dexFile, input);
            }
            case 14: {
                return new AnnotationEncodedValue(dexFile, input);
            }
            case 15: {
                return NullEncodedValue.NullValue;
            }
            case 16: 
        }
        return BooleanEncodedValue.getBooleanEncodedValue(by2);
    }

    public int compareTo(EncodedValue encodedValue) {
        int n = this.getValueType().compareTo((Enum)encodedValue.getValueType());
        if (n == 0) {
            n = this.compareValue(encodedValue);
        }
        return n;
    }

    protected abstract int compareValue(EncodedValue var1);

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5 : {
            block4 : {
                if (this == object) break block4;
                if (object == null || !(object instanceof EncodedValue)) {
                    return false;
                }
                if (this.compareTo((EncodedValue)object) != 0) break block5;
            }
            return true;
        }
        return false;
    }

    public abstract ValueType getValueType();

    public abstract int placeValue(int var1);

    public abstract void writeValue(AnnotatedOutput var1);

}

