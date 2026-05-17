package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 仅允许输入中文 ( 含中文标点等 CJK 字符 )
 * @author Ttt
 */
public class ChineseOnlyInputFilter implements InputFilter {

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
                source, start, end, InputFilterCharUtils::isChinese
        );
    }
}