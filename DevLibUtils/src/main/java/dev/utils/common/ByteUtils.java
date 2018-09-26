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

    private ByteUtils(){
    }

    // 日志TAG
    private static final String TAG = ByteUtils.class.getSimpleName();

    // 按位补运算符 例子：
    // ByteUtils.byteToBit(new byte[] { 1 }) = 00000001 (二进制字符串)
    // 进行反转 变成 11111110
    // 在通过二进制字符串转换 byte 数组  ByteUtils.bits2Bytes("11111110") => byte[] { -2 }
    // ~1 => -2    结果是正常的

    /**
     * (01)、位移加密、解密，调同一个方法
     * @param bytes
     */
    public static void byteJiaMi(byte[] bytes) {
        if (bytes == null){
            return;
        }
        for (int w = 0; w < bytes.length; w++) {
            int a = bytes[w];
            a = ~a; // 按位补运算符 => 翻转操作数的每一位，即0变成1，1变成0,再通过反转后的二进制初始化回16进制
            bytes[w] = (byte) a;
        }
    }

    /**
     * 字符串转数组
     * @param str
     * @return
     */
    public static byte[] hexStrToByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[0];
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            String subStr = str.substring(2 * i, 2 * i + 2);
            byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
        }
        return byteArray;
    }

    /**
     * (02)、从bytes上截取一段
     * @param bytes  母体
     * @param off    起始
     * @param length 个数
     * @return byte[]
     */
    public static byte[] cutOut(byte[] bytes, int off, int length) {
        byte[] bytess = new byte[length];
        System.arraycopy(bytes, off, bytess, 0, length);
        return bytess;
    }

    /**
     * 将字节转换为二进制字符串
     * @param bytes 字节数组
     * @return 二进制字符串
     */
    public static String byteToBit(byte... bytes) {
        StringBuffer sb = new StringBuffer();
        int z, len;
        String str;
        for (int w = 0; w < bytes.length; w++) {
            z = bytes[w];
            z |= 256;
            str = Integer.toBinaryString(z);
            len = str.length();
            sb.append(str.substring(len - 8, len));
        }
        return sb.toString();
    }

    /**
     * 二进制字符串, 转换成byte数组
     * 例: "011000010111001101100100" 传入 bits2Bytes, 返回 byte[], 通过new String(byte()) 获取 asd => 配合 bytes2Bits 使用
     * @param bits The bits.
     * @return bytes
     */
    public static byte[] bits2Bytes(String bits) {
        int lenMod = bits.length() % 8;
        int byteLen = bits.length() / 8;
        // add "0" until length to 8 times
        if (lenMod != 0) {
            for (int i = lenMod; i < 8; i++) {
                bits = "0" + bits;
            }
            byteLen++;
        }
        byte[] bytes = new byte[byteLen];
        for (int i = 0; i < byteLen; ++i) {
            for (int j = 0; j < 8; ++j) {
                bytes[i] <<= 1;
                bytes[i] |= bits.charAt(i * 8 + j) - '0';
            }
        }
        return bytes;
    }

    /**
     * 字节数组转换成16进制字符串
     * @param raw
     * @return
     */
    public static String getHex(byte[] raw) {
        String HEXES = "0123456789ABCDEF";
        if (raw == null) {
            return null;
        }
        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (final byte b : raw) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4))
                    .append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    /**
     * 将一个short转换成字节数组
     * @param sh short
     * @return 字节数组
     */
    public static byte[] valueOf(short sh) {
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
    public static byte[] valueOf(int in) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (b.length - 1 - i) * 8;
            b[i] = (byte) ((in >>> offset) & 0xFF);
        }
        return b;
    }

    /**
     * 从一个byte[]数组中截取一部分
     * @param src
     * @param begin
     * @param count
     * @return
     */
    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        for (int i = begin; i < begin + count; i++) {
            bs[i - begin] = src[i];
        }
        return bs;
    }


    /**
     * byte[] 转为 对象
     * @param bytes
     * @return
     */
    public static Object byteToObject(byte[] bytes) {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "byteToObject");
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
     * @param obj
     * @return
     */
    public static byte[] objectToByte(Object obj) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            return bos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "objectToByte");
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
