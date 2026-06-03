package dev.engine.core.eventbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.logger.Logger
import dev.engine.eventbus.IEventBusEngine

/**
 * detail: LiveEventBus EventBus Engine 实现
 * @author Ttt
 * @see https://github.com/JeremyLiao/LiveEventBus
 * @see https://github.com/michaellee123/LiveEventBus
 */
open class LiveEventBusEngineImpl : IEventBusEngine<EventBusConfig> {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化方法
     * @param config EventBus Config
     * @return `true` success, `false` fail
     */
    override fun initialize(config: EventBusConfig): Boolean {
        return applyConfig(config)
    }

    /**
     * 应用事件配置
     * @param key key
     * @param config EventBus Config
     * @return `true` success, `false` fail
     */
    override fun config(
        key: String,
        config: EventBusConfig
    ): Boolean {
        if (key.isEmpty()) return false
        config.lifecycleObserverAlwaysActive()?.let {
            LiveEventBus.config(key).lifecycleObserverAlwaysActive(it)
        }
        config.autoClear()?.let {
            LiveEventBus.config(key).autoClear(it)
        }
        return true
    }

    // ==========
    // = 发送事件 =
    // ==========

    /**
     * 发送事件
     * @param key key
     * @param value 事件数据
     * @return `true` success, `false` fail
     */
    override fun <T : Any> post(
        key: String,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            post(value); true
        } ?: false
    }

    /**
     * 发送延迟事件
     * @param key key
     * @param value 事件数据
     * @param delay 延迟毫秒数
     * @return `true` success, `false` fail
     */
    override fun <T : Any> postDelay(
        key: String,
        value: T,
        delay: Long
    ): Boolean {
        return getObservable(key, value)?.run {
            postDelay(value, delay); true
        } ?: false
    }

    /**
     * 发送延迟事件
     * @param key key
     * @param lifecycle 生命周期持有者
     * @param value 事件数据
     * @param delay 延迟毫秒数
     * @return `true` success, `false` fail
     */
    override fun <T : Any> postDelay(
        key: String,
        lifecycle: LifecycleOwner,
        value: T,
        delay: Long
    ): Boolean {
        return getObservable(key, value)?.run {
            postDelay(lifecycle, value, delay); true
        } ?: false
    }

    /**
     * 顺序发送事件
     * @param key key
     * @param value 事件数据
     * @return `true` success, `false` fail
     */
    override fun <T : Any> postOrderly(
        key: String,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postOrderly(value); true
        } ?: false
    }

    /**
     * 跨进程发送事件
     * @param key key
     * @param value 事件数据
     * @return `true` success, `false` fail
     */
    override fun <T : Any> postAcrossProcess(
        key: String,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postAcrossProcess(value); true
        } ?: false
    }

    /**
     * 跨 App 发送事件
     * @param key key
     * @param value 事件数据
     * @return `true` success, `false` fail
     */
    override fun <T : Any> postAcrossApp(
        key: String,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            postAcrossApp(value); true
        } ?: false
    }

    /**
     * 广播形式发送事件
     * @param key key
     * @param value 事件数据
     * @return `true` success, `false` fail
     */
    @Deprecated(
        message = "建议使用 postAcrossProcess 或 postAcrossApp",
        replaceWith = ReplaceWith("postAcrossApp(key, value)")
    )
    override fun <T : Any> broadcast(
        key: String,
        value: T
    ): Boolean {
        return getObservable(key, value)?.run {
            @Suppress("DEPRECATION")
            broadcast(value); true
        } ?: false
    }

    /**
     * 广播形式发送事件
     * @param key key
     * @param value 事件数据
     * @param foreground `true` 前台广播, `false` 后台广播
     * @param onlyInApp `true` 只在 App 内有效, `false` 全局有效
     * @return `true` success, `false` fail
     */
    override fun <T : Any> broadcast(
        key: String,
        value: T,
        foreground: Boolean,
        onlyInApp: Boolean
    ): Boolean {
        return getObservable(key, value)?.run {
            broadcast(value, foreground, onlyInApp); true
        } ?: false
    }

    // ==========
    // = 观察事件 =
    // ==========

    /**
     * 注册生命周期感知观察者
     * @param key key
     * @param type 事件类型
     * @param lifecycle 生命周期持有者
     * @param observer 事件观察者
     * @return `true` success, `false` fail
     */
    override fun <T : Any> observe(
        key: String,
        type: Class<T>,
        lifecycle: LifecycleOwner,
        observer: Observer<T>
    ): Boolean {
        return getObservable(key, type)?.run {
            observe(lifecycle, observer); true
        } ?: false
    }

    /**
     * 注册生命周期感知 Sticky 观察者
     * @param key key
     * @param type 事件类型
     * @param lifecycle 生命周期持有者
     * @param observer 事件观察者
     * @return `true` success, `false` fail
     */
    override fun <T : Any> observeSticky(
        key: String,
        type: Class<T>,
        lifecycle: LifecycleOwner,
        observer: Observer<T>
    ): Boolean {
        return getObservable(key, type)?.run {
            observeSticky(lifecycle, observer); true
        } ?: false
    }

    /**
     * 注册永久观察者
     * @param key key
     * @param type 事件类型
     * @param observer 事件观察者
     * @return `true` success, `false` fail
     */
    override fun <T : Any> observeForever(
        key: String,
        type: Class<T>,
        observer: Observer<T>
    ): Boolean {
        return getObservable(key, type)?.run {
            observeForever(observer); true
        } ?: false
    }

    /**
     * 注册永久 Sticky 观察者
     * @param key key
     * @param type 事件类型
     * @param observer 事件观察者
     * @return `true` success, `false` fail
     */
    override fun <T : Any> observeStickyForever(
        key: String,
        type: Class<T>,
        observer: Observer<T>
    ): Boolean {
        return getObservable(key, type)?.run {
            observeStickyForever(observer); true
        } ?: false
    }

    /**
     * 移除永久观察者
     * @param key key
     * @param type 事件类型
     * @param observer 事件观察者
     * @return `true` success, `false` fail
     */
    override fun <T : Any> removeObserver(
        key: String,
        type: Class<T>,
        observer: Observer<T>
    ): Boolean {
        return getObservable(key, type)?.run {
            removeObserver(observer); true
        } ?: false
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 应用 LiveEventBus 全局配置
     * @param config EventBus Config
     * @return `true` success, `false` fail
     */
    protected open fun applyConfig(config: EventBusConfig): Boolean {
        val liveEventConfig = LiveEventBus.config()
        config.context()?.let {
            liveEventConfig.setContext(it.applicationContext)
        }
        config.lifecycleObserverAlwaysActive()?.let {
            liveEventConfig.lifecycleObserverAlwaysActive(it)
        }
        config.autoClear()?.let {
            liveEventConfig.autoClear(it)
        }
        getLogger(config.logger())?.let {
            liveEventConfig.setLogger(it)
        }
        config.enableLogger()?.let {
            liveEventConfig.enableLogger(it)
        }
        return true
    }

    /**
     * 获取事件通道
     * @param key key
     * @param value 事件数据
     * @return [com.jeremyliao.liveeventbus.core.Observable]
     */
    protected open fun <T> getObservable(
        key: String,
        value: T
    ): com.jeremyliao.liveeventbus.core.Observable<T>? {
        if (key.isEmpty()) return null
        @Suppress("UNCHECKED_CAST")
        val type = value?.javaClass as? Class<T> ?: Any::class.java as Class<T>
        return LiveEventBus.get(key, type)
    }

    /**
     * 获取事件通道
     * @param key key
     * @param type 事件类型
     * @return [com.jeremyliao.liveeventbus.core.Observable]
     */
    protected open fun <T> getObservable(
        key: String,
        type: Class<T>?
    ): com.jeremyliao.liveeventbus.core.Observable<T>? {
        if (key.isEmpty()) return null
        type ?: return null
        return LiveEventBus.get(key, type)
    }

    /**
     * 获取 Logger
     * @param logger Logger Item
     * @return [Logger]
     */
    protected open fun getLogger(logger: Any?): Logger? {
        return logger as? Logger
    }
}