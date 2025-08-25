@file:Suppress("unused")
@file:JvmName("HiIfStringKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalContracts::class)

package dev.simple.mvvm.utils.hi.hiif

import dev.simple.mvvm.utils.hi.HiInlineOnly
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// ==================
// = 字符串通用扩展函数 =
// ==================

/**
 * 字符串不为 null or empty 时触发 [hiIf]
 * @param hiIf 字符串不为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun String?.hiIfNotNullOrEmpty(
    hiIf: (String) -> Unit
): String? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    this.hiIfNotNullOrEmpty(
        hiIf = hiIf,
        hiIfNot = { }
    )
    return this
}

/**
 * 字符串不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 字符串不为 null or empty 执行方法体
 * @param hiIfNot 字符串为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun String?.hiIfNotNullOrEmpty(
    hiIf: (String) -> Unit,
    hiIfNot: () -> Unit
): String? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (!this.isNullOrEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}