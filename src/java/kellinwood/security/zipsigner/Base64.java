/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.OutputStream
 *  java.lang.Class
 *  java.lang.ClassNotFoundException
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.lang.reflect.Method
 */
package kellinwood.security.zipsigner;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import kellinwood.logging.LoggerInterface;
import kellinwood.logging.LoggerManager;

public class Base64 {
    static Method aDecodeMethod;
    static Method aEncodeMethod;
    static Method bDecodeMethod;
    static Object bDecoder;
    static Method bEncodeMethod;
    static Object bEncoder;
    static LoggerInterface logger;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static {
        aEncodeMethod = null;
        aDecodeMethod = null;
        bEncoder = null;
        bEncodeMethod = null;
        bDecoder = null;
        bDecodeMethod = null;
        logger = null;
        logger = LoggerManager.getLogger(Base64.class.getName());
        try {
            Class class_ = Class.forName((String)"android.util.Base64");
            Class[] arrclass = new Class[]{byte[].class, Integer.TYPE};
            aEncodeMethod = class_.getMethod("encode", arrclass);
            Class[] arrclass2 = new Class[]{byte[].class, Integer.TYPE};
            aDecodeMethod = class_.getMethod("decode", arrclass2);
            logger.info(class_.getName() + " is available.");
        }
        catch (Exception exception) {
            logger.error("Failed to initialize use of android.util.Base64", exception);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        try {
            Class class_ = Class.forName((String)"org.bouncycastle.util.encoders.Base64Encoder");
            bEncoder = class_.newInstance();
            Class[] arrclass = new Class[]{byte[].class, Integer.TYPE, Integer.TYPE, OutputStream.class};
            bEncodeMethod = class_.getMethod("encode", arrclass);
            logger.info(class_.getName() + " is available.");
            Class[] arrclass3 = new Class[]{byte[].class, Integer.TYPE, Integer.TYPE, OutputStream.class};
            bDecodeMethod = class_.getMethod("decode", arrclass3);
        }
        catch (Exception exception) {
            logger.error("Failed to initialize use of org.bouncycastle.util.encoders.Base64Encoder", exception);
        }
        catch (ClassNotFoundException classNotFoundException) {}
        if (aEncodeMethod == null && bEncodeMethod == null) {
            throw new IllegalStateException("No base64 encoder implementation is available.");
        }
    }

    public static byte[] decode(byte[] arrby) {
        try {
            if (aDecodeMethod != null) {
                Method method = aDecodeMethod;
                Object[] arrobject = new Object[]{arrby, 2};
                return (byte[])method.invoke(null, arrobject);
            }
            if (bDecodeMethod != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Method method = bDecodeMethod;
                Object object = bEncoder;
                Object[] arrobject = new Object[]{arrby, 0, arrby.length, byteArrayOutputStream};
                method.invoke(object, arrobject);
                byte[] arrby2 = byteArrayOutputStream.toByteArray();
                return arrby2;
            }
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception.getClass().getName() + ": " + exception.getMessage());
        }
        throw new IllegalStateException("No base64 encoder implementation is available.");
    }

    public static String encode(byte[] arrby) {
        try {
            if (aEncodeMethod != null) {
                Method method = aEncodeMethod;
                Object[] arrobject = new Object[]{arrby, 2};
                return new String((byte[])method.invoke(null, arrobject));
            }
            if (bEncodeMethod != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Method method = bEncodeMethod;
                Object object = bEncoder;
                Object[] arrobject = new Object[]{arrby, 0, arrby.length, byteArrayOutputStream};
                method.invoke(object, arrobject);
                String string2 = new String(byteArrayOutputStream.toByteArray());
                return string2;
            }
        }
        catch (Exception exception) {
            throw new IllegalStateException(exception.getClass().getName() + ": " + exception.getMessage());
        }
        throw new IllegalStateException("No base64 encoder implementation is available.");
    }
}

