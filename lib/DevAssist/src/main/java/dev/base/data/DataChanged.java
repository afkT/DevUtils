package dev.base.data;

/**
 * detail: 数据改变通知
 * @param <T> 泛型
 * @author Ttt
 */
public interface DataChanged<T> {

    // ===========
    // = 通知方法 =
    // ===========

    /**
     * 通知数据改变
     */
    void notifyDataChanged();

    /**
     * 通知某个数据改变
     * @param value {@link T}
     */
    void notifyElementChanged(T value);
}