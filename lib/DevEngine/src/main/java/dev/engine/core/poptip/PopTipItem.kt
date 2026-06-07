package dev.engine.core.poptip

import android.view.View
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.OnBindView
import dev.engine.poptip.IPopTipEngine

/**
 * detail: PopTip Item Params
 * @author Ttt
 */
open class PopTipItem private constructor(
    message: CharSequence?
) : IPopTipEngine.EngineItem {

    // 提示文本
    private var mMessage: CharSequence? = message

    // 提示文本资源 id
    private var mMessageResId = PopTipConst.UNSET

    // 图标资源 id
    private var mIconResId = PopTipConst.UNSET

    // 按钮文本
    private var mButtonText: CharSequence? = null

    // 按钮点击事件
    private var mOnButtonClickListener: IPopTipEngine.OnButtonClickListener? = null

    // 对齐方式
    private var mAlign = PopTipConst.ALIGN_DEFAULT

    // 自动消失时长 ( ms )
    private var mAutoDismissDelay = PopTipConst.UNSET_LONG

    // 是否常驻显示
    private var mNoAutoDismiss = false

    // 实现模式
    private var mDialogImplMode = PopTipConst.IMPL_MODE_DEFAULT

    // 背景色
    private var mBackgroundColor: Int? = null

    // 圆角 ( px )
    private var mRadius = PopTipConst.UNSET_FLOAT

    // 自定义布局
    private var mCustomView: OnBindView<PopTip>? = null

    // 显示生命周期监听
    private var mLifecycleListener: IPopTipEngine.OnPopTipLifecycleListener? = null

    companion object {

        /**
         * 创建 PopTip Item
         * @return [PopTipItem]
         */
        fun create(): PopTipItem {
            return PopTipItem(null)
        }

        /**
         * 创建 PopTip Item
         * @param message 提示文本
         * @return [PopTipItem]
         */
        fun create(message: CharSequence?): PopTipItem {
            return PopTipItem(message)
        }
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 提示文本
     */
    override fun message(): CharSequence? {
        return mMessage
    }

    open fun setMessage(message: CharSequence?): PopTipItem {
        mMessage = message
        return this
    }

    /**
     * 提示文本资源 id
     */
    override fun messageResId(): Int {
        return mMessageResId
    }

    open fun setMessageResId(messageResId: Int): PopTipItem {
        mMessageResId = messageResId
        return this
    }

    /**
     * 图标资源 id
     */
    override fun iconResId(): Int {
        return mIconResId
    }

    open fun setIconResId(iconResId: Int): PopTipItem {
        mIconResId = iconResId
        return this
    }

    /**
     * 按钮文本
     */
    override fun buttonText(): CharSequence? {
        return mButtonText
    }

    open fun setButtonText(buttonText: CharSequence?): PopTipItem {
        mButtonText = buttonText
        return this
    }

    /**
     * 按钮点击事件
     */
    override fun onButtonClickListener(): IPopTipEngine.OnButtonClickListener? {
        return mOnButtonClickListener
    }

    open fun setOnButtonClickListener(
        listener: IPopTipEngine.OnButtonClickListener?
    ): PopTipItem {
        mOnButtonClickListener = listener
        return this
    }

    /**
     * 对齐方式
     */
    override fun align(): Int {
        return mAlign
    }

    open fun setAlign(align: Int): PopTipItem {
        mAlign = align
        return this
    }

    /**
     * 自动消失时长 ( ms )
     */
    override fun autoDismissDelay(): Long {
        return mAutoDismissDelay
    }

    open fun setAutoDismissDelay(autoDismissDelay: Long): PopTipItem {
        mAutoDismissDelay = autoDismissDelay
        return this
    }

    /**
     * 是否常驻显示
     */
    override fun noAutoDismiss(): Boolean {
        return mNoAutoDismiss
    }

    open fun setNoAutoDismiss(noAutoDismiss: Boolean): PopTipItem {
        mNoAutoDismiss = noAutoDismiss
        return this
    }

    /**
     * 实现模式
     */
    override fun dialogImplMode(): Int {
        return mDialogImplMode
    }

    open fun setDialogImplMode(dialogImplMode: Int): PopTipItem {
        mDialogImplMode = dialogImplMode
        return this
    }

    /**
     * 背景色
     */
    override fun backgroundColor(): Int? {
        return mBackgroundColor
    }

    open fun setBackgroundColor(backgroundColor: Int?): PopTipItem {
        mBackgroundColor = backgroundColor
        return this
    }

    /**
     * 圆角 ( px )
     */
    override fun radius(): Float {
        return mRadius
    }

    open fun setRadius(radius: Float): PopTipItem {
        mRadius = radius
        return this
    }

    /**
     * 自定义布局
     */
    override fun customView(): Any? {
        return mCustomView
    }

    open fun setCustomView(customView: OnBindView<PopTip>?): PopTipItem {
        mCustomView = customView
        return this
    }

    /**
     * 设置自定义布局
     * @param layoutId 布局资源 id
     * @param onBind 布局绑定回调
     * @return [PopTipItem]
     */
    open fun setCustomView(
        layoutId: Int,
        onBind: (dialog: PopTip, view: View?) -> Unit
    ): PopTipItem {
        mCustomView = object : OnBindView<PopTip>(layoutId) {
            override fun onBind(
                dialog: PopTip,
                view: View?
            ) {
                onBind(dialog, view)
            }
        }
        return this
    }

    /**
     * 显示生命周期监听
     */
    override fun lifecycleListener(): IPopTipEngine.OnPopTipLifecycleListener? {
        return mLifecycleListener
    }

    open fun setLifecycleListener(
        listener: IPopTipEngine.OnPopTipLifecycleListener?
    ): PopTipItem {
        mLifecycleListener = listener
        return this
    }
}