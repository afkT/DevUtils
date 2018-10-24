package dev.utils.common;

import java.util.Arrays;

import dev.utils.JCLogUtils;

/**
 * detail: 转换工具类
 * Created by Ttt
 */
public final class ConverUtils {

    private ConverUtils(){
    }

    // 日志TAG
    private static final String TAG = ConverUtils.class.getSimpleName();

    // byte是字节数据类型、有符号型的、占1个字节、大小范围为-128——127
    // char是字符数据类型、无符号型的、占2个字节(unicode码)、大小范围为0-65535

    // byte[] (-128) - 127
    // 当大于127时则开始缩进  127 = 127, 128 = -128 , 129 = -127

    /**
     * char 数组 转 String
     * @param chars
     * @param dfStr
     * @return
     */
    public static String toString(char[] chars, String dfStr){
        if (chars != null){
            try {
                return new String(chars);
            } catch (Exception e){
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return dfStr;
    }

    /**
     * byte 数组 转 String
     * @param bytes
     * @param dfStr
     * @return
     */
    public static String toString(byte[] bytes, String dfStr){
        if (bytes != null){
            try {
                return new String(bytes);
            } catch (Exception e){
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return dfStr;
    }

    /**
     * char 转 String
     * @param val
     * @return
     * --
     * 97 - 122 = a-z, 48-57 = 0-9
     * toString((char) 97); = a
     */
    public static String toString(char val){
        return Character.toString(val);
    }

    /**
     * Object 转 String
     * @param obj
     * @return
     */
    public static String toString(Object obj, String dfStr) {
        if (obj != null){
            // return (obj instanceof String ? (String) obj : obj.toString());
            try {
                if (obj instanceof String){
                    return (String) obj;
                } else {
                    Class<?> cla = obj.getClass();
                    // 判断是否数组类型
                    if (cla.isArray()){
                        // == 基本数据类型 ==
                        if (cla.isAssignableFrom(int[].class)){
                            return Arrays.toString((int[]) obj);
                        } else if (cla.isAssignableFrom(boolean[].class)){
                            return Arrays.toString((boolean[]) obj);
                        } else if (cla.isAssignableFrom(long[].class)){
                            return Arrays.toString((long[]) obj);
                        } else if (cla.isAssignableFrom(double[].class)){
                            return Arrays.toString((double[]) obj);
                        } else if (cla.isAssignableFrom(float[].class)){
                            return Arrays.toString((float[]) obj);
                        } else if (cla.isAssignableFrom(byte[].class)){
                            return Arrays.toString((byte[]) obj);
                        } else if (cla.isAssignableFrom(char[].class)){
                            return Arrays.toString((char[]) obj);
                        } else if (cla.isAssignableFrom(short[].class)){
                            return Arrays.toString((short[]) obj);
                        }
                        // == 基本类型封装 ==
                        if (cla.isAssignableFrom(Integer[].class)){
                            return Arrays.toString((Integer[]) obj);
                        } else if (cla.isAssignableFrom(Boolean[].class)){
                            return Arrays.toString((Boolean[]) obj);
                        } else if (cla.isAssignableFrom(Long[].class)){
                            return Arrays.toString((Long[]) obj);
                        } else if (cla.isAssignableFrom(Double[].class)){
                            return Arrays.toString((Double[]) obj);
                        } else if (cla.isAssignableFrom(Float[].class)){
                            return Arrays.toString((Float[]) obj);
                        } else if (cla.isAssignableFrom(Byte[].class)){
                            return Arrays.toString((Byte[]) obj);
                        } else if (cla.isAssignableFrom(Character[].class)){
                            return Arrays.toString((Character[]) obj);
                        } else if (cla.isAssignableFrom(Short[].class)){
                            return Arrays.toString((Short[]) obj);
                        }
                    }
                    return obj.toString();
                }
            } catch (Exception e){
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return dfStr;
    }

    /**
     * 字符串 转 int
     * @param str
     * @param dfValue
     * @return
     */
    public static int toInt(String str, int dfValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return dfValue;
    }

    /**
     * 字符串 转 boolean
     * @param str
     * @param dfValue
     * @return
     */
    public static boolean toBoolean(String str, boolean dfValue){
        try {
            // 判断是否0
            if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("1")){
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBoolean");
        }
        return dfValue;
    }

    /**
     * 字符串 转 float
     * @param str
     * @param dfValue
     * @return
     */
    public static float toFloat(String str, float dfValue){
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toFloat");
        }
        return dfValue;
    }

    /**
     * 字符串 转 double
     * @param str
     * @param dfValue
     * @return
     */
    public static double toDouble(String str, double dfValue){
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toDouble");
        }
        return dfValue;
    }

    /**
     * 字符串 转 long
     * @param str
     * @param dfValue
     * @return
     */
    public static long toLong(String str, long dfValue){
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toLong");
        }
        return dfValue;
    }

    // == 转换对象 ==

    /**
     * 基本类型对象 转 int
     * @param val
     * @param dfValue
     * @return
     */
    public static int toInt(Integer val, int dfValue) {
        try {
            return val;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return dfValue;
    }

    /**
     * 基本类型对象 转 boolean
     * @param val
     * @return
     */
    public static boolean toBoolean(Boolean val){
        return (val != null && val);
    }

    /**
     * 基本类型对象 转 float
     * @param val
     * @param dfValue
     * @return
     */
    public static float toFloat(Float val, float dfValue){
        try {
            return val;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toFloat");
        }
        return dfValue;
    }

    /**
     * 基本类型对象 转 double
     * @param val
     * @param dfValue
     * @return
     */
    public static double toDouble(Double val, double dfValue){
        try {
            return val;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toDouble");
        }
        return dfValue;
    }

    /**
     * 基本类型对象 转 long
     * @param val
     * @param dfValue
     * @return
     */
    public static long toLong(Long val, long dfValue){
        try {
            return val;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toLong");
        }
        return dfValue;
    }

    // == 平常其他 ==

    /**
     * char 转换 int
     * @param val
     * @return
     */
    public static int toInt(char val){
        return (int) val;
    }

    /**
     * 字符串 获取 char(默认第一位)
     * @param str
     * @param dfValue
     * @return
     */
    public static char toChar(String str, char dfValue){
        return toChar(str, 0, dfValue);
    }

    /**
     * 字符串 获取 char
     * @param str
     * @param pos
     * @param dfValue
     * @return
     */
    public static char toChar(String str, int pos, char dfValue){
        try {
            return str.charAt(pos);
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "toChar");
        }
        return dfValue;
    }

    /**
     * char 转换 unicode 编码
     * @param val
     * @return
     * --
     * toCharInt('a') = 97
     */
    public static int toCharInt(char val){
        return (int) val;
    }

    /**
     * 字符串 获取 char数组
     * @param str
     * @return
     */
    public static char[] toCharArys(String str){
        try {
            return str.toCharArray();
        } catch (Exception e){
            JCLogUtils.eTag(TAG, e, "toCharArys");
        }
        return null;
    }

    /**
     * 字符串 获取 byte数组
     * @param str
     * @return
     */
    public static byte[] toByteArys(String str){
        if (str != null) {
            try {
                return str.getBytes();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toByteArys");
            }
        }
        return null;
    }

    // ======================================================================

    // 以0x开始的数据表示16进制

    /**
     * 一个 int 参数的字符串表示形式在基数为16的无符号 int
     * @param val
     * @return
     * --
     * 例如 -> 传入 0x1f603 => toHexString(0x1f603); 返回: 1f603
     */
    public static String toHexString(int val){
        return Integer.toHexString(val);
    }

    /**
     * 一个 long 参数的字符串表示形式在基数为16的无符号 long
     * @param val
     * @return
     */
    public static String toHexString(long val){
        return Long.toHexString(val);
    }

    /**
     * 一个 double 参数的字符串表示形式在基数为16的无符号 double
     * @param val
     * @return
     */
    public static String toHexString(double val){
        return Double.toHexString(val);
    }

    /**
     * 一个 float 参数的字符串表示形式在基数为16的无符号 float
     * @param val
     * @return
     */
    public static String toHexString(float val){
        return Float.toHexString(val);
    }

    // --

    /**
     * 字符串转换对应的进制
     * @param s
     * @param radix
     * @return
     * --
     * 如: parseInt("1f603", 16) = 128515
     */
    public static int parseInt(String s, int radix){
        return Integer.parseInt(s, radix);
    }

    // ==

    // toHexString(0x1f603) = 1f603
    // parseInt("1f603", 16) = 128515
    // toHexString(128515) = 1f603

    // ==

    // ======================================================================

    // 小写
    public static final char HEX_DIGITS[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    // 大写
    public static final char HEX_DIGITS_UPPER[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};

    /**
     * 进行转换 十六进制字符
     * @param bData
     * @param hexDigits
     * @return
     */
    public static String toHexString(byte[] bData, char[] hexDigits) {
        if (bData == null || hexDigits == null){
            return null;
        }
        StringBuilder sBuilder = new StringBuilder(bData.length * 2);
        for (int i = 0, len = bData.length; i < len; i++) {
            sBuilder.append(hexDigits[(bData[i] & 0xf0) >>> 4]);
            sBuilder.append(hexDigits[bData[i] & 0x0f]);
        }
        return sBuilder.toString();
    }

    /**
     * 十六进制字符串 转换byte数组
     * @param hexString
     * @return
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (isSpace(hexString)) return null;
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Int(hexBytes[i]) << 4 | hex2Int(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Int(final char hexChar) {
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
     * 把 bytes 数据, 转换成二进制数据
     * 例: "asd".getBytes() 传入 bytes2Bits 返回 011000010111001101100100 二进制的字符串数据
     * @param bytes The bytes.
     * @return bits
     */
    public static String bytes2Bits(final byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            for (int j = 7; j >= 0; --j) {
                sb.append(((aByte >> j) & 0x01) == 0 ? '0' : '1');
            }
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
     * byte 数组 转换 char 数组, 并且进行补码
     * @param bytes The bytes.
     * @return chars
     */
    public static char[] bytes2Chars(final byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) (bytes[i] & 0xff);
        }
        return chars;
    }

    /**
     * char 数组 转换 byte 数组
     * @param chars The chars.
     * @return bytes
     */
    public static byte[] chars2Bytes(final char[] chars) {
        if (chars == null || chars.length <= 0) return null;
        int len = chars.length;
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = (byte) (chars[i]);
        }
        return bytes;
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
