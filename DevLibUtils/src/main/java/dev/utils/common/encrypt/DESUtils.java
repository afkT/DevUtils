package dev.utils.common.encrypt;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import dev.utils.JCLogUtils;

/**
 * detail: DES对称加密(Data Encryption Standard，数据加密标准，对称加密算法)
 * Created by Ttt
 */
public final class DESUtils {

    private DESUtils() {
    }

    // 日志TAG
    private static final String TAG = DESUtils.class.getSimpleName();

    /**
     * 返回可逆算法DES的密钥
     * @param key 前8字节将被用来生成密钥。
     * @return 生成的密钥
     * @throws Exception
     */
    public static Key getDESKey(byte[] key){
        try {
            DESKeySpec des = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            return keyFactory.generateSecret(des);
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "getDESKey");
        }
        return null;
    }


    /**
     * DES 加密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key){
        try {
            SecretKey secretKey = new SecretKeySpec(key, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] cipherBytes = cipher.doFinal(data);
            return cipherBytes;
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "encrypt");
        }
        return null;
    }

    /**
     * DES 解密
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key){
        try {
            SecretKey secretKey = new SecretKeySpec(key, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] plainBytes = cipher.doFinal(data);
            return plainBytes;
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "decrypt");
        }
        return null;
    }
}
