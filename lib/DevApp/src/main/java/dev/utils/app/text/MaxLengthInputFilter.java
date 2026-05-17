package dev.utils.app.text;

import android.text.InputFilter;

/**
 * detail: 最大长度限制
 * @author Ttt
 * <pre>
 *     继承 {@link InputFilter.LengthFilter}。
 * </pre>
 */
public class MaxLengthInputFilter
        extends InputFilter.LengthFilter {

    /**
     * 构造函数
     * @param maxLength 最大字符长度
     */
    public MaxLengthInputFilter(final int maxLength) {
        super(maxLength);
    }
}