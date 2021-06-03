package dev.engine.storage;

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
            Cipher cipher,
            IPreference preference
    ) {
        super(cipher);
        this.mPreference = preference;
    }

    public IPreference getPreference() {
        return mPreference;
    }
}