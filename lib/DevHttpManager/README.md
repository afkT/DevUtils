
## Gradle

```gradle
implementation 'io.github.afkt:DevHttpManager:1.0.0'
```

## 目录结构

```
- dev                                                 | 根目录
   - http                                             | 基于 OkHttp 管理实现代码
      - manager                                       | Retrofit 多 BaseUrl 管理
      - progress                                      | OkHttp 上传、下载进度监听
```


## 库功能介绍

> 该库主要对使用 OkHttp 网络请求库的项目，提供 Http 抓包功能，并支持抓包数据加密存储。
>
> **并且是以 Module ( ModuleName Key ) 为基础，支持组件化不同 Module 各自的抓包功能**，支持实时开关抓包功能、可控 Http 拦截过滤器。
>
> 内置两个 Http 抓包拦截器，CallbackInterceptor ( 无存储逻辑，进行回调通知 )、HttpCaptureInterceptor ( 存在存储抓包数据逻辑 )

### 使用示例

```java
// 添加 Http 抓包拦截处理
DevHttpCapture.addInterceptor(
    OkHttpClient.Builder, moduleName
);

// 添加 Http 抓包拦截处理
DevHttpCapture.addInterceptor(
    OkHttpClient.Builder, moduleName, isCapture
);

// 添加 Http 抓包拦截处理
DevHttpCapture.addInterceptor(
    OkHttpClient.Builder, moduleName,
    encrypt, httpFilter, isCapture
);

// 移除对应 Module Http 抓包拦截
DevHttpCapture.removeInterceptor(moduleName);

// 更新对应 Module Http 抓包拦截处理
DevHttpCapture.updateInterceptor(moduleName, isCapture);
```

## 事项

- 部分 API 更新不及时或有遗漏等，`具体以对应的工具类为准`

- [检测代码规范、注释内容排版，API 文档生成](https://github.com/afkT/JavaDoc)

- [Change Log](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/CHANGELOG.md)

## API


- dev                                                 | 根目录
   - [http](#devhttp)                                 | 基于 OkHttp 管理实现代码
      - [manager](#devhttpmanager)                    | Retrofit 多 BaseUrl 管理
      - [progress](#devhttpprogress)                  | OkHttp 上传、下载进度监听


## <span id="dev">**`dev`**</span>


## <span id="devhttp">**`dev.http`**</span>


## <span id="devhttpmanager">**`dev.http.manager`**</span>


## <span id="devhttpprogress">**`dev.http.progress`**</span>