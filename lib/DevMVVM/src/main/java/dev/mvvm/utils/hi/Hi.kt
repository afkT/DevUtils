@file:Suppress("unused")
@file:JvmMultifileClass
@file:JvmName("HiKt")
@file:OptIn(ExperimentalContracts::class)

package dev.mvvm.utils.hi

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// ==============
// = 通用扩展函数 =
// ==============

/**
 * 在 let 基础上增加如果 null 执行方法体
 * @receiver T?
 * @param block 不为 null 执行方法体
 * @param nullBlock null 执行方法体
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T?.hiNull(
    block: (T) -> R,
    nullBlock: () -> R
): R {
    contract {
        callsInPlace(block, InvocationKind.AT_MOST_ONCE)
        callsInPlace(nullBlock, InvocationKind.AT_MOST_ONCE)
    }
    return if (this != null) {
        block.invoke(this)
    } else {
        nullBlock.invoke()
    }
}