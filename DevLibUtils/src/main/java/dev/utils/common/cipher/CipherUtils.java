package dev.utils.common.cipher;

import dev.utils.common.ConvertUtils;

/**
 * detail: 加密工具类
 * @author Ttt
 */
public final class CipherUtils {

    private CipherUtils() {
    }

    /**
     * 加密方法
     * @param object 待加密对象
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(final Object object) {
        return encrypt(object, null);
    }

    /**
     * 加密方法
     * @param object 待加密对象
     * @param cipher 加/解密中间层
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(final Object object, final Cipher cipher) {
        if (object == null) return null;
        byte[] bytes = ConvertUtils.objectToBytes(object);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return ConvertUtils.toHexString(bytes);
    }

    // =

    /**
     * 解密方法
     * @param hex 十六进制字符串
     * @return 解密后的对象
     */
    public static Object decrypt(final String hex) {
        return decrypt(hex, null);
    }

    /**
     * 解密方法
     * @param hex    十六进制字符串
     * @param cipher 加/解密中间层
     * @return 解密后的对象
     */
    public static Object decrypt(final String hex, final Cipher cipher) {
        if (hex == null) return null;
        byte[] bytes = ConvertUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return ConvertUtils.bytesToObject(bytes);
    }
}
