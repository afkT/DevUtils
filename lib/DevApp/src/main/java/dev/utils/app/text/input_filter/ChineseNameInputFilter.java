package dev.utils.app.text.input_filter;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 中文姓名输入：汉字与间隔号
 * @author Ttt
 * <pre>
 *     间隔号可为 {@code ·}；
 *     适用于实名认证、收货人姓名等，不含空格与拉丁字母。
 * </pre>
 */
public class ChineseNameInputFilter
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
        return InputFilterCharUtils.filterByPredicate(
                source, start, end, InputFilterCharUtils::isChineseNameChar
        );
    }
}