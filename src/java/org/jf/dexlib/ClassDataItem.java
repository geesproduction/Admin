/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Comparable
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.Arrays
 *  java.util.Collections
 *  java.util.List
 */
package org.jf.dexlib;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.jf.dexlib.ClassDefItem;
import org.jf.dexlib.CodeItem;
import org.jf.dexlib.DexFile;
import org.jf.dexlib.FieldIdItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.Util.AccessFlags;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;
import org.jf.dexlib.Util.Leb128Utils;

public class ClassDataItem
extends Item<ClassDataItem> {
    private EncodedMethod[] directMethods;
    private EncodedField[] instanceFields;
    private ClassDefItem parent;
    private EncodedField[] staticFields;
    private EncodedMethod[] virtualMethods;

    public ClassDataItem(DexFile dexFile) {
        super(dexFile);
        this.parent = null;
    }

    private ClassDataItem(DexFile dexFile, EncodedField[] arrencodedField, EncodedField[] arrencodedField2, EncodedMethod[] arrencodedMethod, EncodedMethod[] arrencodedMethod2) {
        super(dexFile);
        this.parent = null;
        if (arrencodedField == null) {
            arrencodedField = new EncodedField[]{};
        }
        this.staticFields = arrencodedField;
        if (arrencodedField2 == null) {
            arrencodedField2 = new EncodedField[]{};
        }
        this.instanceFields = arrencodedField2;
        if (arrencodedMethod == null) {
            arrencodedMethod = new EncodedMethod[]{};
        }
        this.directMethods = arrencodedMethod;
        if (arrencodedMethod2 == null) {
            arrencodedMethod2 = new EncodedMethod[]{};
        }
        this.virtualMethods = arrencodedMethod2;
    }

    private void addDirectMethod(EncodedMethod encodedMethod) {
        int n = this.directMethods.length;
        EncodedMethod[] arrencodedMethod = this.directMethods;
        this.directMethods = new EncodedMethod[n + 1];
        System.arraycopy((Object)arrencodedMethod, (int)0, (Object)this.directMethods, (int)0, (int)n);
        this.directMethods[n] = encodedMethod;
    }

    private void addInstanceField(EncodedField encodedField) {
        int n = this.instanceFields.length;
        EncodedField[] arrencodedField = this.instanceFields;
        this.instanceFields = new EncodedField[n + 1];
        System.arraycopy((Object)arrencodedField, (int)0, (Object)this.instanceFields, (int)0, (int)n);
        this.instanceFields[n] = encodedField;
    }

    private void addStaticField(EncodedField encodedField) {
        int n = this.staticFields.length;
        EncodedField[] arrencodedField = this.staticFields;
        this.staticFields = new EncodedField[n + 1];
        System.arraycopy((Object)arrencodedField, (int)0, (Object)this.staticFields, (int)0, (int)n);
        this.staticFields[n] = encodedField;
    }

    private void addVirtualMethod(EncodedMethod encodedMethod) {
        int n = this.virtualMethods.length;
        EncodedMethod[] arrencodedMethod = this.virtualMethods;
        this.virtualMethods = new EncodedMethod[n + 1];
        System.arraycopy((Object)arrencodedMethod, (int)0, (Object)this.virtualMethods, (int)0, (int)n);
        this.virtualMethods[n] = encodedMethod;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static EncodedMethod findMethodByMethodIdInternal(int n, EncodedMethod[] arrencodedMethod) {
        int n2 = 0;
        int n3 = arrencodedMethod.length;
        while (n2 < n3) {
            int n4 = n2 + n3 >> 1;
            EncodedMethod encodedMethod = arrencodedMethod[n4];
            int n5 = encodedMethod.method.getIndex();
            if (n5 == n) {
                return encodedMethod;
            }
            if (n5 < n) {
                if (n2 == n4) break;
                n2 = n4;
                continue;
            }
            if (n3 == n4) break;
            n3 = n4;
        }
        return null;
    }

    public static ClassDataItem internClassDataItem(DexFile dexFile, List<EncodedField> list, List<EncodedField> list2, List<EncodedMethod> list3, List<EncodedMethod> list4) {
        Object[] arrobject = null;
        if (list != null) {
            int n = list.size();
            arrobject = null;
            if (n > 0) {
                Collections.sort(list);
                arrobject = new EncodedField[list.size()];
                list.toArray(arrobject);
            }
        }
        Object[] arrobject2 = null;
        if (list2 != null) {
            int n = list2.size();
            arrobject2 = null;
            if (n > 0) {
                Collections.sort(list2);
                arrobject2 = new EncodedField[list2.size()];
                list2.toArray(arrobject2);
            }
        }
        Object[] arrobject3 = null;
        if (list3 != null) {
            int n = list3.size();
            arrobject3 = null;
            if (n > 0) {
                Collections.sort(list3);
                arrobject3 = new EncodedMethod[list3.size()];
                list3.toArray(arrobject3);
            }
        }
        Object[] arrobject4 = null;
        if (list4 != null) {
            int n = list4.size();
            arrobject4 = null;
            if (n > 0) {
                Collections.sort(list4);
                arrobject4 = new EncodedMethod[list4.size()];
                list4.toArray(arrobject4);
            }
        }
        ClassDataItem classDataItem = new ClassDataItem(dexFile, (EncodedField[])arrobject, (EncodedField[])arrobject2, (EncodedMethod[])arrobject3, (EncodedMethod[])arrobject4);
        return dexFile.ClassDataSection.intern(classDataItem);
    }

    public void addField(EncodedField encodedField) {
        if (encodedField.isStatic()) {
            ClassDataItem.super.addStaticField(encodedField);
            return;
        }
        ClassDataItem.super.addInstanceField(encodedField);
    }

    public void addMethod(EncodedMethod encodedMethod) {
        if (encodedMethod.isDirect()) {
            ClassDataItem.super.addDirectMethod(encodedMethod);
            return;
        }
        ClassDataItem.super.addVirtualMethod(encodedMethod);
    }

    public int compareTo(ClassDataItem classDataItem) {
        if (this.parent == null) {
            if (classDataItem.parent == null) {
                return 0;
            }
            return -1;
        }
        if (classDataItem.parent == null) {
            return 1;
        }
        return this.parent.compareTo(classDataItem.parent);
    }

    public EncodedMethod findDirectMethodByMethodId(MethodIdItem methodIdItem) {
        return ClassDataItem.findMethodByMethodIdInternal(methodIdItem.index, this.directMethods);
    }

    public EncodedMethod findMethodByMethodId(MethodIdItem methodIdItem) {
        EncodedMethod encodedMethod = ClassDataItem.findMethodByMethodIdInternal(methodIdItem.index, this.directMethods);
        if (encodedMethod != null) {
            return encodedMethod;
        }
        return ClassDataItem.findMethodByMethodIdInternal(methodIdItem.index, this.virtualMethods);
    }

    public EncodedMethod findVirtualMethodByMethodId(MethodIdItem methodIdItem) {
        return ClassDataItem.findMethodByMethodIdInternal(methodIdItem.index, this.virtualMethods);
    }

    @Override
    public String getConciseIdentity() {
        if (this.parent == null) {
            return "class_data_item @0x" + Integer.toHexString((int)this.getOffset());
        }
        return "class_data_item @0x" + Integer.toHexString((int)this.getOffset()) + " (" + this.parent.getClassType() + ")";
    }

    public EncodedMethod[] getDirectMethods() {
        return this.directMethods;
    }

    public EncodedField[] getInstanceFields() {
        return this.instanceFields;
    }

    @Override
    public ItemType getItemType() {
        return ItemType.TYPE_CLASS_DATA_ITEM;
    }

    public EncodedField[] getStaticFields() {
        return this.staticFields;
    }

    public EncodedMethod[] getVirtualMethods() {
        return this.virtualMethods;
    }

    /*
     * Enabled aggressive block sorting
     */
    public ClassDataItem internClassDataItem(DexFile dexFile) {
        EncodedField[] arrencodedField = this.staticFields;
        EncodedField[] arrencodedField2 = null;
        if (arrencodedField != null) {
            int n = this.staticFields.length;
            arrencodedField2 = null;
            if (n != 0) {
                arrencodedField2 = new EncodedField[this.staticFields.length];
                for (EncodedField encodedField : this.staticFields) {
                    arrencodedField2[var30_5] = new EncodedField(encodedField.field.internFieldIdItem(dexFile), encodedField.accessFlags);
                }
            }
        }
        EncodedField[] arrencodedField3 = this.instanceFields;
        EncodedField[] arrencodedField4 = null;
        if (arrencodedField3 != null) {
            int n = this.instanceFields.length;
            arrencodedField4 = null;
            if (n != 0) {
                arrencodedField4 = new EncodedField[this.instanceFields.length];
                for (EncodedField encodedField : this.instanceFields) {
                    arrencodedField4[var26_11] = new EncodedField(encodedField.field.internFieldIdItem(dexFile), encodedField.accessFlags);
                }
            }
        }
        EncodedMethod[] arrencodedMethod = this.directMethods;
        EncodedMethod[] arrencodedMethod2 = null;
        if (arrencodedMethod != null) {
            int n = this.directMethods.length;
            arrencodedMethod2 = null;
            if (n != 0) {
                arrencodedMethod2 = new EncodedMethod[this.directMethods.length];
                for (EncodedMethod encodedMethod : this.directMethods) {
                    MethodIdItem methodIdItem = encodedMethod.method.internMethodIdItem(dexFile);
                    int n2 = encodedMethod.accessFlags;
                    CodeItem codeItem = encodedMethod.codeItem != null ? encodedMethod.codeItem.internCodeItem(dexFile) : null;
                    arrencodedMethod2[var19_17] = new EncodedMethod(methodIdItem, n2, codeItem);
                }
            }
        }
        EncodedMethod[] arrencodedMethod3 = this.virtualMethods;
        EncodedMethod[] arrencodedMethod4 = null;
        if (arrencodedMethod3 != null) {
            int n = this.virtualMethods.length;
            arrencodedMethod4 = null;
            if (n != 0) {
                arrencodedMethod4 = new EncodedMethod[this.virtualMethods.length];
                for (EncodedMethod encodedMethod : this.virtualMethods) {
                    MethodIdItem methodIdItem = encodedMethod.method.internMethodIdItem(dexFile);
                    int n3 = encodedMethod.accessFlags;
                    CodeItem codeItem = encodedMethod.codeItem != null ? encodedMethod.codeItem.internCodeItem(dexFile) : null;
                    arrencodedMethod4[var12_26] = new EncodedMethod(methodIdItem, n3, codeItem);
                }
            }
        }
        ClassDataItem classDataItem = new ClassDataItem(dexFile, arrencodedField2, arrencodedField4, arrencodedMethod2, arrencodedMethod4);
        return dexFile.ClassDataSection.intern(classDataItem);
    }

    @Override
    protected int placeItem(int n) {
        int n2 = n + Leb128Utils.unsignedLeb128Size(this.staticFields.length) + Leb128Utils.unsignedLeb128Size(this.instanceFields.length) + Leb128Utils.unsignedLeb128Size(this.directMethods.length) + Leb128Utils.unsignedLeb128Size(this.virtualMethods.length);
        EncodedField encodedField = null;
        for (EncodedField encodedField2 : this.staticFields) {
            n2 = encodedField2.place(n2, encodedField);
            encodedField = encodedField2;
        }
        EncodedField encodedField3 = null;
        for (EncodedField encodedField4 : this.instanceFields) {
            n2 = encodedField4.place(n2, encodedField3);
            encodedField3 = encodedField4;
        }
        EncodedMethod encodedMethod = null;
        for (EncodedMethod encodedMethod2 : this.directMethods) {
            n2 = encodedMethod2.place(n2, encodedMethod);
            encodedMethod = encodedMethod2;
        }
        EncodedMethod encodedMethod3 = null;
        for (EncodedMethod encodedMethod4 : this.virtualMethods) {
            n2 = encodedMethod4.place(n2, encodedMethod3);
            encodedMethod3 = encodedMethod4;
        }
        return n2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected void readItem(Input input, ReadContext readContext) {
        void var15_33;
        block22 : {
            int n7;
            int n;
            void var19_25;
            EncodedMethod encodedMethod;
            block21 : {
                int n4;
                int n6;
                EncodedField encodedField3;
                void var23_17;
                block20 : {
                    int n3;
                    void var27_9;
                    this.staticFields = new EncodedField[input.readUnsignedLeb128()];
                    this.instanceFields = new EncodedField[input.readUnsignedLeb128()];
                    this.directMethods = new EncodedMethod[input.readUnsignedLeb128()];
                    this.virtualMethods = new EncodedMethod[input.readUnsignedLeb128()];
                    int n5 = this.staticFields.length;
                    EncodedField encodedField = null;
                    for (n3 = 0; n3 < n5; ++n3) {
                        EncodedField encodedField2;
                        try {
                            EncodedField[] arrencodedField = this.staticFields;
                            encodedField2 = new EncodedField(this.dexFile, input, encodedField, null);
                        }
                        catch (Exception exception) {
                            throw ExceptionWithContext.withContext((Throwable)var27_9, "Error while reading static field at index " + n3);
                        }
                        arrencodedField[n3] = encodedField2;
                        encodedField = encodedField2;
                        continue;
                    }
                    n6 = this.instanceFields.length;
                    encodedField3 = null;
                    break block20;
                    catch (Exception exception) {
                        throw ExceptionWithContext.withContext((Throwable)var27_9, "Error while reading static field at index " + n3);
                    }
                }
                for (n4 = 0; n4 < n6; ++n4) {
                    EncodedField encodedField4;
                    try {
                        EncodedField[] arrencodedField = this.instanceFields;
                        encodedField4 = new EncodedField(this.dexFile, input, encodedField3, null);
                    }
                    catch (Exception exception) {
                        throw ExceptionWithContext.withContext((Throwable)var23_17, "Error while reading instance field at index " + n4);
                    }
                    arrencodedField[n4] = encodedField4;
                    encodedField3 = encodedField4;
                    continue;
                }
                n7 = this.directMethods.length;
                encodedMethod = null;
                break block21;
                catch (Exception exception) {
                    throw ExceptionWithContext.withContext((Throwable)var23_17, "Error while reading instance field at index " + n4);
                }
            }
            for (n = 0; n < n7; ++n) {
                EncodedMethod encodedMethod2;
                try {
                    EncodedMethod[] arrencodedMethod = this.directMethods;
                    encodedMethod2 = new EncodedMethod(this.dexFile, readContext, input, encodedMethod);
                }
                catch (Exception exception) {
                    throw ExceptionWithContext.withContext((Throwable)var19_25, "Error while reading direct method at index " + n);
                }
                arrencodedMethod[n] = encodedMethod2;
                encodedMethod = encodedMethod2;
                continue;
            }
            break block22;
            catch (Exception exception) {
                throw ExceptionWithContext.withContext((Throwable)var19_25, "Error while reading direct method at index " + n);
            }
        }
        int n2 = 0;
        int n8 = this.virtualMethods.length;
        EncodedMethod encodedMethod3 = null;
        while (n2 < n8) {
            EncodedMethod[] arrencodedMethod = this.virtualMethods;
            EncodedMethod encodedMethod4 = new EncodedMethod(this.dexFile, readContext, input, encodedMethod3);
            arrencodedMethod[n2] = encodedMethod4;
            ++n2;
            encodedMethod3 = encodedMethod4;
        }
        return;
        catch (Exception exception) {
            throw ExceptionWithContext.withContext((Throwable)var15_33, "Error while reading virtual method at index " + n2);
        }
        catch (Exception exception) {
            throw ExceptionWithContext.withContext((Throwable)var15_33, "Error while reading virtual method at index " + n2);
        }
    }

    public void removeDirectMethod(int n) {
        int n2 = this.directMethods.length;
        EncodedMethod[] arrencodedMethod = this.directMethods;
        this.directMethods = new EncodedMethod[n2 - 1];
        System.arraycopy((Object)arrencodedMethod, (int)(n + 1), (Object)this.directMethods, (int)n, (int)(-1 + (n2 - n)));
        System.arraycopy((Object)arrencodedMethod, (int)0, (Object)this.directMethods, (int)0, (int)n);
    }

    public void removeInstanceField(int n) {
        int n2 = this.instanceFields.length;
        EncodedField[] arrencodedField = this.instanceFields;
        this.instanceFields = new EncodedField[n2 - 1];
        System.arraycopy((Object)arrencodedField, (int)(n + 1), (Object)this.instanceFields, (int)n, (int)(-1 + (n2 - n)));
        System.arraycopy((Object)arrencodedField, (int)0, (Object)this.instanceFields, (int)0, (int)n);
    }

    public void removeStaticField(int n) {
        int n2 = this.staticFields.length;
        EncodedField[] arrencodedField = this.staticFields;
        this.staticFields = new EncodedField[n2 - 1];
        System.arraycopy((Object)arrencodedField, (int)(n + 1), (Object)this.staticFields, (int)n, (int)(-1 + (n2 - n)));
        System.arraycopy((Object)arrencodedField, (int)0, (Object)this.staticFields, (int)0, (int)n);
    }

    public void removeVirtualMethod(int n) {
        int n2 = this.virtualMethods.length;
        EncodedMethod[] arrencodedMethod = this.virtualMethods;
        this.virtualMethods = new EncodedMethod[n2 - 1];
        System.arraycopy((Object)arrencodedMethod, (int)(n + 1), (Object)this.virtualMethods, (int)n, (int)(-1 + (n2 - n)));
        System.arraycopy((Object)arrencodedMethod, (int)0, (Object)this.virtualMethods, (int)0, (int)n);
    }

    public void setDirectMethod(int n, EncodedMethod encodedMethod) {
        if (n < -1 || n > -1 + this.directMethods.length) {
            return;
        }
        this.directMethods[n] = encodedMethod;
    }

    public void setInstanceField(int n, EncodedField encodedField) {
        if (n < -1 || n > -1 + this.instanceFields.length) {
            return;
        }
        this.instanceFields[n] = encodedField;
    }

    protected void setParent(ClassDefItem classDefItem) {
        this.parent = classDefItem;
    }

    public void setStaticField(int n, EncodedField encodedField) {
        if (n < -1 || n > -1 + this.staticFields.length) {
            return;
        }
        this.staticFields[n] = encodedField;
    }

    public void setVirtualMethod(int n, EncodedMethod encodedMethod) {
        if (n < -1 || n > -1 + this.virtualMethods.length) {
            return;
        }
        this.virtualMethods[n] = encodedMethod;
    }

    public void sortFields() {
        if (this.staticFields != null && this.staticFields.length > 0) {
            Arrays.sort((Object[])this.staticFields);
        }
        if (this.instanceFields != null && this.instanceFields.length > 0) {
            Arrays.sort((Object[])this.instanceFields);
        }
    }

    public void sortMethods() {
        if (this.directMethods != null && this.directMethods.length > 0) {
            Arrays.sort((Object[])this.directMethods);
        }
        if (this.virtualMethods != null && this.virtualMethods.length > 0) {
            Arrays.sort((Object[])this.virtualMethods);
        }
    }

    @Override
    protected void writeItem(AnnotatedOutput annotatedOutput) {
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate("static_fields_size: 0x" + Integer.toHexString((int)this.staticFields.length) + " (" + this.staticFields.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.staticFields.length);
            annotatedOutput.annotate("instance_fields_size: 0x" + Integer.toHexString((int)this.instanceFields.length) + " (" + this.instanceFields.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.instanceFields.length);
            annotatedOutput.annotate("direct_methods_size: 0x" + Integer.toHexString((int)this.directMethods.length) + " (" + this.directMethods.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.directMethods.length);
            annotatedOutput.annotate("virtual_methods_size: 0x" + Integer.toHexString((int)this.virtualMethods.length) + " (" + this.virtualMethods.length + ")");
            annotatedOutput.writeUnsignedLeb128(this.virtualMethods.length);
            EncodedField[] arrencodedField = this.staticFields;
            EncodedField encodedField = null;
            int n = arrencodedField.length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                EncodedField encodedField2 = arrencodedField[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n3 = n2 + 1;
                annotatedOutput.annotate(stringBuilder.append(n2).append("] static_field").toString());
                annotatedOutput.indent();
                encodedField2.writeTo(annotatedOutput, encodedField);
                annotatedOutput.deindent();
                encodedField = encodedField2;
                n2 = n3;
            }
            EncodedField[] arrencodedField2 = this.instanceFields;
            EncodedField encodedField3 = null;
            int n4 = arrencodedField2.length;
            int n5 = 0;
            for (int i = 0; i < n4; ++i) {
                EncodedField encodedField4 = arrencodedField2[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n6 = n5 + 1;
                annotatedOutput.annotate(stringBuilder.append(n5).append("] instance_field").toString());
                annotatedOutput.indent();
                encodedField4.writeTo(annotatedOutput, encodedField3);
                annotatedOutput.deindent();
                encodedField3 = encodedField4;
                n5 = n6;
            }
            EncodedMethod[] arrencodedMethod = this.directMethods;
            EncodedMethod encodedMethod = null;
            int n7 = arrencodedMethod.length;
            int n8 = 0;
            for (int i = 0; i < n7; ++i) {
                EncodedMethod encodedMethod2 = arrencodedMethod[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n9 = n8 + 1;
                annotatedOutput.annotate(stringBuilder.append(n8).append("] direct_method").toString());
                annotatedOutput.indent();
                encodedMethod2.writeTo(annotatedOutput, encodedMethod);
                annotatedOutput.deindent();
                encodedMethod = encodedMethod2;
                n8 = n9;
            }
            EncodedMethod[] arrencodedMethod2 = this.virtualMethods;
            EncodedMethod encodedMethod3 = null;
            int n10 = arrencodedMethod2.length;
            int n11 = 0;
            for (int i = 0; i < n10; ++i) {
                EncodedMethod encodedMethod4 = arrencodedMethod2[i];
                StringBuilder stringBuilder = new StringBuilder().append("[");
                int n12 = n11 + 1;
                annotatedOutput.annotate(stringBuilder.append(n11).append("] virtual_method").toString());
                annotatedOutput.indent();
                encodedMethod4.writeTo(annotatedOutput, encodedMethod3);
                annotatedOutput.deindent();
                encodedMethod3 = encodedMethod4;
                n11 = n12;
            }
        } else {
            annotatedOutput.writeUnsignedLeb128(this.staticFields.length);
            annotatedOutput.writeUnsignedLeb128(this.instanceFields.length);
            annotatedOutput.writeUnsignedLeb128(this.directMethods.length);
            annotatedOutput.writeUnsignedLeb128(this.virtualMethods.length);
            EncodedField[] arrencodedField = this.staticFields;
            EncodedField encodedField = null;
            for (EncodedField encodedField5 : arrencodedField) {
                encodedField5.writeTo(annotatedOutput, encodedField);
                encodedField = encodedField5;
            }
            EncodedField[] arrencodedField3 = this.instanceFields;
            EncodedField encodedField6 = null;
            for (EncodedField encodedField7 : arrencodedField3) {
                encodedField7.writeTo(annotatedOutput, encodedField6);
                encodedField6 = encodedField7;
            }
            EncodedMethod[] arrencodedMethod = this.directMethods;
            EncodedMethod encodedMethod = null;
            for (EncodedMethod encodedMethod5 : arrencodedMethod) {
                encodedMethod5.writeTo(annotatedOutput, encodedMethod);
                encodedMethod = encodedMethod5;
            }
            EncodedMethod[] arrencodedMethod3 = this.virtualMethods;
            EncodedMethod encodedMethod6 = null;
            for (EncodedMethod encodedMethod7 : arrencodedMethod3) {
                encodedMethod7.writeTo(annotatedOutput, encodedMethod6);
                encodedMethod6 = encodedMethod7;
            }
        }
    }

    public static class EncodedField
    implements Comparable<EncodedField> {
        public final int accessFlags;
        public final FieldIdItem field;

        /*
         * Enabled aggressive block sorting
         */
        private EncodedField(DexFile dexFile, Input input, EncodedField encodedField) {
            int n = encodedField == null ? 0 : encodedField.field.getIndex();
            this.field = dexFile.FieldIdsSection.getItemByIndex(n + input.readUnsignedLeb128());
            this.accessFlags = input.readUnsignedLeb128();
        }

        /* synthetic */ EncodedField(DexFile dexFile, Input input, EncodedField encodedField, 1 var4) {
            super(dexFile, input, encodedField);
        }

        public EncodedField(FieldIdItem fieldIdItem, int n) {
            this.field = fieldIdItem;
            this.accessFlags = n;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private int place(int n, EncodedField encodedField) {
            int n2;
            if (encodedField == null) {
                n2 = 0;
                do {
                    return n + Leb128Utils.unsignedLeb128Size(this.field.getIndex() - n2) + Leb128Utils.unsignedLeb128Size(this.accessFlags);
                    break;
                } while (true);
            }
            n2 = encodedField.field.getIndex();
            return n + Leb128Utils.unsignedLeb128Size(this.field.getIndex() - n2) + Leb128Utils.unsignedLeb128Size(this.accessFlags);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void writeTo(AnnotatedOutput annotatedOutput, EncodedField encodedField) {
            int n = encodedField == null ? 0 : encodedField.field.getIndex();
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate("field: " + this.field.getFieldString());
                annotatedOutput.writeUnsignedLeb128(this.field.getIndex() - n);
                annotatedOutput.annotate("access_flags: " + AccessFlags.formatAccessFlagsForField(this.accessFlags));
                annotatedOutput.writeUnsignedLeb128(this.accessFlags);
                return;
            }
            annotatedOutput.writeUnsignedLeb128(this.field.getIndex() - n);
            annotatedOutput.writeUnsignedLeb128(this.accessFlags);
        }

        public int compareTo(EncodedField encodedField) {
            return this.field.compareTo(encodedField.field);
        }

        public boolean isStatic() {
            return (this.accessFlags & AccessFlags.STATIC.getValue()) != 0;
        }
    }

    public static class EncodedMethod
    implements Comparable<EncodedMethod> {
        public final int accessFlags;
        public final CodeItem codeItem;
        public final MethodIdItem method;

        /*
         * Enabled aggressive block sorting
         */
        public EncodedMethod(DexFile dexFile, ReadContext readContext, Input input, EncodedMethod encodedMethod) {
            int n = encodedMethod == null ? 0 : encodedMethod.method.getIndex();
            this.method = dexFile.MethodIdsSection.getItemByIndex(n + input.readUnsignedLeb128());
            this.accessFlags = input.readUnsignedLeb128();
            if (dexFile.skipInstructions()) {
                input.readUnsignedLeb128();
                this.codeItem = null;
            } else {
                this.codeItem = (CodeItem)readContext.getOptionalOffsettedItemByOffset(ItemType.TYPE_CODE_ITEM, input.readUnsignedLeb128());
            }
            if (this.codeItem != null) {
                this.codeItem.setParent((EncodedMethod)this);
            }
        }

        public EncodedMethod(MethodIdItem methodIdItem, int n, CodeItem codeItem) {
            this.method = methodIdItem;
            this.accessFlags = n;
            this.codeItem = codeItem;
            if (codeItem != null) {
                codeItem.setParent((EncodedMethod)this);
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private int place(int n, EncodedMethod encodedMethod) {
            int n2;
            int n3 = encodedMethod == null ? 0 : encodedMethod.method.getIndex();
            int n4 = n + Leb128Utils.unsignedLeb128Size(this.method.getIndex() - n3) + Leb128Utils.unsignedLeb128Size(this.accessFlags);
            if (this.codeItem == null) {
                n2 = 1;
                return n4 + n2;
            }
            n2 = Leb128Utils.unsignedLeb128Size(this.codeItem.getOffset());
            return n4 + n2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void writeTo(AnnotatedOutput annotatedOutput, EncodedMethod encodedMethod) {
            int n = encodedMethod == null ? 0 : encodedMethod.method.getIndex();
            if (annotatedOutput.annotates()) {
                annotatedOutput.annotate("method: " + this.method.getMethodString());
                annotatedOutput.writeUnsignedLeb128(this.method.getIndex() - n);
                annotatedOutput.annotate("access_flags: " + AccessFlags.formatAccessFlagsForMethod(this.accessFlags));
                annotatedOutput.writeUnsignedLeb128(this.accessFlags);
                if (this.codeItem != null) {
                    annotatedOutput.annotate("code_off: 0x" + Integer.toHexString((int)this.codeItem.getOffset()));
                    annotatedOutput.writeUnsignedLeb128(this.codeItem.getOffset());
                    return;
                }
                annotatedOutput.annotate("code_off: 0x0");
                annotatedOutput.writeUnsignedLeb128(0);
                return;
            }
            annotatedOutput.writeUnsignedLeb128(this.method.getIndex() - n);
            annotatedOutput.writeUnsignedLeb128(this.accessFlags);
            CodeItem codeItem = this.codeItem;
            int n2 = 0;
            if (codeItem != null) {
                n2 = this.codeItem.getOffset();
            }
            annotatedOutput.writeUnsignedLeb128(n2);
        }

        public int compareTo(EncodedMethod encodedMethod) {
            return this.method.compareTo(encodedMethod.method);
        }

        public boolean isDirect() {
            return (this.accessFlags & (AccessFlags.STATIC.getValue() | AccessFlags.PRIVATE.getValue() | AccessFlags.CONSTRUCTOR.getValue())) != 0;
        }
    }

}

