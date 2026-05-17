package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 数值下限：解析后不得低于指定最小值
 * @author Ttt
 * <pre>
 *     仅适用于数字与小数点组成的输入。
 * </pre>
 */
public class MinValueInputFilter
        implements InputFilter {

    private final double mMinValue;

    /**
     * 构造函数
     * @param minValue 允许的最小值
     */
    public MinValueInputFilter(final double minValue) {
        mMinValue = minValue;
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
        if (source == null) return null;
        String result = InputFilterCharUtils.mergeInput(source, start, end, dest, dstart, dend);
        if (TextUtils.isEmpty(result) || ".".equals(result) || "-".equals(result)) return null;
        try {
            double value = Double.parseDouble(result);
            if (value < mMinValue) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return null;
    }
}
