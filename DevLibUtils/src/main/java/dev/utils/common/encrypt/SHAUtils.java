package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

/**
 * detail: SHA 加密工具类
 * Created by Ttt
 */
public final class SHAUtils {

    private SHAUtils() {
    }

    // 日志TAG
    private static final String TAG = SHAUtils.class.getSimpleName();
    /** 小写 */
    private static final char HEX_DIGITS[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    /**
     * 加密内容 SHA1
     * @param str 加密内容
     * @return
     */
    public static String sha1(String str) {
        return shaHex(str, "SHA-1");
    }

    /**
     * 加密内容 SHA224
     * @param str 加密内容
     * @return
     */
    public static String sha224(String str) {
        return shaHex(str, "SHA-224");
    }

    /**
     * 加密内容 SHA256
     * @param str 加密内容
     * @return
     */
    public static String sha256(String str) {
        return shaHex(str, "SHA-256");
    }

    /**
     * 加密内容 SHA384
     * @param str 加密内容
     * @return
     */
    public static String sha384(String str) {
        return shaHex(str, "SHA-384");
    }

    /**
     * 加密内容 SHA512
     * @param str 加密内容
     * @return
     */
    public static String sha512(String str) {
        return shaHex(str, "SHA-512");
    }

    // =======

    /**
     * 加密内容 SHA
     * @param str 加密内容
     * @param sha 加密算法
     * @return
     */
    private static final String shaHex(String str, String sha) {
        try {
            byte[] btInput = str.getBytes();
            // 获取 SHA-1 摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(sha);
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获取密文
            byte[] md = mdInst.digest();
            return toHexString(md, HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "shaHex");
        }
        return null;
    }

    /**
     * 获取文件 Sha 值
     * @param fPath 文件地址
     * @return
     */
    private static final String getFileSHA(String fPath, String sha) {
        try {
            InputStream fis = new FileInputStream(fPath);
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance(sha);
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest(), HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileSHA");
        }
        return null;
    }

    // =======

    /**
     * 进行转换
     * @param bData
     * @param hexDigits
     * @return
     */
    private static String toHexString(byte[] bData, char[] hexDigits) {
        StringBuilder sBuilder = new StringBuilder(bData.length * 2);
        for (int i = 0, len = bData.length; i < len; i++) {
            sBuilder.append(hexDigits[(bData[i] & 0xf0) >>> 4]);
            sBuilder.append(hexDigits[bData[i] & 0x0f]);
        }
        return sBuilder.toString();
    }

    /**
     * 获取文件 Sha1 值
     * @param fPath 文件地址
     * @return
     */
    public static String getFileSHA1(String fPath) {
        return getFileSHA(fPath, "SHA-1");
    }

    /**
     * 获取文件 Sha256 值
     * @param fPath 文件地址
     * @return
     */
    public static String getFileSHA256(String fPath) {
        return getFileSHA(fPath, "SHA-256");
    }
}
