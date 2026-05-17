package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止连续空格 ( 不允许在已有空格后再输入空格 )
 * @author Ttt
 */
public class NoConsecutiveSpaceInputFilter
        implements InputFilter {

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
        boolean lastWasSpace = dstart > 0
                && dest != null
                && Character.isWhitespace(dest.charAt(dstart - 1));
        StringBuilder builder = null;
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (Character.isWhitespace(c)) {
                if (lastWasSpace) {
                    if (builder == null) {
                        builder = new StringBuilder(end - start);
                        for (int j = start; j < i; j++) {
                            builder.append(source.charAt(j));
                        }
                    }
                } else {
                    if (builder != null) builder.append(c);
                    lastWasSpace = true;
                }
            } else {
                if (builder != null) builder.append(c);
                lastWasSpace = false;
            }
        }
        return builder == null ? null : builder;
    }
}