package dev.utils.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import dev.utils.JCLogUtils;
import dev.utils.common.assist.Base64;

/**
 * detail: 加/解密 通用工具类
 * Created by Blankj
 * Update to Ttt
 */
public final class EncryptUtils {

    private EncryptUtils() {
    }

    // 日志 TAG
    private static final String TAG = EncryptUtils.class.getSimpleName();

    /**
     * MD2 加密
     * @param data
     * @return
     */
    public static String encryptMD2ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptMD2ToString(data.getBytes());
    }

    /**
     * MD2 加密
     * @param data
     * @return
     */
    public static String encryptMD2ToString(final byte[] data) {
        return toHexString(encryptMD2(data));
    }

    /**
     * MD2 加密
     * @param data
     * @return
     */
    public static byte[] encryptMD2(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD5 加密
     * @param data
     * @return
     */
    public static String encryptMD5ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5 加密
     * @param data
     * @param salt
     * @return
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return toHexString(encryptMD5(data.getBytes()));
        if (data == null) return toHexString(encryptMD5(salt.getBytes()));
        return toHexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * MD5 加密
     * @param data
     * @return
     */
    public static String encryptMD5ToString(final byte[] data) {
        return toHexString(encryptMD5(data));
    }

    /**
     * MD5 加密
     * @param data
     * @param salt
     * @return
     */
    public static String encryptMD5ToString(final byte[] data, final byte[] salt) {
        if (data == null && salt == null) return "";
        if (salt == null) return toHexString(encryptMD5(data));
        if (data == null) return toHexString(encryptMD5(salt));
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return toHexString(encryptMD5(dataSalt));
    }

    /**
     * MD5 加密
     * @param data
     * @return
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5 加密文件
     * @param filePath
     * @return
     */
    public static String encryptMD5FileToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5FileToString(file);
    }

    /**
     * MD5 加密文件
     * @param filePath
     * @return
     */
    public static byte[] encryptMD5File(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * MD5 加密文件
     * @param file
     * @return
     */
    public static String encryptMD5FileToString(final File file) {
        return toHexString(encryptMD5File(file));
    }

    /**
     * MD5 加密文件
     * @param file
     * @return
     */
    public static byte[] encryptMD5File(final File file) {
        if (file == null) return null;
        FileInputStream fis = null;
        DigestInputStream digestInputStream;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            digestInputStream = new DigestInputStream(fis, md);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0)) break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            JCLogUtils.eTag(TAG, e, "encryptMD5File");
            return null;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * SHA1 加密
     * @param data
     * @return
     */
    public static String encryptSHA1ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA1ToString(data.getBytes());
    }

    /**
     * SHA1 加密
     * @param data
     * @return
     */
    public static String encryptSHA1ToString(final byte[] data) {
        return toHexString(encryptSHA1(data));
    }

    /**
     * SHA1 加密
     * @param data
     * @return
     */
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA-1");
    }

    /**
     * SHA224 加密
     * @param data
     * @return
     */
    public static String encryptSHA224ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA224ToString(data.getBytes());
    }

    /**
     * SHA224 加密
     * @param data
     * @return
     */
    public static String encryptSHA224ToString(final byte[] data) {
        return toHexString(encryptSHA224(data));
    }

    /**
     * SHA224 加密
     * @param data
     * @return
     */
    public static byte[] encryptSHA224(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA256 加密
     * @param data
     * @return
     */
    public static String encryptSHA256ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA256ToString(data.getBytes());
    }

    /**
     * SHA256 加密
     * @param data
     * @return
     */
    public static String encryptSHA256ToString(final byte[] data) {
        return toHexString(encryptSHA256(data));
    }

    /**
     * SHA256 加密
     * @param data
     * @return
     */
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA-256");
    }

    /**
     * SHA384 加密
     * @param data
     * @return
     */
    public static String encryptSHA384ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA384ToString(data.getBytes());
    }

    /**
     * SHA384 加密
     * @param data
     * @return
     */
    public static String encryptSHA384ToString(final byte[] data) {
        return toHexString(encryptSHA384(data));
    }

    /**
     * SHA384 加密
     * @param data
     * @return
     */
    public static byte[] encryptSHA384(final byte[] data) {
        return hashTemplate(data, "SHA-384");
    }

    /**
     * SHA512 加密
     * @param data
     * @return
     */
    public static String encryptSHA512ToString(final String data) {
        if (data == null || data.length() == 0) return "";
        return encryptSHA512ToString(data.getBytes());
    }

    /**
     * SHA512 加密
     * @param data
     * @return
     */
    public static String encryptSHA512ToString(final byte[] data) {
        return toHexString(encryptSHA512(data));
    }

    /**
     * SHA512 加密
     * @param data
     * @return
     */
    public static byte[] encryptSHA512(final byte[] data) {
        return hashTemplate(data, "SHA-512");
    }

    /**
     * hash 加密模版方法
     * @param data 数据
     * @param algorithm 算法
     * @return
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hashTemplate");
            return null;
        }
    }

    // =

    /**
     * HmacMD5 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacMD5ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacMD5(data, key));
    }

    /**
     * HmacMD5 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * HmacSHA1 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA1ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA1ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA1ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacSHA1(data, key));
    }

    /**
     * HmacSHA1 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacSHA1(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA224 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA224ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA224ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA224ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacSHA224(data, key));
    }

    /**
     * HmacSHA224 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacSHA224(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * HmacSHA256 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA256ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA256ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA256ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacSHA256(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * HmacSHA384 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA384ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA384ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA384ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacSHA384(data, key));
    }

    /**
     * HmacSHA384 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacSHA384(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * HmacSHA512 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA512ToString(final String data, final String key) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return "";
        return encryptHmacSHA512ToString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512 加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptHmacSHA512ToString(final byte[] data, final byte[] key) {
        return toHexString(encryptHmacSHA512(data, key));
    }

    /**
     * HmacSHA512 加密
     * @param data
     * @param key
     * @return
     */
    public static byte[] encryptHmacSHA512(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * hmac 加密模版方法
     * @param data 数据
     * @param key
     * @param algorithm 算法
     * @return
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            JCLogUtils.eTag(TAG, e, "hmacTemplate");
            return null;
        }
    }

    // =

    /**
     * DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encryptDESToBase64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static String encryptDESToHexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return toHexString(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }

    /**
     * DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @returndata
     */
    public static byte[] decryptBase64DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptHexStringDES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptDES(hexStringToBytes(data), key, transformation, iv);
    }

    /**
     * DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptDES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    /**
     * 3DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encrypt3DESToBase64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static String encrypt3DESToHexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return toHexString(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encrypt3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true);
    }

    /**
     * 3DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptBase64_3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decrypt3DES(base64Decode(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptHexString3DES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decrypt3DES(hexStringToBytes(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decrypt3DES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    /**
     * AES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encryptAESToBase64(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static String encryptAESToHexString(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return toHexString(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES 加密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] encryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }

    /**
     * AES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptBase64AES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    /**
     * AES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptHexStringAES(final String data, final byte[] key, final String transformation, final byte[] iv) {
        return decryptAES(hexStringToBytes(data), key, transformation, iv);
    }

    /**
     * AES 解密
     * @param data
     * @param key
     * @param transformation
     * @param iv
     * @return
     */
    public static byte[] decryptAES(final byte[] data, final byte[] key, final String transformation, final byte[] iv) {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }

    /**
     * 对称加密模版方法
     * @param data
     * @param key
     * @param algorithm
     * @param transformation
     * @param isEncrypt
     * @return
     */
    private static byte[] symmetricTemplate(final byte[] data, final byte[] key, final String algorithm,
                                        final String transformation, final byte[] iv, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKey secretKey;
            if ("DES".equals(algorithm)) {
                DESKeySpec desKey = new DESKeySpec(key);
                SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
                secretKey = keyFactory.generateSecret(desKey);
            } else {
                secretKey = new SecretKeySpec(key, algorithm);
            }
            Cipher cipher = Cipher.getInstance(transformation);
            if (iv == null || iv.length == 0) {
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey);
            } else {
                AlgorithmParameterSpec params = new IvParameterSpec(iv);
                cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, secretKey, params);
            }
            return cipher.doFinal(data);
        } catch (Throwable e) {
            JCLogUtils.eTag(TAG, e, "symmetricTemplate");
            return null;
        }
    }

    // =

    /**
     * RSA 加密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static byte[] encryptRSAToBase64(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA 加密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static String encryptRSAToHexString(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return toHexString(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA 加密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static byte[] encryptRSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }

    /**
     * RSA 解密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static byte[] decryptBase64RSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation);
    }

    /**
     * RSA 解密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static byte[] decryptHexStringRSA(final String data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return decryptRSA(hexStringToBytes(data), key, isPublicKey, transformation);
    }

    /**
     * RSA 解密
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @return
     */
    public static byte[] decryptRSA(final byte[] data, final byte[] key, final boolean isPublicKey, final String transformation) {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    /**
     * RSA 加/解密模版方法
     * @param data
     * @param key
     * @param isPublicKey
     * @param transformation
     * @param isEncrypt
     * @return
     */
    private static byte[] rsaTemplate(final byte[] data, final byte[] key,
                                      final boolean isPublicKey, final String transformation, final boolean isEncrypt) {
        if (data == null || data.length == 0 || key == null || key.length == 0) {
            return null;
        }
        try {
            Key rsaKey;
            if (isPublicKey) {
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            } else {
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
                rsaKey = KeyFactory.getInstance("RSA").generatePrivate(keySpec);
            }
            if (rsaKey == null) return null;
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, rsaKey);
            int len = data.length;
            int maxLen = isEncrypt ? 117 : 128;
            int count = len / maxLen;
            if (count > 0) {
                byte[] ret = new byte[0];
                byte[] buff = new byte[maxLen];
                int index = 0;
                for (int i = 0; i < count; i++) {
                    System.arraycopy(data, index, buff, 0, maxLen);
                    ret = joins(ret, cipher.doFinal(buff));
                    index += maxLen;
                }
                if (index != len) {
                    int restLen = len - index;
                    buff = new byte[restLen];
                    System.arraycopy(data, index, buff, 0, restLen);
                    ret = joins(ret, cipher.doFinal(buff));
                }
                return ret;
            } else {
                return cipher.doFinal(data);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "rsaTemplate");
        }
        return null;
    }

    // =

    private static byte[] joins(final byte[] prefix, final byte[] suffix) {
        byte[] ret = new byte[prefix.length + suffix.length];
        System.arraycopy(prefix, 0, ret, 0, prefix.length);
        System.arraycopy(suffix, 0, ret, prefix.length, suffix.length);
        return ret;
    }

    // 用于建立十六进制字符的输出的小写字符数组
    public static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 用于建立十六进制字符的输出的大写字符数组
    public static final char HEX_DIGITS_UPPER[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 进行十六进制转换
     * @param data
     * @return
     */
    private static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 进行十六进制转换
     * @param data
     * @param hexDigits
     * @return
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

    /**
     * 十六进制字符串 转换byte数组
     * @param hexString
     * @return
     */
    private static byte[] hexStringToBytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hexToInt(hexBytes[i]) << 4 | hexToInt(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hexToInt(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static byte[] base64Encode(final byte[] input) {
        return Base64.encode(input, Base64.NO_WRAP);
    }

    private static byte[] base64Decode(final byte[] input) {
        return Base64.decode(input, Base64.NO_WRAP);
    }

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return
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