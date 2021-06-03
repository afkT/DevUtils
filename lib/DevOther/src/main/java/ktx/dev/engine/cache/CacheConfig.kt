package ktx.dev.engine.cache

import dev.engine.cache.ICacheEngine
import dev.utils.app.cache.DevCache
import dev.utils.common.cipher.Cipher

/**
 * detail: Cache Config
 * @author Ttt
 */
class CacheConfig(
    cipher: Cipher?,
    val mDevCache: DevCache
) : ICacheEngine.EngineConfig(cipher)