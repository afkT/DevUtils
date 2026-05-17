package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止输入中文 ( 含 CJK 字符 )
 * @author Ttt
 */
public class NoChineseInputFilter implements InputFilter {

    @Override
    public CharSequence filter(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend
    ) {
        return InputFilterCharUtils.filterByPredicate(
                source, start, end, c -> !InputFilterCharUtils.isChinese(c)
        );
    }
}