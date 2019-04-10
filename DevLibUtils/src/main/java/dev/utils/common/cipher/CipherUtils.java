package dev.utils.common.cipher;

import dev.utils.common.ByteUtils;
import dev.utils.common.ConvertUtils;

/**
 * detail: 加密工具类
 * Created by Ttt
 */
public final class CipherUtils {

    private CipherUtils() {
    }

    /**
     * 加密方法
     * @param object
     * @return
     */
    public static String encrypt(final Object object) {
        return encrypt(object, null);
    }

    /**
     * 加密方法
     * @param object
     * @param cipher
     * @return
     */
    public static String encrypt(final Object object, final Cipher cipher) {
        if (object == null) return null;
        byte[] bytes = ByteUtils.objectToBytes(object);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return ConvertUtils.toHexString(bytes);
    }

    // =

    /**
     * 解密方法
     * @param hex
     * @return
     */
    public static Object decrypt(final String hex) {
        return decrypt(hex, null);
    }

    /**
     * 解密方法
     * @param hex
     * @param cipher
     * @return
     */
    public static Object decrypt(final String hex, final Cipher cipher) {
        if (hex == null) return null;
        byte[] bytes = ConvertUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return ByteUtils.bytesToObject(bytes);
    }
}
