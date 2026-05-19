package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 端口号输入：1-65535，仅数字，禁止前导零
 * @author Ttt
 */
public class PortInputFilter
        implements InputFilter {

    // 端口号上限
    private static final int MAX_PORT = 65535;
    // 最大位数
    private static final int MAX_DIGITS = 5;

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
        if (TextUtils.isEmpty(result)) return null;
        if (!isValidPort(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验端口号文本是否合法 ( 含输入过程中的中间态 )
     * @param value 合并后的完整输入
     * @return {@code true} 合法
     */
    private static boolean isValidPort(final String value) {
        for (int i = 0; i < value.length(); i++) {
            if (!InputFilterCharUtils.isDigit(value.charAt(i))) {
                return false;
            }
        }
        if (value.length() > MAX_DIGITS) return false;
        if (value.length() > 1 && value.charAt(0) == '0') return false;
        if (value.length() == 1 && value.charAt(0) == '0') return false;
        try {
            long port = Long.parseLong(value);
            return port <= MAX_PORT;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }
}