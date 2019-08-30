package dev.assist;

import java.util.UUID;

import dev.utils.common.ObjectUtils;

/**
 * detail: 请求状态辅助类
 * @author Ttt
 */
public class RequestStatusAssist<T> {

    private static final int BASE = 102030;
    // 默认状态 ( 暂未进行操作 )
    public static final int REQUEST_NORMAL = BASE + 1;
    // 未请求过
    public static final int REQUEST_NEVER = BASE + 2;
    // 请求中
    public static final int REQUEST_ING = BASE + 3;
    // 请求成功
    public static final int REQUEST_SUC = BASE + 4;
    // 请求失败
    public static final int REQUEST_FAIL = BASE + 5;
    // 请求异常
    public static final int REQUEST_ERROR = BASE + 6;

    // 请求类型
    private T mRequestType;
    // 请求 HashCode
    private long mRequestHashCode = UUID.randomUUID().hashCode();
    // 请求状态 - 默认状态 ( 暂未进行操作 )
    private int mRequestStatus = REQUEST_NORMAL;

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
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestType(final T requestType) {
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
     * 获取请求 HashCode - 随机生成并赋值
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
    public int getRequestStatus() {
        return mRequestStatus;
    }

    /**
     * 获取请求状态
     * @param requestStatus 请求状态
     * @return 请求状态
     */
    public int getRequestStatus(final int requestStatus) {
        return (this.mRequestStatus = requestStatus);
    }

    /**
     * 设置请求状态
     * @param requestStatus 请求状态
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestStatus(final int requestStatus) {
        this.mRequestStatus = requestStatus;
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
        return this.mRequestStatus == REQUEST_NORMAL;
    }

    /**
     * 判断是否未请求过
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestNever() {
        return this.mRequestStatus == REQUEST_NEVER;
    }

    /**
     * 判断是否请求中
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestIng() {
        return this.mRequestStatus == REQUEST_ING;
    }

    /**
     * 判断是否请求成功
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestSuccess() {
        return this.mRequestStatus == REQUEST_SUC;
    }

    /**
     * 判断是否请求失败
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestFail() {
        return this.mRequestStatus == REQUEST_FAIL;
    }

    /**
     * 判断是否请求异常
     * @return {@code true} yes, {@code false} no
     */
    public boolean isRequestError() {
        return this.mRequestStatus == REQUEST_ERROR;
    }

    // =======
    // = set =
    // =======

    /**
     * 设置状态为默认状态 ( 暂未进行操作 )
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestNormal() {
        this.mRequestStatus = REQUEST_NORMAL;
        return this;
    }

    /**
     * 设置状态为未请求过
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestNever() {
        this.mRequestStatus = REQUEST_NEVER;
        return this;
    }

    /**
     * 设置状态为请求中
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestIng() {
        this.mRequestStatus = REQUEST_ING;
        return this;
    }

    /**
     * 设置状态为请求成功
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestSuccess() {
        this.mRequestStatus = REQUEST_SUC;
        return this;
    }

    /**
     * 设置状态为请求失败
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestFail() {
        this.mRequestStatus = REQUEST_FAIL;
        return this;
    }

    /**
     * 设置状态为请求异常
     * @return {@link RequestStatusAssist}
     */
    public RequestStatusAssist<T> setRequestError() {
        this.mRequestStatus = REQUEST_ERROR;
        return this;
    }
}