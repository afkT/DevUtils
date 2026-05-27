package dev.engine.core.eventbus

import android.content.Context
import com.jeremyliao.liveeventbus.logger.Logger
import dev.engine.eventbus.IEventBusEngine

/**
 * detail: LiveEventBus Config
 * @author Ttt
 */
open class LiveEventBusConfig private constructor(
    config: LiveEventBusConfig?
) : IEventBusEngine.EngineConfig {

    // Context
    private var mContext: Context? = null

    // 生命周期观察者是否始终激活
    private var mLifecycleObserverAlwaysActive: Boolean? = null

    // 无观察者时是否自动清理
    private var mAutoClear: Boolean? = null

    // Logger
    private var mLogger: Logger? = null

    // 是否启用 Logger
    private var mEnableLogger: Boolean? = null

    companion object {

        fun create(): LiveEventBusConfig {
            return LiveEventBusConfig(null)
        }

        fun create(config: LiveEventBusConfig?): LiveEventBusConfig {
            return LiveEventBusConfig(config)
        }
    }

    init {
        config?.let {
            this.mContext = it.mContext
            this.mLifecycleObserverAlwaysActive = it.mLifecycleObserverAlwaysActive
            this.mAutoClear = it.mAutoClear
            this.mLogger = it.mLogger
            this.mEnableLogger = it.mEnableLogger
        }
    }

    // ==========
    // = 其他方法 =
    // ==========

    /**
     * 克隆配置信息
     * @return [LiveEventBusConfig]
     */
    open fun clone(): LiveEventBusConfig {
        return LiveEventBusConfig(this)
    }

    // ===========
    // = get/set =
    // ===========

    open fun context(): Context? {
        return mContext
    }

    open fun setContext(context: Context?): LiveEventBusConfig {
        mContext = context
        return this
    }

    open fun lifecycleObserverAlwaysActive(): Boolean? {
        return mLifecycleObserverAlwaysActive
    }

    open fun setLifecycleObserverAlwaysActive(
        lifecycleObserverAlwaysActive: Boolean?
    ): LiveEventBusConfig {
        mLifecycleObserverAlwaysActive = lifecycleObserverAlwaysActive
        return this
    }

    open fun autoClear(): Boolean? {
        return mAutoClear
    }

    open fun setAutoClear(autoClear: Boolean?): LiveEventBusConfig {
        mAutoClear = autoClear
        return this
    }

    open fun logger(): Logger? {
        return mLogger
    }

    open fun setLogger(logger: Logger?): LiveEventBusConfig {
        mLogger = logger
        return this
    }

    open fun enableLogger(): Boolean? {
        return mEnableLogger
    }

    open fun setEnableLogger(enableLogger: Boolean?): LiveEventBusConfig {
        mEnableLogger = enableLogger
        return this
    }
}
