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
 * 目标对象 T 不为 null 触发 [hiIf]
 * @param hiIf 非 null 执行方法体
 * @return 目标对象 T
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T?.hiIfNotNull(
    hiIf: (T) -> Unit
): T? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIfNotNull(
        hiIf = hiIf,
        hiIfNull = { }
    )
}

/**
 * 目标对象 T 不为 null 触发 [hiIf] 反之触发 [hiIfNull]
 * @param hiIf 非 null 执行方法体
 * @param hiIfNull 为 null 执行方法体
 * @return 目标对象 T
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T?.hiIfNotNull(
    hiIf: (T) -> Unit,
    hiIfNull: () -> Unit
): T? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNull, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        hiIf(this)
        return this
    }
    hiIfNull()
    return this
}

/**
 * 目标对象转换 [R] 成功则触发 [hiIf]
 * @param hiIf 成功转换 [R] 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <reified R> Any?.hiIfNotNullAs(
    hiIf: (R) -> Unit
): Any? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return hiIfNotNullAs(
        hiIf = hiIf,
        hiIfNot = { }
    )
}

/**
 * 目标对象转换 [R] 成功则触发 [hiIf] 反之触发 [hiIfNot]
 *
 * ```
 * serializable.hiIfNotNullAs<User> { user ->
 *  log(poster.name)
 * }
 * ```
 *
 * @param hiIf 成功转换 [R] 执行方法体
 * @param hiIfNot 转换失败 [R] 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <reified R> Any?.hiIfNotNullAs(
    hiIf: (R) -> Unit,
    hiIfNot: () -> Unit
): Any? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this is R) {
        hiIf(this as R)
        return this
    }
    hiIfNot()
    return this
}

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