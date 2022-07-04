
## Gradle

```gradle
implementation 'io.github.afkt:DevEngine:1.0.8'
```

## 目录结构

```
- dev.engine            | 根目录
   - barcode            | BarCode Engine 条形码、二维码处理
   - cache              | Cache Engine 有效期键值对缓存
   - compress           | Image Compress Engine 图片压缩
   - image              | Image Engine 图片加载、下载、转格式等
   - json               | JSON Engine 映射
   - keyvalue           | KeyValue Engine 键值对存储
   - log                | Log Engine 日志打印
   - media              | Media Selector Engine 多媒体资源选择
   - permission         | Permission Engine 权限申请
   - storage            | Storage Engine 外部、内部文件存储
```


## About

DevEngine（基于 [DevAssist Engine 模块](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist)）
主要为了解决项目代码中**对第三方框架强依赖使用、以及部分功能版本适配。**

通过实现对应功能模块 Engine 接口，实现对应的方法功能，
**对外无需关注实现代码，直接通过 DevXXXEngine 进行调用，实现对第三方框架解耦、一键替换第三方库、同类库多 Engine 混合使用、以及部分功能适配 ( 如外部文件存储 MediaStore 全局适配 ) 等**

> 可通过 Key-Engine 实现对组件化、模块化各个 Module 使用同类型 Engine 不同库实现使用

## 依赖实现信息

> 该库为 Kotlin 实现，另有 [Java 实现代码](https://github.com/afkT/DevUtils/blob/master/lib/LocalModules/DevOther/src/main/java/java/dev/engine)
> ，**该库会依赖第三方库导致项目体积变大等情况**，可自行 copy 所需已实现代码进行使用

已实现模块有（依赖第三方库实现列表如下展示）：

* **BarCode 条形码**
* **Cache 有效期键值对缓存**
* **Image Compress 图片压缩**
* **Image 图片加载、下载、转格式**
* **JSON 映射**
* **KeyValue 键值对存储**
* **Log 日志打印**
* **Media Selector 多媒体资源选择**
* **Permission 权限申请**
* **Storage Engine 外部、内部文件存储**

> 如已封装代码无法直接使用于需求实现，可自行 copy 代码进行修改，或自定义对应模块 Engine 实现类进行设置并通过 DevXxxEngine.getEngine(key) 进行调用获取

## 项目类结构 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine)


### BarCode 条形码 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/barcode)

可选实现方案：

* 依赖 [ZXing](https://github.com/zxing/zxing) 实现 [ZXingEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/barcode/ZXingEngineImpl.kt)


### Cache 有效期键值对缓存 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/cache)

可选实现方案：

* 依赖 [DevApp DevCache](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/cache)
  实现 [DevCacheEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/cache/engine_dev_cache.kt)


### Image Compress 图片压缩 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/compress)

可选实现方案：

* 依赖 [Luban 鲁班图片压缩](https://github.com/Curzibn/Luban)
  实现 [LubanEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/compress/engine_luban.kt)


### Image 图片加载、下载、转格式 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/image)

可选实现方案：

* 依赖 [Glide 图片加载框架](https://github.com/bumptech/glide)
  实现 [GlideEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/image/engine_glide.kt)


### JSON 映射 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/json)

可选实现方案：

* 依赖 [Gson](https://github.com/google/gson)
  实现 [GsonEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/json/engine_gson.kt)

* 依赖 [Fastjson2](https://github.com/alibaba/fastjson2)
  实现 [FastjsonEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/json/engine_fastjson.kt)


### KeyValue 键值对存储 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/keyvalue)

可选实现方案：

* 依赖 [基于 mmap 的高性能通用 key-value 组件 MMKV](https://github.com/Tencent/MMKV/blob/master/README_CN.md)
  实现 [MMKVKeyValueEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/keyvalue/engine_mmkv.kt)

* 依赖 [DevApp SharedPreferences 封装](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/share)
  实现 [SPKeyValueEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/keyvalue/engine_sp.kt)


### Log 日志打印 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/log)

可选实现方案：

* 依赖 [DevApp DevLogger](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/logger)
  实现 [DevLoggerEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/log/engine_dev_logger.kt)


### Media Selector 多媒体资源选择 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/media)

可选实现方案：

* 依赖 [Android 平台下的图片选择器 PictureSelector](https://github.com/LuckSiege/PictureSelector)
  实现 [PictureSelectorEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/media/engine_picture_selector.kt)


### Permission 权限申请 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/permission)

可选实现方案：

* 依赖 [DevApp PermissionUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/permission/PermissionUtils.java)
  实现 [DevPermissionEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/permission/engine_dev_permission.kt)


### Storage Engine 外部、内部文件存储 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/storage)

可选实现方案：

* 依赖 [DevApp MediaStoreUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/MediaStoreUtils.java)
  、[UriUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/UriUtils.java)
  实现 [DevMediaStoreEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/storage/engine_dev_media_store.kt)


## 使用示例

```kotlin
/**
 * 初始化 Engine
 */
private fun initEngine(appContext: Application) {

    // =========
    // = 初始化 =
    // =========
    
    // =============
    // = 完整版初始化 =
    // =============
    
    // 完整初始化 ( 全面使用该库调用该方法初始化即可 )
    DevEngine.completeInitialize(appContext)

    // ============
    // = 部分初始化 =
    // ============

    // 如不需使用 MMKV Key-Value 则直接调用以下方法即可
    DevEngine.defaultEngine()
    
    // 如果需要使用 MMKV Key-Value 则需要如下初始化

    // 使用内部默认实现 Engine ( 使用 MMKV 必须调用 defaultMMKVInitialize() )
    DevEngine.defaultMMKVInitialize(appContext)
        .defaultEngine(MMKVConfig(cipher, mmkv))

    // 如不想内部默认初始化全部 Engine 也可单独调用进行初始化覆盖 ( 参考下方【设置】 )

    // =======
    // = 使用 =
    // =======

    // =====================
    // = 通过 DevEngine 使用 =
    // =====================

    // Analytics Engine 数据统计 ( 埋点 )
    DevEngine.getAnalytics().xxx

    // Cache Engine 有效期键值对缓存
    DevEngine.getCache().xxx

    // Image Compress Engine 图片压缩
    DevEngine.getCompress().xxx

    // Image Engine 图片加载、下载、转格式等
    DevEngine.getImage().xxx

    // JSON Engine 映射
    DevEngine.getJSON().xxx

    // KeyValue Engine 键值对存储
    DevEngine.getKeyValue().xxx

    // Log Engine 日志打印
    DevEngine.getLog().xxx

    // Media Selector Engine 多媒体资源选择
    DevEngine.getMedia().xxx

    // Permission Engine 权限申请
    DevEngine.getPermission().xxx

    // Push Engine 推送平台处理
    DevEngine.getPush().xxx

    // Share Engine 分享平台处理
    DevEngine.getShare().xxx

    // Storage Engine 外部、内部文件存储
    DevEngine.getStorage().xxx

    // =====================
    // = 通过 DevAssist 使用 =
    // =====================

    // Analytics Engine 数据统计 ( 埋点 )
    DevAnalyticsEngine.getEngine().xxx

    // Cache Engine 有效期键值对缓存
    DevCacheEngine.getEngine().xxx

    // Image Compress Engine 图片压缩
    DevCompressEngine.getEngine().xxx

    // Image Engine 图片加载、下载、转格式等
    DevImageEngine.getEngine().xxx

    // JSON Engine 映射
    DevJSONEngine.getEngine().xxx

    // KeyValue Engine 键值对存储
    DevKeyValueEngine.getEngine().xxx

    // Log Engine 日志打印
    DevLogEngine.getEngine().xxx

    // Media Selector Engine 多媒体资源选择
    DevMediaEngine.getEngine().xxx

    // Permission Engine 权限申请
    DevPermissionEngine.getEngine().xxx

    // Push Engine 推送平台处理
    DevPushEngine.getEngine().xxx

    // Share Engine 分享平台处理
    DevShareEngine.getEngine().xxx

    // Storage Engine 外部、内部文件存储
    DevStorageEngine.getEngine().xxx

    // =======
    // = 设置 =
    // =======

    // 设置 Engine 实现, 组件化、模块化可通过设置各个模块 Key 进行区分
    DevEngine.getJSONAssist().setEngine(EngineImpl)
    DevEngine.getJSONAssist().setEngine(Key, EngineImpl)

    DevJSONEngine.setEngine(EngineImpl)
    DevJSONEngine.setEngine(Key, EngineImpl)

    // 使用 GSON
    DevJSONEngine.setEngine(GsonEngineImpl())
    // 使用 Fastjson
    DevJSONEngine.setEngine(Key, FastjsonEngineImpl())

    // GsonEngineImpl
    DevJSONEngine.getEngine()
    // FastjsonEngineImpl
    DevJSONEngine.getEngine(Key)
}
```


## 目录结构

```
- dev                                                 | 根目录
   - engine                                           | 兼容 Engine
      - analytics                                     | Analytics Engine 数据统计 ( 埋点 )
      - barcode                                       | BarCode Engine 条形码、二维码处理
         - listener                                   | 条形码、二维码操作回调事件
      - cache                                         | Cache Engine 有效期键值对缓存
      - compress                                      | Image Compress Engine 图片压缩
         - listener                                   | 图片压缩回调事件
      - image                                         | Image Engine 图片加载、下载、转格式等
         - listener                                   | 图片加载监听事件
      - json                                          | JSON Engine 映射
      - keyvalue                                      | KeyValue Engine 键值对存储
      - log                                           | Log Engine 日志打印
      - media                                         | Media Selector Engine 多媒体资源选择
      - permission                                    | Permission Engine 权限申请
      - push                                          | Push Engine 推送平台处理
      - share                                         | Share Engine 分享平台处理
         - listener                                   | 分享回调事件
      - storage                                       | Storage Engine 外部、内部文件存储
         - listener                                   | Storage 存储结果事件
```

## API

- dev                                                     | 根目录
    - [engine](#devengine)                                 | 兼容 Engine
        - [analytics](#devengineanalytics)                  | Analytics Engine 数据统计 ( 埋点 )
        - [barcode](#devenginebarcode)                      | BarCode Engine 条形码、二维码处理
            - [listener](#devenginebarcodelistener)          | 条形码、二维码操作回调事件
        - [cache](#devenginecache)                          | Cache Engine 有效期键值对缓存
        - [compress](#devenginecompress)                    | Image Compress Engine 图片压缩
            - [listener](#devenginecompresslistener)         | 图片压缩回调事件
        - [image](#devengineimage)                          | Image Engine 图片加载、下载、转格式等
            - [listener](#devengineimagelistener)            | 图片加载监听事件
        - [json](#devenginejson)                            | JSON Engine 映射
        - [keyvalue](#devenginekeyvalue)                    | KeyValue Engine 键值对存储
        - [log](#devenginelog)                              | Log Engine 日志打印
        - [media](#devenginemedia)                          | Media Selector Engine 多媒体资源选择
        - [permission](#devenginepermission)                | Permission Engine 权限申请
        - [push](#devenginepush)                            | Push Engine 推送平台处理
        - [share](#devengineshare)                          | Share Engine 分享平台处理
            - [listener](#devenginesharelistener)            | 分享回调事件
        - [storage](#devenginestorage)                      | Storage Engine 外部、内部文件存储
            - [listener](#devenginestoragelistener)          | Storage 存储结果事件



## <span id="devengine">**`dev.engine`**</span>


* **DevEngine Generic Assist ->** [DevEngineAssist.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/DevEngineAssist.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


## <span id="devengineanalytics">**`dev.engine.analytics`**</span>


* **Analytics Engine ->** [DevAnalyticsEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/analytics/DevAnalyticsEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Analytics Engine 接口 ->** [IAnalyticsEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/analytics/IAnalyticsEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| register | 绑定 |
| unregister | 解绑 |
| track | 数据统计 ( 埋点 ) 方法 |


## <span id="devenginebarcode">**`dev.engine.barcode`**</span>


* **BarCode Engine ->** [DevBarCodeEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/DevBarCodeEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **BarCode Engine 接口 ->** [IBarCodeEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/IBarCodeEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| getConfig | 获取 BarCode Engine Config |
| encodeBarCode | 编码 ( 生成 ) 条码图片 |
| encodeBarCodeSync | 编码 ( 生成 ) 条码图片 |
| decodeBarCode | 解码 ( 解析 ) 条码图片 |
| decodeBarCodeSync | 解码 ( 解析 ) 条码图片 |
| addIconToBarCode | 添加 Icon 到条码图片上 |


## <span id="devenginebarcodelistener">**`dev.engine.barcode.listener`**</span>


* **条码解码 ( 解析 ) 回调 ->** [BarCodeDecodeCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/listener/BarCodeDecodeCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 条码解码 ( 解析 ) 回调 |


* **条码编码 ( 生成 ) 回调 ->** [BarCodeEncodeCallback.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/barcode/listener/BarCodeEncodeCallback.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 条码编码 ( 生成 ) 回调 |


## <span id="devenginecache">**`dev.engine.cache`**</span>


* **Cache Engine ->** [DevCacheEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/cache/DevCacheEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Cache Engine 接口 ->** [ICacheEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/cache/ICacheEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Cache Engine Config |
| remove | 移除数据 |
| removeForKeys | 移除数组的数据 |
| contains | 是否存在 key |
| isDue | 判断某个 key 是否过期 |
| clear | 清除全部数据 |
| clearDue | 清除过期数据 |
| clearType | 清除某个类型的全部数据 |
| getItemByKey | 通过 Key 获取 Item |
| getKeys | 获取有效 Key 集合 |
| getPermanentKeys | 获取永久有效 Key 集合 |
| getCount | 获取有效 Key 数量 |
| getSize | 获取有效 Key 占用总大小 |
| put | 保存 int 类型的数据 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getBytes | 获取 byte[] 类型的数据 |
| getBitmap | 获取 Bitmap 类型的数据 |
| getDrawable | 获取 Drawable 类型的数据 |
| getSerializable | 获取 Serializable 类型的数据 |
| getParcelable | 获取 Parcelable 类型的数据 |
| getJSONObject | 获取 JSONObject 类型的数据 |
| getJSONArray | 获取 JSONArray 类型的数据 |
| getEntity | 获取指定类型对象 |


## <span id="devenginecompress">**`dev.engine.compress`**</span>


* **Image Compress Engine ->** [DevCompressEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/DevCompressEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Image Compress Engine 接口 ->** [ICompressEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/ICompressEngine.java)

| 方法 | 注释 |
| :- | :- |
| compress | 压缩方法 |


## <span id="devenginecompresslistener">**`dev.engine.compress.listener`**</span>


* **压缩过滤接口 ->** [CompressFilter.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/CompressFilter.java)

| 方法 | 注释 |
| :- | :- |
| apply | 根据路径判断是否进行压缩 |


* **压缩回调接口 ->** [OnCompressListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/OnCompressListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始压缩前调用 |
| onSuccess | 压缩成功后调用 |
| onError | 当压缩过程出现问题时触发 |
| onComplete | 压缩完成 ( 压缩结束 ) |


* **修改压缩图片文件名接口 ->** [OnRenameListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/compress/listener/OnRenameListener.java)

| 方法 | 注释 |
| :- | :- |
| rename | 压缩前调用该方法用于修改压缩后文件名 |


## <span id="devengineimage">**`dev.engine.image`**</span>


* **Image Engine ->** [DevImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/DevImageEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Image Engine 接口 ->** [IImageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/IImageEngine.java)

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


## <span id="devengineimagelistener">**`dev.engine.image.listener`**</span>


* **Bitmap 加载事件 ->** [BitmapListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/BitmapListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


* **转换图片格式存储接口 ->** [ConvertStorage.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/ConvertStorage.java)

| 方法 | 注释 |
| :- | :- |
| convert | 转换图片格式并存储 |


* **Drawable 加载事件 ->** [DrawableListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/DrawableListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | getTranscodeType |


* **图片加载事件 ->** [LoadListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/LoadListener.java)

| 方法 | 注释 |
| :- | :- |
| getTranscodeType | 获取转码类型 |
| onStart | 开始加载 |
| onResponse | 响应回调 |
| onFailure | 失败回调 |


* **转换图片格式回调接口 ->** [OnConvertListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/image/listener/OnConvertListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始转换前调用 |
| onSuccess | 转换成功后调用 |
| onError | 当转换过程出现问题时触发 |
| onComplete | 转换完成 ( 转换结束 ) |


## <span id="devenginejson">**`dev.engine.json`**</span>


* **JSON Engine ->** [DevJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/DevJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **JSON Engine 接口 ->** [IJSONEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/json/IJSONEngine.java)

| 方法 | 注释 |
| :- | :- |
| toJson | 将对象转换为 JSON String |
| fromJson | 将 JSON String 映射为指定类型对象 |
| isJSON | 判断字符串是否 JSON 格式 |
| isJSONObject | 判断字符串是否 JSON Object 格式 |
| isJSONArray | 判断字符串是否 JSON Array 格式 |
| toJsonIndent | JSON String 缩进处理 |


## <span id="devenginekeyvalue">**`dev.engine.keyvalue`**</span>


* **KeyValue Engine ->** [DevKeyValueEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/keyvalue/DevKeyValueEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Key-Value Engine 接口 ->** [IKeyValueEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/keyvalue/IKeyValueEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取 Key-Value Engine Config |
| remove | 移除数据 |
| removeForKeys | 移除数组的数据 |
| contains | 是否存在 key |
| clear | 清除全部数据 |
| putInt | 保存 int 类型的数据 |
| putLong | 保存 long 类型的数据 |
| putFloat | 保存 float 类型的数据 |
| putDouble | 保存 double 类型的数据 |
| putBoolean | 保存 boolean 类型的数据 |
| putString | 保存 String 类型的数据 |
| putEntity | 保存指定类型对象 |
| getInt | 获取 int 类型的数据 |
| getLong | 获取 long 类型的数据 |
| getFloat | 获取 float 类型的数据 |
| getDouble | 获取 double 类型的数据 |
| getBoolean | 获取 boolean 类型的数据 |
| getString | 获取 String 类型的数据 |
| getEntity | 获取指定类型对象 |


## <span id="devenginelog">**`dev.engine.log`**</span>


* **Log Engine ->** [DevLogEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/log/DevLogEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Log Engine 接口 ->** [ILogEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/log/ILogEngine.java)

| 方法 | 注释 |
| :- | :- |
| isPrintLog | 判断是否打印日志 |
| d | 打印 Log.DEBUG |
| e | 打印 Log.ERROR |
| w | 打印 Log.WARN |
| i | 打印 Log.INFO |
| v | 打印 Log.VERBOSE |
| wtf | 打印 Log.ASSERT |
| json | 格式化 JSON 格式数据, 并打印 |
| xml | 格式化 XML 格式数据, 并打印 |
| dTag | 打印 Log.DEBUG |
| eTag | 打印 Log.ERROR |
| wTag | 打印 Log.WARN |
| iTag | 打印 Log.INFO |
| vTag | 打印 Log.VERBOSE |
| wtfTag | 打印 Log.ASSERT |
| jsonTag | 格式化 JSON 格式数据, 并打印 |
| xmlTag | 格式化 XML 格式数据, 并打印 |


## <span id="devenginemedia">**`dev.engine.media`**</span>


* **Media Selector Engine ->** [DevMediaEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/media/DevMediaEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Media Selector Engine 接口 ->** [IMediaEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/media/IMediaEngine.java)

| 方法 | 注释 |
| :- | :- |
| getConfig | 获取全局配置 |
| setConfig | 设置全局配置 |
| openCamera | 打开相册拍照 |
| openGallery | 打开相册选择 |
| openPreview | 打开相册预览 |
| deleteCacheDirFile | 删除缓存文件 |
| deleteAllCacheDirFile | 删除全部缓存文件 |
| isMediaSelectorResult | 是否图片选择 ( onActivityResult ) |
| getSelectors | 获取 Media Selector Data List |
| getSelectorUris | 获取 Media Selector Uri List |
| getSingleSelector | 获取 Single Media Selector Data |
| getSingleSelectorUri | 获取 Single Media Selector Uri |


## <span id="devenginepermission">**`dev.engine.permission`**</span>


* **Permission Engine ->** [DevPermissionEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/permission/DevPermissionEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Permission Engine 接口 ->** [IPermissionEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/permission/IPermissionEngine.java)

| 方法 | 注释 |
| :- | :- |
| isGranted | 判断是否授予了权限 |
| shouldShowRequestPermissionRationale | 获取拒绝权限询问勾选状态 |
| getDeniedPermissionStatus | 获取拒绝权限询问状态集合 |
| againRequest | 再次请求处理操作 |
| request | 请求权限 |
| onGranted | 授权通过权限回调 |
| onDenied | 授权未通过权限回调 |


## <span id="devenginepush">**`dev.engine.push`**</span>


* **Push Engine ->** [DevPushEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/push/DevPushEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Push Engine 接口 ->** [IPushEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/push/IPushEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| register | 绑定 |
| unregister | 解绑 |
| onReceiveServicePid | 推送进程启动通知 |
| onReceiveClientId | 初始化 Client Id 成功通知 |
| onReceiveDeviceToken | 设备 ( 厂商 ) Token 通知 |
| onReceiveOnlineState | 在线状态变化通知 |
| onReceiveCommandResult | 命令回执通知 |
| onNotificationMessageArrived | 推送消息送达通知 |
| onNotificationMessageClicked | 推送消息点击通知 |
| onReceiveMessageData | 透传消息送达通知 |
| convertMessage | 传入 Object 转换 Engine Message |


## <span id="devengineshare">**`dev.engine.share`**</span>


* **Share Engine ->** [DevShareEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/DevShareEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Share Engine 接口 ->** [IShareEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/IShareEngine.java)

| 方法 | 注释 |
| :- | :- |
| initialize | 初始化方法 |
| openMinApp | 打开小程序 |
| shareMinApp | 分享小程序 |
| shareUrl | 分享链接 |
| shareImage | 分享图片 |
| shareImageList | 分享多张图片 |
| shareText | 分享文本 |
| shareVideo | 分享视频 |
| shareMusic | 分享音乐 |
| shareEmoji | 分享表情 |
| shareFile | 分享文件 |
| share | 分享操作 ( 通用扩展 ) |
| onActivityResult | 部分平台 Activity onActivityResult 额外调用处理 |


## <span id="devenginesharelistener">**`dev.engine.share.listener`**</span>


* **分享回调 ->** [ShareListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/share/listener/ShareListener.java)

| 方法 | 注释 |
| :- | :- |
| onStart | 开始分享 |
| onResult | 分享成功 |
| onError | 分享失败 |
| onCancel | 取消分享 |


## <span id="devenginestorage">**`dev.engine.storage`**</span>


* **Storage Engine ->** [DevStorageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/DevStorageEngine.java)

| 方法 | 注释 |
| :- | :- |
| getEngine | 获取 Engine |
| setEngine | 设置 Engine |
| removeEngine | 移除 Engine |
| getAssist | 获取 DevEngine Generic Assist |
| getEngineMaps | 获取 Engine Map |
| contains | 是否存在 Engine |
| isEmpty | 判断 Engine 是否为 null |


* **Storage Engine 接口 ->** [IStorageEngine.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/IStorageEngine.java)

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


## <span id="devenginestoragelistener">**`dev.engine.storage.listener`**</span>


* **插入多媒体资源事件 ->** [OnInsertListener.java](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/src/main/java/dev/engine/storage/listener/OnInsertListener.java)

| 方法 | 注释 |
| :- | :- |
| onResult | 插入多媒体资源结果方法 |



