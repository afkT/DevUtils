package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 百分比输入：非负小数，默认闭区间 [0, 100]，可选 0-1 刻度与末尾 % 符号
 * @author Ttt
 */
public class PercentageInputFilter
        implements InputFilter {

    // 刻度：0-100
    public static final int SCALE_HUNDRED  = 0;
    // 刻度：0-1
    public static final int SCALE_FRACTION = 1;

    private final double  mMaxValue;
    private final int     mIntegerDigits;
    private final int     mDecimalDigits;
    private final boolean mAllowPercentSymbol;

    /**
     * 0-100 刻度，整数最多 3 位、小数最多 2 位，不允许 %
     */
    public PercentageInputFilter() {
        this(SCALE_HUNDRED, 3, 2, false);
    }

    /**
     * 构造函数
     * @param scale              {@link #SCALE_HUNDRED} 或 {@link #SCALE_FRACTION}
     * @param integerDigits        整数部分最大位数
     * @param decimalDigits        小数部分最大位数
     * @param allowPercentSymbol   是否允许末尾单个 % 符号
     */
    public PercentageInputFilter(
            final int scale,
            final int integerDigits,
            final int decimalDigits,
            final boolean allowPercentSymbol
    ) {
        mMaxValue            = scale == SCALE_FRACTION ? 1.0 : 100.0;
        mIntegerDigits       = Math.max(0, integerDigits);
        mDecimalDigits       = Math.max(0, decimalDigits);
        mAllowPercentSymbol  = allowPercentSymbol;
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
        if (!isValidPercentage(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验完整输入是否合法百分比
     * @param value 合并后的完整输入
     * @return {@code true} 合法
     */
    private boolean isValidPercentage(final String value) {
        String numeric = value;
        if (mAllowPercentSymbol) {
            int percentIndex = value.indexOf('%');
            if (percentIndex >= 0) {
                if (percentIndex != value.length() - 1) return false;
                if (value.indexOf('%', percentIndex + 1) >= 0) return false;
                numeric = value.substring(0, percentIndex);
                if (TextUtils.isEmpty(numeric)) return false;
            }
        } else if (value.indexOf('%') >= 0) {
            return false;
        }
        if (".".equals(numeric)) return true;
        if (!isValidDecimalPart(numeric)) return false;
        try {
            double parsed = Double.parseDouble(numeric);
            return parsed >= 0 && parsed <= mMaxValue;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    /**
     * 校验无符号小数部分格式与位数
     * @param value 数值文本
     * @return {@code true} 合法
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