package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

/**
 * detail: 小数输入：数字 + 一个小数点，可限制整数位与小数位长度
 * @author Ttt
 */
public class DecimalInputFilter implements InputFilter {

    private final int mIntegerDigits;
    private final int mDecimalDigits;

    /**
     * 构造函数 ( 不限制整数位、小数位长度，仅保证一个小数点 )
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