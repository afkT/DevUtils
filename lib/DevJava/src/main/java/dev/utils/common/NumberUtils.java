package dev.utils.common;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import dev.utils.JCLogUtils;

/**
 * detail: 数字 ( 计算 ) 工具类
 * @author Ttt
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    // 日志 TAG
    private static final String TAG = NumberUtils.class.getSimpleName();

    // =======
    // = int =
    // =======

    /**
     * 计算百分比值 ( 最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(final int value, final int max) {
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
    public static int percentI(final float value, final float max) {
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
    public static int percentI(final long value, final long max) {
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
    public static int percentI(final double value, final double max) {
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
    public static double percentD(final int value, final int max) {
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
    public static double percentD(final float value, final float max) {
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
    public static double percentD(final long value, final long max) {
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
    public static double percentD(final double value, final double max) {
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
    public static long percentL(final int value, final int max) {
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
    public static long percentL(final float value, final float max) {
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
    public static long percentL(final long value, final long max) {
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
    public static long percentL(final double value, final double max) {
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
    public static float percentF(final int value, final int max) {
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
    public static float percentF(final float value, final float max) {
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
    public static float percentF(final long value, final long max) {
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
    public static float percentF(final double value, final double max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) (value / max);
    }

    // ===============================
    // = 计算百分比值 ( 可超出 100%) =
    // ===============================

    // =======
    // = int =
    // =======

    /**
     * 计算百分比值 ( 可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(final int value, final int max) {
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
    public static int percentI2(final float value, final float max) {
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
    public static int percentI2(final long value, final long max) {
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
    public static int percentI2(final double value, final double max) {
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
    public static double percentD2(final int value, final int max) {
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
    public static double percentD2(final float value, final float max) {
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
    public static double percentD2(final long value, final long max) {
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
    public static double percentD2(final double value, final double max) {
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
    public static long percentL2(final int value, final int max) {
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
    public static long percentL2(final float value, final float max) {
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
    public static long percentL2(final long value, final long max) {
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
    public static long percentL2(final double value, final double max) {
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
    public static float percentF2(final int value, final int max) {
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
    public static float percentF2(final float value, final float max) {
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
    public static float percentF2(final long value, final long max) {
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
    public static float percentF2(final double value, final double max) {
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
    public static int clamp(final int value, final int max, final int min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static double clamp(final double value, final double max, final double min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static long clamp(final long value, final long max, final long min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min 之间, 若 value 小于 min, 返回 min, 若大于 max, 返回 max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min 之间的 value
     */
    public static float clamp(final float value, final float max, final float min) {
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
    public static int getMultiple(final int value, final int divisor) {
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
    public static int getMultiple(final double value, final double divisor) {
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
    public static int getMultiple(final long value, final long divisor) {
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
    public static int getMultiple(final float value, final float divisor) {
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
    public static int getMultipleI(final int value, final int divisor) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(final double value, final double divisor) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(final long value, final long divisor) {
        if (value <= 0 || divisor <= 0) return 0;
        return (int) (value / divisor);
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int getMultipleI(final float value, final float divisor) {
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
    public static double getMultipleD(final int value, final int divisor) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(final double value, final double divisor) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(final long value, final long divisor) {
        if (value <= 0d || divisor <= 0d) return 0d;
        return (double) value / (double) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static double getMultipleD(final float value, final float divisor) {
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
    public static long getMultipleL(final int value, final int divisor) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(final double value, final double divisor) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(final long value, final long divisor) {
        if (value <= 0L || divisor <= 0L) return 0L;
        return (long) value / (long) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long getMultipleL(final float value, final float divisor) {
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
    public static float getMultipleF(final int value, final int divisor) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(final double value, final double divisor) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(final long value, final long divisor) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float getMultipleF(final float value, final float divisor) {
        if (value <= 0f || divisor <= 0f) return 0f;
        return (float) value / (float) divisor;
    }

    // ==================
    // = 数字、中文互转 =
    // ==================

    // 零索引
    private static final int ZERO = 0;
    // 十位数索引
    private static final int TEN_POS = 10;
    // 中文 ( 数字单位 )
    private static final String[] CHN_NUMBER_UNITS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿", "兆"};
    // 中文大写 ( 数字单位 )
    private static final String[] CHN_NUMBER_UPPER_UNITS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "佰", "仟", "万", "亿", "兆"};
    // 数字单位对应数值
    private static final double[] NUMBER_UNITS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 100, 1000, 10000, 100000000, 1000000000000d};

    // ============
    // = 对外公开 =
    // ============

    // ==============
    // = 数字转中文 =
    // ==============

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(final double number, final boolean isUpper) {
        try {
            return numberToCHNNumber(BigDecimal.valueOf(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "numberToCHN");
        }
        return null;
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(final String number, final boolean isUpper) {
        if (number != null) {
            try {
                return numberToCHNNumber(new BigDecimal(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "numberToCHN");
            }
        }
        return null;
    }

    /**
     * 数字转中文数值
     * @param number  数值
     * @param isUpper 是否大写金额
     * @return 数字中文化字符串
     */
    public static String numberToCHN(final BigDecimal number, final boolean isUpper) {
        return numberToCHNNumber(number, isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS);
    }

    // ============
    // = 内部方法 =
    // ============

    // ==============
    // = 数字转中文 =
    // ==============

    /**
     * 数字转中文数值
     * @param bigDecimal 数值
     * @param chnUnits   中文数字单位数组
     * @return 数字中文化字符串
     */
    private static String numberToCHNNumber(final BigDecimal bigDecimal, final String[] chnUnits) {
        // 防止为 null
        if (bigDecimal == null) return null;
        // 去除小数点
        BigDecimal number = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        // 防止小于 1, 直接返回 0
        if (number.doubleValue() < 1) return chnUnits[ZERO];

        StringBuilder builder = new StringBuilder();
        // 索引记录
        int unitIndex = 0; // 当前数字单位
        int beforeUnitIndex = 0; // 之前的数字单位
        // 循环处理
        for (int i = NUMBER_UNITS.length - 1; i > 0; i--) {
            if (number.doubleValue() >= NUMBER_UNITS[i]) {
                final int multiple = (int) (number.doubleValue() / NUMBER_UNITS[i]); // 倍数
                number = number.subtract(BigDecimal.valueOf(multiple * NUMBER_UNITS[i])); // 递减
                // 如果倍数大于万倍, 则直接递归
                if (multiple >= 10000) {
                    // 拼接数值
                    builder.append(numberToCHNNumber(new BigDecimal(multiple), chnUnits));
                    builder.append(chnUnits[i]); // 数字单位
                    // 判断是否需要补零
                    if (unitIndex > i && unitIndex != 0) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                } else {
                    // 判断 兆级与亿级、亿级与万级 补零操作
                    if ((i == 14 && unitIndex == 15) || (i == 13 && unitIndex == 14)) {
                        if (multiple < 1000) {
                            builder.append(chnUnits[ZERO]); // 补零
                        }
                    } else if (unitIndex > i && unitIndex != 0) { // 跨数字单位处理
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                    // 拼接数值
                    builder.append(thousandConvertCHN(multiple, chnUnits));
                    builder.append(chnUnits[i]); // 数字单位
                }
                // 保存旧的数字单位索引
                beforeUnitIndex = unitIndex;
                // 保存新的数字单位索引
                unitIndex = i;
            }

            double numberValue = number.doubleValue();
            // 如果位数小于万位 ( 属于千位 ), 则进行处理
            if (numberValue < 10000) {
                // 判断是否需要补零
                if (unitIndex >= (TEN_POS + 3)) {
                    // 是否大于 1 ( 结尾零, 则不补充数字单位 )
                    if (numberValue >= 1) {
                        if (beforeUnitIndex == 0) {
                            beforeUnitIndex = unitIndex;
                        }
                        // 如果旧的索引, 大于当前索引, 则补零
                        if (unitIndex != 13 && (beforeUnitIndex == 14 || beforeUnitIndex == 15)
                                && beforeUnitIndex >= unitIndex) { // 属于亿、兆级别, 都需要补零
                            builder.append(chnUnits[ZERO]); // 补零
                        } else { // 当前数字单位属于万级
                            if (numberValue < 1000) {
                                builder.append(chnUnits[ZERO]); // 补零
                            }
                        }
                    }
                }
                // 拼接数值
                builder.append(thousandConvertCHN((int) numberValue, chnUnits));
                return builder.toString();
            }
        }
        return builder.toString();
    }

    /**
     * 万位以下 ( 千位 ) 级别转换
     * @param value    数值
     * @param chnUnits 中文数字单位数组
     * @return 数字中文化字符串
     */
    private static String thousandConvertCHN(final int value, final String[] chnUnits) {
        StringBuilder builder = new StringBuilder();
        // 转换数字
        int number = value;
        // 对应数值
        int[] units = {10, 100, 1000};
        // 判断是否需要补零
        boolean[] zeros = new boolean[3];
        zeros[0] = number >= 10; // 大于等于十位, 才需要补零
        zeros[1] = number >= 100; // 大于等于百位, 才需要补零
        zeros[2] = false; // 千位数, 不需要补零
        // 循环处理
        for (int i = 2; i >= 0; i--) {
            if (number >= units[i]) {
                int multiple = (number / units[i]);
                number -= multiple * units[i];
                // 拼接数值
                builder.append(chnUnits[multiple]); // 个位数
                builder.append(chnUnits[TEN_POS + i]); // 数字单位
                // 进行改变处理
                zeros[i] = false;
            }
            // 补零判断处理
            if (number >= 1 && zeros[i]) {
                if (i == 1) { // 属于百位
                    builder.append(chnUnits[ZERO]); // 补零
                } else { // 属于十位
                    // 如果百位, 补零了, 个位数则不用补零
                    if (!zeros[i + 1]) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                }
            }
        }
        // 判断最后值, 是否大于 1 ( 结尾零, 则不补充数字单位 )
        if (number >= 1) {
            builder.append(chnUnits[number]);
        }
        return builder.toString();
    }

    // ======================
    // = 其他工具类实现代码 =
    // ======================

    // ===============
    // = StringUtils =
    // ===============

    /**
     * 判断字符串是否为 null
     * @param str 待校验的字符串
     * @return {@code true} is null, {@code false} not null
     */
    private static boolean isEmpty(final String str) {
        return (str == null || str.length() == 0);
    }

    // ==================
    // = ValidatorUtils =
    // ==================

    // 正则表达式: 验证数字
    private static final String REGEX_NUMBER = "^[0-9]*$";
    // 正则表达式: 验证数字或包含小数点
    private static final String REGEX_NUMBER_OR_DECIMAL = "^[0-9]*[.]?[0-9]*$";

    /**
     * 检验数字
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumber(final String str) {
        return match(REGEX_NUMBER, str);
    }

    /**
     * 检验数字或包含小数点
     * @param str 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isNumberDecimal(final String str) {
        return match(REGEX_NUMBER_OR_DECIMAL, str);
    }

    /**
     * 通用匹配函数
     * @param regex 正则表达式
     * @param input 待校验的字符串
     * @return {@code true} yes, {@code false} no
     */
    private static boolean match(final String regex, final String input) {
        if (!isEmpty(input)) {
            try {
                return Pattern.matches(regex, input);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "match");
            }
        }
        return false;
    }
}