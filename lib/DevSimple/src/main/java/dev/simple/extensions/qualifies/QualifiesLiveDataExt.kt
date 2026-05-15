@file:Suppress("unused")
@file:JvmName("QualifiesLiveDataExtKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalUnsignedTypes::class)

package dev.simple.extensions.qualifies

import android.os.Build
import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

// ============
// = LiveData =
// ============
//
// 因 JVM 擦除，[LiveData] 的多个泛型实参在字节码上均为原始 [LiveData]，
// 故每个 [qualifies] 重载需使用唯一 [@JvmName]（Kotlin 调用处仍写 `qualifies()`）。

/** [LiveData] 非 null 时对 [LiveData.getValue]（Kotlin [LiveData.value]）委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataByte")
fun LiveData<Byte>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataShort")
fun LiveData<Short>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataInt")
fun LiveData<Int>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataLong")
fun LiveData<Long>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataFloat")
fun LiveData<Float>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataDouble")
fun LiveData<Double>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUByte")
fun LiveData<UByte>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUShort")
fun LiveData<UShort>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUInt")
fun LiveData<UInt>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataULong")
fun LiveData<ULong>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataBigInteger")
fun LiveData<BigInteger>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataBigDecimal")
fun LiveData<BigDecimal>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataChar")
fun LiveData<Char>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataBoolean")
fun LiveData<Boolean>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifiesTrue]。 */
fun LiveData<Boolean>?.qualifiesTrue(): Boolean =
    this != null && this.value.qualifiesTrue()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifiesFalse]。 */
fun LiveData<Boolean>?.qualifiesFalse(): Boolean =
    this != null && this.value.qualifiesFalse()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataResult")
fun <T> LiveData<Result<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataCharSequence")
fun LiveData<CharSequence>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataIterable")
fun <T> LiveData<Iterable<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataSequence")
fun <T> LiveData<Sequence<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataIterator")
fun <T> LiveData<Iterator<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataEnumeration")
fun <T> LiveData<Enumeration<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]（API 24+ 与 `QualifiesExt` 一致）。 */
@RequiresApi(Build.VERSION_CODES.N)
@JvmName("qualifiesOfLiveDataOptional")
fun <T> LiveData<Optional<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataBitSet")
fun LiveData<BitSet>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataCollection")
fun <T> LiveData<Collection<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]（[LiveData] 对 `T` 不变，[List] 需单独重载）。 */
@JvmName("qualifiesOfLiveDataList")
fun <T> LiveData<List<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]（[MutableList] 专用）。 */
@JvmName("qualifiesOfLiveDataMutableList")
fun <T> LiveData<MutableList<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]（[Set] 专用）。 */
@JvmName("qualifiesOfLiveDataSet")
fun <T> LiveData<Set<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]（[MutableSet] 专用）。 */
@JvmName("qualifiesOfLiveDataMutableSet")
fun <T> LiveData<MutableSet<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataMap")
fun <K, V> LiveData<Map<K, V>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataSparseArray")
fun <T> LiveData<SparseArray<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataSparseBooleanArray")
fun LiveData<SparseBooleanArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataSparseIntArray")
fun LiveData<SparseIntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataLongSparseArray")
fun <T> LiveData<LongSparseArray<T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataArray")
fun <T> LiveData<Array<out T>>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataByteArray")
fun LiveData<ByteArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataShortArray")
fun LiveData<ShortArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataIntArray")
fun LiveData<IntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataLongArray")
fun LiveData<LongArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataFloatArray")
fun LiveData<FloatArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataDoubleArray")
fun LiveData<DoubleArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataCharArray")
fun LiveData<CharArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataBooleanArray")
fun LiveData<BooleanArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUByteArray")
fun LiveData<UByteArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUShortArray")
fun LiveData<UShortArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataUIntArray")
fun LiveData<UIntArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [qualifies]。 */
@JvmName("qualifiesOfLiveDataULongArray")
fun LiveData<ULongArray>?.qualifies(): Boolean =
    this != null && this.value.qualifies()
