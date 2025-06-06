package afkt.httpmanager.use.network.builder

import afkt.httpmanager.use.network.HttpCore
import dev.expand.engine.log.log_isPrintLog
import dev.http.manager.OkHttpBuilder
import dev.http.manager.OnRetrofitResetListener
import dev.http.manager.RetrofitBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * detail: 全局 Retrofit 重新构建监听事件
 * @author Ttt
 * 执行循序为
 * [Global] [OnRetrofitResetListener] onResetBefore
 * [RetrofitBuilder] onResetBefore
 * [Global] [OkHttpBuilder] createOkHttpBuilder
 * [RetrofitBuilder] createRetrofitBuilder
 * [RetrofitBuilder] onReset
 * [Global] [OnRetrofitResetListener] onReset
 */
class GlobalRetrofitResetListener : OnRetrofitResetListener {

    /**
     * 重新构建前调用
     * @param key String
     * @param oldRetrofit 上一次构建的 Retrofit
     */
    override fun onResetBefore(
        key: String,
        oldRetrofit: Retrofit?
    ) {
        if (log_isPrintLog()) {
            HttpCore.logProcess(
                key, "【Global】OnRetrofitResetListener.onResetBefore()"
            )
        }
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
        if (log_isPrintLog()) {
            HttpCore.logProcess(
                key, "【Global】OnRetrofitResetListener.onReset()"
            )
        }
        newRetrofit?.let { retrofit ->
            // 构建成功如自动请求等
        }
    }
}