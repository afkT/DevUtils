package afkt.httpmanager.use.feature.media.data.api

import afkt.httpmanager.use.feature.media.data.model.MovieDetailBean
import afkt.httpmanager.use.feature.media.data.model.PhotoBean
import afkt.httpmanager.use.network.model.AppResponse
import retrofit2.http.GET

/**
 * detail: Media API Service
 * @author Ttt
 */
interface MediaService {

    /**
     * 获取摄影图片列表
     */
    @GET("assets/photos.json")
    suspend fun getPhotoList(): AppResponse<List<PhotoBean>>

    /**
     * 获取电影详情信息
     */
    @GET("assets/movie_detail.json")
    suspend fun getMovieDetail(): AppResponse<MovieDetailBean>
}