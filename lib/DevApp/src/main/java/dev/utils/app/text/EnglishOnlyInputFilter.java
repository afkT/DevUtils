package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 仅允许输入英文字母 a-z、A-Z
 * @author Ttt
 */
public class EnglishOnlyInputFilter implements InputFilter {

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
                source, start, end, InputFilterCharUtils::isEnglish
        );
    }
}