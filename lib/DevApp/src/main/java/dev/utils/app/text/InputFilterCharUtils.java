package dev.utils.app.text;

import java.util.regex.Pattern;

/**
 * detail: {@link android.text.InputFilter} 字符判断辅助类 ( 包内使用 )
 * @author Ttt
 */
final class InputFilterCharUtils {

    private InputFilterCharUtils() {
    }

    // Emoji 匹配 ( 常用区间 )
    private static final Pattern EMOJI_PATTERN = Pattern.compile(
            "[\\p{So}\\p{Cn}]|[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+"
    );

    /**
     * 是否为英文字母
     */
    static boolean isEnglish(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * 是否为数字
     */
    static boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 是否为中文
     */
    static boolean isChinese(final char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
                || block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || block == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || block == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 是否为十六进制字符
     */
    static boolean isHex(final char c) {
        return isDigit(c)
                || (c >= 'a' && c <= 'f')
                || (c >= 'A' && c <= 'F');
    }

    /**
     * 是否包含 Emoji
     */
    static boolean containsEmoji(final CharSequence source) {
        if (source == null || source.length() == 0) return false;
        return EMOJI_PATTERN.matcher(source).find();
    }

    /**
     * 计算字符串显示长度 ( 中文等宽字符计 2，其余计 1 )
     */
    static int getByteLength(final CharSequence text) {
        if (text == null) return 0;
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            length += isChinese(c) ? 2 : 1;
        }
        return length;
    }

    /**
     * 从 source[start,end) 过滤，仅保留通过 {@link CharPredicate} 的字符
     * @return 全部通过返回 null，部分通过返回新 {@link CharSequence}
     */
    static CharSequence filterByPredicate(
            final CharSequence source,
            final int start,
            final int end,
            final CharPredicate predicate
    ) {
        if (source == null || predicate == null) return null;
        if (start >= end) return null;
        StringBuilder builder = null;
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (predicate.test(c)) {
                if (builder != null) builder.append(c);
            } else {
                if (builder == null) {
                    builder = new StringBuilder(end - start);
                    for (int j = start; j < i; j++) {
                        builder.append(source.charAt(j));
                    }
                }
            }
        }
        return builder == null ? null : builder;
    }

    interface CharPredicate {
        boolean test(char c);
    }
}