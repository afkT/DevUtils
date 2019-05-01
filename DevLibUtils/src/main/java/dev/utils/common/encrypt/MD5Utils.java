package dev.utils.common.encrypt;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

import dev.utils.JCLogUtils;

/**
 * detail: MD5加密 不可逆(Message Digest，消息摘要算法)
 * @author Ttt
 */
public final class MD5Utils {

    private MD5Utils() {
    }

    // 日志 TAG
    private static final String TAG = MD5Utils.class.getSimpleName();
    // 用于建立十六进制字符的输出的小写字符数组
    public static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 用于建立十六进制字符的输出的大写字符数组
    public static final char HEX_DIGITS_UPPER[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 加密内容 - 32 位 MD5 - 小写
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
     * 加密内容 - 32 位 MD5 - 小写
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data);
            // 获取密文
            byte[] md = mdInst.digest();
            return toHexString(md, HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5");
        }
        return null;
    }

    /**
     * 加密内容 - 32 位 MD5 - 大写
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
     * 加密内容 - 32 位 MD5 - 大写
     * @param data 待加密数据
     * @return MD5 加密后的字符串
     */
    public static String md5Upper(final byte[] data) {
        if (data == null) return null;
        try {
            // 获取MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(data);
            // 获取密文
            byte[] md = mdInst.digest();
            return toHexString(md, HEX_DIGITS_UPPER);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "md5Upper");
        }
        return null;
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data 待加密数据
     * @return 十六进制字符串
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      待加密数据
     * @param hexDigits {@link MD5Utils#HEX_DIGITS}， {@link MD5Utils#HEX_DIGITS_UPPER}
     * @return 十六进制字符串
     */
    public static String toHexString(final byte[] data, final char[] hexDigits) {
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

    /**
     * 获取文件 MD5 值 - 小写
     * @param filePath 文件路径
     * @return 文件 MD5 值
     */
    public static String getFileMD5(final String filePath) {
        if (filePath == null) return null;
        try {
            InputStream is = new FileInputStream(filePath);
            byte[] buffer = new byte[1024];
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            int numRead;
            while ((numRead = is.read(buffer)) > 0) {
                mdInst.update(buffer, 0, numRead);
            }
            is.close();
            return toHexString(mdInst.digest(), HEX_DIGITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getFileMD5");
        }
        return null;
    }
}
