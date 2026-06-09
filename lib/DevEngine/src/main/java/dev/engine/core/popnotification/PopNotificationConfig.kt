package dev.engine.core.popnotification

import com.kongzue.dialogx.dialogs.PopNotification
import com.kongzue.dialogx.interfaces.DialogXStyle
import com.kongzue.dialogx.interfaces.PopMoveDisplacementInterceptor
import com.kongzue.dialogx.util.TextInfo
import dev.engine.popnotification.IPopNotificationEngine

/**
 * detail: PopNotification Config
 * @author Ttt
 */
open class PopNotificationConfig private constructor(
    config: PopNotificationConfig?
) : IPopNotificationEngine.EngineConfig {

    // 是否同时仅显示一个 PopNotification
    private var mOnlyOne: Boolean? = null

    // 默认自动消失时长 ( ms )
    private var mAutoDismissDelay = PopNotificationConst.UNSET_LONG

    // 默认对齐方式
    private var mAlign = PopNotificationConst.ALIGN_DEFAULT

    // ===========
    // = DialogX =
    // ===========

    // 默认实现模式 ( 映射全局 DialogX implIMPLMode )
    private var mDialogImplMode = PopNotificationConst.IMPL_MODE_DEFAULT

    // 默认圆角 ( px, 映射全局 DialogX defaultPopNotificationBackgroundRadius )
    private var mRadiusPx = PopNotificationConst.UNSET

    // 默认主题样式对象 ( 映射全局 DialogX globalStyle )
    private var mStyle: Any? = null

    // 默认明暗主题 ( 映射全局 DialogX globalTheme )
    private var mTheme = PopNotificationConst.THEME_DEFAULT

    // 默认标题文本样式对象 ( 映射全局 DialogX titleTextInfo )
    private var mTitleTextInfo: Any? = null

    // 默认提示文本样式对象 ( 映射全局 DialogX messageTextInfo )
    private var mMessageTextInfo: Any? = null

    // 默认按钮文本样式对象 ( 映射全局 DialogX buttonTextInfo )
    private var mButtonTextInfo: Any? = null

    // 默认是否启用振动反馈 ( 映射全局 DialogX useHaptic )
    private var mUseHaptic: Boolean? = null

    // 默认进入动画时长 ( ms, 映射全局 DialogX enterAnimDuration )
    private var mEnterAnimDuration = PopNotificationConst.UNSET_LONG

    // 默认退出动画时长 ( ms, 映射全局 DialogX exitAnimDuration )
    private var mExitAnimDuration = PopNotificationConst.UNSET_LONG

    // 默认背景色 ( ColorInt, 映射全局 DialogX backgroundColor )
    private var mBackgroundColor: Int? = null

    // ==================
    // = PopNotification =
    // ==================

    // 最大同时显示数量 ( 映射全局 PopNotification maxShowCount )
    private var mMaxShowCount = PopNotificationConst.UNSET

    // 覆盖进入动画时长 ( ms, 映射全局 PopNotification overrideEnterDuration )
    private var mOverrideEnterDuration = PopNotificationConst.UNSET_LONG

    // 覆盖退出动画时长 ( ms, 映射全局 PopNotification overrideExitDuration )
    private var mOverrideExitDuration = PopNotificationConst.UNSET_LONG

    // 覆盖进入动画资源 id ( 映射全局 PopNotification overrideEnterAnimRes )
    private var mOverrideEnterAnimRes = PopNotificationConst.UNSET

    // 覆盖退出动画资源 id ( 映射全局 PopNotification overrideExitAnimRes )
    private var mOverrideExitAnimRes = PopNotificationConst.UNSET

    // 多 PopNotification 位移拦截器对象 ( 映射全局 PopNotification moveDisplacementInterceptor )
    private var mMoveDisplacementInterceptor: Any? = null

    companion object {

        /**
         * 创建 PopNotification Config
         * @return [PopNotificationConfig]
         */
        fun create(): PopNotificationConfig {
            return PopNotificationConfig(null)
        }

        /**
         * 创建 PopNotification Config
         * @param config 待克隆的 Config
         * @return [PopNotificationConfig]
         */
        fun create(config: PopNotificationConfig?): PopNotificationConfig {
            return PopNotificationConfig(config)
        }
    }

    init {
        config?.let {
            this.mOnlyOne = it.mOnlyOne
            this.mAutoDismissDelay = it.mAutoDismissDelay
            this.mAlign = it.mAlign
            this.mDialogImplMode = it.mDialogImplMode
            this.mRadiusPx = it.mRadiusPx
            this.mStyle = it.mStyle
            this.mTheme = it.mTheme
            this.mTitleTextInfo = it.mTitleTextInfo
            this.mMessageTextInfo = it.mMessageTextInfo
            this.mButtonTextInfo = it.mButtonTextInfo
            this.mUseHaptic = it.mUseHaptic
            this.mEnterAnimDuration = it.mEnterAnimDuration
            this.mExitAnimDuration = it.mExitAnimDuration
            this.mBackgroundColor = it.mBackgroundColor
            this.mMaxShowCount = it.mMaxShowCount
            this.mOverrideEnterDuration = it.mOverrideEnterDuration
            this.mOverrideExitDuration = it.mOverrideExitDuration
            this.mOverrideEnterAnimRes = it.mOverrideEnterAnimRes
            this.mOverrideExitAnimRes = it.mOverrideExitAnimRes
            this.mMoveDisplacementInterceptor = it.mMoveDisplacementInterceptor
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [PopNotificationConfig]
     */
    open fun clone(): PopNotificationConfig {
        return PopNotificationConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 是否同时仅显示一个 PopNotification
     */
    override fun onlyOne(): Boolean? {
        return mOnlyOne
    }

    open fun setOnlyOne(onlyOne: Boolean?): PopNotificationConfig {
        mOnlyOne = onlyOne
        return this
    }

    /**
     * 默认自动消失时长 ( ms )
     */
    override fun autoDismissDelay(): Long {
        return mAutoDismissDelay
    }

    open fun setAutoDismissDelay(autoDismissDelay: Long): PopNotificationConfig {
        mAutoDismissDelay = autoDismissDelay
        return this
    }

    /**
     * 默认对齐方式
     */
    override fun align(): Int {
        return mAlign
    }

    open fun setAlign(align: Int): PopNotificationConfig {
        mAlign = align
        return this
    }

    // ===========
    // = DialogX =
    // ===========

    /**
     * 默认实现模式 ( 映射全局 DialogX implIMPLMode )
     */
    override fun dialogImplMode(): Int {
        return mDialogImplMode
    }

    open fun setDialogImplMode(dialogImplMode: Int): PopNotificationConfig {
        mDialogImplMode = dialogImplMode
        return this
    }

    /**
     * 默认圆角 ( px, 映射全局 DialogX defaultPopNotificationBackgroundRadius )
     */
    override fun radiusPx(): Int {
        return mRadiusPx
    }

    open fun setRadiusPx(radiusPx: Int): PopNotificationConfig {
        mRadiusPx = radiusPx
        return this
    }

    /**
     * 默认主题样式对象 ( 映射全局 DialogX globalStyle )
     */
    override fun style(): Any? {
        return mStyle
    }

    open fun setStyle(style: DialogXStyle?): PopNotificationConfig {
        mStyle = style
        return this
    }

    /**
     * 设置默认主题样式对象 ( Object 重载 )
     * @param style 主题样式对象
     * @return [PopNotificationConfig]
     */
    open fun setStyle(style: Any?): PopNotificationConfig {
        mStyle = style
        return this
    }

    /**
     * 默认明暗主题 ( 映射全局 DialogX globalTheme )
     */
    override fun theme(): Int {
        return mTheme
    }

    open fun setTheme(theme: Int): PopNotificationConfig {
        mTheme = theme
        return this
    }

    /**
     * 默认标题文本样式对象 ( 映射全局 DialogX titleTextInfo )
     */
    override fun titleTextInfo(): Any? {
        return mTitleTextInfo
    }

    open fun setTitleTextInfo(titleTextInfo: TextInfo?): PopNotificationConfig {
        mTitleTextInfo = titleTextInfo
        return this
    }

    /**
     * 设置默认标题文本样式对象 ( Object 重载 )
     * @param titleTextInfo 标题文本样式对象
     * @return [PopNotificationConfig]
     */
    open fun setTitleTextInfo(titleTextInfo: Any?): PopNotificationConfig {
        mTitleTextInfo = titleTextInfo
        return this
    }

    /**
     * 默认提示文本样式对象 ( 映射全局 DialogX messageTextInfo )
     */
    override fun messageTextInfo(): Any? {
        return mMessageTextInfo
    }

    open fun setMessageTextInfo(messageTextInfo: TextInfo?): PopNotificationConfig {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 设置默认提示文本样式对象 ( Object 重载 )
     * @param messageTextInfo 提示文本样式对象
     * @return [PopNotificationConfig]
     */
    open fun setMessageTextInfo(messageTextInfo: Any?): PopNotificationConfig {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 默认按钮文本样式对象 ( 映射全局 DialogX buttonTextInfo )
     */
    override fun buttonTextInfo(): Any? {
        return mButtonTextInfo
    }

    open fun setButtonTextInfo(buttonTextInfo: TextInfo?): PopNotificationConfig {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 设置默认按钮文本样式对象 ( Object 重载 )
     * @param buttonTextInfo 按钮文本样式对象
     * @return [PopNotificationConfig]
     */
    open fun setButtonTextInfo(buttonTextInfo: Any?): PopNotificationConfig {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 默认是否启用振动反馈 ( 映射全局 DialogX useHaptic )
     */
    override fun useHaptic(): Boolean? {
        return mUseHaptic
    }

    open fun setUseHaptic(useHaptic: Boolean?): PopNotificationConfig {
        mUseHaptic = useHaptic
        return this
    }

    /**
     * 默认进入动画时长 ( ms, 映射全局 DialogX enterAnimDuration )
     */
    override fun enterAnimDuration(): Long {
        return mEnterAnimDuration
    }

    open fun setEnterAnimDuration(enterAnimDuration: Long): PopNotificationConfig {
        mEnterAnimDuration = enterAnimDuration
        return this
    }

    /**
     * 默认退出动画时长 ( ms, 映射全局 DialogX exitAnimDuration )
     */
    override fun exitAnimDuration(): Long {
        return mExitAnimDuration
    }

    open fun setExitAnimDuration(exitAnimDuration: Long): PopNotificationConfig {
        mExitAnimDuration = exitAnimDuration
        return this
    }

    /**
     * 默认背景色 ( ColorInt, 映射全局 DialogX backgroundColor )
     */
    override fun backgroundColor(): Int? {
        return mBackgroundColor
    }

    open fun setBackgroundColor(backgroundColor: Int?): PopNotificationConfig {
        mBackgroundColor = backgroundColor
        return this
    }

    // ==================
    // = PopNotification =
    // ==================

    /**
     * 最大同时显示数量 ( 映射全局 PopNotification maxShowCount )
     */
    override fun maxShowCount(): Int {
        return mMaxShowCount
    }

    open fun setMaxShowCount(maxShowCount: Int): PopNotificationConfig {
        mMaxShowCount = maxShowCount
        return this
    }

    /**
     * 覆盖进入动画时长 ( ms, 映射全局 PopNotification overrideEnterDuration )
     */
    override fun overrideEnterDuration(): Long {
        return mOverrideEnterDuration
    }

    open fun setOverrideEnterDuration(overrideEnterDuration: Long): PopNotificationConfig {
        mOverrideEnterDuration = overrideEnterDuration
        return this
    }

    /**
     * 覆盖退出动画时长 ( ms, 映射全局 PopNotification overrideExitDuration )
     */
    override fun overrideExitDuration(): Long {
        return mOverrideExitDuration
    }

    open fun setOverrideExitDuration(overrideExitDuration: Long): PopNotificationConfig {
        mOverrideExitDuration = overrideExitDuration
        return this
    }

    /**
     * 覆盖进入动画资源 id ( 映射全局 PopNotification overrideEnterAnimRes )
     */
    override fun overrideEnterAnimRes(): Int {
        return mOverrideEnterAnimRes
    }

    open fun setOverrideEnterAnimRes(overrideEnterAnimRes: Int): PopNotificationConfig {
        mOverrideEnterAnimRes = overrideEnterAnimRes
        return this
    }

    /**
     * 覆盖退出动画资源 id ( 映射全局 PopNotification overrideExitAnimRes )
     */
    override fun overrideExitAnimRes(): Int {
        return mOverrideExitAnimRes
    }

    open fun setOverrideExitAnimRes(overrideExitAnimRes: Int): PopNotificationConfig {
        mOverrideExitAnimRes = overrideExitAnimRes
        return this
    }

    /**
     * 多 PopNotification 位移拦截器对象 ( 映射全局 PopNotification moveDisplacementInterceptor )
     */
    override fun moveDisplacementInterceptor(): Any? {
        return mMoveDisplacementInterceptor
    }

    open fun setMoveDisplacementInterceptor(
        interceptor: PopMoveDisplacementInterceptor<PopNotification>?
    ): PopNotificationConfig {
        mMoveDisplacementInterceptor = interceptor
        return this
    }

    /**
     * 设置多 PopNotification 位移拦截器对象 ( Object 重载 )
     * @param interceptor 多 PopNotification 位移拦截器对象
     * @return [PopNotificationConfig]
     */
    open fun setMoveDisplacementInterceptor(interceptor: Any?): PopNotificationConfig {
        mMoveDisplacementInterceptor = interceptor
        return this
    }
}