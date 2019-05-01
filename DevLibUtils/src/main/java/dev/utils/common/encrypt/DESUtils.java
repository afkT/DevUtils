package dev.utils.common.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import dev.utils.JCLogUtils;

/**
 * detail: DES 对称加密
 * @author Ttt
 * <pre>
 *      Data Encryption Standard，数据加密标准，对称加密算法
 * </pre>
 */
public final class DESUtils {

    private DESUtils() {
    }

    // 日志 TAG
    private static final String TAG = DESUtils.class.getSimpleName();

    /**
     * 获取可逆算法 DES 的密钥
     * @param key 前8字节将被用来生成密钥
     * @return 可逆算法 DES 的密钥
     */
    public static Key getDESKey(final byte[] key) {
        if (key == null) return null;
        try {
            DESKeySpec des = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            return keyFactory.generateSecret(des);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getDESKey");
        }
        return null;
    }


    /**
     * DES 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的 byte[]
     */
    public static byte[] encrypt(final byte[] data, final byte[] key) {
        if (data == null || key == null) return null;
        try {
            SecretKey secretKey = new SecretKeySpec(key, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(data);
            return cipherBytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "encrypt");
        }
        return null;
    }

    /**
     * DES 解密
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的 byte[]
     */
    public static byte[] decrypt(final byte[] data, final byte[] key) {
        if (data == null || key == null) return null;
        try {
            SecretKey secretKey = new SecretKeySpec(key, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] plainBytes = cipher.doFinal(data);
            return plainBytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "decrypt");
        }
        return null;
    }
}
