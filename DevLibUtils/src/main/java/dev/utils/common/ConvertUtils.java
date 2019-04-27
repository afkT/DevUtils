package dev.utils.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import dev.utils.JCLogUtils;

/**
 * detail: 转换工具类(Byte、Hex等)
 * @author Ttt
 * <pre>
 *      byte 是字节数据类型、有符号型的、占1个字节、大小范围为 [ -128 - 127]
 *      当大于127时则开始缩进  127 = 127, 128 = -128 , 129 = -127
 *      char 是字符数据类型、无符号型的、占2个字节(unicode码)、大小范围为 [0 - 65535]
 *      <p></p>
 *      48 - 57 = 0-9
 *      58 - 64 = :;<=>?@
 *      65 - 90 = A-Z
 *      91 - 96 = [\]^_`
 *      97 - 122 = a-z
 *      <p></p>
 *      Binary(二进制) toBinaryString
 *      Oct(八进制)
 *      Dec(十进制)
 *      Hex(十六进制) 以0x开始的数据表示16进制
 *      <p></p>
 *      位移加密: bytesEncrypt(byte[] bytes)
 *      @see <a href="http://www.runoob.com/java/java-operators.html"/>
 * </pre>
 */
public final class ConvertUtils {

    private ConvertUtils() {
    }

    // 日志 TAG
    private static final String TAG = ConvertUtils.class.getSimpleName();
    // 用于建立十六进制字符的输出的小写字符数组
    public static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    // 用于建立十六进制字符的输出的大写字符数组
    public static final char HEX_DIGITS_UPPER[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * char[] 转 String
     * @param data char[]
     * @return {@link String}
     */
    public static String toString(final char[] data) {
        return toString(data, null);
    }

    /**
     * byte[] 转 String
     * @param data byte[]
     * @return {@link String}
     */
    public static String toString(final byte[] data) {
        return toString(data, null);
    }

    /**
     * char[] 转 String
     * @param data       char[]
     * @param defaultStr 默认字符串
     * @return {@link String}, 如果转换失败, 则返回 defaultStr
     */
    public static String toString(final char[] data, final String defaultStr) {
        if (length(data) == 0) {
            try {
                return new String(data);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toString");
            }
        }
        return defaultStr;
    }

    /**
     * byte[] 转 String
     * @param data       byte[]
     * @param defaultStr 默认字符串
     * @return {@link String}, 如果转换失败, 则返回 defaultStr
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

    // =

    /**
     * char 转 String
     * @param data char
     * @return {@link String}
     */
    public static String toString(final char data) {
        return toString(data, null);
    }

    /**
     * byte 转 String
     * @param data byte
     * @return {@link String}
     */
    public static String toString(final byte data) {
        return toString(data, null);
    }

    /**
     * char 转 String
     * @param data       char
     * @param defaultStr 默认字符串
     * @return {@link String}, 如果转换失败, 则返回 defaultStr
     */
    public static String toString(final char data, final String defaultStr) {
        try {
            return Character.toString(data);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toString");
        }
        return defaultStr;
    }

    /**
     * byte 转 String
     * @param data       byte
     * @param defaultStr 默认字符串
     * @return {@link String}, 如果转换失败, 则返回 defaultStr
     */
    public static String toString(final byte data, final String defaultStr) {
        try {
            return Byte.toString(data);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toString");
        }
        return defaultStr;
    }

    // =

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
     * @return {@link String}, 如果转换失败, 则返回 defaultStr
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
                        // = 基本类型封装 =
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

    // =

    /**
     * 字符串 转 int
     * @param str String
     * @return int
     */
    public static int toInt(final String str) {
        return toInt(str, 0);
    }

    /**
     * 字符串 转 int
     * @param str          String
     * @param defaultValue 默认值
     * @return int, 如果转换失败, 则返回 defaultValue
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
     * @param str String
     * @return boolean
     */
    public static boolean toBoolean(final String str) {
        return toBoolean(str, false);
    }

    /**
     * 字符串 转 boolean
     * @param str          String
     * @param defaultValue 默认值
     * @return boolean, 如果转换失败, 则返回 defaultValue
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
     * @param str String
     * @return float
     */
    public static float toFloat(final String str) {
        return toFloat(str, 0f);
    }

    /**
     * 字符串 转 float
     * @param str          String
     * @param defaultValue 默认值
     * @return float, 如果转换失败, 则返回 defaultValue
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
     * @param str String
     * @return double
     */
    public static double toDouble(final String str) {
        return toDouble(str, 0d);
    }

    /**
     * 字符串 转 double
     * @param str          String
     * @param defaultValue 默认值
     * @return double, 如果转换失败, 则返回 defaultValue
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
     * @param str String
     * @return long
     */
    public static long toLong(final String str) {
        return toLong(str, 0l);
    }

    /**
     * 字符串 转 long
     * @param str          String
     * @param defaultValue 默认值
     * @return long, 如果转换失败, 则返回 defaultValue
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

    // ============
    // = 转换对象 =
    // ============

    /**
     * 基本类型对象 转 int
     * @param value Integer
     * @return int
     */
    public static int toInt(final Integer value) {
        return toInt(value, 0);
    }

    /**
     * 基本类型对象 转 int
     * @param value        Integer
     * @param defaultValue 默认值
     * @return int, 如果转换失败, 则返回 defaultValue
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
     * @param value Boolean
     * @return boolean, 如果转换失败, 则返回 defaultValue
     */
    public static boolean toBoolean(final Boolean value) {
        return toBoolean(value, false);
    }

    /**
     * 基本类型对象 转 boolean
     * @param value        Boolean
     * @param defaultValue 默认值
     * @return boolean, 如果转换失败, 则返回 defaultValue
     */
    public static boolean toBoolean(final Boolean value, final boolean defaultValue) {
        if (value == null) return defaultValue;
        return value;
    }

    /**
     * 基本类型对象 转 float
     * @param value Float
     * @return float, 如果转换失败, 则返回 defaultValue
     */
    public static float toFloat(final Float value) {
        return toFloat(value, 0f);
    }

    /**
     * 基本类型对象 转 float
     * @param value        Float
     * @param defaultValue 默认值
     * @return float, 如果转换失败, 则返回 defaultValue
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
     * @param value Double
     * @return double, 如果转换失败, 则返回 defaultValue
     */
    public static double toDouble(final Double value) {
        return toDouble(value, 0d);
    }

    /**
     * 基本类型对象 转 double
     * @param value        Double
     * @param defaultValue 默认值
     * @return double, 如果转换失败, 则返回 defaultValue
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
     * @param value Long
     * @return long, 如果转换失败, 则返回 defaultValue
     */
    public static long toLong(final Long value) {
        return toLong(value, 0l);
    }

    /**
     * 基本类型对象 转 long
     * @param value        Long
     * @param defaultValue 默认值
     * @return long, 如果转换失败, 则返回 defaultValue
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

    /**
     * 基本类型对象 转 short
     * @param value Short
     * @return short, 如果转换失败, 则返回 defaultValue
     */
    public static short toShort(final Short value) {
        return toShort(value, (short) 0);
    }

    /**
     * 基本类型对象 转 short
     * @param value        Short
     * @param defaultValue 默认值
     * @return short, 如果转换失败, 则返回 defaultValue
     */
    public static short toShort(final Short value, final short defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toShort");
        }
        return defaultValue;
    }

    /**
     * 基本类型对象 转 char
     * @param value Character
     * @return char, 如果转换失败, 则返回 defaultValue
     */
    public static char toChar(final Character value) {
        return toChar(value, (char) 0);
    }

    /**
     * 基本类型对象 转 char
     * @param value        Character
     * @param defaultValue 默认值
     * @return char, 如果转换失败, 则返回 defaultValue
     */
    public static char toChar(final Character value, final char defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toChar");
        }
        return defaultValue;
    }

    /**
     * 基本类型对象 转 byte
     * @param value Byte
     * @return byte, 如果转换失败, 则返回 defaultValue
     */
    public static byte toByte(final Byte value) {
        return toByte(value, (byte) 0);
    }

    /**
     * 基本类型对象 转 byte
     * @param value        Byte
     * @param defaultValue 默认值
     * @return byte, 如果转换失败, 则返回 defaultValue
     */
    public static byte toByte(final Byte value, final byte defaultValue) {
        if (value == null) return defaultValue;
        try {
            return value;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "toByte");
        }
        return defaultValue;
    }

    // ========
    // = 其他 =
    // ========

    /**
     * 字符串 获取 char (默认第一位)
     * @param str          String
     * @param defaultValue 默认值
     * @return 第一位值, 如果获取失败则返回 defaultValue
     */
    public static char toChar(final String str, final char defaultValue) {
        return toChar(str, 0, defaultValue);
    }

    /**
     * 字符串 获取 char
     * @param str          String
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 指定索引的值, 如果获取失败则返回 defaultValue
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

    // =

    /**
     * char 转换 unicode 编码
     * @param value char
     * @return int
     */
    public static int toCharInt(final char value) {
        return (int) value;
    }

    /**
     * 字符串 获取 char[]
     * @param str String
     * @return char[]
     */
    public static char[] toChars(final String str) {
        if (str != null) {
            try {
                return str.toCharArray();
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "toChars");
            }
        }
        return null;
    }

    /**
     * 字符串 获取 byte[]
     * @param str String
     * @return byte[]
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

    // =

    /**
     * 字符串转换对应的进制
     * <pre>
     *      如: parseInt("1f603", 16) = 128515
     * </pre>
     * @param str   String
     * @param radix 进制
     * @return 对应进制的值
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

    /**
     * 字符串转换对应的进制
     * @param str   String
     * @param radix 进制
     * @return 对应进制的值
     */
    public static long parseLong(final String str, final int radix) {
        if (str == null) return -1;
        try {
            return Long.parseLong(str, radix);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "parseLong");
        }
        return -1;
    }

    // =

//    String data = "测试";
//    // 转换二进制字符串
//    String result = toBinaryString(data.getBytes());
//    // 获取二进制数据
//    byte[] bytes = result.getBytes();
//    // 位移编码
//    bytesEncrypt(bytes);
//    // =
//    // 位移解码
//    bytesEncrypt(bytes);
//    // 二进制数据解码
//    byte[] byteResult = decodeBinary(new String(bytes));
//    // 转换为原始数据
//    String data1 = new String(byteResult);
//    // 判断是否一直
//    boolean equals = data.equals(data1);

    /**
     * 按位求补 byte[] 位移编/解码 （共用同一个方法）
     * @param data byte[]
     */
    public static void bytesBitwiseAND(final byte[] data) {
        if (data == null) return;
        for (int i = 0, len = data.length; i < len; i++) {
            int a = data[i];
            a = ~a; // 按位补运算符 => 翻转操作数的每一位，即0变成1，1变成0,再通过反转后的二进制初始化回16进制
            data[i] = (byte) a;
        }
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

    /**
     * 从 byte[] 上截取一段
     * @param data   byte[]
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的 byte[]
     */
    public static byte[] subBytes(final byte[] data, final int off, final int length) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            byte[] bytes = new byte[length];
            System.arraycopy(data, off, bytes, 0, length);
//            for (int i = off; i <= length; i++) {
//                bytes[i - off] = data[i];
//            }
            return bytes;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subBytes");
        }
        return null;
    }

    /**
     * 拼接 byte[] 数据
     * @param prefix 第一个byte[]
     * @param suffix 第二个byte[]
     * @return 拼接后的 byte[]
     */
    public static byte[] joinBytes(final byte[] prefix, final byte[] suffix) {
        if (prefix == null || suffix == null) return null;
        // 获取数据长度
        int prefixLength = prefix.length;
        int suffixLength = suffix.length;
        // 数据都为null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建 byte[]
        byte[] bytes = new byte[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, bytes, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, bytes, prefixLength, suffixLength);
        }
        return bytes;
    }

    // =

    /**
     * byte[] 转为 Object
     * @param bytes byte[]
     * @return {@link Object}
     */
    public static Object bytesToObject(final byte[] bytes) {
        if (bytes != null) {
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
        }
        return null;
    }

    /**
     * Object 转为 byte[]
     * @param object Object
     * @return byte[]
     */
    public static byte[] objectToBytes(final Object object) {
        if (object != null) {
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

    // =

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
    public static double[] intsToDoubles(final int off, final int[] datas) {
        return intsToDoubles(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 double[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return double[]
     */
    public static double[] intsToDoubles(final int off, final int length, final int[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        double[] doubles = new double[length - off];
        for (int i = 0, count = doubles.length; i < count; i++) {
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
    public static long[] intsToLongs(final int off, final int[] datas) {
        return intsToLongs(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 long[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return long[]
     */
    public static long[] intsToLongs(final int off, final int length, final int[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        long[] longs = new long[length - off];
        for (int i = 0, count = longs.length; i < count; i++) {
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
    public static float[] intsToFloats(final int off, final int[] datas) {
        return intsToFloats(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 float[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return float[]
     */
    public static float[] intsToFloats(final int off, final int length, final int[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        float[] floats = new float[length - off];
        for (int i = 0, count = floats.length; i < count; i++) {
            floats[i] = datas[off + i];
        }
        return floats;
    }

    // =

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
    public static String[] intsToStrings(final int off, final int[] datas) {
        return intsToStrings(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * int[] 转换 string[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  int[]
     * @return String[]
     */
    public static String[] intsToStrings(final int off, final int length, final int[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        String[] strings = new String[length - off];
        for (int i = 0, count = strings.length; i < count; i++) {
            strings[i] = datas[off + i] + "";
        }
        return strings;
    }

    // =

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
    public static int[] stringsToInts(final int off, final String... datas) {
        return stringsToInts(off, (datas != null) ? datas.length : 0, -1, datas);
    }


    /**
     * string[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return int[]
     */
    public static int[] stringsToInts(final int off, final int length, final String... datas) {
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
    public static int[] stringsToInts(final int off, final int length, final int errorValue, final String... datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        int[] ints = new int[length - off];
        for (int i = 0, count = ints.length; i < count; i++) {
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
    public static double[] stringsToDoubles(final int off, final String... datas) {
        return stringsToDoubles(off, (datas != null) ? datas.length : 0, -1d, datas);
    }


    /**
     * string[] 转换 double[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return double[]
     */
    public static double[] stringsToDoubles(final int off, final int length, final String... datas) {
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
    public static double[] stringsToDoubles(final int off, final int length, final double errorValue, final String... datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        double[] doubles = new double[length - off];
        for (int i = 0, count = doubles.length; i < count; i++) {
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
        return stringsToLongs(0, (datas != null) ? datas.length : 0, -1l, datas);
    }

    /**
     * string[] 转换 long[]
     * @param off   起始值
     * @param datas String[]
     * @return long[]
     */
    public static long[] stringsToLongs(final int off, final String... datas) {
        return stringsToLongs(off, (datas != null) ? datas.length : 0, -1l, datas);
    }


    /**
     * string[] 转换 long[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return long[]
     */
    public static long[] stringsToLongs(final int off, final int length, final String... datas) {
        return stringsToLongs(off, length, -1l, datas);
    }

    /**
     * string[] 转换 long[]
     * @param off        起始值
     * @param length     所需长度
     * @param errorValue 转换错误使用值
     * @param datas      String[]
     * @return long[]
     */
    public static long[] stringsToLongs(final int off, final int length, final long errorValue, final String... datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        long[] longs = new long[length - off];
        for (int i = 0, count = longs.length; i < count; i++) {
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
    public static float[] stringsToFloats(final int off, final String... datas) {
        return stringsToFloats(off, (datas != null) ? datas.length : 0, -1f, datas);
    }


    /**
     * string[] 转换 float[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  String[]
     * @return float[]
     */
    public static float[] stringsToFloats(final int off, final int length, final String... datas) {
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
    public static float[] stringsToFloats(final int off, final int length, final float errorValue, final String... datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        float[] floats = new float[length - off];
        for (int i = 0, count = floats.length; i < count; i++) {
            try {
                floats[i] = Float.parseFloat(datas[off + i]);
            } catch (Exception e) {
                floats[i] = errorValue;
            }
        }
        return floats;
    }

    // =

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
    public static int[] doublesToInts(final int off, final double[] datas) {
        return doublesToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * double[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  double[]
     * @return int[]
     */
    public static int[] doublesToInts(final int off, final int length, final double[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        int[] ints = new int[length - off];
        for (int i = 0, count = ints.length; i < count; i++) {
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
    public static int[] longsToInts(final int off, final long[] datas) {
        return longsToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * long[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  long[]
     * @return int[]
     */
    public static int[] longsToInts(final int off, final int length, final long[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        int[] ints = new int[length - off];
        for (int i = 0, count = ints.length; i < count; i++) {
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
    public static int[] floatsToInts(final int off, final float[] datas) {
        return floatsToInts(off, (datas != null) ? datas.length : 0, datas);
    }

    /**
     * float[] 转换 int[]
     * @param off    起始值
     * @param length 所需长度
     * @param datas  float[]
     * @return int[]
     */
    public static int[] floatsToInts(final int off, final int length, final float[] datas) {
        if (off < 0 || length < 1 || off >= length || datas == null || length > datas.length)
            return null;
        int[] ints = new int[length - off];
        for (int i = 0, count = ints.length; i < count; i++) {
            try {
                ints[i] = (int) datas[off + i];
            } catch (Exception e) {
            }
        }
        return ints;
    }

    // ===================
    // = Binary - 二进制 =
    // ===================

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
            JCLogUtils.eTag(TAG, e, "bytesToBits");
        }
        return null;
    }

    /**
     * 二进制字符串 转换 byte[] 解码
     * 例: "011000010111001101100100" 传入 decodeBinary, 返回 byte[], 通过new String(byte()) 获取 asd => 配合 toBinaryString 使用
     * @param str String
     * @return 解码后的 byte[]
     */
    public static byte[] decodeBinary(final String str) {
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

    // ======================
    // = Hex - 十六进制处理 =
    // ======================

    /**
     * 将十六进制字节数组解码
     * @param data 十六进制 byte[]
     * @return 十六进制转(解)码后的数据
     */
    public static byte[] decodeHex(final byte[] data) {
        return decodeHex((length(data) == 0) ? null : bytesToChars(data));
    }

    /**
     * 将十六进制字符串解码
     * @param str 十六进制 String
     * @return 十六进制转(解)码后的数据
     */
    public static byte[] decodeHex(final String str) {
        return decodeHex(isEmpty(str) ? null : str.toCharArray());
    }

    /**
     * 将十六进制字符数组解码
     * @param data 十六进制 char[]
     * @return 十六进制转(解)码后的数据
     */
    public static byte[] decodeHex(final char[] data) {
        if (data == null) return null;
        try {
            int len = data.length;
            byte[] out = new byte[len >> 1];
            // 16进制由两个字符组成
            for (int i = 0, j = 0; j < len; i++) {
                int f = toDigit(data[j], j) << 4;
                j++;
                f = f | toDigit(data[j], j);
                j++;
                out[i] = (byte) (f & 0xFF);
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
     * @return 十六进制转(解)码后的整数
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
     * @throws Exception 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    private static int toDigit(final char ch, final int index) throws Exception {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new Exception("Illegal hexadecimal character " + ch + " at index " + index);
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
     *      如: 传入 0x1f603 => toHexString(0x1f603); 返回: 1f603
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
     * @param str String
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(final String str) {
        return toHexChars(str, true);
    }

    /**
     * 将 string 转换为 十六进制 char[]
     * @param str         String
     * @param toLowerCase true: 小写格式, {@code false} 大写格式
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(final String str, final boolean toLowerCase) {
        return toHexChars(isEmpty(str) ? null : str.getBytes(), toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
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
     * @param toLowerCase true: 小写格式, {@code false} 大写格式
     * @return 十六进制 char[]
     */
    public static char[] toHexChars(final byte[] data, final boolean toLowerCase) {
        return toHexChars(data, toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    /**
     * 将 byte[] 转换为 十六进制 char[]
     * @param data      byte[]
     * @param hexDigits {@link ConvertUtils#HEX_DIGITS}， {@link ConvertUtils#HEX_DIGITS_UPPER}
     * @return 十六进制 char[]
     */
    private static char[] toHexChars(final byte[] data, final char[] hexDigits) {
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
     * @param str String
     * @return 十六进制字符串
     */
    public static String toHexString(final String str) {
        return toHexString(str, true);
    }

    /**
     * 将 string 转换 十六进制字符串
     * @param str         String
     * @param toLowerCase true: 小写格式, {@code false} 大写格式
     * @return 十六进制字符串
     */
    public static String toHexString(final String str, final boolean toLowerCase) {
        return toHexString(isEmpty(str) ? null : str.getBytes(), toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    // =

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data byte[]
     * @return 十六进制字符串
     */
    public static String toHexString(final byte[] data) {
        return toHexString(data, true);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data        byte[]
     * @param toLowerCase true: 小写格式, {@code false} 大写格式
     * @return 十六进制字符串
     */
    public static String toHexString(final byte[] data, final boolean toLowerCase) {
        return toHexString(data, toLowerCase ? HEX_DIGITS : HEX_DIGITS_UPPER);
    }

    /**
     * 将 byte[] 转换 十六进制字符串
     * @param data      byte[]
     * @param hexDigits {@link ConvertUtils#HEX_DIGITS}， {@link ConvertUtils#HEX_DIGITS_UPPER}
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

    // =

    /**
     * 判断是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 获取数组长度
     * @param objects Object[]
     * @return array[].length
     */
    private static int length(final Object... objects) {
        return objects != null ? objects.length : 0;
    }
}
