package dev.utils.app.text;

/**
 * detail: 邮政编码输入：仅数字，默认最大 6 位
 * @author Ttt
 */
public class PostalCodeInputFilter
        extends PhoneInputFilter {

    /**
     * 最大 6 位
     */
    public PostalCodeInputFilter() {
        super(6);
    }

    /**
     * 构造函数
     * @param maxLength 最大位数
     */
    public PostalCodeInputFilter(final int maxLength) {
        super(maxLength);
    }
}