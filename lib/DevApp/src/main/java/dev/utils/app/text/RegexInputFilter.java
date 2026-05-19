package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

import dev.utils.app.InputFilterCharUtils;

/**
 * detail: 正则匹配输入，仅保留匹配规则的字符
 * @author Ttt
 * <pre>
 *     字符串构造时按单字符 {@link String#valueOf(char)} 与 {@link Pattern} 匹配；
 *     正则非法时内部 Pattern 为 null，不再过滤。
 * </pre>
 */
public class RegexInputFilter
        implements InputFilter {

    private final Pattern mPattern;

    /**
     * 构造函数
     * @param regex 正则表达式
     */
    public RegexInputFilter(final String regex) {
        Pattern pattern = null;
        try {
            pattern = Pattern.compile(regex);
        } catch (Throwable ignored) {
        }
        mPattern = pattern;
    }

    /**
     * 构造函数
     * @param pattern 正则表达式
     */
    public RegexInputFilter(final Pattern pattern) {
        mPattern = pattern;
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
        if (mPattern == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mPattern.matcher(String.valueOf(c)).matches()
        );
    }
}