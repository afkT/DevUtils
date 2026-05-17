package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * detail: 禁止回车、换行输入
 * @author Ttt
 */
public class NoEnterInputFilter
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
        for (int i = start; i < end; i++) {
            char c = source.charAt(i);
            if (c == '\n' || c == '\r') {
                return "";
            }
        }
        return null;
    }
}