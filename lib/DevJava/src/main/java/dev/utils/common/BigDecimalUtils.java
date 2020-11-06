package dev.utils.common;

import java.math.BigDecimal;

import dev.utils.JCLogUtils;

/**
 * detail: 资金运算工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.cnblogs.com/liqforstudy/p/5652517.html"/>
 *     @see <a href="https://blog.csdn.net/mo_cold_rain/article/details/81366310"/>
 *     向下取 round = BigDecimal.ROUND_DOWN;
 * </pre>
 */
public final class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    // 日志 TAG
    private static final String TAG = BigDecimalUtils.class.getSimpleName();

    // 小数点位数
    private static int NEW_SCALE     = 10;
    // 舍入模式
    private static int ROUNDING_MODE = BigDecimal.ROUND_UNNECESSARY;

    /**
     * 设置小数点位数
     * @param scale 小数点位数
     */
    public static void setScale(final int scale) {
        NEW_SCALE = scale;
    }

    /**
     * 设置舍入模式
     * @param roundingMode 舍入模式
     */
    public static void setRoundingMode(final int roundingMode) {
        ROUNDING_MODE = roundingMode;
    }

    // =

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final int value) {
        return new BigDecimal(value);
    }

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final long value) {
        return new BigDecimal(value);
    }

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final float value) {
        return new BigDecimal(Float.toString(value));
    }

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final double value) {
        return new BigDecimal(Double.toString(value));
    }

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "getBigDecimal");
        }
        return null;
    }

    // =

    /**
     * 获取此 BigDecimal 的字符串表示形式科学记数法
     * @param value {@link BigDecimal}
     * @return 此 BigDecimal 的字符串表示形式科学记数法
     */
    public static String toString(final BigDecimal value) {
        return (value != null) ? value.toString() : null;
    }

    /**
     * 获取此 BigDecimal 的字符串表示形式不带指数字段
     * @param value {@link BigDecimal}
     * @return 此 BigDecimal 的字符串表示形式不带指数字段
     */
    public static String toPlainString(final BigDecimal value) {
        return (value != null) ? value.toPlainString() : null;
    }

    /**
     * 获取此 BigDecimal 的字符串表示形式工程计数法
     * @param value {@link BigDecimal}
     * @return 此 BigDecimal 的字符串表示形式工程计数法
     */
    public static String toEngineeringString(final BigDecimal value) {
        return (value != null) ? value.toEngineeringString() : null;
    }

    // ======
    // = 加 =
    // ======

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(final double v1, final double v2) {
        return add(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的加法运算
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留 scale 位小数
     * @return 两个参数的和
     */
    public static double add(final double v1, final double v2, final int scale) {
        return add(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的加法运算
     * @param v1           被加数
     * @param v2           加数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的和
     */
    public static double add(final double v1, final double v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.add(b2).intValue();
            } else {
                return b1.add(b2).setScale(scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "add");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(final String v1, final String v2) {
        return add(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的加法运算
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留 scale 位小数
     * @return 两个参数的和
     */
    public static BigDecimal add(final String v1, final String v2, final int scale) {
        return add(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的加法运算
     * @param v1           被加数
     * @param v2           加数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的和
     */
    public static BigDecimal add(final String v1, final String v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return new BigDecimal(b1.add(b2).intValue());
            } else {
                return b1.add(b2).setScale(scale, roundingMode);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "add");
        }
        return null;
    }

    // ======
    // = 减 =
    // ======

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double subtract(final double v1, final double v2) {
        return subtract(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的减法运算
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留 scale 位小数
     * @return 两个参数的差
     */
    public static double subtract(final double v1, final double v2, final int scale) {
        return subtract(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的减法运算
     * @param v1           被减数
     * @param v2           减数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的差
     */
    public static double subtract(final double v1, final double v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.subtract(b2).intValue();
            } else {
                return b1.subtract(b2).setScale(scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subtract");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(final String v1, final String v2) {
        return subtract(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的减法运算
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留 scale 位小数
     * @return 两个参数的差
     */
    public static BigDecimal subtract(final String v1, final String v2, final int scale) {
        return subtract(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的减法运算
     * @param v1           被减数
     * @param v2           减数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的差
     */
    public static BigDecimal subtract(final String v1, final String v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return new BigDecimal(b1.subtract(b2).intValue());
            } else {
                return b1.subtract(b2).setScale(scale, roundingMode);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "subtract");
        }
        return null;
    }

    // ======
    // = 乘 =
    // ======

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(final double v1, final double v2) {
        return multiply(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的乘法运算
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留 scale 位小数
     * @return 两个参数的积
     */
    public static double multiply(final double v1, final double v2, final int scale) {
        return multiply(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的乘法运算
     * @param v1           被乘数
     * @param v2           乘数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的积
     */
    public static double multiply(final double v1, final double v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.multiply(b2).intValue();
            } else {
                return b1.multiply(b2).setScale(scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(final String v1, final String v2) {
        return multiply(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的乘法运算
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留 scale 位小数
     * @return 两个参数的积
     */
    public static BigDecimal multiply(final String v1, final String v2, final int scale) {
        return multiply(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的乘法运算
     * @param v1           被乘数
     * @param v2           乘数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的积
     */
    public static BigDecimal multiply(final String v1, final String v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return new BigDecimal(b1.multiply(b2).intValue());
            } else {
                return b1.multiply(b2).setScale(scale, roundingMode);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return null;
    }

    // ======
    // = 除 =
    // ======

    /**
     * 提供精确的除法运算
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(final double v1, final double v2) {
        return divide(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的除法运算
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留 scale 位小数
     * @return 两个参数的商
     */
    public static double divide(final double v1, final double v2, final int scale) {
        return divide(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的除法运算
     * @param v1           被除数
     * @param v2           除数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的商
     */
    public static double divide(final double v1, final double v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.divide(b2).intValue();
            } else {
                return b1.divide(b2).setScale(scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "divide");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的除法运算
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal divide(final String v1, final String v2) {
        return divide(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的除法运算
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留 scale 位小数
     * @return 两个参数的商
     */
    public static BigDecimal divide(final String v1, final String v2, final int scale) {
        return divide(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的除法运算
     * @param v1           被除数
     * @param v2           除数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的商
     */
    public static BigDecimal divide(final String v1, final String v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return new BigDecimal(b1.divide(b2).intValue());
            } else {
                return b1.divide(b2).setScale(scale, roundingMode);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "divide");
        }
        return null;
    }

    // ========
    // = 取余 =
    // ========

    /**
     * 提供精确的取余运算
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的余数
     */
    public static double remainder(final double v1, final double v2) {
        return remainder(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的取余运算
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留 scale 位小数
     * @return 两个参数的余数
     */
    public static double remainder(final double v1, final double v2, final int scale) {
        return remainder(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的取余运算
     * @param v1           被除数
     * @param v2           除数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的余数
     */
    public static double remainder(final double v1, final double v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.remainder(b2).intValue();
            } else {
                return b1.remainder(b2).setScale(scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "remainder");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的取余运算
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的余数
     */
    public static BigDecimal remainder(final String v1, final String v2) {
        return remainder(v1, v2, NEW_SCALE, ROUNDING_MODE);
    }

    /**
     * 提供精确的取余运算
     * @param v1    被除数
     * @param v2    除数
     * @param scale 保留 scale 位小数
     * @return 两个参数的余数
     */
    public static BigDecimal remainder(final String v1, final String v2, final int scale) {
        return remainder(v1, v2, scale, ROUNDING_MODE);
    }

    /**
     * 提供精确的取余运算
     * @param v1           被除数
     * @param v2           除数
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 两个参数的余数
     */
    public static BigDecimal remainder(final String v1, final String v2, final int scale, final int roundingMode) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return new BigDecimal(b1.remainder(b2).intValue());
            } else {
                return b1.remainder(b2).setScale(scale, roundingMode);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "remainder");
        }
        return null;
    }

    // ===========
    // = 四舍五入 =
    // ===========

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1 需要四舍五入的数值
     * @return 四舍五入后的结果
     */
    public static double round(final double v1) {
        return round(v1, NEW_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1    需要四舍五入的数值
     * @param scale 保留 scale 位小数
     * @return 四舍五入后的结果
     */
    public static double round(final double v1, final int scale) {
        return round(v1, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1           需要四舍五入的数值
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 四舍五入后的结果
     */
    public static double round(final double v1, final int scale, final int roundingMode) {
        return divide(v1, 1d, scale, roundingMode);
    }

    // =

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1 需要四舍五入的数值
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(final String v1) {
        return round(v1, NEW_SCALE, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1    需要四舍五入的数值
     * @param scale 保留 scale 位小数
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(final String v1, final int scale) {
        return round(v1, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v1           需要四舍五入的数值
     * @param scale        保留 scale 位小数
     * @param roundingMode 舍入模式
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(final String v1, final int scale, final int roundingMode) {
        return divide(v1, "1", scale, roundingMode);
    }

    // ===========
    // = 比较大小 =
    // ===========

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
     */
    public static int compareTo(final double v1, final double v2) {
        try {
            return new BigDecimal(Double.valueOf(v1)).compareTo(new BigDecimal(Double.valueOf(v2)));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compareTo");
        }
        return -2;
    }

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
     */
    public static int compareTo(final String v1, final String v2) {
        try {
            return new BigDecimal(v1).compareTo(new BigDecimal(v2));
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compareTo");
        }
        return -2;
    }

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
     */
    public static int compareTo(final BigDecimal v1, final BigDecimal v2) {
        try {
            return v1.compareTo(v2);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compareTo");
        }
        return -2;
    }

    // ===========
    // = 金额分割 =
    // ===========

    /**
     * 金额分割, 四舍五入金额
     * @param value 金额数值
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value) {
        return formatMoney(value, 2, BigDecimal.ROUND_HALF_UP, 3, ",");
    }

    /**
     * 金额分割, 四舍五入金额
     * @param value 金额数值
     * @param scale 小数点后保留几位
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale) {
        return formatMoney(value, scale, BigDecimal.ROUND_HALF_UP, 3, ",");
    }

    /**
     * 金额分割, 四舍五入金额
     * @param value 金额数值
     * @param scale 小数点后保留几位
     * @param mode  处理模式
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale, final int mode) {
        return formatMoney(value, scale, mode, 3, ",");
    }

    /**
     * 金额分割, 四舍五入金额
     * @param value       金额数值
     * @param scale       小数点后保留几位
     * @param mode        处理模式
     * @param splitNumber 拆分位数
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale, final int mode, final int splitNumber) {
        return formatMoney(value, scale, mode, splitNumber, ",");
    }

    /**
     * 金额分割, 四舍五入金额
     * @param value       金额数值
     * @param scale       小数点后保留几位
     * @param mode        处理模式
     * @param splitNumber 拆分位数
     * @param splitSymbol 拆分符号
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale, final int mode, final int splitNumber, final String splitSymbol) {
        if (value == null) return null;
        try {
            // 如果等于 0, 直接返回
            if (value.doubleValue() == 0) {
                return value.setScale(scale, mode).toPlainString();
            }
            // 获取原始值字符串 ( 非科学计数法 )
            String valuePlain = value.toPlainString();
            // 判断是否负数
            boolean isNegative = valuePlain.startsWith("-");
            // 处理后的数据
            BigDecimal bigDecimal = new BigDecimal(isNegative ? valuePlain.substring(1) : valuePlain);
            // 范围处理
            valuePlain = bigDecimal.setScale(scale, mode).toPlainString();
            // 进行拆分小数点处理
            String[] values = valuePlain.split("\\.");
            // 判断是否存在小数点
            boolean isDecimal = (values.length == 2);

            // 拼接符号
            String symbol = (splitSymbol != null) ? splitSymbol : "";
            // 防止出现负数
            int number = Math.max(splitNumber, 0);
            // 格式化数据 ( 拼接处理 )
            StringBuilder builder = new StringBuilder();
            // 进行处理小数点前的数值
            for (int len = values[0].length() - 1, i = len, splitPos = 1; i >= 0; i--) {
                char ch = values[0].charAt(i);
                builder.append(ch);
                // 判断是否需要追加符号
                if (number > 0 && splitPos % number == 0 && i != 0) {
                    builder.append(symbol);
                }
                splitPos++;
            }
            // 倒序处理
            builder.reverse();
            // 存在小数点, 则进行拼接
            if (isDecimal) {
                builder.append(".").append(values[1]);
            }
            // 判断是否负数
            return isNegative ? "-" + builder.toString() : builder.toString();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "formatMoney");
        }
        return null;
    }

    // =

    /**
     * 获取自己想要的数据格式
     * @param value            需处理的数据
     * @param numOfIntPart     整数位数
     * @param numOfDecimalPart 小数位数
     * @return 处理过的数据
     */
    public static String adjustDouble(final String value, final int numOfIntPart, final int numOfDecimalPart) {
        if (value == null) return null;
        try {
            // 按小数点的位置分割成整数部分和小数部分
            String[] array = value.split("\\.");
            char[] tempA = new char[numOfIntPart];
            char[] tempB = new char[numOfDecimalPart];
            // 整数部分满足精度要求 ( 情况 1 )
            if (array[0].length() == numOfIntPart) {
                // 直接获取整数部分长度字符
                for (int i = 0; i < array[0].length(); i++) {
                    tempA[i] = array[0].charAt(i);
                }
                // 小数部分精度大于或等于指定的精度
                if (numOfDecimalPart <= array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        tempB[i] = array[1].charAt(i);
                    }
                }
                // 小数部分精度小于指定的精度
                if (numOfDecimalPart > array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        if (i < array[1].length()) {
                            tempB[i] = array[1].charAt(i);
                        } else {
                            tempB[i] = '0';
                        }
                    }
                }
                if (numOfDecimalPart == 0) {
                    return String.valueOf(tempA) + String.valueOf(tempB);
                }
                return String.valueOf(tempA) + "." + String.valueOf(tempB);
            }
            // 整数部分位数大于精度要求 ( 情况 2 )
            if (array[0].length() > numOfIntPart) {
                // 先倒序获取指定位数的整数
                for (int i = array[0].length() - 1, j = 0; (i >= array[0].length() - numOfIntPart) && (j < numOfIntPart); i--, j++) {
                    tempA[j] = array[0].charAt(i);
                }
                char[] tempA1 = new char[numOfIntPart];
                // 调整顺序
                for (int j = 0, k = tempA.length - 1; j < numOfIntPart && (k >= 0); j++, k--) {
                    tempA1[j] = tempA[k];
                }
                // 小数部分精度大于或等于指定的精度
                if (numOfDecimalPart <= array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        tempB[i] = array[1].charAt(i);
                    }
                }
                // 小数部分精度小于指定的精度
                if (numOfDecimalPart > array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        if (i < array[1].length()) {
                            tempB[i] = array[1].charAt(i);
                        } else {
                            tempB[i] = '0';
                        }
                    }
                }
                return String.valueOf(tempA1) + "." + String.valueOf(tempB);
            }
            // 整数部分满足精度要求 ( 情况 3 )
            if (array[0].length() == numOfIntPart) {
                // 直接获取整数部分长度字符
                for (int i = 0; i < array[0].length(); i++) {
                    tempA[i] = array[0].charAt(i);
                }
                // 小数部分精度小于指定的精度
                if (numOfDecimalPart > array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        if (i < array[1].length()) {
                            tempB[i] = array[1].charAt(i);
                        } else {
                            tempB[i] = '0';
                        }
                    }
                }
                // 小数部分精度大于或等于指定的精度
                if (numOfDecimalPart <= array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        tempB[i] = array[1].charAt(i);
                    }
                }
                if (numOfDecimalPart == 0) {
                    return String.valueOf(tempA) + String.valueOf(tempB);
                }
                return String.valueOf(tempA) + "." + String.valueOf(tempB);
            }
            // 整数部分大于精度要求 ( 情况 4 )
            if (array[0].length() > numOfIntPart) {
                // 先倒序获取指定位数的整数
                for (int i = array[0].length() - 1, j = 0; (i >= array[0].length() - numOfIntPart + 1) && (j < numOfIntPart); i--, j++) {
                    tempA[j] = array[0].charAt(i);
                }
                char[] tempA1 = new char[numOfIntPart];
                // 调整顺序
                for (int j = 0, k = tempA.length - 1; j < numOfIntPart && (k >= 0); j++) {
                    tempA1[j] = tempA[k];
                    k--;
                }
                // 小数部分精度小于指定的精度
                if (numOfDecimalPart > array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        if (i >= array[1].length()) {
                            tempB[i] = '0';
                        } else {
                            tempB[i] = array[1].charAt(i);
                        }
                    }
                }
                // 小数部分精度大于或等于指定的精度
                if (numOfDecimalPart <= array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        tempB[i] = array[1].charAt(i);
                    }
                }
                if (numOfDecimalPart == 0) {
                    return String.valueOf(tempA1) + String.valueOf(tempB);
                }
                return String.valueOf(tempA1) + "." + String.valueOf(tempB);
            }
            // 整数部分小于精度要求 ( 情况 5 )
            if (array[0].length() < numOfIntPart) {
                // 先倒序获取指定位数的整数
                char[] tempA1 = new char[numOfIntPart];
                for (int i = array[0].length() - 1, j = 0; (i >= numOfIntPart - array[0].length() - (numOfIntPart - array[0].length())) && (j < numOfIntPart); i--, j++) {
                    tempA1[j] = array[0].charAt(i);
                }
                // 补 0
                for (int i = array[0].length(); i < array[0].length() + numOfIntPart - array[0].length(); i++) {
                    tempA1[i] = '0';
                }

                char[] tempA2 = new char[numOfIntPart];
                // 调整顺序
                for (int j = 0, k = tempA1.length - 1; j < numOfIntPart && (k >= 0); j++) {
                    tempA2[j] = tempA1[k];
                    k--;
                }
                // 小数部分精度小于指定的精度
                if (numOfDecimalPart > array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        if (i < array[1].length()) {
                            tempB[i] = array[1].charAt(i);
                        } else {
                            tempB[i] = '0';
                        }
                    }
                }
                // 小数部分精度大于或等于指定的精度
                if (numOfDecimalPart <= array[1].length()) {
                    for (int i = 0; i < numOfDecimalPart; i++) {
                        tempB[i] = array[1].charAt(i);
                    }
                }
                if (numOfDecimalPart == 0) {
                    return String.valueOf(tempA2) + String.valueOf(tempB);
                }
                return String.valueOf(tempA2) + "." + String.valueOf(tempB);
            }
            // ( 情况 6 )
            if ((array[0].length() < numOfIntPart) && (array[1].length() < numOfDecimalPart)) {
                StringBuilder builder = new StringBuilder(value);
                for (int i = 0; i < numOfIntPart - array[0].length(); i++) {
                    builder.insert(0, "0");
                }
                for (int i = 0; i < numOfDecimalPart - array[1].length(); i++) {
                    builder.append("0");
                }
                return builder.toString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "adjustDouble");
        }
        return null;
    }
}