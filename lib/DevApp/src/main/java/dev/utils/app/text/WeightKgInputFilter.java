package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 体重 ( kg ) 输入，默认范围 1-500，最多一位小数
 * @author Ttt
 */
public class WeightKgInputFilter
        implements InputFilter {

    private final double mMinValue;
    private final double mMaxValue;

    private final DecimalInputFilter mDecimalInputFilter;

    /**
     * 默认 1-500 kg，一位小数
     */
    public WeightKgInputFilter() {
        this(1, 500);
    }

    /**
     * 构造函数
     * @param minValue 最小体重 ( kg )
     * @param maxValue 最大体重 ( kg )
     */
    public WeightKgInputFilter(
            final double minValue,
            final double maxValue
    ) {
        mMinValue           = Math.min(minValue, maxValue);
        mMaxValue           = Math.max(minValue, maxValue);
        mDecimalInputFilter = new DecimalInputFilter(3, 1);
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
        CharSequence decimalResult = mDecimalInputFilter.filter(
                source, start, end, dest, dstart, dend
        );
        if (decimalResult != null && decimalResult.length() == 0) {
            return "";
        }
        if (source == null) return decimalResult;
        String result = InputFilterCharUtils.mergeInput(source, start, end, dest, dstart, dend);
        if (TextUtils.isEmpty(result) || ".".equals(result)) return decimalResult;
        try {
            double value = Double.parseDouble(result);
            if (value < mMinValue || value > mMaxValue) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return decimalResult;
    }
}