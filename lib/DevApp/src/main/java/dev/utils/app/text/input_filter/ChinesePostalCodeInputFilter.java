package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 中国邮政编码输入：仅数字，固定 6 位
 * @author Ttt
 * <pre>
 *     适用于中国大陆 6 位邮政编码；其它国家/地区邮编规则不同，请勿混用。
 * </pre>
 */
public class ChinesePostalCodeInputFilter
        implements InputFilter {

    // 中国邮政编码位数
    public static final int CHINESE_POSTAL_CODE_LENGTH = 6;

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
        if (!isValidChinesePostalCode(result)) {
            return "";
        }
        return null;
    }

    /**
     * 校验是否为中国邮政编码格式 ( 输入过程中允许未满 6 位 )
     * @param value 合并后的完整输入
     * @return {@code true} 合法
     */
    private static boolean isValidChinesePostalCode(final String value) {
        if (value.length() > CHINESE_POSTAL_CODE_LENGTH) return false;
        for (int i = 0; i < value.length(); i++) {
            if (!InputFilterCharUtils.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}