package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止连续空格 ( 不允许在已有空格后再输入空格 )
 * @author Ttt
 */
public class NoConsecutiveSpaceInputFilter implements InputFilter {

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
        boolean lastWasSpace = dstart > 0
                && dest != null
                && Character.isWhitespace(dest.charAt(dstart - 1));
        StringBuilder builder = null;
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (Character.isWhitespace(c)) {
                if (lastWasSpace) {
                    if (builder == null) {
                        builder = new StringBuilder(end - start);
                        for (int j = start; j < i; j++) {
                            builder.append(source.charAt(j));
                        }
                    }
                } else {
                    if (builder != null) builder.append(c);
                    lastWasSpace = true;
                }
            } else {
                if (builder != null) builder.append(c);
                lastWasSpace = false;
            }
        }
        return builder == null ? null : builder;
    }
}