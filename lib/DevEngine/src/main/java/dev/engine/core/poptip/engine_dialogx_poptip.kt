package dev.engine.core.poptip

import android.app.Activity
import com.kongzue.dialogx.DialogX
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.DialogLifecycleCallback
import com.kongzue.dialogx.interfaces.DialogXStyle
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener
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
                DialogX.onlyOnePopTip = onlyOne
            }
            val radiusPx = it.radiusPx()
            if (radiusPx >= 0) {
                DialogX.defaultPopTipBackgroundRadius = radiusPx
            }
            getImplMode(it.dialogImplMode())?.let { mode ->
                DialogX.implIMPLMode = mode
            }
        }
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

    // ==============
    // = 单例 PopTip =
    // ==============

    /**
     * 显示单例 PopTip
     * @param text 提示文本
     * @return [PopTip]
     */
    override fun showSingle(text: CharSequence?): PopTip {
        return showSingle(PopTipItem.create(text))
    }

    /**
     * 显示单例 PopTip
     * @param item PopTip 参数
     * @return [PopTip]
     */
    override fun showSingle(item: PopTipItem?): PopTip {
        return showSingle(null, item)
    }

    /**
     * 显示单例 PopTip
     * @param activity 显示的 Activity
     * @param item PopTip 参数
     * @return [PopTip]
     */
    override fun showSingle(
        activity: Activity?,
        item: PopTipItem?
    ): PopTip {
        dismiss()
        val popTip = buildPopTip(item)
        mSinglePopTip = popTip
        showPopTip(popTip, activity, item)
        return popTip
    }

    // ==========
    // = 状态操作 =
    // ==========

    /**
     * 单例 PopTip 是否正在显示
     * @return `true` yes, `false` no
     */
    override fun isShow(): Boolean {
        return mSinglePopTip?.isShow ?: false
    }

    /**
     * 关闭单例 PopTip
     */
    override fun dismiss() {
        try {
            mSinglePopTip?.dismiss()
        } catch (_: Exception) {
        }
        mSinglePopTip = null
    }

    /**
     * 关闭指定 PopTip
     * @param popTip [PopTip]
     */
    override fun dismiss(popTip: Any?) {
        try {
            (popTip as? PopTip)?.dismiss()
        } catch (_: Exception) {
        }
    }

    /**
     * 隐藏单例 PopTip
     */
    override fun hide() {
        try {
            mSinglePopTip?.hide()
        } catch (_: Exception) {
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 构建 PopTip
     * @param item PopTip 参数
     * @return [PopTip]
     */
    protected open fun buildPopTip(item: PopTipItem?): PopTip {
        val popTip = PopTip.build()
        item ?: return popTip
        // 文本
        item.message()?.let {
            popTip.message = it
        }
        val messageResId = item.messageResId()
        if (messageResId > 0) {
            popTip.setMessage(messageResId)
        }
        // 图标
        val iconResId = item.iconResId()
        if (iconResId > 0) {
            popTip.setIconResId(iconResId)
        }
        // 按钮
        item.buttonText()?.let { buttonText ->
            val listener = item.onButtonClickListener()
            if (listener != null) {
                popTip.setButton(
                    buttonText,
                    OnDialogButtonClickListener<PopTip> { dialog, view ->
                        listener.onClick(dialog, view)
                    }
                )
            } else {
                popTip.setButton(buttonText)
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
            popTip.setRadius(radius)
        }
        // 自定义布局
        (item.customView() as? OnBindView<PopTip>)?.let {
            popTip.setCustomView(it)
        }
        // 生命周期监听
        item.lifecycleListener()?.let { listener ->
            popTip.setDialogLifecycleCallback(object : DialogLifecycleCallback<PopTip>() {
                override fun onShow(dialog: PopTip) {
                    super.onShow(dialog)
                    listener.onShow(dialog)
                }

                override fun onDismiss(dialog: PopTip) {
                    super.onDismiss(dialog)
                    listener.onDismiss(dialog)
                }
            })
        }
        return popTip
    }

    /**
     * 显示 PopTip 并应用自动消失策略
     * @param popTip [PopTip]
     * @param activity 显示的 Activity
     * @param item PopTip 参数
     */
    protected open fun showPopTip(
        popTip: PopTip,
        activity: Activity?,
        item: PopTipItem?
    ) {
        try {
            if (activity != null) {
                popTip.show(activity)
            } else {
                popTip.show()
            }
        } catch (_: Exception) {
        }
        // 常驻显示
        if (item != null && item.noAutoDismiss()) {
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
            PopTipConst.ALIGN_TOP -> DialogXStyle.PopTipSettings.ALIGN.TOP
            PopTipConst.ALIGN_BOTTOM -> DialogXStyle.PopTipSettings.ALIGN.BOTTOM
            PopTipConst.ALIGN_CENTER -> DialogXStyle.PopTipSettings.ALIGN.CENTER
            else -> null
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
}