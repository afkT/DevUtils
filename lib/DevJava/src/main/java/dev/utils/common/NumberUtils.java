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
     * @param value      待处理值
     * @param appendZero 是否自动补 0
     * @return 自动补 0 字符串
     */
    public static String addZero(
            final int value,
            final boolean appendZero
    ) {
        if (!appendZero) return String.valueOf(value);
        int temp = Math.max(0, value);
        return temp >= 10 ? String.valueOf(temp) : "0" + temp;
    }

    /**
     * 补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param value 待处理值
     * @return 自动补 0 字符串
     */
    public static String addZero(final long value) {
        return addZero(value, true);
    }

    /**
     * 补 0 处理 ( 小于 10, 则自动补充 0x )
     * @param value      待处理值
     * @param appendZero 是否自动补 0
     * @return 自动补 0 字符串
     */
    public static String addZero(
            final long value,
            final boolean appendZero
    ) {
        if (!appendZero) return String.valueOf(value);
        long temp = Math.max(0, value);
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
    public static String subZeroAndDot(final String value) {
        if (StringUtils.isNotEmpty(value)) {
            String str = value;
            if (str.contains(".")) {
                // 去掉多余的 0
                str = str.replaceAll("0+?$", "");
                // 最后一位是 . 则去掉
                str = str.replaceAll("[.]$", "");
            }
            return str;
        }
        return value;
    }

    // =============
    // = Unit Span =
    // =============

    /**
     * 计算指定单位倍数
     * <pre>
     *     返回数组前面都为整数倍, 最后一位可能存在小数点
     *     需要最后一位为最后余数, 则设置 units[last] 为 1 即可
     * </pre>
     * @param value     待计算数值
     * @param unitSpans 单位范围数组
     * @return 单位范围数组对应倍数值
     */
    public static double[] calculateUnitD(
            final double value,
            final double[] unitSpans
    ) {
        if (value <= 0) return null;
        if (unitSpans == null) return null;
        int      len    = unitSpans.length;
        double[] result = new double[len];
        double   temp   = value;
        for (int i = 0; i < len; i++) {
            if (temp >= unitSpans[i]) {
                double multiple = temp / unitSpans[i];
                if (i != len - 1) {
                    multiple = (int) multiple;
                }
                temp -= multiple * unitSpans[i];

                result[i] = multiple;
            }
        }
        return result;
    }

    /**
     * 计算指定单位倍数
     * <pre>
     *     需要最后一位为最后余数, 则设置 units[last] 为 1 即可
     * </pre>
     * @param value     待计算数值
     * @param unitSpans 单位范围数组
     * @return 单位范围数组对应倍数值
     */
    public static int[] calculateUnitI(
            final int value,
            final int[] unitSpans
    ) {
        if (value <= 0) return null;
        if (unitSpans == null) return null;
        int   len    = unitSpans.length;
        int[] result = new int[len];
        int   temp   = value;
        for (int i = 0; i < len; i++) {
            if (temp >= unitSpans[i]) {
                int multiple = temp / unitSpans[i];
                temp -= multiple * unitSpans[i];

                result[i] = multiple;
            }
        }
        return result;
    }

    /**
     * 计算指定单位倍数
     * <pre>
     *     需要最后一位为最后余数, 则设置 units[last] 为 1 即可
     * </pre>
     * @param value     待计算数值
     * @param unitSpans 单位范围数组
     * @return 单位范围数组对应倍数值
     */
    public static long[] calculateUnitL(
            final long value,
            final long[] unitSpans
    ) {
        if (value <= 0) return null;
        if (unitSpans == null) return null;
        int    len    = unitSpans.length;
        long[] result = new long[len];
        long   temp   = value;
        for (int i = 0; i < len; i++) {
            if (temp >= unitSpans[i]) {
                long multiple = temp / unitSpans[i];
                temp -= multiple * unitSpans[i];

                result[i] = multiple;
            }
        }
        return result;
    }

    /**
     * 计算指定单位倍数
     * <pre>
     *     返回数组前面都为整数倍, 最后一位可能存在小数点
     *     需要最后一位为最后余数, 则设置 units[last] 为 1 即可
     * </pre>
     * @param value     待计算数值
     * @param unitSpans 单位范围数组
     * @return 单位范围数组对应倍数值
     */
    public static float[] calculateUnitF(
            final float value,
            final float[] unitSpans
    ) {
        if (value <= 0) return null;
        if (unitSpans == null) return null;
        int     len    = unitSpans.length;
        float[] result = new float[len];
        float   temp   = value;
        for (int i = 0; i < len; i++) {
            if (temp >= unitSpans[i]) {
                float multiple = temp / unitSpans[i];
                if (i != len - 1) {
                    multiple = (int) multiple;
                }
                temp -= multiple * unitSpans[i];

                result[i] = multiple;
            }
        }
        return result;
    }

    // ===========
    // = percent =
    // ===========

    /**
     * 计算百分比值 ( 最大 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static Double percentD(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0D;
        if (value <= 0) return 0D;
        if (value >= max) return 1D;
        return value / max;
    }

    /**
     * 计算百分比值 ( 最大 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI(
            final double value,
            final double max
    ) {
        return percentD(value, max).intValue();
    }

    /**
     * 计算百分比值 ( 最大 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL(
            final double value,
            final double max
    ) {
        return percentD(value, max).longValue();
    }

    /**
     * 计算百分比值 ( 最大 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF(
            final double value,
            final double max
    ) {
        return percentD(value, max).floatValue();
    }

    // ============================
    // = 计算百分比值 ( 可超出 100% ) =
    // ============================

    /**
     * 计算百分比值 ( 可超出 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static Double percentD2(
            final double value,
            final double max
    ) {
        if (max <= 0) return 0D;
        if (value <= 0) return 0D;
        return value / max;
    }

    /**
     * 计算百分比值 ( 可超出 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static int percentI2(
            final double value,
            final double max
    ) {
        return percentD2(value, max).intValue();
    }

    /**
     * 计算百分比值 ( 可超出 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static long percentL2(
            final double value,
            final double max
    ) {
        return percentD2(value, max).longValue();
    }

    /**
     * 计算百分比值 ( 可超出 100% )
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static float percentF2(
            final double value,
            final double max
    ) {
        return percentD2(value, max).floatValue();
    }

    // ============
    // = Multiple =
    // ============

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static Double multipleD(
            final double value,
            final double divisor
    ) {
        if (value <= 0D || divisor <= 0D) return 0D;
        return value / divisor;
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static int multipleI(
            final double value,
            final double divisor
    ) {
        return multipleD(value, divisor).intValue();
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static long multipleL(
            final double value,
            final double divisor
    ) {
        return multipleD(value, divisor).longValue();
    }

    /**
     * 获取倍数
     * @param value   被除数
     * @param divisor 除数
     * @return 倍数
     */
    public static float multipleF(
            final double value,
            final double divisor
    ) {
        return multipleD(value, divisor).floatValue();
    }

    // =

    /**
     * 获取整数倍数 ( 自动补 1 )
     * <pre>
     *     能够整除返回整数倍数
     *     不能够整除返回整数倍数 + 1
     * </pre>
     * @param value   被除数
     * @param divisor 除数
     * @return 整数倍数
     */
    public static int multiple(
            final double value,
            final double divisor
    ) {
        if (value <= 0 || divisor <= 0) return 0;
        if (value <= divisor) return 1;
        int result = (int) (value / divisor);
        return ((value - divisor * result == 0D) ? result : result + 1);
    }

    // =========
    // = clamp =
    // =========

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
        return value > max ? max : Math.max(value, min);
    }

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
        return value > max ? max : Math.max(value, min);
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
        return value > max ? max : Math.max(value, min);
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
        return value > max ? max : Math.max(value, min);
    }

    // ============
    // = 数字转中文 =
    // ============

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