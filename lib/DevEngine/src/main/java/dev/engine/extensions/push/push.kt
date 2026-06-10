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
 * @param engine String?
 * @param context Context
 * @param pid Push 进程 ID
 */
fun push_onReceiveServicePid(
    engine: String? = null,
    context: Context?,
    pid: Int
) {
    engine.getPushEngine()?.onReceiveServicePid(context, pid)
}

/**
 * 初始化 Client Id 成功通知
 * @param engine String?
 * @param context Context
 * @param clientId 唯一 ID 用于标识当前应用
 */
fun push_onReceiveClientId(
    engine: String? = null,
    context: Context?,
    clientId: String?
) {
    engine.getPushEngine()?.onReceiveClientId(context, clientId)
}

/**
 * 设备 ( 厂商 ) Token 通知
 * @param engine String?
 * @param context Context
 * @param deviceToken 设备 Token
 */
fun push_onReceiveDeviceToken(
    engine: String? = null,
    context: Context?,
    deviceToken: String?
) {
    engine.getPushEngine()?.onReceiveDeviceToken(context, deviceToken)
}

/**
 * 在线状态变化通知
 * @param engine String?
 * @param context Context
 * @param online 是否在线
 */
fun push_onReceiveOnlineState(
    engine: String? = null,
    context: Context?,
    online: Boolean
) {
    engine.getPushEngine()?.onReceiveOnlineState(context, online)
}

/**
 * 命令回执通知
 * @param engine String?
 * @param context Context
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> push_onReceiveCommandResult(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveCommandResult(context, message)
}

/**
 * 推送消息送达通知
 * @param engine String?
 * @param context Context
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> push_onNotificationMessageArrived(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageArrived(context, message)
}

/**
 * 推送消息点击通知
 * @param engine String?
 * @param context Context
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> push_onNotificationMessageClicked(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onNotificationMessageClicked(context, message)
}

/**
 * 透传消息送达通知
 * @param engine String?
 * @param context Context
 * @param message Push ( Data、Params ) Item
 */
fun <Item : IPushEngine.EngineItem> push_onReceiveMessageData(
    engine: String? = null,
    context: Context?,
    message: Item?
) {
    engine.getPushEngine()?.onReceiveMessageData(context, message)
}

// ===============
// = 转换 Message =
// ===============

/**
 * 传入 Object 转换 Engine Message
 * @param engine String?
 * @param message Message Object
 * @return Engine Message
 */
fun push_convertMessage(
    engine: String? = null,
    message: Any?
): Any? {
    return engine.getPushEngine()?.convertMessage(message)
}