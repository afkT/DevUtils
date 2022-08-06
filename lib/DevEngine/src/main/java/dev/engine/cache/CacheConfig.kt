package dev.engine.cache

import dev.utils.app.cache.DevCache
import dev.utils.common.cipher.Cipher

/**
 * detail: Cache Config
 * @author Ttt
 */
open class CacheConfig(
    cipher: Cipher?,
    val mDevCache: DevCache
) : ICacheEngine.EngineConfig(cipher)