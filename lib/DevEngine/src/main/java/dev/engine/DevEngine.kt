package dev.engine

import android.content.Context
import com.tencent.mmkv.MMKV
import dev.DevUtils
import dev.engine.DevEngine.defaultMMKVInitialize
import dev.engine.analytics.DevAnalyticsEngine
import dev.engine.analytics.IAnalyticsEngine
import dev.engine.barcode.BarCodeConfig
import dev.engine.barcode.DevBarCodeEngine
import dev.engine.barcode.IBarCodeEngine
import dev.engine.barcode.ZXingEngineImpl
import dev.engine.cache.CacheConfig
import dev.engine.cache.DevCacheEngine
import dev.engine.cache.DevCacheEngineImpl
import dev.engine.cache.ICacheEngine
import dev.engine.compress.DevCompressEngine
import dev.engine.compress.ICompressEngine
import dev.engine.compress.LubanEngineImpl
import dev.engine.image.DevImageEngine
import dev.engine.image.GlideEngineImpl
import dev.engine.image.IImageEngine
import dev.engine.json.DevJSONEngine
import dev.engine.json.FastjsonEngineImpl
import dev.engine.json.GsonEngineImpl
import dev.engine.json.IJSONEngine
import dev.engine.keyvalue.*
import dev.engine.log.DevLogEngine
import dev.engine.log.DevLoggerEngineImpl
import dev.engine.log.ILogEngine
import dev.engine.media.DevMediaEngine
import dev.engine.media.IMediaEngine
import dev.engine.media.PictureSelectorEngineImpl
import dev.engine.permission.DevPermissionEngine
import dev.engine.permission.IPermissionEngine
import dev.engine.permission.XXPermissionsEngineImpl
import dev.engine.push.DevPushEngine
import dev.engine.push.IPushEngine
import dev.engine.share.DevShareEngine
import dev.engine.share.IShareEngine
import dev.engine.storage.DevMediaStoreEngineImpl
import dev.engine.storage.DevStorageEngine
import dev.engine.storage.IStorageEngine
import dev.engine.toast.DevToastEngine
import dev.engine.toast.IToastEngine
import dev.engine.toast.ToasterEngineImpl
import dev.utils.app.cache.DevCache
import dev.utils.app.logger.LogConfig
import dev.utils.common.cipher.Cipher

/**
 * detail: DevEngine
 * @author Ttt
 * <p></p>
 * GitHub
 * @see https://github.com/afkT/DevUtils
 * DevApp Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevApp/README.md
 * DevAssist Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevAssist/README.md
 * DevBase README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBase/README.md
 * DevBaseMVVM README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevBaseMVVM/README.md
 * DevEngine README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevEngine/README.md
 * DevSimple README
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevSimple/DevSimple/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README.md
 * DevRetrofit Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevRetrofit/README.md
 * DevHttpManager Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpManager/README.md
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 */
object DevEngine {

    // ============
    // = 工具类版本 =
    // ============

    /**
     * 获取 DevEngine 版本号
     * @return DevEngine versionCode
     */
    fun getDevEngineVersionCode(): Int {
        return BuildConfig.DevEngine_VersionCode
    }

    /**
     * 获取 DevEngine 版本
     * @return DevEngine versionName
     */
    fun getDevEngineVersion(): String {
        return BuildConfig.DevEngine_Version
    }

    /**
     * 获取 DevAssist 版本号
     * @return DevAssist versionCode
     */
    fun getDevAssistVersionCode(): Int {
        return BuildConfig.DevAssist_VersionCode
    }

    /**
     * 获取 DevAssist 版本
     * @return DevAssist versionName
     */
    fun getDevAssistVersion(): String {
        return BuildConfig.DevAssist_Version
    }

    // ==========
    // = 快捷方法 =
    // ==========

    /**
     * 创建 MMKV Config
     * @param cipher 加解密中间层
     * @param mmkv [MMKV]
     * @return [MMKVConfig]
     * <p></p>
     * 需先调用 [defaultMMKVInitialize]
     */
    fun createMMKVConfig(
        cipher: Cipher? = null,
        mmkv: MMKV
    ): MMKVConfig {
        return MMKVConfig(cipher, mmkv)
    }

    /**
     * 使用 DevEngine 库内部默认实现 MMKV 初始化
     * @param context Context?
     * @return DevEngine
     */
    fun defaultMMKVInitialize(context: Context?): DevEngine {
        DevUtils.getContext(context)?.let {
            MMKVUtils.initialize(it.applicationContext)
        }
        return this
    }

    // ============
    // = 初始化方法 =
    // ============

    /**
     * 完整初始化 ( 全面使用该库调用该方法初始化即可 )
     * @param context Context
     * @param cacheConfig Cache Engine Config
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     * @param barCodeConfig BarCode Config
     * 如果使用 MMKV 必须先调用 [defaultMMKVInitialize] 默认使用 MMKV
     */
    fun completeInitialize(
        context: Context,
        cacheConfig: CacheConfig? = CacheConfig(null, DevCache.newCache()),
        keyValueConfig: IKeyValueEngine.EngineConfig? = null,
        logConfig: LogConfig? = null,
        barCodeConfig: BarCodeConfig? = null
    ) {
        // 使用 DevEngine 库内部默认实现 MMKV 初始化
        defaultMMKVInitialize(context)

        if (keyValueConfig == null) {
            try {
                // 如果 MMKV 不为 null 则进行初始化
                val mmkv = MMKVUtils.defaultHolder().mmkv
                _defaultEngine(
                    context, cacheConfig,
                    createMMKVConfig(cipher = null, mmkv = mmkv!!),
                    logConfig, barCodeConfig
                )
                return
            } catch (_: Exception) {
            }
        }
        _defaultEngine(context, cacheConfig, keyValueConfig, logConfig, barCodeConfig)
    }

    /**
     * 使用 DevEngine 库内部默认实现 Engine
     * @param context Context
     * @param cacheConfig Cache Engine Config
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     * @param barCodeConfig BarCode Config
     * 如果使用 MMKV 必须先调用 [defaultMMKVInitialize]
     */
    private fun _defaultEngine(
        context: Context,
        cacheConfig: CacheConfig?,
        keyValueConfig: IKeyValueEngine.EngineConfig?,
        logConfig: LogConfig?,
        barCodeConfig: BarCodeConfig?
    ) {
        // ========================
        // = BarCode Engine 条形码 =
        // ========================

        // 初始化 ZXing BarCode Engine 实现
        defaultZXingEngineImpl().also { barCode ->
            barCode.initialize(barCodeConfig)
        }

        // ==============================
        // = Cache Engine 有效期键值对缓存 =
        // ==============================

        cacheConfig?.let { config ->
            // 初始化 DevCache ( DevUtils ) Cache Engine 实现
            defaultDevCacheEngineImpl(config)
        }

        // ================================
        // = Image Compress Engine 图片压缩 =
        // ================================

        // 初始化 Luban Image Compress Engine 实现
        defaultLubanEngineImpl()

        // ====================================
        // = Image Engine 图片加载、下载、转格式等 =
        // ====================================

        // 初始化 Glide Image Engine 实现
        defaultGlideEngineImpl()

        // ===============
        // = JSON Engine =
        // ===============

        // 初始化 Gson JSON Engine 实现
        defaultGsonEngineImpl()
//        // 初始化 Fastjson JSON Engine 实现
//        defaultFastjsonEngineImpl()

        // ============================
        // = KeyValue Engine 键值对存储 =
        // ============================

        keyValueConfig?.let { config ->
            if (config is MMKVConfig) {
                // 初始化 MMKV Key-Value Engine 实现
                defaultMMKVKeyValueEngineImpl(config)
            } else if (config is SPConfig) {
                // 初始化 SharedPreferences Key-Value Engine 实现
                defaultSPKeyValueEngineImpl(config)
            }
        }

        // =====================
        // = Log Engine 日志打印 =
        // =====================

        // 初始化 DevLogger Log Engine 实现
        defaultDevLoggerEngineImpl(logConfig)

        // =====================================
        // = Media Selector Engine 多媒体资源选择 =
        // =====================================

        // 初始化 PictureSelector Media Selector Engine 实现
        defaultPictureSelectorEngineImpl()

        // ============================
        // = Permission Engine 权限申请 =
        // ============================

        // 初始化 XXPermissions Engine 实现
        defaultXXPermissionsEngineImpl()

        // =================================
        // = Storage Engine 外部、内部文件存储 =
        // =================================

        // 初始化 DevUtils MediaStore Engine 实现
        defaultDevMediaStoreEngineImpl()

        // =======================
        // = Toast Engine 吐司提示 =
        // =======================

        // 默认初始化 Toaster Toast Engine 实现
        defaultToasterEngineImpl().also { toast ->
            toast.initialize(DevUtils.getApplication(context))
        }
    }

    // =====================
    // = Engine 设置内部实现 =
    // =====================

    // ========================
    // = BarCode Engine 条形码 =
    // ========================

    /**
     * 默认初始化 ZXing BarCode Engine 实现
     * @return ZXingEngineImpl
     */
    fun defaultZXingEngineImpl(): ZXingEngineImpl {
        return newZXingEngineImpl().apply {
            DevBarCodeEngine.setEngine(this)
        }
    }

    // ==============================
    // = Cache Engine 有效期键值对缓存 =
    // ==============================

    /**
     * 默认初始化 DevCache ( DevUtils ) Cache Engine 实现
     * @param config Cache Config
     * @return DevCacheEngineImpl
     */
    fun defaultDevCacheEngineImpl(config: CacheConfig): DevCacheEngineImpl {
        return newDevCacheEngineImpl(config).apply {
            DevCacheEngine.setEngine(this)
        }
    }

    // ================================
    // = Image Compress Engine 图片压缩 =
    // ================================

    /**
     * 默认初始化 Luban Image Compress Engine 实现
     * @return LubanEngineImpl
     */
    fun defaultLubanEngineImpl(): LubanEngineImpl {
        return newLubanEngineImpl().apply {
            DevCompressEngine.setEngine(this)
        }
    }

    // ====================================
    // = Image Engine 图片加载、下载、转格式等 =
    // ====================================

    /**
     * 默认初始化 Glide Image Engine 实现
     * @return GlideEngineImpl
     */
    fun defaultGlideEngineImpl(): GlideEngineImpl {
        return newGlideEngineImpl().apply {
            DevImageEngine.setEngine(this)
        }
    }

    // ===============
    // = JSON Engine =
    // ===============

    /**
     * 默认初始化 Gson JSON Engine 实现 ( JSON Engine )
     * @return GsonEngineImpl
     */
    fun defaultGsonEngineImpl(): GsonEngineImpl {
        return newGsonEngineImpl().apply {
            DevJSONEngine.setEngine(this)
        }
    }

    /**
     * 默认初始化 Fastjson JSON Engine 实现 ( JSON Engine )
     * @return FastjsonEngineImpl
     */
    fun defaultFastjsonEngineImpl(): FastjsonEngineImpl {
        return newFastjsonEngineImpl().apply {
            DevJSONEngine.setEngine(this)
        }
    }

    // ============================
    // = KeyValue Engine 键值对存储 =
    // ============================

    /**
     * 默认初始化 MMKV Key-Value Engine 实现
     * @param config MMKV Config
     * @return MMKVKeyValueEngineImpl
     */
    fun defaultMMKVKeyValueEngineImpl(config: MMKVConfig): MMKVKeyValueEngineImpl {
        return newMMKVKeyValueEngineImpl(config).apply {
            DevKeyValueEngine.setEngine(this)
        }
    }

    /**
     * 默认初始化 SharedPreferences Key-Value Engine 实现
     * @param config SharedPreferences Config
     * @return SPKeyValueEngineImpl
     */
    fun defaultSPKeyValueEngineImpl(config: SPConfig): SPKeyValueEngineImpl {
        return newSPKeyValueEngineImpl(config).apply {
            DevKeyValueEngine.setEngine(this)
        }
    }

    // =====================
    // = Log Engine 日志打印 =
    // =====================

    /**
     * 默认初始化 DevLogger Log Engine 实现
     * @param logConfig Log Config
     * @return DevLoggerEngineImpl
     */
    fun defaultDevLoggerEngineImpl(logConfig: LogConfig?): DevLoggerEngineImpl {
        return newDevLoggerEngineImpl(logConfig).apply {
            DevLogEngine.setEngine(this)
        }
    }

    /**
     * 默认初始化 DevLogger Log Engine 实现
     * @param logConfig Log Config
     * @param printLog 是否打印日志
     * @return DevLoggerEngineImpl
     */
    fun defaultDevLoggerEngineImpl(
        logConfig: LogConfig?,
        printLog: () -> Boolean
    ): DevLoggerEngineImpl {
        return newDevLoggerEngineImpl(logConfig, printLog).apply {
            DevLogEngine.setEngine(this)
        }
    }

    // =====================================
    // = Media Selector Engine 多媒体资源选择 =
    // =====================================

    /**
     * 默认初始化 PictureSelector Media Selector Engine 实现
     * @return PictureSelectorEngineImpl
     */
    fun defaultPictureSelectorEngineImpl(): PictureSelectorEngineImpl {
        return newPictureSelectorEngineImpl().apply {
            DevMediaEngine.setEngine(this)
        }
    }

    // ============================
    // = Permission Engine 权限申请 =
    // ============================

    /**
     * 默认初始化 XXPermissions Engine 实现
     * @return XXPermissionsEngineImpl
     */
    fun defaultXXPermissionsEngineImpl(): XXPermissionsEngineImpl {
        return newXXPermissionsEngineImpl().apply {
            DevPermissionEngine.setEngine(this)
        }
    }

    // =================================
    // = Storage Engine 外部、内部文件存储 =
    // =================================

    /**
     * 默认初始化 DevUtils MediaStore Engine 实现
     * @return DevMediaStoreEngineImpl
     */
    fun defaultDevMediaStoreEngineImpl(): DevMediaStoreEngineImpl {
        return newDevMediaStoreEngineImpl().apply {
            DevStorageEngine.setEngine(this)
        }
    }

    // =======================
    // = Toast Engine 吐司提示 =
    // =======================

    /**
     * 默认初始化 Toaster Toast Engine 实现
     * @return ToasterEngineImpl
     */
    fun defaultToasterEngineImpl(): ToasterEngineImpl {
        return newToasterEngineImpl().apply {
            DevToastEngine.setEngine(this)
        }
    }

    // ==============
    // = Engine set =
    // ==============

    /**
     * 设置 Analytics Engine
     * @param key    key
     * @param engine {@link IAnalyticsEngine}
     */
    fun <Config : IAnalyticsEngine.EngineConfig, Item : IAnalyticsEngine.EngineItem> setAnalyticsEngine(
        key: String,
        engine: IAnalyticsEngine<Config, Item>
    ) {
        DevAnalyticsEngine.setEngine(key, engine)
    }

    /**
     * 设置 BarCode Engine
     * @param key    key
     * @param engine {@link IBarCodeEngine}
     */
    fun <Config : IBarCodeEngine.EngineConfig, Item : IBarCodeEngine.EngineItem, Result : IBarCodeEngine.EngineResult> setBarCodeEngine(
        key: String,
        engine: IBarCodeEngine<Config, Item, Result>
    ) {
        DevBarCodeEngine.setEngine(key, engine)
    }

    /**
     * 设置 Cache Engine
     * @param key    key
     * @param engine {@link ICacheEngine}
     */
    fun <Config : ICacheEngine.EngineConfig, Item : ICacheEngine.EngineItem> setCacheEngine(
        key: String,
        engine: ICacheEngine<Config, Item>
    ) {
        DevCacheEngine.setEngine(key, engine)
    }

    /**
     * 设置 Compress Engine
     * @param key    key
     * @param engine {@link ICompressEngine}
     */
    fun <Config : ICompressEngine.EngineConfig> setCompressEngine(
        key: String,
        engine: ICompressEngine<Config>
    ) {
        DevCompressEngine.setEngine(key, engine)
    }

    /**
     * 设置 Image Engine
     * @param key    key
     * @param engine {@link IImageEngine}
     */
    fun <Config : IImageEngine.EngineConfig> setImageEngine(
        key: String,
        engine: IImageEngine<Config>
    ) {
        DevImageEngine.setEngine(key, engine)
    }

    /**
     * 设置 JSON Engine
     * @param key    key
     * @param engine {@link IJSONEngine}
     */
    fun <Config : IJSONEngine.EngineConfig> setJSONEngine(
        key: String,
        engine: IJSONEngine<Config>
    ) {
        DevJSONEngine.setEngine(key, engine)
    }

    /**
     * 设置 KeyValue Engine
     * @param key    key
     * @param engine {@link IKeyValueEngine}
     */
    fun <Config : IKeyValueEngine.EngineConfig> setKeyValueEngine(
        key: String,
        engine: IKeyValueEngine<Config>
    ) {
        DevKeyValueEngine.setEngine(key, engine)
    }

    /**
     * 设置 Log Engine
     * @param key    key
     * @param engine {@link ILogEngine}
     */
    fun setLogEngine(
        key: String,
        engine: ILogEngine
    ) {
        DevLogEngine.setEngine(key, engine)
    }

    /**
     * 设置 Media Engine
     * @param key    key
     * @param engine {@link IMediaEngine}
     */
    fun <Config : IMediaEngine.EngineConfig, Data : IMediaEngine.EngineData> setMediaEngine(
        key: String,
        engine: IMediaEngine<Config, Data>
    ) {
        DevMediaEngine.setEngine(key, engine)
    }

    /**
     * 设置 Permission Engine
     * @param key    key
     * @param engine {@link IPermissionEngine}
     */
    fun setPermissionEngine(
        key: String,
        engine: IPermissionEngine
    ) {
        DevPermissionEngine.setEngine(key, engine)
    }

    /**
     * 设置 Push Engine
     * @param key    key
     * @param engine {@link IPushEngine}
     */
    fun <Config : IPushEngine.EngineConfig, Item : IPushEngine.EngineItem> setPushEngine(
        key: String,
        engine: IPushEngine<Config, Item>
    ) {
        DevPushEngine.setEngine(key, engine)
    }

    /**
     * 设置 Share Engine
     * @param key    key
     * @param engine {@link IShareEngine}
     */
    fun <Config : IShareEngine.EngineConfig, Item : IShareEngine.EngineItem> setShareEngine(
        key: String,
        engine: IShareEngine<Config, Item>
    ) {
        DevShareEngine.setEngine(key, engine)
    }

    /**
     * 设置 Storage Engine
     * @param key    key
     * @param engine {@link IStorageEngine}
     */
    fun <Item : IStorageEngine.EngineItem, Result : IStorageEngine.EngineResult> setStorageEngine(
        key: String,
        engine: IStorageEngine<Item, Result>
    ) {
        DevStorageEngine.setEngine(key, engine)
    }

    /**
     * 设置 Toast Engine
     * @param key    key
     * @param engine {@link IToastEngine}
     */
    fun <Config : IToastEngine.EngineConfig, Item : IToastEngine.EngineItem> setToastEngine(
        key: String,
        engine: IToastEngine<Config, Item>
    ) {
        DevToastEngine.setEngine(key, engine)
    }

    // ==========
    // = 获取方法 =
    // ==========

    // ==============
    // = Engine get =
    // ==============

    /**
     * 获取 Analytics Engine
     * @return Analytics Engine
     */
    fun getAnalytics() = DevAnalyticsEngine.getEngine()

    /**
     * 获取 BarCode Engine
     * @return BarCode Engine
     */
    fun getBarCode() = DevBarCodeEngine.getEngine()

    /**
     * 获取 Cache Engine
     * @return Cache Engine
     */
    fun getCache() = DevCacheEngine.getEngine()

    /**
     * 获取 Compress Engine
     * @return Compress Engine
     */
    fun getCompress() = DevCompressEngine.getEngine()

    /**
     * 获取 Image Engine
     * @return Image Engine
     */
    fun getImage() = DevImageEngine.getEngine()

    /**
     * 获取 JSON Engine
     * @return JSON Engine
     */
    fun getJSON() = DevJSONEngine.getEngine()

    /**
     * 获取 KeyValue Engine
     * @return KeyValue Engine
     */
    fun getKeyValue() = DevKeyValueEngine.getEngine()

    /**
     * 获取 Log Engine
     * @return Log Engine
     */
    fun getLog() = DevLogEngine.getEngine()

    /**
     * 获取 Media Engine
     * @return Media Engine
     */
    fun getMedia() = DevMediaEngine.getEngine()

    /**
     * 获取 Permission Engine
     * @return Permission Engine
     */
    fun getPermission() = DevPermissionEngine.getEngine()

    /**
     * 获取 Push Engine
     * @return Push Engine
     */
    fun getPush() = DevPushEngine.getEngine()

    /**
     * 获取 Share Engine
     * @return Share Engine
     */
    fun getShare() = DevShareEngine.getEngine()

    /**
     * 获取 Storage Engine
     * @return Storage Engine
     */
    fun getStorage() = DevStorageEngine.getEngine()

    /**
     * 获取 Toast Engine
     * @return Toast Engine
     */
    fun getToast() = DevToastEngine.getEngine()

    // ==================
    // = Engine Key get =
    // ==================

    /**
     * 获取 Analytics Engine
     * @return Analytics Engine
     */
    fun getAnalytics(key: String?) = DevAnalyticsEngine.getEngine(key)

    /**
     * 获取 BarCode Engine
     * @return BarCode Engine
     */
    fun getBarCode(key: String?) = DevBarCodeEngine.getEngine(key)

    /**
     * 获取 Cache Engine
     * @return Cache Engine
     */
    fun getCache(key: String?) = DevCacheEngine.getEngine(key)

    /**
     * 获取 Compress Engine
     * @return Compress Engine
     */
    fun getCompress(key: String?) = DevCompressEngine.getEngine(key)

    /**
     * 获取 Image Engine
     * @return Image Engine
     */
    fun getImage(key: String?) = DevImageEngine.getEngine(key)

    /**
     * 获取 JSON Engine
     * @return JSON Engine
     */
    fun getJSON(key: String?) = DevJSONEngine.getEngine(key)

    /**
     * 获取 KeyValue Engine
     * @return KeyValue Engine
     */
    fun getKeyValue(key: String?) = DevKeyValueEngine.getEngine(key)

    /**
     * 获取 Log Engine
     * @return Log Engine
     */
    fun getLog(key: String?) = DevLogEngine.getEngine(key)

    /**
     * 获取 Media Engine
     * @return Media Engine
     */
    fun getMedia(key: String?) = DevMediaEngine.getEngine(key)

    /**
     * 获取 Permission Engine
     * @return Permission Engine
     */
    fun getPermission(key: String?) = DevPermissionEngine.getEngine(key)

    /**
     * 获取 Push Engine
     * @return Push Engine
     */
    fun getPush(key: String?) = DevPushEngine.getEngine(key)

    /**
     * 获取 Share Engine
     * @return Share Engine
     */
    fun getShare(key: String?) = DevShareEngine.getEngine(key)

    /**
     * 获取 Storage Engine
     * @return Storage Engine
     */
    fun getStorage(key: String?) = DevStorageEngine.getEngine(key)

    /**
     * 获取 Toast Engine
     * @return Toast Engine
     */
    fun getToast(key: String?) = DevToastEngine.getEngine(key)

    // =================
    // = Engine Assist =
    // =================

    /**
     * 获取 Analytics Engine Generic Assist
     * @return Analytics Engine Generic Assist
     */
    fun getAnalyticsAssist() = DevAnalyticsEngine.getAssist()

    /**
     * 获取 BarCode Engine Generic Assist
     * @return BarCode Engine Generic Assist
     */
    fun getBarCodeAssist() = DevBarCodeEngine.getAssist()

    /**
     * 获取 Cache Engine Generic Assist
     * @return Cache Engine Generic Assist
     */
    fun getCacheAssist() = DevCacheEngine.getAssist()

    /**
     * 获取 Compress Engine Generic Assist
     * @return Compress Engine Generic Assist
     */
    fun getCompressAssist() = DevCompressEngine.getAssist()

    /**
     * 获取 Image Engine Generic Assist
     * @return Image Engine Generic Assist
     */
    fun getImageAssist() = DevImageEngine.getAssist()

    /**
     * 获取 JSON Engine Generic Assist
     * @return JSON Engine Generic Assist
     */
    fun getJSONAssist() = DevJSONEngine.getAssist()

    /**
     * 获取 KeyValue Engine Generic Assist
     * @return KeyValue Engine Generic Assist
     */
    fun getKeyValueAssist() = DevKeyValueEngine.getAssist()

    /**
     * 获取 Log Engine Generic Assist
     * @return Log Engine Generic Assist
     */
    fun getLogAssist() = DevLogEngine.getAssist()

    /**
     * 获取 Media Engine Generic Assist
     * @return Media Engine Generic Assist
     */
    fun getMediaAssist() = DevMediaEngine.getAssist()

    /**
     * 获取 Permission Engine Generic Assist
     * @return Permission Engine Generic Assist
     */
    fun getPermissionAssist() = DevPermissionEngine.getAssist()

    /**
     * 获取 Push Engine Generic Assist
     * @return Push Engine Generic Assist
     */
    fun getPushAssist() = DevPushEngine.getAssist()

    /**
     * 获取 Share Engine Generic Assist
     * @return Share Engine Generic Assist
     */
    fun getShareAssist() = DevShareEngine.getAssist()

    /**
     * 获取 Storage Engine Generic Assist
     * @return Storage Engine Generic Assist
     */
    fun getStorageAssist() = DevStorageEngine.getAssist()

    /**
     * 获取 Toast Engine Generic Assist
     * @return Toast Engine Generic Assist
     */
    fun getToastAssist() = DevToastEngine.getAssist()

    // ==================
    // = 内置 Engine 实现 =
    // ==================

    // ========================
    // = BarCode Engine 条形码 =
    // ========================

    /**
     * 创建 ZXing BarCode Engine 实现
     * @return ZXing BarCode Engine 实现
     */
    fun newZXingEngineImpl(): ZXingEngineImpl {
        return ZXingEngineImpl()
    }

    // ==============================
    // = Cache Engine 有效期键值对缓存 =
    // ==============================

    /**
     * 创建 DevCache ( DevUtils ) Cache Engine 实现
     * @param config Cache Config
     * @return DevCache ( DevUtils ) Cache Engine 实现
     */
    fun newDevCacheEngineImpl(config: CacheConfig): DevCacheEngineImpl {
        return DevCacheEngineImpl(config)
    }

    // ================================
    // = Image Compress Engine 图片压缩 =
    // ================================

    /**
     * 创建 Luban Image Compress Engine 实现
     * @return Luban Image Compress Engine 实现
     */
    fun newLubanEngineImpl(): LubanEngineImpl {
        return LubanEngineImpl()
    }

    // ====================================
    // = Image Engine 图片加载、下载、转格式等 =
    // ====================================

    /**
     * 创建 Glide Image Engine 实现
     * @return Glide Image Engine 实现
     */
    fun newGlideEngineImpl(): GlideEngineImpl {
        return GlideEngineImpl()
    }

    // ===============
    // = JSON Engine =
    // ===============

    /**
     * 创建 Gson JSON Engine 实现 ( JSON Engine )
     * @return Gson JSON Engine 实现 ( JSON Engine )
     */
    fun newGsonEngineImpl(): GsonEngineImpl {
        return GsonEngineImpl()
    }

    /**
     * 创建 Fastjson JSON Engine 实现 ( JSON Engine )
     * @return Fastjson JSON Engine 实现 ( JSON Engine )
     */
    fun newFastjsonEngineImpl(): FastjsonEngineImpl {
        return FastjsonEngineImpl()
    }

    // ============================
    // = KeyValue Engine 键值对存储 =
    // ============================

    /**
     * 创建 MMKV Key-Value Engine 实现
     * @param config MMKV Config
     * @return MMKV Key-Value Engine 实现
     */
    fun newMMKVKeyValueEngineImpl(config: MMKVConfig): MMKVKeyValueEngineImpl {
        return MMKVKeyValueEngineImpl(config)
    }

    /**
     * 创建 SharedPreferences Key-Value Engine 实现
     * @param config SharedPreferences Config
     * @return SharedPreferences Key-Value Engine 实现
     */
    fun newSPKeyValueEngineImpl(config: SPConfig): SPKeyValueEngineImpl {
        return SPKeyValueEngineImpl(config)
    }

    // =====================
    // = Log Engine 日志打印 =
    // =====================

    /**
     * 创建 DevLogger Log Engine 实现
     * @param logConfig Log Config
     * @return DevLogger Log Engine 实现
     */
    fun newDevLoggerEngineImpl(logConfig: LogConfig?): DevLoggerEngineImpl {
        return newDevLoggerEngineImpl(logConfig) {
            // 属于 Debug 模式才打印日志
            DevUtils.isDebug()
        }
    }

    /**
     * 创建 DevLogger Log Engine 实现
     * @param logConfig Log Config
     * @param printLog 是否打印日志
     * @return DevLogger Log Engine 实现
     */
    fun newDevLoggerEngineImpl(
        logConfig: LogConfig?,
        printLog: () -> Boolean
    ): DevLoggerEngineImpl {
        return object : DevLoggerEngineImpl(logConfig) {
            override fun isPrintLog(): Boolean {
                return printLog()
            }
        }
    }

    // =====================================
    // = Media Selector Engine 多媒体资源选择 =
    // =====================================

    /**
     * 创建 PictureSelector Media Selector Engine 实现
     * @return PictureSelector Media Selector Engine 实现
     */
    fun newPictureSelectorEngineImpl(): PictureSelectorEngineImpl {
        return PictureSelectorEngineImpl()
    }

    // ============================
    // = Permission Engine 权限申请 =
    // ============================

    /**
     * 创建 XXPermissions Engine 实现
     * @return XXPermissions Engine 实现
     */
    fun newXXPermissionsEngineImpl(): XXPermissionsEngineImpl {
        return XXPermissionsEngineImpl()
    }

    // =================================
    // = Storage Engine 外部、内部文件存储 =
    // =================================

    /**
     * 创建 DevUtils MediaStore Engine 实现
     * @return DevUtils MediaStore Engine 实现
     */
    fun newDevMediaStoreEngineImpl(): DevMediaStoreEngineImpl {
        return DevMediaStoreEngineImpl()
    }

    // =======================
    // = Toast Engine 吐司提示 =
    // =======================

    /**
     * 默认初始化 Toaster Toast Engine 实现
     */
    fun newToasterEngineImpl(): ToasterEngineImpl {
        return ToasterEngineImpl()
    }
}