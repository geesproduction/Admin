/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Util;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedValue.BooleanEncodedValue;
import org.jf.dexlib.EncodedValue.ByteEncodedValue;
import org.jf.dexlib.EncodedValue.CharEncodedValue;
import org.jf.dexlib.EncodedValue.DoubleEncodedValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.EncodedValue.FloatEncodedValue;
import org.jf.dexlib.EncodedValue.IntEncodedValue;
import org.jf.dexlib.EncodedValue.LongEncodedValue;
import org.jf.dexlib.EncodedValue.NullEncodedValue;
import org.jf.dexlib.EncodedValue.ShortEncodedValue;

public class TypeUtils {
    public static EncodedValue makeDefaultValueForType(DexFile dexFile, String string2) {
        switch (string2.charAt(0)) {
            default: {
                return null;
            }
            case 'Z': {
                return BooleanEncodedValue.FalseValue;
            }
            case 'B': {
                return new ByteEncodedValue(0);
            }
            case 'S': {
                return new ShortEncodedValue(0);
            }
            case 'C': {
                return new CharEncodedValue('\u0000');
            }
            case 'I': {
                return new IntEncodedValue(0);
            }
            case 'J': {
                return new LongEncodedValue(0L);
            }
            case 'F': {
                return new FloatEncodedValue(0.0f);
            }
            case 'D': {
                return new DoubleEncodedValue(0.0);
            }
            case 'L': 
            case '[': 
        }
        return NullEncodedValue.NullValue;
    }
}

