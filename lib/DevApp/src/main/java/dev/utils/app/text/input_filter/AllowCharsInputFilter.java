package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 仅允许输入指定字符 ( 白名单 )
 * @author Ttt
 */
public class AllowCharsInputFilter
        implements InputFilter {

    private final String mAllowChars;

    /**
     * 构造函数
     * @param allowChars 允许出现的字符集合
     */
    public AllowCharsInputFilter(final String allowChars) {
        mAllowChars = allowChars;
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
        if (TextUtils.isEmpty(mAllowChars) || source == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mAllowChars.indexOf(c) >= 0
        );
    }
}