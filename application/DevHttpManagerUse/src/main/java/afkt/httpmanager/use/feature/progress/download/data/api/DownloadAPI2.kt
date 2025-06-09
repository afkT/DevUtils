package afkt.httpmanager.use.feature.progress.download.data.api

import afkt.httpmanager.use.network.HttpCore
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * detail: Download API
 * @author Ttt
 * 未使用 DevHttpManager
 */
class DownloadAPI2 private constructor() {

    companion object {

        private val instance: DownloadAPI2 by lazy { DownloadAPI2() }

        fun api(): DownloadService {
            return instance.api()
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
                    mDownloadService = retrofit.create(DownloadService::class.java)
                }
            }
        }
        return mDownloadService as DownloadService
    }
}