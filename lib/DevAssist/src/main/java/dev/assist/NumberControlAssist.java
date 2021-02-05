package dev.assist;

import dev.base.DevNumber;
import dev.base.number.INumberListener;
import dev.base.number.INumberOperate;

/**
 * detail: 数量控制辅助类
 * @author Ttt
 * <pre>
 *     主要用于数量加减限制, 如: 购物车数量加减
 * </pre>
 */
public class NumberControlAssist
        implements INumberOperate<NumberControlAssist> {

    // Number Object
    private final DevNumber mNumber;

    public NumberControlAssist() {
        mNumber = new DevNumber();
    }

    public NumberControlAssist(final int minNumber) {
        mNumber = new DevNumber(minNumber);
    }

    public NumberControlAssist(
            final int minNumber,
            final int maxNumber
    ) {
        mNumber = new DevNumber(minNumber, maxNumber);
    }

    /**
     * 获取 DevNumber Object
     * @return {@link DevNumber}
     */
    public DevNumber getNumber() {
        return mNumber;
    }

    // ==================
    // = INumberOperate =
    // ==================

    /**
     * 判断当前数量, 是否等于最小值
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMinNumber() {
        return mNumber.isMinNumber();
    }

    /**
     * 判断数量, 是否等于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMinNumber(final int number) {
        return mNumber.isMinNumber(number);
    }

    /**
     * 判断数量, 是否小于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLessThanMinNumber(final int number) {
        return mNumber.isLessThanMinNumber(number);
    }

    /**
     * 判断数量, 是否大于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isGreaterThanMinNumber(final int number) {
        return mNumber.isGreaterThanMinNumber(number);
    }

    // =

    /**
     * 判断当前数量, 是否等于最大值
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMaxNumber() {
        return mNumber.isMaxNumber();
    }

    /**
     * 判断数量, 是否等于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMaxNumber(final int number) {
        return mNumber.isMaxNumber(number);
    }

    /**
     * 判断数量, 是否小于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLessThanMaxNumber(final int number) {
        return mNumber.isLessThanMaxNumber(number);
    }

    /**
     * 判断数量, 是否大于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isGreaterThanMaxNumber(final int number) {
        return mNumber.isGreaterThanMaxNumber(number);
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取最小值
     * @return 最小值
     */
    @Override
    public int getMinNumber() {
        return mNumber.getMinNumber();
    }

    /**
     * 设置最小值
     * @param minNumber 最小值
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setMinNumber(final int minNumber) {
        mNumber.setMinNumber(minNumber);
        return this;
    }

    // =

    /**
     * 获取最大值
     * @return 最大值
     */
    @Override
    public int getMaxNumber() {
        return mNumber.getMaxNumber();
    }

    /**
     * 设置最大值
     * @param maxNumber 最大值
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setMaxNumber(final int maxNumber) {
        mNumber.setMaxNumber(maxNumber);
        return this;
    }

    /**
     * 设置最小值、最大值
     * @param minNumber 最小值
     * @param maxNumber 最大值
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setMinMaxNumber(
            final int minNumber,
            final int maxNumber
    ) {
        mNumber.setMinMaxNumber(minNumber, maxNumber);
        return this;
    }

    // =

    /**
     * 获取当前数量
     * @return 当前数量
     */
    @Override
    public int getCurrentNumber() {
        return mNumber.getCurrentNumber();
    }

    /**
     * 设置当前数量
     * @param currentNumber 当前数量
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setCurrentNumber(final int currentNumber) {
        mNumber.setCurrentNumber(currentNumber);
        return this;
    }

    /**
     * 设置当前数量
     * @param currentNumber     当前数量
     * @param isTriggerListener 是否触发事件
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setCurrentNumber(
            final int currentNumber,
            final boolean isTriggerListener
    ) {
        mNumber.setCurrentNumber(currentNumber, isTriggerListener);
        return this;
    }

    // =

    /**
     * 获取重置数量
     * @return 重置数量
     */
    @Override
    public int getResetNumber() {
        return mNumber.getResetNumber();
    }

    /**
     * 设置重置数量
     * @param resetNumber 重置数量
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setResetNumber(final int resetNumber) {
        mNumber.setResetNumber(resetNumber);
        return this;
    }

    // =

    /**
     * 获取是否允许设置为负数
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isAllowNegative() {
        return mNumber.isAllowNegative();
    }

    /**
     * 设置是否允许设置为负数
     * @param allowNegative {@code true} yes, {@code false} no
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setAllowNegative(final boolean allowNegative) {
        mNumber.setAllowNegative(allowNegative);
        return this;
    }

    // ===============
    // = 数量变化方法 =
    // ===============

    /**
     * 数量改变通知
     * @param number 变化基数数量
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist numberChange(final int number) {
        mNumber.numberChange(number);
        return this;
    }

    /**
     * 添加数量 ( 默认累加 1 )
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist addNumber() {
        mNumber.addNumber();
        return this;
    }

    /**
     * 减少数量 ( 默认累减 1 )
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist subtractionNumber() {
        mNumber.subtractionNumber();
        return this;
    }

    // ========
    // = 事件 =
    // ========

    /**
     * 获取数量监听事件接口
     * @return {@link INumberListener}
     */
    @Override
    public INumberListener getNumberListener() {
        return mNumber.getNumberListener();
    }

    /**
     * 设置数量监听事件接口
     * @param listener {@link INumberListener}
     * @return {@link NumberControlAssist}
     */
    @Override
    public NumberControlAssist setNumberListener(final INumberListener listener) {
        mNumber.setNumberListener(listener);
        return this;
    }
}