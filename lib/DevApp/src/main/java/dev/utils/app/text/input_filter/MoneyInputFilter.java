package dev.utils.app.text.input_filter;

/**
 * detail: 金额输入：最多两位小数，整数位默认不限制
 * @author Ttt
 */
public class MoneyInputFilter
        extends DecimalInputFilter {

    /**
     * 整数不限制，小数最多 2 位
     */
    public MoneyInputFilter() {
        super(Integer.MAX_VALUE, 2);
    }

    /**
     * 构造函数
     * @param integerDigits 整数部分最大位数
     */
    public MoneyInputFilter(final int integerDigits) {
        super(integerDigits, 2);
    }
}