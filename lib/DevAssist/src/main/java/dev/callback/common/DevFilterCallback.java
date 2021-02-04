package dev.callback.common;

import dev.callback.AbstractCallback;

/**
 * detail: 过滤处理回调类
 * @author Ttt
 */
public class DevFilterCallback<T>
        extends AbstractCallback<T> {

    public DevFilterCallback() {
    }

    public DevFilterCallback(T value) {
        super(value);
    }

    public DevFilterCallback(
            T value,
            Object object
    ) {
        super(value, object);
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 过滤处理
     * @param value 待处理值
     * @return 过滤处理的值
     */
    public T filter(T value) {
        return value;
    }

    /**
     * 过滤处理
     * @param values 待处理数组值
     * @return 过滤处理的数组值
     */
    public T[] filter(T[] values) {
        return values;
    }

    // =

    /**
     * 判断是否过滤
     * @param value 待判断值
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFilter(T value) {
        return false;
    }

    /**
     * 判断是否过滤
     * @param values 待判断值
     * @return {@code true} yes, {@code false} no
     */
    public boolean isFilter(T[] values) {
        return false;
    }

    // =

    /**
     * 对比过滤处理
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

    /**
     * 对比过滤处理
     * @param values  待对比值
     * @param values1 待对比值
     * @return {@code true} yes, {@code false} no
     */
    public boolean compare(
            T[] values,
            T[] values1
    ) {
        return false;
    }
}