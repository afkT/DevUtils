package dev.utils.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;

/**
 * detail: SHA 加密工具类
 * @author Ttt
 */
public final class SHAUtils {

    private SHAUtils() {
    }

    // 日志 TAG
    private static final String TAG = SHAUtils.class.getSimpleName();

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
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileSHA(file, "SHA-1");
    }

    /**
     * 获取文件 SHA1 值
     * @param file 文件
     * @return 文件 SHA1 字符串信息
     */
    public static String getFileSHA1(final File file) {
        return getFileSHA(file, "SHA-1");
    }

    /**
     * 获取文件 SHA256 值
     * @param filePath 文件路径
     * @return 文件 SHA256 字符串信息
     */
    public static String getFileSHA256(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileSHA(file, "SHA-256");
    }

    /**
     * 获取文件 SHA256 值
     * @param file 文件
     * @return 文件 SHA256 字符串信息
     */
    public static String getFileSHA256(final File file) {
        return getFileSHA(file, "SHA-256");
    }

    // =

    /**
     * 加密内容 SHA 模板
     * @param data      待加密数据
     * @param algorithm 算法
     * @return SHA 算法加密后的字符串
     */
    public static String shaHex(
            final String data,
            final String algorithm
    ) {
        if (data == null || algorithm == null) return null;
        try {
            byte[] bytes = data.getBytes();
            // 获取 SHA-1 摘要算法的 MessageDigest 对象
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            // 使用指定的字节更新摘要
            digest.update(bytes);
            // 获取密文
            return ConvertUtils.toHexString(digest.digest(), true);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "shaHex");
        }
        return null;
    }

    /**
     * 获取文件 SHA 值
     * @param file      文件
     * @param algorithm 算法
     * @return 文件指定 SHA 字符串信息
     */
    public static String getFileSHA(
            final File file,
            final String algorithm
    ) {
        if (file == null || algorithm == null) return null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[]        buffer = new byte[1024];
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            int           numRead;
            while ((numRead = is.read(buffer)) > 0) {
                digest.update(buffer, 0, numRead);
            }
            return ConvertUtils.toHexString(digest.digest(), true);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileSHA");
        } finally {
            CloseUtils.closeIOQuietly(is);
        }
        return null;
    }
}