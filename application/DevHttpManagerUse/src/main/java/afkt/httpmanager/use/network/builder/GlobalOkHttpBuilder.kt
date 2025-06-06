package afkt.httpmanager.use.network.builder

import afkt.httpmanager.use.network.HttpCore
import dev.capture.interceptor.SimpleInterceptor
import dev.expand.engine.log.log_iTag
import dev.expand.engine.log.log_isPrintLog
import dev.expand.engine.log.log_jsonTag
import dev.http.manager.OkHttpBuilder
import dev.http.manager.RetrofitBuilder
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * detail: 全局 OkHttp Builder
 * @author Ttt
 * 全局 ( 通过 Key 进行特殊化创建 )
 * 可用于 [RetrofitBuilder.createRetrofitBuilder] okHttp 参数传入并创建
 */
class GlobalOkHttpBuilder : OkHttpBuilder {

    /**
     * 创建 OkHttp Builder
     * @param key Key ( RetrofitBuilder Manager Key )
     * @return OkHttpClient.Builder
     */
    override fun createOkHttpBuilder(key: String): OkHttpClient.Builder {
        if (log_isPrintLog()) {
            HttpCore.TAG.log_iTag(message = "[${key}] -【Global】OkHttpBuilder.createOkHttpBuilder()")
        }
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

            // 全局的响应超时时间 ( 秒 )
            callTimeout(15, TimeUnit.SECONDS)
            // 全局的读取超时时间
            readTimeout(15, TimeUnit.SECONDS)
            // 全局的写入超时时间
            writeTimeout(15, TimeUnit.SECONDS)
            // 全局的连接超时时间
            connectTimeout(15, TimeUnit.SECONDS)

            // =============
            // = 不同版本构建 =
            // =============

            // 是否 Release 版本标记
            if (HttpCore.isRelease()) {
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
        if (log_isPrintLog()) {
            HttpCore.TAG.log_iTag(message = "[${key}] -【Global】OkHttpBuilder.releaseOkHttpBuilder()")
        }
        builder.apply {}
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
        if (log_isPrintLog()) {
            HttpCore.TAG.log_iTag(message = "[${key}] -【Global】OkHttpBuilder.debugOkHttpBuilder()")
        }
        builder.apply {
            // 设置简单的抓包回调拦截器 ( 无存储逻辑 )
            addInterceptor(SimpleInterceptor { info ->
                if (log_isPrintLog()) {
                    // 打印 Http 请求信息
                    val tag = "${key}_http_capture"
                    tag.log_jsonTag(json = info.toJson())
                }
            })
        }
    }
}