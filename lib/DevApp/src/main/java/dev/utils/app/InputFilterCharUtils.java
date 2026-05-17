package dev.utils.app;

import java.util.regex.Pattern;

/**
 * detail: InputFilter 字符判断辅助类
 * @author Ttt
 * <pre>
 *     仅供 {@code dev.utils.app.text} 包内 {@link android.text.InputFilter} 实现复用。
 * </pre>
 */
public final class InputFilterCharUtils {

    private InputFilterCharUtils() {
    }

    // Emoji 匹配 ( 常用区间 )
    private static final Pattern EMOJI_PATTERN = Pattern.compile(
            "[\\p{So}\\p{Cn}]|[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+"
    );

    /**
     * 判断字符是否为英文字母
     * @param c 待判断字符
     * @return {@code true} 为 a-z 或 A-Z，{@code false} 非英文字母
     */
    public static boolean isEnglish(final char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    /**
     * 判断字符是否为数字
     * @param c 待判断字符
     * @return {@code true} 为 0-9，{@code false} 非数字
     */
    public static boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * 判断字符是否为中文或 CJK 相关字符
     * @param c 待判断字符
     * @return {@code true} 属于 CJK 区块，{@code false} 不属于
     */
    public static boolean isChinese(final char c) {
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
     * 判断字符是否为十六进制字符
     * @param c 待判断字符
     * @return {@code true} 为 0-9 或 a-f、A-F，{@code false} 非十六进制字符
     */
    public static boolean isHex(final char c) {
        return isDigit(c)
                || (c >= 'a' && c <= 'f')
                || (c >= 'A' && c <= 'F');
    }

    /**
     * 判断文本是否包含 Emoji
     * @param source 待检测文本
     * @return {@code true} 包含 Emoji，{@code false} 不包含或为空
     */
    public static boolean containsEmoji(final CharSequence source) {
        if (source == null || source.length() == 0) return false;
        return EMOJI_PATTERN.matcher(source).find();
    }

    /**
     * 计算字符串显示字节长度
     * <pre>
     *     中文等 CJK 字符计 2，其余计 1。
     * </pre>
     * @param text 待计算文本
     * @return 显示字节长度，null 入参返回 0
     */
    public static int getByteLength(final CharSequence text) {
        if (text == null) return 0;
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            length += isChinese(c) ? 2 : 1;
        }
        return length;
    }

    /**
     * 按谓词过滤指定区间字符
     * <pre>
     *     全部通过返回 null；部分剔除时返回新 {@link CharSequence}。
     * </pre>
     * @param source    源文本
     * @param start     区间起始 ( 含 )
     * @param end       区间结束 ( 不含 )
     * @param predicate 字符谓词
     * @return 过滤结果，全部通过或无效入参为 null
     */
    public static CharSequence filterByPredicate(
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

    public interface CharPredicate {
        boolean test(char c);
    }
}