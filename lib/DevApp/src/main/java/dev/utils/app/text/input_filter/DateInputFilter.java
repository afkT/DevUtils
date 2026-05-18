package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 日期输入：数字与常见分隔符，常用于 yyyy-MM-dd 等格式
 * @author Ttt
 * <pre>
 *     允许字符包含 {@code -}、{@code /}、{@code .}。
 * </pre>
 */
public class DateInputFilter
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
        return InputFilterCharUtils.filterByPredicate(
                source, start, end, InputFilterCharUtils::isDateChar
        );
    }
}