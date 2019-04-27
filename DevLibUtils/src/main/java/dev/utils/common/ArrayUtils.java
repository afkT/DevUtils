package dev.utils.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.utils.JCLogUtils;

/**
 * detail: Array 数组工具类
 * @author Ttt
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
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final Object[] objects) {
        return objects == null || objects.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param ints int[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final int[] ints) {
        return ints == null || ints.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param bytes byte[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final byte[] bytes) {
        return bytes == null || bytes.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param chars char[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final char[] chars) {
        return chars == null || chars.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param shorts short[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final short[] shorts) {
        return shorts == null || shorts.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param longs long[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final long[] longs) {
        return longs == null || longs.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param floats float[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final float[] floats) {
        return floats == null || floats.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param doubles double[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final double[] doubles) {
        return doubles == null || doubles.length == 0;
    }

    /**
     * 判断数组是否为 null
     * @param booleans boolean[]
     * @return {@code true} is null, {@code false} not null
     */
    public static boolean isEmpty(final boolean[] booleans) {
        return booleans == null || booleans.length == 0;
    }

    // =

    /**
     * 判断数组是否不为 null
     * @param objects object[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return objects != null && objects.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param ints int[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final int[] ints) {
        return ints != null && ints.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param bytes byte[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final byte[] bytes) {
        return bytes != null && bytes.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param chars char[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final char[] chars) {
        return chars != null && chars.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param shorts short[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final short[] shorts) {
        return shorts != null && shorts.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param longs long[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final long[] longs) {
        return longs != null && longs.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param floats float[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final float[] floats) {
        return floats != null && floats.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param doubles double[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final double[] doubles) {
        return doubles != null && doubles.length != 0;
    }

    /**
     * 判断数组是否不为 null
     * @param booleans boolean[]
     * @return {@code true} not null, {@code false} is null
     */
    public static boolean isNotEmpty(final boolean[] booleans) {
        return booleans != null && booleans.length != 0;
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取数组长度
     * @param objects object[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final Object[] objects) {
        return length(objects, 0);
    }

    /**
     * 获取数组长度
     * @param ints int[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final int[] ints) {
        return length(ints, 0);
    }

    /**
     * 获取数组长度
     * @param bytes byte[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final byte[] bytes) {
        return length(bytes, 0);
    }

    /**
     * 获取数组长度
     * @param chars char[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final char[] chars) {
        return length(chars, 0);
    }

    /**
     * 获取数组长度
     * @param shorts short[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final short[] shorts) {
        return length(shorts, 0);
    }

    /**
     * 获取数组长度
     * @param longs long[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final long[] longs) {
        return length(longs, 0);
    }

    /**
     * 获取数组长度
     * @param floats float[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final float[] floats) {
        return length(floats, 0);
    }

    /**
     * 获取数组长度
     * @param doubles double[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final double[] doubles) {
        return length(doubles, 0);
    }

    /**
     * 获取数组长度
     * @param booleans boolean[]
     * @return 如果数据为null, 则返回默认长度, 如果不为null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans) {
        return length(booleans, 0);
    }

    // =

    /**
     * 获取数组长度
     * @param objects       object[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final Object[] objects, final int defaultLength) {
        return objects != null ? objects.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param ints          int[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final int[] ints, final int defaultLength) {
        return ints != null ? ints.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param bytes         byte[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final byte[] bytes, final int defaultLength) {
        return bytes != null ? bytes.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param chars         char[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final char[] chars, final int defaultLength) {
        return chars != null ? chars.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param shorts        short[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final short[] shorts, final int defaultLength) {
        return shorts != null ? shorts.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param longs         long[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final long[] longs, final int defaultLength) {
        return longs != null ? longs.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param floats        float[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final float[] floats, final int defaultLength) {
        return floats != null ? floats.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param doubles       double[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final double[] doubles, final int defaultLength) {
        return doubles != null ? doubles.length : defaultLength;
    }

    /**
     * 获取数组长度
     * @param booleans      boolean[]
     * @param defaultLength 数组为null时, 返回的长度
     * @return 如果数据为null, 则返回 defaultLength, 如果不为null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans, final int defaultLength) {
        return booleans != null ? booleans.length : defaultLength;
    }

    // =

    /**
     * 判断数组长度是否等于期望长度
     * @param objects object[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Object[] objects, final int length) {
        return objects != null && objects.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param ints   int[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final int[] ints, final int length) {
        return ints != null && ints.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param bytes  byte[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final byte[] bytes, final int length) {
        return bytes != null && bytes.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param chars  char[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final char[] chars, final int length) {
        return chars != null && chars.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param shorts short[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final short[] shorts, final int length) {
        return shorts != null && shorts.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param longs  long[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final long[] longs, final int length) {
        return longs != null && longs.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param floats float[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final float[] floats, final int length) {
        return floats != null && floats.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param doubles double[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final double[] doubles, final int length) {
        return doubles != null && doubles.length == length;
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param booleans boolean[]
     * @param length   期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final boolean[] booleans, final int length) {
        return booleans != null && booleans.length == length;
    }

    // ============
    // = 数据获取 =
    // ============

    /**
     * 获取数组对应索引数据
     * @param array 数组
     * @param pos   索引
     * @param <T>   泛型
     * @return 数组指定索引的值
     */
    public static <T> T get(final T[] array, final int pos) {
        return get(array, pos, null);
    }

    /**
     * 获取数组对应索引数据
     * @param ints int[]
     * @param pos  索引
     * @return 数组指定索引的值
     */
    public static int get(final int[] ints, final int pos) {
        return get(ints, pos, -1);
    }

    /**
     * 获取数组对应索引数据
     * @param bytes byte[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static byte get(final byte[] bytes, final int pos) {
        return get(bytes, pos, (byte) -1);
    }

    /**
     * 获取数组对应索引数据
     * @param chars char[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static char get(final char[] chars, final int pos) {
        return get(chars, pos, (char) -1);
    }

    /**
     * 获取数组对应索引数据
     * @param shorts short[]
     * @param pos    索引
     * @return 数组指定索引的值
     */
    public static short get(final short[] shorts, final int pos) {
        return get(shorts, pos, (short) -1);
    }

    /**
     * 获取数组对应索引数据
     * @param longs long[]
     * @param pos   索引
     * @return 数组指定索引的值
     */
    public static long get(final long[] longs, final int pos) {
        return get(longs, pos, -1l);
    }

    /**
     * 获取数组对应索引数据
     * @param floats float[]
     * @param pos    索引
     * @return 数组指定索引的值
     */
    public static float get(final float[] floats, final int pos) {
        return get(floats, pos, -1f);
    }

    /**
     * 获取数组对应索引数据
     * @param doubles double[]
     * @param pos     索引
     * @return 数组指定索引的值
     */
    public static double get(final double[] doubles, final int pos) {
        return get(doubles, pos, -1d);
    }

    /**
     * 获取数组对应索引数据
     * @param booleans boolean[]
     * @param pos      索引
     * @return 数组指定索引的值
     */
    public static boolean get(final boolean[] booleans, final int pos) {
        return get(booleans, pos, false);
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
    public static <T> T get(final T[] array, final int pos, final T defaultValue) {
        if (array != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return array[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static int get(final int[] ints, final int pos, final int defaultValue) {
        if (ints != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return ints[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static byte get(final byte[] bytes, final int pos, final byte defaultValue) {
        if (bytes != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return bytes[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static char get(final char[] chars, final int pos, final char defaultValue) {
        if (chars != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return chars[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static short get(final short[] shorts, final int pos, final short defaultValue) {
        if (shorts != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return shorts[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static long get(final long[] longs, final int pos, final long defaultValue) {
        if (longs != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return longs[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static float get(final float[] floats, final int pos, final float defaultValue) {
        if (floats != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return floats[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static double get(final double[] doubles, final int pos, final double defaultValue) {
        if (doubles != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return doubles[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
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
    public static boolean get(final boolean[] booleans, final int pos, final boolean defaultValue) {
        if (booleans != null) {
            // 防止索引为负数
            if (pos < 0) {
                return defaultValue;
            }
            try {
                return booleans[pos];
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "get - " + pos);
            }
        }
        return defaultValue;
    }

    // =

    /**
     * 获取数组第一条数据
     * @param array 数组
     * @param <T>   泛型
     * @return 数组索引为0的值
     */
    public static <T> T getFirst(final T[] array) {
        return get(array, 0);
    }

    /**
     * 获取数组第一条数据
     * @param ints int[]
     * @return 数组索引为0的值
     */
    public static int getFirst(final int[] ints) {
        return get(ints, 0);
    }

    /**
     * 获取数组第一条数据
     * @param bytes byte[]
     * @return 数组索引为0的值
     */
    public static byte getFirst(final byte[] bytes) {
        return get(bytes, 0);
    }

    /**
     * 获取数组第一条数据
     * @param chars char[]
     * @return 数组索引为0的值
     */
    public static char getFirst(final char[] chars) {
        return get(chars, 0);
    }

    /**
     * 获取数组第一条数据
     * @param shorts short[]
     * @return 数组索引为0的值
     */
    public static short getFirst(final short[] shorts) {
        return get(shorts, 0);
    }

    /**
     * 获取数组第一条数据
     * @param longs long[]
     * @return 数组索引为0的值
     */
    public static long getFirst(final long[] longs) {
        return get(longs, 0);
    }

    /**
     * 获取数组第一条数据
     * @param floats float[]
     * @return 数组索引为0的值
     */
    public static float getFirst(final float[] floats) {
        return get(floats, 0);
    }

    /**
     * 获取数组第一条数据
     * @param doubles double[]
     * @return 数组索引为0的值
     */
    public static double getFirst(final double[] doubles) {
        return get(doubles, 0);
    }

    /**
     * 获取数组第一条数据
     * @param booleans boolean[]
     * @return 数组索引为0的值
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

    // ===================
    // = 数据获取 - 特殊 =
    // ===================

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数(从0开始, 0 = 1)
     * @param notNull 是否不允许值为null
     * @param offset  偏移量
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(final T[] array, final T value, final int number, final boolean notNull, final int offset) {
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
     * @param number  符合条件次数(从0开始, 0 = 1)
     * @param notNull 是否不允许值为null
     * @param offset  偏移量
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> int getPosition(final T[] array, final T value, final int number, final boolean notNull, final int offset) {
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
    public static <T> T get(final T[] array, final T value) {
        return get(array, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param <T>    泛型
     * @return 对应索引的值
     */
    public static <T> T get(final T[] array, final T value, final int number) {
        return get(array, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array   数组
     * @param value   值
     * @param notNull 是否不允许值为null
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(final T[] array, final T value, final boolean notNull) {
        return get(array, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数(从0开始, 0 = 1)
     * @param notNull 是否不允许值为null
     * @param <T>     泛型
     * @return 对应索引的值
     */
    public static <T> T get(final T[] array, final T value, final int number, final boolean notNull) {
        return get(array, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为null
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应索引的值
     */
    public static <T> T getNotNull(final T[] array, final T value) {
        return get(array, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取 value 所在位置 + 偏移量的值, 不允许值为null
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param <T>    泛型
     * @return 对应索引的值
     */
    public static <T> T getNotNull(final T[] array, final T value, final int number) {
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
    public static <T> int getPosition(final T[] array, final T value) {
        return getPosition(array, value, 0, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param <T>    泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final T[] array, final T value, final int number) {
        return getPosition(array, value, number, false, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array   数组
     * @param value   值
     * @param notNull 是否不允许值为null
     * @param <T>     泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final T[] array, final T value, final boolean notNull) {
        return getPosition(array, value, 0, notNull, 0);
    }

    /**
     * 根据指定 value 获取索引
     * @param array   数组
     * @param value   值
     * @param number  符合条件次数(从0开始, 0 = 1)
     * @param notNull 是否不允许值为null
     * @param <T>     泛型
     * @return 对应的索引
     */
    public static <T> int getPosition(final T[] array, final T value, final int number, final boolean notNull) {
        return getPosition(array, value, number, notNull, 0);
    }

    // =

    /**
     * 根据指定 value 获取索引, 不允许值为null
     * @param array 数组
     * @param value 值
     * @param <T>   泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(final T[] array, final T value) {
        return getPosition(array, value, 0, true, 0);
    }

    /**
     * 根据指定 value 获取索引, 不允许值为null
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param <T>    泛型
     * @return 对应的索引
     */
    public static <T> int getPositionNotNull(final T[] array, final T value, final int number) {
        return getPosition(array, value, number, true, 0);
    }

    // =

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static int get(final int[] array, final int value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final int[] array, final int value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static byte get(final byte[] array, final byte value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final byte[] array, final byte value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static char get(final char[] array, final char value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final char[] array, final char value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static short get(final short[] array, final short value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final short[] array, final short value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static long get(final long[] array, final long value, final int number, final int offset) {
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
        return (long) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final long[] array, final long value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static float get(final float[] array, final float value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final float[] array, final float value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static double get(final double[] array, final double value, final int number, final int offset) {
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
        return (double) -1;
    }

    /**
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final double[] array, final double value, final int number, final int offset) {
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
     * 根据指定值获取 value 所在位置 + 偏移量的索引
     * @param array  数组
     * @param value  值
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应索引的值
     */
    public static boolean get(final boolean[] array, final boolean value, final int number, final int offset) {
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
     * @param number 符合条件次数(从0开始, 0 = 1)
     * @param offset 偏移量
     * @return 对应的索引
     */
    public static int getPosition(final boolean[] array, final boolean value, final int number, final int offset) {
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

    // ============
    // = 转换处理 =
    // ============

    /**
     * int 数组转换 Integer 数组
     * @param ints int[]
     * @return {@link Integer[]}
     */
    public static Integer[] intsToIntegers(final int[] ints) {
        if (ints != null) {
            // 获取数组长度
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
     * byte 数组转换 Byte 数组
     * @param bytes byte[]
     * @return {@link Byte[]}
     */
    public static Byte[] bytesToBytes(final byte[] bytes) {
        if (bytes != null) {
            // 获取数组长度
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
     * char 数组转换 Character 数组
     * @param chars char[]
     * @return {@link Character[]}
     */
    public static Character[] charsToCharacters(final char[] chars) {
        if (chars != null) {
            // 获取数组长度
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
     * short 数组转换 Short 数组
     * @param shorts short[]
     * @return {@link Short[]}
     */
    public static Short[] shortsToShorts(final short[] shorts) {
        if (shorts != null) {
            // 获取数组长度
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
     * long 数组转换 Long 数组
     * @param longs long[]
     * @return {@link Long[]}
     */
    public static Long[] longsToLongs(final long[] longs) {
        if (longs != null) {
            // 获取数组长度
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
     * float 数组转换 Float 数组
     * @param floats float[]
     * @return {@link Float[]}
     */
    public static Float[] floatsToFloats(final float[] floats) {
        if (floats != null) {
            // 获取数组长度
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
     * double 数组转换 Double 数组
     * @param doubles double[]
     * @return {@link Double[]}
     */
    public static Double[] doublesToDoubles(final double[] doubles) {
        if (doubles != null) {
            // 获取数组长度
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
     * boolean 数组转换 Boolean 数组
     * @param booleans boolean[]
     * @return {@link Boolean[]}
     */
    public static Boolean[] booleansToBooleans(final boolean[] booleans) {
        if (booleans != null) {
            // 获取数组长度
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
     * Integer 数组转换 int 数组
     * @param integers     Integer[]
     * @param defaultValue 转换失败使用得默认值
     * @return int[]
     */
    public static int[] integersToInts(final Integer[] integers, final int defaultValue) {
        if (integers != null) {
            // 获取数组长度
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
     * Byte 数组转换 byte 数组
     * @param bytes        Byte[]
     * @param defaultValue 转换失败使用得默认值
     * @return byte[]
     */
    public static byte[] bytesToBytes(final Byte[] bytes, final byte defaultValue) {
        if (bytes != null) {
            // 获取数组长度
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
     * Character 数组转换 char 数组
     * @param characters   Character[]
     * @param defaultValue 转换失败使用得默认值
     * @return char[]
     */
    public static char[] charactersToChars(final Character[] characters, final char defaultValue) {
        if (characters != null) {
            // 获取数组长度
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
     * Short 数组转换 short 数组
     * @param shorts       Short[]
     * @param defaultValue 转换失败使用得默认值
     * @return short[]
     */
    public static short[] shortsToShorts(final Short[] shorts, final short defaultValue) {
        if (shorts != null) {
            // 获取数组长度
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
     * Long 数组转换 long 数组
     * @param longs        Long[]
     * @param defaultValue 转换失败使用得默认值
     * @return long[]
     */
    public static long[] longsToLongs(final Long[] longs, final long defaultValue) {
        if (longs != null) {
            // 获取数组长度
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
     * Float 数组转换 float 数组
     * @param floats       Float[]
     * @param defaultValue 转换失败使用得默认值
     * @return float[]
     */
    public static float[] floatsToFloats(final Float[] floats, final float defaultValue) {
        if (floats != null) {
            // 获取数组长度
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
     * Double 数组转换 double 数组
     * @param doubles      Double[]
     * @param defaultValue 转换失败使用得默认值
     * @return double[]
     */
    public static double[] doublesToDoubles(final Double[] doubles, final double defaultValue) {
        if (doubles != null) {
            // 获取数组长度
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
     * Boolean 数组转换 boolean 数组
     * @param booleans Boolean[]
     * @return boolean[]
     */
    public static boolean[] booleansToBooleans(final Boolean[] booleans, final boolean defaultValue) {
        if (booleans != null) {
            // 获取数组长度
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
    // = 转换List =
    // ============

    /**
     * 转换数组为集合
     * @param array 数组
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

    // =

    /**
     * 转换数组为集合
     * @param ints int[]
     * @return {@link List<Integer>}
     */
    public static List<Integer> asList(final int[] ints) {
        if (ints != null) {
            List<Integer> lists = new ArrayList<>();
            for (int i = 0, len = ints.length; i < len; i++) {
                lists.add(ints[i]);
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
            for (int i = 0, len = bytes.length; i < len; i++) {
                lists.add(bytes[i]);
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
            for (int i = 0, len = chars.length; i < len; i++) {
                lists.add(chars[i]);
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
            for (int i = 0, len = shorts.length; i < len; i++) {
                lists.add(shorts[i]);
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
            for (int i = 0, len = longs.length; i < len; i++) {
                lists.add(longs[i]);
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
            for (int i = 0, len = floats.length; i < len; i++) {
                lists.add(floats[i]);
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
            for (int i = 0, len = doubles.length; i < len; i++) {
                lists.add(doubles[i]);
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
            for (int i = 0, len = booleans.length; i < len; i++) {
                lists.add(booleans[i]);
            }
            return lists;
        }
        return null;
    }

    // ============
    // = 快捷判断 =
    // ============

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(final T value1, final T value2) {
        // 两个值都不为 null
        if (value1 != null && value2 != null) {
            try {
                if (value1 instanceof String && value2 instanceof String) {
                    return value1.equals(value2);
                } else if (value1 instanceof CharSequence && value2 instanceof CharSequence) {
                    CharSequence v1 = (CharSequence) value1;
                    CharSequence v2 = (CharSequence) value2;
                    // 获取数据长度
                    int length = v1.length();
                    // 判断数据长度是否一致
                    if (length == v2.length()) {
                        for (int i = 0; i < length; i++) {
                            if (v1.charAt(i) != v2.charAt(i)) {
                                return false;
                            }
                        }
                        return true;
                    }
                    return false;
                }
                // 其他都使用 equals 判断
                return value1.equals(value2);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "equals");
            }
            return false;
        }
        // 防止两个值都为null
        return (value1 == null && value2 == null);
    }
}
