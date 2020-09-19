package dev.assist;

import java.util.UUID;

import dev.utils.common.ObjectUtils;

/**
 * detail: 请求状态辅助类
 * @author Ttt
 */
public class RequestStateAssist<T> {

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

    // 请求类型
    private T    mRequestType;
    // 请求 HashCode
    private long mRequestHashCode = UUID.randomUUID().hashCode();
    // 请求状态 ( 默认为暂未进行操作 )
    private int  mRequestState    = REQUEST_NORMAL;

    // ================
    // = 对外公开方法 =
    // ================

    /**
     * 获取请求类型
     * @return 请求类型
     */
    public T getRequestType() {
        return mRequestType;
    }

    /**
     * 设置请求类型
     * @param requestType 请求类型
     * @return 请求类型
     */
    public T getRequestType(final T requestType) {
        return (this.mRequestType = requestType);
    }

    /**
     * 设置请求类型
     * @param requestType 请求类型
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestType(final T requestType) {
        this.mRequestType = requestType;
        return this;
    }

    /**
     * 判断请求类型是否一致
     * @param requestType 待校验请求类型
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsRequestType(final T requestType) {
        return this.mRequestType != null && requestType != null && ObjectUtils.equals(this.mRequestType, requestType);
    }

    // =

    /**
     * 获取请求 HashCode
     * @return 请求 HashCode
     */
    public long getRequestHashCode() {
        return mRequestHashCode;
    }

    /**
     * 获取请求 HashCode ( 随机生成并赋值 )
     * @return 请求 HashCode
     */
    public long getRequestHashCodeRandom() {
        return (this.mRequestHashCode = UUID.randomUUID().hashCode());
    }

    /**
     * 判断 HashCode 是否一致
     * @param hashCode 待校验请求 HashCode
     * @return {@code true} yes, {@code false} no
     */
    public boolean equalsHashCode(final long hashCode) {
        return this.mRequestHashCode == hashCode;
    }

    // =

    /**
     * 获取请求状态
     * @return 请求状态
     */
    public int getRequestState() {
        return mRequestState;
    }

    /**
     * 获取请求状态
     * @param requestState 请求状态
     * @return 请求状态
     */
    public int getRequestState(final int requestState) {
        return (this.mRequestState = requestState);
    }

    /**
     * 设置请求状态
     * @param requestState 请求状态
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestState(final int requestState) {
        this.mRequestState = requestState;
        return this;
    }

    // ============
    // = 快捷方法 =
    // ============

    /**
     * 判断是否默认状态 ( 暂未进行操作 )
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNormal() {
        return this.mRequestState == REQUEST_NORMAL;
    }

    /**
     * 判断是否未请求过
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNever() {
        return this.mRequestState == REQUEST_NEVER;
    }

    /**
     * 判断是否请求中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestIng() {
        return this.mRequestState == REQUEST_ING;
    }

    /**
     * 判断是否请求成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestSuccess() {
        return this.mRequestState == REQUEST_SUC;
    }

    /**
     * 判断是否请求失败
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestFail() {
        return this.mRequestState == REQUEST_FAIL;
    }

    /**
     * 判断是否请求异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestError() {
        return this.mRequestState == REQUEST_ERROR;
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态 ( 暂未进行操作 )
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestNormal() {
        this.mRequestState = REQUEST_NORMAL;
        return this;
    }

    /**
     * 设置状态为未请求过
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestNever() {
        this.mRequestState = REQUEST_NEVER;
        return this;
    }

    /**
     * 设置状态为请求中
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestIng() {
        this.mRequestState = REQUEST_ING;
        return this;
    }

    /**
     * 设置状态为请求成功
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestSuccess() {
        this.mRequestState = REQUEST_SUC;
        return this;
    }

    /**
     * 设置状态为请求失败
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestFail() {
        this.mRequestState = REQUEST_FAIL;
        return this;
    }

    /**
     * 设置状态为请求异常
     * @return {@link RequestStateAssist}
     */
    public RequestStateAssist<T> setRequestError() {
        this.mRequestState = REQUEST_ERROR;
        return this;
    }
}