
## DevHttpCapture

> 该库主要对使用 OkHttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。
>
> **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器。
>
> 内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )
>
> `DevHttpCaptureCompiler` 提供对 `DevHttpCapture` 抓包库可视化功能


### 最新版本

module | DevHttpCapture | DevHttpCaptureCompiler | DevHttpCaptureCompilerRelease
:---:|:---:|:---:|:---:
version | [![][maven_svg]][maven] | [![][maven_svg]][maven] | [![][maven_svg]][maven]


### Gradle

```groovy
dependencies {
    debugImplementation 'io.github.afkt:DevHttpCaptureCompiler:1.1.2'
    releaseImplementation 'io.github.afkt:DevHttpCaptureCompilerRelease:1.1.2'
}
```


### 使用示例

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
- dev                   | 根目录
   - capture            | Http 抓包实现代码
```


### API


- dev                                         | 根目录
   - [capture](#devcapture)                   | Http 抓包实现代码




## <span id="dev">**`dev`**</span>


* **OkHttp 抓包工具库 ->** [DevHttpCaptureCompiler.java](https://github.com/afkT/DevUtils/blob/master/lib/HttpCapture/DevHttpCaptureCompiler/src/main/java/dev/DevHttpCaptureCompiler.java)

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
| finishAllActivity | 结束所有 Activity |
| putUrlFunction | 添加接口所属功能注释 |
| removeUrlFunction | 移除接口所属功能注释 |





[maven_svg]: https://img.shields.io/badge/Maven-1.1.2-brightgreen.svg
[maven]: https://search.maven.org/search?q=io.github.afkt