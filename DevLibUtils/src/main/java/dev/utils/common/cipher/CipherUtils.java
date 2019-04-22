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
     * @param object Object
     * @return {@link String}
     */
    public static String encrypt(final Object object) {
        return encrypt(object, null);
    }

    /**
     * 加密方法
     * @param object Object
     * @param cipher {@link Cipher}
     * @return {@link String}
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
     * @param hex Hex String
     * @return Object
     */
    public static Object decrypt(final String hex) {
        return decrypt(hex, null);
    }

    /**
     * 解密方法
     * @param hex Hex String
     * @param cipher {@link Cipher}
     * @return Object
     */
    public static Object decrypt(final String hex, final Cipher cipher) {
        if (hex == null) return null;
        byte[] bytes = ConvertUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return ConvertUtils.bytesToObject(bytes);
    }
}
