package dev.simple.extensions

import java.math.BigDecimal
import java.math.BigInteger

// ======
// = Any =
// ======

/**
 * 判断接收方是否仅为「存在」意义上的有效（非空即成立）。
 * <pre>
 *     与各类 [shouldTriggerEffect] 重载不同：不要求数值大于零或集合有内容，仅判定是否为 `null`。
 * </pre>
 * @receiver 任意类型的可空引用
 * @return `true` 接收方非空，`false` 接收方为 null
 */
fun Any?.shouldTriggerPresence(): Boolean = this != null

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
fun Byte?.shouldTriggerEffect(): Boolean = this != null && this > 0

/**
 * 判断可空短整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空短整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Short?.shouldTriggerEffect(): Boolean = this != null && this > 0

/**
 * 判断可空整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Int?.shouldTriggerEffect(): Boolean = this != null && this > 0

/**
 * 判断可空长整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零与负数均视为不触发。
 * </pre>
 * @receiver 可空长整型
 * @return `true` 非空且大于 0，`false` 为 null、为零或为负
 */
fun Long?.shouldTriggerEffect(): Boolean = this != null && this > 0L

/**
 * 判断可空单精度浮点是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零、负数与 `NaN` 均不视为触发（比较语义同语言内置大于运算符）。
 * </pre>
 * @receiver 可空单精度浮点
 * @return `true` 非空且大于 0，`false` 为 null、为零、为负或不满足大于零
 */
fun Float?.shouldTriggerEffect(): Boolean = this != null && this > 0f

/**
 * 判断可空双精度浮点是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零；零、负数与 `NaN` 均不视为触发（比较语义同语言内置大于运算符）。
 * </pre>
 * @receiver 可空双精度浮点
 * @return `true` 非空且大于 0，`false` 为 null、为零、为负或不满足大于零
 */
fun Double?.shouldTriggerEffect(): Boolean = this != null && this > 0.0

/**
 * 判断可空无符号字节是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号字节
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UByte?.shouldTriggerEffect(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号短整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号短整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UShort?.shouldTriggerEffect(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun UInt?.shouldTriggerEffect(): Boolean = this != null && this > 0u

/**
 * 判断可空无符号长整型是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     条件为非空且严格大于零。
 * </pre>
 * @receiver 可空无符号长整型
 * @return `true` 非空且大于 0，`false` 为 null 或为零
 */
fun ULong?.shouldTriggerEffect(): Boolean = this != null && this > 0uL

/**
 * 判断可空任意精度整数是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     与 [BigInteger.ZERO] 比较，须非空且严格大于零。
 * </pre>
 * @receiver 可空任意精度整数
 * @return `true` 非空且大于零，`false` 为 null、为零或为负
 */
fun BigInteger?.shouldTriggerEffect(): Boolean =
    this != null && this > BigInteger.ZERO

/**
 * 判断可空任意精度小数是否满足「有正向触发意义」的数值条件。
 * <pre>
 *     与 [BigDecimal.ZERO] 比较，须非空且严格大于零（标度参与比较语义由 [BigDecimal] 定义）。
 * </pre>
 * @receiver 可空任意精度小数
 * @return `true` 非空且大于零，`false` 为 null、为零或为负
 */
fun BigDecimal?.shouldTriggerEffect(): Boolean =
    this != null && this > BigDecimal.ZERO

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
fun CharSequence?.shouldTriggerEffect(): Boolean = !isNullOrEmpty()

/**
 * 判断可空可迭代对象是否至少还有一项可供遍历。
 * <pre>
 *     通过 [Iterable.iterator] 与 [Iterator.hasNext] 判断，不要求实现 [Collection]；
 *     与基于 `size` 的集合判定在语义上一致于「是否为空」。
 * </pre>
 * @receiver 可空元素类型的可迭代对象
 * @return `true` 非空且迭代器存在下一项，`false` 为 null 或无可迭代元素
 */
fun <T> Iterable<T>?.shouldTriggerEffect(): Boolean =
    this != null && this.iterator().hasNext()

/**
 * 判断可空序列是否至少还有一项可供遍历。
 * <pre>
 *     通过 [Sequence.iterator] 与 [Iterator.hasNext] 检测；每次调用会取得新的迭代起点。
 * </pre>
 * @receiver 可空元素类型的序列
 * @return `true` 非空且迭代器存在下一项，`false` 为 null 或序列为空
 */
fun <T> Sequence<T>?.shouldTriggerEffect(): Boolean =
    this != null && this.iterator().hasNext()

/**
 * 判断可空集合是否至少含有一个元素。
 * <pre>
 *     使用 `isNullOrEmpty` 取反，覆盖 [List]、[Set] 等 [Collection] 实现。
 * </pre>
 * @receiver 可空元素类型的集合
 * @return `true` 非空且元素个数大于零，`false` 为 null 或为空集合
 */
fun <T> Collection<T>?.shouldTriggerEffect(): Boolean = !isNullOrEmpty()

/**
 * 判断可空映射是否至少含有一条键值对。
 * <pre>
 *     使用 `isNullOrEmpty` 取反。
 * </pre>
 * @receiver 可空键值类型的映射
 * @return `true` 非空且条目数大于零，`false` 为 null 或为空映射
 */
fun <K, V> Map<K, V>?.shouldTriggerEffect(): Boolean = !isNullOrEmpty()

/**
 * 判断可空泛型数组是否至少含有一个元素。
 * <pre>
 *     仅检查 `null` 与 `isNotEmpty`，不展开元素内容。
 * </pre>
 * @receiver 可空对象数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun <T> Array<out T>?.shouldTriggerEffect(): Boolean =
    this != null && this.isNotEmpty()

/**
 * 判断可空字节数组是否至少含有一个元素。
 * @receiver 可空字节数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun ByteArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空短整型数组是否至少含有一个元素。
 * @receiver 可空短整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun ShortArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空整型数组是否至少含有一个元素。
 * @receiver 可空整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun IntArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空长整型数组是否至少含有一个元素。
 * @receiver 可空长整型数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun LongArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空单精度浮点数组是否至少含有一个元素。
 * @receiver 可空单精度浮点数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun FloatArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空双精度浮点数组是否至少含有一个元素。
 * @receiver 可空双精度浮点数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun DoubleArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空字符数组是否至少含有一个元素。
 * @receiver 可空字符数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun CharArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()

/**
 * 判断可空布尔数组是否至少含有一个元素。
 * @receiver 可空布尔数组
 * @return `true` 非空且长度大于零，`false` 为 null 或长度为零
 */
fun BooleanArray?.shouldTriggerEffect(): Boolean = this != null && this.isNotEmpty()
