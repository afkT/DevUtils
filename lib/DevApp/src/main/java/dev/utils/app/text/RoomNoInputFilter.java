package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 楼栋 / 单元 / 房号输入
 * @author Ttt
 * <pre>
 *     中文、数字及 {@code - # 栋单元室层幢弄号楼} 等常用字，默认最大 30 字符。
 * </pre>
 */
public class RoomNoInputFilter
        implements InputFilter {

    private final int mMaxLength;

    /**
     * 最大 30 字符
     */
    public RoomNoInputFilter() {
        this(30);
    }

    /**
     * 构造函数
     * @param maxLength 最大字符长度
     */
    public RoomNoInputFilter(final int maxLength) {
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
        if (source == null) return null;
        CharSequence allowed = InputFilterCharUtils.filterByPredicate(source, start, end,
                RoomNoInputFilter::isRoomNoChar
        );
        CharSequence working   = source;
        int          workStart = start;
        int          workEnd   = end;
        if (allowed != null) {
            working   = allowed;
            workStart = 0;
            workEnd   = allowed.length();
        }
        if (dest == null) return allowed;
        int destLen   = dest.length() - (dend - dstart);
        int insertLen = workEnd - workStart;
        if (destLen + insertLen <= mMaxLength) {
            return allowed;
        }
        int remain = mMaxLength - destLen;
        if (remain <= 0) return "";
        return working.subSequence(workStart, workStart + remain);
    }

    /**
     * 判断是否为房号常用字符
     * @param c 字符
     * @return {@code true} 允许
     */
    private static boolean isRoomNoChar(final char c) {
        if (InputFilterCharUtils.isChineseAddressChar(c)) return true;
        return c == '栋'
                || c == '单'
                || c == '元'
                || c == '室'
                || c == '层'
                || c == '幢'
                || c == '弄'
                || c == '号'
                || c == '楼'
                || c == '座'
                || c == '区'
                || c == '门';
    }
}
