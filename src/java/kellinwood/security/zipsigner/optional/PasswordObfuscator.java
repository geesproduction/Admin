/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.BufferedReader
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.OutputStreamWriter
 *  java.io.Reader
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.Key
 *  javax.crypto.Cipher
 *  javax.crypto.spec.SecretKeySpec
 */
package kellinwood.security.zipsigner.optional;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.LoggerManager;
import kellinwood.security.zipsigner.Base64;

public class PasswordObfuscator {
    private static PasswordObfuscator instance = null;
    static final String x = "harold-and-maude";
    LoggerInterface logger = LoggerManager.getLogger(PasswordObfuscator.class.getName());
    SecretKeySpec skeySpec = new SecretKeySpec("harold-and-maude".getBytes(), "AES");

    private PasswordObfuscator() {
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void flush(byte[] arrby) {
        if (arrby != null) {
            for (int i = 0; i < arrby.length; ++i) {
                arrby[i] = 0;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void flush(char[] arrc) {
        if (arrc != null) {
            for (int i = 0; i < arrc.length; ++i) {
                arrc[i] = '\u0000';
            }
        }
    }

    public static PasswordObfuscator getInstance() {
        if (instance == null) {
            instance = new PasswordObfuscator();
        }
        return instance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public char[] decode(String string2, String string3) {
        if (string3 == null) {
            return null;
        }
        try {
            int n;
            Cipher cipher = Cipher.getInstance((String)"AES/ECB/PKCS5Padding");
            cipher.init(2, (Key)new SecretKeySpec(x.getBytes(), "AES"));
            BufferedReader bufferedReader = new BufferedReader((Reader)new InputStreamReader((InputStream)new ByteArrayInputStream(cipher.doFinal(Base64.decode(string3.getBytes())))));
            char[] arrc = new char[128];
            int n2 = 0;
            while ((n = bufferedReader.read(arrc, n2, 128 - n2)) != -1) {
                n2 += n;
            }
            if (n2 <= string2.length()) {
                return null;
            }
            char[] arrc2 = new char[n2 - string2.length()];
            int n3 = 0;
            for (int i = string2.length(); i < n2; ++n3, ++i) {
                arrc2[n3] = arrc[i];
            }
            PasswordObfuscator.flush(arrc);
            return arrc2;
        }
        catch (Exception exception) {
            this.logger.error("Failed to decode password", exception);
            return null;
        }
    }

    public char[] decodeAliasPassword(String string2, String string3, String string4) {
        return this.decode(string2 + string3, string4);
    }

    public char[] decodeKeystorePassword(String string2, String string3) {
        return this.decode(string2, string3);
    }

    public String encode(String string2, String string3) {
        if (string3 == null) {
            return null;
        }
        char[] arrc = string3.toCharArray();
        String string4 = this.encode(string2, arrc);
        PasswordObfuscator.flush(arrc);
        return string4;
    }

    public String encode(String string2, char[] arrc) {
        if (arrc == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance((String)"AES/ECB/PKCS5Padding");
            cipher.init(1, (Key)this.skeySpec);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream);
            outputStreamWriter.write(string2);
            outputStreamWriter.write(arrc);
            outputStreamWriter.flush();
            String string3 = Base64.encode(cipher.doFinal(byteArrayOutputStream.toByteArray()));
            return string3;
        }
        catch (Exception exception) {
            this.logger.error("Failed to obfuscate password", exception);
            return null;
        }
    }

    public String encodeAliasPassword(String string2, String string3, String string4) {
        return this.encode(string2 + string3, string4);
    }

    public String encodeAliasPassword(String string2, String string3, char[] arrc) {
        return this.encode(string2 + string3, arrc);
    }

    public String encodeKeystorePassword(String string2, String string3) {
        return this.encode(string2, string3);
    }

    public String encodeKeystorePassword(String string2, char[] arrc) {
        return this.encode(string2, arrc);
    }
}

