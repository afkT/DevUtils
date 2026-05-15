@file:Suppress("unused")
@file:JvmName("QualifiesStateFlowExtKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalUnsignedTypes::class)

package dev.simple.extensions.qualifies

import android.os.Build
import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.StateFlow
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

// =============
// = StateFlow =
// =============
//
// 因 JVM 擦除，[StateFlow] 的多个泛型实参在字节码上均为原始 [StateFlow]，
// 故每个 [qualifies] 重载需使用唯一 [@JvmName]（Kotlin 调用处仍写 `qualifies()`）。
//
// 与 [androidx.lifecycle.LiveData] 类似，[StateFlow] 提供同步可读 [StateFlow.value]，适合在绑定或命令判定中委托 [qualifies]；
// 一般冷 [kotlinx.coroutines.flow.Flow] 无「当前值」语义，故不纳入本文件。

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowByte")
fun StateFlow<Byte>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowShort")
fun StateFlow<Short>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowInt")
fun StateFlow<Int>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowLong")
fun StateFlow<Long>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowFloat")
fun StateFlow<Float>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowDouble")
fun StateFlow<Double>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUByte")
fun StateFlow<UByte>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUShort")
fun StateFlow<UShort>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUInt")
fun StateFlow<UInt>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowULong")
fun StateFlow<ULong>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowBigInteger")
fun StateFlow<BigInteger>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowBigDecimal")
fun StateFlow<BigDecimal>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowChar")
fun StateFlow<Char>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowBoolean")
fun StateFlow<Boolean>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifiesTrue]。 */
fun StateFlow<Boolean>?.qualifiesTrue(): Boolean =
    this != null && this.value.qualifiesTrue()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifiesFalse]。 */
fun StateFlow<Boolean>?.qualifiesFalse(): Boolean =
    this != null && this.value.qualifiesFalse()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowResult")
fun <T> StateFlow<Result<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowCharSequence")
fun StateFlow<CharSequence>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowIterable")
fun <T> StateFlow<Iterable<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowSequence")
fun <T> StateFlow<Sequence<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowIterator")
fun <T> StateFlow<Iterator<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowEnumeration")
fun <T> StateFlow<Enumeration<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]（API 24+ 与 `QualifiesExt` 一致）。 */
@RequiresApi(Build.VERSION_CODES.N)
@JvmName("qualifiesOfStateFlowOptional")
fun <T> StateFlow<Optional<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowBitSet")
fun StateFlow<BitSet>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowCollection")
fun <T> StateFlow<Collection<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]（[StateFlow] 对 `T` 不变，[List] 需单独重载）。 */
@JvmName("qualifiesOfStateFlowList")
fun <T> StateFlow<List<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]（[MutableList] 专用）。 */
@JvmName("qualifiesOfStateFlowMutableList")
fun <T> StateFlow<MutableList<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]（[Set] 专用）。 */
@JvmName("qualifiesOfStateFlowSet")
fun <T> StateFlow<Set<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]（[MutableSet] 专用）。 */
@JvmName("qualifiesOfStateFlowMutableSet")
fun <T> StateFlow<MutableSet<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowMap")
fun <K, V> StateFlow<Map<K, V>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowSparseArray")
fun <T> StateFlow<SparseArray<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowSparseBooleanArray")
fun StateFlow<SparseBooleanArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowSparseIntArray")
fun StateFlow<SparseIntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowLongSparseArray")
fun <T> StateFlow<LongSparseArray<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowArray")
fun <T> StateFlow<Array<out T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowByteArray")
fun StateFlow<ByteArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowShortArray")
fun StateFlow<ShortArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowIntArray")
fun StateFlow<IntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowLongArray")
fun StateFlow<LongArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowFloatArray")
fun StateFlow<FloatArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowDoubleArray")
fun StateFlow<DoubleArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowCharArray")
fun StateFlow<CharArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowBooleanArray")
fun StateFlow<BooleanArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUByteArray")
fun StateFlow<UByteArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUShortArray")
fun StateFlow<UShortArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowUIntArray")
fun StateFlow<UIntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [StateFlow] 非 null 时对 [StateFlow.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfStateFlowULongArray")
fun StateFlow<ULongArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()
