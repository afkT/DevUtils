@file:Suppress("unused")
@file:JvmName("HiIfBooleanKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalContracts::class)

package dev.mvvm.utils.hiif

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// =====================
// = Boolean 通用扩展函数 =
// =====================

/**
 * 当 [given] 返回为 true 时触发 [hiIf]
 * @param given 执行 [hiIf] 方法体条件
 * @param hiIf [given] 为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: (T) -> Boolean?,
    hiIf: () -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    if (given(this) == true) {
        hiIf()
    }
    return this
}

/**
 * 当 [given] 返回为 true 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param given 执行 [hiIf] 方法体条件
 * @param hiIf [given] 为 true 执行方法体
 * @param hiIfNot [given] 为 false 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: (T) -> Boolean?,
    hiIf: () -> Unit,
    hiIfNot: () -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (given(this) == true) {
        hiIf()
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 当 [given] 为 true 时在 [apply] 中触发 [hiIf]
 * @param given 执行 [hiIf] 方法体条件
 * @param hiIf [given] 为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: Boolean?,
    hiIf: T.() -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    if (given == true) {
        this.apply { hiIf() }
    }
    return this
}

/**
 * 当 [given] 为 true 时在 [apply] 中触发 [hiIf] 反之触发 [hiIfNot]
 * @param given 执行 [hiIf]、[hiIfNot] 方法体条件
 * @param hiIf [given] 为 true 执行方法体
 * @param hiIfNot [given] 为 false 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: Boolean?,
    hiIf: T.() -> Unit,
    hiIfNot: T.() -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (given == true) {
        this.apply { hiIf() }
    } else {
        this.apply { hiIfNot() }
    }
    return this
}

/**
 * 当 [given] 返回为 true 时触发 [hiIfDo]
 * @param given 执行 [hiIfDo] 方法体条件
 * @param hiIfDo [given] 为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: () -> Boolean?,
    hiIfDo: T.() -> Unit
): T {
    contract {
        callsInPlace(hiIfDo, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIf(
        given = given,
        hiIfDo = hiIfDo,
        hiIfNot = { }
    )
}

/**
 * 当 [given] 返回为 true 时触发 [hiIfDo] 反之触发 [hiIfNot]
 * @param given 执行 [hiIfDo]、[hiIfNot] 方法体条件
 * @param hiIfDo [given] 为 true 执行方法体
 * @param hiIfNot [given] 为 false 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun <T> T.hiIf(
    given: () -> Boolean?,
    hiIfDo: T.() -> Unit,
    hiIfNot: T.() -> Unit
): T {
    contract {
        callsInPlace(hiIfDo, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (given() == true) {
        this.hiIfDo()
    } else {
        this.hiIfNot()
    }
    return this
}

/**
 * 调用对象值为 true 时触发 [hiIf]
 * @param hiIf 调用对象为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun Boolean?.hiIf(
    hiIf: () -> Unit
): Boolean? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIf(
        hiIf = hiIf,
        hiIfNot = { }
    )
}

/**
 * 调用对象值为 true 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 调用对象为 true 执行方法体
 * @param hiIfNot 调用对象为 false 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun Boolean?.hiIf(
    hiIf: () -> Unit,
    hiIfNot: () -> Unit
): Boolean? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this == true) {
        hiIf()
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 调用对象值为 false 时触发 [hiIf]
 * @param hiIf 调用对象为 false 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun Boolean?.hiIfElse(
    hiIf: () -> Unit
): Boolean? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    if (this == false) {
        hiIf()
    }
    return this
}

/**
 * 调用对象值为 true 并且 [predicate] 为 true 时触发 [hiIf]
 * @param predicate 额外判断值
 * @param hiIf 都为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun Boolean?.hiIfAnd(
    predicate: Boolean?,
    hiIf: () -> Unit
): Boolean? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    if (this == true && predicate == true) {
        hiIf()
    }
    return this
}

/**
 * 调用对象值为 true 或 [predicate] 为 true 时触发 [hiIf]
 * @param predicate 额外判断值
 * @param hiIf 其中一个值为 true 执行方法体
 * @return 目标对象
 */
@JvmSynthetic
@DevInlineOnly
inline fun Boolean?.hiIfOr(
    predicate: Boolean?,
    hiIf: () -> Unit
): Boolean? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    if (this == true || predicate == true) {
        hiIf()
    }
    return this
}