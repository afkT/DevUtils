package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 端口号输入：1-65535 的正整数
 * @author Ttt
 */
public class PortInputFilter
        implements InputFilter {

    private final IntegerInputFilter mIntegerInputFilter;

    /**
     * 构造函数
     */
    public PortInputFilter() {
        mIntegerInputFilter = new IntegerInputFilter(5);
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
        CharSequence integerResult = mIntegerInputFilter.filter(
                source, start, end, dest, dstart, dend
        );
        if (integerResult != null && integerResult.length() == 0) {
            return "";
        }
        if (source == null) return integerResult;
        String result = InputFilterCharUtils.mergeInput(source, start, end, dest, dstart, dend);
        if (TextUtils.isEmpty(result)) return integerResult;
        try {
            int port = Integer.parseInt(result);
            if (port < 1 || port > 65535) {
                return "";
            }
        } catch (NumberFormatException ignored) {
            return "";
        }
        return integerResult;
    }
}