package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 仅允许输入十六进制字符 0-9、a-f、A-F
 * @author Ttt
 */
public class HexInputFilter implements InputFilter {

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
                source, start, end, InputFilterCharUtils::isHex
        );
    }
}