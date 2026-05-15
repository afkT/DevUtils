@file:Suppress("unused")

package dev.simple.extensions

import android.os.Build
import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.annotation.RequiresApi
import androidx.databinding.ObservableField
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

// ===================
// = ObservableField =
// ===================
//
// 因 JVM 擦除，[ObservableField] 的多个泛型实参在字节码上均为原始 [ObservableField]，
// 故每个 [shouldTriggerEffect] 重载需使用唯一 [@JvmName]（Kotlin 调用处仍写 `shouldTriggerEffect()`）。

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldByte")
fun ObservableField<Byte>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldShort")
fun ObservableField<Short>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldInt")
fun ObservableField<Int>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldLong")
fun ObservableField<Long>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldFloat")
fun ObservableField<Float>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldDouble")
fun ObservableField<Double>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldUByte")
fun ObservableField<UByte>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldUShort")
fun ObservableField<UShort>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldUInt")
fun ObservableField<UInt>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldULong")
fun ObservableField<ULong>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldBigInteger")
fun ObservableField<BigInteger>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldBigDecimal")
fun ObservableField<BigDecimal>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldChar")
fun ObservableField<Char>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldBoolean")
fun ObservableField<Boolean>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffectTrue]。 */
fun ObservableField<Boolean>?.shouldTriggerEffectTrue(): Boolean =
    this != null && this.get().shouldTriggerEffectTrue()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffectFalse]。 */
fun ObservableField<Boolean>?.shouldTriggerEffectFalse(): Boolean =
    this != null && this.get().shouldTriggerEffectFalse()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldResult")
fun <T> ObservableField<Result<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldCharSequence")
fun ObservableField<CharSequence>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldIterable")
fun <T> ObservableField<Iterable<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldSequence")
fun <T> ObservableField<Sequence<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldIterator")
fun <T> ObservableField<Iterator<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldEnumeration")
fun <T> ObservableField<Enumeration<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]（API 24+ 与 `TriggerEffectExt` 一致）。 */
@RequiresApi(Build.VERSION_CODES.N)
@JvmName("shouldTriggerEffectOfObservableFieldOptional")
fun <T> ObservableField<Optional<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldBitSet")
fun ObservableField<BitSet>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldCollection")
fun <T> ObservableField<Collection<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]（[ObservableField] 对 `T` 不变，[List] 需单独重载）。 */
@JvmName("shouldTriggerEffectOfObservableFieldList")
fun <T> ObservableField<List<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]（[MutableList] 专用）。 */
@JvmName("shouldTriggerEffectOfObservableFieldMutableList")
fun <T> ObservableField<MutableList<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]（[Set] 专用）。 */
@JvmName("shouldTriggerEffectOfObservableFieldSet")
fun <T> ObservableField<Set<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]（[MutableSet] 专用）。 */
@JvmName("shouldTriggerEffectOfObservableFieldMutableSet")
fun <T> ObservableField<MutableSet<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldMap")
fun <K, V> ObservableField<Map<K, V>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldSparseArray")
fun <T> ObservableField<SparseArray<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldSparseBooleanArray")
fun ObservableField<SparseBooleanArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldSparseIntArray")
fun ObservableField<SparseIntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldLongSparseArray")
fun <T> ObservableField<LongSparseArray<T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldArray")
fun <T> ObservableField<Array<out T>>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldByteArray")
fun ObservableField<ByteArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldShortArray")
fun ObservableField<ShortArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldIntArray")
fun ObservableField<IntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldLongArray")
fun ObservableField<LongArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldFloatArray")
fun ObservableField<FloatArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldDoubleArray")
fun ObservableField<DoubleArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldCharArray")
fun ObservableField<CharArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@JvmName("shouldTriggerEffectOfObservableFieldBooleanArray")
fun ObservableField<BooleanArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfObservableFieldUByteArray")
fun ObservableField<UByteArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfObservableFieldUShortArray")
fun ObservableField<UShortArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfObservableFieldUIntArray")
fun ObservableField<UIntArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [shouldTriggerEffect]。 */
@OptIn(ExperimentalUnsignedTypes::class)
@JvmName("shouldTriggerEffectOfObservableFieldULongArray")
fun ObservableField<ULongArray>?.shouldTriggerEffect(): Boolean =
    this != null && this.get().shouldTriggerEffect()
