package dev.base.state;

import dev.base.DevState;
import dev.utils.DevFinal;

/**
 * detail: 请求状态类
 * @author Ttt
 */
public class RequestState<T> {

    // State Object
    private final DevState<T> mState = new DevState<>();

    public RequestState() {
        setRequestNormal();
    }

    // =============
    // = 对外公开方法 =
    // =============

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

    // ==========
    // = 快捷方法 =
    // ==========

    // =======
    // = get =
    // =======

    /**
     * 判断是否默认状态 ( 暂未进行操作 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNormal() {
        return equalsState(DevFinal.INT.REQUEST_NORMAL);
    }

    /**
     * 判断是否请求中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestIng() {
        return equalsState(DevFinal.INT.REQUEST_ING);
    }

    /**
     * 判断是否请求成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestSuccess() {
        return equalsState(DevFinal.INT.REQUEST_SUCCESS);
    }

    /**
     * 判断是否请求失败
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestFail() {
        return equalsState(DevFinal.INT.REQUEST_FAIL);
    }

    /**
     * 判断是否请求异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestError() {
        return equalsState(DevFinal.INT.REQUEST_ERROR);
    }

    /**
     * 判断是否请求开始
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestStart() {
        return equalsState(DevFinal.INT.REQUEST_START);
    }

    /**
     * 判断是否重新请求
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestRestart() {
        return equalsState(DevFinal.INT.REQUEST_RESTART);
    }

    /**
     * 判断是否请求结束
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestEnd() {
        return equalsState(DevFinal.INT.REQUEST_END);
    }

    /**
     * 判断是否请求暂停
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestPause() {
        return equalsState(DevFinal.INT.REQUEST_PAUSE);
    }

    /**
     * 判断是否请求恢复 ( 继续 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestResume() {
        return equalsState(DevFinal.INT.REQUEST_RESUME);
    }

    /**
     * 判断是否请求停止
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestStop() {
        return equalsState(DevFinal.INT.REQUEST_STOP);
    }

    /**
     * 判断是否请求取消
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestCancel() {
        return equalsState(DevFinal.INT.REQUEST_CANCEL);
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态 ( 暂未进行操作 )
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestNormal() {
        return setState(DevFinal.INT.REQUEST_NORMAL);
    }

    /**
     * 设置状态为请求中
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestIng() {
        return setState(DevFinal.INT.REQUEST_ING);
    }

    /**
     * 设置状态为请求成功
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestSuccess() {
        return setState(DevFinal.INT.REQUEST_SUCCESS);
    }

    /**
     * 设置状态为请求失败
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestFail() {
        return setState(DevFinal.INT.REQUEST_FAIL);
    }

    /**
     * 设置状态为请求异常
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestError() {
        return setState(DevFinal.INT.REQUEST_ERROR);
    }

    /**
     * 设置状态为请求开始
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestStart() {
        return setState(DevFinal.INT.REQUEST_START);
    }

    /**
     * 设置状态为重新请求
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestRestart() {
        return setState(DevFinal.INT.REQUEST_RESTART);
    }

    /**
     * 设置状态为请求结束
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestEnd() {
        return setState(DevFinal.INT.REQUEST_END);
    }

    /**
     * 设置状态为请求暂停
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestPause() {
        return setState(DevFinal.INT.REQUEST_PAUSE);
    }

    /**
     * 设置状态为请求恢复 ( 继续 )
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestResume() {
        return setState(DevFinal.INT.REQUEST_RESUME);
    }

    /**
     * 设置状态为请求停止
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestStop() {
        return setState(DevFinal.INT.REQUEST_STOP);
    }

    /**
     * 设置状态为请求取消
     * @return {@link RequestState}
     */
    public RequestState<T> setRequestCancel() {
        return setState(DevFinal.INT.REQUEST_CANCEL);
    }
}