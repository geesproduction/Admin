/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 */
package org.jf.dexlib.Code.Analysis;

import java.util.HashMap;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.Code.Analysis.DexFileClassMap;
import org.jf.dexlib.Code.Format.Format;
import org.jf.dexlib.Code.Format.Instruction22c;
import org.jf.dexlib.Code.Instruction;
import org.jf.dexlib.Code.InstructionWithReference;
import org.jf.dexlib.Code.Opcode;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AccessFlags;

public class SyntheticAccessorResolver {
    public static final int GETTER = 1;
    public static final int METHOD = 0;
    public static final int SETTER = 2;
    private final DexFileClassMap classMap;
    private final HashMap<MethodIdItem, AccessedMember> resolvedAccessors = new HashMap();

    public SyntheticAccessorResolver(DexFile dexFile) {
        this.classMap = new DexFileClassMap(dexFile);
    }

    public static boolean looksLikeSyntheticAccessor(MethodIdItem methodIdItem) {
        return methodIdItem.getMethodName().getStringValue().startsWith("access$");
    }

    /*
     * Enabled aggressive block sorting
     */
    public AccessedMember getAccessedMember(MethodIdItem methodIdItem) {
        AccessedMember accessedMember;
        AccessedMember accessedMember2 = (AccessedMember)this.resolvedAccessors.get((Object)methodIdItem);
        if (accessedMember2 != null) {
            return accessedMember2;
        }
        ClassDefItem classDefItem = this.classMap.getClassDefByType(methodIdItem.getContainingClass());
        AccessedMember accessedMember3 = null;
        if (classDefItem == null) return accessedMember3;
        ClassDataItem.EncodedMethod encodedMethod = classDefItem.getClassData().findDirectMethodByMethodId(methodIdItem);
        accessedMember3 = null;
        if (encodedMethod == null) return accessedMember3;
        int n = encodedMethod.accessFlags & AccessFlags.SYNTHETIC.getValue();
        accessedMember3 = null;
        if (n == 0) return accessedMember3;
        Instruction[] arrinstruction = encodedMethod.codeItem.getInstructions();
        switch (1.$SwitchMap$org$jf$dexlib$Code$Format$Format[arrinstruction[0].opcode.format.ordinal()]) {
            default: {
                return null;
            }
            case 1: 
            case 2: {
                int n2 = arrinstruction.length;
                accessedMember3 = null;
                if (n2 < 2) return accessedMember3;
                int n3 = arrinstruction.length;
                accessedMember3 = null;
                if (n3 > 3) return accessedMember3;
                AccessedMember accessedMember4 = new AccessedMember(0, (MethodIdItem)((InstructionWithReference)arrinstruction[0]).getReferencedItem());
                this.resolvedAccessors.put((Object)methodIdItem, (Object)accessedMember4);
                return accessedMember4;
            }
            case 3: {
                int n4 = arrinstruction.length;
                accessedMember3 = null;
                if (n4 != 2) return accessedMember3;
                Instruction22c instruction22c = (Instruction22c)arrinstruction[0];
                FieldIdItem fieldIdItem = (FieldIdItem)instruction22c.getReferencedItem();
                accessedMember = instruction22c.opcode.setsRegister() || instruction22c.opcode.setsWideRegister() ? new AccessedMember(1, fieldIdItem) : new AccessedMember(2, fieldIdItem);
            }
        }
        this.resolvedAccessors.put((Object)methodIdItem, (Object)accessedMember);
        return accessedMember;
    }

    public static class AccessedMember {
        private final Item accessedMember;
        private final int accessedMemberType;

        public AccessedMember(int n, Item item) {
            this.accessedMemberType = n;
            this.accessedMember = item;
        }

        public Item getAccessedMember() {
            return this.accessedMember;
        }

        public int getAccessedMemberType() {
            return this.accessedMemberType;
        }
    }

}

