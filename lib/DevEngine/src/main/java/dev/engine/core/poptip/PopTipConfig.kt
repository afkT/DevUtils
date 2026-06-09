package dev.engine.core.poptip

import com.kongzue.dialogx.dialogs.PopTip
import com.kongzue.dialogx.interfaces.DialogXStyle
import com.kongzue.dialogx.interfaces.PopMoveDisplacementInterceptor
import com.kongzue.dialogx.util.TextInfo
import dev.engine.poptip.IPopTipEngine

/**
 * detail: PopTip Config
 * @author Ttt
 */
open class PopTipConfig private constructor(
    config: PopTipConfig?
) : IPopTipEngine.EngineConfig {

    // 是否同时仅显示一个 PopTip
    private var mOnlyOne: Boolean? = null

    // 默认自动消失时长 ( ms )
    private var mAutoDismissDelay = PopTipConst.UNSET_LONG

    // 默认对齐方式
    private var mAlign = PopTipConst.ALIGN_DEFAULT

    // 默认实现模式
    private var mDialogImplMode = PopTipConst.IMPL_MODE_DEFAULT

    // 默认圆角 ( px )
    private var mRadiusPx = PopTipConst.UNSET

    // 默认主题样式对象
    private var mStyle: Any? = null

    // 默认明暗主题
    private var mTheme = PopTipConst.THEME_DEFAULT

    // 默认提示文本样式对象
    private var mMessageTextInfo: Any? = null

    // 默认按钮文本样式对象
    private var mButtonTextInfo: Any? = null

    // 默认是否启用振动反馈
    private var mUseHaptic: Boolean? = null

    // 默认进入动画时长 ( ms )
    private var mEnterAnimDuration = PopTipConst.UNSET_LONG

    // 默认退出动画时长 ( ms )
    private var mExitAnimDuration = PopTipConst.UNSET_LONG

    // 默认背景色 ( ColorInt )
    private var mBackgroundColor: Int? = null

    // 最大同时显示数量
    private var mMaxShowCount = PopTipConst.UNSET

    // 覆盖进入动画时长 ( ms )
    private var mOverrideEnterDuration = PopTipConst.UNSET_LONG

    // 覆盖退出动画时长 ( ms )
    private var mOverrideExitDuration = PopTipConst.UNSET_LONG

    // 覆盖进入动画资源 id
    private var mOverrideEnterAnimRes = PopTipConst.UNSET

    // 覆盖退出动画资源 id
    private var mOverrideExitAnimRes = PopTipConst.UNSET

    // 多 PopTip 位移拦截器对象
    private var mMoveDisplacementInterceptor: Any? = null

    companion object {

        /**
         * 创建 PopTip Config
         * @return [PopTipConfig]
         */
        fun create(): PopTipConfig {
            return PopTipConfig(null)
        }

        /**
         * 创建 PopTip Config
         * @param config 待克隆的 Config
         * @return [PopTipConfig]
         */
        fun create(config: PopTipConfig?): PopTipConfig {
            return PopTipConfig(config)
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
     * @return [PopTipConfig]
     */
    open fun clone(): PopTipConfig {
        return PopTipConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 是否同时仅显示一个 PopTip
     */
    override fun onlyOne(): Boolean? {
        return mOnlyOne
    }

    open fun setOnlyOne(onlyOne: Boolean?): PopTipConfig {
        mOnlyOne = onlyOne
        return this
    }

    /**
     * 默认自动消失时长 ( ms )
     */
    override fun autoDismissDelay(): Long {
        return mAutoDismissDelay
    }

    open fun setAutoDismissDelay(autoDismissDelay: Long): PopTipConfig {
        mAutoDismissDelay = autoDismissDelay
        return this
    }

    /**
     * 默认对齐方式
     */
    override fun align(): Int {
        return mAlign
    }

    open fun setAlign(align: Int): PopTipConfig {
        mAlign = align
        return this
    }

    /**
     * 默认实现模式
     */
    override fun dialogImplMode(): Int {
        return mDialogImplMode
    }

    open fun setDialogImplMode(dialogImplMode: Int): PopTipConfig {
        mDialogImplMode = dialogImplMode
        return this
    }

    /**
     * 默认圆角 ( px )
     */
    override fun radiusPx(): Int {
        return mRadiusPx
    }

    open fun setRadiusPx(radiusPx: Int): PopTipConfig {
        mRadiusPx = radiusPx
        return this
    }

    /**
     * 默认主题样式对象
     */
    override fun style(): Any? {
        return mStyle
    }

    open fun setStyle(style: DialogXStyle?): PopTipConfig {
        mStyle = style
        return this
    }

    /**
     * 设置默认主题样式对象 ( Object 重载 )
     * @param style 主题样式对象
     * @return [PopTipConfig]
     */
    open fun setStyle(style: Any?): PopTipConfig {
        mStyle = style
        return this
    }

    /**
     * 默认明暗主题
     */
    override fun theme(): Int {
        return mTheme
    }

    open fun setTheme(theme: Int): PopTipConfig {
        mTheme = theme
        return this
    }

    /**
     * 默认提示文本样式对象
     */
    override fun messageTextInfo(): Any? {
        return mMessageTextInfo
    }

    open fun setMessageTextInfo(messageTextInfo: TextInfo?): PopTipConfig {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 设置默认提示文本样式对象 ( Object 重载 )
     * @param messageTextInfo 提示文本样式对象
     * @return [PopTipConfig]
     */
    open fun setMessageTextInfo(messageTextInfo: Any?): PopTipConfig {
        mMessageTextInfo = messageTextInfo
        return this
    }

    /**
     * 默认按钮文本样式对象
     */
    override fun buttonTextInfo(): Any? {
        return mButtonTextInfo
    }

    open fun setButtonTextInfo(buttonTextInfo: TextInfo?): PopTipConfig {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 设置默认按钮文本样式对象 ( Object 重载 )
     * @param buttonTextInfo 按钮文本样式对象
     * @return [PopTipConfig]
     */
    open fun setButtonTextInfo(buttonTextInfo: Any?): PopTipConfig {
        mButtonTextInfo = buttonTextInfo
        return this
    }

    /**
     * 默认是否启用振动反馈
     */
    override fun useHaptic(): Boolean? {
        return mUseHaptic
    }

    open fun setUseHaptic(useHaptic: Boolean?): PopTipConfig {
        mUseHaptic = useHaptic
        return this
    }

    /**
     * 默认进入动画时长 ( ms )
     */
    override fun enterAnimDuration(): Long {
        return mEnterAnimDuration
    }

    open fun setEnterAnimDuration(enterAnimDuration: Long): PopTipConfig {
        mEnterAnimDuration = enterAnimDuration
        return this
    }

    /**
     * 默认退出动画时长 ( ms )
     */
    override fun exitAnimDuration(): Long {
        return mExitAnimDuration
    }

    open fun setExitAnimDuration(exitAnimDuration: Long): PopTipConfig {
        mExitAnimDuration = exitAnimDuration
        return this
    }

    /**
     * 默认背景色 ( ColorInt )
     */
    override fun backgroundColor(): Int? {
        return mBackgroundColor
    }

    open fun setBackgroundColor(backgroundColor: Int?): PopTipConfig {
        mBackgroundColor = backgroundColor
        return this
    }

    /**
     * 最大同时显示数量
     */
    override fun maxShowCount(): Int {
        return mMaxShowCount
    }

    open fun setMaxShowCount(maxShowCount: Int): PopTipConfig {
        mMaxShowCount = maxShowCount
        return this
    }

    /**
     * 覆盖进入动画时长 ( ms )
     */
    override fun overrideEnterDuration(): Long {
        return mOverrideEnterDuration
    }

    open fun setOverrideEnterDuration(overrideEnterDuration: Long): PopTipConfig {
        mOverrideEnterDuration = overrideEnterDuration
        return this
    }

    /**
     * 覆盖退出动画时长 ( ms )
     */
    override fun overrideExitDuration(): Long {
        return mOverrideExitDuration
    }

    open fun setOverrideExitDuration(overrideExitDuration: Long): PopTipConfig {
        mOverrideExitDuration = overrideExitDuration
        return this
    }

    /**
     * 覆盖进入动画资源 id
     */
    override fun overrideEnterAnimRes(): Int {
        return mOverrideEnterAnimRes
    }

    open fun setOverrideEnterAnimRes(overrideEnterAnimRes: Int): PopTipConfig {
        mOverrideEnterAnimRes = overrideEnterAnimRes
        return this
    }

    /**
     * 覆盖退出动画资源 id
     */
    override fun overrideExitAnimRes(): Int {
        return mOverrideExitAnimRes
    }

    open fun setOverrideExitAnimRes(overrideExitAnimRes: Int): PopTipConfig {
        mOverrideExitAnimRes = overrideExitAnimRes
        return this
    }

    /**
     * 多 PopTip 位移拦截器对象
     */
    override fun moveDisplacementInterceptor(): Any? {
        return mMoveDisplacementInterceptor
    }

    open fun setMoveDisplacementInterceptor(
        interceptor: PopMoveDisplacementInterceptor<PopTip>?
    ): PopTipConfig {
        mMoveDisplacementInterceptor = interceptor
        return this
    }

    /**
     * 设置多 PopTip 位移拦截器对象 ( Object 重载 )
     * @param interceptor 多 PopTip 位移拦截器对象
     * @return [PopTipConfig]
     */
    open fun setMoveDisplacementInterceptor(interceptor: Any?): PopTipConfig {
        mMoveDisplacementInterceptor = interceptor
        return this
    }
}