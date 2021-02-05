package dev.base;

import dev.base.number.INumberListener;
import dev.base.number.INumberOperate;

/**
 * detail: 数量实体类
 * @author Ttt
 */
public class DevNumber<T>
        extends DevObject<T>
        implements INumberOperate<DevNumber<T>> {

    // 最小值
    private int             mMinNumber     = 1;
    // 最大值
    private int             mMaxNumber     = Integer.MAX_VALUE;
    // 当前数量
    private int             mCurrentNumber = 1;
    // 重置数量 ( 出现异常情况, 则使用该变量赋值 )
    private int             mResetNumber   = 1;
    // 是否允许设置为负数
    private boolean         mAllowNegative = false;
    // 数量监听事件接口
    private INumberListener mNumberListener;

    public DevNumber() {
    }

    public DevNumber(final int minNumber) {
        this.mMinNumber = minNumber;
    }

    public DevNumber(
            final int minNumber,
            final int maxNumber
    ) {
        this.mMinNumber = minNumber;
        this.mMaxNumber = maxNumber;
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
        return isMinNumber(mCurrentNumber);
    }

    /**
     * 判断数量, 是否等于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMinNumber(final int number) {
        return number == mMinNumber;
    }

    /**
     * 判断数量, 是否小于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLessThanMinNumber(final int number) {
        return number < mMinNumber;
    }

    /**
     * 判断数量, 是否大于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isGreaterThanMinNumber(final int number) {
        return number > mMinNumber;
    }

    // =

    /**
     * 判断当前数量, 是否等于最大值
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMaxNumber() {
        return isMaxNumber(mCurrentNumber);
    }

    /**
     * 判断数量, 是否等于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isMaxNumber(final int number) {
        return number == mMaxNumber;
    }

    /**
     * 判断数量, 是否小于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isLessThanMaxNumber(final int number) {
        return number < mMaxNumber;
    }

    /**
     * 判断数量, 是否大于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isGreaterThanMaxNumber(final int number) {
        return number > mMaxNumber;
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
        return mMinNumber;
    }

    /**
     * 设置最小值
     * <pre>
     *     内部判断了, 是否大于 mMaxNumber, 属于的话则自动赋值 mMaxNumber
     * </pre>
     * @param minNumber 最小值
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setMinNumber(final int minNumber) {
        int number = minNumber;
        // 如果不允许为负数, 并且设置最小值为负数, 则设置为重置数量
        if (!mAllowNegative && number < 0) {
            number = mResetNumber;
        }
        if (number > mMaxNumber) {
            number = mMaxNumber;
        }
        this.mMinNumber = number;
        return this;
    }

    // =

    /**
     * 获取最大值
     * @return 最大值
     */
    @Override
    public int getMaxNumber() {
        return mMaxNumber;
    }

    /**
     * 设置最大值
     * <pre>
     *     内部判断了, 是否小于 mMinNumber, 属于的话则自动赋值 mMinNumber
     *     特殊情况 ( 修改为负数 ), 最好先调用 setMinNumber, 在调用 setMaxNumber
     * </pre>
     * @param maxNumber 最大值
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setMaxNumber(final int maxNumber) {
        this.mMaxNumber = (maxNumber < mMinNumber) ? mMinNumber : maxNumber;
        return this;
    }

    /**
     * 设置最小值、最大值
     * @param minNumber 最小值
     * @param maxNumber 最大值
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setMinMaxNumber(
            final int minNumber,
            final int maxNumber
    ) {
        return setMinNumber(minNumber).setMaxNumber(maxNumber);
    }

    // =

    /**
     * 获取当前数量
     * @return 当前数量
     */
    @Override
    public int getCurrentNumber() {
        return mCurrentNumber;
    }

    /**
     * 设置当前数量
     * @param currentNumber 当前数量
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setCurrentNumber(final int currentNumber) {
        return setCurrentNumber(currentNumber, true);
    }

    /**
     * 设置当前数量
     * @param currentNumber     当前数量
     * @param isTriggerListener 是否触发事件
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setCurrentNumber(
            final int currentNumber,
            final boolean isTriggerListener
    ) {
        int number = currentNumber;
        if (number < mMinNumber) {
            number = mMinNumber;
        } else if (number > mMaxNumber) {
            number = mMaxNumber;
        }
        // 判断是否添加
        boolean isAdd = (number > mCurrentNumber);
        // 重新赋值
        this.mCurrentNumber = number;
        // 判断是否触发事件
        if (isTriggerListener) {
            if (mNumberListener != null) {
                mNumberListener.onNumberChanged(isAdd, number);
            }
        }
        return this;
    }

    // =

    /**
     * 获取重置数量
     * @return 重置数量
     */
    @Override
    public int getResetNumber() {
        return mResetNumber;
    }

    /**
     * 设置重置数量
     * @param resetNumber 重置数量
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setResetNumber(final int resetNumber) {
        // 防止出现负数
        this.mResetNumber = (!mAllowNegative && mResetNumber < 0) ? 1 : resetNumber;
        return this;
    }

    // =

    /**
     * 获取是否允许设置为负数
     * @return {@code true} yes, {@code false} no
     */
    @Override
    public boolean isAllowNegative() {
        return mAllowNegative;
    }

    /**
     * 设置是否允许设置为负数
     * @param allowNegative {@code true} yes, {@code false} no
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setAllowNegative(final boolean allowNegative) {
        this.mAllowNegative = allowNegative;
        // 进行检查更新
        checkUpdate();
        return this;
    }

    // ===============
    // = 数量变化方法 =
    // ===============

    /**
     * 数量改变通知
     * @param number 变化基数数量
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> numberChange(final int number) {
        if (mNumberListener != null) {
            // 计算之后的数量
            int afterNumber = (mCurrentNumber + number);
            // 判断是否添加, 大于等于当前数量, 表示添加
            boolean isAdd = afterNumber >= mCurrentNumber;
            // 进行判断
            if (mNumberListener.onPrepareChanged(isAdd, mCurrentNumber, afterNumber)) {
                // 进行设置当前数量
                setCurrentNumber(afterNumber, true);
            }
        }
        return this;
    }

    /**
     * 添加数量 ( 默认累加 1 )
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> addNumber() {
        return numberChange(1);
    }

    /**
     * 减少数量 ( 默认累减 1 )
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> subtractionNumber() {
        return numberChange(-1);
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
        return mNumberListener;
    }

    /**
     * 设置数量监听事件接口
     * @param listener {@link INumberListener}
     * @return {@link DevNumber<T>}
     */
    @Override
    public DevNumber<T> setNumberListener(final INumberListener listener) {
        this.mNumberListener = listener;
        return this;
    }

    // ===============
    // = 内部判断方法 =
    // ===============

    /**
     * 检查更新处理
     */
    private void checkUpdate() {
        if (!mAllowNegative && mResetNumber < 0) {
            mResetNumber = 1;
        }
        if (!mAllowNegative && mMinNumber < 0) {
            mMinNumber = mResetNumber;
        }
        if (!mAllowNegative && mMaxNumber < 0) {
            mMaxNumber = mResetNumber;
        }
        // 重置最大值
        setMaxNumber(mMaxNumber);
    }
}