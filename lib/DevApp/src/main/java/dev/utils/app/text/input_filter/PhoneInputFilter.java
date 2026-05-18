package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 手机号输入：仅数字，可限制最大位数
 * @author Ttt
 * <pre>
 *     无参构造默认最大 11 位。
 * </pre>
 */
public class PhoneInputFilter
        implements InputFilter {

    private final int mMaxLength;

    /**
     * 最大 11 位
     */
    public PhoneInputFilter() {
        this(11);
    }

    /**
     * 构造函数
     * @param maxLength 最大位数
     */
    public PhoneInputFilter(final int maxLength) {
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
        CharSequence digitsOnly = InputFilterCharUtils.filterByPredicate(
                source, start, end, InputFilterCharUtils::isDigit
        );
        CharSequence working   = source;
        int          workStart = start;
        int          workEnd   = end;
        if (digitsOnly != null) {
            working   = digitsOnly;
            workStart = 0;
            workEnd   = digitsOnly.length();
        }
        if (dest == null) return digitsOnly;
        int destLen   = dest.length() - (dend - dstart);
        int insertLen = workEnd - workStart;
        if (destLen + insertLen <= mMaxLength) {
            return digitsOnly;
        }
        int remain = mMaxLength - destLen;
        if (remain <= 0) return "";
        return working.subSequence(workStart, workStart + remain);
    }
}