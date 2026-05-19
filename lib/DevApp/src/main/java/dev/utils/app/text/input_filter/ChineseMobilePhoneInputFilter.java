package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 中国大陆手机号输入：仅数字，首位 1、第二位 3-9，默认最多 11 位
 * @author Ttt
 * <pre>
 *     与 {@link dev.utils.common.validator.ValiToPhoneUtils#isPhoneSimple(String)} 输入态规则对齐；
 *     完整号段校验请在失焦或提交时调用 {@link dev.utils.common.validator.ValiToPhoneUtils#isPhone(String)}。
 * </pre>
 */
public class ChineseMobilePhoneInputFilter
        implements InputFilter {

    // 默认最大位数
    private static final int DEFAULT_MAX_LENGTH = 11;

    private final int mMaxLength;

    /**
     * 默认最多 11 位
     */
    public ChineseMobilePhoneInputFilter() {
        this(DEFAULT_MAX_LENGTH);
    }

    /**
     * 构造函数
     * @param maxLength 最大位数
     */
    public ChineseMobilePhoneInputFilter(final int maxLength) {
        mMaxLength = Math.max(1, maxLength);
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
        return InputFilterCharUtils.filterByPosition(
                source, start, end, dest, dstart, dend,
                mMaxLength,
                InputFilterCharUtils::isDigit,
                ChineseMobilePhoneInputFilter::isAllowedAt
        );
    }

    /**
     * 按位判断是否允许
     * @param c     字符
     * @param index 下标
     * @return {@code true} 允许
     */
    private static boolean isAllowedAt(
            final char c,
            final int index
    ) {
        if (!InputFilterCharUtils.isDigit(c)) return false;
        if (index == 0) return c == '1';
        if (index == 1) return c >= '3' && c <= '9';
        return true;
    }
}