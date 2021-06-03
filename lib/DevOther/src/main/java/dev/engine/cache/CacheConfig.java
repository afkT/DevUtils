package dev.engine.cache;

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
            Cipher cipher,
            DevCache cache
    ) {
        super(cipher);
        this.mDevCache = cache;
    }
}