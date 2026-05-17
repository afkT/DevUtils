package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 禁止正整数前导零 ( 单独 {@code 0} 允许 )
 * @author Ttt
 * <pre>
 *     仅适用于纯数字输入，常与 {@link DigitsOnlyInputFilter} 组合使用。
 * </pre>
 */
public class NoLeadingZeroInputFilter
        implements InputFilter {

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
        if (TextUtils.isEmpty(result)) return null;
        if (result.length() > 1 && result.startsWith("0")) {
            return "";
        }
        return null;
    }
}
