package dev.simple.extensions.equality

import dev.utils.common.ObjectUtils
import dev.utils.common.StringUtils

/**
 * 判断接收者与另一字符串是否相等（区分大小写）。
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
 * 判断接收者与另一字符串在忽略大小写时是否相等。
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
 * 判断接收者与另一对象在工具类语义下是否相等。
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
