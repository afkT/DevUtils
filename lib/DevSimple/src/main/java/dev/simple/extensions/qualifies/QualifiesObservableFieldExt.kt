@file:Suppress("unused")
@file:JvmName("QualifiesObservableFieldExtKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalUnsignedTypes::class)

package dev.simple.extensions.qualifies

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
// 故每个 [qualifies] 重载需使用唯一 [@JvmName]（Kotlin 调用处仍写 `qualifies()`）。

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldByte")
fun ObservableField<Byte>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldShort")
fun ObservableField<Short>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldInt")
fun ObservableField<Int>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldLong")
fun ObservableField<Long>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldFloat")
fun ObservableField<Float>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldDouble")
fun ObservableField<Double>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUByte")
fun ObservableField<UByte>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUShort")
fun ObservableField<UShort>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUInt")
fun ObservableField<UInt>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldULong")
fun ObservableField<ULong>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldBigInteger")
fun ObservableField<BigInteger>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldBigDecimal")
fun ObservableField<BigDecimal>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldChar")
fun ObservableField<Char>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldBoolean")
fun ObservableField<Boolean>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifiesTrue]。 */
fun ObservableField<Boolean>?.qualifiesTrue(): Boolean =
    this != null && this.get().qualifiesTrue()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifiesFalse]。 */
fun ObservableField<Boolean>?.qualifiesFalse(): Boolean =
    this != null && this.get().qualifiesFalse()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldResult")
fun <T> ObservableField<Result<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldCharSequence")
fun ObservableField<CharSequence>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldIterable")
fun <T> ObservableField<Iterable<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldSequence")
fun <T> ObservableField<Sequence<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldIterator")
fun <T> ObservableField<Iterator<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldEnumeration")
fun <T> ObservableField<Enumeration<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]（API 24+ 与 `QualifiesExt` 一致）。 */
@RequiresApi(Build.VERSION_CODES.N)
@JvmName("qualifiesOfObservableFieldOptional")
fun <T> ObservableField<Optional<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldBitSet")
fun ObservableField<BitSet>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldCollection")
fun <T> ObservableField<Collection<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]（[ObservableField] 对 `T` 不变，[List] 需单独重载）。 */
@JvmName("qualifiesOfObservableFieldList")
fun <T> ObservableField<List<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]（[MutableList] 专用）。 */
@JvmName("qualifiesOfObservableFieldMutableList")
fun <T> ObservableField<MutableList<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]（[Set] 专用）。 */
@JvmName("qualifiesOfObservableFieldSet")
fun <T> ObservableField<Set<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]（[MutableSet] 专用）。 */
@JvmName("qualifiesOfObservableFieldMutableSet")
fun <T> ObservableField<MutableSet<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldMap")
fun <K, V> ObservableField<Map<K, V>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldSparseArray")
fun <T> ObservableField<SparseArray<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldSparseBooleanArray")
fun ObservableField<SparseBooleanArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldSparseIntArray")
fun ObservableField<SparseIntArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldLongSparseArray")
fun <T> ObservableField<LongSparseArray<T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldArray")
fun <T> ObservableField<Array<out T>>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldByteArray")
fun ObservableField<ByteArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldShortArray")
fun ObservableField<ShortArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldIntArray")
fun ObservableField<IntArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldLongArray")
fun ObservableField<LongArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldFloatArray")
fun ObservableField<FloatArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldDoubleArray")
fun ObservableField<DoubleArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldCharArray")
fun ObservableField<CharArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldBooleanArray")
fun ObservableField<BooleanArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUByteArray")
fun ObservableField<UByteArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUShortArray")
fun ObservableField<UShortArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldUIntArray")
fun ObservableField<UIntArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()

/** [ObservableField] 非 null 时对 [ObservableField.get] 委托 [qualifies]。 */
@JvmName("qualifiesOfObservableFieldULongArray")
fun ObservableField<ULongArray>?.qualifies(): Boolean =
    this != null && this.get().qualifies()