package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 有符号整数输入：可选首位负号，其余为数字，可限制最大位数
 * @author Ttt
 */
public class SignedIntegerInputFilter
        implements InputFilter {

    private final int mMaxDigits;

    /**
     * 不限制位数
     */
    public SignedIntegerInputFilter() {
        this(Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     * @param maxDigits 数字部分最大位数 ( 不含负号 )
     */
    public SignedIntegerInputFilter(final int maxDigits) {
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
        if (TextUtils.isEmpty(result) || "-".equals(result)) return null;
        if (!isValidSignedInteger(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验是否为合法有符号整数
     * @param value 合并后的完整输入
     * @return {@code true} 合法
     */
    private boolean isValidSignedInteger(final String value) {
        int index = 0;
        if (value.charAt(0) == '-') {
            if (value.length() == 1) return true;
            index = 1;
        }
        int digitCount = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '-') {
                if (i != 0) return false;
            } else if (!InputFilterCharUtils.isDigit(c)) {
                return false;
            } else {
                digitCount++;
            }
        }
        return digitCount <= mMaxDigits;
    }
}
