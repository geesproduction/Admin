/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.List
 */
package org.jf.dexlib.Code.Analysis;

import java.util.HashMap;
import java.util.List;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.TypeIdItem;

public class DexFileClassMap {
    private final HashMap<String, ClassDefItem> definedClasses = new HashMap();

    public DexFileClassMap(DexFile dexFile) {
        for (ClassDefItem classDefItem : dexFile.ClassDefsSection.getItems()) {
            this.definedClasses.put((Object)classDefItem.getClassType().getTypeDescriptor(), (Object)classDefItem);
        }
    }

    public ClassDefItem getClassDefByName(String string2) {
        return (ClassDefItem)this.definedClasses.get((Object)string2);
    }

    public ClassDefItem getClassDefByType(TypeIdItem typeIdItem) {
        return (ClassDefItem)this.definedClasses.get((Object)typeIdItem.getTypeDescriptor());
    }
}

