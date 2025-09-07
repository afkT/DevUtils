
## Gradle

```gradle
// DevEngine - 第三方框架解耦、一键替换第三方库、同类库多 Engine 组件化混合使用
implementation 'io.github.afkt:DevEngine:1.1.5'
```

## 目录结构

```
- dev.engine            | 根目录
   - core               | 核心实现代码
      - barcode         | BarCode Engine 条形码、二维码处理
      - cache           | Cache Engine 有效期键值对缓存
      - compress        | Image Compress Engine 图片压缩
      - image           | Image Engine 图片加载、下载、转格式等
      - json            | JSON Engine 映射
      - keyvalue        | KeyValue Engine 键值对存储
      - log             | Log Engine 日志打印
      - media           | Media Selector Engine 多媒体资源选择
      - permission      | Permission Engine 权限申请
      - storage         | Storage Engine 外部、内部文件存储
      - toast           | Toast Engine 吐司提示
   - extensions         | Kotlin 扩展代码、函数实现
      - analytics       | Analytics Engine 数据统计 ( 埋点 )
      - barcode         | BarCode Engine 条形码、二维码处理
      - cache           | Cache Engine 有效期键值对缓存
      - compress        | Image Compress Engine 图片压缩
      - image           | Image Engine 图片加载、下载、转格式等
      - json            | JSON Engine 映射
      - keyvalue        | KeyValue Engine 键值对存储
      - log             | Log Engine 日志打印
      - media           | Media Selector Engine 多媒体资源选择
      - permission      | Permission Engine 权限申请
      - push            | Push Engine 推送平台处理
      - share           | Share Engine 分享平台处理
      - storage         | Storage Engine 外部、内部文件存储
      - toast           | Toast Engine 吐司提示
```


## About

DevEngine（基于 [DevAssist Engine 模块](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist)）
主要为了解决项目代码中**对第三方框架强依赖使用、以及部分功能版本适配。**

通过实现对应功能模块 Engine 接口，实现对应的方法功能，
**对外无需关注实现代码，直接通过 DevXXXEngine 进行调用，实现对第三方框架解耦、一键替换第三方库、同类库多 Engine 混合使用、以及部分功能适配 ( 如外部文件存储 MediaStore 全局适配 ) 等**

> 可通过 Key-Engine 实现对组件化、模块化各个 Module 使用同类型 Engine 不同库实现使用

## 依赖实现信息

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
* **Toast Engine 吐司提示**

> **该库会依赖第三方库导致项目体积变大等情况**，可自行 copy 所需已实现代码进行修改使用

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

* 依赖 [XXPermissions 权限请求框架](https://github.com/getActivity/XXPermissions)
  实现 [XXPermissionsEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/permission/engine_xxpermissions.kt)


### Storage Engine 外部、内部文件存储 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/storage)

可选实现方案：

* 依赖 [DevApp MediaStoreUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/MediaStoreUtils.java)
  、[UriUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/UriUtils.java)
  实现 [DevMediaStoreEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/storage/engine_dev_media_store.kt)


### Toast Engine 吐司提示 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/toast)

可选实现方案：

* 依赖 [Toaster 吐司框架](https://github.com/getActivity/Toaster)
  实现 [ToasterEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/toast/engine_toaster.kt)


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

    // 不想使用完整初始化，可调用默认实现代码，单独进行初始化

    // 初始化 Gson JSON Engine 实现
    DevEngine.defaultGsonEngineImpl()
    // 初始化 Fastjson JSON Engine 实现
    DevEngine.defaultFastjsonEngineImpl()
    // 初始化 DevLogger Log Engine 实现
    DevEngine.defaultDevLoggerEngineImpl(logConfig)
    
    // 初始化 Xxx Engine 实现
    DevEngine.defaultXxxEngineImpl()

    // =======
    // = 使用 =
    // =======

    // =====================
    // = 通过 DevEngine 使用 =
    // =====================

    // JSON Engine 映射
    DevEngine.getJSON().xxx

    // KeyValue Engine 键值对存储
    DevEngine.getKeyValue().xxx

    // Log Engine 日志打印
    DevEngine.getLog().xxx

    // Xxx Engine xxxxx
    DevEngine.getXxxx().xxx

    // =====================
    // = 通过 DevAssist 使用 =
    // =====================

    // JSON Engine 映射
    DevJSONEngine.getEngine().xxx

    // KeyValue Engine 键值对存储
    DevKeyValueEngine.getEngine().xxx

    // Log Engine 日志打印
    DevLogEngine.getEngine().xxx

    // Xxx Engine xxxxx
    DevXxxEngine.getEngine().xxx

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