

## 摘要

* [框架功能介绍](#框架功能介绍)
* [API 文档](#API-文档)


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

* **request.kt 最终执行方法 ->** [request.kt][request.kt]

| 方法 | 注释 |
| :- | :- |
| finalExecute | 无任何封装, 支持自定义解析、处理等代码 |
| finalExecuteResponse | 封装为 Base.Response、Base.Result 进行响应 |


newline





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
