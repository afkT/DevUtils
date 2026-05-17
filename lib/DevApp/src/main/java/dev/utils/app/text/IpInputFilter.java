package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: IP 地址输入：仅数字与英文句点
 * @author Ttt
 */
public class IpInputFilter implements InputFilter {

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
                InputFilterCharUtils.isDigit(c) || c == '.'
        );
    }
}