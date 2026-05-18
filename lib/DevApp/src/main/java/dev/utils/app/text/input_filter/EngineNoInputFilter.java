package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 机动车发动机号输入
 * @author Ttt
 * <pre>
 *     字母与数字，默认最大 20 位，字母自动转大写。
 * </pre>
 */
public class EngineNoInputFilter
        implements InputFilter {

    private final int mMaxLength;

    /**
     * 最大 20 字符
     */
    public EngineNoInputFilter() {
        this(20);
    }

    /**
     * 构造函数
     * @param maxLength 最大字符长度
     */
    public EngineNoInputFilter(final int maxLength) {
        mMaxLength = Math.max(0, maxLength);
    }

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
        if (source == null) return null;
        CharSequence allowed = InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                InputFilterCharUtils.isEnglish(c) || InputFilterCharUtils.isDigit(c)
        );
        CharSequence working   = source;
        int          workStart = start;
        int          workEnd   = end;
        if (allowed != null) {
            working   = allowed;
            workStart = 0;
            workEnd   = allowed.length();
        }
        if (dest == null) return normalizeLetters(allowed, source, start, end);
        int          destLen   = dest.length() - (dend - dstart);
        int          insertLen = workEnd - workStart;
        CharSequence lengthResult;
        if (destLen + insertLen <= mMaxLength) {
            lengthResult = allowed;
        } else {
            int remain = mMaxLength - destLen;
            if (remain <= 0) return "";
            lengthResult = working.subSequence(workStart, workStart + remain);
        }
        return normalizeLetters(lengthResult, source, start, end);
    }

    /**
     * 字母转大写
     * @param filtered 过滤结果
     * @param source   原始输入
     * @param start    原始起始
     * @param end      原始结束
     * @return 规范化后的内容
     */
    private static CharSequence normalizeLetters(
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
            if (InputFilterCharUtils.isEnglish(c) && Character.isLowerCase(c)) {
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