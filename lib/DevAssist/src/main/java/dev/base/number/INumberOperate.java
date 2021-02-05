package dev.base.number;

/**
 * detail: 数量操作接口
 * @param <R> 泛型
 * @author Ttt
 */
public interface INumberOperate<R> {

    /**
     * 判断当前数量是否等于最小值
     * @return {@code true} yes, {@code false} no
     */
    boolean isMinNumber();

    /**
     * 判断数量是否等于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isMinNumber(int number);

    /**
     * 判断数量是否小于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isLessThanMinNumber(int number);

    /**
     * 判断数量是否大于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isGreaterThanMinNumber(int number);

    // =

    /**
     * 判断当前数量是否等于最大值
     * @return {@code true} yes, {@code false} no
     */
    boolean isMaxNumber();

    /**
     * 判断数量是否等于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isMaxNumber(int number);

    /**
     * 判断数量是否小于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isLessThanMaxNumber(int number);

    /**
     * 判断数量是否大于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    boolean isGreaterThanMaxNumber(int number);

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取最小值
     * @return 最小值
     */
    int getMinNumber();

    /**
     * 设置最小值
     * @param minNumber 最小值
     * @return R 泛型返回对象
     */
    R setMinNumber(int minNumber);

    // =

    /**
     * 获取最大值
     * @return 最大值
     */
    int getMaxNumber();

    /**
     * 设置最大值
     * @param maxNumber 最大值
     * @return R 泛型返回对象
     */
    R setMaxNumber(int maxNumber);

    /**
     * 设置最小值、最大值
     * @param minNumber 最小值
     * @param maxNumber 最大值
     * @return R 泛型返回对象
     */
    R setMinMaxNumber(
            int minNumber,
            int maxNumber
    );

    // =

    /**
     * 获取当前数量
     * @return 当前数量
     */
    int getCurrentNumber();

    /**
     * 设置当前数量
     * @param currentNumber 当前数量
     * @return R 泛型返回对象
     */
    R setCurrentNumber(int currentNumber);

    /**
     * 设置当前数量
     * @param currentNumber     当前数量
     * @param isTriggerListener 是否触发事件
     * @return R 泛型返回对象
     */
    R setCurrentNumber(
            int currentNumber,
            boolean isTriggerListener
    );

    // =

    /**
     * 获取重置数量
     * @return 重置数量
     */
    int getResetNumber();

    /**
     * 设置重置数量
     * @param resetNumber 重置数量
     * @return R 泛型返回对象
     */
    R setResetNumber(int resetNumber);

    // =

    /**
     * 获取是否允许设置为负数
     * @return {@code true} yes, {@code false} no
     */
    boolean isAllowNegative();

    /**
     * 设置是否允许设置为负数
     * @param allowNegative {@code true} yes, {@code false} no
     * @return R 泛型返回对象
     */
    R setAllowNegative(boolean allowNegative);

    // =

    /**
     * 数量改变通知
     * @param number 变化基数数量
     * @return R 泛型返回对象
     */
    R numberChange(int number);

    /**
     * 添加数量 ( 默认累加 1 )
     * @return R 泛型返回对象
     */
    R addNumber();

    /**
     * 减少数量 ( 默认累减 1 )
     * @return R 泛型返回对象
     */
    R subtractionNumber();

    // ========
    // = 事件 =
    // ========

    /**
     * 获取数量监听事件接口
     * @return {@link INumberListener}
     */
    INumberListener getNumberListener();

    /**
     * 设置数量监听事件接口
     * @param iNumberListener {@link INumberListener}
     * @return R 泛型返回对象
     */
    R setNumberListener(INumberListener iNumberListener);
}