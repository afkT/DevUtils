package afkt.httpmanager.use.feature.progress.download.data.api

import afkt.httpmanager.use.network.HttpCore
import afkt.httpmanager.use.network.helper.ProgressHelper
import dev.DevHttpManager
import dev.expand.engine.log.log_isPrintLog
import dev.http.manager.RetrofitBuilder
import dev.http.manager.RetrofitOperation
import dev.http.progress.Progress
import dev.http.progress.ProgressOperation
import dev.utils.app.HandlerUtils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * detail: Download API
 * @author Ttt
 * RetrofitManager ( Retrofit 多 BaseUrl 管理 )【搭配】ProgressManager ( Progress 监听 ) 使用
 */
class DownloadAPI private constructor() {

    companion object {

        // Download API Tag Key
        val NAME = DownloadAPI::class.java.simpleName

        private val instance: DownloadAPI by lazy { DownloadAPI() }

        fun api(): DownloadService {
            return instance.api()
        }

        fun operation(): RetrofitOperation {
            return instance.operation()
        }

        fun progress(): ProgressOperation {
            return instance.progress()
        }
    }

    // ===================
    // = DownloadService =
    // ===================

    @Volatile
    private var mDownloadService: DownloadService? = null

    private fun api(): DownloadService {
        if (mDownloadService == null) {
            synchronized(DownloadService::class.java) {
                if (mDownloadService == null) {
                    createAPI()
                }
            }
        }
        return mDownloadService as DownloadService
    }

    private fun createAPI() {
        mDownloadService = operation().create(
            DownloadService::class.java
        )
    }

    // ==================
    // = DevEnvironment =
    // ==================

    private fun apiBaseUrl(): HttpUrl {
        /**
         * 如果未使用 DevEnvironment 库
         * 可以通过数据库、MMKV、静态变量等方式进行修改、读取等
         */
        return "https://github.com/afkT/DevUtils/".toHttpUrl()
    }

    // =====================
    // = ProgressOperation =
    // =====================

    /**
     * 对外提供操作对象
     * @return RetrofitOperation
     */
    private fun progress(): ProgressOperation {
        return mProgressOperation
    }

    // Progress Operation
    private val mProgressOperation: ProgressOperation by lazy {
//        // 获取全局默认 Progress Operation 操作对象 ( 默认监听上下行操作对象 )
//        DevHttpManager.PM.getDefault()
//        // 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 )
//        DevHttpManager.PM.putOperationTypeAll(NAME)
//        // 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )
//        DevHttpManager.PM.putOperationTypeRequest(NAME)
//        // 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
//        DevHttpManager.PM.putOperationTypeResponse(NAME)

        // 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
        DevHttpManager.PM.putOperationTypeResponse(NAME).apply {

            // ===============
            // = 以下配置非必须 =
            // ===============

            // 设置 Progress Operation 实现方式类型 ( 默认使用 PLAN_A )
            setPlanType(ProgressOperation.PLAN_A)
            // 设置回调 UI 线程通知 Handler
            setHandler(HandlerUtils.getMainHandler())
            // 设置回调刷新时间 ( 毫秒 )
            setRefreshTime(100L)
            // 设置全局 Progress Callback
            setCallback(object : Progress.Callback {
                override fun onStart(progress: Progress) {
                    ProgressHelper.startDownload(NAME, progress)
                }

                override fun onProgress(progress: Progress) {
                    ProgressHelper.ingDownload(NAME, progress)
                }

                override fun onError(progress: Progress) {
                    ProgressHelper.errorDownload(NAME, progress)
                }

                override fun onFinish(progress: Progress) {
                    ProgressHelper.successDownload(NAME, progress)
                }

                override fun onEnd(progress: Progress) {
                    ProgressHelper.endDownload(NAME, progress)
                }
            })
        }
    }

    // =====================
    // = RetrofitOperation =
    // =====================

    /**
     * 对外提供操作对象
     * @return RetrofitOperation
     */
    private fun operation(): RetrofitOperation {
        return mOperation
    }

    // Retrofit Operation
    private val mOperation: RetrofitOperation by lazy {
        DevHttpManager.RM.putRetrofitBuilder(
            NAME, mRetrofitBuilder
        )
    }

    // ===================
    // = RetrofitBuilder =
    // ===================

    // Retrofit Builder 接口
    private val mRetrofitBuilder: RetrofitBuilder by lazy {
        object : RetrofitBuilder {

            /**
             * 创建 Retrofit Builder
             * @param oldRetrofit 上一次构建的 Retrofit
             * @param httpUrl 构建使用指定 baseUrl
             * @param okHttp OkHttpClient 构建全局复用
             * @return Retrofit.Builder
             */
            override fun createRetrofitBuilder(
                oldRetrofit: Retrofit?,
                httpUrl: HttpUrl?,
                okHttp: OkHttpClient.Builder?
            ): Retrofit.Builder {
                if (log_isPrintLog()) {
                    HttpCore.logProcess(
                        NAME, "【Custom】RetrofitBuilder.createRetrofitBuilder()"
                    )
                }
                val okHttpBuilder = okHttp ?: OkHttpClient.Builder()
                // 监听下载进度进行拦截器包装 ( 必须调用 )
                val newOKHttp = mProgressOperation.wrap(okHttpBuilder)

                return HttpCore.createRetrofitBuilder(
                    httpUrl = httpUrl ?: apiBaseUrl(),
                    okHttp = newOKHttp
                )
            }

            // ==========
            // = 通知事件 =
            // ==========

            /**
             * 重新构建前调用
             * @param key String
             * @param oldRetrofit 上一次构建的 Retrofit
             * 在 [createRetrofitBuilder] 之前调用
             */
            override fun onResetBefore(
                key: String,
                oldRetrofit: Retrofit?
            ) {
                if (log_isPrintLog()) {
                    HttpCore.logProcess(
                        NAME, "【Custom】RetrofitBuilder.onResetBefore()"
                    )
                }
            }

            /**
             * 重新构建后调用
             * @param key String
             * @param newRetrofit 重新构建的 Retrofit 对象
             * 在 [createRetrofitBuilder] 之后调用
             */
            override fun onReset(
                key: String,
                newRetrofit: Retrofit?
            ) {
                if (log_isPrintLog()) {
                    HttpCore.logProcess(
                        NAME, "【Custom】RetrofitBuilder.onReset()"
                    )
                }
                // 重新构建后创建新的代理对象
                createAPI()
            }
        }
    }
}