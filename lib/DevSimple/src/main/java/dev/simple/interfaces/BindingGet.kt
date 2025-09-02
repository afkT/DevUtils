package dev.simple.interfaces

/**
 * detail: DataBinding 通用获取接口
 * @author Ttt
 */
interface BindingGet<T> {

    /**
     * 获取泛型值
     * @return T
     */
    fun get(): T
}