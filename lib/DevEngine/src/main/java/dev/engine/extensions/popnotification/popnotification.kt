package dev.engine.extensions.popnotification

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LifecycleOwner
import dev.engine.DevEngine
import dev.engine.popnotification.IPopNotificationEngine

// ====================================================
// = IPopNotificationEngine<EngineConfig, EngineItem> =
// ====================================================

/**
 * 通过 Key 获取 PopNotification Engine
 * @receiver String?
 * @return IPopNotificationEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 PopNotification Engine
 */
fun String?.getPopNotificationEngine(): IPopNotificationEngine<
        in IPopNotificationEngine.EngineConfig,
        in IPopNotificationEngine.EngineItem>? {
    DevEngine.getPopNotification(this)?.let { value ->
        return value
    }
    return DevEngine.getPopNotification()
}

// ==============================
// = Key PopNotification Engine =
// ==============================

// =============
// = 对外公开方法 =
// =============

/**
 * 获取 PopNotification Engine Config
 * @param engine String?
 * @return PopNotification Config
 */
fun popnotification_getConfig(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.config
}

/**
 * 设置 PopNotification Engine Config
 * @param engine String?
 */
fun <Config : IPopNotificationEngine.EngineConfig> Config?.popnotification_setConfig(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.setConfig(this)
}

// =======================
// = 构建 PopNotification =
// =======================

/**
 * 构建 PopNotification ( 不显示 )
 * @param engine String?
 * @return PopNotification 对象
 */
fun popnotification_build(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.build()
}

/**
 * 构建 PopNotification ( 不显示 )
 * @receiver PopNotification 参数
 * @param engine String?
 * @return PopNotification 对象
 */
fun <Item : IPopNotificationEngine.EngineItem> Item?.popnotification_build(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.build(this)
}

/**
 * 构建 PopNotification ( 不显示 )
 * @receiver 自定义布局
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_buildView(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.buildView(this)
}

// =======================
// = 默认 PopNotification =
// =======================

/**
 * 显示 PopNotification
 * @receiver 标题文本
 * @param engine String?
 * @return PopNotification 对象
 */
fun CharSequence?.popnotification_show(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.show(this)
}

/**
 * 显示 PopNotification
 * @receiver 标题文本资源 id
 * @param engine String?
 * @return PopNotification 对象
 */
fun Int.popnotification_show(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.show(this)
}

/**
 * 显示 PopNotification
 * @receiver 标题文本
 * @param engine String?
 * @param message 提示文本
 * @return PopNotification 对象
 */
fun CharSequence?.popnotification_show(
    engine: String? = null,
    message: CharSequence?
): Any? {
    return engine.getPopNotificationEngine()?.show(this, message)
}

/**
 * 显示 PopNotification
 * @receiver 标题文本
 * @param engine String?
 * @param iconResId 图标资源 id
 * @return PopNotification 对象
 */
fun CharSequence?.popnotification_show(
    engine: String? = null,
    iconResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.show(iconResId, this)
}

/**
 * 显示 PopNotification
 * @receiver PopNotification 参数
 * @param engine String?
 * @return PopNotification 对象
 */
fun <Item : IPopNotificationEngine.EngineItem> Item?.popnotification_show(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.show(this)
}

/**
 * 显示 PopNotification
 * @receiver PopNotification 参数
 * @param engine String?
 * @param activity 显示的 Activity
 * @return PopNotification 对象
 */
fun <Item : IPopNotificationEngine.EngineItem> Item?.popnotification_show(
    engine: String? = null,
    activity: Activity?
): Any? {
    return engine.getPopNotificationEngine()?.show(activity, this)
}

// ===============================
// = 单例 PopNotification 句柄操作 =
// ===============================

// 以下方法直接操作 Engine 内部维护的单例 PopNotification ( onlyOne 时记录 )

/**
 * 是否使用单例 PopNotification
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun popnotification_isSinglePopNotification(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.isSinglePopNotification ?: false
}

/**
 * 获取单例 PopNotification
 * @param engine String?
 * @return 单例 PopNotification 对象
 */
fun popnotification_getSinglePopNotification(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.singlePopNotification
}

/**
 * 单例 PopNotification 是否正在显示
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun popnotification_isShowSinglePopNotification(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.isShowSinglePopNotification ?: false
}

/**
 * 关闭单例 PopNotification
 * @param engine String?
 */
fun popnotification_dismissSinglePopNotification(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.dismissSinglePopNotification()
}

/**
 * 关闭单例 PopNotification ( 动画 )
 * @param engine String?
 */
fun popnotification_hideSinglePopNotification(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.hideSinglePopNotification()
}

// ==========================
// = PopNotification 句柄操作 =
// ==========================

// 以下方法对齐 com.kongzue.dialogx.dialogs.PopNotification 实例方法
// 统一以 PopNotification 句柄作为 receiver, 链式方法返回该句柄便于继续调用

// ==========
// = 显示状态 =
// ==========

/**
 * 指定 PopNotification 是否正在显示
 * @receiver PopNotification 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.popnotification_isShow(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.isShow(this) ?: false
}

/**
 * 关闭指定 PopNotification
 * @receiver PopNotification 对象
 * @param engine String?
 */
fun Any?.popnotification_dismiss(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.dismiss(this)
}

/**
 * 关闭指定 PopNotification ( 动画 )
 * @receiver PopNotification 对象
 * @param engine String?
 */
fun Any?.popnotification_hide(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.hide(this)
}

/**
 * 重新显示指定 PopNotification ( hide 后复显 )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_show(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.show(this)
}

/**
 * 重新显示指定 PopNotification ( hide 后复显 )
 * @receiver PopNotification 对象
 * @param engine String?
 * @param activity 显示的 Activity
 * @return PopNotification 对象
 */
fun Any?.popnotification_show(
    engine: String? = null,
    activity: Activity?
): Any? {
    return engine.getPopNotificationEngine()?.show(this, activity)
}

/**
 * 刷新指定 PopNotification 界面
 * @receiver PopNotification 对象
 * @param engine String?
 */
fun Any?.popnotification_refreshUI(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.refreshUI(this)
}

// ==========
// = 消失控制 =
// ==========

/**
 * 设置指定 PopNotification 自动消失时长
 * @receiver PopNotification 对象
 * @param engine String?
 * @param delay 自动消失时长 ( ms )
 * @return PopNotification 对象
 */
fun Any?.popnotification_autoDismiss(
    engine: String? = null,
    delay: Long
): Any? {
    return engine.getPopNotificationEngine()?.autoDismiss(this, delay)
}

/**
 * 重置指定 PopNotification 自动消失计时器
 * @receiver PopNotification 对象
 * @param engine String?
 */
fun Any?.popnotification_resetAutoDismissTimer(
    engine: String? = null
) {
    engine.getPopNotificationEngine()?.resetAutoDismissTimer(this)
}

/**
 * 指定 PopNotification 短时间显示
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_showShort(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.showShort(this)
}

/**
 * 指定 PopNotification 长时间显示
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_showLong(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.showLong(this)
}

/**
 * 指定 PopNotification 常驻显示 ( 不自动消失 )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_showAlways(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.showAlways(this)
}

/**
 * 指定 PopNotification 取消自动消失
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_noAutoDismiss(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.noAutoDismiss(this)
}

// ==========
// = 层级显示 =
// ==========

/**
 * 指定 PopNotification 置顶显示
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_bringToFront(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.bringToFront(this)
}

/**
 * 设置指定 PopNotification 显示层级
 * @receiver PopNotification 对象
 * @param engine String?
 * @param orderIndex 显示层级
 * @return PopNotification 对象
 */
fun Any?.popnotification_setThisOrderIndex(
    engine: String? = null,
    orderIndex: Int
): Any? {
    return engine.getPopNotificationEngine()?.setThisOrderIndex(this, orderIndex)
}

// ==========
// = 主题样式 =
// ==========

/**
 * 设置指定 PopNotification 主题样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param style 主题样式对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_setStyle(
    engine: String? = null,
    style: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setStyle(this, style)
}

/**
 * 设置指定 PopNotification 明暗主题
 * @receiver PopNotification 对象
 * @param engine String?
 * @param theme 明暗主题 ( PopNotificationConst.THEME_* )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setTheme(
    engine: String? = null,
    theme: Int
): Any? {
    return engine.getPopNotificationEngine()?.setTheme(this, theme)
}

// ============
// = 自定义布局 =
// ============

/**
 * 设置指定 PopNotification 自定义布局
 * @receiver PopNotification 对象
 * @param engine String?
 * @param onBindView 自定义布局
 * @return PopNotification 对象
 */
fun Any?.popnotification_setCustomView(
    engine: String? = null,
    onBindView: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setCustomView(this, onBindView)
}

/**
 * 获取指定 PopNotification 自定义布局 View
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 自定义布局 View
 */
fun Any?.popnotification_getCustomView(
    engine: String? = null
): View? {
    return engine.getPopNotificationEngine()?.getCustomView(this)
}

/**
 * 移除指定 PopNotification 自定义布局
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_removeCustomView(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.removeCustomView(this)
}

// ==========
// = 对齐方式 =
// ==========

/**
 * 获取指定 PopNotification 对齐方式
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 对齐方式 ( PopNotificationConst.ALIGN_* )
 */
fun Any?.popnotification_getAlign(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getAlign(this) ?: 0
}

/**
 * 设置指定 PopNotification 对齐方式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param align 对齐方式 ( PopNotificationConst.ALIGN_* )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setAlign(
    engine: String? = null,
    align: Int
): Any? {
    return engine.getPopNotificationEngine()?.setAlign(this, align)
}

// ==========
// = 图标设置 =
// ==========

/**
 * 获取指定 PopNotification 图标资源 id
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 图标资源 id
 */
fun Any?.popnotification_getIconResId(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getIconResId(this) ?: 0
}

/**
 * 设置指定 PopNotification 图标资源 id
 * @receiver PopNotification 对象
 * @param engine String?
 * @param iconResId 图标资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setIconResId(
    engine: String? = null,
    iconResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setIconResId(this, iconResId)
}

/**
 * 获取指定 PopNotification 图标 Bitmap
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 图标 Bitmap
 */
fun Any?.popnotification_getIconBitmap(
    engine: String? = null
): Bitmap? {
    return engine.getPopNotificationEngine()?.getIconBitmap(this)
}

/**
 * 设置指定 PopNotification 图标 Bitmap
 * @receiver PopNotification 对象
 * @param engine String?
 * @param bitmap 图标 Bitmap
 * @return PopNotification 对象
 */
fun Any?.popnotification_setIcon(
    engine: String? = null,
    bitmap: Bitmap?
): Any? {
    return engine.getPopNotificationEngine()?.setIcon(this, bitmap)
}

/**
 * 获取指定 PopNotification 图标 Drawable
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 图标 Drawable
 */
fun Any?.popnotification_getIconDrawable(
    engine: String? = null
): Drawable? {
    return engine.getPopNotificationEngine()?.getIconDrawable(this)
}

/**
 * 设置指定 PopNotification 图标 Drawable
 * @receiver PopNotification 对象
 * @param engine String?
 * @param drawable 图标 Drawable
 * @return PopNotification 对象
 */
fun Any?.popnotification_setIcon(
    engine: String? = null,
    drawable: Drawable?
): Any? {
    return engine.getPopNotificationEngine()?.setIcon(this, drawable)
}

/**
 * 获取指定 PopNotification 图标尺寸 ( px )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 图标尺寸 ( px )
 */
fun Any?.popnotification_getIconSize(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getIconSize(this) ?: 0
}

/**
 * 设置指定 PopNotification 图标尺寸 ( px )
 * @receiver PopNotification 对象
 * @param engine String?
 * @param iconSize 图标尺寸 ( px )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setIconSize(
    engine: String? = null,
    iconSize: Int
): Any? {
    return engine.getPopNotificationEngine()?.setIconSize(this, iconSize)
}

// ==========
// = 文本设置 =
// ==========

/**
 * 获取指定 PopNotification 标题文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 标题文本
 */
fun Any?.popnotification_getTitle(
    engine: String? = null
): CharSequence? {
    return engine.getPopNotificationEngine()?.getTitle(this)
}

/**
 * 设置指定 PopNotification 标题文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param title 标题文本
 * @return PopNotification 对象
 */
fun Any?.popnotification_setTitle(
    engine: String? = null,
    title: CharSequence?
): Any? {
    return engine.getPopNotificationEngine()?.setTitle(this, title)
}

/**
 * 获取指定 PopNotification 标题文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 标题文本样式对象
 */
fun Any?.popnotification_getTitleTextInfo(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.getTitleTextInfo(this)
}

/**
 * 设置指定 PopNotification 标题文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param titleTextInfo 标题文本样式对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_setTitleTextInfo(
    engine: String? = null,
    titleTextInfo: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setTitleTextInfo(this, titleTextInfo)
}

/**
 * 获取指定 PopNotification 提示文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 提示文本
 */
fun Any?.popnotification_getMessage(
    engine: String? = null
): CharSequence? {
    return engine.getPopNotificationEngine()?.getMessage(this)
}

/**
 * 设置指定 PopNotification 提示文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param message 提示文本
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMessage(
    engine: String? = null,
    message: CharSequence?
): Any? {
    return engine.getPopNotificationEngine()?.setMessage(this, message)
}

/**
 * 设置指定 PopNotification 提示文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param messageResId 提示文本资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMessage(
    engine: String? = null,
    messageResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMessage(this, messageResId)
}

/**
 * 追加指定 PopNotification 提示文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param message 追加文本
 * @return PopNotification 对象
 */
fun Any?.popnotification_appendMessage(
    engine: String? = null,
    message: CharSequence?
): Any? {
    return engine.getPopNotificationEngine()?.appendMessage(this, message)
}

// ==========
// = 按钮设置 =
// ==========

/**
 * 获取指定 PopNotification 按钮文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 按钮文本
 */
fun Any?.popnotification_getButtonText(
    engine: String? = null
): CharSequence? {
    return engine.getPopNotificationEngine()?.getButtonText(this)
}

/**
 * 设置指定 PopNotification 按钮文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param buttonText 按钮文本
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButton(
    engine: String? = null,
    buttonText: CharSequence?
): Any? {
    return engine.getPopNotificationEngine()?.setButton(this, buttonText)
}

/**
 * 设置指定 PopNotification 按钮文本
 * @receiver PopNotification 对象
 * @param engine String?
 * @param buttonTextResId 按钮文本资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButton(
    engine: String? = null,
    buttonTextResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setButton(this, buttonTextResId)
}

/**
 * 设置指定 PopNotification 按钮点击事件
 * @receiver PopNotification 对象
 * @param engine String?
 * @param listener 按钮点击事件
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButton(
    engine: String? = null,
    listener: IPopNotificationEngine.OnButtonClickListener?
): Any? {
    return engine.getPopNotificationEngine()?.setButton(this, listener)
}

/**
 * 设置指定 PopNotification 按钮文本及点击事件
 * @receiver PopNotification 对象
 * @param engine String?
 * @param buttonText 按钮文本
 * @param listener 按钮点击事件
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButton(
    engine: String? = null,
    buttonText: CharSequence?,
    listener: IPopNotificationEngine.OnButtonClickListener?
): Any? {
    return engine.getPopNotificationEngine()?.setButton(this, buttonText, listener)
}

/**
 * 设置指定 PopNotification 按钮文本及点击事件
 * @receiver PopNotification 对象
 * @param engine String?
 * @param buttonTextResId 按钮文本资源 id
 * @param listener 按钮点击事件
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButton(
    engine: String? = null,
    buttonTextResId: Int,
    listener: IPopNotificationEngine.OnButtonClickListener?
): Any? {
    return engine.getPopNotificationEngine()?.setButton(this, buttonTextResId, listener)
}

/**
 * 获取指定 PopNotification 提示文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 提示文本样式对象
 */
fun Any?.popnotification_getMessageTextInfo(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.getMessageTextInfo(this)
}

/**
 * 设置指定 PopNotification 提示文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param messageTextInfo 提示文本样式对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMessageTextInfo(
    engine: String? = null,
    messageTextInfo: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setMessageTextInfo(this, messageTextInfo)
}

/**
 * 获取指定 PopNotification 按钮文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 按钮文本样式对象
 */
fun Any?.popnotification_getButtonTextInfo(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.getButtonTextInfo(this)
}

/**
 * 设置指定 PopNotification 按钮文本样式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param buttonTextInfo 按钮文本样式对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_setButtonTextInfo(
    engine: String? = null,
    buttonTextInfo: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setButtonTextInfo(this, buttonTextInfo)
}

// ==========
// = 点击事件 =
// ==========

/**
 * 设置指定 PopNotification 按钮点击事件
 * @receiver PopNotification 对象
 * @param engine String?
 * @param listener 按钮点击事件
 * @return PopNotification 对象
 */
fun Any?.popnotification_setOnButtonClickListener(
    engine: String? = null,
    listener: IPopNotificationEngine.OnButtonClickListener?
): Any? {
    return engine.getPopNotificationEngine()?.setOnButtonClickListener(this, listener)
}

/**
 * 设置指定 PopNotification 自身点击事件
 * @receiver PopNotification 对象
 * @param engine String?
 * @param listener 点击事件
 * @return PopNotification 对象
 */
fun Any?.popnotification_setOnPopNotificationClickListener(
    engine: String? = null,
    listener: IPopNotificationEngine.OnButtonClickListener?
): Any? {
    return engine.getPopNotificationEngine()?.setOnPopNotificationClickListener(this, listener)
}

/**
 * 指定 PopNotification 图标是否随明暗模式自动染色
 * @receiver PopNotification 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.popnotification_isAutoTintIconInLightOrDarkMode(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.isAutoTintIconInLightOrDarkMode(this) ?: false
}

/**
 * 设置指定 PopNotification 图标是否随明暗模式自动染色
 * @receiver PopNotification 对象
 * @param engine String?
 * @param autoTint 是否自动染色
 * @return PopNotification 对象
 */
fun Any?.popnotification_setAutoTintIconInLightOrDarkMode(
    engine: String? = null,
    autoTint: Boolean
): Any? {
    return engine.getPopNotificationEngine()?.setAutoTintIconInLightOrDarkMode(this, autoTint)
}

/**
 * 指定 PopNotification 图标是否染色
 * @receiver PopNotification 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.popnotification_getTintIcon(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.getTintIcon(this) ?: false
}

/**
 * 设置指定 PopNotification 图标是否染色
 * @receiver PopNotification 对象
 * @param engine String?
 * @param tintIcon 是否染色
 * @return PopNotification 对象
 */
fun Any?.popnotification_setTintIcon(
    engine: String? = null,
    tintIcon: Boolean
): Any? {
    return engine.getPopNotificationEngine()?.setTintIcon(this, tintIcon)
}

/**
 * 设置指定 PopNotification 成功状态图标
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_iconSuccess(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.iconSuccess(this)
}

/**
 * 设置指定 PopNotification 警告状态图标
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_iconWarning(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.iconWarning(this)
}

/**
 * 设置指定 PopNotification 错误状态图标
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_iconError(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.iconError(this)
}

// ==========
// = 背景圆角 =
// ==========

/**
 * 获取指定 PopNotification 背景色
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 背景色 ( ColorInt )
 */
fun Any?.popnotification_getBackgroundColor(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getBackgroundColor(this) ?: 0
}

/**
 * 设置指定 PopNotification 背景色
 * @receiver PopNotification 对象
 * @param engine String?
 * @param backgroundColor 背景色 ( ColorInt )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setBackgroundColor(
    engine: String? = null,
    backgroundColor: Int
): Any? {
    return engine.getPopNotificationEngine()?.setBackgroundColor(this, backgroundColor)
}

/**
 * 设置指定 PopNotification 背景色
 * @receiver PopNotification 对象
 * @param engine String?
 * @param backgroundColorResId 背景色资源 id ( ColorRes )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setBackgroundColorRes(
    engine: String? = null,
    backgroundColorResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setBackgroundColorRes(this, backgroundColorResId)
}

/**
 * 获取指定 PopNotification 圆角 ( px )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 圆角 ( px )
 */
fun Any?.popnotification_getRadius(
    engine: String? = null
): Float {
    return engine.getPopNotificationEngine()?.getRadius(this) ?: 0F
}

/**
 * 设置指定 PopNotification 圆角 ( px )
 * @receiver PopNotification 对象
 * @param engine String?
 * @param radiusPx 圆角 ( px )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setRadius(
    engine: String? = null,
    radiusPx: Float
): Any? {
    return engine.getPopNotificationEngine()?.setRadius(this, radiusPx)
}

// ==========
// = 动画设置 =
// ==========

/**
 * 获取指定 PopNotification 进入动画时长 ( ms )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 进入动画时长 ( ms )
 */
fun Any?.popnotification_getEnterAnimDuration(
    engine: String? = null
): Long {
    return engine.getPopNotificationEngine()?.getEnterAnimDuration(this) ?: 0L
}

/**
 * 设置指定 PopNotification 进入动画时长 ( ms )
 * @receiver PopNotification 对象
 * @param engine String?
 * @param enterAnimDuration 进入动画时长 ( ms )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setEnterAnimDuration(
    engine: String? = null,
    enterAnimDuration: Long
): Any? {
    return engine.getPopNotificationEngine()?.setEnterAnimDuration(this, enterAnimDuration)
}

/**
 * 获取指定 PopNotification 退出动画时长 ( ms )
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 退出动画时长 ( ms )
 */
fun Any?.popnotification_getExitAnimDuration(
    engine: String? = null
): Long {
    return engine.getPopNotificationEngine()?.getExitAnimDuration(this) ?: 0L
}

/**
 * 设置指定 PopNotification 退出动画时长 ( ms )
 * @receiver PopNotification 对象
 * @param engine String?
 * @param exitAnimDuration 退出动画时长 ( ms )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setExitAnimDuration(
    engine: String? = null,
    exitAnimDuration: Long
): Any? {
    return engine.getPopNotificationEngine()?.setExitAnimDuration(this, exitAnimDuration)
}

/**
 * 设置指定 PopNotification 进出场动画资源
 * @receiver PopNotification 对象
 * @param engine String?
 * @param enterResId 进入动画资源 id
 * @param exitResId 退出动画资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setAnimResId(
    engine: String? = null,
    enterResId: Int,
    exitResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setAnimResId(this, enterResId, exitResId)
}

/**
 * 设置指定 PopNotification 进入动画资源
 * @receiver PopNotification 对象
 * @param engine String?
 * @param enterResId 进入动画资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setEnterAnimResId(
    engine: String? = null,
    enterResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setEnterAnimResId(this, enterResId)
}

/**
 * 设置指定 PopNotification 退出动画资源
 * @receiver PopNotification 对象
 * @param engine String?
 * @param exitResId 退出动画资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setExitAnimResId(
    engine: String? = null,
    exitResId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setExitAnimResId(this, exitResId)
}

/**
 * 获取指定 PopNotification 动画实现
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 动画实现对象
 */
fun Any?.popnotification_getDialogXAnimImpl(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.getDialogXAnimImpl(this)
}

/**
 * 设置指定 PopNotification 动画实现
 * @receiver PopNotification 对象
 * @param engine String?
 * @param animImpl 动画实现对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_setDialogXAnimImpl(
    engine: String? = null,
    animImpl: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setDialogXAnimImpl(this, animImpl)
}

// ==========
// = 其他配置 =
// ==========

/**
 * 设置指定 PopNotification 是否启用振动反馈
 * @receiver PopNotification 对象
 * @param engine String?
 * @param enabled 是否启用振动反馈
 * @return PopNotification 对象
 */
fun Any?.popnotification_setHapticFeedbackEnabled(
    engine: String? = null,
    enabled: Boolean
): Any? {
    return engine.getPopNotificationEngine()?.setHapticFeedbackEnabled(this, enabled)
}

/**
 * 指定 PopNotification 是否支持滑动关闭
 * @receiver PopNotification 对象
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun Any?.popnotification_isSlideToClose(
    engine: String? = null
): Boolean {
    return engine.getPopNotificationEngine()?.isSlideToClose(this) ?: false
}

/**
 * 设置指定 PopNotification 是否支持滑动关闭
 * @receiver PopNotification 对象
 * @param engine String?
 * @param slideToClose 是否支持滑动关闭
 * @return PopNotification 对象
 */
fun Any?.popnotification_setSlideToClose(
    engine: String? = null,
    slideToClose: Boolean
): Any? {
    return engine.getPopNotificationEngine()?.setSlideToClose(this, slideToClose)
}

/**
 * 设置指定 PopNotification 实现模式
 * @receiver PopNotification 对象
 * @param engine String?
 * @param dialogImplMode 实现模式 ( PopNotificationConst.IMPL_MODE_* )
 * @return PopNotification 对象
 */
fun Any?.popnotification_setDialogImplMode(
    engine: String? = null,
    dialogImplMode: Int
): Any? {
    return engine.getPopNotificationEngine()?.setDialogImplMode(this, dialogImplMode)
}

// ============
// = 外边距设置 =
// ============

/**
 * 设置指定 PopNotification 外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param left 左边距
 * @param top 上边距
 * @param right 右边距
 * @param bottom 下边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMargin(
    engine: String? = null,
    left: Int,
    top: Int,
    right: Int,
    bottom: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMargin(this, left, top, right, bottom)
}

/**
 * 获取指定 PopNotification 左外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 左外边距
 */
fun Any?.popnotification_getMarginLeft(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getMarginLeft(this) ?: 0
}

/**
 * 设置指定 PopNotification 左外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param left 左边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMarginLeft(
    engine: String? = null,
    left: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMarginLeft(this, left)
}

/**
 * 获取指定 PopNotification 上外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 上外边距
 */
fun Any?.popnotification_getMarginTop(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getMarginTop(this) ?: 0
}

/**
 * 设置指定 PopNotification 上外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param top 上边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMarginTop(
    engine: String? = null,
    top: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMarginTop(this, top)
}

/**
 * 获取指定 PopNotification 右外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 右外边距
 */
fun Any?.popnotification_getMarginRight(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getMarginRight(this) ?: 0
}

/**
 * 设置指定 PopNotification 右外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param right 右边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMarginRight(
    engine: String? = null,
    right: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMarginRight(this, right)
}

/**
 * 获取指定 PopNotification 下外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @return 下外边距
 */
fun Any?.popnotification_getMarginBottom(
    engine: String? = null
): Int {
    return engine.getPopNotificationEngine()?.getMarginBottom(this) ?: 0
}

/**
 * 设置指定 PopNotification 下外边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param bottom 下边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setMarginBottom(
    engine: String? = null,
    bottom: Int
): Any? {
    return engine.getPopNotificationEngine()?.setMarginBottom(this, bottom)
}

/**
 * 设置指定 PopNotification 根布局内边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param padding 内边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setRootPadding(
    engine: String? = null,
    padding: Int
): Any? {
    return engine.getPopNotificationEngine()?.setRootPadding(this, padding)
}

/**
 * 设置指定 PopNotification 根布局内边距
 * @receiver PopNotification 对象
 * @param engine String?
 * @param paddingLeft 左内边距
 * @param paddingTop 上内边距
 * @param paddingRight 右内边距
 * @param paddingBottom 下内边距
 * @return PopNotification 对象
 */
fun Any?.popnotification_setRootPadding(
    engine: String? = null,
    paddingLeft: Int,
    paddingTop: Int,
    paddingRight: Int,
    paddingBottom: Int
): Any? {
    return engine.getPopNotificationEngine()?.setRootPadding(
        this, paddingLeft, paddingTop, paddingRight, paddingBottom
    )
}

// ==========
// = 生命周期 =
// ==========

/**
 * 设置指定 PopNotification 显示生命周期监听
 * @receiver PopNotification 对象
 * @param engine String?
 * @param listener 显示生命周期监听
 * @return PopNotification 对象
 */
fun Any?.popnotification_setLifecycleListener(
    engine: String? = null,
    listener: IPopNotificationEngine.OnPopNotificationLifecycleListener?
): Any? {
    return engine.getPopNotificationEngine()?.setLifecycleListener(this, listener)
}

/**
 * 设置指定 PopNotification 显示回调
 * @receiver PopNotification 对象
 * @param engine String?
 * @param runnable 显示回调
 * @return PopNotification 对象
 */
fun Any?.popnotification_onShow(
    engine: String? = null,
    runnable: IPopNotificationEngine.OnPopNotificationRunnable?
): Any? {
    return engine.getPopNotificationEngine()?.onShow(this, runnable)
}

/**
 * 设置指定 PopNotification 关闭回调
 * @receiver PopNotification 对象
 * @param engine String?
 * @param runnable 关闭回调
 * @return PopNotification 对象
 */
fun Any?.popnotification_onDismiss(
    engine: String? = null,
    runnable: IPopNotificationEngine.OnPopNotificationRunnable?
): Any? {
    return engine.getPopNotificationEngine()?.onDismiss(this, runnable)
}

// ============
// = 动作与数据 =
// ============

/**
 * 设置指定 PopNotification 快捷功能键动作
 * @receiver PopNotification 对象
 * @param engine String?
 * @param actionId 动作 id
 * @param runnable 动作执行体
 * @return PopNotification 对象
 */
fun Any?.popnotification_setActionRunnable(
    engine: String? = null,
    actionId: Int,
    runnable: IPopNotificationEngine.OnPopNotificationRunnable?
): Any? {
    return engine.getPopNotificationEngine()?.setActionRunnable(this, actionId, runnable)
}

/**
 * 清除指定 PopNotification 快捷功能键动作
 * @receiver PopNotification 对象
 * @param engine String?
 * @param actionId 动作 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_cleanAction(
    engine: String? = null,
    actionId: Int
): Any? {
    return engine.getPopNotificationEngine()?.cleanAction(this, actionId)
}

/**
 * 清除指定 PopNotification 全部快捷功能键动作
 * @receiver PopNotification 对象
 * @param engine String?
 * @return PopNotification 对象
 */
fun Any?.popnotification_cleanAllAction(
    engine: String? = null
): Any? {
    return engine.getPopNotificationEngine()?.cleanAllAction(this)
}

/**
 * 设置指定 PopNotification 临时存储数据
 * @receiver PopNotification 对象
 * @param engine String?
 * @param key 数据 key
 * @param obj 数据值
 * @return PopNotification 对象
 */
fun Any?.popnotification_setData(
    engine: String? = null,
    key: String?,
    obj: Any?
): Any? {
    return engine.getPopNotificationEngine()?.setData(this, key, obj)
}

/**
 * 绑定指定 PopNotification 随 LifecycleOwner 关闭
 * @receiver PopNotification 对象
 * @param engine String?
 * @param owner LifecycleOwner 对象
 * @return PopNotification 对象
 */
fun Any?.popnotification_bindDismissWithLifecycleOwner(
    engine: String? = null,
    owner: LifecycleOwner?
): Any? {
    return engine.getPopNotificationEngine()?.bindDismissWithLifecycleOwner(this, owner)
}

// ==========
// = 弹窗布局 =
// ==========

/**
 * 设置指定 PopNotification 自定义弹窗布局资源 id
 * @receiver PopNotification 对象
 * @param engine String?
 * @param customDialogLayoutId 自定义弹窗布局资源 id
 * @return PopNotification 对象
 */
fun Any?.popnotification_setCustomDialogLayoutResId(
    engine: String? = null,
    customDialogLayoutId: Int
): Any? {
    return engine.getPopNotificationEngine()?.setCustomDialogLayoutResId(this, customDialogLayoutId)
}

/**
 * 设置指定 PopNotification 自定义弹窗布局资源 id
 * @receiver PopNotification 对象
 * @param engine String?
 * @param customDialogLayoutId 自定义弹窗布局资源 id
 * @param isLightTheme 是否亮色主题布局
 * @return PopNotification 对象
 */
fun Any?.popnotification_setCustomDialogLayoutResId(
    engine: String? = null,
    customDialogLayoutId: Int,
    isLightTheme: Boolean
): Any? {
    return engine.getPopNotificationEngine()?.setCustomDialogLayoutResId(
        this, customDialogLayoutId, isLightTheme
    )
}