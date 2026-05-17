package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 按字节长度限制 ( 中文等 CJK 字符计 2，其余计 1 )，常用于短信、昵称等场景
 * @author Ttt
 */
public class ByteLengthInputFilter implements InputFilter {

    private final int mMaxByteLength;

    /**
     * 构造函数
     * @param maxByteLength 最大字节长度 ( 中文计 2 )
     */
    public ByteLengthInputFilter(final int maxByteLength) {
        mMaxByteLength = Math.max(0, maxByteLength);
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
        int destByteLen = 0;
        if (dest != null) {
            destByteLen = InputFilterCharUtils.getByteLength(dest);
            destByteLen -= InputFilterCharUtils.getByteLength(dest.subSequence(dstart, dend));
        }
        int remain = mMaxByteLength - destByteLen;
        if (remain <= 0) return "";
        StringBuilder builder = new StringBuilder();
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            int charLen = InputFilterCharUtils.isChinese(c) ? 2 : 1;
            if (charLen > remain) break;
            builder.append(c);
            remain -= charLen;
        }
        if (builder.length() == end - start) return null;
        return builder;
    }
}