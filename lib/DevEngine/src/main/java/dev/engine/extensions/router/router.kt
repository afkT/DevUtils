package dev.engine.extensions.router

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import dev.engine.DevEngine
import dev.engine.router.IRouterEngine
import java.io.Serializable

// ===========================================
// = IRouterEngine<EngineConfig, EngineItem> =
// ===========================================

/**
 * 通过 Key 获取 Router Engine
 * @receiver String?
 * @return IRouterEngine<EngineConfig, EngineItem>
 * 内部做了处理如果匹配不到则返回默认 Router Engine
 */
fun String?.getRouterEngine(): IRouterEngine<
        in IRouterEngine.EngineConfig,
        in IRouterEngine.EngineItem>? {
    DevEngine.getRouter(this)?.let { value ->
        return value
    }
    return DevEngine.getRouter()
}

// =====================
// = Key Router Engine =
// =====================

// =============
// = 对外公开方法 =
// =============

/**
 * 获取 Router Engine Config
 * @param engine String?
 * @return Router Config
 */
fun router_getConfig(
    engine: String? = null
): Any? {
    return engine.getRouterEngine()?.config
}

/**
 * 设置 Router Engine Config
 * @param engine String?
 * @param config Router Config
 */
fun <Config : IRouterEngine.EngineConfig> router_setConfig(
    engine: String? = null,
    config: Config?
) {
    engine.getRouterEngine()?.setConfig(config)
}

/**
 * 初始化 Router Engine
 * @param engine String?
 * @param config Router Config
 * @return `true` success, `false` fail
 */
fun <Config : IRouterEngine.EngineConfig> router_initialize(
    engine: String? = null,
    config: Config
): Boolean {
    return engine.getRouterEngine()?.initialize(config) ?: false
}

/**
 * Router 是否已初始化
 * @param engine String?
 * @return `true` yes, `false` no
 */
fun router_isInitialized(
    engine: String? = null
): Boolean {
    return engine.getRouterEngine()?.isInitialized ?: false
}

/**
 * 设置是否为 Debug 环境
 * @param engine String?
 * @param debug 是否为 Debug 环境
 */
fun router_setDebug(
    engine: String? = null,
    debug: Boolean
) {
    engine.getRouterEngine()?.setDebug(debug)
}

/**
 * 设置日志输出回调
 * @param engine String?
 * @param callback 日志输出回调
 */
fun router_setLogCallback(
    engine: String? = null,
    callback: IRouterEngine.OnLogCallback?
) {
    engine.getRouterEngine()?.setLogCallback(callback)
}

/**
 * 为 Autowired 注解的变量赋值
 * @param engine String?
 * @param target 目标对象
 */
fun router_inject(
    engine: String? = null,
    target: Any?
) {
    engine.getRouterEngine()?.inject(target)
}

/**
 * 判断 url 是否为 TheRouter 的路由 Path
 * @param engine String?
 * @param url 路由 url
 * @return `true` yes, `false` no
 */
fun router_isRouterPath(
    engine: String? = null,
    url: String?
): Boolean {
    return engine.getRouterEngine()?.isRouterPath(url) ?: false
}

/**
 * 判断 url 是否为 TheRouter 的 Action
 * @param engine String?
 * @param url 路由 url
 * @return `true` yes, `false` no
 */
fun router_isRouterAction(
    engine: String? = null,
    url: String?
): Boolean {
    return engine.getRouterEngine()?.isRouterAction(url) ?: false
}

/**
 * 获取跨模块依赖的服务
 * @param engine String?
 * @param clazz 服务类型
 * @param params 构造参数
 * @return 服务实例
 */
fun <T : Any> router_getService(
    engine: String? = null,
    clazz: Class<T>,
    vararg params: Any?
): T? {
    return engine.getRouterEngine()?.getService(clazz, *params)
}

/**
 * 执行业务自定义 FlowTask
 * @param engine String?
 * @param taskName 任务名
 */
fun router_runTask(
    engine: String? = null,
    taskName: String?
) {
    engine.getRouterEngine()?.runTask(taskName)
}

/**
 * 设置全局默认路由跳转结果回调
 * @param engine String?
 * @param callback 跳转结果回调
 */
fun router_setDefaultNavigationCallback(
    engine: String? = null,
    callback: IRouterEngine.OnNavigationCallback?
) {
    engine.getRouterEngine()?.setDefaultNavigationCallback(callback)
}

// ==============
// = 构建 Navigator =
// ==============

/**
 * 通过 Path 构建 Navigator ( 不跳转 )
 * @param engine String?
 * @param path 路由 Path / Url
 * @return Navigator 对象
 */
fun router_build(
    engine: String? = null,
    path: String?
): Any? {
    return engine.getRouterEngine()?.build(path)
}

/**
 * 通过 Path 构建 Navigator ( 不跳转 )
 * @receiver Router 参数
 * @param engine String?
 * @return Navigator 对象
 */
fun <Item : IRouterEngine.EngineItem> Item?.router_build(
    engine: String? = null
): Any? {
    return engine.getRouterEngine()?.build(this)
}

/**
 * 通过 Intent 构建 Navigator ( 不跳转 )
 * @param engine String?
 * @param intent Intent
 * @return Navigator 对象
 */
fun router_build(
    engine: String? = null,
    intent: Intent?
): Any? {
    return engine.getRouterEngine()?.build(intent)
}

// =================
// = Navigator 句柄操作 =
// =================

/**
 * 获取带参数的完整 url
 * @receiver Navigator 对象
 * @param engine String?
 * @return 完整 url
 */
fun Any?.router_getUrlWithParams(
    engine: String? = null
): String? {
    return engine.getRouterEngine()?.getUrlWithParams(this)
}

/**
 * 设置 pending 等待路由表初始化
 * @receiver Navigator 对象
 * @param engine String?
 * @return Navigator 对象
 */
fun Any?.router_pending(
    engine: String? = null
): Any? {
    return engine.getRouterEngine()?.pending(this)
}

/**
 * 设置 Int 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withInt(
    engine: String? = null,
    key: String?,
    value: Int
): Any? {
    return engine.getRouterEngine()?.withInt(this, key, value)
}

/**
 * 设置 Long 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withLong(
    engine: String? = null,
    key: String?,
    value: Long
): Any? {
    return engine.getRouterEngine()?.withLong(this, key, value)
}

/**
 * 设置 Double 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withDouble(
    engine: String? = null,
    key: String?,
    value: Double
): Any? {
    return engine.getRouterEngine()?.withDouble(this, key, value)
}

/**
 * 设置 Float 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withFloat(
    engine: String? = null,
    key: String?,
    value: Float
): Any? {
    return engine.getRouterEngine()?.withFloat(this, key, value)
}

/**
 * 设置 Char 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withChar(
    engine: String? = null,
    key: String?,
    value: Char
): Any? {
    return engine.getRouterEngine()?.withChar(this, key, value)
}

/**
 * 设置 Byte 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withByte(
    engine: String? = null,
    key: String?,
    value: Byte
): Any? {
    return engine.getRouterEngine()?.withByte(this, key, value)
}

/**
 * 设置 Boolean 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withBoolean(
    engine: String? = null,
    key: String?,
    value: Boolean
): Any? {
    return engine.getRouterEngine()?.withBoolean(this, key, value)
}

/**
 * 设置 String 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withString(
    engine: String? = null,
    key: String?,
    value: String?
): Any? {
    return engine.getRouterEngine()?.withString(this, key, value)
}

/**
 * 设置 Serializable 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withSerializable(
    engine: String? = null,
    key: String?,
    value: Serializable?
): Any? {
    return engine.getRouterEngine()?.withSerializable(this, key, value)
}

/**
 * 设置 Parcelable 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withParcelable(
    engine: String? = null,
    key: String?,
    value: Parcelable?
): Any? {
    return engine.getRouterEngine()?.withParcelable(this, key, value)
}

/**
 * 设置 Object 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withObject(
    engine: String? = null,
    key: String?,
    value: Any?
): Any? {
    return engine.getRouterEngine()?.withObject(this, key, value)
}

/**
 * 设置 Bundle 参数
 * @receiver Navigator 对象
 * @param engine String?
 * @param key 参数 key
 * @param value 参数值
 * @return Navigator 对象
 */
fun Any?.router_withBundle(
    engine: String? = null,
    key: String?,
    value: Bundle?
): Any? {
    return engine.getRouterEngine()?.withBundle(this, key, value)
}

/**
 * 设置 Bundle 参数 ( 合并到 Navigator )
 * @receiver Navigator 对象
 * @param engine String?
 * @param value 参数 Bundle
 * @return Navigator 对象
 */
fun Any?.router_with(
    engine: String? = null,
    value: Bundle?
): Any? {
    return engine.getRouterEngine()?.with(this, value)
}

/**
 * 追加 Intent Flags
 * @receiver Navigator 对象
 * @param engine String?
 * @param flags Intent Flags
 * @return Navigator 对象
 */
fun Any?.router_addFlags(
    engine: String? = null,
    flags: Int
): Any? {
    return engine.getRouterEngine()?.addFlags(this, flags)
}

/**
 * 设置 Intent Flags
 * @receiver Navigator 对象
 * @param engine String?
 * @param flags Intent Flags
 * @return Navigator 对象
 */
fun Any?.router_withFlags(
    engine: String? = null,
    flags: Int
): Any? {
    return engine.getRouterEngine()?.withFlags(this, flags)
}

/**
 * 设置 Activity Options Bundle
 * @receiver Navigator 对象
 * @param engine String?
 * @param optionsCompat Options Bundle
 * @return Navigator 对象
 */
fun Any?.router_withOptionsCompat(
    engine: String? = null,
    optionsCompat: Bundle?
): Any? {
    return engine.getRouterEngine()?.withOptionsCompat(this, optionsCompat)
}

/**
 * 设置进入动画资源 id
 * @receiver Navigator 对象
 * @param engine String?
 * @param animResId 动画资源 id
 * @return Navigator 对象
 */
fun Any?.router_withInAnimation(
    engine: String? = null,
    animResId: Int
): Any? {
    return engine.getRouterEngine()?.withInAnimation(this, animResId)
}

/**
 * 设置退出动画资源 id
 * @receiver Navigator 对象
 * @param engine String?
 * @param animResId 动画资源 id
 * @return Navigator 对象
 */
fun Any?.router_withOutAnimation(
    engine: String? = null,
    animResId: Int
): Any? {
    return engine.getRouterEngine()?.withOutAnimation(this, animResId)
}

/**
 * 创建 Intent
 * @receiver Navigator 对象
 * @param engine String?
 * @param context Context 对象
 * @return Intent
 */
fun Any?.router_createIntent(
    engine: String? = null,
    context: Any?
): Intent? {
    return engine.getRouterEngine()?.createIntent(this, context)
}

/**
 * 创建 Fragment
 * @receiver Navigator 对象
 * @param engine String?
 * @return Fragment 实例
 */
fun <T : Any> Any?.router_createFragment(
    engine: String? = null
): T? {
    return engine.getRouterEngine()?.createFragment(this)
}

/**
 * 执行路由跳转
 * @receiver Navigator 对象
 * @param engine String?
 * @param item Router 参数 ( 可为 null )
 */
fun Any?.router_navigation(
    engine: String? = null,
    item: IRouterEngine.EngineItem? = null
) {
    engine.getRouterEngine()?.navigation(this, item)
}

/**
 * 执行 Action
 * @receiver Navigator 对象
 * @param engine String?
 * @param context Context 对象
 * @return `true` success, `false` fail
 */
fun Any?.router_action(
    engine: String? = null,
    context: Any?
): Boolean {
    return engine.getRouterEngine()?.action(this, context) ?: false
}

/**
 * 通过 Router Item 构建并跳转
 * @receiver Router 参数
 * @param engine String?
 */
fun <Item : IRouterEngine.EngineItem> Item?.router_navigation(
    engine: String? = null
) {
    val routerEngine = engine.getRouterEngine() ?: return
    val navigator = routerEngine.build(this) ?: return
    routerEngine.navigation(navigator, this)
}