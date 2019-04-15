/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.PrintStream
 *  java.lang.AssertionError
 *  java.lang.CharSequence
 *  java.lang.Comparable
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Array
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Set
 *  java.util.TreeSet
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 */
package org.jf.dexlib.Code.Analysis;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.Code.Analysis.ValidationException;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OdexDependencies;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.SparseArray;

public class ClassPath {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String arrayPrefix = "[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[[";
    private static final Pattern dalvikCacheOdexPattern;
    private static ClassPath theClassPath;
    private final HashMap<String, ClassDef> classDefs = new HashMap();
    protected ClassDef javaLangObjectClassDef;
    private LinkedHashMap<String, TempClassInfo> tempClasses;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ClassPath.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        theClassPath = null;
        dalvikCacheOdexPattern = Pattern.compile((String)"@([^@]+)@classes.dex$");
    }

    private ClassPath() {
    }

    public static void InitializeClassPath(String[] arrstring, String[] arrstring2, String[] arrstring3, String string2, DexFile dexFile, ClassPathErrorHandler classPathErrorHandler) {
        if (theClassPath != null) {
            throw new ExceptionWithContext("Cannot initialize ClassPath multiple times");
        }
        theClassPath = new ClassPath();
        theClassPath.initClassPath(arrstring, arrstring2, arrstring3, string2, dexFile, classPathErrorHandler);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void InitializeClassPathFromOdex(String[] arrstring, String[] arrstring2, String string2, DexFile dexFile, ClassPathErrorHandler classPathErrorHandler) {
        if (!dexFile.isOdex()) {
            throw new ExceptionWithContext("Cannot use InitialiazeClassPathFromOdex with a non-odex DexFile");
        }
        if (theClassPath != null) {
            throw new ExceptionWithContext("Cannot initialize ClassPath multiple times");
        }
        OdexDependencies odexDependencies = dexFile.getOdexDependencies();
        String[] arrstring3 = new String[odexDependencies.getDependencyCount()];
        int n = 0;
        do {
            if (n >= arrstring3.length) {
                theClassPath = new ClassPath();
                theClassPath.initClassPath(arrstring, arrstring3, arrstring2, string2, dexFile, classPathErrorHandler);
                return;
            }
            String string3 = odexDependencies.getDependency(n);
            if (string3.endsWith(".odex")) {
                int n2 = string3.lastIndexOf("/");
                if (n2 != -1) {
                    string3 = string3.substring(n2 + 1);
                }
            } else {
                if (!string3.endsWith("@classes.dex")) {
                    throw new ExceptionWithContext(String.format((String)"Cannot parse dependency value %s", (Object[])new Object[]{string3}));
                }
                Matcher matcher = dalvikCacheOdexPattern.matcher((CharSequence)string3);
                if (!matcher.find()) {
                    throw new ExceptionWithContext(String.format((String)"Cannot parse dependency value %s", (Object[])new Object[]{string3}));
                }
                string3 = matcher.group(1);
            }
            arrstring3[n] = string3;
            ++n;
        } while (true);
    }

    static /* synthetic */ ClassPath access$600() {
        return theClassPath;
    }

    private ClassDef createArrayClassDef(String string2) {
        if (!$assertionsDisabled && string2 == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && string2.charAt(0) != '[') {
            throw new AssertionError();
        }
        ArrayClassDef arrayClassDef = new ArrayClassDef(string2);
        if (arrayClassDef.elementClass == null) {
            return null;
        }
        this.classDefs.put((Object)string2, (Object)arrayClassDef);
        return arrayClassDef;
    }

    private ClassDef createUnresolvedClassDef(String string2) {
        if (!$assertionsDisabled && string2.charAt(0) != 'L') {
            throw new AssertionError();
        }
        UnresolvedClassDef unresolvedClassDef = new UnresolvedClassDef(string2);
        this.classDefs.put((Object)string2, (Object)unresolvedClassDef);
        return unresolvedClassDef;
    }

    private static ClassDef getArrayClassDefByElementClassAndDimension(ClassDef classDef, int n) {
        return ClassPath.getClassDef(arrayPrefix.substring(256 - n) + classDef.classType);
    }

    public static ClassDef getClassDef(String string2) {
        return ClassPath.getClassDef(string2, true);
    }

    public static ClassDef getClassDef(String string2, boolean bl) {
        block5 : {
            ClassDef classDef;
            block4 : {
                classDef = (ClassDef)ClassPath.theClassPath.classDefs.get((Object)string2);
                if (classDef != null) break block4;
                if (string2.charAt(0) != '[') break block5;
                classDef = theClassPath.createArrayClassDef(string2);
            }
            return classDef;
        }
        if (bl) {
            return theClassPath.createUnresolvedClassDef(string2);
        }
        return null;
    }

    public static ClassDef getClassDef(TypeIdItem typeIdItem) {
        return ClassPath.getClassDef(typeIdItem.getTypeDescriptor());
    }

    public static ClassDef getClassDef(TypeIdItem typeIdItem, boolean bl) {
        return ClassPath.getClassDef(typeIdItem.getTypeDescriptor(), bl);
    }

    private static ClassDef getCommonArraySuperclass(ArrayClassDef arrayClassDef, ArrayClassDef arrayClassDef2) {
        if (!$assertionsDisabled && arrayClassDef == arrayClassDef2) {
            throw new AssertionError();
        }
        if (arrayClassDef.elementClass instanceof PrimitiveClassDef || arrayClassDef2.elementClass instanceof PrimitiveClassDef) {
            return ClassPath.theClassPath.javaLangObjectClassDef;
        }
        if (arrayClassDef.arrayDimensions == arrayClassDef2.arrayDimensions) {
            return ClassPath.getArrayClassDefByElementClassAndDimension(ClassPath.getCommonSuperclass(arrayClassDef.elementClass, arrayClassDef2.elementClass), arrayClassDef.arrayDimensions);
        }
        int n = Math.min((int)arrayClassDef.arrayDimensions, (int)arrayClassDef2.arrayDimensions);
        return ClassPath.getArrayClassDefByElementClassAndDimension(ClassPath.theClassPath.javaLangObjectClassDef, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static ClassDef getCommonSuperclass(ClassDef var0_1, ClassDef var1) {
        block8 : {
            if (var0_1 == var1) {
                return var0_1;
            }
            if (var0_1 == null) {
                return var1;
            }
            if (var1 == null) return var0_1;
            if (ClassDef.access$200(var1)) {
                if (var0_1.implementsInterface(var1) == false) return ClassPath.theClassPath.javaLangObjectClassDef;
                return var1;
            }
            if (ClassDef.access$200(var0_1)) {
                if (var1.implementsInterface(var0_1) != false) return var0_1;
                return ClassPath.theClassPath.javaLangObjectClassDef;
            }
            if (var0_1 instanceof ArrayClassDef && var1 instanceof ArrayClassDef) {
                return ClassPath.getCommonArraySuperclass((ArrayClassDef)var0_1, (ArrayClassDef)var1);
            }
            var3_3 = var1.getClassDepth();
            for (var2_2 = var0_1.getClassDepth(); var2_2 > var3_3; --var2_2) {
                var0_1 = ClassDef.access$300(var0_1);
            }
            while (var3_3 > var2_2) {
                var1 = ClassDef.access$300(var1);
                --var3_3;
            }
            break block8;
lbl23: // 1 sources:
            do {
                var0_1 = ClassDef.access$300(var0_1);
                --var2_2;
                var1 = ClassDef.access$300(var1);
                --var3_3;
                break;
            } while (true);
        }
        if (var2_2 <= 0) return var0_1;
        ** while (var0_1 != var1)
lbl31: // 1 sources:
        return var0_1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void initClassPath(String[] arrstring, String[] arrstring2, String[] arrstring3, String string2, DexFile dexFile, ClassPathErrorHandler classPathErrorHandler) {
        this.tempClasses = new LinkedHashMap();
        if (arrstring2 != null) {
            int n = arrstring2.length;
            for (int i = 0; i < n; ++i) {
                this.loadBootClassPath(arrstring, arrstring2[i]);
            }
        }
        if (arrstring3 != null) {
            int n = arrstring3.length;
            for (int i = 0; i < n; ++i) {
                this.loadBootClassPath(arrstring, arrstring3[i]);
            }
        }
        if (dexFile != null) {
            this.loadDexFile(string2, dexFile);
        }
        for (String string3 : this.tempClasses.keySet()) {
            Exception exception2;
            block11 : {
                ClassDef classDef = null;
                try {
                    classDef = ClassPath.loadClassDef(string3);
                    if (!$assertionsDisabled && classDef == null) {
                        throw new AssertionError();
                    }
                }
                catch (Exception exception2) {
                    if (classPathErrorHandler == null) break block11;
                    classPathErrorHandler.ClassPathError(string3, exception2);
                }
                if (!string3.equals((Object)"Ljava/lang/Object;")) continue;
                this.javaLangObjectClassDef = classDef;
                continue;
            }
            throw ExceptionWithContext.withContext(exception2, String.format((String)"Error while loading ClassPath class %s", (Object[])new Object[]{string3}));
        }
        String[] arrstring4 = new String[]{"Z", "B", "S", "C", "I", "J", "F", "D"};
        int n = arrstring4.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.tempClasses = null;
                return;
            }
            String string4 = arrstring4[n2];
            PrimitiveClassDef primitiveClassDef = new PrimitiveClassDef(string4);
            this.classDefs.put((Object)string4, (Object)primitiveClassDef);
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadBootClassPath(String[] arrstring, String string2) {
        int n = arrstring.length;
        int n2 = 0;
        block5 : do {
            if (n2 >= n) {
                throw new ExceptionWithContext(String.format((String)"Cannot locate boot class path file %s", (Object[])new Object[]{string2}));
            }
            String string3 = arrstring[n2];
            File file = null;
            int n3 = string2.lastIndexOf(".");
            String string4 = n3 == -1 ? string2 : string2.substring(0, n3);
            String[] arrstring2 = new String[]{"", ".odex", ".jar", ".apk", ".zip"};
            int n4 = arrstring2.length;
            int n5 = 0;
            DexFile dexFile = null;
            do {
                DexFile dexFile2;
                block12 : {
                    block11 : {
                        if (n5 >= n4) break block11;
                        String string5 = arrstring2[n5];
                        file = string5.length() == 0 ? new File(string3, string2) : new File(string3, string4 + string5);
                        if (!file.exists()) break block12;
                        if (!file.canRead()) {
                            PrintStream printStream = System.err;
                            Object[] arrobject = new Object[]{file.getPath()};
                            printStream.println(String.format((String)"warning: cannot open %s for reading. Will continue looking.", (Object[])arrobject));
                            dexFile2 = dexFile;
                        } else {
                            try {
                                dexFile2 = new DexFile(file, false, true);
                            }
                            catch (DexFile.NoClassesDexException noClassesDexException) {
                                dexFile2 = dexFile;
                            }
                            catch (Exception exception) {
                                throw ExceptionWithContext.withContext(exception, "Error while reading boot class path entry \"" + string2 + "\".");
                            }
                        }
                    }
                    if (dexFile == null) {
                        ++n2;
                        continue block5;
                    }
                    try {
                        ClassPath.super.loadDexFile(file.getPath(), dexFile);
                        return;
                    }
                    catch (Exception exception) {
                        throw ExceptionWithContext.withContext(exception, String.format((String)"Error while loading boot classpath entry %s", (Object[])new Object[]{string2}));
                    }
                }
                dexFile2 = dexFile;
                ++n5;
                dexFile = dexFile2;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ClassDef loadClassDef(String string2) {
        ClassDef classDef;
        ClassDef classDef2 = ClassPath.getClassDef(string2, false);
        if (classDef2 != null) return classDef2;
        TempClassInfo tempClassInfo = (TempClassInfo)ClassPath.theClassPath.tempClasses.get((Object)string2);
        if (tempClassInfo == null) {
            return null;
        }
        try {
            classDef = new ClassDef(tempClassInfo);
        }
        catch (Exception exception) {}
        try {
            ClassPath.theClassPath.classDefs.put((Object)classDef.classType, (Object)classDef);
            return classDef;
        }
        catch (Exception exception) {}
        {
            void var4_5;
            Object[] arrobject = new Object[]{tempClassInfo.classType, tempClassInfo.dexFilePath};
            throw ExceptionWithContext.withContext((Throwable)var4_5, String.format((String)"Error while loading class %s from file %s", (Object[])arrobject));
        }
    }

    private void loadDexFile(String string2, DexFile dexFile) {
        for (ClassDefItem classDefItem : dexFile.ClassDefsSection.getItems()) {
            try {
                TempClassInfo tempClassInfo = new TempClassInfo(string2, classDefItem);
                this.tempClasses.put((Object)tempClassInfo.classType, (Object)tempClassInfo);
            }
            catch (Exception exception) {
                Object[] arrobject = new Object[]{classDefItem.getClassType().getTypeDescriptor()};
                throw ExceptionWithContext.withContext(exception, String.format((String)"Error while loading class %s", (Object[])arrobject));
            }
        }
    }

    public static class ArrayClassDef
    extends ClassDef {
        static final /* synthetic */ boolean $assertionsDisabled;
        private final int arrayDimensions;
        private final ClassDef elementClass;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !ClassPath.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        protected ArrayClassDef(String string2) {
            super(string2, 0);
            if (!$assertionsDisabled && string2.charAt(0) != '[') {
                throw new AssertionError();
            }
            int n = 0;
            while (string2.charAt(n) == '[') {
                ++n;
            }
            String string3 = string2.substring(n);
            if (n > 256) {
                throw new ExceptionWithContext("Error while creating array class for element type " + string3 + " with " + n + " dimensions. The maximum number of dimensions is 256");
            }
            try {
                this.elementClass = ClassPath.getClassDef(string2.substring(n));
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw ExceptionWithContext.withContext((Throwable)((Object)classNotFoundException), "Error while creating array class " + string2);
            }
            this.arrayDimensions = n;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public boolean extendsClass(ClassDef classDef) {
            if (!(classDef instanceof ArrayClassDef)) {
                if (classDef == ClassPath.access$600().javaLangObjectClassDef) return true;
                {
                    if (!classDef.isInterface) return false;
                    return this.implementsInterface(classDef);
                }
            }
            ArrayClassDef arrayClassDef = (ArrayClassDef)classDef;
            if (this.arrayDimensions == arrayClassDef.arrayDimensions) {
                ClassDef classDef2 = arrayClassDef.getBaseElementClass();
                if (classDef2.isInterface) return true;
                return classDef2.extendsClass(arrayClassDef.getBaseElementClass());
            }
            if (this.arrayDimensions <= arrayClassDef.arrayDimensions) {
                return false;
            }
            ClassDef classDef3 = arrayClassDef.getBaseElementClass();
            if (!classDef3.isInterface && classDef3 != ClassPath.access$600().javaLangObjectClassDef) return false;
            return true;
        }

        public int getArrayDimensions() {
            return this.arrayDimensions;
        }

        public ClassDef getBaseElementClass() {
            return this.elementClass;
        }

        public ClassDef getImmediateElementClass() {
            if (this.arrayDimensions == 1) {
                return this.elementClass;
            }
            return ClassPath.getArrayClassDefByElementClassAndDimension(this.elementClass, -1 + this.arrayDimensions);
        }
    }

    public static class ClassDef
    implements Comparable<ClassDef> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final int ArrayClassDef = 0;
        public static final int PrimitiveClassDef = 1;
        public static final int UnresolvedClassDef = 2;
        private final int classDepth;
        private final String classType;
        private final TreeSet<ClassDef> implementedInterfaces;
        private final SparseArray<FieldDef> instanceFields;
        private LinkedHashMap<String, ClassDef> interfaceTable;
        private final boolean isInterface;
        private final ClassDef superclass;
        private final HashMap<String, Integer> virtualMethodLookup;
        private String[] virtualMethods;
        private final String[] vtable;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !ClassPath.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        protected ClassDef(String string2, int n) {
            if (n == 0) {
                if (!$assertionsDisabled && string2.charAt(0) != '[') {
                    throw new AssertionError();
                }
                this.classType = string2;
                this.superclass = ClassPath.access$600().javaLangObjectClassDef;
                this.implementedInterfaces = new TreeSet();
                this.implementedInterfaces.add((Object)ClassPath.getClassDef("Ljava/lang/Cloneable;"));
                this.implementedInterfaces.add((Object)ClassPath.getClassDef("Ljava/io/Serializable;"));
                this.isInterface = false;
                this.vtable = this.superclass.vtable;
                this.virtualMethodLookup = this.superclass.virtualMethodLookup;
                this.instanceFields = this.superclass.instanceFields;
                this.classDepth = 1;
                this.virtualMethods = null;
                this.interfaceTable = null;
                return;
            }
            if (n == 1) {
                if (!($assertionsDisabled || string2.charAt(0) != '[' && string2.charAt(0) != 'L')) {
                    throw new AssertionError();
                }
                this.classType = string2;
                this.superclass = null;
                this.implementedInterfaces = null;
                this.isInterface = false;
                this.vtable = null;
                this.virtualMethodLookup = null;
                this.instanceFields = null;
                this.classDepth = 0;
                this.virtualMethods = null;
                this.interfaceTable = null;
                return;
            }
            if (!$assertionsDisabled && string2.charAt(0) != 'L') {
                throw new AssertionError();
            }
            this.classType = string2;
            this.superclass = ClassPath.access$600().javaLangObjectClassDef;
            this.implementedInterfaces = new TreeSet();
            this.isInterface = false;
            this.vtable = this.superclass.vtable;
            this.virtualMethodLookup = this.superclass.virtualMethodLookup;
            this.instanceFields = this.superclass.instanceFields;
            this.classDepth = 1;
            this.virtualMethods = null;
            this.interfaceTable = null;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected ClassDef(TempClassInfo tempClassInfo) {
            this.classType = tempClassInfo.classType;
            this.isInterface = tempClassInfo.isInterface;
            this.superclass = ClassDef.super.loadSuperclass(tempClassInfo);
            this.classDepth = this.superclass == null ? 0 : 1 + this.superclass.classDepth;
            this.implementedInterfaces = ClassDef.super.loadAllImplementedInterfaces(tempClassInfo);
            this.interfaceTable = ClassDef.super.loadInterfaceTable(tempClassInfo);
            this.virtualMethods = tempClassInfo.virtualMethods;
            this.vtable = ClassDef.super.loadVtable(tempClassInfo);
            this.virtualMethodLookup = new HashMap((int)Math.ceil((double)((float)this.vtable.length / 0.7f)), 0.75f);
            int n = 0;
            do {
                if (n >= this.vtable.length) {
                    this.instanceFields = ClassDef.super.loadFields(tempClassInfo);
                    return;
                }
                this.virtualMethodLookup.put((Object)this.vtable[n], (Object)n);
                ++n;
            } while (true);
        }

        static /* synthetic */ ClassDef access$300(ClassDef classDef) {
            return classDef.superclass;
        }

        private byte getFieldType(String string2) {
            char c = string2.charAt(0);
            byte by = 0;
            switch (c) {
                default: {
                    by = 2;
                }
                case 'L': 
                case '[': {
                    return by;
                }
                case 'D': 
                case 'J': 
            }
            return 1;
        }

        private int getNextFieldOffset() {
            if (this.instanceFields == null || this.instanceFields.size() == 0) {
                return 8;
            }
            int n = -1 + this.instanceFields.size();
            int n2 = this.instanceFields.keyAt(n);
            switch (this.instanceFields.valueAt((int)n).type.charAt(0)) {
                default: {
                    return n2 + 4;
                }
                case 'D': 
                case 'J': 
            }
            return n2 + 8;
        }

        private TreeSet<ClassDef> loadAllImplementedInterfaces(TempClassInfo tempClassInfo) {
            if (!$assertionsDisabled && this.classType == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !this.classType.equals((Object)"Ljava/lang/Object;") && this.superclass == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && tempClassInfo == null) {
                throw new AssertionError();
            }
            TreeSet treeSet = new TreeSet();
            if (this.superclass != null) {
                Iterator iterator = this.superclass.implementedInterfaces.iterator();
                while (iterator.hasNext()) {
                    treeSet.add((Object)((ClassDef)iterator.next()));
                }
            }
            if (tempClassInfo.interfaces != null) {
                for (String string2 : tempClassInfo.interfaces) {
                    ClassDef classDef = ClassPath.loadClassDef(string2);
                    if (classDef == null) {
                        throw new ClassNotFoundException(String.format((String)"Could not find interface %s", (Object[])new Object[]{string2}));
                    }
                    if (!$assertionsDisabled && !classDef.isInterface()) {
                        throw new AssertionError();
                    }
                    treeSet.add((Object)classDef);
                    ClassDef classDef2 = classDef.getSuperclass();
                    while (!classDef2.getClassType().equals((Object)"Ljava/lang/Object;")) {
                        if (!$assertionsDisabled && !classDef2.isInterface()) {
                            throw new AssertionError();
                        }
                        treeSet.add((Object)classDef2);
                        classDef2 = classDef2.getSuperclass();
                    }
                }
            }
            return treeSet;
        }

        /*
         * Enabled aggressive block sorting
         */
        private SparseArray<FieldDef> loadFields(TempClassInfo tempClassInfo) {
            int n;
            int n2;
            int n3;
            int n4;
            String[][] arrstring = tempClassInfo.instanceFields;
            byte[] arrby = null;
            FieldDef[] arrfieldDef = null;
            if (arrstring != null) {
                int n5;
                arrfieldDef = new FieldDef[tempClassInfo.instanceFields.length];
                arrby = new byte[arrfieldDef.length];
                for (int i = 0; i < (n5 = arrfieldDef.length); ++i) {
                    FieldDef fieldDef;
                    String[] arrstring2 = tempClassInfo.instanceFields[i];
                    String string2 = arrstring2[0];
                    String string3 = arrstring2[1];
                    arrby[i] = ClassDef.super.getFieldType(string3);
                    arrfieldDef[i] = fieldDef = new FieldDef(tempClassInfo.classType, string2, string3);
                }
            }
            if (arrfieldDef == null) {
                arrfieldDef = new FieldDef[]{};
                arrby = new byte[]{};
            }
            int n6 = -1 + arrfieldDef.length;
            for (n3 = 0; n3 < (n = arrfieldDef.length); ++n3) {
                block23 : {
                    if (arrby[n3] != 0) {
                        int n7;
                        for (n7 = n6; n7 > n3; --n7) {
                            if (arrby[n7] != 0) continue;
                            n6 = n7 - 1;
                            ClassDef.super.swap(arrby, arrfieldDef, n3, n7);
                            break block23;
                        }
                        n6 = n7;
                    }
                }
                if (arrby[n3] != 0) break;
            }
            int n8 = 8;
            if (this.superclass != null) {
                n8 = this.superclass.getNextFieldOffset();
            }
            int n9 = n8 % 8 == 0 ? 0 : 1;
            int n10 = arrfieldDef.length;
            if (n3 < n10 && n3 % 2 != n9) {
                if (arrby[n3] != 1) {
                    ++n3;
                } else {
                    for (int i = -1 + arrfieldDef.length; i > n3; --i) {
                        if (arrby[i] != 2) continue;
                        int n11 = n3 + 1;
                        ClassDef.super.swap(arrby, arrfieldDef, n3, i);
                        n3 = n11;
                        break;
                    }
                }
            }
            int n12 = -1 + arrfieldDef.length;
            while (n3 < (n4 = arrfieldDef.length)) {
                block24 : {
                    if (arrby[n3] != 1) {
                        int n13;
                        for (n13 = n12; n13 > n3; --n13) {
                            if (arrby[n13] != 1) continue;
                            n12 = n13 - 1;
                            ClassDef.super.swap(arrby, arrfieldDef, n3, n13);
                            break block24;
                        }
                        n12 = n13;
                    }
                }
                if (arrby[n3] != 1) break;
                ++n3;
            }
            ClassDef classDef = this.superclass;
            int n14 = 0;
            if (classDef != null) {
                n14 = this.superclass.instanceFields.size();
            }
            int n15 = n14 + arrfieldDef.length;
            SparseArray<FieldDef> sparseArray = new SparseArray<FieldDef>(n15);
            if (this.superclass != null && n14 > 0) {
                for (int i = 0; i < n14; ++i) {
                    sparseArray.append(this.superclass.instanceFields.keyAt(i), this.superclass.instanceFields.valueAt(i));
                }
                int n16 = sparseArray.keyAt(n14 - 1);
                char c = this.superclass.instanceFields.valueAt((int)(n14 - 1)).type.charAt(0);
                n2 = c == 'J' || c == 'D' ? n16 + 8 : n16 + 4;
            } else {
                n2 = 8;
            }
            boolean bl = false;
            int n17 = 0;
            int n18;
            while (n17 < (n18 = arrfieldDef.length)) {
                FieldDef fieldDef = arrfieldDef[n17];
                if (arrby[n17] == 1 && !bl && !bl) {
                    if (n2 % 8 != 0) {
                        if (!$assertionsDisabled && n2 % 8 != 4) {
                            throw new AssertionError();
                        }
                        n2 += 4;
                    }
                    bl = true;
                }
                sparseArray.append(n2, fieldDef);
                n2 = arrby[n17] == 1 ? (n2 += 8) : (n2 += 4);
                ++n17;
            }
            return sparseArray;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private LinkedHashMap<String, ClassDef> loadInterfaceTable(TempClassInfo tempClassInfo) {
            if (tempClassInfo.interfaces == null) {
                return null;
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            String[] arrstring = tempClassInfo.interfaces;
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String string2 = arrstring[n2];
                if (!linkedHashMap.containsKey((Object)string2)) {
                    ClassDef classDef = ClassPath.loadClassDef(string2);
                    if (classDef == null) {
                        throw new ClassNotFoundException(String.format((String)"Could not find interface %s", (Object[])new Object[]{string2}));
                    }
                    linkedHashMap.put((Object)string2, (Object)classDef);
                    if (classDef.interfaceTable != null) {
                        for (ClassDef classDef2 : classDef.interfaceTable.values()) {
                            if (linkedHashMap.containsKey((Object)classDef2.classType)) continue;
                            linkedHashMap.put((Object)classDef2.classType, (Object)classDef2);
                        }
                    }
                }
                ++n2;
            }
            return linkedHashMap;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private ClassDef loadSuperclass(TempClassInfo tempClassInfo) {
            if (tempClassInfo.classType.equals((Object)"Ljava/lang/Object;")) {
                if (tempClassInfo.superclassType == null) return null;
                throw new ExceptionWithContext("Invalid superclass " + tempClassInfo.superclassType + " for Ljava/lang/Object;. " + "The Object class cannot have a superclass");
            }
            String string2 = tempClassInfo.superclassType;
            if (string2 == null) {
                throw new ExceptionWithContext(tempClassInfo.classType + " has no superclass");
            }
            ClassDef classDef = ClassPath.loadClassDef(string2);
            if (classDef == null) {
                throw new ClassNotFoundException(String.format((String)"Could not find superclass %s", (Object[])new Object[]{string2}));
            }
            if (!this.isInterface && classDef.isInterface) {
                throw new ValidationException("Class " + this.classType + " has the interface " + classDef.classType + " as its superclass");
            }
            if (!this.isInterface) return classDef;
            if (classDef.isInterface) return classDef;
            if (classDef == ClassPath.access$600().javaLangObjectClassDef) return classDef;
            throw new ValidationException("Interface " + this.classType + " has the non-interface class " + classDef.classType + " as its superclass");
        }

        /*
         * Enabled aggressive block sorting
         */
        private String[] loadVtable(TempClassInfo tempClassInfo) {
            LinkedList linkedList = new LinkedList();
            HashMap hashMap = new HashMap();
            ClassDef classDef = this.superclass;
            int n = 0;
            if (classDef != null) {
                String[] arrstring = this.superclass.vtable;
                int n2 = arrstring.length;
                int n3 = 0;
                for (int i = 0; i < n2; ++i) {
                    String string2 = arrstring[i];
                    linkedList.add((Object)string2);
                    int n4 = n3 + 1;
                    hashMap.put((Object)string2, (Object)n3);
                    n3 = n4;
                }
                if (!$assertionsDisabled && this.superclass.instanceFields == null) {
                    throw new AssertionError();
                }
                n = n3;
            }
            if (!this.isInterface) {
                if (tempClassInfo.virtualMethods != null) {
                    String[] arrstring = tempClassInfo.virtualMethods;
                    int n5 = arrstring.length;
                    int n6 = n;
                    for (int i = 0; i < n5; ++i) {
                        int n7;
                        String string3 = arrstring[i];
                        if (hashMap.get((Object)string3) == null) {
                            linkedList.add((Object)string3);
                            n7 = n6 + 1;
                            hashMap.put((Object)string3, (Object)n6);
                        } else {
                            n7 = n6;
                        }
                        n6 = n7;
                    }
                    n = n6;
                }
                if (this.interfaceTable != null) {
                    for (ClassDef classDef2 : this.interfaceTable.values()) {
                        if (classDef2.virtualMethods == null) continue;
                        String[] arrstring = classDef2.virtualMethods;
                        int n8 = arrstring.length;
                        int n9 = n;
                        for (int i = 0; i < n8; ++i) {
                            int n10;
                            String string4 = arrstring[i];
                            if (hashMap.get((Object)string4) == null) {
                                linkedList.add((Object)string4);
                                n10 = n9 + 1;
                                hashMap.put((Object)string4, (Object)n9);
                            } else {
                                n10 = n9;
                            }
                            n9 = n10;
                        }
                        n = n9;
                    }
                }
            }
            String[] arrstring = new String[linkedList.size()];
            int n11 = 0;
            while (n11 < linkedList.size()) {
                arrstring[n11] = (String)linkedList.get(n11);
                ++n11;
            }
            return arrstring;
        }

        private void swap(byte[] arrby, FieldDef[] arrfieldDef, int n, int n2) {
            byte by = arrby[n];
            arrby[n] = arrby[n2];
            arrby[n2] = by;
            FieldDef fieldDef = arrfieldDef[n];
            arrfieldDef[n] = arrfieldDef[n2];
            arrfieldDef[n2] = fieldDef;
        }

        public int compareTo(ClassDef classDef) {
            return this.classType.compareTo(classDef.classType);
        }

        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof ClassDef)) {
                return false;
            }
            ClassDef classDef = (ClassDef)object;
            return this.classType.equals((Object)classDef.classType);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean extendsClass(ClassDef classDef) {
            boolean bl = true;
            if (classDef == null) {
                return false;
            }
            if (this == classDef) return bl;
            if (classDef instanceof UnresolvedClassDef) {
                throw ((UnresolvedClassDef)classDef).unresolvedValidationException();
            }
            int n = classDef.classDepth;
            ClassDef classDef2 = this;
            while (classDef2.classDepth > n) {
                classDef2 = classDef2.getSuperclass();
            }
            if (classDef2 == classDef) return bl;
            return false;
        }

        public int getClassDepth() {
            return this.classDepth;
        }

        public String getClassType() {
            return this.classType;
        }

        public FieldDef getInstanceField(int n) {
            return this.instanceFields.get(n, null);
        }

        public ClassDef getSuperclass() {
            return this.superclass;
        }

        public String getVirtualMethod(int n) {
            if (n < 0 || n >= this.vtable.length) {
                return null;
            }
            return this.vtable[n];
        }

        public boolean hasVirtualMethod(String string2) {
            return this.virtualMethodLookup.containsKey((Object)string2);
        }

        public int hashCode() {
            return this.classType.hashCode();
        }

        public boolean implementsInterface(ClassDef classDef) {
            if (!$assertionsDisabled && classDef instanceof UnresolvedClassDef) {
                throw new AssertionError();
            }
            return this.implementedInterfaces.contains((Object)classDef);
        }

        public boolean isInterface() {
            return this.isInterface;
        }
    }

    private static class ClassNotFoundException
    extends ExceptionWithContext {
        public ClassNotFoundException(String string2) {
            super(string2);
        }
    }

    public static interface ClassPathErrorHandler {
        public void ClassPathError(String var1, Exception var2);
    }

    public static class FieldDef {
        public final String definingClass;
        public final String name;
        public final String type;

        public FieldDef(String string2, String string3, String string4) {
            this.definingClass = string2;
            this.name = string3;
            this.type = string4;
        }
    }

    public static class PrimitiveClassDef
    extends ClassDef {
        static final /* synthetic */ boolean $assertionsDisabled;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !ClassPath.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        protected PrimitiveClassDef(String string2) {
            super(string2, 1);
            if (!($assertionsDisabled || string2.charAt(0) != 'L' && string2.charAt(0) != '[')) {
                throw new AssertionError();
            }
        }
    }

    private static class TempClassInfo {
        public final String classType;
        public final String dexFilePath;
        public final String[][] instanceFields;
        public final String[] interfaces;
        public final boolean isInterface;
        public final String superclassType;
        public final String[] virtualMethods;

        /*
         * Enabled aggressive block sorting
         */
        public TempClassInfo(String string2, ClassDefItem classDefItem) {
            this.dexFilePath = string2;
            this.classType = classDefItem.getClassType().getTypeDescriptor();
            boolean bl = (classDefItem.getAccessFlags() & AccessFlags.INTERFACE.getValue()) != 0;
            this.isInterface = bl;
            TypeIdItem typeIdItem = classDefItem.getSuperclass();
            this.superclassType = typeIdItem == null ? null : typeIdItem.getTypeDescriptor();
            this.interfaces = TempClassInfo.super.loadInterfaces(classDefItem);
            ClassDataItem classDataItem = classDefItem.getClassData();
            if (classDataItem != null) {
                this.virtualMethods = TempClassInfo.super.loadVirtualMethods(classDataItem);
                this.instanceFields = TempClassInfo.super.loadInstanceFields(classDataItem);
                return;
            }
            this.virtualMethods = null;
            this.instanceFields = null;
        }

        private String[][] loadInstanceFields(ClassDataItem classDataItem) {
            String[][] arrstring;
            ClassDataItem.EncodedField[] arrencodedField = classDataItem.getInstanceFields();
            if (arrencodedField != null && arrencodedField.length > 0) {
                arrstring = (String[][])Array.newInstance(String.class, (int[])new int[]{arrencodedField.length, 2});
                for (int i = 0; i < arrencodedField.length; ++i) {
                    ClassDataItem.EncodedField encodedField = arrencodedField[i];
                    arrstring[i][0] = encodedField.field.getFieldName().getStringValue();
                    arrstring[i][1] = encodedField.field.getFieldType().getTypeDescriptor();
                }
            } else {
                arrstring = null;
            }
            return arrstring;
        }

        private String[] loadInterfaces(ClassDefItem classDefItem) {
            String[] arrstring;
            List<TypeIdItem> list;
            TypeListItem typeListItem = classDefItem.getInterfaces();
            if (typeListItem != null && (list = typeListItem.getTypes()) != null && list.size() > 0) {
                arrstring = new String[list.size()];
                for (int i = 0; i < arrstring.length; ++i) {
                    arrstring[i] = ((TypeIdItem)list.get(i)).getTypeDescriptor();
                }
            } else {
                arrstring = null;
            }
            return arrstring;
        }

        private String[] loadVirtualMethods(ClassDataItem classDataItem) {
            String[] arrstring;
            ClassDataItem.EncodedMethod[] arrencodedMethod = classDataItem.getVirtualMethods();
            if (arrencodedMethod != null && arrencodedMethod.length > 0) {
                arrstring = new String[arrencodedMethod.length];
                for (int i = 0; i < arrencodedMethod.length; ++i) {
                    arrstring[i] = arrencodedMethod[i].method.getVirtualMethodString();
                }
            } else {
                arrstring = null;
            }
            return arrstring;
        }
    }

    public static class UnresolvedClassDef
    extends ClassDef {
        static final /* synthetic */ boolean $assertionsDisabled;

        /*
         * Enabled aggressive block sorting
         */
        static {
            boolean bl = !ClassPath.class.desiredAssertionStatus();
            $assertionsDisabled = bl;
        }

        protected UnresolvedClassDef(String string2) {
            super(string2, 2);
            if (!$assertionsDisabled && string2.charAt(0) != 'L') {
                throw new AssertionError();
            }
        }

        @Override
        public boolean extendsClass(ClassDef classDef) {
            if (classDef != ClassPath.access$600().javaLangObjectClassDef && classDef != this) {
                throw this.unresolvedValidationException();
            }
            return true;
        }

        @Override
        public int getClassDepth() {
            throw this.unresolvedValidationException();
        }

        @Override
        public ClassDef getSuperclass() {
            throw this.unresolvedValidationException();
        }

        @Override
        public boolean hasVirtualMethod(String string2) {
            if (!super.hasVirtualMethod(string2)) {
                throw this.unresolvedValidationException();
            }
            return true;
        }

        @Override
        public boolean implementsInterface(ClassDef classDef) {
            throw this.unresolvedValidationException();
        }

        @Override
        public boolean isInterface() {
            throw this.unresolvedValidationException();
        }

        protected ValidationException unresolvedValidationException() {
            Object[] arrobject = new Object[]{this.getClassType()};
            return new ValidationException(String.format((String)"class %s cannot be resolved.", (Object[])arrobject));
        }
    }

}

