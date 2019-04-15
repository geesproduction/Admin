/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package org.jf.dexlib.Code.Analysis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jf.dexlib.Code.Analysis.AnalyzedInstruction;
import org.jf.dexlib.Code.Analysis.ClassPath;
import org.jf.dexlib.Code.Analysis.InlineMethodResolver;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OdexHeader;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;

public class DeodexUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int Direct = 1;
    public static final int Static = 2;
    public static final int Virtual;
    private static final Pattern shortMethodPattern;
    public final DexFile dexFile;
    private final InlineMethodResolver inlineMethodResolver;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DeodexUtil.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        shortMethodPattern = Pattern.compile((String)"([^(]+)\\(([^)]*)\\)(.+)");
    }

    public DeodexUtil(DexFile dexFile) {
        this.dexFile = dexFile;
        OdexHeader odexHeader = dexFile.getOdexHeader();
        if (odexHeader == null) {
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            throw new RuntimeException("Cannot create a DeodexUtil object for a dex file without an odex header");
        }
        this.inlineMethodResolver = InlineMethodResolver.createInlineMethodResolver((DeodexUtil)this, odexHeader.version);
    }

    /*
     * Enabled aggressive block sorting
     */
    private FieldIdItem parseAndResolveField(ClassPath.ClassDef classDef, ClassPath.FieldDef fieldDef) {
        ClassPath.ClassDef classDef2;
        String string2 = fieldDef.definingClass;
        String string3 = fieldDef.name;
        String string4 = fieldDef.type;
        StringIdItem stringIdItem = StringIdItem.lookupStringIdItem(this.dexFile, string3);
        if (stringIdItem == null) {
            return null;
        }
        TypeIdItem typeIdItem = TypeIdItem.lookupTypeIdItem(this.dexFile, string4);
        if (typeIdItem == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add((Object)classDef2);
        for (classDef2 = classDef; classDef2 != null && !classDef2.getClassType().equals((Object)string2); classDef2 = classDef2.getSuperclass()) {
            arrayList.add((Object)classDef2);
        }
        int n = -1 + arrayList.size();
        while (n >= 0) {
            FieldIdItem fieldIdItem;
            ClassPath.ClassDef classDef3 = (ClassPath.ClassDef)arrayList.get(n);
            TypeIdItem typeIdItem2 = TypeIdItem.lookupTypeIdItem(this.dexFile, classDef3.getClassType());
            if (typeIdItem2 != null && (fieldIdItem = FieldIdItem.lookupFieldIdItem(this.dexFile, typeIdItem2, typeIdItem, stringIdItem)) != null) {
                return fieldIdItem;
            }
            --n;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private MethodIdItem parseAndResolveMethod(ClassPath.ClassDef classDef, String string2, String string3, String string4) {
        StringIdItem stringIdItem = StringIdItem.lookupStringIdItem(this.dexFile, string2);
        if (stringIdItem == null) {
            return null;
        }
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < string3.length(); ++i) {
            TypeIdItem typeIdItem;
            switch (string3.charAt(i)) {
                default: {
                    throw new RuntimeException("invalid parameter in the method");
                }
                case 'B': 
                case 'C': 
                case 'D': 
                case 'F': 
                case 'I': 
                case 'J': 
                case 'S': 
                case 'Z': {
                    typeIdItem = TypeIdItem.lookupTypeIdItem(this.dexFile, string3.substring(i, i + 1));
                    break;
                }
                case 'L': {
                    int n = string3.indexOf(59, i);
                    if (n == -1) {
                        throw new RuntimeException("invalid parameter in the method");
                    }
                    typeIdItem = TypeIdItem.lookupTypeIdItem(this.dexFile, string3.substring(i, n + 1));
                    i = n;
                    break;
                }
                case '[': {
                    int n;
                    int n2;
                    for (n = i + 1; n < string3.length() && string3.charAt(n) == '['; ++n) {
                    }
                    switch (string3.charAt(n)) {
                        default: {
                            throw new RuntimeException("invalid parameter in the method");
                        }
                        case 'B': 
                        case 'C': 
                        case 'D': 
                        case 'F': 
                        case 'I': 
                        case 'J': 
                        case 'S': 
                        case 'Z': {
                            n2 = n;
                            break;
                        }
                        case 'L': {
                            n2 = string3.indexOf(59, n);
                            if (n2 != -1) break;
                            throw new RuntimeException("invalid parameter in the method");
                        }
                    }
                    typeIdItem = TypeIdItem.lookupTypeIdItem(this.dexFile, string3.substring(i, n2 + 1));
                    i = n2;
                }
            }
            if (typeIdItem == null) {
                return null;
            }
            linkedList.add((Object)typeIdItem);
        }
        int n = linkedList.size();
        TypeListItem typeListItem = null;
        if (n > 0 && (typeListItem = TypeListItem.lookupTypeListItem(this.dexFile, (List<TypeIdItem>)linkedList)) == null) {
            return null;
        }
        TypeIdItem typeIdItem = TypeIdItem.lookupTypeIdItem(this.dexFile, string4);
        if (typeIdItem == null) {
            return null;
        }
        ProtoIdItem protoIdItem = ProtoIdItem.lookupProtoIdItem(this.dexFile, typeIdItem, typeListItem);
        if (protoIdItem == null) {
            return null;
        }
        ClassPath.ClassDef classDef2 = classDef;
        do {
            TypeIdItem typeIdItem2;
            if ((typeIdItem2 = TypeIdItem.lookupTypeIdItem(this.dexFile, classDef2.getClassType())) == null) continue;
            MethodIdItem methodIdItem = MethodIdItem.lookupMethodIdItem(this.dexFile, typeIdItem2, protoIdItem, stringIdItem);
            if (methodIdItem != null) return methodIdItem;
        } while ((classDef2 = classDef2.getSuperclass()) != null);
        return null;
    }

    public FieldIdItem lookupField(ClassPath.ClassDef classDef, int n) {
        ClassPath.FieldDef fieldDef = classDef.getInstanceField(n);
        if (fieldDef == null) {
            return null;
        }
        return DeodexUtil.super.parseAndResolveField(classDef, fieldDef);
    }

    public InlineMethod lookupInlineMethod(AnalyzedInstruction analyzedInstruction) {
        return this.inlineMethodResolver.resolveExecuteInline(analyzedInstruction);
    }

    public MethodIdItem lookupVirtualMethod(ClassPath.ClassDef classDef, int n) {
        String string2 = classDef.getVirtualMethod(n);
        if (string2 == null) {
            return null;
        }
        Matcher matcher = shortMethodPattern.matcher((CharSequence)string2);
        if (!matcher.matches()) {
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
            throw new RuntimeException("Invalid method descriptor: " + string2);
        }
        String string3 = matcher.group(1);
        String string4 = matcher.group(2);
        String string5 = matcher.group(3);
        if (classDef.isInterface()) {
            classDef = classDef.getSuperclass();
            if (!$assertionsDisabled && classDef == null) {
                throw new AssertionError();
            }
        }
        return DeodexUtil.super.parseAndResolveMethod(classDef, string3, string4, string5);
    }

    public class InlineMethod {
        public final String classType;
        private MethodIdItem methodIdItem = null;
        public final String methodName;
        public final int methodType;
        public final String parameters;
        public final String returnType;

        InlineMethod(int n, String string2, String string3, String string4, String string5) {
            this.methodType = n;
            this.classType = string2;
            this.methodName = string3;
            this.parameters = string4;
            this.returnType = string5;
        }

        private void loadMethod() {
            ClassPath.ClassDef classDef = ClassPath.getClassDef(this.classType);
            this.methodIdItem = DeodexUtil.this.parseAndResolveMethod(classDef, this.methodName, this.parameters, this.returnType);
        }

        public MethodIdItem getMethodIdItem() {
            if (this.methodIdItem == null) {
                this.loadMethod();
            }
            return this.methodIdItem;
        }

        public String getMethodString() {
            Object[] arrobject = new Object[]{this.classType, this.methodName, this.parameters, this.returnType};
            return String.format((String)"%s->%s(%s)%s", (Object[])arrobject);
        }
    }

}

