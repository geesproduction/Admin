/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.System
 *  java.util.Arrays
 *  java.util.List
 */
package org.jf.dexlib.Util;

import java.util.Arrays;
import java.util.List;

public class SparseArray<E> {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArray() {
        this(10);
    }

    public SparseArray(int n) {
        this.mKeys = new int[n];
        this.mValues = new Object[n];
        this.mSize = 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int binarySearch(int[] arrn, int n, int n2, int n3) {
        int n4 = n + n2;
        int n5 = n - 1;
        while (n4 - n5 > 1) {
            int n6 = (n4 + n5) / 2;
            if (arrn[n6] < n3) {
                n5 = n6;
                continue;
            }
            n4 = n6;
        }
        if (n4 == n + n2) {
            return -1 ^ n + n2;
        }
        if (arrn[n4] == n3) return n4;
        return ~n4;
    }

    private void gc() {
        int n = this.mSize;
        int n2 = 0;
        int[] arrn = this.mKeys;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            Object object = arrobject[i];
            if (object == DELETED) continue;
            if (i != n2) {
                arrn[n2] = arrn[i];
                arrobject[n2] = object;
            }
            ++n2;
        }
        this.mGarbage = false;
        this.mSize = n2;
    }

    public void append(int n, E e) {
        int n2;
        if (this.mSize != 0 && n <= this.mKeys[-1 + this.mSize]) {
            this.put(n, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            SparseArray.super.gc();
        }
        if ((n2 = this.mSize) >= this.mKeys.length) {
            int n3 = Math.max((int)(n2 + 1), (int)(2 * this.mKeys.length));
            int[] arrn = new int[n3];
            Object[] arrobject = new Object[n3];
            System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)this.mKeys.length);
            System.arraycopy((Object)this.mValues, (int)0, (Object)arrobject, (int)0, (int)this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
        this.mKeys[n2] = n;
        this.mValues[n2] = e;
        this.mSize = n2 + 1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] arrobject = this.mValues;
        for (int i = 0; i < n; ++i) {
            arrobject[i] = null;
        }
        this.mSize = 0;
        this.mGarbage = false;
    }

    public void delete(int n) {
        int n2 = SparseArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n2 >= 0 && this.mValues[n2] != DELETED) {
            this.mValues[n2] = DELETED;
            this.mGarbage = true;
        }
    }

    public void ensureCapacity(int n) {
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            SparseArray.super.gc();
        }
        if (this.mKeys.length < n) {
            int[] arrn = new int[n];
            Object[] arrobject = new Object[n];
            System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)this.mKeys.length);
            System.arraycopy((Object)this.mValues, (int)0, (Object)arrobject, (int)0, (int)this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
    }

    public E get(int n) {
        return this.get(n, null);
    }

    public E get(int n, E e) {
        int n2 = SparseArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n2 < 0 || this.mValues[n2] == DELETED) {
            return e;
        }
        return (E)this.mValues[n2];
    }

    public List<E> getValues() {
        return Arrays.asList((Object[])this.mValues);
    }

    public int indexOfKey(int n) {
        if (this.mGarbage) {
            SparseArray.super.gc();
        }
        return SparseArray.binarySearch(this.mKeys, 0, this.mSize, n);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            SparseArray.super.gc();
        }
        Object[] arrobject = this.mValues;
        for (int i = 0; i < this.mSize; ++i) {
            if (arrobject[i] != e) continue;
            return i;
        }
        return -1;
    }

    public int keyAt(int n) {
        if (this.mGarbage) {
            SparseArray.super.gc();
        }
        return this.mKeys[n];
    }

    public void put(int n, E e) {
        int n2 = SparseArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n2 >= 0) {
            this.mValues[n2] = e;
            return;
        }
        int n3 = ~n2;
        if (n3 < this.mSize && this.mValues[n3] == DELETED) {
            this.mKeys[n3] = n;
            this.mValues[n3] = e;
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            SparseArray.super.gc();
            n3 = -1 ^ SparseArray.binarySearch(this.mKeys, 0, this.mSize, n);
        }
        if (this.mSize >= this.mKeys.length) {
            int n4 = Math.max((int)(1 + this.mSize), (int)(2 * this.mKeys.length));
            int[] arrn = new int[n4];
            Object[] arrobject = new Object[n4];
            System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)this.mKeys.length);
            System.arraycopy((Object)this.mValues, (int)0, (Object)arrobject, (int)0, (int)this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
        if (this.mSize - n3 != 0) {
            System.arraycopy((Object)this.mKeys, (int)n3, (Object)this.mKeys, (int)(n3 + 1), (int)(this.mSize - n3));
            System.arraycopy((Object)this.mValues, (int)n3, (Object)this.mValues, (int)(n3 + 1), (int)(this.mSize - n3));
        }
        this.mKeys[n3] = n;
        this.mValues[n3] = e;
        this.mSize = 1 + this.mSize;
    }

    public void remove(int n) {
        this.delete(n);
    }

    public void setValueAt(int n, E e) {
        if (this.mGarbage) {
            SparseArray.super.gc();
        }
        this.mValues[n] = e;
    }

    public int size() {
        if (this.mGarbage) {
            this.gc();
        }
        return this.mSize;
    }

    public E valueAt(int n) {
        if (this.mGarbage) {
            SparseArray.super.gc();
        }
        return (E)this.mValues[n];
    }
}

