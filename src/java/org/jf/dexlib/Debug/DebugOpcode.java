/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.dexlib.Debug;

public final class DebugOpcode
extends Enum<DebugOpcode> {
    private static final /* synthetic */ DebugOpcode[] $VALUES;
    public static final /* enum */ DebugOpcode DBG_ADVANCE_LINE;
    public static final /* enum */ DebugOpcode DBG_ADVANCE_PC;
    public static final /* enum */ DebugOpcode DBG_END_LOCAL;
    public static final /* enum */ DebugOpcode DBG_END_SEQUENCE;
    public static final /* enum */ DebugOpcode DBG_RESTART_LOCAL;
    public static final /* enum */ DebugOpcode DBG_SET_EPILOGUE_BEGIN;
    public static final /* enum */ DebugOpcode DBG_SET_FILE;
    public static final /* enum */ DebugOpcode DBG_SET_PROLOGUE_END;
    public static final /* enum */ DebugOpcode DBG_SPECIAL_OPCODE;
    public static final /* enum */ DebugOpcode DBG_START_LOCAL;
    public static final /* enum */ DebugOpcode DBG_START_LOCAL_EXTENDED;
    private static DebugOpcode[] opcodesByValue;
    public final byte value;

    static {
        DBG_END_SEQUENCE = new DebugOpcode(0);
        DBG_ADVANCE_PC = new DebugOpcode(1);
        DBG_ADVANCE_LINE = new DebugOpcode(2);
        DBG_START_LOCAL = new DebugOpcode(3);
        DBG_START_LOCAL_EXTENDED = new DebugOpcode(4);
        DBG_END_LOCAL = new DebugOpcode(5);
        DBG_RESTART_LOCAL = new DebugOpcode(6);
        DBG_SET_PROLOGUE_END = new DebugOpcode(7);
        DBG_SET_EPILOGUE_BEGIN = new DebugOpcode(8);
        DBG_SET_FILE = new DebugOpcode(9);
        DBG_SPECIAL_OPCODE = new DebugOpcode(10);
        DebugOpcode[] arrdebugOpcode = new DebugOpcode[]{DBG_END_SEQUENCE, DBG_ADVANCE_PC, DBG_ADVANCE_LINE, DBG_START_LOCAL, DBG_START_LOCAL_EXTENDED, DBG_END_LOCAL, DBG_RESTART_LOCAL, DBG_SET_PROLOGUE_END, DBG_SET_EPILOGUE_BEGIN, DBG_SET_FILE, DBG_SPECIAL_OPCODE};
        $VALUES = arrdebugOpcode;
        opcodesByValue = new DebugOpcode[11];
        DebugOpcode[] arrdebugOpcode2 = DebugOpcode.values();
        int n = arrdebugOpcode2.length;
        for (int i = 0; i < n; ++i) {
            DebugOpcode debugOpcode;
            DebugOpcode.opcodesByValue[255 & debugOpcode.value] = debugOpcode = arrdebugOpcode2[i];
        }
    }

    private DebugOpcode(byte by) {
        this.value = by;
    }

    public static DebugOpcode getDebugOpcodeByValue(byte by) {
        byte by2 = (byte)Math.min((int)(by & 255), (int)10);
        return opcodesByValue[by2];
    }

    public static DebugOpcode valueOf(String string2) {
        return (DebugOpcode)Enum.valueOf(DebugOpcode.class, (String)string2);
    }

    public static DebugOpcode[] values() {
        return (DebugOpcode[])$VALUES.clone();
    }
}

