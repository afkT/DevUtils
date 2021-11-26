
## DevHttpCapture

> 该库主要对使用 Okhttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。
>
> **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器。
>
> 内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )
>
> `DevHttpCaptureCompiler` 提供对 `DevHttpCapture` 抓包库可视化功能


### 最新版本

module | DevHttpCapture | DevHttpCaptureCompiler | DevHttpCaptureCompilerRelease
:---:|:---:|:---:|:---:
version | [![MavenCentral](https://img.shields.io/badge/Maven-1.0.4-brightgreen.svg)](https://search.maven.org/search?q=io.github.afkt) | [![MavenCentral](https://img.shields.io/badge/Maven-1.0.4-brightgreen.svg)](https://search.maven.org/search?q=io.github.afkt) | [![MavenCentral](https://img.shields.io/badge/Maven-1.0.4-brightgreen.svg)](https://search.maven.org/search?q=io.github.afkt)


### Gradle

```groovy
dependencies {
    debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.0.4'
    releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.0.4'
}
```


### 使用方法

```java
// 显示所有 Module 抓包数据
DevHttpCaptureCompiler.start(context);
// 显示指定 Module 抓包数据
DevHttpCaptureCompiler.start(context, moduleName);

// =======
// = 可选 =
// =======

// 添加接口所属功能注释
DevHttpCaptureCompiler.putUrlFunction(moduleName, UrlFunctionGet);
// 移除接口所属功能注释
DevHttpCaptureCompiler.removeUrlFunction(moduleName);
```

### 目录结构

```
- dev                                                 | 根目录
   - capture                                          | Http 抓包实现代码
```


### API


- dev                                                 | 根目录
   - [capture](#devcapture)                           | Http 抓包实现代码


## <span id="dev">**`dev`**</span>


* **OKHttp 抓包工具库 ->** [DevHttpCaptureCompiler.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/DevHttpCaptureCompiler.java)

| 方法 | 注释 |
| :- | :- |
| finishAllActivity | 结束所有 Activity |
| start | 跳转抓包数据可视化 Activity |
| putUrlFunction | 添加接口所属功能注释 |
| removeUrlFunction | 移除接口所属功能注释 |


## <span id="devcapture">**`dev.capture`**</span>


* **DevHttpCapture 抓包数据详情页 ->** [DevHttpCaptureFileActivity.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/DevHttpCaptureFileActivity.java)

| 方法 | 注释 |
| :- | :- |
| onCreate | onCreate |
| onBackPressed | onBackPressed |


* **DevHttpCapture 抓包数据列表 ->** [DevHttpCaptureListActivity.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/DevHttpCaptureListActivity.java)

| 方法 | 注释 |
| :- | :- |
| onCreate | onCreate |
| onBackPressed | onBackPressed |


* **DevHttpCapture 入口 ->** [DevHttpCaptureMainActivity.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/DevHttpCaptureMainActivity.java)

| 方法 | 注释 |
| :- | :- |
| onCreate | onCreate |
| onBackPressed | onBackPressed |


* **接口所属功能注释获取 ->** [UrlFunctionGet.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/UrlFunctionGet.java)

| 方法 | 注释 |
| :- | :- |
| toUrlFunction | 接口所属功能注释获取 |


* ** ->** [UtilsCompiler.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/capture/UtilsCompiler.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 Utils 实例 |
| addActivity | 添加 Activity |
| removeActivity | 移除 Activity |
| finishAllActivity | 结束所有 Activity |
| createGson | 创建 GsonBuilder |
| toJsonIndent | JSON String 缩进处理 |
| fromJson | 将 JSON String 映射为指定类型对象 |
| putUrlFunction | 添加接口所属功能注释 |
| removeUrlFunction | 移除接口所属功能注释 |
| getUrlFunction | 获取接口所属功能注释 |
| clearCallback | 移除所有回调 |
| removeCallback | 移除回调 ( 关闭页面调用 ) |
| addCallback | 添加回调 |
| notifyCallback | 通知回调 |
| queryData | 查询数据 |
| clearData | 移除所有数据 |
| isQuerying | 是否查询中 |
| getMainData | 获取首页数据源 |
| getFileData | 获取抓包文件数据 |
| getDateData | 获取对应时间 ( yyyyMMdd ) 指定筛选条件抓包列表数据 |
| getUrlFunctionByFile | 获取接口所属功能 |
| resetRefreshClick | 重置刷新点击处理 |