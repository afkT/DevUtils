package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 输入自动转小写
 * @author Ttt
 */
public class LowerCaseInputFilter
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
        boolean needLower = false;
        for (int i = start; i < end; i++) {
            if (Character.isUpperCase(source.charAt(i))) {
                needLower = true;
                break;
            }
        }
        if (!needLower) return null;
        StringBuilder builder = new StringBuilder(end - start);
        for (int i = start; i < end; i++) {
            builder.append(Character.toLowerCase(source.charAt(i)));
        }
        return builder;
    }
}