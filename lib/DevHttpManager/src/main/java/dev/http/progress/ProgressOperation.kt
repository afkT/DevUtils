package dev.http.progress

import android.os.Handler
import dev.http.progress.operation.BaseOperation
import dev.http.progress.operation.IOperation
import dev.http.progress.operation.OperationPlanA
import dev.http.progress.operation.OperationPlanB
import dev.utils.common.assist.url.UrlExtras
import okhttp3.OkHttpClient

/**
 * detail: Progress Operation
 * @author Ttt
 * 通过 key 区分, 支持不同组件模块化网络进度监听
 * 支持上行、下行、上下行三种类型, 内部拦截器监听回调
 * <p></p>
 * 注意事项:
 * 绑定监听事件是通过 url 进行绑定的
 * 例 url 为 https://www.abc.com/asd?type=1&abc=b
 * 会统一处理成 https://www.abc.com/asd 作为 key
 * 而其他信息通过 [Progress.Extras] 进行获取请求方法、请求头信息
 * 以及 url 携带参数解析 [Progress.Extras.getUrlExtras]
 * 具体参数信息拆分获取可通过 [UrlExtras] 进行获取
 * 并自行根据 Request 信息进行判断处理回调事件
 * <p></p>
 * 因通知回调功能支持方式不同, 选择的技术方案不同, 各有利弊。
 * 且提供切换实现方式方法 [setPlanType]
 *
 * 方式二:
 * 在创建 [wrapRequestBody]、[wrapResponseBody] 时, 创建一个新的 Callback
 * 并把 listener map 对应 url 监听的 Callback List toArray 传入进行通知使用
 * 优点: 可以对 List 进行弱引用处理, 会自动进行释放资源
 * 缺点: 对 listener map 新增 url add listener 在请求之后添加
 *      则会无法触发 ( 因为在请求拦截时候就已传入 Callback List toArray )
 *
 * 方式一 ( 默认 ):
 * 在创建 [wrapRequestBody]、[wrapResponseBody] 时, 使用统一回调 [innerCallback] 无需每次 new Callback
 * 在统一回调内获取 listener map 对应 url 监听的 Callback List 并进行通知
 * 优点: 支持实时 add listener 并且通知回调
 * 缺点: 实时通知可能因绑定顺序差异, 需自行根据 [Progress.Extras.getUrlExtras] 进行判断是否需要处理该通知
 *      listener map 中的 Callback 需要手动进行释放
 * 针对该缺点提供了两个解决方案
 * 1.[Progress.Callback] 提供 isAutoRecycle 方法 ( 默认销毁 ) 可自行判断是否需要销毁
 * 2.提供 [recycleListener] 方法可在 Callback onEnd 中直接调用释放资源无需实现逻辑
 */
class ProgressOperation private constructor(
    private val key: String,
    // 全局默认操作对象
    private val globalDefault: Boolean,
    // 内部拦截器监听类型
    private val type: Int,
) : IOperation {

    companion object {

        // ==================
        // = 内部拦截器监听类型 =
        // ==================

        // 监听上下行类型
        internal const val TYPE_ALL = 0

        // 监听上行
        internal const val TYPE_REQUEST = 1

        // 监听下行
        internal const val TYPE_RESPONSE = 2

        // =============
        // = 实现方式类型 =
        // =============

        // 实现方式一 ( 默认 )
        const val PLAN_A = 1

        // 实现方式二
        const val PLAN_B = 2

        // ==========
        // = create =
        // ==========

        /**
         * 创建 Progress Operation
         * @param key Key
         * @param globalDefault 全局默认操作对象
         * @param type 内部拦截器监听类型
         * @return Progress Operation
         */
        internal fun get(
            key: String,
            globalDefault: Boolean,
            type: Int
        ): ProgressOperation {
            return ProgressOperation(key, globalDefault, type)
        }
    }

    // 实现方式类型
    private var mPlanType: Int = PLAN_A

    // Progress Operation 方案实现
    private val IMPL: BaseOperation by lazy {
        when (mPlanType) {
            PLAN_B -> OperationPlanB(key, globalDefault, type)
            else -> OperationPlanA(key, globalDefault, type)
        }
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 设置 Progress Operation 实现方式类型
     * @param planType 实现方式类型 [ProgressOperation.PLAN_A]、[ProgressOperation.PLAN_B]
     * @return IOperation
     * 在没调用 IOperation 接口任何方法前, 调用该方法切换才有效
     */
    fun setPlanType(planType: Int): IOperation {
        mPlanType = when (planType) {
            PLAN_B -> planType
            else -> PLAN_A
        }
        return this
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 释放资源方法
     */
    internal fun recycle(): ProgressOperation {
        IMPL.markDeprecated()
        return this
    }

    // ==============
    // = IOperation =
    // ==============

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 进行拦截器包装 ( 必须调用 )
     * @param builder Builder
     * @return Builder
     * 必须进行调用, 否则无法进行拦截监听
     * 推荐在 OkHttpClient Builder 最后一步调用
     * 防止中间有其他拦截器导致获取为旧数据
     * 例:
     * val okhttpClient = wrap(builder).build()
     */
    override fun wrap(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return IMPL.wrap(builder)
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否已调用 wrap 方法
     * @return `true` yes, `false` no
     */
    override fun isUseWrap(): Boolean {
        return IMPL.isUseWrap()
    }

    /**
     * 是否废弃不用状态
     * @return `true` yes, `false` no
     */
    override fun isDeprecated(): Boolean {
        return IMPL.isDeprecated()
    }

    /**
     * 是否全局默认操作对象
     * @return `true` yes, `false` no
     */
    override fun isDefault(): Boolean {
        return IMPL.isDefault()
    }

    /**
     * 是否监听上下行
     * @return `true` yes, `false` no
     */
    override fun isTypeAll(): Boolean {
        return IMPL.isTypeAll()
    }

    /**
     * 是否监听上行
     * @return `true` yes, `false` no
     */
    override fun isTypeRequest(): Boolean {
        return IMPL.isTypeRequest()
    }

    /**
     * 是否监听下行
     * @return `true` yes, `false` no
     */
    override fun isTypeResponse(): Boolean {
        return IMPL.isTypeResponse()
    }

    // ===========
    // = get/set =
    // ===========

    /**
     * 获取 Progress Operation 实现方式类型
     * @return 实现方式类型
     */
    override fun getPlanType(): Int {
        return IMPL.getPlanType()
    }

    // =

    /**
     * 获取回调刷新时间 ( 毫秒 )
     * @return 回调刷新时间 ( 毫秒 )
     */
    override fun getRefreshTime(): Long {
        return IMPL.getRefreshTime()
    }

    /**
     * 设置回调刷新时间 ( 毫秒 )
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     * @return IOperation
     */
    override fun setRefreshTime(refreshTime: Long): IOperation {
        return IMPL.setRefreshTime(refreshTime)
    }

    /**
     * 重置回调刷新时间 ( 毫秒 )
     * @return IOperation
     */
    override fun resetRefreshTime(): IOperation {
        return IMPL.resetRefreshTime()
    }

    // =

    /**
     * 获取全局 Progress Callback
     * @return Progress Callback
     */
    override fun getCallback(): Progress.Callback? {
        return IMPL.getCallback()
    }

    /**
     * 设置全局 Progress Callback
     * @param callback Progress Callback
     * @return IOperation
     */
    override fun setCallback(callback: Progress.Callback?): IOperation {
        return IMPL.setCallback(callback)
    }

    /**
     * 移除全局 Progress Callback
     * @return IOperation
     */
    override fun removeCallback(): IOperation {
        return IMPL.removeCallback()
    }

    // =

    /**
     * 获取回调 UI 线程通知 Handler
     * @return 回调 UI 线程通知 Handler
     */
    override fun getHandler(): Handler? {
        return IMPL.getHandler()
    }

    /**
     * 设置回调 UI 线程通知 Handler
     * @param handler 回调 UI 线程通知 Handler
     * @return IOperation
     */
    override fun setHandler(handler: Handler?): IOperation {
        return IMPL.setHandler(handler)
    }

    /**
     * 重置回调 UI 线程通知 Handler
     * @return IOperation
     */
    override fun resetHandler(): IOperation {
        return IMPL.resetHandler()
    }

    // =

    /**
     * 获取 Body 只请求一次开关配置
     * @return `true` yes, `false` no
     */
    override fun getOneShot(): Boolean {
        return IMPL.getOneShot()
    }

    /**
     * 设置 Body 只请求一次开关配置
     * @param oneShot Body 只请求一次开关配置
     * @return IOperation
     */
    override fun setOneShot(oneShot: Boolean): IOperation {
        return IMPL.setOneShot(oneShot)
    }

    /**
     * 重置 Body 只请求一次开关配置
     * @return IOperation
     */
    override fun resetOneShot(): IOperation {
        return IMPL.resetOneShot()
    }

    // ====================
    // = 操作方法 - 对外公开 =
    // ====================

    /**
     * 移除自身在 Manager Map 中的对象值, 并且标记为废弃状态
     * 会释放所有数据、监听事件且不处理任何监听
     */
    override fun removeSelfFromManager() {
        return IMPL.removeSelfFromManager()
    }

    /**
     * 释放指定监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun recycleListener(
        progress: Progress,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.recycleListener(progress, callback)
    }

    // ====================
    // = Request Listener =
    // ====================

    /**
     * 添加指定 url 上行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun addRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.addRequestListener(url, callback)
    }

    /**
     * 清空指定 url 上行所有监听事件
     * @param url 请求 url
     * @return `true` success, `false` fail
     */
    override fun clearRequestListener(url: String): Boolean {
        return IMPL.clearRequestListener(url)
    }

    /**
     * 清空指定 url 上行所有监听事件
     * @param progress Progress
     * @return `true` success, `false` fail
     */
    override fun clearRequestListener(progress: Progress?): Boolean {
        return IMPL.clearRequestListener(progress)
    }

    /**
     * 移除指定 url 上行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.removeRequestListener(url, callback)
    }

    /**
     * 移除指定 url 上行监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeRequestListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.removeRequestListener(progress, callback)
    }

    // =====================
    // = Response Listener =
    // =====================

    /**
     * 添加指定 url 下行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun addResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.addResponseListener(url, callback)
    }

    /**
     * 清空指定 url 下行所有监听事件
     * @param url 请求 url
     * @return `true` success, `false` fail
     */
    override fun clearResponseListener(url: String): Boolean {
        return IMPL.clearResponseListener(url)
    }

    /**
     * 清空指定 url 下行所有监听事件
     * @param progress Progress
     * @return `true` success, `false` fail
     */
    override fun clearResponseListener(progress: Progress?): Boolean {
        return IMPL.clearResponseListener(progress)
    }

    /**
     * 移除指定 url 下行监听事件
     * @param url 请求 url
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.removeResponseListener(url, callback)
    }

    /**
     * 移除指定 url 下行监听事件
     * @param progress Progress
     * @param callback 上传、下载回调接口
     * @return `true` success, `false` fail
     */
    override fun removeResponseListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        return IMPL.removeResponseListener(progress, callback)
    }
}