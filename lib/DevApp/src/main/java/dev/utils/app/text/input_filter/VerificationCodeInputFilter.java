package dev.utils.app.text.input_filter;

/**
 * detail: 短信验证码输入：仅数字，默认最大 6 位
 * @author Ttt
 */
public class VerificationCodeInputFilter
        extends PhoneInputFilter {

    /**
     * 最大 6 位
     */
    public VerificationCodeInputFilter() {
        super(6);
    }

    /**
     * 构造函数
     * @param maxLength 最大位数
     */
    public VerificationCodeInputFilter(final int maxLength) {
        super(maxLength);
    }
}