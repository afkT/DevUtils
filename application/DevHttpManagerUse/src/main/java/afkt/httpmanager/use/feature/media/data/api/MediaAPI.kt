package afkt.httpmanager.use.feature.media.data.api

import afkt.httpmanager.use.network.HttpCore
import dev.DevHttpManager
import dev.environment.DevEnvironment
import dev.expand.engine.log.log_iTag
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

    // Media API Tag Key
    private val NAME = MediaAPI::class.java.simpleName

    companion object {

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
                    HttpCore.TAG.log_iTag(message = "[${NAME}] -【Custom】RetrofitBuilder.createRetrofitBuilder()")
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
                    HttpCore.TAG.log_iTag(message = "[${NAME}] -【Custom】RetrofitBuilder.onResetBefore()")
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
                    HttpCore.TAG.log_iTag(message = "[${NAME}] -【Custom】RetrofitBuilder.onReset()")
                }
                // 重新构建后创建新的代理对象
                createAPI()
            }
        }
    }
}