package afkt.httpcapture.use

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// ==============
// = 请求 API 类 =
// ==============

/**
 * detail: 服务器接口 API Service
 * @author Ttt
 */
interface APIService {

    /**
     * 获取文章列表
     * @param page 页数
     */
    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): BasePageResponse<ArticleBean>

    /**
     * 获取搜索热词列表
     */
    @GET("/hotkey/json")
    suspend fun getHotkeys(): BaseResponse<List<Any>>

    /**
     * 获取 Banner 列表
     */
    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Any>>
}

/**
 * detail: Retrofit API Service
 * @author Ttt
 */
object RetrofitAPI {

    // API Service Base URL
    private const val BASE_URL = "https://www.wanandroid.com"

    /**
     * 创建新的 API Service【每次都创建新的方便演示】
     * @param builder OkHttpClient.Builder
     * @return [APIService]
     */
    fun newAPI(builder: OkHttpClient.Builder): APIService {

        // ================
        // = Retrofit 配置 =
        // ================

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(APIService::class.java)
    }
}

// =========
// = 实体类 =
// =========

/**
 * detail: 文章实体类
 * @author Ttt
 */
data class ArticleBean(
    val title: String,
    val link: String,
    val author: String
)

// ==============
// = 响应数据模型 =
// ==============

/**
 * detail: 通用响应模型
 * @author Ttt
 */
open class BaseResponse<T>(
    val data: T?,
    val errorCode: Int?,
    val errorMsg: String?
)

/**
 * detail: 通用分页数据模型
 * @author Ttt
 */
data class BasePage<T>(
    val curPage: Int,
    val datas: List<T>,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

/**
 * detail: 分页响应模型
 * @author Ttt
 */
data class BasePageResponse<T>(
    private val _data: BasePage<T>?,
    private val _errorCode: Int?,
    private val _errorMsg: String?
) : BaseResponse<BasePage<T>>(
    _data, _errorCode, _errorMsg
)