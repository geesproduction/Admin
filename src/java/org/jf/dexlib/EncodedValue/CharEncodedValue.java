/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Character
 *  java.lang.Integer
 *  java.lang.String
 */
package org.jf.dexlib.EncodedValue;

import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.ValueType;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.EncodedValueUtils;
import org.jf.dexlib.Util.Input;

public class CharEncodedValue
extends EncodedValue {
    static final /* synthetic */ boolean $assertionsDisabled;
    public final char value;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !CharEncodedValue.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    public CharEncodedValue(char c) {
        this.value = c;
    }

    protected CharEncodedValue(Input input, byte by) {
        this.value = (char)EncodedValueUtils.decodeUnsignedIntegralValue(input.readBytes(by + 1));
    }

    @Override
    protected int compareValue(EncodedValue encodedValue) {
        CharEncodedValue charEncodedValue = (CharEncodedValue)encodedValue;
        if (this.value < charEncodedValue.value) {
            return -1;
        }
        return this.value > charEncodedValue.value;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.VALUE_CHAR;
    }

    public int hashCode() {
        return this.value;
    }

    @Override
    public int placeValue(int n) {
        return 1 + (n + EncodedValueUtils.getRequiredBytesForUnsignedIntegralValue(this.value));
    }

    @Override
    public void writeValue(AnnotatedOutput annotatedOutput) {
        byte[] arrby = EncodedValueUtils.encodeUnsignedIntegralValue(this.value);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(1, "value_type=" + ValueType.VALUE_CHAR.name() + ",value_arg=" + (-1 + arrby.length));
            char[] arrc = Character.toChars((int)this.value);
            if (!$assertionsDisabled && arrc.length <= 0) {
                throw new AssertionError();
            }
            annotatedOutput.annotate(arrby.length, "value: 0x" + Integer.toHexString((int)this.value) + " '" + arrc[0] + "'");
        }
        annotatedOutput.writeByte(ValueType.VALUE_CHAR.value | -1 + arrby.length << 5);
        annotatedOutput.write(arrby);
    }
}

