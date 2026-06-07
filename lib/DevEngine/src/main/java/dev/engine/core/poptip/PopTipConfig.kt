package dev.engine.core.poptip

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
}