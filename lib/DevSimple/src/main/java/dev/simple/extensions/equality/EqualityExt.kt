@file:Suppress("unused")
@file:JvmName("EqualityExtKt")
@file:JvmMultifileClass

package dev.simple.extensions.equality

import dev.simple.interfaces.CompareValue
import dev.utils.common.ObjectUtils
import dev.utils.common.StringUtils

/**
 * 判断接收者与另一字符串是否相等，比较区分大小写。
 * <pre>
 *     委托 [StringUtils.equals]；双方均为 null 时返回 `true`；仅一方为 null 时返回 `false`；否则按工具类语义比较。
 * </pre>
 *
 * @param value 参与比较的对方字符串，可为 null
 * @return `true` 满足上述相等规则，`false` 不满足
 */
fun String?.stringEquals(value: String?): Boolean {
    return StringUtils.equals(this, value)
}

/**
 * 判断接收者与另一字符串是否相等，比较忽略大小写。
 * <pre>
 *     委托 [StringUtils.equalsIgnoreCase]；双方均为 null 时返回 `true`；仅接收者为 null 时仅当对方也为 null 时返回 `true`。
 * </pre>
 *
 * @param value 参与比较的对方字符串，可为 null
 * @return `true` 满足上述相等规则，`false` 不满足
 */
fun String?.stringEqualsIgnoreCase(value: String?): Boolean {
    return StringUtils.equalsIgnoreCase(this, value)
}

/**
 * 判断接收者与另一对象是否在工具类相等语义下一致。
 * <pre>
 *     委托 [ObjectUtils.equals]；对 [String] 与 [CharSequence] 等有专门分支，其余走 `equals`；双方均为 null 时返回 `true`；异常在工具类内消化后视为不相等。
 * </pre>
 *
 * @param value 参与比较的另一对象，可为 null
 * @return `true` 工具类判定为相等，`false` 不相等或比较失败
 */
fun Any?.valueEquals(value: Any?): Boolean {
    return ObjectUtils.equals(this, value)
}

/**
 * 判断双方比对值接口实例是否按接口约定视为相等。
 * <pre>
 *     双方均为非 null 时委托 [CompareValue.equalsCompareValue]；任一方为 null 时返回 `false`。
 *     不读取或比对 [CompareValue.compareValue] 的字符串，仅走接口自定义相等逻辑。
 * </pre>
 *
 * @param value 参与比对的对方实现，可为 null
 * @return `true` 双方均非 null 且接口判定相等，`false` 任一方为 null 或判定不相等
 */
fun CompareValue?.compareValueEquals(value: CompareValue?): Boolean {
    return this != null && value != null && this.equalsCompareValue(value)
}