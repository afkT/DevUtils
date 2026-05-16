package dev.simple.bindingadapters

import dev.simple.extensions.qualifies.qualifies

/**
 * 判断数值型绑定是否应执行一次滚动类逻辑。
 * <pre>
 *     用于需重复触发的场景：时间戳等须非空且大于零；同值不刷新时可据此跳过。
 * </pre>
 *
 * @return `true` 应执行滚动，`false` 跳过
 */
fun Long?.qualifiesScroll(): Boolean = this.qualifies()

/**
 * 数据绑定侧判断时间戳是否应触发一次通用副作用。
 * <pre>
 *     与 [qualifiesScroll] 判定相同，供非滚动类适配器命名使用，避免语义误导。
 *     需多次触发同一命令时，建议绑定递增时间戳或 [System.currentTimeMillis]，优于仅用 [Boolean] 且同值无法二次刷新。
 * </pre>
 *
 * @return `true` 应执行一次，`false` 跳过
 */
fun Long?.qualifiesBindingAction(): Boolean = this.qualifies()