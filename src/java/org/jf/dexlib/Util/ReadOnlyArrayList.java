/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.AbstractList
 *  java.util.RandomAccess
 */
package org.jf.dexlib.Util;

import java.util.AbstractList;
import java.util.RandomAccess;

public class ReadOnlyArrayList<T>
extends AbstractList<T>
implements RandomAccess {
    private final T[] arr;

    public ReadOnlyArrayList(T[] arrT) {
        this.arr = arrT;
    }

    public T get(int n) {
        return this.arr[n];
    }

    public int size() {
        return this.arr.length;
    }
}

