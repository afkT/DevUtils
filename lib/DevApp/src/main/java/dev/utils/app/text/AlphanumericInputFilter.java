package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 仅允许输入英文字母与数字
 * @author Ttt
 */
public class AlphanumericInputFilter implements InputFilter {

    @Override
    public CharSequence filter(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend
    ) {
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                InputFilterCharUtils.isEnglish(c) || InputFilterCharUtils.isDigit(c)
        );
    }
}