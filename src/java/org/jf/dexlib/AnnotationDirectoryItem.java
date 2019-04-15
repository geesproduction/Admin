/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Comparable
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Collections
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.Collections;
import java.util.List;
import org.jf.dexlib.AnnotationSetItem;
import org.jf.dexlib.AnnotationSetRefList;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;

public class AnnotationDirectoryItem
extends Item<AnnotationDirectoryItem> {
    private AnnotationSetItem classAnnotations;
    private FieldIdItem[] fieldAnnotationFields;
    private AnnotationSetItem[] fieldAnnotations;
    private MethodIdItem[] methodAnnotationMethods;
    private AnnotationSetItem[] methodAnnotations;
    private MethodIdItem[] parameterAnnotationMethods;
    private AnnotationSetRefList[] parameterAnnotations;
    private ClassDefItem parent = null;

    protected AnnotationDirectoryItem(DexFile dexFile) {
        super(dexFile);
    }

    private AnnotationDirectoryItem(DexFile dexFile, AnnotationSetItem annotationSetItem, FieldIdItem[] arrfieldIdItem, AnnotationSetItem[] arrannotationSetItem, MethodIdItem[] arrmethodIdItem, AnnotationSetItem[] arrannotationSetItem2, MethodIdItem[] arrmethodIdItem2, AnnotationSetRefList[] arrannotationSetRefList) {
        super(dexFile);
        this.classAnnotations = annotationSetItem;
        this.fieldAnnotationFields = arrfieldIdItem;
        this.fieldAnnotations = arrannotationSetItem;
        this.methodAnnotationMethods = arrmethodIdItem;
        this.methodAnnotations = arrannotationSetItem2;
        this.parameterAnnotationMethods = arrmethodIdItem2;
        this.parameterAnnotations = arrannotationSetRefList;
    }

    public static AnnotationDirectoryItem internAnnotationDirectoryItem(DexFile dexFile, AnnotationSetItem annotationSetItem, List<FieldAnnotation> list, List<MethodAnnotation> list2, List<ParameterAnnotation> list3) {
        FieldIdItem[] arrfieldIdItem = null;
        AnnotationSetItem[] arrannotationSetItem = null;
        if (list != null) {
            int n = list.size();
            arrfieldIdItem = null;
            arrannotationSetItem = null;
            if (n > 0) {
                arrfieldIdItem = new FieldIdItem[list.size()];
                arrannotationSetItem = new AnnotationSetItem[list.size()];
                Collections.sort(list);
                int n2 = 0;
                for (FieldAnnotation fieldAnnotation : list) {
                    arrfieldIdItem[n2] = fieldAnnotation.field;
                    int n3 = n2 + 1;
                    arrannotationSetItem[n2] = fieldAnnotation.annotationSet;
                    n2 = n3;
                }
            }
        }
        MethodIdItem[] arrmethodIdItem = null;
        AnnotationSetItem[] arrannotationSetItem2 = null;
        if (list2 != null) {
            int n = list2.size();
            arrmethodIdItem = null;
            arrannotationSetItem2 = null;
            if (n > 0) {
                arrmethodIdItem = new MethodIdItem[list2.size()];
                arrannotationSetItem2 = new AnnotationSetItem[list2.size()];
                Collections.sort(list2);
                int n4 = 0;
                for (MethodAnnotation methodAnnotation : list2) {
                    arrmethodIdItem[n4] = methodAnnotation.method;
                    int n5 = n4 + 1;
                    arrannotationSetItem2[n4] = methodAnnotation.annotationSet;
                    n4 = n5;
                }
            }
        }
        MethodIdItem[] arrmethodIdItem2 = null;
        AnnotationSetRefList[] arrannotationSetRefList = null;
        if (list3 != null) {
            int n = list3.size();
            arrmethodIdItem2 = null;
            arrannotationSetRefList = null;
            if (n > 0) {
                arrmethodIdItem2 = new MethodIdItem[list3.size()];
                arrannotationSetRefList = new AnnotationSetRefList[list3.size()];
                Collections.sort(list3);
                int n6 = 0;
                for (ParameterAnnotation parameterAnnotation : list3) {
                    arrmethodIdItem2[n6] = parameterAnnotation.method;
                    int n7 = n6 + 1;
                    arrannotationSetRefList[n6] = parameterAnnotation.annotationSet;
                    n6 = n7;
                }
            }
        }
        AnnotationDirectoryItem annotationDirectoryItem = new AnnotationDirectoryItem(dexFile, annotationSetItem, arrfieldIdItem, arrannotationSetItem, arrmethodIdItem, arrannotationSetItem2, arrmethodIdItem2, arrannotationSetRefList);
        return dexFile.AnnotationDirectoriesSection.intern(annotationDirectoryItem);
    }

    private boolean isInternable() {
        return !(this.classAnnotations == null || this.fieldAnnotations != null && this.fieldAnnotations.length != 0 || this.methodAnnotations != null && this.methodAnnotations.length != 0 || this.parameterAnnotations != null && this.parameterAnnotations.length != 0);
    }

    public int compareTo(AnnotationDirectoryItem annotationDirectoryItem) {
        if (!AnnotationDirectoryItem.super.isInternable()) {
            if (!annotationDirectoryItem.isInternable()) {
                return this.parent.compareTo(annotationDirectoryItem.parent);
            }
            return -1;
        }
        if (!annotationDirectoryItem.isInternable()) {
            return 1;
        }
        return this.classAnnotations.compareTo(annotationDirectoryItem.classAnnotations);
    }

    /*
     * Enabled aggressive block sorting
     */
    public AnnotationDirectoryItem copyAnnotationDirectoryItem(DexFile dexFile) {
        FieldIdItem[] arrfieldIdItem = this.fieldAnnotationFields;
        FieldIdItem[] arrfieldIdItem2 = null;
        if (arrfieldIdItem != null) {
            arrfieldIdItem2 = new FieldIdItem[this.fieldAnnotationFields.length];
            FieldIdItem[] arrfieldIdItem3 = this.fieldAnnotationFields;
            int n = arrfieldIdItem3.length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                FieldIdItem fieldIdItem = arrfieldIdItem3[i];
                int n3 = n2 + 1;
                arrfieldIdItem2[n2] = fieldIdItem.internFieldIdItem(dexFile);
                n2 = n3;
            }
        }
        AnnotationSetItem[] arrannotationSetItem = this.fieldAnnotations;
        AnnotationSetItem[] arrannotationSetItem2 = null;
        if (arrannotationSetItem != null) {
            arrannotationSetItem2 = new AnnotationSetItem[this.fieldAnnotations.length];
            AnnotationSetItem[] arrannotationSetItem3 = this.fieldAnnotations;
            int n = arrannotationSetItem3.length;
            int n4 = 0;
            for (int i = 0; i < n; ++i) {
                AnnotationSetItem annotationSetItem = arrannotationSetItem3[i];
                int n5 = n4 + 1;
                arrannotationSetItem2[n4] = annotationSetItem.copyAnnotationSetItem(dexFile);
                n4 = n5;
            }
        }
        MethodIdItem[] arrmethodIdItem = this.methodAnnotationMethods;
        MethodIdItem[] arrmethodIdItem2 = null;
        if (arrmethodIdItem != null) {
            arrmethodIdItem2 = new MethodIdItem[this.methodAnnotationMethods.length];
            MethodIdItem[] arrmethodIdItem3 = this.methodAnnotationMethods;
            int n = arrmethodIdItem3.length;
            int n6 = 0;
            for (int i = 0; i < n; ++i) {
                MethodIdItem methodIdItem = arrmethodIdItem3[i];
                int n7 = n6 + 1;
                arrmethodIdItem2[n6] = methodIdItem.internMethodIdItem(dexFile);
                n6 = n7;
            }
        }
        AnnotationSetItem[] arrannotationSetItem4 = this.methodAnnotations;
        AnnotationSetItem[] arrannotationSetItem5 = null;
        if (arrannotationSetItem4 != null) {
            arrannotationSetItem5 = new AnnotationSetItem[this.methodAnnotations.length];
            AnnotationSetItem[] arrannotationSetItem6 = this.methodAnnotations;
            int n = arrannotationSetItem6.length;
            int n8 = 0;
            for (int i = 0; i < n; ++i) {
                AnnotationSetItem annotationSetItem = arrannotationSetItem6[i];
                int n9 = n8 + 1;
                arrannotationSetItem5[n8] = annotationSetItem.copyAnnotationSetItem(dexFile);
                n8 = n9;
            }
        }
        MethodIdItem[] arrmethodIdItem4 = this.parameterAnnotationMethods;
        MethodIdItem[] arrmethodIdItem5 = null;
        if (arrmethodIdItem4 != null) {
            arrmethodIdItem5 = new MethodIdItem[this.parameterAnnotationMethods.length];
            MethodIdItem[] arrmethodIdItem6 = this.parameterAnnotationMethods;
            int n = arrmethodIdItem6.length;
            int n10 = 0;
            for (int i = 0; i < n; ++i) {
                MethodIdItem methodIdItem = arrmethodIdItem6[i];
                int n11 = n10 + 1;
                arrmethodIdItem5[n10] = methodIdItem.internMethodIdItem(dexFile);
                n10 = n11;
            }
        }
        AnnotationSetRefList[] arrannotationSetRefList = this.parameterAnnotations;
        AnnotationSetRefList[] arrannotationSetRefList2 = null;
        if (arrannotationSetRefList != null) {
            arrannotationSetRefList2 = new AnnotationSetRefList[this.parameterAnnotations.length];
            AnnotationSetRefList[] arrannotationSetRefList3 = this.parameterAnnotations;
            int n = arrannotationSetRefList3.length;
            int n12 = 0;
            for (int i = 0; i < n; ++i) {
                AnnotationSetRefList annotationSetRefList = arrannotationSetRefList3[i];
                int n13 = n12 + 1;
                arrannotationSetRefList2[n12] = annotationSetRefList.copyAnnotationSetRefList(dexFile);
                n12 = n13;
            }
        }
        AnnotationSetItem annotationSetItem = this.classAnnotations == null ? null : this.classAnnotations.copyAnnotationSetItem(dexFile);
        AnnotationDirectoryItem annotationDirectoryItem = new AnnotationDirectoryItem(dexFile, annotationSetItem, arrfieldIdItem2, arrannotationSetItem2, arrmethodIdItem2, arrannotationSetItem5, arrmethodIdItem5, arrannotationSetRefList2);
        return dexFile.AnnotationDirectoriesSection.intern(annotationDirectoryItem);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5 : {
            block4 : {
                if (this == object) break block4;
                if (object == null || !this.getClass().equals((Object)object.getClass())) {
                    return false;
                }
                if (this.compareTo((AnnotationDirectoryItem)object) != 0) break block5;
            }
            return true;
        }
        return false;
    }

    public AnnotationSetItem getClassAnnotations() {
        return this.classAnnotations;
    }

    @Override
    public String getConciseIdentity() {
        if (this.parent == null) {
            return "annotation_directory_item @0x" + Integer.toHexString((int)this.getOffset());
        }
        return "annotation_directory_item @0x" + Integer.toHexString((int)this.getOffset()) + " (" + this.parent.getClassType() + ")";
    }

    public int getFieldAnnotationCount() {
        return this.fieldAnnotationFields.length;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM;
    }

    public int getMethodAnnotationCount() {
        return this.methodAnnotationMethods.length;
    }

    public int getParameterAnnotationCount() {
        return this.parameterAnnotationMethods.length;
    }

    public int hashCode() {
        if (!this.isInternable()) {
            return Object.super.hashCode();
        }
        return this.classAnnotations.hashCode();
    }

    public void iterateFieldAnnotations(FieldAnnotationIteratorDelegate fieldAnnotationIteratorDelegate) {
        for (int i = 0; i < this.fieldAnnotationFields.length; ++i) {
            try {
                fieldAnnotationIteratorDelegate.processFieldAnnotations(this.fieldAnnotationFields[i], this.fieldAnnotations[i]);
            }
            catch (Exception exception) {
                throw this.addExceptionContext((Exception)((Object)ExceptionWithContext.withContext(exception, "Error occured while processing field annotations for field: " + this.fieldAnnotationFields[i].getFieldString())));
            }
        }
    }

    public void iterateMethodAnnotations(MethodAnnotationIteratorDelegate methodAnnotationIteratorDelegate) {
        for (int i = 0; i < this.methodAnnotationMethods.length; ++i) {
            try {
                methodAnnotationIteratorDelegate.processMethodAnnotations(this.methodAnnotationMethods[i], this.methodAnnotations[i]);
            }
            catch (Exception exception) {
                throw this.addExceptionContext((Exception)((Object)ExceptionWithContext.withContext(exception, "Error occured while processing method annotations for method: " + this.methodAnnotationMethods[i].getMethodString())));
            }
        }
    }

    public void iterateParameterAnnotations(ParameterAnnotationIteratorDelegate parameterAnnotationIteratorDelegate) {
        for (int i = 0; i < this.parameterAnnotationMethods.length; ++i) {
            try {
                parameterAnnotationIteratorDelegate.processParameterAnnotations(this.parameterAnnotationMethods[i], this.parameterAnnotations[i]);
            }
            catch (Exception exception) {
                throw this.addExceptionContext((Exception)((Object)ExceptionWithContext.withContext(exception, "Error occured while processing parameter annotations for method: " + this.parameterAnnotationMethods[i].getMethodString())));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected int placeItem(int n) {
        int n2 = n + 16;
        int n3 = this.fieldAnnotations == null ? 0 : this.fieldAnnotations.length;
        int n4 = this.methodAnnotations == null ? 0 : this.methodAnnotations.length;
        int n5 = n3 + n4;
        AnnotationSetRefList[] arrannotationSetRefList = this.parameterAnnotations;
        int n6 = 0;
        if (arrannotationSetRefList == null) {
            return n2 + 8 * (n5 + n6);
        }
        n6 = this.parameterAnnotations.length;
        return n2 + 8 * (n5 + n6);
    }

    @Override
    protected void readItem(Input input, ReadContext readContext) {
        this.classAnnotations = (AnnotationSetItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_SET_ITEM, input.readInt());
        this.fieldAnnotationFields = new FieldIdItem[input.readInt()];
        this.fieldAnnotations = new AnnotationSetItem[this.fieldAnnotationFields.length];
        this.methodAnnotationMethods = new MethodIdItem[input.readInt()];
        this.methodAnnotations = new AnnotationSetItem[this.methodAnnotationMethods.length];
        this.parameterAnnotationMethods = new MethodIdItem[input.readInt()];
        this.parameterAnnotations = new AnnotationSetRefList[this.parameterAnnotationMethods.length];
        for (int i = 0; i < this.fieldAnnotations.length; ++i) {
            try {
                this.fieldAnnotationFields[i] = this.dexFile.FieldIdsSection.getItemByIndex(input.readInt());
                this.fieldAnnotations[i] = (AnnotationSetItem)readContext.getOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_SET_ITEM, input.readInt());
            }
            catch (Exception exception) {
                throw ExceptionWithContext.withContext(exception, "Error occured while reading FieldAnnotation at index " + i);
            }
        }
        for (int i = 0; i < this.methodAnnotations.length; ++i) {
            try {
                this.methodAnnotationMethods[i] = this.dexFile.MethodIdsSection.getItemByIndex(input.readInt());
                this.methodAnnotations[i] = (AnnotationSetItem)readContext.getOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_SET_ITEM, input.readInt());
            }
            catch (Exception exception) {
                throw ExceptionWithContext.withContext(exception, "Error occured while reading MethodAnnotation at index " + i);
            }
        }
        for (int i = 0; i < this.parameterAnnotations.length; ++i) {
            try {
                this.parameterAnnotationMethods[i] = this.dexFile.MethodIdsSection.getItemByIndex(input.readInt());
                this.parameterAnnotations[i] = (AnnotationSetRefList)readContext.getOffsettedItemByOffset(ItemType.TYPE_ANNOTATION_SET_REF_LIST, input.readInt());
            }
            catch (Exception exception) {
                throw ExceptionWithContext.withContext(exception, "Error occured while reading ParameterAnnotation at index " + i);
            }
        }
    }

    protected void setParent(ClassDefItem classDefItem) {
        this.parent = classDefItem;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            if (!AnnotationDirectoryItem.super.isInternable() && this.parent != null) {
                annotatedOutput.annotate(0, this.parent.getClassType().getTypeDescriptor());
            }
            if (this.classAnnotations != null) {
                annotatedOutput.annotate(4, "class_annotations_off: 0x" + Integer.toHexString((int)this.classAnnotations.getOffset()));
            } else {
                annotatedOutput.annotate(4, "class_annotations_off:");
            }
            int n = this.fieldAnnotations == null ? 0 : this.fieldAnnotations.length;
            annotatedOutput.annotate(4, "annotated_fields_size: 0x" + Integer.toHexString((int)n) + " (" + n + ")");
            int n2 = this.methodAnnotations == null ? 0 : this.methodAnnotations.length;
            annotatedOutput.annotate(4, "annotated_methods_size: 0x" + Integer.toHexString((int)n2) + " (" + n2 + ")");
            int n3 = this.parameterAnnotations == null ? 0 : this.parameterAnnotations.length;
            annotatedOutput.annotate(4, "annotated_parameters_size: 0x" + Integer.toHexString((int)n3) + " (" + n3 + ")");
            if (this.fieldAnnotations != null) {
                int n4 = 0;
                for (int i = 0; i < this.fieldAnnotations.length; ++i) {
                    StringBuilder stringBuilder = new StringBuilder().append("[");
                    int n5 = n4 + 1;
                    annotatedOutput.annotate(0, stringBuilder.append(n4).append("] field_annotation").toString());
                    annotatedOutput.indent();
                    annotatedOutput.annotate(4, "field: " + this.fieldAnnotationFields[i].getFieldName().getStringValue() + ":" + this.fieldAnnotationFields[i].getFieldType().getTypeDescriptor());
                    annotatedOutput.annotate(4, "annotations_off: 0x" + Integer.toHexString((int)this.fieldAnnotations[i].getOffset()));
                    annotatedOutput.deindent();
                    n4 = n5;
                }
            }
            if (this.methodAnnotations != null) {
                int n6 = 0;
                for (int i = 0; i < this.methodAnnotations.length; ++i) {
                    StringBuilder stringBuilder = new StringBuilder().append("[");
                    int n7 = n6 + 1;
                    annotatedOutput.annotate(0, stringBuilder.append(n6).append("] method_annotation").toString());
                    annotatedOutput.indent();
                    annotatedOutput.annotate(4, "method: " + this.methodAnnotationMethods[i].getMethodString());
                    annotatedOutput.annotate(4, "annotations_off: 0x" + Integer.toHexString((int)this.methodAnnotations[i].getOffset()));
                    annotatedOutput.deindent();
                    n6 = n7;
                }
            }
            if (this.parameterAnnotations != null) {
                int n8 = 0;
                for (int i = 0; i < this.parameterAnnotations.length; ++i) {
                    StringBuilder stringBuilder = new StringBuilder().append("[");
                    int n9 = n8 + 1;
                    annotatedOutput.annotate(0, stringBuilder.append(n8).append("] parameter_annotation").toString());
                    annotatedOutput.indent();
                    annotatedOutput.annotate(4, "method: " + this.parameterAnnotationMethods[i].getMethodString());
                    annotatedOutput.annotate(4, "annotations_off: 0x" + Integer.toHexString((int)this.parameterAnnotations[i].getOffset()));
                    n8 = n9;
                }
            }
        }
        int n = this.classAnnotations == null ? 0 : this.classAnnotations.getOffset();
        annotatedOutput.writeInt(n);
        int n10 = this.fieldAnnotations == null ? 0 : this.fieldAnnotations.length;
        annotatedOutput.writeInt(n10);
        int n11 = this.methodAnnotations == null ? 0 : this.methodAnnotations.length;
        annotatedOutput.writeInt(n11);
        AnnotationSetRefList[] arrannotationSetRefList = this.parameterAnnotations;
        int n12 = 0;
        if (arrannotationSetRefList != null) {
            n12 = this.parameterAnnotations.length;
        }
        annotatedOutput.writeInt(n12);
        if (this.fieldAnnotations != null) {
            for (int i = 0; i < this.fieldAnnotations.length; ++i) {
                annotatedOutput.writeInt(this.fieldAnnotationFields[i].getIndex());
                annotatedOutput.writeInt(this.fieldAnnotations[i].getOffset());
            }
        }
        if (this.methodAnnotations != null) {
            for (int i = 0; i < this.methodAnnotations.length; ++i) {
                annotatedOutput.writeInt(this.methodAnnotationMethods[i].getIndex());
                annotatedOutput.writeInt(this.methodAnnotations[i].getOffset());
            }
        }
        if (this.parameterAnnotations != null) {
            for (int i = 0; i < this.parameterAnnotations.length; ++i) {
                annotatedOutput.writeInt(this.parameterAnnotationMethods[i].getIndex());
                annotatedOutput.writeInt(this.parameterAnnotations[i].getOffset());
            }
        }
    }

    public static class FieldAnnotation
    implements Comparable<FieldAnnotation> {
        public final AnnotationSetItem annotationSet;
        public final FieldIdItem field;

        public FieldAnnotation(FieldIdItem fieldIdItem, AnnotationSetItem annotationSetItem) {
            this.field = fieldIdItem;
            this.annotationSet = annotationSetItem;
        }

        public int compareTo(FieldAnnotation fieldAnnotation) {
            return this.field.compareTo(fieldAnnotation.field);
        }
    }

    public static interface FieldAnnotationIteratorDelegate {
        public void processFieldAnnotations(FieldIdItem var1, AnnotationSetItem var2);
    }

    public static class MethodAnnotation
    implements Comparable<MethodAnnotation> {
        public final AnnotationSetItem annotationSet;
        public final MethodIdItem method;

        public MethodAnnotation(MethodIdItem methodIdItem, AnnotationSetItem annotationSetItem) {
            this.method = methodIdItem;
            this.annotationSet = annotationSetItem;
        }

        public int compareTo(MethodAnnotation methodAnnotation) {
            return this.method.compareTo(methodAnnotation.method);
        }
    }

    public static interface MethodAnnotationIteratorDelegate {
        public void processMethodAnnotations(MethodIdItem var1, AnnotationSetItem var2);
    }

    public static class ParameterAnnotation
    implements Comparable<ParameterAnnotation> {
        public final AnnotationSetRefList annotationSet;
        public final MethodIdItem method;

        public ParameterAnnotation(MethodIdItem methodIdItem, AnnotationSetRefList annotationSetRefList) {
            this.method = methodIdItem;
            this.annotationSet = annotationSetRefList;
        }

        public int compareTo(ParameterAnnotation parameterAnnotation) {
            return this.method.compareTo(parameterAnnotation.method);
        }
    }

    public static interface ParameterAnnotationIteratorDelegate {
        public void processParameterAnnotations(MethodIdItem var1, AnnotationSetRefList var2);
    }

}

