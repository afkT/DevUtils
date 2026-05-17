package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * detail: 数值上限：输入内容按数字解析后不得超过指定最大值 ( 仅数字与小数点 )
 * @author Ttt
 */
public class MaxValueInputFilter implements InputFilter {

    private final double mMaxValue;

    /**
     * 构造函数
     * @param maxValue 允许的最大值
     */
    public MaxValueInputFilter(final double maxValue) {
        mMaxValue = maxValue;
    }

    @Override
    public CharSequence filter(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend
    ) {
        if (source == null) return null;
        String destStr = dest == null ? "" : dest.toString();
        String result = destStr.substring(0, dstart)
                + source.subSequence(start, end)
                + destStr.substring(dend);
        if (TextUtils.isEmpty(result) || ".".equals(result)) return null;
        try {
            double value = Double.parseDouble(result);
            if (value > mMaxValue) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return null;
    }
}