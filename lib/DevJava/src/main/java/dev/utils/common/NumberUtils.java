package dev.utils.common;

import java.math.BigDecimal;

import dev.utils.common.validator.ValidatorUtils;

/**
 * detail: 数字 ( 计算 ) 工具类
 * @author Ttt
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    /**
     * 补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param value 待处理值
     * @return 自动补 0 字符串
     */
    public static String addZero(final int value) {
        return addZero(value, true);
    }

    /**
     * 补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param value  待处理值
     * @param append 是否需要自动补 0
     * @return 自动补 0 字符串
     */
    public static String addZero(
            final int value,
            final boolean append
    ) {
        if (!append) return String.valueOf(value);
        int temp = Math.max(0, value);
        return temp >= 10 ? String.valueOf(temp) : "0" + temp;
    }

    /**
     * 去掉结尾多余的 . 与 0
     * @param value 待处理数值
     * @return 处理后的数值字符串
     */
    public static String subZeroAndDot(final double value) {
        return subZeroAndDot(String.valueOf(value));
    }

    /**
     * 去掉结尾多余的 . 与 0
     * @param value 待处理数值
     * @return 处理后的数值字符串
     */
    public static String subZeroAndDot(final float value) {
        return subZeroAndDot(String.valueOf(value));
    }

    /**
     * 去掉结尾多余的 . 与 0
     * @param value 待处理数值
     * @return 处理后的数值字符串
     */
    public static String subZeroAndDot(final String value) {
        if (StringUtils.isNotEmpty(value)) {
            String str = value;
            if (str.indexOf(".") >= 0) {
                // 去掉多余的 0
                str = str.replaceAll("0+?$", "");
                // 最后一位是 . 则去掉
                str = str.replaceAll("[.]$", "");
            }
            return str;
        }
        return value;
    }

    // =======
    // = int =
    // =======

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        if (value >= max) return 1;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        if (value >= max) return 1;
        return (int) (value / max);
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        if (value >= max) return 1;
        return (int) (value / max);
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        if (value >= max) return 1;
        return (int) (value / max);
    }

    // ==========
    // = double =
    // ==========

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        if (value >= max) return 1.0d;
        return (double) value / (double) max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        if (value >= max) return 1.0d;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        if (value >= max) return 1.0d;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        if (value >= max) return 1.0d;
        return value / max;
    }

    // ========
    // = long =
    // ========

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        if (value >= max) return 1L;
        return (long) value / (long) max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        if (value >= max) return 1L;
        return (long) value / (long) max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        if (value >= max) return 1L;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        if (value >= max) return 1L;
        return (long) (value / max);
    }

    // =========
    // = float =
    // =========

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) value / (float) max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) (value / max);
    }

    // =============================
    // = 计算百分比值 ( 可超出 100%) =
    // =============================

    // =======
    // = int =
    // =======

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        return (int) (value / max);
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        return (int) (value / max);
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0;
        if (value <= 0) return 0;
        return (int) (value / max);
    }

    // ==========
    // = double =
    // ==========

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD2(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        return (double) value / (double) max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD2(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD2(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percentD2(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0.0d;
        if (value <= 0) return 0.0d;
        return value / max;
    }

    // ========
    // = long =
    // ========

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL2(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        return (long) value / (long) max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL2(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        return (long) value / (long) max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL2(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL2(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0L;
        if (value <= 0) return 0L;
        return (long) (value / max);
    }

    // =========
    // = float =
    // =========

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF2(
            final int value,
            final int max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return (float) value / (float) max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF2(
            final float value,
            final float max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF2(
            final long value,
            final long max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF2(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return (float) (value / max);
    }

    // =

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static int clamp(
            final int value,
            final int max,
            final int min
    ) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static double clamp(
            final double value,
            final double max,
            final double min
    ) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static long clamp(
            final long value,
            final long max,
            final long min
    ) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static float clamp(
            final float value,
            final float max,
            final float min
    ) {
        return value > max ? max : value < min ? min : value;
    }

    // ========
    // = calc =
    // ========

    /**
     * 获取倍数 ( 自动补 1)
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultiple(
            final int value,
            final int divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        return (value % divisor == 0) ? (value / divisor) : (value / divisor) + 1;
    }

    /**
     * 获取倍数 ( 自动补 1)
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultiple(
            final double value,
            final double divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        int result = (int) (value / divisor);
        return ((value - divisor * result == 0d) ? result : result + 1);
    }

    /**
     * 获取倍数 ( 自动补 1)
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultiple(
            final long value,
            final long divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        return (int) ((value % divisor == 0) ? (value / divisor) : (value / divisor) + 1);
    }

    /**
     * 获取倍数 ( 自动补 1)
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultiple(
            final float value,
            final float divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        int result = (int) (value / divisor);
        return ((value - divisor * result == 0f) ? result : result + 1);
    }

    // =======
    // = int =
    // =======

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(
            final int value,
            final int divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(
            final double value,
            final double divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(
            final long value,
            final long divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(
            final float value,
            final float divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    // ==========
    // = double =
    // ==========

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(
            final int value,
            final int divisor
    ) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(
            final double value,
            final double divisor
    ) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(
            final long value,
            final long divisor
    ) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(
            final float value,
            final float divisor
    ) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    // ========
    // = long =
    // ========

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(
            final int value,
            final int divisor
    ) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(
            final double value,
            final double divisor
    ) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(
            final long value,
            final long divisor
    ) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(
            final float value,
            final float divisor
    ) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    // =========
    // = float =
    // =========

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(
            final int value,
            final int divisor
    ) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(
            final double value,
            final double divisor
    ) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(
            final long value,
            final long divisor
    ) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(
            final float value,
            final float divisor
    ) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 计算指定单位倍数
     * @param units 单位数组
     * @param value 待计算数值
     * @return 单位数组对应倍数值
     */
    public static int[] calculateUnit(
            final double[] units,
            final double value
    ) {
        if (units == null) return null;
        if (value <= 0) return null;
        int    len    = units.length;
        int[]  arrays = new int[len];
        double temp   = value;
        for (int i = 0; i < len; i++) {
            if (temp >= units[i]) {
                int mode = (int) (temp / units[i]);
                temp -= mode * units[i];
                arrays[i] = mode;
            }
        }
        return arrays;
    }

    // =============
    // = 数字转中文 =
    // =============

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final double number,
            final boolean isUpper
    ) {
        return ChineseUtils.numberToCHN(number, isUpper);
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final String number,
            final boolean isUpper
    ) {
        return ChineseUtils.numberToCHN(number, isUpper);
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(
            final BigDecimal number,
            final boolean isUpper
    ) {
        return ChineseUtils.numberToCHN(number, isUpper);
    }

    // ==================
    // = ValidatorUtils =
    // ==================

    /**
     * 检验数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumber(final String str) {
        return ValidatorUtils.isNumber(str);
    }

    /**
     * 检验数字或包含小数点
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberDecimal(final String str) {
        return ValidatorUtils.isNumberDecimal(str);
    }
}