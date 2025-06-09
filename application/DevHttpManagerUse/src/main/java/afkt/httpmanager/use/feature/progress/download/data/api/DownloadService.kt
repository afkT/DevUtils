package afkt.httpmanager.use.feature.progress.download.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * detail: Download API Service
 * @author Ttt
 */
interface DownloadService {

    /**
     * 下载文件
     */
    @GET
    @Streaming
    suspend fun downloadFile(
        @Url url: String
    ): ResponseBody
}