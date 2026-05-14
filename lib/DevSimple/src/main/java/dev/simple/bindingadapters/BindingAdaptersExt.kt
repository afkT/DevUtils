package dev.simple.bindingadapters

import dev.utils.common.ObjectUtils
import dev.utils.common.StringUtils

/**
 * 判断数值型绑定是否应执行一次滚动类逻辑。
 * <pre>
 *     用于需重复触发的场景：时间戳等须非空且大于零；同值不刷新时可据此跳过。
 * </pre>
 *
 * @return `true` 应执行滚动，`false` 跳过
 */
fun Long?.shouldTriggerScroll(): Boolean = this != null && this > 0L

/**
 * 数据绑定侧判断时间戳是否应触发一次通用副作用。
 * <pre>
 *     与 [shouldTriggerScroll] 判定相同，供非滚动类适配器命名使用，避免语义误导。
 *     需多次触发同一命令时，建议绑定递增时间戳或 [System.currentTimeMillis]，优于仅用 [Boolean] 且同值无法二次刷新。
 * </pre>
 *
 * @return `true` 应执行一次，`false` 跳过
 */
fun Long?.shouldTriggerBindingAction(): Boolean = shouldTriggerScroll()

/**
 * 判断接收者与另一字符串在忽略大小写时是否相等。
 * <pre>
 *     委托 [StringUtils.equalsIgnoreCase]；双方均为 null 时返回 `true`；仅接收者为 null 时仅当对方也为 null 时返回 `true`。
 * </pre>
 *
 * @param value 参与比较的对方字符串，可为 null
 * @return `true` 满足上述相等规则，`false` 不满足
 */
fun String?.equalsIgnoreCase(value: String?): Boolean {
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