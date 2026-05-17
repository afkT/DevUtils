package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 身高 ( cm ) 输入，默认范围 50-250
 * @author Ttt
 */
public class HeightCmInputFilter
        implements InputFilter {

    private final double mMinValue;
    private final double mMaxValue;

    private final IntegerInputFilter mIntegerInputFilter;

    /**
     * 默认 50-250 cm
     */
    public HeightCmInputFilter() {
        this(50, 250);
    }

    /**
     * 构造函数
     * @param minValue 最小身高 ( cm )
     * @param maxValue 最大身高 ( cm )
     */
    public HeightCmInputFilter(
            final double minValue,
            final double maxValue
    ) {
        mMinValue           = Math.min(minValue, maxValue);
        mMaxValue           = Math.max(minValue, maxValue);
        mIntegerInputFilter = new IntegerInputFilter(3);
    }

    /**
     * 过滤本次输入片段
     * @param source 新输入内容
     * @param start  新输入起始下标
     * @param end    新输入结束下标，不含
     * @param dest   已有文本
     * @param dstart 替换区间起始
     * @param dend   替换区间结束，不含
     * @return 过滤后的替换内容，null 表示接受原输入
     */
    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned dest,
            int dstart,
            int dend
    ) {
        CharSequence integerResult = mIntegerInputFilter.filter(
                source, start, end, dest, dstart, dend
        );
        if (integerResult != null && integerResult.length() == 0) {
            return "";
        }
        if (source == null) return integerResult;
        String result = InputFilterCharUtils.mergeInput(source, start, end, dest, dstart, dend);
        if (TextUtils.isEmpty(result)) return integerResult;
        try {
            double value = Double.parseDouble(result);
            if (value < mMinValue || value > mMaxValue) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return integerResult;
    }
}