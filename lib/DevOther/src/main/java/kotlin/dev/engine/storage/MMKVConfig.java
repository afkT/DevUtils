package kotlin.dev.engine.storage;

import com.tencent.mmkv.MMKV;

import dev.engine.storage.IStorageEngine;
import dev.utils.common.cipher.Cipher;

/**
 * detail: MMKV Storage Config
 * @author Ttt
 */
public class MMKVConfig
        extends IStorageEngine.EngineConfig {

    // MMKV
    private final MMKV mMMKV;

    public MMKVConfig(
            String storageID,
            Cipher cipher,
            MMKV mmkv
    ) {
        super(storageID, cipher);
        this.mMMKV = mmkv;
    }

    public MMKV getMMKV() {
        return mMMKV;
    }
}