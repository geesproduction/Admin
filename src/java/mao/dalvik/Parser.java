/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  org.jf.dexlib.Code.Format.Instruction23x
 *  org.jf.dexlib.Code.Format.Instruction3rc
 */
package mao.dalvik;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.Code.FiveRegisterInstruction;
import org.jf.dexlib.Code.Format.ArrayDataPseudoInstruction;
import org.jf.dexlib.Code.Format.Format;
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
import org.jf.dexlib.Code.Format.Instruction3rc;
import org.jf.dexlib.Code.Format.Instruction51l;
import org.jf.dexlib.Code.Format.PackedSwitchDataPseudoInstruction;
import org.jf.dexlib.Code.Format.SparseSwitchDataPseudoInstruction;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.LiteralInstruction;
import org.jf.dexlib.Code.MultiOffsetInstruction;
import org.jf.dexlib.Code.OffsetInstruction;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.Code.ReferenceType;
import org.jf.dexlib.Code.SingleRegisterInstruction;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.Pair;
import org.jf.dexlib.Util.SparseIntArray;
import org.jf.dexlib.Util.TryListBuilder;
import org.jf.dexlib.Util.Utf8Utils;
import org.jf.util.ByteRenderer;
import org.jf.util.IndentingWriter;
import org.jf.util.LiteralTools;
import org.jf.util.LongRenderer;

public class Parser {
    public static final String ALL = "all";
    public static final String CATCH = ".catch ";
    public static final String END = "end : ";
    public static final String ENDCATCH = ".end catch";
    public static final String HANDLER = "handler : ";
    public static final String LABEL = "label_";
    public static final String START = "start : ";
    public static final String hex_literal = "([+,-])?0[x,X]([0-9,a-f,A-F])+";
    public static int outWords;
    public static final Pattern pField;
    public static final Pattern pInt;
    public static final Pattern pLong;
    public static final Pattern pMethod;
    public static final Pattern pRegister;
    public final CodeItem code;

    static {
        pRegister = Pattern.compile((String)"v\\d+");
        pInt = Pattern.compile((String)"\\s([+,-])?0[x,X]([0-9,a-f,A-F])+|\\s([+,-])?\\d+");
        pLong = Pattern.compile((String)"\\s([+,-])?0[x,X]([0-9,a-f,A-F])+([l,L])?|\\s([+,-])?\\d+([l,L])?");
        pField = Pattern.compile((String)"\\s|:|->");
        pMethod = Pattern.compile((String)"\\s|\\(|\\)|->");
    }

    public Parser(CodeItem codeItem) {
        this.code = codeItem;
    }

    public static TypeListItem buildTypeList(DexFile dexFile, String string2) {
        ArrayList arrayList = new ArrayList();
        int n = 0;
        block5 : while (n < string2.length()) {
            switch (string2.charAt(n)) {
                default: {
                    throw new RuntimeException("Invalid type " + string2.substring(n));
                }
                case 'B': 
                case 'C': 
                case 'D': 
                case 'F': 
                case 'I': 
                case 'J': 
                case 'S': 
                case 'Z': {
                    int n2 = n + 1;
                    arrayList.add((Object)TypeIdItem.internTypeIdItem(dexFile, string2.substring(n, n2)));
                    n = n2;
                    continue block5;
                }
                case 'L': {
                    int n3 = n;
                    while (string2.charAt(++n) != ';') {
                    }
                    arrayList.add((Object)TypeIdItem.internTypeIdItem(dexFile, string2.substring(n3, ++n)));
                    continue block5;
                }
                case '[': 
            }
            int n4 = n;
            while (string2.charAt(++n) == '[') {
            }
            int n5 = n + 1;
            if (string2.charAt(n) == 'L') {
                int n6 = n5;
                do {
                    n5 = n6 + 1;
                    if (string2.charAt(n6) == ';') break;
                    n6 = n5;
                } while (true);
            }
            n = n5;
            arrayList.add((Object)TypeIdItem.internTypeIdItem(dexFile, string2.substring(n4, n)));
        }
        if (arrayList.size() == 0) {
            return null;
        }
        return TypeListItem.internTypeListItem(dexFile, (List<TypeIdItem>)arrayList);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Pair<List<CodeItem.TryItem>, List<CodeItem.EncodedCatchHandler>> parseCatchs(DexFile dexFile, String[] arrstring, int n, HashMap<String, Integer> hashMap) {
        TryListBuilder tryListBuilder = new TryListBuilder();
        while (n < arrstring.length) {
            String string2 = arrstring[n].trim();
            if (string2.startsWith(".catch")) {
                String[] arrstring2 = string2.split(" ");
                if (arrstring2.length < 2) {
                    throw new IllegalArgumentException("no exception type : " + arrstring[n]);
                }
                String string3 = arrstring2[1].trim();
                int n2 = n + 1;
                int n3 = (Integer)hashMap.get((Object)arrstring[n2].trim().split(":")[1].trim());
                int n4 = n2 + 1;
                int n5 = (Integer)hashMap.get((Object)arrstring[n4].trim().split(":")[1].trim());
                n = n4 + 1;
                int n6 = (Integer)hashMap.get((Object)arrstring[n].trim().split(":")[1].trim());
                if (string3.equals((Object)ALL)) {
                    tryListBuilder.addCatchAllHandler(n3, n5, n6);
                } else {
                    tryListBuilder.addHandler(TypeIdItem.internTypeIdItem(dexFile, string3), n3, n5, n6);
                }
            }
            ++n;
        }
        return tryListBuilder.encodeTries();
    }

    private static FieldIdItem parseField(DexFile dexFile, String string2) {
        String[] arrstring = pField.split((CharSequence)string2);
        int n = -1 + arrstring.length;
        if (n < 2) {
            throw new RuntimeException("FieldIdItem error: " + string2);
        }
        String string3 = arrstring[n - 2];
        String string4 = arrstring[n - 1];
        String string5 = arrstring[n];
        return FieldIdItem.internFieldIdItem(dexFile, TypeIdItem.internTypeIdItem(dexFile, string3), TypeIdItem.internTypeIdItem(dexFile, string5), StringIdItem.internStringIdItem(dexFile, string4));
    }

    private static byte[] parseFiveRegister(String string2, int[] arrn) {
        byte[] arrby = new byte[5];
        Arrays.fill((byte[])arrby, (byte)0);
        int n = string2.indexOf(123);
        int n2 = string2.indexOf(125);
        Matcher matcher = pRegister.matcher((CharSequence)string2.substring(n + 1, n2));
        int n3 = 0;
        while (matcher.find() && n3 < 5) {
            int n4 = Integer.parseInt((String)matcher.group().substring(1));
            int n5 = n3 + 1;
            arrby[n3] = (byte)n4;
            n3 = n5;
        }
        arrn[0] = n3;
        outWords = Math.max((int)outWords, (int)n3);
        return arrby;
    }

    private static int parseInt(String string2) throws Exception {
        Matcher matcher = pInt.matcher((CharSequence)string2);
        if (!matcher.find()) {
            throw new Exception("int exception: " + string2);
        }
        return LiteralTools.parseInt(matcher.group().trim());
    }

    private static long parseLong(String string2) throws Exception {
        Matcher matcher = pLong.matcher((CharSequence)string2);
        if (!matcher.find()) {
            throw new Exception("long exception: " + string2);
        }
        return LiteralTools.parseLong(matcher.group().trim());
    }

    private static MethodIdItem parseMethod(DexFile dexFile, String string2) {
        String[] arrstring = pMethod.split((CharSequence)string2);
        int n = -1 + arrstring.length;
        if (n < 3) {
            throw new RuntimeException("MethodIdItem error: " + string2);
        }
        String string3 = arrstring[n - 3];
        String string4 = arrstring[n - 2];
        TypeListItem typeListItem = Parser.buildTypeList(dexFile, arrstring[n - 1]);
        ProtoIdItem protoIdItem = ProtoIdItem.internProtoIdItem(dexFile, TypeIdItem.internTypeIdItem(dexFile, arrstring[n]), typeListItem);
        return MethodIdItem.internMethodIdItem(dexFile, TypeIdItem.internTypeIdItem(dexFile, string3), protoIdItem, StringIdItem.internStringIdItem(dexFile, string4));
    }

    private static int[] parseRangeRegister(String string2) {
        int n;
        int n2;
        int[] arrn = new int[2];
        int n3 = string2.indexOf(123);
        Matcher matcher = pRegister.matcher((CharSequence)string2.substring(n3 + 1, n2 = string2.indexOf(125)));
        if (!matcher.find()) {
            // empty if block
        }
        arrn[1] = Integer.parseInt((String)matcher.group().substring(1));
        int n4 = 0;
        while (matcher.find()) {
            n4 = Integer.parseInt((String)matcher.group().substring(1));
        }
        arrn[0] = n = 1 + (n4 - arrn[1]);
        outWords = Math.max((int)outWords, (int)n);
        return arrn;
    }

    private static int parseSingleRegister(String string2) throws Exception {
        Matcher matcher = pRegister.matcher((CharSequence)string2);
        if (!matcher.find()) {
            throw new Exception("register exception: " + string2);
        }
        return Integer.parseInt((String)matcher.group().substring(1));
    }

    private static StringIdItem parseString(DexFile dexFile, String string2) {
        int n = string2.indexOf("\"");
        int n2 = string2.lastIndexOf("\"");
        return StringIdItem.internStringIdItem(dexFile, Utf8Utils.escapeSequence(string2.substring(n + 1, n2)));
    }

    private static int parseTarget(String string2, HashMap<String, Integer> hashMap, int n) {
        int n2;
        try {
            n2 = (Integer)hashMap.get((Object)string2.split(":")[1].trim());
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("unfound label offset: " + string2);
        }
        return n2 - n;
    }

    private static TypeIdItem parseType(DexFile dexFile, String string2) {
        String string3 = string2.trim();
        return TypeIdItem.internTypeIdItem(dexFile, string3.substring(1 + string3.lastIndexOf(" ")));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean searchFieldInMethod(ClassDataItem.EncodedMethod encodedMethod, String string2, String string3, String string4, boolean bl, boolean bl2) {
        if (encodedMethod.codeItem != null) {
            block6 : for (Instruction instruction : encodedMethod.codeItem.getInstructions()) {
                switch (instruction.getFormat()) {
                    default: {
                        continue block6;
                    }
                    case Format21c: 
                    case Format22c: {
                        switch (instruction.opcode.referenceType) {
                            default: {
                                continue block6;
                            }
                            case field: 
                        }
                        FieldIdItem fieldIdItem = (FieldIdItem)((InstructionWithReference)instruction).getReferencedItem();
                        if (bl && fieldIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) || bl2 && fieldIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) && fieldIdItem.getFieldName().getStringValue().equals((Object)string3)) {
                            return true;
                        }
                        if (!fieldIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) || !fieldIdItem.getFieldName().getStringValue().equals((Object)string3) || !fieldIdItem.getFieldType().getTypeDescriptor().equals((Object)string4)) continue block6;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean searchMethodInMethod(ClassDataItem.EncodedMethod encodedMethod, String string2, String string3, String string4, boolean bl, boolean bl2) {
        if (encodedMethod.codeItem != null) {
            block6 : for (Instruction instruction : encodedMethod.codeItem.getInstructions()) {
                switch (instruction.getFormat()) {
                    default: {
                        continue block6;
                    }
                    case Format35c: 
                    case Format35s: 
                    case Format3rc: {
                        switch (instruction.opcode.referenceType) {
                            default: {
                                continue block6;
                            }
                            case method: 
                        }
                        MethodIdItem methodIdItem = (MethodIdItem)((InstructionWithReference)instruction).getReferencedItem();
                        if (bl && methodIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) || bl2 && methodIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) && methodIdItem.getMethodName().getStringValue().equals((Object)string3)) {
                            return true;
                        }
                        if (!methodIdItem.getContainingClass().getTypeDescriptor().equals((Object)string2) || !methodIdItem.getMethodName().getStringValue().equals((Object)string3) || !methodIdItem.getPrototype().getPrototypeString().equals((Object)string4)) continue block6;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static boolean searchStringInMethod(ClassDataItem.EncodedMethod encodedMethod, String string2) {
        if (encodedMethod.codeItem != null) {
            block6 : for (Instruction instruction : encodedMethod.codeItem.getInstructions()) {
                switch (instruction.getFormat()) {
                    default: {
                        continue block6;
                    }
                    case Format21c: 
                    case Format31c: {
                        switch (instruction.opcode.referenceType) {
                            default: {
                                continue block6;
                            }
                            case string: 
                        }
                        if (((StringIdItem)((InstructionWithReference)instruction).getReferencedItem()).getStringValue().indexOf(string2) == -1) continue block6;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    private int[] tryItemLabels() {
        CodeItem.TryItem[] arrtryItem = this.code.tries;
        if (arrtryItem == null || arrtryItem.length <= 0) {
            return new int[]{-1};
        }
        SparseIntArray sparseIntArray = new SparseIntArray(3);
        int n = arrtryItem.length;
        int n2 = 0;
        while (n2 < n) {
            CodeItem.TryItem tryItem = arrtryItem[n2];
            int n3 = tryItem.getStartCodeAddress();
            int n4 = n3 + tryItem.getTryLength();
            sparseIntArray.put(n3, 0);
            sparseIntArray.put(n4, 0);
            CodeItem.EncodedCatchHandler encodedCatchHandler = tryItem.encodedCatchHandler;
            int n5 = encodedCatchHandler.getCatchAllHandlerAddress();
            if (n5 != -1) {
                sparseIntArray.put(n5, 0);
            } else {
                CodeItem.EncodedTypeAddrPair[] arrencodedTypeAddrPair = encodedCatchHandler.handlers;
                int n6 = arrencodedTypeAddrPair.length;
                for (int i = 0; i < n6; ++i) {
                    sparseIntArray.put(arrencodedTypeAddrPair[i].getHandlerAddress(), 0);
                }
            }
            ++n2;
        }
        return sparseIntArray.keys();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void writeInvokeRegister(IndentingWriter indentingWriter, Instruction instruction) throws IOException {
        FiveRegisterInstruction fiveRegisterInstruction = (FiveRegisterInstruction)((Object)instruction);
        byte by = fiveRegisterInstruction.getRegCount();
        indentingWriter.write(123);
        switch (by) {
            case 1: {
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterD());
                break;
            }
            case 2: {
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterD());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterE());
                break;
            }
            case 3: {
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterD());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterE());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterF());
                break;
            }
            case 4: {
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterD());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterE());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterF());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterG());
                break;
            }
            case 5: {
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterD());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterE());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterF());
                indentingWriter.write(",");
                indentingWriter.write(118);
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterG());
                indentingWriter.write(",");
                indentingWriter.write("v");
                indentingWriter.printIntAsDec(fiveRegisterInstruction.getRegisterA());
                break;
            }
        }
        indentingWriter.write(125);
    }

    private static void writeKeyAndTarget(IndentingWriter indentingWriter, int n, int n2) throws IOException {
        indentingWriter.write(String.valueOf((int)n));
        indentingWriter.write(" : switch_");
        indentingWriter.write(String.valueOf((int)n2));
    }

    private static void writeLabel(IndentingWriter indentingWriter, int n) throws IOException {
        indentingWriter.write(58);
        indentingWriter.write(LABEL);
        indentingWriter.write(String.valueOf((int)n));
    }

    private static void writeToReferencedItem(IndentingWriter indentingWriter, Instruction instruction) throws IOException {
        InstructionWithReference instructionWithReference = (InstructionWithReference)instruction;
        switch (instruction.opcode.referenceType) {
            default: {
                return;
            }
            case field: {
                indentingWriter.write(((FieldIdItem)instructionWithReference.getReferencedItem()).getFieldString());
                return;
            }
            case method: {
                indentingWriter.write(((MethodIdItem)instructionWithReference.getReferencedItem()).getMethodString());
                return;
            }
            case type: {
                indentingWriter.write(((TypeIdItem)instructionWithReference.getReferencedItem()).getTypeDescriptor());
                return;
            }
            case string: 
        }
        StringIdItem stringIdItem = (StringIdItem)instructionWithReference.getReferencedItem();
        indentingWriter.write(34);
        Utf8Utils.writeEscapedString(indentingWriter, stringIdItem.getStringValue());
        indentingWriter.write(34);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeTryItems(IndentingWriter indentingWriter) throws IOException {
        CodeItem.TryItem[] arrtryItem = this.code.tries;
        if (arrtryItem != null && arrtryItem.length > 0) {
            indentingWriter.write(10);
            indentingWriter.write(10);
            indentingWriter.write("#Handler Exceptions");
            indentingWriter.write(10);
            indentingWriter.write(10);
            for (CodeItem.TryItem tryItem : arrtryItem) {
                int n = tryItem.getStartCodeAddress();
                int n2 = n + tryItem.getTryLength();
                CodeItem.EncodedCatchHandler encodedCatchHandler = tryItem.encodedCatchHandler;
                int n3 = encodedCatchHandler.getCatchAllHandlerAddress();
                if (n3 != -1) {
                    indentingWriter.write(CATCH);
                    indentingWriter.write(ALL);
                    indentingWriter.write(10);
                    indentingWriter.indent(4);
                    indentingWriter.write(START);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(n);
                    indentingWriter.write(10);
                    indentingWriter.write(END);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(n2);
                    indentingWriter.write(10);
                    indentingWriter.write(HANDLER);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(n3);
                    indentingWriter.write(10);
                    indentingWriter.deindent(4);
                    indentingWriter.write(ENDCATCH);
                    indentingWriter.write(10);
                    indentingWriter.write(10);
                    continue;
                }
                for (CodeItem.EncodedTypeAddrPair encodedTypeAddrPair : encodedCatchHandler.handlers) {
                    indentingWriter.write(CATCH);
                    indentingWriter.write(encodedTypeAddrPair.exceptionType.getTypeDescriptor());
                    indentingWriter.write(10);
                    indentingWriter.indent(4);
                    indentingWriter.write(START);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(n);
                    indentingWriter.write(10);
                    indentingWriter.write(END);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(n2);
                    indentingWriter.write(10);
                    indentingWriter.write(HANDLER);
                    indentingWriter.write(LABEL);
                    indentingWriter.printIntAsDec(encodedTypeAddrPair.getHandlerAddress());
                    indentingWriter.write(10);
                    indentingWriter.deindent(4);
                    indentingWriter.write(ENDCATCH);
                    indentingWriter.write(10);
                    indentingWriter.write(10);
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dump(IndentingWriter indentingWriter) throws IOException {
        Instruction instruction;
        Instruction instruction2;
        if (this.code == null) {
            return;
        }
        Instruction[] arrinstruction = this.code.instructions;
        int n = 0;
        int[] arrn = new int[arrinstruction.length];
        Arrays.fill((int[])arrn, (int)-1);
        SparseIntArray sparseIntArray = new SparseIntArray(1);
        HashMap hashMap = new HashMap();
        int n2 = arrinstruction.length;
        block31 : for (int i = 0; i < n2; n += instruction.getSize((int)n), ++i) {
            instruction = arrinstruction[i];
            switch (instruction.getFormat()) {
                case Format10t: 
                case Format20t: 
                case Format21t: 
                case Format22t: 
                case Format30t: 
                case Format31t: {
                    arrn[i] = n + ((OffsetInstruction)((Object)instruction)).getTargetAddressOffset();
                    switch (1.$SwitchMap$org$jf$dexlib$Code$Opcode[instruction.opcode.ordinal()]) {
                        default: {
                            continue block31;
                        }
                        case 1: 
                        case 2: 
                    }
                    hashMap.put((Object)arrn[i], (Object)n);
                    continue block31;
                }
                case PackedSwitchData: 
                case SparseSwitchData: {
                    MultiOffsetInstruction multiOffsetInstruction = (MultiOffsetInstruction)((Object)instruction);
                    int n3 = (Integer)hashMap.get((Object)n);
                    int[] arrn2 = multiOffsetInstruction.getTargets();
                    int n4 = arrn2.length;
                    for (int j = 0; j < n4; ++j) {
                        sparseIntArray.put(n3 + arrn2[j], 0);
                    }
                    continue block31;
                }
            }
        }
        Arrays.sort((int[])arrn);
        int[] arrn3 = new int[]{-1};
        if (sparseIntArray.size() > 0) {
            arrn3 = sparseIntArray.keys();
        }
        Arrays.sort((int[])arrn3);
        int[] arrn4 = Parser.super.tryItemLabels();
        Arrays.sort((int[])arrn4);
        int n5 = 0;
        int n6 = arrinstruction.length;
        block33 : for (int i = 0; i < n6; n5 += instruction2.getSize((int)n5), ++i) {
            instruction2 = arrinstruction[i];
            if (Arrays.binarySearch((int[])arrn, (int)n5) >= 0) {
                indentingWriter.write(LABEL);
                indentingWriter.write(String.valueOf((int)n5));
                indentingWriter.write(58);
                indentingWriter.write(10);
            } else if (Arrays.binarySearch((int[])arrn4, (int)n5) >= 0) {
                indentingWriter.write(LABEL);
                indentingWriter.write(String.valueOf((int)n5));
                indentingWriter.write(58);
                indentingWriter.write(10);
            }
            if (Arrays.binarySearch((int[])arrn3, (int)n5) >= 0) {
                indentingWriter.write("switch_");
                indentingWriter.printIntAsDec(n5);
                indentingWriter.write(58);
                indentingWriter.write(10);
            }
            switch (instruction2.getFormat()) {
                case Format10x: {
                    if (instruction2.opcode == Opcode.NOP) continue block33;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format10t: {
                    Instruction10t instruction10t = (Instruction10t)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    Parser.writeLabel(indentingWriter, n5 + instruction10t.getTargetAddressOffset());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format11x: {
                    Instruction11x instruction11x = (Instruction11x)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction11x.getRegisterA());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format11n: {
                    Instruction11n instruction11n = (Instruction11n)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction11n.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(String.valueOf((long)instruction11n.getLiteral()));
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format12x: {
                    Instruction12x instruction12x = (Instruction12x)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction12x.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction12x.getRegisterB());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format20t: 
                case Format30t: {
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    Parser.writeLabel(indentingWriter, n5 + ((OffsetInstruction)((Object)instruction2)).getTargetAddressOffset());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format21t: {
                    Instruction21t instruction21t = (Instruction21t)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction21t.getRegisterA());
                    indentingWriter.write(32);
                    Parser.writeLabel(indentingWriter, n5 + instruction21t.getTargetAddressOffset());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format21c: 
                case Format31c: {
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(((SingleRegisterInstruction)((Object)instruction2)).getRegisterA());
                    indentingWriter.write(32);
                    Parser.writeToReferencedItem(indentingWriter, instruction2);
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format21h: 
                case Format21s: 
                case Format31i: 
                case Format51l: {
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(((SingleRegisterInstruction)((Object)instruction2)).getRegisterA());
                    indentingWriter.write(32);
                    LongRenderer.writeSignedIntOrLongTo(indentingWriter, ((LiteralInstruction)((Object)instruction2)).getLiteral());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format22x: {
                    Instruction22x instruction22x = (Instruction22x)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22x.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22x.getRegisterB());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format22t: {
                    Instruction22t instruction22t = (Instruction22t)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22t.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22t.getRegisterB());
                    indentingWriter.write(32);
                    Parser.writeLabel(indentingWriter, n5 + instruction22t.getTargetAddressOffset());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format22b: {
                    Instruction22b instruction22b = (Instruction22b)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22b.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22b.getRegisterB());
                    indentingWriter.write(32);
                    indentingWriter.write(String.valueOf((long)instruction22b.getLiteral()));
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format22c: {
                    Instruction22c instruction22c = (Instruction22c)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22c.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22c.getRegisterB());
                    indentingWriter.write(32);
                    Parser.writeToReferencedItem(indentingWriter, instruction22c);
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format22s: {
                    Instruction22s instruction22s = (Instruction22s)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22s.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction22s.getRegisterB());
                    indentingWriter.write(32);
                    indentingWriter.write(String.valueOf((long)instruction22s.getLiteral()));
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format23x: {
                    Instruction23x instruction23x = (Instruction23x)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction23x.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction23x.getRegisterB());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction23x.getRegisterC());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format31t: {
                    Instruction31t instruction31t = (Instruction31t)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction31t.getRegisterA());
                    indentingWriter.write(32);
                    Parser.writeLabel(indentingWriter, n5 + instruction31t.getTargetAddressOffset());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format32x: {
                    Instruction32x instruction32x = (Instruction32x)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction32x.getRegisterA());
                    indentingWriter.write(32);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(instruction32x.getRegisterB());
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format35c: 
                case Format35s: {
                    indentingWriter.write(instruction2.opcode.name);
                    indentingWriter.write(32);
                    Parser.writeInvokeRegister(indentingWriter, instruction2);
                    indentingWriter.write(32);
                    Parser.writeToReferencedItem(indentingWriter, instruction2);
                    indentingWriter.write(10);
                    continue block33;
                }
                case Format3rc: {
                    int n7;
                    int n8;
                    Instruction3rc instruction3rc = (Instruction3rc)instruction2;
                    indentingWriter.write(instruction2.opcode.name);
                    short s = instruction3rc.getRegCount();
                    int n9 = instruction3rc.getStartRegister();
                    indentingWriter.write(32);
                    indentingWriter.write(123);
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(n9);
                    indentingWriter.write("..");
                    for (n7 = 0; n7 < (n8 = s - 1); ++n7) {
                    }
                    indentingWriter.write(118);
                    indentingWriter.printIntAsDec(n7 + instruction3rc.getStartRegister());
                    indentingWriter.write(125);
                    indentingWriter.write(32);
                    Parser.writeToReferencedItem(indentingWriter, (Instruction)instruction3rc);
                    indentingWriter.write(10);
                    continue block33;
                }
                case PackedSwitchData: {
                    PackedSwitchDataPseudoInstruction packedSwitchDataPseudoInstruction = (PackedSwitchDataPseudoInstruction)instruction2;
                    indentingWriter.write(".pswitch_data ");
                    int n10 = packedSwitchDataPseudoInstruction.getFirstKey();
                    indentingWriter.printIntAsDec(n10);
                    indentingWriter.write(10);
                    indentingWriter.indent(4);
                    int n11 = (Integer)hashMap.get((Object)n5);
                    int[] arrn5 = packedSwitchDataPseudoInstruction.getTargets();
                    int n12 = arrn5.length;
                    int n13 = n10;
                    for (int j = 0; j < n12; ++j) {
                        int n14 = arrn5[j];
                        int n15 = n13 + 1;
                        int n16 = n11 + n14;
                        Parser.writeKeyAndTarget(indentingWriter, n13, n16);
                        indentingWriter.write(10);
                        n13 = n15;
                    }
                    indentingWriter.deindent(4);
                    indentingWriter.write(".end pswitch_data");
                    indentingWriter.write(10);
                    continue block33;
                }
                case SparseSwitchData: {
                    SparseSwitchDataPseudoInstruction sparseSwitchDataPseudoInstruction = (SparseSwitchDataPseudoInstruction)instruction2;
                    indentingWriter.write(".sswitch_data");
                    indentingWriter.write(10);
                    int n17 = (Integer)hashMap.get((Object)n5);
                    Iterator<SparseSwitchDataPseudoInstruction.SparseSwitchTarget> iterator = sparseSwitchDataPseudoInstruction.iterateKeysAndTargets();
                    indentingWriter.indent(4);
                    while (iterator.hasNext()) {
                        SparseSwitchDataPseudoInstruction.SparseSwitchTarget sparseSwitchTarget = (SparseSwitchDataPseudoInstruction.SparseSwitchTarget)iterator.next();
                        Parser.writeKeyAndTarget(indentingWriter, sparseSwitchTarget.key, n17 + sparseSwitchTarget.targetAddressOffset);
                        indentingWriter.write(10);
                    }
                    indentingWriter.deindent(4);
                    indentingWriter.write(".end sswitch_data");
                    indentingWriter.write(10);
                    continue block33;
                }
                case ArrayData: {
                    ArrayDataPseudoInstruction arrayDataPseudoInstruction = (ArrayDataPseudoInstruction)instruction2;
                    indentingWriter.write(".array_data");
                    indentingWriter.write(32);
                    arrayDataPseudoInstruction.getElementCount();
                    indentingWriter.printIntAsDec(arrayDataPseudoInstruction.getElementWidth());
                    indentingWriter.write(10);
                    indentingWriter.indent(4);
                    Iterator<ArrayDataPseudoInstruction.ArrayElement> iterator = arrayDataPseudoInstruction.getElements();
                    while (iterator.hasNext()) {
                        int n18;
                        ArrayDataPseudoInstruction.ArrayElement arrayElement = (ArrayDataPseudoInstruction.ArrayElement)iterator.next();
                        for (int j = arrayElement.bufferIndex; j < (n18 = arrayElement.elementWidth + arrayElement.bufferIndex); ++j) {
                            ByteRenderer.writeUnsignedTo(indentingWriter, arrayElement.buffer[j]);
                            indentingWriter.write(32);
                        }
                        indentingWriter.write(10);
                    }
                    indentingWriter.deindent(4);
                    indentingWriter.write(".end array_data");
                    indentingWriter.write(10);
                    break;
                }
            }
        }
        if (Arrays.binarySearch((int[])arrn, (int)n5) >= 0) {
            indentingWriter.write(LABEL);
            indentingWriter.write(String.valueOf((int)n5));
            indentingWriter.write(58);
            indentingWriter.write(10);
        } else if (Arrays.binarySearch((int[])arrn4, (int)n5) >= 0) {
            indentingWriter.write(10);
            indentingWriter.write(LABEL);
            indentingWriter.write(String.valueOf((int)n5));
            indentingWriter.write(58);
            indentingWriter.write(10);
        }
        if (Arrays.binarySearch((int[])arrn3, (int)n5) >= 0) {
            indentingWriter.write("switch_");
            indentingWriter.write(String.valueOf((int)n5));
            indentingWriter.write(58);
            indentingWriter.write(10);
        }
        Parser.super.writeTryItems(indentingWriter);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void parse(DexFile var1, String var2_2) throws Exception {
        block99 : {
            block98 : {
                if (this.code == null) {
                    return;
                }
                Parser.outWords = 0;
                var3_3 = var2_2.split("\n");
                var4_4 = new HashMap();
                var5_5 = new HashMap(1);
                var6_6 = 0;
                var7_7 = new Opcode[var3_3.length];
                var8_8 = new ArrayList(var3_3.length);
                var9_9 = new ArrayList(1);
                var10_10 = 0;
                var11_11 = var3_3.length;
                block58 : do {
                    block103 : {
                        block102 : {
                            block101 : {
                                block100 : {
                                    var12_39 = ++var10_10;
                                    var13_28 = null;
                                    if (var12_39 >= var11_11) break block100;
                                    var117_16 = var3_3[var10_10].trim();
                                    if (var117_16.equals((Object)"") || var117_16.startsWith("#") || var117_16.startsWith("//")) {
                                        var7_7[var10_10] = null;
                                        continue;
                                    }
                                    if (var117_16.endsWith(":")) {
                                        var4_4.put((Object)var117_16.substring(0, -1 + var117_16.length()), (Object)var6_6);
                                        var7_7[var10_10] = null;
                                        continue;
                                    }
                                    var118_15 = var117_16.indexOf(" ");
                                    var119_33 = var118_15 != -1 ? var117_16.substring(0, var118_15) : var117_16.trim();
                                    var120_30 = Opcode.getOpcodeByName(var119_33);
                                    if (var120_30 != null) break block101;
                                    if (var119_33.equals((Object)".pswitch_data")) break block102;
                                    if (var119_33.equals((Object)".sswitch_data")) break;
                                    if (var119_33.equals((Object)".array_data")) {
                                        var123_21 = var3_3[var10_10].trim().split(" ");
                                        if (var123_21.length < 2) {
                                            throw new IllegalArgumentException("unknow element Width: " + var3_3[var10_10]);
                                        }
                                        break block98;
                                    }
                                    if (var119_33.equals((Object)".catch") == false) throw new IllegalArgumentException("unknow opcodeName: " + var119_33 + " at " + var10_10);
                                    var13_28 = Parser.parseCatchs(var1, var3_3, var10_10, (HashMap<String, Integer>)var4_4);
                                }
                                var14_45 = 0;
                                var16_47 = var3_3.length;
                                break block99;
                            }
                            switch (1.$SwitchMap$org$jf$dexlib$Code$Opcode[var120_30.ordinal()]) {
                                default: {
                                    break;
                                }
                                case 1: 
                                case 2: {
                                    var121_27 = var117_16.split(":");
                                    if (var121_27.length != 2) {
                                        throw new IllegalArgumentException(" opcode offset error: " + var117_16);
                                    }
                                    var5_5.put((Object)var121_27[1].trim(), (Object)var6_6);
                                }
                            }
                            var6_6 += var120_30.format.size / 2;
                            break block103;
                        }
                        var142_35 = var3_3[var10_10 - 1].trim();
                        var143_25 = (Integer)var5_5.get((Object)var142_35.substring(0, -1 + var142_35.length()));
                        var144_22 = new SparseIntArray();
                        do {
                            if ((var145_29 = var3_3[++var10_10].trim()).startsWith(".end")) {
                                var148_24 = var144_22.keyAt(0);
                                var149_14 = var144_22.values();
                                var150_41 = new PackedSwitchDataPseudoInstruction(var148_24, var149_14);
                                var6_6 += var150_41.getSize(var6_6);
                                var9_9.add((Object)var150_41);
                                break;
                            }
                            var146_17 = var145_29.split(":");
                            if (var146_17.length != 2) {
                                throw new IllegalArgumentException("packed switch data error: " + var145_29);
                            }
                            try {
                                var144_22.put(Integer.parseInt((String)var146_17[0].trim()), (Integer)var4_4.get((Object)var146_17[1].trim()) - var143_25);
                            }
                            catch (Exception var147_43) {
                                throw new IllegalArgumentException("the key is not int or label is not exists: " + var145_29);
                            }
                        } while (true);
                    }
lbl76: // 3 sources:
                    do {
                        var7_7[var10_10] = var120_30;
                        continue block58;
                        break;
                    } while (true);
                    break;
                } while (true);
                var132_32 = var3_3[var10_10 - 1].trim();
                var133_36 = (Integer)var5_5.get((Object)var132_32.substring(0, -1 + var132_32.length()));
                var134_12 = new SparseIntArray();
                do {
                    block104 : {
                        if (!(var135_31 = var3_3[++var10_10].trim()).startsWith(".end")) break block104;
                        var138_38 = var134_12.keys();
                        var139_40 = var134_12.values();
                        var140_42 = new SparseSwitchDataPseudoInstruction(var138_38, var139_40);
                        var9_9.add((Object)var140_42);
                        var6_6 += var140_42.getSize(var6_6);
                        ** GOTO lbl76
                    }
                    var136_34 = var135_31.split(":");
                    if (var136_34.length != 2) {
                        throw new IllegalArgumentException("packed switch data error: " + var135_31 + " at " + var10_10);
                    }
                    try {
                        var134_12.put(Integer.parseInt((String)var136_34[0].trim()), (Integer)var4_4.get((Object)var136_34[1].trim()) - var133_36);
                    }
                    catch (Exception var137_44) {
                        throw new IllegalArgumentException("the key is not int or label is not exists: " + var135_31 + " at " + var10_10);
                    }
                } while (true);
            }
            var124_23 = LiteralTools.parseInt(var123_21[1]);
            var125_13 = new ByteArrayOutputStream();
            block62 : do {
                if ((var126_18 = var3_3[++var10_10].trim()).startsWith(".end")) {
                    var127_20 = new ArrayDataPseudoInstruction(var124_23, var125_13.toByteArray());
                    var9_9.add((Object)var127_20);
                    var6_6 += var127_20.getSize(var6_6);
                    ** continue;
                }
                var129_37 = var126_18.split(" ");
                if (var129_37.length != var124_23) {
                    throw new IllegalArgumentException("encodeValues width:" + var129_37.length + " does not match : " + var124_23 + " at " + var10_10);
                }
                var130_26 = var129_37.length;
                var131_19 = 0;
                do {
                    if (var131_19 >= var130_26) continue block62;
                    var125_13.write((int)LiteralTools.parseByte(var129_37[var131_19].trim()));
                    ++var131_19;
                } while (true);
                break;
            } while (true);
        }
        for (var15_46 = 0; var15_46 < var16_47; ++var15_46) {
            block105 : {
                var28_68 = var3_3[var15_46].trim();
                var29_89 = var7_7[var15_46];
                if (var29_89 == null) continue;
                switch (1.$SwitchMap$org$jf$dexlib$Code$Format$Format[var29_89.format.ordinal()]) {
                    case 9: {
                        var8_8.add((Object)new Instruction10x(var29_89));
                        ** break;
                    }
                    case 1: {
                        var114_94 = new Instruction10t(var29_89, (byte)Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45));
                        var8_8.add((Object)var114_94);
                        ** break;
                    }
                    case 11: {
                        var110_98 = (byte)Parser.parseSingleRegister(var28_68);
                        var111_83 = (byte)Parser.parseInt(var28_68);
                        var112_101 = new Instruction11n(var29_89, var110_98, var111_83);
                        var8_8.add((Object)var112_101);
                        ** break;
                    }
                    case 10: {
                        var8_8.add((Object)new Instruction11x(var29_89, (short)Parser.parseSingleRegister(var28_68)));
                        ** break;
                    }
                    case 12: {
                        var106_76 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var106_76.find()) {
                            // empty if block
                        }
                        var107_106 = (byte)Integer.parseInt((String)var106_76.group().substring(1));
                        if (!var106_76.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction12x(var29_89, var107_106, (byte)Integer.parseInt((String)var106_76.group().substring(1))));
                        ** break;
                    }
                    case 2: {
                        var103_48 = (short)(65535 & Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45));
                        var104_92 = new Instruction20t(var29_89, var103_48);
                        var8_8.add((Object)var104_92);
                        ** break;
                    }
                    case 13: {
                        var93_60 = (short)Parser.parseSingleRegister(var28_68);
                        var94_52 = 1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[var29_89.referenceType.ordinal()];
                        var95_84 /* !! */  = null;
                        switch (var94_52) {
                            case 1: {
                                try {
                                    var95_84 /* !! */  = var102_64 = Parser.parseField(var1, var28_68);
                                    ** break;
                                }
                                catch (Exception var101_108) {
                                    throw new Exception("FieldIdItem error: " + var28_68 + "at " + var15_46);
                                }
                            }
                            case 4: {
                                var100_107 = Parser.parseString(var1, var28_68);
                                var95_84 /* !! */  = var100_107;
                            }
lbl170: // 3 sources:
                            default: {
                                ** GOTO lbl180
                            }
                            catch (Exception var99_109) {
                                throw new Exception("String error: " + var28_68 + "at " + var15_46);
                            }
                            case 3: 
                        }
                        try {
                            var97_50 = Parser.parseType(var1, var28_68);
                            var95_84 /* !! */  = var97_50;
                        }
                        catch (Exception var96_110) {
                            throw new Exception("TypeIdItem error: " + var28_68 + "at " + var15_46);
                        }
lbl180: // 2 sources:
                        var8_8.add((Object)new Instruction21c(var29_89, var93_60, var95_84 /* !! */ ));
                        ** break;
                    }
                    case 15: {
                        var89_105 = (short)Parser.parseSingleRegister(var28_68);
                        var90_86 = (short)(65535 & Parser.parseInt(var28_68));
                        var91_99 = new Instruction21h(var29_89, var89_105, var90_86);
                        var8_8.add((Object)var91_99);
                        ** break;
                    }
                    case 16: {
                        var85_77 = (short)Parser.parseSingleRegister(var28_68);
                        var86_61 = (short)(65535 & Parser.parseInt(var28_68));
                        var87_69 = new Instruction21s(var29_89, var85_77, var86_61);
                        var8_8.add((Object)var87_69);
                        ** break;
                    }
                    case 3: {
                        var81_62 = (short)Parser.parseSingleRegister(var28_68);
                        var82_82 = (short)(65535 & Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45));
                        var83_88 = new Instruction21t(var29_89, var81_62, var82_82);
                        var8_8.add((Object)var83_88);
                        ** break;
                    }
                    case 20: {
                        var78_73 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var78_73.find()) {
                            // empty if block
                        }
                        var79_96 = (short)Integer.parseInt((String)var78_73.group().substring(1));
                        if (!var78_73.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction22b(var29_89, var79_96, (short)Integer.parseInt((String)var78_73.group().substring(1)), (byte)(255 & Parser.parseInt(var28_68))));
                        ** break;
                    }
                    case 22: {
                        var75_102 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var75_102.find()) {
                            // empty if block
                        }
                        var76_74 = (byte)Integer.parseInt((String)var75_102.group().substring(1));
                        if (!var75_102.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction22s(var29_89, var76_74, (byte)Integer.parseInt((String)var75_102.group().substring(1)), (short)(65535 & Parser.parseInt(var28_68))));
                        ** break;
                    }
                    case 4: {
                        var72_79 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var72_79.find()) {
                            // empty if block
                        }
                        var73_104 = (byte)Integer.parseInt((String)var72_79.group().substring(1));
                        if (!var72_79.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction22t(var29_89, var73_104, (byte)Integer.parseInt((String)var72_79.group().substring(1)), (short)(65535 & Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45))));
                        ** break;
                    }
                    case 21: {
                        var66_95 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var66_95.find()) {
                            // empty if block
                        }
                        var67_55 = (byte)Integer.parseInt((String)var66_95.group().substring(1));
                        if (!var66_95.find()) {
                            // empty if block
                        }
                        var68_67 = (byte)Integer.parseInt((String)var66_95.group().substring(1));
                        var69_103 = 1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[var29_89.referenceType.ordinal()];
                        var70_91 /* !! */  = null;
                        switch (var69_103) {
                            case 1: {
                                var70_91 /* !! */  = Parser.parseField(var1, var28_68);
                            }
                            default: {
                                break;
                            }
                            case 3: {
                                var70_91 /* !! */  = Parser.parseType(var1, var28_68);
                            }
                        }
                        var8_8.add((Object)new Instruction22c(var29_89, var67_55, var68_67, var70_91 /* !! */ ));
                        ** break;
                    }
                    case 19: {
                        var63_65 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var63_65.find()) {
                            // empty if block
                        }
                        var64_53 = (short)(65535 & Integer.parseInt((String)var63_65.group().substring(1)));
                        if (!var63_65.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction22x(var29_89, var64_53, 65535 & Integer.parseInt((String)var63_65.group().substring(1))));
                        ** break;
                    }
                    case 23: {
                        var59_59 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var59_59.find()) {
                            // empty if block
                        }
                        var60_71 = (short)Integer.parseInt((String)var59_59.group().substring(1));
                        if (!var59_59.find()) {
                            // empty if block
                        }
                        var61_58 = (short)Integer.parseInt((String)var59_59.group().substring(1));
                        if (!var59_59.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction23x(var29_89, var60_71, var61_58, (short)Integer.parseInt((String)var59_59.group().substring(1))));
                        ** break;
                    }
                    case 5: {
                        var57_57 = new Instruction30t(var29_89, Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45));
                        var8_8.add((Object)var57_57);
                        ** break;
                    }
                    case 14: {
                        var8_8.add((Object)new Instruction31c(var29_89, (short)Parser.parseSingleRegister(var28_68), Parser.parseString(var1, var28_68)));
                        ** break;
                    }
                    case 17: {
                        var52_87 = (byte)Parser.parseSingleRegister(var28_68);
                        var53_80 = Parser.parseInt(var28_68);
                        var54_51 = new Instruction31i(var29_89, var52_87, var53_80);
                        var8_8.add((Object)var54_51);
                        ** break;
                    }
                    case 6: {
                        var48_90 = (short)Parser.parseSingleRegister(var28_68);
                        var49_54 = Parser.parseTarget(var28_68, (HashMap<String, Integer>)var4_4, var14_45);
                        var50_100 = new Instruction31t(var29_89, var48_90, var49_54);
                        var8_8.add((Object)var50_100);
                        ** break;
                    }
                    case 24: {
                        var45_66 = Parser.pRegister.matcher((CharSequence)var28_68);
                        if (!var45_66.find()) {
                            // empty if block
                        }
                        var46_72 = Integer.parseInt((String)var45_66.group().substring(1));
                        if (!var45_66.find()) {
                            // empty if block
                        }
                        var8_8.add((Object)new Instruction32x(var29_89, var46_72, Integer.parseInt((String)var45_66.group().substring(1))));
                        ** break;
                    }
                    case 25: {
                        var39_75 = new int[1];
                        var40_63 = Parser.parseFiveRegister(var28_68, var39_75);
                        var41_70 = 1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[var29_89.referenceType.ordinal()];
                        var42_85 /* !! */  = null;
                        switch (var41_70) {
                            case 2: {
                                var42_85 /* !! */  = Parser.parseMethod(var1, var28_68);
                            }
                            default: {
                                break;
                            }
                            case 3: {
                                var42_85 /* !! */  = Parser.parseType(var1, var28_68);
                            }
                        }
                        try {
                            var8_8.add((Object)new Instruction35c(var29_89, var39_75[0], var40_63[0], var40_63[1], var40_63[2], var40_63[3], var40_63[4], var42_85 /* !! */ ));
                            ** break;
                        }
                        catch (Exception var43_111) {
                            throw new RuntimeException(var28_68 + "  " + var42_85 /* !! */  + "  " + var39_75[0]);
                        }
                    }
                    case 27: {
                        var35_78 = Parser.parseRangeRegister(var28_68);
                        var36_81 = 1.$SwitchMap$org$jf$dexlib$Code$ReferenceType[var29_89.referenceType.ordinal()];
                        var37_49 /* !! */  = null;
                        switch (var36_81) {
                            case 2: {
                                var37_49 /* !! */  = Parser.parseMethod(var1, var28_68);
                            }
                            default: {
                                break;
                            }
                            case 3: {
                                var37_49 /* !! */  = Parser.parseType(var1, var28_68);
                            }
                        }
                        var8_8.add((Object)new Instruction3rc(var29_89, (short)var35_78[0], var35_78[1], (Item)var37_49 /* !! */ ));
                    }
lbl324: // 24 sources:
                    default: {
                        break block105;
                    }
                    case 18: 
                }
                var30_56 = (short)Parser.parseSingleRegister(var28_68);
                var31_93 = Parser.parseLong(var28_68);
                var33_97 = new Instruction51l(var29_89, var30_56, var31_93);
                var8_8.add((Object)var33_97);
            }
            var14_45 += var29_89.format.size / 2;
        }
        var8_8.addAll((Collection)var9_9);
        var18_112 = new Instruction[var8_8.size()];
        var8_8.toArray(var18_112);
        if (var13_28 != null) {
            var22_113 = (List)var13_28.first;
            var23_114 = (List)var13_28.second;
            if (var22_113.size() > 0) {
                var24_115 = new CodeItem.TryItem[var22_113.size()];
                var25_116 = new CodeItem.EncodedCatchHandler[var23_114.size()];
                var22_113.toArray(var24_115);
                var23_114.toArray(var25_116);
                this.code.tries = var24_115;
                this.code.encodedCatchHandlers = var25_116;
            }
        } else {
            this.code.tries = null;
            this.code.encodedCatchHandlers = null;
        }
        var20_117 = this.code.getParent();
        var21_118 = var20_117.method.getPrototype().getParameterRegisterCount();
        if ((var20_117.accessFlags & AccessFlags.STATIC.getValue()) == 0) {
            ++var21_118;
        }
        this.code.inWords = var21_118;
        this.code.outWords = Parser.outWords;
        this.code.updateCode((Instruction[])var18_112);
    }

}

