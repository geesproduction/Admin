/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Comparable
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Comparator
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jf.dexlib.AnnotationDirectoryItem;
import org.jf.dexlib.ClassDataItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.EncodedArrayItem;
import org.jf.dexlib.EncodedValue.ArrayEncodedSubValue;
import org.jf.dexlib.EncodedValue.EncodedValue;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.TypeUtils;

public class ClassDefItem
extends Item<ClassDefItem> {
    static final /* synthetic */ boolean $assertionsDisabled;
    public int accessFlags;
    private AnnotationDirectoryItem annotations;
    private ClassDataItem classData;
    private TypeIdItem classType;
    public TypeListItem implementedInterfaces;
    public StringIdItem sourceFile;
    private EncodedArrayItem staticFieldInitializers;
    public TypeIdItem superType;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !ClassDefItem.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected ClassDefItem(DexFile dexFile) {
        super(dexFile);
    }

    private ClassDefItem(DexFile dexFile, TypeIdItem typeIdItem, int n, TypeIdItem typeIdItem2, TypeListItem typeListItem, StringIdItem stringIdItem, AnnotationDirectoryItem annotationDirectoryItem, ClassDataItem classDataItem, EncodedArrayItem encodedArrayItem) {
        super(dexFile);
        if (!$assertionsDisabled && typeIdItem == null) {
            throw new AssertionError();
        }
        this.classType = typeIdItem;
        this.accessFlags = n;
        this.superType = typeIdItem2;
        this.implementedInterfaces = typeListItem;
        this.sourceFile = stringIdItem;
        this.annotations = annotationDirectoryItem;
        this.classData = classDataItem;
        this.staticFieldInitializers = encodedArrayItem;
        if (classDataItem != null) {
            classDataItem.setParent((ClassDefItem)this);
        }
        if (annotationDirectoryItem != null) {
            annotationDirectoryItem.setParent((ClassDefItem)this);
        }
    }

    private static EncodedArrayItem copyStaticFieldInit(DexFile dexFile, EncodedArrayItem encodedArrayItem) {
        if (encodedArrayItem == null) {
            return null;
        }
        EncodedValue[] arrencodedValue = encodedArrayItem.getEncodedArray().values;
        EncodedValue[] arrencodedValue2 = new EncodedValue[arrencodedValue.length];
        int n = arrencodedValue.length;
        for (int i = 0; i < n; ++i) {
            arrencodedValue2[i] = EncodedValue.copyEncodedValue(dexFile, arrencodedValue[i]);
        }
        return EncodedArrayItem.internEncodedArrayItem(dexFile, new ArrayEncodedSubValue(arrencodedValue2));
    }

    public static ClassDefItem internClassDefItem(DexFile dexFile, TypeIdItem typeIdItem, int n, TypeIdItem typeIdItem2, TypeListItem typeListItem, StringIdItem stringIdItem, AnnotationDirectoryItem annotationDirectoryItem, ClassDataItem classDataItem, List<StaticFieldInitializer> list) {
        boolean bl = dexFile.getInplace();
        EncodedArrayItem encodedArrayItem = null;
        if (!bl) {
            encodedArrayItem = null;
            if (list != null) {
                int n2 = list.size();
                encodedArrayItem = null;
                if (n2 > 0) {
                    if (!$assertionsDisabled && classDataItem == null) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && list.size() != classDataItem.getStaticFields().length) {
                        throw new AssertionError();
                    }
                    encodedArrayItem = ClassDefItem.makeStaticFieldInitializersItem(dexFile, list);
                }
            }
        }
        ClassDefItem classDefItem = new ClassDefItem(dexFile, typeIdItem, n, typeIdItem2, typeListItem, stringIdItem, annotationDirectoryItem, classDataItem, encodedArrayItem);
        return dexFile.ClassDefsSection.intern(classDefItem);
    }

    public static ClassDefItem lookupClassDefItem(DexFile dexFile, TypeIdItem typeIdItem, int n, TypeIdItem typeIdItem2, TypeListItem typeListItem, StringIdItem stringIdItem, AnnotationDirectoryItem annotationDirectoryItem, ClassDataItem classDataItem, List<StaticFieldInitializer> list) {
        boolean bl = dexFile.getInplace();
        EncodedArrayItem encodedArrayItem = null;
        if (!bl) {
            encodedArrayItem = null;
            if (list != null) {
                int n2 = list.size();
                encodedArrayItem = null;
                if (n2 > 0) {
                    if (!$assertionsDisabled && classDataItem == null) {
                        throw new AssertionError();
                    }
                    if (!$assertionsDisabled && list.size() != classDataItem.getStaticFields().length) {
                        throw new AssertionError();
                    }
                    encodedArrayItem = ClassDefItem.makeStaticFieldInitializersItem(dexFile, list);
                }
            }
        }
        ClassDefItem classDefItem = new ClassDefItem(dexFile, typeIdItem, n, typeIdItem2, typeListItem, stringIdItem, annotationDirectoryItem, classDataItem, encodedArrayItem);
        return dexFile.ClassDefsSection.getInternedItem(classDefItem);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static EncodedArrayItem makeStaticFieldInitializersItem(DexFile dexFile, List<StaticFieldInitializer> list) {
        int n;
        block5 : {
            block6 : {
                if (list == null || list.size() == 0) break block6;
                int n2 = list.size();
                Collections.sort(list);
                n = -1;
                int n3 = n2 - 1;
                do {
                    block8 : {
                        block7 : {
                            if (n3 < 0) break block7;
                            StaticFieldInitializer staticFieldInitializer = (StaticFieldInitializer)list.get(n3);
                            if (staticFieldInitializer.value == null || staticFieldInitializer.value.compareTo(TypeUtils.makeDefaultValueForType(dexFile, staticFieldInitializer.field.field.getFieldType().getTypeDescriptor())) == 0) break block8;
                            n = n3;
                        }
                        if (n == -1) break;
                        break block5;
                    }
                    --n3;
                } while (true);
            }
            return null;
        }
        EncodedValue[] arrencodedValue = new EncodedValue[n + 1];
        int n4 = 0;
        while (n4 <= n) {
            StaticFieldInitializer staticFieldInitializer = (StaticFieldInitializer)list.get(n4);
            EncodedValue encodedValue = staticFieldInitializer.value;
            if (encodedValue == null) {
                encodedValue = TypeUtils.makeDefaultValueForType(dexFile, staticFieldInitializer.field.field.getFieldType().getTypeDescriptor());
            }
            arrencodedValue[n4] = encodedValue;
            ++n4;
        }
        return EncodedArrayItem.internEncodedArrayItem(dexFile, new ArrayEncodedSubValue(arrencodedValue));
    }

    public static int placeClassDefItems(IndexedSection<ClassDefItem> indexedSection, int n) {
        return new ClassDefPlacer(indexedSection).placeSection(n);
    }

    public int compareTo(ClassDefItem classDefItem) {
        return this.getOffset() - classDefItem.getOffset();
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public AnnotationDirectoryItem getAnnotations() {
        return this.annotations;
    }

    public ClassDataItem getClassData() {
        return this.classData;
    }

    public TypeIdItem getClassType() {
        return this.classType;
    }

    @Override
    public String getConciseIdentity() {
        return "class_def_item: " + this.classType.getTypeDescriptor();
    }

    public TypeListItem getInterfaces() {
        return this.implementedInterfaces;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_CLASS_DEF_ITEM;
    }

    public StringIdItem getSourceFile() {
        return this.sourceFile;
    }

    public EncodedArrayItem getStaticFieldInitializers() {
        return this.staticFieldInitializers;
    }

    public TypeIdItem getSuperclass() {
        return this.superType;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ClassDefItem internClassDefItem(DexFile dexFile) {
        TypeIdItem typeIdItem = TypeIdItem.internTypeIdItem(dexFile, this.classType.getTypeDescriptor());
        int n = this.accessFlags;
        TypeIdItem typeIdItem2 = TypeIdItem.internTypeIdItem(dexFile, this.superType.getTypeDescriptor());
        TypeListItem typeListItem = this.implementedInterfaces != null ? this.implementedInterfaces.internTypeListItem(dexFile) : null;
        StringIdItem stringIdItem = this.sourceFile != null ? StringIdItem.internStringIdItem(dexFile, this.sourceFile.getStringValue()) : null;
        AnnotationDirectoryItem annotationDirectoryItem = this.annotations != null ? this.annotations.copyAnnotationDirectoryItem(dexFile) : null;
        ClassDataItem classDataItem = this.classData != null ? this.classData.internClassDataItem(dexFile) : null;
        ClassDefItem classDefItem = new ClassDefItem(dexFile, typeIdItem, n, typeIdItem2, typeListItem, stringIdItem, annotationDirectoryItem, classDataItem, ClassDefItem.copyStaticFieldInit(dexFile, this.staticFieldInitializers));
        return dexFile.ClassDefsSection.intern(classDefItem);
    }

    @Override
    protected int placeItem(int n) {
        return n + 32;
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.classType = this.dexFile.TypeIdsSection.getItemByIndex(input.readInt());
        this.accessFlags = input.readInt();
        this.superType = this.dexFile.TypeIdsSection.getOptionalItemByIndex(input.readInt());
        this.implementedInterfaces = (TypeListItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_TYPE_LIST, input.readInt());
        this.sourceFile = this.dexFile.StringIdsSection.getOptionalItemByIndex(input.readInt());
        this.annotations = (AnnotationDirectoryItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM, input.readInt());
        this.classData = (ClassDataItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_CLASS_DATA_ITEM, input.readInt());
        this.staticFieldInitializers = (EncodedArrayItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_ENCODED_ARRAY_ITEM, input.readInt());
        if (this.classData != null) {
            this.classData.setParent((ClassDefItem)this);
        }
        if (this.annotations != null) {
            this.annotations.setParent((ClassDefItem)this);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        int n = -1;
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "class_type: " + this.classType.getTypeDescriptor());
            annotatedOutput.annotate(4, "access_flags: " + AccessFlags.formatAccessFlagsForClass(this.accessFlags));
            StringBuilder stringBuilder = new StringBuilder().append("superclass_type: ");
            String string2 = this.superType == null ? "" : this.superType.getTypeDescriptor();
            annotatedOutput.annotate(4, stringBuilder.append(string2).toString());
            StringBuilder stringBuilder2 = new StringBuilder().append("interfaces: ");
            String string3 = this.implementedInterfaces == null ? "" : this.implementedInterfaces.getTypeListString(" ");
            annotatedOutput.annotate(4, stringBuilder2.append(string3).toString());
            StringBuilder stringBuilder3 = new StringBuilder().append("source_file: ");
            String string4 = this.sourceFile == null ? "" : this.sourceFile.getStringValue();
            annotatedOutput.annotate(4, stringBuilder3.append(string4).toString());
            StringBuilder stringBuilder4 = new StringBuilder().append("annotations_off: ");
            String string5 = this.annotations == null ? "" : "0x" + Integer.toHexString((int)this.annotations.getOffset());
            annotatedOutput.annotate(4, stringBuilder4.append(string5).toString());
            StringBuilder stringBuilder5 = new StringBuilder().append("class_data_off:");
            String string6 = this.classData == null ? "" : "0x" + Integer.toHexString((int)this.classData.getOffset());
            annotatedOutput.annotate(4, stringBuilder5.append(string6).toString());
            StringBuilder stringBuilder6 = new StringBuilder().append("static_values_off: ");
            String string7 = this.staticFieldInitializers == null ? "" : "0x" + Integer.toHexString((int)this.staticFieldInitializers.getOffset());
            annotatedOutput.annotate(4, stringBuilder6.append(string7).toString());
        }
        annotatedOutput.writeInt(this.classType.getIndex());
        annotatedOutput.writeInt(this.accessFlags);
        int n2 = this.superType == null ? n : this.superType.getIndex();
        annotatedOutput.writeInt(n2);
        int n3 = this.implementedInterfaces == null ? 0 : this.implementedInterfaces.getOffset();
        annotatedOutput.writeInt(n3);
        if (this.sourceFile != null) {
            n = this.sourceFile.getIndex();
        }
        annotatedOutput.writeInt(n);
        int n4 = this.annotations == null ? 0 : this.annotations.getOffset();
        annotatedOutput.writeInt(n4);
        int n5 = this.classData == null ? 0 : this.classData.getOffset();
        annotatedOutput.writeInt(n5);
        EncodedArrayItem encodedArrayItem = this.staticFieldInitializers;
        int n6 = 0;
        if (encodedArrayItem != null) {
            n6 = this.staticFieldInitializers.getOffset();
        }
        annotatedOutput.writeInt(n6);
    }

    private static class ClassDefPlacer {
        private int currentIndex = 0;
        private int currentOffset;
        private final IndexedSection<ClassDefItem> section;
        private final HashMap<TypeIdItem, ClassDefItem> unplacedClassDefsByType = new HashMap();

        public ClassDefPlacer(IndexedSection<ClassDefItem> indexedSection) {
            this.section = indexedSection;
            for (ClassDefItem classDefItem : indexedSection.items) {
                TypeIdItem typeIdItem = classDefItem.classType;
                this.unplacedClassDefsByType.put((Object)typeIdItem, (Object)classDefItem);
            }
        }

        private void placeClass(ClassDefItem classDefItem) {
            if (classDefItem.getOffset() == -1) {
                TypeListItem typeListItem;
                TypeIdItem typeIdItem = classDefItem.superType;
                ClassDefItem classDefItem2 = (ClassDefItem)this.unplacedClassDefsByType.get((Object)typeIdItem);
                if (classDefItem2 != null) {
                    ClassDefPlacer.super.placeClass(classDefItem2);
                }
                if ((typeListItem = classDefItem.implementedInterfaces) != null) {
                    for (TypeIdItem typeIdItem2 : typeListItem.getTypes()) {
                        ClassDefItem classDefItem3 = (ClassDefItem)this.unplacedClassDefsByType.get((Object)typeIdItem2);
                        if (classDefItem3 == null) continue;
                        ClassDefPlacer.super.placeClass(classDefItem3);
                    }
                }
                int n = this.currentOffset;
                int n2 = this.currentIndex;
                this.currentIndex = n2 + 1;
                this.currentOffset = classDefItem.placeAt(n, n2);
                this.unplacedClassDefsByType.remove((Object)classDefItem.classType);
            }
        }

        public int placeSection(int n) {
            this.currentOffset = n;
            if (this.section.DexFile.getSortAllItems()) {
                Collections.sort((List)this.section.items, (Comparator)new Comparator<ClassDefItem>(){

                    public int compare(ClassDefItem classDefItem, ClassDefItem classDefItem2) {
                        return classDefItem.getClassType().compareTo(classDefItem2.getClassType());
                    }
                });
            }
            Iterator iterator = this.section.items.iterator();
            while (iterator.hasNext()) {
                ((ClassDefItem)iterator.next()).offset = -1;
            }
            Iterator iterator2 = this.section.items.iterator();
            while (iterator2.hasNext()) {
                ClassDefPlacer.super.placeClass((ClassDefItem)iterator2.next());
            }
            for (ClassDefItem classDefItem : this.unplacedClassDefsByType.values()) {
                this.section.items.set(classDefItem.getIndex(), (Object)classDefItem);
            }
            return this.currentOffset;
        }

    }

    public static class StaticFieldInitializer
    implements Comparable<StaticFieldInitializer> {
        public final ClassDataItem.EncodedField field;
        public final EncodedValue value;

        public StaticFieldInitializer(EncodedValue encodedValue, ClassDataItem.EncodedField encodedField) {
            this.value = encodedValue;
            this.field = encodedField;
        }

        public int compareTo(StaticFieldInitializer staticFieldInitializer) {
            return this.field.compareTo(staticFieldInitializer.field);
        }
    }

}

