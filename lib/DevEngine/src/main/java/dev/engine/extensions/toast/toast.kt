package dev.engine.extensions.toast

import android.app.Application
import dev.engine.DevEngine
import dev.engine.toast.IToastEngine

// ==========================================
// = IToastEngine<EngineConfig, EngineItem> =
// ==========================================

/**
 * 通过 Key 获取 Toast Engine
 * @param engine String?
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
 * @param engine String?
 * @param application Application
 */
fun toast_initialize(
    engine: String? = null,
    application: Application
) {
    engine.getToastEngine()?.initialize(application)
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
 * @param engine String?
 * @param debug 是否为调试模式
 */
fun toast_setDebugMode(
    engine: String? = null,
    debug: Boolean
) {
    engine.getToastEngine()?.setDebugMode(debug)
}

/**
 * 设置 Toast Config
 * @param engine String?
 * @param config Toast Config
 */
fun <Config : IToastEngine.EngineConfig> toast_setConfig(
    engine: String? = null,
    config: Config?
) {
    engine.getToastEngine()?.setConfig(config)
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
 * @param engine String?
 * @param id 字符串资源 ID
 * @param delayMillis 延迟的毫秒数
 */
fun toast_delayedShow(
    engine: String? = null,
    id: Int,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(id, delayMillis)
}

/**
 * 延迟显示 Toast
 * @param engine String?
 * @param obj 要显示的对象
 * @param delayMillis 延迟的毫秒数
 */
fun toast_delayedShow(
    engine: String? = null,
    obj: Any?,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(obj, delayMillis)
}

/**
 * 延迟显示 Toast
 * @param engine String?
 * @param text 要显示的文本
 * @param delayMillis 延迟的毫秒数
 */
fun toast_delayedShow(
    engine: String? = null,
    text: CharSequence?,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(text, delayMillis)
}

/**
 * debug 模式下显示 Toast
 * @param engine String?
 * @param id 字符串资源 ID
 */
fun toast_debugShow(
    engine: String? = null,
    id: Int
) {
    engine.getToastEngine()?.debugShow(id)
}

/**
 * debug 模式下显示 Toast
 * @param engine String?
 * @param obj 要显示的对象
 */
fun toast_debugShow(
    engine: String? = null,
    obj: Any?
) {
    engine.getToastEngine()?.debugShow(obj)
}

/**
 * debug 模式下显示 Toast
 * @param engine String?
 * @param text 要显示的文本
 */
fun toast_debugShow(
    engine: String? = null,
    text: CharSequence?
) {
    engine.getToastEngine()?.debugShow(text)
}

/**
 * 显示一个短 Toast
 * @param engine String?
 * @param id 字符串资源 ID
 */
fun toast_showShort(
    engine: String? = null,
    id: Int
) {
    engine.getToastEngine()?.showShort(id)
}

/**
 * 显示一个短 Toast
 * @param engine String?
 * @param obj 要显示的对象
 */
fun toast_showShort(
    engine: String? = null,
    obj: Any?
) {
    engine.getToastEngine()?.showShort(obj)
}

/**
 * 显示一个短 Toast
 * @param engine String?
 * @param text 要显示的文本
 */
fun toast_showShort(
    engine: String? = null,
    text: CharSequence?
) {
    engine.getToastEngine()?.showShort(text)
}

/**
 * 显示一个长 Toast
 * @param engine String?
 * @param id 字符串资源 ID
 */
fun toast_showLong(
    engine: String? = null,
    id: Int
) {
    engine.getToastEngine()?.showLong(id)
}

/**
 * 显示一个长 Toast
 * @param engine String?
 * @param obj 要显示的对象
 */
fun toast_showLong(
    engine: String? = null,
    obj: Any?
) {
    engine.getToastEngine()?.showLong(obj)
}

/**
 * 显示一个长 Toast
 * @param engine String?
 * @param text 要显示的文本
 */
fun toast_showLong(
    engine: String? = null,
    text: CharSequence?
) {
    engine.getToastEngine()?.showLong(text)
}

/**
 * 显示 Toast
 * @param engine String?
 * @param id 字符串资源 ID
 */
fun toast_show(
    engine: String? = null,
    id: Int
) {
    engine.getToastEngine()?.show(id)
}

/**
 * 显示 Toast
 * @param engine String?
 * @param obj 要显示的对象
 */
fun toast_show(
    engine: String? = null,
    obj: Any?
) {
    engine.getToastEngine()?.show(obj)
}

/**
 * 显示 Toast
 * @param engine String?
 * @param text 要显示的文本
 */
fun toast_show(
    engine: String? = null,
    text: CharSequence?
) {
    engine.getToastEngine()?.show(text)
}

/**
 * 显示 Toast
 * @param engine String?
 * @param item Toast 参数
 */
fun <Item : IToastEngine.EngineItem> toast_show(
    engine: String? = null,
    item: Item
) {
    engine.getToastEngine()?.show(item)
}

// ============
// = Toast UI =
// ============

/**
 * 给当前 Toast 设置新的布局
 * @param engine String?
 * @param id 布局资源 ID
 */
fun toast_setView(
    engine: String? = null,
    id: Int
) {
    engine.getToastEngine()?.setView(id)
}

/**
 * 设置 Toast 的位置
 * @param engine String?
 * @param gravity 重心
 */
fun toast_setGravity(
    engine: String? = null,
    gravity: Int
) {
    engine.getToastEngine()?.setGravity(gravity)
}

/**
 * 设置 Toast 的位置
 * @param engine String?
 * @param gravity 重心
 * @param xOffset X 轴偏移量
 * @param yOffset Y 轴偏移量
 */
fun toast_setGravity(
    engine: String? = null,
    gravity: Int,
    xOffset: Int,
    yOffset: Int
) {
    engine.getToastEngine()?.setGravity(gravity, xOffset, yOffset)
}

/**
 * 设置 Toast 的位置
 * @param engine String?
 * @param gravity 重心
 * @param xOffset X 轴偏移量
 * @param yOffset Y 轴偏移量
 * @param horizontalMargin 水平边距
 * @param verticalMargin 垂直边距
 */
fun toast_setGravity(
    engine: String? = null,
    gravity: Int,
    xOffset: Int,
    yOffset: Int,
    horizontalMargin: Float,
    verticalMargin: Float
) {
    engine.getToastEngine()?.setGravity(
        gravity, xOffset, yOffset, horizontalMargin, verticalMargin
    )
}