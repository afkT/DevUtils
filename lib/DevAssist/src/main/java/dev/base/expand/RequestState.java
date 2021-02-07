package dev.base.expand;

import dev.base.DevState;

/**
 * detail: 请求状态类
 * @author Ttt
 */
public class RequestState<T> {

    private static final int BASE           = 102030;
    // 默认状态 ( 暂未进行操作 )
    public static final  int REQUEST_NORMAL = BASE + 1;
    // 未请求过
    public static final  int REQUEST_NEVER  = BASE + 2;
    // 请求中
    public static final  int REQUEST_ING    = BASE + 3;
    // 请求成功
    public static final  int REQUEST_SUC    = BASE + 4;
    // 请求失败
    public static final  int REQUEST_FAIL   = BASE + 5;
    // 请求异常
    public static final  int REQUEST_ERROR  = BASE + 6;

    // State Object
    private DevState<T> mState = new DevState<>();

    public RequestState() {
        setRequestNormal();
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取请求类型
     * @return 请求类型
     */
    public T getType() {
        return mState.getObject();
    }

    /**
     * 设置请求类型
     * @param type 请求类型
     * @return {@link RequestState}
     */
    public RequestState<T> setType(final T type) {
        mState.setObject(type);
        return this;
    }

    /**
     * 判断请求类型是否一致
     * @param type 待校验请求类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsType(final T type) {
        return mState.equalsObject(type);
    }

    // =

    /**
     * 获取请求 UUID
     * @return 请求 UUID
     */
    public long getRequestUUID() {
        return mState.getTokenUUID();
    }

    /**
     * 获取请求 UUID ( 随机生成并赋值 )
     * @return 请求 UUID
     */
    public long randomRequestUUID() {
        return mState.randomTokenUUID();
    }

    /**
     * 判断 UUID 是否一致
     * @param uuid 待校验请求 UUID
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsRequestUUID(final long uuid) {
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
     * @return {@link RequestState}
     */
    public RequestState<T> setState(final int state) {
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

    // ===========
    // = 快捷方法 =
    // ===========

    /**
     * 判断是否默认状态
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNormal() {
        return equalsState(REQUEST_NORMAL);
    }

    /**
     * 判断是否未请求过
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNever() {
        return equalsState(REQUEST_NEVER);
    }

    /**
     * 判断是否请求中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestIng() {
        return equalsState(REQUEST_ING);
    }

    /**
     * 判断是否请求成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestSuccess() {
        return equalsState(REQUEST_SUC);
    }

    /**
     * 判断是否请求失败
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestFail() {
        return equalsState(REQUEST_FAIL);
    }

    /**
     * 判断是否请求异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestError() {
        return equalsState(REQUEST_ERROR);
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestNormal() {
        return setState(REQUEST_NORMAL);
    }

    /**
     * 设置状态为未请求过
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestNever() {
        return setState(REQUEST_NEVER);
    }

    /**
     * 设置状态为请求中
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestIng() {
        return setState(REQUEST_ING);
    }

    /**
     * 设置状态为请求成功
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestSuccess() {
        return setState(REQUEST_SUC);
    }

    /**
     * 设置状态为请求失败
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestFail() {
        return setState(REQUEST_FAIL);
    }

    /**
     * 设置状态为请求异常
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestError() {
        return setState(REQUEST_ERROR);
    }
}