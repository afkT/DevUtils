package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 身份证号输入：最多 18 位，前 17 位为数字，末位可为数字或 X
 * @author Ttt
 */
public class IdCardInputFilter
        implements InputFilter {

    private static final int MAX_LENGTH = 18;

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
        String before  = destStr.substring(0, dstart);
        String after   = destStr.substring(dend);
        if (before.length() + after.length() >= MAX_LENGTH) {
            return "";
        }
        CharSequence allowedChars = filterAllowedChars(source, start, end);
        CharSequence working      = source;
        int          workStart    = start;
        int          workEnd      = end;
        if (allowedChars != null) {
            working   = allowedChars;
            workStart = 0;
            workEnd   = allowedChars.length();
        }
        StringBuilder insert = new StringBuilder(workEnd - workStart);
        for (int i = workStart; i < workEnd; i++) {
            int index = before.length() + insert.length();
            if (index >= MAX_LENGTH) break;
            char c = working.charAt(i);
            if (isAllowedAt(c, index)) {
                insert.append(c);
            }
        }
        if (insert.length() == workEnd - workStart
                && before.length() + insert.length() + after.length() <= MAX_LENGTH) {
            return allowedChars;
        }
        if (insert.length() == 0) return "";
        return insert;
    }

    /**
     * 剔除非法字符
     * @param source 源文本
     * @param start  起始下标
     * @param end    结束下标，不含
     * @return 过滤结果，全部合法为 null
     */
    private static CharSequence filterAllowedChars(
            final CharSequence source,
            final int start,
            final int end
    ) {
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                InputFilterCharUtils.isDigit(c) || c == 'X' || c == 'x'
        );
    }

    /**
     * 判断字符在指定下标是否允许
     * @param c     字符
     * @param index 在完整身份证号中的下标
     * @return {@code true} 允许，{@code false} 不允许
     */
    private static boolean isAllowedAt(
            final char c,
            final int index
    ) {
        if (index < 17) {
            return InputFilterCharUtils.isDigit(c);
        }
        if (index == 17) {
            return InputFilterCharUtils.isDigit(c) || c == 'X' || c == 'x';
        }
        return false;
    }
}
