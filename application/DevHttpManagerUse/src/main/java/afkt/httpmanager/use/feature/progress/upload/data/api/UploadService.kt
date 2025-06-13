package afkt.httpmanager.use.feature.progress.upload.data.api

import afkt.httpmanager.use.feature.progress.upload.data.model.UploadBean
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

/**
 * detail: Upload API Service
 * @author Ttt
 */
interface UploadService {

    /**
     * 上传文件
     */
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Body body: RequestBody
    ): UploadBean
}