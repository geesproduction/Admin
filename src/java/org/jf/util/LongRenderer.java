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

public class LongRenderer {
    /*
     * Enabled aggressive block sorting
     */
    public static void writeSignedIntOrLongTo(IndentingWriter indentingWriter, long l) throws IOException {
        if (l < 0L) {
            indentingWriter.write("-0x");
            indentingWriter.printLongAsHex(-l);
            if (l >= Integer.MIN_VALUE) return;
            {
                indentingWriter.write(76);
                return;
            }
        } else {
            indentingWriter.write("0x");
            indentingWriter.printLongAsHex(l);
            if (l <= Integer.MAX_VALUE) return;
            {
                indentingWriter.write(76);
                return;
            }
        }
    }

    public static void writeTo(IndentingWriter indentingWriter, long l) throws IOException {
        if (l < 0L) {
            indentingWriter.write("-0x");
            indentingWriter.printLongAsHex(-l);
            indentingWriter.write(76);
            return;
        }
        indentingWriter.write("0x");
        indentingWriter.printLongAsHex(l);
        indentingWriter.write(76);
    }
}

