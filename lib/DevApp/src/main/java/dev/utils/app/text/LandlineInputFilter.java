package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 固定电话输入：数字与分隔符 {@code -}
 * @author Ttt
 * <pre>
 *     如 {@code 010-12345678}、{@code 0755-87654321}，默认最大 16 字符。
 * </pre>
 */
public class LandlineInputFilter
        implements InputFilter {

    private final int mMaxLength;

    /**
     * 最大 16 字符
     */
    public LandlineInputFilter() {
        this(16);
    }

    /**
     * 构造函数
     * @param maxLength 最大字符长度
     */
    public LandlineInputFilter(final int maxLength) {
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
                InputFilterCharUtils.isDigit(c) || c == '-'
        );
        CharSequence working   = source;
        int          workStart = start;
        int          workEnd   = end;
        if (allowed != null) {
            working   = allowed;
            workStart = 0;
            workEnd   = allowed.length();
        }
        if (dest == null) return allowed;
        int destLen   = dest.length() - (dend - dstart);
        int insertLen = workEnd - workStart;
        if (destLen + insertLen <= mMaxLength) {
            return allowed;
        }
        int remain = mMaxLength - destLen;
        if (remain <= 0) return "";
        return working.subSequence(workStart, workStart + remain);
    }
}
