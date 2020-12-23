package dev.callback.result;

import dev.callback.AbstractCommonCallback;

/**
 * detail: 通用结果回调类
 * @author Ttt
 */
public abstract class DevResultCallback<T> extends AbstractCommonCallback<T> {

    public DevResultCallback() {
    }

    public DevResultCallback(T value) {
        super(value);
    }

    public DevResultCallback(
            T value,
            Object object
    ) {
        super(value, object);
    }

    public DevResultCallback(
            T value,
            Object object,
            String tag
    ) {
        super(value, object, tag);
    }

    // ===========
    // = 回调方法 =
    // ===========

    /**
     * 结果回调通知
     * @param str   返回数据
     * @param msg   返回信息
     * @param value 返回值
     */
    @Override
    public abstract void onResult(
            String str,
            String msg,
            T value
    );
}