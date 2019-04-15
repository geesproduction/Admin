/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.System
 */
package org.jf.dexlib.Util;

public class SparseIntArray {
    private int[] mKeys;
    private int mSize;
    private int[] mValues;

    public SparseIntArray() {
        this(10);
    }

    public SparseIntArray(int n) {
        this.mKeys = new int[n];
        this.mValues = new int[n];
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

    public void append(int n, int n2) {
        if (this.mSize != 0 && n <= this.mKeys[-1 + this.mSize]) {
            this.put(n, n2);
            return;
        }
        int n3 = this.mSize;
        if (n3 >= this.mKeys.length) {
            int n4 = Math.max((int)(n3 + 1), (int)(2 * this.mKeys.length));
            int[] arrn = new int[n4];
            int[] arrn2 = new int[n4];
            System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)this.mKeys.length);
            System.arraycopy((Object)this.mValues, (int)0, (Object)arrn2, (int)0, (int)this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrn2;
        }
        this.mKeys[n3] = n;
        this.mValues[n3] = n2;
        this.mSize = n3 + 1;
    }

    public void clear() {
        this.mSize = 0;
    }

    public void delete(int n) {
        int n2 = SparseIntArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n2 >= 0) {
            this.removeAt(n2);
        }
    }

    public int get(int n) {
        return this.get(n, 0);
    }

    public int get(int n, int n2) {
        int n3 = SparseIntArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n3 < 0) {
            return n2;
        }
        return this.mValues[n3];
    }

    public int indexOfKey(int n) {
        return SparseIntArray.binarySearch(this.mKeys, 0, this.mSize, n);
    }

    public int indexOfValue(int n) {
        for (int i = 0; i < this.mSize; ++i) {
            if (this.mValues[i] != n) continue;
            return i;
        }
        return -1;
    }

    public int keyAt(int n) {
        return this.mKeys[n];
    }

    public int[] keys() {
        int[] arrn = new int[this.mSize];
        System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)arrn.length);
        return arrn;
    }

    public void put(int n, int n2) {
        int n3 = SparseIntArray.binarySearch(this.mKeys, 0, this.mSize, n);
        if (n3 >= 0) {
            this.mValues[n3] = n2;
            return;
        }
        int n4 = ~n3;
        if (this.mSize >= this.mKeys.length) {
            int n5 = Math.max((int)(1 + this.mSize), (int)(2 * this.mKeys.length));
            int[] arrn = new int[n5];
            int[] arrn2 = new int[n5];
            System.arraycopy((Object)this.mKeys, (int)0, (Object)arrn, (int)0, (int)this.mKeys.length);
            System.arraycopy((Object)this.mValues, (int)0, (Object)arrn2, (int)0, (int)this.mValues.length);
            this.mKeys = arrn;
            this.mValues = arrn2;
        }
        if (this.mSize - n4 != 0) {
            System.arraycopy((Object)this.mKeys, (int)n4, (Object)this.mKeys, (int)(n4 + 1), (int)(this.mSize - n4));
            System.arraycopy((Object)this.mValues, (int)n4, (Object)this.mValues, (int)(n4 + 1), (int)(this.mSize - n4));
        }
        this.mKeys[n4] = n;
        this.mValues[n4] = n2;
        this.mSize = 1 + this.mSize;
    }

    public void removeAt(int n) {
        System.arraycopy((Object)this.mKeys, (int)(n + 1), (Object)this.mKeys, (int)n, (int)(this.mSize - (n + 1)));
        System.arraycopy((Object)this.mValues, (int)(n + 1), (Object)this.mValues, (int)n, (int)(this.mSize - (n + 1)));
        this.mSize = -1 + this.mSize;
    }

    public int size() {
        return this.mSize;
    }

    public int valueAt(int n) {
        return this.mValues[n];
    }

    public int[] values() {
        int[] arrn = new int[this.mSize];
        System.arraycopy((Object)this.mValues, (int)0, (Object)arrn, (int)0, (int)arrn.length);
        return arrn;
    }
}

