package afkt.httpmanager.use.network

import afkt.httpmanager.use.base.BaseApplication
import afkt.httpmanager.use.network.builder.GlobalOkHttpBuilder
import afkt.httpmanager.use.network.builder.GlobalRetrofitResetListener
import android.content.Context
import dev.DevHttpManager
import dev.DevUtils
import dev.expand.engine.log.log_iTag
import dev.http.manager.OkHttpBuilder
import dev.http.manager.OnRetrofitResetListener
import dev.http.manager.RetrofitBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * detail: Http Core
 * @author Ttt
 * 执行循序为
 * [Global] [OnRetrofitResetListener] onResetBefore
 * [RetrofitBuilder] onResetBefore
 * [Global] [OkHttpBuilder] createOkHttpBuilder
 * [RetrofitBuilder] createRetrofitBuilder
 * [RetrofitBuilder] onReset
 * [Global] [OnRetrofitResetListener] onReset
 * 使用全局监听事件、构建操作是为了提供统一管理方法, 方便统一做处理
 * 并且自身也存在回调方法, 也能够单独处理
 * <p></p>
 * 使用方法
 * [DevHttpManager.RM.putRetrofitBuilder]
 * 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象
 */
object HttpCore {

    // 日志 TAG
    private val TAG = HttpCore::class.java.simpleName

    // 全局 OkHttp Builder
    private val mOkHttpBuilderGlobal = GlobalOkHttpBuilder()

    // 全局 Retrofit 重新构建监听事件
    private val mRetrofitResetListenerGlobal = GlobalRetrofitResetListener()

    // =================
    // = Debug、Release =
    // =================

    /**
     * 是否 Release 版本标记
     */
    fun isRelease(): Boolean = BaseApplication.isRelease()

    /**
     * 是否 Debug 版本标记
     */
    fun isDebug(): Boolean = BaseApplication.isDebug()

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 获取全局 Context
     * @return Context
     */
    fun context(): Context {
        return DevUtils.getContext()
    }

    /**
     * 初始化 OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
     * @param context Context
     */
    fun initialize(context: Context) {
        // 初始化 Retrofit 多 BaseUrl 管理
        initializeRetrofitManager(context)
    }

    /**
     * 初始化 Retrofit 多 BaseUrl 管理
     * @param context Context
     */
    private fun initializeRetrofitManager(context: Context) {
        // 设置全局 OkHttp Builder 接口对象
        DevHttpManager.RM.setOkHttpBuilder(
            mOkHttpBuilderGlobal
        )
        // 设置全局 Retrofit 重新构建监听事件
        DevHttpManager.RM.setRetrofitResetListener(
            mRetrofitResetListenerGlobal
        )
    }

    // ===========
    // = Builder =
    // ===========

    /**
     * 创建 Retrofit Builder
     * @param builder Retrofit Builder ( 可使用历史 [Retrofit.newBuilder] )
     * @param httpUrl HttpUrl
     * @param okHttp Builder
     * @return Retrofit.Builder
     */
    fun createRetrofitBuilder(
        builder: Retrofit.Builder = Retrofit.Builder(),
        httpUrl: HttpUrl,
        okHttp: OkHttpClient.Builder
    ): Retrofit.Builder {
        return builder.apply {
            // 设置 Gson 解析
            addGsonConverterFactory()
            // OkHttpClient
            client(okHttp.build())
            // 服务器地址
            baseUrl(httpUrl)
        }
    }

    // ===================
    // = HttpCore 流程日志 =
    // ===================

    /**
     * Log process
     * @param key Module Key
     * @param message Log Message
     */
    fun logProcess(
        key: String,
        message: String
    ) {
        TAG.log_iTag(message = "[${key}] -${message}")
    }
}

// ==========
// = 快捷方法 =
// ==========

/**
 * 设置 Retrofit Gson 解析
 * @receiver Retrofit.Builder
 * @return Retrofit.Builder
 */
fun Retrofit.Builder.addGsonConverterFactory(): Retrofit.Builder {
    // 设置 Gson 解析
    return addConverterFactory(GsonConverterFactory.create())
}