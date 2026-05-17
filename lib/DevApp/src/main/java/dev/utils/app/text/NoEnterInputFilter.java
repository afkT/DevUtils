package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止回车、换行输入
 * @author Ttt
 */
public class NoEnterInputFilter implements InputFilter {

    @Override
    public CharSequence filter(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend
    ) {
        if (source == null) return null;
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (c == '\n' || c == '\r') {
                return "";
            }
        }
        return null;
    }
}