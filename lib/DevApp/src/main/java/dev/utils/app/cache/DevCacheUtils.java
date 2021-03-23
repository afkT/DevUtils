package dev.utils.app.cache;

import org.json.JSONObject;

import java.io.File;

import dev.utils.LogPrintUtils;
import dev.utils.common.FileUtils;

/**
 * detail: 缓存内部工具类
 * @author Ttt
 */
final class DevCacheUtils {

    // 日志 TAG
    private static final String TAG = DevCacheUtils.class.getSimpleName();

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
                data.getValidTime(), data.getLastModified()
        );
    }

    /**
     * 读取配置初始化 Data
     * @param cache 缓存对象
     * @param key   存储 key
     * @return {@link DevCache.Data}
     */
    public static DevCache.Data getData(
            final DevCache cache,
            final String key
    ) {
        try {
            File       configFile = getKeyConfigFile(cache, key);
            String     config     = new String(readFileBytes(configFile));
            JSONObject jsonObject = new JSONObject(config);
            if (jsonObject.has("key")
                    && jsonObject.has("type")
                    && jsonObject.has("saveTime")
                    && jsonObject.has("validTime")
                    && jsonObject.has("lastModified")
            ) {
                String _key         = jsonObject.getString("key");
                int    type         = jsonObject.getInt("type");
                long   saveTime     = jsonObject.getLong("saveTime");
                long   validTime    = jsonObject.getLong("validTime");
                long   lastModified = jsonObject.getLong("lastModified");
                return new DevCache.Data(cache.getCachePath(), _key,
                        type, saveTime, validTime, lastModified);
            }
        } catch (Exception e) {
            LogPrintUtils.eTag(TAG, e, "getData");
        }
        return null;
    }

    // ===========
    // = 内部方法 =
    // ===========

    /**
     * 将 byte[] 写入文件
     * @param file  待写入文件
     * @param bytes 待写入数据
     * @return {@code true} success, {@code false} fail
     */
    private static boolean saveFileBytes(
            final File file,
            final byte[] bytes
    ) {
        return FileUtils.saveFile(file, bytes);
    }

    /**
     * 读取文件 byte[]
     * @param file 待读取文件
     * @return 文件 byte[]
     */
    private static byte[] readFileBytes(final File file) {
        return FileUtils.readFileBytes(file);
    }
}