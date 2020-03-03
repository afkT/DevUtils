package dev.utils.common;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import dev.utils.JCLogUtils;
import dev.utils.common.encrypt.MD5Utils;

/**
 * detail: 开发常用方法工具类
 * @author Ttt
 */
public final class DevCommonUtils {

    private DevCommonUtils() {
    }

    // 日志 TAG
    private static final String TAG = DevCommonUtils.class.getSimpleName();
    // 换行字符串
    private static final String NEW_LINE_STR = System.getProperty("line.separator");

    // ================
    // = 计时相关处理 =
    // ================

    /**
     * 耗时时间记录
     * @param builder   拼接 Builder
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@link StringBuilder}
     */
    public static StringBuilder timeRecord(final StringBuilder builder, final long startTime, final long endTime) {
        return timeRecord(builder, null, startTime, endTime);
    }

    /**
     * 耗时时间记录
     * @param builder   拼接 Builder
     * @param title     标题
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@link StringBuilder}
     */
    public static StringBuilder timeRecord(final StringBuilder builder, final String title, final long startTime, final long endTime) {
        if (builder == null) return builder;
        // 使用时间
        long diffTime = endTime - startTime;
        // 计算时间
        if (!isEmpty(title)) {
            builder.append(NEW_LINE_STR);
            builder.append(title);
        }
        // 计算时间
        builder.append(NEW_LINE_STR + "开始时间: " + DateUtils.formatTime(startTime, DateUtils.yyyyMMddHHmmss));
        builder.append(NEW_LINE_STR + "结束时间: " + DateUtils.formatTime(endTime, DateUtils.yyyyMMddHHmmss));
        builder.append(NEW_LINE_STR + "所用时间(毫秒): " + diffTime);
        builder.append(NEW_LINE_STR + "所用时间(秒): " + (diffTime / 1000));
        return builder;
    }

    // =

    /**
     * 获取操作时间
     * @param operateTime 操作时间 ( 毫秒 )
     * @return 操作时间
     */
    public static long getOperateTime(final long operateTime) {
        return getOperateTime(operateTime, -1);
    }

    /**
     * 获取操作时间
     * @param operateTime 操作时间 ( 毫秒 )
     * @param randomTime  随机时间范围 ( 毫秒 )
     * @return 操作时间
     */
    public static long getOperateTime(final long operateTime, final int randomTime) {
        int random = 0;
        // 大于 2 才处理
        if (randomTime >= 2) {
            // 随机时间
            random = RandomUtils.getRandom(randomTime);
        }
        // 返回操作时间
        return Math.max(0, operateTime) + random;
    }

    /**
     * 堵塞操作
     * @param sleepTime 堵塞时间 ( 毫秒 )
     */
    public static void sleepOperate(final long sleepTime) {
        sleepOperate(sleepTime, -1);
    }

    /**
     * 堵塞操作
     * @param sleepTime  堵塞时间 ( 毫秒 )
     * @param randomTime 随机时间范围 ( 毫秒 )
     */
    public static void sleepOperate(final long sleepTime, final int randomTime) {
        long time = getOperateTime(sleepTime, randomTime);
        if (time > 0) {
            try {
                Thread.sleep(time);
            } catch (Throwable throwable) {
                JCLogUtils.eTag(TAG, throwable, "sleepOperate");
            }
        }
    }

    // =

    /**
     * 判断是否网络资源
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isHttpRes(final String str) {
        if (!isEmpty(str)) {
            // 属于第一位开始, 才是属于网络资源
            return str.toLowerCase().startsWith("http:") ||
                    str.toLowerCase().startsWith("https:");
        }
        return false;
    }

    /**
     * 循环 MD5 加密处理
     * @param str         待处理数据
     * @param number      MD5 加密次数
     * @param isUppercase 是否大写处理
     * @param salts       特殊 salt 拼接
     * @return 循环加密后的字符串
     */
    public static String whileMD5(final String str, final int number, final boolean isUppercase, final String... salts) {
        if (str != null && number >= 1) {
            int saltLen = (salts != null) ? salts.length : 0;
            // 临时字符串
            String tempString = str;
            // 判断是否大写
            if (isUppercase) {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen > i) {
                        String salt = salts[i];
                        if (salt != null) {
                            tempString = MD5Utils.md5Upper(tempString + salt);
                        } else {
                            tempString = MD5Utils.md5Upper(tempString);
                        }
                    } else {
                        tempString = MD5Utils.md5Upper(tempString);
                    }
                }
            } else {
                // 循环加密
                for (int i = 0; i < number; i++) {
                    if (saltLen > i) {
                        String salt = salts[i];
                        if (salt != null) {
                            tempString = MD5Utils.md5(tempString + salt);
                        } else {
                            tempString = MD5Utils.md5(tempString);
                        }
                    } else {
                        tempString = MD5Utils.md5(tempString);
                    }
                }
            }
            return tempString;
        }
        return str;
    }

    // ==============
    // = 获取唯一数 =
    // ==============

    /**
     * 获取随机唯一数
     * @return {@link UUID}
     */
    public static UUID randomUUID() {
        return UUID.randomUUID();
    }

    /**
     * 获取随机唯一数 HashCode
     * @return 随机 UUID hashCode
     */
    public static int randomUUIDToHashCode() {
        return UUID.randomUUID().hashCode();
    }

    /**
     * 获取随机唯一数 HashCode
     * @param uuid {@link UUID}
     * @return 随机 UUID hashCode
     */
    public static int randomUUIDToHashCode(final UUID uuid) {
        return (uuid != null) ? uuid.hashCode() : 0;
    }

    /**
     * 获取随机规则生成 UUID
     * @return 随机规则生成 UUID
     */
    public static UUID getRandomUUID() {
        // 获取随机数
        String random1 = (900000 + new Random().nextInt(10000)) + "";
        // 获取随机数
        String random2 = (900000 + new Random().nextInt(10000)) + "";
        // 获取当前时间
        String time = System.currentTimeMillis() + random1 + random2;
        // 生成唯一随机 UUID
        return new UUID(time.hashCode(), ((long) random1.hashCode() << 32) | random2.hashCode());
    }

    /**
     * 获取随机规则生成 UUID 字符串
     * @return 随机规则生成 UUID 字符串
     */
    public static String getRandomUUIDToString() {
        return getRandomUUID().toString();
    }

    // ===============
    // = ObjectUtils =
    // ===============

    /**
     * 判断对象是否为空
     * @param object 对象
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Object object) {
        return ObjectUtils.isEmpty(object);
    }

    /**
     * 判断对象是否非空
     * @param object 对象
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Object object) {
        return ObjectUtils.isNotEmpty(object);
    }

    // ==============
    // = ArrayUtils =
    // ==============

    /**
     * 判断数组是否为 null
     * @param objects object[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Object[] objects) {
        return ArrayUtils.isEmpty(objects);
    }

    /**
     * 判断数组是否为 null
     * @param ints int[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final int[] ints) {
        return ArrayUtils.isEmpty(ints);
    }

    /**
     * 判断数组是否为 null
     * @param bytes byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final byte[] bytes) {
        return ArrayUtils.isEmpty(bytes);
    }

    /**
     * 判断数组是否为 null
     * @param chars char[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final char[] chars) {
        return ArrayUtils.isEmpty(chars);
    }

    /**
     * 判断数组是否为 null
     * @param shorts short[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final short[] shorts) {
        return ArrayUtils.isEmpty(shorts);
    }

    /**
     * 判断数组是否为 null
     * @param longs long[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final long[] longs) {
        return ArrayUtils.isEmpty(longs);
    }

    /**
     * 判断数组是否为 null
     * @param floats float[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final float[] floats) {
        return ArrayUtils.isEmpty(floats);
    }

    /**
     * 判断数组是否为 null
     * @param doubles double[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final double[] doubles) {
        return ArrayUtils.isEmpty(doubles);
    }

    /**
     * 判断数组是否为 null
     * @param booleans boolean[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final boolean[] booleans) {
        return ArrayUtils.isEmpty(booleans);
    }

    // =

    /**
     * 判断数组是否不为 null
     * @param objects object[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Object[] objects) {
        return ArrayUtils.isNotEmpty(objects);
    }

    /**
     * 判断数组是否不为 null
     * @param ints int[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final int[] ints) {
        return ArrayUtils.isNotEmpty(ints);
    }

    /**
     * 判断数组是否不为 null
     * @param bytes byte[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final byte[] bytes) {
        return ArrayUtils.isNotEmpty(bytes);
    }

    /**
     * 判断数组是否不为 null
     * @param chars char[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final char[] chars) {
        return ArrayUtils.isNotEmpty(chars);
    }

    /**
     * 判断数组是否不为 null
     * @param shorts short[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final short[] shorts) {
        return ArrayUtils.isNotEmpty(shorts);
    }

    /**
     * 判断数组是否不为 null
     * @param longs long[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final long[] longs) {
        return ArrayUtils.isNotEmpty(longs);
    }

    /**
     * 判断数组是否不为 null
     * @param floats float[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final float[] floats) {
        return ArrayUtils.isNotEmpty(floats);
    }

    /**
     * 判断数组是否不为 null
     * @param doubles double[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final double[] doubles) {
        return ArrayUtils.isNotEmpty(doubles);
    }

    /**
     * 判断数组是否不为 null
     * @param booleans boolean[]
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final boolean[] booleans) {
        return ArrayUtils.isNotEmpty(booleans);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取数组长度
     * @param objects object[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final Object[] objects) {
        return ArrayUtils.length(objects);
    }

    /**
     * 获取数组长度
     * @param ints int[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final int[] ints) {
        return ArrayUtils.length(ints);
    }

    /**
     * 获取数组长度
     * @param bytes byte[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final byte[] bytes) {
        return ArrayUtils.length(bytes);
    }

    /**
     * 获取数组长度
     * @param chars char[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final char[] chars) {
        return ArrayUtils.length(chars);
    }

    /**
     * 获取数组长度
     * @param shorts short[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final short[] shorts) {
        return ArrayUtils.length(shorts);
    }

    /**
     * 获取数组长度
     * @param longs long[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final long[] longs) {
        return ArrayUtils.length(longs);
    }

    /**
     * 获取数组长度
     * @param floats float[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final float[] floats) {
        return ArrayUtils.length(floats);
    }

    /**
     * 获取数组长度
     * @param doubles double[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final double[] doubles) {
        return ArrayUtils.length(doubles);
    }

    /**
     * 获取数组长度
     * @param booleans boolean[]
     * @return 如果数据为 null, 则返回默认长度, 如果不为 null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans) {
        return ArrayUtils.length(booleans);
    }

    // =

    /**
     * 获取数组长度
     * @param objects       object[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final Object[] objects, final int defaultLength) {
        return ArrayUtils.length(objects, defaultLength);
    }

    /**
     * 获取数组长度
     * @param ints          int[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final int[] ints, final int defaultLength) {
        return ArrayUtils.length(ints, defaultLength);
    }

    /**
     * 获取数组长度
     * @param bytes         byte[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final byte[] bytes, final int defaultLength) {
        return ArrayUtils.length(bytes, defaultLength);
    }

    /**
     * 获取数组长度
     * @param chars         char[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final char[] chars, final int defaultLength) {
        return ArrayUtils.length(chars, defaultLength);
    }

    /**
     * 获取数组长度
     * @param shorts        short[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final short[] shorts, final int defaultLength) {
        return ArrayUtils.length(shorts, defaultLength);
    }

    /**
     * 获取数组长度
     * @param longs         long[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final long[] longs, final int defaultLength) {
        return ArrayUtils.length(longs, defaultLength);
    }

    /**
     * 获取数组长度
     * @param floats        float[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final float[] floats, final int defaultLength) {
        return ArrayUtils.length(floats, defaultLength);
    }

    /**
     * 获取数组长度
     * @param doubles       double[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final double[] doubles, final int defaultLength) {
        return ArrayUtils.length(doubles, defaultLength);
    }

    /**
     * 获取数组长度
     * @param booleans      boolean[]
     * @param defaultLength 数组为 null 时, 返回的长度
     * @return 如果数据为 null, 则返回 defaultLength, 如果不为 null, 则返回 array[].length
     */
    public static int length(final boolean[] booleans, final int defaultLength) {
        return ArrayUtils.length(booleans, defaultLength);
    }

    // =

    /**
     * 判断数组长度是否等于期望长度
     * @param objects object[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Object[] objects, final int length) {
        return ArrayUtils.isLength(objects, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param ints   int[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final int[] ints, final int length) {
        return ArrayUtils.isLength(ints, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param bytes  byte[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final byte[] bytes, final int length) {
        return ArrayUtils.isLength(bytes, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param chars  char[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final char[] chars, final int length) {
        return ArrayUtils.isLength(chars, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param shorts short[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final short[] shorts, final int length) {
        return ArrayUtils.isLength(shorts, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param longs  long[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final long[] longs, final int length) {
        return ArrayUtils.isLength(longs, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param floats float[]
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final float[] floats, final int length) {
        return ArrayUtils.isLength(floats, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param doubles double[]
     * @param length  期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final double[] doubles, final int length) {
        return ArrayUtils.isLength(doubles, length);
    }

    /**
     * 判断数组长度是否等于期望长度
     * @param booleans boolean[]
     * @param length   期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final boolean[] booleans, final int length) {
        return ArrayUtils.isLength(booleans, length);
    }

    // ===================
    // = CollectionUtils =
    // ===================

    /**
     * 判断 Collection 是否为 null
     * @param collection {@link Collection}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 判断 Collection 是否不为 null
     * @param collection {@link Collection}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Collection collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Collection 长度
     * @param collection {@link Collection}
     * @return 如果 Collection 为 null, 则返回默认长度, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection) {
        return CollectionUtils.length(collection);
    }

    /**
     * 获取 Collection 长度
     * @param collection    {@link Collection}
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Collection 为 null, 则返回 defaultLength, 如果不为 null, 则返回 collection.size()
     */
    public static int length(final Collection collection, final int defaultLength) {
        return CollectionUtils.length(collection, defaultLength);
    }

    // =

    /**
     * 获取长度 Collection 是否等于期望长度
     * @param collection {@link Collection}
     * @param length     期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Collection collection, final int length) {
        return CollectionUtils.isLength(collection, length);
    }

    // ============
    // = MapUtils =
    // ============

    /**
     * 判断 Map 是否为 null
     * @param map {@link Map}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final Map map) {
        return MapUtils.isEmpty(map);
    }

    /**
     * 判断 Map 是否不为 null
     * @param map {@link Map}
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final Map map) {
        return MapUtils.isNotEmpty(map);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取 Map 长度
     * @param map {@link Map}
     * @return 如果 Map 为 null, 则返回默认长度, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map) {
        return MapUtils.length(map);
    }

    /**
     * 获取 Map 长度
     * @param map           {@link Map}
     * @param defaultLength 集合为 null 默认长度
     * @return 如果 Map 为 null, 则返回 defaultLength, 如果不为 null, 则返回 map.size()
     */
    public static int length(final Map map, final int defaultLength) {
        return MapUtils.length(map, defaultLength);
    }

    // =

    /**
     * 获取长度 Map 是否等于期望长度
     * @param map    {@link Map}
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final Map map, final int length) {
        return MapUtils.isLength(map, length);
    }

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为 null
     * @param str    待校验的字符串
     * @param isTrim 是否调用 trim()
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final String str, final boolean isTrim) {
        return StringUtils.isEmpty(str, isTrim);
    }

    /**
     * 判断多个字符串是否存在为 null 的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEmpty(final String... strs) {
        return StringUtils.isEmpty(strs);
    }

    // =

    /**
     * 判断字符串是否不为 null
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final String str) {
        return StringUtils.isNotEmpty(str);
    }

    /**
     * 判断字符串是否不为 null
     * @param str    待校验的字符串
     * @param isTrim 是否调用 trim()
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNotEmpty(final String str, final boolean isTrim) {
        return StringUtils.isNotEmpty(str, isTrim);
    }

    // ============
    // = 判断长度 =
    // ============

    /**
     * 获取字符串长度
     * @param str 待校验的字符串
     * @return 字符串长度, 如果字符串为 null, 则返回 0
     */
    public static int length(final String str) {
        return StringUtils.length(str);
    }

    /**
     * 获取字符串长度
     * @param str           待校验的字符串
     * @param defaultLength 字符串为 null 时, 返回的长度
     * @return 字符串长度, 如果字符串为 null, 则返回 defaultLength
     */
    public static int length(final String str, final int defaultLength) {
        return StringUtils.length(str, defaultLength);
    }

    // =

    /**
     * 获取字符串长度 是否等于期望长度
     * @param str    待校验的字符串
     * @param length 期望长度
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isLength(final String str, final int length) {
        return StringUtils.isLength(str, length);
    }

    // ============
    // = 对比判断 =
    // ============

    /**
     * 判断两个值是否一样
     * @param value1 第一个值
     * @param value2 第二个值
     * @param <T>    泛型
     * @return {@code true} yes, {@code false} no
     */
    public static <T> boolean equals(final T value1, final T value2) {
        return StringUtils.equals(value1, value2);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true - 对比大小写
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(final String... strs) {
        return StringUtils.isEquals(strs);
    }

    /**
     * 判断多个字符串是否相等, 只有全相等才返回 true
     * @param isIgnore 是否忽略大小写
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEquals(final boolean isIgnore, final String... strs) {
        return StringUtils.isEquals(isIgnore, strs);
    }

    /**
     * 判断多个字符串, 只要有一个符合条件则通过
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(final String str, final String... strs) {
        return StringUtils.isOrEquals(str, strs);
    }

    /**
     * 判断多个字符串, 只要有一个符合条件则通过
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isOrEquals(final boolean isIgnore, final String str, final String... strs) {
        return StringUtils.isOrEquals(isIgnore, str, strs);
    }

    /**
     * 判断一堆值中, 是否存在符合该条件的 ( 包含 )
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(final String str, final String... strs) {
        return StringUtils.isContains(str, strs);
    }

    /**
     * 判断一堆值中, 是否存在符合该条件的 ( 包含 )
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isContains(final boolean isIgnore, final String str, final String... strs) {
        return StringUtils.isContains(isIgnore, str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串开头 - 对比大小写
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(final String str, final String... strs) {
        return StringUtils.isStartsWith(str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串开头
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isStartsWith(final boolean isIgnore, final String str, final String... strs) {
        return StringUtils.isStartsWith(isIgnore, str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾 - 对比大小写
     * @param str  待校验的字符串
     * @param strs 待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(final String str, final String... strs) {
        return StringUtils.isEndsWith(str, strs);
    }

    /**
     * 判断内容, 是否属于特定字符串结尾
     * @param isIgnore 是否忽略大小写
     * @param str      待校验的字符串
     * @param strs     待校验的字符串数组
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isEndsWith(final boolean isIgnore, final String str, final String... strs) {
        return StringUtils.isEndsWith(isIgnore, str, strs);
    }

    // ============
    // = 其他处理 =
    // ============

    /**
     * 判断字符串是否为 null 或全为空白字符
     * @param str 待校验字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isSpace(final String str) {
        return StringUtils.isSpace(str);
    }

    /**
     * 清空字符串全部空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String toClearSpace(final String str) {
        return StringUtils.toClearSpace(str);
    }

    /**
     * 清空字符串前后所有空格
     * @param str 待处理字符串
     * @return 处理后的字符串
     */
    public static String toClearSpaceTrim(final String str) {
        return StringUtils.toClearSpaceTrim(str);
    }

    // =

    /**
     * 追加空格
     * @param number 空格数量
     * @return 指定数量的空格字符串
     */
    public static String appendSpace(final int number) {
        return StringUtils.appendSpace(number);
    }

    /**
     * 追加 Tab
     * @param number tab 键数量
     * @return 指定数量的 Tab 字符串
     */
    public static String appendTab(final int number) {
        return StringUtils.appendTab(number);
    }

    /**
     * 追加换行
     * @param number 换行数量
     * @return 指定数量的换行字符串
     */
    public static String appendLine(final int number) {
        return StringUtils.appendLine(number);
    }

    // =

    /**
     * 检查字符串
     * @param str 待校验字符串
     * @return 如果待校验字符串为 null, 则返回默认字符串, 如果不为 null, 则返回该字符串
     */
    public static String toCheckValue(final String str) {
        return StringUtils.toCheckValue(str);
    }

    /**
     * 检查字符串
     * @param defaultStr 默认字符串
     * @param str        待校验字符串
     * @return 如果待校验字符串为 null, 则返回 defaultStr, 如果不为 null, 则返回该字符串
     */
    public static String toCheckValue(final String defaultStr, final String str) {
        return StringUtils.toCheckValue(defaultStr, str);
    }

    /**
     * 检查字符串 - 单独检查两个值
     * @param defaultStr 默认字符串
     * @param value1     第一个待校验字符串
     * @param value2     第二个待校验字符串
     * @return 两个待校验字符串中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValue(final String defaultStr, final String value1, final String value2) {
        return StringUtils.toCheckValue(defaultStr, value1, value2);
    }

    /**
     * 检查字符串 - 多个值
     * @param defaultStr 默认字符串
     * @param strs       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValues(final String defaultStr, final String... strs) {
        return StringUtils.toCheckValues(defaultStr, strs);
    }

    /**
     * 检查字符串 - 多个值 ( 删除前后空格对比判断 )
     * @param defaultStr 默认字符串
     * @param strs       待校验字符串数组
     * @return 字符串数组中不为 null 的字符串, 如果都为 null, 则返回 defaultStr
     */
    public static String toCheckValuesSpace(final String defaultStr, final String... strs) {
        return StringUtils.toCheckValuesSpace(defaultStr, strs);
    }

    // ==================
    // = 数据格式化处理 =
    // ==================

    /**
     * 获取格式化后的字符串
     * @param format 待格式化字符串
     * @param args   格式化参数
     * @return 格式化后的字符串
     */
    public static String getFormatString(final String format, final Object... args) {
        return StringUtils.getFormatString(format, args);
    }

    // =

    /**
     * 获取自动数量格式化后的字符串 ( 可变参数 )
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString(final Object... args) {
        return StringUtils.getAutoFormatString(args);
    }

    /**
     * 获取自动数量格式化后的字符串 ( 可变参数 )
     * @param args 格式化参数
     * @return 格式化后的字符串
     */
    public static String getAutoFormatString2(final Object... args) {
        return StringUtils.getAutoFormatString2(args);
    }

    // =

    /**
     * StringBuilder 拼接处理
     * @param builder 拼接 Builder
     * @param split   追加间隔
     * @param args    拼接数据源
     * @return {@link StringBuilder}
     */
    public static StringBuilder appends(final StringBuilder builder, final String split, final Object... args) {
        return StringUtils.appends(builder, split, args);
    }

    /**
     * StringBuilder 拼接处理 ( 最后一个不追加间隔 )
     * @param builder 拼接 Builder
     * @param split   追加间隔
     * @param args    拼接数据源
     * @return {@link StringBuilder}
     */
    public static StringBuilder appendsIgnoreLast(final StringBuilder builder, final String split, final Object... args) {
        return StringUtils.appendsIgnoreLast(builder, split, args);
    }

    // ==============
    // = 字符串处理 =
    // ==============

    /**
     * 转换手机号
     * @param phone 待处理字符串
     * @return 处理后的字符串
     */
    public static String converHideMobile(final String phone) {
        return StringUtils.converHideMobile(phone);
    }

    /**
     * 转换手机号
     * @param phone  待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
     */
    public static String converHideMobile(final String phone, final String symbol) {
        return StringUtils.converHideMobile(phone, symbol);
    }

    /**
     * 转换符号处理
     * @param start  开始位置
     * @param str    待处理字符串
     * @param symbol 转换符号
     * @return 处理后的字符串
     */
    public static String converSymbolHide(final int start, final String str, final String symbol) {
        return StringUtils.converSymbolHide(start, str, symbol);
    }

    // =

    /**
     * 裁剪超出的内容, 并且追加符号 ( 如 ...)
     * @param maxLength 允许最大的长度
     * @param str       待处理字符串
     * @param symbol    转换符号
     * @return 处理后的字符串
     */
    public static String subEllipsize(final int maxLength, final String str, final String symbol) {
        return StringUtils.subEllipsize(maxLength, str, symbol);
    }

    /**
     * 裁剪符号处理
     * @param start        开始位置
     * @param symbolNumber 转换数量
     * @param str          待处理字符串
     * @param symbol       转换符号
     * @return 处理后的字符串
     */
    public static String subSymbolHide(final int start, final int symbolNumber, final String str, final String symbol) {
        return StringUtils.subSymbolHide(start, symbolNumber, str, symbol);
    }

    /**
     * 裁剪内容, 设置符号处理
     * @param str               待处理字符串
     * @param frontRetainLength 前面保留的长度
     * @param rearRetainLength  后面保留的长度
     * @param symbol            转换符号
     * @return 处理后的字符串
     */
    public static String subSetSymbol(final String str, final int frontRetainLength, final int rearRetainLength, final String symbol) {
        return StringUtils.subSetSymbol(str, frontRetainLength, rearRetainLength, symbol);
    }

    // ==================
    // = 替换、截取操作 =
    // ==================

    /**
     * 裁剪字符串
     * @param str      待裁剪字符串
     * @param endIndex 结束裁剪的位置
     * @return 裁剪后的字符串
     */
    public static String substring(final String str, final int endIndex) {
        return StringUtils.substring(str, endIndex);
    }

    /**
     * 裁剪字符串
     * @param str      待裁剪字符串
     * @param endIndex 结束裁剪的位置
     * @param isReturn 开始位置超过限制是否返回内容
     * @return 裁剪后的字符串
     */
    public static String substring(final String str, final int endIndex, final boolean isReturn) {
        return StringUtils.substring(str, endIndex, isReturn);
    }

    /**
     * 裁剪字符串
     * @param str        待裁剪字符串
     * @param beginIndex 开始裁剪的位置
     * @param endIndex   结束裁剪的位置
     * @param isReturn   开始位置超过限制是否返回内容
     * @return 裁剪后的字符串
     */
    public static String substring(final String str, final int beginIndex, final int endIndex, final boolean isReturn) {
        return StringUtils.substring(str, beginIndex, endIndex, isReturn);
    }

    // =

    /**
     * 替换 ( 删除 - 替换成 "") 字符串中符合 特定标记字符的 startsWith - endsWith
     * * 如 _____a_a_a_a________ 传入 _ 等于 ____a_a_a_a____
     * @param str    待处理字符串
     * @param suffix 替换符号字符串
     * @return 处理后的字符串
     */
    public static String toReplaceSEWith(final String str, final String suffix) {
        return StringUtils.toReplaceSEWith(str, suffix);
    }

    /**
     * 替换字符串中符合 特定标记字符的 startsWith - endsWith
     * 如 _____a_a_a_a________ 传入 _, c 等于 c____a_a_a_a____c
     * @param str    待处理字符串
     * @param suffix 替换匹配内容
     * @param value  需要替换的内容
     * @return 处理后的字符串
     */
    public static String toReplaceSEWith(final String str, final String suffix, final String value) {
        return StringUtils.toReplaceSEWith(str, suffix, value);
    }

    // =

    /**
     * 替换开头字符串
     * @param str    待处理字符串
     * @param prefix 开头匹配字符串
     * @return 处理后的字符串
     */
    public static String toReplaceStartsWith(final String str, final String prefix) {
        return StringUtils.toReplaceStartsWith(str, prefix);
    }

    /**
     * 替换开头字符串
     * @param str         待处理字符串
     * @param prefix      开头匹配字符串
     * @param startAppend 开头追加的内容
     * @return 处理后的字符串
     */
    public static String toReplaceStartsWith(final String str, final String prefix, final String startAppend) {
        return StringUtils.toReplaceStartsWith(str, prefix, startAppend);
    }

    /**
     * 替换结尾字符串
     * @param str    待处理字符串
     * @param suffix 结尾匹配字符串
     * @return 处理后的字符串
     */
    public static String toReplaceEndsWith(final String str, final String suffix) {
        return StringUtils.toReplaceEndsWith(str, suffix);
    }

    /**
     * 替换结尾字符串
     * @param str    待处理字符串
     * @param suffix 结尾匹配字符串
     * @param value  需要替换的内容
     * @return 处理后的字符串
     */
    public static String toReplaceEndsWith(final String str, final String suffix, final String value) {
        return StringUtils.toReplaceEndsWith(str, suffix, value);
    }

    // =

    /**
     * 这个方法功能主要把字符符合标记的 头部和尾部都替换成 ""
     * 如 _____a_a_a_a________ 传入 _ 等于 a_a_a_a
     * 替换字符串中符合 特定标记字符的 startsWith(indexOf) - endsWith(lastIndexOf)
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String toClearSEWiths(final String str, final String suffix) {
        return StringUtils.toClearSEWiths(str, suffix);
    }

    /**
     * 清空属于特定字符串开头的字段
     * 如 _____a_a_a_a________ 传入 _ 等于 a_a_a_a_____
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf)
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String toClearStartsWith(final String str, final String suffix) {
        return StringUtils.toClearStartsWith(str, suffix);
    }

    /**
     * 清空属于特定字符串结尾的字段
     * 如 _____a_a_a_a________ 传入 _ 等于 _____a_a_a_a
     * 替换字符串中符合 特定标记字符的 endsWith(lastIndexOf)
     * @param str    待处理字符串
     * @param suffix 匹配判断字符串
     * @return 处理后的字符串
     */
    public static String toClearEndsWith(final String str, final String suffix) {
        return StringUtils.toClearEndsWith(str, suffix);
    }

    // =

    /**
     * 替换字符串
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串
     */
    public static String replaceAll(final String str, final String suffix, final String replace) {
        return StringUtils.replaceAll(str, suffix, replace);
    }

    /**
     * 替换字符串
     * @param str     待处理字符串
     * @param suffix  匹配判断字符串
     * @param replace 替换的内容
     * @return 处理后的字符串, 替换失败则返回 null
     */
    public static String replaceAllToNull(final String str, final String suffix, final String replace) {
        return StringUtils.replaceAllToNull(str, suffix, replace);
    }

    /**
     * 替换字符串
     * @param str         待处理数据
     * @param suffixArys  匹配判断字符串数组
     * @param replaceArys 准备替换的字符串数组
     * @return 处理后的字符串
     */
    public static String replaceAlls(final String str, final String[] suffixArys, final String[] replaceArys) {
        return StringUtils.replaceAlls(str, suffixArys, replaceArys);
    }

    /**
     * 拆分字符串
     * @param str   待处理字符串
     * @param regex 正则表达式
     * @return 拆分后的数组
     */
    public static String[] split(final String str, final String regex) {
        return StringUtils.split(str, regex);
    }

    /**
     * 拆分字符串获取指定索引
     * @param str   待处理字符串
     * @param regex 正则表达式
     * @param index 索引
     * @return 拆分后的数组
     */
    public static String split(final String str, final String regex, final int index) {
        return StringUtils.split(str, regex, index);
    }

    /**
     * 拆分字符串获取指定索引
     * @param str        待处理字符串
     * @param regex      正则表达式
     * @param index      索引
     * @param defaultStr 默认字符串
     * @return 拆分后的数组
     */
    public static String split(final String str, final String regex,
                               final int index, final String defaultStr) {
        return StringUtils.split(str, regex, index, defaultStr);
    }
}