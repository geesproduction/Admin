/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Comparable
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.util.Arrays
 *  java.util.Comparator
 */
package org.jf.dexlib.Util;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayUtils {
    public static <A extends Comparable<A>, B> void sortTwoArrays(A[] arrA, B[] arrB) {
        if (arrA.length != arrB.length) {
            throw new RuntimeException("Both arrays must be of the same length");
        }
        Object[] arrobject = new 1element[arrA.length];
        Arrays.sort((Object[])arrobject, (Comparator)new Comparator<1element>(){

            public int compare(1element element, 1element element2) {
                return element.first.compareTo(element2.first);
            }
        });
        for (int i = 0; i < arrobject.length; ++i) {
            arrA[i] = ((1element)arrobject[i]).first;
            arrB[i] = ((1element)arrobject[i]).second;
        }
    }

    class 1element {
        public A first;
        public B second;

        1element() {
        }
    }

}

