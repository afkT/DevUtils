package afkt.project.use_demo

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dev.DevHttpCapture
import dev.capture.CallbackInterceptor
import dev.engine.DevEngine
import dev.retrofit.*
import dev.utils.LogPrintUtils
import dev.utils.common.StringUtils
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.*

// 日志 TAG
const val TAG_L = "DevRetrofitCoroutinesDemo"

/**
 * detail: 服务器接口 API Service
 * @author Ttt
 */
interface APIService {

    @GET("xxx")
    suspend fun loadArticleList(@Path("page") page: Int): ArticleResponse // or BaseResponse<List<ArticleBean?>>

    @POST("xxx")
    suspend fun uploadImage(@Body body: RequestBody): UploadBean // or BaseResponse<List<String?>>()
}

/**
 * detail: Retrofit API Service
 * @author Ttt
 */
object RetrofitAPI {

    private val retrofit: Retrofit

    private val apiService: APIService

    init {
        // ====================
        // = OkHttpClient 配置 =
        // ====================

        val builder = OkHttpClient.Builder()

        // 使用 DevHttpCapture 库进行 Http 拦截回调 ( 抓包数据存储 )
        DevHttpCapture.addInterceptor(builder, "ModuleName")

        // 使用 DevHttpCapture 库进行 Http 拦截回调 ( 不进行抓包数据存储 )
        builder.addInterceptor(CallbackInterceptor { captureInfo ->
            LogPrintUtils.jsonTag(
                TAG_L, DevEngine.getJSON().toJson(captureInfo)
            )
        })

        // ================
        // = Retrofit 配置 =
        // ================

        retrofit = Retrofit.Builder()
            // Gson 解析
            .addConverterFactory(GsonConverterFactory.create())
            // OkHttpClient
            .client(builder.build())
            // 服务器地址
            .baseUrl("https://baseUrl")
            .build()

        apiService = retrofit.create(APIService::class.java)
    }

    fun api(): APIService {
        return apiService
    }
}

// ========
// = 实体类 =
// ========

/**
 * detail: 统一响应实体类
 * @author Ttt
 */
open class BaseResponse<T> : Base.Response<T> {

    private var customData: T? = null
    private var customCode: String? = null
    private var customMessage: String? = null

    // =================
    // = Base.Response =
    // =================

    override fun getData(): T? {
        return customData
    }

    override fun getCode(): String? {
        return customCode
    }

    override fun getMessage(): String? {
        return customMessage
    }

    override fun isSuccess(): Boolean {
        return customCode?.let { code ->
            // 自定义 code 为 200 表示请求成功 ( 后台定义 )
            StringUtils.equals(code, "200")
        } ?: false
    }
}

// =============
// = 非基础实体类 =
// =============

/**
 * detail: 上传图片结果
 * @author Ttt
 */
class UploadBean : BaseResponse<List<String?>>()

/**
 * detail: 文章数据响应类 ( 可不定义, 只是为了方便理解、展示 )
 * @author Ttt
 * data 映射实体类为 List<ArticleBean?>
 */
class ArticleResponse : BaseResponse<List<ArticleBean?>>()

/**
 * detail: 文章实体类
 * @author Ttt
 */
data class ArticleBean(
    val content: String?,
    val cover: String?
)

// ===================
// = DevRetrofit 使用 =
// ===================

/**
 * 模拟在 Activity 下使用
 * 总的请求方法分为以下两种
 * execute Request
 * execute Response Request
 * 区别在于 Response 是直接使用内部封装的 Base.Result 以及 ResultCallback 进行回调通知等
 * 不管什么扩展函数方式请求, 最终都是执行 request.kt 中的 finalExecute、finalExecuteResponse 方法
 */
class TestActivity : AppCompatActivity() {

    // 封装 Base Notify.Callback
    abstract class BaseCallback<T> : Notify.Callback<T>()

    // 封装 Notify.ResultCallback 简化代码
    abstract class BaseResultCallback<T> : Notify.ResultCallback<T, BaseResponse<T>>()

    // LiveData
    private val _articleLiveData = MutableLiveData<ArticleResponse>()
    val articleLiveData: LiveData<ArticleResponse> = _articleLiveData

    private fun request() {
        // 加载文章列表方式一
        simpleLaunchExecuteRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            }, object : Notify.Callback<ArticleResponse>() {
                override fun onSuccess(
                    uuid: UUID,
                    data: ArticleResponse?
                ) {
                    // 请求成功
                }

                override fun onError(
                    uuid: UUID,
                    error: Throwable?
                ) {
                    // 请求异常
                }
            }
        )
        // 加载文章列表方式一 ( 使用封装 Callback )
        simpleLaunchExecuteRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            }, ArticleCallback()
        )

        // 加载文章列表方式二
        simpleLaunchExecuteResponseRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            }, object : Notify.ResultCallback<List<ArticleBean?>, ArticleResponse>() {
                override fun onSuccess(
                    uuid: UUID,
                    data: Base.Result<List<ArticleBean?>, ArticleResponse>
                ) {

                }
            }
        )

        // 加载文章列表方式二 ( 使用 BaseResultCallback )
        simpleLaunchExecuteResponseRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            }, object : BaseResultCallback<List<ArticleBean?>>() {
                override fun onSuccess(
                    uuid: UUID,
                    data: Base.Result<List<ArticleBean?>, BaseResponse<List<ArticleBean?>>>
                ) {

                }
            }
        )

        // 加载文章列表方式三
        liveDataLaunchExecuteRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            },
            liveData = _articleLiveData,
            usePostValue = false // default true
        )

        // 加载文章列表方式四 ( 可自行添加额外流程等 )
        launchExecuteRequest(
            block = {
                RetrofitAPI.api().loadArticleList(1)
            },
            start = {

            },
            success = {

            },
            error = {

            },
            finish = {

            },
            callback = ArticleCallback()
        )
    }

    private class ArticleCallback : Notify.Callback<ArticleResponse>() {
        override fun onSuccess(
            uuid: UUID,
            data: ArticleResponse?
        ) {
            // 请求成功
        }

        override fun onError(
            uuid: UUID,
            error: Throwable?
        ) {
            // 请求异常
        }

        override fun onStart(uuid: UUID) {
            super.onStart(uuid)
            // 开始请求
        }

        override fun onFinish(uuid: UUID) {
            super.onFinish(uuid)
            // 请求结束
        }
    }
}