package dev.utils.common;

import java.math.BigDecimal;
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

    // =======
    // = int =
    // =======

    /**
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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
     * 计算百分比值 (最大 100%)
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

    // ==============================
    // = 计算百分比值 (可超出 100%) =
    // ==============================

    // ========
    // = int =
    // =======

    /**
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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
     * 计算百分比值 (可超出 100%)
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

    // ==================
    // = 数字、中文互转 =
    // ==================

    // 零索引
    private static final int ZERO = 0;
    // 十位数索引
    private static final int TEN_POS = 10;
    // 中文(数字单位)
    private static final String[] CHN_NUMBER_UNITS = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "百", "千", "万", "亿", "兆"};
    // 中文大写(数字单位)
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
            return numberToCHNNumber(BigDecimal.valueOf(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS, false);
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
                return numberToCHNNumber(new BigDecimal(number), isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS, false);
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
        return numberToCHNNumber(number, isUpper ? CHN_NUMBER_UPPER_UNITS : CHN_NUMBER_UNITS, false);
    }

    // ============
    // = 内部方法 =
    // ============

    // ==============
    // = 数字转中文 =
    // ==============

    /**
     * 数字转中文数值
     * @param bigDecimal  数值
     * @param chnUnits    中文数字单位数组
     * @param isRecursion 是否递归处理(倍数超过万位才递归)
     * @return 数字中文化字符串
     */
    private static String numberToCHNNumber(final BigDecimal bigDecimal, final String[] chnUnits, final boolean isRecursion) {
        // 防止为 null
        if (bigDecimal == null) return null;
        // 去除小数点
        BigDecimal number = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        // 防止小于 1, 直接返回 0
        if (number.doubleValue() < 1) return chnUnits[ZERO];

        StringBuilder builder = new StringBuilder();
        // 记录当前数字单位(补零)
        int numberPos = 0;
        // 循环处理
        for (int i = NUMBER_UNITS.length - 1; i > 0; i--) {
            //if (number.compareTo(BigDecimal.valueOf(NUMBER_UNITS[i])) >= 0) {
            if (number.doubleValue() >= NUMBER_UNITS[i]) {
                final int multiple = (int) (number.doubleValue() / NUMBER_UNITS[i]); // 倍数
                number = number.subtract(BigDecimal.valueOf(multiple * NUMBER_UNITS[i])); // 递减
                // 如果倍数大于万倍, 则直接递归
                if (multiple >= 10000) {
                    builder.append(numberToCHNNumber(new BigDecimal(multiple), chnUnits, true));
                    builder.append(chnUnits[i]); // 数字单位
                    // 判断是否需要补零
                    if (numberPos > i && numberPos != 0) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                } else {
                    // 判断是否少一个数字单位
                    if (i == 13 && numberPos == 14) { // 判断亿级与万级, 补零操作
                        // 判断是否不属于千万级
                        if (multiple < 1000) {
                            builder.append(chnUnits[ZERO]); // 补零
                        }
                    }
                    builder.append(thousandConvertCHN(multiple, chnUnits));
                    builder.append(chnUnits[i]); // 数字单位
                }
                // 保存新的数字单位索引
                numberPos = i;
            }

            // 如果位数小于万位(属于千位), 则进行处理
            if (number.doubleValue() < 10000) {
                // 判断是否需要补零(不属于递归的)
                if (!isRecursion && numberPos >= (TEN_POS + 3)) {
                    // 当前数字单位属于万级, 并且数字小于千, 才补零
                    if (numberPos == (TEN_POS + 3) && number.doubleValue() < 1000) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                }
                builder.append(thousandConvertCHN((int) number.doubleValue(), chnUnits));
                return builder.toString();
            }
        }
        return builder.toString();
    }

    /**
     * 万位以下(千位)级别转换
     * @param number   数值
     * @param chnUnits 中文数字单位数组
     * @return 数字中文化字符串
     */
    private static String thousandConvertCHN(int number, final String[] chnUnits) {
        StringBuilder builder = new StringBuilder();
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
            if (number > 1 && zeros[i]) {
                if (i == 1) { // 属于百位, 特殊处理(防止出现 1001 直接个数清空)
                    if (number > 10) {
                        builder.append(chnUnits[ZERO]); // 补零
                    }
                } else {
                    builder.append(chnUnits[ZERO]); // 补零
                }
            }
        }
        // 判断最后值, 是否大于 1 (结尾零, 则不补充单位)
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
