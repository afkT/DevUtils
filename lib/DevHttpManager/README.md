
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


## 框架功能介绍

* 支持 Retrofit 多 BaseUrl 管理及操作方法封装

* 支持 Retrofit BaseUrl Reset 事件全局监听、各个模块单独监听回调

* 支持全局 OkHttp Builder 创建方法, 可进行全局管理

* 针对多 Retrofit 管理封装 Operation 对象并支持组件化使用

* 支持传参 Map 对多个 Retrofit 同时进行 BaseUrl Reset

* 支持对 App 所有链接上传、下载进度监听

* 基于 OkHttp 原生 Api 实现, 不存在兼容问题

* 侵入性低, 使用本框架不需要更改历史上传、下载实现代码


### API 文档

* **DevHttpManager 管理库方法 ->** [DevHttpManager.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/DevHttpManager.kt)

| 方法 | 注释 |
| :- | :- |
| getDevHttpManagerVersionCode | 获取 DevHttpManager 版本号 |
| getDevHttpManagerVersion | 获取 DevHttpManager 版本 |
| getDevAppVersionCode | 获取 DevApp 版本号 |
| getDevAppVersion | 获取 DevApp 版本 |
| getOkHttpBuilder | 获取全局 OkHttp Builder 接口对象 |
| setOkHttpBuilder | 设置全局 OkHttp Builder 接口对象 |
| removeOkHttpBuilder | 移除全局 OkHttp Builder 接口对象 |
| getRetrofitResetListener | 获取全局 Retrofit 重新构建监听事件 |
| setRetrofitResetListener | 设置全局 Retrofit 重新构建监听事件 |
| removeRetrofitResetListener | 移除全局 Retrofit 重新构建监听事件 |
| getOperation | 获取 Retrofit Operation 操作对象 |
| containsOperation | 通过 Key 判断是否存在 Retrofit Operation 操作对象 |
| putRetrofitBuilder | 通过 Key 绑定存储 RetrofitBuilder 并返回 Operation 操作对象 |
| removeRetrofitBuilder | 通过 Key 解绑移除 RetrofitBuilder 并返回 Operation 操作对象 |
| reset | 重置处理 ( 重新构建 Retrofit ) |
| resetAll | 重置处理 ( 重新构建全部 Retrofit ) |

#### Retrofit 多 BaseUrl 管理功能

* **全局 OkHttp Builder 接口 ->** [OkHttpBuilder.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/OkHttpBuilder.kt)

| 方法 | 注释 |
| :- | :- |
| createOkHttpBuilder | 创建 OkHttp Builder |


* **全局 Retrofit 重新构建监听事件 ->** [OnRetrofitResetListener.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/OnRetrofitResetListener.kt)

| 方法 | 注释 |
| :- | :- |
| onResetBefore | 重新构建前调用 |
| onReset | 重新构建后调用 |


* **Retrofit Builder 接口 ->** [RetrofitBuilder.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitBuilder.kt)

| 方法 | 注释 |
| :- | :- |
| createRetrofitBuilder | 创建 Retrofit Builder |
| onResetBefore | 重新构建前调用 |
| onReset | 重新构建后调用 |


* **RetrofitOperation ->** [RetrofitOperation.kt](https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/src/main/java/dev/http/manager/RetrofitOperation.kt)

| 方法 | 注释 |
| :- | :- |
| getRetrofit | 获取 Retrofit 对象 |
| create | 通过 Retrofit 代理创建 Service |
| reset | 重置处理 ( 重新构建 Retrofit ) |
| resetAndCreate | 重置处理 ( 重新构建 Retrofit ) 并代理创建 Service |