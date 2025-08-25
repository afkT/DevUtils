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

fun toast_initialize(engine: String? = null, application: Application) {
    engine.getToastEngine()?.initialize(application)
}

fun toast_isInit(engine: String? = null): Boolean {
    return engine.getToastEngine()?.isInit ?: true
}

fun toast_setDebugMode(engine: String? = null, debug: Boolean) {
    engine.getToastEngine()?.setDebugMode(debug)
}

fun <Config : IToastEngine.EngineConfig> toast_setConfig(engine: String? = null, config: Config?) {
    engine.getToastEngine()?.setConfig(config)
}

fun toast_getConfig(engine: String? = null): Any? {
    return engine.getToastEngine()?.getConfig()
}

// =============
// = Toast 方法 =
// =============

fun toast_cancel(engine: String? = null) {
    engine.getToastEngine()?.cancel()
}

fun toast_delayedShow(
    engine: String? = null,
    id: Int,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(id, delayMillis)
}

fun toast_delayedShow(
    engine: String? = null,
    obj: Any?,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(obj, delayMillis)
}

fun toast_delayedShow(
    engine: String? = null,
    text: CharSequence?,
    delayMillis: Long
) {
    engine.getToastEngine()?.delayedShow(text, delayMillis)
}

fun toast_debugShow(engine: String? = null, id: Int) {
    engine.getToastEngine()?.debugShow(id)
}

fun toast_debugShow(engine: String? = null, obj: Any?) {
    engine.getToastEngine()?.debugShow(obj)
}

fun toast_debugShow(engine: String? = null, text: CharSequence?) {
    engine.getToastEngine()?.debugShow(text)
}

fun toast_showShort(engine: String? = null, id: Int) {
    engine.getToastEngine()?.showShort(id)
}

fun toast_showShort(engine: String? = null, obj: Any?) {
    engine.getToastEngine()?.showShort(obj)
}

fun toast_showShort(engine: String? = null, text: CharSequence?) {
    engine.getToastEngine()?.showShort(text)
}

fun toast_showLong(engine: String? = null, id: Int) {
    engine.getToastEngine()?.showLong(id)
}

fun toast_showLong(engine: String? = null, obj: Any?) {
    engine.getToastEngine()?.showLong(obj)
}

fun toast_showLong(engine: String? = null, text: CharSequence?) {
    engine.getToastEngine()?.showLong(text)
}

fun toast_show(engine: String? = null, id: Int) {
    engine.getToastEngine()?.show(id)
}

fun toast_show(engine: String? = null, obj: Any?) {
    engine.getToastEngine()?.show(obj)
}

fun toast_show(engine: String? = null, text: CharSequence?) {
    engine.getToastEngine()?.show(text)
}

fun <Item : IToastEngine.EngineItem> toast_show(engine: String? = null, item: Item) {
    engine.getToastEngine()?.show(item)
}

// ============
// = Toast UI =
// ============

fun toast_setView(engine: String? = null, id: Int) {
    engine.getToastEngine()?.setView(id)
}

fun toast_setGravity(engine: String? = null, gravity: Int) {
    engine.getToastEngine()?.setGravity(gravity)
}

fun toast_setGravity(
    engine: String? = null,
    gravity: Int,
    xOffset: Int,
    yOffset: Int
) {
    engine.getToastEngine()?.setGravity(gravity, xOffset, yOffset)
}

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