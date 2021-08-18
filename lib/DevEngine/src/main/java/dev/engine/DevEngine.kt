package dev.engine

import android.content.Context
import com.tencent.mmkv.MMKV
import dev.DevUtils
import dev.engine.cache.CacheConfig
import dev.engine.cache.DevCacheEngine
import dev.engine.cache.DevCacheEngineImpl
import dev.engine.compress.DevCompressEngine
import dev.engine.compress.LubanEngineImpl
import dev.engine.image.DevImageEngine
import dev.engine.image.GlideEngineImpl
import dev.engine.json.DevJSONEngine
import dev.engine.json.GsonEngineImpl
import dev.engine.keyvalue.*
import dev.engine.log.DevLogEngine
import dev.engine.log.DevLoggerEngineImpl
import dev.engine.media.DevMediaEngine
import dev.engine.media.PictureSelectorEngineImpl
import dev.engine.permission.DevPermissionEngine
import dev.engine.permission.DevPermissionEngineImpl
import dev.engine.storage.DevMediaStoreEngineImpl
import dev.engine.storage.DevStorageEngine
import dev.utils.app.cache.DevCache
import dev.utils.app.logger.LogConfig

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
 * DevHttpCapture Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevHttpCapture/README.md
 * DevJava Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevJava/README.md
 * DevWidget Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/DevWidget/README_API.md
 * DevEnvironment Api
 * @see https://github.com/afkT/DevUtils/blob/master/lib/Environment
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
     * 获取 DevApp 版本号
     * @return DevApp versionCode
     */
    fun getDevAppVersionCode(): Int {
        return BuildConfig.DevApp_VersionCode
    }

    /**
     * 获取 DevApp 版本
     * @return DevApp versionName
     */
    fun getDevAppVersion(): String {
        return BuildConfig.DevApp_Version
    }

    // ==========
    // = 快捷获取 =
    // ==========

    /**
     * 获取 MMKV 对象
     * @return MMKV
     */
    fun getMMKVByHolder(): MMKV? {
        return MMKVUtils.defaultHolder().mmkv
    }

    // ============
    // = 初始化方法 =
    // ============

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

    /**
     * 使用 DevEngine 库内部默认实现 Engine
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     */
    fun defaultEngine(
        keyValueConfig: IKeyValueEngine.EngineConfig? = null
    ) {
        defaultEngine(
            CacheConfig(null, DevCache.newCache()),
            keyValueConfig
        )
    }

    /**
     * 使用 DevEngine 库内部默认实现 Engine
     * @param cacheConfig Cache Engine Config
     * @param keyValueConfig Key-Value Engine Config
     * @param logConfig Log Config
     * <p><p>
     * 如果 Key-Value 是 MMKV Engine 则需要先调用 MMKV.initialize()
     */
    fun defaultEngine(
        cacheConfig: CacheConfig?,
        keyValueConfig: IKeyValueEngine.EngineConfig?,
        logConfig: LogConfig? = null
    ) {

        // ==============================
        // = Cache Engine 有效期键值对缓存 =
        // ==============================

        cacheConfig?.apply {
            // 初始化 DevCache ( DevUtils ) Cache Engine 实现
            DevCacheEngine.setEngine(DevCacheEngineImpl(this))
        }

        // ================================
        // = Image Compress Engine 图片压缩 =
        // ================================

        // 初始化 Luban Image Compress Engine 实现
        DevCompressEngine.setEngine(LubanEngineImpl())

        // ====================================
        // = Image Engine 图片加载、下载、转格式等 =
        // ====================================

        // 初始化 Glide Image Engine 实现
        DevImageEngine.setEngine(GlideEngineImpl())

        // ===============
        // = JSON Engine =
        // ===============

        // 初始化 Gson JSON Engine 实现
        DevJSONEngine.setEngine(GsonEngineImpl())
//        // 初始化 Fastjson JSON Engine 实现
//        DevJSONEngine.setEngine(FastjsonEngineImpl())

        // ============================
        // = KeyValue Engine 键值对存储 =
        // ============================

        keyValueConfig?.apply {
            if (this is MMKVConfig) {
                // 初始化 MMKV Key-Value Engine 实现
                DevKeyValueEngine.setEngine(MMKVKeyValueEngineImpl(this))
            } else if (this is SPConfig) {
                // 初始化 SharedPreferences Key-Value Engine 实现
                DevKeyValueEngine.setEngine(SPKeyValueEngineImpl(this))
            }
        }

        // =====================
        // = Log Engine 日志打印 =
        // =====================

        // 初始化 DevLogger Log Engine 实现
        DevLogEngine.setEngine(object : DevLoggerEngineImpl(logConfig) {
            override fun isPrintLog(): Boolean {
                // 属于 Debug 模式才打印日志
                return DevUtils.isDebug()
            }
        })

        // =====================================
        // = Media Selector Engine 多媒体资源选择 =
        // =====================================

        // 初始化 PictureSelector Media Selector Engine 实现
        DevMediaEngine.setEngine(PictureSelectorEngineImpl())

        // ============================
        // = Permission Engine 权限申请 =
        // ============================

        // 初始化 DevUtils Permission Engine 实现
        DevPermissionEngine.setEngine(DevPermissionEngineImpl())

        // =================================
        // = Storage Engine 外部、内部文件存储 =
        // =================================

        // 初始化 DevUtils MediaStore Engine 实现
        DevStorageEngine.setEngine(DevMediaStoreEngineImpl())
    }

    // ===========
    // = get/set =
    // ===========


}