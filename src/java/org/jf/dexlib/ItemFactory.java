/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.NoSuchFieldError
 *  java.lang.Object
 */
package org.jf.dexlib;

import org.jf.dexlib.AnnotationDirectoryItem;
import org.jf.dexlib.AnnotationItem;
import org.jf.dexlib.AnnotationSetItem;
import org.jf.dexlib.AnnotationSetRefList;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DebugInfoItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedArrayItem;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.StringDataItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;

class ItemFactory {
    static final /* synthetic */ boolean $assertionsDisabled;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ItemFactory.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    ItemFactory() {
    }

    protected static Item makeItem(ItemType itemType, DexFile dexFile) {
        switch (1.$SwitchMap$org$jf$dexlib$ItemType[itemType.ordinal()]) {
            default: {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
            }
            case 1: {
                return new StringIdItem(dexFile);
            }
            case 2: {
                return new TypeIdItem(dexFile);
            }
            case 3: {
                return new ProtoIdItem(dexFile);
            }
            case 4: {
                return new FieldIdItem(dexFile);
            }
            case 5: {
                return new MethodIdItem(dexFile);
            }
            case 6: {
                return new ClassDefItem(dexFile);
            }
            case 7: {
                return new TypeListItem(dexFile);
            }
            case 8: {
                return new AnnotationSetRefList(dexFile);
            }
            case 9: {
                return new AnnotationSetItem(dexFile);
            }
            case 10: {
                return new ClassDataItem(dexFile);
            }
            case 11: {
                return new CodeItem(dexFile);
            }
            case 12: {
                return new StringDataItem(dexFile);
            }
            case 13: {
                return new DebugInfoItem(dexFile);
            }
            case 14: {
                return new AnnotationItem(dexFile);
            }
            case 15: {
                return new EncodedArrayItem(dexFile);
            }
            case 16: {
                return new AnnotationDirectoryItem(dexFile);
            }
        }
        return null;
    }

}

