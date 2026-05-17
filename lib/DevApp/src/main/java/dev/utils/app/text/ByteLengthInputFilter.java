package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 按字节长度限制，常用于短信、昵称等场景
 * @author Ttt
 * <pre>
 *     中文等 CJK 字符计 2，其余计 1。
 * </pre>
 */
public class ByteLengthInputFilter
        implements InputFilter {

    private final int mMaxByteLength;

    /**
     * 构造函数
     * @param maxByteLength 最大字节长度
     */
    public ByteLengthInputFilter(final int maxByteLength) {
        mMaxByteLength = Math.max(0, maxByteLength);
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
        int destByteLen = 0;
        if (dest != null) {
            destByteLen = InputFilterCharUtils.getByteLength(dest);
            destByteLen -= InputFilterCharUtils.getByteLength(dest.subSequence(dstart, dend));
        }
        int remain = mMaxByteLength - destByteLen;
        if (remain <= 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char c       = source.charAt(i);
            int  charLen = InputFilterCharUtils.isChinese(c) ? 2 : 1;
            if (charLen > remain) break;
            builder.append(c);
            remain -= charLen;
        }
        if (builder.length() == end - start) return null;
        return builder;
    }
}