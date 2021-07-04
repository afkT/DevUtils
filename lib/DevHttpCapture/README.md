
## Gradle

```java
implementation 'io.github.afkt:DevHttpCapture:1.0.0'
```

## 目录结构

```
- dev             | 根目录
   - capture      | Http 抓包实现代码
```


## 使用

> 该库主要对使用 Okhttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。
>
> **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器。
>
> 内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )

## API


- dev                                   | 根目录
   - [capture](#devcapture)             | Http 抓包实现代码




## <span id="dev">**`dev`**</span>


* **OKHttp 抓包工具库 ->** [DevHttpCapture.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/DevHttpCapture.java)

| 方法 | 注释 |
| :- | :- |
| getDevHttpCaptureVersionCode | 获取 DevHttpCapture 版本号 |
| getDevHttpCaptureVersion | 获取 DevHttpCapture 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |
| addInterceptor | 添加 Http 抓包拦截处理 |
| isContainsModule | 是否存在对应 Module Http 抓包拦截 |
| removeInterceptor | 移除对应 Module Http 抓包拦截 |
| updateInterceptor | 更新对应 Module Http 抓包拦截处理 |
| getModulePath | 获取指定模块抓包存储路径 |
| getModuleHttpCaptures | 获取指定模块所有抓包数据 |


## <span id="devcapture">**`dev.capture`**</span>


* **Http 抓包拦截器 ( 无存储逻辑, 进行回调通知 ) ->** [CallbackInterceptor.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/CallbackInterceptor.java)

| 方法 | 注释 |
| :- | :- |
| intercept | intercept |
| innerResponse | 内部抓包方法 |


* **抓包存储文件 ->** [CaptureFile.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/CaptureFile.java)

| 方法 | 注释 |
| :- | :- |
| getUrl | getUrl |
| getMethod | getMethod |
| getHttpCaptureData | getHttpCaptureData |
| isEncrypt | isEncrypt |
| getTime | getTime |
| getFileName | getFileName |
| getModuleName | getModuleName |
| getCaptureInfo | 获取抓包数据实体类 |
| toJson | 将对象转换为 JSON String |
| deleteFile | 删除该对象抓包存储文件 |
| getFile | 获取该对象抓包存储文件 |


* **抓包数据 ->** [CaptureInfo.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/CaptureInfo.java)

| 方法 | 注释 |
| :- | :- |
| getRequestUrl | getRequestUrl |
| getRequestMethod | getRequestMethod |
| getRequestHeader | getRequestHeader |
| getRequestBody | getRequestBody |
| getResponseStatus | getResponseStatus |
| getResponseHeader | getResponseHeader |
| getResponseBody | getResponseBody |
| toJson | 将对象转换为 JSON String |


* **Http 抓包拦截器 ( 存在存储抓包数据逻辑 ) ->** [HttpCaptureInterceptor.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/HttpCaptureInterceptor.java)

| 方法 | 注释 |
| :- | :- |
| getModuleName | getModuleName |
| getEncrypt | getEncrypt |
| getHttpFilter | getHttpFilter |
| isCapture | isCapture |
| setCapture | setCapture |
| getModulePath | getModulePath |
| getModuleHttpCaptures | getModuleHttpCaptures |
| intercept | intercept |
| innerResponse | 内部抓包方法 |


* **Http 抓包接口信息获取 ->** [IHttpCapture.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/IHttpCapture.java)

| 方法 | 注释 |
| :- | :- |
| getModuleName | 获取模块名 ( 要求唯一性 ) |
| getEncrypt | 获取抓包数据加密中间层 |
| getHttpFilter | 获取 Http 拦截过滤器 |
| isCapture | 是否进行 Http 抓包拦截 |
| setCapture | 设置是否进行 Http 抓包拦截 |
| getModulePath | 获取模块抓包存储路径 |
| getModuleHttpCaptures | 获取模块所有抓包数据 |


* **Http 抓包成功回调接口 ->** [IHttpCaptureCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/IHttpCaptureCallback.java)

| 方法 | 注释 |
| :- | :- |
| callback | 抓包成功回调 |


* **Http 拦截过滤器 ->** [IHttpFilter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/src/main/java/dev/capture/IHttpFilter.java)

| 方法 | 注释 |
| :- | :- |
| filter | 是否过滤该 Http 请求不进行抓包存储 |