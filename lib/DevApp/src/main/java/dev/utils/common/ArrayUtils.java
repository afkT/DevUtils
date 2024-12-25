package dev.utils.common;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.utils.DevFinal;
import dev.utils.JCLogUtils;

/**
 * detail: Array 数组工具类
 * @author Ttt
 * <pre>
 *     // 升序
 *     Arrays.sort(arrays);
 *     // 降序 ( 只能对对象数组降序 )
 *     Arrays.sort(arrays, Collections.reverseOrder());
 * </pre>
 */
public final class ArrayUtils {

    private ArrayUtils() {
    }

    // 日志 TAG
    private static final String TAG = ArrayUtils.class.getSimpleName();

    // =========
    // = Array =
    // =========

    /**
     * 判断数组是否为 null
     * @param objects object[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param ints int[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final int[] ints) {
        return ints == null || ints.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param bytes byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param chars char[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final char[] chars) {
        return chars == null || chars.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param shorts short[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final short[] shorts) {
        return shorts == null || shorts.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param longs long[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final long[] longs) {
        return longs == null || longs.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param floats float[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final float[] floats) {
        return floats == null || floats.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param doubles double[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final double[] doubles) {
        return doubles == null || doubles.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param booleans boolean[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final boolean[] booleans) {
        return booleans == null || booleans.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param object Array[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Object object) {
        return object == null || length(object) == 0;
    }

    // =

    /**
     * 判断数组是否不为 null
     * @param objects object[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return objects != null && objects.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param ints int[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final int[] ints) {
        return ints != null && ints.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param bytes byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final byte[] bytes) {
        return bytes != null && bytes.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param chars char[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final char[] chars) {
        return chars != null && chars.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param shorts short[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final short[] shorts) {
        return shorts != null && shorts.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param longs long[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final long[] longs) {
        return longs != null && longs.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param floats float[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final float[] floats) {
        return floats != null && floats.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param doubles double[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final double[] doubles) {
        return doubles != null && doubles.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param booleans boolean[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final boolean[] booleans) {
        return booleans != null && booleans.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param object Array[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Object object) {
        return object != null && length(object) != 0;
    }

    // ==========
    // = 判断长度 =
    // ==========

    /**
     * 获取数组长度
     * @param objects object[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final Object[] objects) {
        return length(objects, 0);
    }

    /**
     * 获取数组长度
     * @param ints int[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final int[] ints) {
        return length(ints, 0);
    }

    /**
     * 获取数组长度
     * @param bytes byte[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final byte[] bytes) {
        return length(bytes, 0);
    }

    /**
     * 获取数组长度
     * @param chars char[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final char[] chars) {
        return length(chars, 0);
    }

    /**
     * 获取数组长度
     * @param shorts short[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final short[] shorts) {
        return length(shorts, 0);
    }

    /**
     * 获取数组长度
     * @param longs long[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final long[] longs) {
        return length(longs, 0);
    }

    /**
     * 获取数组长度
     * @param floats float[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final float[] floats) {
        return length(floats, 0);
    }

    /**
     * 获取数组长度
     * @param doubles double[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final double[] doubles) {
        return length(doubles, 0);
    }

    /**
     * 获取数组长度
     * @param booleans boolean[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans) {
        return length(booleans, 0);
    }

    /**
     * 获取数组长度
     * @param object Array[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final Object object) {
        return length(object, 0);
    }

    // =

    /**
     * 获取数组长度
     * @param objects       object[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final Object[] objects,
            final int defaultLength
    ) {
        return objects != null ? objects.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param ints          int[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final int[] ints,
            final int defaultLength
    ) {
        return ints != null ? ints.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param bytes         byte[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final byte[] bytes,
            final int defaultLength
    ) {
        return bytes != null ? bytes.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param chars         char[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final char[] chars,
            final int defaultLength
    ) {
        return chars != null ? chars.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param shorts        short[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final short[] shorts,
            final int defaultLength
    ) {
        return shorts != null ? shorts.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param longs         long[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final long[] longs,
            final int defaultLength
    ) {
        return longs != null ? longs.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param floats        float[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final float[] floats,
            final int defaultLength
    ) {
        return floats != null ? floats.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param doubles       double[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final double[] doubles,
            final int defaultLength
    ) {
        return doubles != null ? doubles.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param booleans      boolean[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final boolean[] booleans,
            final int defaultLength
    ) {
        return booleans != null ? booleans.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param object        Array[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(
            final Object object,
            final int defaultLength
    ) {
        if (object != null) {
            Class<?> clazz = object.getClass();
            // 判断是否数组类型
            if (clazz.isArray()) {
                try {
                    // = 基本数据类型 =
                    if (clazz.isAssignableFrom(int[].class)) {
                        return ((int[]) object).length;
                    } else if (clazz.isAssignableFrom(boolean[].class)) {
                        return ((boolean[]) object).length;
                    } else if (clazz.isAssignableFrom(long[].class)) {
                        return ((long[]) object).length;
                    } else if (clazz.isAssignableFrom(double[].class)) {
                        return ((double[]) object).length;
                    } else if (clazz.isAssignableFrom(float[].class)) {
                        return ((float[]) object).length;
                    } else if (clazz.isAssignableFrom(byte[].class)) {
                        return ((byte[]) object).length;
                    } else if (clazz.isAssignableFrom(char[].class)) {
                        return ((char[]) object).length;
                    } else if (clazz.isAssignableFrom(short[].class)) {
                        return ((short[]) object).length;
                    }
                    return ((Object[]) object).length;
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "length");
                }
            }
        }
        return defaultLength;
    }

    // =

    /**
     * 判断数组长度是否等于期望长度
     * @param objects object[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final Object[] objects,
            final int length
    ) {
        return objects != null && objects.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param ints   int[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final int[] ints,
            final int length
    ) {
        return ints != null && ints.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param bytes  byte[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final byte[] bytes,
            final int length
    ) {
        return bytes != null && bytes.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param chars  char[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final char[] chars,
            final int length
    ) {
        return chars != null && chars.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param shorts short[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final short[] shorts,
            final int length
    ) {
        return shorts != null && shorts.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param longs  long[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final long[] longs,
            final int length
    ) {
        return longs != null && longs.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param floats float[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final float[] floats,
            final int length
    ) {
        return floats != null && floats.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param doubles double[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final double[] doubles,
            final int length
    ) {
        return doubles != null && doubles.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param booleans boolean[]
     * @param length   期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final boolean[] booleans,
            final int length
    ) {
        return booleans != null && booleans.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param object Array[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(
            final Object object,
            final int length
    ) {
        return object != null && length(object) == length;
    }

    // =============
    // = 获取长度总和 =
    // =============

    /**
     * 获取数组长度总和
     * @param objects Array[]
     * @return 数组长度总和
     */
    public static int getCount(final Object... objects) {
        if (objects == null) return 0;
        int count = 0;
        for (Object object : objects) {
            count += length(object);
        }
        return count;
    }

    // ==========
    // = 数据获取 =
    // ==========

    /**
     * 获取数组对应索引数据
     * @param array 数组
     * @param pos   索引
     * @param <T>   泛型
     * @return 数组指定索引的值
     */
    public static <T> T getByArray(
            final Object array,
            final int pos
    ) {
        return getByArray(array, pos, null);
    }

    /**
     * 获取数组对应索引数据
     * @param array        数组
     * @param pos          索引
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 数组指定索引的值
     */
    public static <T> T getByArray(
            final Object array,
            final int pos,
            final T defaultValue
    ) {
        if (array == null || pos < 0) return defaultValue;
        try {
            return (T) Array.get(array, pos);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getByArray");
        }
        return defaultValue;
    }

    // =

    /**
     * 获取数组对应索引数据
     * @param array 数组
     * @param pos   索引
     * @param <T>   泛型
     * @return 数组指定索引的值
     */
    public static <T> T get(
            final T[] array,
            final int pos
    ) {
        return get(array, pos, null);
    }

    /**
     * 获取数组对应索引数据
     * @param ints int[]
     * @param pos  索引
     * @return 数组指定索引的值
     */
    public static int get(
            final int[] ints,
            final int pos
    ) {
        return get(ints, pos, DevFinal.DEFAULT.ERROR_INT);
    }

    /**
     * 获取数组对应索引数据
     * @param bytes byte[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static byte get(
            final byte[] bytes,
            final int pos
    ) {
        return get(bytes, pos, DevFinal.DEFAULT.ERROR_BYTE);
    }

    /**
     * 获取数组对应索引数据
     * @param chars char[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static char get(
            final char[] chars,
            final int pos
    ) {
        return get(chars, pos, DevFinal.DEFAULT.ERROR_CHAR);
    }

    /**
     * 获取数组对应索引数据
     * @param shorts short[]
     * @param pos    索引
     * @return 数组指定索引的值
     */
    public static short get(
            final short[] shorts,
            final int pos
    ) {
        return get(shorts, pos, DevFinal.DEFAULT.ERROR_SHORT);
    }

    /**
     * 获取数组对应索引数据
     * @param longs long[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static long get(
            final long[] longs,
            final int pos
    ) {
        return get(longs, pos, DevFinal.DEFAULT.ERROR_LONG);
    }

    /**
     * 获取数组对应索引数据
     * @param floats float[]
     * @param pos    索引
     * @return 数组指定索引的值
     */
    public static float get(
            final float[] floats,
            final int pos
    ) {
        return get(floats, pos, DevFinal.DEFAULT.ERROR_FLOAT);
    }

    /**
     * 获取数组对应索引数据
     * @param doubles double[]
     * @param pos     索引
     * @return 数组指定索引的值
     */
    public static double get(
            final double[] doubles,
            final int pos
    ) {
        return get(doubles, pos, DevFinal.DEFAULT.ERROR_DOUBLE);
    }

    /**
     * 获取数组对应索引数据
     * @param booleans boolean[]
     * @param pos      索引
     * @return 数组指定索引的值
     */
    public static boolean get(
            final boolean[] booleans,
            final int pos
    ) {
        return get(booleans, pos, DevFinal.DEFAULT.ERROR_BOOLEAN);
    }

    // =

    /**
     * 获取数组对应索引数据
     * @param array        数组
     * @param pos          索引
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static <T> T get(
            final T[] array,
            final int pos,
            final T defaultValue
    ) {
        if (array == null || pos < 0) return defaultValue;
        try {
            return array[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param ints         int[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static int get(
            final int[] ints,
            final int pos,
            final int defaultValue
    ) {
        if (ints == null || pos < 0) return defaultValue;
        try {
            return ints[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param bytes        byte[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static byte get(
            final byte[] bytes,
            final int pos,
            final byte defaultValue
    ) {
        if (bytes == null || pos < 0) return defaultValue;
        try {
            return bytes[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param chars        char[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static char get(
            final char[] chars,
            final int pos,
            final char defaultValue
    ) {
        if (chars == null || pos < 0) return defaultValue;
        try {
            return chars[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param shorts       short[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static short get(
            final short[] shorts,
            final int pos,
            final short defaultValue
    ) {
        if (shorts == null || pos < 0) return defaultValue;
        try {
            return shorts[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param longs        long[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static long get(
            final long[] longs,
            final int pos,
            final long defaultValue
    ) {
        if (longs == null || pos < 0) return defaultValue;
        try {
            return longs[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param floats       float[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static float get(
            final float[] floats,
            final int pos,
            final float defaultValue
    ) {
        if (floats == null || pos < 0) return defaultValue;
        try {
            return floats[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param doubles      double[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static double get(
            final double[] doubles,
            final int pos,
            final double defaultValue
    ) {
        if (doubles == null || pos < 0) return defaultValue;
        try {
            return doubles[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    /**
     * 获取数组对应索引数据
     * @param booleans     boolean[]
     * @param pos          索引
     * @param defaultValue 默认值
     * @return 数组指定索引的值, 如果获取失败, 则返回 defaultValue
     */
    public static boolean get(
            final boolean[] booleans,
            final int pos,
            final boolean defaultValue
    ) {
        if (booleans == null || pos < 0) return defaultValue;
        try {
            return booleans[pos];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "get %s", pos);
        }
        return defaultValue;
    }

    // =

    /**
     * 获取数组第一条数据
     * @param array 数组
     * @param <T>   泛型
     * @return 数组索引为 0 的值
     */
    public static <T> T getFirst(final T[] array) {
        return get(array, 0);
    }

    /**
     * 获取数组第一条数据
     * @param ints int[]
     * @return 数组索引为 0 的值
     */
    public static int getFirst(final int[] ints) {
        return get(ints, 0);
    }

    /**
     * 获取数组第一条数据
     * @param bytes byte[]
     * @return 数组索引为 0 的值
     */
    public static byte getFirst(final byte[] bytes) {
        return get(bytes, 0);
    }

    /**
     * 获取数组第一条数据
     * @param chars char[]
     * @return 数组索引为 0 的值
     */
    public static char getFirst(final char[] chars) {
        return get(chars, 0);
    }

    /**
     * 获取数组第一条数据
     * @param shorts short[]
     * @return 数组索引为 0 的值
     */
    public static short getFirst(final short[] shorts) {
        return get(shorts, 0);
    }

    /**
     * 获取数组第一条数据
     * @param longs long[]
     * @return 数组索引为 0 的值
     */
    public static long getFirst(final long[] longs) {
        return get(longs, 0);
    }

    /**
     * 获取数组第一条数据
     * @param floats float[]
     * @return 数组索引为 0 的值
     */
    public static float getFirst(final float[] floats) {
        return get(floats, 0);
    }

    /**
     * 获取数组第一条数据
     * @param doubles double[]
     * @return 数组索引为 0 的值
     */
    public static double getFirst(final double[] doubles) {
        return get(doubles, 0);
    }

    /**
     * 获取数组第一条数据
     * @param booleans boolean[]
     * @return 数组索引为 0 的值
     */
    public static boolean getFirst(final boolean[] booleans) {
        return get(booleans, 0);
    }

    // =

    /**
     * 获取数组最后一条数据
     * @param array 数组
     * @param <T>   泛型
     * @return 数组索引 length - 1 的值
     */
    public static <T> T getLast(final T[] array) {
        return get(array, length(array) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param ints int[]
     * @return 数组索引 length - 1 的值
     */
    public static int getLast(final int[] ints) {
        return get(ints, length(ints) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param bytes byte[]
     * @return 数组索引 length - 1 的值
     */
    public static byte getLast(final byte[] bytes) {
        return get(bytes, length(bytes) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param chars char[]
     * @return 数组索引 length - 1 的值
     */
    public static char getLast(final char[] chars) {
        return get(chars, length(chars) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param shorts short[]
     * @return 数组索引 length - 1 的值
     */
    public static short getLast(final short[] shorts) {
        return get(shorts, length(shorts) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param longs long[]
     * @return 数组索引 length - 1 的值
     */
    public static long getLast(final long[] longs) {
        return get(longs, length(longs) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param floats float[]
     * @return 数组索引 length - 1 的值
     */
    public static float getLast(final float[] floats) {
        return get(floats, length(floats) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param doubles double[]
     * @return 数组索引 length - 1 的值
     */
    public static double getLast(final double[] doubles) {
        return get(doubles, length(doubles) - 1);
    }

    /**
     * 获取数组最后一条数据
     * @param booleans boolean[]
     * @return 数组索引 length - 1 的值
     */
    public static boolean getLast(final boolean[] booleans) {
        return get(booleans, length(booleans) - 1);
    }

    // ==========
    // = 数据获取 =
    // ==========

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param notNull 是否不允许值为 null
     * @param offset  偏移量
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final T[] array,
            final T value,
            final int number,
            final boolean notNull,
            final int offset
    ) {
        if (array != null) {
            if (notNull && value == null) {
                return null;
            }
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    T t = array[i];
                    // 判断是否一样
                    if (equals(t, value)) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return null;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param notNull 是否不允许值为 null
     * @param offset  偏移量
     * @param <T>     泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final T[] array,
            final T value,
            final int number,
            final boolean notNull,
            final int offset
    ) {
        if (array != null) {
            if (notNull && value == null) {
                return -1;
            }
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    T t = array[i];
                    // 判断是否一样
                    if (equals(t, value)) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final T[] array,
            final T value
    ) {
        return get(array, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param <T>    泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final T[] array,
            final T value,
            final int number
    ) {
        return get(array, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array   数组
     * @param value   值
     * @param notNull 是否不允许值为 null
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final T[] array,
            final T value,
            final boolean notNull
    ) {
        return get(array, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param notNull 是否不允许值为 null
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(
            final T[] array,
            final T value,
            final int number,
            final boolean notNull
    ) {
        return get(array, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为 null
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应索引的值
     */
    public static <T> T getNotNull(
            final T[] array,
            final T value
    ) {
        return get(array, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为 null
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param <T>    泛型
     * @return 对应索引的值
     */
    public static <T> T getNotNull(
            final T[] array,
            final T value,
            final int number
    ) {
        return get(array, value, number, true, 0);
    }

    // =

    /**
     * 根据指定 value 获取索引
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final T[] array,
            final T value
    ) {
        return getPosition(array, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param <T>    泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final T[] array,
            final T value,
            final int number
    ) {
        return getPosition(array, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array   数组
     * @param value   值
     * @param notNull 是否不允许值为 null
     * @param <T>     泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final T[] array,
            final T value,
            final boolean notNull
    ) {
        return getPosition(array, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param notNull 是否不允许值为 null
     * @param <T>     泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(
            final T[] array,
            final T value,
            final int number,
            final boolean notNull
    ) {
        return getPosition(array, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取索引, 不允许值为 null
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(
            final T[] array,
            final T value
    ) {
        return getPosition(array, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取索引, 不允许值为 null
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param <T>    泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(
            final T[] array,
            final T value,
            final int number
    ) {
        return getPosition(array, value, number, true, 0);
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static int get(
            final int[] array,
            final int value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    int valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final int[] array,
            final int value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    int valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static byte get(
            final byte[] array,
            final byte value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    byte valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return (byte) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final byte[] array,
            final byte value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    byte valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static char get(
            final char[] array,
            final char value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    char valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return (char) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final char[] array,
            final char value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    char valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static short get(
            final short[] array,
            final short value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    short valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return (short) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final short[] array,
            final short value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    short valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static long get(
            final long[] array,
            final long value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    long valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return -1L;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final long[] array,
            final long value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    long valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static float get(
            final float[] array,
            final float value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    float valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return (float) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final float[] array,
            final float value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    float valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static double get(
            final double[] array,
            final double value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    double valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return -1D;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final double[] array,
            final double value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    double valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static boolean get(
            final boolean[] array,
            final boolean value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    boolean valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return array[i + offset];
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get");
            }
        }
        return false;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数 ( 从 0 开始, 0 = 1 )
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(
            final boolean[] array,
            final boolean value,
            final int number,
            final int offset
    ) {
        if (array != null) {
            try {
                // 保存当前临时次数
                int temp = number;
                // 进行循环判断
                for (int i = 0, len = array.length; i < len; i++) {
                    boolean valueI = array[i];
                    // 判断是否一样
                    if (valueI == value) {
                        if (temp - 1 < 0) {
                            return i + offset;
                        }
                        temp--;
                    }
                }
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "getPosition");
            }
        }
        return -1;
    }

    // ==========
    // = 转换处理 =
    // ==========

    /**
     * int[] 转换 Integer[]
     * @param ints int[]
     * @return {@link Integer[]}
     */
    public static Integer[] intsToIntegers(final int[] ints) {
        if (ints != null) {
            int len = ints.length;
            // 创建数组
            Integer[] array = new Integer[len];
            for (int i = 0; i < len; i++) {
                array[i] = ints[i];
            }
            return array;
        }
        return null;
    }

    /**
     * byte[] 转换 Byte[]
     * @param bytes byte[]
     * @return {@link Byte[]}
     */
    public static Byte[] bytesToBytes(final byte[] bytes) {
        if (bytes != null) {
            int len = bytes.length;
            // 创建数组
            Byte[] array = new Byte[len];
            for (int i = 0; i < len; i++) {
                array[i] = bytes[i];
            }
            return array;
        }
        return null;
    }

    /**
     * char[] 转换 Character[]
     * @param chars char[]
     * @return {@link Character[]}
     */
    public static Character[] charsToCharacters(final char[] chars) {
        if (chars != null) {
            int len = chars.length;
            // 创建数组
            Character[] array = new Character[len];
            for (int i = 0; i < len; i++) {
                array[i] = chars[i];
            }
            return array;
        }
        return null;
    }

    /**
     * short[] 转换 Short[]
     * @param shorts short[]
     * @return {@link Short[]}
     */
    public static Short[] shortsToShorts(final short[] shorts) {
        if (shorts != null) {
            int len = shorts.length;
            // 创建数组
            Short[] array = new Short[len];
            for (int i = 0; i < len; i++) {
                array[i] = shorts[i];
            }
            return array;
        }
        return null;
    }

    /**
     * long[] 转换 Long[]
     * @param longs long[]
     * @return {@link Long[]}
     */
    public static Long[] longsToLongs(final long[] longs) {
        if (longs != null) {
            int len = longs.length;
            // 创建数组
            Long[] array = new Long[len];
            for (int i = 0; i < len; i++) {
                array[i] = longs[i];
            }
            return array;
        }
        return null;
    }

    /**
     * float[] 转换 Float[]
     * @param floats float[]
     * @return {@link Float[]}
     */
    public static Float[] floatsToFloats(final float[] floats) {
        if (floats != null) {
            int len = floats.length;
            // 创建数组
            Float[] array = new Float[len];
            for (int i = 0; i < len; i++) {
                array[i] = floats[i];
            }
            return array;
        }
        return null;
    }

    /**
     * double[] 转换 Double[]
     * @param doubles double[]
     * @return {@link Double[]}
     */
    public static Double[] doublesToDoubles(final double[] doubles) {
        if (doubles != null) {
            int len = doubles.length;
            // 创建数组
            Double[] array = new Double[len];
            for (int i = 0; i < len; i++) {
                array[i] = doubles[i];
            }
            return array;
        }
        return null;
    }

    /**
     * boolean[] 转换 Boolean[]
     * @param booleans boolean[]
     * @return {@link Boolean[]}
     */
    public static Boolean[] booleansToBooleans(final boolean[] booleans) {
        if (booleans != null) {
            int len = booleans.length;
            // 创建数组
            Boolean[] array = new Boolean[len];
            for (int i = 0; i < len; i++) {
                array[i] = booleans[i];
            }
            return array;
        }
        return null;
    }

    // =

    /**
     * Integer[] 转换 int[]
     * @param integers     Integer[]
     * @param defaultValue 转换失败使用得默认值
     * @return int[]
     */
    public static int[] integersToInts(
            final Integer[] integers,
            final int defaultValue
    ) {
        if (integers != null) {
            int len = integers.length;
            // 创建数组
            int[] array = new int[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = integers[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Byte[] 转换 byte[]
     * @param bytes        Byte[]
     * @param defaultValue 转换失败使用得默认值
     * @return byte[]
     */
    public static byte[] bytesToBytes(
            final Byte[] bytes,
            final byte defaultValue
    ) {
        if (bytes != null) {
            int len = bytes.length;
            // 创建数组
            byte[] array = new byte[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = bytes[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Character[] 转换 char[]
     * @param characters   Character[]
     * @param defaultValue 转换失败使用得默认值
     * @return char[]
     */
    public static char[] charactersToChars(
            final Character[] characters,
            final char defaultValue
    ) {
        if (characters != null) {
            int len = characters.length;
            // 创建数组
            char[] array = new char[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = characters[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Short[] 转换 short[]
     * @param shorts       Short[]
     * @param defaultValue 转换失败使用得默认值
     * @return short[]
     */
    public static short[] shortsToShorts(
            final Short[] shorts,
            final short defaultValue
    ) {
        if (shorts != null) {
            int len = shorts.length;
            // 创建数组
            short[] array = new short[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = shorts[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Long[] 转换 long[]
     * @param longs        Long[]
     * @param defaultValue 转换失败使用得默认值
     * @return long[]
     */
    public static long[] longsToLongs(
            final Long[] longs,
            final long defaultValue
    ) {
        if (longs != null) {
            int len = longs.length;
            // 创建数组
            long[] array = new long[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = longs[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Float[] 转换 float[]
     * @param floats       Float[]
     * @param defaultValue 转换失败使用得默认值
     * @return float[]
     */
    public static float[] floatsToFloats(
            final Float[] floats,
            final float defaultValue
    ) {
        if (floats != null) {
            int len = floats.length;
            // 创建数组
            float[] array = new float[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = floats[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Double[] 转换 double[]
     * @param doubles      Double[]
     * @param defaultValue 转换失败使用得默认值
     * @return double[]
     */
    public static double[] doublesToDoubles(
            final Double[] doubles,
            final double defaultValue
    ) {
        if (doubles != null) {
            int len = doubles.length;
            // 创建数组
            double[] array = new double[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = doubles[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    /**
     * Boolean[] 转换 boolean[]
     * @param booleans     Boolean[]
     * @param defaultValue 转换失败使用得默认值
     * @return boolean[]
     */
    public static boolean[] booleansToBooleans(
            final Boolean[] booleans,
            final boolean defaultValue
    ) {
        if (booleans != null) {
            int len = booleans.length;
            // 创建数组
            boolean[] array = new boolean[len];
            for (int i = 0; i < len; i++) {
                try {
                    array[i] = booleans[i];
                } catch (Exception e) {
                    array[i] = defaultValue;
                }
            }
            return array;
        }
        return null;
    }

    // ============
    // = 转换 List =
    // ============

    /**
     * 转换数组为集合
     * @param array 数组
     * @param <T>   泛型
     * @return {@link List<T>}
     */
    public static <T> List<T> asList(final T[] array) {
        if (array != null) {
            try {
                return new ArrayList<>(Arrays.asList(array));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "asList");
            }
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param array 数组
     * @param <T>   泛型
     * @return {@link List<T>}
     */
    public static <T> List<T> asListArgs(final T... array) {
        if (array != null) {
            try {
                return new ArrayList<>(Arrays.asList(array));
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "asListArgs");
            }
        }
        return null;
    }

    // =

    /**
     * 转换数组为集合
     * @param ints int[]
     * @return {@link List<Integer>}
     */
    public static List<Integer> asList(final int[] ints) {
        if (ints != null) {
            List<Integer> lists = new ArrayList<>();
            for (int value : ints) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param bytes byte[]
     * @return {@link List<Byte>}
     */
    public static List<Byte> asList(final byte[] bytes) {
        if (bytes != null) {
            List<Byte> lists = new ArrayList<>();
            for (byte value : bytes) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param chars char[]
     * @return {@link List<Character>}
     */
    public static List<Character> asList(final char[] chars) {
        if (chars != null) {
            List<Character> lists = new ArrayList<>();
            for (char value : chars) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param shorts short[]
     * @return {@link List<Short>}
     */
    public static List<Short> asList(final short[] shorts) {
        if (shorts != null) {
            List<Short> lists = new ArrayList<>();
            for (short value : shorts) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param longs long[]
     * @return {@link List<Long>}
     */
    public static List<Long> asList(final long[] longs) {
        if (longs != null) {
            List<Long> lists = new ArrayList<>();
            for (long value : longs) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param floats float[]
     * @return {@link List<Float>}
     */
    public static List<Float> asList(final float[] floats) {
        if (floats != null) {
            List<Float> lists = new ArrayList<>();
            for (float value : floats) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param doubles double[]
     * @return {@link List<Double>}
     */
    public static List<Double> asList(final double[] doubles) {
        if (doubles != null) {
            List<Double> lists = new ArrayList<>();
            for (double value : doubles) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param booleans boolean[]
     * @return {@link List<Boolean>}
     */
    public static List<Boolean> asList(final boolean[] booleans) {
        if (booleans != null) {
            List<Boolean> lists = new ArrayList<>();
            for (boolean value : booleans) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    // =

    /**
     * 转换数组为集合
     * @param ints int[]
     * @return {@link List<Integer>}
     */
    public static List<Integer> asListArgsInt(final int... ints) {
        if (ints != null) {
            List<Integer> lists = new ArrayList<>();
            for (int value : ints) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param bytes byte[]
     * @return {@link List<Byte>}
     */
    public static List<Byte> asListArgsByte(final byte... bytes) {
        if (bytes != null) {
            List<Byte> lists = new ArrayList<>();
            for (byte value : bytes) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param chars char[]
     * @return {@link List<Character>}
     */
    public static List<Character> asListArgsChar(final char... chars) {
        if (chars != null) {
            List<Character> lists = new ArrayList<>();
            for (char value : chars) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param shorts short[]
     * @return {@link List<Short>}
     */
    public static List<Short> asListArgsShort(final short... shorts) {
        if (shorts != null) {
            List<Short> lists = new ArrayList<>();
            for (short value : shorts) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param longs long[]
     * @return {@link List<Long>}
     */
    public static List<Long> asListArgsLong(final long... longs) {
        if (longs != null) {
            List<Long> lists = new ArrayList<>();
            for (long value : longs) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param floats float[]
     * @return {@link List<Float>}
     */
    public static List<Float> asListArgsFloat(final float... floats) {
        if (floats != null) {
            List<Float> lists = new ArrayList<>();
            for (float value : floats) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param doubles double[]
     * @return {@link List<Double>}
     */
    public static List<Double> asListArgsDouble(final double... doubles) {
        if (doubles != null) {
            List<Double> lists = new ArrayList<>();
            for (double value : doubles) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    /**
     * 转换数组为集合
     * @param booleans boolean[]
     * @return {@link List<Boolean>}
     */
    public static List<Boolean> asListArgsBoolean(final boolean... booleans) {
        if (booleans != null) {
            List<Boolean> lists = new ArrayList<>();
            for (boolean value : booleans) {
                lists.add(value);
            }
            return lists;
        }
        return null;
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(
            final T value1,
            final T value2
    ) {
        return ObjectUtils.equals(value1, value2);
    }

    // =

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @param <T>    泛型
     * @return 拼接后的数组集合
     */
    public static <T> T[] arrayCopy(
            final T[] prefix,
            final T[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建集合
        List<T> lists = new ArrayList<>(prefixLength + suffixLength);
        // 进行判断处理
        if (prefixLength != 0) {
            lists.addAll(Arrays.asList(prefix).subList(0, prefixLength));
        }
        if (suffixLength != 0) {
            lists.addAll(Arrays.asList(suffix).subList(0, suffixLength));
        }
        if (prefix != null) {
            return (T[]) Arrays.copyOf(lists.toArray(), lists.size(), prefix.getClass());
        } else {
            return (T[]) Arrays.copyOf(lists.toArray(), lists.size(), suffix.getClass());
        }
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static int[] arrayCopy(
            final int[] prefix,
            final int[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        int[] arrays = new int[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static byte[] arrayCopy(
            final byte[] prefix,
            final byte[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        byte[] arrays = new byte[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static char[] arrayCopy(
            final char[] prefix,
            final char[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        char[] arrays = new char[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static short[] arrayCopy(
            final short[] prefix,
            final short[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        short[] arrays = new short[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static long[] arrayCopy(
            final long[] prefix,
            final long[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        long[] arrays = new long[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static float[] arrayCopy(
            final float[] prefix,
            final float[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        float[] arrays = new float[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static double[] arrayCopy(
            final double[] prefix,
            final double[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        double[] arrays = new double[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    /**
     * 拼接数组
     * @param prefix 第一个数组
     * @param suffix 第二个数组
     * @return 拼接后的数组
     */
    public static boolean[] arrayCopy(
            final boolean[] prefix,
            final boolean[] suffix
    ) {
        // 获取数据长度
        int prefixLength = (prefix != null) ? prefix.length : 0;
        int suffixLength = (suffix != null) ? suffix.length : 0;
        // 数据都为 null, 则直接跳过
        if (prefixLength + suffixLength == 0) return null;
        // 创建数组
        boolean[] arrays = new boolean[prefixLength + suffixLength];
        // 进行判断处理
        if (prefixLength != 0) {
            System.arraycopy(prefix, 0, arrays, 0, prefixLength);
        }
        if (suffixLength != 0) {
            System.arraycopy(suffix, 0, arrays, prefixLength, suffixLength);
        }
        return arrays;
    }

    // =

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @param <T>    泛型
     * @return 指定长度数组
     */
    public static <T> T[] newArray(
            final T[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static int[] newArray(
            final int[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static byte[] newArray(
            final byte[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static char[] newArray(
            final char[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static short[] newArray(
            final short[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static long[] newArray(
            final long[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static float[] newArray(
            final float[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static double[] newArray(
            final double[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    /**
     * 创建指定长度数组
     * @param data   待处理数组
     * @param length 保留长度
     * @return 指定长度数组
     */
    public static boolean[] newArray(
            final boolean[] data,
            final int length
    ) {
        if (data != null && length >= 0) return Arrays.copyOf(data, length);
        return null;
    }

    // =

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @param <T>    泛型
     * @return 裁剪后的数组
     */
    public static <T> T[] subArray(
            final T[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            List<T> lists = new ArrayList<>(length);
            lists.addAll(Arrays.asList(data).subList(off, off + length));
            return (T[]) Arrays.copyOf(lists.toArray(), length, data.getClass());
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static int[] subArray(
            final int[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            int[] arrays = new int[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static byte[] subArray(
            final byte[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            byte[] arrays = new byte[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static char[] subArray(
            final char[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            char[] arrays = new char[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static short[] subArray(
            final short[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            short[] arrays = new short[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static long[] subArray(
            final long[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            long[] arrays = new long[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static float[] subArray(
            final float[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            float[] arrays = new float[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static double[] subArray(
            final double[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            double[] arrays = new double[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    /**
     * 从数组上截取一段
     * @param data   数组
     * @param off    起始值
     * @param length 所需长度
     * @return 裁剪后的数组
     */
    public static boolean[] subArray(
            final boolean[] data,
            final int off,
            final int length
    ) {
        if (data == null || off < 0 || length < 0) return null;
        try {
            boolean[] arrays = new boolean[length];
            System.arraycopy(data, off, arrays, 0, length);
            return arrays;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subArray");
        }
        return null;
    }

    // =

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @param <T>  泛型
     * @return 追加数组内容字符串
     */
    public static <T> String appendToString(final T[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final int[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final byte[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final char[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final short[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final long[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final float[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final double[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    /**
     * 追加数组内容字符串
     * @param data 数组
     * @return 追加数组内容字符串
     */
    public static String appendToString(final boolean[] data) {
        if (data != null) {
            int len = data.length;
            if (len != 0) {
                if (len == 1) {
                    return "\"" + data[0] + "\"";
                } else {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < len - 1; i++) {
                        builder.append("\"").append(data[i]).append("\",");
                    }
                    builder.append("\"").append(data[len - 1]).append("\"");
                    return builder.toString();
                }
            }
        }
        return "";
    }

    // ============
    // = 最小值索引 =
    // ============

    /**
     * 获取数组中最小值索引
     * @param data 数组
     * @return 最小值索引
     */
    public static int getMinimumIndex(final int[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int index = 0;
                int temp  = data[index];
                for (int i = 1; i < len; i++) {
                    int value = data[i];
                    if (value < temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最小值索引
     * @param data 数组
     * @return 最小值索引
     */
    public static int getMinimumIndex(final long[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int  index = 0;
                long temp  = data[index];
                for (int i = 1; i < len; i++) {
                    long value = data[i];
                    if (value < temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最小值索引
     * @param data 数组
     * @return 最小值索引
     */
    public static int getMinimumIndex(final float[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int   index = 0;
                float temp  = data[index];
                for (int i = 1; i < len; i++) {
                    float value = data[i];
                    if (value < temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最小值索引
     * @param data 数组
     * @return 最小值索引
     */
    public static int getMinimumIndex(final double[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int    index = 0;
                double temp  = data[index];
                for (int i = 1; i < len; i++) {
                    double value = data[i];
                    if (value < temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    // ============
    // = 最大值索引 =
    // ============

    /**
     * 获取数组中最大值索引
     * @param data 数组
     * @return 最大值索引
     */
    public static int getMaximumIndex(final int[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int index = 0;
                int temp  = data[index];
                for (int i = 1; i < len; i++) {
                    int value = data[i];
                    if (value > temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最大值索引
     * @param data 数组
     * @return 最大值索引
     */
    public static int getMaximumIndex(final long[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int  index = 0;
                long temp  = data[index];
                for (int i = 1; i < len; i++) {
                    long value = data[i];
                    if (value > temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最大值索引
     * @param data 数组
     * @return 最大值索引
     */
    public static int getMaximumIndex(final float[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int   index = 0;
                float temp  = data[index];
                for (int i = 1; i < len; i++) {
                    float value = data[i];
                    if (value > temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    /**
     * 获取数组中最大值索引
     * @param data 数组
     * @return 最大值索引
     */
    public static int getMaximumIndex(final double[] data) {
        if (data != null) {
            int len = data.length;
            if (len > 0) {
                int    index = 0;
                double temp  = data[index];
                for (int i = 1; i < len; i++) {
                    double value = data[i];
                    if (value > temp) {
                        index = i;
                        temp  = value;
                    }
                }
                return index;
            }
        }
        return -1;
    }

    // ===========
    // = 获取最小值 =
    // ===========

    /**
     * 获取数组中最小值
     * @param data 数组
     * @return 最小值
     */
    public static int getMinimum(final int[] data) {
        try {
            return data[getMinimumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimum");
        }
        return 0;
    }

    /**
     * 获取数组中最小值
     * @param data 数组
     * @return 最小值
     */
    public static long getMinimum(final long[] data) {
        try {
            return data[getMinimumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimum");
        }
        return 0L;
    }

    /**
     * 获取数组中最小值
     * @param data 数组
     * @return 最小值
     */
    public static float getMinimum(final float[] data) {
        try {
            return data[getMinimumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimum");
        }
        return 0F;
    }

    /**
     * 获取数组中最小值
     * @param data 数组
     * @return 最小值
     */
    public static double getMinimum(final double[] data) {
        try {
            return data[getMinimumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMinimum");
        }
        return 0D;
    }

    // ===========
    // = 获取最大值 =
    // ===========

    /**
     * 获取数组中最大值
     * @param data 数组
     * @return 最大值
     */
    public static int getMaximum(final int[] data) {
        try {
            return data[getMaximumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximum");
        }
        return 0;
    }

    /**
     * 获取数组中最大值
     * @param data 数组
     * @return 最大值
     */
    public static long getMaximum(final long[] data) {
        try {
            return data[getMaximumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximum");
        }
        return 0L;
    }

    /**
     * 获取数组中最大值
     * @param data 数组
     * @return 最大值
     */
    public static float getMaximum(final float[] data) {
        try {
            return data[getMaximumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximum");
        }
        return 0F;
    }

    /**
     * 获取数组中最大值
     * @param data 数组
     * @return 最大值
     */
    public static double getMaximum(final double[] data) {
        try {
            return data[getMaximumIndex(data)];
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getMaximum");
        }
        return 0D;
    }

    // =============
    // = 计算数组总和 =
    // =============

    /**
     * 计算数组总和
     * @param data 数组
     * @return 数组总和
     */
    public static int sumArray(final int[] data) {
        return sumArray(data, 0, length(data), 0);
    }

    /**
     * 计算数组总和
     * @param data 数组
     * @param end  结束位置
     * @return 数组总和
     */
    public static int sumArray(
            final int[] data,
            final int end
    ) {
        return sumArray(data, 0, end, 0);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static int sumArray(
            final int[] data,
            final int end,
            final int extra
    ) {
        return sumArray(data, 0, end, extra);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static int sumArray(
            final int[] data,
            final int start,
            final int end,
            final int extra
    ) {
        int total = 0;
        if (data != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (data[i] + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumArray");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算数组总和
     * @param data 数组
     * @return 数组总和
     */
    public static long sumArray(final long[] data) {
        return sumArray(data, 0, length(data), 0);
    }

    /**
     * 计算数组总和
     * @param data 数组
     * @param end  结束位置
     * @return 数组总和
     */
    public static long sumArray(
            final long[] data,
            final int end
    ) {
        return sumArray(data, 0, end, 0);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static long sumArray(
            final long[] data,
            final int end,
            final long extra
    ) {
        return sumArray(data, 0, end, extra);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static long sumArray(
            final long[] data,
            final int start,
            final int end,
            final long extra
    ) {
        long total = 0;
        if (data != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (data[i] + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumArray");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算数组总和
     * @param data 数组
     * @return 数组总和
     */
    public static float sumArray(final float[] data) {
        return sumArray(data, 0, length(data), 0);
    }

    /**
     * 计算数组总和
     * @param data 数组
     * @param end  结束位置
     * @return 数组总和
     */
    public static float sumArray(
            final float[] data,
            final int end
    ) {
        return sumArray(data, 0, end, 0);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static float sumArray(
            final float[] data,
            final int end,
            final float extra
    ) {
        return sumArray(data, 0, end, extra);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static float sumArray(
            final float[] data,
            final int start,
            final int end,
            final float extra
    ) {
        float total = 0;
        if (data != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (data[i] + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumArray");
                }
            }
        }
        return total;
    }

    // =

    /**
     * 计算数组总和
     * @param data 数组
     * @return 数组总和
     */
    public static double sumArray(final double[] data) {
        return sumArray(data, 0, length(data), 0);
    }

    /**
     * 计算数组总和
     * @param data 数组
     * @param end  结束位置
     * @return 数组总和
     */
    public static double sumArray(
            final double[] data,
            final int end
    ) {
        return sumArray(data, 0, end, 0);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static double sumArray(
            final double[] data,
            final int end,
            final double extra
    ) {
        return sumArray(data, 0, end, extra);
    }

    /**
     * 计算数组总和
     * @param data  数组
     * @param start 开始位置
     * @param end   结束位置
     * @param extra 额外值
     * @return 数组总和
     */
    public static double sumArray(
            final double[] data,
            final int start,
            final int end,
            final double extra
    ) {
        double total = 0;
        if (data != null) {
            for (int i = start; i < end; i++) {
                try {
                    total += (data[i] + extra);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "sumArray");
                }
            }
        }
        return total;
    }
}