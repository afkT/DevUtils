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
    public static String encrypt(Object obj) {
        return encrypt(obj, null);
    }

    /**
     * 加密方法
     * @param obj
     * @param cipher
     * @return
     */
    public static String encrypt(Object obj, Cipher cipher) {
        if (obj == null) return null;
        byte[] bytes = ByteUtils.objectToByte(obj);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return HexUtils.encodeHexStr(bytes);
    }

    // =

    /**
     * 解密方法
     * @param hex
     * @return
     */
    public static Object decrypt(String hex) {
        return decrypt(hex, null);
    }

    /**
     * 解密方法
     * @param hex
     * @param cipher
     * @return
     */
    public static Object decrypt(String hex, Cipher cipher) {
        if (hex == null) return null;
        byte[] bytes = HexUtils.decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return ByteUtils.byteToObject(bytes);
    }
}
