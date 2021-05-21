package ktx.dev.other.retrofit_rxjava

import dev.utils.LogPrintUtils
import retrofit2.Retrofit
import java.util.*

/**
 * detail: Retrofit 管理类
 * @author Ttt
 * 使用 Retrofit2 + RxJava3 + RxAndroid3, 预留多 Retrofit 处理
 * 也可以使用单 Retrofit 通过 OkHttp Interceptor 进行拦截处理 ( 例: 通过 Header 某个字段判断重新设置 baseUrl、Header )
 * Android : 手把手带你深入剖析 Retrofit 2.0 源码
 * @see https://blog.csdn.net/carson_ho/article/details/73732115
 * 这是一份很详细的 Retrofit 2.0 使用教程
 * @see https://blog.csdn.net/carson_ho/article/details/73732076
 * 封装 Retrofit2 + RxJava2 网络请求框架
 * @see https://www.jianshu.com/p/2e8b400909b7
 */
class RetrofitManager private constructor() {

    // 日志 TAG
    private val TAG = RetrofitManager::class.java.simpleName

    // Retrofit Map ( 多 BaseUrl、Header、OkHttp 配置等考虑 )
    private val mRetrofitMap: MutableMap<String, Retrofit> = HashMap()

    companion object {
        // RetrofitManager 实例
        val instance: RetrofitManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitManager()
        }
    }

    /**
     * 通过 tag 获取 Retrofit
     * @param tag Retrofit tag
     * @return [Retrofit]
     */
    fun get(tag: String): Retrofit? {
        return mRetrofitMap[tag]
    }

    /**
     * 通过 tag 保存 Retrofit
     * @param tag      Retrofit tag
     * @param retrofit [Retrofit]
     * @return [Retrofit]
     */
    fun put(
        tag: String,
        retrofit: Retrofit?
    ): Retrofit? {
        if (retrofit != null) {
            mRetrofitMap[tag] = retrofit
        }
        return retrofit
    }

    /**
     * 通过 tag 移除 Retrofit
     * @param tag Retrofit tag
     * @return [Retrofit]
     */
    fun remove(tag: String): Retrofit? {
        return mRetrofitMap.remove(tag)
    }

    /**
     * 通过 tag 判断是否存在 Retrofit
     * @param tag Retrofit tag
     * @return `true` yes, `false` no
     */
    fun contains(tag: String): Boolean {
        return mRetrofitMap.containsKey(tag)
    }

    /**
     * 获取 Retrofit Map
     * @return Retrofit Map
     */
    fun getRetrofitMap(): Map<String, Retrofit> {
        return mRetrofitMap
    }

    // ===================
    // = 代理 API Service =
    // ===================

    /**
     * 创建 API Service Class
     * @param tag     Retrofit tag
     * @param service API Service
     * @param <T>     ApiServiceT.class
     * @return API Service Instance
     */
    fun <T> create(
        tag: String,
        service: Class<T>?
    ): T? {
        mRetrofitMap[tag]?.let { retrofit ->
            try {
                return retrofit.create(service)
            } catch (e: Exception) {
                LogPrintUtils.eTag(TAG, e, "create")
            }
        }
        return null
    }
}