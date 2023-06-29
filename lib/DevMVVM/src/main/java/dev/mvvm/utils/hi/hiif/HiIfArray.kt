@file:Suppress("unused")
@file:JvmName("HiIfArrayKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalContracts::class)

package dev.mvvm.utils.hi.hiif

import dev.mvvm.utils.hi.HiInlineOnly
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

// =================
// = 数组通用扩展函数 =
// =================

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> Array<out T>?.hiIfNotNullOrEmpty(
    hiIf: (Array<out T>) -> Unit
): Array<out T>? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun <T> Array<out T>?.hiIfNotNullOrEmpty(
    hiIf: (Array<out T>) -> Unit,
    hiIfNot: () -> Unit
): Array<out T>? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

// =

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun IntArray?.hiIfNotNullOrEmpty(
    hiIf: (IntArray) -> Unit
): IntArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun IntArray?.hiIfNotNullOrEmpty(
    hiIf: (IntArray) -> Unit,
    hiIfNot: () -> Unit
): IntArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun ByteArray?.hiIfNotNullOrEmpty(
    hiIf: (ByteArray) -> Unit
): ByteArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun ByteArray?.hiIfNotNullOrEmpty(
    hiIf: (ByteArray) -> Unit,
    hiIfNot: () -> Unit
): ByteArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun CharArray?.hiIfNotNullOrEmpty(
    hiIf: (CharArray) -> Unit
): CharArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun CharArray?.hiIfNotNullOrEmpty(
    hiIf: (CharArray) -> Unit,
    hiIfNot: () -> Unit
): CharArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun ShortArray?.hiIfNotNullOrEmpty(
    hiIf: (ShortArray) -> Unit
): ShortArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun ShortArray?.hiIfNotNullOrEmpty(
    hiIf: (ShortArray) -> Unit,
    hiIfNot: () -> Unit
): ShortArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun LongArray?.hiIfNotNullOrEmpty(
    hiIf: (LongArray) -> Unit
): LongArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun LongArray?.hiIfNotNullOrEmpty(
    hiIf: (LongArray) -> Unit,
    hiIfNot: () -> Unit
): LongArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun FloatArray?.hiIfNotNullOrEmpty(
    hiIf: (FloatArray) -> Unit
): FloatArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun FloatArray?.hiIfNotNullOrEmpty(
    hiIf: (FloatArray) -> Unit,
    hiIfNot: () -> Unit
): FloatArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun DoubleArray?.hiIfNotNullOrEmpty(
    hiIf: (DoubleArray) -> Unit
): DoubleArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun DoubleArray?.hiIfNotNullOrEmpty(
    hiIf: (DoubleArray) -> Unit,
    hiIfNot: () -> Unit
): DoubleArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}

/**
 * 数组不为 null or empty 时触发 [hiIf]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun BooleanArray?.hiIfNotNullOrEmpty(
    hiIf: (BooleanArray) -> Unit
): BooleanArray? {
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
 * 数组不为 null or empty 时触发 [hiIf] 反之触发 [hiIfNot]
 * @param hiIf 数组不为 null or empty 执行方法体
 * @param hiIfNot 数组为 null or empty 执行方法体
 * @return 当前数组
 */
@JvmSynthetic
@HiInlineOnly
inline fun BooleanArray?.hiIfNotNullOrEmpty(
    hiIf: (BooleanArray) -> Unit,
    hiIfNot: () -> Unit
): BooleanArray? {
    contract {
        callsInPlace(hiIf, InvocationKind.AT_MOST_ONCE)
        callsInPlace(hiIfNot, InvocationKind.AT_MOST_ONCE)
    }
    if (this != null && this.isNotEmpty()) {
        hiIf(this)
    } else {
        hiIfNot()
    }
    return this
}