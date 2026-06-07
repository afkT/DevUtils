package dev.engine.extensions.poptip

import android.app.Activity
import dev.engine.DevEngine
import dev.engine.poptip.IPopTipEngine

// ===========================================
// = IPopTipEngine<EngineConfig, EngineItem> =
// ===========================================

/**
 * 通过 Key 获取 PopTip Engine
 * @receiver String?
 * @return IPopTipEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 PopTip Engine
 */
fun String?.getPopTipEngine(): IPopTipEngine<
        in IPopTipEngine.EngineConfig,
        in IPopTipEngine.EngineItem>? {
    DevEngine.getPopTip(this)?.let { value ->
        return value
    }
    return DevEngine.getPopTip()
}

// =====================
// = Key PopTip Engine =
// =====================

// =============
// = 对外公开方法 =
// =============

/**
 * 获取 PopTip Config
 * @param engine String?
 * @return PopTip Config
 */
fun poptip_getConfig(engine: String? = null): Any? {
    return engine.getPopTipEngine()?.config
}

/**
 * 设置 PopTip Config
 * @param engine String?
 * @param config PopTip Config
 */
fun <Config : IPopTipEngine.EngineConfig> poptip_setConfig(
    engine: String? = null,
    config: Config?
) {
    engine.getPopTipEngine()?.setConfig(config)
}

// ==============
// = 默认 PopTip =
// ==============

/**
 * 显示 PopTip
 * @param engine String?
 * @param text 提示文本
 * @return PopTip 对象
 */
fun poptip_show(
    engine: String? = null,
    text: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.show(text)
}

/**
 * 显示 PopTip
 * @param engine String?
 * @param textResId 提示文本资源 id
 * @return PopTip 对象
 */
fun poptip_show(
    engine: String? = null,
    textResId: Int
): Any? {
    return engine.getPopTipEngine()?.show(textResId)
}

/**
 * 显示 PopTip
 * @param engine String?
 * @param text 提示文本
 * @param buttonText 按钮文本
 * @return PopTip 对象
 */
fun poptip_show(
    engine: String? = null,
    text: CharSequence?,
    buttonText: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.show(text, buttonText)
}

/**
 * 显示 PopTip
 * @param engine String?
 * @param iconResId 图标资源 id
 * @param text 提示文本
 * @return PopTip 对象
 */
fun poptip_show(
    engine: String? = null,
    iconResId: Int,
    text: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.show(iconResId, text)
}

/**
 * 显示 PopTip
 * @receiver PopTip 参数
 * @param engine String?
 * @return PopTip 对象
 */
fun <Item : IPopTipEngine.EngineItem> Item?.poptip_show(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.show(this)
}

/**
 * 显示 PopTip
 * @receiver PopTip 参数
 * @param engine String?
 * @param activity 显示的 Activity
 * @return PopTip 对象
 */
fun <Item : IPopTipEngine.EngineItem> Item?.poptip_show(
    engine: String? = null,
    activity: Activity?
): Any? {
    return engine.getPopTipEngine()?.show(activity, this)
}