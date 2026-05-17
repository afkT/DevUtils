package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止输入 Emoji 表情
 * @author Ttt
 */
public class EmojiInputFilter implements InputFilter {

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
        StringBuilder builder = null;
        for (int i = start; i < end; ) {
            int codePoint = Character.codePointAt(source, i);
            int charCount = Character.charCount(codePoint);
            CharSequence sub = source.subSequence(i, i + charCount);
            if (InputFilterCharUtils.containsEmoji(sub)) {
                if (builder == null) {
                    builder = new StringBuilder(end - start);
                    for (int j = start; j < i; j++) {
                        builder.append(source.charAt(j));
                    }
                }
            } else {
                if (builder != null) {
                    builder.append(sub);
                }
            }
            i += charCount;
        }
        return builder == null ? null : builder;
    }
}