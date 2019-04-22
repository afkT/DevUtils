package dev.utils.app;

import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.File;
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
 * detail: 签名工具类(获取 App 签名信息)
 * @author Ttt
 * <pre>
 *      Android的 Apk 应用签名机制以及读取签名的方法
 *      @see <a href="http://www.jb51.net/article/79894.htm"/>
 * </pre>
 */
public final class SignaturesUtils {

    private SignaturesUtils() {
    }

    // 日志 TAG
    private static final String TAG = SignaturesUtils.class.getSimpleName();
    // 用于建立十六进制字符的输出的小写字符数组
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 检测应用程序是否是用"CN=Android Debug,O=Android,C=US"的debug信息来签名的
     * 判断签名是debug签名还是release签名
     */
    private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    /**
     * 进行转换
     * @param data
     * @return
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 返回 MD5
     * @param signatures
     * @return
     */
    public static String signatureMD5(final Signature[] signatures) {
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
     * 返回 SHA1 加密字符串
     * @param signatures
     * @return
     */
    public static String signatureSHA1(final Signature[] signatures) {
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
     * 返回 SHA256 加密字符串
     * @param signatures
     * @return
     */
    public static String signatureSHA256(final Signature[] signatures) {
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
     * @return {@code true} 开发(debug.keystore), {@code false} 上线发布(非.android默认debug.keystore)
     */
    public static boolean isDebuggable(final Signature[] signatures) {
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
     * 获取 App 证书对象
     */
    public static X509Certificate getX509Certificate(final Signature[] signatures) {
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
     * 打印签名信息
     * @param signatures
     * @return
     */
    public static void printSignatureName(final Signature[] signatures) {
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
                LogPrintUtils.dTag(TAG, "subjectDN:" + cert.getSubjectDN().toString());
                LogPrintUtils.dTag(TAG, cert.getNotAfter() + "--" + cert.getNotBefore());
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureNameA");
        }
    }

    // =

    /**
     * 从 Apk 中读取签名
     * @param file
     * @return
     */
    public static Signature[] getSignaturesFromApk(final File file) {
        try {
            Certificate[] certificates = getCertificateFromApk(file);
            Signature[] signatures = new Signature[]{new Signature(certificates[0].getEncoded())};
            return signatures;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSignaturesFromApk");
        }
        return null;
    }

    /**
     * 从 Apk 中读取签名
     * @param file
     * @return
     */
    public static Certificate[] getCertificateFromApk(final File file) {
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
    private static Certificate[] loadCertificates(final JarFile jarFile, final JarEntry je, final byte[] readBuffer) {
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

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data
     * @param hexDigits
     * @return
     */
    private static String toHexString(final byte[] data, final char[] hexDigits) {
        if (data == null || hexDigits == null) return null;
        try {
            int len = data.length;
            StringBuilder builder = new StringBuilder(len);
            for (int i = 0; i < len; i++) {
                builder.append(hexDigits[(data[i] & 0xf0) >>> 4]);
                builder.append(hexDigits[data[i] & 0x0f]);
            }
            return builder.toString();
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }
}
