/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.BitSet
 *  java.util.EnumSet
 *  java.util.Iterator
 *  java.util.LinkedList
 *  java.util.List
 *  org.jf.dexlib.Code.Format.Instruction3rc
 *  org.jf.dexlib.Code.ThreeRegisterInstruction
 */
package org.jf.dexlib.Code.Analysis;

import java.util.BitSet;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.Code.Analysis.AnalyzedInstruction;
import org.jf.dexlib.Code.Analysis.ClassPath;
import org.jf.dexlib.Code.Analysis.DeodexUtil;
import org.jf.dexlib.Code.Analysis.OdexedFieldInstructionMapper;
import org.jf.dexlib.Code.Analysis.RegisterType;
import org.jf.dexlib.Code.Analysis.ValidationException;
import org.jf.dexlib.Code.FiveRegisterInstruction;
import org.jf.dexlib.Code.Format.ArrayDataPseudoInstruction;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Format.Instruction21c;
import org.jf.dexlib.Code.Format.Instruction22c;
import org.jf.dexlib.Code.Format.Instruction22cs;
import org.jf.dexlib.Code.Format.Instruction35c;
import org.jf.dexlib.Code.Format.Instruction35ms;
import org.jf.dexlib.Code.Format.Instruction35s;
import org.jf.dexlib.Code.Format.Instruction3rc;
import org.jf.dexlib.Code.Format.Instruction3rms;
import org.jf.dexlib.Code.Format.UnresolvedOdexInstruction;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.LiteralInstruction;
import org.jf.dexlib.Code.MultiOffsetInstruction;
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.RegisterRangeInstruction;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.Code.ThreeRegisterInstruction;
import org.jf.dexlib.Code.TwoRegisterInstruction;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.SparseArray;

public class MethodAnalyzer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int ANALYZED = 1;
    private static final EnumSet<RegisterType.Category> BooleanCategories;
    private static final int INVOKE_DIRECT = 4;
    private static final int INVOKE_INTERFACE = 8;
    private static final int INVOKE_STATIC = 16;
    private static final int INVOKE_SUPER = 2;
    private static final int INVOKE_VIRTUAL = 1;
    private static final int NOT_ANALYZED = 0;
    private static final EnumSet<RegisterType.Category> Primitive32BitCategories;
    private static final EnumSet<RegisterType.Category> ReferenceAndPrimitive32BitCategories;
    private static final EnumSet<RegisterType.Category> ReferenceCategories;
    private static final EnumSet<RegisterType.Category> ReferenceOrUninitCategories;
    private static final EnumSet<RegisterType.Category> ReferenceOrUninitThisCategories;
    private static final int VERIFIED = 2;
    private static final EnumSet<RegisterType.Category> WideHighCategories;
    private static final EnumSet<RegisterType.Category> WideLowCategories;
    private BitSet analyzedInstructions;
    private int analyzerState = 0;
    private final DeodexUtil deodexUtil;
    private final ClassDataItem.EncodedMethod encodedMethod;
    private SparseArray<AnalyzedInstruction> instructions;
    private AnalyzedInstruction startOfMethod;
    private ValidationException validationException = null;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !MethodAnalyzer.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        RegisterType.Category category = RegisterType.Category.Null;
        Enum[] arrenum = new RegisterType.Category[]{RegisterType.Category.One, RegisterType.Category.Boolean, RegisterType.Category.Byte, RegisterType.Category.PosByte, RegisterType.Category.Short, RegisterType.Category.PosShort, RegisterType.Category.Char, RegisterType.Category.Integer, RegisterType.Category.Float};
        Primitive32BitCategories = EnumSet.of((Enum)category, (Enum[])arrenum);
        WideLowCategories = EnumSet.of((Enum)RegisterType.Category.LongLo, (Enum)RegisterType.Category.DoubleLo);
        WideHighCategories = EnumSet.of((Enum)RegisterType.Category.LongHi, (Enum)RegisterType.Category.DoubleHi);
        ReferenceCategories = EnumSet.of((Enum)RegisterType.Category.Null, (Enum)RegisterType.Category.Reference);
        ReferenceOrUninitThisCategories = EnumSet.of((Enum)RegisterType.Category.Null, (Enum)RegisterType.Category.UninitThis, (Enum)RegisterType.Category.Reference);
        ReferenceOrUninitCategories = EnumSet.of((Enum)RegisterType.Category.Null, (Enum)RegisterType.Category.UninitRef, (Enum)RegisterType.Category.UninitThis, (Enum)RegisterType.Category.Reference);
        RegisterType.Category category2 = RegisterType.Category.Null;
        Enum[] arrenum2 = new RegisterType.Category[]{RegisterType.Category.One, RegisterType.Category.Boolean, RegisterType.Category.Byte, RegisterType.Category.PosByte, RegisterType.Category.Short, RegisterType.Category.PosShort, RegisterType.Category.Char, RegisterType.Category.Integer, RegisterType.Category.Float, RegisterType.Category.Reference};
        ReferenceAndPrimitive32BitCategories = EnumSet.of((Enum)category2, (Enum[])arrenum2);
        BooleanCategories = EnumSet.of((Enum)RegisterType.Category.Null, (Enum)RegisterType.Category.One, (Enum)RegisterType.Category.Boolean);
    }

    /*
     * Enabled aggressive block sorting
     */
    public MethodAnalyzer(ClassDataItem.EncodedMethod encodedMethod, boolean bl) {
        if (encodedMethod == null) {
            throw new IllegalArgumentException("encodedMethod cannot be null");
        }
        if (encodedMethod.codeItem == null || encodedMethod.codeItem.getInstructions().length == 0) {
            throw new IllegalArgumentException("The method has no code");
        }
        this.encodedMethod = encodedMethod;
        this.deodexUtil = bl ? new DeodexUtil(encodedMethod.method.getDexFile()) : null;
        this.startOfMethod = new AnalyzedInstruction(null, -1, encodedMethod.codeItem.getRegisterCount()){
            static final /* synthetic */ boolean $assertionsDisabled;

            /*
             * Enabled aggressive block sorting
             */
            static {
                boolean bl = !MethodAnalyzer.class.desiredAssertionStatus();
                $assertionsDisabled = bl;
            }

            @Override
            public int getDestinationRegister() {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                return -1;
            }

            @Override
            public boolean setsRegister() {
                return false;
            }

            @Override
            public boolean setsRegister(int n) {
                return false;
            }

            @Override
            public boolean setsWideRegister() {
                return false;
            }
        };
        MethodAnalyzer.super.buildInstructionList();
        this.analyzedInstructions = new BitSet(this.instructions.size());
    }

    private void addPredecessorSuccessor(AnalyzedInstruction analyzedInstruction, AnalyzedInstruction analyzedInstruction2, AnalyzedInstruction[][] arranalyzedInstruction, BitSet bitSet) {
        MethodAnalyzer.super.addPredecessorSuccessor(analyzedInstruction, analyzedInstruction2, arranalyzedInstruction, bitSet, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addPredecessorSuccessor(AnalyzedInstruction analyzedInstruction, AnalyzedInstruction analyzedInstruction2, AnalyzedInstruction[][] arranalyzedInstruction, BitSet bitSet, boolean bl) {
        if (!bl && analyzedInstruction2.instruction.opcode == Opcode.MOVE_EXCEPTION) {
            throw new ValidationException("Execution can pass from the " + analyzedInstruction.instruction.opcode.name + " instruction at code address 0x" + Integer.toHexString((int)this.getInstructionAddress(analyzedInstruction)) + " to the move-exception instruction at address 0x" + Integer.toHexString((int)this.getInstructionAddress(analyzedInstruction2)));
        }
        if (analyzedInstruction2.addPredecessor(analyzedInstruction)) {
            analyzedInstruction.addSuccessor(analyzedInstruction2);
            bitSet.set(analyzedInstruction2.getInstructionIndex());
            AnalyzedInstruction[] arranalyzedInstruction2 = arranalyzedInstruction[analyzedInstruction2.instructionIndex];
            if (arranalyzedInstruction2 != null) {
                if (!$assertionsDisabled && !analyzedInstruction2.instruction.opcode.canThrow()) {
                    throw new AssertionError();
                }
                int n = arranalyzedInstruction2.length;
                for (int i = 0; i < n; ++i) {
                    this.addPredecessorSuccessor(analyzedInstruction, arranalyzedInstruction2[i], arranalyzedInstruction, bitSet, true);
                }
            }
        }
    }

    private void analyze32BitPrimitiveAget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyze32BitPrimitiveIget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyze32BitPrimitiveSget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyzeAgetObject(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(((ThreeRegisterInstruction)analyzedInstruction.instruction).getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            ClassPath.ClassDef classDef = ((ClassPath.ArrayClassDef)registerType.type).getImmediateElementClass();
            char c = classDef.getClassType().charAt(0);
            if (c != 'L' && c != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
            MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Reference, classDef));
            return;
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Null, null));
    }

    private void analyzeAgetWide(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(((ThreeRegisterInstruction)analyzedInstruction.instruction).getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-wide with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            char c = ((ClassPath.ArrayClassDef)registerType.type).getBaseElementClass().getClassType().charAt(0);
            if (c == 'J') {
                MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.LongLo, null));
                return;
            }
            if (c == 'D') {
                MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.DoubleLo, null));
                return;
            }
            Object[] arrobject = new Object[]{registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot use aget-wide with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.LongLo, null));
    }

    private void analyzeArrayDataOrSwitch(AnalyzedInstruction analyzedInstruction) {
        int n = ((OffsetInstruction)((Object)analyzedInstruction.instruction)).getTargetAddressOffset() + this.getInstructionAddress(analyzedInstruction);
        AnalyzedInstruction analyzedInstruction2 = this.instructions.get(n);
        if (analyzedInstruction2 != null) {
            analyzedInstruction2.dead = false;
            AnalyzedInstruction analyzedInstruction3 = this.instructions.valueAt(-1 + analyzedInstruction2.getInstructionIndex());
            if (analyzedInstruction3.getInstruction().opcode == Opcode.NOP && !analyzedInstruction3.getInstruction().getFormat().variableSizeFormat) {
                analyzedInstruction3.dead = false;
            }
        }
    }

    private void analyzeArrayLength(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Integer, null));
    }

    private void analyzeBinary2AddrOp(AnalyzedInstruction analyzedInstruction, RegisterType.Category category, boolean bl) {
        if (bl) {
            TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
            RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterA());
            RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterB());
            if (BooleanCategories.contains((Object)registerType.category) && BooleanCategories.contains((Object)registerType2.category)) {
                category = RegisterType.Category.Boolean;
            }
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyzeBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType.Category category, boolean bl) {
        if (bl) {
            ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
            RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
            RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterC());
            if (BooleanCategories.contains((Object)registerType.category) && BooleanCategories.contains((Object)registerType2.category)) {
                category = RegisterType.Category.Boolean;
            }
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyzeCheckCast(AnalyzedInstruction analyzedInstruction) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item));
    }

    private void analyzeConst(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForLiteral(((LiteralInstruction)((Object)analyzedInstruction.instruction)).getLiteral()));
    }

    private void analyzeConstClass(AnalyzedInstruction analyzedInstruction) {
        ClassPath.ClassDef classDef = ClassPath.getClassDef("Ljava/lang/Class;");
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Reference, classDef));
    }

    private void analyzeConstHigh16(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Integer, null));
    }

    private void analyzeConstString(AnalyzedInstruction analyzedInstruction) {
        ClassPath.ClassDef classDef = ClassPath.getClassDef("Ljava/lang/String;");
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Reference, classDef));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void analyzeExecuteInline(AnalyzedInstruction analyzedInstruction) {
        Opcode opcode;
        if (this.deodexUtil == null) {
            throw new ValidationException("Cannot analyze an odexed instruction unless we are deodexing");
        }
        Instruction35ms instruction35ms = (Instruction35ms)analyzedInstruction.instruction;
        DeodexUtil.InlineMethod inlineMethod = this.deodexUtil.lookupInlineMethod(analyzedInstruction);
        MethodIdItem methodIdItem = inlineMethod.getMethodIdItem();
        if (methodIdItem == null) {
            Object[] arrobject = new Object[]{instruction35ms.getMethodIndex()};
            throw new ValidationException(String.format((String)"Cannot load inline method with index %d", (Object[])arrobject));
        }
        switch (inlineMethod.methodType) {
            default: {
                boolean bl = $assertionsDisabled;
                opcode = null;
                if (!bl) {
                    throw new AssertionError();
                }
                break;
            }
            case 1: {
                opcode = Opcode.INVOKE_DIRECT;
                break;
            }
            case 2: {
                opcode = Opcode.INVOKE_STATIC;
                break;
            }
            case 0: {
                opcode = Opcode.INVOKE_VIRTUAL;
            }
        }
        analyzedInstruction.setDeodexedInstruction(new Instruction35c(opcode, instruction35ms.getRegCount(), instruction35ms.getRegisterD(), instruction35ms.getRegisterE(), instruction35ms.getRegisterF(), instruction35ms.getRegisterG(), instruction35ms.getRegisterA(), methodIdItem));
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void analyzeExecuteInlineRange(AnalyzedInstruction analyzedInstruction) {
        Opcode opcode;
        if (this.deodexUtil == null) {
            throw new ValidationException("Cannot analyze an odexed instruction unless we are deodexing");
        }
        Instruction3rms instruction3rms = (Instruction3rms)analyzedInstruction.instruction;
        DeodexUtil.InlineMethod inlineMethod = this.deodexUtil.lookupInlineMethod(analyzedInstruction);
        MethodIdItem methodIdItem = inlineMethod.getMethodIdItem();
        if (methodIdItem == null) {
            Object[] arrobject = new Object[]{instruction3rms.getMethodIndex()};
            throw new ValidationException(String.format((String)"Cannot load inline method with index %d", (Object[])arrobject));
        }
        switch (inlineMethod.methodType) {
            default: {
                boolean bl = $assertionsDisabled;
                opcode = null;
                if (!bl) {
                    throw new AssertionError();
                }
                break;
            }
            case 1: {
                opcode = Opcode.INVOKE_DIRECT_RANGE;
                break;
            }
            case 2: {
                opcode = Opcode.INVOKE_STATIC_RANGE;
                break;
            }
            case 0: {
                opcode = Opcode.INVOKE_VIRTUAL_RANGE;
            }
        }
        analyzedInstruction.setDeodexedInstruction((Instruction)new Instruction3rc(opcode, instruction3rms.getRegCount(), instruction3rms.getStartRegister(), (Item)methodIdItem));
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
    }

    private void analyzeFloatWideCmp(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Byte, null));
    }

    private void analyzeIgetWideObject(AnalyzedInstruction analyzedInstruction) {
        (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForTypeIdItem(((FieldIdItem)item).getFieldType()));
    }

    private void analyzeInstanceOf(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.Boolean, null));
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private boolean analyzeInstruction(AnalyzedInstruction analyzedInstruction) {
        Instruction instruction = analyzedInstruction.instruction;
        switch (instruction.opcode) {
            default: {
                if ($assertionsDisabled) return true;
                throw new AssertionError();
            }
            case MOVE: 
            case MOVE_FROM16: 
            case MOVE_16: 
            case MOVE_WIDE: 
            case MOVE_WIDE_FROM16: 
            case MOVE_WIDE_16: 
            case MOVE_OBJECT: 
            case MOVE_OBJECT_FROM16: 
            case MOVE_OBJECT_16: {
                MethodAnalyzer.super.analyzeMove(analyzedInstruction);
            }
            case NOP: 
            case RETURN_VOID: 
            case RETURN: 
            case RETURN_WIDE: 
            case RETURN_OBJECT: 
            case MONITOR_ENTER: 
            case MONITOR_EXIT: 
            case FILLED_NEW_ARRAY: 
            case FILLED_NEW_ARRAY_RANGE: 
            case THROW: 
            case GOTO: 
            case GOTO_16: 
            case GOTO_32: 
            case IF_EQ: 
            case IF_NE: 
            case IF_LT: 
            case IF_GE: 
            case IF_GT: 
            case IF_LE: 
            case IF_EQZ: 
            case IF_NEZ: 
            case IF_LTZ: 
            case IF_GEZ: 
            case IF_GTZ: 
            case IF_LEZ: 
            case APUT: 
            case APUT_BOOLEAN: 
            case APUT_BYTE: 
            case APUT_CHAR: 
            case APUT_SHORT: 
            case APUT_WIDE: 
            case APUT_OBJECT: 
            case IPUT: 
            case IPUT_BOOLEAN: 
            case IPUT_BYTE: 
            case IPUT_CHAR: 
            case IPUT_SHORT: 
            case IPUT_WIDE: 
            case IPUT_OBJECT: 
            case SPUT: 
            case SPUT_BOOLEAN: 
            case SPUT_BYTE: 
            case SPUT_CHAR: 
            case SPUT_SHORT: 
            case SPUT_WIDE: 
            case SPUT_OBJECT: 
            case INVOKE_VIRTUAL: 
            case INVOKE_SUPER: 
            case INVOKE_STATIC: 
            case INVOKE_INTERFACE: 
            case INVOKE_VIRTUAL_RANGE: 
            case INVOKE_SUPER_RANGE: 
            case INVOKE_STATIC_RANGE: 
            case INVOKE_INTERFACE_RANGE: {
                return true;
            }
            case MOVE_RESULT: 
            case MOVE_RESULT_WIDE: 
            case MOVE_RESULT_OBJECT: {
                MethodAnalyzer.super.analyzeMoveResult(analyzedInstruction);
                return true;
            }
            case MOVE_EXCEPTION: {
                MethodAnalyzer.super.analyzeMoveException(analyzedInstruction);
                return true;
            }
            case CONST_4: 
            case CONST_16: 
            case CONST: {
                MethodAnalyzer.super.analyzeConst(analyzedInstruction);
                return true;
            }
            case CONST_HIGH16: {
                MethodAnalyzer.super.analyzeConstHigh16(analyzedInstruction);
                return true;
            }
            case CONST_WIDE_16: 
            case CONST_WIDE_32: 
            case CONST_WIDE: 
            case CONST_WIDE_HIGH16: {
                MethodAnalyzer.super.analyzeWideConst(analyzedInstruction);
                return true;
            }
            case CONST_STRING: 
            case CONST_STRING_JUMBO: {
                MethodAnalyzer.super.analyzeConstString(analyzedInstruction);
                return true;
            }
            case CONST_CLASS: {
                MethodAnalyzer.super.analyzeConstClass(analyzedInstruction);
                return true;
            }
            case CHECK_CAST: {
                MethodAnalyzer.super.analyzeCheckCast(analyzedInstruction);
                return true;
            }
            case INSTANCE_OF: {
                MethodAnalyzer.super.analyzeInstanceOf(analyzedInstruction);
                return true;
            }
            case ARRAY_LENGTH: {
                MethodAnalyzer.super.analyzeArrayLength(analyzedInstruction);
                return true;
            }
            case NEW_INSTANCE: {
                MethodAnalyzer.super.analyzeNewInstance(analyzedInstruction);
                return true;
            }
            case NEW_ARRAY: {
                MethodAnalyzer.super.analyzeNewArray(analyzedInstruction);
                return true;
            }
            case FILL_ARRAY_DATA: {
                MethodAnalyzer.super.analyzeArrayDataOrSwitch(analyzedInstruction);
                return true;
            }
            case PACKED_SWITCH: 
            case SPARSE_SWITCH: {
                MethodAnalyzer.super.analyzeArrayDataOrSwitch(analyzedInstruction);
                return true;
            }
            case CMPL_FLOAT: 
            case CMPG_FLOAT: 
            case CMPL_DOUBLE: 
            case CMPG_DOUBLE: 
            case CMP_LONG: {
                MethodAnalyzer.super.analyzeFloatWideCmp(analyzedInstruction);
                return true;
            }
            case AGET: {
                MethodAnalyzer.super.analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case AGET_BOOLEAN: {
                MethodAnalyzer.super.analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Boolean);
                return true;
            }
            case AGET_BYTE: {
                MethodAnalyzer.super.analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Byte);
                return true;
            }
            case AGET_CHAR: {
                MethodAnalyzer.super.analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Char);
                return true;
            }
            case AGET_SHORT: {
                MethodAnalyzer.super.analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Short);
                return true;
            }
            case AGET_WIDE: {
                MethodAnalyzer.super.analyzeAgetWide(analyzedInstruction);
                return true;
            }
            case AGET_OBJECT: {
                MethodAnalyzer.super.analyzeAgetObject(analyzedInstruction);
                return true;
            }
            case IGET: {
                MethodAnalyzer.super.analyze32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case IGET_BOOLEAN: {
                MethodAnalyzer.super.analyze32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Boolean);
                return true;
            }
            case IGET_BYTE: {
                MethodAnalyzer.super.analyze32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Byte);
                return true;
            }
            case IGET_CHAR: {
                MethodAnalyzer.super.analyze32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Char);
                return true;
            }
            case IGET_SHORT: {
                MethodAnalyzer.super.analyze32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Short);
                return true;
            }
            case IGET_WIDE: 
            case IGET_OBJECT: {
                MethodAnalyzer.super.analyzeIgetWideObject(analyzedInstruction);
                return true;
            }
            case SGET: {
                MethodAnalyzer.super.analyze32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case SGET_BOOLEAN: {
                MethodAnalyzer.super.analyze32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Boolean);
                return true;
            }
            case SGET_BYTE: {
                MethodAnalyzer.super.analyze32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Byte);
                return true;
            }
            case SGET_CHAR: {
                MethodAnalyzer.super.analyze32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Char);
                return true;
            }
            case SGET_SHORT: {
                MethodAnalyzer.super.analyze32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Short);
                return true;
            }
            case SGET_WIDE: 
            case SGET_OBJECT: {
                MethodAnalyzer.super.analyzeSgetWideObject(analyzedInstruction);
                return true;
            }
            case INVOKE_DIRECT: {
                MethodAnalyzer.super.analyzeInvokeDirect(analyzedInstruction);
                return true;
            }
            case INVOKE_DIRECT_RANGE: {
                MethodAnalyzer.super.analyzeInvokeDirectRange(analyzedInstruction);
                return true;
            }
            case NEG_INT: 
            case NOT_INT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case NEG_LONG: 
            case NOT_LONG: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.LongLo);
                return true;
            }
            case NEG_FLOAT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Float);
                return true;
            }
            case NEG_DOUBLE: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.DoubleLo);
                return true;
            }
            case INT_TO_LONG: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.LongLo);
                return true;
            }
            case INT_TO_FLOAT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Float);
                return true;
            }
            case INT_TO_DOUBLE: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.DoubleLo);
                return true;
            }
            case LONG_TO_INT: 
            case DOUBLE_TO_INT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case LONG_TO_FLOAT: 
            case DOUBLE_TO_FLOAT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Float);
                return true;
            }
            case LONG_TO_DOUBLE: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.DoubleLo);
                return true;
            }
            case FLOAT_TO_INT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Integer);
                return true;
            }
            case FLOAT_TO_LONG: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.LongLo);
                return true;
            }
            case FLOAT_TO_DOUBLE: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.DoubleLo);
                return true;
            }
            case DOUBLE_TO_LONG: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.LongLo);
                return true;
            }
            case INT_TO_BYTE: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Byte);
                return true;
            }
            case INT_TO_CHAR: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Char);
                return true;
            }
            case INT_TO_SHORT: {
                MethodAnalyzer.super.analyzeUnaryOp(analyzedInstruction, RegisterType.Category.Short);
                return true;
            }
            case ADD_INT: 
            case SUB_INT: 
            case MUL_INT: 
            case DIV_INT: 
            case REM_INT: 
            case SHL_INT: 
            case SHR_INT: 
            case USHR_INT: {
                MethodAnalyzer.super.analyzeBinaryOp(analyzedInstruction, RegisterType.Category.Integer, false);
                return true;
            }
            case AND_INT: 
            case OR_INT: 
            case XOR_INT: {
                MethodAnalyzer.super.analyzeBinaryOp(analyzedInstruction, RegisterType.Category.Integer, true);
                return true;
            }
            case ADD_LONG: 
            case SUB_LONG: 
            case MUL_LONG: 
            case DIV_LONG: 
            case REM_LONG: 
            case AND_LONG: 
            case OR_LONG: 
            case XOR_LONG: 
            case SHL_LONG: 
            case SHR_LONG: 
            case USHR_LONG: {
                MethodAnalyzer.super.analyzeBinaryOp(analyzedInstruction, RegisterType.Category.LongLo, false);
                return true;
            }
            case ADD_FLOAT: 
            case SUB_FLOAT: 
            case MUL_FLOAT: 
            case DIV_FLOAT: 
            case REM_FLOAT: {
                MethodAnalyzer.super.analyzeBinaryOp(analyzedInstruction, RegisterType.Category.Float, false);
                return true;
            }
            case ADD_DOUBLE: 
            case SUB_DOUBLE: 
            case MUL_DOUBLE: 
            case DIV_DOUBLE: 
            case REM_DOUBLE: {
                MethodAnalyzer.super.analyzeBinaryOp(analyzedInstruction, RegisterType.Category.DoubleLo, false);
                return true;
            }
            case ADD_INT_2ADDR: 
            case SUB_INT_2ADDR: 
            case MUL_INT_2ADDR: 
            case DIV_INT_2ADDR: 
            case REM_INT_2ADDR: 
            case SHL_INT_2ADDR: 
            case SHR_INT_2ADDR: 
            case USHR_INT_2ADDR: {
                MethodAnalyzer.super.analyzeBinary2AddrOp(analyzedInstruction, RegisterType.Category.Integer, false);
                return true;
            }
            case AND_INT_2ADDR: 
            case OR_INT_2ADDR: 
            case XOR_INT_2ADDR: {
                MethodAnalyzer.super.analyzeBinary2AddrOp(analyzedInstruction, RegisterType.Category.Integer, true);
                return true;
            }
            case ADD_LONG_2ADDR: 
            case SUB_LONG_2ADDR: 
            case MUL_LONG_2ADDR: 
            case DIV_LONG_2ADDR: 
            case REM_LONG_2ADDR: 
            case AND_LONG_2ADDR: 
            case OR_LONG_2ADDR: 
            case XOR_LONG_2ADDR: 
            case SHL_LONG_2ADDR: 
            case SHR_LONG_2ADDR: 
            case USHR_LONG_2ADDR: {
                MethodAnalyzer.super.analyzeBinary2AddrOp(analyzedInstruction, RegisterType.Category.LongLo, false);
                return true;
            }
            case ADD_FLOAT_2ADDR: 
            case SUB_FLOAT_2ADDR: 
            case MUL_FLOAT_2ADDR: 
            case DIV_FLOAT_2ADDR: 
            case REM_FLOAT_2ADDR: {
                MethodAnalyzer.super.analyzeBinary2AddrOp(analyzedInstruction, RegisterType.Category.Float, false);
                return true;
            }
            case ADD_DOUBLE_2ADDR: 
            case SUB_DOUBLE_2ADDR: 
            case MUL_DOUBLE_2ADDR: 
            case DIV_DOUBLE_2ADDR: 
            case REM_DOUBLE_2ADDR: {
                MethodAnalyzer.super.analyzeBinary2AddrOp(analyzedInstruction, RegisterType.Category.DoubleLo, false);
                return true;
            }
            case ADD_INT_LIT16: 
            case RSUB_INT: 
            case MUL_INT_LIT16: 
            case DIV_INT_LIT16: 
            case REM_INT_LIT16: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.Category.Integer, false);
                return true;
            }
            case AND_INT_LIT16: 
            case OR_INT_LIT16: 
            case XOR_INT_LIT16: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.Category.Integer, true);
                return true;
            }
            case ADD_INT_LIT8: 
            case RSUB_INT_LIT8: 
            case MUL_INT_LIT8: 
            case DIV_INT_LIT8: 
            case REM_INT_LIT8: 
            case SHL_INT_LIT8: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.Category.Integer, false);
                return true;
            }
            case AND_INT_LIT8: 
            case OR_INT_LIT8: 
            case XOR_INT_LIT8: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.Category.Integer, true);
                return true;
            }
            case SHR_INT_LIT8: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, MethodAnalyzer.super.getDestTypeForLiteralShiftRight(analyzedInstruction, true), false);
                return true;
            }
            case USHR_INT_LIT8: {
                MethodAnalyzer.super.analyzeLiteralBinaryOp(analyzedInstruction, MethodAnalyzer.super.getDestTypeForLiteralShiftRight(analyzedInstruction, false), false);
                return true;
            }
            case IGET_VOLATILE: 
            case IPUT_VOLATILE: 
            case SGET_VOLATILE: 
            case SPUT_VOLATILE: 
            case IGET_OBJECT_VOLATILE: 
            case IGET_WIDE_VOLATILE: 
            case IPUT_WIDE_VOLATILE: 
            case SGET_WIDE_VOLATILE: 
            case SPUT_WIDE_VOLATILE: {
                MethodAnalyzer.super.analyzePutGetVolatile(analyzedInstruction);
                return true;
            }
            case EXECUTE_INLINE: {
                MethodAnalyzer.super.analyzeExecuteInline(analyzedInstruction);
                return true;
            }
            case EXECUTE_INLINE_RANGE: {
                MethodAnalyzer.super.analyzeExecuteInlineRange(analyzedInstruction);
                return true;
            }
            case INVOKE_DIRECT_EMPTY: {
                MethodAnalyzer.super.analyzeInvokeDirectEmpty(analyzedInstruction);
                return true;
            }
            case IGET_QUICK: 
            case IGET_WIDE_QUICK: 
            case IGET_OBJECT_QUICK: 
            case IPUT_QUICK: 
            case IPUT_WIDE_QUICK: 
            case IPUT_OBJECT_QUICK: {
                return MethodAnalyzer.super.analyzeIputIgetQuick(analyzedInstruction);
            }
            case INVOKE_VIRTUAL_QUICK: {
                return MethodAnalyzer.super.analyzeInvokeVirtualQuick(analyzedInstruction, false, false);
            }
            case INVOKE_SUPER_QUICK: {
                return MethodAnalyzer.super.analyzeInvokeVirtualQuick(analyzedInstruction, true, false);
            }
            case INVOKE_VIRTUAL_QUICK_RANGE: {
                return MethodAnalyzer.super.analyzeInvokeVirtualQuick(analyzedInstruction, false, true);
            }
            case INVOKE_SUPER_QUICK_RANGE: {
                return MethodAnalyzer.super.analyzeInvokeVirtualQuick(analyzedInstruction, true, true);
            }
            case IPUT_OBJECT_VOLATILE: 
            case SGET_OBJECT_VOLATILE: 
            case SPUT_OBJECT_VOLATILE: 
        }
        MethodAnalyzer.super.analyzePutGetVolatile(analyzedInstruction);
        return true;
    }

    private void analyzeInvokeDirect(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.analyzeInvokeDirectCommon(analyzedInstruction, new Format35cRegisterIterator((FiveRegisterInstruction)((Object)analyzedInstruction.instruction)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void analyzeInvokeDirectCommon(AnalyzedInstruction analyzedInstruction, RegisterIterator registerIterator) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_METHOD_ID_ITEM) {
            throw new AssertionError();
        }
        if (((MethodIdItem)item).getMethodName().getStringValue().equals((Object)"<init>")) {
            int n = registerIterator.getRegister();
            RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(n);
            if (!$assertionsDisabled && registerType == null) {
                throw new AssertionError();
            }
            if (registerType.category == RegisterType.Category.UninitRef || registerType.category == RegisterType.Category.UninitThis) {
                MethodAnalyzer.super.setPostRegisterTypeAndPropagateChanges(analyzedInstruction, n, RegisterType.getRegisterType(RegisterType.Category.Reference, registerType.type));
                for (int i = 0; i < analyzedInstruction.postRegisterMap.length; ++i) {
                    if (analyzedInstruction.postRegisterMap[i].category != RegisterType.Category.Unknown) continue;
                    RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(i);
                    if (registerType2.category != RegisterType.Category.UninitRef && registerType2.category != RegisterType.Category.UninitThis) continue;
                    RegisterType registerType3 = registerType2 == registerType ? analyzedInstruction.postRegisterMap[n] : registerType2;
                    MethodAnalyzer.super.setPostRegisterTypeAndPropagateChanges(analyzedInstruction, i, registerType3);
                }
            }
        }
    }

    private void analyzeInvokeDirectEmpty(AnalyzedInstruction analyzedInstruction) {
        Instruction35s instruction35s = (Instruction35s)analyzedInstruction.instruction;
        analyzedInstruction.setDeodexedInstruction(new Instruction35c(Opcode.INVOKE_DIRECT, instruction35s.getRegCount(), instruction35s.getRegisterD(), instruction35s.getRegisterE(), instruction35s.getRegisterF(), instruction35s.getRegisterG(), instruction35s.getRegisterA(), instruction35s.getReferencedItem()));
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
    }

    private void analyzeInvokeDirectRange(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.analyzeInvokeDirectCommon(analyzedInstruction, new Format3rcRegisterIterator((RegisterRangeInstruction)((Object)analyzedInstruction.instruction)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean analyzeInvokeVirtualQuick(AnalyzedInstruction analyzedInstruction, boolean bl, boolean bl2) {
        int n;
        void var11_16;
        int n2;
        MethodIdItem methodIdItem;
        if (bl2) {
            Instruction3rms instruction3rms = (Instruction3rms)analyzedInstruction.instruction;
            n2 = instruction3rms.getMethodIndex();
            n = instruction3rms.getStartRegister();
        } else {
            Instruction35ms instruction35ms = (Instruction35ms)analyzedInstruction.instruction;
            n2 = instruction35ms.getMethodIndex();
            n = instruction35ms.getRegisterD();
        }
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, n, ReferenceOrUninitCategories);
        if (registerType.category == RegisterType.Category.Null) {
            return false;
        }
        if (bl) {
            ClassPath.ClassDef classDef = ClassPath.getClassDef(this.encodedMethod.method.getContainingClass(), false);
            if (!$assertionsDisabled && classDef == null) {
                throw new AssertionError();
            }
            ClassPath.ClassDef classDef2 = classDef.getSuperclass();
            methodIdItem = null;
            if (classDef2 != null) {
                methodIdItem = this.deodexUtil.lookupVirtualMethod(classDef.getSuperclass(), n2);
            }
            if (methodIdItem == null) {
                methodIdItem = this.deodexUtil.lookupVirtualMethod(classDef, n2);
            }
        } else {
            methodIdItem = this.deodexUtil.lookupVirtualMethod(registerType.type, n2);
        }
        if (methodIdItem == null) {
            Object[] arrobject = new Object[]{registerType.type.getClassType(), n2};
            throw new ValidationException(String.format((String)"Could not resolve the method in class %s at index %d", (Object[])arrobject));
        }
        if (bl2) {
            Instruction3rms instruction3rms = (Instruction3rms)analyzedInstruction.instruction;
            Opcode opcode = bl ? Opcode.INVOKE_SUPER_RANGE : Opcode.INVOKE_VIRTUAL_RANGE;
            Instruction3rc instruction3rc = new Instruction3rc(opcode, instruction3rms.getRegCount(), instruction3rms.getStartRegister(), (Item)methodIdItem);
        } else {
            Instruction35ms instruction35ms = (Instruction35ms)analyzedInstruction.instruction;
            Opcode opcode = bl ? Opcode.INVOKE_SUPER : Opcode.INVOKE_VIRTUAL;
            Instruction35c instruction35c = new Instruction35c(opcode, instruction35ms.getRegCount(), instruction35ms.getRegisterD(), instruction35ms.getRegisterE(), instruction35ms.getRegisterF(), instruction35ms.getRegisterG(), instruction35ms.getRegisterA(), methodIdItem);
        }
        analyzedInstruction.setDeodexedInstruction((Instruction)var11_16);
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
        return true;
    }

    private boolean analyzeIputIgetQuick(AnalyzedInstruction analyzedInstruction) {
        Instruction22cs instruction22cs = (Instruction22cs)analyzedInstruction.instruction;
        int n = instruction22cs.getFieldOffset();
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, instruction22cs.getRegisterB(), ReferenceOrUninitCategories);
        if (registerType.category == RegisterType.Category.Null) {
            return false;
        }
        FieldIdItem fieldIdItem = this.deodexUtil.lookupField(registerType.type, n);
        if (fieldIdItem == null) {
            Object[] arrobject = new Object[]{registerType.type.getClassType(), n};
            throw new ValidationException(String.format((String)"Could not resolve the field in class %s at offset %d", (Object[])arrobject));
        }
        analyzedInstruction.setDeodexedInstruction(new Instruction22c(OdexedFieldInstructionMapper.getAndCheckDeodexedOpcodeForOdexedOpcode(fieldIdItem.getFieldType().getTypeDescriptor(), instruction22cs.opcode), (byte)instruction22cs.getRegisterA(), (byte)instruction22cs.getRegisterB(), fieldIdItem));
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
        return true;
    }

    private void analyzeLiteralBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType.Category category, boolean bl) {
        if (bl) {
            long l;
            RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB());
            if (BooleanCategories.contains((Object)registerType.category) && ((l = ((LiteralInstruction)((Object)analyzedInstruction.instruction)).getLiteral()) == 0L || l == 1L)) {
                category = RegisterType.Category.Boolean;
            }
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyzeMove(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, analyzedInstruction.getPreInstructionRegisterType(((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB()));
    }

    private void analyzeMoveException(AnalyzedInstruction analyzedInstruction) {
        CodeItem.TryItem[] arrtryItem = this.encodedMethod.codeItem.getTries();
        int n = this.getInstructionAddress(analyzedInstruction);
        if (arrtryItem == null) {
            throw new ValidationException("move-exception must be the first instruction in an exception handler block");
        }
        RegisterType registerType = null;
        CodeItem.TryItem[] arrtryItem2 = this.encodedMethod.codeItem.getTries();
        int n2 = arrtryItem2.length;
        int n3 = 0;
        do {
            CodeItem.TryItem tryItem;
            block7 : {
                block6 : {
                    if (n3 >= n2) break block6;
                    tryItem = arrtryItem2[n3];
                    if (tryItem.encodedCatchHandler.getCatchAllHandlerAddress() != n) break block7;
                    registerType = RegisterType.getRegisterType(RegisterType.Category.Reference, ClassPath.getClassDef("Ljava/lang/Throwable;"));
                }
                if (registerType != null) break;
                throw new ValidationException("move-exception must be the first instruction in an exception handler block");
            }
            for (CodeItem.EncodedTypeAddrPair encodedTypeAddrPair : tryItem.encodedCatchHandler.handlers) {
                if (encodedTypeAddrPair.getHandlerAddress() != n) continue;
                registerType = RegisterType.getRegisterTypeForTypeIdItem(encodedTypeAddrPair.exceptionType).merge(registerType);
            }
            ++n3;
        } while (true);
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void analyzeMoveResult(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType;
        AnalyzedInstruction analyzedInstruction2 = this.instructions.valueAt(-1 + analyzedInstruction.instructionIndex);
        if (!analyzedInstruction2.instruction.opcode.setsResult()) {
            throw new ValidationException(analyzedInstruction.instruction.opcode.name + " must occur after an " + "invoke-*/fill-new-array instruction");
        }
        Item item = ((InstructionWithReference)analyzedInstruction2.instruction).getReferencedItem();
        if (item.getItemType() == ItemType.TYPE_METHOD_ID_ITEM) {
            registerType = RegisterType.getRegisterTypeForTypeIdItem(((MethodIdItem)item).getPrototype().getReturnType());
        } else {
            if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
                throw new AssertionError();
            }
            registerType = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType);
    }

    private void analyzeNewArray(AnalyzedInstruction analyzedInstruction) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        RegisterType registerType = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
        if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
            throw new AssertionError();
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType);
    }

    private void analyzeNewInstance(AnalyzedInstruction analyzedInstruction) {
        InstructionWithReference instructionWithReference = (InstructionWithReference)analyzedInstruction.instruction;
        RegisterType registerType = analyzedInstruction.getPostInstructionRegisterType(((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA());
        if (registerType.category != RegisterType.Category.Unknown) {
            if (!$assertionsDisabled && registerType.category != RegisterType.Category.UninitRef) {
                throw new AssertionError();
            }
        } else {
            Item item = instructionWithReference.getReferencedItem();
            if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
                throw new AssertionError();
            }
            MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getUnitializedReference(RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)((TypeIdItem)item)).type));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean analyzePutGetVolatile(AnalyzedInstruction analyzedInstruction) {
        void var4_5;
        FieldIdItem fieldIdItem = (FieldIdItem)((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        Opcode opcode = OdexedFieldInstructionMapper.getAndCheckDeodexedOpcodeForOdexedOpcode(fieldIdItem.getFieldType().getTypeDescriptor(), analyzedInstruction.instruction.opcode);
        if (analyzedInstruction.instruction.opcode.isOdexedStaticVolatile()) {
            Instruction21c instruction21c = new Instruction21c(opcode, (byte)((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), fieldIdItem);
        } else {
            TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
            Instruction22c instruction22c = new Instruction22c(opcode, (byte)twoRegisterInstruction.getRegisterA(), (byte)twoRegisterInstruction.getRegisterB(), fieldIdItem);
        }
        analyzedInstruction.setDeodexedInstruction((Instruction)var4_5);
        MethodAnalyzer.super.analyzeInstruction(analyzedInstruction);
        return true;
    }

    private void analyzeSgetWideObject(AnalyzedInstruction analyzedInstruction) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForTypeIdItem(((FieldIdItem)item).getFieldType()));
    }

    private void analyzeUnaryOp(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(category, null));
    }

    private void analyzeWideConst(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(RegisterType.Category.LongLo, null));
    }

    private AnalyzedInstruction[] buildExceptionHandlerArray(CodeItem.TryItem tryItem) {
        int n = tryItem.encodedCatchHandler.handlers.length;
        int n2 = tryItem.encodedCatchHandler.getCatchAllHandlerAddress();
        if (n2 != -1) {
            ++n;
        }
        AnalyzedInstruction[] arranalyzedInstruction = new AnalyzedInstruction[n];
        for (int i = 0; i < tryItem.encodedCatchHandler.handlers.length; ++i) {
            arranalyzedInstruction[i] = this.instructions.get(tryItem.encodedCatchHandler.handlers[i].getHandlerAddress());
        }
        if (n2 != -1) {
            arranalyzedInstruction[-1 + arranalyzedInstruction.length] = this.instructions.get(n2);
        }
        return arranalyzedInstruction;
    }

    private void buildInstructionList() {
        if (!$assertionsDisabled && this.encodedMethod == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && this.encodedMethod.codeItem == null) {
            throw new AssertionError();
        }
        int n = this.encodedMethod.codeItem.getRegisterCount();
        Instruction[] arrinstruction = this.encodedMethod.codeItem.getInstructions();
        this.instructions = new SparseArray(arrinstruction.length);
        int n2 = 0;
        for (int i = 0; i < arrinstruction.length; ++i) {
            SparseArray<AnalyzedInstruction> sparseArray = this.instructions;
            AnalyzedInstruction analyzedInstruction = new AnalyzedInstruction(arrinstruction[i], i, n);
            sparseArray.append(n2, analyzedInstruction);
            if (!$assertionsDisabled && this.instructions.indexOfKey(n2) != i) {
                throw new AssertionError();
            }
            n2 += arrinstruction[i].getSize(n2);
        }
        CodeItem.TryItem[] arrtryItem = this.encodedMethod.codeItem.getTries();
        int n3 = 0;
        CodeItem.TryItem tryItem = null;
        AnalyzedInstruction[] arranalyzedInstruction = null;
        AnalyzedInstruction[][] arranalyzedInstruction2 = new AnalyzedInstruction[arrinstruction.length][];
        if (arrtryItem != null) {
            for (int i = 0; i < this.instructions.size(); ++i) {
                int n4;
                CodeItem.TryItem tryItem2;
                AnalyzedInstruction analyzedInstruction = this.instructions.valueAt(i);
                Opcode opcode = analyzedInstruction.instruction.opcode;
                int n5 = this.getInstructionAddress(analyzedInstruction);
                if (tryItem != null && tryItem.getStartCodeAddress() + tryItem.getTryLength() <= n5) {
                    tryItem = null;
                    ++n3;
                }
                if (tryItem == null && n3 < (n4 = arrtryItem.length) && (tryItem2 = arrtryItem[n3]).getStartCodeAddress() <= n5) {
                    if (!$assertionsDisabled && tryItem2.getStartCodeAddress() + tryItem2.getTryLength() <= n5) {
                        throw new AssertionError();
                    }
                    tryItem = tryItem2;
                    arranalyzedInstruction = this.buildExceptionHandlerArray(tryItem2);
                }
                if (tryItem == null || !opcode.canThrow()) continue;
                arranalyzedInstruction2[i] = arranalyzedInstruction;
            }
        }
        if (!$assertionsDisabled && this.instructions.size() <= 0) {
            throw new AssertionError();
        }
        BitSet bitSet = new BitSet(arrinstruction.length);
        this.addPredecessorSuccessor(this.startOfMethod, this.instructions.valueAt(0), arranalyzedInstruction2, bitSet);
        while (!bitSet.isEmpty()) {
            int n6 = bitSet.nextSetBit(0);
            bitSet.clear(n6);
            AnalyzedInstruction analyzedInstruction = this.instructions.valueAt(n6);
            Opcode opcode = analyzedInstruction.instruction.opcode;
            int n7 = this.getInstructionAddress(analyzedInstruction);
            if (analyzedInstruction.instruction.opcode.canContinue() && (analyzedInstruction.instruction.opcode != Opcode.NOP || !analyzedInstruction.instruction.getFormat().variableSizeFormat)) {
                if (n6 == -1 + this.instructions.size()) {
                    throw new ValidationException("Execution can continue past the last instruction");
                }
                this.addPredecessorSuccessor(analyzedInstruction, this.instructions.valueAt(n6 + 1), arranalyzedInstruction2, bitSet);
            }
            if (!(analyzedInstruction.instruction instanceof OffsetInstruction)) continue;
            OffsetInstruction offsetInstruction = (OffsetInstruction)((Object)analyzedInstruction.instruction);
            if (opcode == Opcode.PACKED_SWITCH || opcode == Opcode.SPARSE_SWITCH) {
                for (int n8 : ((MultiOffsetInstruction)((Object)this.instructions.get((int)(n7 + offsetInstruction.getTargetAddressOffset())).instruction)).getTargets()) {
                    this.addPredecessorSuccessor(analyzedInstruction, this.instructions.get(n7 + n8), arranalyzedInstruction2, bitSet);
                }
                continue;
            }
            int n9 = offsetInstruction.getTargetAddressOffset();
            this.addPredecessorSuccessor(analyzedInstruction, this.instructions.get(n7 + n9), arranalyzedInstruction2, bitSet);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean checkArrayFieldAssignment(RegisterType.Category category, RegisterType.Category category2) {
        return category == category2 || category == RegisterType.Category.Integer && category2 == RegisterType.Category.Float || category == RegisterType.Category.Float && category2 == RegisterType.Category.Integer;
    }

    private static void checkRegister(RegisterType registerType, int n, EnumSet enumSet) {
        if (!enumSet.contains((Object)registerType.category)) {
            Object[] arrobject = new Object[]{registerType.toString(), n};
            throw new ValidationException(String.format((String)"Invalid register type %s for register v%d.", (Object[])arrobject));
        }
    }

    private static void checkWidePair(int n, AnalyzedInstruction analyzedInstruction) {
        if (n + 1 >= analyzedInstruction.postRegisterMap.length) {
            Object[] arrobject = new Object[]{n};
            throw new ValidationException(String.format((String)"v%d cannot be used as the first register in a wide registerpair because it is the last register.", (Object[])arrobject));
        }
    }

    private static RegisterType getAndCheckSourceRegister(AnalyzedInstruction analyzedInstruction, int n, EnumSet enumSet) {
        if (!($assertionsDisabled || n >= 0 && n < analyzedInstruction.postRegisterMap.length)) {
            throw new AssertionError();
        }
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(n);
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        MethodAnalyzer.checkRegister(registerType, n, enumSet);
        if (enumSet == WideLowCategories) {
            MethodAnalyzer.checkRegister(registerType, n, WideLowCategories);
            MethodAnalyzer.checkWidePair(n, analyzedInstruction);
            RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(n + 1);
            if (!$assertionsDisabled && registerType2 == null) {
                throw new AssertionError();
            }
            MethodAnalyzer.checkRegister(registerType2, n + 1, WideHighCategories);
        }
        return registerType;
    }

    /*
     * Enabled aggressive block sorting
     */
    private RegisterType.Category getDestTypeForLiteralShiftRight(AnalyzedInstruction analyzedInstruction, boolean bl) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), Primitive32BitCategories);
        long l = ((LiteralInstruction)((Object)analyzedInstruction.instruction)).getLiteral();
        if (l == 0L) {
            return registerType.category;
        }
        RegisterType.Category category = !bl ? RegisterType.Category.Integer : registerType.category;
        if (l >= 32L) return category;
        switch (registerType.category) {
            case Byte: {
                return category;
            }
            default: {
                if ($assertionsDisabled) return category;
                throw new AssertionError();
            }
            case Integer: 
            case Float: {
                if (!bl) {
                    if (l > 24L) {
                        return RegisterType.Category.PosByte;
                    }
                    if (l < 16L) return category;
                    return RegisterType.Category.Char;
                }
                if (l >= 24L) {
                    return RegisterType.Category.Byte;
                }
                if (l < 16L) return category;
                return RegisterType.Category.Short;
            }
            case Short: {
                if (!bl) return category;
                if (l < 8L) return category;
                return RegisterType.Category.Byte;
            }
            case PosShort: {
                if (l < 8L) return category;
                return RegisterType.Category.PosByte;
            }
            case Char: {
                if (l <= 8L) return category;
                return RegisterType.Category.PosByte;
            }
            case PosByte: {
                return RegisterType.Category.PosByte;
            }
            case Null: 
            case One: 
            case Boolean: 
        }
        return RegisterType.Category.Null;
    }

    private static RegisterType[] getParameterTypes(TypeListItem typeListItem, int n) {
        if (!$assertionsDisabled && typeListItem == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && n != typeListItem.getRegisterCount()) {
            throw new AssertionError();
        }
        RegisterType[] arrregisterType = new RegisterType[n];
        int n2 = 0;
        for (TypeIdItem typeIdItem : typeListItem.getTypes()) {
            if (typeIdItem.getRegisterCount() == 2) {
                int n3 = n2 + 1;
                arrregisterType[n2] = RegisterType.getWideRegisterTypeForTypeIdItem(typeIdItem, true);
                n2 = n3 + 1;
                arrregisterType[n3] = RegisterType.getWideRegisterTypeForTypeIdItem(typeIdItem, false);
                continue;
            }
            int n4 = n2 + 1;
            arrregisterType[n2] = RegisterType.getRegisterTypeForTypeIdItem(typeIdItem);
            n2 = n4;
        }
        return arrregisterType;
    }

    private int getThisRegister() {
        if (!$assertionsDisabled && (this.encodedMethod.accessFlags & AccessFlags.STATIC.getValue()) != 0) {
            throw new AssertionError();
        }
        CodeItem codeItem = this.encodedMethod.codeItem;
        if (!$assertionsDisabled && codeItem == null) {
            throw new AssertionError();
        }
        MethodIdItem methodIdItem = this.encodedMethod.method;
        if (!$assertionsDisabled && methodIdItem == null) {
            throw new AssertionError();
        }
        int n = codeItem.getRegisterCount();
        if (n == 0) {
            throw new ValidationException("A non-static method must have at least 1 register");
        }
        return -1 + (n - methodIdItem.getPrototype().getParameterRegisterCount());
    }

    private boolean isInstanceConstructor() {
        return (this.encodedMethod.accessFlags & AccessFlags.STATIC.getValue()) == 0 && (this.encodedMethod.accessFlags & AccessFlags.CONSTRUCTOR.getValue()) != 0;
    }

    private boolean isStaticConstructor() {
        return (this.encodedMethod.accessFlags & AccessFlags.STATIC.getValue()) != 0 && (this.encodedMethod.accessFlags & AccessFlags.CONSTRUCTOR.getValue()) != 0;
    }

    private void propagateRegisterToSuccessors(AnalyzedInstruction analyzedInstruction, int n, BitSet bitSet) {
        RegisterType registerType = analyzedInstruction.getPostInstructionRegisterType(n);
        for (AnalyzedInstruction analyzedInstruction2 : analyzedInstruction.successors) {
            if (!analyzedInstruction2.mergeRegister(n, registerType, this.analyzedInstructions)) continue;
            bitSet.set(analyzedInstruction2.instructionIndex);
        }
    }

    private void setDestinationRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, RegisterType registerType) {
        MethodAnalyzer.super.setPostRegisterTypeAndPropagateChanges(analyzedInstruction, analyzedInstruction.getDestinationRegister(), registerType);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPostRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, int n, RegisterType registerType) {
        block7 : {
            block6 : {
                BitSet bitSet = new BitSet(this.instructions.size());
                if (!analyzedInstruction.setPostRegisterType(n, registerType)) break block6;
                MethodAnalyzer.super.propagateRegisterToSuccessors(analyzedInstruction, n, bitSet);
                while (!bitSet.isEmpty()) {
                    int n2 = bitSet.nextSetBit(0);
                    while (n2 >= 0) {
                        bitSet.clear(n2);
                        MethodAnalyzer.super.propagateRegisterToSuccessors(this.instructions.valueAt(n2), n, bitSet);
                        n2 = bitSet.nextSetBit(n2 + 1);
                    }
                }
                if (registerType.category == RegisterType.Category.LongLo) {
                    MethodAnalyzer.checkWidePair(n, analyzedInstruction);
                    MethodAnalyzer.super.setPostRegisterTypeAndPropagateChanges(analyzedInstruction, n + 1, RegisterType.getRegisterType(RegisterType.Category.LongHi, null));
                    return;
                }
                if (registerType.category == RegisterType.Category.DoubleLo) break block7;
            }
            return;
        }
        MethodAnalyzer.checkWidePair(n, analyzedInstruction);
        MethodAnalyzer.super.setPostRegisterTypeAndPropagateChanges(analyzedInstruction, n + 1, RegisterType.getRegisterType(RegisterType.Category.DoubleHi, null));
    }

    private void verify32BitPrimitiveAget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (registerType.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use %s with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            ClassPath.ArrayClassDef arrayClassDef = (ClassPath.ArrayClassDef)registerType.type;
            if (arrayClassDef.getArrayDimensions() != 1) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with multi-dimensional array type %s", (Object[])arrobject));
            }
            if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForType((String)arrayClassDef.getBaseElementClass().getClassType()).category, category)) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verify32BitPrimitiveAput(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterA());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (!registerType.canBeAssignedTo(RegisterType.getRegisterType(category, null))) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.toString()};
            throw new ValidationException(String.format((String)"Cannot use %s with source register type %s.", (Object[])arrobject));
        }
        RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType2 == null) {
            throw new AssertionError();
        }
        if (registerType2.category != RegisterType.Category.Null) {
            if (registerType2.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType2.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use %s with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType2.type == null) {
                throw new AssertionError();
            }
            if (registerType2.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType2.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType2.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            ClassPath.ArrayClassDef arrayClassDef = (ClassPath.ArrayClassDef)registerType2.type;
            if (arrayClassDef.getArrayDimensions() != 1) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType2.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with multi-dimensional array type %s", (Object[])arrobject));
            }
            if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForType((String)arrayClassDef.getBaseElementClass().getClassType()).category, category)) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType2.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use %s with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verify32BitPrimitiveIget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), ReferenceOrUninitThisCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category, category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verify32BitPrimitiveIput(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterB(), ReferenceOrUninitThisCategories);
        RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterA());
        if (!$assertionsDisabled && registerType2 == null) {
            throw new AssertionError();
        }
        if (registerType2.category == RegisterType.Category.Byte && category == RegisterType.Category.Boolean) {
            registerType2 = RegisterType.getRegisterType(RegisterType.Category.Boolean, null);
        }
        if (!registerType2.canBeAssignedTo(RegisterType.getRegisterType(category, null))) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType2.toString()};
            throw new ValidationException(String.format((String)"Cannot use %s with source register type %s.", (Object[])arrobject));
        }
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category, category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verify32BitPrimitiveSget(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category, category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verify32BitPrimitiveSput(AnalyzedInstruction analyzedInstruction, RegisterType.Category category) {
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category == RegisterType.Category.Byte && category == RegisterType.Category.Boolean) {
            registerType = RegisterType.getRegisterType(RegisterType.Category.Boolean, null);
        }
        if (!registerType.canBeAssignedTo(RegisterType.getRegisterType(category, null))) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.toString()};
            throw new ValidationException(String.format((String)"Cannot use %s with source register type %s.", (Object[])arrobject));
        }
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (!MethodAnalyzer.checkArrayFieldAssignment(RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category, category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifyAgetObject(AnalyzedInstruction analyzedInstruction) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (registerType.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{registerType.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            char c = ((ClassPath.ArrayClassDef)registerType.type).getImmediateElementClass().getClassType().charAt(0);
            if (c != 'L' && c != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verifyAgetWide(AnalyzedInstruction analyzedInstruction) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (registerType.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{registerType.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use aget-wide with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-wide with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            ClassPath.ArrayClassDef arrayClassDef = (ClassPath.ArrayClassDef)registerType.type;
            if (arrayClassDef.getArrayDimensions() != 1) {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-wide with multi-dimensional array type %s", (Object[])arrobject));
            }
            char c = arrayClassDef.getBaseElementClass().getClassType().charAt(0);
            if (c != 'J' && c != 'D') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-wide with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verifyAputObject(AnalyzedInstruction analyzedInstruction) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterA());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType2 == null) {
            throw new AssertionError();
        }
        if (registerType2.category != RegisterType.Category.Null) {
            if (registerType2.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{registerType2.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType2.type == null) {
                throw new AssertionError();
            }
            if (registerType2.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType2.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType2.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            char c = ((ClassPath.ArrayClassDef)registerType2.type).getImmediateElementClass().getClassType().charAt(0);
            if (c != 'L' && c != '[') {
                Object[] arrobject = new Object[]{registerType2.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aget-object with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verifyAputWide(AnalyzedInstruction analyzedInstruction) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), Primitive32BitCategories);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterA(), WideLowCategories);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(threeRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Null) {
            if (registerType.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{registerType.category.toString()};
                throw new ValidationException(String.format((String)"Cannot use aput-wide with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && registerType.type == null) {
                throw new AssertionError();
            }
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aput-wide with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
            ClassPath.ArrayClassDef arrayClassDef = (ClassPath.ArrayClassDef)registerType.type;
            if (arrayClassDef.getArrayDimensions() != 1) {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aput-wide with multi-dimensional array type %s", (Object[])arrobject));
            }
            char c = arrayClassDef.getBaseElementClass().getClassType().charAt(0);
            if (c != 'J' && c != 'D') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use aput-wide with array type %s. Incorrect array type for the instruction.", (Object[])arrobject));
            }
        }
    }

    private void verifyArrayLength(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), ReferenceCategories);
        if (registerType.type != null) {
            if (registerType.type.getClassType().charAt(0) != '[') {
                Object[] arrobject = new Object[]{registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot use array-length with non-array type %s", (Object[])arrobject));
            }
            if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                throw new AssertionError();
            }
        }
    }

    private void verifyBinary2AddrOp(AnalyzedInstruction analyzedInstruction, EnumSet enumSet, EnumSet enumSet2) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterA(), enumSet);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterB(), enumSet2);
    }

    private void verifyBinaryOp(AnalyzedInstruction analyzedInstruction, EnumSet enumSet, EnumSet enumSet2) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterB(), enumSet);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), enumSet2);
    }

    private void verifyCheckCast(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), ReferenceCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        if (RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)((TypeIdItem)item)).category != RegisterType.Category.Reference) {
            // empty if block
        }
    }

    private void verifyConstClass(AnalyzedInstruction analyzedInstruction) {
        ClassPath.ClassDef classDef = ClassPath.getClassDef("Ljava/lang/Class;");
        RegisterType.getRegisterType(RegisterType.Category.Reference, classDef);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        ClassPath.getClassDef((TypeIdItem)item);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyFillArrayData(AnalyzedInstruction analyzedInstruction) {
        int n;
        int n2;
        ArrayDataPseudoInstruction arrayDataPseudoInstruction;
        ClassPath.ArrayClassDef arrayClassDef;
        block15 : {
            block14 : {
                AnalyzedInstruction analyzedInstruction2;
                int n3 = ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA();
                RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(n3);
                if (!$assertionsDisabled && registerType == null) {
                    throw new AssertionError();
                }
                if (registerType.category == RegisterType.Category.Null) break block14;
                if (registerType.category != RegisterType.Category.Reference) {
                    Object[] arrobject = new Object[]{n3, registerType.toString()};
                    throw new ValidationException(String.format((String)"Cannot use fill-array-data with non-array register v%d of type %s", (Object[])arrobject));
                }
                if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
                    throw new AssertionError();
                }
                arrayClassDef = (ClassPath.ArrayClassDef)registerType.type;
                if (arrayClassDef.getArrayDimensions() != 1) {
                    Object[] arrobject = new Object[]{arrayClassDef.getClassType()};
                    throw new ValidationException(String.format((String)"Cannot use fill-array-data with array type %s. It can only be used with a one-dimensional array of primitives.", (Object[])arrobject));
                }
                switch (arrayClassDef.getBaseElementClass().getClassType().charAt(0)) {
                    default: {
                        Object[] arrobject = new Object[]{arrayClassDef.getClassType()};
                        throw new ValidationException(String.format((String)"Cannot use fill-array-data with array type %s. It can only be used with a one-dimensional array of primitives.", (Object[])arrobject));
                    }
                    case 'B': 
                    case 'Z': {
                        n2 = 1;
                        break;
                    }
                    case 'C': 
                    case 'S': {
                        n2 = 2;
                        break;
                    }
                    case 'F': 
                    case 'I': {
                        n2 = 4;
                        break;
                    }
                    case 'D': 
                    case 'J': {
                        n2 = 8;
                    }
                }
                if ((analyzedInstruction2 = this.instructions.get(n = ((OffsetInstruction)((Object)analyzedInstruction.instruction)).getTargetAddressOffset() + this.getInstructionAddress(analyzedInstruction))) == null || analyzedInstruction2.instruction.getFormat() != Format.ArrayData) {
                    Object[] arrobject = new Object[]{n};
                    throw new ValidationException(String.format((String)"Could not find an array data structure at code address 0x%x", (Object[])arrobject));
                }
                arrayDataPseudoInstruction = (ArrayDataPseudoInstruction)analyzedInstruction2.instruction;
                if (n2 != arrayDataPseudoInstruction.getElementWidth()) break block15;
            }
            return;
        }
        Object[] arrobject = new Object[]{n, arrayClassDef.getClassType(), n2, arrayDataPseudoInstruction.getElementWidth()};
        throw new ValidationException(String.format((String)"The array data at code address 0x%x does not have the correct element width for array type %s. Expecting element width %d, got element width %d.", (Object[])arrobject));
    }

    private void verifyFilledNewArray(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.super.verifyFilledNewArrayCommon(analyzedInstruction, new Format35cRegisterIterator((FiveRegisterInstruction)((Object)analyzedInstruction.instruction)));
    }

    private void verifyFilledNewArrayCommon(AnalyzedInstruction analyzedInstruction, RegisterIterator registerIterator) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        ClassPath.ClassDef classDef = ClassPath.getClassDef((TypeIdItem)item);
        if (classDef.getClassType().charAt(0) != '[') {
            throw new ValidationException("Cannot use non-array type \"" + classDef.getClassType() + "\" with new-array. Use new-instance instead.");
        }
        ClassPath.ArrayClassDef arrayClassDef = (ClassPath.ArrayClassDef)classDef;
        RegisterType registerType = RegisterType.getRegisterType(RegisterType.Category.Reference, classDef);
        RegisterType registerType2 = RegisterType.getRegisterTypeForType(arrayClassDef.getImmediateElementClass().getClassType());
        String string2 = arrayClassDef.getBaseElementClass().getClassType();
        if (string2.charAt(0) == 'J' || string2.charAt(0) == 'D') {
            throw new ValidationException("Cannot use filled-new-array to create an array of wide values (long or double)");
        }
        do {
            int n = registerIterator.getRegister();
            RegisterType registerType3 = analyzedInstruction.getPreInstructionRegisterType(n);
            if (!$assertionsDisabled && registerType3 == null) {
                throw new AssertionError();
            }
            if (registerType3.canBeAssignedTo(registerType2)) continue;
            throw new ValidationException("Register v" + Integer.toString((int)n) + " is of type " + registerType3.toString() + " and is incompatible with the array type " + registerType.type.getClassType());
        } while (registerIterator.moveNext());
    }

    private void verifyFilledNewArrayRange(AnalyzedInstruction analyzedInstruction) {
        RegisterRangeInstruction registerRangeInstruction = (RegisterRangeInstruction)((Object)analyzedInstruction.instruction);
        if (registerRangeInstruction.getStartRegister() + registerRangeInstruction.getRegCount() >= 65536) {
            Object[] arrobject = new Object[]{registerRangeInstruction.getStartRegister(), -1 + (registerRangeInstruction.getStartRegister() + registerRangeInstruction.getRegCount())};
            throw new ValidationException(String.format((String)"Invalid register range {v%d .. v%d}. The ending register is larger than the largest allowed register of v65535.", (Object[])arrobject));
        }
        MethodAnalyzer.super.verifyFilledNewArrayCommon(analyzedInstruction, new Format3rcRegisterIterator(registerRangeInstruction));
    }

    private void verifyFloatWideCmp(AnalyzedInstruction analyzedInstruction, EnumSet enumSet) {
        ThreeRegisterInstruction threeRegisterInstruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterB(), enumSet);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, threeRegisterInstruction.getRegisterC(), enumSet);
    }

    private void verifyIf(AnalyzedInstruction analyzedInstruction) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterA(), Primitive32BitCategories);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterB(), Primitive32BitCategories);
    }

    private void verifyIfEqNe(AnalyzedInstruction analyzedInstruction) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterA());
        if (!$assertionsDisabled && registerType == null) {
            throw new AssertionError();
        }
        RegisterType registerType2 = analyzedInstruction.getPreInstructionRegisterType(twoRegisterInstruction.getRegisterB());
        if (!$assertionsDisabled && registerType2 == null) {
            throw new AssertionError();
        }
        if (!(ReferenceCategories.contains((Object)registerType.category) && ReferenceCategories.contains((Object)registerType2.category) || Primitive32BitCategories.contains((Object)registerType.category) && Primitive32BitCategories.contains((Object)registerType2.category))) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, registerType.toString(), registerType2.toString()};
            throw new ValidationException(String.format((String)"%s cannot be used on registers of dissimilar types %s and %s. They must both be a reference type or a primitive 32 bit type.", (Object[])arrobject));
        }
    }

    private void verifyIfEqzNez(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), ReferenceAndPrimitive32BitCategories);
    }

    private void verifyIfz(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), Primitive32BitCategories);
    }

    private void verifyIgetObject(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), ReferenceOrUninitThisCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        if (RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifyIgetWide(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), ReferenceOrUninitThisCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        RegisterType registerType2 = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (!WideLowCategories.contains((Object)registerType2.category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifyInstanceOf(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), ReferenceCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        RegisterType registerType = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
        if (registerType.category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{registerType.toString()};
            throw new ValidationException(String.format((String)"Cannot use instance-of with a non-reference type %s", (Object[])arrobject));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void verifyInstruction(AnalyzedInstruction analyzedInstruction) {
        Instruction instruction = analyzedInstruction.instruction;
        switch (instruction.opcode) {
            default: {
                if ($assertionsDisabled) return;
                throw new AssertionError();
            }
            case MOVE: 
            case MOVE_FROM16: 
            case MOVE_16: {
                MethodAnalyzer.super.verifyMove(analyzedInstruction, Primitive32BitCategories);
            }
            case NOP: 
            case CONST_4: 
            case CONST_16: 
            case CONST: 
            case CONST_HIGH16: 
            case CONST_WIDE_16: 
            case CONST_WIDE_32: 
            case CONST_WIDE: 
            case CONST_WIDE_HIGH16: 
            case CONST_STRING: 
            case CONST_STRING_JUMBO: 
            case GOTO: 
            case GOTO_16: 
            case GOTO_32: {
                return;
            }
            case MOVE_WIDE: 
            case MOVE_WIDE_FROM16: 
            case MOVE_WIDE_16: {
                MethodAnalyzer.super.verifyMove(analyzedInstruction, WideLowCategories);
                return;
            }
            case MOVE_OBJECT: 
            case MOVE_OBJECT_FROM16: 
            case MOVE_OBJECT_16: {
                MethodAnalyzer.super.verifyMove(analyzedInstruction, ReferenceOrUninitCategories);
                return;
            }
            case MOVE_RESULT: {
                MethodAnalyzer.super.verifyMoveResult(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case MOVE_RESULT_WIDE: {
                MethodAnalyzer.super.verifyMoveResult(analyzedInstruction, WideLowCategories);
                return;
            }
            case MOVE_RESULT_OBJECT: {
                MethodAnalyzer.super.verifyMoveResult(analyzedInstruction, ReferenceCategories);
                return;
            }
            case MOVE_EXCEPTION: {
                MethodAnalyzer.super.verifyMoveException(analyzedInstruction);
                return;
            }
            case RETURN_VOID: {
                MethodAnalyzer.super.verifyReturnVoid(analyzedInstruction);
                return;
            }
            case RETURN: {
                MethodAnalyzer.super.verifyReturn(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case RETURN_WIDE: {
                MethodAnalyzer.super.verifyReturn(analyzedInstruction, WideLowCategories);
                return;
            }
            case RETURN_OBJECT: {
                MethodAnalyzer.super.verifyReturn(analyzedInstruction, ReferenceCategories);
                return;
            }
            case CONST_CLASS: {
                MethodAnalyzer.super.verifyConstClass(analyzedInstruction);
                return;
            }
            case MONITOR_ENTER: 
            case MONITOR_EXIT: {
                MethodAnalyzer.super.verifyMonitor(analyzedInstruction);
                return;
            }
            case CHECK_CAST: {
                MethodAnalyzer.super.verifyCheckCast(analyzedInstruction);
                return;
            }
            case INSTANCE_OF: {
                MethodAnalyzer.super.verifyInstanceOf(analyzedInstruction);
                return;
            }
            case ARRAY_LENGTH: {
                MethodAnalyzer.super.verifyArrayLength(analyzedInstruction);
                return;
            }
            case NEW_INSTANCE: {
                MethodAnalyzer.super.verifyNewInstance(analyzedInstruction);
                return;
            }
            case NEW_ARRAY: {
                MethodAnalyzer.super.verifyNewArray(analyzedInstruction);
                return;
            }
            case FILLED_NEW_ARRAY: {
                MethodAnalyzer.super.verifyFilledNewArray(analyzedInstruction);
                return;
            }
            case FILLED_NEW_ARRAY_RANGE: {
                MethodAnalyzer.super.verifyFilledNewArrayRange(analyzedInstruction);
                return;
            }
            case FILL_ARRAY_DATA: {
                MethodAnalyzer.super.verifyFillArrayData(analyzedInstruction);
                return;
            }
            case THROW: {
                MethodAnalyzer.super.verifyThrow(analyzedInstruction);
                return;
            }
            case PACKED_SWITCH: {
                MethodAnalyzer.super.verifySwitch(analyzedInstruction, Format.PackedSwitchData);
                return;
            }
            case SPARSE_SWITCH: {
                MethodAnalyzer.super.verifySwitch(analyzedInstruction, Format.SparseSwitchData);
                return;
            }
            case CMPL_FLOAT: 
            case CMPG_FLOAT: {
                MethodAnalyzer.super.verifyFloatWideCmp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case CMPL_DOUBLE: 
            case CMPG_DOUBLE: 
            case CMP_LONG: {
                MethodAnalyzer.super.verifyFloatWideCmp(analyzedInstruction, WideLowCategories);
                return;
            }
            case IF_EQ: 
            case IF_NE: {
                MethodAnalyzer.super.verifyIfEqNe(analyzedInstruction);
                return;
            }
            case IF_LT: 
            case IF_GE: 
            case IF_GT: 
            case IF_LE: {
                MethodAnalyzer.super.verifyIf(analyzedInstruction);
                return;
            }
            case IF_EQZ: 
            case IF_NEZ: {
                MethodAnalyzer.super.verifyIfEqzNez(analyzedInstruction);
                return;
            }
            case IF_LTZ: 
            case IF_GEZ: 
            case IF_GTZ: 
            case IF_LEZ: {
                MethodAnalyzer.super.verifyIfz(analyzedInstruction);
                return;
            }
            case AGET: {
                MethodAnalyzer.super.verify32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case AGET_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case AGET_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case AGET_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case AGET_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveAget(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case AGET_WIDE: {
                MethodAnalyzer.super.verifyAgetWide(analyzedInstruction);
                return;
            }
            case AGET_OBJECT: {
                MethodAnalyzer.super.verifyAgetObject(analyzedInstruction);
                return;
            }
            case APUT: {
                MethodAnalyzer.super.verify32BitPrimitiveAput(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case APUT_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveAput(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case APUT_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveAput(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case APUT_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveAput(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case APUT_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveAput(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case APUT_WIDE: {
                MethodAnalyzer.super.verifyAputWide(analyzedInstruction);
                return;
            }
            case APUT_OBJECT: {
                MethodAnalyzer.super.verifyAputObject(analyzedInstruction);
                return;
            }
            case IGET: {
                MethodAnalyzer.super.verify32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case IGET_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case IGET_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case IGET_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case IGET_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveIget(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case IGET_WIDE: {
                MethodAnalyzer.super.verifyIgetWide(analyzedInstruction);
                return;
            }
            case IGET_OBJECT: {
                MethodAnalyzer.super.verifyIgetObject(analyzedInstruction);
                return;
            }
            case IPUT: {
                MethodAnalyzer.super.verify32BitPrimitiveIput(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case IPUT_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveIput(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case IPUT_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveIput(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case IPUT_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveIput(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case IPUT_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveIput(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case IPUT_WIDE: {
                MethodAnalyzer.super.verifyIputWide(analyzedInstruction);
                return;
            }
            case IPUT_OBJECT: {
                MethodAnalyzer.super.verifyIputObject(analyzedInstruction);
                return;
            }
            case SGET: {
                MethodAnalyzer.super.verify32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case SGET_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case SGET_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case SGET_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case SGET_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveSget(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case SGET_WIDE: {
                MethodAnalyzer.super.verifySgetWide(analyzedInstruction);
                return;
            }
            case SGET_OBJECT: {
                MethodAnalyzer.super.verifySgetObject(analyzedInstruction);
                return;
            }
            case SPUT: {
                MethodAnalyzer.super.verify32BitPrimitiveSput(analyzedInstruction, RegisterType.Category.Integer);
                return;
            }
            case SPUT_BOOLEAN: {
                MethodAnalyzer.super.verify32BitPrimitiveSput(analyzedInstruction, RegisterType.Category.Boolean);
                return;
            }
            case SPUT_BYTE: {
                MethodAnalyzer.super.verify32BitPrimitiveSput(analyzedInstruction, RegisterType.Category.Byte);
                return;
            }
            case SPUT_CHAR: {
                MethodAnalyzer.super.verify32BitPrimitiveSput(analyzedInstruction, RegisterType.Category.Char);
                return;
            }
            case SPUT_SHORT: {
                MethodAnalyzer.super.verify32BitPrimitiveSput(analyzedInstruction, RegisterType.Category.Short);
                return;
            }
            case SPUT_WIDE: {
                MethodAnalyzer.super.verifySputWide(analyzedInstruction);
                return;
            }
            case SPUT_OBJECT: {
                MethodAnalyzer.super.verifySputObject(analyzedInstruction);
                return;
            }
            case INVOKE_VIRTUAL: {
                MethodAnalyzer.super.verifyInvoke(analyzedInstruction, 1);
                return;
            }
            case INVOKE_SUPER: {
                MethodAnalyzer.super.verifyInvoke(analyzedInstruction, 2);
                return;
            }
            case INVOKE_DIRECT: {
                MethodAnalyzer.super.verifyInvoke(analyzedInstruction, 4);
                return;
            }
            case INVOKE_STATIC: {
                MethodAnalyzer.super.verifyInvoke(analyzedInstruction, 16);
                return;
            }
            case INVOKE_INTERFACE: {
                MethodAnalyzer.super.verifyInvoke(analyzedInstruction, 8);
                return;
            }
            case INVOKE_VIRTUAL_RANGE: {
                MethodAnalyzer.super.verifyInvokeRange(analyzedInstruction, 1);
                return;
            }
            case INVOKE_SUPER_RANGE: {
                MethodAnalyzer.super.verifyInvokeRange(analyzedInstruction, 2);
                return;
            }
            case INVOKE_DIRECT_RANGE: {
                MethodAnalyzer.super.verifyInvokeRange(analyzedInstruction, 4);
                return;
            }
            case INVOKE_STATIC_RANGE: {
                MethodAnalyzer.super.verifyInvokeRange(analyzedInstruction, 16);
                return;
            }
            case INVOKE_INTERFACE_RANGE: {
                MethodAnalyzer.super.verifyInvokeRange(analyzedInstruction, 8);
                return;
            }
            case NEG_INT: 
            case NOT_INT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case NEG_LONG: 
            case NOT_LONG: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case NEG_FLOAT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case NEG_DOUBLE: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case INT_TO_LONG: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case INT_TO_FLOAT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case INT_TO_DOUBLE: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case LONG_TO_INT: 
            case DOUBLE_TO_INT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case LONG_TO_FLOAT: 
            case DOUBLE_TO_FLOAT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case LONG_TO_DOUBLE: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case FLOAT_TO_INT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case FLOAT_TO_LONG: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case FLOAT_TO_DOUBLE: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case DOUBLE_TO_LONG: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, WideLowCategories);
                return;
            }
            case INT_TO_BYTE: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case INT_TO_CHAR: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case INT_TO_SHORT: {
                MethodAnalyzer.super.verifyUnaryOp(analyzedInstruction, Primitive32BitCategories);
                return;
            }
            case ADD_INT: 
            case SUB_INT: 
            case MUL_INT: 
            case DIV_INT: 
            case REM_INT: 
            case SHL_INT: 
            case SHR_INT: 
            case USHR_INT: 
            case AND_INT: 
            case OR_INT: 
            case XOR_INT: {
                MethodAnalyzer.super.verifyBinaryOp(analyzedInstruction, Primitive32BitCategories, Primitive32BitCategories);
                return;
            }
            case ADD_LONG: 
            case SUB_LONG: 
            case MUL_LONG: 
            case DIV_LONG: 
            case REM_LONG: 
            case AND_LONG: 
            case OR_LONG: 
            case XOR_LONG: {
                MethodAnalyzer.super.verifyBinaryOp(analyzedInstruction, WideLowCategories, WideLowCategories);
                return;
            }
            case SHL_LONG: 
            case SHR_LONG: 
            case USHR_LONG: {
                MethodAnalyzer.super.verifyBinaryOp(analyzedInstruction, WideLowCategories, Primitive32BitCategories);
                return;
            }
            case ADD_FLOAT: 
            case SUB_FLOAT: 
            case MUL_FLOAT: 
            case DIV_FLOAT: 
            case REM_FLOAT: {
                MethodAnalyzer.super.verifyBinaryOp(analyzedInstruction, Primitive32BitCategories, Primitive32BitCategories);
                return;
            }
            case ADD_DOUBLE: 
            case SUB_DOUBLE: 
            case MUL_DOUBLE: 
            case DIV_DOUBLE: 
            case REM_DOUBLE: {
                MethodAnalyzer.super.verifyBinaryOp(analyzedInstruction, WideLowCategories, WideLowCategories);
                return;
            }
            case ADD_INT_2ADDR: 
            case SUB_INT_2ADDR: 
            case MUL_INT_2ADDR: 
            case DIV_INT_2ADDR: 
            case REM_INT_2ADDR: 
            case SHL_INT_2ADDR: 
            case SHR_INT_2ADDR: 
            case USHR_INT_2ADDR: 
            case AND_INT_2ADDR: 
            case OR_INT_2ADDR: 
            case XOR_INT_2ADDR: {
                MethodAnalyzer.super.verifyBinary2AddrOp(analyzedInstruction, Primitive32BitCategories, Primitive32BitCategories);
                return;
            }
            case ADD_LONG_2ADDR: 
            case SUB_LONG_2ADDR: 
            case MUL_LONG_2ADDR: 
            case DIV_LONG_2ADDR: 
            case REM_LONG_2ADDR: 
            case AND_LONG_2ADDR: 
            case OR_LONG_2ADDR: 
            case XOR_LONG_2ADDR: {
                MethodAnalyzer.super.verifyBinary2AddrOp(analyzedInstruction, WideLowCategories, WideLowCategories);
                return;
            }
            case SHL_LONG_2ADDR: 
            case SHR_LONG_2ADDR: 
            case USHR_LONG_2ADDR: {
                MethodAnalyzer.super.verifyBinary2AddrOp(analyzedInstruction, WideLowCategories, Primitive32BitCategories);
                return;
            }
            case ADD_FLOAT_2ADDR: 
            case SUB_FLOAT_2ADDR: 
            case MUL_FLOAT_2ADDR: 
            case DIV_FLOAT_2ADDR: 
            case REM_FLOAT_2ADDR: {
                MethodAnalyzer.super.verifyBinary2AddrOp(analyzedInstruction, Primitive32BitCategories, Primitive32BitCategories);
                return;
            }
            case ADD_DOUBLE_2ADDR: 
            case SUB_DOUBLE_2ADDR: 
            case MUL_DOUBLE_2ADDR: 
            case DIV_DOUBLE_2ADDR: 
            case REM_DOUBLE_2ADDR: {
                MethodAnalyzer.super.verifyBinary2AddrOp(analyzedInstruction, WideLowCategories, WideLowCategories);
                return;
            }
            case ADD_INT_LIT16: 
            case RSUB_INT: 
            case MUL_INT_LIT16: 
            case DIV_INT_LIT16: 
            case REM_INT_LIT16: {
                MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
                return;
            }
            case AND_INT_LIT16: 
            case OR_INT_LIT16: 
            case XOR_INT_LIT16: {
                MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
                return;
            }
            case ADD_INT_LIT8: 
            case RSUB_INT_LIT8: 
            case MUL_INT_LIT8: 
            case DIV_INT_LIT8: 
            case REM_INT_LIT8: 
            case SHL_INT_LIT8: {
                MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
                return;
            }
            case AND_INT_LIT8: 
            case OR_INT_LIT8: 
            case XOR_INT_LIT8: {
                MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
                return;
            }
            case SHR_INT_LIT8: {
                MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
                return;
            }
            case USHR_INT_LIT8: 
        }
        MethodAnalyzer.super.verifyLiteralBinaryOp(analyzedInstruction);
    }

    private void verifyInvoke(AnalyzedInstruction analyzedInstruction, int n) {
        MethodAnalyzer.super.verifyInvokeCommon(analyzedInstruction, false, n, new Format35cRegisterIterator((FiveRegisterInstruction)((Object)analyzedInstruction.instruction)));
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyInvokeCommon(AnalyzedInstruction analyzedInstruction, boolean bl, int n, RegisterIterator registerIterator) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_METHOD_ID_ITEM) {
            throw new AssertionError();
        }
        MethodIdItem methodIdItem = (MethodIdItem)item;
        TypeIdItem typeIdItem = methodIdItem.getContainingClass();
        char c = methodIdItem.getMethodName().getStringValue().charAt(0);
        boolean bl2 = false;
        if (c == '<') {
            if ((n & 4) == 0) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name};
                throw new ValidationException(String.format((String)"Cannot call constructor %s with %s", (Object[])arrobject));
            }
            bl2 = true;
        }
        ClassPath.ClassDef classDef = ClassPath.getClassDef(typeIdItem);
        if ((n & 8) != 0) {
            if (!classDef.isInterface()) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name, classDef.getClassType()};
                throw new ValidationException(String.format((String)"Cannot call method %s with %s. %s is not an interface class.", (Object[])arrobject));
            }
        } else if (classDef.isInterface()) {
            Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name, classDef.getClassType()};
            throw new ValidationException(String.format((String)"Cannot call method %s with %s. %s is an interface class. Use invoke-interface or invoke-interface/range instead.", (Object[])arrobject));
        }
        if ((n & 2) != 0) {
            ClassPath.ClassDef classDef2 = ClassPath.getClassDef(this.encodedMethod.method.getContainingClass());
            if (classDef2.getSuperclass() == null) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name, classDef.getSuperclass().getClassType()};
                throw new ValidationException(String.format((String)"Cannot call method %s with %s. %s has no superclass", (Object[])arrobject));
            }
            if (!classDef2.getSuperclass().extendsClass(classDef)) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name, typeIdItem.getTypeDescriptor(), this.encodedMethod.method.getContainingClass().getTypeDescriptor()};
                throw new ValidationException(String.format((String)"Cannot call method %s with %s. %s is not an ancestor of the current class %s", (Object[])arrobject));
            }
            if (!classDef2.getSuperclass().hasVirtualMethod(methodIdItem.getVirtualMethodString())) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), analyzedInstruction.instruction.opcode.name, classDef.getSuperclass().getClassType()};
                throw new ValidationException(String.format((String)"Cannot call method %s with %s. The superclass %s hasno such method", (Object[])arrobject));
            }
        }
        if (!$assertionsDisabled && !bl && registerIterator.getCount() > 5) {
            throw new AssertionError();
        }
        TypeListItem typeListItem = methodIdItem.getPrototype().getParameters();
        int n2 = typeListItem == null ? 0 : typeListItem.getRegisterCount();
        if ((n & 16) == 0) {
            ++n2;
        }
        if (n2 != registerIterator.getCount()) {
            Object[] arrobject = new Object[]{methodIdItem.getMethodString(), n2 + 1, registerIterator.getCount()};
            throw new ValidationException(String.format((String)"The number of registers does not match the number of parameters for method %s. Expecting %d registers, got %d.", (Object[])arrobject));
        }
        if ((n & 16) == 0) {
            int n3 = registerIterator.getRegister();
            registerIterator.moveNext();
            RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(n3);
            if (!$assertionsDisabled && registerType == null) {
                throw new AssertionError();
            }
            if (registerType.category == RegisterType.Category.UninitRef || registerType.category == RegisterType.Category.UninitThis) {
                if (!bl2) {
                    Object[] arrobject = new Object[]{methodIdItem.getMethodString(), registerType.type.getClassType()};
                    throw new ValidationException(String.format((String)"Cannot invoke non-<init> method %s on uninitialized reference type %s", (Object[])arrobject));
                }
            } else if (registerType.category == RegisterType.Category.Reference) {
                if (bl2) {
                    Object[] arrobject = new Object[]{methodIdItem.getMethodString(), registerType.type.getClassType()};
                    throw new ValidationException(String.format((String)"Cannot invoke %s on initialized reference type %s", (Object[])arrobject));
                }
            } else {
                if (registerType.category != RegisterType.Category.Null) {
                    Object[] arrobject = new Object[]{methodIdItem.getMethodString(), registerType.toString()};
                    throw new ValidationException(String.format((String)"Cannot invoke %s on non-reference type %s", (Object[])arrobject));
                }
                if (bl2) {
                    Object[] arrobject = new Object[]{methodIdItem.getMethodString()};
                    throw new ValidationException(String.format((String)"Cannot invoke %s on a null reference", (Object[])arrobject));
                }
            }
            if (bl2 && registerType.type.getSuperclass() == classDef && !this.encodedMethod.method.getMethodName().getStringValue().equals((Object)"<init>")) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), registerType.type.getClassType()};
                throw new ValidationException(String.format((String)"Cannot call %s on type %s. The object type must match the method type exactly", (Object[])arrobject));
            }
            if ((n & 8) == 0 && registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(classDef)) {
                Object[] arrobject = new Object[]{methodIdItem.getMethodString(), registerType.type.getClassType(), classDef.getClassType()};
                throw new ValidationException(String.format((String)"Cannot call method %s on an object of type %s, which does not extend %s.", (Object[])arrobject));
            }
        }
        if (typeListItem != null) {
            List<TypeIdItem> list = typeListItem.getTypes();
            int n4 = 0;
            while (!registerIterator.pastEnd()) {
                RegisterType registerType;
                int n5;
                if (!$assertionsDisabled && n4 >= (n5 = list.size())) {
                    throw new AssertionError();
                }
                RegisterType registerType2 = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)list.get(n4));
                int n6 = registerIterator.getRegister();
                if (WideLowCategories.contains((Object)registerType2.category)) {
                    registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, n6, WideLowCategories);
                    if (!registerIterator.moveNext()) {
                        Object[] arrobject = new Object[]{n4 + 1};
                        throw new ValidationException(String.format((String)"No 2nd register specified for wide register pair v%d", (Object[])arrobject));
                    }
                    int n7 = registerIterator.getRegister();
                    if (n7 != n6 + 1) {
                        Object[] arrobject = new Object[]{n6, n7};
                        throw new ValidationException(String.format((String)"Invalid wide register pair (v%d, v%d). Registers must be consecutive.", (Object[])arrobject));
                    }
                } else {
                    registerType = analyzedInstruction.getPreInstructionRegisterType(n6);
                }
                if (!$assertionsDisabled && registerType == null) {
                    throw new AssertionError();
                }
                if (!registerType.canBeAssignedTo(registerType2)) {
                    Object[] arrobject = new Object[]{registerType.toString(), n4 + 1, registerType2.toString()};
                    throw new ValidationException(String.format((String)"Invalid register type %s for parameter %d %s.", (Object[])arrobject));
                }
                ++n4;
                registerIterator.moveNext();
            }
        }
    }

    private void verifyInvokeRange(AnalyzedInstruction analyzedInstruction, int n) {
        MethodAnalyzer.super.verifyInvokeCommon(analyzedInstruction, true, n, new Format3rcRegisterIterator((RegisterRangeInstruction)((Object)analyzedInstruction.instruction)));
    }

    private void verifyIputObject(AnalyzedInstruction analyzedInstruction) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterB(), ReferenceOrUninitThisCategories);
        RegisterType registerType2 = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterA(), ReferenceCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        RegisterType registerType3 = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (registerType3.category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
        if (registerType2.category != RegisterType.Category.Null && !registerType3.type.isInterface() && !registerType2.type.extendsClass(registerType3.type)) {
            Object[] arrobject = new Object[]{registerType2.type.getClassType(), registerType3.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot store a value of type %s into a field of type %s", (Object[])arrobject));
        }
    }

    private void verifyIputWide(AnalyzedInstruction analyzedInstruction) {
        TwoRegisterInstruction twoRegisterInstruction = (TwoRegisterInstruction)((Object)analyzedInstruction.instruction);
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterB(), ReferenceOrUninitThisCategories);
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, twoRegisterInstruction.getRegisterA(), WideLowCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (registerType.category != RegisterType.Category.Null && !registerType.type.extendsClass(ClassPath.getClassDef(fieldIdItem.getContainingClass()))) {
            Object[] arrobject = new Object[]{fieldIdItem.getFieldString(), registerType.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot access field %s through type %s", (Object[])arrobject));
        }
        RegisterType registerType2 = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (!WideLowCategories.contains((Object)registerType2.category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifyLiteralBinaryOp(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), Primitive32BitCategories);
    }

    private void verifyMonitor(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), ReferenceCategories);
    }

    private void verifyMove(AnalyzedInstruction analyzedInstruction, EnumSet enumSet) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), enumSet);
    }

    private void verifyMoveException(AnalyzedInstruction analyzedInstruction) {
        CodeItem.TryItem[] arrtryItem = this.encodedMethod.codeItem.getTries();
        int n = this.getInstructionAddress(analyzedInstruction);
        if (arrtryItem == null) {
            throw new ValidationException("move-exception must be the first instruction in an exception handler block");
        }
        RegisterType registerType = null;
        CodeItem.TryItem[] arrtryItem2 = this.encodedMethod.codeItem.getTries();
        int n2 = arrtryItem2.length;
        int n3 = 0;
        do {
            CodeItem.TryItem tryItem;
            block8 : {
                block7 : {
                    if (n3 >= n2) break block7;
                    tryItem = arrtryItem2[n3];
                    if (tryItem.encodedCatchHandler.getCatchAllHandlerAddress() != n) break block8;
                    registerType = RegisterType.getRegisterType(RegisterType.Category.Reference, ClassPath.getClassDef("Ljava/lang/Throwable;"));
                }
                if (registerType != null) break;
                throw new ValidationException("move-exception must be the first instruction in an exception handler block");
            }
            for (CodeItem.EncodedTypeAddrPair encodedTypeAddrPair : tryItem.encodedCatchHandler.handlers) {
                if (encodedTypeAddrPair.getHandlerAddress() != n) continue;
                registerType = RegisterType.getRegisterTypeForTypeIdItem(encodedTypeAddrPair.exceptionType).merge(registerType);
            }
            ++n3;
        } while (true);
        if (registerType.category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{registerType.toString()};
            throw new ValidationException(String.format((String)"Exception type %s is not a reference type", (Object[])arrobject));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyMoveResult(AnalyzedInstruction analyzedInstruction, EnumSet<RegisterType.Category> enumSet) {
        RegisterType registerType;
        if (analyzedInstruction.instructionIndex == 0) {
            throw new ValidationException(analyzedInstruction.instruction.opcode.name + " cannot be the first " + "instruction in a method. It must occur after an invoke-*/fill-new-array instruction");
        }
        AnalyzedInstruction analyzedInstruction2 = this.instructions.valueAt(-1 + analyzedInstruction.instructionIndex);
        if (!analyzedInstruction2.instruction.opcode.setsResult()) {
            throw new ValidationException(analyzedInstruction.instruction.opcode.name + " must occur after an " + "invoke-*/fill-new-array instruction");
        }
        Item item = ((InstructionWithReference)analyzedInstruction2.getInstruction()).getReferencedItem();
        if (item instanceof MethodIdItem) {
            registerType = RegisterType.getRegisterTypeForTypeIdItem(((MethodIdItem)item).getPrototype().getReturnType());
        } else {
            if (!$assertionsDisabled && !(item instanceof TypeIdItem)) {
                throw new AssertionError();
            }
            registerType = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
        }
        if (!enumSet.contains((Object)registerType.category)) {
            Object[] arrobject = new Object[]{registerType.toString()};
            throw new ValidationException(String.format((String)"Wrong move-result* instruction for return value %s", (Object[])arrobject));
        }
    }

    private void verifyNewArray(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), Primitive32BitCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
            throw new AssertionError();
        }
        RegisterType registerType = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
        if (!$assertionsDisabled && !(registerType.type instanceof ClassPath.ArrayClassDef)) {
            throw new AssertionError();
        }
        if (registerType.category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{registerType.toString()};
            throw new ValidationException(String.format((String)"Cannot use new-array with a non-reference type %s", (Object[])arrobject));
        }
        if (registerType.type.getClassType().charAt(0) != '[') {
            throw new ValidationException("Cannot use non-array type \"" + registerType.type.getClassType() + "\" with new-array. Use new-instance instead.");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyNewInstance(AnalyzedInstruction analyzedInstruction) {
        InstructionWithReference instructionWithReference = (InstructionWithReference)analyzedInstruction.instruction;
        int n = ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA();
        RegisterType registerType = analyzedInstruction.postRegisterMap[n];
        if (registerType.category == RegisterType.Category.Unknown) {
            Item item = instructionWithReference.getReferencedItem();
            if (!$assertionsDisabled && item.getItemType() != ItemType.TYPE_TYPE_ID_ITEM) {
                throw new AssertionError();
            }
            RegisterType registerType2 = RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)item);
            if (registerType2.category != RegisterType.Category.Reference) {
                Object[] arrobject = new Object[]{registerType2.toString()};
                throw new ValidationException(String.format((String)"Cannot use new-instance with a non-reference type %s", (Object[])arrobject));
            }
            if (((TypeIdItem)item).getTypeDescriptor().charAt(0) != '[') return;
            {
                throw new ValidationException("Cannot use array type \"" + ((TypeIdItem)item).getTypeDescriptor() + "\" with new-instance. Use new-array instead.");
            }
        } else {
            if (!$assertionsDisabled && registerType.category != RegisterType.Category.UninitRef) {
                throw new AssertionError();
            }
            for (int i = 0; i < analyzedInstruction.postRegisterMap.length; ++i) {
                if (i == n || analyzedInstruction.getPreInstructionRegisterType(i) != registerType) continue;
                Object[] arrobject = new Object[]{i};
                throw new ValidationException(String.format((String)"Register v%d contains an uninitialized reference that was created by this new-instance instruction.", (Object[])arrobject));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyReturn(AnalyzedInstruction analyzedInstruction, EnumSet enumSet) {
        RegisterType registerType;
        RegisterType registerType2;
        int n;
        block9 : {
            n = ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA();
            registerType2 = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, n, enumSet);
            TypeIdItem typeIdItem = this.encodedMethod.method.getPrototype().getReturnType();
            if (typeIdItem.getTypeDescriptor().charAt(0) == 'V') {
                throw new ValidationException("Cannot use return with a void return type. Use return-void instead");
            }
            registerType = RegisterType.getRegisterTypeForTypeIdItem(typeIdItem);
            if (!enumSet.contains((Object)registerType.category)) {
                Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, typeIdItem.getTypeDescriptor()};
                throw new ValidationException(String.format((String)"Cannot use %s with return type %s", (Object[])arrobject));
            }
            if (enumSet != ReferenceCategories) return;
            if (registerType.type.isInterface()) {
                if (registerType2.category == RegisterType.Category.Null || registerType2.type.implementsInterface(registerType.type)) return;
                {
                    // empty if block
                }
            }
            if (registerType2.category == RegisterType.Category.Reference && !registerType2.type.extendsClass(registerType.type)) break block9;
            return;
        }
        Object[] arrobject = new Object[]{n, registerType2.type.getClassType(), registerType.type.getClassType()};
        throw new ValidationException(String.format((String)"The return value in register v%d (%s) is not compatible with the method's return type %s", (Object[])arrobject));
    }

    private void verifyReturnVoid(AnalyzedInstruction analyzedInstruction) {
        TypeIdItem typeIdItem = this.encodedMethod.method.getPrototype().getReturnType();
        if (typeIdItem.getTypeDescriptor().charAt(0) != 'V') {
            throw new ValidationException("Cannot use return-void with a non-void return type (" + typeIdItem.getTypeDescriptor() + ")");
        }
    }

    private void verifySgetObject(AnalyzedInstruction analyzedInstruction) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        if (RegisterType.getRegisterTypeForTypeIdItem((TypeIdItem)fieldIdItem.getFieldType()).category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifySgetWide(AnalyzedInstruction analyzedInstruction) {
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        RegisterType registerType = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (registerType.category != RegisterType.Category.LongLo && registerType.category != RegisterType.Category.DoubleLo) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifySputObject(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType = MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), ReferenceCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        RegisterType registerType2 = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (registerType2.category != RegisterType.Category.Reference) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
        if (registerType.category != RegisterType.Category.Null && !registerType2.type.isInterface() && !registerType.type.extendsClass(registerType2.type)) {
            Object[] arrobject = new Object[]{registerType.type.getClassType(), registerType2.type.getClassType()};
            throw new ValidationException(String.format((String)"Cannot store a value of type %s into a field of type %s", (Object[])arrobject));
        }
    }

    private void verifySputWide(AnalyzedInstruction analyzedInstruction) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA(), WideLowCategories);
        Item item = ((InstructionWithReference)analyzedInstruction.instruction).getReferencedItem();
        if (!$assertionsDisabled && !(item instanceof FieldIdItem)) {
            throw new AssertionError();
        }
        FieldIdItem fieldIdItem = (FieldIdItem)item;
        RegisterType registerType = RegisterType.getRegisterTypeForTypeIdItem(fieldIdItem.getFieldType());
        if (!WideLowCategories.contains((Object)registerType.category)) {
            Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name, fieldIdItem.getFieldString()};
            throw new ValidationException(String.format((String)"Cannot use %s with field %s. Incorrect field type for the instruction.", (Object[])arrobject));
        }
    }

    private void verifySwitch(AnalyzedInstruction analyzedInstruction, Format format) {
        int n = ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA();
        int n2 = ((OffsetInstruction)((Object)analyzedInstruction.instruction)).getTargetAddressOffset();
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, n, Primitive32BitCategories);
        int n3 = n2 + this.getInstructionAddress(analyzedInstruction);
        AnalyzedInstruction analyzedInstruction2 = this.instructions.get(n3);
        if (analyzedInstruction2 == null || analyzedInstruction2.instruction.getFormat() != format) {
            Object[] arrobject = new Object[]{format.name(), n3};
            throw new ValidationException(String.format((String)"There is no %s structure at code address 0x%x", (Object[])arrobject));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void verifyThrow(AnalyzedInstruction analyzedInstruction) {
        RegisterType registerType;
        int n;
        block7 : {
            block6 : {
                n = ((SingleRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterA();
                registerType = analyzedInstruction.getPreInstructionRegisterType(n);
                if (!$assertionsDisabled && registerType == null) {
                    throw new AssertionError();
                }
                if (registerType.category == RegisterType.Category.Null) break block6;
                if (registerType.category != RegisterType.Category.Reference) {
                    Object[] arrobject = new Object[]{registerType.toString(), n};
                    throw new ValidationException(String.format((String)"Cannot use throw with non-reference type %s in register v%d", (Object[])arrobject));
                }
                if (!$assertionsDisabled && registerType.type == null) {
                    throw new AssertionError();
                }
                if (!registerType.type.extendsClass(ClassPath.getClassDef("Ljava/lang/Throwable;"))) break block7;
            }
            return;
        }
        Object[] arrobject = new Object[]{registerType.type.getClassType(), n};
        throw new ValidationException(String.format((String)"Cannot use throw with non-throwable type %s in register v%d", (Object[])arrobject));
    }

    private void verifyUnaryOp(AnalyzedInstruction analyzedInstruction, EnumSet enumSet) {
        MethodAnalyzer.getAndCheckSourceRegister(analyzedInstruction, ((TwoRegisterInstruction)((Object)analyzedInstruction.instruction)).getRegisterB(), enumSet);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void analyze() {
        if (!MethodAnalyzer.$assertionsDisabled && this.encodedMethod == null) {
            throw new AssertionError();
        }
        if (!MethodAnalyzer.$assertionsDisabled && this.encodedMethod.codeItem == null) {
            throw new AssertionError();
        }
        if (this.analyzerState >= 1) {
            return;
        }
        var1_1 = this.encodedMethod.codeItem;
        var2_2 = this.encodedMethod.method;
        var3_3 = var1_1.getRegisterCount();
        var4_4 = var2_2.getPrototype().getParameterRegisterCount();
        var5_5 = var3_3 - var4_4;
        var6_6 = this.instructions.getValues().iterator();
        while (var6_6.hasNext()) {
            ((AnalyzedInstruction)var6_6.next()).dead = true;
        }
        if ((this.encodedMethod.accessFlags & AccessFlags.STATIC.getValue()) == 0) {
            --var5_5;
            var31_7 = -1 + (var3_3 - var4_4);
            if ((this.encodedMethod.accessFlags & AccessFlags.CONSTRUCTOR.getValue()) != 0) {
                this.setPostRegisterTypeAndPropagateChanges(this.startOfMethod, var31_7, RegisterType.getRegisterType(RegisterType.Category.UninitThis, ClassPath.getClassDef(var2_2.getContainingClass())));
            } else {
                this.setPostRegisterTypeAndPropagateChanges(this.startOfMethod, var31_7, RegisterType.getRegisterType(RegisterType.Category.Reference, ClassPath.getClassDef(var2_2.getContainingClass())));
            }
        }
        if ((var7_8 = var2_2.getPrototype().getParameters()) != null) {
            var27_9 = MethodAnalyzer.getParameterTypes(var7_8, var4_4);
            for (var28_10 = 0; var28_10 < var27_9.length; ++var28_10) {
                var29_12 = var27_9[var28_10];
                var30_11 = var28_10 + (var3_3 - var4_4);
                this.setPostRegisterTypeAndPropagateChanges(this.startOfMethod, var30_11, var29_12);
            }
        }
        var8_13 = RegisterType.getRegisterType(RegisterType.Category.Uninit, null);
        for (var9_14 = 0; var9_14 < var5_5; ++var9_14) {
            this.setPostRegisterTypeAndPropagateChanges(this.startOfMethod, var9_14, var8_13);
        }
        var10_15 = new BitSet(this.instructions.size());
        var11_16 = this.startOfMethod.successors.iterator();
        while (var11_16.hasNext()) {
            var10_15.set(((AnalyzedInstruction)var11_16.next()).instructionIndex);
        }
        var12_17 = new BitSet(this.instructions.size());
        block12 : do lbl-1000: // 3 sources:
        {
            var13_24 = false;
            while (!var10_15.isEmpty()) {
                var19_26 = var10_15.nextSetBit(0);
                while (var19_26 >= 0) {
                    block30 : {
                        var10_15.clear(var19_26);
                        if (!this.analyzedInstructions.get(var19_26)) {
                            var20_23 = this.instructions.valueAt(var19_26);
                            var20_23.dead = false;
                            try {
                                if (var20_23.originalInstruction.opcode.odexOnly()) {
                                    var20_23.restoreOdexedInstruction();
                                }
                                if (!this.analyzeInstruction(var20_23)) {
                                    var12_17.set(var19_26);
                                    break block30;
                                }
                                var13_24 = true;
                                var12_17.clear(var19_26);
                            }
                            catch (ValidationException var21_22) {
                                this.validationException = var21_22;
                                var22_25 = this.getInstructionAddress(var20_23);
                                var21_22.setCodeAddress(var22_25);
                                var23_19 = new Object[]{var20_23.instruction.opcode.name};
                                var21_22.addContext(String.format((String)"opcode: %s", (Object[])var23_19));
                                var24_27 = new Object[]{var22_25};
                                var21_22.addContext(String.format((String)"CodeAddress: %d", (Object[])var24_27));
                                var25_20 = new Object[]{this.encodedMethod.method.getMethodString()};
                                var21_22.addContext(String.format((String)"Method: %s", (Object[])var25_20));
                                break;
                            }
                            this.analyzedInstructions.set(var20_23.getInstructionIndex());
                            var26_18 = var20_23.successors.iterator();
                            while (var26_18.hasNext()) {
                                var10_15.set(((AnalyzedInstruction)var26_18.next()).getInstructionIndex());
                            }
                        }
                    }
                    var19_26 = var10_15.nextSetBit(var19_26 + 1);
                }
                if (this.validationException == null) continue;
            }
            if (!var13_24) {
                var15_28 = 0;
                break;
            }
            if (var12_17.isEmpty()) ** GOTO lbl-1000
            var14_21 = var12_17.nextSetBit(0);
            do {
                if (var14_21 < 0) continue block12;
                var10_15.set(var14_21);
                var14_21 = var12_17.nextSetBit(var14_21 + 1);
            } while (true);
            break;
        } while (true);
        do {
            if (var15_28 >= this.instructions.size()) {
                this.analyzerState = 1;
                return;
            }
            var16_30 = this.instructions.valueAt(var15_28);
            switch (2.$SwitchMap$org$jf$dexlib$Code$Format$Format[var16_30.getInstruction().getFormat().ordinal()]) {
                case 1: {
                    var17_29 = ((Instruction22cs)var16_30.instruction).getRegisterB();
                    ** GOTO lbl102
                }
                case 2: {
                    var17_29 = ((Instruction35ms)var16_30.instruction).getRegisterD();
                    ** GOTO lbl102
                }
                case 3: {
                    var17_29 = ((Instruction3rms)var16_30.instruction).getStartRegister();
lbl102: // 3 sources:
                    var18_31 = new UnresolvedOdexInstruction(var16_30.instruction, var17_29);
                    var16_30.setDeodexedInstruction(var18_31);
                }
            }
            ++var15_28;
        } while (true);
    }

    public int getInstructionAddress(AnalyzedInstruction analyzedInstruction) {
        return this.instructions.keyAt(analyzedInstruction.instructionIndex);
    }

    public List<AnalyzedInstruction> getInstructions() {
        return this.instructions.getValues();
    }

    public ClassDataItem.EncodedMethod getMethod() {
        return this.encodedMethod;
    }

    public AnalyzedInstruction getStartOfMethod() {
        return this.startOfMethod;
    }

    public ValidationException getValidationException() {
        return this.validationException;
    }

    public boolean isAnalyzed() {
        return this.analyzerState >= 1;
    }

    public boolean isVerified() {
        return this.analyzerState == 2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void verify() {
        if (this.analyzerState < 1) {
            throw new ExceptionWithContext("You must call analyze() before calling verify().");
        }
        if (this.analyzerState == 2) {
            return;
        }
        BitSet bitSet = new BitSet(this.instructions.size());
        BitSet bitSet2 = new BitSet(this.instructions.size());
        Iterator iterator = this.startOfMethod.successors.iterator();
        while (iterator.hasNext()) {
            bitSet.set(((AnalyzedInstruction)iterator.next()).instructionIndex);
        }
        while (!bitSet.isEmpty()) {
            int n = bitSet.nextSetBit(0);
            while (n >= 0) {
                AnalyzedInstruction analyzedInstruction;
                bitSet.clear(n);
                if (!bitSet2.get(n)) {
                    analyzedInstruction = this.instructions.valueAt(n);
                    this.verifyInstruction(analyzedInstruction);
                    bitSet2.set(analyzedInstruction.getInstructionIndex());
                    Iterator iterator2 = analyzedInstruction.successors.iterator();
                    while (iterator2.hasNext()) {
                        bitSet.set(((AnalyzedInstruction)iterator2.next()).getInstructionIndex());
                    }
                }
                n = bitSet.nextSetBit(n + 1);
                continue;
                catch (ValidationException validationException) {
                    this.validationException = validationException;
                    int n2 = this.getInstructionAddress(analyzedInstruction);
                    validationException.setCodeAddress(n2);
                    Object[] arrobject = new Object[]{analyzedInstruction.instruction.opcode.name};
                    validationException.addContext(String.format((String)"opcode: %s", (Object[])arrobject));
                    Object[] arrobject2 = new Object[]{n2};
                    validationException.addContext(String.format((String)"CodeAddress: %d", (Object[])arrobject2));
                    Object[] arrobject3 = new Object[]{this.encodedMethod.method.getMethodString()};
                    validationException.addContext(String.format((String)"Method: %s", (Object[])arrobject3));
                    break;
                }
            }
            if (this.validationException == null) continue;
        }
        this.analyzerState = 2;
    }

    private static class Format35cRegisterIterator
    implements RegisterIterator {
        private int currentRegister = 0;
        private final int registerCount;
        private final int[] registers;

        public Format35cRegisterIterator(FiveRegisterInstruction fiveRegisterInstruction) {
            this.registerCount = fiveRegisterInstruction.getRegCount();
            int[] arrn = new int[]{fiveRegisterInstruction.getRegisterD(), fiveRegisterInstruction.getRegisterE(), fiveRegisterInstruction.getRegisterF(), fiveRegisterInstruction.getRegisterG(), fiveRegisterInstruction.getRegisterA()};
            this.registers = arrn;
        }

        @Override
        public int getCount() {
            return this.registerCount;
        }

        @Override
        public int getRegister() {
            return this.registers[this.currentRegister];
        }

        @Override
        public boolean moveNext() {
            this.currentRegister = 1 + this.currentRegister;
            return !this.pastEnd();
        }

        @Override
        public boolean pastEnd() {
            return this.currentRegister >= this.registerCount;
        }
    }

    private static class Format3rcRegisterIterator
    implements RegisterIterator {
        private int currentRegister = 0;
        private final int registerCount;
        private final int startRegister;

        public Format3rcRegisterIterator(RegisterRangeInstruction registerRangeInstruction) {
            this.startRegister = registerRangeInstruction.getStartRegister();
            this.registerCount = registerRangeInstruction.getRegCount();
        }

        @Override
        public int getCount() {
            return this.registerCount;
        }

        @Override
        public int getRegister() {
            return this.startRegister + this.currentRegister;
        }

        @Override
        public boolean moveNext() {
            this.currentRegister = 1 + this.currentRegister;
            return !this.pastEnd();
        }

        @Override
        public boolean pastEnd() {
            return this.currentRegister >= this.registerCount;
        }
    }

    private static interface RegisterIterator {
        public int getCount();

        public int getRegister();

        public boolean moveNext();

        public boolean pastEnd();
    }

}

