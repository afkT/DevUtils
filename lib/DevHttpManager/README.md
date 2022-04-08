
## Gradle

```gradle
implementation 'io.github.afkt:DevHttpManager:1.0.0'
```

## 目录结构

```
- dev                                                 | 根目录
   - http                                             | 基于 OkHttp 管理实现代码
      - manager                                       | Retrofit 多 BaseUrl 管理
      - progress                                      | OkHttp 上传、下载进度监听
```


## 框架功能介绍

* 支持 Retrofit 多 BaseUrl 管理及操作方法封装

* 支持 Retrofit BaseUrl Reset 事件全局监听、各个模块单独监听回调

* 支持全局 OkHttp Builder 创建方法，可进行全局管理

* 针对多 Retrofit 管理封装 Operation 对象并支持组件化使用

* 支持传参 Map 对多个 Retrofit 同时进行 BaseUrl Reset

* 支持对 App 所有链接上传、下载进度监听

* 基于 OkHttp 原生 Api 实现，不存在兼容问题

* 侵入性低，使用本框架不需要更改历史上传、下载实现代码


## API 文档

* **DevHttpManager 管理库方法 ->** [DevHttpManager.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt)

| 方法 | 注释 |
| :- | :- |
| getDevHttpManagerVersionCode | 获取 DevHttpManager 版本号 |
| getDevHttpManagerVersion | 获取 DevHttpManager 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |
| getOkHttpBuilder | 获取全局 OkHttp Builder 接口对象 |
| setOkHttpBuilder | 设置全局 OkHttp Builder 接口对象 |
| removeOkHttpBuilder | 移除全局 OkHttp Builder 接口对象 |
| getRetrofitResetListener | 获取全局 Retrofit 重新构建监听事件 |
| setRetrofitResetListener | 设置全局 Retrofit 重新构建监听事件 |
| removeRetrofitResetListener | 移除全局 Retrofit 重新构建监听事件 |
| getOperation | 获取 Retrofit Operation 操作对象 |
| containsOperation | 通过 Key 判断是否存在 Retrofit Operation 操作对象 |
| putRetrofitBuilder | 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象 |
| removeRetrofitBuilder | 通过 Key 解绑移除 RetrofitBuilder 并返回 Operation 操作对象 |
| reset | 重置处理 ( 重新构建 Retrofit ) |
| resetAll | 重置处理 ( 重新构建全部 Retrofit ) |

## Retrofit 多 BaseUrl 管理功能 [目录](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager)

* **全局 OkHttp Builder 接口 ->** [OkHttpBuilder.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/OkHttpBuilder.kt)

| 方法 | 注释 |
| :- | :- |
| createOkHttpBuilder | 创建 OkHttp Builder |


* **全局 Retrofit 重新构建监听事件 ->** [OnRetrofitResetListener.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/OnRetrofitResetListener.kt)

| 方法 | 注释 |
| :- | :- |
| onResetBefore | 重新构建前调用 |
| onReset | 重新构建后调用 |


* **Retrofit Builder 接口 ->** [RetrofitBuilder.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitBuilder.kt)

| 方法 | 注释 |
| :- | :- |
| createRetrofitBuilder | 创建 Retrofit Builder |
| onResetBefore | 重新构建前调用 |
| onReset | 重新构建后调用 |


* **Retrofit Operation ->** [RetrofitOperation.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitOperation.kt)

| 方法 | 注释 |
| :- | :- |
| getRetrofit | 获取 Retrofit 对象 |
| create | 通过 Retrofit 代理创建 Service |
| reset | 重置处理 ( 重新构建 Retrofit ) |
| resetAndCreate | 重置处理 ( 重新构建 Retrofit ) 并代理创建 Service |


# Retrofit 多 BaseUrl 管理功能使用

使用代码只有一步：**通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象**

```kotlin
// 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象
DevHttpManager.putRetrofitBuilder(
    stringKey, RetrofitBuilder
)
```

通过返回的 [Operation][Operation] 对象进行获取 Retrofit 或直接 create APIService

```kotlin
/**
 * 获取 Retrofit 对象
 * @param check 是否需要判断 Retrofit 是否为 null
 * @return Retrofit
 */
fun getRetrofit(check: Boolean = true): Retrofit?

/**
 * 通过 Retrofit 代理创建 Service
 * @param service Class<T>
 * @return Service Class
 */
fun <T> create(service: Class<T>): T?

/**
 * 重置处理 ( 重新构建 Retrofit )
 * @param httpUrl 构建使用指定 baseUrl
 * @return Retrofit Operation
 */
fun reset(httpUrl: HttpUrl? = null): RetrofitOperation

/**
 * 重置处理 ( 重新构建 Retrofit ) 并代理创建 Service
 * @param httpUrl 构建使用指定 baseUrl
 * @return Retrofit Operation
 */
fun <T> resetAndCreate(
    service: Class<T>,
    httpUrl: HttpUrl? = null
): T?
```


**具体实现代码可以查看 [DevComponent lib_network][DevComponent lib_network]、[WanAndroidAPI][WanAndroidAPI]**
，以 [DevComponent][DevComponent] 组件化项目代码为例。

* HttpCoreLibrary initialize() 方法中的代码非必须设置，只是提供全局管理控制方法，支持设置全局 OkHttp Builder 接口对象、全局 Retrofit 重新构建监听事件。

* 如 [OkHttpBuilderGlobal][OkHttpBuilderGlobal] 内部实现 OkHttpBuilder 接口，
  通过创建通用的 OkHttpClient.Builder 提供给 RetrofitBuilder.createRetrofitBuilder() 方法创建 Retrofit.Builder 使用

* [RetrofitResetListenerGlobal][RetrofitResetListenerGlobal] 则提供全局 BaseUrl Reset 监听，例如重新构建 Retrofit 前取消历史请求操作、重新构建后等操作

```kotlin
/**
 * detail: Http Core Lib
 * @author Ttt
 */
object HttpCoreLibrary {

    // 全局通用 OkHttp Builder
    private val mOkHttpBuilderGlobal = OkHttpBuilderGlobal()

    // 全局 Retrofit 重新构建监听事件
    private val mRetrofitResetListenerGlobal = RetrofitResetListenerGlobal()

    // =============
    // = 对外公开方法 =
    // =============

    /**
     * 初始化 OkHttp 管理库 ( Retrofit 多 BaseUrl 等 )
     * @param context Context
     */
    fun initialize(context: Context) {
        // 设置全局 OkHttp Builder 接口对象
        DevHttpManager.setOkHttpBuilder(
            mOkHttpBuilderGlobal
        )
        // 设置全局 Retrofit 重新构建监听事件
        DevHttpManager.setRetrofitResetListener(
            mRetrofitResetListenerGlobal
        )
    }
}
```

> **以上代码为非必须实现，以下为使用该库核心方法示例**

```kotlin
/**
 * detail: 玩 Android API Service
 * @author Ttt
 */
interface WanAndroidService {

    @GET("/article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ArticleBean
}

/**
 * detail: 玩 Android API
 * @author Ttt
 */
class WanAndroidAPI private constructor() {

    companion object {

        private val instance: WanAndroidAPI by lazy { WanAndroidAPI() }

        fun api(): WanAndroidService {
            return instance.api()
        }

        fun operation(): RetrofitOperation {
            return instance.operation()
        }
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
        return DevEnvironment.getWanAndroidEnvironmentValue(
            AppContext.content()
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
        DevHttpManager.putRetrofitBuilder(
            BuildConfig.MODULE_NAME, mRetrofitBuilder
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
                return HttpCoreUtils.createRetrofitBuilder(
                    httpUrl = httpUrl ?: apiBaseUrl(),
                    okHttp = okHttp ?: OkHttpClient.Builder()
                )
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
```

**整个方法流程执行循序为：**

1. `Global.OnRetrofitResetListener` onResetBefore
2. `RetrofitBuilder` onResetBefore
3. `Global.OkHttpBuilder` createOkHttpBuilder
4. `RetrofitBuilder` createRetrofitBuilder
5. `RetrofitBuilder` onReset
6. `Global.OnRetrofitResetListener` onReset

```kotlin
/**
 * 构建 Retrofit 方法 ( 最终调用 )
 * @param httpUrl 构建使用指定 baseUrl
 * @return Retrofit Operation
 */
private fun buildRetrofit(httpUrl: HttpUrl? = null): RetrofitOperation {
    if (mReset) {
        try {
            RetrofitManager.getRetrofitResetListener()?.onResetBefore(
                key, mRetrofit
            )
        } catch (e: Exception) {
        }
        builder.onResetBefore(key, mRetrofit)
    }

    // 获取全局 OkHttp Builder
    val okHttpBuilder = try {
        RetrofitManager.getOkHttpBuilder()?.createOkHttpBuilder(key)
    } catch (e: Exception) {
        null
    }
    // 可以通过 mRetrofit?.baseUrl() 获取之前的配置
    mRetrofit = builder.createRetrofitBuilder(
        mRetrofit, httpUrl, okHttpBuilder
    ).build()

    if (mReset) {
        builder.onReset(key, mRetrofit)
        try {
            RetrofitManager.getRetrofitResetListener()?.onReset(
                key, mRetrofit
            )
        } catch (e: Exception) {
        }
    }
    // 首次为初始化, 后续操作为重置操作
    mReset = true
    return this
}
```





[DevComponent]: https://github.com/afkT/DevComponent
[DevComponent lib_network]: https://github.com/afkT/DevComponent/tree/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network
[WanAndroidAPI]: https://github.com/afkT/DevComponent/blob/main/component/module/module_wanandroid/src/main/java/afkt_replace/module/wan_android/data/api/WanAndroidAPI.kt
[OkHttpBuilderGlobal]: https://github.com/afkT/DevComponent/blob/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network/common/OkHttpBuilderGlobal.kt
[RetrofitResetListenerGlobal]: https://github.com/afkT/DevComponent/blob/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network/common/RetrofitResetListenerGlobal.kt
[Operation]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitOperation.kt