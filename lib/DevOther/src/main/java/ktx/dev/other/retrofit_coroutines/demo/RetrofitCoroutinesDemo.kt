package ktx.dev.other.retrofit_coroutines.demo

import ktx.dev.other.http.HttpLoggingInterceptor
import ktx.dev.other.retrofit_coroutines.BaseResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

/**
 * detail: 玩 Android API Service
 * @author Ttt
 */
interface APIService {

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ArticleBean?

    @GET("/article/list/{page}/json")
    suspend fun getArticleList2(@Path("page") page: Int): BaseResponse<ArticleBean2?>
}

/**
 * detail: Retrofit 请求 Demo
 * @author Ttt
 */
object RetrofitManager {

    private val retrofit: Retrofit

    val apiService: APIService

    init {
        // ====================
        // = OkHttpClient 配置 =
        // ====================

        val builder = OkHttpClient.Builder()
        // 自定义日志拦截 JSON 打印
        builder.addInterceptor(HttpLoggingInterceptor())
        // 全局的读取超时时间
        builder.readTimeout(60000L, TimeUnit.MILLISECONDS)
        // 全局的写入超时时间
        builder.writeTimeout(60000L, TimeUnit.MILLISECONDS)
        // 全局的连接超时时间
        builder.connectTimeout(60000L, TimeUnit.MILLISECONDS)

        // ================
        // = Retrofit 配置 =
        // ================

        retrofit = Retrofit.Builder()
            // Gson 解析
            .addConverterFactory(GsonConverterFactory.create())
            // OkHttpClient
            .client(builder.build())
            // 服务器地址
            .baseUrl("https://www.wanandroid.com/")
            .build()

        apiService = retrofit.create(APIService::class.java)
    }
}

// ==========
// = 请求方法 =
// ==========

// 日志 TAG
const val TAG_L = "RetrofitCoroutinesDemo"

fun getArticleList(page: Int) {

}

fun getArticleList2(page: Int) {

}

// =========
// = 实体类 =
// =========

/**
 * detail: 文章信息实体类
 * @author Ttt
 */
class ArticleBean2 : ArticleBean.DataBean()

/**
 * detail: 文章信息实体类
 * @author Ttt
 */
class ArticleBean {
    @JvmField
    var data: DataBean? = null

    open class DataBean {
        var size = 0

        @JvmField
        var datas: List<ListBean>? = null

        class ListBean {
            var id = 0
            var link: String? = null
            var niceDate: String? = null
            var niceShareDate: String? = null
            var author: String? = null
            var title: String? = null
            var type = 0
        }
    }
}