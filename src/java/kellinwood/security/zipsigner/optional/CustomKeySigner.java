/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.security.Key
 *  java.security.KeyStore
 *  java.security.PrivateKey
 *  java.security.cert.Certificate
 *  java.security.cert.X509Certificate
 */
package kellinwood.security.zipsigner.optional;

import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;

public class CustomKeySigner {
    public static void signZip(ZipSigner zipSigner, String string2, char[] arrc, String string3, char[] arrc2, String string4, String string5, String string6) throws Exception {
        zipSigner.issueLoadingCertAndKeysProgressEvent();
        KeyStore keyStore = KeyStoreFileManager.loadKeyStore(string2, arrc);
        zipSigner.setKeys("custom", (X509Certificate)keyStore.getCertificate(string3), (PrivateKey)keyStore.getKey(string3, arrc2), string4, null);
        zipSigner.signZip(string5, string6);
    }
}

