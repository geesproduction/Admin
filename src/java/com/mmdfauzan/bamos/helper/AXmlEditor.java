/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 */
package com.mmdfauzan.bamos.helper;

import com.mmdfauzan.bamos.helper.AXmlDecoder;
import com.mmdfauzan.bamos.helper.Edit;
import com.mmdfauzan.bamos.helper.StringBlock;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class AXmlEditor
implements Edit {
    private AXmlDecoder axml;

    @Override
    public void read(List<String> list, byte[] arrby) throws IOException {
        this.axml = AXmlDecoder.read((InputStream)new ByteArrayInputStream(arrby));
        this.axml.mTableStrings.getStrings(list);
    }

    @Override
    public void write(String string2, OutputStream outputStream) throws IOException {
        String[] arrstring = string2.split("\n");
        ArrayList arrayList = new ArrayList(arrstring.length);
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add((Object)arrstring[i]);
        }
        this.axml.write((List<String>)arrayList, outputStream);
    }
}

