package dev.simple.agile.interfaces

/**
 * detail: 方法流程回调
 * @author Ttt
 */
interface FunctionFlowCall {

    // 执行方法体
    fun block() = run { }

    // 开始执行
    fun start() = run { }

    // 执行成功
    fun success() = run { }

    // 执行异常
    fun error() = run { }

    // 执行结束
    fun finish() = run { }
}