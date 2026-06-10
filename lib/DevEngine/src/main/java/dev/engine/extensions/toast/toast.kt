package dev.engine.extensions.toast

import android.app.Application
import dev.engine.DevEngine
import dev.engine.toast.IToastEngine

// ==========================================
// = IToastEngine<EngineConfig, EngineItem> =
// ==========================================

/**
 * 通过 Key 获取 Toast Engine
 * @receiver String?
 * @return IToastEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Toast Engine
 */
fun String?.getToastEngine(): IToastEngine<
        in IToastEngine.EngineConfig,
        in IToastEngine.EngineItem>? {
    DevEngine.getToast(this)?.let { value ->
        return value
    }
    return DevEngine.getToast()
}

// ====================
// = Key Toast Engine =
// ====================

// =============
// = 对外公开方法 =
// =============

/**
 * 初始化方法
 * @receiver Application
 * @param engine String?
 */
fun Application.toast_initialize(
    engine: String? = null
) {
    engine.getToastEngine()?.initialize(this)
}

/**
 * 判断 Toast 框架是否已经初始化
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun toast_isInit(engine: String? = null): Boolean {
    return engine.getToastEngine()?.isInit ?: false
}

/**
 * 设置是否为调试模式
 * @receiver 是否为调试模式
 * @param engine String?
 */
fun Boolean.toast_setDebugMode(
    engine: String? = null
) {
    engine.getToastEngine()?.setDebugMode(this)
}

/**
 * 设置 Toast Config
 * @receiver Toast Config
 * @param engine String?
 */
fun <Config : IToastEngine.EngineConfig> Config?.toast_setConfig(
    engine: String? = null
) {
    engine.getToastEngine()?.setConfig(this)
}

/**
 * 获取 Toast Config
 * @param engine String?
 * @return Toast Config
 */
fun toast_getConfig(engine: String? = null): Any? {
    return engine.getToastEngine()?.getConfig()
}

// =============
// = Toast 方法 =
// =============

/**
 * 取消 Toast 的显示
 * @param engine String?
 */
fun toast_cancel(engine: String? = null) {
    engine.getToastEngine()?.cancel()
}

/**
 * 延迟显示 Toast
 * @receiver 字符串资源 ID
 * @param engine String?
 * @param delayMillis 延迟的毫秒数
 */
fun Int.toast_delayedShow(
    engine: String? = null,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(this, delayMillis)
}

/**
 * 延迟显示 Toast
 * @receiver 要显示的对象
 * @param engine String?
 * @param delayMillis 延迟的毫秒数
 */
fun Any?.toast_delayedShow(
    engine: String? = null,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(this, delayMillis)
}

/**
 * 延迟显示 Toast
 * @receiver 要显示的文本
 * @param engine String?
 * @param delayMillis 延迟的毫秒数
 */
fun CharSequence?.toast_delayedShow(
    engine: String? = null,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(this, delayMillis)
}

/**
 * debug 模式下显示 Toast
 * @receiver 字符串资源 ID
 * @param engine String?
 */
fun Int.toast_debugShow(
    engine: String? = null
) {
    engine.getToastEngine()?.debugShow(this)
}

/**
 * debug 模式下显示 Toast
 * @receiver 要显示的对象
 * @param engine String?
 */
fun Any?.toast_debugShow(
    engine: String? = null
) {
    engine.getToastEngine()?.debugShow(this)
}

/**
 * debug 模式下显示 Toast
 * @receiver 要显示的文本
 * @param engine String?
 */
fun CharSequence?.toast_debugShow(
    engine: String? = null
) {
    engine.getToastEngine()?.debugShow(this)
}

/**
 * 显示一个短 Toast
 * @receiver 字符串资源 ID
 * @param engine String?
 */
fun Int.toast_showShort(
    engine: String? = null
) {
    engine.getToastEngine()?.showShort(this)
}

/**
 * 显示一个短 Toast
 * @receiver 要显示的对象
 * @param engine String?
 */
fun Any?.toast_showShort(
    engine: String? = null
) {
    engine.getToastEngine()?.showShort(this)
}

/**
 * 显示一个短 Toast
 * @receiver 要显示的文本
 * @param engine String?
 */
fun CharSequence?.toast_showShort(
    engine: String? = null
) {
    engine.getToastEngine()?.showShort(this)
}

/**
 * 显示一个长 Toast
 * @receiver 字符串资源 ID
 * @param engine String?
 */
fun Int.toast_showLong(
    engine: String? = null
) {
    engine.getToastEngine()?.showLong(this)
}

/**
 * 显示一个长 Toast
 * @receiver 要显示的对象
 * @param engine String?
 */
fun Any?.toast_showLong(
    engine: String? = null
) {
    engine.getToastEngine()?.showLong(this)
}

/**
 * 显示一个长 Toast
 * @receiver 要显示的文本
 * @param engine String?
 */
fun CharSequence?.toast_showLong(
    engine: String? = null
) {
    engine.getToastEngine()?.showLong(this)
}

/**
 * 显示 Toast
 * @receiver 字符串资源 ID
 * @param engine String?
 */
fun Int.toast_show(
    engine: String? = null
) {
    engine.getToastEngine()?.show(this)
}

/**
 * 显示 Toast
 * @receiver 要显示的对象
 * @param engine String?
 */
fun Any?.toast_show(
    engine: String? = null
) {
    engine.getToastEngine()?.show(this)
}

/**
 * 显示 Toast
 * @receiver 要显示的文本
 * @param engine String?
 */
fun CharSequence?.toast_show(
    engine: String? = null
) {
    engine.getToastEngine()?.show(this)
}

/**
 * 显示 Toast
 * @receiver Toast 参数
 * @param engine String?
 */
fun <Item : IToastEngine.EngineItem> Item.toast_show(
    engine: String? = null
) {
    engine.getToastEngine()?.show(this)
}

// ============
// = Toast UI =
// ============

/**
 * 给当前 Toast 设置新的布局
 * @receiver 布局资源 ID
 * @param engine String?
 */
fun Int.toast_setView(
    engine: String? = null
) {
    engine.getToastEngine()?.setView(this)
}

/**
 * 设置 Toast 的位置
 * @receiver 重心
 * @param engine String?
 */
fun Int.toast_setGravity(
    engine: String? = null
) {
    engine.getToastEngine()?.setGravity(this)
}

/**
 * 设置 Toast 的位置
 * @receiver 重心
 * @param engine String?
 * @param xOffset X 轴偏移量
 * @param yOffset Y 轴偏移量
 */
fun Int.toast_setGravity(
    engine: String? = null,
    xOffset: Int,
    yOffset: Int
) {
    engine.getToastEngine()?.setGravity(this, xOffset, yOffset)
}

/**
 * 设置 Toast 的位置
 * @receiver 重心
 * @param engine String?
 * @param xOffset X 轴偏移量
 * @param yOffset Y 轴偏移量
 * @param horizontalMargin 水平边距
 * @param verticalMargin 垂直边距
 */
fun Int.toast_setGravity(
    engine: String? = null,
    xOffset: Int,
    yOffset: Int,
    horizontalMargin: Float,
    verticalMargin: Float
) {
    engine.getToastEngine()?.setGravity(
        this, xOffset, yOffset, horizontalMargin, verticalMargin
    )
}