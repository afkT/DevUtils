

## 目录结构

```
- src.main                      | 根目录
   - dev                        | 通用实现
      - receiver                | BroadcastReceiver 监听相关
      - service                 | Service 相关
   - java                       | Java 实现
      - dev                     | 根目录
         - assist               | 常用辅助类封装
         - engine               | 兼容 Engine
            - barcode           | BarCode Engine 条形码、二维码处理
            - cache             | Cache Engine 有效期键值对缓存
            - compress          | Image Compress Engine 图片压缩
            - image             | Image Engine 图片加载、下载、转格式等
            - json              | JSON Engine 映射
            - keyvalue          | KeyValue Engine 键值对存储
            - log               | Log Engine 日志打印
            - media             | Media Selector Engine 多媒体资源选择
               - luck_lib_engine| LuckSiege PictureSelector Engine 实现
            - permission        | Permission Engine 权限申请
            - storage           | Storage Engine 外部、内部文件存储
         - other                | 第三方库封装工具类
            - cache             | 缓存工具类
            - okgo              | OkGo 网络请求
            - retrofit_rxjava   | Retrofit RxJava 封装
               - response       | 请求响应解析
               - subscriber     | 请求响应处理
            - work              | WorkManager Utils
               - demo           | WorkManager 使用 Demo
   - ktx                        | Kotlin 实现
      - dev                     | 根目录
         - assist               | 常用辅助类封装
         - other                | 第三方库封装工具类
            - retrofit_rxjava   | Retrofit RxJava 封装
               - demo           | Retrofit RxJava Demo
               - response       | 请求响应解析
               - subscriber     | 请求响应处理
```


## Use

> 直接 copy 所需要的类到项目中使用


## API


- src.main                                                         | 根目录
   - [dev](#dev)                                                   | 通用实现
      - [receiver](#devreceiver)                                   | BroadcastReceiver 监听相关
      - [service](#devservice)                                     | Service 相关
   - [java](#java)                                                 | Java 实现
      - [dev](#javadev)                                            | 根目录
         - [assist](#javadevassist)                                | 常用辅助类封装
         - [engine](#javadevengine)                                | 兼容 Engine
            - [barcode](#javadevenginebarcode)                     | BarCode Engine 条形码、二维码处理
            - [cache](#javadevenginecache)                         | Cache Engine 有效期键值对缓存
            - [compress](#javadevenginecompress)                   | Image Compress Engine 图片压缩
            - [image](#javadevengineimage)                         | Image Engine 图片加载、下载、转格式等
            - [json](#javadevenginejson)                           | JSON Engine 映射
            - [keyvalue](#javadevenginekeyvalue)                   | KeyValue Engine 键值对存储
            - [log](#javadevenginelog)                             | Log Engine 日志打印
            - [media](#javadevenginemedia)                         | Media Selector Engine 多媒体资源选择
               - [luck_lib_engine](#javadevenginemedialuck_lib_engine)| LuckSiege PictureSelector Engine 实现
            - [permission](#javadevenginepermission)               | Permission Engine 权限申请
            - [storage](#javadevenginestorage)                     | Storage Engine 外部、内部文件存储
         - [other](#javadevother)                                  | 第三方库封装工具类
            - [cache](#javadevothercache)                          | 缓存工具类
            - [okgo](#javadevotherokgo)                            | OkGo 网络请求
            - [retrofit_rxjava](#javadevotherretrofit_rxjava)      | Retrofit RxJava 封装
               - [response](#javadevotherretrofit_rxjavaresponse)  | 请求响应解析
               - [subscriber](#javadevotherretrofit_rxjavasubscriber)| 请求响应处理
            - [work](#javadevotherwork)                            | WorkManager Utils
               - [demo](#javadevotherworkdemo)                     | WorkManager 使用 Demo
   - [ktx](#ktx)                                                   | Kotlin 实现
      - [dev](#ktxdev)                                             | 根目录
         - [assist](#ktxdevassist)                                 | 常用辅助类封装
         - [other](#ktxdevother)                                   | 第三方库封装工具类
            - [retrofit_rxjava](#ktxdevotherretrofit_rxjava)       | Retrofit RxJava 封装
               - [demo](#ktxdevotherretrofit_rxjavademo)           | Retrofit RxJava Demo
               - [response](#ktxdevotherretrofit_rxjavaresponse)   | 请求响应解析
               - [subscriber](#ktxdevotherretrofit_rxjavasubscriber)| 请求响应处理




## <span id="dev">**`dev`**</span>


## <span id="devreceiver">**`dev.receiver`**</span>


## <span id="devservice">**`dev.service`**</span>


## <span id="java">**`java`**</span>


## <span id="javadev">**`java.dev`**</span>


## <span id="javadevassist">**`java.dev.assist`**</span>


* **WebView 辅助类 ->** [WebViewAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/assist/WebViewAssist.java)

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


## <span id="javadevengine">**`java.dev.engine`**</span>


## <span id="javadevenginebarcode">**`java.dev.engine.barcode`**</span>


* **BarCode Config ->** [BarCodeConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/barcode/BarCodeConfig.java)

| 方法 | 注释 |
| :- | :- |
| getEncodeHints | 获取编码 ( 生成 ) 配置 |
| getDecodeHints | 获取解码 ( 解析 ) 配置 |
| defaultEncode | 设置默认编码 ( 生成 ) 配置 |
| putEncodeHints | 设置编码 ( 生成 ) 配置 |
| putDecodeHints | 设置解码 ( 解析 ) 配置 |


* **BarCode ( Data、Params ) Item ->** [BarCodeData.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/barcode/BarCodeData.java)

| 方法 | 注释 |
| :- | :- |
| get | 快捷创建 BarCode ( Data、Params ) Item |
| getContent | 获取条码内容 |
| getWidth | 获取条码宽度 |
| getHeight | 获取条码高度 |
| getFormat | 获取条码类型 |
| setFormat | 设置条码类型 |
| getForegroundColor | 获取条码前景色 |
| setForegroundColor | 设置条码前景色 |
| getBackgroundColor | 获取条码背景色 |
| setBackgroundColor | 设置条码背景色 |
| getIcon | 获取条码嵌入 icon、logo |
| setIcon | 设置条码嵌入 icon、logo |
| getIconScale | 获取 icon 占比 |
| setIconScale | 设置 icon 占比 |


* **BarCode Result ->** [BarCodeResult.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/barcode/BarCodeResult.java)

| 方法 | 注释 |
| :- | :- |
| isSuccess | 是否解析成功 |
| getResult | 获取解析结果 |
| getResultData | 获取扫描结果数据 |
| getBarcodeFormat | 获取条码类型 |


* **ZXing BarCode Engine 实现 ->** [ZXingEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/barcode/ZXingEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| initialize | initialize |
| getConfig | getConfig |
| encodeBarCode | encodeBarCode |
| encodeBarCodeSync | encodeBarCodeSync |
| decodeBarCode | decodeBarCode |
| decodeBarCodeSync | decodeBarCodeSync |
| addIconToBarCode | 添加 Icon 到条码图片上 |


## <span id="javadevenginecache">**`java.dev.engine.cache`**</span>


* **DevCache ( DevUtils ) Cache Engine 实现 ->** [DevCacheEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/cache/DevCacheEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| setJSONEngine | setJSONEngine |
| getConfig | getConfig |
| remove | remove |
| removeForKeys | removeForKeys |
| contains | contains |
| isDue | isDue |
| clear | clear |
| clearDue | clearDue |
| clearType | clearType |
| getItemByKey | getItemByKey |
| getKeys | getKeys |
| getPermanentKeys | getPermanentKeys |
| getCount | getCount |
| getSize | getSize |
| put | put |
| getInt | getInt |
| getLong | getLong |
| getFloat | getFloat |
| getDouble | getDouble |
| getBoolean | getBoolean |
| getString | getString |
| getBytes | getBytes |
| getBitmap | getBitmap |
| getDrawable | getDrawable |
| getSerializable | getSerializable |
| getParcelable | getParcelable |
| getJSONObject | getJSONObject |
| getJSONArray | getJSONArray |
| getEntity | getEntity |


## <span id="javadevenginecompress">**`java.dev.engine.compress`**</span>


* **Image Compress Config ->** [CompressConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/compress/CompressConfig.java)

| 方法 | 注释 |
| :- | :- |
| isFailFinish | isFailFinish |
| setFailFinish | setFailFinish |


* **Luban Image Compress Engine 实现 ->** [LubanEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/compress/LubanEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| compress | compress |


## <span id="javadevengineimage">**`java.dev.engine.image`**</span>


* **Glide Image Engine 实现 ->** [GlideEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/image/GlideEngineImpl.java)

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
| loadImageThrows | loadImageThrows |
| loadBitmap | loadBitmap |
| loadBitmapThrows | loadBitmapThrows |
| loadDrawable | loadDrawable |
| loadDrawableThrows | loadDrawableThrows |
| convertImageFormat | convertImageFormat |


* **Image Config ->** [ImageConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/image/ImageConfig.java)

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
| getQuality | getQuality |
| setQuality | setQuality |
| isOriginalPathReturn | isOriginalPathReturn |
| setOriginalPathReturn | setOriginalPathReturn |
| isDontAnimate | isDontAnimate |
| setDontAnimate | setDontAnimate |
| isDontTransform | isDontTransform |
| setDontTransform | setDontTransform |


## <span id="javadevenginejson">**`java.dev.engine.json`**</span>


* **Fastjson JSON Engine 实现 ->** [FastjsonEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/json/FastjsonEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| toJson | toJson |
| fromJson | fromJson |
| isJSON | isJSON |
| isJSONObject | isJSONObject |
| isJSONArray | isJSONArray |
| toJsonIndent | toJsonIndent |


* **Gson JSON Engine 实现 ->** [GsonEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/json/GsonEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| toJson | toJson |
| fromJson | fromJson |
| isJSON | isJSON |
| isJSONObject | isJSONObject |
| isJSONArray | isJSONArray |
| toJsonIndent | toJsonIndent |


## <span id="javadevenginekeyvalue">**`java.dev.engine.keyvalue`**</span>


* **MMKV Key-Value Config ->** [MMKVConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/keyvalue/MMKVConfig.java)

| 方法 | 注释 |
| :- | :- |
| getMMKV | getMMKV |


* **MMKV Key-Value Engine 实现 ->** [MMKVKeyValueEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/keyvalue/MMKVKeyValueEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| setJSONEngine | setJSONEngine |
| getConfig | getConfig |
| remove | remove |
| removeForKeys | removeForKeys |
| contains | contains |
| clear | clear |
| putInt | putInt |
| putLong | putLong |
| putFloat | putFloat |
| putDouble | putDouble |
| putBoolean | putBoolean |
| putString | putString |
| putEntity | putEntity |
| getInt | getInt |
| getLong | getLong |
| getFloat | getFloat |
| getDouble | getDouble |
| getBoolean | getBoolean |
| getString | getString |
| getEntity | getEntity |


* **SharedPreferences Key-Value Config ->** [SPConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/keyvalue/SPConfig.java)

| 方法 | 注释 |
| :- | :- |
| getPreference | getPreference |


* **SharedPreferences Key-Value Engine 实现 ->** [SPKeyValueEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/keyvalue/SPKeyValueEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| setJSONEngine | setJSONEngine |
| getConfig | getConfig |
| remove | remove |
| removeForKeys | removeForKeys |
| contains | contains |
| clear | clear |
| putInt | putInt |
| putLong | putLong |
| putFloat | putFloat |
| putDouble | putDouble |
| putBoolean | putBoolean |
| putString | putString |
| putEntity | putEntity |
| getInt | getInt |
| getLong | getLong |
| getFloat | getFloat |
| getDouble | getDouble |
| getBoolean | getBoolean |
| getString | getString |
| getEntity | getEntity |


## <span id="javadevenginelog">**`java.dev.engine.log`**</span>


* **DevLogger Log Engine 实现 ->** [DevLoggerEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/log/DevLoggerEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| getLogConfig | getLogConfig |
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


## <span id="javadevenginemedia">**`java.dev.engine.media`**</span>


* **Local Media Selector Data ->** [LocalMediaData.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/media/LocalMediaData.java)

| 方法 | 注释 |
| :- | :- |
| getLocalMedia | getLocalMedia |
| setLocalMedia | setLocalMedia |
| getLocalMediaPath | 获取本地资源路径 |


* **Media Selector Config ->** [MediaConfig.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/media/MediaConfig.java)

| 方法 | 注释 |
| :- | :- |
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
| getMinimumCompressSize | 获取图片大于多少才进行压缩 |
| setMinimumCompressSize | 设置图片大于多少才进行压缩 |
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
| getCameraSavePath | 获取拍照存储地址 |
| setCameraSavePath | 设置拍照存储地址 |
| getCompressSavePath | 获取压缩图片存储地址 |
| setCompressSavePath | 设置压缩图片存储地址 |
| clone | 克隆新的配置信息 |
| set | 设置新的配置信息 |
| ofAll | ofAll |
| ofImage | ofImage |
| ofVideo | ofVideo |
| ofAudio | ofAudio |


* **PictureSelector Media Selector Engine 实现 ->** [PictureSelectorEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/media/PictureSelectorEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | getConfig |
| setConfig | setConfig |
| openCamera | openCamera |
| openGallery | openGallery |
| deleteCacheDirFile | deleteCacheDirFile |
| deleteAllCacheDirFile | deleteAllCacheDirFile |
| isMediaSelectorResult | isMediaSelectorResult |
| getSelectors | getSelectors |
| getSelectorUris | getSelectorUris |
| getSingleSelector | getSingleSelector |
| getSingleSelectorUri | getSingleSelectorUri |


## <span id="javadevenginemedialuck_lib_engine">**`java.dev.engine.media.luck_lib_engine`**</span>


* ** ->** [LuckGlideEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/media/luck_lib_engine/LuckGlideEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| loadImage | 加载图片 |
| loadFolderImage | 加载相册目录 |
| loadGridImage | 加载图片列表图片 |
| createGlideEngine | createGlideEngine |


## <span id="javadevenginepermission">**`java.dev.engine.permission`**</span>


* **DevUtils Permission Engine 实现 ->** [DevPermissionEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/permission/DevPermissionEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| isGranted | isGranted |
| shouldShowRequestPermissionRationale | shouldShowRequestPermissionRationale |
| getDeniedPermissionStatus | getDeniedPermissionStatus |
| againRequest | againRequest |
| request | request |


## <span id="javadevenginestorage">**`java.dev.engine.storage`**</span>


* **DevUtils MediaStore Engine 实现 ->** [DevMediaStoreEngineImpl.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/storage/DevMediaStoreEngineImpl.java)

| 方法 | 注释 |
| :- | :- |
| insertImageToExternal | 插入一张图片到外部存储空间 ( SDCard ) |
| insertVideoToExternal | 插入一条视频到外部存储空间 ( SDCard ) |
| insertAudioToExternal | 插入一条音频到外部存储空间 ( SDCard ) |
| insertDownloadToExternal | 插入一条文件资源到外部存储空间 ( SDCard ) |
| insertMediaToExternal | 插入一条多媒体资源到外部存储空间 ( SDCard ) |
| insertImageToInternal | 插入一张图片到内部存储空间 |
| insertVideoToInternal | 插入一条视频到内部存储空间 |
| insertAudioToInternal | 插入一条音频到内部存储空间 |
| insertDownloadToInternal | 插入一条文件资源到内部存储空间 |
| insertMediaToInternal | 插入一条多媒体资源到内部存储空间 |


* **Storage Item Params ->** [StorageItem.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/storage/StorageItem.java)

| 方法 | 注释 |
| :- | :- |
| getOutputUri | getOutputUri |
| getFileName | getFileName |
| getFilePath | getFilePath |
| getFolder | getFolder |
| getMimeType | getMimeType |
| getFormat | getFormat |
| setFormat | setFormat |
| getQuality | getQuality |
| setQuality | setQuality |
| getInternalFile | 获取内部存储完整路径 |
| getExternalFile | 获取外部存储完整路径 |
| getExternalFolder | 获取外部存储文件夹路径 |
| createUriItem | 创建指定输出 Uri Item |
| createInternalItem | 创建内部存储路径信息 Item |
| createExternalItem | 创建外部存储路径信息 Item |
| createExternalItemFolder | 创建外部存储路径信息 Item |


* **Storage Result ->** [StorageResult.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/storage/StorageResult.java)

| 方法 | 注释 |
| :- | :- |
| success | 存储成功 |
| failure | 存储失败 |
| isSuccess | isSuccess |
| getUri | getUri |
| getFile | getFile |
| getError | getError |
| getType | getType |
| isExternal | isExternal |
| setUri | setUri |
| setFile | setFile |
| setError | setError |
| setType | setType |
| setExternal | setExternal |


* **Storage Type ->** [StorageType.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/engine/storage/StorageType.java)

| 方法 | 注释 |
| :- | :- |
| isImage | isImage |
| isVideo | isVideo |
| isAudio | isAudio |
| isDownload | isDownload |
| isNone | isNone |
| convertType | 通过文件后缀判断存储类型 |
| convertTypeByMimeType | 通过 mimeType 判断存储类型 |
| convertTypeByFileName | 通过 fileName 判断存储类型 |
| getTypeRelativePath | 通过 mimeType 获取对应存储文件夹 |


## <span id="javadevother">**`java.dev.other`**</span>


* **EventBus 工具类 ->** [EventBusUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/EventBusUtils.java)

| 方法 | 注释 |
| :- | :- |
| register | 注册 EventBus |
| unregister | 解绑 EventBus |
| post | 发送事件消息 |
| cancelEventDelivery | 取消事件传送 |
| postSticky | 发送粘性事件消息 |
| removeStickyEvent | 移除指定的粘性订阅事件 |
| removeAllStickyEvents | 移除所有的粘性订阅事件 |


* **Fastjson 工具类 ->** [FastjsonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/FastjsonUtils.java)

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


* **Glide 工具类 ->** [GlideUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/GlideUtils.java)

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
| loadImageThrows | loadImageThrows |
| loadBitmap | loadBitmap |
| loadBitmapThrows | loadBitmapThrows |
| loadDrawable | loadDrawable |
| loadDrawableThrows | loadDrawableThrows |
| convertImageFormat | convertImageFormat |


* **Gson 工具类 ->** [GsonUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/GsonUtils.java)

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


* **ImageLoader 工具类 ->** [ImageLoaderUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/ImageLoaderUtils.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化 ImageLoader 加载配置 |
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


* **Luban 工具类 ->** [LubanUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/LubanUtils.java)

| 方法 | 注释 |
| :- | :- |
| compress | 压缩方法 |
| isFailFinish | isFailFinish |
| setFailFinish | setFailFinish |
| onStart | 开始压缩前调用 |
| onSuccess | 压缩成功后调用 |
| onError | 当压缩过程出现问题时触发 |
| onComplete | 压缩完成 ( 压缩结束 ) |


* **MMKV 工具类 ->** [MMKVUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/MMKVUtils.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 ( 必须调用 ) |
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
| clear | 清除全部数据 |
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


* **Android 平台下的图片选择器 ->** [PictureSelectorUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/PictureSelectorUtils.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取全局配置 |
| setConfig | 设置全局配置 |
| openCamera | 打开相册拍照 |
| openGallery | 打开相册选择 |
| deleteCacheDirFile | 删除缓存文件 |
| deleteAllCacheDirFile | 删除全部缓存文件 |
| isMediaSelectorResult | 是否图片选择 ( onActivityResult ) |
| getSelectors | 获取 Media Selector Data List |
| getSelectorUris | 获取 Media Selector Uri List |
| getSingleSelector | 获取 Single Media Selector Data |
| getSingleSelectorUri | 获取 Single Media Selector Uri |


* **ZXing 条形码、二维码工具类 ->** [ZXingUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/ZXingUtils.java)

| 方法 | 注释 |
| :- | :- |
| initialize | initialize |
| getConfig | getConfig |
| encodeBarCode | encodeBarCode |
| encodeBarCodeSync | encodeBarCodeSync |
| decodeBarCode | decodeBarCode |
| decodeBarCodeSync | decodeBarCodeSync |
| addIconToBarCode | addIconToBarCode |


## <span id="javadevothercache">**`java.dev.other.cache`**</span>


* **缓存工具类 ->** [ACache.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/cache/ACache.java)

| 方法 | 注释 |
| :- | :- |
| newCache | 获取 DevCache ( 默认缓存文件名 ) |
| put | 保存 String 数据到缓存中 |
| getAsString | 读取 String 数据 |
| getAsJSONObject | 读取 JSONObject 数据 |
| getAsJSONArray | 读取 JSONArray 数据 |
| get | 获取对应 key 的 File 输入流 |
| getAsBinary | 获取 byte[] 数据 |
| getAsObject | 读取 Serializable 数据 |
| getAsBitmap | 读取 Bitmap 数据 |
| getAsDrawable | 读取 Drawable 数据 |
| file | 获取缓存文件 |
| remove | 移除某个 key 的数据 |
| clear | 清除所有数据 |


## <span id="javadevotherokgo">**`java.dev.other.okgo`**</span>


* **请求回调统一处理类 ->** [OkGoCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/okgo/OkGoCallback.java)

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


* **OkGo 使用 Demo ->** [OkGoDemo.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/okgo/OkGoDemo.java)

| 方法 | 注释 |
| :- | :- |
| getUserInfo | 获取用户信息 |
| getUserList | 获取用户列表 |
| uploadImage | 上传文件 |
| uploadImages | 上传多个文件 |


* **请求响应统一解析类 ->** [OkGoResponse.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/okgo/OkGoResponse.java)

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


* **OkGo 配置相关工具类 ->** [OkGoUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/okgo/OkGoUtils.java)

| 方法 | 注释 |
| :- | :- |
| initOkGo | 初始化 OkGo 配置 |
| execute | 执行请求处理 |


## <span id="javadevotherretrofit_rxjava">**`java.dev.other.retrofit_rxjava`**</span>


* **Retrofit 管理类 ->** [RetrofitManager.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/retrofit_rxjava/RetrofitManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | getInstance |
| get | 通过 tag 获取 Retrofit |
| put | 通过 tag 保存 Retrofit |
| remove | 通过 tag 移除 Retrofit |
| contains | 通过 tag 判断是否存在 Retrofit |
| getRetrofitMap | 获取 Retrofit Map |
| create | 创建 API Service Class |


* **RxJava 相关管理类 ( 针对 Retrofit ) ->** [RxJavaManager.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/retrofit_rxjava/RxJavaManager.java)

| 方法 | 注释 |
| :- | :- |
| getInstance | getInstance |
| add | 通过 tag 将请求添加到统一管理对象中 |
| remove | 通过 tag 移除请求 |
| contains | 通过 tag 判断是否存在 CompositeDisposable |
| getManagerMap | 获取 CompositeDisposable Map |
| io_main | Flowable UI 线程 |


## <span id="javadevotherretrofit_rxjavaresponse">**`java.dev.other.retrofit_rxjava.response`**</span>


## <span id="javadevotherretrofit_rxjavasubscriber">**`java.dev.other.retrofit_rxjava.subscriber`**</span>


* **服务器请求响应处理, 映射各种 JSON 实体类 ->** [BaseBeanSubscriber.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/retrofit_rxjava/subscriber/BaseBeanSubscriber.java)

| 方法 | 注释 |
| :- | :- |
| onNext | onNext |
| onError | onError |
| onStart | onStart |
| onComplete | onComplete |
| onSuccessResponse | 请求响应并处理数据无误 |
| onErrorResponse | 请求失败、响应错误、数据解析错误等, 都会回调该方法 ( UI 线程 ) |
| getErrorMessage | 获取异常信息 |


* **服务器请求响应处理, 映射统一标准 JSON 格式实体类 ->** [BaseResponseSubscriber.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/retrofit_rxjava/subscriber/BaseResponseSubscriber.java)

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


## <span id="javadevotherwork">**`java.dev.other.work`**</span>


* **WorkManager 工具类 ->** [WorkManagerUtils.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/work/WorkManagerUtils.java)

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


## <span id="javadevotherworkdemo">**`java.dev.other.work.demo`**</span>


* ** ->** [ChainWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/work/demo/ChainWorker.java)

| 方法 | 注释 |
| :- | :- |
| start | 模拟工作链 |
| builder | builder |
| doWork | doWork |


* **模拟后台间隔性进行定位 ->** [LocationWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/work/demo/LocationWorker.java)

| 方法 | 注释 |
| :- | :- |
| doWork | doWork |
| builder | 快捷创建循环 Worker Request |


* **打印日志 Worker ( 用于演示 ) ->** [LogWorker.java](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java//java/dev/other/work/demo/LogWorker.java)

| 方法 | 注释 |
| :- | :- |
| onStopped | 只会 worker 在运行时执行 onStopped |
| doWork | doWork |
| builder | 快捷创建 {@link OneTimeWorkRequest.Builder} |
| observe | 监听 WorkRequest 状态 |


## <span id="ktx">**`ktx`**</span>


## <span id="ktxdev">**`ktx.dev`**</span>


## <span id="ktxdevassist">**`ktx.dev.assist`**</span>


## <span id="ktxdevother">**`ktx.dev.other`**</span>


## <span id="ktxdevotherretrofit_rxjava">**`ktx.dev.other.retrofit_rxjava`**</span>


## <span id="ktxdevotherretrofit_rxjavademo">**`ktx.dev.other.retrofit_rxjava.demo`**</span>


## <span id="ktxdevotherretrofit_rxjavaresponse">**`ktx.dev.other.retrofit_rxjava.response`**</span>


## <span id="ktxdevotherretrofit_rxjavasubscriber">**`ktx.dev.other.retrofit_rxjava.subscriber`**</span>