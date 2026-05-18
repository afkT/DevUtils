package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 小数输入：数字加一个小数点，可限制整数位与小数位长度
 * @author Ttt
 */
public class DecimalInputFilter
        implements InputFilter {

    private final int mIntegerDigits;
    private final int mDecimalDigits;

    /**
     * 不限制整数位、小数位长度，仅保证一个小数点
     */
    public DecimalInputFilter() {
        this(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 构造函数
     * @param integerDigits 整数部分最大位数
     * @param decimalDigits 小数部分最大位数
     */
    public DecimalInputFilter(
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
        String destStr = dest == null ? "" : dest.toString();
        String result = destStr.substring(0, dstart)
                + source.subSequence(start, end)
                + destStr.substring(dend);
        if (TextUtils.isEmpty(result)) return null;
        if (!isValidDecimal(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验完整输入是否合法小数且在位数限制内
     * @param value 合并后的完整输入
     * @return {@code true} 合法，{@code false} 不合法
     */
    private boolean isValidDecimal(final String value) {
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