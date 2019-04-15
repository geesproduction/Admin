/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package org.jf.util;

import java.io.IOException;
import org.jf.util.IndentingWriter;

public class ByteRenderer {
    public static void writeTo(IndentingWriter indentingWriter, byte by) throws IOException {
        if (by < 0) {
            indentingWriter.write("-0x");
            indentingWriter.printLongAsHex(-by);
            indentingWriter.write(116);
            return;
        }
        indentingWriter.write("0x");
        indentingWriter.printLongAsHex(by);
        indentingWriter.write(116);
    }

    public static void writeUnsignedTo(IndentingWriter indentingWriter, byte by) throws IOException {
        indentingWriter.write("0x");
        indentingWriter.printLongAsHex(by & 255);
        indentingWriter.write(116);
    }
}

