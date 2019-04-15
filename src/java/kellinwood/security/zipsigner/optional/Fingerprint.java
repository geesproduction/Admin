/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.Throwable
 *  java.security.MessageDigest
 *  org.spongycastle.util.encoders.HexTranslator
 */
package kellinwood.security.zipsigner.optional;

import java.security.MessageDigest;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.LoggerManager;
import kellinwood.security.zipsigner.Base64;
import org.spongycastle.util.encoders.HexTranslator;

public class Fingerprint {
    static LoggerInterface logger = LoggerManager.getLogger(Fingerprint.class.getName());

    public static String base64Fingerprint(String string2, byte[] arrby) {
        byte[] arrby2;
        block3 : {
            try {
                arrby2 = Fingerprint.calcDigest(string2, arrby);
                if (arrby2 != null) break block3;
                return null;
            }
            catch (Exception exception) {
                logger.error(exception.getMessage(), exception);
                return null;
            }
        }
        String string3 = Base64.encode(arrby2);
        return string3;
    }

    static byte[] calcDigest(String string2, byte[] arrby) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance((String)string2);
            messageDigest.update(arrby);
            byte[] arrby2 = messageDigest.digest();
            return arrby2;
        }
        catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
            return null;
        }
    }

    public static String hexFingerprint(String string2, byte[] arrby) {
        byte[] arrby2;
        block7 : {
            arrby2 = Fingerprint.calcDigest(string2, arrby);
            if (arrby2 != null) break block7;
            return null;
        }
        HexTranslator hexTranslator = new HexTranslator();
        byte[] arrby3 = new byte[2 * arrby2.length];
        hexTranslator.encode(arrby2, 0, arrby2.length, arrby3, 0);
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        do {
            block8 : {
                try {
                    if (n < arrby3.length) {
                        stringBuilder.append((char)arrby3[n]);
                        stringBuilder.append((char)arrby3[n + 1]);
                        if (n != -2 + arrby3.length) {
                            stringBuilder.append(':');
                        }
                        break block8;
                    }
                    String string3 = stringBuilder.toString().toUpperCase();
                    return string3;
                }
                catch (Exception exception) {
                    logger.error(exception.getMessage(), exception);
                    return null;
                }
            }
            n += 2;
        } while (true);
    }
}

