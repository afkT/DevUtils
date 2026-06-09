package dev.engine.core.popnotification

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.DialogXAnimInterface
import com.kongzue.dialogx.interfaces.DialogXStyle
import com.kongzue.dialogx.interfaces.OnBindView
import com.kongzue.dialogx.util.TextInfo
import dev.engine.popnotification.IPopNotificationEngine

/**
 * detail: PopNotification Item Params
 * @author Ttt
 */
open class PopNotificationItem private constructor(
    title: CharSequence?
) : IPopNotificationEngine.EngineItem {

    // 标题文本
    private var mTitle: CharSequence? = title

    // 标题文本资源 id
    private var mTitleResId = PopNotificationConst.UNSET

    // 提示文本
    private var mMessage: CharSequence? = null

    // 提示文本资源 id
    private var mMessageResId = PopNotificationConst.UNSET

    // 图标资源 id
    private var mIconResId = PopNotificationConst.UNSET

    // 图标 Bitmap 对象
    private var mIconBitmap: Bitmap? = null

    // 图标 Drawable 对象
    private var mIconDrawable: Drawable? = null

    // 图标尺寸 ( px )
    private var mIconSize = PopNotificationConst.UNSET

    // 按钮文本
    private var mButtonText: CharSequence? = null

    // 按钮文本资源 id
    private var mButtonTextResId = PopNotificationConst.UNSET

    // 按钮点击事件
    private var mOnButtonClickListener: IPopNotificationEngine.OnButtonClickListener? = null

    // 对齐方式
    private var mAlign = PopNotificationConst.ALIGN_DEFAULT

    // 自动消失时长 ( ms )
    private var mAutoDismissDelay = PopNotificationConst.UNSET_LONG

    // 是否常驻显示
    private var mNoAutoDismiss = false

    // 实现模式
    private var mDialogImplMode = PopNotificationConst.IMPL_MODE_DEFAULT

    // 背景色
    private var mBackgroundColor: Int? = null

    // 圆角 ( px )
    private var mRadius = PopNotificationConst.UNSET_FLOAT

    // 自定义布局
    private var mCustomView: OnBindView<PopNotification>? = null

    // 显示生命周期监听
    private var mLifecycleListener: IPopNotificationEngine.OnPopNotificationLifecycleListener? =
        null

    // 主题样式对象
    private var mStyle: Any? = null

    // 明暗主题
    private var mTheme = PopNotificationConst.THEME_DEFAULT

    // 标题文本样式对象
    private var mTitleTextInfo: Any? = null

    // 提示文本样式对象
    private var mMessageTextInfo: Any? = null

    // 按钮文本样式对象
    private var mButtonTextInfo: Any? = null

    // 状态预置图标
    private var mIconState = PopNotificationConst.ICON_DEFAULT

    // PopNotification 自身点击事件
    private var mOnPopNotificationClickListener: IPopNotificationEngine.OnButtonClickListener? =
        null

    // 背景色资源 id ( ColorRes )
    private var mBackgroundColorRes = PopNotificationConst.UNSET

    // 图标是否随明暗模式自动染色
    private var mAutoTintIcon: Boolean? = null

    // 图标是否染色
    private var mTintIcon: Boolean? = null

    // 是否支持滑动关闭
    private var mSlideToClose: Boolean? = null

    // 进入动画时长 ( ms )
    private var mEnterAnimDuration = PopNotificationConst.UNSET_LONG

    // 退出动画时长 ( ms )
    private var mExitAnimDuration = PopNotificationConst.UNSET_LONG

    // 进入动画资源 id
    private var mEnterAnimResId = PopNotificationConst.UNSET

    // 退出动画资源 id
    private var mExitAnimResId = PopNotificationConst.UNSET

    // 自定义动画实现对象
    private var mDialogXAnimImpl: Any? = null

    // 是否启用振动反馈
    private var mHapticFeedbackEnabled: Boolean? = null

    // 左外边距 ( px )
    private var mMarginLeft = PopNotificationConst.UNSET

    // 上外边距 ( px )
    private var mMarginTop = PopNotificationConst.UNSET

    // 右外边距 ( px )
    private var mMarginRight = PopNotificationConst.UNSET

    // 下外边距 ( px )
    private var mMarginBottom = PopNotificationConst.UNSET

    // 根布局内边距 ( px )
    private var mRootPadding = PopNotificationConst.UNSET

    // 临时存储数据
    private var mData: MutableMap<String, Any?>? = null

    // 显示层级
    private var mThisOrderIndex = PopNotificationConst.UNSET

    // 绑定关闭的 LifecycleOwner 对象
    private var mLifecycleOwner: Any? = null

    // 自定义弹窗布局资源 id
    private var mCustomDialogLayoutResId = PopNotificationConst.UNSET

    // 自定义弹窗布局是否亮色主题
    private var mCustomDialogLayoutLightTheme: Boolean? = null

    companion object {

        /**
         * 创建 PopNotification Item
         * @return [PopNotificationItem]
         */
        fun create(): PopNotificationItem {
            return PopNotificationItem(null)
        }

        /**
         * 创建 PopNotification Item
         * @param title 标题文本
         * @return [PopNotificationItem]
         */
        fun create(title: CharSequence?): PopNotificationItem {
            return PopNotificationItem(title)
        }
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 标题文本
     */
    override fun title(): CharSequence? {
        return mTitle
    }

    open fun setTitle(title: CharSequence?): PopNotificationItem {
        mTitle = title
        return this
    }

    /**
     * 标题文本资源 id
     */
    override fun titleResId(): Int {
        return mTitleResId
    }

    open fun setTitleResId(titleResId: Int): PopNotificationItem {
        mTitleResId = titleResId
        return this
    }

    /**
     * 提示文本
     */
    override fun message(): CharSequence? {
        return mMessage
    }

    open fun setMessage(message: CharSequence?): PopNotificationItem {
        mMessage = message
        return this
    }

    /**
     * 提示文本资源 id
     */
    override fun messageResId(): Int {
        return mMessageResId
    }

    open fun setMessageResId(messageResId: Int): PopNotificationItem {
        mMessageResId = messageResId
        return this
    }

    /**
     * 图标资源 id
     */
    override fun iconResId(): Int {
        return mIconResId
    }

    open fun setIconResId(iconResId: Int): PopNotificationItem {
        mIconResId = iconResId
        return this
    }

    /**
     * 图标 Bitmap 对象
     */
    override fun iconBitmap(): Bitmap? {
        return mIconBitmap
    }

    open fun setIcon(bitmap: Bitmap?): PopNotificationItem {
        mIconBitmap = bitmap
        return this
    }

    /**
     * 图标 Drawable 对象
     */
    override fun iconDrawable(): Drawable? {
        return mIconDrawable
    }

    open fun setIcon(drawable: Drawable?): PopNotificationItem {
        mIconDrawable = drawable
        return this
    }

    /**
     * 图标尺寸 ( px )
     */
    override fun iconSize(): Int {
        return mIconSize
    }

    open fun setIconSize(iconSize: Int): PopNotificationItem {
        mIconSize = iconSize
        return this
    }

    /**
     * 按钮文本
     */
    override fun buttonText(): CharSequence? {
        return mButtonText
    }

    open fun setButtonText(buttonText: CharSequence?): PopNotificationItem {
        mButtonText = buttonText
        return this
    }

    /**
     * 按钮文本资源 id
     */
    override fun buttonTextResId(): Int {
        return mButtonTextResId
    }

    open fun setButtonTextResId(buttonTextResId: Int): PopNotificationItem {
        mButtonTextResId = buttonTextResId
        return this
    }

    /**
     * 按钮点击事件
     */
    override fun onButtonClickListener(): IPopNotificationEngine.OnButtonClickListener? {
        return mOnButtonClickListener
    }

    open fun setOnButtonClickListener(
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): PopNotificationItem {
        mOnButtonClickListener = listener
        return this
    }

    /**
     * 对齐方式
     */
    override fun align(): Int {
        return mAlign
    }

    open fun setAlign(align: Int): PopNotificationItem {
        mAlign = align
        return this
    }

    /**
     * 自动消失时长 ( ms )
     */
    override fun autoDismissDelay(): Long {
        return mAutoDismissDelay
    }

    open fun setAutoDismissDelay(autoDismissDelay: Long): PopNotificationItem {
        mAutoDismissDelay = autoDismissDelay
        return this
    }

    /**
     * 是否常驻显示
     */
    override fun noAutoDismiss(): Boolean {
        return mNoAutoDismiss
    }

    open fun setNoAutoDismiss(noAutoDismiss: Boolean): PopNotificationItem {
        mNoAutoDismiss = noAutoDismiss
        return this
    }

    /**
     * 实现模式
     */
    override fun dialogImplMode(): Int {
        return mDialogImplMode
    }

    open fun setDialogImplMode(dialogImplMode: Int): PopNotificationItem {
        mDialogImplMode = dialogImplMode
        return this
    }

    /**
     * 背景色
     */
    override fun backgroundColor(): Int? {
        return mBackgroundColor
    }

    open fun setBackgroundColor(backgroundColor: Int?): PopNotificationItem {
        mBackgroundColor = backgroundColor
        return this
    }

    /**
     * 圆角 ( px )
     */
    override fun radius(): Float {
        return mRadius
    }

    open fun setRadius(radius: Float): PopNotificationItem {
        mRadius = radius
        return this
    }

    /**
     * 自定义布局
     */
    override fun customView(): Any? {
        return mCustomView
    }

    open fun setCustomView(customView: OnBindView<PopNotification>?): PopNotificationItem {
        mCustomView = customView
        return this
    }

    /**
     * 设置自定义布局
     * @param layoutId 布局资源 id
     * @param onBind 布局绑定回调
     * @return [PopNotificationItem]
     */
    open fun setCustomView(
        layoutId: Int,
        onBind: (dialog: PopNotification, view: View?) -> Unit
    ): PopNotificationItem {
        mCustomView = object : OnBindView<PopNotification>(layoutId) {
            override fun onBind(
                dialog: PopNotification,
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
    override fun lifecycleListener(): IPopNotificationEngine.OnPopNotificationLifecycleListener? {
        return mLifecycleListener
    }

    open fun setLifecycleListener(
        listener: IPopNotificationEngine.OnPopNotificationLifecycleListener?
    ): PopNotificationItem {
        mLifecycleListener = listener
        return this
    }

    /**
     * 主题样式对象
     */
    override fun style(): Any? {
        return mStyle
    }

    open fun setStyle(style: DialogXStyle?): PopNotificationItem {
        mStyle = style
        return this
    }

    /**
     * 设置主题样式对象 ( Object 重载 )
     * @param style 主题样式对象
     * @return [PopNotificationItem]
     */
    open fun setStyle(style: Any?): PopNotificationItem {
        mStyle = style
        return this
    }

    /**
     * 明暗主题
     */
    override fun theme(): Int {
        return mTheme
    }

    open fun setTheme(theme: Int): PopNotificationItem {
        mTheme = theme
        return this
    }

    /**
     * 标题文本样式对象
     */
    override fun titleTextInfo(): Any? {
        return mTitleTextInfo
    }

    open fun setTitleTextInfo(titleTextInfo: TextInfo?): PopNotificationItem {
        mTitleTextInfo = titleTextInfo
        return this
    }

    /**
     * 设置标题文本样式对象 ( Object 重载 )
     * @param titleTextInfo 标题文本样式对象
     * @return [PopNotificationItem]
     */
    open fun setTitleTextInfo(titleTextInfo: Any?): PopNotificationItem {
        mTitleTextInfo = titleTextInfo
        return this
    }

    /**
     * 提示文本样式对象
     */
    override fun messageTextInfo(): Any? {
        return mMessageTextInfo
    }

    open fun setMessageTextInfo(messageTextInfo: TextInfo?): PopNotificationItem {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 设置提示文本样式对象 ( Object 重载 )
     * @param messageTextInfo 提示文本样式对象
     * @return [PopNotificationItem]
     */
    open fun setMessageTextInfo(messageTextInfo: Any?): PopNotificationItem {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 按钮文本样式对象
     */
    override fun buttonTextInfo(): Any? {
        return mButtonTextInfo
    }

    open fun setButtonTextInfo(buttonTextInfo: TextInfo?): PopNotificationItem {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 设置按钮文本样式对象 ( Object 重载 )
     * @param buttonTextInfo 按钮文本样式对象
     * @return [PopNotificationItem]
     */
    open fun setButtonTextInfo(buttonTextInfo: Any?): PopNotificationItem {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 状态预置图标
     */
    override fun iconState(): Int {
        return mIconState
    }

    open fun setIconState(iconState: Int): PopNotificationItem {
        mIconState = iconState
        return this
    }

    /**
     * PopNotification 自身点击事件
     */
    override fun onPopNotificationClickListener(): IPopNotificationEngine.OnButtonClickListener? {
        return mOnPopNotificationClickListener
    }

    open fun setOnPopNotificationClickListener(
        listener: IPopNotificationEngine.OnButtonClickListener?
    ): PopNotificationItem {
        mOnPopNotificationClickListener = listener
        return this
    }

    /**
     * 背景色资源 id ( ColorRes )
     */
    override fun backgroundColorRes(): Int {
        return mBackgroundColorRes
    }

    open fun setBackgroundColorRes(backgroundColorRes: Int): PopNotificationItem {
        mBackgroundColorRes = backgroundColorRes
        return this
    }

    /**
     * 图标是否随明暗模式自动染色
     */
    override fun autoTintIcon(): Boolean? {
        return mAutoTintIcon
    }

    open fun setAutoTintIcon(autoTintIcon: Boolean?): PopNotificationItem {
        mAutoTintIcon = autoTintIcon
        return this
    }

    /**
     * 图标是否染色
     */
    override fun tintIcon(): Boolean? {
        return mTintIcon
    }

    open fun setTintIcon(tintIcon: Boolean?): PopNotificationItem {
        mTintIcon = tintIcon
        return this
    }

    /**
     * 是否支持滑动关闭
     */
    override fun slideToClose(): Boolean? {
        return mSlideToClose
    }

    open fun setSlideToClose(slideToClose: Boolean?): PopNotificationItem {
        mSlideToClose = slideToClose
        return this
    }

    /**
     * 进入动画时长 ( ms )
     */
    override fun enterAnimDuration(): Long {
        return mEnterAnimDuration
    }

    open fun setEnterAnimDuration(enterAnimDuration: Long): PopNotificationItem {
        mEnterAnimDuration = enterAnimDuration
        return this
    }

    /**
     * 退出动画时长 ( ms )
     */
    override fun exitAnimDuration(): Long {
        return mExitAnimDuration
    }

    open fun setExitAnimDuration(exitAnimDuration: Long): PopNotificationItem {
        mExitAnimDuration = exitAnimDuration
        return this
    }

    /**
     * 进入动画资源 id
     */
    override fun enterAnimResId(): Int {
        return mEnterAnimResId
    }

    open fun setEnterAnimResId(enterAnimResId: Int): PopNotificationItem {
        mEnterAnimResId = enterAnimResId
        return this
    }

    /**
     * 退出动画资源 id
     */
    override fun exitAnimResId(): Int {
        return mExitAnimResId
    }

    open fun setExitAnimResId(exitAnimResId: Int): PopNotificationItem {
        mExitAnimResId = exitAnimResId
        return this
    }

    /**
     * 设置进出场动画资源
     * @param enterResId 进入动画资源 id
     * @param exitResId 退出动画资源 id
     * @return [PopNotificationItem]
     */
    open fun setAnimResId(
        enterResId: Int,
        exitResId: Int
    ): PopNotificationItem {
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

    open fun setDialogXAnimImpl(
        dialogXAnimImpl: DialogXAnimInterface<PopNotification>?
    ): PopNotificationItem {
        mDialogXAnimImpl = dialogXAnimImpl
        return this
    }

    /**
     * 设置自定义动画实现对象 ( Object 重载 )
     * @param dialogXAnimImpl 自定义动画实现对象
     * @return [PopNotificationItem]
     */
    open fun setDialogXAnimImpl(dialogXAnimImpl: Any?): PopNotificationItem {
        mDialogXAnimImpl = dialogXAnimImpl
        return this
    }

    /**
     * 是否启用振动反馈
     */
    override fun hapticFeedbackEnabled(): Boolean? {
        return mHapticFeedbackEnabled
    }

    open fun setHapticFeedbackEnabled(hapticFeedbackEnabled: Boolean?): PopNotificationItem {
        mHapticFeedbackEnabled = hapticFeedbackEnabled
        return this
    }

    /**
     * 左外边距 ( px )
     */
    override fun marginLeft(): Int {
        return mMarginLeft
    }

    open fun setMarginLeft(marginLeft: Int): PopNotificationItem {
        mMarginLeft = marginLeft
        return this
    }

    /**
     * 上外边距 ( px )
     */
    override fun marginTop(): Int {
        return mMarginTop
    }

    open fun setMarginTop(marginTop: Int): PopNotificationItem {
        mMarginTop = marginTop
        return this
    }

    /**
     * 右外边距 ( px )
     */
    override fun marginRight(): Int {
        return mMarginRight
    }

    open fun setMarginRight(marginRight: Int): PopNotificationItem {
        mMarginRight = marginRight
        return this
    }

    /**
     * 下外边距 ( px )
     */
    override fun marginBottom(): Int {
        return mMarginBottom
    }

    open fun setMarginBottom(marginBottom: Int): PopNotificationItem {
        mMarginBottom = marginBottom
        return this
    }

    /**
     * 设置外边距
     * @param left 左边距
     * @param top 上边距
     * @param right 右边距
     * @param bottom 下边距
     * @return [PopNotificationItem]
     */
    open fun setMargin(
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ): PopNotificationItem {
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

    open fun setRootPadding(rootPadding: Int): PopNotificationItem {
        mRootPadding = rootPadding
        return this
    }

    /**
     * 临时存储数据
     */
    override fun data(): Map<String, Any?>? {
        return mData
    }

    open fun setData(data: Map<String, Any?>?): PopNotificationItem {
        mData = data?.let { LinkedHashMap(it) }
        return this
    }

    /**
     * 追加临时存储数据
     * @param key 数据 key
     * @param obj 数据值
     * @return [PopNotificationItem]
     */
    open fun putData(
        key: String,
        obj: Any?
    ): PopNotificationItem {
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

    open fun setThisOrderIndex(thisOrderIndex: Int): PopNotificationItem {
        mThisOrderIndex = thisOrderIndex
        return this
    }

    /**
     * 绑定关闭的 LifecycleOwner 对象
     */
    override fun lifecycleOwner(): Any? {
        return mLifecycleOwner
    }

    open fun setLifecycleOwner(lifecycleOwner: LifecycleOwner?): PopNotificationItem {
        mLifecycleOwner = lifecycleOwner
        return this
    }

    /**
     * 设置绑定关闭的 LifecycleOwner 对象 ( Object 重载 )
     * @param lifecycleOwner LifecycleOwner 对象
     * @return [PopNotificationItem]
     */
    open fun setLifecycleOwner(lifecycleOwner: Any?): PopNotificationItem {
        mLifecycleOwner = lifecycleOwner
        return this
    }

    /**
     * 自定义弹窗布局资源 id
     */
    override fun customDialogLayoutResId(): Int {
        return mCustomDialogLayoutResId
    }

    open fun setCustomDialogLayoutResId(customDialogLayoutResId: Int): PopNotificationItem {
        mCustomDialogLayoutResId = customDialogLayoutResId
        mCustomDialogLayoutLightTheme = null
        return this
    }

    /**
     * 设置自定义弹窗布局资源 id
     * @param customDialogLayoutResId 自定义弹窗布局资源 id
     * @param isLightTheme 是否亮色主题布局
     * @return [PopNotificationItem]
     */
    open fun setCustomDialogLayoutResId(
        customDialogLayoutResId: Int,
        isLightTheme: Boolean
    ): PopNotificationItem {
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