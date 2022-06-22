

## 摘要

* [框架功能介绍](#框架功能介绍)
* [API 文档](#API-文档)
* [使用示例](#使用示例)
* [使用步骤](#使用步骤)
* [总结与扩展](#总结与扩展)


## Gradle

```gradle
implementation 'io.github.afkt:DevRetrofit:1.0.0'
```

## 框架功能介绍

DevRetrofit 是基于 Retrofit + Kotlin Coroutines 进行封装的网络层封装库，
针对 `CoroutineScope`、`ViewModel`、`Lifecycle`、`LifecycleOwner` 及 `LifecycleOwner` 实现类 ( `Activity`、`Fragment` 等 ) 封装快捷扩展函数。

并对上述封装的请求方法扩展函数支持传入 `LiveData`、`方法体`、`Callback`、`ResultCallback`。


## 项目类结构 - [包目录][包目录]

* 数据模型类 [model.kt][model.kt]：DevRetrofit Base 数据模型、接口汇总类

* 数据模型扩展类 [model_ext.kt][model_ext.kt]：`model.kt` 扩展函数汇总类

* 封装请求方法 [request.kt][request.kt]：整个 DevRetrofit 库最终实现，统一调用该请求封装方法

* 请求方法协程扩展函数 [request_coroutines.kt][request_coroutines.kt]：在 `request.kt` 基础上封装使用协程

* Callback 扩展函数 [request_coroutines_simple.kt][request_coroutines_simple.kt]：在 `request_coroutines.kt` 基础上减少 start、success、error、finish 方法体传参，使用 Callback、ResultCallback

* LiveData 扩展函数 [request_coroutines_simple_livedata.kt][request_coroutines_simple_livedata.kt]：在 `request_coroutines_simple.kt` 基础上使用 LiveData


## API 文档

* **Base.Response 请求响应解析类 ( 接口 ) ->** [Base.Response][Base.Response]

| 方法 | 注释 |
| :- | :- |
| getData | 获取响应数据 |
| getCode | 获取响应 Code |
| getMessage | 获取提示 Message |
| isSuccess | 判断请求是否成功 |

* **Base.Result 请求结果包装类 ->** [Base.Result][Base.Result]

| 方法 | 注释 |
| :- | :- |
| getData | 获取响应数据 |
| getCode | 获取响应 Code |
| getMessage | 获取提示 Message |
| isSuccess | 判断请求是否成功 |
| getResponse | 获取 Base.Response |
| isError | 是否请求抛出异常 |
| getError | 获取异常信息 |
| getErrorCode | 获取错误类型 |
| getParams | 获取额外携带参数 |
| setParams | 设置额外携带参数 |

* **Notify.Callback 请求每个阶段通知回调 ->** [Notify.Callback][Notify.Callback]

| 方法 | 注释 |
| :- | :- |
| onStart | 开始请求 |
| onSuccess | 请求成功 |
| onError | 请求异常 |
| onFinish | 请求结束 |
| getParams | 获取额外携带参数 |
| setParams | 设置额外携带参数 |

* **Notify.ResultCallback 请求结果阶段通知回调 ->** [Notify.ResultCallback][Notify.ResultCallback]

| 方法 | 注释 |
| :- | :- |
| onStart | 开始请求 |
| onSuccess | 请求成功 |
| onFinish | 请求结束 |
| getParams | 获取额外携带参数 |
| setParams | 设置额外携带参数 |

* **Notify.GlobalCallback 全局通知回调 ->** [Notify.GlobalCallback][Notify.GlobalCallback]

| 方法 | 注释 |
| :- | :- |
| onStart | 开始请求 |
| onSuccess | 请求成功 |
| onError | 请求异常 |
| onFinish | 请求结束 |

* **整个 DevRetrofit 库最终调用方法 ->** [request.kt][request.kt]

| 方法 | 注释 |
| :- | :- |
| finalExecute | 无任何额外逻辑封装, 支持自定义解析、处理等代码 |
| finalExecuteResponse | 封装为 Base.Response、Base.Result 进行响应 |

* **请求方法协程扩展函数 ->** [request_coroutines.kt][request_coroutines.kt]

| 方法 | 注释 |
| :- | :- |
| CoroutineScope.scopeExecuteRequest | 无任何额外逻辑封装, 支持自定义解析、处理等代码 |
| CoroutineScope.scopeExecuteResponseRequest | 封装为 Base.Response、Base.Result 进行响应 |
| launchExecuteRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |
| launchExecuteResponseRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |

* **Callback 扩展函数 ->** [request_coroutines_simple.kt][request_coroutines_simple.kt]

| 方法 | 注释 |
| :- | :- |
| CoroutineScope.simpleScopeExecuteRequest | 无任何额外逻辑封装, 支持自定义解析、处理等代码 |
| CoroutineScope.simpleScopeExecuteResponseRequest | 封装为 Base.Response、Base.Result 进行响应 |
| simpleLaunchExecuteRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |
| simpleLaunchExecuteResponseRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |

* **LiveData 扩展函数 ->** [request_coroutines_simple_livedata.kt][request_coroutines_simple_livedata.kt]

| 方法 | 注释 |
| :- | :- |
| CoroutineScope.liveDataScopeExecuteRequest | 无任何额外逻辑封装, 支持自定义解析、处理等代码 |
| CoroutineScope.liveDataScopeExecuteResponseRequest | 封装为 Base.Response、Base.Result 进行响应 |
| liveDataLaunchExecuteRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |
| liveDataLaunchExecuteResponseRequest | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |


## 使用示例

具体实现代码可以查看 [DevRetrofitCoroutinesDemo][DevRetrofitCoroutinesDemo]。


## 使用步骤

### 1. 首先创建 Response 请求响应解析类

`需要创建 Response 类并实现 Base.Response 解析类`

主要是为了解决一个问题：

假设 A 公司，后台返回响应数据结构为

```json
{
    "resultData": Object,
    "resultCode": 200,
    "errorMessage": "错误提示",
    "isToast": true
}
```

B 公司，后台返回响应数据结构为

```json
{
    "response": Object,
    "code": "200",
    "toast": "提示消息"
}
```

等等诸如此类不同公司差异化字段命名、字段类型，以 A 公司为例定义 `BaseResponse`

```kotlin
/**
 * detail: 统一响应实体类
 * @author Ttt
 */
open class BaseResponse<T> : Base.Response<T> {

    private var resultData: T? = null
    private var resultCode: Int = 0
    private var errorMessage: String? = null
    private var isToast: Boolean = false

    // =================
    // = Base.Response =
    // =================

    override fun getData(): T? {
        return resultData
    }

    override fun getCode(): String? {
        return resultCode.toString()
    }

    override fun getMessage(): String? {
        return errorMessage
    }

    override fun isSuccess(): Boolean {
        return resultCode == 200
    }

    // ==============
    // = 自定义差异化 =
    // ==============

    fun isToast(): Boolean {
        return isToast
    }
}
```

只要实现 `Base.Response` 四个核心方法按照对应意思 return 即可

| 方法 | 注释 |
| :- | :- |
| getData | 获取响应数据 |
| getCode | 获取响应 Code |
| getMessage | 获取提示 Message |
| isSuccess | 判断请求是否成功 |

> 有其他额外的字段如 `isToast` 则自行添加获取方法即可。

如果以 B 公司定义 `BaseResponse` 将会是这样

```kotlin
/**
 * detail: 统一响应实体类
 * @author Ttt
 */
open class BaseResponse<T> : Base.Response<T> {

    private var response: T? = null
    private var code: String? = null
    private var toast: String? = null

    // =================
    // = Base.Response =
    // =================

    override fun getData(): T? {
        return response
    }

    override fun getCode(): String? {
        return code
    }

    override fun getMessage(): String? {
        return toast
    }

    override fun isSuccess(): Boolean {
        return code?.let { code ->
            // 自定义 code 为 200 表示请求成功 ( 后台定义 )
            StringUtils.equals(code, "200")
        } ?: false
    }
}
```

### 2. 创建 Callback 每个阶段通知回调类 ( 非必须 )

创建 Base Callback 是为了整个项目统一使用，内部可添加`额外逻辑处理`、`特殊字段存储`等，视项目情况定义，参考如下：

```kotlin
// 封装 Base Notify.Callback
abstract class BaseCallback<T> : Notify.Callback<T>()

// 封装 Base Notify.ResultCallback 简化代码
abstract class BaseResultCallback<T> : Notify.ResultCallback<T, BaseResponse<T>>()
```

### 3. 发起请求 ( 最后一步 )

以`获取文章列表`请求为例

```kotlin
/**
 * detail: 文章实体类
 * @author Ttt
 */
data class ArticleBean(
    val content: String?,
    val cover: String?
)

/**
 * detail: 文章数据响应类 ( 可不定义, 只是为了方便理解、展示 )
 * @author Ttt
 * data 映射实体类为 List<ArticleBean?>
 */
class ArticleResponse : BaseResponse<List<ArticleBean?>>()

/**
 * detail: 服务器接口 API Service
 * @author Ttt
 */
interface APIService {

    @GET("xxx")
    suspend fun loadArticleList(@Path("page") page: Int): ArticleResponse // or BaseResponse<List<ArticleBean?>>
}
```

针对 `CoroutineScope`、`ViewModel`、`Lifecycle`、`LifecycleOwner` 及 `LifecycleOwner` 实现类 ( `Activity`、`Fragment` 等 ) 封装快捷扩展函数。

以下例子，模拟在 `Activity` 下使用 ( 在 `ViewModel ( 上述 )` 下使用也一样 )

```kotlin
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
```

## 总结与扩展

至此，整个 `DevRetrofit` 库使用及介绍如上，但是 **`强烈推荐`** 在该基础上进行二次封装，并搭配 [DevHttpCapture][DevHttpCapture]、[DevHttpManager][DevHttpManager] 使用。

**`二次封装`**：例针对 Activity、Fragment 所属 `ViewModel` 统一添加 Request Loading Dialog 等，以及其他`全局回调`、`请求阶段数据记录`等各种独立项目逻辑

并且针对请求方法二次封装，统一使用如 `ViewModel`、`LiveData` 等方法入参参数，方便排查调试以及统一维护。





[DevRetrofit]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit
[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit
[model.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt
[model_ext.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model_ext.kt
[request.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/request.kt
[request_coroutines.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/request_coroutines.kt
[request_coroutines_simple.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/request_coroutines_simple.kt
[request_coroutines_simple_livedata.kt]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/request_coroutines_simple_livedata.kt
[Base.Response]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt#L15
[Base.Result]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt#L29
[Notify.Callback]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt#L160
[Notify.ResultCallback]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt#L230
[Notify.GlobalCallback]: https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/src/main/java/dev/retrofit/model.kt#L280
[DevRetrofitCoroutinesDemo]: https://github.com/afkT/DevUtils/blob/master/application/DevUtilsApp/src/main/java/afkt/project/use_demo/DevRetrofitCoroutinesDemo.kt
[DevHttpCapture]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
[DevHttpManager]: https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
