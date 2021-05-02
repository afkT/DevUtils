package kotlin.dev.engine.cache;

import dev.engine.cache.ICacheEngine;
import dev.utils.app.cache.DevCache;
import dev.utils.common.cipher.Cipher;

/**
 * detail: Cache Config
 * @author Ttt
 */
public class CacheConfig
        extends ICacheEngine.EngineConfig {

    public final DevCache mDevCache;

    public CacheConfig(
            String cacheID,
            Cipher cipher,
            DevCache cache
    ) {
        super(cacheID, cipher);
        this.mDevCache = cache;
    }
}