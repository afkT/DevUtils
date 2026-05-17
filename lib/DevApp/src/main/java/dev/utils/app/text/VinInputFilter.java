package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 车辆识别代号 ( VIN ) 输入
 * @author Ttt
 * <pre>
 *     17 位，字母与数字，不含 I、O、Q，字母自动转大写。
 * </pre>
 */
public class VinInputFilter
        implements InputFilter {

    private static final int MAX_LENGTH = 17;

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
        CharSequence result = InputFilterCharUtils.filterByPosition(
                source, start, end, dest, dstart, dend, MAX_LENGTH,
                InputFilterCharUtils::isVinChar,
                (c, index) -> InputFilterCharUtils.isVinChar(c)
        );
        return normalizeVinLetters(result, source, start, end);
    }

    /**
     * VIN 字母转大写
     * @param filtered 按位过滤结果
     * @param source   原始输入
     * @param start    原始起始
     * @param end      原始结束
     * @return 规范化后的内容
     */
    private static CharSequence normalizeVinLetters(
            final CharSequence filtered,
            final CharSequence source,
            final int start,
            final int end
    ) {
        CharSequence working = filtered != null ? filtered : source;
        int          wStart  = filtered != null ? 0 : start;
        int          wEnd    = filtered != null ? filtered.length() : end;
        boolean      changed = false;
        StringBuilder builder = new StringBuilder(wEnd - wStart);
        for (int i = wStart; i < wEnd; i++) {
            char c = working.charAt(i);
            if (InputFilterCharUtils.isEnglish(c) && Character.isLowerCase(c)) {
                builder.append(Character.toUpperCase(c));
                changed = true;
            } else {
                builder.append(c);
            }
        }
        if (!changed) return filtered;
        return builder;
    }
}
