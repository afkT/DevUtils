package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 手机号输入：仅数字，可限制最大位数 ( 默认 11 位 )
 * @author Ttt
 */
public class PhoneInputFilter implements InputFilter {

    private final int mMaxLength;

    /**
     * 构造函数 ( 最大 11 位 )
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
        CharSequence digitsOnly = InputFilterCharUtils.filterByPredicate(
                source, start, end, InputFilterCharUtils::isDigit
        );
        CharSequence working = source;
        int workStart = start;
        int workEnd = end;
        if (digitsOnly != null) {
            working = digitsOnly;
            workStart = 0;
            workEnd = digitsOnly.length();
        }
        if (dest == null) return digitsOnly;
        int destLen = dest.length() - (dend - dstart);
        int insertLen = workEnd - workStart;
        if (destLen + insertLen <= mMaxLength) {
            return digitsOnly;
        }
        int remain = mMaxLength - destLen;
        if (remain <= 0) return "";
        return working.subSequence(workStart, workStart + remain);
    }
}