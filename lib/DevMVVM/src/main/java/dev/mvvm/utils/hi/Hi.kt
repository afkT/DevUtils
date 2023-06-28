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
        hiIfNot = { }
    )
}

/**
 * 目标对象 T 不为 null 触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 非 null 执行方法体
 * @param hiIfNot 为 null 执行方法体
 * @return 目标对象 T
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T?.hiIfNotNull(
    hiIf: (T) -> Unit,
    hiIfNot: () -> Unit
): T? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        hiIf(this)
        return this
    }
    hiIfNot()
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
 * 目标对象 T 不为 null 触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 非 null 执行方法体
 * @param hiIfNot 为 null 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T?.hiIfNotNullWith(
    hiIf: (T) -> R,
    hiIfNot: () -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    return if (this != null) {
        hiIf(this)
    } else {
        hiIfNot()
    }
}

// =

/**
 * 目标对象 T 不为 null 触发 [hiIf]
 * @param default 默认返回值
 * @param hiIf 非 null 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T.hiIfMap(
    default: R,
    hiIf: (T) -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIfMap(
        hiIf = hiIf,
        hiIfNot = { default }
    )
}

/**
 * 目标对象 T 不为 null 触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 非 null 执行方法体
 * @param hiIfNot 为 null 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T.hiIfMap(
    hiIf: (T) -> R,
    hiIfNot: (T) -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null) {
        return hiIf(this)
    }
    return hiIfNot(this)
}

/**
 * 目标对象 T 执行 [hiIf] 方法体
 * @param given 执行 [hiIf] 方法体条件
 * @param default 默认返回值
 * @param hiIf [given] 为 true 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T.hiIfMap(
    given: Boolean?,
    default: R,
    hiIf: (T) -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIfMap(
        given = given,
        hiIf = hiIf,
        hiIfNot = { default }
    )
}

/**
 * 目标对象 T 执行 [hiIf]、[hiIfNot] 方法体
 * @param given 执行 [hiIf]、[hiIfNot] 方法体条件
 * @param hiIf [given] 为 true 执行方法体
 * @param hiIfNot [given] 为 false 执行方法体
 * @return 方法体返回所需对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T, R> T.hiIfMap(
    given: Boolean?,
    hiIf: (T) -> R,
    hiIfNot: (T) -> R
): R {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (given == true) {
        return hiIf(this)
    }
    return hiIfNot(this)
}