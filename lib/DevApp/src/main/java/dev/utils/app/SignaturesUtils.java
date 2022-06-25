package dev.utils.app;

import android.content.pm.Signature;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.security.auth.x500.X500Principal;

import dev.utils.DevFinal;
import dev.utils.LogPrintUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.DateUtils;

/**
 * detail: 签名工具类 ( 获取 APP 签名信息 )
 * @author Ttt
 * <pre>
 *     Android 的 APK 应用签名机制以及读取签名的方法
 *     @see <a href="http://www.jb51.net/article/79894.htm"/>
 * </pre>
 */
public final class SignaturesUtils {

    private SignaturesUtils() {
    }

    // 日志 TAG
    private static final String TAG = SignaturesUtils.class.getSimpleName();

    /**
     * 判断签名是 debug 签名还是 release 签名
     * 检测应用程序是否是用 "CN=Android Debug,O=Android,C=US" 的 debug 信息进行签名
     */
    private static final X500Principal DEBUG_DN = new X500Principal("CN=Android Debug,O=Android,C=US");

    /**
     * 获取 APP Signature
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature() {
        return AppUtils.getAppSignature();
    }

    /**
     * 获取 APP Signature
     * @param packageName 应用包名
     * @return {@link Signature} 数组
     */
    public static Signature[] getAppSignature(final String packageName) {
        return AppUtils.getAppSignature(packageName);
    }

    /**
     * 获取 MD5 签名
     * @param signatures {@link Signature}[]
     * @return MD5 签名
     */
    public static String signatureMD5(final Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            if (signatures != null) {
                for (Signature sign : signatures) {
                    if (sign != null) {
                        digest.update(sign.toByteArray());
                    }
                }
            }
            return ConvertUtils.toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureMD5");
            return "";
        }
    }

    /**
     * 获取签名 SHA1 加密字符串
     * @param signatures {@link Signature}[]
     * @return 签名 SHA1 加密字符串
     */
    public static String signatureSHA1(final Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (signatures != null) {
                for (Signature sign : signatures) {
                    if (sign != null) {
                        digest.update(sign.toByteArray());
                    }
                }
            }
            return ConvertUtils.toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureSHA1");
            return "";
        }
    }

    /**
     * 获取签名 SHA256 加密字符串
     * @param signatures {@link Signature}[]
     * @return 签名 SHA256 加密字符串
     */
    public static String signatureSHA256(final Signature[] signatures) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            if (signatures != null) {
                for (Signature sign : signatures) {
                    if (sign != null) {
                        digest.update(sign.toByteArray());
                    }
                }
            }
            return ConvertUtils.toHexString(digest.digest());
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "signatureSHA256");
            return "";
        }
    }

    /**
     * 判断 debug 签名还是 release 签名
     * @param signatures {@link Signature}[]
     * @return {@code true} debug.keystore, {@code false} release.keystore
     */
    public static boolean isDebuggable(final Signature[] signatures) {
        // 默认属于 debug 签名
        boolean debuggable = true;
        if (signatures != null) {
            try {
                for (Signature sign : signatures) {
                    if (sign != null) {
                        X509Certificate cert = getX509Certificate(sign);
                        if (cert != null && cert.getSubjectX500Principal() != null) {
                            debuggable = DEBUG_DN.equals(cert.getSubjectX500Principal());
                            if (debuggable) {
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "isDebuggable");
            }
        }
        return debuggable;
    }

    /**
     * 获取证书对象
     * @param signatures {@link Signature}[]
     * @return {@link X509Certificate}
     */
    public static X509Certificate getX509Certificate(final Signature[] signatures) {
        if (signatures != null && signatures.length != 0) {
            return getX509Certificate(signatures[0]);
        }
        return null;
    }

    /**
     * 获取证书对象
     * @param signature {@link Signature}
     * @return {@link X509Certificate}
     */
    public static X509Certificate getX509Certificate(final Signature signature) {
        if (signature != null) {
            try {
                CertificateFactory   cf   = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream bais = new ByteArrayInputStream(signature.toByteArray());
                return (X509Certificate) cf.generateCertificate(bais);
            } catch (Exception e) {
                LogPrintUtils.eTag(TAG, e, "getX509Certificate");
            }
        }
        return null;
    }

    /**
     * 打印签名信息
     * @param signatures {@link Signature}[]
     * @return 签名信息 {@link List}
     */
    public static List<Map<String, String>> printSignatureInfo(final Signature[] signatures) {
        List<Map<String, String>> lists = new ArrayList<>();
        try {
            // 格式化日期
            SimpleDateFormat sdf = DateUtils.getDefaultFormat();
            // 遍历获取
            for (Signature sign : signatures) {
                if (sign != null) {
                    X509Certificate cert = getX509Certificate(sign);

                    // 证书生成日期
                    Date notBefore = cert.getNotBefore();
                    // 证书有效期
                    Date notAfter = cert.getNotAfter();
                    // 设置有效期
                    StringBuilder builder = new StringBuilder();
                    builder.append(sdf.format(notBefore));
                    builder.append(" to "); // 至
                    builder.append(sdf.format(notAfter));
                    builder.append(DevFinal.SYMBOL.NEW_LINE_X2);
                    builder.append(notBefore);
                    builder.append(" to ");
                    builder.append(notAfter);
                    // 保存有效期转换信息
                    String effectiveStr = builder.toString();
                    // 证书是否过期
                    boolean effective = false;
                    try {
                        cert.checkValidity();
                        // CertificateExpiredException ( 证书已过期 )
                        // CertificateNotYetValidException ( 证书不再有效 )
                    } catch (CertificateExpiredException ce) {
                        effective = true;
                    } catch (CertificateNotYetValidException ce) {
                        effective = true;
                    }
                    // 证书发布方
                    String certPrincipal = cert.getIssuerX500Principal().toString();
                    // 证书版本号
                    String certVersion = String.valueOf(cert.getVersion());
                    // 证书算法名称
                    String certSigAlgName = cert.getSigAlgName();
                    // 证书算法 OID
                    String certSigAlgOID = cert.getSigAlgOID();
                    // 证书机器码
                    String certSerialnumber = cert.getSerialNumber().toString();
                    // 证书 DER 编码
                    String certDERCode = null;
                    try {
                        // 证书 DER 编码
                        certDERCode = ConvertUtils.toHexString(cert.getTBSCertificate());
                    } catch (CertificateEncodingException ignored) {
                    }
                    // 证书公钥
                    String pubKey = cert.getPublicKey().toString();

                    // 保存信息
                    Map<String, String> maps = new LinkedHashMap<>();
                    maps.put("证书有效期信息", effectiveStr);
                    maps.put("证书是否过期", Boolean.toString(effective));
                    maps.put("证书发布方", certPrincipal);
                    maps.put("证书版本号", certVersion);
                    maps.put("证书算法名称", certSigAlgName);
                    maps.put("证书算法 OID", certSigAlgOID);
                    maps.put("证书机器码", certSerialnumber);
                    maps.put("证书 DER 编码", certDERCode);
                    maps.put("证书公钥", pubKey);
                    lists.add(maps);
                }
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "printSignatureInfo");
        }
        return lists;
    }

    // =

    /**
     * 从 APK 中读取签名
     * @param file 文件
     * @return {@link Signature}[]
     */
    public static Signature[] getSignaturesFromApk(final File file) {
        Certificate[] certificates = getCertificateFromApk(file);
        if (certificates == null) return null;
        try {
            return new Signature[]{new Signature(certificates[0].getEncoded())};
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getSignaturesFromApk");
        }
        return null;
    }

    /**
     * 从 APK 中读取签名
     * @param file 文件
     * @return {@link Certificate}[]
     */
    public static Certificate[] getCertificateFromApk(final File file) {
        try {
            JarFile  jarFile    = new JarFile(file);
            JarEntry je         = jarFile.getJarEntry("AndroidManifest.xml");
            byte[]   readBuffer = new byte[8192];
            return loadCertificates(jarFile, je, readBuffer);
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getCertificateFromApk");
        }
        return null;
    }

    /**
     * 加载文件, 获取签名信息
     * @param jarFile    {@link JarFile}
     * @param jarEntry   {@link JarEntry}
     * @param readBuffer 文件 Buffer
     * @return {@link Certificate}[]
     */
    private static Certificate[] loadCertificates(
            final JarFile jarFile,
            final JarEntry jarEntry,
            final byte[] readBuffer
    ) {
        try {
            InputStream is = jarFile.getInputStream(jarEntry);
            while (is.read(readBuffer, 0, readBuffer.length) != -1) {
            }
            CloseUtils.closeIOQuietly(is);
            return jarEntry != null ? jarEntry.getCertificates() : null;
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "loadCertificates");
        }
        return null;
    }
}