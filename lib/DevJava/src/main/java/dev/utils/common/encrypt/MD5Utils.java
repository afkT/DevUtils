package dev.utils.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;

/**
 * detail: MD5 加密工具类
 * @author Ttt
 * <pre>
 *     Message Digest 消息摘要算法
 * </pre>
 */
public final class MD5Utils {

    private MD5Utils() {
    }

    // 日志 TAG
    private static final String TAG = MD5Utils.class.getSimpleName();

    /**
     * 加密内容 (32 位小写 MD5)
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5(final String data) {
        if (data == null) return null;
        try {
            return md5(data.getBytes());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5");
        }
        return null;
    }

    /**
     * 加密内容 (32 位小写 MD5)
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取 MD5 摘要算法的 MessageDigest 对象
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            digest.update(data);
            // 获取密文
            return ConvertUtils.toHexString(digest.digest(), true);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5");
        }
        return null;
    }

    /**
     * 加密内容 (32 位大写 MD5)
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5Upper(final String data) {
        if (data == null) return null;
        try {
            return md5Upper(data.getBytes());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5Upper");
        }
        return null;
    }

    /**
     * 加密内容 (32 位大写 MD5)
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5Upper(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取 MD5 摘要算法的 MessageDigest 对象
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            digest.update(data);
            // 获取密文
            return ConvertUtils.toHexString(digest.digest(), false);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5Upper");
        }
        return null;
    }

    // =

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值
     */
    public static byte[] getFileMD5(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileMD5(file);
    }

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToHexString(file);
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final File file) {
        return ConvertUtils.toHexString(getFileMD5(file));
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值 byte[]
     */
    public static byte[] getFileMD5(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis    = new FileInputStream(file);
            MessageDigest   digest = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, digest);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            digest = dis.getMessageDigest();
            return digest.digest();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileMD5");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(dis);
        }
    }
}