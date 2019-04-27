package dev.utils.common;

import java.math.BigDecimal;

import dev.utils.JCLogUtils;

/**
 * detail: 资金运算工具类
 * @author Ttt
 * <pre>
 *      @see <a href="https://www.cnblogs.com/liqforstudy/p/5652517.html"/>
 *      向下取 round = BigDecimal.ROUND_DOWN;
 * </pre>
 */
public final class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    // 日志 TAG
    private static final String TAG = BigDecimalUtils.class.getSimpleName();
    // 默认保留位数
    private static final int DEF_DIV_SCALE = 10;

    // =

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(final double v1, final double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(final String v1, final String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "add");
        }
        return null;
    }

    // =

    /**
     * 提供精确的加法运算
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留 scale 位小数
     * @return 两个参数的和
     */
    public static double add(final double v1, final double v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.add(b2).intValue();
            } else {
                return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "add");
        }
        return 0;
    }

    /**
     * 提供精确的加法运算
     * @param v1    被加数
     * @param v2    加数
     * @param scale 保留 scale 位小数
     * @return 两个参数的和
     */
    public static String add(final String v1, final String v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return b1.add(b2).intValue() + "";
            } else {
                return b1.add(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "add");
        }
        return null;
    }

    // =

    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double substract(final double v1, final double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal substract(final String v1, final String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "substract");
        }
        return null;
    }

    // =

    /**
     * 提供精确的减法运算
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留 scale 位小数
     * @return 两个参数的差
     */
    public static String substract(final double v1, final double v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.subtract(b2).intValue() + "";
            } else {
                return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "substract");
        }
        return null;
    }

    /**
     * 提供精确的减法运算
     * @param v1    被减数
     * @param v2    减数
     * @param scale 保留 scale 位小数
     * @return 两个参数的差
     */
    public static String substract(final String v1, final String v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return b1.subtract(b2).intValue() + "";
            } else {
                return b1.subtract(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "substract");
        }
        return null;
    }

    // =

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(final double v1, final double v2) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.multiply(b2).doubleValue();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return 0d;
    }

    /**
     * 提供精确的乘法运算
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(final String v1, final String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2).doubleValue();
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的乘法运算
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留 scale 位小数
     * @return 两个参数的积
     */
    public static double multiply(final double v1, final double v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.multiply(b2).intValue();
            } else {
                return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return 0d;
    }

    // =

    /**
     * 提供精确的乘法运算
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留 scale 位小数
     * @return 两个参数的积
     */
    public static String multiply(final String v1, final String v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return b1.multiply(b2).intValue() + "";
            } else {
                return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "multiply");
        }
        return null;
    }

    // =

    /**
     * 提供(相对)精确的除法运算,当发生除不尽的情况时,
     * 精确到小数点以后10位,以后的数字四舍五入.
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(final double v1, final double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供(相对)精确的除法运算.
     * 当发生除不尽的情况时,由scale参数指 定精度,以后的数字四舍五入.
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static double divide(final double v1, final double v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            if (scale <= 0) {
                return b1.divide(b2).intValue();
            } else {
                return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "divide");
        }
        return 0d;
    }

    /**
     * 提供(相对)精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static String divide(final String v1, final String v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return b1.divide(b2).intValue() + "";
            } else {
                return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "divide");
        }
        return null;
    }

    // =

    /**
     * 提供精确的小数位四舍五入处理
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(final double v, final int scale) {
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        if (scale <= 0) {
            return b.divide(one).intValue();
        } else {
            return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    /**
     * 提供精确的小数位四舍五入处理
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(final String v, final int scale) {
        try {
            BigDecimal b = new BigDecimal(v);
            if (scale <= 0) {
                return b.intValue() + "";
            } else {
                return b.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "round");
        }
        return null;
    }

    // =

    /**
     * 提供精确的小数位获取
     * @param v            需要处理的数字
     * @param scale        小数点后保留几位
     * @param roundingMode 取小数点模式
     * @return 最后的结果
     */
    public static double round(final double v, final int scale, final int roundingMode) {
        try {
            BigDecimal b = new BigDecimal(Double.toString(v));
            BigDecimal one = new BigDecimal("1");
            if (scale <= 0) {
                return b.divide(one).intValue();
            } else {
                return b.divide(one, scale, roundingMode).doubleValue();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "round");
        }
        return 0d;
    }

    // =

    /**
     * 取余数
     * @param v1    被除数
     * @param v2    除数
     * @param scale 小数点后保留几位
     * @return 余数
     */
    public static String remainder(final String v1, final String v2, final int scale) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (scale <= 0) {
                return b1.remainder(b2).intValue() + "";
            } else {
                return b1.remainder(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "remainder");
        }
        return null;
    }

    /**
     * 取余数
     * @param v1    被除数
     * @param v2    除数
     * @param scale 小数点后保留几位
     * @return 余数
     */
    public static BigDecimal remainder(final BigDecimal v1, final BigDecimal v2, final int scale) {
        try {
            if (scale <= 0) {
                return v1.remainder(v2);
            } else {
                return v1.remainder(v2).setScale(scale, BigDecimal.ROUND_HALF_UP);
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "remainder");
        }
        return null;
    }

    // =

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return {@code true} v1 >= v2, {@code false} v1 < v2
     */
    public static Boolean compare(final String v1, final String v2) {
        try {
            return new BigDecimal(v1).compareTo(new BigDecimal(v2)) != -1;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compare");
        }
        return true;
    }

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return {@code true} v1 >= v2, {@code false} v1 < v2
     */
    public static Boolean compare(final BigDecimal v1, final BigDecimal v2) {
        try {
            return v1.compareTo(v2) != -1;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compare");
        }
        return false;
    }

    /**
     * 比较大小
     * @param v1 输入的数值
     * @param v2 被比较的数字
     * @return {@code true} v1 >= v2, {@code false} v1 < v2
     */
    public static Boolean compare(final String v1, final double v2) {
        try {
            return new BigDecimal(v1).compareTo(new BigDecimal(Double.toString(v2))) != -1;
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "compare");
        }
        return true;
    }

    // =

    /**
     * 金额分割，四舍五入金额
     * @param value 金额/数值
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value) {
        return formatMoney(value, 2, BigDecimal.ROUND_HALF_UP, 3, ",");
    }

    /**
     * 金额分割，四舍五入金额
     * @param value 金额/数值
     * @param scale 小数点后保留几位
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale) {
        return formatMoney(value, scale, BigDecimal.ROUND_HALF_UP, 3, ",");
    }

    /**
     * 金额分割，四舍五入金额
     * @param value 金额/数值
     * @param scale 小数点后保留几位
     * @param mode  处理模式
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale, final int mode) {
        return formatMoney(value, scale, mode, 3, ",");
    }

    /**
     * 金额分割，四舍五入金额
     * @param value       金额/数值
     * @param scale       小数点后保留几位
     * @param mode        处理模式
     * @param splitNumber 拆分位数
     * @return 指定格式处理的字符串
     */
    public static String formatMoney(final BigDecimal value, final int scale, final int mode, final int splitNumber) {
        return formatMoney(value, scale, mode, splitNumber, ",");
    }

    /**
     * 金额分割，四舍五入金额
     * @param value       金额/数值
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
            // 获取原始值字符串 - 非科学计数法
            String valuePlain = value.toPlainString();
            // 判断是否负数
            boolean isNegative = valuePlain.startsWith("-");
            // 处理后的数据
            BigDecimal bigDecimal = new BigDecimal(isNegative ? valuePlain.substring(1) : valuePlain);
            // 范围处理
            valuePlain = bigDecimal.setScale(scale, mode).toPlainString();
            // 进行拆分小数点处理
            String values[] = valuePlain.split("\\.");
            // 判断是否存在小数点
            boolean isDecimal = (values.length == 2);

            // 拼接符号
            String symbol = (splitSymbol != null) ? splitSymbol : "";
            // 防止出现负数
            int number = Math.max(splitNumber, 0);
            // 格式化数据 - 拼接处理
            StringBuffer buffer = new StringBuffer();
            // 进行处理小数点前的数值
            for (int len = values[0].length() - 1, i = len, splitPos = 1; i >= 0; i--) {
                // 获取数据
                char ch = values[0].charAt(i);
                buffer.append(ch); // 保存数据
                // 判断是否需要追加符号
                if (number > 0 && splitPos % number == 0 && i != 0) {
                    buffer.append(symbol);
                }
                splitPos++;
            }
            // 倒序处理
            buffer.reverse();
            // 存在小数点, 则进行拼接
            if (isDecimal) {
                buffer.append(".").append(values[1]);
            }
            // 判断是否负数
            return isNegative ? "-" + buffer.toString() : buffer.toString();
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
            // 整数部分满足精度要求(情况1)
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
            // 整数部分位数大于精度要求(情况2)
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
            // 整数部分满足精度要求(情况3)
            if (array[0].length() == numOfIntPart) {
                //直接获取整数部分长度字符
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
            // 整数部分大于精度要求(情况4)
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
            // 整数部分小于精度要求(情况5)
            if (array[0].length() < numOfIntPart) {
                // 先倒序获取指定位数的整数
                char[] tempA1 = new char[numOfIntPart];
                for (int i = array[0].length() - 1, j = 0; (i >= numOfIntPart - array[0].length() - (numOfIntPart - array[0].length())) && (j < numOfIntPart); i--, j++) {
                    tempA1[j] = array[0].charAt(i);
                }
                // 补0
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
            // 情况(6)
            if ((array[0].length() < numOfIntPart) && (array[1].length() < numOfDecimalPart)) {
                String data = value;
                for (int i = 0; i < numOfIntPart - array[0].length(); i++) {
                    data = "0" + data;
                }
                for (int i = 0; i < numOfDecimalPart - array[1].length(); i++) {
                    data = data + "0";
                }
                return data;
            }
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "adjustDouble");
        }
        return null;
    }
}
