
## DevHttpCapture

* 该库对使用 OkHttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。

* **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器，并支持自定义数据转换。

* 内置三个 Http 抓包拦截器，SimpleInterceptor ( 简单的抓包回调拦截器 )、CallbackInterceptor ( 无存储逻辑，进行回调通知 )、StorageInterceptor ( 存在存储抓包数据逻辑 )

* `DevHttpCaptureCompiler` 提供对 `DevHttpCapture` 抓包库可视化功能


### 最新版本

module | DevHttpCapture | DevHttpCaptureCompiler | DevHttpCaptureCompilerRelease
:---:|:---:|:---:|:---:
version | [![][maven_svg]][maven] | [![][maven_svg]][maven] | [![][maven_svg]][maven]


### Gradle

```groovy
dependencies {
    // DevHttpCapture - OkHttp 抓包工具库
    implementation 'io.github.afkt:DevHttpCapture:1.1.9'

    // DevHttpCaptureCompiler - OkHttp 抓包工具库 ( 可视化功能 )
    debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.1.9'
    releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.1.9'
}
```


### 示例项目

直接运行、查看使用示例项目代码！！！

[DevHttpCaptureUse][DevHttpCaptureUse]！！！
[DevHttpCaptureUse][DevHttpCaptureUse]！！！
[DevHttpCaptureUse][DevHttpCaptureUse]！！！


### 使用示例

```kotlin

// ==================
// = DevHttpCapture =
// ==================

// 添加 Http 抓包拦截处理
DevHttpCapture.addInterceptor(
    OkHttpClient.Builder, moduleName
)

// 移除对应 Module Http 抓包拦截
DevHttpCapture.removeInterceptor(moduleName)

// 更新对应 Module Http 抓包拦截处理
DevHttpCapture.updateInterceptor(moduleName, capture)

// 是否存在对应 Module Http 抓包拦截
DevHttpCapture.containsInterceptor(moduleName)

// ===================================
// = DevHttpCaptureCompiler 抓包可视化 =
// ===================================

// 显示所有 Module 抓包数据
DevHttpCaptureCompiler.start(context)
// 显示指定 Module 抓包数据
DevHttpCaptureCompiler.start(context, moduleName)

// =======
// = 可选 =
// =======

// 添加接口所属功能注释
DevHttpCaptureCompiler.putUrlFunction(moduleName, UrlFunctionGet)
// 移除接口所属功能注释
DevHttpCaptureCompiler.removeUrlFunction(moduleName)
```


## DevHttpCapture API

* **OkHttp 抓包工具库 ->** [DevHttpCapture.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/DevHttpCapture.kt#L47)

| 方法 | 注释 |
| :- | :- |
| getDevHttpCaptureVersionCode | 获取 DevHttpCapture 版本号 |
| getDevHttpCaptureVersion | 获取 DevHttpCapture 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |
| addInterceptor | 添加 Http 抓包拦截处理 |
| removeInterceptor | 移除对应 Module Http 抓包拦截 |
| updateInterceptor | 更新对应 Module Http 抓包拦截处理 |
| containsInterceptor | 是否存在对应 Module Http 抓包拦截 |
| getModulePath | 获取指定模块抓包存储路径 |
| getModuleHttpCaptures | 获取指定模块所有抓包数据 |
| utils | 对外公开快捷工具类 ( UtilsPublic ) |


## <span id="devcapture">**`dev.capture`**</span>

* **简单的抓包回调拦截器 ( 无存储逻辑 ) ->** [SimpleInterceptor.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interceptor/SimpleInterceptor.kt)
* **Http 抓包拦截器 ( 无存储逻辑, 通过回调通知 ) ->** [CallbackInterceptor.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interceptor/CallbackInterceptor.kt)
* **Http 抓包拦截器 ( 存在存储抓包数据逻辑 ) ->** [StorageInterceptor.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interceptor/StorageInterceptor.kt)

* **抓包信息封装类 ->** [CaptureInfo.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/DataModel.kt#L67)

| 方法 | 注释 |
| :- | :- |
| requestTime | 请求时间 ( 毫秒 ) |
| requestUrl | 请求链接 |
| requestMethod | 请求方法 |
| requestHeader | 请求头信息 |
| requestBody | 请求数据 |
| responseTime | 响应时间 ( 毫秒 ) |
| responseStatus | 响应状态 |
| responseHeader | 响应头信息 |
| responseBody | 响应数据 |
| toJson | 将对象转换为 JSON String |


* **Http 拦截过滤器 ->** [IHttpFilter.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interfaces/Interface.kt#L10)

| 方法 | 注释 |
| :- | :- |
| filter | 是否过滤该 Http 请求不进行抓包存储 |


* **Http 抓包接口信息获取 ->** [IHttpCapture.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interfaces/Interface.kt#L34)

| 方法 | 注释 |
| :- | :- |
| getModuleName | 获取模块名 ( 要求唯一性 ) |
| getEncrypt | 获取抓包数据加密中间层 |
| getHttpFilter | 获取 Http 拦截过滤器 |
| isCapture | 是否进行 Http 抓包拦截 |
| setCapture | 设置是否进行 Http 抓包拦截 |
| captureRedact | 获取抓包信息隐藏字段 |
| getModulePath | 获取模块抓包存储路径 |
| getModuleHttpCaptures | 获取模块所有抓包数据 |


* **Http 抓包事件回调 ->** [IHttpCaptureEvent.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/interfaces/Interface.kt#L97)

| 方法 | 注释 |
| :- | :- |
| callRequestUrl | 生成请求链接字符串 |
| callRequestMethod | 生成请求方法字符串 |
| callRequestHeaders | 生成请求头信息 Map |
| callRequestBody | 生成请求体信息 Map |
| callResponseStatus | 生成响应状态 Map |
| callResponseHeaders | 生成响应头信息 Map |
| callResponseBodyFailed | 生成错误响应体信息 |
| callResponseBody | 生成响应体信息 Map |
| converterRequestBody | 转换请求体信息 Map |
| callEnd | 抓包结束 |


* **抓包存储文件 ->** [CaptureFile.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/DataModel.kt#L109)

| 方法 | 注释 |
| :- | :- |
| getUrl | getUrl |
| getMethod | getMethod |
| isEncrypt | isEncrypt |
| getTime | getTime |
| getFileName | getFileName |
| getModuleName | getModuleName |
| getHttpCaptureData | 获取请求数据 ( 抓包数据 ) |
| getCaptureInfo | 获取抓包信息封装类 |
| toJson | 将对象转换为 JSON String |
| deleteFile | 删除该对象抓包存储文件 |
| getFile | 获取该对象抓包存储文件 |
| getDataFile | 获取该对象抓包数据存储文件 |


* **对外公开快捷方法 ->** [UtilsPublic.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCapture/src/main/java/dev/capture/Utils.kt)

| 方法 | 注释 |
| :- | :- |
| getStoragePath | 获取抓包存储路径 |
| getModulePath | 获取指定模块抓包存储路径 |
| getAllModuleName | 获取全部模块名 |
| getAllModule | 获取全部模块所有抓包数据 |
| deleteModule | 删除指定模块抓包数据 |
| deleteAllModule | 删除全部模块抓包数据 |
| getModuleFileSize | 获取指定模块抓包文件大小 |
| getAllModuleFileSize | 获取全部模块抓包文件大小 |
| getModuleFileLength | 获取指定模块抓包文件大小 |
| getAllModuleFileLength | 获取全部模块抓包文件大小 |


## DevHttpCaptureCompiler API ( 抓包可视化 )

* **OkHttp 抓包工具库 ->** [DevHttpCaptureCompiler.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/DevHttpCaptureCompiler.kt)

| 方法 | 注释 |
| :- | :- |
| finishAllActivity | 结束所有 Activity |
| start | 跳转抓包数据可视化 Activity |
| putUrlFunction | 添加接口所属功能注释 |
| removeUrlFunction | 移除接口所属功能注释 |


## <span id="devcapture">**`dev.capture`**</span>


* **接口所属功能注释获取 ->** [UrlFunctionGet.kt](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/UrlFunctionGet.kt)

| 方法 | 注释 |
| :- | :- |
| toUrlFunction | 接口所属功能注释获取 |





[maven_svg]: https://img.shields.io/badge/Maven-1.1.9-brightgreen.svg
[maven]: https://search.maven.org/search?q=io.github.afkt
[DevHttpCaptureUse]: https://github.com/afkT/DevUtils/blob/master/application/DevHttpCaptureUse/src/main/java/afkt/httpcapture/use