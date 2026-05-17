package dev.utils.app;

import android.text.Spanned;

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
     * 判断字符是否为邮箱常用字符
     * @param c 待判断字符
     * @return {@code true} 为字母、数字或 {@code @._+-}，{@code false} 否则
     */
    public static boolean isEmailChar(final char c) {
        return isEnglish(c)
                || isDigit(c)
                || c == '@'
                || c == '.'
                || c == '_'
                || c == '-'
                || c == '+';
    }

    /**
     * 判断字符是否为可打印 ASCII
     * @param c 待判断字符
     * @return {@code true} 为 ASCII 32-126，{@code false} 超出该范围
     */
    public static boolean isPrintableAscii(final char c) {
        return c >= 32 && c <= 126;
    }

    /**
     * 判断字符是否为日期输入常用字符
     * @param c 待判断字符
     * @return {@code true} 为数字或 {@code -}、{@code /}、{@code .}，{@code false} 否则
     */
    public static boolean isDateChar(final char c) {
        return isDigit(c) || c == '-' || c == '/' || c == '.';
    }

    /**
     * 判断字符是否为 MAC 地址输入常用字符
     * @param c 待判断字符
     * @return {@code true} 为十六进制或 {@code :}、{@code -}，{@code false} 否则
     */
    public static boolean isMacAddressChar(final char c) {
        return isHex(c) || c == ':' || c == '-';
    }

    /**
     * 判断字符是否为车牌字母 ( 不含 I、O )
     * @param c 待判断字符
     * @return {@code true} 为 A-HJ-NP-Z，{@code false} 否则
     */
    public static boolean isPlateLetter(final char c) {
        char upper = Character.toUpperCase(c);
        return upper >= 'A' && upper <= 'Z' && upper != 'I' && upper != 'O';
    }

    /**
     * 判断字符是否为车牌序号字符 ( 数字或车牌字母 )
     * @param c 待判断字符
     * @return {@code true} 允许，{@code false} 不允许
     */
    public static boolean isPlateSerialChar(final char c) {
        return isDigit(c) || isPlateLetter(c);
    }

    /**
     * 判断字符是否为中文姓名常用字符
     * @param c 待判断字符
     * @return {@code true} 为中文或间隔号 {@code ·}，{@code false} 否则
     */
    public static boolean isChineseNameChar(final char c) {
        return isChinese(c) || c == '·' || c == '•';
    }

    /**
     * 判断字符是否为国内地址常用字符
     * @param c 待判断字符
     * @return {@code true} 为中文、数字或常见地址符号，{@code false} 否则
     */
    public static boolean isChineseAddressChar(final char c) {
        return isChinese(c)
                || isDigit(c)
                || c == '-'
                || c == '#'
                || c == '('
                || c == ')'
                || c == '（'
                || c == '）';
    }

    /**
     * 判断字符是否为 VIN 字符 ( 不含 I、O、Q )
     * @param c 待判断字符
     * @return {@code true} 允许，{@code false} 不允许
     */
    public static boolean isVinChar(final char c) {
        char upper = Character.toUpperCase(c);
        if (isDigit(c)) return true;
        if (upper >= 'A' && upper <= 'Z') {
            return upper != 'I' && upper != 'O' && upper != 'Q';
        }
        return false;
    }

    /**
     * 按位规则过滤输入 ( 常用于证件号、车牌等固定位数场景 )
     * @param source       新输入内容
     * @param start        新输入起始下标
     * @param end          新输入结束下标，不含
     * @param dest         已有文本
     * @param dstart       替换区间起始
     * @param dend         替换区间结束，不含
     * @param maxLength    最大长度
     * @param positionRule 按位字符规则
     * @return 过滤后的替换内容，null 表示接受原输入
     */
    public static CharSequence filterByPosition(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend,
            final int maxLength,
            final PositionCharPredicate positionRule
    ) {
        return filterByPosition(source, start, end, dest, dstart, dend, maxLength, null, positionRule);
    }

    /**
     * 按位规则过滤输入，可先按字符集剔除非法字符
     * @param source        新输入内容
     * @param start         新输入起始下标
     * @param end           新输入结束下标，不含
     * @param dest          已有文本
     * @param dstart        替换区间起始
     * @param dend          替换区间结束，不含
     * @param maxLength     最大长度
     * @param preCharFilter 字符集预过滤，null 表示不预过滤
     * @param positionRule  按位字符规则
     * @return 过滤后的替换内容，null 表示接受原输入
     */
    public static CharSequence filterByPosition(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend,
            final int maxLength,
            final CharPredicate preCharFilter,
            final PositionCharPredicate positionRule
    ) {
        if (source == null || positionRule == null) return null;
        String destStr = dest == null ? "" : dest.toString();
        String before  = destStr.substring(0, dstart);
        String after   = destStr.substring(dend);
        if (before.length() + after.length() >= maxLength) {
            return "";
        }
        CharSequence allowedChars = preCharFilter == null
                ? null
                : filterByPredicate(source, start, end, preCharFilter);
        CharSequence working   = source;
        int          workStart = start;
        int          workEnd   = end;
        if (allowedChars != null) {
            working   = allowedChars;
            workStart = 0;
            workEnd   = allowedChars.length();
        }
        StringBuilder insert = new StringBuilder(workEnd - workStart);
        for (int i = workStart; i < workEnd; i++) {
            int index = before.length() + insert.length();
            if (index >= maxLength) break;
            char c = working.charAt(i);
            if (positionRule.isAllowedAt(c, index)) {
                insert.append(c);
            }
        }
        if (insert.length() == workEnd - workStart
                && before.length() + insert.length() + after.length() <= maxLength) {
            return allowedChars;
        }
        if (insert.length() == 0) return "";
        return insert;
    }

    /**
     * 按位字符是否允许
     */
    public interface PositionCharPredicate {

        /**
         * 判断字符在指定下标是否允许
         * @param c     字符
         * @param index 在完整文本中的下标
         * @return {@code true} 允许，{@code false} 不允许
         */
        boolean isAllowedAt(
                char c,
                int index
        );
    }

    /**
     * 合并本次输入与已有文本
     * @param source 新输入内容
     * @param start  新输入起始下标
     * @param end    新输入结束下标，不含
     * @param dest   已有文本
     * @param dstart 替换区间起始
     * @param dend   替换区间结束，不含
     * @return 合并后的完整字符串
     */
    public static String mergeInput(
            final CharSequence source,
            final int start,
            final int end,
            final Spanned dest,
            final int dstart,
            final int dend
    ) {
        String destStr = dest == null ? "" : dest.toString();
        if (source == null) return destStr;
        return destStr.substring(0, dstart)
                + source.subSequence(start, end)
                + destStr.substring(dend);
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

    /**
     * 单字符谓词
     */
    public interface CharPredicate {

        /**
         * 测试字符是否满足条件
         * @param c 字符
         * @return {@code true} 通过，{@code false} 不通过
         */
        boolean test(char c);
    }
}