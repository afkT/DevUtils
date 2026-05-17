package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 禁止输入指定字符 ( 黑名单 )
 * @author Ttt
 */
public class ProhibitCharsInputFilter
        implements InputFilter {

    private final String mProhibitChars;

    /**
     * 构造函数
     * @param prohibitChars 禁止出现的字符集合
     */
    public ProhibitCharsInputFilter(final String prohibitChars) {
        mProhibitChars = prohibitChars;
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
        if (TextUtils.isEmpty(mProhibitChars) || source == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mProhibitChars.indexOf(c) < 0
        );
    }
}