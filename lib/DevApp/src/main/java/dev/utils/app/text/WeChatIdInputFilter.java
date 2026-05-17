package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 微信号输入
 * @author Ttt
 * <pre>
 *     6-20 位，以字母开头，可含字母、数字、下划线、减号，默认最大 20 位。
 * </pre>
 */
public class WeChatIdInputFilter
        implements InputFilter {

    private final int mMaxLength;

    /**
     * 最大 20 字符
     */
    public WeChatIdInputFilter() {
        this(20);
    }

    /**
     * 构造函数
     * @param maxLength 最大字符长度
     */
    public WeChatIdInputFilter(final int maxLength) {
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
        return InputFilterCharUtils.filterByPosition(
                source, start, end, dest, dstart, dend, mMaxLength,
                WeChatIdInputFilter::isAllowedAt
        );
    }

    /**
     * 判断字符在指定下标是否允许
     * @param c     字符
     * @param index 在完整微信号中的下标
     * @return {@code true} 允许
     */
    private static boolean isAllowedAt(
            final char c,
            final int index
    ) {
        if (index == 0) {
            return InputFilterCharUtils.isEnglish(c);
        }
        return InputFilterCharUtils.isEnglish(c)
                || InputFilterCharUtils.isDigit(c)
                || c == '_'
                || c == '-';
    }
}
