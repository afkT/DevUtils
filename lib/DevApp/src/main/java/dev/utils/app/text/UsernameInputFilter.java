package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 用户名输入：英文字母、数字、下划线
 * @author Ttt
 */
public class UsernameInputFilter implements InputFilter {

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
                InputFilterCharUtils.isEnglish(c)
                        || InputFilterCharUtils.isDigit(c)
                        || c == '_'
        );
    }
}