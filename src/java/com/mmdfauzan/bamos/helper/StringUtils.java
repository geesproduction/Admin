/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.util.Collection
 *  java.util.Iterator
 */
package com.mmdfauzan.bamos.helper;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
    private StringUtils() {
    }

    public static String join(Collection<String> collection, String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator iterator = collection.iterator();
        while (iterator.hasNext()) {
            stringBuffer.append((String)iterator.next());
            if (!iterator.hasNext()) continue;
            stringBuffer.append(string2);
        }
        return stringBuffer.toString();
    }
}

