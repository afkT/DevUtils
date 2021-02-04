package dev.callback;

/**
 * detail: 通用结果回调类
 * @author Ttt
 */
public abstract class DevResultCallback<T>
        extends DevCallback<T> {

    public DevResultCallback() {
    }

    public DevResultCallback(final T value) {
        super(value);
    }

    public DevResultCallback(
            final T value,
            final Object object
    ) {
        super(value, object);
    }

    // ===========
    // = 结果回调 =
    // ===========

    /**
     * 结果回调通知
     * @param data    返回数据
     * @param message 返回信息
     * @param value   返回值
     */
    public abstract void onResult(
            String data,
            String message,
            T value
    );

    /**
     * 异常回调通知
     * @param e   异常信息
     * @param <E> 泛型
     */
    public <E extends Throwable> void onError(E e) {
    }

    /**
     * 失败回调通知
     * @param fail      失败信息
     * @param errorCode 错误 code
     */
    public void onFailure(
            String fail,
            String errorCode
    ) {
    }
}