package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 正整数输入：仅数字，可限制最大位数
 * @author Ttt
 */
public class IntegerInputFilter
        implements InputFilter {

    private final int mMaxDigits;

    /**
     * 不限制位数
     */
    public IntegerInputFilter() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     * @param maxDigits 最大位数
     */
    public IntegerInputFilter(final int maxDigits) {
        mMaxDigits = Math.max(0, maxDigits);
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
        if (!isValidInteger(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验是否为合法正整数且在位数限制内
     * @param value 合并后的完整输入
     * @return {@code true} 合法，{@code false} 不合法
     */
    private boolean isValidInteger(final String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!InputFilterCharUtils.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return value.length() <= mMaxDigits;
    }
}