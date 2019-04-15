/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.String
 *  org.jf.dexlib.Code.Format.Instruction23x
 *  org.jf.dexlib.Code.Format.Instruction3rc
 */
package org.jf.dexlib.Code.Format;

import org.jf.dexlib.Code.Format.Instruction10t;
import org.jf.dexlib.Code.Format.Instruction10x;
import org.jf.dexlib.Code.Format.Instruction11n;
import org.jf.dexlib.Code.Format.Instruction11x;
import org.jf.dexlib.Code.Format.Instruction12x;
import org.jf.dexlib.Code.Format.Instruction20t;
import org.jf.dexlib.Code.Format.Instruction21c;
import org.jf.dexlib.Code.Format.Instruction21h;
import org.jf.dexlib.Code.Format.Instruction21s;
import org.jf.dexlib.Code.Format.Instruction21t;
import org.jf.dexlib.Code.Format.Instruction22b;
import org.jf.dexlib.Code.Format.Instruction22c;
import org.jf.dexlib.Code.Format.Instruction22cs;
import org.jf.dexlib.Code.Format.Instruction22s;
import org.jf.dexlib.Code.Format.Instruction22t;
import org.jf.dexlib.Code.Format.Instruction22x;
import org.jf.dexlib.Code.Format.Instruction23x;
import org.jf.dexlib.Code.Format.Instruction30t;
import org.jf.dexlib.Code.Format.Instruction31c;
import org.jf.dexlib.Code.Format.Instruction31i;
import org.jf.dexlib.Code.Format.Instruction31t;
import org.jf.dexlib.Code.Format.Instruction32x;
import org.jf.dexlib.Code.Format.Instruction35c;
import org.jf.dexlib.Code.Format.Instruction35ms;
import org.jf.dexlib.Code.Format.Instruction35s;
import org.jf.dexlib.Code.Format.Instruction3rc;
import org.jf.dexlib.Code.Format.Instruction3rms;
import org.jf.dexlib.Code.Format.Instruction51l;
import org.jf.dexlib.Code.Instruction;

public final class Format
extends Enum<Format> {
    private static final /* synthetic */ Format[] $VALUES;
    public static final /* enum */ Format ArrayData;
    public static final /* enum */ Format Format10t;
    public static final /* enum */ Format Format10x;
    public static final /* enum */ Format Format11n;
    public static final /* enum */ Format Format11x;
    public static final /* enum */ Format Format12x;
    public static final /* enum */ Format Format20t;
    public static final /* enum */ Format Format21c;
    public static final /* enum */ Format Format21h;
    public static final /* enum */ Format Format21s;
    public static final /* enum */ Format Format21t;
    public static final /* enum */ Format Format22b;
    public static final /* enum */ Format Format22c;
    public static final /* enum */ Format Format22cs;
    public static final /* enum */ Format Format22s;
    public static final /* enum */ Format Format22t;
    public static final /* enum */ Format Format22x;
    public static final /* enum */ Format Format23x;
    public static final /* enum */ Format Format30t;
    public static final /* enum */ Format Format31c;
    public static final /* enum */ Format Format31i;
    public static final /* enum */ Format Format31t;
    public static final /* enum */ Format Format32x;
    public static final /* enum */ Format Format35c;
    public static final /* enum */ Format Format35ms;
    public static final /* enum */ Format Format35s;
    public static final /* enum */ Format Format3rc;
    public static final /* enum */ Format Format3rms;
    public static final /* enum */ Format Format51l;
    public static final /* enum */ Format PackedSwitchData;
    public static final /* enum */ Format SparseSwitchData;
    public static final /* enum */ Format UnresolvedOdexInstruction;
    public final Instruction.InstructionFactory Factory;
    public final int size;
    public final boolean variableSizeFormat;

    static {
        Format10t = new Format(Instruction10t.Factory, 2);
        Format10x = new Format(Instruction10x.Factory, 2);
        Format11n = new Format(Instruction11n.Factory, 2);
        Format11x = new Format(Instruction11x.Factory, 2);
        Format12x = new Format(Instruction12x.Factory, 2);
        Format20t = new Format(Instruction20t.Factory, 4);
        Format21c = new Format(Instruction21c.Factory, 4);
        Format21h = new Format(Instruction21h.Factory, 4);
        Format21s = new Format(Instruction21s.Factory, 4);
        Format21t = new Format(Instruction21t.Factory, 4);
        Format22b = new Format(Instruction22b.Factory, 4);
        Format22c = new Format(Instruction22c.Factory, 4);
        Format22cs = new Format(Instruction22cs.Factory, 4);
        Format22s = new Format(Instruction22s.Factory, 4);
        Format22t = new Format(Instruction22t.Factory, 4);
        Format22x = new Format(Instruction22x.Factory, 4);
        Format23x = new Format(Instruction23x.Factory, 4);
        Format30t = new Format(Instruction30t.Factory, 6);
        Format31c = new Format(Instruction31c.Factory, 6);
        Format31i = new Format(Instruction31i.Factory, 6);
        Format31t = new Format(Instruction31t.Factory, 6);
        Format32x = new Format(Instruction32x.Factory, 6);
        Format35c = new Format(Instruction35c.Factory, 6);
        Format35s = new Format(Instruction35s.Factory, 6);
        Format35ms = new Format(Instruction35ms.Factory, 6);
        Format3rc = new Format(Instruction3rc.Factory, 6);
        Format3rms = new Format(Instruction3rms.Factory, 6);
        Format51l = new Format(Instruction51l.Factory, 10);
        ArrayData = new Format(null, -1, true);
        PackedSwitchData = new Format(null, -1, true);
        SparseSwitchData = new Format(null, -1, true);
        UnresolvedOdexInstruction = new Format(null, -1, false);
        Format[] arrformat = new Format[]{Format10t, Format10x, Format11n, Format11x, Format12x, Format20t, Format21c, Format21h, Format21s, Format21t, Format22b, Format22c, Format22cs, Format22s, Format22t, Format22x, Format23x, Format30t, Format31c, Format31i, Format31t, Format32x, Format35c, Format35s, Format35ms, Format3rc, Format3rms, Format51l, ArrayData, PackedSwitchData, SparseSwitchData, UnresolvedOdexInstruction};
        $VALUES = arrformat;
    }

    private Format(Instruction.InstructionFactory instructionFactory, int n2) {
    }

    private Format(Instruction.InstructionFactory instructionFactory, int n2, boolean bl) {
        this.Factory = instructionFactory;
        this.size = n2;
        this.variableSizeFormat = bl;
    }

    public static Format valueOf(String string2) {
        return (Format)Enum.valueOf(Format.class, (String)string2);
    }

    public static Format[] values() {
        return (Format[])$VALUES.clone();
    }
}

