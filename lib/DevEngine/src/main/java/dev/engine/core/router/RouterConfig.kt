package dev.engine.core.router

import dev.engine.router.IRouterEngine

/**
 * detail: Router Config
 * @author Ttt
 */
open class RouterConfig private constructor(
    config: RouterConfig?
) : IRouterEngine.EngineConfig {

    // 是否为 Debug 环境
    private var mDebug: Boolean? = null

    // 是否由框架自动初始化
    private var mAutoInit: Boolean? = null

    // 是否异步初始化 Autowired 注入表
    private var mAsyncInitRouterInject: Boolean? = null

    companion object {

        /**
         * 创建 Router Config
         * @return [RouterConfig]
         */
        fun create(): RouterConfig {
            return RouterConfig(null)
        }

        /**
         * 创建 Router Config
         * @param config Router Config
         * @return [RouterConfig]
         */
        fun create(config: RouterConfig?): RouterConfig {
            return RouterConfig(config)
        }
    }

    init {
        config?.let {
            mDebug = it.mDebug
            mAutoInit = it.mAutoInit
            mAsyncInitRouterInject = it.mAsyncInitRouterInject
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [RouterConfig]
     */
    open fun clone(): RouterConfig {
        return RouterConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    override fun debug(): Boolean? {
        return mDebug
    }

    open fun setDebug(debug: Boolean?): RouterConfig {
        mDebug = debug
        return this
    }

    override fun autoInit(): Boolean? {
        return mAutoInit
    }

    open fun setAutoInit(autoInit: Boolean?): RouterConfig {
        mAutoInit = autoInit
        return this
    }

    override fun asyncInitRouterInject(): Boolean? {
        return mAsyncInitRouterInject
    }

    open fun setAsyncInitRouterInject(asyncInitRouterInject: Boolean?): RouterConfig {
        mAsyncInitRouterInject = asyncInitRouterInject
        return this
    }
}