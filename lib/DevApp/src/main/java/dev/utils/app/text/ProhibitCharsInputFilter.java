package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * detail: 禁止输入指定字符 ( 黑名单 )
 * @author Ttt
 */
public class ProhibitCharsInputFilter implements InputFilter {

    private final String mProhibitChars;

    /**
     * 构造函数
     * @param prohibitChars 禁止出现的字符集合
     */
    public ProhibitCharsInputFilter(final String prohibitChars) {
        mProhibitChars = prohibitChars;
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
        if (TextUtils.isEmpty(mProhibitChars) || source == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mProhibitChars.indexOf(c) < 0
        );
    }
}