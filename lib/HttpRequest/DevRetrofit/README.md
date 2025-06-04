

## 摘要

* [框架功能介绍](#框架功能介绍)
* [示例项目](#示例项目)
* [API 文档](#API-文档)
* [使用步骤](#使用步骤)


## Gradle

```gradle
// DevRetrofit - Retrofit + Kotlin Coroutines 封装
implementation 'io.github.afkt:DevRetrofit:1.0.7'
```

## 框架功能介绍

DevRetrofit 基于 Retrofit + Kotlin Coroutines 进行封装的网络层封装库，
针对 `CoroutineScope`、`ViewModel`、`Lifecycle`、`LifecycleOwner` 及 `LifecycleOwner` 实现类 ( `Activity`、`Fragment` 等 ) 封装快捷扩展函数。

并对上述封装的请求方法扩展函数支持传入 `方法体`、`Callback`、`ResultCallback`。

推荐在该基础上进行二次封装，并搭配 [DevHttpCapture][DevHttpCapture]、[DevHttpManager][DevHttpManager] 使用。


## 示例项目

直接运行、查看使用示例项目代码！！！

[DevRetrofitUse][DevRetrofitUse]！！！
[DevRetrofitUse][DevRetrofitUse]！！！
[DevRetrofitUse][DevRetrofitUse]！！！


## 项目类结构 - [包目录][包目录]

* 数据模型类 [model.kt][model.kt]：DevRetrofit Base 数据模型、接口汇总类

* 封装请求方法 [request.kt][request.kt]：整个 DevRetrofit 库最终实现，统一调用该请求封装方法

* 请求方法协程扩展函数 [request_coroutines.kt][request_coroutines.kt]：在 `request.kt` 基础上封装使用协程

* Callback 扩展函数 [request_coroutines_simple.kt][request_coroutines_simple.kt]：在 `request_coroutines.kt` 基础上减少 start、success、error、finish 方法体传参，使用 Callback、ResultCallback


## API 文档

* **Base.Response 请求响应解析类 ( 接口 ) ->** [Base.Response][Base.Response]

| 方法                | 注释                      |
|:------------------|:------------------------|
| getData           | 获取响应数据                  |
| getCode           | 获取响应 Code               |
| getMessage        | 获取提示 Message            |
| isSuccess         | 判断请求是否成功                |
| isSuccessWithData | 判断请求是否成功并且 Data 不为 null |
| hasData           | 判断 Data 是否不为 null       |
| requireData       | 获取不为 null 的 Data        |

* **请求方法协程扩展函数 ->** [request_coroutines.kt][request_coroutines.kt]

| 方法                                         | 注释                                               |
|:-------------------------------------------|:-------------------------------------------------|
| CoroutineScope.scopeExecuteRequest         | 无任何额外逻辑封装, 支持自定义解析、处理等代码                         |
| CoroutineScope.scopeExecuteResponseRequest | 封装为 Base.Response、Base.Result 进行响应               |
| launchExecuteRequest                       | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |
| launchExecuteResponseRequest               | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |

* **Callback 扩展函数 ->** [request_coroutines_simple.kt][request_coroutines_simple.kt]

| 方法                                               | 注释                                               |
|:-------------------------------------------------|:-------------------------------------------------|
| CoroutineScope.simpleScopeExecuteRequest         | 无任何额外逻辑封装, 支持自定义解析、处理等代码                         |
| CoroutineScope.simpleScopeExecuteResponseRequest | 封装为 Base.Response、Base.Result 进行响应               |
| simpleLaunchExecuteRequest                       | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |
| simpleLaunchExecuteResponseRequest               | ViewModel、Lifecycle、LifecycleOwner 扩展函数 ( 功能如上 ) |


## 使用步骤

### 1. 首先创建 Response 请求响应解析类 ( 非必须 )

`需要创建 Response 类并实现 Base.Response 解析类`

主要是为了解决一个问题：**新旧 API 变更、使用第三方接口、SDK 等返回结构不一致时，可自定义解析类进行解析处理**

假设 A 项目新接口，后台返回响应数据结构为

```json
{
    "resultCode" : 200,
    "message" : "success",
    "result" : Object
}
```

A 项目旧接口，后台返回响应数据结构为

```json
{
    "responseCode" : 0,
    "responseMessage" : "获取成功",
    "response" : Object
}
```

第三方 SDK 接口，后台返回响应数据结构为

```json
{
    "code": "200",
    "message": "查询成功",
    "response" : Object
}
```

等等诸如此类不同公司差异化字段命名、字段类型，创建 `Response` 解析类即可解决

```kotlin
/**
 * detail: App 统一响应模型
 * @author Ttt
 * 解决不同请求响应字段不一致情况
 */
open class AppResponse<T> : Base.Response<T> {

    // ===========================
    // = error.json、photos.json =
    // ===========================

    private var code: String? = null
    private var message: String? = null
    private var data: T? = null

    // ==============
    // = books.json =
    // ==============

    private var resultCode: Int? = null
    private var result: T? = null

    // =====================
    // = movie_detail.json =
    // =====================

    private var responseCode: Int? = null
    private var responseMessage: String? = null
    private var response: T? = null

    // =====================
    // = Base.Response 实现 =
    // =====================

    override fun getData(): T? {
        if (data != null) return data
        if (result != null) return result
        if (response != null) return response
        return null
    }

    override fun getCode(): String? {
        if (code != null) return code
        if (resultCode != null) return resultCode.toString()
        if (responseCode != null) return responseCode.toString()
        return null
    }

    override fun getMessage(): String? {
        if (message != null) return message
        if (responseMessage != null) return responseMessage
        return null
    }

    override fun isSuccess(): Boolean {
//        // 方式一
//        val _code = getCode().orEmpty()
//        return when (_code) {
//            "200", "0" -> true
//            else -> false
//        }
        // 方式二
        if (code == "200") return true
        if (resultCode == 200) return true
        if (responseCode == 0) return true
        return false
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

```kotlin
// 具体查看【示例项目】
fun fetchData(
    viewModel: ViewModel,
    startBlock: () -> Unit = {},
    finishBlock: () -> Unit = {},
    errorBlock: (Throwable) -> Unit = {},
    successBlock: (Bean) -> Unit
) {
    viewModel.launchExecuteRequest(block = {
        RetrofitAPI.api().getData()
    }, start = {
        startBlock.invoke()
    }, success = { result ->
        successBlock.invoke(result)
    }, error = {
        errorBlock.invoke(it)
    }, finish = {
        finishBlock.invoke()
    })
}
```





[DevRetrofitUse]: https://github.com/afkT/DevUtils/blob/master/application/DevRetrofitUse/src/main/java/afkt/retrofit/use
[包目录]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit
[model.kt]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit/model.kt
[request.kt]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit/request.kt
[request_coroutines.kt]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit/request_coroutines.kt
[request_coroutines_simple.kt]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit/request_coroutines_simple.kt
[Base.Response]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevRetrofit/src/main/java/dev/retrofit/model.kt#L15
[DevHttpCapture]: https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/README.md
[DevHttpManager]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/README.md
