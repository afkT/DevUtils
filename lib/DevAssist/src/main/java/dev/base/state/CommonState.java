package dev.base.state;

import dev.base.DevState;

/**
 * detail: 通用状态类
 * @author Ttt
 */
public class CommonState<T> {

    private static final int BASE   = 102036;
    // 默认状态 ( 暂未进行操作 )
    public static final  int NORMAL = BASE + 1;

    // State Object
    private final DevState<T> mState = new DevState<>();

    public CommonState() {
        setNormal();
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取操作类型
     * @return 操作类型
     */
    public T getType() {
        return mState.getObject();
    }

    /**
     * 设置操作类型
     * @param type 操作类型
     * @return {@link CommonState}
     */
    public CommonState<T> setType(final T type) {
        mState.setObject(type);
        return this;
    }

    /**
     * 判断操作类型是否一致
     * @param type 待校验操作类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsType(final T type) {
        return mState.equalsObject(type);
    }

    // =

    /**
     * 获取操作 UUID
     * @return 操作 UUID
     */
    public long getUUID() {
        return mState.getTokenUUID();
    }

    /**
     * 获取操作 UUID ( 随机生成并赋值 )
     * @return 操作 UUID
     */
    public long randomUUID() {
        return mState.randomTokenUUID();
    }

    /**
     * 判断 UUID 是否一致
     * @param uuid 待校验操作 UUID
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsUUID(final long uuid) {
        return mState.equalsTokenUUID(uuid);
    }

    // =

    /**
     * 获取 State
     * @return State
     */
    public int getState() {
        return mState.getState();
    }

    /**
     * 设置 State
     * @param state State
     * @return {@link CommonState}
     */
    public CommonState<T> setState(final int state) {
        mState.setState(state);
        return this;
    }

    /**
     * 判断 State 是否一致
     * @param state 待校验 State
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsState(final int state) {
        return mState.equalsState(state);
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 判断是否默认状态
     * @return {@code true} yes, {@code false} no
     */
    public boolean isNormal() {
        return equalsState(NORMAL);
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态
     * @return {@link CommonState}
     */
    public CommonState<T> setNormal() {
        return setState(NORMAL);
    }
}