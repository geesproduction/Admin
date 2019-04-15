/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.AssertionError
 *  java.lang.Comparable
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package org.jf.dexlib;

import org.jf.dexlib.DexFile;
import org.jf.dexlib.ItemType;
import org.jf.dexlib.ReadContext;
import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.AnnotatedOutput;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Input;

public abstract class Item<T extends Item>
implements Comparable<T> {
    static final /* synthetic */ boolean $assertionsDisabled;
    protected final DexFile dexFile;
    protected int index = -1;
    protected int offset = -1;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !Item.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
    }

    protected Item(DexFile dexFile) {
        if (!$assertionsDisabled && dexFile == null) {
            throw new AssertionError();
        }
        this.dexFile = dexFile;
    }

    protected final RuntimeException addExceptionContext(Exception exception) {
        return ExceptionWithContext.withContext(exception, this.getConciseIdentity());
    }

    public abstract String getConciseIdentity();

    public DexFile getDexFile() {
        return this.dexFile;
    }

    public int getIndex() {
        return this.index;
    }

    public abstract ItemType getItemType();

    public int getOffset() {
        return this.offset;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected int placeAt(int n, int n2) {
        try {
            if (!$assertionsDisabled && !AlignmentUtils.isAligned(n, this.getItemType().ItemAlignment)) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.dexFile.getInplace()) {
                if (n != this.offset) throw new AssertionError();
                if (this.index != n2) {
                    throw new AssertionError();
                }
            }
        }
        catch (Exception exception) {
            throw this.addExceptionContext(exception);
        }
        this.offset = n;
        this.index = n2;
        return this.placeItem(n);
    }

    protected abstract int placeItem(int var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void readFrom(Input input, int n, ReadContext readContext) {
        try {
            if (!$assertionsDisabled && !AlignmentUtils.isAligned(input.getCursor(), this.getItemType().ItemAlignment)) {
                throw new AssertionError();
            }
            this.offset = input.getCursor();
            this.index = n;
            this.readItem(input, readContext);
            return;
        }
        catch (Exception exception) {
            throw this.addExceptionContext(exception);
        }
    }

    protected abstract void readItem(Input var1, ReadContext var2);

    public String toString() {
        return this.getConciseIdentity();
    }

    protected abstract void writeItem(AnnotatedOutput var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void writeTo(AnnotatedOutput annotatedOutput) {
        try {
            if (!$assertionsDisabled && !AlignmentUtils.isAligned(this.offset, this.getItemType().ItemAlignment)) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && annotatedOutput.getCursor() != this.offset) {
                throw new AssertionError();
            }
        }
        catch (Exception exception) {
            throw this.addExceptionContext(exception);
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, "[" + this.index + "] " + this.getItemType().TypeName);
        }
        annotatedOutput.indent();
        this.writeItem(annotatedOutput);
        annotatedOutput.deindent();
    }
}

