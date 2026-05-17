package dev.utils.app.text;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Pattern;

/**
 * detail: 正则匹配输入，仅保留匹配 {@link Pattern} 的字符
 * @author Ttt
 */
public class RegexInputFilter implements InputFilter {

    private final Pattern mPattern;

    /**
     * 构造函数
     * @param regex 正则表达式 ( 按单字符 {@link String#valueOf(char)} 匹配 )
     */
    public RegexInputFilter(final String regex) {
        mPattern = Pattern.compile(regex);
    }

    /**
     * 构造函数
     * @param pattern 正则表达式
     */
    public RegexInputFilter(final Pattern pattern) {
        mPattern = pattern;
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
        if (mPattern == null) return null;
        return InputFilterCharUtils.filterByPredicate(source, start, end, c ->
                mPattern.matcher(String.valueOf(c)).matches()
        );
    }
}