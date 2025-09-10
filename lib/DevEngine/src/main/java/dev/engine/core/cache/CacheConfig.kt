package dev.engine.core.cache

import dev.engine.cache.ICacheEngine
import dev.utils.app.cache.DevCache
import dev.utils.common.cipher.Cipher

/**
 * detail: Cache Config
 * @author Ttt
 */
open class CacheConfig(
    val mDevCache: DevCache,
    // 通用加解密中间层
    val cipher: Cipher? = null
) : ICacheEngine.EngineConfig