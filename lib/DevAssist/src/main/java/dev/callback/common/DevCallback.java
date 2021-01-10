package dev.callback.common;

import dev.callback.AbstractCallback;

/**
 * detail: Dev 回调基类
 * @author Ttt
 */
public class DevCallback<T>
        extends AbstractCallback<T> {

    public DevCallback() {
    }

    public DevCallback(T value) {
        super(value);
    }

    public DevCallback(
            T value,
            Object object
    ) {
        super(value, object);
    }

    public DevCallback(
            T value,
            Object object,
            String tag
    ) {
        super(value, object, tag);
    }

    // ===========
    // = 通用方法 =
    // ===========

    /**
     * 回调方法
     */
    public void callback() {
    }

    /**
     * 回调方法
     * @param value 回调值
     */
    public void callback(T value) {
    }

    /**
     * 回调方法
     * @param value 回调值
     * @param param 回调参数
     */
    public void callback(
            T value,
            int param
    ) {
    }

    /**
     * 回调方法
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void callback(
            T value,
            Object[] objects
    ) {
    }

    /**
     * 回调方法
     * @param value   回调值
     * @param param   回调参数
     * @param objects 回调对象数组
     */
    public void callback(
            T value,
            int param,
            Object[] objects
    ) {
    }
}