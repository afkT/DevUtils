package ktx.dev.engine.storage;

import dev.engine.storage.IStorageEngine;
import dev.utils.app.share.IPreference;
import dev.utils.common.cipher.Cipher;

/**
 * detail: SharedPreferences Storage Config
 * @author Ttt
 */
public class SPConfig
        extends IStorageEngine.EngineConfig {

    // SharedPreferences
    private final IPreference mPreference;

    public SPConfig(
            String storageID,
            Cipher cipher,
            IPreference preference
    ) {
        super(storageID, cipher);
        this.mPreference = preference;
    }

    public IPreference getPreference() {
        return mPreference;
    }
}