/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayInputStream
 *  java.io.DataInputStream
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.Exception
 *  java.lang.NullPointerException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.security.DigestInputStream
 *  java.security.DigestOutputStream
 *  java.security.Key
 *  java.security.KeyFactory
 *  java.security.KeyStoreException
 *  java.security.KeyStoreSpi
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.security.PrivateKey
 *  java.security.PublicKey
 *  java.security.SecureRandom
 *  java.security.UnrecoverableKeyException
 *  java.security.cert.Certificate
 *  java.security.cert.CertificateException
 *  java.security.cert.CertificateFactory
 *  java.security.spec.InvalidKeySpecException
 *  java.security.spec.KeySpec
 *  java.security.spec.PKCS8EncodedKeySpec
 *  java.util.Date
 *  java.util.Enumeration
 *  java.util.HashMap
 *  java.util.Set
 *  java.util.Vector
 *  javax.crypto.EncryptedPrivateKeyInfo
 *  javax.crypto.spec.SecretKeySpec
 */
package kellinwood.security.zipsigner.optional;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.spec.SecretKeySpec;

public class JKS
extends KeyStoreSpi {
    private static final int MAGIC = -17957139;
    private static final int PRIVATE_KEY = 1;
    private static final int TRUSTED_CERT = 2;
    private final Vector aliases = new Vector();
    private final HashMap certChains = new HashMap();
    private final HashMap dates = new HashMap();
    private final HashMap privateKeys = new HashMap();
    private final HashMap trustedCerts = new HashMap();

    private static byte[] charsToBytes(char[] arrc) {
        byte[] arrby = new byte[2 * arrc.length];
        int n = 0;
        for (int i = 0; i < arrc.length; ++i) {
            int n2 = n + 1;
            arrby[n] = (byte)(arrc[i] >>> 8);
            n = n2 + 1;
            arrby[n2] = (byte)arrc[i];
        }
        return arrby;
    }

    private static byte[] decryptKey(byte[] arrby, byte[] arrby2) throws UnrecoverableKeyException {
        byte[] arrby3 = new EncryptedPrivateKeyInfo(arrby).getEncryptedData();
        byte[] arrby4 = new byte[20];
        System.arraycopy((Object)arrby3, (int)0, (Object)arrby4, (int)0, (int)20);
        byte[] arrby5 = new byte[20];
        System.arraycopy((Object)arrby3, (int)(-20 + arrby3.length), (Object)arrby5, (int)0, (int)20);
        byte[] arrby6 = new byte[-40 + arrby3.length];
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
        int n = 0;
        block5 : do {
            if (n >= arrby6.length) break;
            messageDigest.reset();
            messageDigest.update(arrby2);
            messageDigest.update(arrby4);
            messageDigest.digest(arrby4, 0, arrby4.length);
            int n2 = 0;
            do {
                if (n2 >= arrby4.length || n >= arrby6.length) continue block5;
                arrby6[n] = (byte)(arrby4[n2] ^ arrby3[n + 20]);
                ++n;
                ++n2;
            } while (true);
            break;
        } while (true);
        try {
            messageDigest.reset();
            messageDigest.update(arrby2);
            messageDigest.update(arrby6);
            if (!MessageDigest.isEqual((byte[])arrby5, (byte[])messageDigest.digest())) {
                throw new UnrecoverableKeyException("checksum mismatch");
            }
        }
        catch (Exception exception) {
            throw new UnrecoverableKeyException(exception.getMessage());
        }
        return arrby6;
    }

    private static byte[] encryptKey(Key key, byte[] arrby) throws KeyStoreException {
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
        SecureRandom.getInstance((String)"SHA1PRNG");
        byte[] arrby2 = key.getEncoded();
        byte[] arrby3 = new byte[40 + arrby2.length];
        byte[] arrby4 = SecureRandom.getSeed((int)20);
        System.arraycopy((Object)arrby4, (int)0, (Object)arrby3, (int)0, (int)20);
        int n = 0;
        block5 : do {
            if (n >= arrby2.length) break;
            messageDigest.reset();
            messageDigest.update(arrby);
            messageDigest.update(arrby4);
            messageDigest.digest(arrby4, 0, arrby4.length);
            int n2 = 0;
            do {
                if (n2 >= arrby4.length || n >= arrby2.length) continue block5;
                arrby3[n + 20] = (byte)(arrby4[n2] ^ arrby2[n]);
                ++n;
                ++n2;
            } while (true);
            break;
        } while (true);
        try {
            messageDigest.reset();
            messageDigest.update(arrby);
            messageDigest.update(arrby2);
            messageDigest.digest(arrby3, -20 + arrby3.length, 20);
            byte[] arrby5 = new EncryptedPrivateKeyInfo("1.3.6.1.4.1.42.2.17.1.1", arrby3).getEncoded();
            return arrby5;
        }
        catch (Exception exception) {
            throw new KeyStoreException(exception.getMessage());
        }
    }

    private static Certificate readCert(DataInputStream dataInputStream) throws IOException, CertificateException, NoSuchAlgorithmException {
        String string2 = dataInputStream.readUTF();
        byte[] arrby = new byte[dataInputStream.readInt()];
        dataInputStream.read(arrby);
        return CertificateFactory.getInstance((String)string2).generateCertificate((InputStream)new ByteArrayInputStream(arrby));
    }

    private static void writeCert(DataOutputStream dataOutputStream, Certificate certificate) throws IOException, CertificateException {
        dataOutputStream.writeUTF(certificate.getType());
        byte[] arrby = certificate.getEncoded();
        dataOutputStream.writeInt(arrby.length);
        dataOutputStream.write(arrby);
    }

    public Enumeration engineAliases() {
        return this.aliases.elements();
    }

    public boolean engineContainsAlias(String string2) {
        String string3 = string2.toLowerCase();
        return this.aliases.contains((Object)string3);
    }

    public void engineDeleteEntry(String string2) throws KeyStoreException {
        String string3 = string2.toLowerCase();
        this.aliases.remove((Object)string3);
    }

    public Certificate engineGetCertificate(String string2) {
        Certificate[] arrcertificate;
        String string3 = string2.toLowerCase();
        if (this.engineIsKeyEntry(string3) && (arrcertificate = (Certificate[])this.certChains.get((Object)string3)) != null && arrcertificate.length > 0) {
            return arrcertificate[0];
        }
        return (Certificate)this.trustedCerts.get((Object)string3);
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        for (String string2 : this.trustedCerts.keySet()) {
            if (!certificate.equals(this.trustedCerts.get((Object)string2))) continue;
            return string2;
        }
        return null;
    }

    public Certificate[] engineGetCertificateChain(String string2) {
        String string3 = string2.toLowerCase();
        return (Certificate[])this.certChains.get((Object)string3);
    }

    public Date engineGetCreationDate(String string2) {
        String string3 = string2.toLowerCase();
        return (Date)this.dates.get((Object)string3);
    }

    public Key engineGetKey(String string2, char[] arrc) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        String string3 = string2.toLowerCase();
        if (!this.privateKeys.containsKey((Object)string3)) {
            return null;
        }
        byte[] arrby = JKS.decryptKey((byte[])this.privateKeys.get((Object)string3), JKS.charsToBytes(arrc));
        Certificate[] arrcertificate = this.engineGetCertificateChain(string3);
        if (arrcertificate.length > 0) {
            try {
                PrivateKey privateKey = KeyFactory.getInstance((String)arrcertificate[0].getPublicKey().getAlgorithm()).generatePrivate((KeySpec)new PKCS8EncodedKeySpec(arrby));
                return privateKey;
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
                throw new UnrecoverableKeyException(invalidKeySpecException.getMessage());
            }
        }
        return new SecretKeySpec(arrby, string3);
    }

    public boolean engineIsCertificateEntry(String string2) {
        String string3 = string2.toLowerCase();
        return this.trustedCerts.containsKey((Object)string3);
    }

    public boolean engineIsKeyEntry(String string2) {
        String string3 = string2.toLowerCase();
        return this.privateKeys.containsKey((Object)string3);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void engineLoad(InputStream inputStream, char[] arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        block13 : {
            block12 : {
                MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA");
                if (arrc != null) {
                    messageDigest.update(JKS.charsToBytes(arrc));
                }
                messageDigest.update("Mighty Aphrodite".getBytes("UTF-8"));
                this.aliases.clear();
                this.trustedCerts.clear();
                this.privateKeys.clear();
                this.certChains.clear();
                this.dates.clear();
                if (inputStream == null) break block12;
                DataInputStream dataInputStream = new DataInputStream((InputStream)new DigestInputStream(inputStream, messageDigest));
                if (dataInputStream.readInt() != -17957139) {
                    throw new IOException("not a JavaKeyStore");
                }
                dataInputStream.readInt();
                int n = dataInputStream.readInt();
                this.aliases.ensureCapacity(n);
                if (n < 0) {
                    throw new IOException("negative entry count");
                }
                block4 : for (int i = 0; i < n; ++i) {
                    int n2 = dataInputStream.readInt();
                    String string2 = dataInputStream.readUTF();
                    this.aliases.add((Object)string2);
                    this.dates.put((Object)string2, (Object)new Date(dataInputStream.readLong()));
                    switch (n2) {
                        default: {
                            throw new IOException("malformed key store");
                        }
                        case 1: {
                            byte[] arrby = new byte[dataInputStream.readInt()];
                            dataInputStream.read(arrby);
                            this.privateKeys.put((Object)string2, (Object)arrby);
                            int n3 = dataInputStream.readInt();
                            Certificate[] arrcertificate = new Certificate[n3];
                            for (int j = 0; j < n3; ++j) {
                                arrcertificate[j] = JKS.readCert(dataInputStream);
                            }
                            this.certChains.put((Object)string2, (Object)arrcertificate);
                            continue block4;
                        }
                        case 2: {
                            this.trustedCerts.put((Object)string2, (Object)JKS.readCert(dataInputStream));
                        }
                    }
                }
                byte[] arrby = new byte[20];
                dataInputStream.read(arrby);
                if (arrc != null && MessageDigest.isEqual((byte[])arrby, (byte[])messageDigest.digest())) break block13;
            }
            return;
        }
        throw new IOException("signature not verified");
    }

    public void engineSetCertificateEntry(String string2, Certificate certificate) throws KeyStoreException {
        String string3 = string2.toLowerCase();
        if (this.privateKeys.containsKey((Object)string3)) {
            throw new KeyStoreException("\"" + string3 + "\" is a private key entry");
        }
        if (certificate == null) {
            throw new NullPointerException();
        }
        this.trustedCerts.put((Object)string3, (Object)certificate);
        if (!this.aliases.contains((Object)string3)) {
            this.dates.put((Object)string3, (Object)new Date());
            this.aliases.add((Object)string3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void engineSetKeyEntry(String string2, Key key, char[] arrc, Certificate[] arrcertificate) throws KeyStoreException {
        String string3 = string2.toLowerCase();
        if (this.trustedCerts.containsKey((Object)string3)) {
            throw new KeyStoreException("\"" + string3 + " is a trusted certificate entry");
        }
        this.privateKeys.put((Object)string3, (Object)JKS.encryptKey(key, JKS.charsToBytes(arrc)));
        if (arrcertificate != null) {
            this.certChains.put((Object)string3, (Object)arrcertificate);
        } else {
            this.certChains.put((Object)string3, (Object)new Certificate[0]);
        }
        if (!this.aliases.contains((Object)string3)) {
            this.dates.put((Object)string3, (Object)new Date());
            this.aliases.add((Object)string3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void engineSetKeyEntry(String string2, byte[] arrby, Certificate[] arrcertificate) throws KeyStoreException {
        String string3 = string2.toLowerCase();
        if (this.trustedCerts.containsKey((Object)string3)) {
            throw new KeyStoreException("\"" + string3 + "\" is a trusted certificate entry");
        }
        try {
            new EncryptedPrivateKeyInfo(arrby);
        }
        catch (IOException iOException) {
            throw new KeyStoreException("encoded key is not an EncryptedPrivateKeyInfo");
        }
        this.privateKeys.put((Object)string3, (Object)arrby);
        if (arrcertificate != null) {
            this.certChains.put((Object)string3, (Object)arrcertificate);
        } else {
            this.certChains.put((Object)string3, (Object)new Certificate[0]);
        }
        if (!this.aliases.contains((Object)string3)) {
            this.dates.put((Object)string3, (Object)new Date());
            this.aliases.add((Object)string3);
        }
    }

    public int engineSize() {
        return this.aliases.size();
    }

    public void engineStore(OutputStream outputStream, char[] arrc) throws IOException, NoSuchAlgorithmException, CertificateException {
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
        messageDigest.update(JKS.charsToBytes(arrc));
        messageDigest.update("Mighty Aphrodite".getBytes("UTF-8"));
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)new DigestOutputStream(outputStream, messageDigest));
        dataOutputStream.writeInt(-17957139);
        dataOutputStream.writeInt(2);
        dataOutputStream.writeInt(this.aliases.size());
        Enumeration enumeration = this.aliases.elements();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            if (this.trustedCerts.containsKey((Object)string2)) {
                dataOutputStream.writeInt(2);
                dataOutputStream.writeUTF(string2);
                dataOutputStream.writeLong(((Date)this.dates.get((Object)string2)).getTime());
                JKS.writeCert(dataOutputStream, (Certificate)this.trustedCerts.get((Object)string2));
                continue;
            }
            dataOutputStream.writeInt(1);
            dataOutputStream.writeUTF(string2);
            dataOutputStream.writeLong(((Date)this.dates.get((Object)string2)).getTime());
            byte[] arrby = (byte[])this.privateKeys.get((Object)string2);
            dataOutputStream.writeInt(arrby.length);
            dataOutputStream.write(arrby);
            Certificate[] arrcertificate = (Certificate[])this.certChains.get((Object)string2);
            dataOutputStream.writeInt(arrcertificate.length);
            for (int i = 0; i < arrcertificate.length; ++i) {
                JKS.writeCert(dataOutputStream, arrcertificate[i]);
            }
        }
        dataOutputStream.write(messageDigest.digest());
    }
}

