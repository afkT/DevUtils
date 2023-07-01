
## Gradle

```gradle
implementation 'io.github.afkt:DevHttpCapture:1.1.4'
```

## 目录结构

```
- dev                   | 根目录
   - capture            | Http 抓包实现代码
```


## 框架功能介绍

> 该库主要对使用 OkHttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。
>
> **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器。
>
> 内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )

### 使用示例

```kotlin
// 添加 Http 抓包拦截处理
DevHttpCapture.addInterceptor(
    OkHttpClient.Builder, moduleName
)

// 移除对应 Module Http 抓包拦截
DevHttpCapture.removeInterceptor(moduleName)

// 更新对应 Module Http 抓包拦截处理
DevHttpCapture.updateInterceptor(moduleName, capture)
```

## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/CHANGELOG.md)

## API


- dev                                         | 根目录
    - [capture](#devcapture)                   | Http 抓包实现代码


## <span id="dev">**`dev`**</span>


* **OkHttp 抓包工具库 ->** [DevHttpCapture.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/DevHttpCapture.kt)

| 方法 | 注释 |
| :- | :- |
| getDevHttpCaptureVersionCode | 获取 DevHttpCapture 版本号 |
| getDevHttpCaptureVersion | 获取 DevHttpCapture 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |
| addInterceptor | 添加 Http 抓包拦截处理 |
| containsInterceptor | 是否存在对应 Module Http 抓包拦截 |
| removeInterceptor | 移除对应 Module Http 抓包拦截 |
| updateInterceptor | 更新对应 Module Http 抓包拦截处理 |
| getModulePath | 获取指定模块抓包存储路径 |
| getModuleHttpCaptures | 获取指定模块所有抓包数据 |
| utils | 对外公开快捷工具类 ( UtilsPublic ) |


## <span id="devcapture">**`dev.capture`**</span>


* **Http 抓包拦截器 ( 无存储逻辑, 进行回调通知 ) ->** [CallbackInterceptor.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/CallbackInterceptor.kt)
* **Http 抓包拦截器 ( 存在存储抓包数据逻辑 ) ->** [HttpCaptureInterceptor.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/HttpCaptureInterceptor.kt)


* **对外公开快捷方法 ->** [UtilsPublic.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Utils.kt)

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


* **抓包存储文件 ->** [CaptureFile.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Model.kt#L103)

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


* **抓包信息封装类 ->** [CaptureInfo.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Model.kt#L67)

| 方法 | 注释 |
| :- | :- |
| requestUrl | 请求链接 |
| requestMethod | 请求方法 |
| requestHeader | 请求头信息 |
| requestBody | 请求数据 |
| responseStatus | 响应状态 |
| responseHeader | 响应头信息 |
| responseBody | 响应数据 |
| toJson | 将对象转换为 JSON String |


* **Http 拦截过滤器 ->** [IHttpFilter.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Interface.kt)

| 方法 | 注释 |
| :- | :- |
| filter | 是否过滤该 Http 请求不进行抓包存储 |


* **Http 抓包接口信息获取 ->** [IHttpCapture.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Interface.kt#L31)

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


* **Http 抓包事件回调 ->** [IHttpCaptureEvent.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/Interface.kt#L94)

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