
## Gradle

```java
implementation 'io.github.afkt:DevEngine:1.0.0'
```

## 目录结构

```
- dev.engine         | 根目录
   - barcode         | BarCode Engine 条形码、二维码处理
   - cache           | Cache Engine 有效期键值对缓存
   - compress        | Image Compress Engine 图片压缩
   - image           | Image Engine 图片加载、下载、转格式等
   - json            | JSON Engine
   - keyvalue        | KeyValue Engine 键值对存储
   - log             | Log Engine 日志打印
   - media           | Media Selector Engine 多媒体资源选择
   - permission      | Permission Engine 权限申请
   - storage         | Storage Engine 外部、内部文件存储
```


## About

DevEngine（基于 [DevAssist Engine 模块](https://github.com/afkT/DevUtils/blob/master/lib/DevAssist) ）
主要为了解决项目代码中**对第三方框架强依赖使用、以及部分功能版本适配。** 通过实现对应功能模块 Engine 接口，实现对应的方法功能，
**对外无需关注实现代码，直接通过 DevXXXEngine 进行调用，实现对第三方框架解耦、一键替换第三方库、同类库多 Engine 混合使用、以及部分功能适配 ( 如外部文件存储 MediaStore 全局适配 ) 等**

> 可通过 Key-Engine 实现对组件化、模块化各个 Module 使用同类型 Engine 不同库实现使用

## 实现信息

> 该库为 Kotlin 实现，另有 [Java 实现代码](https://github.com/afkT/DevUtils/blob/master/lib/DevOther/src/main/java/java/dev/engine)

该库实现模块为 **BarCode 条形码**、**Cache 有效期键值对缓存**、**Image Compress 图片压缩**、**Image 图片加载、下载、转格式**
、**JSON 映射**、**KeyValue 键值对存储**、**Log 日志打印**、**Media Selector 多媒体资源选择**、**Permission 权限申请**
、**Storage Engine 外部、内部文件存储**，且依赖第三方库实现列表如下展示。

> 该库会依赖第三方库导致项目体积变大等情况，可自行 copy 所需已实现库代码进行使用

> 如无法直接使用于需求实现，可自行 copy 代码进行修改，或自定义对应模块 Engine 实现类进行设置并通过 DevXxxEngine.getEngine(key) 进行获取

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

* 依赖 [Fastjson](https://github.com/alibaba/fastjson)
  实现 [FastjsonEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/json/engine_fastjson.kt)


### KeyValue 键值对存储 - [包目录](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/keyvalue)

可选实现方案：

* 依赖 [基于 mmap 的高性能通用 key-value 组件 MMKV](https://github.com/Tencent/MMKV/blob/master/readme_cn.md)
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

* 依赖 DevApp [MediaStoreUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/MediaStoreUtils.java)、
  、[UriUtils](https://github.com/afkT/DevUtils/blob/master/lib/DevApp/src/main/java/dev/utils/app/UriUtils.java)
  实现 [DevMediaStoreEngineImpl](https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/src/main/java/dev/engine/storage/engine_dev_media_store.kt)


