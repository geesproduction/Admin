/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Enum
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package org.jf.dexlib.Code;

import java.util.HashMap;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.ReferenceType;

public final class Opcode
extends Enum<Opcode> {
    private static final /* synthetic */ Opcode[] $VALUES;
    public static final /* enum */ Opcode ADD_DOUBLE;
    public static final /* enum */ Opcode ADD_DOUBLE_2ADDR;
    public static final /* enum */ Opcode ADD_FLOAT;
    public static final /* enum */ Opcode ADD_FLOAT_2ADDR;
    public static final /* enum */ Opcode ADD_INT;
    public static final /* enum */ Opcode ADD_INT_2ADDR;
    public static final /* enum */ Opcode ADD_INT_LIT16;
    public static final /* enum */ Opcode ADD_INT_LIT8;
    public static final /* enum */ Opcode ADD_LONG;
    public static final /* enum */ Opcode ADD_LONG_2ADDR;
    public static final /* enum */ Opcode AGET;
    public static final /* enum */ Opcode AGET_BOOLEAN;
    public static final /* enum */ Opcode AGET_BYTE;
    public static final /* enum */ Opcode AGET_CHAR;
    public static final /* enum */ Opcode AGET_OBJECT;
    public static final /* enum */ Opcode AGET_SHORT;
    public static final /* enum */ Opcode AGET_WIDE;
    public static final /* enum */ Opcode AND_INT;
    public static final /* enum */ Opcode AND_INT_2ADDR;
    public static final /* enum */ Opcode AND_INT_LIT16;
    public static final /* enum */ Opcode AND_INT_LIT8;
    public static final /* enum */ Opcode AND_LONG;
    public static final /* enum */ Opcode AND_LONG_2ADDR;
    public static final /* enum */ Opcode APUT;
    public static final /* enum */ Opcode APUT_BOOLEAN;
    public static final /* enum */ Opcode APUT_BYTE;
    public static final /* enum */ Opcode APUT_CHAR;
    public static final /* enum */ Opcode APUT_OBJECT;
    public static final /* enum */ Opcode APUT_SHORT;
    public static final /* enum */ Opcode APUT_WIDE;
    public static final /* enum */ Opcode ARRAY_LENGTH;
    public static final int CAN_CONTINUE = 4;
    public static final int CAN_THROW = 1;
    public static final /* enum */ Opcode CHECK_CAST;
    public static final /* enum */ Opcode CMPG_DOUBLE;
    public static final /* enum */ Opcode CMPG_FLOAT;
    public static final /* enum */ Opcode CMPL_DOUBLE;
    public static final /* enum */ Opcode CMPL_FLOAT;
    public static final /* enum */ Opcode CMP_LONG;
    public static final /* enum */ Opcode CONST;
    public static final /* enum */ Opcode CONST_16;
    public static final /* enum */ Opcode CONST_4;
    public static final /* enum */ Opcode CONST_CLASS;
    public static final /* enum */ Opcode CONST_HIGH16;
    public static final /* enum */ Opcode CONST_STRING;
    public static final /* enum */ Opcode CONST_STRING_JUMBO;
    public static final /* enum */ Opcode CONST_WIDE;
    public static final /* enum */ Opcode CONST_WIDE_16;
    public static final /* enum */ Opcode CONST_WIDE_32;
    public static final /* enum */ Opcode CONST_WIDE_HIGH16;
    public static final /* enum */ Opcode DIV_DOUBLE;
    public static final /* enum */ Opcode DIV_DOUBLE_2ADDR;
    public static final /* enum */ Opcode DIV_FLOAT;
    public static final /* enum */ Opcode DIV_FLOAT_2ADDR;
    public static final /* enum */ Opcode DIV_INT;
    public static final /* enum */ Opcode DIV_INT_2ADDR;
    public static final /* enum */ Opcode DIV_INT_LIT16;
    public static final /* enum */ Opcode DIV_INT_LIT8;
    public static final /* enum */ Opcode DIV_LONG;
    public static final /* enum */ Opcode DIV_LONG_2ADDR;
    public static final /* enum */ Opcode DOUBLE_TO_FLOAT;
    public static final /* enum */ Opcode DOUBLE_TO_INT;
    public static final /* enum */ Opcode DOUBLE_TO_LONG;
    public static final /* enum */ Opcode EXECUTE_INLINE;
    public static final /* enum */ Opcode EXECUTE_INLINE_RANGE;
    public static final /* enum */ Opcode FILLED_NEW_ARRAY;
    public static final /* enum */ Opcode FILLED_NEW_ARRAY_RANGE;
    public static final /* enum */ Opcode FILL_ARRAY_DATA;
    public static final /* enum */ Opcode FLOAT_TO_DOUBLE;
    public static final /* enum */ Opcode FLOAT_TO_INT;
    public static final /* enum */ Opcode FLOAT_TO_LONG;
    public static final /* enum */ Opcode GOTO;
    public static final /* enum */ Opcode GOTO_16;
    public static final /* enum */ Opcode GOTO_32;
    public static final /* enum */ Opcode IF_EQ;
    public static final /* enum */ Opcode IF_EQZ;
    public static final /* enum */ Opcode IF_GE;
    public static final /* enum */ Opcode IF_GEZ;
    public static final /* enum */ Opcode IF_GT;
    public static final /* enum */ Opcode IF_GTZ;
    public static final /* enum */ Opcode IF_LE;
    public static final /* enum */ Opcode IF_LEZ;
    public static final /* enum */ Opcode IF_LT;
    public static final /* enum */ Opcode IF_LTZ;
    public static final /* enum */ Opcode IF_NE;
    public static final /* enum */ Opcode IF_NEZ;
    public static final /* enum */ Opcode IGET;
    public static final /* enum */ Opcode IGET_BOOLEAN;
    public static final /* enum */ Opcode IGET_BYTE;
    public static final /* enum */ Opcode IGET_CHAR;
    public static final /* enum */ Opcode IGET_OBJECT;
    public static final /* enum */ Opcode IGET_OBJECT_QUICK;
    public static final /* enum */ Opcode IGET_OBJECT_VOLATILE;
    public static final /* enum */ Opcode IGET_QUICK;
    public static final /* enum */ Opcode IGET_SHORT;
    public static final /* enum */ Opcode IGET_VOLATILE;
    public static final /* enum */ Opcode IGET_WIDE;
    public static final /* enum */ Opcode IGET_WIDE_QUICK;
    public static final /* enum */ Opcode IGET_WIDE_VOLATILE;
    public static final /* enum */ Opcode INSTANCE_OF;
    public static final /* enum */ Opcode INT_TO_BYTE;
    public static final /* enum */ Opcode INT_TO_CHAR;
    public static final /* enum */ Opcode INT_TO_DOUBLE;
    public static final /* enum */ Opcode INT_TO_FLOAT;
    public static final /* enum */ Opcode INT_TO_LONG;
    public static final /* enum */ Opcode INT_TO_SHORT;
    public static final /* enum */ Opcode INVOKE_DIRECT;
    public static final /* enum */ Opcode INVOKE_DIRECT_EMPTY;
    public static final /* enum */ Opcode INVOKE_DIRECT_RANGE;
    public static final /* enum */ Opcode INVOKE_INTERFACE;
    public static final /* enum */ Opcode INVOKE_INTERFACE_RANGE;
    public static final /* enum */ Opcode INVOKE_STATIC;
    public static final /* enum */ Opcode INVOKE_STATIC_RANGE;
    public static final /* enum */ Opcode INVOKE_SUPER;
    public static final /* enum */ Opcode INVOKE_SUPER_QUICK;
    public static final /* enum */ Opcode INVOKE_SUPER_QUICK_RANGE;
    public static final /* enum */ Opcode INVOKE_SUPER_RANGE;
    public static final /* enum */ Opcode INVOKE_VIRTUAL;
    public static final /* enum */ Opcode INVOKE_VIRTUAL_QUICK;
    public static final /* enum */ Opcode INVOKE_VIRTUAL_QUICK_RANGE;
    public static final /* enum */ Opcode INVOKE_VIRTUAL_RANGE;
    public static final /* enum */ Opcode IPUT;
    public static final /* enum */ Opcode IPUT_BOOLEAN;
    public static final /* enum */ Opcode IPUT_BYTE;
    public static final /* enum */ Opcode IPUT_CHAR;
    public static final /* enum */ Opcode IPUT_OBJECT;
    public static final /* enum */ Opcode IPUT_OBJECT_QUICK;
    public static final /* enum */ Opcode IPUT_OBJECT_VOLATILE;
    public static final /* enum */ Opcode IPUT_QUICK;
    public static final /* enum */ Opcode IPUT_SHORT;
    public static final /* enum */ Opcode IPUT_VOLATILE;
    public static final /* enum */ Opcode IPUT_WIDE;
    public static final /* enum */ Opcode IPUT_WIDE_QUICK;
    public static final /* enum */ Opcode IPUT_WIDE_VOLATILE;
    public static final /* enum */ Opcode LONG_TO_DOUBLE;
    public static final /* enum */ Opcode LONG_TO_FLOAT;
    public static final /* enum */ Opcode LONG_TO_INT;
    public static final /* enum */ Opcode MONITOR_ENTER;
    public static final /* enum */ Opcode MONITOR_EXIT;
    public static final /* enum */ Opcode MOVE;
    public static final /* enum */ Opcode MOVE_16;
    public static final /* enum */ Opcode MOVE_EXCEPTION;
    public static final /* enum */ Opcode MOVE_FROM16;
    public static final /* enum */ Opcode MOVE_OBJECT;
    public static final /* enum */ Opcode MOVE_OBJECT_16;
    public static final /* enum */ Opcode MOVE_OBJECT_FROM16;
    public static final /* enum */ Opcode MOVE_RESULT;
    public static final /* enum */ Opcode MOVE_RESULT_OBJECT;
    public static final /* enum */ Opcode MOVE_RESULT_WIDE;
    public static final /* enum */ Opcode MOVE_WIDE;
    public static final /* enum */ Opcode MOVE_WIDE_16;
    public static final /* enum */ Opcode MOVE_WIDE_FROM16;
    public static final /* enum */ Opcode MUL_DOUBLE;
    public static final /* enum */ Opcode MUL_DOUBLE_2ADDR;
    public static final /* enum */ Opcode MUL_FLOAT;
    public static final /* enum */ Opcode MUL_FLOAT_2ADDR;
    public static final /* enum */ Opcode MUL_INT;
    public static final /* enum */ Opcode MUL_INT_2ADDR;
    public static final /* enum */ Opcode MUL_INT_LIT16;
    public static final /* enum */ Opcode MUL_INT_LIT8;
    public static final /* enum */ Opcode MUL_LONG;
    public static final /* enum */ Opcode MUL_LONG_2ADDR;
    public static final /* enum */ Opcode NEG_DOUBLE;
    public static final /* enum */ Opcode NEG_FLOAT;
    public static final /* enum */ Opcode NEG_INT;
    public static final /* enum */ Opcode NEG_LONG;
    public static final /* enum */ Opcode NEW_ARRAY;
    public static final /* enum */ Opcode NEW_INSTANCE;
    public static final /* enum */ Opcode NOP;
    public static final /* enum */ Opcode NOT_INT;
    public static final /* enum */ Opcode NOT_LONG;
    public static final int ODEXED_INSTANCE_QUICK = 64;
    public static final int ODEXED_INSTANCE_VOLATILE = 128;
    public static final int ODEXED_STATIC_VOLATILE = 256;
    public static final int ODEX_ONLY = 2;
    public static final /* enum */ Opcode OR_INT;
    public static final /* enum */ Opcode OR_INT_2ADDR;
    public static final /* enum */ Opcode OR_INT_LIT16;
    public static final /* enum */ Opcode OR_INT_LIT8;
    public static final /* enum */ Opcode OR_LONG;
    public static final /* enum */ Opcode OR_LONG_2ADDR;
    public static final /* enum */ Opcode PACKED_SWITCH;
    public static final /* enum */ Opcode REM_DOUBLE;
    public static final /* enum */ Opcode REM_DOUBLE_2ADDR;
    public static final /* enum */ Opcode REM_FLOAT;
    public static final /* enum */ Opcode REM_FLOAT_2ADDR;
    public static final /* enum */ Opcode REM_INT;
    public static final /* enum */ Opcode REM_INT_2ADDR;
    public static final /* enum */ Opcode REM_INT_LIT16;
    public static final /* enum */ Opcode REM_INT_LIT8;
    public static final /* enum */ Opcode REM_LONG;
    public static final /* enum */ Opcode REM_LONG_2ADDR;
    public static final /* enum */ Opcode RETURN;
    public static final /* enum */ Opcode RETURN_OBJECT;
    public static final /* enum */ Opcode RETURN_VOID;
    public static final /* enum */ Opcode RETURN_WIDE;
    public static final /* enum */ Opcode RSUB_INT;
    public static final /* enum */ Opcode RSUB_INT_LIT8;
    public static final int SETS_REGISTER = 16;
    public static final int SETS_RESULT = 8;
    public static final int SETS_WIDE_REGISTER = 32;
    public static final /* enum */ Opcode SGET;
    public static final /* enum */ Opcode SGET_BOOLEAN;
    public static final /* enum */ Opcode SGET_BYTE;
    public static final /* enum */ Opcode SGET_CHAR;
    public static final /* enum */ Opcode SGET_OBJECT;
    public static final /* enum */ Opcode SGET_OBJECT_VOLATILE;
    public static final /* enum */ Opcode SGET_SHORT;
    public static final /* enum */ Opcode SGET_VOLATILE;
    public static final /* enum */ Opcode SGET_WIDE;
    public static final /* enum */ Opcode SGET_WIDE_VOLATILE;
    public static final /* enum */ Opcode SHL_INT;
    public static final /* enum */ Opcode SHL_INT_2ADDR;
    public static final /* enum */ Opcode SHL_INT_LIT8;
    public static final /* enum */ Opcode SHL_LONG;
    public static final /* enum */ Opcode SHL_LONG_2ADDR;
    public static final /* enum */ Opcode SHR_INT;
    public static final /* enum */ Opcode SHR_INT_2ADDR;
    public static final /* enum */ Opcode SHR_INT_LIT8;
    public static final /* enum */ Opcode SHR_LONG;
    public static final /* enum */ Opcode SHR_LONG_2ADDR;
    public static final /* enum */ Opcode SPARSE_SWITCH;
    public static final /* enum */ Opcode SPUT;
    public static final /* enum */ Opcode SPUT_BOOLEAN;
    public static final /* enum */ Opcode SPUT_BYTE;
    public static final /* enum */ Opcode SPUT_CHAR;
    public static final /* enum */ Opcode SPUT_OBJECT;
    public static final /* enum */ Opcode SPUT_OBJECT_VOLATILE;
    public static final /* enum */ Opcode SPUT_SHORT;
    public static final /* enum */ Opcode SPUT_VOLATILE;
    public static final /* enum */ Opcode SPUT_WIDE;
    public static final /* enum */ Opcode SPUT_WIDE_VOLATILE;
    public static final /* enum */ Opcode SUB_DOUBLE;
    public static final /* enum */ Opcode SUB_DOUBLE_2ADDR;
    public static final /* enum */ Opcode SUB_FLOAT;
    public static final /* enum */ Opcode SUB_FLOAT_2ADDR;
    public static final /* enum */ Opcode SUB_INT;
    public static final /* enum */ Opcode SUB_INT_2ADDR;
    public static final /* enum */ Opcode SUB_LONG;
    public static final /* enum */ Opcode SUB_LONG_2ADDR;
    public static final /* enum */ Opcode THROW;
    public static final /* enum */ Opcode USHR_INT;
    public static final /* enum */ Opcode USHR_INT_2ADDR;
    public static final /* enum */ Opcode USHR_INT_LIT8;
    public static final /* enum */ Opcode USHR_LONG;
    public static final /* enum */ Opcode USHR_LONG_2ADDR;
    public static final /* enum */ Opcode XOR_INT;
    public static final /* enum */ Opcode XOR_INT_2ADDR;
    public static final /* enum */ Opcode XOR_INT_LIT16;
    public static final /* enum */ Opcode XOR_INT_LIT8;
    public static final /* enum */ Opcode XOR_LONG;
    public static final /* enum */ Opcode XOR_LONG_2ADDR;
    private static HashMap<Integer, Opcode> opcodesByName;
    private static Opcode[] opcodesByValue;
    public final int flags;
    public final Format format;
    public final String name;
    public final ReferenceType referenceType;
    public final byte value;

    static {
        NOP = new Opcode(0, "nop", ReferenceType.none, Format.Format10x, 4);
        MOVE = new Opcode(1, "move", ReferenceType.none, Format.Format12x, 20);
        MOVE_FROM16 = new Opcode(2, "move/from16", ReferenceType.none, Format.Format22x, 20);
        MOVE_16 = new Opcode(3, "move/16", ReferenceType.none, Format.Format32x, 20);
        MOVE_WIDE = new Opcode(4, "move-wide", ReferenceType.none, Format.Format12x, 52);
        MOVE_WIDE_FROM16 = new Opcode(5, "move-wide/from16", ReferenceType.none, Format.Format22x, 52);
        MOVE_WIDE_16 = new Opcode(6, "move-wide/16", ReferenceType.none, Format.Format32x, 52);
        MOVE_OBJECT = new Opcode(7, "move-object", ReferenceType.none, Format.Format12x, 20);
        MOVE_OBJECT_FROM16 = new Opcode(8, "move-object/from16", ReferenceType.none, Format.Format22x, 20);
        MOVE_OBJECT_16 = new Opcode(9, "move-object/16", ReferenceType.none, Format.Format32x, 20);
        MOVE_RESULT = new Opcode(10, "move-result", ReferenceType.none, Format.Format11x, 20);
        MOVE_RESULT_WIDE = new Opcode(11, "move-result-wide", ReferenceType.none, Format.Format11x, 52);
        MOVE_RESULT_OBJECT = new Opcode(12, "move-result-object", ReferenceType.none, Format.Format11x, 20);
        MOVE_EXCEPTION = new Opcode(13, "move-exception", ReferenceType.none, Format.Format11x, 20);
        RETURN_VOID = new Opcode(14, "return-void", ReferenceType.none, Format.Format10x);
        RETURN = new Opcode(15, "return", ReferenceType.none, Format.Format11x);
        RETURN_WIDE = new Opcode(16, "return-wide", ReferenceType.none, Format.Format11x);
        RETURN_OBJECT = new Opcode(17, "return-object", ReferenceType.none, Format.Format11x);
        CONST_4 = new Opcode(18, "const/4", ReferenceType.none, Format.Format11n, 20);
        CONST_16 = new Opcode(19, "const/16", ReferenceType.none, Format.Format21s, 20);
        CONST = new Opcode(20, "const", ReferenceType.none, Format.Format31i, 20);
        CONST_HIGH16 = new Opcode(21, "const/high16", ReferenceType.none, Format.Format21h, 20);
        CONST_WIDE_16 = new Opcode(22, "const-wide/16", ReferenceType.none, Format.Format21s, 52);
        CONST_WIDE_32 = new Opcode(23, "const-wide/32", ReferenceType.none, Format.Format31i, 52);
        CONST_WIDE = new Opcode(24, "const-wide", ReferenceType.none, Format.Format51l, 52);
        CONST_WIDE_HIGH16 = new Opcode(25, "const-wide/high16", ReferenceType.none, Format.Format21h, 52);
        CONST_STRING = new Opcode(26, "const-string", ReferenceType.string, Format.Format21c, 21);
        CONST_STRING_JUMBO = new Opcode(27, "const-string/jumbo", ReferenceType.string, Format.Format31c, 21);
        CONST_CLASS = new Opcode(28, "const-class", ReferenceType.type, Format.Format21c, 21);
        MONITOR_ENTER = new Opcode(29, "monitor-enter", ReferenceType.none, Format.Format11x, 5);
        MONITOR_EXIT = new Opcode(30, "monitor-exit", ReferenceType.none, Format.Format11x, 5);
        CHECK_CAST = new Opcode(31, "check-cast", ReferenceType.type, Format.Format21c, 21);
        INSTANCE_OF = new Opcode(32, "instance-of", ReferenceType.type, Format.Format22c, 21);
        ARRAY_LENGTH = new Opcode(33, "array-length", ReferenceType.none, Format.Format12x, 21);
        NEW_INSTANCE = new Opcode(34, "new-instance", ReferenceType.type, Format.Format21c, 21);
        NEW_ARRAY = new Opcode(35, "new-array", ReferenceType.type, Format.Format22c, 21);
        FILLED_NEW_ARRAY = new Opcode(36, "filled-new-array", ReferenceType.type, Format.Format35c, 13);
        FILLED_NEW_ARRAY_RANGE = new Opcode(37, "filled-new-array/range", ReferenceType.type, Format.Format3rc, 13);
        FILL_ARRAY_DATA = new Opcode(38, "fill-array-data", ReferenceType.none, Format.Format31t, 4);
        THROW = new Opcode(39, "throw", ReferenceType.none, Format.Format11x, 1);
        GOTO = new Opcode(40, "goto", ReferenceType.none, Format.Format10t);
        GOTO_16 = new Opcode(41, "goto/16", ReferenceType.none, Format.Format20t);
        GOTO_32 = new Opcode(42, "goto/32", ReferenceType.none, Format.Format30t);
        PACKED_SWITCH = new Opcode(43, "packed-switch", ReferenceType.none, Format.Format31t, 4);
        SPARSE_SWITCH = new Opcode(44, "sparse-switch", ReferenceType.none, Format.Format31t, 4);
        CMPL_FLOAT = new Opcode(45, "cmpl-float", ReferenceType.none, Format.Format23x, 20);
        CMPG_FLOAT = new Opcode(46, "cmpg-float", ReferenceType.none, Format.Format23x, 20);
        CMPL_DOUBLE = new Opcode(47, "cmpl-double", ReferenceType.none, Format.Format23x, 20);
        CMPG_DOUBLE = new Opcode(48, "cmpg-double", ReferenceType.none, Format.Format23x, 20);
        CMP_LONG = new Opcode(49, "cmp-long", ReferenceType.none, Format.Format23x, 20);
        IF_EQ = new Opcode(50, "if-eq", ReferenceType.none, Format.Format22t, 4);
        IF_NE = new Opcode(51, "if-ne", ReferenceType.none, Format.Format22t, 4);
        IF_LT = new Opcode(52, "if-lt", ReferenceType.none, Format.Format22t, 4);
        IF_GE = new Opcode(53, "if-ge", ReferenceType.none, Format.Format22t, 4);
        IF_GT = new Opcode(54, "if-gt", ReferenceType.none, Format.Format22t, 4);
        IF_LE = new Opcode(55, "if-le", ReferenceType.none, Format.Format22t, 4);
        IF_EQZ = new Opcode(56, "if-eqz", ReferenceType.none, Format.Format21t, 4);
        IF_NEZ = new Opcode(57, "if-nez", ReferenceType.none, Format.Format21t, 4);
        IF_LTZ = new Opcode(58, "if-ltz", ReferenceType.none, Format.Format21t, 4);
        IF_GEZ = new Opcode(59, "if-gez", ReferenceType.none, Format.Format21t, 4);
        IF_GTZ = new Opcode(60, "if-gtz", ReferenceType.none, Format.Format21t, 4);
        IF_LEZ = new Opcode(61, "if-lez", ReferenceType.none, Format.Format21t, 4);
        AGET = new Opcode(68, "aget", ReferenceType.none, Format.Format23x, 21);
        AGET_WIDE = new Opcode(69, "aget-wide", ReferenceType.none, Format.Format23x, 53);
        AGET_OBJECT = new Opcode(70, "aget-object", ReferenceType.none, Format.Format23x, 21);
        AGET_BOOLEAN = new Opcode(71, "aget-boolean", ReferenceType.none, Format.Format23x, 21);
        AGET_BYTE = new Opcode(72, "aget-byte", ReferenceType.none, Format.Format23x, 21);
        AGET_CHAR = new Opcode(73, "aget-char", ReferenceType.none, Format.Format23x, 21);
        AGET_SHORT = new Opcode(74, "aget-short", ReferenceType.none, Format.Format23x, 21);
        APUT = new Opcode(75, "aput", ReferenceType.none, Format.Format23x, 5);
        APUT_WIDE = new Opcode(76, "aput-wide", ReferenceType.none, Format.Format23x, 5);
        APUT_OBJECT = new Opcode(77, "aput-object", ReferenceType.none, Format.Format23x, 5);
        APUT_BOOLEAN = new Opcode(78, "aput-boolean", ReferenceType.none, Format.Format23x, 5);
        APUT_BYTE = new Opcode(79, "aput-byte", ReferenceType.none, Format.Format23x, 5);
        APUT_CHAR = new Opcode(80, "aput-char", ReferenceType.none, Format.Format23x, 5);
        APUT_SHORT = new Opcode(81, "aput-short", ReferenceType.none, Format.Format23x, 5);
        IGET = new Opcode(82, "iget", ReferenceType.field, Format.Format22c, 21);
        IGET_WIDE = new Opcode(83, "iget-wide", ReferenceType.field, Format.Format22c, 53);
        IGET_OBJECT = new Opcode(84, "iget-object", ReferenceType.field, Format.Format22c, 21);
        IGET_BOOLEAN = new Opcode(85, "iget-boolean", ReferenceType.field, Format.Format22c, 21);
        IGET_BYTE = new Opcode(86, "iget-byte", ReferenceType.field, Format.Format22c, 21);
        IGET_CHAR = new Opcode(87, "iget-char", ReferenceType.field, Format.Format22c, 21);
        IGET_SHORT = new Opcode(88, "iget-short", ReferenceType.field, Format.Format22c, 21);
        IPUT = new Opcode(89, "iput", ReferenceType.field, Format.Format22c, 5);
        IPUT_WIDE = new Opcode(90, "iput-wide", ReferenceType.field, Format.Format22c, 5);
        IPUT_OBJECT = new Opcode(91, "iput-object", ReferenceType.field, Format.Format22c, 5);
        IPUT_BOOLEAN = new Opcode(92, "iput-boolean", ReferenceType.field, Format.Format22c, 5);
        IPUT_BYTE = new Opcode(93, "iput-byte", ReferenceType.field, Format.Format22c, 5);
        IPUT_CHAR = new Opcode(94, "iput-char", ReferenceType.field, Format.Format22c, 5);
        IPUT_SHORT = new Opcode(95, "iput-short", ReferenceType.field, Format.Format22c, 5);
        SGET = new Opcode(96, "sget", ReferenceType.field, Format.Format21c, 21);
        SGET_WIDE = new Opcode(97, "sget-wide", ReferenceType.field, Format.Format21c, 53);
        SGET_OBJECT = new Opcode(98, "sget-object", ReferenceType.field, Format.Format21c, 21);
        SGET_BOOLEAN = new Opcode(99, "sget-boolean", ReferenceType.field, Format.Format21c, 21);
        SGET_BYTE = new Opcode(100, "sget-byte", ReferenceType.field, Format.Format21c, 21);
        SGET_CHAR = new Opcode(101, "sget-char", ReferenceType.field, Format.Format21c, 21);
        SGET_SHORT = new Opcode(102, "sget-short", ReferenceType.field, Format.Format21c, 21);
        SPUT = new Opcode(103, "sput", ReferenceType.field, Format.Format21c, 5);
        SPUT_WIDE = new Opcode(104, "sput-wide", ReferenceType.field, Format.Format21c, 5);
        SPUT_OBJECT = new Opcode(105, "sput-object", ReferenceType.field, Format.Format21c, 5);
        SPUT_BOOLEAN = new Opcode(106, "sput-boolean", ReferenceType.field, Format.Format21c, 5);
        SPUT_BYTE = new Opcode(107, "sput-byte", ReferenceType.field, Format.Format21c, 5);
        SPUT_CHAR = new Opcode(108, "sput-char", ReferenceType.field, Format.Format21c, 5);
        SPUT_SHORT = new Opcode(109, "sput-short", ReferenceType.field, Format.Format21c, 5);
        INVOKE_VIRTUAL = new Opcode(110, "invoke-virtual", ReferenceType.method, Format.Format35c, 13);
        INVOKE_SUPER = new Opcode(111, "invoke-super", ReferenceType.method, Format.Format35c, 13);
        INVOKE_DIRECT = new Opcode(112, "invoke-direct", ReferenceType.method, Format.Format35c, 13);
        INVOKE_STATIC = new Opcode(113, "invoke-static", ReferenceType.method, Format.Format35c, 13);
        INVOKE_INTERFACE = new Opcode(114, "invoke-interface", ReferenceType.method, Format.Format35c, 13);
        INVOKE_VIRTUAL_RANGE = new Opcode(116, "invoke-virtual/range", ReferenceType.method, Format.Format3rc, 13);
        INVOKE_SUPER_RANGE = new Opcode(117, "invoke-super/range", ReferenceType.method, Format.Format3rc, 13);
        INVOKE_DIRECT_RANGE = new Opcode(118, "invoke-direct/range", ReferenceType.method, Format.Format3rc, 13);
        INVOKE_STATIC_RANGE = new Opcode(119, "invoke-static/range", ReferenceType.method, Format.Format3rc, 13);
        INVOKE_INTERFACE_RANGE = new Opcode(120, "invoke-interface/range", ReferenceType.method, Format.Format3rc, 13);
        NEG_INT = new Opcode(123, "neg-int", ReferenceType.none, Format.Format12x, 20);
        NOT_INT = new Opcode(124, "not-int", ReferenceType.none, Format.Format12x, 20);
        NEG_LONG = new Opcode(125, "neg-long", ReferenceType.none, Format.Format12x, 52);
        NOT_LONG = new Opcode(126, "not-long", ReferenceType.none, Format.Format12x, 52);
        NEG_FLOAT = new Opcode(127, "neg-float", ReferenceType.none, Format.Format12x, 20);
        NEG_DOUBLE = new Opcode(-128, "neg-double", ReferenceType.none, Format.Format12x, 52);
        INT_TO_LONG = new Opcode(-127, "int-to-long", ReferenceType.none, Format.Format12x, 52);
        INT_TO_FLOAT = new Opcode(-126, "int-to-float", ReferenceType.none, Format.Format12x, 20);
        INT_TO_DOUBLE = new Opcode(-125, "int-to-double", ReferenceType.none, Format.Format12x, 52);
        LONG_TO_INT = new Opcode(-124, "long-to-int", ReferenceType.none, Format.Format12x, 20);
        LONG_TO_FLOAT = new Opcode(-123, "long-to-float", ReferenceType.none, Format.Format12x, 20);
        LONG_TO_DOUBLE = new Opcode(-122, "long-to-double", ReferenceType.none, Format.Format12x, 52);
        FLOAT_TO_INT = new Opcode(-121, "float-to-int", ReferenceType.none, Format.Format12x, 20);
        FLOAT_TO_LONG = new Opcode(-120, "float-to-long", ReferenceType.none, Format.Format12x, 52);
        FLOAT_TO_DOUBLE = new Opcode(-119, "float-to-double", ReferenceType.none, Format.Format12x, 52);
        DOUBLE_TO_INT = new Opcode(-118, "double-to-int", ReferenceType.none, Format.Format12x, 20);
        DOUBLE_TO_LONG = new Opcode(-117, "double-to-long", ReferenceType.none, Format.Format12x, 52);
        DOUBLE_TO_FLOAT = new Opcode(-116, "double-to-float", ReferenceType.none, Format.Format12x, 20);
        INT_TO_BYTE = new Opcode(-115, "int-to-byte", ReferenceType.none, Format.Format12x, 20);
        INT_TO_CHAR = new Opcode(-114, "int-to-char", ReferenceType.none, Format.Format12x, 20);
        INT_TO_SHORT = new Opcode(-113, "int-to-short", ReferenceType.none, Format.Format12x, 20);
        ADD_INT = new Opcode(-112, "add-int", ReferenceType.none, Format.Format23x, 20);
        SUB_INT = new Opcode(-111, "sub-int", ReferenceType.none, Format.Format23x, 20);
        MUL_INT = new Opcode(-110, "mul-int", ReferenceType.none, Format.Format23x, 20);
        DIV_INT = new Opcode(-109, "div-int", ReferenceType.none, Format.Format23x, 21);
        REM_INT = new Opcode(-108, "rem-int", ReferenceType.none, Format.Format23x, 21);
        AND_INT = new Opcode(-107, "and-int", ReferenceType.none, Format.Format23x, 20);
        OR_INT = new Opcode(-106, "or-int", ReferenceType.none, Format.Format23x, 20);
        XOR_INT = new Opcode(-105, "xor-int", ReferenceType.none, Format.Format23x, 20);
        SHL_INT = new Opcode(-104, "shl-int", ReferenceType.none, Format.Format23x, 20);
        SHR_INT = new Opcode(-103, "shr-int", ReferenceType.none, Format.Format23x, 20);
        USHR_INT = new Opcode(-102, "ushr-int", ReferenceType.none, Format.Format23x, 20);
        ADD_LONG = new Opcode(-101, "add-long", ReferenceType.none, Format.Format23x, 52);
        SUB_LONG = new Opcode(-100, "sub-long", ReferenceType.none, Format.Format23x, 52);
        MUL_LONG = new Opcode(-99, "mul-long", ReferenceType.none, Format.Format23x, 52);
        DIV_LONG = new Opcode(-98, "div-long", ReferenceType.none, Format.Format23x, 53);
        REM_LONG = new Opcode(-97, "rem-long", ReferenceType.none, Format.Format23x, 53);
        AND_LONG = new Opcode(-96, "and-long", ReferenceType.none, Format.Format23x, 52);
        OR_LONG = new Opcode(-95, "or-long", ReferenceType.none, Format.Format23x, 52);
        XOR_LONG = new Opcode(-94, "xor-long", ReferenceType.none, Format.Format23x, 52);
        SHL_LONG = new Opcode(-93, "shl-long", ReferenceType.none, Format.Format23x, 52);
        SHR_LONG = new Opcode(-92, "shr-long", ReferenceType.none, Format.Format23x, 52);
        USHR_LONG = new Opcode(-91, "ushr-long", ReferenceType.none, Format.Format23x, 52);
        ADD_FLOAT = new Opcode(-90, "add-float", ReferenceType.none, Format.Format23x, 20);
        SUB_FLOAT = new Opcode(-89, "sub-float", ReferenceType.none, Format.Format23x, 20);
        MUL_FLOAT = new Opcode(-88, "mul-float", ReferenceType.none, Format.Format23x, 20);
        DIV_FLOAT = new Opcode(-87, "div-float", ReferenceType.none, Format.Format23x, 20);
        REM_FLOAT = new Opcode(-86, "rem-float", ReferenceType.none, Format.Format23x, 20);
        ADD_DOUBLE = new Opcode(-85, "add-double", ReferenceType.none, Format.Format23x, 52);
        SUB_DOUBLE = new Opcode(-84, "sub-double", ReferenceType.none, Format.Format23x, 52);
        MUL_DOUBLE = new Opcode(-83, "mul-double", ReferenceType.none, Format.Format23x, 52);
        DIV_DOUBLE = new Opcode(-82, "div-double", ReferenceType.none, Format.Format23x, 52);
        REM_DOUBLE = new Opcode(-81, "rem-double", ReferenceType.none, Format.Format23x, 52);
        ADD_INT_2ADDR = new Opcode(-80, "add-int/2addr", ReferenceType.none, Format.Format12x, 20);
        SUB_INT_2ADDR = new Opcode(-79, "sub-int/2addr", ReferenceType.none, Format.Format12x, 20);
        MUL_INT_2ADDR = new Opcode(-78, "mul-int/2addr", ReferenceType.none, Format.Format12x, 20);
        DIV_INT_2ADDR = new Opcode(-77, "div-int/2addr", ReferenceType.none, Format.Format12x, 21);
        REM_INT_2ADDR = new Opcode(-76, "rem-int/2addr", ReferenceType.none, Format.Format12x, 21);
        AND_INT_2ADDR = new Opcode(-75, "and-int/2addr", ReferenceType.none, Format.Format12x, 20);
        OR_INT_2ADDR = new Opcode(-74, "or-int/2addr", ReferenceType.none, Format.Format12x, 20);
        XOR_INT_2ADDR = new Opcode(-73, "xor-int/2addr", ReferenceType.none, Format.Format12x, 20);
        SHL_INT_2ADDR = new Opcode(-72, "shl-int/2addr", ReferenceType.none, Format.Format12x, 20);
        SHR_INT_2ADDR = new Opcode(-71, "shr-int/2addr", ReferenceType.none, Format.Format12x, 20);
        USHR_INT_2ADDR = new Opcode(-70, "ushr-int/2addr", ReferenceType.none, Format.Format12x, 20);
        ADD_LONG_2ADDR = new Opcode(-69, "add-long/2addr", ReferenceType.none, Format.Format12x, 52);
        SUB_LONG_2ADDR = new Opcode(-68, "sub-long/2addr", ReferenceType.none, Format.Format12x, 52);
        MUL_LONG_2ADDR = new Opcode(-67, "mul-long/2addr", ReferenceType.none, Format.Format12x, 52);
        DIV_LONG_2ADDR = new Opcode(-66, "div-long/2addr", ReferenceType.none, Format.Format12x, 53);
        REM_LONG_2ADDR = new Opcode(-65, "rem-long/2addr", ReferenceType.none, Format.Format12x, 53);
        AND_LONG_2ADDR = new Opcode(-64, "and-long/2addr", ReferenceType.none, Format.Format12x, 52);
        OR_LONG_2ADDR = new Opcode(-63, "or-long/2addr", ReferenceType.none, Format.Format12x, 52);
        XOR_LONG_2ADDR = new Opcode(-62, "xor-long/2addr", ReferenceType.none, Format.Format12x, 52);
        SHL_LONG_2ADDR = new Opcode(-61, "shl-long/2addr", ReferenceType.none, Format.Format12x, 52);
        SHR_LONG_2ADDR = new Opcode(-60, "shr-long/2addr", ReferenceType.none, Format.Format12x, 52);
        USHR_LONG_2ADDR = new Opcode(-59, "ushr-long/2addr", ReferenceType.none, Format.Format12x, 52);
        ADD_FLOAT_2ADDR = new Opcode(-58, "add-float/2addr", ReferenceType.none, Format.Format12x, 20);
        SUB_FLOAT_2ADDR = new Opcode(-57, "sub-float/2addr", ReferenceType.none, Format.Format12x, 20);
        MUL_FLOAT_2ADDR = new Opcode(-56, "mul-float/2addr", ReferenceType.none, Format.Format12x, 20);
        DIV_FLOAT_2ADDR = new Opcode(-55, "div-float/2addr", ReferenceType.none, Format.Format12x, 20);
        REM_FLOAT_2ADDR = new Opcode(-54, "rem-float/2addr", ReferenceType.none, Format.Format12x, 20);
        ADD_DOUBLE_2ADDR = new Opcode(-53, "add-double/2addr", ReferenceType.none, Format.Format12x, 52);
        SUB_DOUBLE_2ADDR = new Opcode(-52, "sub-double/2addr", ReferenceType.none, Format.Format12x, 52);
        MUL_DOUBLE_2ADDR = new Opcode(-51, "mul-double/2addr", ReferenceType.none, Format.Format12x, 52);
        DIV_DOUBLE_2ADDR = new Opcode(-50, "div-double/2addr", ReferenceType.none, Format.Format12x, 52);
        REM_DOUBLE_2ADDR = new Opcode(-49, "rem-double/2addr", ReferenceType.none, Format.Format12x, 52);
        ADD_INT_LIT16 = new Opcode(-48, "add-int/lit16", ReferenceType.none, Format.Format22s, 20);
        RSUB_INT = new Opcode(-47, "rsub-int", ReferenceType.none, Format.Format22s, 20);
        MUL_INT_LIT16 = new Opcode(-46, "mul-int/lit16", ReferenceType.none, Format.Format22s, 20);
        DIV_INT_LIT16 = new Opcode(-45, "div-int/lit16", ReferenceType.none, Format.Format22s, 21);
        REM_INT_LIT16 = new Opcode(-44, "rem-int/lit16", ReferenceType.none, Format.Format22s, 21);
        AND_INT_LIT16 = new Opcode(-43, "and-int/lit16", ReferenceType.none, Format.Format22s, 20);
        OR_INT_LIT16 = new Opcode(-42, "or-int/lit16", ReferenceType.none, Format.Format22s, 20);
        XOR_INT_LIT16 = new Opcode(-41, "xor-int/lit16", ReferenceType.none, Format.Format22s, 20);
        ADD_INT_LIT8 = new Opcode(-40, "add-int/lit8", ReferenceType.none, Format.Format22b, 20);
        RSUB_INT_LIT8 = new Opcode(-39, "rsub-int/lit8", ReferenceType.none, Format.Format22b, 20);
        MUL_INT_LIT8 = new Opcode(-38, "mul-int/lit8", ReferenceType.none, Format.Format22b, 20);
        DIV_INT_LIT8 = new Opcode(-37, "div-int/lit8", ReferenceType.none, Format.Format22b, 21);
        REM_INT_LIT8 = new Opcode(-36, "rem-int/lit8", ReferenceType.none, Format.Format22b, 21);
        AND_INT_LIT8 = new Opcode(-35, "and-int/lit8", ReferenceType.none, Format.Format22b, 20);
        OR_INT_LIT8 = new Opcode(-34, "or-int/lit8", ReferenceType.none, Format.Format22b, 20);
        XOR_INT_LIT8 = new Opcode(-33, "xor-int/lit8", ReferenceType.none, Format.Format22b, 20);
        SHL_INT_LIT8 = new Opcode(-32, "shl-int/lit8", ReferenceType.none, Format.Format22b, 20);
        SHR_INT_LIT8 = new Opcode(-31, "shr-int/lit8", ReferenceType.none, Format.Format22b, 20);
        USHR_INT_LIT8 = new Opcode(-30, "ushr-int/lit8", ReferenceType.none, Format.Format22b, 20);
        IGET_VOLATILE = new Opcode(-29, "iget-volatile", ReferenceType.field, Format.Format22c, 151);
        IPUT_VOLATILE = new Opcode(-28, "iput-volatile", ReferenceType.field, Format.Format22c, 135);
        SGET_VOLATILE = new Opcode(-27, "sget-volatile", ReferenceType.field, Format.Format21c, 279);
        SPUT_VOLATILE = new Opcode(-26, "sput-volatile", ReferenceType.field, Format.Format21c, 263);
        IGET_OBJECT_VOLATILE = new Opcode(-25, "iget-object-volatile", ReferenceType.field, Format.Format22c, 151);
        IGET_WIDE_VOLATILE = new Opcode(-24, "iget-wide-volatile", ReferenceType.field, Format.Format22c, 183);
        IPUT_WIDE_VOLATILE = new Opcode(-23, "iput-wide-volatile", ReferenceType.field, Format.Format22c, 135);
        SGET_WIDE_VOLATILE = new Opcode(-22, "sget-wide-volatile", ReferenceType.field, Format.Format21c, 311);
        SPUT_WIDE_VOLATILE = new Opcode(-21, "sput-wide-volatile", ReferenceType.field, Format.Format21c, 263);
        EXECUTE_INLINE = new Opcode(-18, "execute-inline", ReferenceType.none, Format.Format35ms, 15);
        EXECUTE_INLINE_RANGE = new Opcode(-17, "execute-inline/range", ReferenceType.none, Format.Format3rms, 15);
        INVOKE_DIRECT_EMPTY = new Opcode(-16, "invoke-direct-empty", ReferenceType.method, Format.Format35s, 15);
        IGET_QUICK = new Opcode(-14, "iget-quick", ReferenceType.none, Format.Format22cs, 87);
        IGET_WIDE_QUICK = new Opcode(-13, "iget-wide-quick", ReferenceType.none, Format.Format22cs, 119);
        IGET_OBJECT_QUICK = new Opcode(-12, "iget-object-quick", ReferenceType.none, Format.Format22cs, 87);
        IPUT_QUICK = new Opcode(-11, "iput-quick", ReferenceType.none, Format.Format22cs, 71);
        IPUT_WIDE_QUICK = new Opcode(-10, "iput-wide-quick", ReferenceType.none, Format.Format22cs, 71);
        IPUT_OBJECT_QUICK = new Opcode(-9, "iput-object-quick", ReferenceType.none, Format.Format22cs, 71);
        INVOKE_VIRTUAL_QUICK = new Opcode(-8, "invoke-virtual-quick", ReferenceType.none, Format.Format35ms, 15);
        INVOKE_VIRTUAL_QUICK_RANGE = new Opcode(-7, "invoke-virtual-quick/range", ReferenceType.none, Format.Format3rms, 15);
        INVOKE_SUPER_QUICK = new Opcode(-6, "invoke-super-quick", ReferenceType.none, Format.Format35ms, 15);
        INVOKE_SUPER_QUICK_RANGE = new Opcode(-5, "invoke-super-quick/range", ReferenceType.none, Format.Format3rms, 15);
        IPUT_OBJECT_VOLATILE = new Opcode(-4, "iput-object-volatile", ReferenceType.field, Format.Format22c, 135);
        SGET_OBJECT_VOLATILE = new Opcode(-3, "sget-object-volatile", ReferenceType.field, Format.Format21c, 279);
        SPUT_OBJECT_VOLATILE = new Opcode(-2, "sput-object-volatile", ReferenceType.field, Format.Format21c, 263);
        Opcode[] arropcode = new Opcode[]{NOP, MOVE, MOVE_FROM16, MOVE_16, MOVE_WIDE, MOVE_WIDE_FROM16, MOVE_WIDE_16, MOVE_OBJECT, MOVE_OBJECT_FROM16, MOVE_OBJECT_16, MOVE_RESULT, MOVE_RESULT_WIDE, MOVE_RESULT_OBJECT, MOVE_EXCEPTION, RETURN_VOID, RETURN, RETURN_WIDE, RETURN_OBJECT, CONST_4, CONST_16, CONST, CONST_HIGH16, CONST_WIDE_16, CONST_WIDE_32, CONST_WIDE, CONST_WIDE_HIGH16, CONST_STRING, CONST_STRING_JUMBO, CONST_CLASS, MONITOR_ENTER, MONITOR_EXIT, CHECK_CAST, INSTANCE_OF, ARRAY_LENGTH, NEW_INSTANCE, NEW_ARRAY, FILLED_NEW_ARRAY, FILLED_NEW_ARRAY_RANGE, FILL_ARRAY_DATA, THROW, GOTO, GOTO_16, GOTO_32, PACKED_SWITCH, SPARSE_SWITCH, CMPL_FLOAT, CMPG_FLOAT, CMPL_DOUBLE, CMPG_DOUBLE, CMP_LONG, IF_EQ, IF_NE, IF_LT, IF_GE, IF_GT, IF_LE, IF_EQZ, IF_NEZ, IF_LTZ, IF_GEZ, IF_GTZ, IF_LEZ, AGET, AGET_WIDE, AGET_OBJECT, AGET_BOOLEAN, AGET_BYTE, AGET_CHAR, AGET_SHORT, APUT, APUT_WIDE, APUT_OBJECT, APUT_BOOLEAN, APUT_BYTE, APUT_CHAR, APUT_SHORT, IGET, IGET_WIDE, IGET_OBJECT, IGET_BOOLEAN, IGET_BYTE, IGET_CHAR, IGET_SHORT, IPUT, IPUT_WIDE, IPUT_OBJECT, IPUT_BOOLEAN, IPUT_BYTE, IPUT_CHAR, IPUT_SHORT, SGET, SGET_WIDE, SGET_OBJECT, SGET_BOOLEAN, SGET_BYTE, SGET_CHAR, SGET_SHORT, SPUT, SPUT_WIDE, SPUT_OBJECT, SPUT_BOOLEAN, SPUT_BYTE, SPUT_CHAR, SPUT_SHORT, INVOKE_VIRTUAL, INVOKE_SUPER, INVOKE_DIRECT, INVOKE_STATIC, INVOKE_INTERFACE, INVOKE_VIRTUAL_RANGE, INVOKE_SUPER_RANGE, INVOKE_DIRECT_RANGE, INVOKE_STATIC_RANGE, INVOKE_INTERFACE_RANGE, NEG_INT, NOT_INT, NEG_LONG, NOT_LONG, NEG_FLOAT, NEG_DOUBLE, INT_TO_LONG, INT_TO_FLOAT, INT_TO_DOUBLE, LONG_TO_INT, LONG_TO_FLOAT, LONG_TO_DOUBLE, FLOAT_TO_INT, FLOAT_TO_LONG, FLOAT_TO_DOUBLE, DOUBLE_TO_INT, DOUBLE_TO_LONG, DOUBLE_TO_FLOAT, INT_TO_BYTE, INT_TO_CHAR, INT_TO_SHORT, ADD_INT, SUB_INT, MUL_INT, DIV_INT, REM_INT, AND_INT, OR_INT, XOR_INT, SHL_INT, SHR_INT, USHR_INT, ADD_LONG, SUB_LONG, MUL_LONG, DIV_LONG, REM_LONG, AND_LONG, OR_LONG, XOR_LONG, SHL_LONG, SHR_LONG, USHR_LONG, ADD_FLOAT, SUB_FLOAT, MUL_FLOAT, DIV_FLOAT, REM_FLOAT, ADD_DOUBLE, SUB_DOUBLE, MUL_DOUBLE, DIV_DOUBLE, REM_DOUBLE, ADD_INT_2ADDR, SUB_INT_2ADDR, MUL_INT_2ADDR, DIV_INT_2ADDR, REM_INT_2ADDR, AND_INT_2ADDR, OR_INT_2ADDR, XOR_INT_2ADDR, SHL_INT_2ADDR, SHR_INT_2ADDR, USHR_INT_2ADDR, ADD_LONG_2ADDR, SUB_LONG_2ADDR, MUL_LONG_2ADDR, DIV_LONG_2ADDR, REM_LONG_2ADDR, AND_LONG_2ADDR, OR_LONG_2ADDR, XOR_LONG_2ADDR, SHL_LONG_2ADDR, SHR_LONG_2ADDR, USHR_LONG_2ADDR, ADD_FLOAT_2ADDR, SUB_FLOAT_2ADDR, MUL_FLOAT_2ADDR, DIV_FLOAT_2ADDR, REM_FLOAT_2ADDR, ADD_DOUBLE_2ADDR, SUB_DOUBLE_2ADDR, MUL_DOUBLE_2ADDR, DIV_DOUBLE_2ADDR, REM_DOUBLE_2ADDR, ADD_INT_LIT16, RSUB_INT, MUL_INT_LIT16, DIV_INT_LIT16, REM_INT_LIT16, AND_INT_LIT16, OR_INT_LIT16, XOR_INT_LIT16, ADD_INT_LIT8, RSUB_INT_LIT8, MUL_INT_LIT8, DIV_INT_LIT8, REM_INT_LIT8, AND_INT_LIT8, OR_INT_LIT8, XOR_INT_LIT8, SHL_INT_LIT8, SHR_INT_LIT8, USHR_INT_LIT8, IGET_VOLATILE, IPUT_VOLATILE, SGET_VOLATILE, SPUT_VOLATILE, IGET_OBJECT_VOLATILE, IGET_WIDE_VOLATILE, IPUT_WIDE_VOLATILE, SGET_WIDE_VOLATILE, SPUT_WIDE_VOLATILE, EXECUTE_INLINE, EXECUTE_INLINE_RANGE, INVOKE_DIRECT_EMPTY, IGET_QUICK, IGET_WIDE_QUICK, IGET_OBJECT_QUICK, IPUT_QUICK, IPUT_WIDE_QUICK, IPUT_OBJECT_QUICK, INVOKE_VIRTUAL_QUICK, INVOKE_VIRTUAL_QUICK_RANGE, INVOKE_SUPER_QUICK, INVOKE_SUPER_QUICK_RANGE, IPUT_OBJECT_VOLATILE, SGET_OBJECT_VOLATILE, SPUT_OBJECT_VOLATILE};
        $VALUES = arropcode;
        opcodesByValue = new Opcode[256];
        opcodesByName = new HashMap();
        Opcode[] arropcode2 = Opcode.values();
        int n = arropcode2.length;
        for (int i = 0; i < n; ++i) {
            Opcode opcode;
            Opcode.opcodesByValue[255 & opcode.value] = opcode = arropcode2[i];
            opcodesByName.put((Object)opcode.name.hashCode(), (Object)opcode);
        }
    }

    private Opcode(byte by, String string3, ReferenceType referenceType, Format format) {
        this(by, string3, referenceType, format, 0);
    }

    private Opcode(byte by, String string3, ReferenceType referenceType, Format format, int n2) {
        this.value = by;
        this.name = string3;
        this.referenceType = referenceType;
        this.format = format;
        this.flags = n2;
    }

    public static Opcode getOpcodeByName(String string2) {
        return (Opcode)((Object)opcodesByName.get((Object)string2.toLowerCase().hashCode()));
    }

    public static Opcode getOpcodeByValue(byte by) {
        return opcodesByValue[by & 255];
    }

    public static Opcode valueOf(String string2) {
        return (Opcode)Enum.valueOf(Opcode.class, (String)string2);
    }

    public static Opcode[] values() {
        return (Opcode[])$VALUES.clone();
    }

    public final boolean canContinue() {
        return (4 & this.flags) != 0;
    }

    public final boolean canThrow() {
        return (1 & this.flags) != 0;
    }

    public final boolean isOdexedInstanceQuick() {
        return (64 & this.flags) != 0;
    }

    public final boolean isOdexedInstanceVolatile() {
        return (128 & this.flags) != 0;
    }

    public final boolean isOdexedStaticVolatile() {
        return (256 & this.flags) != 0;
    }

    public final boolean odexOnly() {
        return (2 & this.flags) != 0;
    }

    public final boolean setsRegister() {
        return (16 & this.flags) != 0;
    }

    public final boolean setsResult() {
        return (8 & this.flags) != 0;
    }

    public final boolean setsWideRegister() {
        return (32 & this.flags) != 0;
    }
}

