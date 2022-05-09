package dev.http.progress.operation

import android.os.Handler
import dev.http.progress.Progress
import dev.http.progress.toExtras
import dev.http.progress.wrapRequestBody
import dev.http.progress.wrapResponseBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * detail: 基础 Progress Operation 通用实现类
 * @author Ttt
 */
internal abstract class BaseOperation constructor(
    private val key: String,
    private val isDefault: Boolean,
    private val type: Int
) : IOperation {

    // 是否废弃不用 ( true 的情况下不会拦截网络且任何操作都不会赋值 )
    private var mDeprecated = false

    // 回调刷新时间 ( 毫秒 ) - 小于等于 0 则每次进度变更都进行通知
    private var mRefreshTime: Long = Progress.REFRESH_TIME

    // 全局 Progress.Callback
    private var mCallback: Progress.Callback? = null

    // 回调 UI 线程通知 ( 如果为 null 则会非 UI 线程通知 )
    private var mHandler: Handler? = null

    // 是否推荐请求一次 ( isOneShot() return 使用 ) 避免拦截器调用 writeTo 导致多次触发
    private var mOneShot: Boolean = true

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
        // 防止多次添加
        if (builder.interceptors().contains(innerProgressInterceptor)) {
            return builder
        }
        builder.addInterceptor(innerProgressInterceptor)
        return builder
    }

    // =============
    // = 对外公开方法 =
    // =============

    override fun isDeprecated(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isDefault(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTypeAll(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTypeRequest(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTypeResponse(): Boolean {
        TODO("Not yet implemented")
    }

    // ===========
    // = get/set =
    // ===========

    override fun getPlanType(): Int {
        TODO("Not yet implemented")
    }

    override fun setPlanType(planType: Int): IOperation {
        TODO("Not yet implemented")
    }

    // =

    override fun getRefreshTime(): Long {
        TODO("Not yet implemented")
    }

    override fun setRefreshTime(refreshTime: Long): IOperation {
        TODO("Not yet implemented")
    }

    override fun resetRefreshTime(): IOperation {
        TODO("Not yet implemented")
    }

    // =

    override fun getCallback(): Progress.Callback? {
        TODO("Not yet implemented")
    }

    override fun setCallback(callback: Progress.Callback?): IOperation {
        TODO("Not yet implemented")
    }

    override fun removeCallback(): IOperation {
        TODO("Not yet implemented")
    }

    // =

    override fun getHandler(): Handler? {
        TODO("Not yet implemented")
    }

    override fun setHandler(handler: Handler?): IOperation {
        TODO("Not yet implemented")
    }

    override fun resetHandler(): IOperation {
        TODO("Not yet implemented")
    }

    // =

    override fun getOneShot(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOneShot(oneShot: Boolean): IOperation {
        TODO("Not yet implemented")
    }

    override fun resetOneShot(): IOperation {
        TODO("Not yet implemented")
    }

    // ====================
    // = 操作方法 - 对外公开 =
    // ====================

    override fun removeSelfFromManager() {
        TODO("Not yet implemented")
    }

    override fun recycleListener(
        progress: Progress,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    // ====================
    // = Request Listener =
    // ====================

    override fun addRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearRequestListener(url: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearRequestListener(progress: Progress?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeRequestListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeRequestListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    // =====================
    // = Response Listener =
    // =====================

    override fun addResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearResponseListener(url: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun clearResponseListener(progress: Progress?): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeResponseListener(
        url: String,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeResponseListener(
        progress: Progress?,
        callback: Progress.Callback
    ): Boolean {
        TODO("Not yet implemented")
    }

    // ============
    // = abstract =
    // ============

    /**
     * 获取对应方案回调实现
     * @param extras 额外携带信息
     * @return Progress.Callback
     */
    internal abstract fun getPlanCallback(extras: Progress.Extras?): Progress.Callback

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 标记废弃不用状态
     * @return IOperation
     */
    internal fun markDeprecated(): IOperation {
        mDeprecated = true
        mCallback = null
        mHandler = null
        return this
    }

    /**
     * detail: 内部 Progress 拦截器
     * @author Ttt
     * DevHttpManager 库内部包装, 拦截监听上行 ( 上传、请求 )、下行 ( 下载、响应 ) 进度
     */
    private val innerProgressInterceptor: Interceptor by lazy {
        if (isTypeRequest()) {
            // 监听上行类型
            Interceptor { chain ->
                if (mDeprecated) {
                    chain.proceed(chain.request())
                } else {
                    val request = chain.request()
                    val extras = request.toExtras()
                    val wrapRequest = request.wrapRequestBody(
                        callback = getPlanCallback(extras),
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        shouldOneShot = mOneShot,
                        extras = extras
                    )
                    chain.proceed(wrapRequest)
                }
            }
        } else if (isTypeResponse()) {
            // 监听下行类型
            Interceptor { chain ->
                if (mDeprecated) {
                    chain.proceed(chain.request())
                } else {
                    val request = chain.request()
                    val extras = request.toExtras()
                    val response = chain.proceed(request)
                    response.wrapResponseBody(
                        callback = getPlanCallback(extras),
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        extras = extras
                    )
                }
            }
        } else {
            // 监听上下行类型 ( 默认 )
            Interceptor { chain ->
                if (mDeprecated) {
                    chain.proceed(chain.request())
                } else {
                    val request = chain.request()
                    val extras = request.toExtras()
                    val wrapRequest = request.wrapRequestBody(
                        callback = getPlanCallback(extras),
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        shouldOneShot = mOneShot,
                        extras = extras
                    )
                    val response = chain.proceed(wrapRequest)
                    response.wrapResponseBody(
                        callback = getPlanCallback(extras),
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        extras = extras
                    )
                }
            }
        }
    }
}