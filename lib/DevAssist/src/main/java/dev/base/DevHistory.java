package dev.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import dev.utils.JCLogUtils;

/**
 * detail: 历史数据记录功类
 * @author Ttt
 * <pre>
 *     不判 null ( null 默认可加入 )
 *     可通过 {@link Listener#accept(boolean, Object)} 进行拦截
 * </pre>
 */
public class DevHistory<T> {

    // 日志 TAG
    private final String      TAG      = DevHistory.class.getSimpleName();
    // 回退栈
    private final Stack<T>    mBack    = new Stack<>();
    // 前进栈
    private final Stack<T>    mForward = new Stack<>();
    // 当前数据
    private       T           mCurrent;
    // 方法事件触发接口
    private       Listener<T> mListener;

    // ==========
    // = 构造函数 =
    // ==========

    public DevHistory() {
    }

    public DevHistory(final Listener<T> listener) {
        this.mListener = listener;
    }

    // ==========
    // = 接口回调 =
    // ==========

    /**
     * detail: 方法事件触发接口
     * @author Ttt
     */
    public interface Listener<T> {

        /**
         * 是否允许添加
         * <pre>
         *     调用 {@link #addBack(Object)}、{@link #addForward(Object)} 触发
         * </pre>
         * @param back  {@code true} 回退栈操作, {@code false} 前进栈操作
         * @param value 待添加数据
         * @return {@code true} 允许, {@code false} 不允许
         */
        boolean accept(
                boolean back,
                T value
        );

        /**
         * 当前数据改变通知
         * <pre>
         *     调用 {@link #setCurrent(Object)} 触发
         * </pre>
         * @param beforeCurrent 操作前的当前数据
         * @param afterCurrent  操作后的当前数据
         */
        void changeCurrent(
                T beforeCurrent,
                T afterCurrent
        );

        /**
         * 清空数据回调
         * <pre>
         *     调用 {@link #clearBack()}、{@link #clearForward()} 触发
         * </pre>
         * @param back {@code true} 清空回退栈数据, {@code false} 清空前进栈数据
         */
        void clear(boolean back);

        /**
         * 添加数据到栈内
         * <pre>
         *     调用 {@link #addBack(Object)}、{@link #addForward(Object)} 触发
         * </pre>
         * @param back  {@code true} 回退栈, {@code false} 前进栈
         * @param value 已添加数据
         */
        void add(
                boolean back,
                T value
        );

        /**
         * 是否允许 Current 添加到列表中
         * <pre>
         *     调用 {@link #goBack(int)}、{@link #getForward(int)} 触发
         * </pre>
         * @param back          {@code true} 回退操作 ( 添加到前进栈 ), {@code false} 前进操作 ( 添加到回退栈 )
         * @param beforeCurrent 操作前的当前数据
         * @return {@code true} yes, {@code false} no
         */
        boolean acceptCurrentToList(
                boolean back,
                T beforeCurrent
        );
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取当前数据
     * @return 当前数据
     */
    public T getCurrent() {
        return mCurrent;
    }

    /**
     * 设置当前数据
     * @param current 当前数据
     * @return {@link DevHistory}
     */
    public DevHistory<T> setCurrent(final T current) {
        return setCurrent(current, Action.SET_CURRENT);
    }

    /**
     * 获取方法事件触发接口
     * @return 方法事件触发接口
     */
    public Listener<T> getListener() {
        return mListener;
    }

    /**
     * 设置方法事件触发接口
     * @param listener 方法事件触发接口
     * @return {@link DevHistory}
     */
    public DevHistory<T> setListener(final Listener<T> listener) {
        this.mListener = listener;
        return this;
    }

    // ============
    // = 回退栈相关 =
    // ============

    /**
     * 清空回退栈数据
     * @return {@link DevHistory}
     */
    public DevHistory<T> clearBack() {
        return clear(Type.BACK);
    }

    /**
     * 获取回退栈数据条数
     * @return 回退栈数据条数
     */
    public int sizeBack() {
        return size(Type.BACK);
    }

    /**
     * 是否不存在回退栈数据
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEmptyBack() {
        return isEmpty(Type.BACK);
    }

    /**
     * 是否能够执行回退操作
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoBack() {
        return canGoBack(1);
    }

    /**
     * 是否能够执行回退操作
     * @param index 索引
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoBack(final int index) {
        return canGo(Type.BACK, index);
    }

    /**
     * 添加到回退栈
     * @param value 待添加数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean addBack(final T value) {
        return add(Type.BACK, value);
    }

    /**
     * 获取上一条回退栈数据
     * @return 上一条回退栈数据
     */
    public T getBack() {
        return getBack(1);
    }

    /**
     * 获取指定索引回退栈数据
     * @param index 索引
     * @return 指定索引回退栈数据
     */
    public T getBack(final int index) {
        return get(Type.BACK, index);
    }

    /**
     * 前往上一条回退栈数据
     * @return 上一条回退栈数据
     */
    public T goBack() {
        return goBack(1);
    }

    /**
     * 前往指定索引回退栈数据
     * @param index 索引
     * @return 指定索引回退栈数据
     */
    public T goBack(final int index) {
        return gotoBack(index);
    }

    // ============
    // = 前进栈相关 =
    // ============

    /**
     * 清空前进栈数据
     * @return {@link DevHistory}
     */
    public DevHistory<T> clearForward() {
        return clear(Type.FORWARD);
    }

    /**
     * 获取前进栈数据条数
     * @return 前进栈数据条数
     */
    public int sizeForward() {
        return size(Type.FORWARD);
    }

    /**
     * 是否不存在前进栈数据
     * @return {@code true} yes, {@code false} no
     */
    public boolean isEmptyForward() {
        return isEmpty(Type.FORWARD);
    }

    /**
     * 是否能够执行前进操作
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoForward() {
        return canGoForward(1);
    }

    /**
     * 是否能够执行前进操作
     * @param index 索引
     * @return {@code true} yes, {@code false} no
     */
    public boolean canGoForward(final int index) {
        return canGo(Type.FORWARD, index);
    }

    /**
     * 添加到前进栈
     * @param value 待添加数据
     * @return {@code true} success, {@code false} fail
     */
    public boolean addForward(final T value) {
        return add(Type.FORWARD, value);
    }

    /**
     * 获取下一条前进栈数据
     * @return 下一条前进栈数据
     */
    public T getForward() {
        return getForward(1);
    }

    /**
     * 获取指定索引前进栈数据
     * @param index 索引
     * @return 指定索引前进栈数据
     */
    public T getForward(final int index) {
        return get(Type.FORWARD, index);
    }

    /**
     * 前往下一条前进栈数据
     * @return 下一条前进栈数据
     */
    public T goForward() {
        return goForward(1);
    }

    /**
     * 前往指定索引前进栈数据
     * @param index 索引
     * @return 指定索引前进栈数据
     */
    public T goForward(final int index) {
        return gotoForward(index);
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 计算 ( 回退、前进 ) 操作真实索引
     * <pre>
     *     需传入正整数, 如前往 ( 回退、前进 ) 上、下一条数据则传入 1
     *     会自动取绝对值, 并获取长度 - index 位置数据
     * </pre>
     * @param size  数据条数
     * @param index 索引
     * @return 操作真实索引
     */
    private int calculateRealIndex(
            final int size,
            final int index
    ) {
        if (size == 0) return -1;
        int realIndex = size - Math.abs(index);
        if (realIndex < 0) return -1;
        if (realIndex == size) return -1;
        return realIndex;
    }

    /**
     * 设置当前数据
     * @param current Current Data
     * @param operate 操作类型
     * @return {@link DevHistory}
     */
    private DevHistory<T> setCurrent(
            final T current,
            final Action operate
    ) {
        switch (operate) {
            case SET_CURRENT: // 正常设置当前数据操作
                // 清空前进栈数据
                clearForward();
                // 追加当前数据到回退栈数据中
                addBack(mCurrent);
                break;
            case GO_BACK: // 回退操作
                // 清空前进栈数据
                clearForward();
                break;
            case GO_FORWARD: // 前进操作
                break;
        }
        T beforeCurrent = this.mCurrent;
        this.mCurrent = current;
        // 当前数据改变通知
        if (mListener != null) {
            mListener.changeCurrent(beforeCurrent, current);
        }
        return this;
    }

    /**
     * detail: 操作行为
     * @author Ttt
     */
    private enum Action {

        // 正常设置当前数据操作
        SET_CURRENT,

        // 回退操作
        GO_BACK,

        // 前进操作
        GO_FORWARD
    }

    /**
     * detail: 操作类型
     * @author Ttt
     */
    private enum Type {

        // 回退数据相关
        BACK(true),

        // 前进数据相关
        FORWARD(false);

        protected boolean type;

        Type(boolean type) {
            this.type = type;
        }
    }

    // =

    /**
     * 清空栈内数据
     * @param operate 操作类型
     * @return {@link DevHistory}
     */
    private DevHistory<T> clear(final Type operate) {
        switch (operate) {
            case BACK:
                mBack.clear();
                break;
            case FORWARD:
                mForward.clear();
                break;
        }
        if (mListener != null) {
            mListener.clear(operate.type);
        }
        return this;
    }

    /**
     * 获取栈内数据条数
     * @param operate 操作类型
     * @return 栈内数据条数
     */
    private int size(final Type operate) {
        switch (operate) {
            case BACK:
                return mBack.size();
            case FORWARD:
                return mForward.size();
        }
        return 0;
    }

    /**
     * 是否不存在栈内数据
     * @param operate 操作类型
     * @return {@code true} yes, {@code false} no
     */
    private boolean isEmpty(final Type operate) {
        return size(operate) == 0;
    }

    /**
     * 是否能够执行指定操作
     * @param operate 操作类型
     * @param index   索引
     * @return {@code true} yes, {@code false} no
     */
    private boolean canGo(
            final Type operate,
            final int index
    ) {
        return calculateRealIndex(size(operate), index) >= 0;
    }

    /**
     * 添加数据到栈内
     * @param operate 操作类型
     * @param value   待添加数据
     * @return {@code true} success, {@code false} fail
     */
    private boolean add(
            final Type operate,
            final T value
    ) {
        if (mListener != null) {
            if (!mListener.accept(operate.type, value)) {
                return false;
            }
        }
        switch (operate) {
            case BACK:
                mBack.add(value);
                break;
            case FORWARD:
                mForward.add(value);
                break;
        }
        if (mListener != null) {
            mListener.add(operate.type, value);
        }
        return true;
    }

    /**
     * 获取指定索引栈内数据
     * <pre>
     *     索引传入值可以看该方法注释
     *     {@link #calculateRealIndex(int, int)}
     * </pre>
     * @param operate 操作类型
     * @param index   索引
     * @return 指定索引栈内数据
     */
    private T get(
            final Type operate,
            final int index
    ) {
        int realIndex;
        switch (operate) {
            case BACK:
                realIndex = calculateRealIndex(sizeBack(), index);
                if (realIndex < 0) return null;
                try {
                    return mBack.get(realIndex);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get - Back");
                }
            case FORWARD:
                realIndex = calculateRealIndex(sizeForward(), index);
                if (realIndex < 0) return null;
                try {
                    return mForward.get(realIndex);
                } catch (Exception e) {
                    JCLogUtils.eTag(TAG, e, "get - Forward");
                }
        }
        return null;
    }

    /**
     * 前往指定索引回退栈数据
     * <pre>
     *     索引传入值可以看该方法注释
     *     {@link #calculateRealIndex(int, int)}
     *     <p></p>
     *     例如目前 A、B、C、D、E、F、G ( Current )
     *     mBack    存储了 A、B、C、D、E、F 六条数据
     *     mForward 存储了 0 条数据 ( 倒序 )
     *     该方法传入 3 ( 6 - 3 = 3 => D ) 前往 D 的位置
     *     这个时候会设置 D 为 Current
     *     mBack    存储变更为 A、B、C
     *     mForward 存储变更为 G ( beforeCurrent )、F、E
     *     <p></p>
     *     每次操作都会清空前进栈 ( mForward ) 数据, 再进行添加新数据
     *     mForward 为什么进行倒序存储主要是为了复用 {@link #calculateRealIndex(int, int)} 方法, 统一逻辑
     *     <p></p>
     *     注: 需要 {@link #canGoBack(int)} 能够执行回退操作
     *     否则直接返回 null 且不会对任何数据、状态进行变更
     * </pre>
     * @param index 索引
     * @return 指定索引回退栈数据
     */
    private T gotoBack(final int index) {
        int size      = sizeBack();
        int realIndex = calculateRealIndex(size, index);
        if (realIndex < 0) return null;
        T beforeCurrent = mCurrent;
        // 获取准备回退数据并设置为当前数据
        T backValue = getBack(index);
        setCurrent(backValue, Action.GO_BACK);
        // 待添加前进栈数据
        List<T> lists = new ArrayList<>();
        // 是否需要把 beforeCurrent 添加到前进栈中
        if (mListener != null) {
            if (mListener.acceptCurrentToList(true, beforeCurrent)) {
                lists.add(beforeCurrent);
            }
        } else {
            lists.add(beforeCurrent);
        }
        try {
            // 添加回退索引位置到结尾之间的数据并进行降序排序
            List<T> temps = mBack.subList(realIndex + 1, size);
            Collections.reverse(temps);
            lists.addAll(temps);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "gotoBack - subList");
        }
        for (int i = realIndex; i < size; i++) {
            try {
                // 一直进行移除同一索引 ( 数据长度递减 )
                mBack.remove(realIndex);
            } catch (Exception ignored) {
            }
        }
        mForward.addAll(lists);
        return backValue;
    }

    /**
     * 前往指定索引前进栈数据
     * <pre>
     *     索引传入值可以看该方法注释
     *     {@link #calculateRealIndex(int, int)}
     *     <p></p>
     *     例如目前 A、B、C、D ( Current )、E、F、G
     *     mBack    存储了 A、B、C 三条数据
     *     mForward 存储了 G、F、E 三条数据 ( 倒序 )
     *     该方法传入 2 ( 3 - 2 = 1 => F ) 前往 F 的位置
     *     这个时候会设置 F 为 Current
     *     mBack    存储变更为 A、B、C、D、E
     *     mForward 存储变更为 G
     *     <p></p>
     *     每次操作都会清空前进栈 ( mForward ) 数据, 再进行添加新数据
     *     mForward 为什么进行倒序存储主要是为了复用 {@link #calculateRealIndex(int, int)} 方法, 统一逻辑
     *     <p></p>
     *     注: 需要 {@link #canGoForward(int)} 能够执行前进操作
     *     否则直接返回 null 且不会对任何数据、状态进行变更
     * </pre>
     * @param index 索引
     * @return 指定索引前进栈数据
     */
    private T gotoForward(final int index) {
        int size      = sizeForward();
        int realIndex = calculateRealIndex(size, index);
        if (realIndex < 0) return null;
        T beforeCurrent = mCurrent;
        // 获取准备前进数据并设置为当前数据
        T forwardValue = getForward(index);
        setCurrent(forwardValue, Action.GO_FORWARD);
        // 待添加回退栈数据
        List<T> lists = new ArrayList<>();
        try {
            // 添加前进索引位置到结尾之间的数据并进行降序排序
            List<T> temps = mForward.subList(realIndex + 1, size);
            Collections.reverse(temps);
            lists.addAll(temps);
        } catch (Exception e) {
            JCLogUtils.eTag(TAG, e, "gotoForward - subList");
        }
        // 是否需要把 beforeCurrent 添加到回退栈中
        if (mListener != null) {
            if (mListener.acceptCurrentToList(false, beforeCurrent)) {
                lists.add(beforeCurrent);
            }
        } else {
            lists.add(beforeCurrent);
        }
        for (int i = realIndex; i < size; i++) {
            try {
                // 一直进行移除同一索引 ( 数据长度递减 )
                mForward.remove(realIndex);
            } catch (Exception ignored) {
            }
        }
        mBack.addAll(lists);
        return forwardValue;
    }
}