

## 目录结构

```
- dev                                                 | 根目录
   - assist                                           | 常用辅助类封装
   - engine                                           | 兼容 Engine
      - image                                         | Image Engine
      - json                                          | JSON Engine
      - log                                           | Log Engine
   - other                                            | 第三方库封装工具类
      - okgo                                          | OkGo 网络请求
         - demo                                       | OkGo 使用 Demo
      - picture                                       | PictureSelector 图片选择库
      - retrofit                                      | Retrofit 网络请求
         - response                                   | 请求响应解析
         - subscriber                                 | 请求响应处理
      - work                                          | WorkManager Utils
         - demo                                       | WorkManager 使用 Demo
   - receiver                                         | BroadcastReceiver 监听相关
   - service                                          | Service 相关
```


## Use

> 直接 copy 所需要的类到项目中使用


## API


- dev                                                 | 根目录
   - [assist](#devassist)                             | 常用辅助类封装
   - [engine](#devengine)                             | 兼容 Engine
      - [image](#devengineimage)                      | Image Engine
      - [json](#devenginejson)                        | JSON Engine
      - [log](#devenginelog)                          | Log Engine
   - [other](#devother)                               | 第三方库封装工具类
      - [okgo](#devotherokgo)                         | OkGo 网络请求
         - [demo](#devotherokgodemo)                  | OkGo 使用 Demo
      - [picture](#devotherpicture)                   | PictureSelector 图片选择库
      - [retrofit](#devotherretrofit)                 | Retrofit 网络请求
         - [response](#devotherretrofitresponse)      | 请求响应解析
         - [subscriber](#devotherretrofitsubscriber)  | 请求响应处理
      - [work](#devotherwork)                         | WorkManager Utils
         - [demo](#devotherworkdemo)                  | WorkManager 使用 Demo
   - [receiver](#devreceiver)                         | BroadcastReceiver 监听相关
   - [service](#devservice)                           | Service 相关




## <span id="dev">**`dev`**</span>


## <span id="devassist">**`dev.assist`**</span>


* **WebView 辅助类 ->** [WebViewAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/assist/WebViewAssist.java)

| 方法 | 注释 |
| :- | :- |
| setWebView | 设置 WebView |
| getWebView | 获取 WebView |
| isWebViewNotEmpty | WebView 是否不为 null |
| setBuilder | 设置 WebView 常用配置构建类 |
| getBuilder | 获取 WebView 常用配置构建类 |
| apply | 应用 ( 设置 ) 配置 |
| setGlobalBuilder | 设置全局 WebView 常用配置构建类 |
| getGlobalBuilder | 获取全局 WebView 常用配置构建类 |
| loadUrl | 加载网页 |
| loadData | 加载 Html 代码 |
| loadDataWithBaseURL | 加载 Html 代码 |
| postUrl | 使用 POST 方法将带有 postData 的 url 加载到 WebView 中 |
| getSettings | 获取 WebView 配置 |
| getUserAgentString | 获取浏览器标识 UA |
| setUserAgentString | 设置浏览器标识 |
| addJavascriptInterface | 添加 JS 交互注入对象 |
| removeJavascriptInterface | 移除 JS 交互注入对象 |
| evaluateJavascript | 执行 JS 方法 |
| setWebViewClient | 设置处理各种通知和请求事件对象 |
| getWebViewClient | 获取处理各种通知和请求事件对象 |
| setWebChromeClient | 设置辅助 WebView 处理 Javascript 对话框、标题等对象 |
| getWebChromeClient | 获取辅助 WebView 处理 Javascript 对话框、标题等对象 |
| destroy | 销毁处理 |
| canGoBack | WebView 是否可以后退 |
| goBack | WebView 后退 |
| canGoForward | WebView 是否可以前进 |
| goForward | WebView 前进 |
| canGoBackOrForward | WebView 是否可以跳转到当前起始点相距的历史记录 |
| goBackOrForward | WebView 跳转到当前起始点相距的历史记录 |
| reload | 刷新页面 ( 当前页面的所有资源都会重新加载 ) |
| stopLoading | 停止加载 |
| clearCache | 清除资源缓存 |
| clearHistory | 清除当前 WebView 访问的历史记录 |
| clearFormData | 清除自动完成填充的表单数据 |
| getScale | 获取缩放比例 |
| getScrollY | 获取当前可见区域的顶端距整个页面顶端的距离 ( 当前内容滚动的距离 ) |
| getScrollX | 获取当前内容横向滚动距离 |
| getContentHeight | 获取 HTML 的高度 ( 原始高度, 不包括缩放后的高度 ) |
| getScaleHeight | 获取缩放高度 |
| getHeight | 获取 WebView 控件高度 |
| pageDown | 将视图内容向下滚动一半页面大小 |
| pageUp | 将视图内容向上滚动一半页面大小 |
| handlerKeyDown | 处理按键 ( 是否回退 ) |
| setLayerTypeSoftware | 关闭 WebView 硬件加速功能 |
| setLayerType | 设置 WebView 硬件加速类型 |
| getUrl | 获取当前 Url |
| getOriginalUrl | 获取最初请求 Url |
| getHitTestResult | 获取长按事件类型 |
| setCookie | 将 Cookie 设置到 WebView |
| getCookie | 获取指定 Url 的 Cookie |
| removeCookie | 移除 Cookie |
| removeSessionCookie | 移除 Session Cookie |
| removeAllCookie | 移除所有的 Cookie |
| setOnApplyListener | 设置应用配置监听事件 |
| getApplyListener | 获取应用配置监听事件 |
| clone | 克隆方法 ( 用于全局配置克隆操作 ) |
| reset | 重置方法 |
| isJavaScriptEnabled | 是否支持 JavaScript |
| setJavaScriptEnabled | 设置是否支持 JavaScript |
| getRenderPriority | 获取渲染优先级 |
| setRenderPriority | 设置渲染优先级 |
| isUseWideViewPort | 是否使用宽视图 |
| setUseWideViewPort | 设置是否使用宽视图 |
| isLoadWithOverviewMode | 是否按宽度缩小内容以适合屏幕 |
| setLoadWithOverviewMode | 设置是否按宽度缩小内容以适合屏幕 |
| getLayoutAlgorithm | 获取基础布局算法 |
| setLayoutAlgorithm | 设置基础布局算法 |
| isSupportZoom | 是否支持缩放 |
| setSupportZoom | 设置是否支持缩放 |
| isBuiltInZoomControls | 是否显示内置缩放工具 |
| setBuiltInZoomControls | 设置是否显示内置缩放工具 |
| isDisplayZoomControls | 是否显示缩放工具 |
| setDisplayZoomControls | 设置是否显示缩放工具 |
| getTextZoom | 获取文本缩放倍数 |
| setTextZoom | 设置文本缩放倍数 |
| getStandardFontFamily | 获取 WebView 字体 |
| setStandardFontFamily | 设置 WebView 字体 |
| getDefaultFontSize | 获取 WebView 字体大小 |
| setDefaultFontSize | 设置 WebView 字体大小 |
| getMinimumFontSize | 获取 WebView 支持最小字体大小 |
| setMinimumFontSize | 设置 WebView 支持最小字体大小 |
| getMixedContentMode | 获取混合内容模式 |
| setMixedContentMode | 设置混合内容模式 |
| isLoadsImagesAutomatically | 是否支持自动加载图片 |
| setLoadsImagesAutomatically | 设置是否支持自动加载图片 |
| isJavaScriptCanOpenWindowsAutomatically | 是否支持通过 JS 打开新窗口 |
| setJavaScriptCanOpenWindowsAutomatically | 设置是否支持通过 JS 打开新窗口 |
| getDefaultTextEncodingName | 获取编码格式 |
| setDefaultTextEncodingName | 设置编码格式 |
| isGeolocationEnabled | 是否允许网页执行定位操作 |
| setGeolocationEnabled | 设置是否允许网页执行定位操作 |
| isAllowFileAccess | 是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 ) |
| setAllowFileAccess | 设置是否可以访问文件 ( false 不影响 assets 和 resources 资源的加载 ) |
| isAllowFileAccessFromFileURLs | 是否允许通过 file url 加载的 JS 代码读取其他的本地文件 |
| setAllowFileAccessFromFileURLs | 设置是否允许通过 file url 加载的 JS 代码读取其他的本地文件 |
| isAllowUniversalAccessFromFileURLs | 是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 ) |
| setAllowUniversalAccessFromFileURLs | 设置是否允许通过 file url 加载的 JS 可以访问其他的源 ( 包括 http、https 等源 ) |
| getCacheMode | 获取 WebView 缓存模式 |
| setCacheMode | 设置 WebView 缓存模式 |
| isDomStorageEnabled | 是否支持 DOM Storage |
| setDomStorageEnabled | 设置是否支持 DOM Storage |
| isAppCacheEnabled | 是否开启 Application Caches 功能 |
| setAppCacheEnabled | 设置是否开启 Application Caches 功能 |
| getAppCachePath | 获取 Application Caches 地址 |
| setAppCachePath | 设置 Application Caches 地址 |
| getAppCacheMaxSize | 获取 Application Caches 大小 |
| setAppCacheMaxSize | 设置 Application Caches 大小 |
| isDatabaseEnabled | 是否支持数据库缓存 |
| setDatabaseEnabled | 设置是否支持数据库缓存 |
| getDatabasePath | 获取数据库缓存路径 |
| setDatabasePath | 设置数据库缓存路径 |
| onApply | 应用配置通知方法 |


## <span id="devengine">**`dev.engine`**</span>


## <span id="devengineimage">**`dev.engine.image`**</span>


* **Glide Image Config ->** [GlideConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/engine/image/GlideConfig.java)

| 方法 | 注释 |
| :- | :- |
| create | create |
| clone | 克隆配置信息 |
| isCacheDisk | isCacheDisk |
| setCacheDisk | setCacheDisk |
| isCacheMemory | isCacheMemory |
| setCacheMemory | setCacheMemory |
| getScaleType | getScaleType |
| setScaleType | setScaleType |
| getTransform | getTransform |
| setTransform | setTransform |
| getRoundedCornersRadius | getRoundedCornersRadius |
| setRoundedCornersRadius | setRoundedCornersRadius |
| getErrorPlaceholder | getErrorPlaceholder |
| setErrorPlaceholder | setErrorPlaceholder |
| getLoadingPlaceholder | getLoadingPlaceholder |
| setLoadingPlaceholder | setLoadingPlaceholder |
| getErrorDrawable | getErrorDrawable |
| setErrorDrawable | setErrorDrawable |
| getLoadingDrawable | getLoadingDrawable |
| setLoadingDrawable | setLoadingDrawable |
| getWidth | getWidth |
| getHeight | getHeight |
| setSize | setSize |
| getThumbnail | getThumbnail |
| setThumbnail | setThumbnail |


* **Glide Image Engine 实现 ->** [GlideEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/engine/image/GlideEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| pause | pause |
| resume | resume |
| preload | preload |
| clear | clear |
| clearDiskCache | clearDiskCache |
| clearMemoryCache | clearMemoryCache |
| clearAllCache | clearAllCache |
| lowMemory | lowMemory |
| display | display |
| loadImage | loadImage |
| loadBitmap | loadBitmap |
| loadDrawable | loadDrawable |


## <span id="devenginejson">**`dev.engine.json`**</span>


* **Gson JSON Engine 实现 ->** [GsonEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/engine/json/GsonEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| toJson | toJson |
| fromJson | fromJson |
| isJSON | isJSON |
| isJSONObject | isJSONObject |
| isJSONArray | isJSONArray |
| toJsonIndent | toJsonIndent |


## <span id="devenginelog">**`dev.engine.log`**</span>


* **DevLogger Log Engine 实现 ->** [LoggerEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/engine/log/LoggerEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| d | d |
| e | e |
| w | w |
| i | i |
| v | v |
| wtf | wtf |
| json | json |
| xml | xml |
| dTag | dTag |
| eTag | eTag |
| wTag | wTag |
| iTag | iTag |
| vTag | vTag |
| wtfTag | wtfTag |
| jsonTag | jsonTag |
| xmlTag | xmlTag |


## <span id="devother">**`dev.other`**</span>


* **EventBus 工具类 ->** [EventBusUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/EventBusUtils.java)

| 方法 | 注释 |
| :- | :- |
| register | 注册 EventBus |
| unregister | 解绑 EventBus |
| post | 发送事件消息 |
| cancelEventDelivery | 取消事件传送 |
| postSticky | 发送粘性事件消息 |
| removeStickyEvent | 移除指定的粘性订阅事件 |
| removeAllStickyEvents | 移除所有的粘性订阅事件 |


* **Fastjson 工具类 ->** [FastjsonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/FastjsonUtils.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |
| getArrayType | 获取 Array Type |
| getListType | 获取 List Type |
| getSetType | 获取 Set Type |
| getMapType | 获取 Map Type |
| getType | 获取 Type |


* **Glide 图形处理工具类 ->** [GlideTransformUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GlideTransformUtils.java)

| 方法 | 注释 |
| :- | :- |
| transform | transform |
| updateDiskCacheKey | updateDiskCacheKey |
| blurBitmap | 模糊图片处理 |


* **Glide 工具类 ->** [GlideUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GlideUtils.java)

| 方法 | 注释 |
| :- | :- |
| with | with |
| init | 初始化方法 ( 必须调用 ) |
| cloneImageOptions | 克隆图片加载配置 |
| defaultOptions | 获取默认加载配置 |
| emptyOptions | 获取空白加载配置 |
| skipCacheOptions | 获取跳过缓存 ( 每次都从服务端获取最新 ) 加载配置 |
| getLoadResOptions | 获取自定义图片加载配置 |
| transformationOptions | 获取图片处理效果加载配置 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| onLowMemory | 低内存通知 |
| getDiskCache | 获取 SDCard 缓存空间 |
| preload | 预加载图片 |
| displayImage | 图片显示 |
| displayImageToGif | 图片显示 |
| displayImageToDrawable | 图片显示 |
| displayImageToFile | 图片显示 |
| loadImageBitmap | 图片加载 |
| loadImageDrawable | 图片加载 |
| loadImageFile | 图片加载 |
| loadImageGif | 图片加载 |
| cancelDisplayTask | 取消图片显示任务 |
| destroy | 销毁操作 |
| pause | 暂停图片加载 |
| resume | 恢复图片加载 |
| stop | 停止图片加载 |
| start | 开始图片加载 |


* **Gson 工具类 ->** [GsonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/GsonUtils.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |
| createGson | 创建 GsonBuilder |
| createGsonExcludeFields | 创建过滤指定修饰符字段 GsonBuilder |
| getArrayType | 获取 Array Type |
| getListType | 获取 List Type |
| getSetType | 获取 Set Type |
| getMapType | 获取 Map Type |
| getType | 获取 Type |


* **ImageLoader 工具类 ->** [ImageLoaderUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/ImageLoaderUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化 ImageLoader 加载配置 |
| defaultOptions | 获取 DisplayImageOptions 图片加载配置 |
| getDefaultImageOptions | 获取图片默认加载配置 |
| getNoCacheImageOptions | 获取不使用缓存的图片加载配置 |
| getCacheImageOptions | 获取 ImageLoader 图片缓存加载配置 |
| cloneImageOptions | 克隆图片加载配置 |
| getFadeInBitmapDisplayer | 获取图片渐变动画加载配置 |
| getRoundedBitmapDisplayer | 获取圆角图片加载配置 |
| getBitmapDisplayerOptions | 获取图片效果加载配置 |
| displayImage | 图片显示 |
| loadImage | 图片加载 |
| loadImageSync | 图片同步加载 |
| clearDiskCache | 清除磁盘缓存 |
| clearMemoryCache | 清除内存缓存 |
| getDiskCache | 获取 SDCard 缓存空间 |
| getMemoryCache | 获取 Memory 缓存空间 |
| handleSlowNetwork | 设置是否处理弱网情况 |
| denyNetworkDownloads | 设置是否禁止网络下载 |
| cancelDisplayTask | 取消图片显示任务 |
| getLoadingUriForView | 通过 ImageView 获取图片加载地址 |
| setDefaultLoadingListener | 设置全局加载监听事件 |
| destroy | 销毁操作 |
| pause | 暂停图片加载 |
| resume | 恢复图片加载 |
| stop | 停止图片加载 |


* **Luban 工具类 ->** [LubanUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/LubanUtils.java)

| 方法 | 注释 |
| :- | :- |
| setConfig | 设置全局默认配置 |
| compress | 压缩方法 |
| onStart | 压缩开始前调用 |
| onSuccess | 压缩成功后调用 |
| onError | 当压缩过程出现问题时调用 |
| onComplete | 压缩完成 |


* **MMKV 工具类 ->** [MMKVUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/MMKVUtils.java)

| 方法 | 注释 |
| :- | :- |
| init | 初始化方法 ( 必须调用 ) |
| containsMMKV | 是否存在指定 Key 的 MMKV Holder |
| get | 通过 Key 获取 MMKV Holder |
| putHolder | 保存自定义 MMKV Holder |
| defaultHolder | 获取 Default MMKV Holder |
| getMMKV | 获取 MMKV |
| mmapID | 获取 MMKV mmap id |
| isMMKVEmpty | 判断 MMKV 是否为 null |
| isMMKVNotEmpty | 判断 MMKV 是否不为 null |
| containsKey | 是否存在指定 Key value |
| removeValueForKey | 通过 key 移除 value |
| removeValuesForKeys | 通过 key 数组移除 value |
| sync | 同步操作 |
| async | 异步操作 |
| encode | encode |
| decodeBool | decodeBool |
| decodeInt | decodeInt |
| decodeLong | decodeLong |
| decodeFloat | decodeFloat |
| decodeDouble | decodeDouble |
| decodeString | decodeString |
| decodeStringSet | decodeStringSet |
| decodeBytes | decodeBytes |
| decodeParcelable | decodeParcelable |


* **ZXing 二维码工具类 ->** [ZXingQRCodeUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/ZXingQRCodeUtils.java)

| 方法 | 注释 |
| :- | :- |
| createQRCodeImage | 生成二维码图片 |
| decodeQRCode | 解析二维码图片 |
| getResultData | 获取扫描结果数据 |
| syncEncodeQRCode | 同步创建黑色前景色、白色背景色的二维码图片 |
| addLogoToQRCode | 添加 Logo 到二维码图片上 |
| onResult | 生成二维码结果回调 |


## <span id="devotherokgo">**`dev.other.okgo`**</span>


* **OkHttp 打印日志拦截器 ->** [HttpLoggingInterceptor.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/okgo/HttpLoggingInterceptor.java)

| 方法 | 注释 |
| :- | :- |
| intercept | intercept |


* **请求回调统一处理类 ->** [OkGoCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/okgo/OkGoCallback.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 请求网络开始前 ( UI 线程 ) |
| onFinish | 请求网络结束后, UI 线程 |
| uploadProgress | 上传过程中的进度回调, get 请求不回调 ( UI 线程 ) |
| downloadProgress | 下载过程中的进度回调 ( UI 线程 ) |
| onCacheSuccess | 缓存成功的回调 ( UI 线程 ) |
| onError | 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 ) |
| onSuccess | 对返回数据进行操作的回调 ( UI 线程 ) |
| convertResponse | 拿到响应后, 将数据转换成需要的格式 ( 子线程中执行, 可以是耗时操作 ) |
| onSuccessResponse | 请求响应并处理数据无误 |
| onErrorResponse | 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 ) |


* **请求响应统一解析类 ->** [OkGoResponse.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/okgo/OkGoResponse.java)

| 方法 | 注释 |
| :- | :- |
| getDataString | 获取 Data 字符串 |
| build | build Response 对象 |
| setData | setData |
| setMessage | setMessage |
| setCode | setCode |
| setToast | setToast |
| setResult | setResult |
| setOriginal | setOriginal |
| setException | setException |


* **OkGo 配置相关工具类 ->** [OkGoUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/okgo/OkGoUtils.java)

| 方法 | 注释 |
| :- | :- |
| initOkGo | 初始化 OkGo 配置 |
| execute | 执行请求处理 |


## <span id="devotherokgodemo">**`dev.other.okgo.demo`**</span>


* **OkGo 使用 Demo ->** [OkGoDemo.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/okgo/demo/OkGoDemo.java)

| 方法 | 注释 |
| :- | :- |
| getUserInfo | 获取用户信息 |
| getUserList | 获取用户列表 |
| uploadImage | 上传文件 |
| uploadImages | 上传多个文件 |


## <span id="devotherpicture">**`dev.other.picture`**</span>


* **Glide 加载引擎 ->** [GlideEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/picture/GlideEngine.java)

| 方法 | 注释 |
| :- | :- |
| loadImage | 加载图片 |
| loadFolderImage | 加载相册目录 |
| loadAsGifImage | 加载 gif |
| loadGridImage | 加载图片列表图片 |
| createGlideEngine | createGlideEngine |


* **Android 平台下的图片选择器 ->** [PictureSelectorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/picture/PictureSelectorUtils.java)

| 方法 | 注释 |
| :- | :- |
| getPicConfig | 获取全局相册配置 |
| setPicConfig | 设置全局相册配置 |
| getCameraSavePath | 获取拍照保存地址 |
| getCompressSavePath | 获取压缩图片保存地址 |
| setSavePath | 设置保存地址 |
| getMinimumCompressSize | 获取图片大于多少才进行压缩 (kb) |
| setMinimumCompressSize | 设置图片大于多少才进行压缩 (kb) |
| deleteCacheDirFile | 清空缓存 |
| getLocalMedias | 获取选中的资源集合 |
| getSingleMedia | 获取单独选中的资源 |
| getLocalMediaPath | 获取本地资源路径 |
| getLocalMediaPaths | 获取本地资源地址集合 |
| getPictureSelectionModel | 获取图片选择配置模型 |
| openCamera | 打开相册拍照 |
| openGallery | 打开相册选择 |
| getMimeType | 获取相册选择类型 |
| setMimeType | 设置相册选择类型 |
| getSelectionMode | 获取相册选择模式 |
| setSelectionMode | 设置相册选择模式 |
| isCamera | 是否显示拍照 |
| setCamera | 设置是否显示拍照 |
| isCrop | 是否裁减 |
| setCrop | 设置是否裁减 |
| isCircleCrop | 是否圆形裁减 |
| setCircleCrop | 设置是否圆形裁减 |
| isCompress | 是否压缩 |
| setCompress | 设置是否压缩 |
| getWithAspectRatio | 获取裁减比例 |
| setWithAspectRatio | 设置裁减比例 |
| isGif | 是否显示 Gif |
| setGif | 设置是否显示 Gif |
| getImageSpanCount | 获取每行显示个数 |
| setImageSpanCount | 设置每行显示个数 |
| getMinSelectNum | 获取最小选择数量 |
| setMinSelectNum | 设置最小选择数量 |
| getMaxSelectNum | 获取最大选择数量 |
| setMaxSelectNum | 设置最大选择数量 |
| getLocalMedia | 获取已选择的本地资源 |
| setLocalMedia | 设置已选择的本地资源 |
| setCameraSavePath | 设置拍照保存地址 |
| setCompressSavePath | 设置压缩图片保存地址 |
| clone | 克隆新的相册配置 |
| set | 设置新的相册配置 |


## <span id="devotherretrofit">**`dev.other.retrofit`**</span>


* **Retrofit 管理类 ->** [RetrofitManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/retrofit/RetrofitManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | getInstance |
| get | 通过 tag 获取 Retrofit |
| put | 通过 tag 保存 Retrofit |
| remove | 通过 tag 移除 Retrofit |
| contains | 通过 tag 判断是否存在 Retrofit |
| getRetrofitMap | 获取 Retrofit Map |
| create | 创建 API Service Class |


* **RxJava 相关管理类 ( 针对 Retrofit ) ->** [RxJavaManager.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/retrofit/RxJavaManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | getInstance |
| add | 通过 tag 将请求添加到统一管理对象中 |
| remove | 通过 tag 移除请求 |
| contains | 通过 tag 判断是否存在 CompositeDisposable |
| getManagerMap | 获取 CompositeDisposable Map |
| io_main | Flowable UI 线程 |


## <span id="devotherretrofitresponse">**`dev.other.retrofit.response`**</span>


* **请求响应统一解析类 ->** [BaseResponse.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/retrofit/response/BaseResponse.java)

| 方法 | 注释 |
| :- | :- |
| getOriginal | getOriginal |


## <span id="devotherretrofitsubscriber">**`dev.other.retrofit.subscriber`**</span>


* **服务器请求响应处理, 映射各种 JSON 实体类 ->** [BaseBeanSubscriber.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/retrofit/subscriber/BaseBeanSubscriber.java)

| 方法 | 注释 |
| :- | :- |
| onNext | onNext |
| onError | onError |
| onStart | onStart |
| onComplete | onComplete |
| onSuccessResponse | 请求响应并处理数据无误 |
| onErrorResponse | 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 ) |
| getErrorMessage | 获取异常信息 |


* **服务器请求响应处理, 映射统一标准 JSON 格式实体类 ->** [BaseResponseSubscriber.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/retrofit/subscriber/BaseResponseSubscriber.java)

| 方法 | 注释 |
| :- | :- |
| onNext | onNext |
| onError | onError |
| onStart | onStart |
| onComplete | onComplete |
| onSuccessResponse | 请求响应并处理数据无误 |
| onErrorResponse | 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 ) |
| getErrorMessage | 获取异常信息 |
| isSuccess | 通过 code 判断请求是否正确 |


## <span id="devotherwork">**`dev.other.work`**</span>


* **WorkManager 工具类 ->** [WorkManagerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/work/WorkManagerUtils.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | 获取 WorkManagerUtils 实例 |
| getWorkManager | 获取 WorkManager |
| setWorkManager | 设置 WorkManager |
| cancelAllWork | 取消所有未完成的 Worker |
| cancelWorkById | 通过 UUID 取消未完成 Worker |
| cancelAllWorkByTag | 通过 TAG 取消未完成 Worker |
| cancelUniqueWork | 通过 Worker 唯一名称取消未完成 Worker |
| pruneWork | 清除已执行 Worker |
| getLastCancelAllTimeMillisLiveData | 获取上次调用 cancelAllWork() 时间戳 |
| getLastCancelAllTimeMillis | 获取上次调用 cancelAllWork() 时间戳 |
| getWorkInfoByIdLiveData | 通过 UUID 获取 WorkInfo |
| getWorkInfoById | 通过 UUID 获取 WorkInfo |
| getWorkInfosByTagLiveData | 通过 TAG 获取 WorkInfo |
| getWorkInfosByTag | 通过 TAG 获取 WorkInfo |
| getWorkInfosForUniqueWorkLiveData | 通过 Worker 唯一名称获取 WorkInfo |
| getWorkInfosForUniqueWork | 通过 Worker 唯一名称获取 WorkInfo |
| getWorkInfosLiveData | 自定义搜索条件获取 WorkInfo |
| getWorkInfos | 自定义搜索条件获取 WorkInfo |
| enqueue | enqueue |
| enqueueUniqueWork | enqueueUniqueWork |
| enqueueUniquePeriodicWork | 加入唯一定期 Worker 执行队列 |
| beginWith | beginWith |
| beginUniqueWork | beginUniqueWork |


## <span id="devotherworkdemo">**`dev.other.work.demo`**</span>


* ** ->** [ChainWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/work/demo/ChainWorker.java)

| 方法 | 注释 |
| :- | :- |
| start | 模拟工作链 |
| builder | builder |
| doWork | doWork |


* **模拟后台间隔性进行定位 ->** [LocationWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/work/demo/LocationWorker.java)

| 方法 | 注释 |
| :- | :- |
| doWork | doWork |
| builder | 快捷创建循环 Worker Request |


* **打印日志 Worker ( 用于演示 ) ->** [LogWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/other/work/demo/LogWorker.java)

| 方法 | 注释 |
| :- | :- |
| onStopped | 只会 worker 在运行时执行 onStopped |
| doWork | doWork |
| builder | 快捷创建 {@link OneTimeWorkRequest.Builder} |
| observe | 监听 WorkRequest 状态 |


## <span id="devreceiver">**`dev.receiver`**</span>


* **应用状态监听广播 ( 安装、更新、卸载 ) ->** [AppStateReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/AppStateReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册应用状态监听广播 |
| unregisterReceiver | 取消注册应用状态监听广播 |
| setAppStateListener | 设置应用状态监听事件 |
| onAdded | 应用安装 |
| onReplaced | 应用更新 |
| onRemoved | 应用卸载 |


* **电量监听广播 ->** [BatteryReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/BatteryReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册电量监听广播 |
| unregisterReceiver | 取消注册电量监听广播 |
| setBatteryListener | 设置电量监听事件 |
| onBatteryChanged | 电量改变通知 |
| onBatteryLow | 电量低通知 |
| onBatteryOkay | 电量从低变回高通知 |
| onPowerConnected | 充电状态改变通知 |
| onPowerUsageSummary | 电力使用情况总结 |


* **网络监听广播 ->** [NetWorkReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/NetWorkReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| isConnectNetWork | 是否连接网络 |
| getConnectType | 获取连接的网络类型 |
| registerReceiver | 注册网络广播监听 |
| unregisterReceiver | 取消注册网络广播监听 |
| setNetListener | 设置监听通知事件 |
| onNetworkState | 网络连接状态改变时通知 |


* **手机监听广播 ->** [PhoneReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/PhoneReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册电话监听广播 |
| unregisterReceiver | 取消注册电话监听广播 |
| setPhoneListener | 设置电话状态监听事件 |
| onPhoneStateChanged | 电话状态改变通知 |


* **屏幕监听广播 ( 锁屏 / 解锁 / 亮屏 ) ->** [ScreenReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/ScreenReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册屏幕监听广播 |
| unregisterReceiver | 取消注册屏幕监听广播 |
| setScreenListener | 设置屏幕监听事件 |
| screenOn | 用户打开屏幕 ( 屏幕变亮 ) |
| screenOff | 锁屏触发 |
| userPresent | 用户解锁触发 |


* **短信监听广播 ->** [SmsReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/SmsReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| getMessageData | 获取消息数据 |
| registerReceiver | 注册短信监听广播 |
| unregisterReceiver | 取消注册短信监听广播 |
| setSmsListener | 设置短信监听事件 |
| onMessage | 最终触发通知 ( 超过长度的短信消息, 最终合并成一条内容体 ) |


* **时间监听广播 ->** [TimeReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/TimeReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册时间监听广播 |
| unregisterReceiver | 取消注册时间监听广播 |
| setTimeListener | 设置时间监听事件 |
| onTimeZoneChanged | 时区改变 |
| onTimeChanged | 设置时间 |
| onTimeTick | 每分钟调用 |


* **Wifi 监听广播 ->** [WifiReceiver.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/receiver/WifiReceiver.java)

| 方法 | 注释 |
| :- | :- |
| onReceive | onReceive |
| registerReceiver | 注册 Wifi 监听广播 |
| unregisterReceiver | 取消注册 Wifi 监听广播 |
| setWifiListener | 设置 Wifi 监听事件 |
| onIntoTrigger | 触发回调通知 ( 每次进入都通知 ) |
| onTrigger | 触发回调通知 |
| onWifiSwitch | Wifi 开关状态 |


## <span id="devservice">**`dev.service`**</span>


* **无障碍功能监听服务 ->** [AccessibilityListenerService.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/service/AccessibilityListenerService.java)

| 方法 | 注释 |
| :- | :- |
| onAccessibilityEvent | 通过这个函数可以接收系统发送来的 AccessibilityEvent |
| onInterrupt | 系统想要中断 AccessibilityService 返给的响应时会调用 |
| onServiceConnected | 系统成功绑定该服务时被触发, 也就是当你在设置中开启相应的服务, 系统成功的绑定了该服务时会触发, 通常我们可以在这里做一些初始化操作 |
| onCreate | onCreate |
| onDestroy | onDestroy |
| getSelf | 获取当前服务所持对象 |
| startService | 启动服务 |
| stopService | 停止服务 |
| checkAccessibility | 检查是否开启无障碍功能 |
| isAccessibilitySettingsOn | 判断是否开启无障碍功能 |
| setAccessibilityListener | 设置监听事件 |
| onServiceCreated | 服务创建通知 |
| onServiceDestroy | 服务销毁通知 |


* **通知栏监听服务 ->** [NotificationService.java](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/dev/service/NotificationService.java)

| 方法 | 注释 |
| :- | :- |
| onNotificationPosted | 当系统收到新的通知后触发回调 |
| onNotificationRemoved | 当系统通知被删掉后触发回调 |
| onCreate | onCreate |
| onDestroy | onDestroy |
| onStartCommand | onStartCommand |
| getSelf | 获取当前服务所持对象 |
| startService | 启动服务 |
| stopService | 停止服务 |
| checkAndIntentSetting | 检查是否有获取通知栏信息权限并跳转设置页面 |
| isNotificationListenerEnabled | 判断是否有获取通知栏信息权限 |
| startNotificationListenSettings | 跳转到设置页面, 开启获取通知栏信息权限 |
| cancelNotification | 取消通知方法 |
| setNotificationListener | 设置通知栏监听事件 |
| onServiceCreated | 服务创建通知 |
| onServiceDestroy | 服务销毁通知 |