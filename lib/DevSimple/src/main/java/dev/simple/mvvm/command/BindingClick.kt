package dev.simple.mvvm.command

/**
 * detail: DataBinding 点击事件
 * @author Ttt
 */
interface BindingClick<T> {

    fun onClick(value: T)
}