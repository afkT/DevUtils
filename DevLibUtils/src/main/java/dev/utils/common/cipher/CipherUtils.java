package dev.utils.common.cipher;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dev.utils.JCLogUtils;

/**
 * detail: 加密工具类
 * @author Ttt
 */
public final class CipherUtils {

    private CipherUtils() {
    }

    // 日志 TAG
    private static final String TAG = CipherUtils.class.getSimpleName();

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
     * @param cipher 加解密中间层
     * @return 加密后的十六进制字符串
     */
    public static String encrypt(final Object object, final Cipher cipher) {
        if (object == null) return null;
        byte[] bytes = objectToBytes(object);
        if (cipher != null) bytes = cipher.encrypt(bytes);
        return toHexString(bytes);
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
     * @param cipher 加解密中间层
     * @return 解密后的对象
     */
    public static Object decrypt(final String hex, final Cipher cipher) {
        if (hex == null) return null;
        byte[] bytes = decodeHex(hex.toCharArray());
        if (cipher != null) bytes = cipher.decrypt(bytes);
        return bytesToObject(bytes);
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ================
    // = ConvertUtils =
    // ================

    // 用于建立十六进制字符的输出的小写字符数组
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data byte[]
     * @return 十六进制字符串
     */
    private static String toHexString(final byte[] data) {
        return toHexString(data, HEX_DIGITS);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      byte[]
     * @param hexDigits {@link CipherUtils#HEX_DIGITS}
     * @return 十六进制字符串
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
     * 将十六进制字符数组解码
     * @param data 十六进制 char[]
     * @return 十六进制转(解)码后的数据
     */
    private static byte[] decodeHex(final char[] data) {
        if (data == null) return null;
        try {
            int len = data.length;
            byte[] out = new byte[len >> 1];
            // 十六进制由两个字符组成
            for (int i = 0, j = 0; j < len; i++) {
                int d = toDigit(data[j], j) << 4;
                j++;
                d = d | toDigit(data[j], j);
                j++;
                out[i] = (byte) (d & 0xFF);
            }
            return out;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "decodeHex");
        }
        return null;
    }

    /**
     * 将十六进制字符转换成一个整数
     * @param ch    十六进制 char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws Exception 当 ch 不是一个合法的十六进制字符时, 抛出运行时异常
     */
    private static int toDigit(final char ch, final int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

    /**
     * byte[] 转为 Object
     * @param bytes byte[]
     * @return {@link Object}
     */
    private static Object bytesToObject(final byte[] bytes) {
        if (bytes != null) {
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return ois.readObject();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "bytesToObject");
            } finally {
                closeIOQuietly(ois);
            }
        }
        return null;
    }

    /**
     * Object 转为 byte[]
     * @param object Object
     * @return byte[]
     */
    private static byte[] objectToBytes(final Object object) {
        if (object != null) {
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                return baos.toByteArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "objectToBytes");
            } finally {
                closeIOQuietly(oos);
            }
        }
        return null;
    }

    // ==============
    // = CloseUtils =
    // ==============

    /**
     * 安静关闭 IO
     * @param closeables Closeable[]
     */
    private static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignore) {
                }
            }
        }
    }
}
