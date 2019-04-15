/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib.Code.Analysis;

import org.jf.dexlib.Code.Analysis.ValidationException;
import org.jf.dexlib.Code.Opcode;

public class OdexedFieldInstructionMapper {
    private static Opcode[][][][] opcodeMap;

    static {
        Opcode[][][][] arrarropcode = new Opcode[2][][][];
        Opcode[][][] arrarropcode2 = new Opcode[3][][];
        Opcode[][] arrarropcode3 = new Opcode[2][];
        Opcode[] arropcode = new Opcode[]{Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_WIDE_QUICK, Opcode.IGET_OBJECT_QUICK};
        arrarropcode3[0] = arropcode;
        Opcode[] arropcode2 = new Opcode[]{Opcode.IGET_BOOLEAN, Opcode.IGET_BYTE, Opcode.IGET_SHORT, Opcode.IGET_CHAR, Opcode.IGET, Opcode.IGET_WIDE, Opcode.IGET_OBJECT};
        arrarropcode3[1] = arropcode2;
        arrarropcode2[0] = arrarropcode3;
        Opcode[][] arrarropcode4 = new Opcode[2][];
        Opcode[] arropcode3 = new Opcode[]{Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_WIDE_VOLATILE, Opcode.IGET_OBJECT_VOLATILE};
        arrarropcode4[0] = arropcode3;
        Opcode[] arropcode4 = new Opcode[]{Opcode.IGET_BOOLEAN, Opcode.IGET_BYTE, Opcode.IGET_SHORT, Opcode.IGET_CHAR, Opcode.IGET, Opcode.IGET_WIDE, Opcode.IGET_OBJECT};
        arrarropcode4[1] = arropcode4;
        arrarropcode2[1] = arrarropcode4;
        Opcode[][] arrarropcode5 = new Opcode[2][];
        Opcode[] arropcode5 = new Opcode[]{Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_WIDE_VOLATILE, Opcode.SGET_OBJECT_VOLATILE};
        arrarropcode5[0] = arropcode5;
        Opcode[] arropcode6 = new Opcode[]{Opcode.SGET_BOOLEAN, Opcode.SGET_BYTE, Opcode.SGET_SHORT, Opcode.SGET_CHAR, Opcode.SGET, Opcode.SGET_WIDE, Opcode.SGET_OBJECT};
        arrarropcode5[1] = arropcode6;
        arrarropcode2[2] = arrarropcode5;
        arrarropcode[0] = arrarropcode2;
        Opcode[][][] arrarropcode6 = new Opcode[3][][];
        Opcode[][] arrarropcode7 = new Opcode[2][];
        Opcode[] arropcode7 = new Opcode[]{Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_WIDE_QUICK, Opcode.IPUT_OBJECT_QUICK};
        arrarropcode7[0] = arropcode7;
        Opcode[] arropcode8 = new Opcode[]{Opcode.IPUT_BOOLEAN, Opcode.IPUT_BYTE, Opcode.IPUT_SHORT, Opcode.IPUT_CHAR, Opcode.IPUT, Opcode.IPUT_WIDE, Opcode.IPUT_OBJECT};
        arrarropcode7[1] = arropcode8;
        arrarropcode6[0] = arrarropcode7;
        Opcode[][] arrarropcode8 = new Opcode[2][];
        Opcode[] arropcode9 = new Opcode[]{Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_WIDE_VOLATILE, Opcode.IPUT_OBJECT_VOLATILE};
        arrarropcode8[0] = arropcode9;
        Opcode[] arropcode10 = new Opcode[]{Opcode.IPUT_BOOLEAN, Opcode.IPUT_BYTE, Opcode.IPUT_SHORT, Opcode.IPUT_CHAR, Opcode.IPUT, Opcode.IPUT_WIDE, Opcode.IPUT_OBJECT};
        arrarropcode8[1] = arropcode10;
        arrarropcode6[1] = arrarropcode8;
        Opcode[][] arrarropcode9 = new Opcode[2][];
        Opcode[] arropcode11 = new Opcode[]{Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_WIDE_VOLATILE, Opcode.SPUT_OBJECT_VOLATILE};
        arrarropcode9[0] = arropcode11;
        Opcode[] arropcode12 = new Opcode[]{Opcode.SPUT_BOOLEAN, Opcode.SPUT_BYTE, Opcode.SPUT_SHORT, Opcode.SPUT_CHAR, Opcode.SPUT, Opcode.SPUT_WIDE, Opcode.SPUT_OBJECT};
        arrarropcode9[1] = arropcode12;
        arrarropcode6[2] = arrarropcode9;
        arrarropcode[1] = arrarropcode6;
        opcodeMap = arrarropcode;
    }

    /*
     * Enabled aggressive block sorting
     */
    static Opcode getAndCheckDeodexedOpcodeForOdexedOpcode(String string2, Opcode opcode) {
        int n = opcode.setsRegister() ? 0 : 1;
        int n2 = OdexedFieldInstructionMapper.getOpcodeSubtype(opcode);
        int n3 = OdexedFieldInstructionMapper.getTypeIndex(string2.charAt(0));
        Opcode opcode2 = opcodeMap[n][n2][0][n3];
        Opcode opcode3 = opcodeMap[n][n2][1][n3];
        if (opcode2 != opcode) {
            Object[] arrobject = new Object[]{string2, opcode.name};
            throw new ValidationException(String.format((String)"Incorrect field type \"%s\" for %s", (Object[])arrobject));
        }
        return opcode3;
    }

    private static int getOpcodeSubtype(Opcode opcode) {
        if (opcode.isOdexedInstanceQuick()) {
            return 0;
        }
        if (opcode.isOdexedInstanceVolatile()) {
            return 1;
        }
        if (opcode.isOdexedStaticVolatile()) {
            return 2;
        }
        Object[] arrobject = new Object[]{opcode.name};
        throw new RuntimeException(String.format((String)"Not an odexed field access opcode: %s", (Object[])arrobject));
    }

    private static int getTypeIndex(char c) {
        int n = 0;
        switch (c) {
            default: {
                Object[] arrobject = new Object[]{Character.valueOf((char)c)};
                throw new RuntimeException(String.format((String)"Unknown type %s: ", (Object[])arrobject));
            }
            case 'B': {
                n = 1;
            }
            case 'Z': {
                return n;
            }
            case 'S': {
                return 2;
            }
            case 'C': {
                return 3;
            }
            case 'F': 
            case 'I': {
                return 4;
            }
            case 'D': 
            case 'J': {
                return 5;
            }
            case 'L': 
            case '[': 
        }
        return 6;
    }
}

