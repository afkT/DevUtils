package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止输入任何空白字符 ( 空格、制表符等 )
 * @author Ttt
 */
public class NoSpaceInputFilter implements InputFilter {

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
                source, start, end, c -> !Character.isWhitespace(c)
        );
    }
}