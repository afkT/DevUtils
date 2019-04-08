package dev.utils.common;

import java.util.Arrays;

import dev.utils.JCLogUtils;

/**
 * detail: 转换工具类
 * Created by Ttt
 */
public final class ConverUtils {

    private ConverUtils() {
    }

    // 日志 TAG
    private static final String TAG = ConverUtils.class.getSimpleName();

    // byte 是字节数据类型、有符号型的、占1个字节、大小范围为-128——127
    // char 是字符数据类型、无符号型的、占2个字节(unicode码)、大小范围为0-65535

    // byte[] (-128) - 127
    // 当大于127时则开始缩进  127 = 127, 128 = -128 , 129 = -127

    /**
     * char 数组 转 String
     * @param data
     * @param defaultStr
     * @return
     */
    public static String toString(final char[] data, final String defaultStr) {
        if (data != null) {
            try {
                return new String(data);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return defaultStr;
    }

    /**
     * byte 数组 转 String
     * @param data
     * @param defaultStr
     * @return
     */
    public static String toString(final byte[] data, final String defaultStr) {
        if (data != null) {
            try {
                return new String(data);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return defaultStr;
    }

    /**
     * char 转 String
     * @param data
     * @return
     * 97 - 122 = a-z, 48-57 = 0-9
     * toString((char) 97); = a
     */
    public static String toString(final char data) {
        try {
            return Character.toString(data);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toString");
        }
        return null;
    }

    /**
     * Object 转 String
     * @param object
     * @return
     */
    public static String toString(final Object object, final String defaultStr) {
        if (object != null) {
            try {
                if (object instanceof String) {
                    return (String) object;
                } else {
                    Class<?> clazz = object.getClass();
                    // 判断是否数组类型
                    if (clazz.isArray()) {
                        // == 基本数据类型 ==
                        if (clazz.isAssignableFrom(int[].class)) {
                            return Arrays.toString((int[]) object);
                        } else if (clazz.isAssignableFrom(boolean[].class)) {
                            return Arrays.toString((boolean[]) object);
                        } else if (clazz.isAssignableFrom(long[].class)) {
                            return Arrays.toString((long[]) object);
                        } else if (clazz.isAssignableFrom(double[].class)) {
                            return Arrays.toString((double[]) object);
                        } else if (clazz.isAssignableFrom(float[].class)) {
                            return Arrays.toString((float[]) object);
                        } else if (clazz.isAssignableFrom(byte[].class)) {
                            return Arrays.toString((byte[]) object);
                        } else if (clazz.isAssignableFrom(char[].class)) {
                            return Arrays.toString((char[]) object);
                        } else if (clazz.isAssignableFrom(short[].class)) {
                            return Arrays.toString((short[]) object);
                        }
                        // == 基本类型封装 ==
                        if (clazz.isAssignableFrom(Integer[].class)) {
                            return Arrays.toString((Integer[]) object);
                        } else if (clazz.isAssignableFrom(Boolean[].class)) {
                            return Arrays.toString((Boolean[]) object);
                        } else if (clazz.isAssignableFrom(Long[].class)) {
                            return Arrays.toString((Long[]) object);
                        } else if (clazz.isAssignableFrom(Double[].class)) {
                            return Arrays.toString((Double[]) object);
                        } else if (clazz.isAssignableFrom(Float[].class)) {
                            return Arrays.toString((Float[]) object);
                        } else if (clazz.isAssignableFrom(Byte[].class)) {
                            return Arrays.toString((Byte[]) object);
                        } else if (clazz.isAssignableFrom(Character[].class)) {
                            return Arrays.toString((Character[]) object);
                        } else if (clazz.isAssignableFrom(Short[].class)) {
                            return Arrays.toString((Short[]) object);
                        }
                    }
                    return object.toString();
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return defaultStr;
    }

    /**
     * 字符串 转 int
     * @param str
     * @param defaultValue
     * @return
     */
    public static int toInt(final String str, final int defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return defaultValue;
    }

    /**
     * 字符串 转 boolean
     * @param str
     * @param defaultValue
     * @return
     */
    public static boolean toBoolean(final String str, final boolean defaultValue) {
        if (str == null) return defaultValue;
        try {
            // 判断是否0
            if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("1")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBoolean");
        }
        return defaultValue;
    }

    /**
     * 字符串 转 float
     * @param str
     * @param defaultValue
     * @return
     */
    public static float toFloat(final String str, final float defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toFloat");
        }
        return defaultValue;
    }

    /**
     * 字符串 转 double
     * @param str
     * @param defaultValue
     * @return
     */
    public static double toDouble(final String str, final double defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toDouble");
        }
        return defaultValue;
    }

    /**
     * 字符串 转 long
     * @param str
     * @param defaultValue
     * @return
     */
    public static long toLong(final String str, final long defaultValue) {
        if (str == null) return defaultValue;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toLong");
        }
        return defaultValue;
    }

    // == 转换对象 ==

    /**
     * 基本类型对象 转 int
     * @param value
     * @param defaultValue
     * @return
     */
    public static int toInt(final Integer value, final int defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return defaultValue;
    }

    /**
     * 基本类型对象 转 boolean
     * @param value
     * @return
     */
    public static boolean toBoolean(final Boolean value) {
        return (value != null && value);
    }

    /**
     * 基本类型对象 转 float
     * @param value
     * @param defaultValue
     * @return
     */
    public static float toFloat(final Float value, final float defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toFloat");
        }
        return defaultValue;
    }

    /**
     * 基本类型对象 转 double
     * @param value
     * @param defaultValue
     * @return
     */
    public static double toDouble(final Double value, final double defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toDouble");
        }
        return defaultValue;
    }

    /**
     * 基本类型对象 转 long
     * @param value
     * @param defaultValue
     * @return
     */
    public static long toLong(final Long value, final long defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toLong");
        }
        return defaultValue;
    }

    // == 平常其他 ==

    /**
     * char 转换 int
     * @param value
     * @return
     */
    public static int toInt(final char value) {
        return (int) value;
    }

    /**
     * 字符串 获取 char (默认第一位)
     * @param str
     * @param defaultValue
     * @return
     */
    public static char toChar(final String str, final char defaultValue) {
        return toChar(str, 0, defaultValue);
    }

    /**
     * 字符串 获取 char
     * @param str
     * @param pos
     * @param defaultValue
     * @return
     */
    public static char toChar(final String str, final int pos, final char defaultValue) {
        if (str == null || pos < 0) return defaultValue;
        try {
            return str.charAt(pos);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toChar");
        }
        return defaultValue;
    }

    /**
     * char 转换 unicode 编码
     * @param value
     * @return
     * toCharInt('a') = 97
     */
    public static int toCharInt(final char value) {
        return (int) value;
    }

    /**
     * 字符串 获取 char[]
     * @param str
     * @return
     */
    public static char[] toChars(final String str) {
        if (str == null) return null;
        try {
            return str.toCharArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toChars");
        }
        return null;
    }

    /**
     * 字符串 获取 byte[]
     * @param str
     * @return
     */
    public static byte[] toBytes(final String str) {
        if (str != null) {
            try {
                return str.getBytes();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toBytes");
            }
        }
        return null;
    }

    // ==============================
//    Binary(二进制) toBinaryString
//    Oct(八进制)
//    Dec(十进制)
//    Hex(十六进制)
//
//    https://baike.baidu.com/item/ASCII/309296?fr=aladdin#3

    // 以0x开始的数据表示16进制

    /**
     * 一个 int 参数的字符串表示形式在基数为16的无符号 int
     * @param value
     * @return
     * 例如 -> 传入 0x1f603 => toHexString(0x1f603); 返回: 1f603
     */
    public static String toHexString(final int value) {
        try {
            return Integer.toHexString(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    /**
     * 一个 long 参数的字符串表示形式在基数为16的无符号 long
     * @param value
     * @return
     */
    public static String toHexString(final long value) {
        try {
            return Long.toHexString(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    /**
     * 一个 double 参数的字符串表示形式在基数为16的无符号 double
     * @param value
     * @return
     */
    public static String toHexString(final double value) {
        try {
            return Double.toHexString(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    /**
     * 一个 float 参数的字符串表示形式在基数为16的无符号 float
     * @param value
     * @return
     */
    public static String toHexString(final float value) {
        try {
            return Float.toHexString(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    // --

    /**
     * 字符串转换对应的进制
     * @param str
     * @param radix
     * @return
     * 如: parseInt("1f603", 16) = 128515
     */
    public static int parseInt(final String str, final int radix) {
        if (str == null) return -1;
        try {
            return Integer.parseInt(str, radix);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseInt");
        }
        return -1;
    }

    // ==

    // toHexString(0x1f603) = 1f603
    // parseInt("1f603", 16) = 128515
    // toHexString(128515) = 1f603

    // ==

    // ======================================================================

    // 小写
    public static final char HEX_DIGITS [] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    // 大写
    public static final char HEX_DIGITS_UPPER [] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    /**
     * 进行十六进制转换
     * @param data
     * @param hexDigits
     * @return
     */
    public static String toHexString(final byte[] data, final char[] hexDigits) {
        if (data == null || hexDigits == null) return null;
        try {
            StringBuilder builder = new StringBuilder(data.length * 2);
            for (int i = 0, len = data.length; i < len; i++) {
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
     * @param hex
     * @return
     */
    public static byte[] hexStringToBytes(final String hex) {
        try {
            if (isSpace(hex)) return null;
            String hexString = hex;
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
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "hexStringToBytes");
        }
        return null;
    }

    /**
     * 十六进制 char 转换 int
     * @param hexChar
     * @return
     */
    public static int hexToInt(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // ==

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
     * byte 数组 转换 char 数组, 并且进行补码
     * @param data The bytes.
     * @return chars
     */
    public static char[] bytesToChars(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (data[i] & 0xff);
        }
        return chars;
    }

    /**
     * char 数组 转换 byte 数组
     * @param data The chars.
     * @return bytes
     */
    public static byte[] charsToBytes(final char[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len <= 0) return null;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (data[i]);
        }
        return bytes;
    }

    /**
     * byte 数组转换16进制字符串
     * @param data
     * @return
     */
    public static String bytesToHexString(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[data[i] >>> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[data[i] & 0x0f];
        }
        return new String(ret);
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
