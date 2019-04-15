/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.DataInputStream
 *  java.io.File
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.IllegalAccessException
 *  java.lang.IllegalArgumentException
 *  java.lang.IllegalStateException
 *  java.lang.InstantiationException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 *  java.net.URL
 *  java.security.AlgorithmParameters
 *  java.security.DigestOutputStream
 *  java.security.GeneralSecurityException
 *  java.security.Key
 *  java.security.KeyFactory
 *  java.security.KeyStore
 *  java.security.MessageDigest
 *  java.security.NoSuchAlgorithmException
 *  java.security.PrivateKey
 *  java.security.Provider
 *  java.security.Security
 *  java.security.cert.Certificate
 *  java.security.cert.CertificateFactory
 *  java.security.cert.X509Certificate
 *  java.security.spec.InvalidKeySpecException
 *  java.security.spec.KeySpec
 *  java.security.spec.PKCS8EncodedKeySpec
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Collections
 *  java.util.Date
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Observable
 *  java.util.Observer
 *  java.util.Set
 *  java.util.TreeMap
 *  java.util.jar.Attributes
 *  java.util.jar.Manifest
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  javax.crypto.Cipher
 *  javax.crypto.EncryptedPrivateKeyInfo
 *  javax.crypto.SecretKey
 *  javax.crypto.SecretKeyFactory
 *  javax.crypto.spec.PBEKeySpec
 *  kellinwood.security.zipsigner.DefaultResourceAdapter
 *  kellinwood.zipio.ZioEntry
 *  kellinwood.zipio.ZipInput
 *  kellinwood.zipio.ZipOutput
 */
package kellinwood.security.zipsigner;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.AlgorithmParameters;
import java.security.DigestOutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.LoggerManager;
import kellinwood.security.zipsigner.AutoKeyException;
import kellinwood.security.zipsigner.Base64;
import kellinwood.security.zipsigner.DefaultResourceAdapter;
import kellinwood.security.zipsigner.HexDumpEncoder;
import kellinwood.security.zipsigner.KeySet;
import kellinwood.security.zipsigner.ProgressHelper;
import kellinwood.security.zipsigner.ProgressListener;
import kellinwood.security.zipsigner.ResourceAdapter;
import kellinwood.security.zipsigner.ZipSignature;
import kellinwood.zipio.ZioEntry;
import kellinwood.zipio.ZipInput;
import kellinwood.zipio.ZipOutput;

public class ZipSigner {
    private static final String CERT_RSA_NAME = "META-INF/CERT.RSA";
    private static final String CERT_SF_NAME = "META-INF/CERT.SF";
    public static final String KEY_NONE = "none";
    public static final String KEY_TESTKEY = "testkey";
    public static final String MODE_AUTO = "auto";
    public static final String MODE_AUTO_NONE = "auto-none";
    public static final String MODE_AUTO_TESTKEY = "auto-testkey";
    public static final String[] SUPPORTED_KEY_MODES;
    static LoggerInterface log;
    private static Pattern stripPattern;
    Map<String, String> autoKeyDetect = new HashMap();
    AutoKeyObservable autoKeyObservable = new AutoKeyObservable();
    private boolean canceled = false;
    KeySet keySet = null;
    String keymode = "testkey";
    Map<String, KeySet> loadedKeys = new HashMap();
    private ProgressHelper progressHelper = new ProgressHelper();
    private ResourceAdapter resourceAdapter = new DefaultResourceAdapter();

    static {
        log = null;
        stripPattern = Pattern.compile((String)"^META-INF/(.*)[.](SF|RSA|DSA)$");
        SUPPORTED_KEY_MODES = new String[]{MODE_AUTO_TESTKEY, MODE_AUTO, MODE_AUTO_NONE, "media", "platform", "shared", KEY_TESTKEY, KEY_NONE};
    }

    public ZipSigner() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        this.autoKeyDetect.put((Object)"aa9852bc5a53272ac8031d49b65e4b0e", (Object)"media");
        this.autoKeyDetect.put((Object)"e60418c4b638f20d0721e115674ca11f", (Object)"platform");
        this.autoKeyDetect.put((Object)"3e24e49741b60c215c010dc6048fca7d", (Object)"shared");
        this.autoKeyDetect.put((Object)"dab2cead827ef5313f28e22b6fa8479f", (Object)KEY_TESTKEY);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Manifest addDigestsToManifest(Map<String, ZioEntry> map) throws IOException, GeneralSecurityException {
        ZioEntry zioEntry = (ZioEntry)map.get((Object)"META-INF/MANIFEST.MF");
        Manifest manifest = null;
        if (zioEntry != null) {
            manifest = new Manifest();
            manifest.read(zioEntry.getInputStream());
        }
        Manifest manifest2 = new Manifest();
        Attributes attributes = manifest2.getMainAttributes();
        if (manifest != null) {
            attributes.putAll((Map)manifest.getMainAttributes());
        } else {
            attributes.putValue("Manifest-Version", "1.0");
            attributes.putValue("Created-By", "1.0 (Android SignApk)");
        }
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
        byte[] arrby = new byte[512];
        TreeMap treeMap = new TreeMap();
        treeMap.putAll(map);
        boolean bl = ZipSigner.getLogger().isDebugEnabled();
        if (bl) {
            ZipSigner.getLogger().debug("Manifest entries:");
        }
        Iterator iterator = treeMap.values().iterator();
        do {
            int n;
            ZioEntry zioEntry2;
            block14 : {
                block13 : {
                    if (!iterator.hasNext()) break block13;
                    zioEntry2 = (ZioEntry)iterator.next();
                    if (!this.canceled) break block14;
                }
                return manifest2;
            }
            String string2 = zioEntry2.getName();
            if (bl) {
                ZipSigner.getLogger().debug(string2);
            }
            if (zioEntry2.isDirectory() || string2.equals((Object)"META-INF/MANIFEST.MF") || string2.equals((Object)CERT_SF_NAME) || string2.equals((Object)CERT_RSA_NAME) || stripPattern != null && stripPattern.matcher((CharSequence)string2).matches()) continue;
            this.progressHelper.progress(0, this.resourceAdapter.getString(ResourceAdapter.Item.GENERATING_MANIFEST, new Object[0]));
            InputStream inputStream = zioEntry2.getInputStream();
            while ((n = inputStream.read(arrby)) > 0) {
                messageDigest.update(arrby, 0, n);
            }
            Attributes attributes2 = null;
            if (manifest != null) {
                Attributes attributes3 = manifest.getAttributes(string2);
                attributes2 = null;
                if (attributes3 != null) {
                    attributes2 = new Attributes(attributes3);
                }
            }
            if (attributes2 == null) {
                attributes2 = new Attributes();
            }
            attributes2.putValue("SHA1-Digest", Base64.encode(messageDigest.digest()));
            manifest2.getEntries().put((Object)string2, (Object)attributes2);
        } while (true);
    }

    private void copyFiles(Map<String, ZioEntry> map, ZipOutput zipOutput) throws IOException {
        int n = 1;
        Iterator iterator = map.values().iterator();
        do {
            ZioEntry zioEntry;
            block4 : {
                block3 : {
                    if (!iterator.hasNext()) break block3;
                    zioEntry = (ZioEntry)iterator.next();
                    if (!this.canceled) break block4;
                }
                return;
            }
            ProgressHelper progressHelper = this.progressHelper;
            ResourceAdapter resourceAdapter = this.resourceAdapter;
            ResourceAdapter.Item item = ResourceAdapter.Item.COPYING_ZIP_ENTRY;
            Object[] arrobject = new Object[]{n, map.size()};
            progressHelper.progress(0, resourceAdapter.getString(item, arrobject));
            ++n;
            zipOutput.write(zioEntry);
        } while (true);
    }

    private void copyFiles(Manifest manifest, Map<String, ZioEntry> map, ZipOutput zipOutput, long l) throws IOException {
        ArrayList arrayList = new ArrayList((Collection)manifest.getEntries().keySet());
        Collections.sort((List)arrayList);
        int n = 1;
        Iterator iterator = arrayList.iterator();
        do {
            String string2;
            block4 : {
                block3 : {
                    if (!iterator.hasNext()) break block3;
                    string2 = (String)iterator.next();
                    if (!this.canceled) break block4;
                }
                return;
            }
            ProgressHelper progressHelper = this.progressHelper;
            ResourceAdapter resourceAdapter = this.resourceAdapter;
            ResourceAdapter.Item item = ResourceAdapter.Item.COPYING_ZIP_ENTRY;
            Object[] arrobject = new Object[]{n, arrayList.size()};
            progressHelper.progress(0, resourceAdapter.getString(item, arrobject));
            ++n;
            ZioEntry zioEntry = (ZioEntry)map.get((Object)string2);
            zioEntry.setTime(l);
            zipOutput.write(zioEntry);
        } while (true);
    }

    private KeySpec decryptPrivateKey(byte[] arrby, String string2) throws GeneralSecurityException {
        EncryptedPrivateKeyInfo encryptedPrivateKeyInfo;
        try {
            encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(arrby);
        }
        catch (IOException iOException) {
            return null;
        }
        char[] arrc = string2.toCharArray();
        SecretKey secretKey = SecretKeyFactory.getInstance((String)encryptedPrivateKeyInfo.getAlgName()).generateSecret((KeySpec)new PBEKeySpec(arrc));
        Cipher cipher = Cipher.getInstance((String)encryptedPrivateKeyInfo.getAlgName());
        cipher.init(2, (Key)secretKey, encryptedPrivateKeyInfo.getAlgParameters());
        try {
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = encryptedPrivateKeyInfo.getKeySpec(cipher);
            return pKCS8EncodedKeySpec;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            ZipSigner.getLogger().error("signapk: Password for private key may be bad.");
            throw invalidKeySpecException;
        }
    }

    private void generateSignatureFile(Manifest manifest, OutputStream outputStream) throws IOException, GeneralSecurityException {
        outputStream.write("Signature-Version: 1.0\r\n".getBytes());
        outputStream.write("Created-By: 1.0 (Android SignApk)\r\n".getBytes());
        MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
        PrintStream printStream = new PrintStream((OutputStream)new DigestOutputStream((OutputStream)new ByteArrayOutputStream(), messageDigest), true, "UTF-8");
        manifest.write((OutputStream)printStream);
        printStream.flush();
        outputStream.write(("SHA1-Digest-Manifest: " + Base64.encode(messageDigest.digest()) + "\r\n\r\n").getBytes());
        Iterator iterator = manifest.getEntries().entrySet().iterator();
        do {
            Map.Entry entry;
            block5 : {
                block4 : {
                    if (!iterator.hasNext()) break block4;
                    entry = (Map.Entry)iterator.next();
                    if (!this.canceled) break block5;
                }
                return;
            }
            this.progressHelper.progress(0, this.resourceAdapter.getString(ResourceAdapter.Item.GENERATING_SIGNATURE_FILE, new Object[0]));
            String string2 = "Name: " + (String)entry.getKey() + "\r\n";
            printStream.print(string2);
            for (Map.Entry entry2 : ((Attributes)entry.getValue()).entrySet()) {
                printStream.print(entry2.getKey() + ": " + entry2.getValue() + "\r\n");
            }
            printStream.print("\r\n");
            printStream.flush();
            outputStream.write(string2.getBytes());
            outputStream.write(("SHA1-Digest: " + Base64.encode(messageDigest.digest()) + "\r\n\r\n").getBytes());
        } while (true);
    }

    public static LoggerInterface getLogger() {
        if (log == null) {
            log = LoggerManager.getLogger(ZipSigner.class.getName());
        }
        return log;
    }

    public static String[] getSupportedKeyModes() {
        return SUPPORTED_KEY_MODES;
    }

    private void writeSignatureBlock(KeySet keySet, byte[] arrby, OutputStream outputStream) throws IOException, GeneralSecurityException {
        if (keySet.getSigBlockTemplate() != null) {
            ZipSignature zipSignature = new ZipSignature();
            zipSignature.initSign(keySet.getPrivateKey());
            zipSignature.update(arrby);
            byte[] arrby2 = zipSignature.sign();
            outputStream.write(keySet.getSigBlockTemplate());
            outputStream.write(arrby2);
            if (ZipSigner.getLogger().isDebugEnabled()) {
                MessageDigest messageDigest = MessageDigest.getInstance((String)"SHA1");
                messageDigest.update(arrby);
                byte[] arrby3 = messageDigest.digest();
                ZipSigner.getLogger().debug("Sig File SHA1: \n" + HexDumpEncoder.encode(arrby3));
                ZipSigner.getLogger().debug("Signature: \n" + HexDumpEncoder.encode(arrby2));
                Cipher cipher = Cipher.getInstance((String)"RSA/ECB/PKCS1Padding");
                cipher.init(2, (Certificate)keySet.getPublicKey());
                byte[] arrby4 = cipher.doFinal(arrby2);
                ZipSigner.getLogger().debug("Signature Decrypted: \n" + HexDumpEncoder.encode(arrby4));
            }
            return;
        }
        try {
            Class class_ = Class.forName((String)"kellinwood.security.zipsigner.optional.SignatureBlockGenerator");
            Class[] arrclass = new Class[]{KeySet.class, new byte[1].getClass()};
            outputStream.write((byte[])class_.getMethod("generate", arrclass).invoke(null, new Object[]{keySet, arrby}));
            return;
        }
        catch (Exception exception) {
            throw new RuntimeException(exception.getMessage(), (Throwable)exception);
        }
    }

    public void addAutoKeyObserver(Observer observer) {
        this.autoKeyObservable.addObserver(observer);
    }

    public void addProgressListener(ProgressListener progressListener) {
        this.progressHelper.addProgressListener(progressListener);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected String autoDetectKey(String var1, Map<String, ZioEntry> var2_2) throws NoSuchAlgorithmException, IOException {
        block6 : {
            var3_3 = ZipSigner.getLogger().isDebugEnabled();
            if (!var1.startsWith("auto")) {
                return var1;
            }
            var4_4 = null;
            var5_5 = var2_2.entrySet().iterator();
            do lbl-1000: // 3 sources:
            {
                block8 : {
                    block7 : {
                        if (!var5_5.hasNext()) break block7;
                        var6_10 = (Map.Entry)var5_5.next();
                        var7_16 = (String)var6_10.getKey();
                        if (!var7_16.startsWith("META-INF/") || !var7_16.endsWith(".RSA")) ** GOTO lbl-1000
                        var8_6 = MessageDigest.getInstance((String)"MD5");
                        var9_11 = ((ZioEntry)var6_10.getValue()).getData();
                        if (var9_11.length >= 1458) break block8;
                    }
                    if (var1.equals((Object)"auto-testkey")) {
                        if (var3_3 == false) return "testkey";
                        ZipSigner.getLogger().debug("Falling back to key=" + var4_4);
                        return "testkey";
                    }
                    break block6;
                }
                var8_6.update(var9_11, 0, 1458);
                var10_14 = var8_6.digest();
                var11_7 = new StringBuilder();
                for (byte var15_9 : var10_14) {
                    var16_15 = new Object[]{var15_9};
                    var11_7.append(String.format((String)"%02x", (Object[])var16_15));
                }
                var14_13 = var11_7.toString();
                var4_4 = (String)this.autoKeyDetect.get((Object)var14_13);
                if (!var3_3) continue;
                if (var4_4 != null) {
                    ZipSigner.getLogger().debug(String.format((String)"Auto-determined key=%s using md5=%s", (Object[])new Object[]{var4_4, var14_13}));
                    continue;
                }
                ZipSigner.getLogger().debug(String.format((String)"Auto key determination failed for md5=%s", (Object[])new Object[]{var14_13}));
            } while (var4_4 == null);
            return var4_4;
        }
        if (var1.equals((Object)"auto-none") == false) return null;
        if (var3_3 == false) return "none";
        ZipSigner.getLogger().debug("Unable to determine key, returning: none");
        return "none";
    }

    public void cancel() {
        this.canceled = true;
    }

    public KeySet getKeySet() {
        return this.keySet;
    }

    public String getKeymode() {
        return this.keymode;
    }

    public ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    public boolean isCanceled() {
        return this.canceled;
    }

    public void issueLoadingCertAndKeysProgressEvent() {
        this.progressHelper.progress(1, this.resourceAdapter.getString(ResourceAdapter.Item.LOADING_CERTIFICATE_AND_KEY, new Object[0]));
    }

    /*
     * Enabled aggressive block sorting
     */
    public void loadKeys(String string2) throws IOException, GeneralSecurityException {
        URL uRL;
        block3 : {
            block2 : {
                this.keySet = (KeySet)this.loadedKeys.get((Object)string2);
                if (this.keySet != null) break block2;
                this.keySet = new KeySet();
                this.keySet.setName(string2);
                this.loadedKeys.put((Object)string2, (Object)this.keySet);
                if (KEY_NONE.equals((Object)string2)) break block2;
                this.issueLoadingCertAndKeysProgressEvent();
                URL uRL2 = this.getClass().getResource("/keys/" + string2 + ".pk8");
                this.keySet.setPrivateKey(this.readPrivateKey(uRL2, null));
                URL uRL3 = this.getClass().getResource("/keys/" + string2 + ".x509.pem");
                this.keySet.setPublicKey(this.readPublicKey(uRL3));
                uRL = this.getClass().getResource("/keys/" + string2 + ".sbt");
                if (uRL != null) break block3;
            }
            return;
        }
        this.keySet.setSigBlockTemplate(this.readContentAsBytes(uRL));
    }

    public void loadProvider(String string2) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Security.insertProviderAt((Provider)((Provider)Class.forName((String)string2).newInstance()), (int)1);
    }

    public byte[] readContentAsBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[2048];
        int n = inputStream.read(arrby);
        while (n != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
            n = inputStream.read(arrby);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] readContentAsBytes(URL uRL) throws IOException {
        return this.readContentAsBytes(uRL.openStream());
    }

    public PrivateKey readPrivateKey(URL uRL, String string2) throws IOException, GeneralSecurityException {
        DataInputStream dataInputStream = new DataInputStream(uRL.openStream());
        try {
            byte[] arrby = this.readContentAsBytes((InputStream)dataInputStream);
            KeySpec keySpec = ZipSigner.super.decryptPrivateKey(arrby, string2);
            if (keySpec == null) {
                keySpec = new PKCS8EncodedKeySpec(arrby);
            }
            try {
                PrivateKey privateKey = KeyFactory.getInstance((String)"RSA").generatePrivate(keySpec);
                return privateKey;
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
                PrivateKey privateKey = KeyFactory.getInstance((String)"DSA").generatePrivate(keySpec);
                dataInputStream.close();
                return privateKey;
            }
        }
        finally {
            dataInputStream.close();
        }
    }

    public X509Certificate readPublicKey(URL uRL) throws IOException, GeneralSecurityException {
        InputStream inputStream = uRL.openStream();
        try {
            X509Certificate x509Certificate = (X509Certificate)CertificateFactory.getInstance((String)"X.509").generateCertificate(inputStream);
            return x509Certificate;
        }
        finally {
            inputStream.close();
        }
    }

    public void removeProgressListener(ProgressListener progressListener) {
        void var3_2 = this;
        synchronized (var3_2) {
            this.progressHelper.removeProgressListener(progressListener);
            return;
        }
    }

    public void resetCanceled() {
        this.canceled = false;
    }

    public void setKeymode(String string2) throws IOException, GeneralSecurityException {
        if (ZipSigner.getLogger().isDebugEnabled()) {
            ZipSigner.getLogger().debug("setKeymode: " + string2);
        }
        this.keymode = string2;
        if (this.keymode.startsWith(MODE_AUTO)) {
            this.keySet = null;
            return;
        }
        this.progressHelper.initProgress();
        this.loadKeys(this.keymode);
    }

    public void setKeys(String string2, X509Certificate x509Certificate, PrivateKey privateKey, String string3, byte[] arrby) {
        this.keySet = new KeySet(string2, x509Certificate, privateKey, string3, arrby);
    }

    public void setKeys(String string2, X509Certificate x509Certificate, PrivateKey privateKey, byte[] arrby) {
        this.keySet = new KeySet(string2, x509Certificate, privateKey, arrby);
    }

    public void setResourceAdapter(ResourceAdapter resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    public void signZip(String string2, String string3) throws IOException, GeneralSecurityException {
        if (new File(string2).getCanonicalFile().equals((Object)new File(string3).getCanonicalFile())) {
            throw new IllegalArgumentException(this.resourceAdapter.getString(ResourceAdapter.Item.INPUT_SAME_AS_OUTPUT_ERROR, new Object[0]));
        }
        this.progressHelper.initProgress();
        this.progressHelper.progress(1, this.resourceAdapter.getString(ResourceAdapter.Item.PARSING_CENTRAL_DIRECTORY, new Object[0]));
        this.signZip((Map<String, ZioEntry>)ZipInput.read((String)string2).getEntries(), (OutputStream)new FileOutputStream(string3), string3);
    }

    public void signZip(URL uRL, String string2, String string3, String string4, String string5, String string6, String string7) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException, GeneralSecurityException {
        this.signZip(uRL, string2, string3.toCharArray(), string4, string5.toCharArray(), "SHA1withRSA", string6, string7);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void signZip(URL var1_5, String var2_8, char[] var3_3, String var4_4, char[] var5_2, String var6_7, String var7_6, String var8) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException, GeneralSecurityException {
        var9_9 = null;
        if (var2_8 != null) ** GOTO lbl5
        try {
            var2_8 = KeyStore.getDefaultType();
lbl5: // 2 sources:
            var11_10 = KeyStore.getInstance((String)var2_8);
            var9_9 = var1_5.openStream();
            var11_10.load(var9_9, var3_3);
            this.setKeys("custom", (X509Certificate)var11_10.getCertificate(var4_4), (PrivateKey)var11_10.getKey(var4_4, var5_2), var6_7, null);
            this.signZip(var7_6, var8);
            return;
        }
        finally {
            if (var9_9 != null) {
                var9_9.close();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void signZip(Map<String, ZioEntry> var1, OutputStream var2_3, String var3_2) throws IOException, GeneralSecurityException {
        block32 : {
            var4_4 = ZipSigner.getLogger().isDebugEnabled();
            this.progressHelper.initProgress();
            if (this.keySet == null) {
                if (!this.keymode.startsWith("auto")) {
                    throw new IllegalStateException("No keys configured for signing the file!");
                }
                var41_5 = this.autoDetectKey(this.keymode, var1);
                if (var41_5 == null) {
                    var42_6 = this.resourceAdapter;
                    var43_7 = ResourceAdapter.Item.AUTO_KEY_SELECTION_ERROR;
                    var44_8 = new Object[1];
                    var45_9 = new File(var3_2);
                    var44_8[0] = var45_9.getName();
                    throw new AutoKeyException(var42_6.getString(var43_7, var44_8));
                }
                this.autoKeyObservable.notifyObservers(var41_5);
                this.loadKeys(var41_5);
            }
            var5_10 = new ZipOutput(var2_3);
            if (!"none".equals((Object)this.keySet.getName())) break block32;
            this.progressHelper.setProgressTotalItems(var1.size());
            this.progressHelper.setProgressCurrentItem(0);
            ZipSigner.super.copyFiles(var1, var5_10);
            var5_10.close();
            if (!this.canceled || var3_2 == null) ** GOTO lbl27
            try {
                new File(var3_2).delete();
lbl27: // 7 sources:
                do {
                    return;
                    break;
                } while (true);
            }
            catch (Throwable var39_11) {
                ZipSigner.getLogger().warning(var39_11.getClass().getName() + ":" + var39_11.getMessage());
                return;
            }
        }
        var9_12 = 0;
        try {
            var10_13 = var1.values().iterator();
lbl36: // 2 sources:
            do {
                if (!var10_13.hasNext()) ** break block33
                var37_15 = (ZioEntry)var10_13.next();
                var38_14 = var37_15.getName();
                if (var37_15.isDirectory() || var38_14.equals((Object)"META-INF/MANIFEST.MF") || var38_14.equals((Object)"META-INF/CERT.SF") || var38_14.equals((Object)"META-INF/CERT.RSA") || ZipSigner.stripPattern != null && ZipSigner.stripPattern.matcher((CharSequence)var38_14).matches()) continue;
                break;
            } while (true);
        }
        catch (Throwable var6_38) {
            ** continue;
        }
        {
            block37 : {
                block36 : {
                    block35 : {
                        block34 : {
                            
                            var11_16 = var9_12 + 1;
                            this.progressHelper.setProgressTotalItems(var11_16);
                            this.progressHelper.setProgressCurrentItem(0);
                            var12_17 = 3600000L + this.keySet.getPublicKey().getNotBefore().getTime();
                            var14_18 = ZipSigner.super.addDigestsToManifest(var1);
                            var15_19 = this.canceled;
                            if (!var15_19) break block34;
                            var5_10.close();
                            if (!this.canceled || var3_2 == null) ** GOTO lbl27
                            try {
                                new File(var3_2).delete();
                                return;
                            }
                            catch (Throwable var35_20) {
                                ZipSigner.getLogger().warning(var35_20.getClass().getName() + ":" + var35_20.getMessage());
                                return;
                            }
                        }
                        var16_21 = new ZioEntry("META-INF/MANIFEST.MF");
                        var16_21.setTime(var12_17);
                        var14_18.write(var16_21.getOutputStream());
                        var5_10.write(var16_21);
                        var17_22 = new ZioEntry("META-INF/CERT.SF");
                        var17_22.setTime(var12_17);
                        var18_23 = new ByteArrayOutputStream();
                        ZipSigner.super.generateSignatureFile(var14_18, (OutputStream)var18_23);
                        var19_24 = this.canceled;
                        if (!var19_24) break block35;
                        var5_10.close();
                        if (!this.canceled || var3_2 == null) ** GOTO lbl27
                        try {
                            new File(var3_2).delete();
                            return;
                        }
                        catch (Throwable var33_25) {
                            ZipSigner.getLogger().warning(var33_25.getClass().getName() + ":" + var33_25.getMessage());
                            return;
                        }
                    }
                    var20_26 = var18_23.toByteArray();
                    if (!var4_4) ** GOTO lbl90
                    var21_27 = ZipSigner.getLogger();
                    var22_28 = new StringBuilder().append("Signature File: \n");
                    var23_29 = new String(var20_26);
                    var21_27.debug(var22_28.append(var23_29).append("\n").append(HexDumpEncoder.encode(var20_26)).toString());
lbl90: // 2 sources:
                    var17_22.getOutputStream().write(var20_26);
                    var5_10.write(var17_22);
                    this.progressHelper.progress(0, this.resourceAdapter.getString(ResourceAdapter.Item.GENERATING_SIGNATURE_BLOCK, new Object[0]));
                    var24_30 = new ZioEntry("META-INF/CERT.RSA");
                    var24_30.setTime(var12_17);
                    ZipSigner.super.writeSignatureBlock(this.keySet, var20_26, var24_30.getOutputStream());
                    var5_10.write(var24_30);
                    var25_31 = this.canceled;
                    if (!var25_31) break block36;
                    var5_10.close();
                    if (!this.canceled || var3_2 == null) ** GOTO lbl27
                    try {
                        new File(var3_2).delete();
                        return;
                    }
                    catch (Throwable var31_32) {
                        ZipSigner.getLogger().warning(var31_32.getClass().getName() + ":" + var31_32.getMessage());
                        return;
                    }
                }
                ZipSigner.super.copyFiles(var14_18, var1, var5_10, var12_17);
                var26_33 = this.canceled;
                if (!var26_33) break block37;
                var5_10.close();
                if (!this.canceled || var3_2 == null) ** GOTO lbl27
                try {
                    new File(var3_2).delete();
                    return;
                }
                catch (Throwable var29_34) {
                    ZipSigner.getLogger().warning(var29_34.getClass().getName() + ":" + var29_34.getMessage());
                    return;
                }
            }
            var5_10.close();
            if (!this.canceled || var3_2 == null) ** continue;
            try {
                new File(var3_2).delete();
                return;
            }
            catch (Throwable var27_35) {
                ZipSigner.getLogger().warning(var27_35.getClass().getName() + ":" + var27_35.getMessage());
                return;
            }
        }
        catch (Throwable var6_36) {
            var5_10 = null;
lbl131: // 2 sources:
            do {
                var5_10.close();
                if (this.canceled && var3_2 != null) {
                    new File(var3_2).delete();
                }
                do {
                    throw var6_37;
                    break;
                } while (true);
                catch (Throwable var7_39) {
                    ZipSigner.getLogger().warning(var7_39.getClass().getName() + ":" + var7_39.getMessage());
                    throw var6_37;
                }
                break;
            } while (true);
        }
        var9_12 += 3;
        ** while (true)
    }

    public void signZip(Map<String, ZioEntry> map, String string2) throws IOException, GeneralSecurityException {
        this.progressHelper.initProgress();
        this.signZip(map, (OutputStream)new FileOutputStream(string2), string2);
    }

    public static class AutoKeyObservable
    extends Observable {
        public void notifyObservers(Object object) {
            super.setChanged();
            super.notifyObservers(object);
        }
    }

}

