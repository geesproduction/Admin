/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalArgumentException
 *  java.lang.IndexOutOfBoundsException
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 */
package org.jf.dexlib.Util;

import java.util.ArrayList;
import org.jf.dexlib.Util.AlignmentUtils;
import org.jf.dexlib.Util.ByteArray;
import org.jf.dexlib.Util.ExceptionWithContext;
import org.jf.dexlib.Util.Output;

public final class ByteArrayOutput
implements Output {
    private static final int DEFAULT_SIZE = 1000;
    private int annotationWidth;
    private ArrayList<Annotation> annotations;
    private int cursor;
    private byte[] data;
    private int hexCols;
    private final boolean stretchy;
    private boolean verbose;

    public ByteArrayOutput() {
        this(new byte[1000], true);
    }

    public ByteArrayOutput(byte[] arrby) {
        super(arrby, false);
    }

    private ByteArrayOutput(byte[] arrby, boolean bl) {
        if (arrby == null) {
            throw new NullPointerException("data == null");
        }
        this.stretchy = bl;
        this.data = arrby;
        this.cursor = 0;
        this.verbose = false;
        this.annotations = null;
        this.annotationWidth = 0;
        this.hexCols = 0;
    }

    private void ensureCapacity(int n) {
        if (this.data.length < n) {
            byte[] arrby = new byte[1000 + n * 2];
            System.arraycopy((Object)this.data, (int)0, (Object)arrby, (int)0, (int)this.cursor);
            this.data = arrby;
        }
    }

    private static void throwBounds() {
        throw new IndexOutOfBoundsException("attempt to write past the end");
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void alignTo(int n) {
        int n2 = AlignmentUtils.alignOffset(this.cursor, n);
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n2);
        } else if (n2 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        this.cursor = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void annotate(int n, String string2) {
        if (this.annotations == null) {
            return;
        }
        this.endAnnotation();
        int n2 = this.annotations.size();
        int n3 = n2 == 0 ? 0 : ((Annotation)this.annotations.get(n2 - 1)).getEnd();
        int n4 = n3 <= this.cursor ? this.cursor : n3;
        this.annotations.add((Object)new Annotation(n4, n4 + n, string2));
    }

    public void annotate(String string2) {
        if (this.annotations == null) {
            return;
        }
        this.endAnnotation();
        this.annotations.add((Object)new Annotation(this.cursor, string2));
    }

    public boolean annotates() {
        return this.annotations != null;
    }

    @Override
    public void assertCursor(int n) {
        if (this.cursor != n) {
            throw new ExceptionWithContext("expected cursor " + n + "; actual value: " + this.cursor);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void enableAnnotations(int n, boolean bl) {
        if (this.annotations != null || this.cursor != 0) {
            throw new RuntimeException("cannot enable annotations");
        }
        if (n < 40) {
            throw new IllegalArgumentException("annotationWidth < 40");
        }
        int n2 = -2 & 1 + (n - 7) / 15;
        if (n2 < 6) {
            n2 = 6;
        } else if (n2 > 10) {
            n2 = 10;
        }
        this.annotations = new ArrayList(1000);
        this.annotationWidth = n;
        this.hexCols = n2;
        this.verbose = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void endAnnotation() {
        int n;
        if (this.annotations == null || (n = this.annotations.size()) == 0) {
            return;
        }
        ((Annotation)this.annotations.get(n - 1)).setEndIfUnset(this.cursor);
    }

    public void finishAnnotating() {
        this.endAnnotation();
        if (this.annotations != null) {
            for (int i = this.annotations.size(); i > 0; --i) {
                Annotation annotation = (Annotation)this.annotations.get(i - 1);
                if (annotation.getStart() > this.cursor) {
                    this.annotations.remove(i - 1);
                    continue;
                }
                if (annotation.getEnd() <= this.cursor) break;
                annotation.setEnd(this.cursor);
                break;
            }
        }
    }

    public int getAnnotationWidth() {
        int n = 8 + 2 * this.hexCols + this.hexCols / 2;
        return this.annotationWidth - n;
    }

    public byte[] getArray() {
        return this.data;
    }

    @Override
    public int getCursor() {
        return this.cursor;
    }

    public boolean isVerbose() {
        return this.verbose;
    }

    public byte[] toByteArray() {
        byte[] arrby = new byte[this.cursor];
        System.arraycopy((Object)this.data, (int)0, (Object)arrby, (int)0, (int)this.cursor);
        return arrby;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(ByteArray byteArray) {
        int n = byteArray.size();
        int n2 = this.cursor;
        int n3 = n2 + n;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n3);
        } else if (n3 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        byteArray.getBytes(this.data, n2);
        this.cursor = n3;
    }

    @Override
    public void write(byte[] arrby) {
        this.write(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void write(byte[] arrby, int n, int n2) {
        int n3 = this.cursor;
        int n4 = n3 + n2;
        int n5 = n + n2;
        if ((n4 | (n | n2)) < 0 || n5 > arrby.length) {
            throw new IndexOutOfBoundsException("bytes.length " + arrby.length + "; " + n + "..!" + n4);
        }
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n4);
        } else if (n4 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        System.arraycopy((Object)arrby, (int)n, (Object)this.data, (int)n3, (int)n2);
        this.cursor = n4;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeByte(int n) {
        int n2 = this.cursor;
        int n3 = n2 + 1;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n3);
        } else if (n3 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        this.data[n2] = (byte)n;
        this.cursor = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeInt(int n) {
        int n2 = this.cursor;
        int n3 = n2 + 4;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n3);
        } else if (n3 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        this.data[n2] = (byte)n;
        this.data[n2 + 1] = (byte)(n >> 8);
        this.data[n2 + 2] = (byte)(n >> 16);
        this.data[n2 + 3] = (byte)(n >> 24);
        this.cursor = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeLong(long l) {
        int n = this.cursor;
        int n2 = n + 8;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n2);
        } else if (n2 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        int n3 = (int)l;
        this.data[n] = (byte)n3;
        this.data[n + 1] = (byte)(n3 >> 8);
        this.data[n + 2] = (byte)(n3 >> 16);
        this.data[n + 3] = (byte)(n3 >> 24);
        int n4 = (int)(l >> 32);
        this.data[n + 4] = (byte)n4;
        this.data[n + 5] = (byte)(n4 >> 8);
        this.data[n + 6] = (byte)(n4 >> 16);
        this.data[n + 7] = (byte)(n4 >> 24);
        this.cursor = n2;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeShort(int n) {
        int n2 = this.cursor;
        int n3 = n2 + 2;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n3);
        } else if (n3 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        this.data[n2] = (byte)n;
        this.data[n2 + 1] = (byte)(n >> 8);
        this.cursor = n3;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public int writeSignedLeb128(int n) {
        int n2;
        int n3 = n >> 7;
        int n4 = 0;
        boolean bl = true;
        if ((Integer.MIN_VALUE & n) == 0) {
            n2 = 0;
        } else {
            n2 = -1;
            n4 = 0;
        }
        while (bl) {
            bl = n3 != n2 || (n3 & 1) != (1 & n >> 6);
            int n5 = n & 127;
            int n6 = bl ? 128 : 0;
            this.writeByte(n6 | n5);
            n = n3;
            n3 >>= 7;
            ++n4;
        }
        return n4;
    }

    @Override
    public int writeUnsignedLeb128(int n) {
        int n2 = n >>> 7;
        int n3 = 0;
        while (n2 != 0) {
            this.writeByte(128 | n & 127);
            n = n2;
            n2 >>>= 7;
            ++n3;
        }
        this.writeByte(n & 127);
        return n3 + 1;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void writeZeroes(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        int n2 = n + this.cursor;
        if (this.stretchy) {
            ByteArrayOutput.super.ensureCapacity(n2);
        } else if (n2 > this.data.length) {
            ByteArrayOutput.throwBounds();
            return;
        }
        this.cursor = n2;
    }

    private static class Annotation {
        private int end;
        private final int start;
        private final String text;

        public Annotation(int n, int n2, String string2) {
            this.start = n;
            this.end = n2;
            this.text = string2;
        }

        public Annotation(int n, String string2) {
            super(n, Integer.MAX_VALUE, string2);
        }

        public int getEnd() {
            return this.end;
        }

        public int getStart() {
            return this.start;
        }

        public String getText() {
            return this.text;
        }

        public void setEnd(int n) {
            this.end = n;
        }

        public void setEndIfUnset(int n) {
            if (this.end == Integer.MAX_VALUE) {
                this.end = n;
            }
        }
    }

}

