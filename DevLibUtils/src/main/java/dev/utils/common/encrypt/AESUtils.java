package dev.utils.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import dev.utils.JCLogUtils;

/**
 * detail: AES 对称加密
 * @author Ttt
 * <pre>
 *      Advanced Encryption Standard，高级数据加密标准，AES算法可以有效抵制针对 DES 的攻击算法，对称加密算法
 * </pre>
 */
public final class AESUtils {

    private AESUtils() {
    }

    // 日志 TAG
    private static final String TAG = AESUtils.class.getSimpleName();

    /**
     * 生成密钥
     * @return 密钥 byte[]
     */
    public static byte[] initKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256); // 192 256
            SecretKey secretKey = keyGen.generateKey();
            return secretKey.getEncoded();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "initKey");
        }
        return null;
    }

    /**
     * AES 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的 byte[]
     */
    public static byte[] encrypt(final byte[] data, final byte[] key) {
        if (data == null || key == null) return null;
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data);
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "encrypt");
        }
        return null;
    }

    /**
     * AES 解密
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的 byte[]
     */
    public static byte[] decrypt(final byte[] data, final byte[] key) {
        if (data == null || key == null) return null;
        try {
            SecretKey secretKey = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] bytes = cipher.doFinal(data);
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "decrypt");
        }
        return null;
    }
}