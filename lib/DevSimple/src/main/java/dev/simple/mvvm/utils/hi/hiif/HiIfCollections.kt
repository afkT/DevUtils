@file:Suppress("unused")
@file:JvmName("HiIfCollectionsKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalContracts::class)

package dev.simple.mvvm.utils.hi.hiif

import dev.simple.mvvm.utils.hi.HiInlineOnly
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// =================
// = 集合通用扩展函数 =
// =================

/**
 * 集合不为 null or empty 时触发 [hiIf]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> List<T>?.hiIfNotNullOrEmpty(
    hiIf: (List<T>) -> Unit
): List<T>? {
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
 * 集合不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @param hiIfNot 集合为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> List<T>?.hiIfNotNullOrEmpty(
    hiIf: (List<T>) -> Unit,
    hiIfNot: () -> Unit
): List<T>? {
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

/**
 * 集合不为 null or empty 时触发 [hiIf]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> Set<T>?.hiIfNotNullOrEmpty(
    hiIf: (Set<T>) -> Unit
): Set<T>? {
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
 * 集合不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @param hiIfNot 集合为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> Set<T>?.hiIfNotNullOrEmpty(
    hiIf: (Set<T>) -> Unit,
    hiIfNot: () -> Unit
): Set<T>? {
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

/**
 * 集合不为 null or empty 时触发 [hiIf]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T, R> Map<T, R>?.hiIfNotNullOrEmpty(
    hiIf: (Map<T, R>) -> Unit
): Map<T, R>? {
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
 * 集合不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 集合不为 null or empty 执行方法体
 * @param hiIfNot 集合为 null or empty 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T, R> Map<T, R>?.hiIfNotNullOrEmpty(
    hiIf: (Map<T, R>) -> Unit,
    hiIfNot: () -> Unit
): Map<T, R>? {
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

// =============
// = Operation =
// =============

// =======
// = add =
// =======

/**
 * [element] 不为 null 时进行添加并触发 [hiIf]
 * @param element 待添加元素
 * @param hiIf [element] 不为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addHiIfNotNull(
    element: E?,
    hiIf: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    this.addHiIfNotNull(
        element = element,
        hiIf = hiIf,
        hiIfNot = { }
    )
    return this
}

/**
 * [element] 不为 null 时进行添加并触发 [hiIf] 反之触发 [hiIfNot]
 * @param element 待添加元素
 * @param hiIf [element] 不为 null 执行方法体
 * @param hiIfNot [element] 为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addHiIfNotNull(
    element: E?,
    hiIf: (T) -> Unit,
    hiIfNot: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    element?.hiIfNotNullAs<E>(
        hiIf = {
            add(it)
            hiIf(this)
        },
        hiIfNot = {
            hiIfNot(this)
        }
    )
    return this
}

/**
 * [element] 不为 null 时进行添加并触发 [hiIf]
 * @param element 待添加元素集合
 * @param hiIf [element] 不为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addAllHiIfNotNull(
    element: Collection<E>?,
    hiIf: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    this.addAllHiIfNotNull(
        element = element,
        hiIf = hiIf,
        hiIfNot = { }
    )
    return this
}

/**
 * [element] 不为 null 时进行添加并触发 [hiIf] 反之触发 [hiIfNot]
 * @param element 待添加元素集合
 * @param hiIf [element] 不为 null 执行方法体
 * @param hiIfNot [element] 为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addAllHiIfNotNull(
    element: Collection<E>?,
    hiIf: (T) -> Unit,
    hiIfNot: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    element?.hiIfNotNull(
        hiIf = {
            addAll(it)
            hiIf(this)
        },
        hiIfNot = {
            hiIfNot(this)
        }
    )
    return this
}

// ==========
// = remove =
// ==========

/**
 * [element] 不为 null 时进行移除并触发 [hiIf]
 * @param element 待移除元素
 * @param hiIf [element] 不为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeHiIfNotNull(
    element: E?,
    hiIf: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    this.removeHiIfNotNull(
        element = element,
        hiIf = hiIf,
        hiIfNot = { }
    )
    return this
}

/**
 * [element] 不为 null 时进行移除并触发 [hiIf] 反之触发 [hiIfNot]
 * @param element 待移除元素
 * @param hiIf [element] 不为 null 执行方法体
 * @param hiIfNot [element] 为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeHiIfNotNull(
    element: E?,
    hiIf: (T) -> Unit,
    hiIfNot: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    element?.hiIfNotNullAs<E>(
        hiIf = {
            remove(it)
            hiIf(this)
        },
        hiIfNot = {
            hiIfNot(this)
        }
    )
    return this
}

/**
 * [element] 不为 null 时进行移除并触发 [hiIf]
 * @param element 待移除元素集合
 * @param hiIf [element] 不为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeAllHiIfNotNull(
    element: Collection<E>?,
    hiIf: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    this.removeAllHiIfNotNull(
        element = element,
        hiIf = hiIf,
        hiIfNot = { }
    )
    return this
}

/**
 * [element] 不为 null 时进行移除并触发 [hiIf] 反之触发 [hiIfNot]
 * @param element 待移除元素集合
 * @param hiIf [element] 不为 null 执行方法体
 * @param hiIfNot [element] 为 null 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeAllHiIfNotNull(
    element: Collection<E>?,
    hiIf: (T) -> Unit,
    hiIfNot: (T) -> Unit
): T {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    element?.hiIfNotNull(
        hiIf = {
            removeAll(it)
            hiIf(this)
        },
        hiIfNot = {
            hiIfNot(this)
        }
    )
    return this
}

// ============
// = Iterable =
// ============

/**
 * boolean list 都为 true 则触发 [hiIf]
 * @param hiIf 都为 true 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun Iterable<Boolean?>.hiIfAnd(
    hiIf: () -> Unit
): Iterable<Boolean?> {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIfAnd(
        hiIf = hiIf,
        hiIfNot = { }
    )
}

/**
 * boolean list 都为 true 则触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 都为 true 执行方法体
 * @param hiIfNot 其中存在 null or false 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun Iterable<Boolean?>.hiIfAnd(
    hiIf: () -> Unit,
    hiIfNot: (() -> Unit)
): Iterable<Boolean?> {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    var predicate: Boolean? = null

    this.forEach {
        val temp = predicate
        predicate = if (temp == null) {
            it
        } else {
            temp and (it == true)
        }
    }

    if (predicate == true) {
        hiIf()
    } else {
        hiIfNot()
    }
    return this
}

/**
 * boolean list 其中一个为 true 则触发 [hiIf]
 * @param hiIf 其中一个为 true 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun Iterable<Boolean?>.hiIfOr(
    hiIf: () -> Unit
): Iterable<Boolean?> {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
    }
    return this.hiIfOr(
        hiIf = hiIf,
        hiIfNot = { }
    )
}

/**
 * boolean list 其中一个为 true 则触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 其中一个为 true 执行方法体
 * @param hiIfNot 不存在 true 执行方法体
 * @return 调用对象
 */
@JvmSynthetic
@HiInlineOnly
inline fun Iterable<Boolean?>.hiIfOr(
    hiIf: () -> Unit,
    hiIfNot: (() -> Unit)
): Iterable<Boolean?> {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    var predicate: Boolean? = null

    this.forEach {
        val temp = predicate
        predicate = if (temp == null) {
            it
        } else {
            temp or (it == true)
        }
    }

    if (predicate == true) {
        hiIf()
    } else {
        hiIfNot()
    }
    return this
}