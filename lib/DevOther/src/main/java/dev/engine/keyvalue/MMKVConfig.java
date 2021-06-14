package dev.engine.keyvalue;

import com.tencent.mmkv.MMKV;

import dev.utils.common.cipher.Cipher;

/**
 * detail: MMKV Key-Value Config
 * @author Ttt
 */
public class MMKVConfig
        extends IKeyValueEngine.EngineConfig {

    // MMKV
    private final MMKV mMMKV;

    public MMKVConfig(
            Cipher cipher,
            MMKV mmkv
    ) {
        super(cipher);
        this.mMMKV = mmkv;
    }

    public MMKV getMMKV() {
        return mMMKV;
    }
}