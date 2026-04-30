package dev.callback;

/**
 * detail: Dev 通用回调
 * @author Ttt
 */
public class DevCallback<T>
        extends BaseCallback<T> {

    public DevCallback() {
    }

    public DevCallback(final T object) {
        super(object);
    }

    public DevCallback(
            final T object,
            final Object tag
    ) {
        super(object, tag);
    }

    // ==========
    // = 通用方法 =
    // ==========

    /**
     * 投递回调（无参）
     */
    public void deliver() {
    }

    /**
     * 投递回调值
     * @param value 回调值
     */
    public void deliver(T value) {
    }

    /**
     * 投递回调值与附加参数
     * @param value 回调值
     * @param param 回调参数
     */
    public void deliver(
            T value,
            int param
    ) {
    }

    // ==========
    // = 过滤方法 =
    // ==========

    /**
     * 过滤处理
     * @param value 待处理值
     * @return 过滤处理的值
     */
    public T filter(T value) {
        return value;
    }

    /**
     * 判断是否过滤
     * @param value 待判断值
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFilter(T value) {
        return false;
    }

    /**
     * 对比判断
     * @param value  待对比值
     * @param value1 待对比值
     * @return {@code true} yes, {@code false} no
     */
    public boolean compare(
            T value,
            T value1
    ) {
        return false;
    }
}