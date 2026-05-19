package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 非负整数输入，禁止前导零 ( 可配置是否允许单独 0 )
 * @author Ttt
 */
public class NoLeadingZeroIntegerInputFilter
        implements InputFilter {

    private final int     mMaxDigits;
    private final boolean mAllowSingleZero;

    /**
     * 不限制位数，允许单独 0
     */
    public NoLeadingZeroIntegerInputFilter() {
        this(Integer.MAX_VALUE, true);
    }

    /**
     * 构造函数
     * @param maxDigits       最大位数
     * @param allowSingleZero 是否允许单独字符 0
     */
    public NoLeadingZeroIntegerInputFilter(
            final int maxDigits,
            final boolean allowSingleZero
    ) {
        mMaxDigits       = Math.max(0, maxDigits);
        mAllowSingleZero = allowSingleZero;
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
        String result = InputFilterCharUtils.mergeInput(source, start, end, dest, dstart, dend);
        if (TextUtils.isEmpty(result)) return null;
        if (!isValid(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验合并后的完整输入
     * @param value 文本
     * @return {@code true} 合法
     */
    private boolean isValid(final String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!InputFilterCharUtils.isDigit(value.charAt(i))) {
                return false;
            }
        }
        if (value.length() > mMaxDigits) return false;
        if (value.length() == 1 && value.charAt(0) == '0') {
            return mAllowSingleZero;
        }
        return value.length() <= 1 || value.charAt(0) != '0';
    }
}