package dev.engine.extensions.eventbus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import dev.engine.DevEngine
import dev.engine.eventbus.IEventBusEngine

// =================================
// = IEventBusEngine<EngineConfig> =
// =================================

/**
 * 通过 Key 获取 EventBus Engine
 * @receiver String?
 * @return IEventBusEngine<EngineConfig>
 * 内部做了处理如果匹配不到则返回默认 EventBus Engine
 */
fun String?.getEventBusEngine(): IEventBusEngine<in IEventBusEngine.EngineConfig>? {
    DevEngine.getEventBus(this)?.let { value ->
        return value
    }
    return DevEngine.getEventBus()
}

// ======================
// = Key EventBus Engine =
// ======================

// =============
// = 对外公开方法 =
// =============

/**
 * 初始化方法
 * @receiver EventBus Config
 * @param engine String?
 * @return `true` success, `false` fail
 */
fun <Config : IEventBusEngine.EngineConfig> Config.eventbus_initialize(
    engine: String? = null
): Boolean {
    return engine.getEventBusEngine()?.initialize(this) ?: false
}

/**
 * 应用事件配置
 * @receiver EventBus Config
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
fun <Config : IEventBusEngine.EngineConfig> Config.eventbus_config(
    engine: String? = null,
    key: String
): Boolean {
    return engine.getEventBusEngine()?.config(key, this) ?: false
}

// ==========
// = 发送事件 =
// ==========

/**
 * 发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_post(
    engine: String? = null,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.post(key, this) ?: false
}

/**
 * 发送延迟事件
 * @receiver 事件数据
 * @param engine String?
 * @param delay 延迟毫秒数
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_postDelay(
    engine: String? = null,
    delay: Long,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postDelay(key, this, delay) ?: false
}

/**
 * 发送延迟事件
 * @receiver 事件数据
 * @param engine String?
 * @param lifecycle 生命周期持有者
 * @param delay 延迟毫秒数
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_postDelay(
    engine: String? = null,
    lifecycle: LifecycleOwner?,
    delay: Long,
    key: String = this.javaClass.name
): Boolean {
    lifecycle ?: return false
    return engine.getEventBusEngine()?.postDelay(key, lifecycle, this, delay) ?: false
}

/**
 * 顺序发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_postOrderly(
    engine: String? = null,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postOrderly(key, this) ?: false
}

/**
 * 跨进程发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_postAcrossProcess(
    engine: String? = null,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postAcrossProcess(key, this) ?: false
}

/**
 * 跨 App 发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_postAcrossApp(
    engine: String? = null,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postAcrossApp(key, this) ?: false
}

/**
 * 广播形式发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param key key
 * @return `true` success, `false` fail
 */
@Deprecated(
    message = "建议使用 eventbus_postAcrossProcess 或 eventbus_postAcrossApp",
    replaceWith = ReplaceWith("this.eventbus_postAcrossApp(engine, key)")
)
fun <T : Any> T.eventbus_broadcast(
    engine: String? = null,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.broadcast(key, this) ?: false
}

/**
 * 广播形式发送事件
 * @receiver 事件数据
 * @param engine String?
 * @param foreground `true` 前台广播, `false` 后台广播
 * @param onlyInApp `true` 只在 App 内有效, `false` 全局有效
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> T.eventbus_broadcast(
    engine: String? = null,
    foreground: Boolean,
    onlyInApp: Boolean,
    key: String = this.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.broadcast(key, this, foreground, onlyInApp) ?: false
}

// ==========
// = 观察事件 =
// ==========

/**
 * 注册生命周期感知观察者
 * @receiver 生命周期持有者
 * @param engine String?
 * @param type 事件类型
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> LifecycleOwner.eventbus_observe(
    engine: String? = null,
    type: Class<T>,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observe(key, type, this, observer) ?: false
}

/**
 * 注册生命周期感知 Sticky 观察者
 * @receiver 生命周期持有者
 * @param engine String?
 * @param type 事件类型
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> LifecycleOwner.eventbus_observeSticky(
    engine: String? = null,
    type: Class<T>,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observeSticky(key, type, this, observer) ?: false
}

/**
 * 注册永久观察者
 * @receiver 事件类型
 * @param engine String?
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> Class<T>.eventbus_observeForever(
    engine: String? = null,
    key: String = this.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observeForever(key, this, observer) ?: false
}

/**
 * 注册永久 Sticky 观察者
 * @receiver 事件类型
 * @param engine String?
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> Class<T>.eventbus_observeStickyForever(
    engine: String? = null,
    key: String = this.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observeStickyForever(key, this, observer) ?: false
}

/**
 * 移除永久观察者
 * @receiver 事件类型
 * @param engine String?
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> Class<T>.eventbus_removeObserver(
    engine: String? = null,
    key: String = this.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.removeObserver(key, this, observer) ?: false
}