package afkt.httpmanager.use.feature.media.data.api

import afkt.httpmanager.use.network.HttpCore
import dev.DevHttpManager
import dev.environment.DevEnvironment
import dev.expand.engine.log.log_isPrintLog
import dev.http.manager.RetrofitBuilder
import dev.http.manager.RetrofitOperation
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * detail: Media API
 * @author Ttt
 */
class MediaAPI private constructor() {

    companion object {

        // Media API Tag Key
        val NAME = MediaAPI::class.java.simpleName

        private val instance: MediaAPI by lazy { MediaAPI() }

        fun api(): MediaService {
            return instance.api()
        }

        fun operation(): RetrofitOperation {
            return instance.operation()
        }
    }

    // ================
    // = MediaService =
    // ================

    @Volatile
    private var mMediaService: MediaService? = null

    private fun api(): MediaService {
        if (mMediaService == null) {
            synchronized(MediaService::class.java) {
                if (mMediaService == null) {
                    createAPI()
                }
            }
        }
        return mMediaService as MediaService
    }

    private fun createAPI() {
        mMediaService = operation().create(
            MediaService::class.java
        )
    }

    // ==================
    // = DevEnvironment =
    // ==================

    private fun apiBaseUrl(): HttpUrl {
        return DevEnvironment.getMediaEnvironmentValue(
            HttpCore.context()
        ).toHttpUrl()
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
                return HttpCore.createRetrofitBuilder(
                    httpUrl = httpUrl ?: apiBaseUrl(),
                    okHttp = okHttp ?: OkHttpClient.Builder()
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

// =======================
// = MediaAPI 切换环境方法 =
// =======================

/**
 * 设置 Media 模块 release environment baseUrl
 * @param reset 是否通知重新构建 Retrofit
 */
fun MediaAPI.Companion.updateReleaseEnvironment(reset: Boolean = true) {
    val newEnvironment = DevEnvironment.getMediaReleaseEnvironment()
    // 切换 Release 环境
    DevEnvironment.setMediaEnvironment(HttpCore.context(), newEnvironment)
    // 通知重新构建 Retrofit
    if (reset) operation().reset()
}

/**
 * 设置 Media 模块 debug environment baseUrl
 * @param reset 是否通知重新构建 Retrofit
 */
fun MediaAPI.Companion.updateDebugEnvironment(reset: Boolean = true) {
    DevEnvironment.getMediaModule().environments.forEach { bean ->
        if ("debug".equals(bean.name, true)) {
            val newEnvironment = bean
            // 切换环境
            DevEnvironment.setMediaEnvironment(HttpCore.context(), newEnvironment)
            // 通知重新构建 Retrofit
            if (reset) operation().reset()
        }
    }
}