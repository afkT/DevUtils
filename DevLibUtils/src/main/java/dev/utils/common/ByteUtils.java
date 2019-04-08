package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import dev.utils.JCLogUtils;

/**
 * detail: 字节工具类，提供一些有关字节的便捷方法
 * Created by Ttt
 * (01)、位移加密：static void byteJiaMi(byte[] bytes)
 * (02)、从bytes上截取一段：static byte[] cutOut(byte[] bytes, int off, int length)
 * http://www.runoob.com/java/java-operators.html
 */
public final class ByteUtils {

    private ByteUtils() {
    }

    // 日志 TAG
    private static final String TAG = ByteUtils.class.getSimpleName();

    // 按位补运算符 例子：
    // ByteUtils.byteToBit(new byte[] { 1 }) = 00000001 (二进制字符串)
    // 进行反转 变成 11111110
    // 在通过二进制字符串转换 byte 数组  ByteUtils.bitsToBytes("11111110") => byte[] { -2 }
    // ~1 => -2    结果是正常的

    /**
     * 位移 加/解密 （共用同一个方法）
     * @param data
     */
    public static void byteJiaMi(final byte[] data) {
        if (data == null) return;
        for (int i = 0, len = data.length; i < len; i++) {
            int a = data[i];
            a = ~a; // 按位补运算符 => 翻转操作数的每一位，即0变成1，1变成0,再通过反转后的二进制初始化回16进制
            data[i] = (byte) a;
        }
    }

    /**
     * 字符串转数组
     * @param str
     * @return
     */
    public static byte[] hexStrToBytes(final String str) {
        if (str == null || str.length() == 0) return null;
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0, len = byteArray.length; i < len; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * 从 byte[] 上截取一段
     * @param data
     * @param off    起始
     * @param length 个数
     * @return byte[]
     */
    public static byte[] cutOut(final byte[] data, final int off, final int length) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            byte[] bytes = new byte[length];
            System.arraycopy(data, off, bytes, 0, length);
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "cutOut");
        }
        return null;
    }

    /**
     * 将 字节转换 为 二进制字符串
     * @param datas 字节数组
     * @return 二进制字符串
     */
    public static String bytesToBits(final byte... datas) {
        if (datas == null || datas.length <= 0) return null;
        StringBuilder builder = new StringBuilder();
        for (byte value : datas) {
            for (int j = 7; j >= 0; --j) {
                builder.append(((value >> j) & 0x01) == 0 ? '0' : '1');
            }
        }
        return builder.toString();
//        try {
//            StringBuffer buffer = new StringBuffer();
//            int z, strLength;
//            String str;
//            for (int i = 0, len = datas.length; i < len; i++) {
//                z = datas[i];
//                z |= 256;
//                str = Integer.toBinaryString(z);
//                strLength = str.length();
//                buffer.append(str.substring(strLength - 8, strLength));
//            }
//            return buffer.toString();
//        } catch (Exception e) {
//            JCLogUtils.eTag(TAG, e, "bytesToBits");
//        }
//        return null;
    }

    /**
     * 二进制字符串, 转换成 byte[]
     * 例: "011000010111001101100100" 传入 bitsToBytes, 返回 byte[], 通过new String(byte()) 获取 asd => 配合 bytesToBits 使用
     * @param str The bits.
     * @return bytes
     */
    public static byte[] bitsToBytes(final String str) {
        if (str == null) return null;
        try {
            String data = str;
            int lenMod = data.length() % 8;
            int byteLen = data.length() / 8;
            // add "0" until length to 8 times
            if (lenMod != 0) {
                for (int i = lenMod; i < 8; i++) {
                    data = "0" + data;
                }
                byteLen++;
            }
            byte[] bytes = new byte[byteLen];
            for (int i = 0; i < byteLen; ++i) {
                for (int j = 0; j < 8; ++j) {
                    bytes[i] <<= 1;
                    bytes[i] |= data.charAt(i * 8 + j) - '0';
                }
            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bitsToBytes");
        }
        return null;
    }

    /**
     * 字节数组转换成 16进制字符串
     * @param data
     * @return
     */
    public static String getHex(final byte[] data) {
        if (data == null) return null;
        String HEXES = "0123456789ABCDEF";
        try {
            StringBuilder builder = new StringBuilder(2 * data.length);
            for (final byte b : data) {
                builder.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getHex");
        }
        return null;
    }

    /**
     * 将一个 short 转换成字节数组
     * @param sh short
     * @return 字节数组
     */
    public static byte[] valueOf(final short sh) {
        byte[] shortBuf = new byte[2];
        for (int i = 0; i < 2; i++) {
            int offset = (shortBuf.length - 1 - i) * 8;
            shortBuf[i] = (byte) ((sh >>> offset) & 0xff);
        }
        return shortBuf;
    }

    /**
     * 将一个int转换成字节数组
     * @param in int
     * @return 字节数组
     */
    public static byte[] valueOf(final int in) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((in >>> offset) & 0xFF);
        }
        return b;
    }

    /**
     * 从一个 byte[] 中截取一部分
     * @param data
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(final byte[] data, final int begin, final int count) {
        if (data == null || begin < 0 || count < 0) return null;
        try {
            byte[] bytes = new byte[count];
            for (int i = begin, len = begin + count; i < len; i++) {
                bytes[i - begin] = data[i];
            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subBytes");
        }
        return null;
    }


    /**
     * byte[] 转为 对象
     * @param bytes
     * @return
     */
    public static Object bytesToObject(final byte[] bytes) {
        if (bytes == null) return null;
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToObject");
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }

    /**
     * 对象 转为 byte[]
     * @param object
     * @return
     */
    public static byte[] objectToBytes(final Object object) {
        if (object == null) return null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "objectToBytes");
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (Exception e) {
                }
            }
        }
        return null;
    }
}
