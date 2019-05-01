package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

/**
 * detail: SHA 加密工具类
 * @author Ttt
 */
public final class SHAUtils {

    private SHAUtils() {
    }

    // 日志 TAG
    private static final String TAG = SHAUtils.class.getSimpleName();
    // 用于建立十六进制字符的输出的小写字符数组
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 加密内容 SHA1
     * @param data 待加密数据
     * @return SHA1 加密后的字符串
     */
    public static String sha1(final String data) {
        return shaHex(data, "SHA-1");
    }

    /**
     * 加密内容 SHA224
     * @param data 待加密数据
     * @return SHA224 加密后的字符串
     */
    public static String sha224(final String data) {
        return shaHex(data, "SHA-224");
    }

    /**
     * 加密内容 SHA256
     * @param data 待加密数据
     * @return SHA256 加密后的字符串
     */
    public static String sha256(final String data) {
        return shaHex(data, "SHA-256");
    }

    /**
     * 加密内容 SHA384
     * @param data 待加密数据
     * @return SHA384 加密后的字符串
     */
    public static String sha384(final String data) {
        return shaHex(data, "SHA-384");
    }

    /**
     * 加密内容 SHA512
     * @param data 待加密数据
     * @return SHA512 加密后的字符串
     */
    public static String sha512(final String data) {
        return shaHex(data, "SHA-512");
    }

    // =

    /**
     * 获取文件 SHA1 值
     * @param filePath 文件路径
     * @return 文件 SHA1 字符串信息
     */
    public static String getFileSHA1(final String filePath) {
        return getFileSHA(filePath, "SHA-1");
    }

    /**
     * 获取文件 SHA256 值
     * @param filePath 文件路径
     * @return 文件 SHA256 字符串信息
     */
    public static String getFileSHA256(final String filePath) {
        return getFileSHA(filePath, "SHA-256");
    }

    // =

    /**
     * 加密内容 SHA 模板
     * @param data      待加密数据
     * @param algorithm 算法
     * @return SHA 算法加密后的字符串
     */
    public static String shaHex(final String data, final String algorithm) {
        if (data == null || algorithm == null) return null;
        try {
            byte[] bytes = data.getBytes();
            // 获取 SHA-1 摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance(algorithm);
            // 使用指定的字节更新摘要
            mdInst.update(bytes);
            // 获取密文
            return toHexString(mdInst.digest(), HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "shaHex");
        }
        return null;
    }

    /**
     * 获取文件 SHA 值
     * @param filePath  文件路径
     * @param algorithm 算法
     * @return 获取文件指定 SHA 字符串信息
     */
    public static String getFileSHA(final String filePath, final String algorithm) {
        if (filePath == null || algorithm == null) return null;
        try {
            InputStream is = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance(algorithm);
            int numRead = 0;
            while ((numRead = is.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            is.close();
            return toHexString(md5.digest(), HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileSHA");
        }
        return null;
    }

    // =

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      待加密数据
     * @param hexDigits {@link SHAUtils#HEX_DIGITS}
     * @return 十六进制字符串
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
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }
}
