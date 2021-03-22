package dev.utils.app.cache;

import java.io.File;

import dev.utils.common.FileUtils;

/**
 * detail: 缓存内部工具类
 * @author Ttt
 */
final class DevCacheUtils {

    // ===========
    // = 判断方法 =
    // ===========

    /**
     * 获取 Key 配置文件
     * @param cache 缓存对象
     * @param key   存储 key
     * @return Key 配置文件
     */
    public static File getKeyConfigFile(
            final DevCache cache,
            final String key
    ) {
        return getKeyConfigFile(cache.getCachePath(), key);
    }

    /**
     * 获取 Key 数据文件
     * @param cache 缓存对象
     * @param key   存储 key
     * @return Key 数据文件
     */
    public static File getKeyDataFile(
            final DevCache cache,
            final String key
    ) {
        return getKeyDataFile(cache.getCachePath(), key);
    }

    // =

    /**
     * 获取 Key 配置文件
     * @param path 文件地址
     * @param key  存储 key
     * @return Key 配置文件
     */
    public static File getKeyConfigFile(
            final String path,
            final String key
    ) {
        return FileUtils.getFile(path, key + ".json");
    }

    /**
     * 获取 Key 数据文件
     * @param path 文件地址
     * @param key  存储 key
     * @return Key 数据文件
     */
    public static File getKeyDataFile(
            final String path,
            final String key
    ) {
        return FileUtils.getFile(path, key + ".data");
    }

    // =

    /**
     * 判断是否存在 Key 配置、数据文件
     * @param cache 缓存对象
     * @param key   存储 key
     * @return {@code true} yes, {@code false} no
     */
    public static boolean isExistKeyFile(
            final DevCache cache,
            final String key
    ) {
        return FileUtils.isFileExists(getKeyConfigFile(cache, key))
                && FileUtils.isFileExists(getKeyDataFile(cache, key));
    }

    /**
     * 获取存储数据大小
     * @param path 文件地址
     * @param key  存储 key
     * @return 存储数据大小
     */
    public static long getDataFileSize(
            final String path,
            final String key
    ) {
        return FileUtils.getFileLength(getKeyDataFile(path, key));
    }

    // ========
    // = 配置 =
    // ========

    // Data JSON Format
    public static String DATA_FORMAT = "{\"key\":\"%s\",\"type\":%d,\"saveTime\":%d,\"validTime\":%d,\"lastModified\":%d}";

    /**
     * Data Format JSON String
     * @param data 数据源
     * @return JSON String
     */
    public static String toString(final DevCache.Data data) {
        return String.format(DATA_FORMAT, data.getKey(),
                data.getType(), data.getSaveTime(),
                data.getValidTime(), data.getLastModified());
    }

    public static DevCache.Data getData(
            final DevCache cache,
            final String key
    ) {
        return null;
    }
}