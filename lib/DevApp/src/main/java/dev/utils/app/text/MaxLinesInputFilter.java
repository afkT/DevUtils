package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 限制最大行数 ( 换行符个数不超过 maxLines - 1 )
 * @author Ttt
 * <pre>
 *     例如 maxLines 为 3 时，最多允许 2 个换行符。
 * </pre>
 */
public class MaxLinesInputFilter
        implements InputFilter {

    private final int mMaxLines;

    /**
     * 构造函数
     * @param maxLines 最大行数，小于 1 时按 1 处理
     */
    public MaxLinesInputFilter(final int maxLines) {
        mMaxLines = Math.max(1, maxLines);
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
        if (countLineBreaks(result) < mMaxLines) {
            return null;
        }
        return "";
    }

    /**
     * 统计换行符个数
     * @param text 文本
     * @return 换行符个数
     */
    private static int countLineBreaks(final String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                count++;
            }
        }
        return count;
    }
}
