package dev.utils.common.cipher;

import dev.utils.common.ByteUtils;
import dev.utils.common.HexUtils;

/**
 * detail: 加密工具类
 * Created by Ttt
 */
public final class CipherUtils {

    private CipherUtils() {
    }

    /**
     * 加密方法
     * @param obj
     * @return
     */
    public static String encrypt(final Object obj) {
        return encrypt(obj, null);
    }

    /**
     * 加密方法
     * @param object
     * @param cipher
     * @return
     */
    public static String encrypt(final Object object, final Cipher cipher) {
        if (object == null) return null;
        byte[] bytes = ByteUtils.objectToByte(object);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return HexUtils.encodeHexStr(bytes);
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
        byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return ByteUtils.byteToObject(bytes);
    }
}
