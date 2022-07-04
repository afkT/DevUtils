

## 摘要

* [框架功能介绍](#框架功能介绍)
* [Retrofit 多 BaseUrl 管理功能](#Retrofit-多-BaseUrl-管理功能)
* [Retrofit 多 BaseUrl 管理功能使用](#Retrofit-多-BaseUrl-管理功能使用)
* [OkHttp 上传、下载进度监听](#OkHttp-上传下载进度监听)
* [OkHttp 上传、下载进度监听使用](#OkHttp-上传下载进度监听使用)


## Gradle

```gradle
implementation 'io.github.afkt:DevHttpManager:1.0.2'
```

## 目录结构

```
- dev                   | 根目录
   - http               | 基于 OkHttp 管理实现代码
      - manager         | Retrofit 多 BaseUrl 管理
      - progress        | OkHttp 上传、下载进度监听
         - operation    | 监听通知不同方式实现
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

* 对外公开封装 Progress RequestBody、ResponseBody 类，支持自定义使用

* 支持监听代码，不同实现方式切换，内部内存回收、监听通知方式不同

* 针对多组件模块化封装，内置默认全局通用对象，也可传 Key 创建独立 Progress 管理操作对象


## API 文档

* **DevHttpManager 管理库方法 ->** [DevHttpManager.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt)

| 方法 | 注释 |
| :- | :- |
| getDevHttpManagerVersionCode | 获取 DevHttpManager 版本号 |
| getDevHttpManagerVersion | 获取 DevHttpManager 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |


* **RetrofitManager 方法 ->** [DevHttpManager.RM.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L77)

| 方法 | 注释 |
| :- | :- |
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


* **ProgressManager 方法 ->** [DevHttpManager.PM.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L219)

| 方法 | 注释 |
| :- | :- |
| getDefault | 获取全局默认 Progress Operation 操作对象 |
| getOperation | 获取 Progress Operation 操作对象 |
| containsOperation | 通过 Key 判断是否存在 Progress Operation 操作对象 |
| removeOperation | 通过 Key 解绑并返回 Operation 操作对象 |
| clearOperation | 清空所有 Progress Operation 操作对象 |
| putOperationTypeAll | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 ) |
| putOperationTypeRequest | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 ) |
| putOperationTypeResponse | 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 ) |


## Retrofit 多 BaseUrl 管理功能

* **RetrofitManager 方法 ->** [DevHttpManager.RM.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L77)

| 方法 | 注释 |
| :- | :- |
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

### 具体实现代码 [目录](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager)

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
DevHttpManager.RM.putRetrofitBuilder(
    stringKey, RetrofitBuilder
)
```

通过返回的 [RetrofitOperation][RetrofitOperation] 对象进行获取 Retrofit 或直接 create APIService

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

**DevUtilsApp Demo 完整使用查看 [RetrofitManagerUse][RetrofitManagerUse]**

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
     * 初始化 OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
     * @param context Context
     */
    fun initialize(context: Context) {
        // 设置全局 OkHttp Builder 接口对象
        DevHttpManager.RM.setOkHttpBuilder(
            mOkHttpBuilderGlobal
        )
        // 设置全局 Retrofit 重新构建监听事件
        DevHttpManager.RM.setRetrofitResetListener(
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
            AppContext.context()
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


## OkHttp 上传、下载进度监听

* **ProgressManager 方法 ->** [DevHttpManager.PM.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L219)

| 方法 | 注释 |
| :- | :- |
| getDefault | 获取全局默认 Progress Operation 操作对象 |
| getOperation | 获取 Progress Operation 操作对象 |
| containsOperation | 通过 Key 判断是否存在 Progress Operation 操作对象 |
| removeOperation | 通过 Key 解绑并返回 Operation 操作对象 |
| clearOperation | 清空所有 Progress Operation 操作对象 |
| putOperationTypeAll | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 ) |
| putOperationTypeRequest | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 ) |
| putOperationTypeResponse | 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 ) |

### 具体实现代码 [目录](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/progress)

* **Progress Operation ->** [ProgressOperation.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/progress/ProgressOperation.kt)

| 方法 | 注释 |
| :- | :- |
| setPlanType | 设置 Progress Operation 实现方式类型 |
| wrap | 进行拦截器包装 ( 必须调用 ) |
| isUseWrap | 是否已调用 wrap 方法 |
| isDeprecated | 是否废弃不使用状态 |
| isDefault | 是否全局默认操作对象 |
| isTypeAll | 是否监听上下行 |
| isTypeRequest | 是否监听上行 |
| isTypeResponse | 是否监听下行 |
| getPlanType | 获取 Progress Operation 实现方式类型 |
| getRefreshTime | 获取回调刷新时间 ( 毫秒 ) |
| setRefreshTime | 设置回调刷新时间 ( 毫秒 ) |
| resetRefreshTime | 重置回调刷新时间 ( 毫秒 ) |
| getCallback | 获取全局 Progress Callback |
| setCallback | 设置全局 Progress Callback |
| removeCallback | 移除全局 Progress Callback |
| getHandler | 获取回调 UI 线程通知 Handler |
| setHandler | 设置回调 UI 线程通知 Handler |
| resetHandler | 重置回调 UI 线程通知 Handler |
| removeSelfFromManager | 移除自身在 Manager Map 中的对象值, 并且标记为废弃状态 |
| recycleListener | 释放指定监听事件 |
| addRequestListener | 添加指定 url 上行监听事件 |
| clearRequestListener | 清空指定 url 上行所有监听事件 |
| removeRequestListener | 移除指定 url 上行监听事件 |
| addResponseListener | 添加指定 url 下行监听事件 |
| clearResponseListener | 清空指定 url 下行所有监听事件 |
| removeResponseListener | 移除指定 url 下行监听事件 |


## OkHttp 上传、下载进度监听使用

使用代码只有一步：**通过 Key 绑定并返回 Operation 操作对象**

```kotlin
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 )
DevHttpManager.PM.putOperationTypeAll(stringKey)
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )
DevHttpManager.PM.putOperationTypeRequest(stringKey)
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
DevHttpManager.PM.putOperationTypeResponse(stringKey)
// 或者使用全局默认 Progress Operation 操作对象
DevHttpManager.PM.getDefault()
```

通过返回的 [ProgressOperation][ProgressOperation] 对象进行操作，具体公开方法可以查看 [IOperation][IOperation] 接口

```kotlin
/**
 * 设置 Progress Operation 实现方式类型
 * @param planType 实现方式类型 [ProgressOperation.PLAN_A]、[ProgressOperation.PLAN_B]
 * @return IOperation
 * 在没调用 IOperation 接口任何方法前, 调用该方法切换才有效
 */
fun setPlanType(planType: Int): IOperation

/**
 * 进行拦截器包装 ( 必须调用 )
 * @param builder Builder
 * @return Builder
 */
fun wrap(builder: OkHttpClient.Builder): OkHttpClient.Builder

// ====================
// = 操作方法 - 对外公开 =
// ====================

/**
 * 移除自身在 Manager Map 中的对象值, 并且标记为废弃状态
 * 会释放所有数据、监听事件且不处理任何监听
 */
fun removeSelfFromManager()

/**
 * 释放指定监听事件
 * @param progress Progress
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun recycleListener(
    progress: Progress,
    callback: Progress.Callback
): Boolean

// ====================
// = Request Listener =
// ====================

/**
 * 添加指定 url 上行监听事件
 * @param url 请求 url
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun addRequestListener(
    url: String,
    callback: Progress.Callback
): Boolean

/**
 * 清空指定 url 上行所有监听事件
 * @param url 请求 url
 * @return `true` success, `false` fail
 */
fun clearRequestListener(url: String): Boolean

/**
 * 清空指定 url 上行所有监听事件
 * @param progress Progress
 * @return `true` success, `false` fail
 */
fun clearRequestListener(progress: Progress?): Boolean

/**
 * 移除指定 url 上行监听事件
 * @param url 请求 url
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun removeRequestListener(
    url: String,
    callback: Progress.Callback
): Boolean

/**
 * 移除指定 url 上行监听事件
 * @param progress Progress
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun removeRequestListener(
    progress: Progress?,
    callback: Progress.Callback
): Boolean

// =====================
// = Response Listener =
// =====================

/**
 * 添加指定 url 下行监听事件
 * @param url 请求 url
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun addResponseListener(
    url: String,
    callback: Progress.Callback
): Boolean

/**
 * 清空指定 url 下行所有监听事件
 * @param url 请求 url
 * @return `true` success, `false` fail
 */
fun clearResponseListener(url: String): Boolean

/**
 * 清空指定 url 下行所有监听事件
 * @param progress Progress
 * @return `true` success, `false` fail
 */
fun clearResponseListener(progress: Progress?): Boolean

/**
 * 移除指定 url 下行监听事件
 * @param url 请求 url
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun removeResponseListener(
    url: String,
    callback: Progress.Callback
): Boolean

/**
 * 移除指定 url 下行监听事件
 * @param progress Progress
 * @param callback 上传、下载回调接口
 * @return `true` success, `false` fail
 */
fun removeResponseListener(
    progress: Progress?,
    callback: Progress.Callback
): Boolean
```


**具体实现代码可以查看 [ProgressManagerUse][ProgressManagerUse]**

* 监听指定 url 进度，也是只用一句代码

```kotlin
val mOperation = DevHttpManager.PM.getDefault()
// 添加指定 url 上行监听事件
mOperation.addRequestListener(url, progressCallback)
// 添加指定 url 下行监听事件
mOperation.addResponseListener(url, progressCallback)
```

* 完整使用过程模拟代码

```kotlin
/**
 * 需要切换内部实现方式, 必须先调用该方法
 * 实现方式差异可以查看 [ProgressOperation] 类注释
 * 可不调用默认使用 PLAN_A
 */
mOperation.setPlanType(ProgressOperation.PLAN_A)
mOperation.setPlanType(ProgressOperation.PLAN_B)

// 进行拦截器包装 ( 必须调用 )
val okHttpClient = mOperation.wrap(OkHttpClient.Builder()).build()

// 基于 OkHttp 库, 不同库封装使用不同, 只要使用 wrap build 后的 client 就能够实现监听
val retrofit = Retrofit.Builder()
    // Gson 解析
    .addConverterFactory(GsonConverterFactory.create())
    // OkHttpClient
    .client(okHttpClient)
    // 服务器地址
    .baseUrl("")
    .build()


// 添加指定 url 上行监听事件
mOperation.addRequestListener(url, progressCallback)
// 添加指定 url 下行监听事件
mOperation.addResponseListener(url, progressCallback)
```





[DevComponent]: https://github.com/afkT/DevComponent
[DevComponent lib_network]: https://github.com/afkT/DevComponent/blob/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network
[WanAndroidAPI]: https://github.com/afkT/DevComponent/blob/main/application/module/module_wanandroid/src/main/java/afkt_replace/module/wan_android/data/api/WanAndroidAPI.kt
[OkHttpBuilderGlobal]: https://github.com/afkT/DevComponent/blob/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network/common/OkHttpBuilderGlobal.kt
[RetrofitResetListenerGlobal]: https://github.com/afkT/DevComponent/blob/main/component/core/libs/lib_network/src/main/java/afkt_replace/core/lib/network/common/RetrofitResetListenerGlobal.kt
[RetrofitOperation]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitOperation.kt
[ProgressOperation]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/progress/ProgressOperation.kt
[IOperation]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/progress/operation/IOperation.kt
[ProgressManagerUse]: https://github.com/afkT/DevUtils/blob/master/application/DevUtilsApp/src/main/java/afkt/project/base/http/ProgressManagerUse.kt
[RetrofitManagerUse]: https://github.com/afkT/DevUtils/blob/master/application/DevUtilsApp/src/main/java/afkt/project/base/http/RetrofitManagerUse.kt