

## 摘要

* [框架功能介绍](#框架功能介绍)
* [示例项目](#示例项目)
* [API 文档](#API-文档)
* [Retrofit 多 BaseUrl 管理功能使用](#Retrofit-多-BaseUrl-管理功能使用)
* [OkHttp 上传、下载进度监听使用](#OkHttp-上传下载进度监听使用)


## Gradle

```gradle
// DevHttpManager - OkHttp 管理库 ( Retrofit 多 BaseUrl 管理、Progress 监听 )
implementation 'io.github.afkt:DevHttpManager:1.0.9'
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


## 示例项目

直接运行、查看使用示例项目代码！！！

[DevHttpManagerUse][DevHttpManagerUse]！！！
[DevHttpManagerUse][DevHttpManagerUse]！！！
[DevHttpManagerUse][DevHttpManagerUse]！！！


## API 文档

* **DevHttpManager 管理库方法 ->** [DevHttpManager.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/DevHttpManager.kt)

| 方法                           | 注释                    |
|:-----------------------------|:----------------------|
| getDevHttpManagerVersionCode | 获取 DevHttpManager 版本号 |
| getDevHttpManagerVersion     | 获取 DevHttpManager 版本  |
| getDevAppVersionCode         | 获取 DevApp 版本号         |
| getDevAppVersion             | 获取 DevApp 版本          |
| RM                           | RetrofitManager 方法    |
| PM                           | ProgressManager 方法    |


* **RetrofitManager 方法 ->** [DevHttpManager.RM.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L77)

| 方法                          | 注释                                             |
|:----------------------------|:-----------------------------------------------|
| getOkHttpBuilder            | 获取全局 OkHttp Builder 接口对象                       |
| setOkHttpBuilder            | 设置全局 OkHttp Builder 接口对象                       |
| removeOkHttpBuilder         | 移除全局 OkHttp Builder 接口对象                       |
| getRetrofitResetListener    | 获取全局 Retrofit 重新构建监听事件                         |
| setRetrofitResetListener    | 设置全局 Retrofit 重新构建监听事件                         |
| removeRetrofitResetListener | 移除全局 Retrofit 重新构建监听事件                         |
| getOperation                | 获取 Retrofit Operation 操作对象                     |
| containsOperation           | 通过 Key 判断是否存在 Retrofit Operation 操作对象          |
| putRetrofitBuilder          | 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象 |
| removeRetrofitBuilder       | 通过 Key 解绑移除 RetrofitBuilder 并返回 Operation 操作对象 |
| reset                       | 重置处理 ( 重新构建 Retrofit )                         |
| resetAll                    | 重置处理 ( 重新构建全部 Retrofit )                       |


* **ProgressManager 方法 ->** [DevHttpManager.PM.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/DevHttpManager.kt#L219)

| 方法                       | 注释                                    |
|:-------------------------|:--------------------------------------|
| getDefault               | 获取全局默认 Progress Operation 操作对象        |
| getOperation             | 获取 Progress Operation 操作对象            |
| containsOperation        | 通过 Key 判断是否存在 Progress Operation 操作对象 |
| removeOperation          | 通过 Key 解绑并返回 Operation 操作对象           |
| clearOperation           | 清空所有 Progress Operation 操作对象          |
| putOperationTypeAll      | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 ) |
| putOperationTypeRequest  | 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )  |
| putOperationTypeResponse | 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )  |



# Retrofit 多 BaseUrl 管理功能使用

### 具体实现代码 [目录](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/http/manager)

使用代码只有一步：**通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象**

```kotlin
// 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象
DevHttpManager.RM.putRetrofitBuilder(
    moduleKey, RetrofitBuilder
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

**完整使用查看示例项目 [DevHttpManagerUse - MediaAPI][DevHttpManagerUse - MediaAPI]**



## OkHttp 上传、下载进度监听使用

### 具体实现代码 [目录](https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/http/progress)

使用代码只有一步：**通过 Key 绑定并返回 Operation 操作对象**

```kotlin
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听上下行 )
DevHttpManager.PM.putOperationTypeAll(moduleKey)
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听上行 )
DevHttpManager.PM.putOperationTypeRequest(moduleKey)
// 通过 Key 绑定并返回 Operation 操作对象 ( 监听下行 )
DevHttpManager.PM.putOperationTypeResponse(moduleKey)
// 或者使用全局默认 Progress Operation 操作对象
DevHttpManager.PM.getDefault()
```

* 监听指定 url 进度，也是只用一句代码

```kotlin
// 通过返回的 Operation 对象进行操作
val mOperation = DevHttpManager.PM.getDefault()
// 添加指定 url 上行监听事件
mOperation.addRequestListener(url, progressCallback)
// 添加指定 url 下行监听事件
mOperation.addResponseListener(url, progressCallback)
```

通过返回的 [ProgressOperation][ProgressOperation] 对象进行操作，具体公开方法可以查看 [IOperation][IOperation] 接口

**完整使用查看示例项目 [DevHttpManagerUse - 上传、下载进度监听使用][DevHttpManagerUse - 上传、下载进度监听使用]**

```kotlin
/**
 * 需要切换内部实现方式, 必须先调用该方法
 * 实现方式差异可以查看 ProgressOperation 类注释
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




[DevHttpManagerUse]: https://github.com/afkT/DevUtils/blob/master/application/DevHttpManagerUse/src/main/java/afkt/httpmanager/use
[DevHttpManagerUse - MediaAPI]: https://github.com/afkT/DevUtils/blob/master/application/DevHttpManagerUse/src/main/java/afkt/httpmanager/use/feature/media/data/api/MediaAPI.kt
[DevHttpManagerUse - 上传、下载进度监听使用]: https://github.com/afkT/DevUtils/tree/master/application/DevHttpManagerUse/src/main/java/afkt/httpmanager/use/feature/progress
[RetrofitOperation]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/http/manager/RetrofitOperation.kt
[ProgressOperation]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/http/progress/ProgressOperation.kt
[IOperation]: https://github.com/afkT/DevUtils/blob/master/lib/HttpRequest/DevHttpManager/src/main/java/dev/http/progress/operation/IOperation.kt