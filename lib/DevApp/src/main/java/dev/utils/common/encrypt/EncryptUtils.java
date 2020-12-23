package dev.utils.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
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
import dev.utils.common.ArrayUtils;
import dev.utils.common.CloseUtils;
import dev.utils.common.ConvertUtils;
import dev.utils.common.StringUtils;
import dev.utils.common.cipher.Base64;

/**
 * detail: 加解密通用工具类
 * @author Blankj
 * @author Ttt
 */
public final class EncryptUtils {

    private EncryptUtils() {
    }

    // 日志 TAG
    private static final String TAG = EncryptUtils.class.getSimpleName();

    /**
     * MD2 加密
     * @param data 待加密数据
     * @return MD2 加密后的数据
     */
    public static byte[] encryptMD2(final byte[] data) {
        return hashTemplate(data, "MD2");
    }

    /**
     * MD2 加密
     * @param data 待加密数据
     * @return MD2 加密后的十六进制字符串
     */
    public static String encryptMD2ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptMD2ToHexString(data.getBytes());
    }

    /**
     * MD2 加密
     * @param data 待加密数据
     * @return MD2 加密后的十六进制字符串
     */
    public static String encryptMD2ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptMD2(data));
    }

    // =

    /**
     * MD5 加密
     * @param data 待加密数据
     * @return MD5 加密后的数据
     */
    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }

    /**
     * MD5 加密
     * @param data 待加密数据
     * @return MD5 加密后的十六进制字符串
     */
    public static String encryptMD5ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptMD5ToHexString(data.getBytes());
    }

    /**
     * MD5 加密
     * @param data 待加密数据
     * @param salt salt
     * @return MD5 加密后的十六进制字符串
     */
    public static String encryptMD5ToHexString(
            final String data,
            final String salt
    ) {
        if (data == null && salt == null) return null;
        if (salt == null) return ConvertUtils.toHexString(encryptMD5(data.getBytes()));
        if (data == null) return ConvertUtils.toHexString(encryptMD5(salt.getBytes()));
        return ConvertUtils.toHexString(encryptMD5((data + salt).getBytes()));
    }

    // =

    /**
     * MD5 加密
     * @param data 待加密数据
     * @return MD5 加密后的十六进制字符串
     */
    public static String encryptMD5ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptMD5(data));
    }

    /**
     * MD5 加密
     * @param data 待加密数据
     * @param salt salt
     * @return MD5 加密后的十六进制字符串
     */
    public static String encryptMD5ToHexString(
            final byte[] data,
            final byte[] salt
    ) {
        if (data == null && salt == null) return null;
        if (salt == null) return ConvertUtils.toHexString(encryptMD5(data));
        if (data == null) return ConvertUtils.toHexString(encryptMD5(salt));
        // 拼接数据
        byte[] bytes = ArrayUtils.arrayCopy(data, salt);
        return ConvertUtils.toHexString(encryptMD5(bytes));
    }

    // =

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值
     */
    public static byte[] encryptMD5File(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return encryptMD5File(file);
    }

    /**
     * 获取文件 MD5 值
     * @param filePath 文件路径
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String encryptMD5FileToHexString(final String filePath) {
        File file = StringUtils.isSpace(filePath) ? null : new File(filePath);
        return encryptMD5FileToHexString(file);
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值转十六进制字符串
     */
    public static String encryptMD5FileToHexString(final File file) {
        return ConvertUtils.toHexString(encryptMD5File(file));
    }

    /**
     * 获取文件 MD5 值
     * @param file 文件
     * @return 文件 MD5 值 byte[]
     */
    public static byte[] encryptMD5File(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis    = new FileInputStream(file);
            MessageDigest   digest = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, digest);
            byte[] buffer = new byte[256 * 1024];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            digest = dis.getMessageDigest();
            return digest.digest();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "encryptMD5File");
            return null;
        } finally {
            CloseUtils.closeIOQuietly(dis);
        }
    }

    // =

    /**
     * SHA1 加密
     * @param data 待加密数据
     * @return SHA1 加密后的数据
     */
    public static byte[] encryptSHA1(final byte[] data) {
        return hashTemplate(data, "SHA-1");
    }

    /**
     * SHA1 加密
     * @param data 待加密数据
     * @return SHA1 加密后的数据转十六进制字符串
     */
    public static String encryptSHA1ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptSHA1ToHexString(data.getBytes());
    }

    /**
     * SHA1 加密
     * @param data 待加密数据
     * @return SHA1 加密后的数据转十六进制字符串
     */
    public static String encryptSHA1ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptSHA1(data));
    }

    // =

    /**
     * SHA224 加密
     * @param data 待加密数据
     * @return SHA224 加密后的数据
     */
    public static byte[] encryptSHA224(final byte[] data) {
        return hashTemplate(data, "SHA224");
    }

    /**
     * SHA224 加密
     * @param data 待加密数据
     * @return SHA224 加密后的数据转十六进制字符串
     */
    public static String encryptSHA224ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptSHA224ToHexString(data.getBytes());
    }

    /**
     * SHA224 加密
     * @param data 待加密数据
     * @return SHA224 加密后的数据转十六进制字符串
     */
    public static String encryptSHA224ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptSHA224(data));
    }

    // =

    /**
     * SHA256 加密
     * @param data 待加密数据
     * @return SHA256 加密后的数据
     */
    public static byte[] encryptSHA256(final byte[] data) {
        return hashTemplate(data, "SHA-256");
    }

    /**
     * SHA256 加密
     * @param data 待加密数据
     * @return SHA256 加密后的数据转十六进制字符串
     */
    public static String encryptSHA256ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptSHA256ToHexString(data.getBytes());
    }

    /**
     * SHA256 加密
     * @param data 待加密数据
     * @return SHA256 加密后的数据转十六进制
     */
    public static String encryptSHA256ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptSHA256(data));
    }

    // =

    /**
     * SHA384 加密
     * @param data 待加密数据
     * @return SHA384 加密后的数据
     */
    public static byte[] encryptSHA384(final byte[] data) {
        return hashTemplate(data, "SHA-384");
    }

    /**
     * SHA384 加密
     * @param data 待加密数据
     * @return SHA384 加密后的数据转十六进制
     */
    public static String encryptSHA384ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptSHA384ToHexString(data.getBytes());
    }

    /**
     * SHA384 加密
     * @param data 待加密数据
     * @return SHA384 加密后的数据转十六进制
     */
    public static String encryptSHA384ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptSHA384(data));
    }

    // =

    /**
     * SHA512 加密
     * @param data 待加密数据
     * @return SHA512 加密后的数据
     */
    public static byte[] encryptSHA512(final byte[] data) {
        return hashTemplate(data, "SHA-512");
    }

    /**
     * SHA512 加密
     * @param data 待加密数据
     * @return SHA512 加密后的数据转十六进制
     */
    public static String encryptSHA512ToHexString(final String data) {
        if (data == null || data.length() == 0) return null;
        return encryptSHA512ToHexString(data.getBytes());
    }

    /**
     * SHA512 加密
     * @param data 待加密数据
     * @return SHA512 加密后的数据转十六进制
     */
    public static String encryptSHA512ToHexString(final byte[] data) {
        return ConvertUtils.toHexString(encryptSHA512(data));
    }

    /**
     * Hash 加密模版方法
     * @param data      待加密数据
     * @param algorithm 算法
     * @return 指定加密算法加密后的数据
     */
    public static byte[] hashTemplate(
            final byte[] data,
            final String algorithm
    ) {
        if (data == null || data.length == 0) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hashTemplate");
            return null;
        }
    }

    // =

    /**
     * HmacMD5 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacMD5 加密后的数据
     */
    public static byte[] encryptHmacMD5(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacMD5");
    }

    /**
     * HmacMD5 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacMD5 加密后的数据转十六进制
     */
    public static String encryptHmacMD5ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacMD5ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacMD5 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacMD5 加密后的数据转十六进制
     */
    public static String encryptHmacMD5ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacMD5(data, key));
    }

    // =

    /**
     * HmacSHA1 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA1 加密后的数据
     */
    public static byte[] encryptHmacSHA1(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacSHA1");
    }

    /**
     * HmacSHA1 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA1 加密后的数据转十六进制
     */
    public static String encryptHmacSHA1ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacSHA1ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA1 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA1 加密后的数据转十六进制
     */
    public static String encryptHmacSHA1ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacSHA1(data, key));
    }

    // =

    /**
     * HmacSHA224 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA224 加密后的数据
     */
    public static byte[] encryptHmacSHA224(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacSHA224");
    }

    /**
     * HmacSHA224 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA224 加密后的数据转十六进制
     */
    public static String encryptHmacSHA224ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacSHA224ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA224 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA224 加密后的数据转十六进制
     */
    public static String encryptHmacSHA224ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacSHA224(data, key));
    }

    // =

    /**
     * HmacSHA256 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA256 加密后的数据
     */
    public static byte[] encryptHmacSHA256(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacSHA256");
    }

    /**
     * HmacSHA256 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA256 加密后的数据转十六进制
     */
    public static String encryptHmacSHA256ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacSHA256ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA256 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA256 加密后的数据转十六进制
     */
    public static String encryptHmacSHA256ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacSHA256(data, key));
    }

    // =

    /**
     * HmacSHA384 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA384 加密后的数据
     */
    public static byte[] encryptHmacSHA384(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacSHA384");
    }

    /**
     * HmacSHA384 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA384 加密后的数据转十六进制
     */
    public static String encryptHmacSHA384ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacSHA384ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA384 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA384 加密后的数据转十六进制
     */
    public static String encryptHmacSHA384ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacSHA384(data, key));
    }

    // =

    /**
     * HmacSHA512 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA512 加密后的数据
     */
    public static byte[] encryptHmacSHA512(
            final byte[] data,
            final byte[] key
    ) {
        return hmacTemplate(data, key, "HmacSHA512");
    }

    /**
     * HmacSHA512 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA512 加密后的数据转十六进制
     */
    public static String encryptHmacSHA512ToHexString(
            final String data,
            final String key
    ) {
        if (data == null || data.length() == 0 || key == null || key.length() == 0) return null;
        return encryptHmacSHA512ToHexString(data.getBytes(), key.getBytes());
    }

    /**
     * HmacSHA512 加密
     * @param data 待加密数据
     * @param key  密钥
     * @return HmacSHA512 加密后的数据转十六进制
     */
    public static String encryptHmacSHA512ToHexString(
            final byte[] data,
            final byte[] key
    ) {
        return ConvertUtils.toHexString(encryptHmacSHA512(data, key));
    }

    /**
     * Hmac 加密模版方法
     * @param data      待加密数据
     * @param key       密钥
     * @param algorithm 算法
     * @return 指定加密算法和密钥, 加密后的数据
     */
    public static byte[] hmacTemplate(
            final byte[] data,
            final byte[] key,
            final String algorithm
    ) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac           mac       = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hmacTemplate");
            return null;
        }
    }

    // =

    /**
     * DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return DES 加密后的数据
     */
    public static byte[] encryptDES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "DES", transformation, iv, true);
    }

    /**
     * DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return DES 加密后的数据转 Base64
     */
    public static byte[] encryptDESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return base64Encode(encryptDES(data, key, transformation, iv));
    }

    /**
     * DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return DES 加密后的数据转十六进制
     */
    public static String encryptDESToHexString(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return ConvertUtils.toHexString(encryptDES(data, key, transformation, iv));
    }

    // =

    /**
     * DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return DES 解密后的数据
     */
    public static byte[] decryptDES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "DES", transformation, iv, false);
    }

    /**
     * DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return Base64 解码后, 在进行 DES 解密后的数据
     */
    public static byte[] decryptDESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decryptDES(base64Decode(data), key, transformation, iv);
    }

    /**
     * DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 十六进制转换后, 在进行 DES 解密后的数据
     */
    public static byte[] decryptDESToHexString(
            final String data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decryptDES(ConvertUtils.decodeHex(data), key, transformation, iv);
    }

    // =

    /**
     * 3DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 3DES 加密后的数据
     */
    public static byte[] encrypt3DES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, true);
    }

    /**
     * 3DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 3DES 加密后的数据转 Base64
     */
    public static byte[] encrypt3DESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return base64Encode(encrypt3DES(data, key, transformation, iv));
    }

    /**
     * 3DES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 3DES 加密后的数据转十六进制
     */
    public static String encrypt3DESToHexString(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return ConvertUtils.toHexString(encrypt3DES(data, key, transformation, iv));
    }

    // =

    /**
     * 3DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 3DES 解密后的数据
     */
    public static byte[] decrypt3DES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "DESede", transformation, iv, false);
    }

    /**
     * 3DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return Base64 解码后, 在进行 3DES 解密后的数据
     */
    public static byte[] decrypt3DESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decrypt3DES(base64Decode(data), key, transformation, iv);
    }

    /**
     * 3DES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 十六进制转换后, 在进行 3DES 解密后的数据
     */
    public static byte[] decrypt3DESToHexString(
            final String data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decrypt3DES(ConvertUtils.decodeHex(data), key, transformation, iv);
    }

    // =

    /**
     * AES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return AES 加密后的数据
     */
    public static byte[] encryptAES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "AES", transformation, iv, true);
    }

    /**
     * AES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return AES 加密后的数据转 Base64
     */
    public static byte[] encryptAESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return base64Encode(encryptAES(data, key, transformation, iv));
    }

    /**
     * AES 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return AES 加密后的数据转十六进制
     */
    public static String encryptAESToHexString(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return ConvertUtils.toHexString(encryptAES(data, key, transformation, iv));
    }

    // =

    /**
     * AES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return AES 解密后的数据
     */
    public static byte[] decryptAES(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return symmetricTemplate(data, key, "AES", transformation, iv, false);
    }

    /**
     * AES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return Base64 解码后, 在进行 AES 解密后的数据
     */
    public static byte[] decryptAESToBase64(
            final byte[] data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decryptAES(base64Decode(data), key, transformation, iv);
    }

    /**
     * AES 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @return 十六进制转换后, 在进行 AES 解密后的数据
     */
    public static byte[] decryptAESToHexString(
            final String data,
            final byte[] key,
            final String transformation,
            final byte[] iv
    ) {
        return decryptAES(ConvertUtils.decodeHex(data), key, transformation, iv);
    }

    /**
     * 对称加密模版方法
     * @param data           待加解密数据
     * @param key            密钥
     * @param algorithm      算法
     * @param transformation {@link Cipher#getInstance} transformation
     * @param iv             算法参数 {@link AlgorithmParameterSpec}
     * @param isEncrypt      是否加密处理
     * @return 指定加密算法, 加解密后的数据
     */
    public static byte[] symmetricTemplate(
            final byte[] data,
            final byte[] key,
            final String algorithm,
            final String transformation,
            final byte[] iv,
            final boolean isEncrypt
    ) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKey secretKey;
            if ("DES".equals(algorithm)) {
                DESKeySpec       desKey     = new DESKeySpec(key);
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
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "symmetricTemplate");
            return null;
        }
    }

    // =

    /**
     * RSA 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return RSA 加密后的数据
     */
    public static byte[] encryptRSA(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return rsaTemplate(data, key, isPublicKey, transformation, true);
    }

    /**
     * RSA 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return RSA 加密后的数据转 Base64
     */
    public static byte[] encryptRSAToBase64(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return base64Encode(encryptRSA(data, key, isPublicKey, transformation));
    }

    /**
     * RSA 加密
     * @param data           待加密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return RSA 加密后的数据转十六进制
     */
    public static String encryptRSAToHexString(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return ConvertUtils.toHexString(encryptRSA(data, key, isPublicKey, transformation));
    }

    // =

    /**
     * RSA 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return RSA 解密后的数据
     */
    public static byte[] decryptRSA(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return rsaTemplate(data, key, isPublicKey, transformation, false);
    }

    /**
     * RSA 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return Base64 解码后, 在进行 RSA 解密后的数据
     */
    public static byte[] decryptRSAToBase64(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return decryptRSA(base64Decode(data), key, isPublicKey, transformation);
    }

    /**
     * RSA 解密
     * @param data           待解密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @return 十六进制转换后, 在进行 RSA 解密后的数据
     */
    public static byte[] decryptRSAToHexString(
            final String data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation
    ) {
        return decryptRSA(ConvertUtils.decodeHex(data), key, isPublicKey, transformation);
    }

    /**
     * RSA 加解密模版方法
     * @param data           待加解密数据
     * @param key            密钥
     * @param isPublicKey    {@code true} {@link X509EncodedKeySpec}, {@code false} {@link PKCS8EncodedKeySpec}
     * @param transformation {@link Cipher#getInstance} transformation
     * @param isEncrypt      是否加密处理
     * @return 指定加密算法, 加解密后的数据
     */
    public static byte[] rsaTemplate(
            final byte[] data,
            final byte[] key,
            final boolean isPublicKey,
            final String transformation,
            final boolean isEncrypt
    ) {
        if (data == null || key == null) return null;
        try {
            int dataLength = data.length;
            int keyLength  = key.length;
            if (dataLength == 0 || keyLength == 0) return null;

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
            int maxLen = isEncrypt ? 117 : 128;
            int count  = dataLength / maxLen;
            if (count > 0) {
                byte[] ret    = new byte[0];
                byte[] buffer = new byte[maxLen];
                int    index  = 0;
                for (int i = 0; i < count; i++) {
                    System.arraycopy(data, index, buffer, 0, maxLen);
                    ret = ArrayUtils.arrayCopy(ret, cipher.doFinal(buffer));
                    index += maxLen;
                }
                if (index != dataLength) {
                    int restLen = dataLength - index;
                    buffer = new byte[restLen];
                    System.arraycopy(data, index, buffer, 0, restLen);
                    ret = ArrayUtils.arrayCopy(ret, cipher.doFinal(buffer));
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

    // ===========
    // = 私有方法 =
    // ===========

    /**
     * Base64 编码
     * @param input 待编码数据
     * @return Base64 编码后的 byte[]
     */
    private static byte[] base64Encode(final byte[] input) {
        if (input == null) return null;
        return Base64.encode(input, Base64.NO_WRAP);
    }

    /**
     * Base64 解码
     * @param input 待解码数据
     * @return Base64 解码后的 byte[]
     */
    private static byte[] base64Decode(final byte[] input) {
        if (input == null) return null;
        return Base64.decode(input, Base64.NO_WRAP);
    }
}