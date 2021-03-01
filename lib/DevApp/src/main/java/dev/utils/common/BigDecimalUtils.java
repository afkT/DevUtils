package dev.utils.common;

import java.math.BigDecimal;

import dev.utils.JCLogUtils;

/**
 * detail: 资金运算工具类
 * @author Ttt
 * <pre>
 *     @see <a href="https://www.cnblogs.com/liqforstudy/p/5652517.html"/>
 *     @see <a href="https://blog.csdn.net/mo_cold_rain/article/details/81366310"/>
 * </pre>
 */
public final class BigDecimalUtils {

    private BigDecimalUtils() {
    }

    // 日志 TAG
    private static final String TAG = BigDecimalUtils.class.getSimpleName();

    // 小数点位数
    private static int NEW_SCALE     = 10;
    // 舍入模式 ( 默认向下取 )
    private static int ROUNDING_MODE = BigDecimal.ROUND_DOWN;

    /**
     * 设置全局小数点保留位数、舍入模式
     * @param scale        小数点保留位数
     * @param roundingMode 舍入模式
     */
    public static void setScale(
            final int scale,
            final int roundingMode
    ) {
        NEW_SCALE = scale;
        ROUNDING_MODE = roundingMode;
    }

    // =

    /**
     * 获取 BigDecimal
     * @param value Value
     * @return {@link BigDecimal}
     */
    public static BigDecimal getBigDecimal(final double value) {
        return new BigDecimal(value);
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
     * 获取 Operation
     * @param value Value
     * @return {@link Operation}
     */
    public static Operation operation(final double value) {
        return new Operation(value);
    }

    /**
     * 获取 Operation
     * @param value Value
     * @return {@link Operation}
     */
    public static Operation operation(final String value) {
        return new Operation(value);
    }

    /**
     * 获取 Operation
     * @param value Value
     * @return {@link Operation}
     */
    public static Operation operation(final BigDecimal value) {
        return new Operation(value);
    }

    // =========
    // = 包装类 =
    // =========

    /**
     * detail: 配置信息
     * @author Ttt
     */
    public static final class Config {

        // 小数点位数
        private final int mScale;
        // 舍入模式
        private final int mRoundingMode;

        public Config() {
            this(NEW_SCALE, ROUNDING_MODE);
        }

        /**
         * 初始化小数点保留位数、舍入模式
         * @param scale        小数点保留位数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Config(
                final int scale,
                final int roundingMode
        ) {
            this.mScale = scale;
            this.mRoundingMode = roundingMode;
        }

        /**
         * 获取小数点保留位数
         * @return 小数点保留位数
         */
        public int getScale() {
            return mScale;
        }

        /**
         * 获取舍入模式
         * @return 舍入模式
         */
        public int getRoundingMode() {
            return mRoundingMode;
        }
    }

    /**
     * detail: BigDecimal 操作包装类
     * @author Ttt
     */
    public static final class Operation {

        // 计算数值
        private BigDecimal mValue;
        // 配置信息
        private Config     mConfig;

        public Operation(final BigDecimal value) {
            this.mValue = value;
        }

        public Operation(final double value) {
            this.mValue = new BigDecimal(value);
        }

        public Operation(final String value) {
            try {
                this.mValue = new BigDecimal(value);
            } catch (Exception e) {
                JCLogUtils.eTag(TAG, e, "Operation");
            }
        }

        public Operation(
                final BigDecimal value,
                final Config config
        ) {
            this.mValue = value;
            this.mConfig = config;
        }

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取 Value
         * @return {@link BigDecimal}
         */
        public BigDecimal getBigDecimal() {
            return mValue;
        }

        /**
         * 设置 Value
         * @param value {@link BigDecimal}
         * @return {@link Operation}
         */
        public Operation setBigDecimal(final BigDecimal value) {
            this.mValue = value;
            return this;
        }

        /**
         * 获取配置信息
         * @return {@link Config}
         */
        public Config getConfig() {
            return mConfig;
        }

        /**
         * 设置配置信息
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation setConfig(final Config config) {
            return setConfig(config, true);
        }

        /**
         * 设置配置信息
         * @param config {@link Config}
         * @param set    是否进行设置
         * @return {@link Operation}
         */
        public Operation setConfig(
                final Config config,
                final boolean set
        ) {
            this.mConfig = config;
            if (set) setScaleByConfig();
            return this;
        }

        /**
         * 移除配置信息
         * @return {@link Operation}
         */
        public Operation removeConfig() {
            return setConfig(null, false);
        }

        // =

        /**
         * 设置小数点保留位数、舍入模式
         * @param scale        小数点保留位数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Operation setScale(
                final int scale,
                final int roundingMode
        ) {
            return setScale(new Config(scale, roundingMode));
        }

        /**
         * 设置小数点保留位数、舍入模式
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation setScale(final Config config) {
            if (mValue != null && config != null) {
                try {
                    mValue = mValue.setScale(
                            config.getScale(),
                            config.getRoundingMode()
                    );
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "setScale");
                }
            }
            return this;
        }

        /**
         * 设置小数点保留位数、舍入模式
         * @return {@link Operation}
         */
        public Operation setScaleByConfig() {
            return setScale(mConfig);
        }

        // ===========
        // = 获取方法 =
        // ===========

        /**
         * 克隆对象
         * @return {@link Operation}
         */
        public Operation clone() {
            return new Operation(mValue, mConfig);
        }

        /**
         * 获取此 BigDecimal 的字符串表示形式科学记数法
         * @return 此 BigDecimal 的字符串表示形式科学记数法
         */
        public String toString() {
            return (mValue != null) ? mValue.toString() : null;
        }

        /**
         * 获取此 BigDecimal 的字符串表示形式不带指数字段
         * @return 此 BigDecimal 的字符串表示形式不带指数字段
         */
        public String toPlainString() {
            return (mValue != null) ? mValue.toPlainString() : null;
        }

        /**
         * 获取此 BigDecimal 的字符串表示形式工程计数法
         * @return 此 BigDecimal 的字符串表示形式工程计数法
         */
        public String toEngineeringString() {
            return (mValue != null) ? mValue.toEngineeringString() : null;
        }

        /**
         * 获取指定类型值
         * @return 指定类型值
         */
        public int intValue() {
            return (mValue != null) ? mValue.intValue() : 0;
        }

        /**
         * 获取指定类型值
         * @return 指定类型值
         */
        public float floatValue() {
            return (mValue != null) ? mValue.floatValue() : 0F;
        }

        /**
         * 获取指定类型值
         * @return 指定类型值
         */
        public long longValue() {
            return (mValue != null) ? mValue.longValue() : 0L;
        }

        /**
         * 获取指定类型值
         * @return 指定类型值
         */
        public double doubleValue() {
            return (mValue != null) ? mValue.doubleValue() : 0D;
        }

        // ======
        // = 加 =
        // ======

        /**
         * 提供精确的加法运算
         * @param value 加数
         * @return {@link Operation}
         */
        public Operation add(final double value) {
            return add(new BigDecimal(value));
        }

        /**
         * 提供精确的加法运算
         * @param value 加数
         * @return {@link Operation}
         */
        public Operation add(final String value) {
            return add(BigDecimalUtils.getBigDecimal(value));
        }

        /**
         * 提供精确的加法运算
         * @param value 加数
         * @return {@link Operation}
         */
        public Operation add(final BigDecimal value) {
            if (mValue != null && value != null) {
                mValue = mValue.add(value);
            }
            return this;
        }

        // ======
        // = 减 =
        // ======

        /**
         * 提供精确的减法运算
         * @param value 减数
         * @return {@link Operation}
         */
        public Operation subtract(final double value) {
            return subtract(new BigDecimal(value));
        }

        /**
         * 提供精确的减法运算
         * @param value 减数
         * @return {@link Operation}
         */
        public Operation subtract(final String value) {
            return subtract(BigDecimalUtils.getBigDecimal(value));
        }

        /**
         * 提供精确的减法运算
         * @param value 减数
         * @return {@link Operation}
         */
        public Operation subtract(final BigDecimal value) {
            if (mValue != null && value != null) {
                mValue = mValue.subtract(value);
            }
            return this;
        }

        // ======
        // = 乘 =
        // ======

        /**
         * 提供精确的乘法运算
         * @param value 乘数
         * @return {@link Operation}
         */
        public Operation multiply(final double value) {
            return multiply(new BigDecimal(value));
        }

        /**
         * 提供精确的乘法运算
         * @param value 乘数
         * @return {@link Operation}
         */
        public Operation multiply(final String value) {
            return multiply(BigDecimalUtils.getBigDecimal(value));
        }

        /**
         * 提供精确的乘法运算
         * @param value 乘数
         * @return {@link Operation}
         */
        public Operation multiply(final BigDecimal value) {
            if (mValue != null && value != null) {
                mValue = mValue.multiply(value);
            }
            return this;
        }

        // ======
        // = 除 =
        // ======

        /**
         * 提供精确的除法运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation divide(final double value) {
            return divide(new BigDecimal(value), mConfig);
        }

        /**
         * 提供精确的除法运算
         * @param value  除数
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation divide(
                final double value,
                final Config config
        ) {
            return divide(new BigDecimal(value), config);
        }

        /**
         * 提供精确的除法运算
         * @param value        除数
         * @param scale        保留 scale 位小数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Operation divide(
                final double value,
                final int scale,
                final int roundingMode
        ) {
            return divide(new BigDecimal(value), scale, roundingMode);
        }

        // =

        /**
         * 提供精确的除法运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation divide(final String value) {
            return divide(new BigDecimal(value), mConfig);
        }

        /**
         * 提供精确的除法运算
         * @param value  除数
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation divide(
                final String value,
                final Config config
        ) {
            return divide(new BigDecimal(value), config);
        }

        /**
         * 提供精确的除法运算
         * @param value        除数
         * @param scale        保留 scale 位小数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Operation divide(
                final String value,
                final int scale,
                final int roundingMode
        ) {
            return divide(new BigDecimal(value), scale, roundingMode);
        }

        // =

        /**
         * 提供精确的除法运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation divide(final BigDecimal value) {
            return divide(value, mConfig);
        }

        /**
         * 提供精确的除法运算
         * @param value  除数
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation divide(
                final BigDecimal value,
                final Config config
        ) {
            if (config != null) {
                return divide(value, config.getScale(), config.getRoundingMode());
            } else {
                return divide(value, NEW_SCALE, ROUNDING_MODE);
            }
        }

        /**
         * 提供精确的除法运算
         * @param value        除数
         * @param scale        保留 scale 位小数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Operation divide(
                final BigDecimal value,
                final int scale,
                final int roundingMode
        ) {
            if (mValue != null && value != null) {
                try {
                    if (scale <= 0) {
                        mValue = mValue.divide(value);
                    } else {
                        mValue = mValue.divide(value, scale, roundingMode);
                    }
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "divide");
                }
            }
            return this;
        }

        // ========
        // = 取余 =
        // ========

        /**
         * 提供精确的取余运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation remainder(final double value) {
            return remainder(new BigDecimal(value));
        }

        /**
         * 提供精确的取余运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation remainder(final String value) {
            return remainder(BigDecimalUtils.getBigDecimal(value));
        }

        /**
         * 提供精确的取余运算
         * @param value 除数
         * @return {@link Operation}
         */
        public Operation remainder(final BigDecimal value) {
            if (mValue != null && value != null) {
                mValue = mValue.remainder(value);
            }
            return this;
        }

        // ===========
        // = 四舍五入 =
        // ===========

        /**
         * 提供精确的小数位四舍五入处理
         * @return {@link Operation}
         */
        public Operation round() {
            return round(mConfig);
        }

        /**
         * 提供精确的小数位四舍五入处理
         * @param config {@link Config}
         * @return {@link Operation}
         */
        public Operation round(final Config config) {
            return divide(new BigDecimal(1D), config);
        }

        /**
         * 提供精确的小数位四舍五入处理
         * @param scale        保留 scale 位小数
         * @param roundingMode 舍入模式
         * @return {@link Operation}
         */
        public Operation round(
                final int scale,
                final int roundingMode
        ) {
            return divide(new BigDecimal(1D), scale, roundingMode);
        }

        // ===========
        // = 比较大小 =
        // ===========

        /**
         * 比较大小
         * @param value 被比较的数字
         * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
         */
        public int compareTo(final double value) {
            return compareTo(new BigDecimal(value));
        }

        /**
         * 比较大小
         * @param value 被比较的数字
         * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
         */
        public int compareTo(final String value) {
            return compareTo(BigDecimalUtils.getBigDecimal(value));
        }

        /**
         * 比较大小
         * @param value 被比较的数字
         * @return [1 = v1 > v2]、[-1 = v1 < v2]、[0 = v1 = v2]、[-2 = error]
         */
        public int compareTo(final BigDecimal value) {
            if (mValue != null && value != null) {
                try {
                    return mValue.compareTo(value);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "compareTo");
                }
            }
            return -2;
        }

        // ===========
        // = 金额分割 =
        // ===========

        /**
         * 金额分割, 四舍五入金额
         * @return 指定格式处理的字符串
         */
        public String formatMoney() {
            return formatMoney(mConfig);
        }

        /**
         * 金额分割, 四舍五入金额
         * @param splitNumber 拆分位数
         * @param splitSymbol 拆分符号
         * @return 指定格式处理的字符串
         */
        public String formatMoney(
                final int splitNumber,
                final String splitSymbol
        ) {
            return formatMoney(mConfig, splitNumber, splitSymbol);
        }

        // =

        /**
         * 金额分割, 四舍五入金额
         * @param config {@link Config}
         * @return 指定格式处理的字符串
         */
        public String formatMoney(final Config config) {
            return formatMoney(config, 3, ",");
        }

        /**
         * 金额分割, 四舍五入金额
         * @param config      {@link Config}
         * @param splitNumber 拆分位数
         * @param splitSymbol 拆分符号
         * @return 指定格式处理的字符串
         */
        public String formatMoney(
                final Config config,
                final int splitNumber,
                final String splitSymbol
        ) {
            if (config != null) {
                return formatMoney(config.getScale(), config.getRoundingMode(), splitNumber, splitSymbol);
            } else {
                return formatMoney(NEW_SCALE, ROUNDING_MODE, splitNumber, splitSymbol);
            }
        }

        /**
         * 金额分割, 四舍五入金额
         * @param scale       小数点后保留几位
         * @param mode        处理模式
         * @param splitNumber 拆分位数
         * @param splitSymbol 拆分符号
         * @return 指定格式处理的字符串
         */
        public String formatMoney(
                final int scale,
                final int mode,
                final int splitNumber,
                final String splitSymbol
        ) {
            if (mValue == null) return null;
            try {
                // 如果等于 0, 直接返回
                if (mValue.doubleValue() == 0) {
                    return mValue.setScale(scale, mode).toPlainString();
                }
                // 获取原始值字符串 ( 非科学计数法 )
                String valuePlain = mValue.toPlainString();
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
    }

    // ====================
    // = 获取指定格式字符串 =
    // ====================

    /**
     * 获取自己想要的数据格式
     * @param value            需处理的数据
     * @param numOfIntPart     整数位数
     * @param numOfDecimalPart 小数位数
     * @return 处理过的数据
     */
    public static String adjustDouble(
            final String value,
            final int numOfIntPart,
            final int numOfDecimalPart
    ) {
        if (value == null) return null;
        try {
            // 按小数点的位置分割成整数部分和小数部分
            String[] array = value.split("\\.");
            char[]   tempA = new char[numOfIntPart];
            char[]   tempB = new char[numOfDecimalPart];
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