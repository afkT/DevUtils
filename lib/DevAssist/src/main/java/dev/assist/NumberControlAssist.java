package dev.assist;

/**
 * detail: 数量控制辅助类
 * @author Ttt
 * <pre>
 *     主要用于数量加减限制, 如: 购物车数量加减
 * </pre>
 */
public class NumberControlAssist {

    // Object
    private Object  mObject          = null;
    // 最小值
    private int     mMinNumber       = 1;
    // 最大值
    private int     mMaxNumber       = Integer.MAX_VALUE;
    // 当前数量
    private int     mCurrentNumber   = 1;
    // 重置数量 ( 出现异常情况, 则使用该变量赋值 )
    private int     mResetNumber     = 1;
    // 是否允许设置为负数
    private boolean mIsAllowNegative = false;

    public NumberControlAssist() {
    }

    public NumberControlAssist(final int minNumber) {
        this.mMinNumber = minNumber;
    }

    public NumberControlAssist(final int minNumber, final int maxNumber) {
        this.mMinNumber = minNumber;
        this.mMaxNumber = maxNumber;
    }

    // ===============
    // = 对外提供方法 =
    // ===============

    /**
     * 判断当前数量, 是否等于最小值
     * @return {@code true} yes, {@code false} no
     */
    public boolean isMinNumber() {
        return isMinNumber(mCurrentNumber);
    }

    /**
     * 判断数量, 是否等于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isMinNumber(final int number) {
        return number == mMinNumber;
    }

    /**
     * 判断数量, 是否小于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLessThanMinNumber(final int number) {
        return number < mMinNumber;
    }

    /**
     * 判断数量, 是否大于最小值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isGreaterThanMinNumber(final int number) {
        return number > mMinNumber;
    }

    // =

    /**
     * 判断当前数量, 是否等于最大值
     * @return {@code true} yes, {@code false} no
     */
    public boolean isMaxNumber() {
        return isMaxNumber(mCurrentNumber);
    }

    /**
     * 判断数量, 是否等于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isMaxNumber(final int number) {
        return number == mMaxNumber;
    }

    /**
     * 判断数量, 是否小于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isLessThanMaxNumber(final int number) {
        return number < mMaxNumber;
    }

    /**
     * 判断数量, 是否大于最大值
     * @param number Number
     * @return {@code true} yes, {@code false} no
     */
    public boolean isGreaterThanMaxNumber(final int number) {
        return number > mMaxNumber;
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取 Object
     * @param <T> 泛型
     * @return Object convert T object
     */
    public <T> T getObject() {
        try {
            return (T) mObject;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 设置 Object
     * @param object Object
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setObject(final Object object) {
        this.mObject = object;
        return this;
    }

    // =

    /**
     * 获取最小值
     * @return 最小值
     */
    public int getMinNumber() {
        return mMinNumber;
    }

    /**
     * 设置最小值
     * <pre>
     *     内部判断了, 是否大于 mMaxNumber, 属于的话则自动赋值 mMaxNumber
     * </pre>
     * @param minNumber 最小值
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setMinNumber(final int minNumber) {
        int number = minNumber;
        // 如果不允许为负数, 并且设置最小值为负数, 则设置为重置数量
        if (!mIsAllowNegative && number < 0) {
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
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setMaxNumber(final int maxNumber) {
        this.mMaxNumber = (maxNumber < mMinNumber) ? mMinNumber : maxNumber;
        return this;
    }

    /**
     * 设置最小值、最大值
     * @param minNumber 最小值
     * @param maxNumber 最大值
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setMinMaxNumber(final int minNumber, final int maxNumber) {
        return setMinNumber(minNumber).setMaxNumber(maxNumber);
    }

    // =

    /**
     * 获取当前数量
     * @return 当前数量
     */
    public int getCurrentNumber() {
        return mCurrentNumber;
    }

    /**
     * 设置当前数量
     * @param currentNumber 当前数量
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setCurrentNumber(final int currentNumber) {
        return setCurrentNumber(currentNumber, true);
    }

    /**
     * 设置当前数量
     * @param currentNumber     当前数量
     * @param isTriggerListener 是否触发事件
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setCurrentNumber(final int currentNumber, final boolean isTriggerListener) {
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
            if (mINumberListener != null) {
                mINumberListener.onNumberChanged(isAdd, number);
            }
        }
        return this;
    }

    // =

    /**
     * 获取重置数量
     * @return 重置数量
     */
    public int getResetNumber() {
        return mResetNumber;
    }

    /**
     * 设置重置数量
     * @param resetNumber 重置数量
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setResetNumber(final int resetNumber) {
        // 防止出现负数
        this.mResetNumber = (!mIsAllowNegative && mResetNumber < 0) ? 1 : resetNumber;
        return this;
    }

    // =

    /**
     * 获取是否允许设置为负数
     * @return {@code true} yes, {@code false} no
     */
    public boolean isAllowNegative() {
        return mIsAllowNegative;
    }

    /**
     * 设置是否允许设置为负数
     * @param allowNegative {@code true} yes, {@code false} no
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setAllowNegative(final boolean allowNegative) {
        this.mIsAllowNegative = allowNegative;
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
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist numberChange(final int number) {
        if (mINumberListener != null) {
            // 计算之后的数量
            int afterNumber = (mCurrentNumber + number);
            // 判断是否添加, 大于等于当前数量, 表示添加
            boolean isAdd = afterNumber >= mCurrentNumber;
            // 进行判断
            if (mINumberListener.onPrepareChanged(isAdd, mCurrentNumber, afterNumber)) {
                // 进行设置当前数量
                setCurrentNumber(afterNumber, true);
            }
        }
        return this;
    }

    /**
     * 添加数量 ( 默认累加 1 )
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist addNumber() {
        return numberChange(1);
    }

    /**
     * 减少数量 ( 默认累减 1 )
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist subtractionNumber() {
        return numberChange(-1);
    }

    // ===============
    // = 内部判断方法 =
    // ===============

    /**
     * 检查更新处理
     */
    private void checkUpdate() {
        if (!mIsAllowNegative && mResetNumber < 0) {
            mResetNumber = 1;
        }
        if (!mIsAllowNegative && mMinNumber < 0) {
            mMinNumber = mResetNumber;
        }
        if (!mIsAllowNegative && mMaxNumber < 0) {
            mMaxNumber = mResetNumber;
        }
        // 重置最大值
        setMaxNumber(mMaxNumber);
    }

    // ===========
    // = 接口相关 =
    // ===========

    /**
     * detail: 数量操作接口
     * @param <R> 泛型
     * @author Ttt
     * <pre>
     *     每个接口方法直接通过调用 NumberControlAssist 已实现同名方法即可
     * </pre>
     */
    public interface INumberOperate<R> {

        /**
         * 判断当前数量, 是否等于最小值
         * @return {@code true} yes, {@code false} no
         */
        boolean isMinNumber();

        /**
         * 判断数量, 是否等于最小值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isMinNumber(int number);

        /**
         * 判断数量, 是否小于最小值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isLessThanMinNumber(int number);

        /**
         * 判断数量, 是否大于最小值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isGreaterThanMinNumber(int number);

        // =

        /**
         * 判断当前数量, 是否等于最大值
         * @return {@code true} yes, {@code false} no
         */
        boolean isMaxNumber();

        /**
         * 判断数量, 是否等于最大值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isMaxNumber(int number);

        /**
         * 判断数量, 是否小于最大值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isLessThanMaxNumber(int number);

        /**
         * 判断数量, 是否大于最大值
         * @param number Number
         * @return {@code true} yes, {@code false} no
         */
        boolean isGreaterThanMaxNumber(int number);

        // ===========
        // = get/set =
        // ===========

        /**
         * 获取 Object
         * @param <T> 泛型
         * @return Object convert T object
         */
        <T> T getObject();

        /**
         * 设置 Object
         * @param object Object
         * @return R 泛型返回对象
         */
        R setObject(Object object);

        // =

        /**
         * 获取最小值
         * @return 最小值
         */
        int getMinNumber();

        /**
         * 设置最小值
         * <pre>
         *     内部判断了, 是否大于 mMaxNumber, 属于的话则自动赋值 mMaxNumber
         * </pre>
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
         * <pre>
         *     内部判断了, 是否小于 mMinNumber, 属于的话则自动赋值 mMinNumber
         *     特殊情况 ( 修改为负数 ), 最好先调用 setMinNumber, 在调用 setMaxNumber
         * </pre>
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
        R setMinMaxNumber(int minNumber, int maxNumber);

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
        R setCurrentNumber(int currentNumber, boolean isTriggerListener);

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

        // =

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

    /**
     * detail: 数量监听事件接口
     * @author Ttt
     */
    public interface INumberListener {

        /**
         * 数量准备变化通知
         * @param isAdd       是否增加
         * @param curNumber   当前数量
         * @param afterNumber 处理之后的数量
         * @return {@code true} allow, {@code false} prohibit
         */
        boolean onPrepareChanged(boolean isAdd, int curNumber, int afterNumber);

        /**
         * 数量变化通知
         * @param isAdd     是否增加
         * @param curNumber 当前数量
         */
        void onNumberChanged(boolean isAdd, int curNumber);
    }

    // 数量监听事件接口
    private INumberListener mINumberListener;

    /**
     * 获取数量监听事件接口
     * @return {@link INumberListener}
     */
    public INumberListener getNumberListener() {
        return mINumberListener;
    }

    /**
     * 设置数量监听事件接口
     * @param iNumberListener {@link INumberListener}
     * @return {@link NumberControlAssist}
     */
    public NumberControlAssist setNumberListener(final INumberListener iNumberListener) {
        this.mINumberListener = iNumberListener;
        return this;
    }
}