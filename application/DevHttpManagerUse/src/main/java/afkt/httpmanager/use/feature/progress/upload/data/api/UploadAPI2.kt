package afkt.httpmanager.use.feature.progress.upload.data.api

import afkt.httpmanager.use.network.HttpCore
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * detail: Upload API
 * @author Ttt
 * 未使用 DevHttpManager
 */
class UploadAPI2 private constructor() {

    companion object {

        private val instance: UploadAPI2 by lazy { UploadAPI2() }

        fun api(): UploadService {
            return instance.api()
        }
    }

    // =================
    // = UploadService =
    // =================

    @Volatile
    private var mUploadService: UploadService? = null

    private fun api(): UploadService {
        if (mUploadService == null) {
            synchronized(UploadService::class.java) {
                if (mUploadService == null) {
                    val retrofit = HttpCore.createRetrofitBuilder(
                        httpUrl = "https://github.com/afkT/DevUtils/".toHttpUrl(),
                        okHttp = OkHttpClient.Builder().apply {
                            // 全局的响应超时时间 ( 秒 )
                            callTimeout(9999, TimeUnit.SECONDS)
                            // 全局的读取超时时间
                            readTimeout(9999, TimeUnit.SECONDS)
                            // 全局的写入超时时间
                            writeTimeout(9999, TimeUnit.SECONDS)
                            // 全局的连接超时时间
                            connectTimeout(9999, TimeUnit.SECONDS)
                        }
                    ).build()
                    mUploadService = retrofit.create(UploadService::class.java)
                }
            }
        }
        return mUploadService as UploadService
    }
}