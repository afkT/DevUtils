package dev.utils.common;

import java.util.Random;
import java.util.UUID;

import dev.utils.DevFinal;
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

    // ===============
    // = 计时相关处理 =
    // ===============

    /**
     * 耗时时间记录
     * @param builder   拼接 Builder
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {@link StringBuilder}
     */
    public static StringBuilder timeRecord(
            final StringBuilder builder,
            final long startTime,
            final long endTime
    ) {
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
    public static StringBuilder timeRecord(
            final StringBuilder builder,
            final String title,
            final long startTime,
            final long endTime
    ) {
        if (builder == null) return builder;
        // 使用时间
        long diffTime = endTime - startTime;
        // 计算时间
        if (!StringUtils.isEmpty(title)) {
            builder.append(DevFinal.NEW_LINE_STR);
            builder.append(title);
        }
        // 计算时间
        builder.append(DevFinal.NEW_LINE_STR).append("开始时间: ")
                .append(DateUtils.formatTime(startTime, DateUtils.yyyyMMddHHmmss))
                .append(DevFinal.NEW_LINE_STR).append("结束时间: ")
                .append(DateUtils.formatTime(endTime, DateUtils.yyyyMMddHHmmss))
                .append(DevFinal.NEW_LINE_STR).append("所用时间(毫秒): ")
                .append(diffTime)
                .append(DevFinal.NEW_LINE_STR).append("所用时间(秒): ")
                .append(diffTime / 1000);
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
    public static long getOperateTime(
            final long operateTime,
            final int randomTime
    ) {
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
    public static void sleepOperate(
            final long sleepTime,
            final int randomTime
    ) {
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
        if (!StringUtils.isEmpty(str)) {
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
    public static String whileMD5(
            final String str,
            final int number,
            final boolean isUppercase,
            final String... salts
    ) {
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

    // =============
    // = 获取唯一数 =
    // =============

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
        String random1 = String.valueOf(900000 + new Random().nextInt(10000));
        // 获取随机数
        String random2 = String.valueOf(900000 + new Random().nextInt(10000));
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
}