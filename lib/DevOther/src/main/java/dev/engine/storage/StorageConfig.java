package dev.engine.storage;

import com.tencent.mmkv.MMKV;

import dev.utils.app.share.IPreference;
import dev.utils.common.cipher.Cipher;

/**
 * detail: Storage Config
 * @author Ttt
 */
public class StorageConfig
        extends IStorageEngine.EngineConfig {

    // MMKV
    private MMKV        mMMKV;
    // SharedPreferences
    private IPreference mPreference;

    public StorageConfig(
            String storageID,
            Cipher cipher
    ) {
        super(storageID, cipher);
    }

    public MMKV getMMKV() {
        return mMMKV;
    }

    public void setMMKV(MMKV mmkv) {
        if (this.mMMKV != null) return;
        this.mMMKV = mmkv;
    }

    public IPreference getPreference() {
        return mPreference;
    }

    public void setPreference(IPreference preference) {
        if (this.mPreference != null) return;
        this.mPreference = preference;
    }
}