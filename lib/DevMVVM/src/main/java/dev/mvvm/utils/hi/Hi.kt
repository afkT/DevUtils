@file:Suppress("unused")
@file:JvmName("HiKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalContracts::class)

package dev.mvvm.utils.hi

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// ==============
// = 通用扩展函数 =
// ==============

/**
 * 目标对象 T 不为 null 触发 [hiIf] 反之触发 [hiIfNull]
 * @param hiIf 非 null 执行方法体
 * @param hiIfNull 为 null 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T?.hiIfNotNullWith(
    hiIf: (T) -> R,
    hiIfNull: () -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNull, InvocationKind.AT_MOST_ONCE)
    }
    return if (this != null) {
        hiIf.invoke(this)
    } else {
        hiIfNull.invoke()
    }
}