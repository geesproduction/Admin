/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.lang.Throwable
 *  java.security.DigestException
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.util.Arrays
 *  java.util.Comparator
 *  java.util.List
 *  java.util.zip.Adler32
 *  org.jf.dexlib.DexFile$2
 */
package org.jf.dexlib;

import java.io.File;
import java.io.IOException;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.zip.Adler32;
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
import org.jf.dexlib.HeaderItem;
import org.jf.dexlib.IndexedSection;
import org.jf.dexlib.Item;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.MapItem;
import org.jf.dexlib.MethodIdItem;
import org.jf.dexlib.OdexDependencies;
import org.jf.dexlib.OdexHeader;
import org.jf.dexlib.OffsettedSection;
import org.jf.dexlib.ProtoIdItem;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Section;
import org.jf.dexlib.StringDataItem;
import org.jf.dexlib.StringIdItem;
import org.jf.dexlib.TypeIdItem;
import org.jf.dexlib.TypeListItem;
import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ByteArrayInput;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Hex;
import org.jf.dexlib.Util.Input;

public class DexFile {
    public final OffsettedSection<AnnotationDirectoryItem> AnnotationDirectoriesSection;
    public final OffsettedSection<AnnotationSetRefList> AnnotationSetRefListsSection;
    public final OffsettedSection<AnnotationSetItem> AnnotationSetsSection;
    public final OffsettedSection<AnnotationItem> AnnotationsSection;
    public final OffsettedSection<ClassDataItem> ClassDataSection;
    public final IndexedSection<ClassDefItem> ClassDefsSection;
    public final OffsettedSection<CodeItem> CodeItemsSection;
    public final OffsettedSection<DebugInfoItem> DebugInfoItemsSection;
    public final OffsettedSection<EncodedArrayItem> EncodedArraysSection;
    public final IndexedSection<FieldIdItem> FieldIdsSection;
    public final HeaderItem HeaderItem;
    public final MapItem MapItem;
    public final IndexedSection<MethodIdItem> MethodIdsSection;
    public final IndexedSection<ProtoIdItem> ProtoIdsSection;
    public final OffsettedSection<StringDataItem> StringDataSection;
    public final IndexedSection<StringIdItem> StringIdsSection;
    public final IndexedSection<TypeIdItem> TypeIdsSection;
    public final OffsettedSection<TypeListItem> TypeListsSection;
    private int dataOffset;
    private int dataSize;
    private final DexFile dexFile;
    private int fileSize;
    private final IndexedSection[] indexedSections;
    private boolean inplace;
    private boolean isOdex;
    private OdexDependencies odexDependencies;
    private OdexHeader odexHeader;
    private final OffsettedSection[] offsettedSections;
    private final boolean preserveSignedRegisters;
    private final Section[] sectionsByType;
    private final boolean skipInstructions;
    private boolean sortAllItems;

    public DexFile() {
        this(true, false);
    }

    public DexFile(File file) throws IOException {
        super(file, true, false);
    }

    /*
     * Exception decompiling
     */
    public DexFile(File var1, boolean var2_3, boolean var3_2) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [9[CATCHBLOCK]], but top level block is 5[TRYBLOCK]
        // org.benf.cfr.reader.b.a.a.j.a(Op04StructuredStatement.java:432)
        // org.benf.cfr.reader.b.a.a.j.d(Op04StructuredStatement.java:484)
        // org.benf.cfr.reader.b.a.a.i.a(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:692)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    public DexFile(String string2) throws IOException {
        super(new File(string2), true, false);
    }

    public DexFile(String string2, boolean bl, boolean bl2) throws IOException {
        super(new File(string2), bl, bl2);
    }

    private DexFile(boolean bl, boolean bl2) {
        this.inplace = false;
        this.sortAllItems = false;
        this.dexFile = this;
        this.isOdex = false;
        this.HeaderItem = new HeaderItem((DexFile)this);
        this.MapItem = new MapItem((DexFile)this);
        this.StringIdsSection = new IndexedSection((DexFile)this, ItemType.TYPE_STRING_ID_ITEM);
        this.TypeIdsSection = new IndexedSection((DexFile)this, ItemType.TYPE_TYPE_ID_ITEM);
        this.ProtoIdsSection = new IndexedSection((DexFile)this, ItemType.TYPE_PROTO_ID_ITEM);
        this.FieldIdsSection = new IndexedSection((DexFile)this, ItemType.TYPE_FIELD_ID_ITEM);
        this.MethodIdsSection = new IndexedSection((DexFile)this, ItemType.TYPE_METHOD_ID_ITEM);
        this.ClassDefsSection = new 2((DexFile)this, (DexFile)this, ItemType.TYPE_CLASS_DEF_ITEM);
        this.TypeListsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_TYPE_LIST);
        this.AnnotationSetRefListsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_ANNOTATION_SET_REF_LIST);
        this.AnnotationSetsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_ANNOTATION_SET_ITEM);
        this.ClassDataSection = new OffsettedSection((DexFile)this, ItemType.TYPE_CLASS_DATA_ITEM);
        this.CodeItemsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_CODE_ITEM);
        this.StringDataSection = new OffsettedSection((DexFile)this, ItemType.TYPE_STRING_DATA_ITEM);
        this.DebugInfoItemsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_DEBUG_INFO_ITEM);
        this.AnnotationsSection = new OffsettedSection((DexFile)this, ItemType.TYPE_ANNOTATION_ITEM);
        this.EncodedArraysSection = new OffsettedSection((DexFile)this, ItemType.TYPE_ENCODED_ARRAY_ITEM);
        this.AnnotationDirectoriesSection = new OffsettedSection((DexFile)this, ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM);
        this.preserveSignedRegisters = bl;
        this.skipInstructions = bl2;
        Section[] arrsection = new Section[]{this.StringIdsSection, this.TypeIdsSection, this.ProtoIdsSection, this.FieldIdsSection, this.MethodIdsSection, this.ClassDefsSection, this.TypeListsSection, this.AnnotationSetRefListsSection, this.AnnotationSetsSection, this.ClassDataSection, this.CodeItemsSection, this.AnnotationDirectoriesSection, this.StringDataSection, this.DebugInfoItemsSection, this.AnnotationsSection, this.EncodedArraysSection, null, null};
        this.sectionsByType = arrsection;
        IndexedSection[] arrindexedSection = new IndexedSection[]{this.StringIdsSection, this.TypeIdsSection, this.ProtoIdsSection, this.FieldIdsSection, this.MethodIdsSection, this.ClassDefsSection};
        this.indexedSections = arrindexedSection;
        OffsettedSection[] arroffsettedSection = new OffsettedSection[]{this.AnnotationSetRefListsSection, this.AnnotationSetsSection, this.CodeItemsSection, this.AnnotationDirectoriesSection, this.TypeListsSection, this.StringDataSection, this.AnnotationsSection, this.EncodedArraysSection, this.ClassDataSection, this.DebugInfoItemsSection};
        this.offsettedSections = arroffsettedSection;
    }

    public DexFile(byte[] arrby) {
        super(arrby, true, false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public DexFile(byte[] arrby, boolean bl, boolean bl2) {
        super(bl, bl2);
        byte[] arrby2 = new byte[8];
        System.arraycopy((Object)arrby, (int)0, (Object)arrby2, (int)0, (int)8);
        long l = arrby.length;
        if (l < 40L) {
            throw new RuntimeException(" is too small to be a valid dex file");
        }
        if (l > Integer.MAX_VALUE) {
            throw new RuntimeException(" is too large to read in");
        }
        if (!Arrays.equals((byte[])arrby2, (byte[])HeaderItem.MAGIC)) {
            StringBuffer stringBuffer = new StringBuffer("bad magic value:");
            int n = 0;
            do {
                if (n >= 8) {
                    throw new RuntimeException(stringBuffer.toString());
                }
                stringBuffer.append(" ");
                stringBuffer.append(Hex.u1(arrby2[n]));
                ++n;
            } while (true);
        }
        ByteArrayInput byteArrayInput = new ByteArrayInput(arrby);
        ReadContext readContext = new ReadContext();
        this.HeaderItem.readFrom(byteArrayInput, 0, readContext);
        byteArrayInput.setCursor(readContext.getSectionOffset(ItemType.TYPE_MAP_LIST));
        this.MapItem.readFrom(byteArrayInput, 0, readContext);
        Section[] arrsection = new Section[]{this.StringDataSection, this.StringIdsSection, this.TypeIdsSection, this.TypeListsSection, this.ProtoIdsSection, this.FieldIdsSection, this.MethodIdsSection, this.AnnotationsSection, this.AnnotationSetsSection, this.AnnotationSetRefListsSection, this.AnnotationDirectoriesSection, this.DebugInfoItemsSection, this.CodeItemsSection, this.ClassDataSection, this.EncodedArraysSection, this.ClassDefsSection};
        int n = arrsection.length;
        int n2 = 0;
        while (n2 < n) {
            int n3;
            Section section = arrsection[n2];
            if (section != null && (!bl2 || section != this.CodeItemsSection && section != this.DebugInfoItemsSection) && (n3 = readContext.getSectionOffset(section.ItemType)) > 0) {
                int n4 = readContext.getSectionSize(section.ItemType);
                byteArrayInput.setCursor(n3);
                section.readFrom(n4, byteArrayInput, readContext);
            }
            ++n2;
        }
        return;
    }

    static /* synthetic */ DexFile access$000(DexFile dexFile) {
        return dexFile.dexFile;
    }

    public static void calcChecksum(byte[] arrby) {
        Adler32 adler32 = new Adler32();
        adler32.update(arrby, 12, -12 + arrby.length);
        int n = (int)adler32.getValue();
        arrby[8] = (byte)n;
        arrby[9] = (byte)(n >> 8);
        arrby[10] = (byte)(n >> 16);
        arrby[11] = (byte)(n >> 24);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void calcSignature(byte[] arrby) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance((String)"SHA-1");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException((Throwable)noSuchAlgorithmException);
        }
        messageDigest.update(arrby, 32, -32 + arrby.length);
        try {
            int n = messageDigest.digest(arrby, 12, 20);
            if (n == 20) return;
            throw new RuntimeException("unexpected digest write: " + n + " bytes");
        }
        catch (DigestException digestException) {
            throw new RuntimeException((Throwable)digestException);
        }
    }

    public int getDataOffset() {
        return this.dataOffset;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public int getFileSize() {
        return this.fileSize;
    }

    public boolean getInplace() {
        return this.inplace;
    }

    public OdexDependencies getOdexDependencies() {
        return this.odexDependencies;
    }

    public OdexHeader getOdexHeader() {
        return this.odexHeader;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected Section[] getOrderedSections() {
        int n = 0;
        for (Section section : this.sectionsByType) {
            if (section == null || section.getItems().size() <= 0) continue;
            ++n;
        }
        Object[] arrobject = new Section[n];
        Section[] arrsection = this.sectionsByType;
        int n2 = arrsection.length;
        int n3 = 0;
        int n4 = 0;
        do {
            int n5;
            if (n3 >= n2) {
                Arrays.sort((Object[])arrobject, (Comparator)new Comparator<Section>(){

                    public int compare(Section section, Section section2) {
                        return section.getOffset() - section2.getOffset();
                    }
                });
                return arrobject;
            }
            Section section = arrsection[n3];
            if (section != null && section.getItems().size() > 0) {
                n5 = n4 + 1;
                arrobject[n4] = section;
            } else {
                n5 = n4;
            }
            ++n3;
            n4 = n5;
        } while (true);
    }

    public boolean getPreserveSignedRegisters() {
        return this.preserveSignedRegisters;
    }

    public <T extends Item> Section<T> getSectionForItem(T t) {
        return this.sectionsByType[t.getItemType().SectionIndex];
    }

    public Section getSectionForType(ItemType itemType) {
        return this.sectionsByType[itemType.SectionIndex];
    }

    public boolean getSortAllItems() {
        return this.sortAllItems;
    }

    public boolean isOdex() {
        return this.isOdex;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void place() {
        Section[] arrsection;
        int n = this.HeaderItem.placeAt(0, 0);
        int n2 = 0;
        if (this.inplace) {
            arrsection = this.getOrderedSections();
        } else {
            arrsection = new Section[this.indexedSections.length + this.offsettedSections.length];
            System.arraycopy((Object)this.indexedSections, (int)0, (Object)arrsection, (int)0, (int)this.indexedSections.length);
            System.arraycopy((Object)this.offsettedSections, (int)0, (Object)arrsection, (int)this.indexedSections.length, (int)this.offsettedSections.length);
            n2 = 0;
        }
        while (n2 < arrsection.length && arrsection[n2].ItemType.isIndexedItem()) {
            Section section = arrsection[n2];
            if (!this.inplace) {
                section.sortSection();
            }
            n = section.placeAt(n);
            ++n2;
        }
        this.dataOffset = n;
        do {
            if (n2 >= arrsection.length) {
                int n3;
                int n4 = AlignmentUtils.alignOffset(n, ItemType.TYPE_MAP_LIST.ItemAlignment);
                this.fileSize = n3 = this.MapItem.placeAt(n4, 0);
                this.dataSize = n3 - this.dataOffset;
                return;
            }
            Section section = arrsection[n2];
            if (this.sortAllItems && !this.inplace) {
                section.sortSection();
            }
            n = section.placeAt(n);
            ++n2;
        } while (true);
    }

    public void setInplace(boolean bl) {
        this.inplace = bl;
    }

    public void setSortAllItems(boolean bl) {
        this.sortAllItems = bl;
    }

    public boolean skipInstructions() {
        return this.skipInstructions;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void writeTo(AnnotatedOutput annotatedOutput) {
        Section[] arrsection;
        annotatedOutput.annotate(0, "-----------------------------");
        annotatedOutput.annotate(0, "header item");
        annotatedOutput.annotate(0, "-----------------------------");
        annotatedOutput.annotate(0, " ");
        this.HeaderItem.writeTo(annotatedOutput);
        annotatedOutput.annotate(0, " ");
        int n = 0;
        if (this.inplace) {
            arrsection = this.getOrderedSections();
        } else {
            arrsection = new Section[this.indexedSections.length + this.offsettedSections.length];
            System.arraycopy((Object)this.indexedSections, (int)0, (Object)arrsection, (int)0, (int)this.indexedSections.length);
            System.arraycopy((Object)this.offsettedSections, (int)0, (Object)arrsection, (int)this.indexedSections.length, (int)this.offsettedSections.length);
            n = 0;
        }
        do {
            if (n >= arrsection.length) {
                annotatedOutput.alignTo(this.MapItem.getItemType().ItemAlignment);
                annotatedOutput.annotate(0, " ");
                annotatedOutput.annotate(0, "-----------------------------");
                annotatedOutput.annotate(0, "map item");
                annotatedOutput.annotate(0, "-----------------------------");
                annotatedOutput.annotate(0, " ");
                this.MapItem.writeTo(annotatedOutput);
                return;
            }
            arrsection[n].writeTo(annotatedOutput);
            ++n;
        } while (true);
    }

    public static class NoClassesDexException
    extends ExceptionWithContext {
        public NoClassesDexException(String string2) {
            super(string2);
        }
    }

}

