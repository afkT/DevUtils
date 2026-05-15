@file:Suppress("unused")

package dev.simple.extensions

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
// 故每个 [shouldTriggerEffect] 重载需使用唯一 [@JvmName]（Kotlin 调用处仍写 `shouldTriggerEffect()`）。

/** [LiveData] 非 null 时对 [LiveData.getValue]（Kotlin [LiveData.value]）委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataByte")
fun LiveData<Byte>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataShort")
fun LiveData<Short>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataInt")
fun LiveData<Int>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataLong")
fun LiveData<Long>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataFloat")
fun LiveData<Float>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataDouble")
fun LiveData<Double>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataUByte")
fun LiveData<UByte>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataUShort")
fun LiveData<UShort>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataUInt")
fun LiveData<UInt>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataULong")
fun LiveData<ULong>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataBigInteger")
fun LiveData<BigInteger>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataBigDecimal")
fun LiveData<BigDecimal>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataChar")
fun LiveData<Char>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataBoolean")
fun LiveData<Boolean>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffectTrue]。 */
fun LiveData<Boolean>?.shouldTriggerEffectTrue(): Boolean =
    this != null && this.value.shouldTriggerEffectTrue()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffectFalse]。 */
fun LiveData<Boolean>?.shouldTriggerEffectFalse(): Boolean =
    this != null && this.value.shouldTriggerEffectFalse()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataResult")
fun <T> LiveData<Result<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataCharSequence")
fun LiveData<CharSequence>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataIterable")
fun <T> LiveData<Iterable<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataSequence")
fun <T> LiveData<Sequence<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataIterator")
fun <T> LiveData<Iterator<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataEnumeration")
fun <T> LiveData<Enumeration<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]（API 24+ 与 `TriggerEffectExt` 一致）。 */
@RequiresApi(Build.VERSION_CODES.N)
@JvmName("shouldTriggerEffectOfLiveDataOptional")
fun <T> LiveData<Optional<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataBitSet")
fun LiveData<BitSet>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataCollection")
fun <T> LiveData<Collection<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]（[LiveData] 对 `T` 不变，[List] 需单独重载）。 */
@JvmName("shouldTriggerEffectOfLiveDataList")
fun <T> LiveData<List<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]（[MutableList] 专用）。 */
@JvmName("shouldTriggerEffectOfLiveDataMutableList")
fun <T> LiveData<MutableList<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]（[Set] 专用）。 */
@JvmName("shouldTriggerEffectOfLiveDataSet")
fun <T> LiveData<Set<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]（[MutableSet] 专用）。 */
@JvmName("shouldTriggerEffectOfLiveDataMutableSet")
fun <T> LiveData<MutableSet<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataMap")
fun <K, V> LiveData<Map<K, V>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataSparseArray")
fun <T> LiveData<SparseArray<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataSparseBooleanArray")
fun LiveData<SparseBooleanArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataSparseIntArray")
fun LiveData<SparseIntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataLongSparseArray")
fun <T> LiveData<LongSparseArray<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataArray")
fun <T> LiveData<Array<out T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataByteArray")
fun LiveData<ByteArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataShortArray")
fun LiveData<ShortArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataIntArray")
fun LiveData<IntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataLongArray")
fun LiveData<LongArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataFloatArray")
fun LiveData<FloatArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataDoubleArray")
fun LiveData<DoubleArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataCharArray")
fun LiveData<CharArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfLiveDataBooleanArray")
fun LiveData<BooleanArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfLiveDataUByteArray")
fun LiveData<UByteArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfLiveDataUShortArray")
fun LiveData<UShortArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfLiveDataUIntArray")
fun LiveData<UIntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()

/** [LiveData] 非 null 时对 [LiveData.value] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfLiveDataULongArray")
fun LiveData<ULongArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.value.shouldTriggerEffect()
