package dev.engine.extensions.push

import android.app.Application
import android.content.Context
import dev.engine.DevEngine
import dev.engine.push.IPushEngine

// =========================================
// = IPushEngine<EngineConfig, EngineItem> =
// =========================================

/**
 * 通过 Key 获取 Push Engine
 * @receiver String?
 * @return IPushEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Push Engine
 */
fun String?.getPushEngine(): IPushEngine<in IPushEngine.EngineConfig, in IPushEngine.EngineItem>? {
    DevEngine.getPush(this)?.let { value ->
        return value
    }
    return DevEngine.getPush()
}

// ===================
// = Key Push Engine =
// ===================

// =============
// = 对外公开方法 =
// =============

/**
 * 初始化方法
 * @receiver Application
 * @param engine String?
 * @param config Push Config
 */
fun <Config : IPushEngine.EngineConfig> Application.push_initialize(
    engine: String? = null,
    config: Config?
) {
    engine.getPushEngine()?.initialize(this, config)
}

/**
 * 绑定
 * @receiver Context
 * @param engine String?
 * @param config Push Config
 */
fun <Config : IPushEngine.EngineConfig> Context.push_register(
    engine: String? = null,
    config: Config?
) {
    engine.getPushEngine()?.register(this, config)
}

/**
 * 解绑
 * @receiver Context
 * @param engine String?
 * @param config Push Config
 */
fun <Config : IPushEngine.EngineConfig> Context.push_unregister(
    engine: String? = null,
    config: Config?
) {
    engine.getPushEngine()?.unregister(this, config)
}

// =

/**
 * 推送进程启动通知
 * @receiver Context
 * @param engine String?
 * @param pid Push 进程 ID
 */
fun Context?.push_onReceiveServicePid(
    engine: String? = null,
    pid: Int
) {
    engine.getPushEngine()?.onReceiveServicePid(this, pid)
}

/**
 * 初始化 Client Id 成功通知
 * @receiver Context
 * @param engine String?
 * @param clientId 唯一 ID 用于标识当前应用
 */
fun Context?.push_onReceiveClientId(
    engine: String? = null,
    clientId: String?
) {
    engine.getPushEngine()?.onReceiveClientId(this, clientId)
}

/**
 * 设备 ( 厂商 ) Token 通知
 * @receiver Context
 * @param engine String?
 * @param deviceToken 设备 Token
 */
fun Context?.push_onReceiveDeviceToken(
    engine: String? = null,
    deviceToken: String?
) {
    engine.getPushEngine()?.onReceiveDeviceToken(this, deviceToken)
}

/**
 * 在线状态变化通知
 * @receiver Context
 * @param engine String?
 * @param online 是否在线
 */
fun Context?.push_onReceiveOnlineState(
    engine: String? = null,
    online: Boolean
) {
    engine.getPushEngine()?.onReceiveOnlineState(this, online)
}

/**
 * 命令回执通知
 * @receiver Context
 * @param engine String?
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> Context?.push_onReceiveCommandResult(
    engine: String? = null,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveCommandResult(this, message)
}

/**
 * 推送消息送达通知
 * @receiver Context
 * @param engine String?
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> Context?.push_onNotificationMessageArrived(
    engine: String? = null,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageArrived(this, message)
}

/**
 * 推送消息点击通知
 * @receiver Context
 * @param engine String?
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> Context?.push_onNotificationMessageClicked(
    engine: String? = null,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageClicked(this, message)
}

/**
 * 透传消息送达通知
 * @receiver Context
 * @param engine String?
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> Context?.push_onReceiveMessageData(
    engine: String? = null,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveMessageData(this, message)
}

// ===============
// = 转换 Message =
// ===============

/**
 * 传入 Object 转换 Engine Message
 * @receiver Message Object
 * @param engine String?
 * @return Engine Message
 */
fun Any?.push_convertMessage(
    engine: String? = null
): Any? {
    return engine.getPushEngine()?.convertMessage(this)
}