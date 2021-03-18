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
     * @param storageID 存储 Engine id
     * @return {@link IStorageEngine}
     */
    public static IStorageEngine getEngine(final String storageID) {
        return sEngineMaps.get(storageID);
    }

    /**
     * 设置 StorageEngine
     * @param engine {@link IStorageEngine}
     */
    public static void setEngine(final IStorageEngine engine) {
        sEngineMaps.put(engine.getConfig().storageID, engine);
    }

    /**
     * 判断是否存在 StorageEngine
     * @param storageID 存储 Engine id
     * @return {@code true} yes, {@code false} no
     */
    public static boolean contains(final String storageID) {
        return sEngineMaps.containsKey(storageID);
    }
}