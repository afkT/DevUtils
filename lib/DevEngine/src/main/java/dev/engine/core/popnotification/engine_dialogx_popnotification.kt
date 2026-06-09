package dev.engine.core.popnotification

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.*
import com.kongzue.dialogx.util.TextInfo
import dev.engine.popnotification.IPopNotificationEngine
import dev.utils.app.ResourceUtils

/**
 * detail: DialogX PopNotification Engine 实现
 * @author Ttt
 * @see https://github.com/kongzue/DialogX
 */
open class DialogXPopNotificationEngineImpl(
    @JvmField protected val mConfig: PopNotificationConfig
) : IPopNotificationEngine<PopNotificationConfig?, PopNotificationItem?> {

    // 单例 PopNotification
    @JvmField
    protected var mSinglePopNotification: PopNotification? = null

    // 是否单例 PopNotification
    @JvmField
    protected var mOnlyOnePopNotification: Boolean = false

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 PopNotification Engine Config
     * @return PopNotification Config
     */
    override fun getConfig(): PopNotificationConfig {
        return mConfig
    }

    /**
     * 设置 PopNotification Engine Config
     * @param config PopNotification Config
     */
    override fun setConfig(config: PopNotificationConfig?) {
        config?.let {
            it.onlyOne()?.let { onlyOne ->
//                // 是否单个 PopNotification
//                DialogX.onlyOnePopNotification = onlyOne
                // 不通过 DialogX.onlyOnePopNotification 实现，避免影响全局
                mOnlyOnePopNotification = onlyOne
            }
            // 最大同时显示数量
            val maxShowCount = it.maxShowCount()
            if (maxShowCount > 0) {
                PopNotification.maxShowCount = maxShowCount
            }
            // 圆角 ( px )
            val radiusPx = it.radiusPx()
            if (radiusPx >= 0) {
                DialogX.defaultPopNotificationBackgroundRadius = radiusPx
            }
            // 实现模式
            getImplMode(it.dialogImplMode())?.let { mode ->
                DialogX.implIMPLMode = mode
            }
            // 主题样式
            getDialogXStyle(it.style())?.let { style ->
                DialogX.globalStyle = style
            }
            // 明暗主题
            getTheme(it.theme())?.let { theme ->
                DialogX.globalTheme = theme
            }
            // 标题文本样式
            getTextInfo(it.titleTextInfo())?.let { textInfo ->
                DialogX.titleTextInfo = textInfo
            }
            // 提示文本样式
            getTextInfo(it.messageTextInfo())?.let { textInfo ->
                DialogX.messageTextInfo = textInfo
            }
            // 按钮文本样式
            getTextInfo(it.buttonTextInfo())?.let { textInfo ->
                DialogX.buttonTextInfo = textInfo
            }
            // 振动反馈
            it.useHaptic()?.let { useHaptic ->
                DialogX.useHaptic = useHaptic
            }
            // 进入动画时长
            val enterAnimDuration = it.enterAnimDuration()
            if (enterAnimDuration >= 0) {
                DialogX.enterAnimDuration = enterAnimDuration
            }
            // 退出动画时长
            val exitAnimDuration = it.exitAnimDuration()
            if (exitAnimDuration >= 0) {
                DialogX.exitAnimDuration = exitAnimDuration
            }
            // 背景色
            it.backgroundColor()?.let { backgroundColor ->
                DialogX.backgroundColor = backgroundColor
            }
        }
    }

    // =======================
    // = 构建 PopNotification =
    // =======================

    /**
     * 构建 PopNotification ( 不显示 )
     * @return [PopNotification]
     */
    override fun build(): PopNotification {
        return PopNotification.build()
    }

    /**
     * 构建 PopNotification ( 不显示 )
     * @param item PopNotification 参数
     * @return [PopNotification]
     */
    override fun build(item: PopNotificationItem?): PopNotification {
        return buildPopNotification(item)
    }

    /**
     * 构建 PopNotification ( 不显示 )
     * @param onBindView 自定义布局
     * @return [PopNotification]
     */
    override fun buildView(onBindView: Any?): PopNotification {
        val bindView = getOnBindView(onBindView)
        return if (bindView != null) PopNotification.build(bindView) else PopNotification.build()
    }

    // =======================
    // = 默认 PopNotification =
    // =======================

    /**
     * 显示 PopNotification
     * @param title 标题文本
     * @return [PopNotification]
     */
    override fun show(title: CharSequence?): PopNotification {
        return show(PopNotificationItem.create(title))
    }

    /**
     * 显示 PopNotification
     * @param titleResId 标题文本资源 id
     * @return [PopNotification]
     */
    override fun show(titleResId: Int): PopNotification {
        return show(PopNotificationItem.create().setTitleResId(titleResId))
    }

    /**
     * 显示 PopNotification
     * @param title 标题文本
     * @param message 提示文本
     * @return [PopNotification]
     */
    override fun show(
        title: CharSequence?,
        message: CharSequence?
    ): PopNotification {
        return show(PopNotificationItem.create(title).setMessage(message))
    }

    /**
     * 显示 PopNotification
     * @param iconResId 图标资源 id
     * @param title 标题文本
     * @return [PopNotification]
     */
    override fun show(
        iconResId: Int,
        title: CharSequence?
    ): PopNotification {
        return show(PopNotificationItem.create(title).setIconResId(iconResId))
    }

    /**
     * 显示 PopNotification
     * @param item PopNotification 参数
     * @return [PopNotification]
     */
    override fun show(item: PopNotificationItem?): PopNotification {
        return show(null, item)
    }

    /**
     * 显示 PopNotification
     * @param activity 显示的 Activity
     * @param item PopNotification 参数
     * @return [PopNotification]
     */
    override fun show(
        activity: Activity?,
        item: PopNotificationItem?
    ): PopNotification {
        val popNotification = buildPopNotification(item)
        showPopNotification(popNotification, activity, item)
        return popNotification
    }

    // ===============================
    // = 单例 PopNotification 句柄操作 =
    // ===============================

    /**
     * 是否使用单例 PopNotification
     * @return `true` yes, `false` no
     */
    override fun isSinglePopNotification(): Boolean {
        return mOnlyOnePopNotification
    }

    /**
     * 获取单例 PopNotification
     * @return 单例 [PopNotification]
     */
    override fun getSinglePopNotification(): PopNotification? {
        return mSinglePopNotification
    }

    /**
     * 单例 PopNotification 是否正在显示
     * @return `true` yes, `false` no
     */
    override fun isShowSinglePopNotification(): Boolean {
        return mSinglePopNotification?.isShow ?: false
    }

    /**
     * 关闭单例 PopNotification
     */
    override fun dismissSinglePopNotification() {
        try {
            mSinglePopNotification?.dismiss()
        } catch (_: Exception) {
        }
        mSinglePopNotification = null
    }

    /**
     * 关闭单例 PopNotification ( 动画 )
     */
    override fun hideSinglePopNotification() {
        try {
            mSinglePopNotification?.hide()
        } catch (_: Exception) {
        }
    }

    // ==========================
    // = PopNotification 句柄操作 =
    // ==========================

    /**
     * 指定 PopNotification 是否正在显示
     * @param popNotification [PopNotification]
     * @return `true` yes, `false` no
     */
    override fun isShow(popNotification: Any?): Boolean {
        return getPopNotification(popNotification)?.isShow ?: false
    }

    /**
     * 关闭指定 PopNotification
     * @param popNotification [PopNotification]
     */
    override fun dismiss(popNotification: Any?) {
        try {
            getPopNotification(popNotification)?.dismiss()
        } catch (_: Exception) {
        }
    }

    /**
     * 关闭指定 PopNotification ( 动画 )
     * @param popNotification [PopNotification]
     */
    override fun hide(popNotification: Any?) {
        try {
            getPopNotification(popNotification)?.hide()
        } catch (_: Exception) {
        }
    }

    /**
     * 重新显示指定 PopNotification ( hide 后复显 )
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun show(popNotification: Any?): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        showPopNotification(popNotificationObj, null, null)
        return popNotification
    }

    /**
     * 重新显示指定 PopNotification ( hide 后复显 )
     * @param popNotification [PopNotification]
     * @param activity 显示的 Activity
     * @return [PopNotification]
     */
    override fun show(
        popNotification: Any?,
        activity: Activity?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        showPopNotification(popNotificationObj, activity, null)
        return popNotification
    }

    /**
     * 刷新指定 PopNotification 界面
     * @param popNotification [PopNotification]
     */
    override fun refreshUI(popNotification: Any?) {
        getPopNotification(popNotification)?.refreshUI()
    }

    /**
     * 设置指定 PopNotification 自动消失时长
     * @param popNotification [PopNotification]
     * @param delay 自动消失时长 ( ms )
     * @return [PopNotification]
     */
    override fun autoDismiss(
        popNotification: Any?,
        delay: Long
    ): Any? {
        getPopNotification(popNotification)?.autoDismiss(delay)
        return popNotification
    }

    /**
     * 重置指定 PopNotification 自动消失计时器
     * @param popNotification [PopNotification]
     */
    override fun resetAutoDismissTimer(popNotification: Any?) {
        getPopNotification(popNotification)?.resetAutoDismissTimer()
    }

    /**
     * 指定 PopNotification 短时间显示
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun showShort(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.showShort()
        return popNotification
    }

    /**
     * 指定 PopNotification 长时间显示
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun showLong(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.showLong()
        return popNotification
    }

    /**
     * 指定 PopNotification 常驻显示 ( 不自动消失 )
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun showAlways(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.showAlways()
        return popNotification
    }

    /**
     * 指定 PopNotification 取消自动消失
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun noAutoDismiss(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.noAutoDismiss()
        return popNotification
    }

    /**
     * 指定 PopNotification 置顶显示
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun bringToFront(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.bringToFront()
        return popNotification
    }

    /**
     * 设置指定 PopNotification 显示层级
     * @param popNotification [PopNotification]
     * @param orderIndex 显示层级
     * @return [PopNotification]
     */
    override fun setThisOrderIndex(
        popNotification: Any?,
        orderIndex: Int
    ): Any? {
        getPopNotification(popNotification)?.setThisOrderIndex(orderIndex)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 主题样式
     * @param popNotification [PopNotification]
     * @param style 主题样式对象
     * @return [PopNotification]
     */
    override fun setStyle(
        popNotification: Any?,
        style: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getDialogXStyle(style)?.let {
            popNotificationObj.setStyle(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 明暗主题
     * @param popNotification [PopNotification]
     * @param theme 明暗主题 ( [PopNotificationConst.THEME_DEFAULT] 等 )
     * @return [PopNotification]
     */
    override fun setTheme(
        popNotification: Any?,
        theme: Int
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getTheme(theme)?.let {
            popNotificationObj.setTheme(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 自定义布局
     * @param popNotification [PopNotification]
     * @param onBindView 自定义布局
     * @return [PopNotification]
     */
    override fun setCustomView(
        popNotification: Any?,
        onBindView: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getOnBindView(onBindView)?.let {
            popNotificationObj.setCustomView(it)
        }
        return popNotification
    }

    /**
     * 获取指定 PopNotification 自定义布局 View
     * @param popNotification [PopNotification]
     * @return 自定义布局 [View]
     */
    override fun getCustomView(popNotification: Any?): View? {
        return getPopNotification(popNotification)?.customView
    }

    /**
     * 移除指定 PopNotification 自定义布局
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun removeCustomView(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.removeCustomView()
        return popNotification
    }

    /**
     * 获取指定 PopNotification 对齐方式
     * @param popNotification [PopNotification]
     * @return 对齐方式 ( [PopNotificationConst.ALIGN_DEFAULT] 等 )
     */
    override fun getAlign(popNotification: Any?): Int {
        return getAlignValue(getPopNotification(popNotification)?.align)
    }

    /**
     * 设置指定 PopNotification 对齐方式
     * @param popNotification [PopNotification]
     * @param align 对齐方式 ( [PopNotificationConst.ALIGN_DEFAULT] 等 )
     * @return [PopNotification]
     */
    override fun setAlign(
        popNotification: Any?,
        align: Int
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getAlign(align)?.let {
            popNotificationObj.setAlign(it)
        }
        return popNotification
    }

    /**
     * 获取指定 PopNotification 图标资源 id
     * @param popNotification [PopNotification]
     * @return 图标资源 id
     */
    override fun getIconResId(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.iconResId ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 图标资源 id
     * @param popNotification [PopNotification]
     * @param iconResId 图标资源 id
     * @return [PopNotification]
     */
    override fun setIconResId(
        popNotification: Any?,
        iconResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setIconResId(iconResId)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 图标 Bitmap
     * @param popNotification [PopNotification]
     * @return 图标 [Bitmap]
     */
    override fun getIconBitmap(popNotification: Any?): Bitmap? {
        return getPopNotification(popNotification)?.iconBitmap
    }

    /**
     * 设置指定 PopNotification 图标 Bitmap
     * @param popNotification [PopNotification]
     * @param bitmap 图标 [Bitmap]
     * @return [PopNotification]
     */
    override fun setIcon(
        popNotification: Any?,
        bitmap: Bitmap?
    ): Any? {
        getPopNotification(popNotification)?.setIcon(bitmap)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 图标 Drawable
     * @param popNotification [PopNotification]
     * @return 图标 [Drawable]
     */
    override fun getIconDrawable(popNotification: Any?): Drawable? {
        return getPopNotification(popNotification)?.iconDrawable
    }

    /**
     * 设置指定 PopNotification 图标 Drawable
     * @param popNotification [PopNotification]
     * @param drawable 图标 [Drawable]
     * @return [PopNotification]
     */
    override fun setIcon(
        popNotification: Any?,
        drawable: Drawable?
    ): Any? {
        getPopNotification(popNotification)?.setIcon(drawable)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 图标尺寸 ( px )
     * @param popNotification [PopNotification]
     * @return 图标尺寸 ( px )
     */
    override fun getIconSize(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.iconSize ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 图标尺寸 ( px )
     * @param popNotification [PopNotification]
     * @param iconSize 图标尺寸 ( px )
     * @return [PopNotification]
     */
    override fun setIconSize(
        popNotification: Any?,
        iconSize: Int
    ): Any? {
        getPopNotification(popNotification)?.setIconSize(iconSize)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 标题文本
     * @param popNotification [PopNotification]
     * @return 标题文本
     */
    override fun getTitle(popNotification: Any?): CharSequence? {
        return getPopNotification(popNotification)?.title
    }

    /**
     * 设置指定 PopNotification 标题文本
     * @param popNotification [PopNotification]
     * @param title 标题文本
     * @return [PopNotification]
     */
    override fun setTitle(
        popNotification: Any?,
        title: CharSequence?
    ): Any? {
        getPopNotification(popNotification)?.setTitle(title)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 标题文本样式
     * @param popNotification [PopNotification]
     * @return 标题文本样式对象 [TextInfo]
     */
    override fun getTitleTextInfo(popNotification: Any?): Any? {
        return getPopNotification(popNotification)?.titleTextInfo
    }

    /**
     * 设置指定 PopNotification 标题文本样式
     * @param popNotification [PopNotification]
     * @param titleTextInfo 标题文本样式对象 [TextInfo]
     * @return [PopNotification]
     */
    override fun setTitleTextInfo(
        popNotification: Any?,
        titleTextInfo: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getTextInfo(titleTextInfo)?.let {
            popNotificationObj.setTitleTextInfo(it)
        }
        return popNotification
    }

    /**
     * 获取指定 PopNotification 提示文本
     * @param popNotification [PopNotification]
     * @return 提示文本
     */
    override fun getMessage(popNotification: Any?): CharSequence? {
        return getPopNotification(popNotification)?.message
    }

    /**
     * 设置指定 PopNotification 提示文本
     * @param popNotification [PopNotification]
     * @param message 提示文本
     * @return [PopNotification]
     */
    override fun setMessage(
        popNotification: Any?,
        message: CharSequence?
    ): Any? {
        getPopNotification(popNotification)?.setMessage(message)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 提示文本
     * @param popNotification [PopNotification]
     * @param messageResId 提示文本资源 id
     * @return [PopNotification]
     */
    override fun setMessage(
        popNotification: Any?,
        messageResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setMessage(messageResId)
        return popNotification
    }

    /**
     * 追加指定 PopNotification 提示文本
     * @param popNotification [PopNotification]
     * @param message 追加文本
     * @return [PopNotification]
     */
    override fun appendMessage(
        popNotification: Any?,
        message: CharSequence?
    ): Any? {
        getPopNotification(popNotification)?.appendMessage(message)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 按钮文本
     * @param popNotification [PopNotification]
     * @return 按钮文本
     */
    override fun getButtonText(popNotification: Any?): CharSequence? {
        return getPopNotification(popNotification)?.buttonText
    }

    /**
     * 设置指定 PopNotification 按钮文本
     * @param popNotification [PopNotification]
     * @param buttonText 按钮文本
     * @return [PopNotification]
     */
    override fun setButton(
        popNotification: Any?,
        buttonText: CharSequence?
    ): Any? {
        getPopNotification(popNotification)?.setButton(buttonText)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 按钮文本
     * @param popNotification [PopNotification]
     * @param buttonTextResId 按钮文本资源 id
     * @return [PopNotification]
     */
    override fun setButton(
        popNotification: Any?,
        buttonTextResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setButton(buttonTextResId)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 按钮点击事件
     * @param popNotification [PopNotification]
     * @param listener 按钮点击事件
     * @return [PopNotification]
     */
    override fun setButton(
        popNotification: Any?,
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        wrapButtonClick(listener)?.let {
            popNotificationObj.setButton(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 按钮文本及点击事件
     * @param popNotification [PopNotification]
     * @param buttonText 按钮文本
     * @param listener 按钮点击事件
     * @return [PopNotification]
     */
    override fun setButton(
        popNotification: Any?,
        buttonText: CharSequence?,
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): Any? {
        getPopNotification(popNotification)?.setButton(buttonText, wrapButtonClick(listener))
        return popNotification
    }

    /**
     * 设置指定 PopNotification 按钮文本及点击事件
     * @param popNotification [PopNotification]
     * @param buttonTextResId 按钮文本资源 id
     * @param listener 按钮点击事件
     * @return [PopNotification]
     */
    override fun setButton(
        popNotification: Any?,
        buttonTextResId: Int,
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): Any? {
        getPopNotification(popNotification)?.setButton(buttonTextResId, wrapButtonClick(listener))
        return popNotification
    }

    /**
     * 获取指定 PopNotification 提示文本样式
     * @param popNotification [PopNotification]
     * @return 提示文本样式对象 [TextInfo]
     */
    override fun getMessageTextInfo(popNotification: Any?): Any? {
        return getPopNotification(popNotification)?.messageTextInfo
    }

    /**
     * 设置指定 PopNotification 提示文本样式
     * @param popNotification [PopNotification]
     * @param messageTextInfo 提示文本样式对象 [TextInfo]
     * @return [PopNotification]
     */
    override fun setMessageTextInfo(
        popNotification: Any?,
        messageTextInfo: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getTextInfo(messageTextInfo)?.let {
            popNotificationObj.setMessageTextInfo(it)
        }
        return popNotification
    }

    /**
     * 获取指定 PopNotification 按钮文本样式
     * @param popNotification [PopNotification]
     * @return 按钮文本样式对象 [TextInfo]
     */
    override fun getButtonTextInfo(popNotification: Any?): Any? {
        return getPopNotification(popNotification)?.buttonTextInfo
    }

    /**
     * 设置指定 PopNotification 按钮文本样式
     * @param popNotification [PopNotification]
     * @param buttonTextInfo 按钮文本样式对象 [TextInfo]
     * @return [PopNotification]
     */
    override fun setButtonTextInfo(
        popNotification: Any?,
        buttonTextInfo: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getTextInfo(buttonTextInfo)?.let {
            popNotificationObj.setButtonTextInfo(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 按钮点击事件
     * @param popNotification [PopNotification]
     * @param listener 按钮点击事件
     * @return [PopNotification]
     */
    override fun setOnButtonClickListener(
        popNotification: Any?,
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): Any? {
        getPopNotification(popNotification)?.setOnButtonClickListener(wrapButtonClick(listener))
        return popNotification
    }

    /**
     * 设置指定 PopNotification 自身点击事件
     * @param popNotification [PopNotification]
     * @param listener 点击事件
     * @return [PopNotification]
     */
    override fun setOnPopNotificationClickListener(
        popNotification: Any?,
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): Any? {
        getPopNotification(popNotification)?.setOnPopNotificationClickListener(
            wrapButtonClick(listener)
        )
        return popNotification
    }

    /**
     * 指定 PopNotification 图标是否随明暗模式自动染色
     * @param popNotification [PopNotification]
     * @return `true` yes, `false` no
     */
    override fun isAutoTintIconInLightOrDarkMode(popNotification: Any?): Boolean {
        return getPopNotification(popNotification)?.isAutoTintIconInLightOrDarkMode ?: false
    }

    /**
     * 设置指定 PopNotification 图标是否随明暗模式自动染色
     * @param popNotification [PopNotification]
     * @param autoTint 是否自动染色
     * @return [PopNotification]
     */
    override fun setAutoTintIconInLightOrDarkMode(
        popNotification: Any?,
        autoTint: Boolean
    ): Any? {
        getPopNotification(popNotification)?.isAutoTintIconInLightOrDarkMode = autoTint
        return popNotification
    }

    /**
     * 指定 PopNotification 图标是否染色
     * @param popNotification [PopNotification]
     * @return `true` yes, `false` no
     */
    override fun getTintIcon(popNotification: Any?): Boolean {
        return getPopNotification(popNotification)?.tintIcon ?: false
    }

    /**
     * 设置指定 PopNotification 图标是否染色
     * @param popNotification [PopNotification]
     * @param tintIcon 是否染色
     * @return [PopNotification]
     */
    override fun setTintIcon(
        popNotification: Any?,
        tintIcon: Boolean
    ): Any? {
        getPopNotification(popNotification)?.setTintIcon(tintIcon)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 成功状态图标
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun iconSuccess(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.iconSuccess()
        return popNotification
    }

    /**
     * 设置指定 PopNotification 警告状态图标
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun iconWarning(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.iconWarning()
        return popNotification
    }

    /**
     * 设置指定 PopNotification 错误状态图标
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun iconError(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.iconError()
        return popNotification
    }

    /**
     * 获取指定 PopNotification 背景色
     * @param popNotification [PopNotification]
     * @return 背景色 ( ColorInt )
     */
    override fun getBackgroundColor(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.backgroundColor ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 背景色
     * @param popNotification [PopNotification]
     * @param backgroundColor 背景色 ( ColorInt )
     * @return [PopNotification]
     */
    override fun setBackgroundColor(
        popNotification: Any?,
        backgroundColor: Int
    ): Any? {
        getPopNotification(popNotification)?.setBackgroundColor(backgroundColor)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 背景色
     * @param popNotification [PopNotification]
     * @param backgroundColorResId 背景色资源 id ( ColorRes )
     * @return [PopNotification]
     */
    override fun setBackgroundColorRes(
        popNotification: Any?,
        backgroundColorResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setBackgroundColorRes(backgroundColorResId)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 圆角 ( px )
     * @param popNotification [PopNotification]
     * @return 圆角 ( px )
     */
    override fun getRadius(popNotification: Any?): Float {
        return getPopNotification(popNotification)?.radius ?: PopNotificationConst.UNSET_FLOAT
    }

    /**
     * 设置指定 PopNotification 圆角 ( px )
     * @param popNotification [PopNotification]
     * @param radiusPx 圆角 ( px )
     * @return [PopNotification]
     */
    override fun setRadius(
        popNotification: Any?,
        radiusPx: Float
    ): Any? {
        getPopNotification(popNotification)?.radius = radiusPx
        return popNotification
    }

    /**
     * 获取指定 PopNotification 进入动画时长 ( ms )
     * @param popNotification [PopNotification]
     * @return 进入动画时长 ( ms )
     */
    override fun getEnterAnimDuration(popNotification: Any?): Long {
        return getPopNotification(popNotification)?.enterAnimDuration
            ?: PopNotificationConst.UNSET_LONG
    }

    /**
     * 设置指定 PopNotification 进入动画时长 ( ms )
     * @param popNotification [PopNotification]
     * @param enterAnimDuration 进入动画时长 ( ms )
     * @return [PopNotification]
     */
    override fun setEnterAnimDuration(
        popNotification: Any?,
        enterAnimDuration: Long
    ): Any? {
        getPopNotification(popNotification)?.setEnterAnimDuration(enterAnimDuration)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 退出动画时长 ( ms )
     * @param popNotification [PopNotification]
     * @return 退出动画时长 ( ms )
     */
    override fun getExitAnimDuration(popNotification: Any?): Long {
        return getPopNotification(popNotification)?.exitAnimDuration
            ?: PopNotificationConst.UNSET_LONG
    }

    /**
     * 设置指定 PopNotification 退出动画时长 ( ms )
     * @param popNotification [PopNotification]
     * @param exitAnimDuration 退出动画时长 ( ms )
     * @return [PopNotification]
     */
    override fun setExitAnimDuration(
        popNotification: Any?,
        exitAnimDuration: Long
    ): Any? {
        getPopNotification(popNotification)?.setExitAnimDuration(exitAnimDuration)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 进出场动画资源
     * @param popNotification [PopNotification]
     * @param enterResId 进入动画资源 id
     * @param exitResId 退出动画资源 id
     * @return [PopNotification]
     */
    override fun setAnimResId(
        popNotification: Any?,
        enterResId: Int,
        exitResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setAnimResId(enterResId, exitResId)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 进入动画资源
     * @param popNotification [PopNotification]
     * @param enterResId 进入动画资源 id
     * @return [PopNotification]
     */
    override fun setEnterAnimResId(
        popNotification: Any?,
        enterResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setEnterAnimResId(enterResId)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 退出动画资源
     * @param popNotification [PopNotification]
     * @param exitResId 退出动画资源 id
     * @return [PopNotification]
     */
    override fun setExitAnimResId(
        popNotification: Any?,
        exitResId: Int
    ): Any? {
        getPopNotification(popNotification)?.setExitAnimResId(exitResId)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 动画实现
     * @param popNotification [PopNotification]
     * @return 动画实现对象 [DialogXAnimInterface]
     */
    override fun getDialogXAnimImpl(popNotification: Any?): Any? {
        return getPopNotification(popNotification)?.dialogXAnimImpl
    }

    /**
     * 设置指定 PopNotification 动画实现
     * @param popNotification [PopNotification]
     * @param animImpl 动画实现对象 [DialogXAnimInterface]
     * @return [PopNotification]
     */
    override fun setDialogXAnimImpl(
        popNotification: Any?,
        animImpl: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getDialogXAnimInterface(animImpl)?.let {
            popNotificationObj.setDialogXAnimImpl(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 是否启用振动反馈
     * @param popNotification [PopNotification]
     * @param enabled 是否启用振动反馈
     * @return [PopNotification]
     */
    override fun setHapticFeedbackEnabled(
        popNotification: Any?,
        enabled: Boolean
    ): Any? {
        getPopNotification(popNotification)?.setHapticFeedbackEnabled(enabled)
        return popNotification
    }

    /**
     * 指定 PopNotification 是否支持滑动关闭
     * @param popNotification [PopNotification]
     * @return `true` yes, `false` no
     */
    override fun isSlideToClose(popNotification: Any?): Boolean {
        return getPopNotification(popNotification)?.isSlideToClose ?: false
    }

    /**
     * 设置指定 PopNotification 是否支持滑动关闭
     * @param popNotification [PopNotification]
     * @param slideToClose 是否支持滑动关闭
     * @return [PopNotification]
     */
    override fun setSlideToClose(
        popNotification: Any?,
        slideToClose: Boolean
    ): Any? {
        getPopNotification(popNotification)?.isSlideToClose = slideToClose
        return popNotification
    }

    /**
     * 设置指定 PopNotification 实现模式
     * @param popNotification [PopNotification]
     * @param dialogImplMode 实现模式 ( [PopNotificationConst.IMPL_MODE_DEFAULT] 等 )
     * @return [PopNotification]
     */
    override fun setDialogImplMode(
        popNotification: Any?,
        dialogImplMode: Int
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getImplMode(dialogImplMode)?.let {
            popNotificationObj.setDialogImplMode(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 外边距
     * @param popNotification [PopNotification]
     * @param left 左边距
     * @param top 上边距
     * @param right 右边距
     * @param bottom 下边距
     * @return [PopNotification]
     */
    override fun setMargin(
        popNotification: Any?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): Any? {
        getPopNotification(popNotification)?.setMargin(left, top, right, bottom)
        return popNotification
    }

    /**
     * 获取指定 PopNotification 左外边距
     * @param popNotification [PopNotification]
     * @return 左外边距
     */
    override fun getMarginLeft(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.marginLeft ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 左外边距
     * @param popNotification [PopNotification]
     * @param left 左边距
     * @return [PopNotification]
     */
    override fun setMarginLeft(
        popNotification: Any?,
        left: Int
    ): Any? {
        getPopNotification(popNotification)?.marginLeft = left
        return popNotification
    }

    /**
     * 获取指定 PopNotification 上外边距
     * @param popNotification [PopNotification]
     * @return 上外边距
     */
    override fun getMarginTop(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.marginTop ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 上外边距
     * @param popNotification [PopNotification]
     * @param top 上边距
     * @return [PopNotification]
     */
    override fun setMarginTop(
        popNotification: Any?,
        top: Int
    ): Any? {
        getPopNotification(popNotification)?.marginTop = top
        return popNotification
    }

    /**
     * 获取指定 PopNotification 右外边距
     * @param popNotification [PopNotification]
     * @return 右外边距
     */
    override fun getMarginRight(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.marginRight ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 右外边距
     * @param popNotification [PopNotification]
     * @param right 右边距
     * @return [PopNotification]
     */
    override fun setMarginRight(
        popNotification: Any?,
        right: Int
    ): Any? {
        getPopNotification(popNotification)?.marginRight = right
        return popNotification
    }

    /**
     * 获取指定 PopNotification 下外边距
     * @param popNotification [PopNotification]
     * @return 下外边距
     */
    override fun getMarginBottom(popNotification: Any?): Int {
        return getPopNotification(popNotification)?.marginBottom ?: PopNotificationConst.UNSET
    }

    /**
     * 设置指定 PopNotification 下外边距
     * @param popNotification [PopNotification]
     * @param bottom 下边距
     * @return [PopNotification]
     */
    override fun setMarginBottom(
        popNotification: Any?,
        bottom: Int
    ): Any? {
        getPopNotification(popNotification)?.marginBottom = bottom
        return popNotification
    }

    /**
     * 设置指定 PopNotification 根布局内边距
     * @param popNotification [PopNotification]
     * @param padding 内边距
     * @return [PopNotification]
     */
    override fun setRootPadding(
        popNotification: Any?,
        padding: Int
    ): Any? {
        getPopNotification(popNotification)?.setRootPadding(padding)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 根布局内边距
     * @param popNotification [PopNotification]
     * @param paddingLeft 左内边距
     * @param paddingTop 上内边距
     * @param paddingRight 右内边距
     * @param paddingBottom 下内边距
     * @return [PopNotification]
     */
    override fun setRootPadding(
        popNotification: Any?,
        paddingLeft: Int,
        paddingTop: Int,
        paddingRight: Int,
        paddingBottom: Int
    ): Any? {
        getPopNotification(popNotification)?.setRootPadding(
            paddingLeft, paddingTop, paddingRight, paddingBottom
        )
        return popNotification
    }

    /**
     * 设置指定 PopNotification 显示生命周期监听
     * @param popNotification [PopNotification]
     * @param listener 显示生命周期监听
     * @return [PopNotification]
     */
    override fun setLifecycleListener(
        popNotification: Any?,
        listener: IPopNotificationEngine.OnPopNotificationLifecycleListener?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        wrapLifecycle(listener)?.let {
            popNotificationObj.dialogLifecycleCallback = it
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 显示回调
     * @param popNotification [PopNotification]
     * @param runnable 显示回调
     * @return [PopNotification]
     */
    override fun onShow(
        popNotification: Any?,
        runnable: IPopNotificationEngine.OnPopNotificationRunnable?
    ): Any? {
        getPopNotification(popNotification)?.onShow(wrapRunnable(runnable))
        return popNotification
    }

    /**
     * 设置指定 PopNotification 关闭回调
     * @param popNotification [PopNotification]
     * @param runnable 关闭回调
     * @return [PopNotification]
     */
    override fun onDismiss(
        popNotification: Any?,
        runnable: IPopNotificationEngine.OnPopNotificationRunnable?
    ): Any? {
        getPopNotification(popNotification)?.onDismiss(wrapRunnable(runnable))
        return popNotification
    }

    /**
     * 设置指定 PopNotification 快捷功能键动作
     * @param popNotification [PopNotification]
     * @param actionId 动作 id
     * @param runnable 动作执行体
     * @return [PopNotification]
     */
    override fun setActionRunnable(
        popNotification: Any?,
        actionId: Int,
        runnable: IPopNotificationEngine.OnPopNotificationRunnable?
    ): Any? {
        getPopNotification(popNotification)?.setActionRunnable(actionId, wrapRunnable(runnable))
        return popNotification
    }

    /**
     * 清除指定 PopNotification 快捷功能键动作
     * @param popNotification [PopNotification]
     * @param actionId 动作 id
     * @return [PopNotification]
     */
    override fun cleanAction(
        popNotification: Any?,
        actionId: Int
    ): Any? {
        getPopNotification(popNotification)?.cleanAction(actionId)
        return popNotification
    }

    /**
     * 清除指定 PopNotification 全部快捷功能键动作
     * @param popNotification [PopNotification]
     * @return [PopNotification]
     */
    override fun cleanAllAction(popNotification: Any?): Any? {
        getPopNotification(popNotification)?.cleanAllAction()
        return popNotification
    }

    /**
     * 设置指定 PopNotification 临时存储数据
     * @param popNotification [PopNotification]
     * @param key 数据 key
     * @param obj 数据值
     * @return [PopNotification]
     */
    override fun setData(
        popNotification: Any?,
        key: String?,
        obj: Any?
    ): Any? {
        getPopNotification(popNotification)?.setData(key, obj)
        return popNotification
    }

    /**
     * 绑定指定 PopNotification 随 LifecycleOwner 关闭
     * @param popNotification [PopNotification]
     * @param owner LifecycleOwner 对象
     * @return [PopNotification]
     */
    override fun bindDismissWithLifecycleOwner(
        popNotification: Any?,
        owner: Any?
    ): Any? {
        val popNotificationObj = getPopNotification(popNotification) ?: return popNotification
        getLifecycleOwner(owner)?.let {
            popNotificationObj.bindDismissWithLifecycleOwner(it)
        }
        return popNotification
    }

    /**
     * 设置指定 PopNotification 自定义弹窗布局资源 id
     * @param popNotification [PopNotification]
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @return [PopNotification]
     */
    override fun setCustomDialogLayoutResId(
        popNotification: Any?,
        customDialogLayoutId: Int
    ): Any? {
        getPopNotification(popNotification)?.setCustomDialogLayoutResId(customDialogLayoutId)
        return popNotification
    }

    /**
     * 设置指定 PopNotification 自定义弹窗布局资源 id
     * @param popNotification [PopNotification]
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @param isLightTheme 是否亮色主题布局
     * @return [PopNotification]
     */
    override fun setCustomDialogLayoutResId(
        popNotification: Any?,
        customDialogLayoutId: Int,
        isLightTheme: Boolean
    ): Any? {
        getPopNotification(popNotification)?.setCustomDialogLayoutResId(
            customDialogLayoutId, isLightTheme
        )
        return popNotification
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 构建 PopNotification
     * @param item PopNotification 参数
     * @return [PopNotification]
     */
    protected open fun buildPopNotification(item: PopNotificationItem?): PopNotification {
        val popNotification = PopNotification.build()
        item ?: return popNotification
        // 标题
        item.title()?.let {
            popNotification.setTitle(it)
        }
        // 标题资源
        val titleResId = item.titleResId()
        if (titleResId > 0) {
            popNotification.setTitle(ResourceUtils.getString(titleResId))
        }
        // 文本
        item.message()?.let {
            popNotification.setMessage(it)
        }
        // 文本资源
        val messageResId = item.messageResId()
        if (messageResId > 0) {
            popNotification.setMessage(messageResId)
        }
        // 图标
        val iconResId = item.iconResId()
        if (iconResId > 0) {
            popNotification.setIconResId(iconResId)
        }
        // 图标 Bitmap
        item.iconBitmap()?.let {
            popNotification.setIcon(it)
        }
        // 图标 Drawable
        item.iconDrawable()?.let {
            popNotification.setIcon(it)
        }
        // 图标尺寸
        val iconSize = item.iconSize()
        if (iconSize > 0) {
            popNotification.setIconSize(iconSize)
        }
        // 按钮 ( 文本优先, 其次文本资源 id )
        val buttonClick = wrapButtonClick(
            item.onButtonClickListener()
        )
        val buttonText = item.buttonText()
        val buttonTextResId = item.buttonTextResId()
        if (buttonText != null) {
            if (buttonClick != null) {
                popNotification.setButton(buttonText, buttonClick)
            } else {
                popNotification.setButton(buttonText)
            }
        } else if (buttonTextResId > 0) {
            if (buttonClick != null) {
                popNotification.setButton(buttonTextResId, buttonClick)
            } else {
                popNotification.setButton(buttonTextResId)
            }
        }
        // 对齐方式
        getAlign(item.align())?.let {
            popNotification.setAlign(it)
        }
        // 实现模式
        getImplMode(item.dialogImplMode())?.let {
            popNotification.setDialogImplMode(it)
        }
        // 背景色
        item.backgroundColor()?.let {
            popNotification.setBackgroundColor(it)
        }
        // 圆角
        val radius = item.radius()
        if (radius >= 0) {
            popNotification.radius = radius
        }
        // 自定义布局
        getOnBindView(item.customView())?.let {
            popNotification.setCustomView(it)
        }
        // 生命周期监听
        item.lifecycleListener()?.let { listener ->
            popNotification.dialogLifecycleCallback = wrapLifecycle(listener)
        }
        // 主题样式
        getDialogXStyle(item.style())?.let {
            popNotification.setStyle(it)
        }
        // 明暗主题
        getTheme(item.theme())?.let {
            popNotification.setTheme(it)
        }
        // 标题文本样式
        getTextInfo(item.titleTextInfo())?.let {
            popNotification.setTitleTextInfo(it)
        }
        // 提示文本样式
        getTextInfo(item.messageTextInfo())?.let {
            popNotification.setMessageTextInfo(it)
        }
        // 按钮文本样式
        getTextInfo(item.buttonTextInfo())?.let {
            popNotification.setButtonTextInfo(it)
        }
        // 状态预置图标
        when (item.iconState()) {
            PopNotificationConst.ICON_SUCCESS -> popNotification.iconSuccess()
            PopNotificationConst.ICON_WARNING -> popNotification.iconWarning()
            PopNotificationConst.ICON_ERROR -> popNotification.iconError()
        }
        // PopNotification 自身点击事件
        item.onPopNotificationClickListener()?.let { listener ->
            popNotification.setOnPopNotificationClickListener(wrapButtonClick(listener))
        }
        // 背景色资源 id
        val backgroundColorRes = item.backgroundColorRes()
        if (backgroundColorRes > 0) {
            popNotification.setBackgroundColorRes(backgroundColorRes)
        }
        // 图标染色
        item.autoTintIcon()?.let {
            popNotification.setAutoTintIconInLightOrDarkMode(it)
        }
        item.tintIcon()?.let {
            popNotification.setTintIcon(it)
        }
        // 滑动关闭
        item.slideToClose()?.let {
            popNotification.setSlideToClose(it)
        }
        // 动画时长
        val enterAnimDuration = item.enterAnimDuration()
        if (enterAnimDuration >= 0) {
            popNotification.setEnterAnimDuration(enterAnimDuration)
        }
        val exitAnimDuration = item.exitAnimDuration()
        if (exitAnimDuration >= 0) {
            popNotification.setExitAnimDuration(exitAnimDuration)
        }
        // 动画资源
        val enterAnimResId = item.enterAnimResId()
        if (enterAnimResId > 0) {
            popNotification.setEnterAnimResId(enterAnimResId)
        }
        val exitAnimResId = item.exitAnimResId()
        if (exitAnimResId > 0) {
            popNotification.setExitAnimResId(exitAnimResId)
        }
        // 自定义动画实现
        getDialogXAnimInterface(item.dialogXAnimImpl())?.let {
            popNotification.setDialogXAnimImpl(it)
        }
        // 振动反馈
        item.hapticFeedbackEnabled()?.let {
            popNotification.setHapticFeedbackEnabled(it)
        }
        // 外边距
        val marginLeft = item.marginLeft()
        val marginTop = item.marginTop()
        val marginRight = item.marginRight()
        val marginBottom = item.marginBottom()
        if (marginLeft != PopNotificationConst.UNSET) {
            popNotification.marginLeft = marginLeft
        }
        if (marginTop != PopNotificationConst.UNSET) {
            popNotification.marginTop = marginTop
        }
        if (marginRight != PopNotificationConst.UNSET) {
            popNotification.marginRight = marginRight
        }
        if (marginBottom != PopNotificationConst.UNSET) {
            popNotification.marginBottom = marginBottom
        }
        // 根布局内边距
        val rootPadding = item.rootPadding()
        if (rootPadding != PopNotificationConst.UNSET) {
            popNotification.setRootPadding(rootPadding)
        }
        // 临时存储数据
        item.data()?.forEach { (key, value) ->
            popNotification.setData(key, value)
        }
        // 显示层级
        val thisOrderIndex = item.thisOrderIndex()
        if (thisOrderIndex != PopNotificationConst.UNSET) {
            popNotification.setThisOrderIndex(thisOrderIndex)
        }
        // 绑定关闭的 LifecycleOwner
        getLifecycleOwner(item.lifecycleOwner())?.let {
            popNotification.bindDismissWithLifecycleOwner(it)
        }
        // 自定义弹窗布局
        val customDialogLayoutResId = item.customDialogLayoutResId()
        if (customDialogLayoutResId > 0) {
            val isLightTheme = item.customDialogLayoutLightTheme()
            if (isLightTheme == null) {
                popNotification.setCustomDialogLayoutResId(customDialogLayoutResId)
            } else {
                popNotification.setCustomDialogLayoutResId(customDialogLayoutResId, isLightTheme)
            }
        }
        return popNotification
    }

    /**
     * 显示 PopNotification 并应用自动消失策略
     * @param popNotification [PopNotification]
     * @param activity 显示的 Activity
     * @param item PopNotification 参数
     * @param onlyOnePopNotification 是否单例 PopNotification
     */
    protected open fun showPopNotification(
        popNotification: PopNotification,
        activity: Activity?,
        item: PopNotificationItem?,
        onlyOnePopNotification: Boolean = mOnlyOnePopNotification
    ) {
        // 关闭单例 PopNotification
        dismissSinglePopNotification()
        // 设置单例 PopNotification
        setSinglePopNotification(popNotification, onlyOnePopNotification)
        try {
            if (activity != null) {
                popNotification.show(activity)
            } else {
                popNotification.show()
            }
        } catch (_: Exception) {
        }
        item ?: return
        // 常驻显示
        if (item.noAutoDismiss()) {
            popNotification.noAutoDismiss()
            return
        }
        // 自动消失时长
        val delay = getAutoDismissDelay(item)
        if (delay > 0) {
            popNotification.autoDismiss(delay)
        }
    }

    /**
     * 获取自动消失时长
     * @param item PopNotification 参数
     * @return 自动消失时长 ( ms )
     */
    protected open fun getAutoDismissDelay(item: PopNotificationItem?): Long {
        val delay = item?.autoDismissDelay() ?: PopNotificationConst.UNSET_LONG
        if (delay >= 0) return delay
        return mConfig.autoDismissDelay()
    }

    /**
     * 获取 PopNotification 对齐方式
     * @param align 对齐方式
     * @return [DialogXStyle.PopNotificationSettings.ALIGN]
     */
    protected open fun getAlign(align: Int): DialogXStyle.PopNotificationSettings.ALIGN? {
        val value = if (align == PopNotificationConst.ALIGN_DEFAULT) {
            mConfig.align()
        } else {
            align
        }
        return when (value) {
            PopNotificationConst.ALIGN_CENTER -> DialogXStyle.PopNotificationSettings.ALIGN.CENTER
            PopNotificationConst.ALIGN_TOP -> DialogXStyle.PopNotificationSettings.ALIGN.TOP
            PopNotificationConst.ALIGN_BOTTOM -> DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM
            PopNotificationConst.ALIGN_TOP_INSIDE -> DialogXStyle.PopNotificationSettings.ALIGN.TOP_INSIDE
            PopNotificationConst.ALIGN_BOTTOM_INSIDE -> DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM_INSIDE
            else -> null
        }
    }

    /**
     * 获取 PopNotification 对齐方式常量值
     * @param align [DialogXStyle.PopNotificationSettings.ALIGN]
     * @return 对齐方式 ( [PopNotificationConst.ALIGN_DEFAULT] 等 )
     */
    protected open fun getAlignValue(align: DialogXStyle.PopNotificationSettings.ALIGN?): Int {
        return when (align) {
            DialogXStyle.PopNotificationSettings.ALIGN.CENTER -> PopNotificationConst.ALIGN_CENTER
            DialogXStyle.PopNotificationSettings.ALIGN.TOP -> PopNotificationConst.ALIGN_TOP
            DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM -> PopNotificationConst.ALIGN_BOTTOM
            DialogXStyle.PopNotificationSettings.ALIGN.TOP_INSIDE -> PopNotificationConst.ALIGN_TOP_INSIDE
            DialogXStyle.PopNotificationSettings.ALIGN.BOTTOM_INSIDE -> PopNotificationConst.ALIGN_BOTTOM_INSIDE
            else -> PopNotificationConst.ALIGN_DEFAULT
        }
    }

    /**
     * 获取 PopNotification 实现模式
     * @param dialogImplMode 实现模式
     * @return [DialogX.IMPL_MODE]
     */
    protected open fun getImplMode(dialogImplMode: Int): DialogX.IMPL_MODE? {
        val value = if (dialogImplMode == PopNotificationConst.IMPL_MODE_DEFAULT) {
            mConfig.dialogImplMode()
        } else {
            dialogImplMode
        }
        return when (value) {
            PopNotificationConst.IMPL_MODE_VIEW -> DialogX.IMPL_MODE.VIEW
            PopNotificationConst.IMPL_MODE_WINDOW -> DialogX.IMPL_MODE.WINDOW
            PopNotificationConst.IMPL_MODE_DIALOG_FRAGMENT -> DialogX.IMPL_MODE.DIALOG_FRAGMENT
            PopNotificationConst.IMPL_MODE_FLOATING_ACTIVITY -> DialogX.IMPL_MODE.FLOATING_ACTIVITY
            else -> null
        }
    }

    /**
     * 获取 PopNotification 明暗主题
     * @param theme 明暗主题
     * @return [DialogX.THEME]
     */
    protected open fun getTheme(theme: Int): DialogX.THEME? {
        return when (theme) {
            PopNotificationConst.THEME_LIGHT -> DialogX.THEME.LIGHT
            PopNotificationConst.THEME_DARK -> DialogX.THEME.DARK
            PopNotificationConst.THEME_AUTO -> DialogX.THEME.AUTO
            else -> null
        }
    }

    /**
     * 包装按钮点击事件
     * @param listener PopNotification 按钮点击事件
     * @return [OnDialogButtonClickListener]
     */
    protected open fun wrapButtonClick(
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): OnDialogButtonClickListener<PopNotification>? {
        listener ?: return null
        return OnDialogButtonClickListener<PopNotification> { dialog, view ->
            listener.onClick(dialog, view)
        }
    }

    /**
     * 包装显示生命周期监听
     * @param listener PopNotification 显示生命周期监听
     * @return [DialogLifecycleCallback]
     */
    protected open fun wrapLifecycle(
        listener: IPopNotificationEngine.OnPopNotificationLifecycleListener?
    ): DialogLifecycleCallback<PopNotification>? {
        listener ?: return null
        return object : DialogLifecycleCallback<PopNotification>() {
            override fun onShow(dialog: PopNotification) {
                super.onShow(dialog)
                listener.onShow(dialog)
            }

            override fun onDismiss(dialog: PopNotification) {
                super.onDismiss(dialog)
                listener.onDismiss(dialog)
            }
        }
    }

    /**
     * 包装通用执行体
     * @param runnable PopNotification 通用执行体
     * @return [DialogXRunnable]
     */
    protected open fun wrapRunnable(
        runnable: IPopNotificationEngine.OnPopNotificationRunnable?
    ): DialogXRunnable<PopNotification>? {
        runnable ?: return null
        return DialogXRunnable<PopNotification> { dialog ->
            runnable.run(dialog)
        }
    }

    /**
     * 获取 PopNotification
     * @param popNotification PopNotification Item
     * @return [PopNotification]
     */
    protected open fun getPopNotification(popNotification: Any?): PopNotification? {
        return popNotification as? PopNotification
    }

    /**
     * 获取自定义布局
     * @param onBindView OnBindView Item
     * @return [OnBindView]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getOnBindView(onBindView: Any?): OnBindView<PopNotification>? {
        return onBindView as? OnBindView<PopNotification>
    }

    /**
     * 获取主题样式
     * @param style Style Item
     * @return [DialogXStyle]
     */
    protected open fun getDialogXStyle(style: Any?): DialogXStyle? {
        return style as? DialogXStyle
    }

    /**
     * 获取文本样式
     * @param textInfo TextInfo Item
     * @return [TextInfo]
     */
    protected open fun getTextInfo(textInfo: Any?): TextInfo? {
        return textInfo as? TextInfo
    }

    /**
     * 获取动画实现
     * @param animImpl Anim Impl Item
     * @return [DialogXAnimInterface]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getDialogXAnimInterface(animImpl: Any?): DialogXAnimInterface<PopNotification>? {
        return animImpl as? DialogXAnimInterface<PopNotification>
    }

    /**
     * 获取 LifecycleOwner
     * @param owner LifecycleOwner Item
     * @return [LifecycleOwner]
     */
    protected open fun getLifecycleOwner(owner: Any?): LifecycleOwner? {
        return owner as? LifecycleOwner
    }

    // =======================
    // = 单例 PopNotification =
    // =======================

    /**
     * 设置单例 PopNotification
     * @param popNotification [PopNotification]
     * @param onlyOnePopNotification 是否单例 PopNotification
     */
    protected open fun setSinglePopNotification(
        popNotification: PopNotification?,
        onlyOnePopNotification: Boolean
    ) {
        if (onlyOnePopNotification) {
            mSinglePopNotification = popNotification
        }
    }
}