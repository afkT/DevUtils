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
 * @param engine String?
 * @param value 事件数据
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_post(
    engine: String? = null,
    value: T,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.post(key, value) ?: false
}

/**
 * 发送延迟事件
 * @param engine String?
 * @param value 事件数据
 * @param delay 延迟毫秒数
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_postDelay(
    engine: String? = null,
    value: T,
    delay: Long,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postDelay(key, value, delay) ?: false
}

/**
 * 发送延迟事件
 * @param engine String?
 * @param lifecycle 生命周期持有者
 * @param value 事件数据
 * @param delay 延迟毫秒数
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_postDelay(
    engine: String? = null,
    lifecycle: LifecycleOwner?,
    value: T,
    delay: Long,
    key: String = value.javaClass.name
): Boolean {
    lifecycle ?: return false
    return engine.getEventBusEngine()?.postDelay(key, lifecycle, value, delay) ?: false
}

/**
 * 顺序发送事件
 * @param engine String?
 * @param value 事件数据
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_postOrderly(
    engine: String? = null,
    value: T,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postOrderly(key, value) ?: false
}

/**
 * 跨进程发送事件
 * @param engine String?
 * @param value 事件数据
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_postAcrossProcess(
    engine: String? = null,
    value: T,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postAcrossProcess(key, value) ?: false
}

/**
 * 跨 App 发送事件
 * @param engine String?
 * @param value 事件数据
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_postAcrossApp(
    engine: String? = null,
    value: T,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.postAcrossApp(key, value) ?: false
}

/**
 * 广播形式发送事件
 * @param engine String?
 * @param value 事件数据
 * @param key key
 * @return `true` success, `false` fail
 */
@Deprecated(
    message = "建议使用 eventbus_postAcrossProcess 或 eventbus_postAcrossApp",
    replaceWith = ReplaceWith("eventbus_postAcrossApp(engine, value, key)")
)
fun <T : Any> eventbus_broadcast(
    engine: String? = null,
    value: T,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.broadcast(key, value) ?: false
}

/**
 * 广播形式发送事件
 * @param engine String?
 * @param value 事件数据
 * @param foreground `true` 前台广播, `false` 后台广播
 * @param onlyInApp `true` 只在 App 内有效, `false` 全局有效
 * @param key key
 * @return `true` success, `false` fail
 */
fun <T : Any> eventbus_broadcast(
    engine: String? = null,
    value: T,
    foreground: Boolean,
    onlyInApp: Boolean,
    key: String = value.javaClass.name
): Boolean {
    return engine.getEventBusEngine()?.broadcast(key, value, foreground, onlyInApp) ?: false
}

// ==========
// = 观察事件 =
// ==========

/**
 * 注册生命周期感知观察者
 * @param engine String?
 * @param type 事件类型
 * @param lifecycle 生命周期持有者
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> eventbus_observe(
    engine: String? = null,
    type: Class<T>,
    lifecycle: LifecycleOwner?,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    lifecycle ?: return false
    return engine.getEventBusEngine()?.observe(key, type, lifecycle, observer) ?: false
}

/**
 * 注册生命周期感知 Sticky 观察者
 * @param engine String?
 * @param type 事件类型
 * @param lifecycle 生命周期持有者
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> eventbus_observeSticky(
    engine: String? = null,
    type: Class<T>,
    lifecycle: LifecycleOwner?,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    lifecycle ?: return false
    return engine.getEventBusEngine()?.observeSticky(key, type, lifecycle, observer) ?: false
}

/**
 * 注册永久观察者
 * @param engine String?
 * @param type 事件类型
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> eventbus_observeForever(
    engine: String? = null,
    type: Class<T>,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observeForever(key, type, observer) ?: false
}

/**
 * 注册永久 Sticky 观察者
 * @param engine String?
 * @param type 事件类型
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> eventbus_observeStickyForever(
    engine: String? = null,
    type: Class<T>,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.observeStickyForever(key, type, observer) ?: false
}

/**
 * 移除永久观察者
 * @param engine String?
 * @param type 事件类型
 * @param key key
 * @param observer 事件观察者
 * @return `true` success, `false` fail
 */
fun <T> eventbus_removeObserver(
    engine: String? = null,
    type: Class<T>,
    key: String = type.name,
    observer: Observer<T>
): Boolean {
    return engine.getEventBusEngine()?.removeObserver(key, type, observer) ?: false
}