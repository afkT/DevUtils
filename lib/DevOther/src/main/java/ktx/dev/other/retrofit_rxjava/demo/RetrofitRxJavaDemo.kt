package ktx.dev.other.retrofit_rxjava.demo

import dev.engine.log.DevLogEngine
import io.reactivex.rxjava3.core.Flowable
import ktx.dev.other.http.HttpLoggingInterceptor
import ktx.dev.other.retrofit_rxjava.RxJavaManager
import ktx.dev.other.retrofit_rxjava.response.BaseResponse
import ktx.dev.other.retrofit_rxjava.subscriber.BaseBeanSubscriber
import ktx.dev.other.retrofit_rxjava.subscriber.BaseResponseSubscriber
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
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
    fun getArticleList(@Path("page") page: Int): Flowable<ArticleBean?>

    // 演示 BaseBeanSubscriber、BaseResponseSubscriber 区别
    @GET("/article/list/{page}/json")
    fun getArticleList2(@Path("page") page: Int): Flowable<BaseResponse<ArticleBean2?>>
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
            // RxJava3 适配器
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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
const val TAG_L = "RetrofitRxJavaDemo"

fun getArticleList(page: Int) {
    // 映射各种 JSON 实体类
    val articleList =
        RetrofitManager.apiService.getArticleList(page)
            .compose(RxJavaManager.instance.io_main())
            .subscribeWith(object : BaseBeanSubscriber<ArticleBean>() {
                override fun onSuccessResponse(data: ArticleBean) {
                    // 请求成功
                    DevLogEngine.getEngine()?.dTag(
                        TAG_L, "onSuccessResponse data: ${data.data?.datas?.size}"
                    )
                }

                override fun onErrorResponse(
                    throwable: Throwable?,
                    message: String?
                ) {
                    // 请求失败
                    DevLogEngine.getEngine()?.eTag(
                        TAG_L, throwable, "onErrorResponse $message"
                    )
                }
            })
    RxJavaManager.instance.add(TAG_L, articleList)
//    // 用于 Retrofit 请求管理, 在 Activity onDestroy 调用
//    RxJavaManager.instance.remove(TAG_L)
}

fun getArticleList2(page: Int) {
    // 映射统一标准 JSON 格式实体类 => BaseResponse
    val articleList =
        RetrofitManager.apiService.getArticleList2(page)
            .compose(RxJavaManager.instance.io_main())
            .subscribeWith(object : BaseResponseSubscriber<ArticleBean2?>() {
                override fun onSuccessResponse(response: BaseResponse<ArticleBean2?>) {
                    // 请求成功
                    DevLogEngine.getEngine()?.dTag(
                        TAG_L, "onSuccessResponse data: ${response.data?.datas?.size}"
                    )
                }

                override fun onErrorResponse(response: BaseResponse<ArticleBean2?>) {
                    // 请求失败
                    DevLogEngine.getEngine()?.eTag(
                        TAG_L, response.exception,
                        "onErrorResponse ${response.errorMsg}"
                    )
                }
            })
    RxJavaManager.instance.add(TAG_L, articleList)
//    // 用于 Retrofit 请求管理, 在 Activity onDestroy 调用
//    RxJavaManager.instance.remove(TAG_L)
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