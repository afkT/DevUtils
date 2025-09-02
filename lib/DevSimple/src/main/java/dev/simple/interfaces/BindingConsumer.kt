package dev.simple.interfaces

/**
 * detail: DataBinding 消费事件
 * @author Ttt
 */
interface BindingConsumer<T> {

    /**
     * 传入指定参数执行操作
     * @param value T
     */
    fun accept(value: T)
}