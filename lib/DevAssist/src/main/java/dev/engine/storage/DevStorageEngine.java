package dev.engine.storage;

import java.util.LinkedHashMap;

/**
 * detail: Storage Engine
 * @author Ttt
 */
public final class DevStorageEngine {

    private DevStorageEngine() {
    }

    private static LinkedHashMap<String, IStorageEngine> sEngineMaps = new LinkedHashMap<>();

    /**
     * 获取 StorageEngine
     * @param storageID 存储 id
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine getEngine(final String storageID) {
        return sEngineMaps.get(storageID);
    }

    /**
     * 设置 StorageEngine
     * @param engine {@link IStorageEngine}
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine setEngine(final IStorageEngine engine) {
        return sEngineMaps.get(engine.getConfig().storageID);
    }
}