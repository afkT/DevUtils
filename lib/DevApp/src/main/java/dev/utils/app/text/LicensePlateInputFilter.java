package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 中国大陆民用车牌号输入
 * @author Ttt
 * <pre>
 *     普通车牌 7 位：省份汉字 + 字母 ( 不含 I、O ) + 5 位序号。
 *     新能源车牌 8 位：省份汉字 + 字母 + 6 位序号。
 *     字母自动转大写，序号位不含 I、O。
 * </pre>
 */
public class LicensePlateInputFilter
        implements InputFilter {

    private static final int MAX_LENGTH = 8;

    /**
     * 过滤本次输入片段
     * @param source 新输入内容
     * @param start  新输入起始下标
     * @param end    新输入结束下标，不含
     * @param dest   已有文本
     * @param dstart 替换区间起始
     * @param dend   替换区间结束，不含
     * @return 过滤后的替换内容，null 表示接受原输入
     */
    @Override
    public CharSequence filter(
            CharSequence source,
            int start,
            int end,
            Spanned dest,
            int dstart,
            int dend
    ) {
        CharSequence result = InputFilterCharUtils.filterByPosition(
                source, start, end, dest, dstart, dend, MAX_LENGTH,
                LicensePlateInputFilter::isAllowedAt
        );
        return normalizePlateLetters(result, source, start, end);
    }

    /**
     * 判断字符在指定下标是否允许
     * @param c     字符
     * @param index 在完整车牌中的下标
     * @return {@code true} 允许，{@code false} 不允许
     */
    private static boolean isAllowedAt(
            final char c,
            final int index
    ) {
        if (index == 0) {
            return InputFilterCharUtils.isChinese(c);
        }
        if (index == 1) {
            return InputFilterCharUtils.isPlateLetter(c);
        }
        if (index >= 2 && index < MAX_LENGTH) {
            return InputFilterCharUtils.isPlateSerialChar(c);
        }
        return false;
    }

    /**
     * 将车牌字母转为大写
     * @param filtered 按位过滤结果
     * @param source   原始输入
     * @param start    原始起始
     * @param end      原始结束
     * @return 规范化后的内容
     */
    private static CharSequence normalizePlateLetters(
            final CharSequence filtered,
            final CharSequence source,
            final int start,
            final int end
    ) {
        CharSequence  working = filtered != null ? filtered : source;
        int           wStart  = filtered != null ? 0 : start;
        int           wEnd    = filtered != null ? filtered.length() : end;
        boolean       changed = false;
        StringBuilder builder = new StringBuilder(wEnd - wStart);
        for (int i = wStart; i < wEnd; i++) {
            char c = working.charAt(i);
            if (InputFilterCharUtils.isPlateLetter(c) && Character.isLowerCase(c)) {
                builder.append(Character.toUpperCase(c));
                changed = true;
            } else {
                builder.append(c);
            }
        }
        if (!changed) return filtered;
        return builder;
    }
}