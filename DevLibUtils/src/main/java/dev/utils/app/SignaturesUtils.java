package dev.utils.app;

import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.security.auth.x500.X500Principal;

import dev.utils.LogPrintUtils;

/**
 * detail: 签名工具类（获取app，签名信息）
 * Created by Ttt
 */
public final class SignaturesUtils {

    private SignaturesUtils() {
    }

    // 日志TAG
    private static final String TAG = SignaturesUtils.class.getSimpleName();

    // 如需要小写则把ABCDEF改成小写
    private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 检测应用程序是否是用"CN=Android Debug,O=Android,C=US"的debug信息来签名的
     * 判断签名是debug签名还是release签名
     */
    private final static X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    /**
     * 进行转换
     * @param bData
     * @return
     */
    public static String toHexString(byte[] bData) {
        StringBuilder sb = new StringBuilder(bData.length * 2);
        for (int i = 0, len = bData.length; i < len; i++) {
            sb.append(HEX_DIGITS[(bData[i] & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[bData[i] & 0x0f]);
        }
        return sb.toString();
    }

    /**
     * 返回MD5
     * @param signatures
     * @return
     */
    public static String signatureMD5(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureMD5");
            return "";
        }
    }

    /**
     * SHA1
     */
    public static String signatureSHA1(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureSHA1");
            return "";
        }
    }

    /**
     * SHA256
     */
    public static String signatureSHA256(Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (signatures != null) {
                for (Signature s : signatures)
                    digest.update(s.toByteArray());
            }
            return toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureSHA256");
            return "";
        }
    }

    /**
     * 判断签名是debug签名还是release签名
     * @return true = 开发(debug.keystore)，false = 上线发布（非.android默认debug.keystore）
     */
    public static boolean isDebuggable(Signature[] signatures) {
        // 判断是否默认key(默认是)
        boolean debuggable = true;
        try {
            for (int i = 0, len = signatures.length; i < len; i++) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable) {
                    break;
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "isDebuggable");
        }
        return debuggable;
    }

    /**
     * 获取App 证书对象
     */
    public static X509Certificate getX509Certificate(Signature[] signatures){
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream stream = new ByteArrayInputStream(signatures[0].toByteArray());
            X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
            return cert;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getX509Certificate");
        }
        return null;
    }

    /**
     *  打印签名信息
     * @param signatures
     * @return
     */
    public static void printSignatureName(Signature[] signatures){
        try {
            for (int i = 0, len = signatures.length; i < len; i++) {
                CertificateFactory cf = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);

                String pubKey = cert.getPublicKey().toString(); // 公钥
                String signNumber = cert.getSerialNumber().toString();

                LogPrintUtils.dTag(TAG, "signName:" + cert.getSigAlgName());//算法名
                LogPrintUtils.dTag(TAG, "pubKey:" + pubKey);
                LogPrintUtils.dTag(TAG, "signNumber:" + signNumber);//证书序列编号
                LogPrintUtils.dTag(TAG, "subjectDN:"+cert.getSubjectDN().toString());
                LogPrintUtils.dTag(TAG, cert.getNotAfter() + "--" + cert.getNotBefore());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureNameA");
        }
    }

    // --

    // Android的APK应用签名机制以及读取签名的方法
    // http://www.jb51.net/article/79894.htm

    /**
     * 从APK中读取签名
     * @param file
     * @return
     * @throws IOException
     */
    public static Signature[] getSignaturesFromApk(File file) {
        try {
            Certificate[] certificates = getCertificateFromApk(file);
            Signature[] signatures = new Signature[] { new Signature(certificates[0].getEncoded()) };
            return signatures;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSignaturesFromApk");
        }
        return null;
    }

    /**
     * 从APK中读取签名
     * @param file
     * @return
     * @throws IOException
     */
    public static Certificate[] getCertificateFromApk(File file) {
        try {
            JarFile jarFile = new JarFile(file);
            JarEntry je = jarFile.getJarEntry("AndroidManifest.xml");
            byte[] readBuffer = new byte[8192];
            return loadCertificates(jarFile, je, readBuffer);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCertificateFromApk");
        }
        return null;
    }

    /**
     * 加载签名
     * @param jarFile
     * @param je
     * @param readBuffer
     * @return
     */
    private static Certificate[] loadCertificates(JarFile jarFile, JarEntry je, byte[] readBuffer) {
        try {
            InputStream is = jarFile.getInputStream(je);
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
            }
            is.close();
            return je != null ? je.getCertificates() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "loadCertificates");
        }
        return null;
    }
}
