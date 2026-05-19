package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 中国大陆居民身份证 18 位输入：前 17 位数字，末位数字或 X/x
 * @author Ttt
 * <pre>
 *     提交合法性请使用 {@link dev.utils.common.validator.IDCardUtils#validateCard(String)}。
 *     15 位老证请使用 {@link IntegerInputFilter} 等单独限制位数。
 * </pre>
 */
public class ChineseIdCardInputFilter
        implements InputFilter {

    // 18 位身份证长度
    private static final int ID_CARD_LENGTH = 18;

    private final boolean mAllowXOnlyOnLastPosition;

    /**
     * 末位允许 X/x
     */
    public ChineseIdCardInputFilter() {
        this(true);
    }

    /**
     * 构造函数
     * @param allowXOnlyOnLastPosition 末位是否允许 X/x
     */
    public ChineseIdCardInputFilter(final boolean allowXOnlyOnLastPosition) {
        mAllowXOnlyOnLastPosition = allowXOnlyOnLastPosition;
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
                ID_CARD_LENGTH,
                (c, index) -> isAllowedAt(c, index, mAllowXOnlyOnLastPosition)
        );
    }

    /**
     * 按位判断是否允许
     * @param c                        字符
     * @param index                    下标
     * @param allowXOnlyOnLastPosition 末位是否允许 X/x
     * @return {@code true} 允许
     */
    private static boolean isAllowedAt(
            final char c,
            final int index,
            final boolean allowXOnlyOnLastPosition
    ) {
        if (index < 17) {
            return InputFilterCharUtils.isDigit(c);
        }
        if (index == 17) {
            if (InputFilterCharUtils.isDigit(c)) return true;
            return allowXOnlyOnLastPosition && (c == 'X' || c == 'x');
        }
        return false;
    }
}