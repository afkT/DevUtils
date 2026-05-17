@file:Suppress("unused")
@file:JvmName("QualifiesExtKt")
@file:JvmMultifileClass
@file:OptIn(ExperimentalUnsignedTypes::class)

package dev.simple.extensions.qualifies

import android.os.Build
import android.util.LongSparseArray
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.util.SparseIntArray
import androidx.annotation.RequiresApi
import androidx.core.util.isNotEmpty
import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

// ======
// = Any =
// ======

/**
 * 判断接收方是否仅为「引用存在」意义上的成立（非 `null` 即成立）。
 * <pre>
 *     使用 [qualifiesPresence] 与各类型的 [qualifies] 区分：此处 **不做** 数值大于零、集合非空等类型约定，
 *     仅判定引用是否为 `null`；需要按类型门槛判定时请对该类型的可空值调用 [qualifies] 或其特化（如 [qualifiesTrue]）。
 * </pre>
 * @receiver 任意类型的可空引用
 * @return `true` 接收方非空，`false` 接收方为 null
 */
fun Any?.qualifiesPresence(): Boolean = this != null

// =======================
// = 数值：非空且严格大于零 =
// =======================

/**
 * 判断可空字节是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空字节
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Byte?.qualifies(): Boolean = this != null && this > 0

/**
 * 判断可空短整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空短整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Short?.qualifies(): Boolean = this != null && this > 0

/**
 * 判断可空整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Int?.qualifies(): Boolean = this != null && this > 0

/**
 * 判断可空长整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空长整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Long?.qualifies(): Boolean = this != null && this > 0L

/**
 * 判断可空单精度浮点是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零、负数与 `NaN` 均不视为触发（比较语义同语言内置大于运算符）。
 * </pre>
 * @receiver 可空单精度浮点
 * @return `true` 非空且大于 0，`false` 为 null、为零、为负或不满足大于零
 */
fun Float?.qualifies(): Boolean = this != null && this > 0f

/**
 * 判断可空双精度浮点是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零、负数与 `NaN` 均不视为触发（比较语义同语言内置大于运算符）。
 * </pre>
 * @receiver 可空双精度浮点
 * @return `true` 非空且大于 0，`false` 为 null、为零、为负或不满足大于零
 */
fun Double?.qualifies(): Boolean = this != null && this > 0.0

/**
 * 判断可空无符号字节是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号字节
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UByte?.qualifies(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号短整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号短整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UShort?.qualifies(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UInt?.qualifies(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号长整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号长整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun ULong?.qualifies(): Boolean = this != null && this > 0uL

/**
 * 判断可空任意精度整数是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     与 [BigInteger.ZERO] 比较，须非空且严格大于零。
 * </pre>
 * @receiver 可空任意精度整数
 * @return `true` 非空且大于零，`false` 为 null、为零或为负
 */
fun BigInteger?.qualifies(): Boolean =
    this != null && this > BigInteger.ZERO

/**
 * 判断可空任意精度小数是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     与 [BigDecimal.ZERO] 比较，须非空且严格大于零（标度参与比较语义由 [BigDecimal] 定义）。
 * </pre>
 * @receiver 可空任意精度小数
 * @return `true` 非空且大于零，`false` 为 null、为零或为负
 */
fun BigDecimal?.qualifies(): Boolean =
    this != null && this > BigDecimal.ZERO

/**
 * 判断可空字符的 Unicode 码元是否视为「正数」意义上的有效。
 * <pre>
 *     非 null 且 [Char.code] 严格大于零，与其它数值型 [qualifies] 的「大于零」字面一致；
 *     注意 `Char` 为 UTF-16 码元，与整码点（grapheme）语义不同。
 * </pre>
 * @receiver 可空字符
 * @return `true` 非空且 [Char.code] 严格大于零，`false` 为 null 或 code 不大于零
 */
fun Char?.qualifies(): Boolean = this != null && this.code > 0

/**
 * 判断可空布尔是否「已给出取值」（非 null 即成立）。
 * <pre>
 *     仅 `null` 不成立；`true` 与 `false` 均成立（与 [qualifiesTrue]、[qualifiesFalse] 区分）。
 * </pre>
 * @receiver 可空布尔
 * @return `true` 接收方非 null，`false` 接收方为 null
 */
fun Boolean?.qualifies(): Boolean = this != null

/**
 * 判断可空布尔是否显式为 `true`。
 * <pre>
 *     `null` 与 `false` 均不触发；仅当接收方非 null 且为 `true` 时返回 `true`。
 *     与 [qualifies]（非 null 即成立）区分，用于仅需显式 `true` 的场景。
 * </pre>
 * @receiver 可空布尔
 * @return `true` 非空且为 `true`，`false` 为 null 或为 `false`
 */
fun Boolean?.qualifiesTrue(): Boolean = this != null && this

/**
 * 判断可空布尔是否显式为 `false`。
 * <pre>
 *     `null` 与 `true` 均不触发；仅当接收方非 null 且为 `false` 时返回 `true`。
 *     适用于需单独响应「否定」或关闭类命令绑定的场景。
 * </pre>
 * @receiver 可空布尔
 * @return `true` 非空且为 `false`，`false` 为 null 或为 `true`
 */
fun Boolean?.qualifiesFalse(): Boolean = this != null && !this

/**
 * 判断可空 [Result] 是否表示成功。
 * <pre>
 *     容器为 null 不触发；非 null 时取 [Result.isSuccess]（与集合「有内容」不同，按操作结果语义单独约定）。
 * </pre>
 * @receiver 可空操作结果
 * @return `true` 非空且为成功，`false` 为 null 或为失败
 */
fun <T> Result<T>?.qualifies(): Boolean =
    this != null && this.isSuccess

// ===========================================
// = 文本、可迭代、集合、映射与数组：非空且至少含一项 =
// ===========================================

/**
 * 判断可空字符序列是否含有可见的文本内容。
 * <pre>
 *     等价于对 `CharSequence` 使用 `isNullOrEmpty` 取反：非 null 且长度大于零。
 * </pre>
 * @receiver 可空字符序列
 * @return `true` 非空且非空串，`false` 为 null 或长度为 0
 */
fun CharSequence?.qualifies(): Boolean = !isNullOrEmpty()

/**
 * 判断可空可迭代对象是否至少还有一项可供遍历。
 * <pre>
 *     通过 [Iterable.iterator] 与 [Iterator.hasNext] 判断，不要求实现 [Collection]；
 *     与基于 `size` 的集合判定在语义上一致于「是否为空」。
 * </pre>
 * @receiver 可空元素类型的可迭代对象
 * @return `true` 非空且迭代器存在下一项，`false` 为 null 或无可迭代元素
 */
fun <T> Iterable<T>?.qualifies(): Boolean =
    this != null && this.iterator().hasNext()

/**
 * 判断可空序列是否至少还有一项可供遍历。
 * <pre>
 *     通过 [Sequence.iterator] 与 [Iterator.hasNext] 检测；每次调用会取得新的迭代起点。
 * </pre>
 * @receiver 可空元素类型的序列
 * @return `true` 非空且迭代器存在下一项，`false` 为 null 或序列为空
 */
fun <T> Sequence<T>?.qualifies(): Boolean =
    this != null && this.iterator().hasNext()

/**
 * 判断可空迭代器是否仍有下一项。
 * <pre>
 *     与 [Iterable] 判定类似，适用于直接持有 [Iterator] 引用的场景（含 Java 集合返回的迭代器）。
 * </pre>
 * @receiver 可空迭代器
 * @return `true` 非空且 [Iterator.hasNext] 为 `true`
 */
fun <T> Iterator<T>?.qualifies(): Boolean =
    this != null && this.hasNext()

/**
 * 判断可空 `Enumeration` 是否仍有更多元素。
 * <pre>
 *     典型于 Java 遗留 API；语义为「非 null 且 [Enumeration.hasMoreElements]」。
 * </pre>
 * @receiver 可空枚举迭代
 * @return `true` 非空且仍有元素
 */
fun <T> Enumeration<T>?.qualifies(): Boolean =
    this != null && this.hasMoreElements()

/**
 * 判断可空 `Optional` 是否呈现值。
 * <pre>
 *     与 Kotlin 可空类型不同，`Optional.empty()` 仍为非 null 容器；本方法要求 **容器非 null 且 [Optional.isPresent]**。
 * </pre>
 * @receiver 可空 `Optional` 容器
 * @return `true` 容器非空且内含值
 */
@RequiresApi(Build.VERSION_CODES.N)
fun <T> Optional<T>?.qualifies(): Boolean =
    this != null && this.isPresent

/**
 * 判断可空位集是否至少含有一个被置位的比特。
 * <pre>
 *     使用 [BitSet.cardinality] 与零比较，兼容较低 Android/Java API（不依赖 [BitSet.isEmpty]）。
 * </pre>
 * @receiver 可空位集
 * @return `true` 非空且基数大于零
 */
fun BitSet?.qualifies(): Boolean =
    this != null && this.cardinality() > 0

/**
 * 判断可空集合是否至少含有一个元素。
 * <pre>
 *     使用 `isNullOrEmpty` 取反，覆盖 [List]、[Set] 等 [Collection] 实现。
 * </pre>
 * @receiver 可空元素类型的集合
 * @return `true` 非空且元素个数大于零，`false` 为 null 或为空集合
 */
fun <T> Collection<T>?.qualifies(): Boolean = !isNullOrEmpty()

/**
 * 判断可空映射是否至少含有一条键值对。
 * <pre>
 *     使用 `isNullOrEmpty` 取反。
 * </pre>
 * @receiver 可空键值类型的映射
 * @return `true` 非空且条目数大于零，`false` 为 null 或为空映射
 */
fun <K, V> Map<K, V>?.qualifies(): Boolean = !isNullOrEmpty()

/**
 * 判断可空 [SparseArray] 是否至少映射一项。
 * <pre>
 *     非 null 且 [SparseArray.size] 大于零（不根据 `keyAt` 是否有效逐项校验）。
 * </pre>
 * @receiver 可空稀疏数组
 * @return `true` 非空且条目数大于零
 */
fun <T> SparseArray<T>?.qualifies(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空 [SparseBooleanArray] 是否至少含有一项。
 * @receiver 可空稀疏布尔数组
 * @return `true` 非空且条目数大于零
 */
fun SparseBooleanArray?.qualifies(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空 [SparseIntArray] 是否至少含有一项。
 * @receiver 可空稀疏整型数组
 * @return `true` 非空且条目数大于零
 */
fun SparseIntArray?.qualifies(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空 [LongSparseArray] 是否至少映射一项。
 * @receiver 可空长整型键稀疏数组
 * @return `true` 非空且条目数大于零
 */
fun <T> LongSparseArray<T>?.qualifies(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空泛型数组是否至少含有一个元素。
 * <pre>
 *     仅检查 `null` 与 `isNotEmpty`，不展开元素内容。
 * </pre>
 * @receiver 可空对象数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun <T> Array<out T>?.qualifies(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空字节数组是否至少含有一个元素。
 * @receiver 可空字节数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun ByteArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空短整型数组是否至少含有一个元素。
 * @receiver 可空短整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun ShortArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空整型数组是否至少含有一个元素。
 * @receiver 可空整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun IntArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空长整型数组是否至少含有一个元素。
 * @receiver 可空长整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun LongArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空单精度浮点数组是否至少含有一个元素。
 * @receiver 可空单精度浮点数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun FloatArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空双精度浮点数组是否至少含有一个元素。
 * @receiver 可空双精度浮点数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun DoubleArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空字符数组是否至少含有一个元素。
 * @receiver 可空字符数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun CharArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空布尔数组是否至少含有一个元素。
 * @receiver 可空布尔数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun BooleanArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空无符号字节数组是否至少含有一个元素。
 * @receiver 可空无符号字节数组
 * @return `true` 非空且长度大于零
 */
fun UByteArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空无符号短整型数组是否至少含有一个元素。
 * @receiver 可空无符号短整型数组
 * @return `true` 非空且长度大于零
 */
fun UShortArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空无符号整型数组是否至少含有一个元素。
 * @receiver 可空无符号整型数组
 * @return `true` 非空且长度大于零
 */
fun UIntArray?.qualifies(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空无符号长整型数组是否至少含有一个元素。
 * @receiver 可空无符号长整型数组
 * @return `true` 非空且长度大于零
 */
fun ULongArray?.qualifies(): Boolean = this != null && this.isNotEmpty()