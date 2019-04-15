/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.FileWriter
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintWriter
 *  java.io.Writer
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.Throwable
 *  java.security.Key
 *  java.security.KeyStore
 *  java.security.KeyStore$Entry
 *  java.security.KeyStore$PasswordProtection
 *  java.security.KeyStore$ProtectionParameter
 *  java.security.Provider
 *  java.security.Security
 *  org.spongycastle.jce.provider.BouncyCastleProvider
 */
package kellinwood.security.zipsigner.optional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.Key;
import java.security.KeyStore;
import java.security.Provider;
import java.security.Security;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.LoggerManager;
import kellinwood.security.zipsigner.optional.JksKeyStore;
import kellinwood.security.zipsigner.optional.PasswordObfuscator;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class KeyStoreFileManager {
    static LoggerInterface logger;
    static Provider provider;

    static {
        provider = new BouncyCastleProvider();
        logger = LoggerManager.getLogger(KeyStoreFileManager.class.getName());
        Security.addProvider((Provider)KeyStoreFileManager.getProvider());
    }

    public static boolean containsKey(String string2, String string3, String string4) throws Exception {
        return KeyStoreFileManager.loadKeyStore(string2, string3).containsAlias(string4);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static void copyFile(File var0_1, File var1, boolean var2_2) throws IOException {
        if (var1.exists() && var1.isDirectory()) {
            throw new IOException("Destination '" + (Object)var1 + "' exists but is a directory");
        }
        var3_3 = new FileInputStream(var0_1);
        var4_4 = new FileOutputStream(var1);
        try {
            var9_5 = new byte[4096];
            var10_6 = 0L;
            ** GOTO lbl17
        }
        catch (Throwable var5_8) {
            try {
                var4_4.close();
            }
            catch (IOException var6_11) {
                throw var5_8;
            }
            throw var5_8;
lbl17: // 2 sources:
            while (-1 != (var12_7 = var3_3.read(var9_5))) {
                var4_4.write(var9_5, 0, var12_7);
                var10_6 += (long)var12_7;
            }
            try {
                var4_4.close();
                ** GOTO lbl31
            }
            catch (Throwable var7_9) {
                try {
                    var3_3.close();
                }
                catch (IOException var8_13) {
                    throw var7_9;
                }
                throw var7_9;
                catch (IOException var13_10) {}
lbl31: // 2 sources:
                try {
                    var3_3.close();
                }
                catch (IOException var14_12) {}
                if (var0_1.length() != var1.length()) {
                    throw new IOException("Failed to copy full contents from '" + (Object)var0_1 + "' to '" + (Object)var1 + "'");
                }
                if (var2_2 == false) return;
                var1.setLastModified(var0_1.lastModified());
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static KeyStore createKeyStore(String string2, char[] arrc) throws Exception {
        JksKeyStore jksKeyStore = string2.toLowerCase().endsWith(".bks") ? KeyStore.getInstance((String)"bks", (Provider)new BouncyCastleProvider()) : new JksKeyStore();
        jksKeyStore.load(null, arrc);
        return jksKeyStore;
    }

    public static void deleteKey(String string2, String string3, String string4) throws Exception {
        KeyStore keyStore = KeyStoreFileManager.loadKeyStore(string2, string3);
        keyStore.deleteEntry(string4);
        KeyStoreFileManager.writeKeyStore(keyStore, string2, string3);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static KeyStore.Entry getKeyEntry(String string2, String string3, String string4, String string5) throws Exception {
        KeyStore.PasswordProtection passwordProtection;
        KeyStore.Entry entry;
        char[] arrc;
        KeyStore.PasswordProtection passwordProtection2;
        block7 : {
            KeyStore keyStore;
            arrc = null;
            passwordProtection2 = null;
            try {
                keyStore = KeyStoreFileManager.loadKeyStore(string2, string3);
                arrc = PasswordObfuscator.getInstance().decodeAliasPassword(string2, string4, string5);
                passwordProtection = new KeyStore.PasswordProtection(arrc);
            }
            catch (Throwable throwable) {}
            try {
                entry = keyStore.getEntry(string4, (KeyStore.ProtectionParameter)passwordProtection);
                if (arrc == null) break block7;
            }
            catch (Throwable throwable) {
                passwordProtection2 = passwordProtection;
            }
            PasswordObfuscator.flush(arrc);
        }
        if (passwordProtection != null) {
            passwordProtection.destroy();
        }
        return entry;
        {
            void var6_10;
            if (arrc != null) {
                PasswordObfuscator.flush(arrc);
            }
            if (passwordProtection2 != null) {
                passwordProtection2.destroy();
            }
            throw var6_10;
        }
    }

    public static Provider getProvider() {
        return provider;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static KeyStore loadKeyStore(String var0_1, String var1) throws Exception {
        var2_2 = null;
        if (var1 == null) ** GOTO lbl5
        try {
            var2_2 = PasswordObfuscator.getInstance().decodeKeystorePassword(var0_1, var1);
lbl5: // 2 sources:
            var4_3 = KeyStoreFileManager.loadKeyStore(var0_1, var2_2);
            return var4_3;
        }
        finally {
            if (var2_2 != null) {
                PasswordObfuscator.flush(var2_2);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static KeyStore loadKeyStore(String string2, char[] arrc) throws Exception {
        JksKeyStore jksKeyStore;
        try {
            jksKeyStore = new JksKeyStore();
        }
        catch (Exception exception) {}
        try {
            FileInputStream fileInputStream = new FileInputStream(string2);
            jksKeyStore.load((InputStream)fileInputStream, arrc);
            fileInputStream.close();
            return jksKeyStore;
        }
        catch (Exception exception) {}
        {
            try {
                KeyStore keyStore = KeyStore.getInstance((String)"bks", (Provider)KeyStoreFileManager.getProvider());
                FileInputStream fileInputStream = new FileInputStream(string2);
                keyStore.load((InputStream)fileInputStream, arrc);
                fileInputStream.close();
                return keyStore;
            }
            catch (Exception exception) {
                throw new RuntimeException("Failed to load keystore: " + exception.getMessage(), (Throwable)exception);
            }
        }
    }

    /*
     * Exception decompiling
     */
    public static String renameKey(String var0_2, String var1_1, String var2_4, String var3_3, String var4) throws Exception {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl16 : af: try { 1[TRYBLOCK]

        // org.benf.cfr.reader.b.a.a.b.g$1.a(Cleaner.java:44)
        // org.benf.cfr.reader.b.a.a.b.g$1.a(Cleaner.java:22)
        // org.benf.cfr.reader.util.d.c.d(GraphVisitorDFS.java:68)
        // org.benf.cfr.reader.b.a.a.b.g.a(Cleaner.java:54)
        // org.benf.cfr.reader.b.a.a.b.ao.a(RemoveDeterministicJumps.java:38)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:443)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:182)
        // org.benf.cfr.reader.b.f.a(CodeAnalyser.java:127)
        // org.benf.cfr.reader.entities.attributes.f.c(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.g.p(Method.java:396)
        // org.benf.cfr.reader.entities.d.e(ClassFile.java:890)
        // org.benf.cfr.reader.entities.d.b(ClassFile.java:792)
        // org.benf.cfr.reader.b.a(Driver.java:128)
        // org.benf.cfr.reader.a.a(CfrDriverImpl.java:63)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.decompileWithCFR(JavaExtractionWorker.kt:61)
        // com.njlabs.showjava.decompilers.JavaExtractionWorker.doWork(JavaExtractionWorker.kt:130)
        // com.njlabs.showjava.decompilers.BaseDecompiler.withAttempt(BaseDecompiler.kt:108)
        // com.njlabs.showjava.workers.DecompilerWorker$b.run(DecompilerWorker.kt:118)
        // java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1113)
        // java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:588)
        // java.lang.Thread.run(Thread.java:818)
        throw new IllegalStateException("Decompilation failed");
    }

    public static void renameTo(File file, File file2) throws IOException {
        KeyStoreFileManager.copyFile(file, file2, true);
        if (!file.delete()) {
            throw new IOException("Failed to delete " + (Object)file);
        }
    }

    public static void setProvider(Provider provider) {
        if (KeyStoreFileManager.provider != null) {
            Security.removeProvider((String)KeyStoreFileManager.provider.getName());
        }
        KeyStoreFileManager.provider = provider;
        Security.addProvider((Provider)provider);
    }

    public static void validateKeyPassword(String string2, String string3, String string4) throws Exception {
        block3 : {
            char[] arrc = null;
            try {
                KeyStore keyStore = KeyStoreFileManager.loadKeyStore(string2, (char[])null);
                arrc = PasswordObfuscator.getInstance().decodeAliasPassword(string2, string3, string4);
                keyStore.getKey(string3, arrc);
                if (arrc == null) break block3;
            }
            catch (Throwable throwable) {
                if (arrc != null) {
                    PasswordObfuscator.flush(arrc);
                }
                throw throwable;
            }
            PasswordObfuscator.flush(arrc);
        }
    }

    public static void validateKeystorePassword(String string2, String string3) throws Exception {
        try {
            KeyStoreFileManager.loadKeyStore(string2, string3);
            return;
        }
        finally {
            if (false) {
                PasswordObfuscator.flush(null);
            }
        }
    }

    public static void writeKeyStore(KeyStore keyStore, String string2, String string3) throws Exception {
        char[] arrc = null;
        try {
            arrc = PasswordObfuscator.getInstance().decodeKeystorePassword(string2, string3);
            KeyStoreFileManager.writeKeyStore(keyStore, string2, arrc);
            return;
        }
        finally {
            if (arrc != null) {
                PasswordObfuscator.flush(arrc);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void writeKeyStore(KeyStore keyStore, String string2, char[] arrc) throws Exception {
        File file = new File(string2);
        try {
            if (file.exists()) {
                File file2 = File.createTempFile((String)file.getName(), null, (File)file.getParentFile());
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                keyStore.store((OutputStream)fileOutputStream, arrc);
                fileOutputStream.flush();
                fileOutputStream.close();
                KeyStoreFileManager.renameTo(file2, file);
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(string2);
            keyStore.store((OutputStream)fileOutputStream, arrc);
            fileOutputStream.close();
            return;
        }
        catch (Exception exception) {
            try {
                PrintWriter printWriter = new PrintWriter((Writer)new FileWriter(File.createTempFile((String)"zipsigner-error", (String)".log", (File)file.getParentFile())));
                exception.printStackTrace(printWriter);
                printWriter.flush();
                printWriter.close();
            }
            catch (Exception exception2) {
                throw exception;
            }
            do {
                throw exception;
                break;
            } while (true);
        }
    }
}

