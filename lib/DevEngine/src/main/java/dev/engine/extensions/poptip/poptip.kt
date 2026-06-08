package dev.engine.extensions.poptip

import android.app.Activity
import android.view.View
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
 * 获取 PopTip Engine Config
 * @param engine String?
 * @return PopTip Config
 */
fun poptip_getConfig(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.config
}

/**
 * 设置 PopTip Engine Config
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
// = 构建 PopTip =
// ==============

/**
 * 构建 PopTip ( 不显示 )
 * @param engine String?
 * @return PopTip 对象
 */
fun poptip_build(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.build()
}

/**
 * 构建 PopTip ( 不显示 )
 * @receiver PopTip 参数
 * @param engine String?
 * @return PopTip 对象
 */
fun <Item : IPopTipEngine.EngineItem> Item?.poptip_build(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.build(this)
}

/**
 * 构建 PopTip ( 不显示 )
 * @param engine String?
 * @param onBindView 自定义布局
 * @return PopTip 对象
 */
fun poptip_buildView(
    engine: String? = null,
    onBindView: Any?
): Any? {
    return engine.getPopTipEngine()?.buildView(onBindView)
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

// =====================
// = 单例 PopTip 句柄操作 =
// =====================

// 以下方法直接操作 Engine 内部维护的单例 PopTip ( onlyOne 时记录 )

/**
 * 是否使用单例 PopTip
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun poptip_isSinglePopTip(
    engine: String? = null
): Boolean {
    return engine.getPopTipEngine()?.isSinglePopTip ?: false
}

/**
 * 获取单例 PopTip
 * @param engine String?
 * @return 单例 PopTip 对象
 */
fun poptip_getSinglePopTip(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.singlePopTip
}

/**
 * 单例 PopTip 是否正在显示
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun poptip_isShowSinglePopTip(
    engine: String? = null
): Boolean {
    return engine.getPopTipEngine()?.isShowSinglePopTip ?: false
}

/**
 * 关闭单例 PopTip
 * @param engine String?
 */
fun poptip_dismissSinglePopTip(
    engine: String? = null
) {
    engine.getPopTipEngine()?.dismissSinglePopTip()
}

/**
 * 关闭单例 PopTip ( 动画 )
 * @param engine String?
 */
fun poptip_hideSinglePopTip(
    engine: String? = null
) {
    engine.getPopTipEngine()?.hideSinglePopTip()
}

// =================
// = PopTip 句柄操作 =
// =================

// 以下方法对齐 com.kongzue.dialogx.dialogs.PopTip 实例方法
// 统一以 PopTip 句柄作为 receiver, 链式方法返回该句柄便于继续调用

// ==========
// = 显示状态 =
// ==========

/**
 * 指定 PopTip 是否正在显示
 * @receiver PopTip 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.poptip_isShow(
    engine: String? = null
): Boolean {
    return engine.getPopTipEngine()?.isShow(this) ?: false
}

/**
 * 关闭指定 PopTip
 * @receiver PopTip 对象
 * @param engine String?
 */
fun Any?.poptip_dismiss(
    engine: String? = null
) {
    engine.getPopTipEngine()?.dismiss(this)
}

/**
 * 关闭指定 PopTip ( 动画 )
 * @receiver PopTip 对象
 * @param engine String?
 */
fun Any?.poptip_hide(
    engine: String? = null
) {
    engine.getPopTipEngine()?.hide(this)
}

/**
 * 重新显示指定 PopTip ( hide 后复显 )
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_show(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.show(this)
}

/**
 * 重新显示指定 PopTip ( hide 后复显 )
 * @receiver PopTip 对象
 * @param engine String?
 * @param activity 显示的 Activity
 * @return PopTip 对象
 */
fun Any?.poptip_show(
    engine: String? = null,
    activity: Activity?
): Any? {
    return engine.getPopTipEngine()?.show(this, activity)
}

/**
 * 刷新指定 PopTip 界面
 * @receiver PopTip 对象
 * @param engine String?
 */
fun Any?.poptip_refreshUI(
    engine: String? = null
) {
    engine.getPopTipEngine()?.refreshUI(this)
}

// ==========
// = 消失控制 =
// ==========

/**
 * 设置指定 PopTip 自动消失时长
 * @receiver PopTip 对象
 * @param engine String?
 * @param delay 自动消失时长 ( ms )
 * @return PopTip 对象
 */
fun Any?.poptip_autoDismiss(
    engine: String? = null,
    delay: Long
): Any? {
    return engine.getPopTipEngine()?.autoDismiss(this, delay)
}

/**
 * 重置指定 PopTip 自动消失计时器
 * @receiver PopTip 对象
 * @param engine String?
 */
fun Any?.poptip_resetAutoDismissTimer(
    engine: String? = null
) {
    engine.getPopTipEngine()?.resetAutoDismissTimer(this)
}

/**
 * 指定 PopTip 短时间显示
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_showShort(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.showShort(this)
}

/**
 * 指定 PopTip 长时间显示
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_showLong(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.showLong(this)
}

/**
 * 指定 PopTip 常驻显示 ( 不自动消失 )
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_showAlways(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.showAlways(this)
}

/**
 * 指定 PopTip 取消自动消失
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_noAutoDismiss(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.noAutoDismiss(this)
}

// ==========
// = 层级显示 =
// ==========

/**
 * 指定 PopTip 置顶显示
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_bringToFront(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.bringToFront(this)
}

/**
 * 设置指定 PopTip 显示层级
 * @receiver PopTip 对象
 * @param engine String?
 * @param orderIndex 显示层级
 * @return PopTip 对象
 */
fun Any?.poptip_setThisOrderIndex(
    engine: String? = null,
    orderIndex: Int
): Any? {
    return engine.getPopTipEngine()?.setThisOrderIndex(this, orderIndex)
}

// ==========
// = 主题样式 =
// ==========

/**
 * 设置指定 PopTip 主题样式
 * @receiver PopTip 对象
 * @param engine String?
 * @param style 主题样式对象
 * @return PopTip 对象
 */
fun Any?.poptip_setStyle(
    engine: String? = null,
    style: Any?
): Any? {
    return engine.getPopTipEngine()?.setStyle(this, style)
}

/**
 * 设置指定 PopTip 明暗主题
 * @receiver PopTip 对象
 * @param engine String?
 * @param theme 明暗主题 ( PopTipConst.THEME_* )
 * @return PopTip 对象
 */
fun Any?.poptip_setTheme(
    engine: String? = null,
    theme: Int
): Any? {
    return engine.getPopTipEngine()?.setTheme(this, theme)
}

// ============
// = 自定义布局 =
// ============

/**
 * 设置指定 PopTip 自定义布局
 * @receiver PopTip 对象
 * @param engine String?
 * @param onBindView 自定义布局
 * @return PopTip 对象
 */
fun Any?.poptip_setCustomView(
    engine: String? = null,
    onBindView: Any?
): Any? {
    return engine.getPopTipEngine()?.setCustomView(this, onBindView)
}

/**
 * 获取指定 PopTip 自定义布局 View
 * @receiver PopTip 对象
 * @param engine String?
 * @return 自定义布局 View
 */
fun Any?.poptip_getCustomView(
    engine: String? = null
): View? {
    return engine.getPopTipEngine()?.getCustomView(this)
}

/**
 * 移除指定 PopTip 自定义布局
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_removeCustomView(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.removeCustomView(this)
}

// ==========
// = 对齐方式 =
// ==========

/**
 * 获取指定 PopTip 对齐方式
 * @receiver PopTip 对象
 * @param engine String?
 * @return 对齐方式 ( PopTipConst.ALIGN_* )
 */
fun Any?.poptip_getAlign(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getAlign(this) ?: 0
}

/**
 * 设置指定 PopTip 对齐方式
 * @receiver PopTip 对象
 * @param engine String?
 * @param align 对齐方式 ( PopTipConst.ALIGN_* )
 * @return PopTip 对象
 */
fun Any?.poptip_setAlign(
    engine: String? = null,
    align: Int
): Any? {
    return engine.getPopTipEngine()?.setAlign(this, align)
}

// ==========
// = 图标设置 =
// ==========

/**
 * 获取指定 PopTip 图标资源 id
 * @receiver PopTip 对象
 * @param engine String?
 * @return 图标资源 id
 */
fun Any?.poptip_getIconResId(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getIconResId(this) ?: 0
}

/**
 * 设置指定 PopTip 图标资源 id
 * @receiver PopTip 对象
 * @param engine String?
 * @param iconResId 图标资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setIconResId(
    engine: String? = null,
    iconResId: Int
): Any? {
    return engine.getPopTipEngine()?.setIconResId(this, iconResId)
}

/**
 * 设置指定 PopTip 成功状态图标
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_iconSuccess(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.iconSuccess(this)
}

/**
 * 设置指定 PopTip 警告状态图标
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_iconWarning(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.iconWarning(this)
}

/**
 * 设置指定 PopTip 错误状态图标
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_iconError(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.iconError(this)
}

/**
 * 指定 PopTip 图标是否随明暗模式自动染色
 * @receiver PopTip 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.poptip_isAutoTintIconInLightOrDarkMode(
    engine: String? = null
): Boolean {
    return engine.getPopTipEngine()?.isAutoTintIconInLightOrDarkMode(this) ?: false
}

/**
 * 设置指定 PopTip 图标是否随明暗模式自动染色
 * @receiver PopTip 对象
 * @param engine String?
 * @param autoTint 是否自动染色
 * @return PopTip 对象
 */
fun Any?.poptip_setAutoTintIconInLightOrDarkMode(
    engine: String? = null,
    autoTint: Boolean
): Any? {
    return engine.getPopTipEngine()?.setAutoTintIconInLightOrDarkMode(this, autoTint)
}

/**
 * 指定 PopTip 图标是否染色
 * @receiver PopTip 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.poptip_isTintIcon(
    engine: String? = null
): Boolean {
    return engine.getPopTipEngine()?.isTintIcon(this) ?: false
}

/**
 * 设置指定 PopTip 图标是否染色
 * @receiver PopTip 对象
 * @param engine String?
 * @param tintIcon 是否染色
 * @return PopTip 对象
 */
fun Any?.poptip_setTintIcon(
    engine: String? = null,
    tintIcon: Boolean
): Any? {
    return engine.getPopTipEngine()?.setTintIcon(this, tintIcon)
}

// ==========
// = 文本设置 =
// ==========

/**
 * 获取指定 PopTip 提示文本
 * @receiver PopTip 对象
 * @param engine String?
 * @return 提示文本
 */
fun Any?.poptip_getMessage(
    engine: String? = null
): CharSequence? {
    return engine.getPopTipEngine()?.getMessage(this)
}

/**
 * 设置指定 PopTip 提示文本
 * @receiver PopTip 对象
 * @param engine String?
 * @param message 提示文本
 * @return PopTip 对象
 */
fun Any?.poptip_setMessage(
    engine: String? = null,
    message: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.setMessage(this, message)
}

/**
 * 设置指定 PopTip 提示文本
 * @receiver PopTip 对象
 * @param engine String?
 * @param messageResId 提示文本资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setMessage(
    engine: String? = null,
    messageResId: Int
): Any? {
    return engine.getPopTipEngine()?.setMessage(this, messageResId)
}

/**
 * 追加指定 PopTip 提示文本
 * @receiver PopTip 对象
 * @param engine String?
 * @param message 追加文本
 * @return PopTip 对象
 */
fun Any?.poptip_appendMessage(
    engine: String? = null,
    message: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.appendMessage(this, message)
}

/**
 * 获取指定 PopTip 提示文本样式
 * @receiver PopTip 对象
 * @param engine String?
 * @return 提示文本样式对象
 */
fun Any?.poptip_getMessageTextInfo(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.getMessageTextInfo(this)
}

/**
 * 设置指定 PopTip 提示文本样式
 * @receiver PopTip 对象
 * @param engine String?
 * @param messageTextInfo 提示文本样式对象
 * @return PopTip 对象
 */
fun Any?.poptip_setMessageTextInfo(
    engine: String? = null,
    messageTextInfo: Any?
): Any? {
    return engine.getPopTipEngine()?.setMessageTextInfo(this, messageTextInfo)
}

// ==========
// = 按钮设置 =
// ==========

/**
 * 获取指定 PopTip 按钮文本
 * @receiver PopTip 对象
 * @param engine String?
 * @return 按钮文本
 */
fun Any?.poptip_getButtonText(
    engine: String? = null
): CharSequence? {
    return engine.getPopTipEngine()?.getButtonText(this)
}

/**
 * 设置指定 PopTip 按钮文本
 * @receiver PopTip 对象
 * @param engine String?
 * @param buttonText 按钮文本
 * @return PopTip 对象
 */
fun Any?.poptip_setButton(
    engine: String? = null,
    buttonText: CharSequence?
): Any? {
    return engine.getPopTipEngine()?.setButton(this, buttonText)
}

/**
 * 设置指定 PopTip 按钮文本
 * @receiver PopTip 对象
 * @param engine String?
 * @param buttonTextResId 按钮文本资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setButton(
    engine: String? = null,
    buttonTextResId: Int
): Any? {
    return engine.getPopTipEngine()?.setButton(this, buttonTextResId)
}

/**
 * 设置指定 PopTip 按钮点击事件
 * @receiver PopTip 对象
 * @param engine String?
 * @param listener 按钮点击事件
 * @return PopTip 对象
 */
fun Any?.poptip_setButton(
    engine: String? = null,
    listener: IPopTipEngine.OnButtonClickListener?
): Any? {
    return engine.getPopTipEngine()?.setButton(this, listener)
}

/**
 * 设置指定 PopTip 按钮文本及点击事件
 * @receiver PopTip 对象
 * @param engine String?
 * @param buttonText 按钮文本
 * @param listener 按钮点击事件
 * @return PopTip 对象
 */
fun Any?.poptip_setButton(
    engine: String? = null,
    buttonText: CharSequence?,
    listener: IPopTipEngine.OnButtonClickListener?
): Any? {
    return engine.getPopTipEngine()?.setButton(this, buttonText, listener)
}

/**
 * 设置指定 PopTip 按钮文本及点击事件
 * @receiver PopTip 对象
 * @param engine String?
 * @param buttonTextResId 按钮文本资源 id
 * @param listener 按钮点击事件
 * @return PopTip 对象
 */
fun Any?.poptip_setButton(
    engine: String? = null,
    buttonTextResId: Int,
    listener: IPopTipEngine.OnButtonClickListener?
): Any? {
    return engine.getPopTipEngine()?.setButton(this, buttonTextResId, listener)
}

/**
 * 获取指定 PopTip 按钮文本样式
 * @receiver PopTip 对象
 * @param engine String?
 * @return 按钮文本样式对象
 */
fun Any?.poptip_getButtonTextInfo(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.getButtonTextInfo(this)
}

/**
 * 设置指定 PopTip 按钮文本样式
 * @receiver PopTip 对象
 * @param engine String?
 * @param buttonTextInfo 按钮文本样式对象
 * @return PopTip 对象
 */
fun Any?.poptip_setButtonTextInfo(
    engine: String? = null,
    buttonTextInfo: Any?
): Any? {
    return engine.getPopTipEngine()?.setButtonTextInfo(this, buttonTextInfo)
}

// ==========
// = 点击事件 =
// ==========

/**
 * 设置指定 PopTip 按钮点击事件
 * @receiver PopTip 对象
 * @param engine String?
 * @param listener 按钮点击事件
 * @return PopTip 对象
 */
fun Any?.poptip_setOnButtonClickListener(
    engine: String? = null,
    listener: IPopTipEngine.OnButtonClickListener?
): Any? {
    return engine.getPopTipEngine()?.setOnButtonClickListener(this, listener)
}

/**
 * 设置指定 PopTip 自身点击事件
 * @receiver PopTip 对象
 * @param engine String?
 * @param listener 点击事件
 * @return PopTip 对象
 */
fun Any?.poptip_setOnPopTipClickListener(
    engine: String? = null,
    listener: IPopTipEngine.OnButtonClickListener?
): Any? {
    return engine.getPopTipEngine()?.setOnPopTipClickListener(this, listener)
}

// ==========
// = 背景圆角 =
// ==========

/**
 * 获取指定 PopTip 背景色
 * @receiver PopTip 对象
 * @param engine String?
 * @return 背景色 ( ColorInt )
 */
fun Any?.poptip_getBackgroundColor(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getBackgroundColor(this) ?: 0
}

/**
 * 设置指定 PopTip 背景色
 * @receiver PopTip 对象
 * @param engine String?
 * @param backgroundColor 背景色 ( ColorInt )
 * @return PopTip 对象
 */
fun Any?.poptip_setBackgroundColor(
    engine: String? = null,
    backgroundColor: Int
): Any? {
    return engine.getPopTipEngine()?.setBackgroundColor(this, backgroundColor)
}

/**
 * 设置指定 PopTip 背景色
 * @receiver PopTip 对象
 * @param engine String?
 * @param backgroundColorResId 背景色资源 id ( ColorRes )
 * @return PopTip 对象
 */
fun Any?.poptip_setBackgroundColorRes(
    engine: String? = null,
    backgroundColorResId: Int
): Any? {
    return engine.getPopTipEngine()?.setBackgroundColorRes(this, backgroundColorResId)
}

/**
 * 获取指定 PopTip 圆角 ( px )
 * @receiver PopTip 对象
 * @param engine String?
 * @return 圆角 ( px )
 */
fun Any?.poptip_getRadius(
    engine: String? = null
): Float {
    return engine.getPopTipEngine()?.getRadius(this) ?: 0F
}

/**
 * 设置指定 PopTip 圆角 ( px )
 * @receiver PopTip 对象
 * @param engine String?
 * @param radiusPx 圆角 ( px )
 * @return PopTip 对象
 */
fun Any?.poptip_setRadius(
    engine: String? = null,
    radiusPx: Float
): Any? {
    return engine.getPopTipEngine()?.setRadius(this, radiusPx)
}

// ==========
// = 动画设置 =
// ==========

/**
 * 获取指定 PopTip 进入动画时长 ( ms )
 * @receiver PopTip 对象
 * @param engine String?
 * @return 进入动画时长 ( ms )
 */
fun Any?.poptip_getEnterAnimDuration(
    engine: String? = null
): Long {
    return engine.getPopTipEngine()?.getEnterAnimDuration(this) ?: 0L
}

/**
 * 设置指定 PopTip 进入动画时长 ( ms )
 * @receiver PopTip 对象
 * @param engine String?
 * @param enterAnimDuration 进入动画时长 ( ms )
 * @return PopTip 对象
 */
fun Any?.poptip_setEnterAnimDuration(
    engine: String? = null,
    enterAnimDuration: Long
): Any? {
    return engine.getPopTipEngine()?.setEnterAnimDuration(this, enterAnimDuration)
}

/**
 * 获取指定 PopTip 退出动画时长 ( ms )
 * @receiver PopTip 对象
 * @param engine String?
 * @return 退出动画时长 ( ms )
 */
fun Any?.poptip_getExitAnimDuration(
    engine: String? = null
): Long {
    return engine.getPopTipEngine()?.getExitAnimDuration(this) ?: 0L
}

/**
 * 设置指定 PopTip 退出动画时长 ( ms )
 * @receiver PopTip 对象
 * @param engine String?
 * @param exitAnimDuration 退出动画时长 ( ms )
 * @return PopTip 对象
 */
fun Any?.poptip_setExitAnimDuration(
    engine: String? = null,
    exitAnimDuration: Long
): Any? {
    return engine.getPopTipEngine()?.setExitAnimDuration(this, exitAnimDuration)
}

/**
 * 设置指定 PopTip 进出场动画资源
 * @receiver PopTip 对象
 * @param engine String?
 * @param enterResId 进入动画资源 id
 * @param exitResId 退出动画资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setAnimResId(
    engine: String? = null,
    enterResId: Int,
    exitResId: Int
): Any? {
    return engine.getPopTipEngine()?.setAnimResId(this, enterResId, exitResId)
}

/**
 * 设置指定 PopTip 进入动画资源
 * @receiver PopTip 对象
 * @param engine String?
 * @param enterResId 进入动画资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setEnterAnimResId(
    engine: String? = null,
    enterResId: Int
): Any? {
    return engine.getPopTipEngine()?.setEnterAnimResId(this, enterResId)
}

/**
 * 设置指定 PopTip 退出动画资源
 * @receiver PopTip 对象
 * @param engine String?
 * @param exitResId 退出动画资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setExitAnimResId(
    engine: String? = null,
    exitResId: Int
): Any? {
    return engine.getPopTipEngine()?.setExitAnimResId(this, exitResId)
}

/**
 * 获取指定 PopTip 动画实现
 * @receiver PopTip 对象
 * @param engine String?
 * @return 动画实现对象
 */
fun Any?.poptip_getDialogXAnimImpl(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.getDialogXAnimImpl(this)
}

/**
 * 设置指定 PopTip 动画实现
 * @receiver PopTip 对象
 * @param engine String?
 * @param animImpl 动画实现对象
 * @return PopTip 对象
 */
fun Any?.poptip_setDialogXAnimImpl(
    engine: String? = null,
    animImpl: Any?
): Any? {
    return engine.getPopTipEngine()?.setDialogXAnimImpl(this, animImpl)
}

// ==========
// = 其他配置 =
// ==========

/**
 * 设置指定 PopTip 是否启用振动反馈
 * @receiver PopTip 对象
 * @param engine String?
 * @param enabled 是否启用振动反馈
 * @return PopTip 对象
 */
fun Any?.poptip_setHapticFeedbackEnabled(
    engine: String? = null,
    enabled: Boolean
): Any? {
    return engine.getPopTipEngine()?.setHapticFeedbackEnabled(this, enabled)
}

/**
 * 设置指定 PopTip 实现模式
 * @receiver PopTip 对象
 * @param engine String?
 * @param dialogImplMode 实现模式 ( PopTipConst.IMPL_MODE_* )
 * @return PopTip 对象
 */
fun Any?.poptip_setDialogImplMode(
    engine: String? = null,
    dialogImplMode: Int
): Any? {
    return engine.getPopTipEngine()?.setDialogImplMode(this, dialogImplMode)
}

// ============
// = 外边距设置 =
// ============

/**
 * 设置指定 PopTip 外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param left 左边距
 * @param top 上边距
 * @param right 右边距
 * @param bottom 下边距
 * @return PopTip 对象
 */
fun Any?.poptip_setMargin(
    engine: String? = null,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
): Any? {
    return engine.getPopTipEngine()?.setMargin(this, left, top, right, bottom)
}

/**
 * 获取指定 PopTip 左外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @return 左外边距
 */
fun Any?.poptip_getMarginLeft(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getMarginLeft(this) ?: 0
}

/**
 * 设置指定 PopTip 左外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param left 左边距
 * @return PopTip 对象
 */
fun Any?.poptip_setMarginLeft(
    engine: String? = null,
    left: Int
): Any? {
    return engine.getPopTipEngine()?.setMarginLeft(this, left)
}

/**
 * 获取指定 PopTip 上外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @return 上外边距
 */
fun Any?.poptip_getMarginTop(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getMarginTop(this) ?: 0
}

/**
 * 设置指定 PopTip 上外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param top 上边距
 * @return PopTip 对象
 */
fun Any?.poptip_setMarginTop(
    engine: String? = null,
    top: Int
): Any? {
    return engine.getPopTipEngine()?.setMarginTop(this, top)
}

/**
 * 获取指定 PopTip 右外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @return 右外边距
 */
fun Any?.poptip_getMarginRight(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getMarginRight(this) ?: 0
}

/**
 * 设置指定 PopTip 右外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param right 右边距
 * @return PopTip 对象
 */
fun Any?.poptip_setMarginRight(
    engine: String? = null,
    right: Int
): Any? {
    return engine.getPopTipEngine()?.setMarginRight(this, right)
}

/**
 * 获取指定 PopTip 下外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @return 下外边距
 */
fun Any?.poptip_getMarginBottom(
    engine: String? = null
): Int {
    return engine.getPopTipEngine()?.getMarginBottom(this) ?: 0
}

/**
 * 设置指定 PopTip 下外边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param bottom 下边距
 * @return PopTip 对象
 */
fun Any?.poptip_setMarginBottom(
    engine: String? = null,
    bottom: Int
): Any? {
    return engine.getPopTipEngine()?.setMarginBottom(this, bottom)
}

/**
 * 设置指定 PopTip 根布局内边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param padding 内边距
 * @return PopTip 对象
 */
fun Any?.poptip_setRootPadding(
    engine: String? = null,
    padding: Int
): Any? {
    return engine.getPopTipEngine()?.setRootPadding(this, padding)
}

/**
 * 设置指定 PopTip 根布局内边距
 * @receiver PopTip 对象
 * @param engine String?
 * @param paddingLeft 左内边距
 * @param paddingTop 上内边距
 * @param paddingRight 右内边距
 * @param paddingBottom 下内边距
 * @return PopTip 对象
 */
fun Any?.poptip_setRootPadding(
    engine: String? = null,
    paddingLeft: Int,
    paddingTop: Int,
    paddingRight: Int,
    paddingBottom: Int
): Any? {
    return engine.getPopTipEngine()?.setRootPadding(
        this, paddingLeft, paddingTop, paddingRight, paddingBottom
    )
}

// ==========
// = 生命周期 =
// ==========

/**
 * 设置指定 PopTip 显示生命周期监听
 * @receiver PopTip 对象
 * @param engine String?
 * @param listener 显示生命周期监听
 * @return PopTip 对象
 */
fun Any?.poptip_setLifecycleListener(
    engine: String? = null,
    listener: IPopTipEngine.OnPopTipLifecycleListener?
): Any? {
    return engine.getPopTipEngine()?.setLifecycleListener(this, listener)
}

/**
 * 设置指定 PopTip 显示回调
 * @receiver PopTip 对象
 * @param engine String?
 * @param runnable 显示回调
 * @return PopTip 对象
 */
fun Any?.poptip_onShow(
    engine: String? = null,
    runnable: IPopTipEngine.OnPopTipRunnable?
): Any? {
    return engine.getPopTipEngine()?.onShow(this, runnable)
}

/**
 * 设置指定 PopTip 关闭回调
 * @receiver PopTip 对象
 * @param engine String?
 * @param runnable 关闭回调
 * @return PopTip 对象
 */
fun Any?.poptip_onDismiss(
    engine: String? = null,
    runnable: IPopTipEngine.OnPopTipRunnable?
): Any? {
    return engine.getPopTipEngine()?.onDismiss(this, runnable)
}

/**
 * 绑定指定 PopTip 随 LifecycleOwner 关闭
 * @receiver PopTip 对象
 * @param engine String?
 * @param owner LifecycleOwner 对象
 * @return PopTip 对象
 */
fun Any?.poptip_bindDismissWithLifecycleOwner(
    engine: String? = null,
    owner: Any?
): Any? {
    return engine.getPopTipEngine()?.bindDismissWithLifecycleOwner(this, owner)
}

// ============
// = 动作与数据 =
// ============

/**
 * 设置指定 PopTip 快捷功能键动作
 * @receiver PopTip 对象
 * @param engine String?
 * @param actionId 动作 id
 * @param runnable 动作执行体
 * @return PopTip 对象
 */
fun Any?.poptip_setActionRunnable(
    engine: String? = null,
    actionId: Int,
    runnable: IPopTipEngine.OnPopTipRunnable?
): Any? {
    return engine.getPopTipEngine()?.setActionRunnable(this, actionId, runnable)
}

/**
 * 清除指定 PopTip 快捷功能键动作
 * @receiver PopTip 对象
 * @param engine String?
 * @param actionId 动作 id
 * @return PopTip 对象
 */
fun Any?.poptip_cleanAction(
    engine: String? = null,
    actionId: Int
): Any? {
    return engine.getPopTipEngine()?.cleanAction(this, actionId)
}

/**
 * 清除指定 PopTip 全部快捷功能键动作
 * @receiver PopTip 对象
 * @param engine String?
 * @return PopTip 对象
 */
fun Any?.poptip_cleanAllAction(
    engine: String? = null
): Any? {
    return engine.getPopTipEngine()?.cleanAllAction(this)
}

/**
 * 设置指定 PopTip 临时存储数据
 * @receiver PopTip 对象
 * @param engine String?
 * @param key 数据 key
 * @param obj 数据值
 * @return PopTip 对象
 */
fun Any?.poptip_setData(
    engine: String? = null,
    key: String?,
    obj: Any?
): Any? {
    return engine.getPopTipEngine()?.setData(this, key, obj)
}

// ==========
// = 弹窗布局 =
// ==========

/**
 * 设置指定 PopTip 自定义弹窗布局资源 id
 * @receiver PopTip 对象
 * @param engine String?
 * @param customDialogLayoutId 自定义弹窗布局资源 id
 * @return PopTip 对象
 */
fun Any?.poptip_setCustomDialogLayoutResId(
    engine: String? = null,
    customDialogLayoutId: Int
): Any? {
    return engine.getPopTipEngine()?.setCustomDialogLayoutResId(this, customDialogLayoutId)
}

/**
 * 设置指定 PopTip 自定义弹窗布局资源 id
 * @receiver PopTip 对象
 * @param engine String?
 * @param customDialogLayoutId 自定义弹窗布局资源 id
 * @param isLightTheme 是否亮色主题布局
 * @return PopTip 对象
 */
fun Any?.poptip_setCustomDialogLayoutResId(
    engine: String? = null,
    customDialogLayoutId: Int,
    isLightTheme: Boolean
): Any? {
    return engine.getPopTipEngine()?.setCustomDialogLayoutResId(
        this, customDialogLayoutId, isLightTheme
    )
}