package dev.callback;

/**
 * detail: 通用抽象回调 - 基类
 * @author Ttt
 */
public abstract class AbstractCommonCallBack<T> extends AbstractCallBack<T> {

    public AbstractCommonCallBack() {
    }

    public AbstractCommonCallBack(T value) {
        super(value);
    }

    public AbstractCommonCallBack(T value, Object object) {
        super(value, object);
    }

    public AbstractCommonCallBack(T value, Object object, String tag) {
        super(value, object, tag);
    }

    // ============
    // = 通用方法 =
    // ============

    /**
     * 成功回调通知
     * @param str 返回数据
     */
    public void onSuccess(String str) {
    }

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
    public void onFailure(String fail, String errorCode) {
    }
}
