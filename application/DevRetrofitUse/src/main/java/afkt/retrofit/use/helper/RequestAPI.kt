package afkt.retrofit.use.helper

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// ==============
// = 请求 API 类 =
// ==============

/**
 * detail: API Service
 * @author Ttt
 */
interface APIService {

    // ==============
    // = 通用响应模型 =
    // ==============

    /**
     * 模拟请求错误
     */
    @GET("assets/error.json")
    suspend fun mockError(): BaseResponse<Any>

    /**
     * 获取摄影图片列表
     */
    @GET("assets/photos.json")
    suspend fun getPhotoList(): BaseResponse<List<PhotoBean>>

    // =====================
    // = Base.Response 实现 =
    // =====================

    /**
     * 模拟请求错误
     */
    @GET("assets/error.json")
    suspend fun app_mockError(): AppResponse<Any>

    /**
     * 获取摄影图片列表
     */
    @GET("assets/photos.json")
    suspend fun app_getPhotoList(): AppResponse<List<PhotoBean>>

    /**
     * 获取电影详情信息
     */
    @GET("assets/movie_detail.json")
    suspend fun app_getMovieDetail(): AppResponse<MovieDetailBean>

    /**
     * 获取书籍列表
     */
    @GET("assets/books.json")
    suspend fun app_getBookList(): AppResponse<BasePage<BookBean>>
}

/**
 * detail: Retrofit API Service
 * @author Ttt
 */
object RetrofitAPI {

    // API Service Base URL
    private const val BASE_URL =
        "https://raw.githubusercontent.com/afkT/DevUtils/refs/heads/master/application/DevRetrofitUse/src/main/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .baseUrl(BASE_URL)
            .build()
    }

    // ==================
    // = 创建 APIService =
    // ==================

    @Volatile
    private var mAPIService: APIService? = null

    fun api(): APIService {
        if (mAPIService == null) {
            synchronized(APIService::class.java) {
                if (mAPIService == null) {
                    mAPIService = retrofit.create(APIService::class.java)
                }
            }
        }
        return mAPIService as APIService
    }
}