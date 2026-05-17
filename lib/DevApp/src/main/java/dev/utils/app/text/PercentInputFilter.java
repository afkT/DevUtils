package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 百分比输入：0-100，可限制小数位，默认最多两位小数
 * @author Ttt
 */
public class PercentInputFilter
        implements InputFilter {

    private final int mDecimalDigits;

    private final DecimalInputFilter mDecimalInputFilter;

    /**
     * 小数最多 2 位
     */
    public PercentInputFilter() {
        this(2);
    }

    /**
     * 构造函数
     * @param decimalDigits 小数部分最大位数
     */
    public PercentInputFilter(final int decimalDigits) {
        mDecimalDigits = Math.max(0, decimalDigits);
        mDecimalInputFilter = new DecimalInputFilter(3, mDecimalDigits);
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
            if (value > 100D) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return decimalResult;
    }
}
