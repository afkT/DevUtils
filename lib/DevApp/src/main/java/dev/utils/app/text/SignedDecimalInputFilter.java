package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 有符号小数输入：可选首位负号，数字与一个小数点，可限制整数位与小数位
 * @author Ttt
 */
public class SignedDecimalInputFilter
        implements InputFilter {

    private final int mIntegerDigits;
    private final int mDecimalDigits;

    /**
     * 不限制整数位、小数位长度，仅保证一个小数点
     */
    public SignedDecimalInputFilter() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     * @param integerDigits 整数部分最大位数
     * @param decimalDigits 小数部分最大位数
     */
    public SignedDecimalInputFilter(
            final int integerDigits,
            final int decimalDigits
    ) {
        mIntegerDigits = Math.max(0, integerDigits);
        mDecimalDigits = Math.max(0, decimalDigits);
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
        if (TextUtils.isEmpty(result) || ".".equals(result) || "-".equals(result) || "-.".equals(result)) {
            return null;
        }
        if (!isValidSignedDecimal(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验完整输入是否合法有符号小数
     * @param value 合并后的完整输入
     * @return {@code true} 合法，{@code false} 不合法
     */
    private boolean isValidSignedDecimal(final String value) {
        int index = 0;
        if (value.charAt(0) == '-') {
            index = 1;
            if (value.length() == 1) return true;
        }
        return isValidDecimalPart(value.substring(index));
    }

    /**
     * 校验无符号小数部分
     * @param value 小数文本
     * @return {@code true} 合法，{@code false} 不合法
     */
    private boolean isValidDecimalPart(final String value) {
        int dotIndex = value.indexOf('.');
        int dotCount = 0;
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c == '.') {
                dotCount++;
            } else if (!InputFilterCharUtils.isDigit(c)) {
                return false;
            }
        }
        if (dotCount > 1) return false;
        String integerPart;
        String decimalPart = "";
        if (dotIndex >= 0) {
            integerPart = value.substring(0, dotIndex);
            if (dotIndex < value.length() - 1) {
                decimalPart = value.substring(dotIndex + 1);
            }
        } else {
            integerPart = value;
        }
        if (integerPart.length() > mIntegerDigits) return false;
        return decimalPart.length() <= mDecimalDigits;
    }
}
