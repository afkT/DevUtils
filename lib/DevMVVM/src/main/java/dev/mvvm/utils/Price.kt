package dev.kotlin.utils.price

import dev.utils.common.BigDecimalUtils
import dev.utils.common.NumberUtils
import java.math.BigDecimal

// ==========
// = 快捷方法 =
// ==========

/**
 * 拼接 RMB 符号
 * @return ¥xxx
 */
fun String.toRMB(): String {
    return "¥$this"
}

/**
 * 去掉结尾多余的 . 与 0
 * @return 处理后的字符串
 */
fun String.toSubZeroAndDot(): String {
    return NumberUtils.subZeroAndDot(this)
}

/**
 * 去掉结尾多余的 . 与 0 并拼接 RMB 符号
 * @return 处理后的字符串
 */
fun String.toRMBSubZeroAndDot(): String {
    return "¥${NumberUtils.subZeroAndDot(this)}"
}

// =============
// = 价格转换处理 =
// =============

/**
 * 提供精确的小数位四舍五入处理
 * @param defaultValue 默认值
 * @param scale        保留 scale 位小数
 * @param roundingMode 舍入模式
 * @return 四舍五入后的结果
 */
fun Any.toPriceString(
    defaultValue: String? = null,
    scale: Int = 2,
    roundingMode: Int = BigDecimal.ROUND_DOWN
): String? {
    return try {
        BigDecimalUtils.roundThrow(
            this, scale, roundingMode
        ).toString()
    } catch (e: Exception) {
        defaultValue
    }
}

/**
 * 提供精确的小数位四舍五入处理
 * @param defaultValue 默认值
 * @param scale        保留 scale 位小数
 * @param roundingMode 舍入模式
 * @return 四舍五入后的结果
 */
fun Any.toPriceDouble(
    defaultValue: Double = 0.0,
    scale: Int = 2,
    roundingMode: Int = BigDecimal.ROUND_DOWN
): Double {
    return try {
        BigDecimalUtils.roundThrow(
            this, scale, roundingMode
        )
    } catch (e: Exception) {
        defaultValue
    }
}

/**
 * 提供精确的小数位四舍五入处理
 * @param defaultValue 默认值
 * @param scale        保留 scale 位小数
 * @param roundingMode 舍入模式
 * @return 四舍五入后的结果
 */
fun Any.toPriceFloat(
    defaultValue: Float = 0F,
    scale: Int = 2,
    roundingMode: Int = BigDecimal.ROUND_DOWN
): Float {
    return toPriceDouble(
        defaultValue = defaultValue.toDouble(),
        scale = scale,
        roundingMode = roundingMode
    ).toFloat()
}

/**
 * 提供精确的小数位四舍五入处理
 * @param defaultValue 默认值
 * @param scale        保留 scale 位小数
 * @param roundingMode 舍入模式
 * @return 四舍五入后的结果
 */
fun Any.toPriceLong(
    defaultValue: Long = 0,
    scale: Int = 2,
    roundingMode: Int = BigDecimal.ROUND_DOWN
): Long {
    return toPriceDouble(
        defaultValue = defaultValue.toDouble(),
        scale = scale,
        roundingMode = roundingMode
    ).toLong()
}

/**
 * 提供精确的小数位四舍五入处理
 * @param defaultValue 默认值
 * @param scale        保留 scale 位小数
 * @param roundingMode 舍入模式
 * @return 四舍五入后的结果
 */
fun Any.toPriceInt(
    defaultValue: Int = 0,
    scale: Int = 2,
    roundingMode: Int = BigDecimal.ROUND_DOWN
): Int {
    return toPriceDouble(
        defaultValue = defaultValue.toDouble(),
        scale = scale,
        roundingMode = roundingMode
    ).toInt()
}