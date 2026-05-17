package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * detail: 仅允许输入指定字符 ( 白名单 )
 * @author Ttt
 */
public class AllowCharsInputFilter implements InputFilter {

    private final String mAllowChars;

    /**
     * 构造函数
     * @param allowChars 允许出现的字符集合
     */
    public AllowCharsInputFilter(final String allowChars) {
        mAllowChars = allowChars;
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
        if (TextUtils.isEmpty(mAllowChars) || source == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mAllowChars.indexOf(c) >= 0
        );
    }
}