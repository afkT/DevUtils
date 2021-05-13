package ktx.dev.other.retrofit_coroutines.demo

import androidx.lifecycle.LiveData
import dev.engine.log.DevLogEngine
import ktx.dev.other.http.HttpLoggingInterceptor
import ktx.dev.other.retrofit_coroutines.*
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

suspend fun getArticleList(page: Int) {
    /**
     * 请求方法统一放到 Module Repository 中
     * 并通过 [LiveData.postValue] 进行通知
     */
    val errorMethod = errorMethodCommon()
    // 映射各种 JSON 实体类
    executeRequest(block = {
        var bean = RetrofitManager.apiService.getArticleList(page)
        if (isSuccess(bean?.errorCode)) {
            DevLogEngine.getEngine()?.dTag(
                TAG_L, "请求成功 data: ${bean?.data?.datas?.size}"
            )
        } else {
            errorMethod.invoke(bean?.errorCode, Throwable(bean?.errorMsg))
        }
    }, start = {
        DevLogEngine.getEngine()?.dTag(
            TAG_L, "开始请求"
        )
    }, complete = {
        DevLogEngine.getEngine()?.dTag(
            TAG_L, "请求完成"
        )
    }, error = errorMethod)
}

suspend fun getArticleList2(page: Int) {
    /**
     * 请求方法统一放到 Module Repository 中
     * 并通过 [LiveData.postValue] 进行通知
     */
    val errorMethod = errorMethodCommon()
    // 映射统一标准 JSON 格式实体类 => BaseResponse
    executeRequest(block = {
        var result = RetrofitManager.apiService.getArticleList2(page).result()
        if (result.success) {
            DevLogEngine.getEngine()?.dTag(
                TAG_L, "请求成功 data: ${result.data?.datas?.size}"
            )
        } else {
            errorMethod.invoke(result.errorCode, result.error)
        }
    }, start = {
        DevLogEngine.getEngine()?.dTag(
            TAG_L, "开始请求"
        )
    }, complete = {
        DevLogEngine.getEngine()?.dTag(
            TAG_L, "请求完成"
        )
    }, error = errorMethod)
}

/**
 * 通用错误回调方法 ( 方便复用 )
 * @return (Throwable) -> Unit
 */
fun errorMethodCommon(): suspend (String?, Throwable) -> Unit {
    return { code, error ->
        DevLogEngine.getEngine()?.eTag(
            TAG_L, error, "请求异常 code: ${code}, error: ${getErrorMessage(error)}"
        )
    }
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
    var data: DataBean? = null

    // 返回消息
    var errorMsg: String? = null

    // 返回结果状态 ( 内部定义 )
    var errorCode: String? = null

    open class DataBean {
        var size = 0

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