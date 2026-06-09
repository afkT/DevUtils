package dev.engine.core.poptip

import android.app.Activity
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.*
import com.kongzue.dialogx.util.TextInfo
import dev.engine.poptip.IPopTipEngine

/**
 * detail: DialogX PopTip Engine 实现
 * @author Ttt
 * @see https://github.com/kongzue/DialogX
 */
open class DialogXPopTipEngineImpl(
    @JvmField protected val mConfig: PopTipConfig
) : IPopTipEngine<PopTipConfig?, PopTipItem?> {

    // 单例 PopTip
    @JvmField
    protected var mSinglePopTip: PopTip? = null

    // 是否单例 PopTip
    @JvmField
    protected var mOnlyOnePopTip: Boolean = false

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 PopTip Engine Config
     * @return PopTip Config
     */
    override fun getConfig(): PopTipConfig {
        return mConfig
    }

    /**
     * 设置 PopTip Engine Config
     * @param config PopTip Config
     */
    override fun setConfig(config: PopTipConfig?) {
        config?.let {
            it.onlyOne()?.let { onlyOne ->
//                // 是否单个 PopTip
//                DialogX.onlyOnePopTip = onlyOne
                // 不通过 DialogX.onlyOnePopTip 实现，避免影响全局
                mOnlyOnePopTip = onlyOne
            }

            // ===========
            // = DialogX =
            // ===========

            // 圆角 ( px, 映射全局 DialogX defaultPopTipBackgroundRadius )
            val radiusPx = it.radiusPx()
            if (radiusPx >= 0) {
                DialogX.defaultPopTipBackgroundRadius = radiusPx
            }
            // 实现模式 ( 映射全局 DialogX implIMPLMode )
            getImplMode(it.dialogImplMode())?.let { mode ->
                DialogX.implIMPLMode = mode
            }
            // 主题样式 ( 映射全局 DialogX globalStyle )
            getDialogXStyle(it.style())?.let { style ->
                DialogX.globalStyle = style
            }
            // 明暗主题 ( 映射全局 DialogX globalTheme )
            getTheme(it.theme())?.let { theme ->
                DialogX.globalTheme = theme
            }
            // 提示文本样式 ( 映射全局 DialogX popTextInfo )
            getTextInfo(it.messageTextInfo())?.let { textInfo ->
                DialogX.popTextInfo = textInfo
            }
            // 按钮文本样式 ( 映射全局 DialogX buttonTextInfo )
            getTextInfo(it.buttonTextInfo())?.let { textInfo ->
                DialogX.buttonTextInfo = textInfo
            }
            // 振动反馈 ( 映射全局 DialogX useHaptic )
            it.useHaptic()?.let { useHaptic ->
                DialogX.useHaptic = useHaptic
            }
            // 进入动画时长 ( 映射全局 DialogX enterAnimDuration )
            val enterAnimDuration = it.enterAnimDuration()
            if (enterAnimDuration >= 0) {
                DialogX.enterAnimDuration = enterAnimDuration
            }
            // 退出动画时长 ( 映射全局 DialogX exitAnimDuration )
            val exitAnimDuration = it.exitAnimDuration()
            if (exitAnimDuration >= 0) {
                DialogX.exitAnimDuration = exitAnimDuration
            }
            // 背景色 ( 映射全局 DialogX backgroundColor )
            it.backgroundColor()?.let { backgroundColor ->
                DialogX.backgroundColor = backgroundColor
            }

            // ==========
            // = PopTip =
            // ==========

            // 最大同时显示数量 ( 映射全局 PopTip maxShowCount )
            val maxShowCount = it.maxShowCount()
            if (maxShowCount > 0) {
                PopTip.maxShowCount = maxShowCount
            }
            // 覆盖进入动画时长 ( 映射全局 PopTip overrideEnterDuration )
            val overrideEnterDuration = it.overrideEnterDuration()
            if (overrideEnterDuration >= 0) {
                PopTip.overrideEnterDuration = overrideEnterDuration
            }
            // 覆盖退出动画时长 ( 映射全局 PopTip overrideExitDuration )
            val overrideExitDuration = it.overrideExitDuration()
            if (overrideExitDuration >= 0) {
                PopTip.overrideExitDuration = overrideExitDuration
            }
            // 覆盖进入动画资源 ( 映射全局 PopTip overrideEnterAnimRes )
            val overrideEnterAnimRes = it.overrideEnterAnimRes()
            if (overrideEnterAnimRes > 0) {
                PopTip.overrideEnterAnimRes = overrideEnterAnimRes
            }
            // 覆盖退出动画资源 ( 映射全局 PopTip overrideExitAnimRes )
            val overrideExitAnimRes = it.overrideExitAnimRes()
            if (overrideExitAnimRes > 0) {
                PopTip.overrideExitAnimRes = overrideExitAnimRes
            }
            // 多 PopTip 位移拦截器 ( 映射全局 PopTip moveDisplacementInterceptor )
            getMoveDisplacementInterceptor(it.moveDisplacementInterceptor())?.let { interceptor ->
                PopTip.moveDisplacementInterceptor = interceptor
            }
        }
    }

    // ==============
    // = 构建 PopTip =
    // ==============

    /**
     * 构建 PopTip ( 不显示 )
     * @return [PopTip]
     */
    override fun build(): PopTip {
        return PopTip.build()
    }

    /**
     * 构建 PopTip ( 不显示 )
     * @param item PopTip 参数
     * @return [PopTip]
     */
    override fun build(item: PopTipItem?): PopTip {
        return buildPopTip(item)
    }

    /**
     * 构建 PopTip ( 不显示 )
     * @param onBindView 自定义布局
     * @return [PopTip]
     */
    override fun buildView(onBindView: Any?): PopTip {
        val bindView = getOnBindView(onBindView)
        return if (bindView != null) PopTip.build(bindView) else PopTip.build()
    }

    // ==============
    // = 默认 PopTip =
    // ==============

    /**
     * 显示 PopTip
     * @param text 提示文本
     * @return [PopTip]
     */
    override fun show(text: CharSequence?): PopTip {
        return show(PopTipItem.create(text))
    }

    /**
     * 显示 PopTip
     * @param textResId 提示文本资源 id
     * @return [PopTip]
     */
    override fun show(textResId: Int): PopTip {
        return show(PopTipItem.create().setMessageResId(textResId))
    }

    /**
     * 显示 PopTip
     * @param text 提示文本
     * @param buttonText 按钮文本
     * @return [PopTip]
     */
    override fun show(
        text: CharSequence?,
        buttonText: CharSequence?
    ): PopTip {
        return show(PopTipItem.create(text).setButtonText(buttonText))
    }

    /**
     * 显示 PopTip
     * @param iconResId 图标资源 id
     * @param text 提示文本
     * @return [PopTip]
     */
    override fun show(
        iconResId: Int,
        text: CharSequence?
    ): PopTip {
        return show(PopTipItem.create(text).setIconResId(iconResId))
    }

    /**
     * 显示 PopTip
     * @param item PopTip 参数
     * @return [PopTip]
     */
    override fun show(item: PopTipItem?): PopTip {
        return show(null, item)
    }

    /**
     * 显示 PopTip
     * @param activity 显示的 Activity
     * @param item PopTip 参数
     * @return [PopTip]
     */
    override fun show(
        activity: Activity?,
        item: PopTipItem?
    ): PopTip {
        val popTip = buildPopTip(item)
        showPopTip(popTip, activity, item)
        return popTip
    }

    // =====================
    // = 单例 PopTip 句柄操作 =
    // =====================

    /**
     * 是否使用单例 PopTip
     * @return `true` yes, `false` no
     */
    override fun isSinglePopTip(): Boolean {
        return mOnlyOnePopTip
    }

    /**
     * 获取单例 PopTip
     * @return 单例 [PopTip]
     */
    override fun getSinglePopTip(): PopTip? {
        return mSinglePopTip
    }

    /**
     * 单例 PopTip 是否正在显示
     * @return `true` yes, `false` no
     */
    override fun isShowSinglePopTip(): Boolean {
        return mSinglePopTip?.isShow ?: false
    }

    /**
     * 关闭单例 PopTip
     */
    override fun dismissSinglePopTip() {
        try {
            mSinglePopTip?.dismiss()
        } catch (_: Exception) {
        }
        mSinglePopTip = null
    }

    /**
     * 关闭单例 PopTip ( 动画 )
     */
    override fun hideSinglePopTip() {
        try {
            mSinglePopTip?.hide()
        } catch (_: Exception) {
        }
    }

    // =================
    // = PopTip 句柄操作 =
    // =================

    /**
     * 指定 PopTip 是否正在显示
     * @param popTip [PopTip]
     * @return `true` yes, `false` no
     */
    override fun isShow(popTip: Any?): Boolean {
        return getPopTip(popTip)?.isShow ?: false
    }

    /**
     * 关闭指定 PopTip
     * @param popTip [PopTip]
     */
    override fun dismiss(popTip: Any?) {
        try {
            getPopTip(popTip)?.dismiss()
        } catch (_: Exception) {
        }
    }

    /**
     * 关闭指定 PopTip ( 动画 )
     * @param popTip [PopTip]
     */
    override fun hide(popTip: Any?) {
        try {
            getPopTip(popTip)?.hide()
        } catch (_: Exception) {
        }
    }

    /**
     * 重新显示指定 PopTip ( hide 后复显 )
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun show(popTip: Any?): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        showPopTip(popTipObj, null, null)
        return popTip
    }

    /**
     * 重新显示指定 PopTip ( hide 后复显 )
     * @param popTip [PopTip]
     * @param activity 显示的 Activity
     * @return [PopTip]
     */
    override fun show(
        popTip: Any?,
        activity: Activity?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        showPopTip(popTipObj, activity, null)
        return popTip
    }

    /**
     * 刷新指定 PopTip 界面
     * @param popTip [PopTip]
     */
    override fun refreshUI(popTip: Any?) {
        getPopTip(popTip)?.refreshUI()
    }

    /**
     * 设置指定 PopTip 自动消失时长
     * @param popTip [PopTip]
     * @param delay 自动消失时长 ( ms )
     * @return [PopTip]
     */
    override fun autoDismiss(
        popTip: Any?,
        delay: Long
    ): Any? {
        getPopTip(popTip)?.autoDismiss(delay)
        return popTip
    }

    /**
     * 重置指定 PopTip 自动消失计时器
     * @param popTip [PopTip]
     */
    override fun resetAutoDismissTimer(popTip: Any?) {
        getPopTip(popTip)?.resetAutoDismissTimer()
    }

    /**
     * 指定 PopTip 短时间显示
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun showShort(popTip: Any?): Any? {
        getPopTip(popTip)?.showShort()
        return popTip
    }

    /**
     * 指定 PopTip 长时间显示
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun showLong(popTip: Any?): Any? {
        getPopTip(popTip)?.showLong()
        return popTip
    }

    /**
     * 指定 PopTip 常驻显示 ( 不自动消失 )
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun showAlways(popTip: Any?): Any? {
        getPopTip(popTip)?.showAlways()
        return popTip
    }

    /**
     * 指定 PopTip 取消自动消失
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun noAutoDismiss(popTip: Any?): Any? {
        getPopTip(popTip)?.noAutoDismiss()
        return popTip
    }

    /**
     * 指定 PopTip 置顶显示
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun bringToFront(popTip: Any?): Any? {
        getPopTip(popTip)?.bringToFront()
        return popTip
    }

    /**
     * 设置指定 PopTip 显示层级
     * @param popTip [PopTip]
     * @param orderIndex 显示层级
     * @return [PopTip]
     */
    override fun setThisOrderIndex(
        popTip: Any?,
        orderIndex: Int
    ): Any? {
        getPopTip(popTip)?.setThisOrderIndex(orderIndex)
        return popTip
    }

    /**
     * 设置指定 PopTip 主题样式
     * @param popTip [PopTip]
     * @param style 主题样式对象
     * @return [PopTip]
     */
    override fun setStyle(
        popTip: Any?,
        style: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getDialogXStyle(style)?.let {
            popTipObj.setStyle(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 明暗主题
     * @param popTip [PopTip]
     * @param theme 明暗主题 ( [PopTipConst.THEME_DEFAULT] 等 )
     * @return [PopTip]
     */
    override fun setTheme(
        popTip: Any?,
        theme: Int
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getTheme(theme)?.let {
            popTipObj.setTheme(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 自定义布局
     * @param popTip [PopTip]
     * @param onBindView 自定义布局
     * @return [PopTip]
     */
    override fun setCustomView(
        popTip: Any?,
        onBindView: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getOnBindView(onBindView)?.let {
            popTipObj.setCustomView(it)
        }
        return popTip
    }

    /**
     * 获取指定 PopTip 自定义布局 View
     * @param popTip [PopTip]
     * @return 自定义布局 [View]
     */
    override fun getCustomView(popTip: Any?): View? {
        return getPopTip(popTip)?.customView
    }

    /**
     * 移除指定 PopTip 自定义布局
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun removeCustomView(popTip: Any?): Any? {
        getPopTip(popTip)?.removeCustomView()
        return popTip
    }

    /**
     * 获取指定 PopTip 对齐方式
     * @param popTip [PopTip]
     * @return 对齐方式 ( [PopTipConst.ALIGN_DEFAULT] 等 )
     */
    override fun getAlign(popTip: Any?): Int {
        return getAlignValue(getPopTip(popTip)?.align)
    }

    /**
     * 设置指定 PopTip 对齐方式
     * @param popTip [PopTip]
     * @param align 对齐方式 ( [PopTipConst.ALIGN_DEFAULT] 等 )
     * @return [PopTip]
     */
    override fun setAlign(
        popTip: Any?,
        align: Int
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getAlign(align)?.let {
            popTipObj.setAlign(it)
        }
        return popTip
    }

    /**
     * 获取指定 PopTip 图标资源 id
     * @param popTip [PopTip]
     * @return 图标资源 id
     */
    override fun getIconResId(popTip: Any?): Int {
        return getPopTip(popTip)?.iconResId ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 图标资源 id
     * @param popTip [PopTip]
     * @param iconResId 图标资源 id
     * @return [PopTip]
     */
    override fun setIconResId(
        popTip: Any?,
        iconResId: Int
    ): Any? {
        getPopTip(popTip)?.setIconResId(iconResId)
        return popTip
    }

    /**
     * 获取指定 PopTip 提示文本
     * @param popTip [PopTip]
     * @return 提示文本
     */
    override fun getMessage(popTip: Any?): CharSequence? {
        return getPopTip(popTip)?.message
    }

    /**
     * 设置指定 PopTip 提示文本
     * @param popTip [PopTip]
     * @param message 提示文本
     * @return [PopTip]
     */
    override fun setMessage(
        popTip: Any?,
        message: CharSequence?
    ): Any? {
        getPopTip(popTip)?.message = message
        return popTip
    }

    /**
     * 设置指定 PopTip 提示文本
     * @param popTip [PopTip]
     * @param messageResId 提示文本资源 id
     * @return [PopTip]
     */
    override fun setMessage(
        popTip: Any?,
        messageResId: Int
    ): Any? {
        getPopTip(popTip)?.setMessage(messageResId)
        return popTip
    }

    /**
     * 追加指定 PopTip 提示文本
     * @param popTip [PopTip]
     * @param message 追加文本
     * @return [PopTip]
     */
    override fun appendMessage(
        popTip: Any?,
        message: CharSequence?
    ): Any? {
        getPopTip(popTip)?.appendMessage(message)
        return popTip
    }

    /**
     * 获取指定 PopTip 按钮文本
     * @param popTip [PopTip]
     * @return 按钮文本
     */
    override fun getButtonText(popTip: Any?): CharSequence? {
        return getPopTip(popTip)?.buttonText
    }

    /**
     * 设置指定 PopTip 按钮文本
     * @param popTip [PopTip]
     * @param buttonText 按钮文本
     * @return [PopTip]
     */
    override fun setButton(
        popTip: Any?,
        buttonText: CharSequence?
    ): Any? {
        getPopTip(popTip)?.setButton(buttonText)
        return popTip
    }

    /**
     * 设置指定 PopTip 按钮文本
     * @param popTip [PopTip]
     * @param buttonTextResId 按钮文本资源 id
     * @return [PopTip]
     */
    override fun setButton(
        popTip: Any?,
        buttonTextResId: Int
    ): Any? {
        getPopTip(popTip)?.setButton(buttonTextResId)
        return popTip
    }

    /**
     * 设置指定 PopTip 按钮点击事件
     * @param popTip [PopTip]
     * @param listener 按钮点击事件
     * @return [PopTip]
     */
    override fun setButton(
        popTip: Any?,
        listener: IPopTipEngine.OnButtonClickListener?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        wrapButtonClick(listener)?.let {
            popTipObj.setButton(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 按钮文本及点击事件
     * @param popTip [PopTip]
     * @param buttonText 按钮文本
     * @param listener 按钮点击事件
     * @return [PopTip]
     */
    override fun setButton(
        popTip: Any?,
        buttonText: CharSequence?,
        listener: IPopTipEngine.OnButtonClickListener?
    ): Any? {
        getPopTip(popTip)?.setButton(buttonText, wrapButtonClick(listener))
        return popTip
    }

    /**
     * 设置指定 PopTip 按钮文本及点击事件
     * @param popTip [PopTip]
     * @param buttonTextResId 按钮文本资源 id
     * @param listener 按钮点击事件
     * @return [PopTip]
     */
    override fun setButton(
        popTip: Any?,
        buttonTextResId: Int,
        listener: IPopTipEngine.OnButtonClickListener?
    ): Any? {
        getPopTip(popTip)?.setButton(buttonTextResId, wrapButtonClick(listener))
        return popTip
    }

    /**
     * 获取指定 PopTip 提示文本样式
     * @param popTip [PopTip]
     * @return 提示文本样式对象 [TextInfo]
     */
    override fun getMessageTextInfo(popTip: Any?): Any? {
        return getPopTip(popTip)?.messageTextInfo
    }

    /**
     * 设置指定 PopTip 提示文本样式
     * @param popTip [PopTip]
     * @param messageTextInfo 提示文本样式对象 [TextInfo]
     * @return [PopTip]
     */
    override fun setMessageTextInfo(
        popTip: Any?,
        messageTextInfo: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getTextInfo(messageTextInfo)?.let {
            popTipObj.setMessageTextInfo(it)
        }
        return popTip
    }

    /**
     * 获取指定 PopTip 按钮文本样式
     * @param popTip [PopTip]
     * @return 按钮文本样式对象 [TextInfo]
     */
    override fun getButtonTextInfo(popTip: Any?): Any? {
        return getPopTip(popTip)?.buttonTextInfo
    }

    /**
     * 设置指定 PopTip 按钮文本样式
     * @param popTip [PopTip]
     * @param buttonTextInfo 按钮文本样式对象 [TextInfo]
     * @return [PopTip]
     */
    override fun setButtonTextInfo(
        popTip: Any?,
        buttonTextInfo: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getTextInfo(buttonTextInfo)?.let {
            popTipObj.setButtonTextInfo(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 按钮点击事件
     * @param popTip [PopTip]
     * @param listener 按钮点击事件
     * @return [PopTip]
     */
    override fun setOnButtonClickListener(
        popTip: Any?,
        listener: IPopTipEngine.OnButtonClickListener?
    ): Any? {
        getPopTip(popTip)?.setOnButtonClickListener(wrapButtonClick(listener))
        return popTip
    }

    /**
     * 设置指定 PopTip 自身点击事件
     * @param popTip [PopTip]
     * @param listener 点击事件
     * @return [PopTip]
     */
    override fun setOnPopTipClickListener(
        popTip: Any?,
        listener: IPopTipEngine.OnButtonClickListener?
    ): Any? {
        getPopTip(popTip)?.setOnPopTipClickListener(wrapButtonClick(listener))
        return popTip
    }

    /**
     * 指定 PopTip 图标是否随明暗模式自动染色
     * @param popTip [PopTip]
     * @return `true` yes, `false` no
     */
    @Suppress("DEPRECATION")
    override fun isAutoTintIconInLightOrDarkMode(popTip: Any?): Boolean {
        return getPopTip(popTip)?.isAutoTintIconInLightOrDarkMode ?: false
    }

    /**
     * 设置指定 PopTip 图标是否随明暗模式自动染色
     * @param popTip [PopTip]
     * @param autoTint 是否自动染色
     * @return [PopTip]
     */
    @Suppress("DEPRECATION")
    override fun setAutoTintIconInLightOrDarkMode(
        popTip: Any?,
        autoTint: Boolean
    ): Any? {
        getPopTip(popTip)?.isAutoTintIconInLightOrDarkMode = autoTint
        return popTip
    }

    /**
     * 指定 PopTip 图标是否染色
     * @param popTip [PopTip]
     * @return `true` yes, `false` no
     */
    override fun isTintIcon(popTip: Any?): Boolean {
        return getPopTip(popTip)?.isTintIcon ?: false
    }

    /**
     * 设置指定 PopTip 图标是否染色
     * @param popTip [PopTip]
     * @param tintIcon 是否染色
     * @return [PopTip]
     */
    override fun setTintIcon(
        popTip: Any?,
        tintIcon: Boolean
    ): Any? {
        getPopTip(popTip)?.isTintIcon = tintIcon
        return popTip
    }

    /**
     * 设置指定 PopTip 成功状态图标
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun iconSuccess(popTip: Any?): Any? {
        getPopTip(popTip)?.iconSuccess()
        return popTip
    }

    /**
     * 设置指定 PopTip 警告状态图标
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun iconWarning(popTip: Any?): Any? {
        getPopTip(popTip)?.iconWarning()
        return popTip
    }

    /**
     * 设置指定 PopTip 错误状态图标
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun iconError(popTip: Any?): Any? {
        getPopTip(popTip)?.iconError()
        return popTip
    }

    /**
     * 获取指定 PopTip 背景色
     * @param popTip [PopTip]
     * @return 背景色 ( ColorInt )
     */
    override fun getBackgroundColor(popTip: Any?): Int {
        return getPopTip(popTip)?.backgroundColor ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 背景色
     * @param popTip [PopTip]
     * @param backgroundColor 背景色 ( ColorInt )
     * @return [PopTip]
     */
    override fun setBackgroundColor(
        popTip: Any?,
        backgroundColor: Int
    ): Any? {
        getPopTip(popTip)?.setBackgroundColor(backgroundColor)
        return popTip
    }

    /**
     * 设置指定 PopTip 背景色
     * @param popTip [PopTip]
     * @param backgroundColorResId 背景色资源 id ( ColorRes )
     * @return [PopTip]
     */
    override fun setBackgroundColorRes(
        popTip: Any?,
        backgroundColorResId: Int
    ): Any? {
        getPopTip(popTip)?.setBackgroundColorRes(backgroundColorResId)
        return popTip
    }

    /**
     * 获取指定 PopTip 圆角 ( px )
     * @param popTip [PopTip]
     * @return 圆角 ( px )
     */
    override fun getRadius(popTip: Any?): Float {
        return getPopTip(popTip)?.radius ?: PopTipConst.UNSET_FLOAT
    }

    /**
     * 设置指定 PopTip 圆角 ( px )
     * @param popTip [PopTip]
     * @param radiusPx 圆角 ( px )
     * @return [PopTip]
     */
    override fun setRadius(
        popTip: Any?,
        radiusPx: Float
    ): Any? {
        getPopTip(popTip)?.radius = radiusPx
        return popTip
    }

    /**
     * 获取指定 PopTip 进入动画时长 ( ms )
     * @param popTip [PopTip]
     * @return 进入动画时长 ( ms )
     */
    override fun getEnterAnimDuration(popTip: Any?): Long {
        return getPopTip(popTip)?.enterAnimDuration ?: PopTipConst.UNSET_LONG
    }

    /**
     * 设置指定 PopTip 进入动画时长 ( ms )
     * @param popTip [PopTip]
     * @param enterAnimDuration 进入动画时长 ( ms )
     * @return [PopTip]
     */
    override fun setEnterAnimDuration(
        popTip: Any?,
        enterAnimDuration: Long
    ): Any? {
        getPopTip(popTip)?.setEnterAnimDuration(enterAnimDuration)
        return popTip
    }

    /**
     * 获取指定 PopTip 退出动画时长 ( ms )
     * @param popTip [PopTip]
     * @return 退出动画时长 ( ms )
     */
    override fun getExitAnimDuration(popTip: Any?): Long {
        return getPopTip(popTip)?.exitAnimDuration ?: PopTipConst.UNSET_LONG
    }

    /**
     * 设置指定 PopTip 退出动画时长 ( ms )
     * @param popTip [PopTip]
     * @param exitAnimDuration 退出动画时长 ( ms )
     * @return [PopTip]
     */
    override fun setExitAnimDuration(
        popTip: Any?,
        exitAnimDuration: Long
    ): Any? {
        getPopTip(popTip)?.setExitAnimDuration(exitAnimDuration)
        return popTip
    }

    /**
     * 设置指定 PopTip 进出场动画资源
     * @param popTip [PopTip]
     * @param enterResId 进入动画资源 id
     * @param exitResId 退出动画资源 id
     * @return [PopTip]
     */
    override fun setAnimResId(
        popTip: Any?,
        enterResId: Int,
        exitResId: Int
    ): Any? {
        getPopTip(popTip)?.setAnimResId(enterResId, exitResId)
        return popTip
    }

    /**
     * 设置指定 PopTip 进入动画资源
     * @param popTip [PopTip]
     * @param enterResId 进入动画资源 id
     * @return [PopTip]
     */
    override fun setEnterAnimResId(
        popTip: Any?,
        enterResId: Int
    ): Any? {
        getPopTip(popTip)?.setEnterAnimResId(enterResId)
        return popTip
    }

    /**
     * 设置指定 PopTip 退出动画资源
     * @param popTip [PopTip]
     * @param exitResId 退出动画资源 id
     * @return [PopTip]
     */
    override fun setExitAnimResId(
        popTip: Any?,
        exitResId: Int
    ): Any? {
        getPopTip(popTip)?.setExitAnimResId(exitResId)
        return popTip
    }

    /**
     * 获取指定 PopTip 动画实现
     * @param popTip [PopTip]
     * @return 动画实现对象 [DialogXAnimInterface]
     */
    override fun getDialogXAnimImpl(popTip: Any?): Any? {
        return getPopTip(popTip)?.dialogXAnimImpl
    }

    /**
     * 设置指定 PopTip 动画实现
     * @param popTip [PopTip]
     * @param animImpl 动画实现对象 [DialogXAnimInterface]
     * @return [PopTip]
     */
    override fun setDialogXAnimImpl(
        popTip: Any?,
        animImpl: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getDialogXAnimInterface(animImpl)?.let {
            popTipObj.setDialogXAnimImpl(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 是否启用振动反馈
     * @param popTip [PopTip]
     * @param enabled 是否启用振动反馈
     * @return [PopTip]
     */
    override fun setHapticFeedbackEnabled(
        popTip: Any?,
        enabled: Boolean
    ): Any? {
        getPopTip(popTip)?.setHapticFeedbackEnabled(enabled)
        return popTip
    }

    /**
     * 设置指定 PopTip 实现模式
     * @param popTip [PopTip]
     * @param dialogImplMode 实现模式 ( [PopTipConst.IMPL_MODE_DEFAULT] 等 )
     * @return [PopTip]
     */
    override fun setDialogImplMode(
        popTip: Any?,
        dialogImplMode: Int
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getImplMode(dialogImplMode)?.let {
            popTipObj.setDialogImplMode(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 外边距
     * @param popTip [PopTip]
     * @param left 左边距
     * @param top 上边距
     * @param right 右边距
     * @param bottom 下边距
     * @return [PopTip]
     */
    override fun setMargin(
        popTip: Any?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): Any? {
        getPopTip(popTip)?.setMargin(left, top, right, bottom)
        return popTip
    }

    /**
     * 获取指定 PopTip 左外边距
     * @param popTip [PopTip]
     * @return 左外边距
     */
    override fun getMarginLeft(popTip: Any?): Int {
        return getPopTip(popTip)?.marginLeft ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 左外边距
     * @param popTip [PopTip]
     * @param left 左边距
     * @return [PopTip]
     */
    override fun setMarginLeft(
        popTip: Any?,
        left: Int
    ): Any? {
        getPopTip(popTip)?.marginLeft = left
        return popTip
    }

    /**
     * 获取指定 PopTip 上外边距
     * @param popTip [PopTip]
     * @return 上外边距
     */
    override fun getMarginTop(popTip: Any?): Int {
        return getPopTip(popTip)?.marginTop ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 上外边距
     * @param popTip [PopTip]
     * @param top 上边距
     * @return [PopTip]
     */
    override fun setMarginTop(
        popTip: Any?,
        top: Int
    ): Any? {
        getPopTip(popTip)?.marginTop = top
        return popTip
    }

    /**
     * 获取指定 PopTip 右外边距
     * @param popTip [PopTip]
     * @return 右外边距
     */
    override fun getMarginRight(popTip: Any?): Int {
        return getPopTip(popTip)?.marginRight ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 右外边距
     * @param popTip [PopTip]
     * @param right 右边距
     * @return [PopTip]
     */
    override fun setMarginRight(
        popTip: Any?,
        right: Int
    ): Any? {
        getPopTip(popTip)?.marginRight = right
        return popTip
    }

    /**
     * 获取指定 PopTip 下外边距
     * @param popTip [PopTip]
     * @return 下外边距
     */
    override fun getMarginBottom(popTip: Any?): Int {
        return getPopTip(popTip)?.marginBottom ?: PopTipConst.UNSET
    }

    /**
     * 设置指定 PopTip 下外边距
     * @param popTip [PopTip]
     * @param bottom 下边距
     * @return [PopTip]
     */
    override fun setMarginBottom(
        popTip: Any?,
        bottom: Int
    ): Any? {
        getPopTip(popTip)?.marginBottom = bottom
        return popTip
    }

    /**
     * 设置指定 PopTip 根布局内边距
     * @param popTip [PopTip]
     * @param padding 内边距
     * @return [PopTip]
     */
    override fun setRootPadding(
        popTip: Any?,
        padding: Int
    ): Any? {
        getPopTip(popTip)?.setRootPadding(padding)
        return popTip
    }

    /**
     * 设置指定 PopTip 根布局内边距
     * @param popTip [PopTip]
     * @param paddingLeft 左内边距
     * @param paddingTop 上内边距
     * @param paddingRight 右内边距
     * @param paddingBottom 下内边距
     * @return [PopTip]
     */
    override fun setRootPadding(
        popTip: Any?,
        paddingLeft: Int,
        paddingTop: Int,
        paddingRight: Int,
        paddingBottom: Int
    ): Any? {
        getPopTip(popTip)?.setRootPadding(
            paddingLeft, paddingTop, paddingRight, paddingBottom
        )
        return popTip
    }

    /**
     * 设置指定 PopTip 显示生命周期监听
     * @param popTip [PopTip]
     * @param listener 显示生命周期监听
     * @return [PopTip]
     */
    override fun setLifecycleListener(
        popTip: Any?,
        listener: IPopTipEngine.OnPopTipLifecycleListener?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        wrapLifecycle(listener)?.let {
            popTipObj.dialogLifecycleCallback = it
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 显示回调
     * @param popTip [PopTip]
     * @param runnable 显示回调
     * @return [PopTip]
     */
    override fun onShow(
        popTip: Any?,
        runnable: IPopTipEngine.OnPopTipRunnable?
    ): Any? {
        getPopTip(popTip)?.onShow(wrapRunnable(runnable))
        return popTip
    }

    /**
     * 设置指定 PopTip 关闭回调
     * @param popTip [PopTip]
     * @param runnable 关闭回调
     * @return [PopTip]
     */
    override fun onDismiss(
        popTip: Any?,
        runnable: IPopTipEngine.OnPopTipRunnable?
    ): Any? {
        getPopTip(popTip)?.onDismiss(wrapRunnable(runnable))
        return popTip
    }

    /**
     * 设置指定 PopTip 快捷功能键动作
     * @param popTip [PopTip]
     * @param actionId 动作 id
     * @param runnable 动作执行体
     * @return [PopTip]
     */
    override fun setActionRunnable(
        popTip: Any?,
        actionId: Int,
        runnable: IPopTipEngine.OnPopTipRunnable?
    ): Any? {
        getPopTip(popTip)?.setActionRunnable(actionId, wrapRunnable(runnable))
        return popTip
    }

    /**
     * 清除指定 PopTip 快捷功能键动作
     * @param popTip [PopTip]
     * @param actionId 动作 id
     * @return [PopTip]
     */
    override fun cleanAction(
        popTip: Any?,
        actionId: Int
    ): Any? {
        getPopTip(popTip)?.cleanAction(actionId)
        return popTip
    }

    /**
     * 清除指定 PopTip 全部快捷功能键动作
     * @param popTip [PopTip]
     * @return [PopTip]
     */
    override fun cleanAllAction(popTip: Any?): Any? {
        getPopTip(popTip)?.cleanAllAction()
        return popTip
    }

    /**
     * 设置指定 PopTip 临时存储数据
     * @param popTip [PopTip]
     * @param key 数据 key
     * @param obj 数据值
     * @return [PopTip]
     */
    override fun setData(
        popTip: Any?,
        key: String?,
        obj: Any?
    ): Any? {
        getPopTip(popTip)?.setData(key, obj)
        return popTip
    }

    /**
     * 绑定指定 PopTip 随 LifecycleOwner 关闭
     * @param popTip [PopTip]
     * @param owner LifecycleOwner 对象
     * @return [PopTip]
     */
    override fun bindDismissWithLifecycleOwner(
        popTip: Any?,
        owner: Any?
    ): Any? {
        val popTipObj = getPopTip(popTip) ?: return popTip
        getLifecycleOwner(owner)?.let {
            popTipObj.bindDismissWithLifecycleOwner(it)
        }
        return popTip
    }

    /**
     * 设置指定 PopTip 自定义弹窗布局资源 id
     * @param popTip [PopTip]
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @return [PopTip]
     */
    override fun setCustomDialogLayoutResId(
        popTip: Any?,
        customDialogLayoutId: Int
    ): Any? {
        getPopTip(popTip)?.setCustomDialogLayoutResId(customDialogLayoutId)
        return popTip
    }

    /**
     * 设置指定 PopTip 自定义弹窗布局资源 id
     * @param popTip [PopTip]
     * @param customDialogLayoutId 自定义弹窗布局资源 id
     * @param isLightTheme 是否亮色主题布局
     * @return [PopTip]
     */
    override fun setCustomDialogLayoutResId(
        popTip: Any?,
        customDialogLayoutId: Int,
        isLightTheme: Boolean
    ): Any? {
        getPopTip(popTip)?.setCustomDialogLayoutResId(customDialogLayoutId, isLightTheme)
        return popTip
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 构建 PopTip
     * @param item PopTip 参数
     * @return [PopTip]
     */
    @Suppress("DEPRECATION")
    protected open fun buildPopTip(item: PopTipItem?): PopTip {
        val popTip = PopTip.build()
        item ?: return popTip
        // 文本
        item.message()?.let {
            popTip.message = it
        }
        // 文本资源
        val messageResId = item.messageResId()
        if (messageResId > 0) {
            popTip.setMessage(messageResId)
        }
        // 图标
        val iconResId = item.iconResId()
        if (iconResId > 0) {
            popTip.setIconResId(iconResId)
        }
        // 按钮 ( 文本优先, 其次文本资源 id )
        val buttonClick = wrapButtonClick(
            item.onButtonClickListener()
        )
        val buttonText = item.buttonText()
        val buttonTextResId = item.buttonTextResId()
        if (buttonText != null) {
            if (buttonClick != null) {
                popTip.setButton(buttonText, buttonClick)
            } else {
                popTip.setButton(buttonText)
            }
        } else if (buttonTextResId > 0) {
            if (buttonClick != null) {
                popTip.setButton(buttonTextResId, buttonClick)
            } else {
                popTip.setButton(buttonTextResId)
            }
        }
        // 对齐方式
        getAlign(item.align())?.let {
            popTip.setAlign(it)
        }
        // 实现模式
        getImplMode(item.dialogImplMode())?.let {
            popTip.setDialogImplMode(it)
        }
        // 背景色
        item.backgroundColor()?.let {
            popTip.setBackgroundColor(it)
        }
        // 圆角
        val radius = item.radius()
        if (radius >= 0) {
            popTip.radius = radius
        }
        // 自定义布局
        getOnBindView(item.customView())?.let {
            popTip.setCustomView(it)
        }
        // 生命周期监听
        item.lifecycleListener()?.let { listener ->
            popTip.dialogLifecycleCallback = wrapLifecycle(listener)
        }
        // 主题样式
        getDialogXStyle(item.style())?.let {
            popTip.setStyle(it)
        }
        // 明暗主题
        getTheme(item.theme())?.let {
            popTip.setTheme(it)
        }
        // 提示文本样式
        getTextInfo(item.messageTextInfo())?.let {
            popTip.setMessageTextInfo(it)
        }
        // 按钮文本样式
        getTextInfo(item.buttonTextInfo())?.let {
            popTip.setButtonTextInfo(it)
        }
        // 状态预置图标
        when (item.iconState()) {
            PopTipConst.ICON_SUCCESS -> popTip.iconSuccess()
            PopTipConst.ICON_WARNING -> popTip.iconWarning()
            PopTipConst.ICON_ERROR -> popTip.iconError()
        }
        // PopTip 自身点击事件
        item.onPopTipClickListener()?.let { listener ->
            popTip.setOnPopTipClickListener(wrapButtonClick(listener))
        }
        // 背景色资源 id
        val backgroundColorRes = item.backgroundColorRes()
        if (backgroundColorRes > 0) {
            popTip.setBackgroundColorRes(backgroundColorRes)
        }
        // 图标染色
        item.autoTintIcon()?.let {
            popTip.setAutoTintIconInLightOrDarkMode(it)
        }
        item.tintIcon()?.let {
            popTip.setTintIcon(it)
        }
        // 动画时长
        val enterAnimDuration = item.enterAnimDuration()
        if (enterAnimDuration >= 0) {
            popTip.setEnterAnimDuration(enterAnimDuration)
        }
        val exitAnimDuration = item.exitAnimDuration()
        if (exitAnimDuration >= 0) {
            popTip.setExitAnimDuration(exitAnimDuration)
        }
        // 动画资源
        val enterAnimResId = item.enterAnimResId()
        if (enterAnimResId > 0) {
            popTip.setEnterAnimResId(enterAnimResId)
        }
        val exitAnimResId = item.exitAnimResId()
        if (exitAnimResId > 0) {
            popTip.setExitAnimResId(exitAnimResId)
        }
        // 自定义动画实现
        getDialogXAnimInterface(item.dialogXAnimImpl())?.let {
            popTip.setDialogXAnimImpl(it)
        }
        // 振动反馈
        item.hapticFeedbackEnabled()?.let {
            popTip.setHapticFeedbackEnabled(it)
        }
        // 外边距
        val marginLeft = item.marginLeft()
        if (marginLeft != PopTipConst.UNSET) {
            popTip.marginLeft = marginLeft
        }
        val marginTop = item.marginTop()
        if (marginTop != PopTipConst.UNSET) {
            popTip.marginTop = marginTop
        }
        val marginRight = item.marginRight()
        if (marginRight != PopTipConst.UNSET) {
            popTip.marginRight = marginRight
        }
        val marginBottom = item.marginBottom()
        if (marginBottom != PopTipConst.UNSET) {
            popTip.marginBottom = marginBottom
        }
        // 根布局内边距
        val rootPadding = item.rootPadding()
        if (rootPadding != PopTipConst.UNSET) {
            popTip.setRootPadding(rootPadding)
        }
        // 临时存储数据
        item.data()?.forEach { (key, value) ->
            popTip.setData(key, value)
        }
        // 显示层级
        val thisOrderIndex = item.thisOrderIndex()
        if (thisOrderIndex != PopTipConst.UNSET) {
            popTip.setThisOrderIndex(thisOrderIndex)
        }
        // 绑定关闭的 LifecycleOwner
        getLifecycleOwner(item.lifecycleOwner())?.let {
            popTip.bindDismissWithLifecycleOwner(it)
        }
        // 自定义弹窗布局
        val customDialogLayoutResId = item.customDialogLayoutResId()
        if (customDialogLayoutResId > 0) {
            val isLightTheme = item.customDialogLayoutLightTheme()
            if (isLightTheme == null) {
                popTip.setCustomDialogLayoutResId(customDialogLayoutResId)
            } else {
                popTip.setCustomDialogLayoutResId(
                    customDialogLayoutResId, isLightTheme
                )
            }
        }
        return popTip
    }

    /**
     * 显示 PopTip 并应用自动消失策略
     * @param popTip [PopTip]
     * @param activity 显示的 Activity
     * @param item PopTip 参数
     * @param onlyOnePopTip 是否单例 PopTip
     */
    protected open fun showPopTip(
        popTip: PopTip,
        activity: Activity?,
        item: PopTipItem?,
        onlyOnePopTip: Boolean = mOnlyOnePopTip
    ) {
        // 关闭单例 PopTip
        dismissSinglePopTip()
        // 设置单例 PopTip
        setSinglePopTip(popTip, onlyOnePopTip)
        try {
            if (activity != null) {
                popTip.show(activity)
            } else {
                popTip.show()
            }
        } catch (_: Exception) {
        }
        item ?: return
        // 常驻显示
        if (item.noAutoDismiss()) {
            popTip.noAutoDismiss()
            return
        }
        // 自动消失时长
        val delay = getAutoDismissDelay(item)
        if (delay > 0) {
            popTip.autoDismiss(delay)
        }
    }

    /**
     * 获取自动消失时长
     * @param item PopTip 参数
     * @return 自动消失时长 ( ms )
     */
    protected open fun getAutoDismissDelay(item: PopTipItem?): Long {
        val delay = item?.autoDismissDelay() ?: PopTipConst.UNSET_LONG
        if (delay >= 0) return delay
        return mConfig.autoDismissDelay()
    }

    /**
     * 获取 PopTip 对齐方式
     * @param align 对齐方式
     * @return [DialogXStyle.PopTipSettings.ALIGN]
     */
    protected open fun getAlign(align: Int): DialogXStyle.PopTipSettings.ALIGN? {
        val value = if (align == PopTipConst.ALIGN_DEFAULT) {
            mConfig.align()
        } else {
            align
        }
        return when (value) {
            PopTipConst.ALIGN_CENTER -> DialogXStyle.PopTipSettings.ALIGN.CENTER
            PopTipConst.ALIGN_TOP -> DialogXStyle.PopTipSettings.ALIGN.TOP
            PopTipConst.ALIGN_BOTTOM -> DialogXStyle.PopTipSettings.ALIGN.BOTTOM
            PopTipConst.ALIGN_TOP_INSIDE -> DialogXStyle.PopTipSettings.ALIGN.TOP_INSIDE
            PopTipConst.ALIGN_BOTTOM_INSIDE -> DialogXStyle.PopTipSettings.ALIGN.BOTTOM_INSIDE
            else -> null
        }
    }

    /**
     * 获取 PopTip 对齐方式常量值
     * @param align [DialogXStyle.PopTipSettings.ALIGN]
     * @return 对齐方式 ( [PopTipConst.ALIGN_DEFAULT] 等 )
     */
    protected open fun getAlignValue(align: DialogXStyle.PopTipSettings.ALIGN?): Int {
        return when (align) {
            DialogXStyle.PopTipSettings.ALIGN.CENTER -> PopTipConst.ALIGN_CENTER
            DialogXStyle.PopTipSettings.ALIGN.TOP -> PopTipConst.ALIGN_TOP
            DialogXStyle.PopTipSettings.ALIGN.BOTTOM -> PopTipConst.ALIGN_BOTTOM
            DialogXStyle.PopTipSettings.ALIGN.TOP_INSIDE -> PopTipConst.ALIGN_TOP_INSIDE
            DialogXStyle.PopTipSettings.ALIGN.BOTTOM_INSIDE -> PopTipConst.ALIGN_BOTTOM_INSIDE
            else -> PopTipConst.ALIGN_DEFAULT
        }
    }

    /**
     * 获取 PopTip 实现模式
     * @param dialogImplMode 实现模式
     * @return [DialogX.IMPL_MODE]
     */
    protected open fun getImplMode(dialogImplMode: Int): DialogX.IMPL_MODE? {
        val value = if (dialogImplMode == PopTipConst.IMPL_MODE_DEFAULT) {
            mConfig.dialogImplMode()
        } else {
            dialogImplMode
        }
        return when (value) {
            PopTipConst.IMPL_MODE_VIEW -> DialogX.IMPL_MODE.VIEW
            PopTipConst.IMPL_MODE_WINDOW -> DialogX.IMPL_MODE.WINDOW
            PopTipConst.IMPL_MODE_DIALOG_FRAGMENT -> DialogX.IMPL_MODE.DIALOG_FRAGMENT
            PopTipConst.IMPL_MODE_FLOATING_ACTIVITY -> DialogX.IMPL_MODE.FLOATING_ACTIVITY
            else -> null
        }
    }

    /**
     * 获取 PopTip 明暗主题
     * @param theme 明暗主题
     * @return [DialogX.THEME]
     */
    protected open fun getTheme(theme: Int): DialogX.THEME? {
        return when (theme) {
            PopTipConst.THEME_LIGHT -> DialogX.THEME.LIGHT
            PopTipConst.THEME_DARK -> DialogX.THEME.DARK
            PopTipConst.THEME_AUTO -> DialogX.THEME.AUTO
            else -> null
        }
    }

    /**
     * 包装按钮点击事件
     * @param listener PopTip 按钮点击事件
     * @return [OnDialogButtonClickListener]
     */
    protected open fun wrapButtonClick(
        listener: IPopTipEngine.OnButtonClickListener?
    ): OnDialogButtonClickListener<PopTip>? {
        listener ?: return null
        return OnDialogButtonClickListener<PopTip> { dialog, view ->
            listener.onClick(dialog, view)
        }
    }

    /**
     * 包装显示生命周期监听
     * @param listener PopTip 显示生命周期监听
     * @return [DialogLifecycleCallback]
     */
    protected open fun wrapLifecycle(
        listener: IPopTipEngine.OnPopTipLifecycleListener?
    ): DialogLifecycleCallback<PopTip>? {
        listener ?: return null
        return object : DialogLifecycleCallback<PopTip>() {
            override fun onShow(dialog: PopTip) {
                super.onShow(dialog)
                listener.onShow(dialog)
            }

            override fun onDismiss(dialog: PopTip) {
                super.onDismiss(dialog)
                listener.onDismiss(dialog)
            }
        }
    }

    /**
     * 包装通用执行体
     * @param runnable PopTip 通用执行体
     * @return [DialogXRunnable]
     */
    protected open fun wrapRunnable(
        runnable: IPopTipEngine.OnPopTipRunnable?
    ): DialogXRunnable<PopTip>? {
        runnable ?: return null
        return DialogXRunnable<PopTip> { dialog ->
            runnable.run(dialog)
        }
    }

    /**
     * 获取 PopTip
     * @param popTip PopTip Item
     * @return [PopTip]
     */
    protected open fun getPopTip(popTip: Any?): PopTip? {
        return popTip as? PopTip
    }

    /**
     * 获取自定义布局
     * @param onBindView OnBindView Item
     * @return [OnBindView]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getOnBindView(onBindView: Any?): OnBindView<PopTip>? {
        return onBindView as? OnBindView<PopTip>
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
    protected open fun getDialogXAnimInterface(animImpl: Any?): DialogXAnimInterface<PopTip>? {
        return animImpl as? DialogXAnimInterface<PopTip>
    }

    /**
     * 获取 LifecycleOwner
     * @param owner LifecycleOwner Item
     * @return [LifecycleOwner]
     */
    protected open fun getLifecycleOwner(owner: Any?): LifecycleOwner? {
        return owner as? LifecycleOwner
    }

    /**
     * 获取多 PopTip 位移拦截器
     * @param interceptor Move Displacement Interceptor Item
     * @return [PopMoveDisplacementInterceptor]
     */
    @Suppress("UNCHECKED_CAST")
    protected open fun getMoveDisplacementInterceptor(
        interceptor: Any?
    ): PopMoveDisplacementInterceptor<PopTip>? {
        return interceptor as? PopMoveDisplacementInterceptor<PopTip>
    }

    // ==============
    // = 单例 PopTip =
    // ==============

    /**
     * 设置单例 PopTip
     * @param popTip [PopTip]
     * @param onlyOnePopTip 是否单例 PopTip
     */
    protected open fun setSinglePopTip(
        popTip: PopTip?,
        onlyOnePopTip: Boolean
    ) {
        if (onlyOnePopTip) {
            mSinglePopTip = popTip
        }
    }
}