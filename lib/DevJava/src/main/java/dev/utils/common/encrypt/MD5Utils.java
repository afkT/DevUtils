package dev.utils.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

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
            return toHexString(digest.digest(), HEX_DIGITS);
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
            return toHexString(digest.digest(), HEX_DIGITS_UPPER);
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
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5(file);
    }

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToHexString(file);
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String getFileMD5ToHexString(final File file) {
        return toHexString(getFileMD5(file));
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
            FileInputStream fis = new FileInputStream(file);
            MessageDigest digest = MessageDigest.getInstance("MD5");
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
            if (dis != null) {
                try {
                    dis.close();
                } catch (Exception e) {
                }
            }
        }
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ================
    // = ConvertUtils =
    // ================

    // 用于建立十六进制字符的输出的小写字符数组
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 用于建立十六进制字符的输出的大写字符数组
    private static final char[] HEX_DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data 待转换数据
     * @return 十六进制 String
     */
    private static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      待转换数据
     * @param hexDigits {@link #HEX_DIGITS}、{@link #HEX_DIGITS_UPPER}
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

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean isSpace(final String str) {
        if (str == null) return true;
        for (int i = 0, len = str.length(); i < len; ++i) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}