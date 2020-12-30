package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import dev.utils.JCLogUtils;

/**
 * detail: 转换工具类 ( Byte、Hex 等 )
 * @author Ttt
 * <pre>
 *     byte 是字节数据类型、有符号型的、占 1 个字节、大小范围为 [ -128 - 127]
 *     当大于 127 时则开始缩进 127 = 127, 128 = -128, 129 = -127
 *     char 是字符数据类型、无符号型的、占 2 个字节 (unicode 码 )、大小范围为 [0 - 65535]
 *     <p></p>
 *     Binary( 二进制 ) toBinaryString
 *     Oct( 八进制 )
 *     Dec( 十进制 )
 *     Hex( 十六进制 ) 以 0x 开始的数据表示十六进制
 *     <p></p>
 *     位移加密: bytesBitwiseAND(byte[] bytes)
 *     @see <a href="http://www.runoob.com/java/java-operators.html"/>
 * </pre>
 */
public final class ConvertUtils {

    private ConvertUtils() {
    }

    // 日志 TAG
    private static final String TAG = ConvertUtils.class.getSimpleName();

    // 用于建立十六进制字符的输出的小写字符数组
    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    // 用于建立十六进制字符的输出的大写字符数组
    private static final char[] HEX_DIGITS_UPPER = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * Object 转换所需类型对象
     * @param object Object
     * @param <T>    泛型
     * @return Object convert T object
     */
    public static <T> T convert(final Object object) {
        if (object == null) return null;
        try {
            return (T) object;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "convert");
        }
        return null;
    }

    /**
     * Object 转 String
     * @param data byte[]
     * @return {@link String}
     * @deprecated {@link #newString}
     */
    @Deprecated
    public static String toString(final byte[] data) {
        return newString(data, null);
    }

    /**
     * Object 转 String
     * @param data       byte[]
     * @param defaultStr 默认字符串
     * @return {@link String} 如果转换失败则返回 defaultStr
     * @deprecated {@link #newString}
     */
    @Deprecated
    public static String toString(
            final byte[] data,
            final String defaultStr
    ) {
        return newString(data, defaultStr);
    }

    /**
     * Object 转 String
     * @param data char[]
     * @return {@link String}
     * @deprecated {@link #newString}
     */
    @Deprecated
    public static String toString(final char[] data) {
        return newString(data, null);
    }

    /**
     * Object 转 String
     * @param data       char[]
     * @param defaultStr 默认字符串
     * @return {@link String} 如果转换失败则返回 defaultStr
     * @deprecated {@link #newString}
     */
    @Deprecated
    public static String toString(
            final char[] data,
            final String defaultStr
    ) {
        return newString(data, defaultStr);
    }

    /**
     * Object 转 String
     * @param value Value
     * @return {@link String}
     */
    public static String newString(final Object value) {
        return newString(value, null);
    }

    /**
     * Object 转 String
     * @param value      Value
     * @param defaultStr 默认字符串
     * @return {@link String} 如果转换失败则返回 defaultStr
     */
    public static String newString(
            final Object value,
            final String defaultStr
    ) {
        if (value != null) {
            try {
                if (value instanceof byte[]) {
                    return new String((byte[]) value);
                }
                if (value instanceof char[]) {
                    return new String((char[]) value);
                }
                if (value instanceof String) {
                    return (String) value;
                }
                if (value instanceof StringBuffer) {
                    return new String((StringBuffer) value);
                }
                if (value instanceof StringBuilder) {
                    return new String((StringBuilder) value);
                }
                throw new Exception("can not new string, value : " + value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "newString");
            }
        }
        return defaultStr;
    }

    /**
     * Object 转 String
     * @param object Object
     * @return {@link String}
     */
    public static String toString(final Object object) {
        return toString(object, null);
    }

    /**
     * Object 转 String
     * @param object     Object
     * @param defaultStr 默认字符串
     * @return {@link String} 如果转换失败则返回 defaultStr
     */
    public static String toString(
            final Object object,
            final String defaultStr
    ) {
        if (object != null) {
            try {
                if (object instanceof String) {
                    return (String) object;
                }
                if (object instanceof Integer) {
                    return Integer.toString((Integer) object);
                }
                if (object instanceof Boolean) {
                    return Boolean.toString((Boolean) object);
                }
                if (object instanceof Long) {
                    return Long.toString((Long) object);
                }
                if (object instanceof Double) {
                    return Double.toString((Double) object);
                }
                if (object instanceof Float) {
                    return Float.toString((Float) object);
                }
                if (object instanceof Byte) {
                    return Byte.toString((Byte) object);
                }
                if (object instanceof Character) {
                    return Character.toString((Character) object);
                }
                if (object instanceof Short) {
                    return Short.toString((Short) object);
                }
                Class<?> clazz = object.getClass();
                // 判断是否数组类型
                if (clazz.isArray()) {
                    // = 基本数据类型 =
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
                    return Arrays.toString((Object[]) object);
                }
                return object.toString();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return defaultStr;
    }

    /**
     * Object 转 Integer
     * @param value Value
     * @return Integer
     */
    public static Integer toInt(final Object value) {
        return toInt(value, 0);
    }

    /**
     * Object 转 Integer
     * @param value        Value
     * @param defaultValue 默认值
     * @return Integer, 如果转换失败则返回 defaultValue
     */
    public static Integer toInt(
            final Object value,
            final Integer defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Integer) {
                return (Integer) value;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(',') != 0) {
                    strVal = strVal.replaceAll(",", "");
                }
                return Integer.parseInt(strVal);
            }
            if (value instanceof Boolean) {
                return ((Boolean) value).booleanValue() ? 1 : 0;
            }
            throw new Exception("can not cast to int, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toInt");
        }
        return defaultValue;
    }

    /**
     * Object 转 Boolean
     * @param value Value
     * @return Boolean
     */
    public static Boolean toBoolean(final Object value) {
        return toBoolean(value, false);
    }

    /**
     * Object 转 Boolean
     * @param value        Value
     * @param defaultValue 默认值
     * @return Boolean, 如果转换失败则返回 defaultValue
     */
    public static Boolean toBoolean(
            final Object value,
            final Boolean defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Boolean) {
                return (Boolean) value;
            }
            if (value instanceof Number) {
                return ((Number) value).intValue() == 1;
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if ("true".equalsIgnoreCase(strVal) || "1".equals(strVal)) {
                    return Boolean.TRUE;
                }
                if ("false".equalsIgnoreCase(strVal) || "0".equals(strVal)) {
                    return Boolean.FALSE;
                }
                // YES、TRUE
                if ("Y".equalsIgnoreCase(strVal) || "T".equalsIgnoreCase(strVal)) {
                    return Boolean.TRUE;
                }
                // NO、FALSE
                if ("N".equalsIgnoreCase(strVal) || "F".equalsIgnoreCase(strVal)) {
                    return Boolean.FALSE;
                }
            }
            throw new Exception("can not cast to boolean, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBoolean");
        }
        return defaultValue;
    }

    /**
     * Object 转 Float
     * @param value Value
     * @return Float
     */
    public static Float toFloat(final Object value) {
        return toFloat(value, 0f);
    }

    /**
     * Object 转 Float
     * @param value        Value
     * @param defaultValue 默认值
     * @return Float, 如果转换失败则返回 defaultValue
     */
    public static Float toFloat(
            final Object value,
            final Float defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Float) {
                return (Float) value;
            }
            if (value instanceof Number) {
                return ((Number) value).floatValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(',') != 0) {
                    strVal = strVal.replaceAll(",", "");
                }
                return Float.parseFloat(strVal);
            }
            throw new Exception("can not cast to float, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toFloat");
        }
        return defaultValue;
    }

    /**
     * Object 转 Double
     * @param value Value
     * @return Double
     */
    public static Double toDouble(final Object value) {
        return toDouble(value, 0d);
    }

    /**
     * Object 转 Double
     * @param value        Value
     * @param defaultValue 默认值
     * @return Double, 如果转换失败则返回 defaultValue
     */
    public static Double toDouble(
            final Object value,
            final Double defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Double) {
                return (Double) value;
            }
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(',') != 0) {
                    strVal = strVal.replaceAll(",", "");
                }
                return Double.parseDouble(strVal);
            }
            throw new Exception("can not cast to double, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toDouble");
        }
        return defaultValue;
    }

    /**
     * Object 转 Long
     * @param value Value
     * @return Long
     */
    public static Long toLong(final Object value) {
        return toLong(value, 0L);
    }

    /**
     * Object 转 Long
     * @param value        Value
     * @param defaultValue 默认值
     * @return Long, 如果转换失败则返回 defaultValue
     */
    public static Long toLong(
            final Object value,
            final Long defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Long) {
                return (Long) value;
            }
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.indexOf(',') != 0) {
                    strVal = strVal.replaceAll(",", "");
                }
                return Long.parseLong(strVal);
            }
            throw new Exception("can not cast to long, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toLong");
        }
        return defaultValue;
    }

    /**
     * Object 转 Short
     * @param value Value
     * @return Short
     */
    public static Short toShort(final Object value) {
        return toShort(value, (short) 0);
    }

    /**
     * Object 转 Short
     * @param value        Value
     * @param defaultValue 默认值
     * @return Short, 如果转换失败则返回 defaultValue
     */
    public static Short toShort(
            final Object value,
            final Short defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Short) {
                return (Short) value;
            }
            if (value instanceof Number) {
                return ((Number) value).shortValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                return Short.parseShort(strVal);
            }
            throw new Exception("can not cast to short, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toShort");
        }
        return defaultValue;
    }

    /**
     * Object 转 Character
     * @param value Value
     * @return Character
     */
    public static Character toChar(final Object value) {
        return toChar(value, (char) 0);
    }

    /**
     * Object 转 Character
     * @param value        Value
     * @param defaultValue 默认值
     * @return Character, 如果转换失败则返回 defaultValue
     */
    public static Character toChar(
            final Object value,
            final Character defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Character) {
                return (Character) value;
            }
            if (value instanceof String) {
                String strVal = (String) value;
                if (strVal.length() == 1) {
                    return strVal.charAt(0);
                }
            }
            throw new Exception("can not cast to char, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toChar");
        }
        return defaultValue;
    }

    /**
     * Object 转 Byte
     * @param value Value
     * @return Byte
     */
    public static byte toByte(final Object value) {
        return toByte(value, (byte) 0);
    }

    /**
     * Object 转 Byte
     * @param value        Value
     * @param defaultValue 默认值
     * @return Byte, 如果转换失败则返回 defaultValue
     */
    public static byte toByte(
            final Object value,
            final Byte defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof Byte) {
                return (Byte) value;
            }
            if (value instanceof Number) {
                return ((Number) value).byteValue();
            }
            if (value instanceof String) {
                String strVal = (String) value;
                return Byte.parseByte(strVal);
            }
            throw new Exception("can not cast to byte, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toByte");
        }
        return defaultValue;
    }

    /**
     * Object 转 BigDecimal
     * @param value Value
     * @return BigDecimal
     */
    public static BigDecimal toBigDecimal(final Object value) {
        return toBigDecimal(value, BigDecimal.ZERO);
    }

    /**
     * Object 转 BigDecimal
     * @param value        Value
     * @param defaultValue 默认值
     * @return BigDecimal, 如果转换失败则返回 defaultValue
     */
    public static BigDecimal toBigDecimal(
            final Object value,
            final BigDecimal defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof BigDecimal) {
                return (BigDecimal) value;
            }
            if (value instanceof BigInteger) {
                return new BigDecimal((BigInteger) value);
            }
            if (value instanceof String) {
                String strVal = (String) value;
                return new BigDecimal(strVal);
            }
            throw new Exception("can not cast to BigDecimal, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBigDecimal");
        }
        return defaultValue;
    }

    /**
     * Object 转 BigInteger
     * @param value Value
     * @return BigInteger
     */
    public static BigInteger toBigInteger(final Object value) {
        return toBigInteger(value, BigInteger.valueOf(0L));
    }

    /**
     * Object 转 BigInteger
     * @param value        Value
     * @param defaultValue 默认值
     * @return BigInteger, 如果转换失败则返回 defaultValue
     */
    public static BigInteger toBigInteger(
            final Object value,
            final BigInteger defaultValue
    ) {
        if (value == null) return defaultValue;
        try {
            if (value instanceof BigInteger) {
                return (BigInteger) value;
            }
            if (value instanceof Float || value instanceof Double) {
                return BigInteger.valueOf(((Number) value).longValue());
            }
            if (value instanceof String) {
                String strVal = (String) value;
                return new BigInteger(strVal);
            }
            throw new Exception("can not cast to BigInteger, value : " + value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBigInteger");
        }
        return defaultValue;
    }

    /**
     * Object 获取 char[]
     * @param value Value
     * @return char[]
     */
    public static char[] toChars(final Object value) {
        try {
            if (value instanceof char[]) {
                return (char[]) value;
            }
            if (value instanceof String) {
                return ((String) value).toCharArray();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toChars");
        }
        return null;
    }

    /**
     * Object 获取 byte[]
     * @param value Value
     * @return byte[]
     */
    public static byte[] toBytes(final Object value) {
        try {
            if (value instanceof byte[]) {
                return (byte[]) value;
            }
            if (value instanceof String) {
                return ((String) value).getBytes();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBytes");
        }
        return null;
    }

    /**
     * char 转换 unicode 编码
     * @param value char
     * @return int
     */
    public static int toCharInt(final char value) {
        return (int) value;
    }

    /**
     * Object 获取 char ( 默认第一位 )
     * @param value        Value
     * @param defaultValue 默认值
     * @return 第一位值, 如果获取失败则返回 defaultValue
     */
    public static char charAt(
            final Object value,
            final char defaultValue
    ) {
        return charAt(value, 0, defaultValue);
    }

    /**
     * Object 获取 char
     * @param value        Value
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 指定索引的值, 如果获取失败则返回 defaultValue
     */
    public static char charAt(
            final Object value,
            final int pos,
            final char defaultValue
    ) {
        if (value == null || pos < 0) return defaultValue;
        try {
            return toChars(value)[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "charAt");
        }
        return defaultValue;
    }

    // =

    /**
     * 字符串转换对应的进制
     * <pre>
     *     如: parseInt("1f603", 16) = 128515
     * </pre>
     * @param str   待处理字符串
     * @param radix 进制
     * @return 对应进制的值
     */
    public static int parseInt(
            final String str,
            final int radix
    ) {
        if (str == null) return -1;
        try {
            return Integer.parseInt(str, radix);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseInt");
        }
        return -1;
    }

    /**
     * 字符串转换对应的进制
     * @param str   待处理字符串
     * @param radix 进制
     * @return 对应进制的值
     */
    public static long parseLong(
            final String str,
            final int radix
    ) {
        if (str == null) return -1;
        try {
            return Long.parseLong(str, radix);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseLong");
        }
        return -1;
    }

    // =

    /**
     * 将 short 转换成字节数组
     * @param data short
     * @return byte[]
     */
    public static byte[] valueOf(final short data) {
        try {
            byte[] bytes = new byte[2];
            for (int i = 0; i < 2; i++) {
                int offset = (bytes.length - 1 - i) * 8;
                bytes[i] = (byte) ((data >>> offset) & 0xff);
            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "valueOf");
        }
        return null;
    }

    /**
     * 将 int 转换成字节数组
     * @param data int
     * @return byte[]
     */
    public static byte[] valueOf(final int data) {
        try {
            byte[] bytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                int offset = (bytes.length - 1 - i) * 8;
                bytes[i] = (byte) ((data >>> offset) & 0xFF);
            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "valueOf");
        }
        return null;
    }

    // =

    /**
     * byte[] 转为 Object
     * @param bytes byte[]
     * @return {@link Object}
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
            CloseUtils.closeIOQuietly(ois);
        }
        return null;
    }

    /**
     * Object 转为 byte[]
     * @param object Object
     * @return byte[]
     */
    public static byte[] objectToBytes(final Object object) {
        if (object == null) return null;
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "objectToBytes");
        } finally {
            CloseUtils.closeIOQuietly(oos);
        }
        return null;
    }

    // =

    /**
     * byte[] 转换 char[], 并且进行补码
     * @param data byte[]
     * @return char[]
     */
    public static char[] bytesToChars(final byte[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        try {
            char[] chars = new char[len];
            for (int i = 0; i < len; i++) {
                chars[i] = (char) (data[i] & 0xff);
            }
            return chars;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "bytesToChars");
        }
        return null;
    }

    /**
     * char[] 转换 byte[]
     * @param data char[]
     * @return byte[]
     */
    public static byte[] charsToBytes(final char[] data) {
        if (data == null) return null;
        int len = data.length;
        if (len == 0) return null;
        try {
            byte[] bytes = new byte[len];
            for (int i = 0; i < len; i++) {
                bytes[i] = (byte) (data[i]);
            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "charsToBytes");
        }
        return null;
    }

    // =============================================
    // = (int、double、long、float)[] 转换 String[] =
    // =============================================

    /**
     * int[] 转换 string[]
     * @param datas int[]
     * @return String[]
     */
    public static String[] intsToStrings(final int[] datas) {
        return intsToStrings(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 string[]
     * @param off   起始值
     * @param datas int[]
     * @return String[]
     */
    public static String[] intsToStrings(
            final int off,
            final int[] datas
    ) {
        return intsToStrings(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 string[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return String[]
     */
    public static String[] intsToStrings(
            final int off,
            final int length,
            final int[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        String[] strings = new String[length - off];
        for (int i = 0, len = strings.length; i < len; i++) {
            strings[i] = String.valueOf(datas[off + i]);
        }
        return strings;
    }

    // =

    /**
     * double[] 转换 string[]
     * @param datas double[]
     * @return String[]
     */
    public static String[] doublesToStrings(final double[] datas) {
        return doublesToStrings(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * double[] 转换 string[]
     * @param off   起始值
     * @param datas double[]
     * @return String[]
     */
    public static String[] doublesToStrings(
            final int off,
            final double[] datas
    ) {
        return doublesToStrings(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * double[] 转换 string[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  double[]
     * @return String[]
     */
    public static String[] doublesToStrings(
            final int off,
            final int length,
            final double[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        String[] strings = new String[length - off];
        for (int i = 0, len = strings.length; i < len; i++) {
            strings[i] = String.valueOf(datas[off + i]);
        }
        return strings;
    }

    // =

    /**
     * long[] 转换 string[]
     * @param datas long[]
     * @return String[]
     */
    public static String[] longsToStrings(final long[] datas) {
        return longsToStrings(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * long[] 转换 string[]
     * @param off   起始值
     * @param datas long[]
     * @return String[]
     */
    public static String[] longsToStrings(
            final int off,
            final long[] datas
    ) {
        return longsToStrings(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * long[] 转换 string[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  long[]
     * @return String[]
     */
    public static String[] longsToStrings(
            final int off,
            final int length,
            final long[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        String[] strings = new String[length - off];
        for (int i = 0, len = strings.length; i < len; i++) {
            strings[i] = String.valueOf(datas[off + i]);
        }
        return strings;
    }

    // =

    /**
     * float[] 转换 string[]
     * @param datas float[]
     * @return String[]
     */
    public static String[] floatsToStrings(final float[] datas) {
        return floatsToStrings(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * float[] 转换 string[]
     * @param off   起始值
     * @param datas float[]
     * @return String[]
     */
    public static String[] floatsToStrings(
            final int off,
            final float[] datas
    ) {
        return floatsToStrings(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * float[] 转换 string[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  float[]
     * @return String[]
     */
    public static String[] floatsToStrings(
            final int off,
            final int length,
            final float[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        String[] strings = new String[length - off];
        for (int i = 0, len = strings.length; i < len; i++) {
            strings[i] = String.valueOf(datas[off + i]);
        }
        return strings;
    }

    // =====================================
    // = int[] 转换 (double、long、float)[] =
    // =====================================

    /**
     * int[] 转换 double[]
     * @param datas int[]
     * @return double[]
     */
    public static double[] intsToDoubles(final int[] datas) {
        return intsToDoubles(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 double[]
     * @param off   起始值
     * @param datas int[]
     * @return double[]
     */
    public static double[] intsToDoubles(
            final int off,
            final int[] datas
    ) {
        return intsToDoubles(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 double[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return double[]
     */
    public static double[] intsToDoubles(
            final int off,
            final int length,
            final int[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        double[] doubles = new double[length - off];
        for (int i = 0, len = doubles.length; i < len; i++) {
            doubles[i] = datas[off + i];
        }
        return doubles;
    }

    // =

    /**
     * int[] 转换 long[]
     * @param datas int[]
     * @return long[]
     */
    public static long[] intsToLongs(final int[] datas) {
        return intsToLongs(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 long[]
     * @param off   起始值
     * @param datas int[]
     * @return long[]
     */
    public static long[] intsToLongs(
            final int off,
            final int[] datas
    ) {
        return intsToLongs(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 long[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return long[]
     */
    public static long[] intsToLongs(
            final int off,
            final int length,
            final int[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        long[] longs = new long[length - off];
        for (int i = 0, len = longs.length; i < len; i++) {
            longs[i] = datas[off + i];
        }
        return longs;
    }

    // =

    /**
     * int[] 转换 float[]
     * @param datas int[]
     * @return float[]
     */
    public static float[] intsToFloats(final int[] datas) {
        return intsToFloats(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 float[]
     * @param off   起始值
     * @param datas int[]
     * @return float[]
     */
    public static float[] intsToFloats(
            final int off,
            final int[] datas
    ) {
        return intsToFloats(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 float[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return float[]
     */
    public static float[] intsToFloats(
            final int off,
            final int length,
            final int[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        float[] floats = new float[length - off];
        for (int i = 0, len = floats.length; i < len; i++) {
            floats[i] = datas[off + i];
        }
        return floats;
    }

    // =============================================
    // = String[] 转换 (int、double、long、float)[] =
    // =============================================

    /**
     * string[] 转换 int[]
     * @param datas String[]
     * @return int[]
     */
    public static int[] stringsToInts(final String... datas) {
        return stringsToInts(0, (datas != null) ? datas.length : 0, -1, datas);
    }

    /**
     * string[] 转换 int[]
     * @param off   起始值
     * @param datas String[]
     * @return int[]
     */
    public static int[] stringsToInts(
            final int off,
            final String... datas
    ) {
        return stringsToInts(off, (datas != null) ? datas.length : 0, -1, datas);
    }

    /**
     * string[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return int[]
     */
    public static int[] stringsToInts(
            final int off,
            final int length,
            final String... datas
    ) {
        return stringsToInts(off, length, -1, datas);
    }

    /**
     * string[] 转换 int[]
     * @param off        起始值
     * @param length     所需长度
     * @param errorValue 转换错误使用值
     * @param datas      String[]
     * @return int[]
     */
    public static int[] stringsToInts(
            final int off,
            final int length,
            final int errorValue,
            final String... datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        int[] ints = new int[length - off];
        for (int i = 0, len = ints.length; i < len; i++) {
            try {
                ints[i] = Integer.parseInt(datas[off + i]);
            } catch (Exception e) {
                ints[i] = errorValue;
            }
        }
        return ints;
    }

    // =

    /**
     * string[] 转换 double[]
     * @param datas String[]
     * @return double[]
     */
    public static double[] stringsToDoubles(final String... datas) {
        return stringsToDoubles(0, (datas != null) ? datas.length : 0, -1d, datas);
    }

    /**
     * string[] 转换 double[]
     * @param off   起始值
     * @param datas String[]
     * @return double[]
     */
    public static double[] stringsToDoubles(
            final int off,
            final String... datas
    ) {
        return stringsToDoubles(off, (datas != null) ? datas.length : 0, -1d, datas);
    }

    /**
     * string[] 转换 double[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return double[]
     */
    public static double[] stringsToDoubles(
            final int off,
            final int length,
            final String... datas
    ) {
        return stringsToDoubles(off, length, -1d, datas);
    }

    /**
     * string[] 转换 double[]
     * @param off        起始值
     * @param length     所需长度
     * @param errorValue 转换错误使用值
     * @param datas      String[]
     * @return double[]
     */
    public static double[] stringsToDoubles(
            final int off,
            final int length,
            final double errorValue,
            final String... datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        double[] doubles = new double[length - off];
        for (int i = 0, len = doubles.length; i < len; i++) {
            try {
                doubles[i] = Double.parseDouble(datas[off + i]);
            } catch (Exception e) {
                doubles[i] = errorValue;
            }
        }
        return doubles;
    }

    // =

    /**
     * string[] 转换 long[]
     * @param datas String[]
     * @return long[]
     */
    public static long[] stringsToLongs(final String... datas) {
        return stringsToLongs(0, (datas != null) ? datas.length : 0, -1L, datas);
    }

    /**
     * string[] 转换 long[]
     * @param off   起始值
     * @param datas String[]
     * @return long[]
     */
    public static long[] stringsToLongs(
            final int off,
            final String... datas
    ) {
        return stringsToLongs(off, (datas != null) ? datas.length : 0, -1L, datas);
    }

    /**
     * string[] 转换 long[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return long[]
     */
    public static long[] stringsToLongs(
            final int off,
            final int length,
            final String... datas
    ) {
        return stringsToLongs(off, length, -1L, datas);
    }

    /**
     * string[] 转换 long[]
     * @param off        起始值
     * @param length     所需长度
     * @param errorValue 转换错误使用值
     * @param datas      String[]
     * @return long[]
     */
    public static long[] stringsToLongs(
            final int off,
            final int length,
            final long errorValue,
            final String... datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        long[] longs = new long[length - off];
        for (int i = 0, len = longs.length; i < len; i++) {
            try {
                longs[i] = Long.parseLong(datas[off + i]);
            } catch (Exception e) {
                longs[i] = errorValue;
            }
        }
        return longs;
    }

    // =

    /**
     * string[] 转换 float[]
     * @param datas String[]
     * @return float[]
     */
    public static float[] stringsToFloats(final String... datas) {
        return stringsToFloats(0, (datas != null) ? datas.length : 0, -1f, datas);
    }

    /**
     * string[] 转换 float[]
     * @param off   起始值
     * @param datas String[]
     * @return float[]
     */
    public static float[] stringsToFloats(
            final int off,
            final String... datas
    ) {
        return stringsToFloats(off, (datas != null) ? datas.length : 0, -1f, datas);
    }

    /**
     * string[] 转换 float[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return float[]
     */
    public static float[] stringsToFloats(
            final int off,
            final int length,
            final String... datas
    ) {
        return stringsToFloats(off, length, -1f, datas);
    }

    /**
     * string[] 转换 float[]
     * @param off        起始值
     * @param length     所需长度
     * @param errorValue 转换错误使用值
     * @param datas      String[]
     * @return float[]
     */
    public static float[] stringsToFloats(
            final int off,
            final int length,
            final float errorValue,
            final String... datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        float[] floats = new float[length - off];
        for (int i = 0, len = floats.length; i < len; i++) {
            try {
                floats[i] = Float.parseFloat(datas[off + i]);
            } catch (Exception e) {
                floats[i] = errorValue;
            }
        }
        return floats;
    }

    // =====================================
    // = (double、long、float)[] 转换 int[] =
    // =====================================

    /**
     * double[] 转换 int[]
     * @param datas double[]
     * @return int[]
     */
    public static int[] doublesToInts(final double[] datas) {
        return doublesToInts(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * double[] 转换 int[]
     * @param off   起始值
     * @param datas double[]
     * @return int[]
     */
    public static int[] doublesToInts(
            final int off,
            final double[] datas
    ) {
        return doublesToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * double[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  double[]
     * @return int[]
     */
    public static int[] doublesToInts(
            final int off,
            final int length,
            final double[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        int[] ints = new int[length - off];
        for (int i = 0, len = ints.length; i < len; i++) {
            try {
                ints[i] = (int) datas[off + i];
            } catch (Exception e) {
            }
        }
        return ints;
    }

    // =

    /**
     * long[] 转换 int[]
     * @param datas long[]
     * @return int[]
     */
    public static int[] longsToInts(final long[] datas) {
        return longsToInts(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * long[] 转换 int[]
     * @param off   起始值
     * @param datas long[]
     * @return int[]
     */
    public static int[] longsToInts(
            final int off,
            final long[] datas
    ) {
        return longsToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * long[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  long[]
     * @return int[]
     */
    public static int[] longsToInts(
            final int off,
            final int length,
            final long[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        int[] ints = new int[length - off];
        for (int i = 0, len = ints.length; i < len; i++) {
            try {
                ints[i] = (int) datas[off + i];
            } catch (Exception e) {
            }
        }
        return ints;
    }

    // =

    /**
     * float[] 转换 int[]
     * @param datas float[]
     * @return int[]
     */
    public static int[] floatsToInts(final float[] datas) {
        return floatsToInts(0, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * float[] 转换 int[]
     * @param off   起始值
     * @param datas float[]
     * @return int[]
     */
    public static int[] floatsToInts(
            final int off,
            final float[] datas
    ) {
        return floatsToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * float[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  float[]
     * @return int[]
     */
    public static int[] floatsToInts(
            final int off,
            final int length,
            final float[] datas
    ) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length) {
            return null;
        }
        int[] ints = new int[length - off];
        for (int i = 0, len = ints.length; i < len; i++) {
            try {
                ints[i] = (int) datas[off + i];
            } catch (Exception e) {
            }
        }
        return ints;
    }

    // ====================
    // = Binary ( 二进制 ) =
    // ====================

    /**
     * 将 字节转换 为 二进制字符串
     * @param datas byte[]
     * @return 二进制字符串
     */
    public static String toBinaryString(final byte... datas) {
        if (datas == null || datas.length == 0) return null;
        try {
            StringBuilder builder = new StringBuilder();
            for (byte value : datas) {
                for (int j = 7; j >= 0; --j) {
                    builder.append(((value >> j) & 0x01) == 0 ? '0' : '1');
                }
            }
            return builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toBinaryString");
        }
        return null;
    }

    /**
     * 二进制字符串 转换 byte[] 解码
     * <pre>
     *     例: "011000010111001101100100" 传入 decodeBinary, 返回 byte[], 通过 new String(byte()) 获取配合 toBinaryString 使用
     * </pre>
     * @param str 待处理字符串
     * @return 解码后的 byte[]
     */
    public static byte[] decodeBinary(final String str) {
        if (str == null) return null;
        try {
            String data    = str;
            int    lenMod  = data.length() % 8;
            int    byteLen = data.length() / 8;
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
            JCLogUtils.eTag(TAG, e, "decodeBinary");
        }
        return null;
    }

    // ===================
    // = Hex ( 十六进制 ) =
    // ===================

    /**
     * 判断是否十六进制数据
     * @param data 待检验数据
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHex(final String data) {
        if (data == null) return false;
        // 获取数据长度
        int len = data.length();
        if (len > 0) {
            for (int i = len - 1; i >= 0; i--) {
                char c = data.charAt(i);
                if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 将十六进制字节数组解码
     * @param data 十六进制 byte[]
     * @return 十六进制转 ( 解 ) 码后的数据
     */
    public static byte[] decodeHex(final byte[] data) {
        return decodeHex((ArrayUtils.length(data) == 0) ? null : bytesToChars(data));
    }

    /**
     * 将十六进制字符串解码
     * @param str 十六进制字符串
     * @return 十六进制转 ( 解 ) 码后的数据
     */
    public static byte[] decodeHex(final String str) {
        return decodeHex(StringUtils.isEmpty(str) ? null : str.toCharArray());
    }

    /**
     * 将十六进制字符数组解码
     * @param data 十六进制 char[]
     * @return 十六进制转 ( 解 ) 码后的数据
     */
    public static byte[] decodeHex(final char[] data) {
        if (data == null) return null;
        try {
            int    len = data.length;
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
     * 十六进制 char 转换 int
     * @param hexChar 十六进制 char
     * @return 十六进制转 ( 解 ) 码后的整数
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

    /**
     * 将十六进制字符转换成一个整数
     * @param ch    十六进制 char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws Exception 当 ch 不是一个合法的十六进制字符时, 抛出运行时异常
     */
    private static int toDigit(
            final char ch,
            final int index
    )
            throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception(String.format("Illegal hexadecimal character %s at index %s", ch, index));
        }
        return digit;
    }

    // =

    // toHexString(0x1f603) = 1f603
    // parseInt("1f603", 16) = 128515
    // toHexString(128515) = 1f603

    /**
     * int 转换十六进制
     * <pre>
     *     如: toHexString(0x1f603) 返回: 1f603
     * </pre>
     * @param value int
     * @return 十六进制字符串
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
     * long 转换十六进制
     * @param value long
     * @return 十六进制字符串
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
     * double 转换十六进制
     * @param value double
     * @return 十六进制字符串
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
     * float 转换十六进制
     * @param value float
     * @return 十六进制字符串
     */
    public static String toHexString(final float value) {
        try {
            return Float.toHexString(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexString");
        }
        return null;
    }

    // =

    /**
     * 将 string 转换为 十六进制 char[]
     * @param str 待处理字符串
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(final String str) {
        return toHexChars(str, true);
    }

    /**
     * 将 string 转换为 十六进制 char[]
     * @param str         待处理字符串
     * @param toLowerCase {@code true} 小写格式, {@code false} 大写格式
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(
            final String str,
            final boolean toLowerCase
    ) {
        return toHexChars(StringUtils.isEmpty(str) ? null : str.getBytes(), toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    // =

    /**
     * 将 byte[] 转换为 十六进制 char[]
     * @param data byte[]
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(final byte[] data) {
        return toHexChars(data, true);
    }

    /**
     * 将 byte[] 转换为 十六进制 char[]
     * @param data        byte[]
     * @param toLowerCase {@code true} 小写格式, {@code false} 大写格式
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(
            final byte[] data,
            final boolean toLowerCase
    ) {
        return toHexChars(data, toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    /**
     * 将 byte[] 转换为 十六进制 char[]
     * @param data      byte[]
     * @param hexDigits {@link #HEX_DIGITS}、{@link #HEX_DIGITS_UPPER}
     * @return 十六进制 char[]
     */
    private static char[] toHexChars(
            final byte[] data,
            final char[] hexDigits
    ) {
        if (data == null || hexDigits == null) return null;
        try {
            return toHexString(data, hexDigits).toCharArray();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toHexChars");
        }
        return null;
    }

    // =

    /**
     * 将 string 转换 十六进制字符串
     * @param str 待转换数据
     * @return 十六进制字符串
     */
    public static String toHexString(final String str) {
        return toHexString(str, true);
    }

    /**
     * 将 string 转换 十六进制字符串
     * @param str         待转换数据
     * @param toLowerCase {@code true} 小写格式, {@code false} 大写格式
     * @return 十六进制字符串
     */
    public static String toHexString(
            final String str,
            final boolean toLowerCase
    ) {
        return toHexString(StringUtils.isEmpty(str) ? null : str.getBytes(), toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    // =

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data 待转换数据
     * @return 十六进制字符串
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, true);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data        待转换数据
     * @param toLowerCase {@code true} 小写格式, {@code false} 大写格式
     * @return 十六进制字符串
     */
    public static String toHexString(
            final byte[] data,
            final boolean toLowerCase
    ) {
        return toHexString(data, toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      待转换数据
     * @param hexDigits {@link #HEX_DIGITS}、{@link #HEX_DIGITS_UPPER}
     * @return 十六进制字符串
     */
    private static String toHexString(
            final byte[] data,
            final char[] hexDigits
    ) {
        if (data == null || hexDigits == null) return null;
        try {
            int           len     = data.length;
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

    // =

//    String data = "test";
//    // 转换二进制字符串
//    String result = toBinaryString(data.getBytes());
//    // 获取二进制数据
//    byte[] bytes = result.getBytes();
//    // 位移编码
//    bytesBitwiseAND(bytes);
//    // =
//    // 位移解码
//    bytesBitwiseAND(bytes);
//    // 二进制数据解码
//    byte[] byteResult = decodeBinary(new String(bytes));
//    // 转换为原始数据
//    String data1 = new String(byteResult);
//    // 判断是否一致
//    boolean equals = data.equals(data1);

    /**
     * 按位求补 byte[] 位移编解码 ( 共用同一个方法 )
     * @param data byte[]
     */
    public static void bytesBitwiseAND(final byte[] data) {
        if (data == null) return;
        for (int i = 0, len = data.length; i < len; i++) {
            int d = data[i];
            d = ~d; // 按位补运算符, 翻转操作数的每一位, 即 0 变成 1, 1 变成 0, 再通过反转后的二进制初始化回十六进制
            data[i] = (byte) d;
        }
    }
}