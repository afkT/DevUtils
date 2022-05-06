package dev.http.progress

import android.os.Handler
import dev.DevUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient

/**
 * detail: Progress Operation
 * @author Ttt
 * 通过 key 区分, 支持不同组件模块化网络进度监听
 * 支持上行、下行、上下行三种类型, 内部拦截器监听回调
 */
class ProgressOperation private constructor(
    private val key: String,
    private val isDefault: Boolean,
    private val type: Int
) {

    companion object {

        // ==================
        // = 内部拦截器监听类型 =
        // ==================

        // 监听上下行类型
        internal const val TYPE_ALL = 0

        // 监听上行 ( 上传、请求 )
        internal const val TYPE_REQUEST = 1

        // 监听下行 ( 下载、响应 )
        internal const val TYPE_RESPONSE = 2

        // ==========
        // = create =
        // ==========

        /**
         * 创建 Progress Operation
         * @param key Key
         * @param isDefault 是否默认操作对象
         * @param type 内部拦截器监听类型
         * @return Progress Operation
         */
        internal fun get(
            key: String,
            isDefault: Boolean,
            type: Int
        ): ProgressOperation {
            return ProgressOperation(key, isDefault, type)
        }
    }

    // 日志 TAG
    val TAG = ProgressOperation::class.java.simpleName

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

    // ==========
    // = 操作方法 =
    // ==========

    /**
     * 进行拦截器包装
     * @param builder Builder
     * @return ProgressOperation
     */
    fun wrap(builder: OkHttpClient.Builder): ProgressOperation {
        // 防止多次添加
        if (builder.interceptors().contains(innerProgressInterceptor)) {
            return this
        }
        builder.addInterceptor(innerProgressInterceptor)
        return this
    }

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 是否废弃不用状态
     * @return `true` yes, `false` no
     */
    fun isDeprecated(): Boolean {
        return mDeprecated
    }

    /**
     * 是否默认操作对象
     * @return `true` yes, `false` no
     */
    fun isDefault(): Boolean {
        return isDefault
    }

    /**
     * 是否监听上下行
     * @return `true` yes, `false` no
     */
    fun isTypeAll(): Boolean {
        return type == TYPE_ALL
    }

    /**
     * 是否监听上行
     * @return `true` yes, `false` no
     */
    fun isTypeRequest(): Boolean {
        return type == TYPE_REQUEST
    }

    /**
     * 是否监听下行
     * @return `true` yes, `false` no
     */
    fun isTypeResponse(): Boolean {
        return type == TYPE_RESPONSE
    }

    // =

    /**
     * 获取回调刷新时间 ( 毫秒 )
     * @return 回调刷新时间 ( 毫秒 )
     */
    fun getRefreshTime(): Long {
        return mRefreshTime
    }

    /**
     * 设置回调刷新时间 ( 毫秒 )
     * @param refreshTime 回调刷新时间 ( 毫秒 )
     * @return ProgressOperation
     */
    fun setRefreshTime(refreshTime: Long): ProgressOperation {
        if (mDeprecated) return this
        mRefreshTime = refreshTime.coerceAtLeast(0)
        return this
    }

    /**
     * 重置回调刷新时间 ( 毫秒 )
     * @return ProgressOperation
     */
    fun resetRefreshTime(): ProgressOperation {
        return setRefreshTime(Progress.REFRESH_TIME)
    }

    // =

    /**
     * 获取全局 Progress Callback
     * @return Progress Callback
     */
    fun getCallback(): Progress.Callback? {
        return mCallback
    }

    /**
     * 设置全局 Progress Callback
     * @param callback Progress Callback
     * @return ProgressOperation
     */
    fun setCallback(callback: Progress.Callback?): ProgressOperation {
        if (mDeprecated) return this
        mCallback = callback
        return this
    }

    /**
     * 移除全局 Progress Callback
     * @return ProgressOperation
     */
    fun removeCallback(): ProgressOperation {
        return setCallback(null)
    }

    // =

    /**
     * 获取回调 UI 线程通知 Handler
     * @return 回调 UI 线程通知 Handler
     */
    fun getHandler(): Handler? {
        return mHandler
    }

    /**
     * 设置回调 UI 线程通知 Handler
     * @param handler 回调 UI 线程通知 Handler
     * @return ProgressOperation
     */
    fun setHandler(handler: Handler?): ProgressOperation {
        if (mDeprecated) return this
        mHandler = handler
        return this
    }

    /**
     * 重置回调 UI 线程通知 Handler
     * @return ProgressOperation
     */
    fun resetHandler(): ProgressOperation {
        return setHandler(DevUtils.getHandler())
    }

    // =

    /**
     * 获取 Body 只请求一次开关配置
     * @return Body 只请求一次开关配置
     */
    fun getOneShot(): Boolean {
        return mOneShot
    }

    /**
     * 设置 Body 只请求一次开关配置
     * @param oneShot Body 只请求一次开关配置
     * @return ProgressOperation
     */
    fun setOneShot(oneShot: Boolean): ProgressOperation {
        if (mDeprecated) return this
        mOneShot = oneShot
        return this
    }

    /**
     * 重置 Body 只请求一次开关配置
     * @return ProgressOperation
     */
    fun resetOneShot(): ProgressOperation {
        return setOneShot(true)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 标记废弃不用状态
     * @return ProgressOperation
     */
    internal fun markDeprecated(): ProgressOperation {
        mDeprecated = true
        mCallback = null
        mHandler = null
        return this
    }

    // =

    /**
     * detail: 内部 Progress 回调
     * @author Ttt
     * 只要是 DevHttpManager 库内部创建的 [ProgressRequestBody]、[ProgressResponseBody]
     * 都会统一使用该回调接口实现
     */
    private val innerCallback: Progress.Callback by lazy {
        object : Progress.Callback {
            override fun onStart(progress: Progress) {
                if (mDeprecated) return
                mCallback?.onStart(progress)
            }

            override fun onProgress(progress: Progress) {
                if (mDeprecated) return
                mCallback?.onProgress(progress)
            }

            override fun onError(progress: Progress) {
                if (mDeprecated) return
                mCallback?.onError(progress)
            }

            override fun onFinish(progress: Progress) {
                if (mDeprecated) return
                mCallback?.onFinish(progress)
            }

            override fun onEnd(progress: Progress) {
                if (mDeprecated) return
                mCallback?.onEnd(progress)
            }
        }
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
                        callback = innerCallback,
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
                        callback = innerCallback,
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
                        callback = innerCallback,
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        shouldOneShot = mOneShot,
                        extras = extras
                    )
                    val response = chain.proceed(wrapRequest)
                    response.wrapResponseBody(
                        callback = innerCallback,
                        handler = mHandler,
                        refreshTime = mRefreshTime,
                        extras = extras
                    )
                }
            }
        }
    }

    // =
}