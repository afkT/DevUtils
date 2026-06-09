package dev.engine.core.router

import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.therouter.TheRouter
import com.therouter.getApplicationContext
import com.therouter.router.Navigator
import com.therouter.router.action.interceptor.ActionInterceptor
import com.therouter.router.defaultNavigationCallback
import com.therouter.router.interceptor.*
import com.therouter.theRouterInited
import com.therouter.theRouterUseAutoInit
import dev.engine.router.IRouterEngine
import java.io.Serializable

/**
 * detail: TheRouter Router Engine 实现
 * @author Ttt
 * @see https://github.com/HuolalaTech/hll-wp-therouter-android
 */
open class TheRouterEngineImpl(
    @JvmField protected val mConfig: RouterConfig
) : IRouterEngine<RouterConfig?, RouterItem?> {

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取 Router Engine Config
     * @return Router Config
     */
    override fun getConfig(): RouterConfig {
        return mConfig
    }

    /**
     * 设置 Router Engine Config
     * @param config Router Config
     */
    override fun setConfig(config: RouterConfig?) {
        config?.let { applyConfig(it) }
    }

    /**
     * 初始化 Router Engine
     * @param config Router Config
     * @return `true` success, `false` fail
     */
    override fun initialize(config: RouterConfig?): Boolean {
        config ?: return false
        return applyConfig(config)
    }

    /**
     * Router 是否已初始化
     * @return `true` yes, `false` no
     */
    override fun isInitialized(): Boolean {
        return theRouterInited()
    }

    /**
     * 设置是否为 Debug 环境
     * @param debug 是否为 Debug 环境
     */
    override fun setDebug(debug: Boolean) {
        TheRouter.isDebug = debug
    }

    /**
     * 设置日志输出回调
     * @param callback 日志输出回调
     */
    override fun setLogCallback(callback: IRouterEngine.OnLogCallback?) {
        TheRouter.logCat = if (callback == null) {
            { _, _ -> }
        } else {
            { tag, msg -> callback.log(tag, msg) }
        }
    }

    /**
     * 为 Autowired 注解的变量赋值
     * @param target 目标对象
     */
    override fun inject(target: Any?) {
        try {
            TheRouter.inject(target)
        } catch (_: Exception) {
        }
    }

    /**
     * 判断 url 是否为 TheRouter 的路由 Path
     * @param url 路由 url
     * @return `true` yes, `false` no
     */
    override fun isRouterPath(url: String?): Boolean {
        return try {
            TheRouter.isRouterPath(url)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * 判断 url 是否为 TheRouter 的 Action
     * @param url 路由 url
     * @return `true` yes, `false` no
     */
    override fun isRouterAction(url: String?): Boolean {
        return try {
            TheRouter.isRouterAction(url)
        } catch (_: Exception) {
            false
        }
    }

    /**
     * 获取跨模块依赖的服务
     * @param clazz 服务类型
     * @param params 构造参数
     * @return 服务实例
     */
    override fun <T : Any> getService(
        clazz: Class<T>,
        vararg params: Any?
    ): T? {
        return try {
            TheRouter.get(clazz, *params)
        } catch (_: Exception) {
            null
        }
    }

    /**
     * 执行业务自定义 FlowTask
     * @param taskName 任务名
     */
    override fun runTask(taskName: String?) {
        if (taskName.isNullOrEmpty()) return
        try {
            TheRouter.runTask(taskName)
        } catch (_: Exception) {
        }
    }

    /**
     * 设置全局默认路由跳转结果回调
     * @param callback 跳转结果回调
     */
    override fun setDefaultNavigationCallback(callback: IRouterEngine.OnNavigationCallback?) {
        wrapNavigationCallback(callback)?.let {
            defaultNavigationCallback(it)
        }
    }

    /**
     * 新增 Path 修改器
     * @param handle Path 修改器对象
     */
    override fun addNavigatorPathFixHandle(handle: Any?) {
        getNavigatorPathFixHandle(handle)?.let {
            addNavigatorPathFixHandle(it)
        }
    }

    /**
     * 移除 Path 修改器
     * @param handle Path 修改器对象
     * @return `true` success, `false` fail
     */
    override fun removeNavigatorPathFixHandle(handle: Any?): Boolean {
        val fixHandle = getNavigatorPathFixHandle(handle) ?: return false
        return removeNavigatorPathFixHandle(fixHandle)
    }

    /**
     * 新增 Path 替换拦截器
     * @param interceptor Path 替换拦截器对象
     */
    override fun addPathReplaceInterceptor(interceptor: Any?) {
        getPathReplaceInterceptor(interceptor)?.let {
            addPathReplaceInterceptor(it)
        }
    }

    /**
     * 移除 Path 替换拦截器
     * @param interceptor Path 替换拦截器对象
     * @return `true` success, `false` fail
     */
    override fun removePathReplaceInterceptor(interceptor: Any?): Boolean {
        val pathInterceptor = getPathReplaceInterceptor(interceptor) ?: return false
        return removePathReplaceInterceptor(pathInterceptor)
    }

    /**
     * 新增路由替换拦截器
     * @param interceptor 路由替换拦截器对象
     */
    override fun addRouterReplaceInterceptor(interceptor: Any?) {
        getRouterReplaceInterceptor(interceptor)?.let {
            addRouterReplaceInterceptor(it)
        }
    }

    /**
     * 移除路由替换拦截器
     * @param interceptor 路由替换拦截器对象
     * @return `true` success, `false` fail
     */
    override fun removeRouterReplaceInterceptor(interceptor: Any?): Boolean {
        val routerInterceptor = getRouterReplaceInterceptor(interceptor) ?: return false
        return removeRouterReplaceInterceptor(routerInterceptor)
    }

    /**
     * 新增 Action 拦截器
     * @param action Action
     * @param interceptor Action 拦截器对象
     */
    override fun addActionInterceptor(
        action: String?,
        interceptor: Any?
    ) {
        TheRouter.addActionInterceptor(action, getActionInterceptor(interceptor))
    }

    /**
     * 移除 Action 拦截器
     * @param action Action
     * @param interceptor Action 拦截器对象
     */
    override fun removeActionInterceptor(
        action: String?,
        interceptor: Any?
    ) {
        TheRouter.removeActionInterceptor(action, getActionInterceptor(interceptor))
    }

    /**
     * 移除指定 Action 的全部拦截器
     * @param action Action
     */
    override fun removeAllInterceptorForKey(action: String?) {
        TheRouter.removeAllInterceptorForKey(action)
    }

    /**
     * 移除指定拦截器 ( 所有 Action 共用 )
     * @param interceptor Action 拦截器对象
     */
    override fun removeAllInterceptorForValue(interceptor: Any?) {
        TheRouter.removeAllInterceptorForValue(getActionInterceptor(interceptor))
    }

    /**
     * 新增 Autowired 注解解析器
     * @param parser Autowired 解析器对象
     */
    override fun addAutowiredParser(parser: Any?) {
        getAutowiredParser(parser)?.let {
            TheRouter.addAutowiredParser(it)
        }
    }

    // =================
    // = 构建 Navigator =
    // =================

    /**
     * 通过 Path 构建 Navigator ( 不跳转 )
     * @param path 路由 Path / Url
     * @return [Navigator]
     */
    override fun build(path: String?): Navigator {
        return TheRouter.build(path)
    }

    /**
     * 通过 Path 构建 Navigator ( 不跳转 )
     * @param item Router 参数
     * @return [Navigator]
     */
    override fun build(item: RouterItem?): Navigator {
        val navigator = TheRouter.build(item?.path())
        applyRouterItem(navigator, item)
        return navigator
    }

    /**
     * 通过 Intent 构建 Navigator ( 不跳转 )
     * @param intent Intent
     * @return [Navigator]
     */
    override fun build(intent: Intent?): Navigator {
        return TheRouter.build(intent ?: Intent())
    }

    // ====================
    // = Navigator 句柄操作 =
    // ====================

    /**
     * 获取带参数的完整 url
     * @param navigator Navigator 对象
     * @return 完整 url
     */
    override fun getUrlWithParams(navigator: Any?): String? {
        return getNavigator(navigator)?.getUrlWithParams()
    }

    /**
     * 设置 pending 等待路由表初始化
     * @param navigator Navigator 对象
     * @return Navigator 对象
     */
    override fun pending(navigator: Any?): Any? {
        getNavigator(navigator)?.pending()
        return navigator
    }

    /**
     * 设置 Int 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withInt(
        navigator: Any?,
        key: String?,
        value: Int
    ): Any? {
        getNavigator(navigator)?.withInt(key, value)
        return navigator
    }

    /**
     * 设置 Long 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withLong(
        navigator: Any?,
        key: String?,
        value: Long
    ): Any? {
        getNavigator(navigator)?.withLong(key, value)
        return navigator
    }

    /**
     * 设置 Double 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withDouble(
        navigator: Any?,
        key: String?,
        value: Double
    ): Any? {
        getNavigator(navigator)?.withDouble(key, value)
        return navigator
    }

    /**
     * 设置 Float 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withFloat(
        navigator: Any?,
        key: String?,
        value: Float
    ): Any? {
        getNavigator(navigator)?.withFloat(key, value)
        return navigator
    }

    /**
     * 设置 Char 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withChar(
        navigator: Any?,
        key: String?,
        value: Char
    ): Any? {
        getNavigator(navigator)?.withChar(key, value)
        return navigator
    }

    /**
     * 设置 Byte 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withByte(
        navigator: Any?,
        key: String?,
        value: Byte
    ): Any? {
        getNavigator(navigator)?.withByte(key, value)
        return navigator
    }

    /**
     * 设置 Boolean 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withBoolean(
        navigator: Any?,
        key: String?,
        value: Boolean
    ): Any? {
        getNavigator(navigator)?.withBoolean(key, value)
        return navigator
    }

    /**
     * 设置 String 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withString(
        navigator: Any?,
        key: String?,
        value: String?
    ): Any? {
        getNavigator(navigator)?.withString(key, value)
        return navigator
    }

    /**
     * 设置 Serializable 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withSerializable(
        navigator: Any?,
        key: String?,
        value: Serializable?
    ): Any? {
        getNavigator(navigator)?.withSerializable(key, value)
        return navigator
    }

    /**
     * 设置 Parcelable 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withParcelable(
        navigator: Any?,
        key: String?,
        value: Parcelable?
    ): Any? {
        getNavigator(navigator)?.withParcelable(key, value)
        return navigator
    }

    /**
     * 设置 Object 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withObject(
        navigator: Any?,
        key: String?,
        value: Any?
    ): Any? {
        if (key.isNullOrEmpty() || value == null) return navigator
        getNavigator(navigator)?.withObject(key, value)
        return navigator
    }

    /**
     * 设置 Bundle 参数
     * @param navigator Navigator 对象
     * @param key 参数 key
     * @param value 参数值
     * @return Navigator 对象
     */
    override fun withBundle(
        navigator: Any?,
        key: String?,
        value: Bundle?
    ): Any? {
        getNavigator(navigator)?.withBundle(key, value)
        return navigator
    }

    /**
     * 设置 Bundle 参数 ( 合并到 Navigator )
     * @param navigator Navigator 对象
     * @param value 参数 Bundle
     * @return Navigator 对象
     */
    override fun with(
        navigator: Any?,
        value: Bundle?
    ): Any? {
        getNavigator(navigator)?.with(value)
        return navigator
    }

    /**
     * 追加 Intent Flags
     * @param navigator Navigator 对象
     * @param flags Intent Flags
     * @return Navigator 对象
     */
    override fun addFlags(
        navigator: Any?,
        flags: Int
    ): Any? {
        getNavigator(navigator)?.addFlags(flags)
        return navigator
    }

    /**
     * 设置 Intent Flags
     * @param navigator Navigator 对象
     * @param flags Intent Flags
     * @return Navigator 对象
     */
    override fun withFlags(
        navigator: Any?,
        flags: Int
    ): Any? {
        getNavigator(navigator)?.withFlags(flags)
        return navigator
    }

    /**
     * 设置 Activity Options Bundle
     * @param navigator Navigator 对象
     * @param optionsCompat Options Bundle
     * @return Navigator 对象
     */
    override fun withOptionsCompat(
        navigator: Any?,
        optionsCompat: Bundle?
    ): Any? {
        getNavigator(navigator)?.withOptionsCompat(optionsCompat)
        return navigator
    }

    /**
     * 设置进入动画资源 id
     * @param navigator Navigator 对象
     * @param animResId 动画资源 id
     * @return Navigator 对象
     */
    override fun withInAnimation(
        navigator: Any?,
        animResId: Int
    ): Any? {
        getNavigator(navigator)?.withInAnimation(animResId)
        return navigator
    }

    /**
     * 设置退出动画资源 id
     * @param navigator Navigator 对象
     * @param animResId 动画资源 id
     * @return Navigator 对象
     */
    override fun withOutAnimation(
        navigator: Any?,
        animResId: Int
    ): Any? {
        getNavigator(navigator)?.withOutAnimation(animResId)
        return navigator
    }

    /**
     * 创建 Intent
     * @param navigator Navigator 对象
     * @param context Context 对象
     * @return Intent
     */
    override fun createIntent(
        navigator: Any?,
        context: Any?
    ): Intent? {
        val navigatorObj = getNavigator(navigator) ?: return null
        return try {
            navigatorObj.createIntent(getContext(context))
        } catch (_: Exception) {
            null
        }
    }

    /**
     * 创建 Fragment
     * @param navigator Navigator 对象
     * @return Fragment 实例
     */
    override fun <T : Any> createFragment(navigator: Any?): T? {
        val navigatorObj = getNavigator(navigator) ?: return null
        return try {
            navigatorObj.createFragment()
        } catch (_: Exception) {
            null
        }
    }

    /**
     * 执行路由跳转
     * @param navigator Navigator 对象
     * @param item Router 参数 ( 可为 null, 用于 context / fragment / requestCode / callback )
     */
    override fun navigation(
        navigator: Any?,
        item: RouterItem?
    ) {
        val navigatorObj = getNavigator(navigator) ?: return
        val context = getContext(item?.context())
        val fragment = getFragment(item?.fragment())
        val requestCode = item?.requestCode() ?: RouterConst.UNSET
        val callback = wrapNavigationCallback(item?.navigationCallback())
        try {
            if (requestCode != RouterConst.UNSET) {
                // 统一走四参 overload, 避免 Context? / Fragment? 三参重载歧义
                if (callback != null) {
                    navigatorObj.navigation(context, fragment, requestCode, callback)
                } else {
                    navigatorObj.navigation(context, fragment, requestCode)
                }
                return
            }
            when {
                fragment != null && callback != null -> {
                    navigatorObj.navigation(fragment, callback)
                }

                fragment != null -> {
                    navigatorObj.navigation(fragment)
                }

                context != null && callback != null -> {
                    navigatorObj.navigation(context, callback)
                }

                context != null -> {
                    navigatorObj.navigation(context)
                }

                callback != null -> {
                    navigatorObj.navigation(getApplicationContext(), callback)
                }

                else -> {
                    navigatorObj.navigation()
                }
            }
        } catch (_: Exception) {
        }
    }

    /**
     * 执行 Action
     * @param navigator Navigator 对象
     * @param context Context 对象
     * @return `true` success, `false` fail
     */
    override fun action(
        navigator: Any?,
        context: Any?
    ): Boolean {
        val navigatorObj = getNavigator(navigator) ?: return false
        val ctx = getContext(context) ?: return false
        return try {
            navigatorObj.action(ctx)
            true
        } catch (_: Exception) {
            false
        }
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 应用 Router Config
     * @param config Router Config
     * @return `true` success, `false` fail
     */
    protected open fun applyConfig(config: RouterConfig): Boolean {
        config.debug()?.let {
            TheRouter.isDebug = it
        }
        config.autoInit()?.let {
            theRouterUseAutoInit = it
        }
        val asyncInit = config.asyncInitRouterInject() ?: true
        if (!theRouterInited() && config.autoInit() == false) {
            TheRouter.init(null, asyncInit)
        }
        return true
    }

    /**
     * 将 RouterItem 参数应用到 Navigator
     * @param navigator Navigator
     * @param item Router 参数
     */
    protected open fun applyRouterItem(
        navigator: Navigator,
        item: RouterItem?
    ) {
        item ?: return
        if (item.pending()) {
            navigator.pending()
        }
        item.extras()?.let { extras ->
            navigator.with(extras)
        }
        item.objectParams()?.forEach { (key, value) ->
            navigator.withObject(key, value)
        }
        val flags = item.flags()
        if (flags != RouterConst.UNSET) {
            navigator.withFlags(flags)
        }
        val animIn = item.animIn()
        if (animIn != RouterConst.UNSET) {
            navigator.withInAnimation(animIn)
        }
        val animOut = item.animOut()
        if (animOut != RouterConst.UNSET) {
            navigator.withOutAnimation(animOut)
        }
        getOptionsCompat(item.optionsCompat())?.let {
            navigator.withOptionsCompat(it)
        }
        getUri(item.intentData())?.let {
            navigator.setData(it)
        }
        item.intentIdentifier()?.let {
            navigator.setIdentifier(it)
        }
        getClipData(item.intentClipData())?.let {
            navigator.setClipData(it)
        }
    }

    /**
     * 包装跳转结果回调
     * @param callback 跳转结果回调
     * @return [NavigationCallback]
     */
    protected open fun wrapNavigationCallback(
        callback: IRouterEngine.OnNavigationCallback?
    ): NavigationCallback? {
        callback ?: return null
        return object : NavigationCallback() {
            override fun onFound(navigator: Navigator) {
                callback.onFound(navigator)
            }

            override fun onLost(
                navigator: Navigator,
                requestCode: Int
            ) {
                callback.onLost(navigator, requestCode)
            }

            override fun onArrival(navigator: Navigator) {
                callback.onArrival(navigator)
            }

            override fun onActivityCreated(
                navigator: Navigator,
                activity: Activity
            ) {
                callback.onActivityCreated(navigator, activity)
            }
        }
    }

    /**
     * 获取 Navigator
     * @param navigator Navigator Item
     * @return [Navigator]
     */
    protected open fun getNavigator(navigator: Any?): Navigator? {
        return navigator as? Navigator
    }

    /**
     * 获取 Context
     * @param context Context Item
     * @return [Context]
     */
    protected open fun getContext(context: Any?): Context? {
        return context as? Context
    }

    /**
     * 获取 Fragment
     * @param fragment Fragment Item
     * @return [Fragment]
     */
    protected open fun getFragment(fragment: Any?): Fragment? {
        return fragment as? Fragment
    }

    /**
     * 获取 Uri
     * @param uri Uri Item
     * @return [Uri]
     */
    protected open fun getUri(uri: Any?): Uri? {
        return uri as? Uri
    }

    /**
     * 获取 ClipData
     * @param clipData ClipData Item
     * @return [ClipData]
     */
    protected open fun getClipData(clipData: Any?): ClipData? {
        return clipData as? ClipData
    }

    /**
     * 获取 Options Bundle
     * @param options Options Item
     * @return [Bundle]
     */
    protected open fun getOptionsCompat(options: Any?): Bundle? {
        return options as? Bundle
    }

    /**
     * 获取 Path 修改器
     * @param handle Path 修改器 Item
     * @return [NavigatorPathFixHandle]
     */
    protected open fun getNavigatorPathFixHandle(handle: Any?): NavigatorPathFixHandle? {
        return handle as? NavigatorPathFixHandle
    }

    /**
     * 获取 Path 替换拦截器
     * @param interceptor Path 替换拦截器 Item
     * @return [PathReplaceInterceptor]
     */
    protected open fun getPathReplaceInterceptor(
        interceptor: Any?
    ): PathReplaceInterceptor? {
        return interceptor as? PathReplaceInterceptor
    }

    /**
     * 获取路由替换拦截器
     * @param interceptor 路由替换拦截器 Item
     * @return [RouterReplaceInterceptor]
     */
    protected open fun getRouterReplaceInterceptor(
        interceptor: Any?
    ): RouterReplaceInterceptor? {
        return interceptor as? RouterReplaceInterceptor
    }

    /**
     * 获取 Action 拦截器
     * @param interceptor Action 拦截器 Item
     * @return [ActionInterceptor]
     */
    protected open fun getActionInterceptor(interceptor: Any?): ActionInterceptor? {
        return interceptor as? ActionInterceptor
    }

    /**
     * 获取 Autowired 解析器
     * @param parser Autowired 解析器 Item
     * @return [AutowiredParser]
     */
    protected open fun getAutowiredParser(parser: Any?): AutowiredParser? {
        return parser as? AutowiredParser
    }
}