package afkt.project.base.http

import afkt.project.model.bean.ArticleBean
import dev.DevHttpCapture
import dev.DevHttpManager
import dev.DevUtils
import dev.capture.CallbackInterceptor
import dev.environment.DevEnvironment
import dev.http.manager.OkHttpBuilder
import dev.http.manager.OnRetrofitResetListener
import dev.http.manager.RetrofitBuilder
import dev.http.manager.RetrofitOperation
import dev.utils.LogPrintUtils
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
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
interface WanAndroidService {

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ArticleBean
}

/**
 * detail: DevHttpManager RetrofitManager 使用示范
 * @author Ttt
 */
class RetrofitManagerUse private constructor() {

    companion object {

        private val instance: RetrofitManagerUse by lazy { RetrofitManagerUse() }

        // 日志 TAG
        private val TAG = RetrofitManagerUse::class.java.simpleName

        fun api(): WanAndroidService {
            return instance.api()
        }

        fun operation(): RetrofitOperation {
            return instance.operation()
        }
    }

    // 全局通用 OkHttp Builder
    private val mOkHttpBuilderGlobal = OkHttpBuilderGlobal()

    // 全局 Retrofit 重新构建监听事件
    private val mRetrofitResetListenerGlobal = RetrofitResetListenerGlobal()

    init {
        // 设置全局 OkHttp Builder 接口对象
        DevHttpManager.RM.setOkHttpBuilder(
            mOkHttpBuilderGlobal
        )
        // 设置全局 Retrofit 重新构建监听事件
        DevHttpManager.RM.setRetrofitResetListener(
            mRetrofitResetListenerGlobal
        )
    }

    // =====================
    // = WanAndroidService =
    // =====================

    @Volatile
    private var mWanAndroidService: WanAndroidService? = null

    fun api(): WanAndroidService {
        if (mWanAndroidService == null) {
            synchronized(WanAndroidService::class.java) {
                if (mWanAndroidService == null) {
                    createAPI()
                }
            }
        }
        return mWanAndroidService as WanAndroidService
    }

    private fun createAPI() {
        mWanAndroidService = operation().create(
            WanAndroidService::class.java
        )
    }

    // ==================
    // = DevEnvironment =
    // ==================

    private fun apiBaseUrl(): HttpUrl {
        return DevEnvironment.getServiceEnvironmentValue(
            DevUtils.getContext()
        ).toHttpUrl()
    }

    // =====================
    // = RetrofitOperation =
    // =====================

    /**
     * 对外提供操作对象
     * @return RetrofitOperation
     */
    fun operation(): RetrofitOperation {
        return mOperation
    }

    // Retrofit Operation
    private val mOperation: RetrofitOperation by lazy {
        DevHttpManager.RM.putRetrofitBuilder(
            "MODULE_NAME", mRetrofitBuilder
        )
    }

    // ===================
    // = RetrofitBuilder =
    // ===================

    // Retrofit Builder 接口
    private val mRetrofitBuilder: RetrofitBuilder by lazy {
        object : RetrofitBuilder {

            /**
             * 创建 Retrofit Builder
             * @param oldRetrofit 上一次构建的 Retrofit
             * @param httpUrl 构建使用指定 baseUrl
             * @param okHttp OkHttpClient 构建全局复用
             * @return Retrofit.Builder
             */
            override fun createRetrofitBuilder(
                oldRetrofit: Retrofit?,
                httpUrl: HttpUrl?,
                okHttp: OkHttpClient.Builder?
            ): Retrofit.Builder {
                return Retrofit.Builder().apply {
                    // Gson 解析
                    addConverterFactory(GsonConverterFactory.create())
                    // OkHttpClient
                    client((okHttp ?: OkHttpClient.Builder()).build())
                    // 服务器地址
                    baseUrl(httpUrl ?: apiBaseUrl())
                }
            }

            // ==========
            // = 通知事件 =
            // ==========

            /**
             * 重新构建前调用
             * @param key String
             * @param oldRetrofit 上一次构建的 Retrofit
             * 在 [createRetrofitBuilder] 之前调用
             */
            override fun onResetBefore(
                key: String,
                oldRetrofit: Retrofit?
            ) {

            }

            /**
             * 重新构建后调用
             * @param key String
             * @param newRetrofit 重新构建的 Retrofit 对象
             * 在 [createRetrofitBuilder] 之后调用
             */
            override fun onReset(
                key: String,
                newRetrofit: Retrofit?
            ) {
                // 重新构建后创建新的代理对象
                createAPI()
            }
        }
    }
}

/**
 * detail: 全局通用 OkHttp Builder
 * @author Ttt
 * 全局 ( 通过 Key 进行特殊化创建 )
 * 可用于 [RetrofitBuilder.createRetrofitBuilder] okHttp 参数传入并创建
 */
class OkHttpBuilderGlobal : OkHttpBuilder {

    /**
     * 创建 OkHttp Builder
     * @param key Key ( RetrofitBuilder Manager Key )
     * @return OkHttpClient.Builder
     */
    override fun createOkHttpBuilder(key: String): OkHttpClient.Builder {
//        // 可搭配监听进度使用
//        return ProgressManagerUse.operation().wrap(
//            commonOkHttpBuilder(key)
//        )
        return commonOkHttpBuilder(key)
    }

    // ==========
    // = 内部方法 =
    // ==========

    /**
     * 通用 OkHttp Builder 创建方法
     * @param key String
     * @return OkHttpClient.Builder
     */
    private fun commonOkHttpBuilder(key: String): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {

            // ==========
            // = 通用配置 =
            // ==========

            // 全局的读取超时时间
            readTimeout(60000L, TimeUnit.MILLISECONDS)
            // 全局的写入超时时间
            writeTimeout(60000L, TimeUnit.MILLISECONDS)
            // 全局的连接超时时间
            connectTimeout(60000L, TimeUnit.MILLISECONDS)

            // =============
            // = 不同版本构建 =
            // =============

            // 是否 Release 版本标记 ( 用于标记 APK 是否 Release 发布版本 )
            if (!DevUtils.isDebug()) {
                releaseOkHttpBuilder(key, this)
            } else {
                debugOkHttpBuilder(key, this)
            }
        }
    }

    // ===========
    // = Release =
    // ===========

    /**
     * release 版本构建 OkHttp Builder
     * @param key String
     * @param builder Builder
     */
    private fun releaseOkHttpBuilder(
        key: String,
        builder: OkHttpClient.Builder
    ) {
        builder.apply {
        }
    }

    // =========
    // = Debug =
    // =========

    /**
     * debug 版本构建 OkHttp Builder
     * @param key String
     * @param builder Builder
     */
    private fun debugOkHttpBuilder(
        key: String,
        builder: OkHttpClient.Builder
    ) {
        builder.apply {
            // 使用 DevHttpCapture 库进行 Http 拦截回调 ( 抓包数据存储 )
            DevHttpCapture.addInterceptor(builder, "ModuleName")

            // Http 抓包拦截器 ( 无存储逻辑, 进行回调通知 )
            addInterceptor(CallbackInterceptor { info ->
                // 打印 Http 请求信息 log
                LogPrintUtils.jsonTag(
                    key, info.toJson()
                )
            })
        }
    }
}

/**
 * detail: 全局 Retrofit 重新构建监听事件
 * @author Ttt
 */
class RetrofitResetListenerGlobal : OnRetrofitResetListener {

    /**
     * 重新构建前调用
     * @param key String
     * @param oldRetrofit 上一次构建的 Retrofit
     */
    override fun onResetBefore(
        key: String,
        oldRetrofit: Retrofit?
    ) {
        oldRetrofit?.let { retrofit ->
            val factory = retrofit.callFactory()
            if (factory is OkHttpClient) {
                // 重新构建则把之前的请求全部取消 ( 视情况自行处理 )
                factory.dispatcher.cancelAll()
            }
        }
    }

    /**
     * 重新构建后调用
     * @param key String
     * @param newRetrofit 重新构建的 Retrofit 对象
     * 在 [onResetBefore] 之后调用
     */
    override fun onReset(
        key: String,
        newRetrofit: Retrofit?
    ) {
        newRetrofit?.let { retrofit ->
            // 构建成功如自动请求等
        }
    }
}