/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.Writer
 *  java.lang.AssertionError
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.HashMap
 */
package org.jf.dexlib.Code.Analysis;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import org.jf.dexlib.Code.Analysis.ClassPath;
import org.jf.dexlib.Code.Analysis.ValidationException;
import org.jf.dexlib.TypeIdItem;

public class RegisterType {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final HashMap<RegisterType, RegisterType> internedRegisterTypes;
    public final Category category;
    public final ClassPath.ClassDef type;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !RegisterType.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        internedRegisterTypes = new HashMap();
    }

    private RegisterType(Category category, ClassPath.ClassDef classDef) {
        if (!($assertionsDisabled || (category == Category.Reference || category == Category.UninitRef || category == Category.UninitThis) && classDef != null || category != Category.Reference && category != Category.UninitRef && category != Category.UninitThis && classDef == null)) {
            throw new AssertionError();
        }
        this.category = category;
        this.type = classDef;
    }

    public static RegisterType getRegisterType(Category category, ClassPath.ClassDef classDef) {
        RegisterType registerType = new RegisterType(category, classDef);
        RegisterType registerType2 = (RegisterType)internedRegisterTypes.get((Object)registerType);
        if (registerType2 == null) {
            internedRegisterTypes.put((Object)registerType, (Object)registerType);
            return registerType;
        }
        return registerType2;
    }

    public static RegisterType getRegisterTypeForLiteral(long l) {
        if (l < -32768L) {
            return RegisterType.getRegisterType(Category.Integer, null);
        }
        if (l < -128L) {
            return RegisterType.getRegisterType(Category.Short, null);
        }
        if (l < 0L) {
            return RegisterType.getRegisterType(Category.Byte, null);
        }
        if (l == 0L) {
            return RegisterType.getRegisterType(Category.Null, null);
        }
        if (l == 1L) {
            return RegisterType.getRegisterType(Category.One, null);
        }
        if (l < 128L) {
            return RegisterType.getRegisterType(Category.PosByte, null);
        }
        if (l < 32768L) {
            return RegisterType.getRegisterType(Category.PosShort, null);
        }
        if (l < 65536L) {
            return RegisterType.getRegisterType(Category.Char, null);
        }
        return RegisterType.getRegisterType(Category.Integer, null);
    }

    public static RegisterType getRegisterTypeForType(String string2) {
        switch (string2.charAt(0)) {
            default: {
                throw new RuntimeException("Invalid type: " + string2);
            }
            case 'V': {
                throw new ValidationException("The V type can only be used as a method return type");
            }
            case 'Z': {
                return RegisterType.getRegisterType(Category.Boolean, null);
            }
            case 'B': {
                return RegisterType.getRegisterType(Category.Byte, null);
            }
            case 'S': {
                return RegisterType.getRegisterType(Category.Short, null);
            }
            case 'C': {
                return RegisterType.getRegisterType(Category.Char, null);
            }
            case 'I': {
                return RegisterType.getRegisterType(Category.Integer, null);
            }
            case 'F': {
                return RegisterType.getRegisterType(Category.Float, null);
            }
            case 'J': {
                return RegisterType.getRegisterType(Category.LongLo, null);
            }
            case 'D': {
                return RegisterType.getRegisterType(Category.DoubleLo, null);
            }
            case 'L': 
            case '[': 
        }
        return RegisterType.getRegisterType(Category.Reference, ClassPath.getClassDef(string2));
    }

    public static RegisterType getRegisterTypeForTypeIdItem(TypeIdItem typeIdItem) {
        return RegisterType.getRegisterTypeForType(typeIdItem.getTypeDescriptor());
    }

    public static RegisterType getUnitializedReference(ClassPath.ClassDef classDef) {
        return new RegisterType(Category.UninitRef, classDef);
    }

    public static RegisterType getWideRegisterTypeForTypeIdItem(TypeIdItem typeIdItem, boolean bl) {
        if (typeIdItem.getRegisterCount() == 1) {
            throw new RuntimeException("Cannot use this method for non-wide register type: " + typeIdItem.getTypeDescriptor());
        }
        switch (typeIdItem.getTypeDescriptor().charAt(0)) {
            default: {
                throw new RuntimeException("Invalid type: " + typeIdItem.getTypeDescriptor());
            }
            case 'J': {
                if (bl) {
                    return RegisterType.getRegisterType(Category.LongLo, null);
                }
                return RegisterType.getRegisterType(Category.LongHi, null);
            }
            case 'D': 
        }
        if (bl) {
            return RegisterType.getRegisterType(Category.DoubleLo, null);
        }
        return RegisterType.getRegisterType(Category.DoubleHi, null);
    }

    public boolean canBeAssignedTo(RegisterType registerType) {
        if (Category.assigmentTable[this.category.ordinal()][registerType.category.ordinal()]) {
            if (this.category == Category.Reference && registerType.category == Category.Reference && !registerType.type.isInterface()) {
                return this.type.extendsClass(registerType.type);
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        RegisterType registerType = (RegisterType)object;
        if (this.category != registerType.category) {
            return false;
        }
        if (this.type != null) {
            if (this.type.equals(registerType.type)) return true;
            return false;
        }
        if (registerType.type == null) return true;
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        int n2 = 31 * this.category.hashCode();
        if (this.type != null) {
            n = this.type.hashCode();
            do {
                return n2 + n;
                break;
            } while (true);
        }
        n = 0;
        return n2 + n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public RegisterType merge(RegisterType registerType) {
        block7 : {
            block6 : {
                if (registerType == null || registerType == this) break block6;
                Category category = Category.mergeTable[this.category.ordinal()][registerType.category.ordinal()];
                Category category2 = Category.Reference;
                ClassPath.ClassDef classDef = null;
                if (category == category2) {
                    classDef = ClassPath.getCommonSuperclass(this.type, registerType.type);
                }
                if (category != Category.UninitRef && category != Category.UninitThis) {
                    return RegisterType.getRegisterType(category, classDef);
                }
                if (this.category == Category.Unknown) {
                    return registerType;
                }
                if (!$assertionsDisabled && registerType.category != Category.Unknown) break block7;
            }
            return this;
        }
        throw new AssertionError();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String toString() {
        String string2;
        StringBuilder stringBuilder = new StringBuilder().append("(").append(this.category.name());
        if (this.type == null) {
            string2 = "";
            do {
                return stringBuilder.append(string2).append(")").toString();
                break;
            } while (true);
        }
        string2 = "," + this.type.getClassType();
        return stringBuilder.append(string2).append(")").toString();
    }

    public void writeTo(Writer writer) throws IOException {
        writer.write(40);
        writer.write(this.category.name());
        if (this.type != null) {
            writer.write(44);
            writer.write(this.type.getClassType());
        }
        writer.write(41);
    }

    public static final class Category
    extends Enum<Category> {
        private static final /* synthetic */ Category[] $VALUES;
        public static final /* enum */ Category Boolean;
        public static final /* enum */ Category Byte;
        public static final /* enum */ Category Char;
        public static final /* enum */ Category Conflicted;
        public static final /* enum */ Category DoubleHi;
        public static final /* enum */ Category DoubleLo;
        public static final /* enum */ Category Float;
        public static final /* enum */ Category Integer;
        public static final /* enum */ Category LongHi;
        public static final /* enum */ Category LongLo;
        public static final /* enum */ Category Null;
        public static final /* enum */ Category One;
        public static final /* enum */ Category PosByte;
        public static final /* enum */ Category PosShort;
        public static final /* enum */ Category Reference;
        public static final /* enum */ Category Short;
        public static final /* enum */ Category Uninit;
        public static final /* enum */ Category UninitRef;
        public static final /* enum */ Category UninitThis;
        public static final /* enum */ Category Unknown;
        protected static boolean[][] assigmentTable;
        protected static Category[][] mergeTable;

        static {
            Unknown = new Category();
            Uninit = new Category();
            Null = new Category();
            One = new Category();
            Boolean = new Category();
            Byte = new Category();
            PosByte = new Category();
            Short = new Category();
            PosShort = new Category();
            Char = new Category();
            Integer = new Category();
            Float = new Category();
            LongLo = new Category();
            LongHi = new Category();
            DoubleLo = new Category();
            DoubleHi = new Category();
            UninitRef = new Category();
            UninitThis = new Category();
            Reference = new Category();
            Conflicted = new Category();
            Category[] arrcategory = new Category[]{Unknown, Uninit, Null, One, Boolean, Byte, PosByte, Short, PosShort, Char, Integer, Float, LongLo, LongHi, DoubleLo, DoubleHi, UninitRef, UninitThis, Reference, Conflicted};
            $VALUES = arrcategory;
            Category[][] arrarrcategory = new Category[20][];
            Category[] arrcategory2 = new Category[]{Unknown, Uninit, Null, One, Boolean, Byte, PosByte, Short, PosShort, Char, Integer, Float, LongLo, LongHi, DoubleLo, DoubleHi, UninitRef, UninitThis, Reference, Conflicted};
            arrarrcategory[0] = arrcategory2;
            Category[] arrcategory3 = new Category[]{Uninit, Uninit, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[1] = arrcategory3;
            Category[] arrcategory4 = new Category[]{Null, Conflicted, Null, Boolean, Boolean, Byte, PosByte, Short, PosShort, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Reference, Conflicted};
            arrarrcategory[2] = arrcategory4;
            Category[] arrcategory5 = new Category[]{One, Conflicted, Boolean, One, Boolean, Byte, PosByte, Short, PosShort, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[3] = arrcategory5;
            Category[] arrcategory6 = new Category[]{Boolean, Conflicted, Boolean, Boolean, Boolean, Byte, PosByte, Short, PosShort, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[4] = arrcategory6;
            Category[] arrcategory7 = new Category[]{Byte, Conflicted, Byte, Byte, Byte, Byte, Byte, Short, Short, Integer, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[5] = arrcategory7;
            Category[] arrcategory8 = new Category[]{PosByte, Conflicted, PosByte, PosByte, PosByte, Byte, PosByte, Short, PosShort, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[6] = arrcategory8;
            Category[] arrcategory9 = new Category[]{Short, Conflicted, Short, Short, Short, Short, Short, Short, Short, Integer, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[7] = arrcategory9;
            Category[] arrcategory10 = new Category[]{PosShort, Conflicted, PosShort, PosShort, PosShort, Short, PosShort, Short, PosShort, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[8] = arrcategory10;
            Category[] arrcategory11 = new Category[]{Char, Conflicted, Char, Char, Char, Integer, Char, Integer, Char, Char, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[9] = arrcategory11;
            Category[] arrcategory12 = new Category[]{Integer, Conflicted, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[10] = arrcategory12;
            Category[] arrcategory13 = new Category[]{Float, Conflicted, Float, Float, Float, Float, Float, Float, Float, Float, Integer, Float, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[11] = arrcategory13;
            Category[] arrcategory14 = new Category[]{LongLo, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, LongLo, Conflicted, LongLo, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[12] = arrcategory14;
            Category[] arrcategory15 = new Category[]{LongHi, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, LongHi, Conflicted, LongHi, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[13] = arrcategory15;
            Category[] arrcategory16 = new Category[]{DoubleLo, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, LongLo, Conflicted, DoubleLo, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[14] = arrcategory16;
            Category[] arrcategory17 = new Category[]{DoubleHi, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, LongHi, Conflicted, DoubleHi, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[15] = arrcategory17;
            Category[] arrcategory18 = new Category[]{UninitRef, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[16] = arrcategory18;
            Category[] arrcategory19 = new Category[]{UninitThis, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, UninitThis, Conflicted, Conflicted};
            arrarrcategory[17] = arrcategory19;
            Category[] arrcategory20 = new Category[]{Reference, Conflicted, Reference, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Reference, Conflicted};
            arrarrcategory[18] = arrcategory20;
            Category[] arrcategory21 = new Category[]{Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted, Conflicted};
            arrarrcategory[19] = arrcategory21;
            mergeTable = arrarrcategory;
            assigmentTable = new boolean[][]{{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, {false, false, true, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, true, false}, {false, false, false, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, true, false, true, true, false, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, true, false, false, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, true, true, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false}, {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false}};
        }

        public static Category valueOf(String string2) {
            return (Category)Enum.valueOf(Category.class, (String)string2);
        }

        public static Category[] values() {
            return (Category[])$VALUES.clone();
        }
    }

}

