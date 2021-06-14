package dev.engine.storage;

import dev.utils.common.cipher.Cipher;

/**
 * detail: Storage Engine 接口
 * @author Ttt
 */
public interface IStorageEngine<Config extends IStorageEngine.EngineConfig> {

    /**
     * detail: Storage Config
     * @author Ttt
     */
    class EngineConfig {

        // 通用加解密中间层
        public final Cipher cipher;

        public EngineConfig(
                Cipher cipher
        ) {
            this.cipher = cipher;
        }
    }

    // ===============
    // = 对外公开方法 =
    // ===============

    /**
     * 获取 Storage Engine Config
     * @return {@link Config}
     */
    Config getConfig();

    // =

    /**
     * 是否存在 key
     * @param key 保存的 key
     * @return {@code true} yes, {@code false} no
     */
    boolean contains(String key);
}