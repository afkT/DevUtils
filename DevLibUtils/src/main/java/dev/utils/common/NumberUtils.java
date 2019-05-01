package dev.utils.common;

import java.util.regex.Pattern;

import dev.utils.JCLogUtils;

/**
 * detail: 数字(计算)工具类
 * @author Ttt
 */
public final class NumberUtils {

    private NumberUtils() {
    }

    // 日志 TAG
    private static final String TAG = NumberUtils.class.getSimpleName();
    // 正则表达式: 验证数字
    private static final String REGEX_NUMBER = "^[0-9]*$";
    // 正则表达式: 验证数字或包含小数点
    private static final String REGEX_NUMBER_OR_DECIMAL = "^[0-9]*[.]?[0-9]*$";

    /**
     * 计算百分比值 (最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percent(final int value, final int max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return (float) value / (float) max;
    }

    /**
     * 计算百分比值 (最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percent(final float value, final float max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percent(final long value, final long max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (最大 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 百分比值
     */
    public static double percent(final double value, final double max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        if (value >= max) return 1.0f;
        return value / max;
    }

    // =

    /**
     * 计算百分比值 (可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 比例值
     */
    public static double percent2(final int value, final int max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return (float) value / (float) max;
    }

    /**
     * 计算百分比值 (可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 比例值
     */
    public static double percent2(final float value, final float max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 比例值
     */
    public static double percent2(final long value, final long max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    /**
     * 计算百分比值 (可超出 100%)
     * @param value 指定值
     * @param max   最大值
     * @return 比例值
     */
    public static double percent2(final double value, final double max) {
        if (max <= 0) return 0.0f;
        if (value <= 0) return 0.0f;
        return value / max;
    }

    // =

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min之间的 value
     */
    public static int clamp(final int value, final int max, final int min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min之间的 value
     */
    public static float clamp(final float value, final float max, final float min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min之间的 value
     */
    public static long clamp(final long value, final long max, final long min) {
        return value > max ? max : value < min ? min : value;
    }

    /**
     * 返回的 value 介于 max、min之间，若 value 小于min，返回min，若大于max，返回max
     * @param value 指定值
     * @param max   最大值
     * @param min   最小值
     * @return 介于 max、min之间的 value
     */
    public static double clamp(final double value, final double max, final double min) {
        return value > max ? max : value < min ? min : value;
    }

    // =

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
