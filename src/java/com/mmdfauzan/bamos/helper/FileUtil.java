/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 */
package com.mmdfauzan.bamos.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class FileUtil {
    private FileUtil() {
    }

    public static byte[] readFile(File file) throws IOException {
        return FileUtil.readFile(file, 0, -1);
    }

    public static byte[] readFile(File file, int n, int n2) throws IOException {
        if (!file.exists()) {
            throw new RuntimeException((Object)file + ": file not found");
        }
        if (!file.isFile()) {
            throw new RuntimeException((Object)file + ": not a file");
        }
        if (!file.canRead()) {
            throw new RuntimeException((Object)file + ": file not readable");
        }
        long l = file.length();
        int n3 = (int)l;
        if ((long)n3 != l) {
            throw new RuntimeException((Object)file + ": file too long");
        }
        if (n2 == -1) {
            n2 = n3 - n;
        }
        if (n + n2 > n3) {
            throw new RuntimeException((Object)file + ": file too short");
        }
        FileInputStream fileInputStream = new FileInputStream(file);
        int n4 = n;
        while (n4 > 0) {
            long l2 = fileInputStream.skip((long)n4);
            if (l2 == -1L) {
                throw new RuntimeException((Object)file + ": unexpected EOF");
            }
            n4 = (int)((long)n4 - l2);
        }
        byte[] arrby = FileUtil.readStream((InputStream)fileInputStream, n2);
        fileInputStream.close();
        return arrby;
    }

    public static byte[] readFile(String string2) throws IOException {
        return FileUtil.readFile(new File(string2));
    }

    public static byte[] readStream(InputStream inputStream, int n) throws IOException {
        byte[] arrby = new byte[n];
        int n2 = 0;
        while (n > 0) {
            int n3 = inputStream.read(arrby, n2, n);
            if (n3 == -1) {
                throw new RuntimeException("unexpected EOF");
            }
            n2 += n3;
            n -= n3;
        }
        return arrby;
    }
}

