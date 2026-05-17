package dev.utils.app.text;

/**
 * detail: 银行卡号输入：仅数字，默认最大 19 位
 * @author Ttt
 */
public class BankCardInputFilter
        extends PhoneInputFilter {

    /**
     * 最大 19 位
     */
    public BankCardInputFilter() {
        super(19);
    }

    /**
     * 构造函数
     * @param maxLength 最大位数
     */
    public BankCardInputFilter(final int maxLength) {
        super(maxLength);
    }
}