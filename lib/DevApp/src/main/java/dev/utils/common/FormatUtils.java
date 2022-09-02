package dev.utils.common;

import dev.utils.common.format.UnitSpanFormatter;

/**
 * detail: 格式化工具类
 * @author Ttt
 */
public final class FormatUtils {

    private FormatUtils() {
    }

    // 日志 TAG
    private static final String TAG = FormatUtils.class.getSimpleName();

    // =============
    // = Unit Span =
    // =============

    /**
     * 获取 UnitSpanFormatter
     * @param precision 单位格式化精度
     * @return {@link UnitSpanFormatter}
     */
    public static UnitSpanFormatter unitSpanOf(
            final int precision
    ) {
        return UnitSpanFormatter.get(precision);
    }

    /**
     * 获取 UnitSpanFormatter
     * @param precision    单位格式化精度
     * @param defaultValue 格式化异常默认值
     * @return {@link UnitSpanFormatter}
     */
    public static UnitSpanFormatter unitSpanOf(
            final int precision,
            final String defaultValue
    ) {
        return UnitSpanFormatter.get(precision, defaultValue);
    }

    /**
     * 获取 UnitSpanFormatter
     * @param precision  单位格式化精度
     * @param appendZero 是否自动补 0 ( 只有 int、long 有效 )
     * @return {@link UnitSpanFormatter}
     */
    public static UnitSpanFormatter unitSpanOf(
            final int precision,
            final boolean appendZero
    ) {
        return UnitSpanFormatter.get(precision, appendZero);
    }

    /**
     * 获取 UnitSpanFormatter
     * @param precision    单位格式化精度
     * @param appendZero   是否自动补 0 ( 只有 int、long 有效 )
     * @param defaultValue 格式化异常默认值
     * @return {@link UnitSpanFormatter}
     */
    public static UnitSpanFormatter unitSpanOf(
            final int precision,
            final boolean appendZero,
            final String defaultValue
    ) {
        return UnitSpanFormatter.get(precision, appendZero, defaultValue);
    }
}