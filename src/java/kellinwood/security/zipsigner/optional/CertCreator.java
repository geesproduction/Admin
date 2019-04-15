/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.File
 *  java.io.IOException
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.math.BigInteger
 *  java.security.Key
 *  java.security.KeyPair
 *  java.security.KeyPairGenerator
 *  java.security.KeyStore
 *  java.security.PrivateKey
 *  java.security.PublicKey
 *  java.security.SecureRandom
 *  java.security.cert.Certificate
 *  java.security.cert.X509Certificate
 *  java.util.Date
 *  kellinwood.security.zipsigner.optional.DistinguishedNameValues
 *  kellinwood.security.zipsigner.optional.KeyStoreFileManager
 *  org.spongycastle.asn1.x509.X509Name
 *  org.spongycastle.jce.X509Principal
 *  org.spongycastle.x509.X509V3CertificateGenerator
 */
package kellinwood.security.zipsigner.optional;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import kellinwood.security.zipsigner.KeySet;
import kellinwood.security.zipsigner.optional.DistinguishedNameValues;
import kellinwood.security.zipsigner.optional.KeyStoreFileManager;
import org.spongycastle.asn1.x509.X509Name;
import org.spongycastle.jce.X509Principal;
import org.spongycastle.x509.X509V3CertificateGenerator;

public class CertCreator {
    public static KeySet createKey(String string2, int n, String string3, String string4, int n2, DistinguishedNameValues distinguishedNameValues) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance((String)string2);
            keyPairGenerator.initialize(n);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            X509V3CertificateGenerator x509V3CertificateGenerator = new X509V3CertificateGenerator();
            X509Principal x509Principal = distinguishedNameValues.getPrincipal();
            BigInteger bigInteger = BigInteger.valueOf((long)new SecureRandom().nextInt());
            while (bigInteger.compareTo(BigInteger.ZERO) < 0) {
                bigInteger = BigInteger.valueOf((long)new SecureRandom().nextInt());
            }
            x509V3CertificateGenerator.setSerialNumber(bigInteger);
            x509V3CertificateGenerator.setIssuerDN((X509Name)x509Principal);
            x509V3CertificateGenerator.setNotBefore(new Date(System.currentTimeMillis() - 2592000000L));
            x509V3CertificateGenerator.setNotAfter(new Date(System.currentTimeMillis() + 31622400000L * (long)n2));
            x509V3CertificateGenerator.setSubjectDN((X509Name)x509Principal);
            x509V3CertificateGenerator.setPublicKey(keyPair.getPublic());
            x509V3CertificateGenerator.setSignatureAlgorithm(string4);
            X509Certificate x509Certificate = x509V3CertificateGenerator.generate(keyPair.getPrivate(), "BC");
            KeySet keySet = new KeySet();
            keySet.setName(string3);
            keySet.setPrivateKey(keyPair.getPrivate());
            keySet.setPublicKey(x509Certificate);
            return keySet;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage(), (Throwable)exception);
        }
    }

    public static KeySet createKey(String string2, char[] arrc, String string3, int n, String string4, char[] arrc2, String string5, int n2, DistinguishedNameValues distinguishedNameValues) {
        try {
            KeySet keySet = CertCreator.createKey(string3, n, string4, string5, n2, distinguishedNameValues);
            KeyStore keyStore = KeyStoreFileManager.loadKeyStore((String)string2, (char[])arrc);
            PrivateKey privateKey = keySet.getPrivateKey();
            Certificate[] arrcertificate = new Certificate[]{keySet.getPublicKey()};
            keyStore.setKeyEntry(string4, (Key)privateKey, arrc2, arrcertificate);
            KeyStoreFileManager.writeKeyStore((KeyStore)keyStore, (String)string2, (char[])arrc);
            return keySet;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage(), (Throwable)exception);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static KeySet createKeystoreAndKey(String string2, char[] arrc, String string3, int n, String string4, char[] arrc2, String string5, int n2, DistinguishedNameValues distinguishedNameValues) {
        try {
            KeySet keySet = CertCreator.createKey(string3, n, string4, string5, n2, distinguishedNameValues);
            KeyStore keyStore = KeyStoreFileManager.createKeyStore((String)string2, (char[])arrc);
            PrivateKey privateKey = keySet.getPrivateKey();
            Certificate[] arrcertificate = new Certificate[]{keySet.getPublicKey()};
            keyStore.setKeyEntry(string4, (Key)privateKey, arrc2, arrcertificate);
            if (new File(string2).exists()) {
                throw new IOException("File already exists: " + string2);
            }
            KeyStoreFileManager.writeKeyStore((KeyStore)keyStore, (String)string2, (char[])arrc);
            return keySet;
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage(), (Throwable)exception);
        }
    }

    public static void createKeystoreAndKey(String string2, char[] arrc, String string3, DistinguishedNameValues distinguishedNameValues) {
        CertCreator.createKeystoreAndKey(string2, arrc, "RSA", 2048, string3, arrc, "SHA1withRSA", 30, distinguishedNameValues);
    }
}

