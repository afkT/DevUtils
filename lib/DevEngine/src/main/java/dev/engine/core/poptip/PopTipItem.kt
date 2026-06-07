package dev.engine.core.poptip

import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.DialogXAnimInterface
import com.kongzue.dialogx.interfaces.DialogXStyle
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.util.TextInfo
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

    // 按钮文本资源 id
    private var mButtonTextResId = PopTipConst.UNSET

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

    // 主题样式对象
    private var mStyle: Any? = null

    // 明暗主题
    private var mTheme = PopTipConst.THEME_DEFAULT

    // 提示文本样式对象
    private var mMessageTextInfo: Any? = null

    // 按钮文本样式对象
    private var mButtonTextInfo: Any? = null

    // 状态预置图标
    private var mIconState = PopTipConst.ICON_DEFAULT

    // PopTip 自身点击事件
    private var mOnPopTipClickListener: IPopTipEngine.OnButtonClickListener? = null

    // 背景色资源 id ( ColorRes )
    private var mBackgroundColorRes = PopTipConst.UNSET

    // 图标是否随明暗模式自动染色
    private var mAutoTintIcon: Boolean? = null

    // 图标是否染色
    private var mTintIcon: Boolean? = null

    // 进入动画时长 ( ms )
    private var mEnterAnimDuration = PopTipConst.UNSET_LONG

    // 退出动画时长 ( ms )
    private var mExitAnimDuration = PopTipConst.UNSET_LONG

    // 进入动画资源 id
    private var mEnterAnimResId = PopTipConst.UNSET

    // 退出动画资源 id
    private var mExitAnimResId = PopTipConst.UNSET

    // 自定义动画实现对象
    private var mDialogXAnimImpl: Any? = null

    // 是否启用振动反馈
    private var mHapticFeedbackEnabled: Boolean? = null

    // 左外边距 ( px )
    private var mMarginLeft = PopTipConst.UNSET

    // 上外边距 ( px )
    private var mMarginTop = PopTipConst.UNSET

    // 右外边距 ( px )
    private var mMarginRight = PopTipConst.UNSET

    // 下外边距 ( px )
    private var mMarginBottom = PopTipConst.UNSET

    // 根布局内边距 ( px )
    private var mRootPadding = PopTipConst.UNSET

    // 临时储物柜数据
    private var mData: MutableMap<String, Any?>? = null

    // 显示层级
    private var mThisOrderIndex = PopTipConst.UNSET

    // 绑定关闭的 LifecycleOwner 对象
    private var mLifecycleOwner: Any? = null

    // 自定义弹窗布局资源 id
    private var mCustomDialogLayoutResId = PopTipConst.UNSET

    // 自定义弹窗布局是否亮色主题
    private var mCustomDialogLayoutLightTheme: Boolean? = null

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
     * 按钮文本资源 id
     */
    override fun buttonTextResId(): Int {
        return mButtonTextResId
    }

    open fun setButtonTextResId(buttonTextResId: Int): PopTipItem {
        mButtonTextResId = buttonTextResId
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

    /**
     * 主题样式对象
     */
    override fun style(): Any? {
        return mStyle
    }

    open fun setStyle(style: DialogXStyle?): PopTipItem {
        mStyle = style
        return this
    }

    /**
     * 设置主题样式对象 ( Object 重载 )
     * @param style 主题样式对象
     * @return [PopTipItem]
     */
    open fun setStyle(style: Any?): PopTipItem {
        mStyle = style
        return this
    }

    /**
     * 明暗主题
     */
    override fun theme(): Int {
        return mTheme
    }

    open fun setTheme(theme: Int): PopTipItem {
        mTheme = theme
        return this
    }

    /**
     * 提示文本样式对象
     */
    override fun messageTextInfo(): Any? {
        return mMessageTextInfo
    }

    open fun setMessageTextInfo(messageTextInfo: TextInfo?): PopTipItem {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 设置提示文本样式对象 ( Object 重载 )
     * @param messageTextInfo 提示文本样式对象
     * @return [PopTipItem]
     */
    open fun setMessageTextInfo(messageTextInfo: Any?): PopTipItem {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 按钮文本样式对象
     */
    override fun buttonTextInfo(): Any? {
        return mButtonTextInfo
    }

    open fun setButtonTextInfo(buttonTextInfo: TextInfo?): PopTipItem {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 设置按钮文本样式对象 ( Object 重载 )
     * @param buttonTextInfo 按钮文本样式对象
     * @return [PopTipItem]
     */
    open fun setButtonTextInfo(buttonTextInfo: Any?): PopTipItem {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 状态预置图标
     */
    override fun iconState(): Int {
        return mIconState
    }

    open fun setIconState(iconState: Int): PopTipItem {
        mIconState = iconState
        return this
    }

    /**
     * PopTip 自身点击事件
     */
    override fun onPopTipClickListener(): IPopTipEngine.OnButtonClickListener? {
        return mOnPopTipClickListener
    }

    open fun setOnPopTipClickListener(
        listener: IPopTipEngine.OnButtonClickListener?
    ): PopTipItem {
        mOnPopTipClickListener = listener
        return this
    }

    /**
     * 背景色资源 id ( ColorRes )
     */
    override fun backgroundColorRes(): Int {
        return mBackgroundColorRes
    }

    open fun setBackgroundColorRes(backgroundColorRes: Int): PopTipItem {
        mBackgroundColorRes = backgroundColorRes
        return this
    }

    /**
     * 图标是否随明暗模式自动染色
     */
    override fun autoTintIcon(): Boolean? {
        return mAutoTintIcon
    }

    open fun setAutoTintIcon(autoTintIcon: Boolean?): PopTipItem {
        mAutoTintIcon = autoTintIcon
        return this
    }

    /**
     * 图标是否染色
     */
    override fun tintIcon(): Boolean? {
        return mTintIcon
    }

    open fun setTintIcon(tintIcon: Boolean?): PopTipItem {
        mTintIcon = tintIcon
        return this
    }

    /**
     * 进入动画时长 ( ms )
     */
    override fun enterAnimDuration(): Long {
        return mEnterAnimDuration
    }

    open fun setEnterAnimDuration(enterAnimDuration: Long): PopTipItem {
        mEnterAnimDuration = enterAnimDuration
        return this
    }

    /**
     * 退出动画时长 ( ms )
     */
    override fun exitAnimDuration(): Long {
        return mExitAnimDuration
    }

    open fun setExitAnimDuration(exitAnimDuration: Long): PopTipItem {
        mExitAnimDuration = exitAnimDuration
        return this
    }

    /**
     * 进入动画资源 id
     */
    override fun enterAnimResId(): Int {
        return mEnterAnimResId
    }

    open fun setEnterAnimResId(enterAnimResId: Int): PopTipItem {
        mEnterAnimResId = enterAnimResId
        return this
    }

    /**
     * 退出动画资源 id
     */
    override fun exitAnimResId(): Int {
        return mExitAnimResId
    }

    open fun setExitAnimResId(exitAnimResId: Int): PopTipItem {
        mExitAnimResId = exitAnimResId
        return this
    }

    /**
     * 设置进出场动画资源
     * @param enterResId 进入动画资源 id
     * @param exitResId 退出动画资源 id
     * @return [PopTipItem]
     */
    open fun setAnimResId(
        enterResId: Int,
        exitResId: Int
    ): PopTipItem {
        mEnterAnimResId = enterResId
        mExitAnimResId = exitResId
        return this
    }

    /**
     * 自定义动画实现对象
     */
    override fun dialogXAnimImpl(): Any? {
        return mDialogXAnimImpl
    }

    open fun setDialogXAnimImpl(dialogXAnimImpl: DialogXAnimInterface<PopTip>?): PopTipItem {
        mDialogXAnimImpl = dialogXAnimImpl
        return this
    }

    /**
     * 设置自定义动画实现对象 ( Object 重载 )
     * @param dialogXAnimImpl 自定义动画实现对象
     * @return [PopTipItem]
     */
    open fun setDialogXAnimImpl(dialogXAnimImpl: Any?): PopTipItem {
        mDialogXAnimImpl = dialogXAnimImpl
        return this
    }

    /**
     * 是否启用振动反馈
     */
    override fun hapticFeedbackEnabled(): Boolean? {
        return mHapticFeedbackEnabled
    }

    open fun setHapticFeedbackEnabled(hapticFeedbackEnabled: Boolean?): PopTipItem {
        mHapticFeedbackEnabled = hapticFeedbackEnabled
        return this
    }

    /**
     * 左外边距 ( px )
     */
    override fun marginLeft(): Int {
        return mMarginLeft
    }

    open fun setMarginLeft(marginLeft: Int): PopTipItem {
        mMarginLeft = marginLeft
        return this
    }

    /**
     * 上外边距 ( px )
     */
    override fun marginTop(): Int {
        return mMarginTop
    }

    open fun setMarginTop(marginTop: Int): PopTipItem {
        mMarginTop = marginTop
        return this
    }

    /**
     * 右外边距 ( px )
     */
    override fun marginRight(): Int {
        return mMarginRight
    }

    open fun setMarginRight(marginRight: Int): PopTipItem {
        mMarginRight = marginRight
        return this
    }

    /**
     * 下外边距 ( px )
     */
    override fun marginBottom(): Int {
        return mMarginBottom
    }

    open fun setMarginBottom(marginBottom: Int): PopTipItem {
        mMarginBottom = marginBottom
        return this
    }

    /**
     * 设置外边距
     * @param left 左边距
     * @param top 上边距
     * @param right 右边距
     * @param bottom 下边距
     * @return [PopTipItem]
     */
    open fun setMargin(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): PopTipItem {
        mMarginLeft = left
        mMarginTop = top
        mMarginRight = right
        mMarginBottom = bottom
        return this
    }

    /**
     * 根布局内边距 ( px )
     */
    override fun rootPadding(): Int {
        return mRootPadding
    }

    open fun setRootPadding(rootPadding: Int): PopTipItem {
        mRootPadding = rootPadding
        return this
    }

    /**
     * 临时储物柜数据
     */
    override fun data(): Map<String, Any?>? {
        return mData
    }

    open fun setData(data: Map<String, Any?>?): PopTipItem {
        mData = data?.let { LinkedHashMap(it) }
        return this
    }

    /**
     * 追加临时储物柜数据
     * @param key 数据 key
     * @param obj 数据值
     * @return [PopTipItem]
     */
    open fun putData(
        key: String,
        obj: Any?
    ): PopTipItem {
        val map = mData ?: LinkedHashMap<String, Any?>().also { mData = it }
        map[key] = obj
        return this
    }

    /**
     * 显示层级
     */
    override fun thisOrderIndex(): Int {
        return mThisOrderIndex
    }

    open fun setThisOrderIndex(thisOrderIndex: Int): PopTipItem {
        mThisOrderIndex = thisOrderIndex
        return this
    }

    /**
     * 绑定关闭的 LifecycleOwner 对象
     */
    override fun lifecycleOwner(): Any? {
        return mLifecycleOwner
    }

    open fun setLifecycleOwner(lifecycleOwner: LifecycleOwner?): PopTipItem {
        mLifecycleOwner = lifecycleOwner
        return this
    }

    /**
     * 设置绑定关闭的 LifecycleOwner 对象 ( Object 重载 )
     * @param lifecycleOwner LifecycleOwner 对象
     * @return [PopTipItem]
     */
    open fun setLifecycleOwner(lifecycleOwner: Any?): PopTipItem {
        mLifecycleOwner = lifecycleOwner
        return this
    }

    /**
     * 自定义弹窗布局资源 id
     */
    override fun customDialogLayoutResId(): Int {
        return mCustomDialogLayoutResId
    }

    open fun setCustomDialogLayoutResId(customDialogLayoutResId: Int): PopTipItem {
        mCustomDialogLayoutResId = customDialogLayoutResId
        mCustomDialogLayoutLightTheme = null
        return this
    }

    /**
     * 设置自定义弹窗布局资源 id
     * @param customDialogLayoutResId 自定义弹窗布局资源 id
     * @param isLightTheme 是否亮色主题布局
     * @return [PopTipItem]
     */
    open fun setCustomDialogLayoutResId(
        customDialogLayoutResId: Int,
        isLightTheme: Boolean
    ): PopTipItem {
        mCustomDialogLayoutResId = customDialogLayoutResId
        mCustomDialogLayoutLightTheme = isLightTheme
        return this
    }

    /**
     * 自定义弹窗布局是否亮色主题
     */
    override fun customDialogLayoutLightTheme(): Boolean? {
        return mCustomDialogLayoutLightTheme
    }
}