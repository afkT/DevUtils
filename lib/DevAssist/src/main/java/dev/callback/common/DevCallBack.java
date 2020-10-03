package dev.callback.common;

import dev.callback.AbstractCallBack;

/**
 * detail: Dev 回调基类
 * @author Ttt
 */
public class DevCallBack<T> extends AbstractCallBack<T> {

    public DevCallBack() {
    }

    public DevCallBack(T value) {
        super(value);
    }

    public DevCallBack(T value, Object object) {
        super(value, object);
    }

    public DevCallBack(T value, Object object, String tag) {
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
     * @param param 回调参数
     */
    public void callback(int param) {
    }

    /**
     * 回调方法
     * @param param 回调参数
     * @param value 回调值
     */
    public void callback(int param, T value) {
    }

    /**
     * 回调方法
     * @param param   回调参数
     * @param value   回调值
     * @param objects 回调对象数组
     */
    public void callback(int param, T value, Object[] objects) {
    }
}